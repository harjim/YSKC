package com.yskc.rs.service;

import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.tech.ProjectAppendixEntity;
import com.yskc.rs.models.tech.ProjectAppendixModel;

import java.util.List;
/**
 * @author Administrator
 */
public interface ProjectAppendixService {
    /**
     *
     * @param companyId
     * @param projectId
     * @return
     */
    List<ProjectAppendixEntity> selectByProjectId(Integer companyId, Integer projectId);

    /**
     *
     * @param entity
     * @return
     */
    Integer insertFile(ProjectAppendixEntity entity);

    /**
     *
     * @param type
     * @param projectId
     * @return
     */
    boolean del(Integer type, Integer projectId);

    /**
     *
     * @param fileName
     * @param projectId
     * @param companyId
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageModel<List<ProjectAppendixModel>> queryDocument(String fileName, Integer projectId, Integer companyId, int pageNo, int pageSize);

    /**
     *
     * @param documentFile
     */
    void uploadReport(ProjectAppendixEntity documentFile);

    /**
     *
     * @param id
     * @return
     */
    boolean delAuditReport(Integer id);
}
