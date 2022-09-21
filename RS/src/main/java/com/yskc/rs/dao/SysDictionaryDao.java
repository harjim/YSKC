package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.SysDictionaryEntity;
import com.yskc.rs.models.HighTecIndustryModel;
import com.yskc.rs.models.SysDictionaryModel;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典表
 * @author huronghua
 */
@Repository
public interface SysDictionaryDao extends BaseMapper<SysDictionaryEntity> {
    /**
     * 获取字典树
     * @param type
     * @return
     */
    List<SysDictionaryModel> getDictionaryByType(@Param("type") Integer type);

    /**
     * 获取高新领域树
     * @return
     */
    List<HighTecIndustryModel> getHighTecIndustryModels();

    /**
     * 获取高新领域名
     * @return
     */
    List<String> getHighTecIndustry(@Param("list")List<Integer> list);

    /**
     * 获取字典
     * @param
     * @return
     */
    List<SysDictionaryModel> getDictionaryByBudgetType();

    /***
     * 获取阶段名称
     * @param stageKey
     * @return
     */
    SysDictionaryEntity getByKey(@Param("stageKey") String stageKey);
}
