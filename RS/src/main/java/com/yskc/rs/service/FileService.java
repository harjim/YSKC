package com.yskc.rs.service;

import com.yskc.rs.entity.FileEntity;
/**
 * @author Administrator
 */
public interface FileService {
    /**
     *
     * @param entity
     * @return
     */
    boolean insertFile(FileEntity entity);

    /**
     *
     * @param companyId
     * @param projectId
     * @return
     */
    FileEntity queryFilePath(int companyId, Integer projectId);

    /**
     *
     * @param entity
     * @return
     */
    Boolean delFile(FileEntity entity);
}
