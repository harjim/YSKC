package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectYieldConfigEntity;
import com.yskc.docservice.models.rs.QueryYieldConfigModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-02 18:17
 * @Description: 项目试制量配置dao层
 */
@Repository
public interface ProjectYieldConfigDao extends BaseMapper<ProjectYieldConfigEntity> {

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int addBatch(@Param("list") List<ProjectYieldConfigEntity> list);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<ProjectYieldConfigEntity> list);
    /**
     * 获取重复时间段数据
     *
     * @param configs
     * @return
     */
    ProjectYieldConfigEntity getReport(@Param("configs") List<ProjectYieldConfigEntity> configs);

    List<ProjectYieldConfigEntity> findByBean(@Param("queryList")List<ProjectYieldConfigEntity> queryList);

    List<Date> getDate(@Param("projectId")Integer projectId,@Param("month")Date month,@Param("companyId")Integer companyId);

    /**
     * 获取试制时间
     *
     * @param query
     * @return
     */
    List<Date> getTrialDate(@Param("query") QueryYieldConfigModel query);
}
