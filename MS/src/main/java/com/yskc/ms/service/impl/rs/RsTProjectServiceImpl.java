package com.yskc.ms.service.impl.rs;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.ProjectDao;
import com.yskc.ms.dao.rs.BeianDao;
import com.yskc.ms.dao.rs.RsProductDao;
import com.yskc.ms.dao.rs.RsProjectListDao;
import com.yskc.ms.dao.rs.RsTProjectDao;
import com.yskc.ms.entity.rs.BeianEntity;
import com.yskc.ms.entity.rs.RsProductEntity;
import com.yskc.ms.entity.rs.RsProjectEntity;
import com.yskc.ms.entity.rs.RsProjectListEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.rs.*;
import com.yskc.ms.service.rs.RsTProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by hck
 * on 2020/10/15 11:35
 * description:技改项目实现类
 */
@Service
public class RsTProjectServiceImpl implements RsTProjectService {

    @Autowired
    private RsTProjectDao rsTProjectDao;
    @Autowired
    private RsProductDao rsProductDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private RsProjectListDao rsProjectListDao;
    @Autowired
    private BeianDao rsBeianDao;

    @Override
    public PageModel<List<RsTechProjectModel>> getList(QueryTechProjectModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("pj.lastUpdateTime");
            page.setDescs(descs);
        }
        List<RsTechProjectModel> listModels = projectDao.getTechProjects(page, query, dataPerm);
        if (!CollectionUtils.isEmpty(listModels)) {
            List<Integer> techProjectIds = new ArrayList<>();
            List<Integer> beianProjectIds = new ArrayList<>();
            Map<Integer, RsTechProjectModel> dataMap = new HashMap<>();
            listModels.forEach(item -> {
                Integer id = item.getId();
                dataMap.put(id, item);
                if (item.getBeian()) {
                    beianProjectIds.add(id);
                } else {
                    techProjectIds.add(id);
                }
            });
            List<TechProjectResultModel> projects = new ArrayList<>();
            if (!CollectionUtils.isEmpty(techProjectIds)) {
                List<TechProjectResultModel> techProjects = rsTProjectDao.getBySourceIds(techProjectIds);
                if (!CollectionUtils.isEmpty(techProjects)) {
                    projects.addAll(techProjects);
                }
            }
            if (!CollectionUtils.isEmpty(beianProjectIds)) {
                List<TechProjectResultModel> beianProjects = rsBeianDao.getBySourceIds(beianProjectIds);
                if (!CollectionUtils.isEmpty(beianProjects)) {
                    projects.addAll(beianProjects);
                }
            }
            if (!CollectionUtils.isEmpty(projects)) {
                projects.forEach(item -> {
                    RsTechProjectModel model = dataMap.get(item.getSourceProjectId());
                    if (null != model) {
                        model.setDirection(item.getReportType());
                        model.setYear(item.getPyear());
                        model.setProductName(item.getPname());
                        model.setTproductId(item.getProductId());
                        model.setAddressCode(item.getAddressCode());
                        model.settProjectId(item.getId());
                        model.setReportYear(item.getReportYear());
                    }
                });
            }
        }
        return PageUtils.build(page, listModels);
    }

    @Override
    public List<RsProductListModel> getProductList(String addressCode, Integer year) {
        List<RsProductListModel> products = rsProductDao.getProductList(addressCode, year);
        return products;
    }


    @Override
    public boolean relatedProject(RelatedProjectModel model, UserInfo userInfo) {
        Date date = new Date();
        RsProductEntity rsProductEntity = rsProductDao.selectById(model.getProductId());
        if (rsProductEntity == null) {
            return false;
        }
        RsProjectEntity rsProjectEntity = rsTProjectDao.getBySourceProject(model.getSourceProjectId());
        if (rsProjectEntity != null) {
            //编辑操作
            if (judgeRelatedProject(model.getProductId())) {
                //可修改申报项目及方向
                rsProjectEntity.setDirectionId(model.getDirectionId());
                rsProjectEntity.setProductId(model.getProductId());
                rsProjectEntity.setPname(rsProductEntity.getProductName());
                rsProjectEntity.setReportType(model.getDirection());
            }
            rsProjectEntity.setAidType(model.getAidType());
            rsProjectEntity.setMasterName(model.getMasterName());
            rsProjectEntity.setMasterTel(model.getMasterTel());
            rsProjectEntity.setLinkName(model.getLinkName());
            rsProjectEntity.setLinkTel(model.getLinkTel());
            rsProjectEntity.setRemark(model.getRemark());
            rsProjectEntity.setApplyDate(model.getApplyDate());
            rsProjectEntity.setBeginDate(model.getBeginDate());
            rsProjectEntity.setEndDate(model.getEndDate());
            rsProjectEntity.setLastUpdateTime(date);
            rsProjectEntity.setLastUpdatorId(userInfo.getId());
            rsProjectEntity.setMsLastUpdatorId(userInfo.getId());
            rsProjectEntity.setPyear(model.getPyear());
            return rsTProjectDao.updateById(rsProjectEntity) > 0;
        } else {
            RsProjectEntity entity = new RsProjectEntity();
            BeanUtils.copyProperties(model, entity);
            entity.setPname(rsProductEntity.getProductName());
            entity.setReportType(model.getDirection());
            entity.setCreateTime(date);
            entity.setCreatorId(userInfo.getId());
            entity.setMsCreatorId(userInfo.getId());
            entity.setLastUpdateTime(date);
            entity.setLastUpdatorId(userInfo.getId());
            entity.setMsLastUpdatorId(userInfo.getId());
            return rsTProjectDao.insert(entity) > 0;
        }
    }

    @Override
    public RelatedProjectModel getProject(Integer projectId, Boolean beian) {
        if (beian) {
            return rsBeianDao.getById(projectId);
        }
        RelatedProjectModel model = rsTProjectDao.getByProjectId(projectId);
        if (model != null) {
            if (judgeRelatedProject(projectId)) {
                model.setHasUsed(false);
            } else {
                model.setHasUsed(true);
            }
        }
        return model;
    }

    @Override
    public boolean saveBeian(RelatedProjectModel model, UserInfo userInfo) throws OwnerException {
        Date now = new Date();
        if (null == model.getProjectId()) {
            model.setProjectId(rsBeianDao.getBySourceProjectId(model.getSourceProjectId()));
        }
        if (null != model.getProjectId()) {
            return rsBeianDao.updateBeian(model.getProjectId(), model.getPyear(), model.getRemark(), now, userInfo.getId()) > 0;
        }
        if (model.getCompanyId() < 1) {
            throw new OwnerException("未开通客户端，请开通后关联申报项目！");
        }
        return rsBeianDao.insert(new BeianEntity(model, userInfo.getId(), now)) > 0;
    }

    @Override
    public boolean disassociation(RsTechProjectModel model, UserInfo userInfo) throws OwnerException {
        Integer projectId = model.gettProjectId();
        if (model.getBeian()) {
            // 当前projectId = beianId 【该id从getList方法出】
            if (rsBeianDao.countData(projectId) > 0) {
                throw new OwnerException("该项目已有备案数据，不能取消关联。");
            }
            return rsBeianDao.deleteById(projectId) > 0;
        }
        List<RsProjectListEntity> rsProjectList = rsProjectListDao.getByProject(projectId);
        if (!CollectionUtils.isEmpty(rsProjectList)) {
            throw new OwnerException("该项目已有申报数据，不能取消关联。");
        }
        return rsTProjectDao.deleteById(projectId) > 0;
    }

    /**
     * 判断申报项目是否已存在上传文件
     *
     * @param projectId
     * @return
     */
    private Boolean judgeRelatedProject(Integer projectId) {
        List<RsProjectListEntity> projectList = rsProjectListDao.getByProject(projectId);
        if (!CollectionUtils.isEmpty(projectList)) {
            return false;
        }
        return true;
    }
}
