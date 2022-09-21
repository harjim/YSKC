package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.FlowInstanceUser;
import com.yskc.ms.entity.ms.ServiceApplyEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.serviceApply.QueryApplyModel;
import com.yskc.ms.models.serviceApply.QueryMobileApply;
import com.yskc.ms.models.serviceApply.ServiceApplyModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ServiceApplyDao extends BaseMapper<ServiceApplyEntity> {
    /**
     * 获取申请列表
     * @param page
     * @param query
     * @param moduleId
     * @return
     */
    List<ServiceApplyModel> getList(@Param("page")Pagination page, @Param("query")QueryApplyModel query,
                                    @Param("moduleId")Integer moduleId,@Param("dataPerm") DataPermModel dataPerm);
    /**
     * 获取申请列表
     * @param query
     * @param moduleId
     * @return
     */
    List<ServiceApplyModel> getMobileList(@Param("page")Pagination page,@Param("query") QueryMobileApply query,
                                          @Param("moduleId")Integer moduleId,@Param("dataPerm") DataPermModel dataPerm);

    /**
     * 根据单号或id获取详情
     * @param serviceNo
     * @param serviceId
     * @return
     */
    ServiceApplyModel getApplyInfo(@Param("serviceNo")String serviceNo,@Param("serviceId")Integer serviceId);

    /**
     * 获取当天最大单号
     * @return
     */
    String getApplyToday(@Param("today")Date today,@Param("tomorrow")Date tomorrow);


    /**
     * 获取各个节点
     * @param instanceId
     * @param userId
     * @return
     */
    List<FlowInstanceUser> getNodeUser(@Param("instanceId")Integer instanceId,
                                       @Param("userId")Integer userId);
}
