package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.TechSummaryEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-14 15:12
 * @Description: 技改项目汇总dao层
 */
@Repository
public interface TechSummaryDao extends BaseMapper<TechSummaryEntity> {

    /**
     * 更新或插入
     *
     * @param list
     */
    void insertOrUpdate(@Param("list") List<TechSummaryEntity> list);
}
