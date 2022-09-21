package com.yskc.rs.dao.company;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.company.BackupDataEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/11 16:00
 * @Description:
 */
@Repository
public interface BackupDataDao extends BaseMapper<BackupDataEntity> {
    /**
     * 批量插入
     * @param backupDatas
     * @return
     */
    Integer insertList(@Param("backupDatas") List<BackupDataEntity> backupDatas);
}
