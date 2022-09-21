package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;

/**
 * @author Administrator
 */
public interface InsuranceConfigService {
    /**
     *
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    //boolean saveInsuranceConfig(UserInfo userInfo, InsuranceConfigEntity model) throws OwnerException;

    /**
     *
     * @param userInfo
     * @param companyId
     * @param deptId
     * @param enumber
     * @return
     */
    //List<InsuranceConfigEntity> queryInsuranceConfig(UserInfo userInfo, Integer companyId, Integer deptId, String enumber);

    /**
     *
     * @param model
     * @return
     */
    //boolean delInsuranceConfig(InsuranceConfigEntity model);

    /**
     *
     * @param model
     * @return
     */
    //boolean updateInsurance(InsuranceConfigEntity model);


    /**
     * 调整五险一金比例
     * @param userInfo
     * @param settingInsuranceConfig
     * @return
     * @throws OwnerException
     */
    //boolean set(UserInfo userInfo,SettingInsuranceConfig settingInsuranceConfig)throws OwnerException;

    /**
     * 根据公司获取当前五险一金配置
     * @param companyId
     * @return
     */
    // getInsuranceConfig(Integer companyId);
}
