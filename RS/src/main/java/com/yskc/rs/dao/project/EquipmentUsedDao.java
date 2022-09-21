package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.EquipmentUsedEntity;
import com.yskc.rs.entity.project.ProjectEquipmentEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-13 18:13:59
 */
@Repository
public interface EquipmentUsedDao extends BaseMapper<EquipmentUsedEntity> {

    /**
     * 获取设备使用
     *
     * @param companyId
     * @param month
     * @param ecode
     * @return
     */
    EquipmentUsedEntity getEquipmentUsed(@Param("companyId") Integer companyId, @Param("month") Date month, @Param("ecode") String ecode);

    /**
     * 获取剩余工时
     *
     * @param companyId
     * @param month
     * @param ecodes
     * @return
     */
    List<EquipmentUsedEntity> getByEcodes(@Param("companyId") Integer companyId, @Param("month") Date month, @Param("ecodes") List<String> ecodes);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<EquipmentUsedEntity> list);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<EquipmentUsedEntity> list);

    /**
     * 获取使用过的工时记录
     *
     * @param list
     * @param companyId
     * @return
     */
    List<EquipmentUsedEntity> getUseless(@Param("companyId") Integer companyId, @Param("list") List<ProjectEquipmentEntity> list);
}
