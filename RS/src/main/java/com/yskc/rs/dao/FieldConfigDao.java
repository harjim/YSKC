package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.FieldConfigEntity;
import com.yskc.rs.models.fieldconfig.FieldConfigModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: wjy
 * @CreateTime: 2020-03-16 10:30
 * @Description: 薪资配置dao层
 */
@Repository
public interface FieldConfigDao extends BaseMapper<FieldConfigEntity> {
    /**
     * 更新配置
     *
     * @param e
     * @return
     */
    int updateConfig(@Param("e") FieldConfigEntity e);

    /**
     * 获取默认配置
     *
     * @return
     */
    FieldConfigModel getDefault();

    /**
     * 获取配置
     *
     * @param companyId
     * @param type
     * @return
     */
    FieldConfigModel getTypeConfig(@Param("companyId") Integer companyId, @Param("type") Integer type);

    /**
     * 获取薪资/五险一金配置
     * @param companyId
     * @return
     */
    List<FieldConfigModel> getSalaryConfig(@Param("companyId") Integer companyId);
}
