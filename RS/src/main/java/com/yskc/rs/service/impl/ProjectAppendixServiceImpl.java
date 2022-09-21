package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.tech.TechProjectAppendixDao;
import com.yskc.rs.entity.tech.ProjectAppendixEntity;
import com.yskc.rs.models.tech.ProjectAppendixModel;
import com.yskc.rs.service.ProjectAppendixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectAppendixServiceImpl implements ProjectAppendixService {
    @Autowired
    TechProjectAppendixDao techProjectAppendixDao;

    @Override
    public List<ProjectAppendixEntity> selectByProjectId(Integer companyId, Integer projectId) {
        return techProjectAppendixDao.selectByProjectId(companyId, projectId);
    }

    @Override
    public Integer insertFile(ProjectAppendixEntity entity) {

        ProjectAppendixEntity projectAppendixEntity = techProjectAppendixDao.selectByType(entity.getProjectId(), entity.getType());

        if (projectAppendixEntity == null) {
            techProjectAppendixDao.insert(entity);
        } else {
            entity.setId(projectAppendixEntity.getId());
            techProjectAppendixDao.updateById(entity);
        }
        return entity.getId();
    }

    @Override
    public boolean del(Integer type, Integer projectId) {
        techProjectAppendixDao.updateByType(projectId, type);
        return true;
    }

    @Override
    public PageModel<List<ProjectAppendixModel>> queryDocument(String fileName, Integer projectId, Integer companyId, int pageNo, int pageSize) {
        Pagination pagination = new Pagination(pageNo, pageSize);
        return PageUtils.build(pagination, techProjectAppendixDao.queryDocument(pagination, fileName, projectId, companyId));
    }

    @Override
    public void uploadReport(ProjectAppendixEntity documentFile) {
        techProjectAppendixDao.insert(documentFile);
    }

    @Override
    public boolean delAuditReport(Integer id) {
        return techProjectAppendixDao.deleteById(id)>0;
    }
}
