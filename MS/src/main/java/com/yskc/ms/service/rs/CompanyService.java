package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.models.CompanyModel;
import com.yskc.ms.entity.rs.models.MyCustomerModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.CompanyInfoModel;
import com.yskc.ms.models.company.QueryCompanyModel;
import com.yskc.ms.models.params.CompanyParams;

import java.util.List;

public interface CompanyService {

	/**
	 * 获取公司列表
	 * @param query
	 * @return
	 */
	PageModel<List<CompanyModel>> queryCompanyList(QueryCompanyModel query);
	PageModel<List<CompanyModel>> getCompanyList(CompanyParams params);
	Integer saveCompany(CompanyModel model,UserInfo userInfo);
	boolean addCustomer(CompanyModel model,Integer userId) throws OwnerException;
	boolean editCustomer(CompanyModel model, Integer userId) throws OwnerException;

	/**
	 * 跳转rs
	 * @param model
	 * @param userInfo
	 * @param dataPerm
	 * @return
	 * @throws OwnerException
	 */
	String jump(MyCustomerModel model, UserInfo userInfo, DataPermModel dataPerm) throws OwnerException;

    boolean updateCompany(CompanyModel model, UserInfo userInfo);

	/**
	 * 获取公司基本信息
	 * @param companyId
	 * @return
	 */
    CompanyInfoModel getInfo(Integer companyId);

	/**
	 * 编辑基本信息
	 * @param model
	 * @param userInfo
	 * @return
	 */
	Boolean editInfo(CompanyInfoModel model, UserInfo userInfo);


}
