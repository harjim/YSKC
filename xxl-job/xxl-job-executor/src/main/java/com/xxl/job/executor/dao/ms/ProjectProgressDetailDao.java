package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.ProjectProgressDetail;
import com.xxl.job.executor.models.projectsummary.BaseSummaryModel;
import com.xxl.job.executor.models.projectsummary.OperationSummaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-13 18:43
 * @Description: 项目进度明细dao层
 */
@Repository
public interface ProjectProgressDetailDao extends BaseMapper<ProjectProgressDetail> {
    /**
     * 批量插入
     *
     * @param data
     * @return
     */
    int insertOrUpdate(@Param("data") List<ProjectProgressDetail> data);

    /**
     * 获取公司年份
     *
     * @return
     */
    List<ProjectProgressDetail> getCompanyYear();

    /**
     * 获取最后操作人
     *
     * @param data
     * @return
     */
    List<OperationSummaryModel> getLastOperation(@Param("data") List<BaseSummaryModel> data);

    /**
     * 获取所有操作人
     *
     * @param beforeFive
     * @return
     */
    List<Integer> getLastOperationCompanyId(@Param("beforeFive") Date beforeFive);

    /**
     * 获取所有的公司和年
     * @param beforeFive
     * @return
     */
    List<BaseSummaryModel> getAllCompany(@Param("beforeFive") Date beforeFive);

}
