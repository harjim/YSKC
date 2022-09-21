package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.PatentOpinionEntity;
import com.yskc.ms.entity.rs.RsPatentPlanEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.patentPlan.PatentOpinionModel;
import com.yskc.ms.models.patentPlan.QueryPatentPlanModel;
import com.yskc.ms.models.patentPlan.RsPatentPlanModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/7 8:13
 * description:
 */
@Repository
public interface RsPatentPlanDao extends BaseMapper<RsPatentPlanEntity> {

    List<RsPatentPlanModel> queryPatentPlanList(@Param("query") QueryPatentPlanModel query,
                                                @Param("page") Pagination page,
                                                @Param("perm") DataPermModel perm,
                                                @Param("patentIds") List<Integer> patentIds);

    Integer updateList(@Param("models") List<RsPatentPlanEntity> models);

    List<RsPatentPlanEntity> queryListByMasterId(@Param("masterId") Integer masterId);

    /**
     * 批量驳回专利立项
     *
     * @param ids
     * @param status
     * @param updateTime
     * @param updateUserId
     * @return
     */
    Integer rejectPatent(@Param("ids") List<Integer> ids, @Param("status") Integer status, @Param("updateTime") Date updateTime, @Param("updateUserId") Integer updateUserId);

    /**
     * 获取专利申请信息
     *
     * @param patentPlanId
     * @return
     */
    RsPatentPlanModel getPatentInfo(@Param("patentPlanId") Integer patentPlanId);

    /**
     * 获取最新意见列表
     *
     * @param patentIds
     * @return
     */
    List<PatentOpinionEntity> getOpinions(@Param("patentIds") List<Integer> patentIds);

    /**
     * 获取专利申请
     *
     * @param patentId
     * @param patentNo
     * @return
     */
    RsPatentPlanEntity getPatent(@Param("patentId") Integer patentId, @Param("patentNo") String patentNo);

    /**
     * 获取专利意见列表
     *
     * @param patentId
     * @return
     */
    List<PatentOpinionModel> getOpinionByPatent(@Param("patentId") Integer patentId);
}
