package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.project.AuditDocFileDao;
import com.yskc.rs.dao.project.DocFileAttachmentDao;
import com.yskc.rs.entity.project.AuditDocFileEntity;
import com.yskc.rs.entity.project.DocFileAttachmentEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.AttachmentModel;
import com.yskc.rs.models.project.DocFileAttachmentModel;
import com.yskc.rs.models.project.QueryAttachmentModel;
import com.yskc.rs.models.project.RdManageMenuModel;
import com.yskc.rs.models.project.RdManageModel;
import com.yskc.rs.service.DocFileAttachmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: hck
 * @DateTime: 2021/6/17 14:59
 * @Description:
 */
@Service
public class DocFileAttachmentServiceImpl implements DocFileAttachmentService {

    @Autowired
    private DocFileAttachmentDao docFileAttachmentDao;
    @Autowired
    private AuditDocFileDao auditDocFileDao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public AttachmentModel save(UserInfo userInfo, AttachmentModel model) throws OwnerException {
        if (model.getDocFileId() != null) {
            List<AuditDocFileEntity> docs = auditDocFileDao.getDocAuditStatus(model.getProjectId(), Arrays.asList(model.getDocFileId()));
            if (userInfo.getUserSource() != 0 && !CollectionUtils.isEmpty(docs)) {
                AuditDocFileEntity audit = docs.get(0);
                if (audit.getStatus() != 2 && audit.getStatus() != 3 && audit.getStatus() != 5) {
                    throw new OwnerException("已提交审核，不能进行该操作！");
                }
            }
        }
        Date date = new Date();
        DocFileAttachmentEntity entity;
        if (model.getId() != null) {
            entity = docFileAttachmentDao.selectById(model.getId());
            BeanUtils.copyProperties(model, entity);
            entity.update(userInfo.getUserId(), userInfo.getMsUserId(), date);
            entity.setDocFileId(model.getDocFileId());
            entity.setFileType(model.getFileType());
            docFileAttachmentDao.updateById(entity);
        } else {
            entity = new DocFileAttachmentEntity();
            BeanUtils.copyProperties(model, entity);
            entity.setFilePath(model.getFilePath());
            entity.setCompanyId(userInfo.getCompanyId());
            entity.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
            model.setId(entity.getId());
            entity.setDocFileId(model.getDocFileId());
            entity.setFileType(model.getFileType());
            docFileAttachmentDao.insert(entity);
        }
        return model;
    }


    @Override
    public Boolean del(Integer id) {
        return docFileAttachmentDao.deleteById(id) > 0;
    }

    @Override
    public List<AttachmentModel> getAttachments(Integer docFileId, Integer projectId) {
        // ProjectDocFileEntity docFile=projectDocFileDao.selectById(docFileId);
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        //2021/6/15  验证报告地点数据来源项目的部门/车间/产线/工艺段
        List<String> strs = new ArrayList<>();
        if (!StringUtils.isEmpty(projectEntity.getDeptName())) {
            strs.add(projectEntity.getDeptName());
        }
        if (!StringUtils.isEmpty(projectEntity.getWorkshop())) {
            strs.add(projectEntity.getWorkshop());
        }
        if (!StringUtils.isEmpty(projectEntity.getProductLine())) {
            strs.add(projectEntity.getProductLine());
        }
        if (!StringUtils.isEmpty(projectEntity.getProcessSection())) {
            strs.add(projectEntity.getProcessSection());
        }
        String place = CollectionUtils.isEmpty(strs) ? "" : org.apache.commons.lang3.StringUtils.join(strs, "/");
        List<AttachmentModel> models = docFileAttachmentDao.getByDocFile(projectId, docFileId);
        if (!CollectionUtils.isEmpty(models)) {
            models.forEach(item -> {
                item.setPlace(place);
            });
        }
        return models;
    }

    @Override
    public List<RdManageModel> getRdManagerMenu(Integer year, Integer companyId) {
        List<ProjectEntity> projects = projectDao.getRdManages(year, companyId);
        Map<String, List<ProjectEntity>> preMap = new LinkedHashMap<>();
        Map<String, RdManageModel> preMenuMap = new HashMap<>();
        List<RdManageModel> resultModels = new ArrayList<>();
        if (!CollectionUtils.isEmpty(projects)) {
            String preMenuKey;
            for (ProjectEntity project : projects) {
                preMenuKey = project.getDeptName();
                if (!preMap.containsKey(preMenuKey)) {
                    RdManageModel model = new RdManageModel();
                    BeanUtils.copyProperties(project, model);
                    model.setMenu(project.getDeptName());
                    preMenuMap.put(preMenuKey, model);
                    List<ProjectEntity> projectList = new ArrayList<>();
                    preMap.put(preMenuKey, projectList);
                }
                preMap.get(preMenuKey).add(project);
            }

            String menuKey;
            for (String key : preMap.keySet()) {
                Map<String, ProjectEntity> nextMenuMap = new HashMap<>();
                List<ProjectEntity> projectEntities = preMap.get(key);
                List<RdManageMenuModel> menuModels = new ArrayList<>();
                for (ProjectEntity projectEntity : projectEntities) {
                    menuKey = projectEntity.getProcessSection();
                    if (!nextMenuMap.containsKey(menuKey)) {
                        RdManageMenuModel menuModel = new RdManageMenuModel();
                        BeanUtils.copyProperties(projectEntity, menuModel);
                        menuModel.setMenuInfo(projectEntity.getProcessSection());
                        menuModels.add(menuModel);
                        nextMenuMap.put(menuKey, projectEntity);
                    }
                }
                RdManageModel manageModel = preMenuMap.get(key);
                manageModel.setModels(menuModels);
                resultModels.add(manageModel);
            }
        }
        return resultModels;
    }

    @Override
    public PageModel<List<DocFileAttachmentModel>> getList(Integer companyId, QueryAttachmentModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("pda.uploadTime"));
        }
        Date begin = DateUtil.beginOfMonth(query.getMonth());
        Date end = DateUtil.endOfMonth(query.getMonth());
        return PageUtils.build(page, docFileAttachmentDao.getList(page, companyId, query, begin, end));
    }

}
