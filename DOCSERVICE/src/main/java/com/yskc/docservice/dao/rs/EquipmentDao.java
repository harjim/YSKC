package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.EquipmentEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
     * 插入或更新
     *
     * @param equipments
     * @return
     */
    int insertOrUpdate(@Param("equipments") List<EquipmentEntity> equipments);

    /**
     * 查询是否存在相同的ecode
     *
     * @param companyId
     * @param ecodes
     * @return
     */
    List<EquipmentEntity> getEquipmentByEcodes(@Param("companyId") Integer companyId, @Param("ecodes") Set<String> ecodes);

}
