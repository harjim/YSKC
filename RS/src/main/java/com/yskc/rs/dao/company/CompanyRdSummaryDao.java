package com.yskc.rs.dao.company;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.company.CompanyRdSummary;
import com.yskc.rs.models.companyrdsummary.CompanyRdSummaryModel;
import com.yskc.rs.models.companyrdsummary.QueryCompanyRdSummaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.company
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-02 17:06
 * @Description: 公司研发汇总
 */
@Repository
public interface CompanyRdSummaryDao extends BaseMapper<CompanyRdSummary> {
    /**
     * 获取公司研发汇总
     *
     * @param page
     * @param query
     * @param groupFullPath
     * @param companyIds
     * @return
     */
    List<CompanyRdSummaryModel> getList(@Param("page") Pagination page, @Param("query") QueryCompanyRdSummaryModel query,
                                        @Param("groupFullPath") String groupFullPath, @Param("companyIds") List<Integer> companyIds);

    /**
     * 获取汇总信息
     *
     * @param query
     * @param groupFullPath
     * @param companyIds
     * @return
     */
    CompanyRdSummaryModel getTotal(@Param("query") QueryCompanyRdSummaryModel query, @Param("groupFullPath") String groupFullPath,
                                   @Param("companyIds") List<Integer> companyIds);

    /**
     * 获取机构建设数
     *
     * @param companyId
     * @param year
     * @return
     */
    Integer getInfo(@Param("companyId") Integer companyId, @Param("year") Integer year);
}
