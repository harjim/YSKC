package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.models.docFile.*;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.hightechprogress.HighTechFileModel;
import com.yskc.rs.models.hightechprogress.QueryHighTechFileModel;
import com.yskc.rs.models.projectDocFile.ProjectDocFileModel;
import com.yskc.rs.models.stage.StageModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ProjectDocFileDao extends BaseMapper<ProjectDocFileEntity> {
    @Options(useGeneratedKeys = true)
    Integer insertList(List<ProjectDocFileEntity> entities);

    List<ProjectDocFileModel> selectByStageList(@Param("projectId") Integer projectId,@Param("stageList")  List<String> stageList);

    List<StageModel> queryFileList(@Param("projectId") Integer projectId,@Param("pDocFileIds") List<Integer> pDocFileIds);

    /**
     * 根据项目id和阶段查询文件列表
     * @param projectId
     * @param stage
     * @return
     */
    List<ProjectDocFileEntity> queryByProject(@Param("projectId")Integer projectId,@Param("stage")String stage);
    /**
     * 获取项目试验试制通知单内容
     * @param projectId
     * @param id
     * @return
     */
    List<ReceptionModel> getReceptions(@Param("projectId") Integer projectId,@Param("id") int id);

    /**
     * 获取会议纪要内容
     * @param projectId
     * @return
     */
    List<MeetingSignInModel> getMeeting(@Param("projectId") Integer projectId);

    /**
     * 获取项目阶段指定顺序的文件
     * @param projectId
     * @param stage
     * @param seq
     * @return
     */
    ProjectDocFileEntity getFileByOrder(@Param("projectId") Integer projectId, @Param("stage") String stage, @Param("seq") Integer seq);

    /**
     * 批量修改文件阶段
     * @param docFiles
     * @return
     */
    Boolean updateStages(@Param("docFiles") List<ProjectDocFileEntity> docFiles);


    List<ProjectDocFileEntity> verifyDoc(@Param("docFileIds") List<Integer> docFileIds, @Param("projectId") Integer projectId);

    /**
     * 更新数据状态（假删）
     * @param ids
     * @param date
     * @param userId
     * @param msUserId
     * @return
     */
    Boolean updateDataStatus(@Param("ids") List<Integer> ids, @Param("date") Date date, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId);

    /**
     * 根据项目获取文件信息
     * @param projectId
     * @param docFileId
     * @return
     */
    List<DocFileInfoModel> getFileInfo(@Param("projectId") Integer projectId, @Param("docFileId") Integer docFileId);

    /**
     * 获取备查资料过程文件审核状态
     * @param projectId
     * @return
     */
    List<DocFileInfoModel> getAuditStatus(@Param("projectId") Integer projectId);

    /**
     * 获取备查资料中过程文件
     * @param projectId
     * @return
     */
    List<DataModel> getBackupDataFile(@Param("projectId") Integer projectId);

    /**
     * 获取属于财务的过程文件
     * @param pdocFileIds
     * @return
     */
    List<ProjectDocFileEntity> getFinanceFile(@Param("pdocFileIds") List<Integer> pdocFileIds);

    /**
     * 验证文档可否提交
     * @param docFileId
     * @return
     */
    List<SubmitFileModel> verifySubmitFile(@Param("docFileId") List<Integer> docFileId);

    /**
     * 获取项目某阶段最大排序号
     * @param projectId
     * @param stage
     * @return
     */
    Integer getMaxSeq(@Param("projectId") Integer projectId,@Param("stage") String stage);

    /**
     * count项目阶段文档
     * @param projectId
     * @param stage
     * @return
     */
    int countByProject(@Param("projectId") Integer projectId,@Param("stage") String stage);

    /**
     * 获取已经存在的文档
     * @param stageKeys
     * @param projectId
     * @return
     */
    List<ProjectDocFileEntity> getExistSingleDocFiles(@Param("stages") List<String> stageKeys,@Param("projectId")Integer projectId);

    /**
     * 根据文档id获取过程文件
     * @param docIds
     * @return
     */
    List<ProjectDocFileEntity> getByDocIds(@Param("docIds") List<Integer> docIds,
                                           @Param("projectId") Integer projectId,
                                           @Param("stageKey") String stageKey);

    /**
     * 获取项目某阶段所有过程文件
     * @param stage
     * @param projectId
     * @return
     */
    List<ProjectDocFileEntity> getDocsByStage(@Param("stage") String stage, @Param("projectId") Integer projectId);

    /**
     * 批量更新文档排序
     * @param updateList
     * @return
     */
    Boolean updateSeq(@Param("updateList") List<ProjectDocFileEntity> updateList);

    /**
     * 获取项目各个阶段最大文档seq
     * @param projectId
     * @return
     */
    List<ProjectDocFileEntity> getStageMaxSeq(@Param("projectId") Integer projectId);

    /**
     * 获取文档(年度技术总结报告)
     * @param projectId
     * @return
     */
    ProjectDocFileEntity getDocFile(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 获取效用报告
     * @param projectId
     * @return
     */
    ProjectDocFileEntity getUtilityReportForm(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 获取过程文件详情
     * @param pDocFileId
     * @return
     */
    DocFileInfoModel getDocInfo(@Param("pDocFileId") Integer pDocFileId);

    /**
     * 获取项目没有阶段的过程文件
     * @param projectId
     * @return
     */
    List<ProjectDocFileEntity> getFileNoStage(@Param("projectId") Integer projectId);

    /**
     * 获取记录附件数据【插入p_docFile_attachment】
     * @return
     */
    List<Map<String, Object>> getAppendixData();

    /**
     * 根据阶段唯一获取项目文档
     * @param docIds
     * @param stageKey
     * @param projectId
     * @param docFileId 不为空排除该文档id
     * @return
     */
    List<ProjectDocFileEntity> getDocFileByStage(@Param("docIds") List<Integer> docIds,
                                                @Param("stageKey") String stageKey,
                                                @Param("projectId") Integer projectId,
                                                 @Param("docFileId")Integer docFileId);

    /**
     * 根据年唯一获取文档
     * @param docFileIds
     * @param projectId
     * @param year
     * @param id 排除该文件
     * @return
     */
    List<ProjectDocFileEntity> getDocFileByYear(@Param("docFileIds") List<Integer> docFileIds, @Param("projectId") Integer projectId,
                                                @Param("year") Integer year,@Param("id") Integer id);

    /**
     * 根据年获取文档
     * @param docFileIds
     * @param projectId
     * @param year
     * @param id 排除该文件
     * @return
     */
    List<ProjectDocFileEntity> getDocByYear(@Param("docFileIds") List<Integer> docFileIds, @Param("projectId") Integer projectId,
                                            @Param("year") Integer year,@Param("id") Integer id);

    /**
     * 根据月份获取项目相同类型过程文件
     * @param projectId
     * @param beginMonth
     * @param endMonth
     * @param docFileId
     * @param id 排除文件
     * @return
     */
    List<ProjectDocFileEntity> getDocFileByMonth(@Param("projectId") Integer projectId, @Param("beginMonth") Date beginMonth,
                                                 @Param("endMonth") Date endMonth, @Param("docFileId") Integer docFileId,
                                                 @Param("id") Integer id);

    /**
     * 获取文档详情
     * @param ids
     * @return
     */
    List<DocFileInfoModel> getFileDetail(@Param("ids") List<Integer> ids);

    /**
     * 根据项目id获取当年所有项目的会议纪要
     * @param projectIds
     * @param month
     * @return
     */
    List<ProjectDocFileEntity> getMeetingFormIds(@Param("projectIds") List<Integer> projectIds,@Param("month") Date month);

    /**
     * 获取需要修改排序的文档
     * @param model
     * @param seq
     * @param ids
     * @return
     */
    List<ProjectDocFileEntity> selectEditList(@Param("model") ChangeDocStageModel model,@Param("seq") Integer seq,@Param("ids") List<Integer> ids);

    /**
     * 根据项目获取高新材料文档
     * @param model
     * @return
     */
    List<HighTechFileModel> getHighTechFiles(@Param("model") QueryHighTechFileModel model);
}
