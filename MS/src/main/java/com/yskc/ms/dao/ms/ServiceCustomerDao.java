package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.ServiceCustomerEntity;
import com.yskc.ms.models.serviceApply.ServiceApplyModel;
import com.yskc.ms.models.serviceApply.ServiceCustomerModel;
import com.yskc.ms.models.serviceApply.ServiceNoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ServiceCustomerDao extends BaseMapper<ServiceCustomerEntity> {
    /**
     * 添加服务申请客户列
     * @param serviceIds
     * @return
     */
    List<ServiceCustomerModel> getCustomerList(@Param("serviceIds") Set<Integer> serviceIds);

    /**
     * 客户获取服务单号
     * @param page
     * @param customerId
     * @param status
     * @return
     */
    List<ServiceNoModel> getServiceNo(@Param("page") Pagination page, @Param("customerId") Integer customerId, @Param("status")Integer status);

    /**
     * 添加服务申请客户
     * @param list
     * @return
     */
    Integer addCustomerList(@Param("list")List<ServiceCustomerEntity> list);

    /**
     * 编辑服务申请客户
     * @param list
     * @return
     */
    Integer updateCustomer(@Param("list")List<ServiceCustomerEntity> list);

    /**
     * 编辑时删除
     * @param ids
     * @param serviceApplyId
     * @return
     */
    Integer delByCustomerIds(@Param("ids")List<Integer> ids,@Param("serviceApplyId")Integer serviceApplyId);

    /**
     * 根据ApplyId删除
     * @param ids
     * @return
     */
    Integer delByApplyIds(@Param("ids")List<Integer> ids);
}
