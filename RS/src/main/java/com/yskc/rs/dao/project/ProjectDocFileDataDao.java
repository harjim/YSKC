package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectDocFileDataEntity;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.entity.project.ProjectYieldConfigEntity;
import com.yskc.rs.models.docFile.DocFileDataModel;
import com.yskc.rs.models.docFile.DocFileInfoModel;
import com.yskc.rs.models.docFile.MeetingFromModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-26 10:07
 * @Description: 项目文档dao层
 */
@Repository
public interface ProjectDocFileDataDao extends BaseMapper<ProjectDocFileDataEntity> {
    /**
     * 获取项目文档数据
     *
     * @param pDocFileId
     * @return
     */
    DocFileDataModel getData(@Param("pDocFileId") Integer pDocFileId);

    /**
     * 获取项目所有过程文件
     *
     * @param ids
     * @return
     */
    List<DocFileDataModel> getFileDatas(@Param("ids") List<Integer> ids);

    /**
     * 根据pDocFileId获取模板数据
     *
     * @param pDocFileId
     * @return
     */
    ProjectDocFileDataEntity getByDocId(@Param("pDocFileId") Integer pDocFileId);

    /**
     * 获取项目验证报告数据
     *
     * @param projectId
     * @return
     */
    List<ProjectDocFileEntity> getDesignReportData(@Param("projectId") Integer projectId);

    /**
     * 获取文档数据
     *
     * @param docFileIds
     * @return
     */
    List<ProjectDocFileDataEntity> getDocFiles(@Param("docFileIds") List<Integer> docFileIds);

    /**
     * 根据文档id删除内容
     *
     * @param docFileId
     * @return
     */
    Boolean deleteByDocId(@Param("docFileId") Integer docFileId);

    /**
     * 获取需要进行项目进度分析的文档
     *
     * @param projectId
     * @param docFileIds
     * @return
     */
    List<DocFileDataModel> getAnalysisDocFile(@Param("projectId") Integer projectId, @Param("docFileIds") List<Integer> docFileIds);

    /**
     * 获取项目所有试制
     *
     * @param projectId
     * @return
     */
    List<ProjectYieldConfigEntity> getTrialList(@Param("projectId") Integer projectId);

    /**
     * 统计附件数
     *
     * @param companyId
     * @param projectId
     * @return
     */
    Integer countAttachments(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    /**
     * 统计 项目进度分析成果数
     * @param companyId
     * @param projectId
     * @return
     */
    int countAnalysisResult(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);

    /**
     * 统计项目进度分析专利数
     * @param companyId
     * @param projectId
     * @return
     */
    int countAnalysisPatent(@Param("companyId") Integer companyId,@Param("projectId") Integer projectId);

    /**
     * 统计 项目进度分析查新数
     * @param companyId
     * @param projectId
     * @return
     */
    int countAnalysisNewReport(@Param("companyId") Integer companyId,@Param("projectId") Integer projectId);

    /**
     * 根据文件Id获取data数据
     * @param fileIds
     * @return
     */
    List<DocFileDataModel> getDataByFileIds(@Param("fileIds") List<Integer> fileIds);
}
