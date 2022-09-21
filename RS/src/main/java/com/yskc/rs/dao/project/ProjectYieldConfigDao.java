package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.ProjectYieldConfigEntity;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigInfoModel;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigModel;
import com.yskc.rs.models.projectyieldconfig.QueryYieldConfigModel;
import com.yskc.rs.models.projectyieldconfig.QueryYieldConfigParams;
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
     * 获取试制量列表
     *
     * @param query
     * @return
     */
    List<ProjectYieldConfigModel> getList(@Param("query") QueryYieldConfigModel query);

    /**
     * 获取试制时间
     *
     * @param query
     * @return
     */
    List<Date> getTrialDate(@Param("query") QueryYieldConfigModel query);

    /**
     * 获取重复数据
     *
     * @param config
     * @return
     */
    ProjectYieldConfigEntity getRepeat(@Param("config") ProjectYieldConfigEntity config);

    /**
     * 获取重复时间段数据
     *
     * @param config
     * @return
     */
    ProjectYieldConfigEntity getRepeatTime(@Param("config") ProjectYieldConfigEntity config);
    /**
     * 获取重复时间段数据
     *
     * @param configs
     * @return
     */
    ProjectYieldConfigEntity getReport(@Param("configs") List<ProjectYieldConfigEntity> configs);

    /**
     * 获取使用次数
     *
     * @param ids
     * @return
     */
    int getUsedCount(@Param("ids") List<Integer> ids);

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

    List<ProjectYieldConfigModel> queryPYieldConfigList(@Param("pagination") Pagination pagination,
                                                        @Param("param") QueryYieldConfigParams param,
                                                        @Param("companyId") Integer companyId,
                                                        @Param("beginMonth") Date beginMonth, @Param("endMonth") Date endMonth);

    List<ProjectYieldConfigEntity> findByBean(@Param("queryList") List<ProjectYieldConfigEntity> queryList);

    List<ProjectYieldConfigModel> queryPYieldConfigList(@Param("param") QueryYieldConfigParams param,
                                                        @Param("companyId") Integer companyId,
                                                        @Param("beginMonth") Date beginMonth,
                                                        @Param("endMonth") Date endMonth);

    /**
     * 删除旧的试制
     *
     * @param companyId
     * @param projectId
     * @param month
     * @param source
     * @return
     */
    int deleteOldTrial(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId,
                       @Param("month") Date month, @Param("source") Integer source);

    List<Date> getDate(@Param("projectId") Integer projectId, @Param("month") Date month, @Param("companyId") Integer companyId);

    /**
     * 批量修改显示状态
     *
     * @param ids
     * @param entity
     * @param status
     * @return
     */
    int editSelected(@Param("ids") List<Integer> ids, @Param("entity") ProjectYieldConfigEntity entity, @Param("status") Integer status);

    /**
     * 获取试制信息
     *
     * @param companyId
     * @param aggType
     * @param month
     * @return
     */
    List<ProjectYieldConfigInfoModel> getInfos(@Param("companyId") Integer companyId, @Param("month") Date month, @Param("aggType") Integer aggType);
}
