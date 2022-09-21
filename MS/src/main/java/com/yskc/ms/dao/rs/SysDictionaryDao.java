package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.SysDictionaryEntity;
import com.yskc.ms.entity.rs.models.SysDictionaryEntityModel;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.models.SysDictionaryParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典表
 *
 * @author huronghua
 */
@Repository
public interface SysDictionaryDao extends BaseMapper<SysDictionaryEntity> {
    /**
     * 获取字典树
     *
     * @param type
     * @return
     */
    List<SysDictionaryModel> getDictionaryByType(@Param("type") Integer type);

    /**
     * 获取客户字典
     * @param list
     * @return
     */
    List<SysDictionaryModel> getCustomerDictionary(@Param("list") List<Integer> list);

    /**
     * 获取预算字典
     *
     * @return
     */
    List<SysDictionaryModel> getDictionaryByBudgetType();

    /**
     * 获取申报项目所需字典数据
     * @return
     */
    List<SysDictionaryModel> getProductNeedData();

    List<SysDictionaryEntityModel> getAddress(@Param("list")List<String> list,@Param("type")Integer type);


    List<SysDictionaryEntityModel > queryList(@Param("page") Pagination page, @Param("params") SysDictionaryParams params);

    List<SysDictionaryEntity> findKey(@Param("key")String key,@Param("type")Integer type);

    Integer findByKey(@Param("key")String key);

    Integer findBean(@Param("entity") SysDictionaryEntity entity);

    List<String> getHighTecIndustry(@Param("list")List<Integer> list);
}
