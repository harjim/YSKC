package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.MeetingSignInModel;
import com.yskc.rs.models.docFile.ReceptionModel;
import com.yskc.rs.models.employee.EmployeeModel;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.hightechprogress.HighTechFileModel;
import com.yskc.rs.models.hightechprogress.QueryHighTechFileModel;
import com.yskc.rs.models.projectDocFile.ProjectDocFileModel;
import com.yskc.rs.models.stage.StageModel;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProjectDocFileService {
    Boolean addDocList(UserInfo info, ProjectDocFileModel model)throws OwnerException;

    /**
     * 查询过程阶段文档
     *
     * @param info
     * @param projectId
     * @param year
     * @return
     * @throws OwnerException
     */
    List<StageModel> queryStage(UserInfo info, Integer projectId, int year)throws OwnerException;

    boolean delete(ProjectDocFileModel model, UserInfo userInfo)throws OwnerException;

    boolean editDocFileName(UserInfo userInfo, ProjectDocFileEntity entity) throws OwnerException;

    /**
     * 研发项目资料清单
     *
     * @param projectId
     * @return
     */
    List<StageModel> queryFileList(Integer projectId, int year);

    /**
     * 获取参会人员列表
     * @param projectId
     * @param companyId
     * @param docDate
     * @return
     */
    List<EmployeeSelectModel> getMeetingEmployee(Integer projectId, int companyId, Date docDate,Integer pDocFileId);

    /**
     * 获取项目试验试制通知单接收部门情况
     * @param projectId
     * @param id
     * @return
     */
    List<ReceptionModel> getReception(Integer projectId,int id);

    /**
     * 获取项目会议纪要情况
     * @param projectId
     * @return
     */
    List<MeetingSignInModel> getMeeting(Integer projectId, Integer companyId, Integer year) throws Exception;

    /**
     * 获取参会人员评审委员会名单
     * @param projectId
     * @param companyId
     * @return
     */
    List<EmployeeSelectModel> getAuditor(Integer projectId, Integer companyId,Date docDate,Integer pDocFileId);

    /**
     * 获取项目阶段
     * @param projectId
     * @param companyId
     * @return
     */
    List<StageModel> getReportStage(Integer projectId, Integer companyId);

    /**
     * 过滤不能重复添加的过程文件
     * @param project
     * @param asList
     */
    void filterRepeatAdd(ProjectEntity project, List<Integer> asList, String stageKey, Integer year) throws OwnerException;

    /**
     * 限制文档添加唯一性
     * @param docFileEntity
     * @param projectId
     * @param month
     * @throws OwnerException
     */
     void limitDocFileOnly(ProjectDocFileEntity docFileEntity,Integer projectId,Date month)throws OwnerException;

    /**
     * 批量删除过程文件/更新文件状态
     * @param ids
     * @param userInfo
     * @return
     */
    boolean deleteDocFiles(List<Integer> ids, UserInfo userInfo) throws OwnerException;

    List<EmployeeSelectModel> getList(Integer companyId,Integer year,String ename);

    /**
     * 获取高新材料文档
     * @param model
     * @return
     */
    List<HighTechFileModel> getHighTechFiles(QueryHighTechFileModel model);
}
