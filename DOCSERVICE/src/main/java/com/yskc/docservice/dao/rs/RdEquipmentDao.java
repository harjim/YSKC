package com.yskc.docservice.dao.rs;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.RdEquipmentEntity;
import com.yskc.docservice.models.rs.rdequipment.RdEquipmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-02 09:15
 * @Description: 研发设备dao层
 */
@Repository
public interface RdEquipmentDao extends BaseMapper<RdEquipmentEntity> {

    /**
     * 批量添加
     *
     * @param rdEquipments
     * @return
     */
    Integer addBatch(@Param("rdEquipments") List<RdEquipmentEntity> rdEquipments);

    /**
     * 通过ecode获取研发设备
     *
     * @param companyId
     * @param ecodes
     * @return
     */
    List<RdEquipmentModel> getByEcodes(@Param("companyId") Integer companyId, @Param("ecodes") Set<String> ecodes, @Param("year") Integer year);

    /**
     * 添加导入研发设备列表
     *
     * @param rdEquipmentEntities
     * @return
     */
    Integer saveRdEquipmentLists(@Param("RdEquipmentEntitys") List<RdEquipmentEntity> rdEquipmentEntities);


    Integer updateList(@Param("updateModels") List<RdEquipmentModel> updateModels, @Param("date") Date date, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId);

    List<Integer> getEtype(@Param("companyId") Integer companyId,@Param("year") Integer year,@Param("ecode") Set<String> ecode);
}
