package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.models.AttachmentModel;
import com.yskc.ms.entity.rs.models.DocFileAttachmentEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/6/17 14:38
 * @Description:
 */
@Repository
public interface DocFileAttachmentDao extends BaseMapper<DocFileAttachmentEntity> {

    /**
     * 根据文档获取附件列表
     *
     * @param docFileId
     * @param projectId
     * @return
     */
    List<AttachmentModel> getByDocFile(@Param("projectId") Integer projectId, @Param("docFileId") Integer docFileId);
}
