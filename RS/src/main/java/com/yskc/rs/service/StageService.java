package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.company.CompanyStageEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.StageExcel;
import com.yskc.rs.models.stage.QueryStageModel;
import com.yskc.rs.models.stage.StageModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface StageService {
    /**
     * @param companyId
     * @param queryStageModel
     * @return
     */
    List<StageModel> queryStage(Integer companyId, QueryStageModel queryStageModel);

    /**
     * 查询c_stage获取stageName
     * @param companyId
     * @return
     */
    List<Map<String,Object>> getCStage(Integer companyId);
    /**
     *查询项目立项阶段
     * @param companyId
     * @return
     */
    List<CompanyStageEntity> getStage(Integer companyId);

    /**
     *修改/添加 项目立项阶段
     * @param companyId
     * @return
     */
    boolean saveStage(Integer companyId, Integer projectId, List<CompanyStageEntity> list, UserInfo userInfo,Boolean ChangeCStage) throws OwnerException;

    /**
     * @param userInfo
     * @param model
     * @return
     */
    boolean addStage(UserInfo userInfo, StageModel model)throws OwnerException;

    /**
     * @param model
     * @param info
     * @return
     */
    boolean delStage(StageEntity model, UserInfo info) throws OwnerException;

    /**
     * 编辑阶段
     *
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean editStage(UserInfo userInfo, StageEntity model) throws OwnerException;

    /**
     * 导入数据
     *
     * @param info
     * @param stageExcels
     * @return
     * @throws OwnerException
     */
    boolean importStage(UserInfo info, List<StageExcel> stageExcels, Integer projectId) throws OwnerException;

    /**
     * 编辑阶段
     *
     * @param userInfo
     * @param stageList
     * @param autoAdd
     * @param type
     * @return
	 * @throws OwnerException
     */
    boolean editList(UserInfo userInfo, List<StageEntity> stageList,Boolean autoAdd,Integer type)throws OwnerException;
}
