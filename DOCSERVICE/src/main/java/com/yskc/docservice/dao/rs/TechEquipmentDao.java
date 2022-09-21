package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.tech.TechEquipment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zdf-hck123
 * @since 2021-03-18
 */
@Repository
public interface TechEquipmentDao extends BaseMapper<TechEquipment> {
    /**
     * 批量更新
     * @param entities
     * @return
     */
    Boolean updateList(@Param("entities") List<TechEquipment> entities);

    /**
     * 批量插入
     * @param entities
     * @return
     */
    Integer insertList(@Param("entities") List<TechEquipment> entities);

    /**
     * 更新 Beian 表
     */
    void updateBeianTable();
}
