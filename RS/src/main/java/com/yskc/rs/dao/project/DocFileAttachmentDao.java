package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.DocFileAttachmentEntity;
import com.yskc.rs.models.docFile.AttachmentModel;
import com.yskc.rs.models.project.DocFileAttachmentModel;
import com.yskc.rs.models.project.QueryAttachmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
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
     * @param projectId
     * @param docFileId
     * @return
     */
    List<AttachmentModel> getByDocFile(@Param("projectId") Integer projectId, @Param("docFileId") Integer docFileId);

    /**
     * 根据多级研发管理附件列表
     * @param page
     * @param companyId
     * @param query
     * @param begin
     * @param end
     * @return
     */
    List<DocFileAttachmentModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryAttachmentModel query, @Param("begin") Date begin, @Param("end") Date end);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int addBatch(@Param("list") List<DocFileAttachmentEntity> list);
}
