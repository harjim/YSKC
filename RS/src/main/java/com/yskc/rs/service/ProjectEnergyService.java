package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.projectenergy.*;
import com.yskc.rs.models.projectfinaschedule.ProjectFinaScheduleModel;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigModel;
import com.yskc.rs.models.projectyieldconfig.QueryYieldConfigModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 研发能源业务接口层
 *
 * @author zhangdingfu
 */
public interface ProjectEnergyService extends TrialAggService {

    /**
     * 查询
     *
     * @param companyId
     * @param queryEnergy
     * @return
     */
    PageResult queryProjectEnergy(Integer companyId, QueryProjectEnergy queryEnergy);

    /**
     * 批量删除能源
     *
     * @param userInfo
     * @param delBatchModel
     * @return
     * @throws OwnerException
     */
    boolean delEnergies(UserInfo userInfo, BatchProjectEnergyModel delBatchModel) throws OwnerException;

    /**
     * 查询能源损耗列表
     *
     * @param companyId
     * @param queryEnergy
     * @return
     */
    PageResult getEnergyList(Integer companyId, QueryProjectEnergy queryEnergy);

    /**
     * 批量添加能源
     *
     * @param userInfo
     * @param energyAddListModel
     * @return
     * @throws OwnerException
     */
    boolean addList(UserInfo userInfo, EnergyAddListModel energyAddListModel) throws OwnerException;

    /**
     * 按条件批量添加
     *
     * @param userInfo
     * @param queryEnergy
     * @return
     * @throws OwnerException
     */
    boolean addByCondition(UserInfo userInfo, QueryProjectEnergy queryEnergy) throws OwnerException;

    /**
     * 批量更新研发费用
     *
     * @param userInfo
     * @param updateProjectEnergy
     * @return
     * @throws OwnerException
     */
    boolean updateList(UserInfo userInfo, UpdateProjectEnergy updateProjectEnergy, Boolean subtract) throws OwnerException;

    /**
     * 能源同步折旧工时
     *
     * @param userInfo
     * @param query
     * @return
     * @throws OwnerException
     */
    boolean syncDepreciation(UserInfo userInfo, QueryProjectEnergy query) throws OwnerException;

    /**
     * 设置能源研发工时
     *
     * @param userInfo
     * @param update
     * @return
     * @throws OwnerException
     */
    boolean setRdHour(UserInfo userInfo, UpdateProjectEnergy update) throws OwnerException;

    /**
     * 设置能源总产量/试制量
     *
     * @param userInfo
     * @param yieldMap
     * @param ratioMap
     * @param query
     * @return
     * @throws OwnerException
     */
    boolean setYield(UserInfo userInfo, Map<String, ProjectYieldConfigModel> yieldMap, Map<String, BigDecimal> ratioMap, QueryYieldConfigModel query) throws OwnerException;

    /**
     * 通过财务排期表【动力试验/试制实际工时】
     *
     * @param hourMap
     * @param month
     * @param userInfo
     * @param type
     * @param keyFormat
     * @return
     * @throws OwnerException
     */
    boolean aggByFinaScheduleHours(Map<String, ProjectFinaScheduleModel> hourMap, Date month, UserInfo userInfo, int type, String keyFormat) throws OwnerException;

    /**
     * 查询研发试制，动力明细表
     * @param companyId
     * @param queryEnergy
     * @return
     */
    PageModel<List<HighTechProjectEnergyModel>> getEnergies(Integer companyId, QueryProjectEnergy queryEnergy);
}
