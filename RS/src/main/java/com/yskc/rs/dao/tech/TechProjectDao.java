package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.TechProjectEntity;
import com.yskc.rs.models.tech.QueryTechProjectModel;
import com.yskc.rs.models.tech.TechProjectModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.tech
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-23 11:00
 * @Description: 技改项目dao层
 */
@Repository
public interface TechProjectDao extends BaseMapper<TechProjectEntity> {

    /**
     * 获取项目
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<TechProjectModel> getTechProjects(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryTechProjectModel query);
}
