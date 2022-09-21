package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.energy.EnergyModel;
import com.yskc.rs.models.energy.EnergyModifyModel;
import com.yskc.rs.models.energy.QueryEnergy;
import com.yskc.rs.models.excel.EnergyExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-11 08:18
 * @Description: 能源损耗业务层接口
 */
public interface EnergyService {

    /**
     * 查询公司所有能源损耗
     *
     * @param companyId
     * @param queryEnergy
     * @return
     */
    PageModel<List<EnergyModel>> queryAll(Integer companyId, QueryEnergy queryEnergy);

    /**
     * 导入数据
     *
     * @param info
     * @param energyExcels
     * @param type
     * @return
     * @throws OwnerException
     */
    boolean importEnergy(UserInfo info, List<EnergyExcel> energyExcels, Integer type, Integer year) throws OwnerException;


    /**
     * 获取导出报表内容
     *
     * @param companyId
     * @param queryEnergy
     * @return
     */
    List<EnergyExcel> getExportEnergyList(Integer companyId, QueryEnergy queryEnergy);

    /**
     * 删除数据
     *
     * @param id
     * @return
     * @throws OwnerException
     */
    Boolean deleteById(Integer id) throws OwnerException;

    /**
     * 添加能源
     *
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    Boolean addEnergy(UserInfo userInfo, EnergyModel model)throws OwnerException;

    /**
     * 更新能源
     *
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    Boolean updateEnergy(UserInfo userInfo, EnergyModel model)throws OwnerException;

    /**
     * 设置研发部门
     *
     * @param model
     * @return
     */
    boolean setRdDept(EnergyModifyModel model);


    /**
     * 批量删除能源损耗
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    Boolean deleteByIds(EnergyModifyModel model) throws OwnerException;
}
