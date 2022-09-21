package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.AuditPatentEntity;
import com.yskc.rs.models.PatentPlan.PatentPlanModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/6/8 11:42
 * @Description:
 */
@Repository
public interface AuditPatentDao extends BaseMapper<AuditPatentEntity> {

    /**
     * 获取专利申请列表
     * @param patentIds
     * @return
     */
    List<PatentPlanModel> getPatentStatus(@Param("patentIds") List<Integer> patentIds);

    /**
     * 批量提交专利审核
     * @param inserts
     * @return
     */
    Integer batchAdd(@Param("inserts") List<AuditPatentEntity> inserts);

    /**
     * 批量更新专利审核状态
     * @param updateIds
     * @param date
     * @param msUserId
     * @param status
     * @return
     */
    Boolean batchUpdate(@Param("updateIds") List<Integer> updateIds, @Param("date") Date date, @Param("msUserId") Integer msUserId, @Param("status") int status);

    /**
     * 获取专利白底书审核状态
     * @param patentPlanId
     * @return
     */
    AuditPatentEntity getByPatent(@Param("patentPlanId") Integer patentPlanId);
}
