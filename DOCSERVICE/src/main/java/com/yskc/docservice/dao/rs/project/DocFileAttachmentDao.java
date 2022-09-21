package com.yskc.docservice.dao.rs.project;

import com.yskc.docservice.models.rs.docfile.AttachmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author: hck
 * @DateTime: 2021/6/17 14:38
 * @Description:
 */
@Repository
public interface DocFileAttachmentDao {

    /**
     * 根据文档获取附件列表
     * @param projectId
     * @param docFileId
     * @return
     */
    List<AttachmentModel> getByDocFile(@Param("projectId") Integer projectId, @Param("docFileId") Integer docFileId);
}
