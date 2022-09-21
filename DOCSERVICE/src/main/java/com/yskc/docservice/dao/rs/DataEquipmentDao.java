package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.DataEquipmentEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备使用
 *
 * @author huronghua
 */
@Repository
public interface DataEquipmentDao extends BaseMapper<DataEquipmentEntity> {

    /**
     * 批量更新插入
     *
     * @param dataEquipmentList
     * @return
     */
    int insertOrUpdate(@Param("dataEquipmentList") List<DataEquipmentEntity> dataEquipmentList);

    /**
     * 批量更新
     *
     * @param dataEquipmentList
     * @return
     */
    int updateBatch(@Param("dataEquipmentList") List<DataEquipmentEntity> dataEquipmentList);
}
