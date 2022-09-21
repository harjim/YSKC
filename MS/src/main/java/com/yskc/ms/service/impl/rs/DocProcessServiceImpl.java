package com.yskc.ms.service.impl.rs;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.ms.dao.rs.DocProcessDao;
import com.yskc.ms.dao.rs.DocProcessTemplateDao;
import com.yskc.ms.entity.rs.DocProcessEntity;
import com.yskc.ms.entity.rs.DocProcessTemplateEntity;
import com.yskc.ms.entity.rs.models.DocProcessModel;
import com.yskc.ms.entity.rs.models.DocProcessTemplateListModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.service.rs.DocProcessService;
import com.yskc.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.rs
 * @Author: wangxing
 * @CreateTime: 2019-08-05 11:48
 * @Description: 过程管理业务实现类
 */
@Service
public class DocProcessServiceImpl implements DocProcessService {
    @Autowired
    private DocProcessDao dao;
    @Autowired
    private DocProcessTemplateDao processTemplateDao;

    @Override
    public PageModel<List<DocProcessModel>> queryDocProcess(int pageNo, int pageSize, String processName) {
        Pagination pagination = new Pagination(pageNo, pageSize);
        List<DocProcessModel> entityList = dao.queryDocProcess(pagination, processName);
        for (int i = 0; i < entityList.size(); i++) {
            int docProcessId = entityList.get(i).getId();
            List<DocProcessTemplateListModel> listModels = dao.queryListModels(docProcessId);
            entityList.get(i).setTemplateListModels(listModels);
        }
        return PageUtils.build(pagination, entityList);
    }

    @Override
    public PageModel<List<DocProcessTemplateListModel>> getDataById(int pageNo, int pageSize, int id) {
        Pagination pagination = new Pagination(pageNo, pageSize);
        return PageUtils.build(pagination, dao.getDataById(pagination, id));
    }

    @Override
    public boolean addDocProcessAndProcessTemplate(UserInfo info, DocProcessModel model) {
        List<DocProcessTemplateListModel> processModels = model.getTemplateListModels();
        List<DocProcessTemplateEntity> entities = new ArrayList<>();
        DocProcessEntity entity = new DocProcessEntity();
        Date now = new Date();
        entity.setCreateTime(now);
        entity.setLastMsUpdatorId(info.getId());
        entity.setLastUpdateTime(now);
        entity.setLastMsUpdatorId(info.getId());
        entity.setMsCreatorId(info.getId());
        entity.setType(0);
        entity.setProcessName(model.getProcessName());
        if (dao.insert(entity) > 0) {
            if (processModels.size() > 0) {
                for (int i = 0; i < processModels.size(); i++) {
                    DocProcessTemplateListModel listModel = processModels.get(i);
                    DocProcessTemplateEntity processTemplateEntity = new DocProcessTemplateEntity();
                    processTemplateEntity.setProcessId(entity.getId());
                    processTemplateEntity.setTemplateId(listModel.getTemplateId());
                    processTemplateEntity.setSeq(listModel.getSeq());
                    processTemplateEntity.setEnabled(true);
                    processTemplateEntity.setOptional(true);
                    entities.add(processTemplateEntity);
                }
                processTemplateDao.addProcessTemplateBatch(entities);
            }
        }
        return true;
    }

    @Override
    public boolean updateDocProcessAndProcessTemplate(UserInfo info, DocProcessModel model) {
        //先文件配置表
        processTemplateDao.delProcessTemplateByProcessId(model.getId());
        //删阶段表
        dao.deleteById(model.getId());
        List<DocProcessTemplateListModel> processModels = model.getTemplateListModels();
        List<DocProcessTemplateEntity> entities = new ArrayList<>();
        DocProcessEntity entity = new DocProcessEntity();
        Date now = new Date();
        entity.setCreateTime(now);
        entity.setLastMsUpdatorId(info.getId());
        entity.setLastUpdateTime(now);
        entity.setLastMsUpdatorId(info.getId());
        entity.setMsCreatorId(info.getId());
        entity.setType(0);
        entity.setProcessName(model.getProcessName());
        if (dao.insert(entity) > 0) {
            if (processModels.size() > 0) {
                for (int i = 0; i < processModels.size(); i++) {
                    DocProcessTemplateListModel listModel = processModels.get(i);
                    DocProcessTemplateEntity processTemplateEntity = new DocProcessTemplateEntity();
                    processTemplateEntity.setProcessId(entity.getId());
                    processTemplateEntity.setTemplateId(listModel.getTemplateId());
                    processTemplateEntity.setSeq(listModel.getSeq());
                    processTemplateEntity.setEnabled(true);
                    processTemplateEntity.setOptional(true);
                    entities.add(processTemplateEntity);
                }
                processTemplateDao.addProcessTemplateBatch(entities);
            }
        }
        return true;
    }

    @Override
    public boolean delDocProcessAndProcessTemplate(DocProcessModel model) {
        processTemplateDao.delProcessTemplateByProcessId(model.getId());
        dao.deleteById(model.getId());
        return true;
    }

    @Override
    public boolean delProcessTemplate(int id) {
        return processTemplateDao.deleteById(id) > 0;
    }
}
