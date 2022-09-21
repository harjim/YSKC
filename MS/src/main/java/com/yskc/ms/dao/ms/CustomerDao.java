package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.Customer;
import com.yskc.ms.entity.rs.models.CompanyModel;
import com.yskc.ms.entity.rs.models.MyCustomerModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.company.CompanyInfoModel;
import com.yskc.ms.models.customer.*;
import com.yskc.ms.models.serviceApply.ServiceCustomerModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface CustomerDao extends BaseMapper<Customer> {

    /**
     * 获取下拉客户（带地址）
     *
     * @param customerName
     * @return
     */
    List<MiniCustomerModel> customerForSelect(@Param("customerName") String customerName, @Param("addressStr") String addressStr, @Param("deptId") Integer deptId);

    /**
     * 获取下拉客户
     *
     * @param customerName
     * @return
     */
    List<MiniModel> getCustomerSelect(@Param("customerName") String customerName);

    /**
     * 获取公司下拉
     *
     * @param customerName
     * @return
     */
    List<MiniModel> getCompanySelect(@Param("customerName") String customerName);

    /**
     * 获取客户列表
     *
     * @param page
     * @param query
     * @param perm
     * @return
     */
    List<CustomerModel> getList(@Param("page") Pagination page, @Param("query") QueryCustomerModel query, @Param("perm") DataPermModel perm);

    /**
     * 获取移动端客户列表
     *
     * @param query
     * @param perm
     * @return
     */
    List<CustomerModel> getMobileList(@Param("page") Pagination page,  @Param("query") QueryCustomerModel query, @Param("perm") DataPermModel perm);

    /**
     * 获取未签客户列表
     *
     * @param page
     * @param query
     * @return
     */
    List<CustomerModel> getUnsignedList(@Param("page") Pagination page, @Param("query") QueryCustomerModel query);

    /**
     * 获取未签客户列表
     *
     * @param query
     * @return
     */
    List<CustomerModel> getMobileUnsignedList(@Param("page") Pagination page, @Param("query") QueryCustomerModel query);

    /**
     * 检查是否重名忽略括号空格
     * @param companyName
     * @return
     */
    List<CustomerModel> checkCompanyName(@Param("companyName") String companyName);
    /**
     * 检查是否重名不忽略括号空格
     * @param companyName
     * @return
     */
    List<CustomerModel> checkName(@Param("companyName") String companyName);



    /**
     * 跟进修改状态
     * @param id
     * @param status
     * @return
     */
    Boolean updateStatus(@Param("id")Integer id,@Param("status")Integer status,@Param("userId")Integer userId);

    /**
     * 批量修改跟进状态
     * @param ids
     * @param status
     * @param owerId
     * @param deptId
     * @return
     */
    Boolean updateStatusList(@Param("ids")List<Integer> ids,@Param("status")Integer status,
                         @Param("owerId")Integer owerId, @Param("deptId") Integer deptId);

    /**
     * 修改项目信息
     * @param model
     * @param date
     * @return
     */
    Boolean updateCustomer(@Param("model")CustomerModel model,@Param("date")Date date);

    /**
     * 变更名称
     * @param id
     * @param companyName
     * @return
     */
    Boolean updateCompanyName(@Param("id")Integer id, @Param("companyName")String companyName,@Param("userId")Integer userId);

    /**
     * 获取公司id
     * @param id
     * @return
     */
    Integer getCompanyId(@Param("id")Integer id);

    /**
     * 根据公司名获取公司id
     * @param companyName
     * @return
     */
    Integer getIdByCompanyName(@Param("companyName")String companyName);

    /**
     * 获取用户客户
     *
     * @param page
     * @param query
     * @return
     */
    List<MyCustomerModel> getMyCustomerList(Pagination page, @Param("query") QueryCustomerModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 查询业务员
     * @param ids
     * @return
     */
    List<CustomerOwerModel> getOwerList(@Param("ids")List<Integer> ids);
    /**
     * 更新业务员
     * @param now
     * @param own
     * @param userId
     * @param deptId
     * @return
     */
    int updateOwnUser(@Param("now") Date now, @Param("own") CustomerOwnerUserModel own,
                      @Param("userId") Integer userId, @Param("deptId") Integer deptId);

    /**
     * 检测是否有此客户
     * @param customerIds
     * @param status
     * @return
     */
    List<CompanyModel> checkCustomer(@Param("customerIds") List<Integer> customerIds,@Param("status")Integer status);

    /**
     * 根据公司获取客户信息
     *
     * @param companyId
     * @return
     */
    CompanyInfoModel getByCompany(@Param("companyId") Integer companyId);

    /**
     * 获取集团下拉【包括总公司】
     *
     * @param groupId
     * @return
     */
    List<MiniModel> getGroupSelect(@Param("groupId") Integer groupId);

    List<FindCustomerModel> findCustomerList(@Param("page") Pagination page, @Param("query") FindCustomerModel query);

    List<FindCustomerModel> findSonCustomerList(@Param("page") Pagination page, @Param("model") FindCustomerModel model);

    /**
     * 更新所属集团
     *
     * @param userId
     * @param now
     * @param companyType
     * @param groupId
     * @param list
     * @return
     */
    Integer updateGroups(@Param("userId") Integer userId, @Param("now") Date now, @Param("companyType") Integer companyType,
                         @Param("groupId") Integer groupId, @Param("list") List<Customer> list);

    Integer upNotCustomer(@Param("customer") Customer customer, @Param("ids") List<Integer> ids,@Param("companyType") Integer companyType);

    /**
     * 根据公司ID 批量获取
     * @param companyIds
     * @return
     */
    List<CustomerModel> getByCompanyIds(@Param("companyIds") List<Integer> companyIds);

    /**
     * 获取所属部门
     * @param companyIds
     * @return
     */
    List<FindCustomerModel> getDeptName(@Param("companyIds") List<Integer> companyIds);

    /**
     * 获取集团列表
     * @param groupIds
     * @return
     */
    List<MiniModel> getGroupList(@Param("groupIds") Collection<Integer> groupIds);

    /**
     * 服务申请时查询公司名
     * @param model
     * @return
     */
    List<ServiceCustomerModel> getServiceCustomerList(@Param("model") com.yskc.ms.models.serviceApply.QueryCustomerModel model);

    /**
     * 根据客户查业务员及相关
     * @param companyName
     * @return
     */
    List<CustomerOwnerModel> getOwnerDept(@Param("companyName")String companyName);

    /**
     * 获取公司名称
     * @param id
     * @return
     */
    String getCompanyName(@Param("id") Integer id);
}
