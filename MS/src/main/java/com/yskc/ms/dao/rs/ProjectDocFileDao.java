package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.ProjectDocFileEntity;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.entity.rs.models.ProjectDocFileModel;
import com.yskc.ms.models.project.ProjectProgressModel;
import com.yskc.ms.models.project.ProjectReportModel;
import com.yskc.ms.models.projectAudit.AuditDocFileModel;
import com.yskc.ms.models.rs.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProjectDocFileDao extends BaseMapper<ProjectDocFileEntity> {

    List<ProjectDocFileModel> queryProjectDocFile(@Param("projectList") List<ProjectEntity> projectList);

    Integer queryFileById(Integer templateId);

    List<RsProjectDocFileModel> selectByStageList(@Param("projectId") Integer projectId, @Param("stageList") List<String> stageList);

    List<RsStageModel> queryFileList(@Param("projectId") Integer projectId,@Param("pDocFileIds") List<Integer> pDocFileIds);

    /**
     * 获取公司项目立项报告数量
     *
     * @param list
     * @return
     */
    List<ProjectReportModel> getReportNum(@Param("list") List<ProjectProgressModel> list);

    /**
     * 获取公司项目过程文件统计
     *
     * @param year
     * @param companyId
     * @return
     */
    List<DocAuditModel> countAuditInfo(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 获取项目文档统计【已审核/已提交】
     *
     * @param type       统计类型 不为空统计备查资料
     * @param projectIds
     * @return
     */
    List<RsProjectListModel> getDocCnt(@Param("projectIds") List<Integer> projectIds, @Param("type") Integer type);

    /**
     * 过程文档数
     *
     * @param projectIds
     * @param type       type!=null 查询备查资料
     * @return
     */
    List<AuditDocFileModel> getDocNum(@Param("projectIds") List<Integer> projectIds, @Param("type") Integer type);

    /**
     * 根据项目获取文件信息
     *
     * @param projectId
     * @param docFileIds
     * @return
     */
    List<DocFileInfoModel> getFileInfo(@Param("projectId") Integer projectId, @Param("docFileIds") List<Integer> docFileIds);

    /**
     * 获取项目某阶段最大排序号
     *
     * @param projectId
     * @param stage
     * @return
     */
    Integer getMaxSeq(@Param("projectId") Integer projectId, @Param("stage") String stage);

    /**
     * 获取备查资料中过程文件
     *
     * @param projectId
     * @return
     */
    List<DataModel> getBackupDataFile(@Param("projectId") Integer projectId);

    /**
     * 获取项目的备查资料
     *
     * @param projectIds
     * @return
     */
    List<Integer> getBackupDataFiles(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取项目留存备查资料Id
     * @param projectId
     * @return
     */
    List<Integer> getBackupDataFileIds(@Param("projectId") Integer projectId);

    /**
     * 获取文档(年度技术总结报告)
     *
     * @param projectId
     * @return
     */
    ProjectDocFileEntity getDocFile(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 获取过程文件详情
     *
     * @param pDocFileId
     * @return
     */
    DocFileInfoModel getDocInfo(@Param("pDocFileId") Integer pDocFileId);

    /**
     * 获取文档(年度技术总结报告)同年公司所有
     *
     * @param projectIds
     * @return
     */
    List<ProjectDocFileEntity> getDocFiles(@Param("projectIds") Set<Integer> projectIds, @Param("year") Integer year);

    /**
     * 获取文档数据
     *
     * @param id
     * @param docFileId
     * @return
     */
    DataModel getDocData(@Param("id") Integer id, @Param("docFileId") Integer docFileId);

    List<DataModel> getReportData();
    List<String> getProjectNames();

    /**
     * 获取项目立项报告id
     * @param projectId
     * @return
     */
    Integer getRdReportId(@Param("projectId") Integer projectId);
}
