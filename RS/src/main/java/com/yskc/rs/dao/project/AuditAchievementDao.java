package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.AuditAchievementEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-19 14:48
 * @Description: 成果审核dao层
 */
@Repository
public interface AuditAchievementDao extends BaseMapper<AuditAchievementEntity> {
    /**
     * 插入或更新
     *
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<AuditAchievementEntity> list);
}
