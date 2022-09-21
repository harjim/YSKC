package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.FinancialConditionEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.FinancialConditionExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: wangxing
 * @CreateTime: 2019-09-25 11:30
 * @Description: FinancialConditionService
 */
public interface FinancialConditionService {
    /**
     * @param companyId
     * @param year
     * @return
     */
    List<FinancialConditionEntity> queryFinancialCond(Integer companyId, Integer year);

    /**
     * 新增
     *
     * @param info
     * @param financialConditionEntity
     * @return
     */

    boolean addFinancialCond(UserInfo info, FinancialConditionEntity financialConditionEntity) throws OwnerException;

    /**
     * 修改
     *
     * @param info
     * @param entity
     * @return
     */
    boolean updateFinancialCond(UserInfo info, FinancialConditionEntity entity);

    /**
     * 导入财务状况
     *
     * @param info
     * @param excels
     * @return
     * @throws OwnerException
     */
    boolean importFinancialCondition(UserInfo info, List<FinancialConditionExcel> excels) throws OwnerException;

    /**
     * @param id
     * @return
     */
    boolean delFinancialCondition(Integer id);

    /**
     * 获取近几年财务状况
     *
     * @param companyId
     * @param years
     * @return
     */
    List<FinancialConditionEntity> getFinancialCond(Integer companyId, Integer[] years);

    /**
     *
     * @param companyId
     * @param year
     * @return
     */
    boolean delYear(Integer companyId, Integer year);


    /**
     * 获取公司年份的财务状况
     *
     * @param companyId
     * @param years
     * @return
     */
    FinancialConditionEntity getDataByTerm(Integer companyId, List<Integer> years);
}
