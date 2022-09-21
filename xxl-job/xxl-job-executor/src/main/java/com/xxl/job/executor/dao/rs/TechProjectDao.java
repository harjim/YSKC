package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.TechProjectEntity;
import com.xxl.job.executor.models.techproject.StageCountModel;
import com.xxl.job.executor.models.techproject.TechSummaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-14 15:06
 * @Description: 技改项目dao层
 */
@Repository
public interface TechProjectDao extends BaseMapper<TechProjectEntity> {


    /**
     * 获取最后操作
     *
     * @param beforeTime
     * @return
     */
    List<TechSummaryModel> getLastOperation(@Param("beforeTime") Date beforeTime);

    /**
     * 获取阶段数，明细数
     *
     * @param directionIds
     * @return
     */
    List<StageCountModel> getStageCount(@Param("directionIds") List<Integer> directionIds);
}
