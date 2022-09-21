package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.SalaryExcel;
import com.yskc.rs.models.fieldconfig.FieldConfigModel;
import com.yskc.rs.models.salary.SalaryInfoModel;
import com.yskc.rs.models.salary.SalaryModel;
import com.yskc.rs.models.salary.SalaryQuery;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 */
public interface SalaryService {
    /**
     * 获取薪资表
     *
     * @param salaryQuery
     * @return
     */
    PageModel<List<SalaryModel>> querySalary(SalaryQuery salaryQuery);

    /**
     * 删除薪资
     *
     * @param companyId
     * @param syModel
     * @return
     * @throws OwnerException
     */
    boolean delSalary(int companyId, SalaryModel syModel) throws OwnerException;

    /**
     * 修改薪资
     *
     * @param info
     * @param syModel
     * @return
     * @throws OwnerException
     */
    boolean updateSalary(UserInfo info, SalaryModel syModel) throws OwnerException;

    /**
     * 新增薪资
     *
     * @param info
     * @param syModel
     * @return
     * @throws OwnerException
     */
    boolean addSalary(UserInfo info, SalaryModel syModel) throws OwnerException;

    /**
     * 导出数据
     *
     * @param companyId
     * @param salaryQuery
     * @return
     */
    List<SalaryExcel> exportSalaryModel(Integer companyId, SalaryQuery salaryQuery);

    /**
     * 导入薪资数据
     *
     * @param info
     * @param salaryExcels
     * @return
     * @throws OwnerException
     */
    boolean importSalary(UserInfo info, List<SalaryExcel> salaryExcels) throws OwnerException;


    /**
     * 批量修改
     *
     * @param info
     * @param model
     * @return
     */
    boolean updateSalaryDayHours(UserInfo info, SalaryModel model);

    /**
     * 批量删除
     *
     * @param companyId
     * @param models
     * @return
     */
    boolean delSalaryBatch(Integer companyId, List<SalaryModel> models) throws OwnerException;

    /**
     * 获取薪资配置
     *
     * @param companyId
     * @return
     */
    Map<String, FieldConfigModel> getSalaryConfig(Integer companyId);

    /**
     * 获取薪资信息
     * @param companyId
     * @param month
     * @param enumbers
     * @return
     */
    List<SalaryInfoModel> getSalaryInfos(Integer companyId, Date month, Set<String> enumbers);
}
