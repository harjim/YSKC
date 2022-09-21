package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.AppCompanyModel;
import com.yskc.rs.models.company.CompanyNameHistoryModel;
import com.yskc.rs.models.company.CountDataModel;

import java.util.List;

/**
 * @author Administrator
 */
public interface CompanyService {
    /**
     * @param userInfo
     * @param model
     * @param token
     * @return
     */
    boolean saveCompany(UserInfo userInfo, AppCompanyModel model, String token) throws Exception;

    /**
     * @param userInfo
     * @param model
     * @return
     */
//    boolean updateCompanyName (UserInfo userInfo, CompanyNameModel model) throws Exception;

    /**
     * @param id
     * @return
     */
    AppCompanyModel queryAll(Integer id);

    /**
     * @param companyName
     * @return
     */
    Integer registerCheckCompany(String companyName);

    /**
     * 获取公司首页统计数据
     * @param companyId
     * @param year
     * @return
     */
    CountDataModel getCountData(Integer companyId, Integer year);

    /**
     * 获取公司信息是否完善
     * @param companyId
     * @return
     */
    Boolean getCompanyFinished(Integer companyId);

    /**
     * 获取公司历史名称
     * @param companyId 公司id
     * @return
     */
    List< CompanyNameHistoryModel > getCompanyHistoryName(Integer companyId);

    /**
     * 删除历史名称记录
     * @param nameId 名称记录id
     * @param companyId 公司id
     * @return
     */
    CompanyNameHistoryModel delCompanyHistoryName(Integer nameId, UserInfo companyId) throws OwnerException;

    /**
     * 保存历史名称修改
     * @param nameChangeModel 名称记录Model
     * @param companyId 公司id
     * @return
     */
    CompanyNameHistoryModel saveCompanyName(CompanyNameHistoryModel nameChangeModel, UserInfo companyId) throws OwnerException;
}
