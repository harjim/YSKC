package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.PatentPlanEntity;
import com.xxl.job.executor.entity.ms.Project;
import com.xxl.job.executor.entity.ms.ProjectProgressDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2021/12/30 8:52
 * @Description:
 * @author: hsx
 */
@Repository
public interface PatentPlanDao extends BaseMapper<PatentPlanEntity> {

    /**
     * 获取专利总数
     * @return
     */
    Integer getPatentStatNumber();

    List<ProjectProgressDetail> getPatentPlan(@Param("lastTime") Date lastTime, @Param("stage")Integer stage);
}
