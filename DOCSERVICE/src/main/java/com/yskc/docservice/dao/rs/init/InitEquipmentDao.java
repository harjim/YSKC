package com.yskc.docservice.dao.rs.init;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.init.InitEquipmentEntity;
import com.yskc.docservice.models.rs.initequipment.InitEquipmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-23 16:40:58
 */
@Repository
public interface InitEquipmentDao extends BaseMapper<InitEquipmentEntity> {


    /**
     * 批量新增人员
     *
     * @param initEquipmentList
     * @return
     */
    int addBatch(@Param("initEquipmentList") List<InitEquipmentEntity> initEquipmentList);


    /**
     * 获取存在列表
     *
     * @param companyId
     * @param projectId
     * @param ecodes
     * @return
     */
    List<InitEquipmentEntity> getExistList(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId,
                                           @Param("ecodes") Set<String> ecodes, Integer year);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<InitEquipmentEntity> list);

    /**
     * 通过enumber获取
     *
     * @param companyId
     * @param projectId
     * @param ecodes
     * @param year
     * @return
     */
    List<String> getByEcodes(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("ecodes") Set<String> ecodes, @Param("year") Integer year);

    /**
     * 根据年和项目获取设备
     *
     * @param companyId
     * @param year
     * @param projectId
     * @return
     */
    List<InitEquipmentModel> getEquList(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("projectId") Integer projectId);


}
