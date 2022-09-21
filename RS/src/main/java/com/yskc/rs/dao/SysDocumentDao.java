package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.SysDocumentEntity;
import com.yskc.rs.models.highscore.ChildBuildTypeModel;
import com.yskc.rs.models.sysDocument.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-16 11:19:57
 */
@Repository
public interface SysDocumentDao extends BaseMapper<SysDocumentEntity> {

    /**
     * 查询文档
     *
     * @param pagination
     * @param query
     * @param companyId
     * @return
     */
    List<SysDocumentModel> queryDocument(@Param("pagination") Pagination pagination,
                                         @Param("query") QuerySysDocumentModel query,
                                         @Param("companyId") Integer companyId);

    SysDocumentModel selectByParam(@Param("companyId") Integer companyId, @Param("fileType") Integer fileType, @Param("year") Integer year, @Param("projectId") Integer projectId);

    List<SysDocumentModel> queryAppendixList(@Param("pagination") Pagination pagination, @Param("fileName") String fileName, @Param("fileType") Integer[] fileType, @Param("companyId") int companyId, @Param("year") String year, @Param("projectId") String projectId);

    List<SysDocumentEntity> queryDocByProjectIds(@Param("projectIds") List<Integer> projectIds);

    List<DocListModel> queryDocList(@Param("listType") int listType, @Param("companyId") int companyId);

    List<SysDocumentModel> queryDocForDocList(@Param("companyId") int companyId, @Param("ids") List<Integer> ids, @Param("projectId") Integer projectId, @Param("patentNo") String patentNo, @Param("year") int year);

    List<SysDocumentEntity> selectByListId(@Param("listId") Integer listId);

    List<SysDocumentEntity> selectByPatentNo(@Param("patentNo") String patentNo);

    /**
     * 获取项目查新报告列表
     *
     * @param projectId
     * @return
     */
    List<SysDocumentEntity> getReportByProject(@Param("projectId") Integer projectId);

    /**
     * 批量新增文档
     *
     * @param entities
     * @return
     */
    Integer insertDocuments(@Param("entities") List<SysDocumentEntity> entities);

    /**
     * 查询建设事项下对应年份的document附件
     *
     * @param listId
     * @param year
     * @return
     */
    List<SysDocumentModel> selectFileNameAndId(@Param("page") Pagination page, @Param("listId") Integer listId, @Param("year") Integer year, @Param("companyId") Integer companyId);


    List<SysDocumentEntity> selectByYearAndComId(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 查询建设事项 存在文件的之前的年份
     *
     * @param year
     * @param companyId
     * @return
     */
    List<Integer> getYear(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 获取机构建设事项的文件类型
     *
     * @param companyId
     * @param beginYear
     * @param endYear
     * @return
     */
    List<ChildBuildTypeModel> getChildBuildTypes(@Param("companyId") Integer companyId, @Param("beginYear") Integer beginYear,
                                                 @Param("endYear") Integer endYear);
}
