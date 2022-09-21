package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.FlowInstanceRdFee;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.flowInstance.FlowInstanceInfoModel;
import com.yskc.ms.models.projectAudit.*;
import com.yskc.ms.models.rdfunds.RdFinaFundsModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2022/4/26 10:21
 * @Description:
 * @author: hsx
 */

@Repository
public interface FlowInstanceRdFeeDao extends BaseMapper<FlowInstanceRdFee> {

    /**
     * 获取对应的审核数据
     *
     * @param ids
     * @param userId
     * @return
     */
    List<RdFinaFundsModel> getAuditData(@Param("ids") List<Integer> ids, @Param("userId") Integer userId);

    /**
     * 用户可审核数统计
     *
     * @param query
     * @param userId
     * @return
     */
    Map<String, Object> getAuditCnt(@Param("query") QueryRdFeeModel query, @Param("userId") Integer userId);

    /**
     * 根据rdFeeId获取数据
     *
     * @param rdFeeId
     * @return
     */
    FlowInstanceRdFee getByRdFeeId(@Param("rdFeeId") Integer rdFeeId);

    FlowInstanceInfoModel getInstanceInfo(@Param("id") Integer id);

    AuditStatusModel getAuditStatus(@Param("rdFeeId") Integer rdFeeId);

    /**
     * 获取费用及审核
     * 审核数依赖归集费用，有归集费才统计审核数
     * @param list
     * @param userId
     * @return
     */
    List<FinaAuditMonthModel> getFundsAudits(@Param("list") List<FinaAuditModelGroup> list,@Param("userId") Integer userId);

    /**
     * 获取财务审核（按创新项目年获取）
     * @param page
     * @param query
     * @param dataPerm
     * @return
     */
    List<FinaAuditModelGroup> getList(@Param("page") Pagination page, @Param("query") QueryFinaAuditModel query, @Param("dataPerm") DataPermModel dataPerm);
}
