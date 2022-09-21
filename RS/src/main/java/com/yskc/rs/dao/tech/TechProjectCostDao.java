package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.TechProjectCostEntity;
import com.yskc.rs.models.tech.cost.MinProjectCostModel;
import com.yskc.rs.models.tech.cost.QueryTechProjectCostModel;
import com.yskc.rs.models.tech.cost.TechProjectCostModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.tech
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-24 17:33
 * @Description: 项目支出dao层
 */
@Repository
public interface TechProjectCostDao extends BaseMapper<TechProjectCostEntity> {
    /**
     * 查询项目支出(分页)
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<TechProjectCostModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryTechProjectCostModel query);

    /**
     * 批量插入数据
     *
     * @param costList
     * @return
     */
    int addList(@Param("costList") List<TechProjectCostEntity> costList);

    /**
     * 查询项目支出(不分页)
     *
     * @param companyId
     * @param query
     * @return
     */
    List<TechProjectCostModel> getList(@Param("companyId") Integer companyId, @Param("query") QueryTechProjectCostModel query);

    /**
     * 查询项目支出
     *
     * @param companyId
     * @param projectId
     * @return
     */
    List<MinProjectCostModel> getProjectCost(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);
}
