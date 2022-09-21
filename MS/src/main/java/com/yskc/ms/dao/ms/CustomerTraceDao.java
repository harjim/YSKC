package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.models.customer.CustomerOwerModel;
import com.yskc.ms.models.customer.CustomerTraceModel;
import com.yskc.ms.models.customer.QueryFollowModel;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public interface CustomerTraceDao {
    /**
     * 查询跟进记录
     * @param customerId
     * @param page
     * @return
     */
    List<CustomerTraceModel> getTraceList(@Param("customerId")Integer customerId,@Param("page") Pagination page);
    /**
     * 查询移动端跟进记录
     * @param customerId
     * @return
     */
    List<CustomerTraceModel> getMobileTraceList(@Param("customerId")Integer customerId,@Param("page") Pagination page);
    /**
     * 查询移动端最后一条跟进记录
     * @param customerIds
     * @return
     */
    @MapKey("customerId")
    Map<Integer,CustomerTraceModel> getMobileTraceMap(@Param("customerIds") Set<Integer> customerIds);
    /**
     * 插入更新记录
     * @param query
     * @return
     */
    Boolean insertInfo(@Param("query")QueryFollowModel query);

    /**
     * 批量跟进业务员记录
     * @param customerIds
     * @param userId
     * @param info
     * @return
     */
    Boolean insertInfoList(@Param("customerIds")List<Integer> customerIds,@Param("type")Integer type,
                           @Param("userId")Integer userId,@Param("info")String info);

    /**
     * 批量指定业务员记录
     * @param type
     * @param owerList
     * @param userId
     * @return
     */
    Boolean insertOwerList(@Param("type")Integer type,@Param("owerList")List<CustomerOwerModel> owerList,
                           @Param("userId")Integer userId);
}
