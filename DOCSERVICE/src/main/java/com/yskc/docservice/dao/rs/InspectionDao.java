package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.InspectionEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-11 15:04
 * @Description: 检测修理Dao层
 */
@Repository
public interface InspectionDao extends BaseMapper<InspectionEntity> {

    /**
     * 批量插入
     *
     * @param addList
     * @return
     */
    int addBatch(@Param("addList") List<InspectionEntity> addList);

   Integer updateList(@Param("inspectionEntities") List<InspectionEntity> inspectionEntities);
}