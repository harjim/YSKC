package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.AuditDocFileEntity;
import com.yskc.rs.models.projectDocFile.ProjectDocFileModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2021/1/25 9:02
 * description:
 */
@Repository
public interface AuditDocFileDao extends BaseMapper<AuditDocFileEntity> {
    /**
     * 获取文件审核状态
     * @param projectId
     * @param docFileIds
     * @return
     */
    List<AuditDocFileEntity> getDocAuditStatus(@Param("projectId") Integer projectId, @Param("docFileIds") List<Integer> docFileIds);

    /**
     * 统计过程文件审核数据
     * @param projectId
     * @return
     */
    List<ProjectDocFileModel> countAuditData(@Param("projectId") Integer projectId);

    /**
     * 批量添加
     * @param inserts
     * @return
     */
    Integer batchAdd(@Param("inserts") List<AuditDocFileEntity> inserts);

    /**
     * 批量更新
     * @param updateIds
     * @param date
     * @param msUserId
     * @param status
     * @return
     */
    Integer batchUpdate(@Param("updateIds") List<Integer> updateIds, @Param("date") Date date, @Param("msUserId") Integer msUserId, @Param("status") Integer status);

    /**
     * 获取过程文件的审核状态
     * @param docFileIds
     * @return
     */
    List<AuditDocFileEntity> getByDocFileId(@Param("docFileIds") List<Integer> docFileIds, @Param("status") List<Integer> status);

    /**
     * 获取审核状态
     * @param projectId
     * @param moduleId
     * @return
     */
    Integer getAuditStatus(@Param("projectId") Integer projectId, @Param("moduleId") int moduleId);

    /**
     * 统计审核文档（财务文档默认为无状态）
     * @param docFileId
     * @param status
     * @return
     */
    int countAuditDoc(@Param("docFileId") Integer docFileId,@Param("status") List<Integer> status);
}
