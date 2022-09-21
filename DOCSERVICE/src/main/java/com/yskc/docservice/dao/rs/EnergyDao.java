package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.EnergyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: blueToWhite
 * @CreateTime: 2019-07-09 09:18
 * @Description: 设备Dao层
 */
@Repository
public interface EnergyDao extends BaseMapper<EnergyEntity> {

    /**
     * 批量更新
     *
     * @param energyList
     * @return
     */
    int updateList(@Param("energyList") List<EnergyEntity> energyList);

    /**
     * 批量插入
     *
     * @param energys
     * @return
     */
    int addList(@Param("energys") List<EnergyEntity> energys);
}
