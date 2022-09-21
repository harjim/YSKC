package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.TechSummaryEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.techsummary.QueryTechSummaryModel;
import com.yskc.ms.models.techsummary.TechSummaryModel;
import com.yskc.ms.models.techsummary.TechTotalSummaryModel;
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
     * 获取技改汇总列表
     *
     * @param page
     * @param query
     * @param dataPerm
     * @return
     */
    List<TechSummaryModel> getList(@Param("page") Pagination page, @Param("query") QueryTechSummaryModel query,
                                   @Param("dataPerm") DataPermModel dataPerm);

    /**
     * 获取总数
     *
     * @return
     */
    TechTotalSummaryModel getTotal();
}
