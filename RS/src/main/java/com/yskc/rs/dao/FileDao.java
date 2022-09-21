package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.FileEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-29 13:52:34
 */
@Repository
public interface FileDao extends BaseMapper<FileEntity> {

    FileEntity selectByProjectId(@Param("projectId") Integer projectId);

    FileEntity queryFilePath(@Param("companyId") int companyId, @Param("projectId") Integer projectId);

    Boolean deleteByProjectId(@Param("entity")FileEntity entity);
}
