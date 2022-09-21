package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.EquipmentEntity;
import com.yskc.rs.models.equipment.EquipmentMinModel;
import com.yskc.rs.models.equipment.EquipmentModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.excel.EquipmentExcel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: blueToWhite
 * @CreateTime: 2019-07-09 09:18
 * @Description: 设备Dao层
 */
@Repository
public interface EquipmentDao extends BaseMapper<EquipmentEntity> {

    /**
     * 查询公司所有数据
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<EquipmentModel> queryAll(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryEquipmentModel query);


    /**
     * 查询导出
     *
     * @param compantyId
     * @param query
     * @return
     */
    List<EquipmentExcel> queryForExport(@Param("companyId") Integer compantyId, @Param("query") QueryEquipmentModel query);

    /**
     * 查询是否存在相同的ecode
     *
     * @param companyId
     * @param ecode
     * @return
     */
    EquipmentEntity getEquipmentByEcode(@Param("companyId") Integer companyId, @Param("ecode") String ecode);

    /**
     * 查询是否存在相同的ecode
     *
     * @param companyId
     * @param ecodes
     * @return
     */
    List<EquipmentEntity> getEquipmentByEcodes(@Param("companyId") Integer companyId, @Param("ecodes") Set<String> ecodes);

    /**
     * 批量修改设备类型
     *
     * @param etype
     * @param userId
     * @param msUserId
     * @param now
     * @param ids
     * @return
     */
    Integer updateEquipmentEtype(@Param("etype") Integer etype, @Param("userId") Integer userId,
                                 @Param("msUserId") Integer msUserId, @Param("now") Date now, @Param("ids") List<Integer> ids);

    /**
     * 更新设备
     * @param e 实体
     * @return
     */
    boolean updateEquipmentByEntity(@Param("e")EquipmentEntity e);
    /**
     * 插入或更新
     *
     * @param equipments
     * @return
     */
    int insertOrUpdate(@Param("equipments") List<EquipmentEntity> equipments);

    /**
     * 获取设备
     *
     * @param query
     * @param companyId
     * @return
     */
    List<EquipmentMinModel> getEquipment(@Param("query") String query, @Param("companyId") Integer companyId);

    /**
     * 查询设备是否被使用
     * 返回设备自身id
     *
     * @param tableName
     * @param companyId
     * @param ids
     * @return
     */
    List<Integer> getUsed(@Param("tableName") String tableName, @Param("companyId") Integer companyId, @Param("ids") List<Integer> ids);

    /**
     * 通过ecode更新type
     *
     * @param companyId
     * @param etype
     * @param ecode
     * @return
     */
    int updateEtypeByEcode(@Param("companyId") Integer companyId, @Param("etype") Integer etype, @Param("ecode") String ecode);

    /**
     * 根据导入研发设备批量修改设备表设备类型
     *
     * @param equipmentEntities
     * @return
     */
    Integer updateEtypeByRd(@Param("equipmentEntities") List<EquipmentEntity> equipmentEntities);


    Integer updateList(@Param("updateEquipments") List<EquipmentEntity> updateEquipments);
}
