package com.yskc.rs.dao.tech;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.tech.TechEquipment;
import com.yskc.rs.models.tech.QueryEquipmentModel;
import com.yskc.rs.models.tech.TechEquipmentModel;

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


    List<TechEquipmentModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryEquipmentModel query);

    /**
     * 根据beian求和
     * @param companyId
     * @param beianId
     * @return
     */
    TechEquipmentModel getTotal(@Param("companyId") Integer companyId,@Param("beianId") Integer beianId);

    /**
     * 更新 Beian 表
     */
    void updateBeianTable();

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
     * 获取备案列表
     * @param beianId
     * @return
     */
    List<TechEquipmentModel> getEquipments(@Param("beianId") Integer beianId);
}
