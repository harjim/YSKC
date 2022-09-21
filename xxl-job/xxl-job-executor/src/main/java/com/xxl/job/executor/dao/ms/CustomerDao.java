package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.Customer;
import com.xxl.job.executor.models.customer.CustomerStatusModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface CustomerDao extends BaseMapper<Customer> {

    void InsertH3Customers(@Param("customerList") List<Map> customerList);

    void insertH3Projects(@Param("projectList") List<Map> projectList);

    /**
     * 修改跟进超时客户
     * @param inviteDate
     * @param visitDate
     * @return
     */
    Boolean updateCustomer(@Param("inviteDate")Date inviteDate,@Param("visitDate")Date visitDate);

    /**
     * 获取企业总数
     * @return
     */
    Integer countCustomer();
}
