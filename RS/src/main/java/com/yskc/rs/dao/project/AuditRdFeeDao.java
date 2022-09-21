package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.AuditRdFeeEntity;
import com.yskc.rs.models.CheckStatusModel;
import com.yskc.rs.models.finance.AuditFinanceModel;
import com.yskc.rs.models.finance.AuditStatusModel;
import com.yskc.rs.models.project.AuditRdFeeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2022/4/28 8:56
 * @Description:
 * @author: hsx
 */
@Repository
public interface AuditRdFeeDao extends BaseMapper<AuditRdFeeEntity> {

    /**
     * 获取审核数据
     *
     * @param projectId
     * @param beginDate
     * @param endDate
     * @return
     */
    List<AuditStatusModel> getStatus(@Param("projectId") Integer projectId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    /**
     * 获取审核状态
     *
     * @param list
     * @param rdTypes
     * @return
     */
    List<AuditRdFeeModel> getStatusList(@Param("list") List<CheckStatusModel> list, @Param("rdTypes") List<Integer> rdTypes);

    /**
     * 获取审核数据
     *
     * @param model
     * @return
     */
    AuditRdFeeEntity getAudit(@Param("model") AuditFinanceModel model);

    /**
     * 更新提交
     *
     * @param id
     * @param status
     * @param msUserId
     * @param now
     * @return
     */
    int updateStatus(@Param("id") Integer id,@Param("status") Integer status,@Param("msUserId") Integer msUserId,@Param("now")Date now);
}
