package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.PatentPlanEntity;
import com.yskc.rs.models.PatentPlan.PatentPlanModel;
import com.yskc.rs.models.PatentPlan.QueryPatentPlanModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/6 10:42
 * description:
 */
@Repository
public interface PatentPlanDao extends BaseMapper<PatentPlanEntity> {

    List<PatentPlanModel>  queryPatentPlans(@Param("page") Pagination page, @Param("query")QueryPatentPlanModel query, @Param("companyId")Integer companyId);

    Integer addPatentPlanList(@Param("patentPlanEntities") List<PatentPlanEntity> patentPlanEntities);

    List<String> getByPatentNos(@Param("patentNos") List<String> patentNos);

    int updatePorjectId(@Param("patentNos") List<String> patentNos, @Param("projectId") Integer projectId, @Param("updateTime")Date updateTime,@Param("userId")Integer userId);

    /**
     * 获取专利申请
     * @param patentId
     * @param patentNo
     * @return
     */
    PatentPlanEntity getPatent(@Param("patentId") Integer patentId, @Param("patentNo") String patentNo);
}
