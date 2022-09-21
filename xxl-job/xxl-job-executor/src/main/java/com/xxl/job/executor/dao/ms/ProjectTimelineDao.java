package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.ProjectTimelineEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/11/4 10:28
 * description:
 */
@Repository
public interface ProjectTimelineDao extends BaseMapper<ProjectTimelineEntity> {
    /**
     * 类型获取时间线
     * @param itemType
     * @return
     */
    List<ProjectTimelineEntity> getByProjectId(@Param("itemType") Integer itemType);

    /**
     * 批量插入
     * @param insertList
     */
    int inertList(@Param("insertList") List<ProjectTimelineEntity> insertList);

    /**
     * 批量更新
     * @param updateList
     * @return
     */
    int updateList(@Param("updateList") List<ProjectTimelineEntity> updateList);

    /**
     * 获取阶段文件时间线
     * @return
     */
    List<ProjectTimelineEntity> getDocByStage();
}
