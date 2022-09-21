package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.PatentPlanEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.customer.CustomerSelectModel;
import com.yskc.ms.models.patentPlan.PatentPlanModel;
import com.yskc.ms.models.patentPlan.PatentPlanStatModel;
import com.yskc.ms.models.patentPlan.PatentPlanTotalModel;
import com.yskc.ms.models.patentPlan.QueryPatentPlanModel;
import com.yskc.ms.models.projectAudit.QueryAuditDataModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/8/19 10:51
 * @Description:
 */
@Repository
public interface PatentPlanDao extends BaseMapper<PatentPlanEntity> {
    /**
     * 获取客户下拉列表
     *
     * @param companyName
     * @return
     */
    List<CustomerSelectModel> getCustomers(@Param("companyName") String companyName);

    /**
     * 获取专利申请列表
     *
     * @param page
     * @param query
     * @param perm
     * @return
     */
    List<PatentPlanModel> getList(@Param("page") Pagination page, @Param("query") QueryPatentPlanModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取专利申请列表统计数
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PatentPlanTotalModel getTotalNumber(@Param("query") QueryPatentPlanModel query, @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取专利申请信息
     *
     * @param patentPlanId
     * @return
     */
    PatentPlanModel getInfo(@Param("patentPlanId") Integer patentPlanId);

    /**
     * 获取专利申请
     *
     * @param patentId
     * @param patentNo
     * @return
     */
    PatentPlanEntity getById(@Param("patentId") Integer patentId, @Param("patentNo") String patentNo);


    /**
     * 更新专利号
     *
     * @param patentNo
     * @param id
     * @param date
     * @param userId
     * @return
     */
    int updatePatentNo(@Param("patentNo") String patentNo, @Param("id") Integer id, @Param("date") Date date,
                       @Param("userId") Integer userId,@Param("masterId") Integer masterId);

    /**
     * 项目进度获取专利列表
     *
     * @param page
     * @param query
     * @return
     */
    List<PatentPlanModel> queryList(@Param("page") Pagination page, @Param("query") QueryAuditDataModel query);

    /**
     * 项目进度获取专利列表
     *
     * @param query
     * @return
     */
    List<Integer> getPatentPlanIds(@Param("query") QueryAuditDataModel query);

    /**
     * 获取专利信息
     *
     * @param patentPlanId
     * @return
     */
    PatentPlanModel getPatent(@Param("patentPlanId") Integer patentPlanId);

    /**
     * 更新工程师
     *
     * @param ids
     * @param engineerId
     * @param now
     * @param userId
     * @return
     */
    int updateEngineer(@Param("ids") List<Integer> ids, @Param("engineerId") Integer engineerId, @Param("now") Date now, @Param("userId") Integer userId);

    /**
     * 更新专利代理人
     *
     * @param patentIds
     * @param masterId
     * @param now
     * @param userId
     * @return
     */
    int updateMaster(@Param("patentIds") List<Integer> patentIds, @Param("masterId") Integer masterId, @Param("now") Date now, @Param("userId") Integer userId);

    /**
     * 获取申请所处的节点number
     * @param patentId
     * @return
     */
    Integer getNodeNumber(@Param("patentId") Integer patentId);

    /**
     * 批量修改专利process
     * @param ids
     * @param now
     * @param userId
     * @return
     */
    int updateProcessBatch(@Param("ids") List<Integer> ids, @Param("now") Date now, @Param("userId") Integer userId);

    /**
     * 根据instanceId获取专利
     * @param instanceIds
     * @return
     */
    List<PatentPlanEntity> getByInstanceId(@Param("instanceIds") List<Integer> instanceIds);

    /**
     * 获取专利申请列表
     *
     * @param page
     * @param query
     * @return
     */
    List<PatentPlanModel> getPlanList(@Param("page") Pagination page, @Param("query") QueryPatentPlanModel query);

    List<PatentPlanStatModel> getPatentStatList(@Param("demandIds")List<Integer> demandIds);
}
