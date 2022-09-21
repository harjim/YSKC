package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.DocListEntity;
import com.yskc.rs.models.docFile.NewReportsModel;
import com.yskc.rs.models.sysDocument.DocListModel;
import com.yskc.rs.models.sysDocument.FileTypeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-08 10:03:17
 */
@Repository
public interface DocListDao extends BaseMapper<DocListEntity> {

    int getListType(@Param("listId") Integer listId);

    List<FileTypeModel> getFileTypes();

    /**
     * 获取项目查新报告列表
     *
     * @param page
     * @param projectId
     * @return
     */
    List<NewReportsModel> getDocList(@Param("page") Pagination page, @Param("projectId") Integer projectId);

    /**
     * 获取项目查新报告列表
     * @param projectId
     * @return
     */
    List<NewReportsModel> getReports(@Param("projectId") Integer projectId);

    /**
     * 更新信息
     *
     * @param model
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    int updateInfo(@Param("model") DocListModel model,@Param("userId") Integer userId,@Param("msUserId") Integer msUserId,@Param("now") Date now);

    /**
     * 获取最新插入的一条数据的id
     **/
    Integer getLastId();
}
