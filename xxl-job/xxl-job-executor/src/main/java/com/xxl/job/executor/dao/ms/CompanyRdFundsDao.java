package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.CompanyRdFunds;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-21 09:08
 * @Description: 公司研发dao层
 */
@Repository
public interface CompanyRdFundsDao extends BaseMapper<CompanyRdFunds> {
    /**
     * 插入或更新
     *
     * @param list
     * @param now
     * @return
     */
    Integer insertOrUpdate(@Param("list") List<CompanyRdFunds> list, @Param("now") Date now);

    /**
     * 获取分公司研发费统计
     *
     * @param year
     * @param branchIds
     * @return
     */
    List<CompanyRdFunds> getDistrictFunds(@Param("year") Integer year, @Param("branchIds") Set<Integer> branchIds);

    /**
     * 插入或更新分公司统计
     *
     * @param list
     * @param now
     * @return
     */
    int insertOrUpdateDistrict(@Param("list") List<CompanyRdFunds> list,@Param("now") Date now);
}
