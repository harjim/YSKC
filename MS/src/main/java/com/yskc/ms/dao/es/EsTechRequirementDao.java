package com.yskc.ms.dao.es;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.es.TechRequirementEntity;
import com.yskc.ms.models.newexpert.techrequirement.EsQueryTechRequirementModel;
import com.yskc.ms.models.newexpert.techrequirement.EsTechRequirementModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface EsTechRequirementDao extends BaseMapper<TechRequirementEntity> {

    /**
     * 获取技术需求列表
     * @param page
     * @param query
     * @return
     */
    List<EsTechRequirementModel> getList(@Param("page")Pagination page, @Param("query")EsQueryTechRequirementModel query) ;

    /**
     * 获取技术需求详情
     * @param id
     * @return
     */
    EsTechRequirementModel getData(@Param("id") Integer id);

    /**
     * 编辑技术需求
     * @param entity
     * @return
     */
    Integer updateTech(@Param("entity") TechRequirementEntity entity);

    /**
     * 技术需求状态是否可编辑
     *
     * @param ids
     * @return
     */
    List<EsTechRequirementModel> getStatus(@Param("ids") Integer[] ids);

}
