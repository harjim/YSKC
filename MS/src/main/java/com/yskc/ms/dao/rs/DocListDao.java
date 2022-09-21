package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.DocListEntity;
import com.yskc.ms.entity.rs.SysDocumentEntity;
import com.yskc.ms.entity.rs.models.DocListModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.doctemplate.QueryDocModel;
import com.yskc.ms.models.rs.DocumentListModel;
import com.yskc.ms.models.rs.NewReportsModel;
import com.yskc.ms.models.rs.QueryDocListModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-08 10:03:17
 */
@Repository
public interface DocListDao extends BaseMapper<DocListEntity> {

    List<DocListModel> queryDocList(@Param("page") Pagination page, @Param("query") QueryDocModel query, @Param("perm") DataPermModel perm);

    List<SysDocumentEntity> selectByListId(@Param("listId") Integer listId);

    Integer selectMaxSeq(@Param("listType") Integer listType);

    /**
     * 获取查新报告列表
     *
     * @return
     */
    List<NewReportsModel> getDocList(@Param("page") Pagination page, @Param("projectId") Integer projectId);

    /**
     * 获取项目查新报告列表
     *
     * @param projectId
     * @return
     */
    List<NewReportsModel> getReports(@Param("projectId") Integer projectId);

    /**
     * 获取创新体系文件列表
     *
     * @param page
     * @param query
     * @return
     */
    List<DocumentListModel> getDocuments(@Param("page") Pagination page, @Param("query") QueryDocListModel query);
}
