package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.models.CompanyModel;
import com.yskc.ms.entity.rs.models.MyCustomerModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.NameHistoryModel;
import com.yskc.ms.models.customer.*;

import java.util.List;

public interface CustomerService {
    /**
     * 查询客户下拉框（带地址）
     *
     * @param customerName
     * @return
     */
    List<MiniCustomerModel> customerForSelect(String customerName, String addressStr, Integer deptId);

    /**
     * 获取客户列表
     *
     * @param userInfo
     * @param queryCustomerModel
     * @param dataPerm
     * @return
     */
    PageModel<List<CustomerModel>> getList(UserInfo userInfo, QueryCustomerModel queryCustomerModel,DataPermModel dataPerm,Boolean unsigned);

    /**
     * 获取移动端客户列表
     *
     * @param userInfo
     * @param queryCustomerModel
     * @param dataPerm
     * @return
     */
    PageModel<List<CustomerModel>> getMobileList(UserInfo userInfo, QueryCustomerModel queryCustomerModel,DataPermModel dataPerm,Boolean unsigned);


    /**
     * 查询客户下拉框
     *
     * @param customerName
     * @return
     */
    List<MiniModel> getCustomerSelect(String customerName);

    /**查询客户数据(带业务员)
     *
     * @param companyName
     * @return
     */
    List<CustomerOwnerModel> getCustomerList(String companyName);

    /**
     * 开通账户
     *
     * @param model
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean openingAccount(CompanyModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 跟进
     * @param query
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean followUp(QueryFollowModel query, UserInfo userInfo) throws OwnerException;

    /**
     * 批量跟进
     * @param query
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean followUpList(QueryFollowListModel query, UserInfo userInfo) throws OwnerException;

    /**
     * 跟进记录
     * @param query
     * @return
     * @throws OwnerException
     */
    PageModel<List<CustomerTraceModel>> getTraceList(QueryTraceModel query,UserInfo userInfo) throws OwnerException;

    /**
     * 移动端跟进记录
     * @param query
     * @return
     * @throws OwnerException
     */
    PageModel<List<CustomerTraceModel>> getMobileTraceList(QueryTraceModel query,UserInfo userInfo) throws OwnerException;

    /**
     * 变更名称
     * @param id
     * @return
     * @throws OwnerException
     */
    boolean changeName(Integer id,String companyName,UserInfo userInfo) throws OwnerException;

    /**
     * 批量指定业务员
     *
     * @param userInfo
     * @param customerOwnerUserModel
     * @return
     * @throws OwnerException
     */
    boolean setOwnerUser(UserInfo userInfo, CustomerOwnerUserModel customerOwnerUserModel) throws OwnerException;

    /**
     * 添加客户
     *
     * @param model
     * @param userId
     * @return
     */
    boolean addCustomer(CustomerModel model, Integer userId, UserInfo userInfo) throws OwnerException;

    /**
     * 更新客户
     *
     * @param model
     * @param userInfo
     * @return
     */
    boolean editCustomer(CustomerModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 检查客户名是否已存在
     *
     * @param companyName
     * @return
     */
    boolean checkCompanyName(String companyName);

    /**
     * 获取我的客户列表
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel<List<MyCustomerModel>> getMyCustomerList(QueryCustomerModel query, DataPermModel dataPerm);

    /**
     * 获取公司下拉
     *
     * @param customerName
     * @return
     */
    List<MiniModel> getCompanySelect(String customerName);

    /**
     * 获取集团下拉【包括总公司】
     * @param groupId
     * @return
     */
    List<MiniModel> getGroupSelect(Integer groupId);
    Integer setGroup(Integer userId,Integer id) throws OwnerException;

    PageModel<List<FindCustomerModel>> findCustomerList(FindCustomerModel query);

    PageModel<List<FindCustomerModel>> findSonCustomerList(FindCustomerModel model);

    Boolean insertSonCustomer(Integer companyId,Integer userId,Integer groupId,List<Integer> ids) throws OwnerException;

    Boolean delSonCustomer(Integer userId,Integer companyId,List<Integer> customerIds) throws OwnerException;

    /**
     * 获取公司历史名称列表
     * @param companyId 公司id
     * @return 列表
     */
    List< NameHistoryModel> getNameHistory(Integer companyId);

    Integer saveNameHistory(NameHistoryModel nameHistoryModel, UserInfo userInfo) throws OwnerException;

    Integer delNameHistory(NameHistoryModel nameHistoryModel, UserInfo userInfo) throws OwnerException;
}
