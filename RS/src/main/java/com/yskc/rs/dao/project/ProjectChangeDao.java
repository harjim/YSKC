package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectChangeEntity;
import com.yskc.rs.models.project.ChangeRecordModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/rs/dao/project
 * @Author: hm
 * @CreateTime: 2022/8/26
 * @Description: p_change 表dao层
 */
@Repository
public interface ProjectChangeDao extends BaseMapper< ProjectChangeEntity > {
    /**
     * 查询所有信息
     * @param projectId
     * @param companyId
     * @return
     */
    List< ChangeRecordModel> getChangeList(@Param("projectId") Integer projectId, @Param("companyId") Integer companyId);

    /**
     * 查询最后一条项目名称记录
     * @param projectId
     * @param companyId
     * @return
     */
    ChangeRecordModel getLastHistory(@Param("projectId") Integer projectId, @Param("companyId") Integer companyId);

    /**
     * 查询是否存在相同公司名称或变更时间记录
     * @param insertId
     * @param projectId
     * @param companyId
     * @param insertContent
     * @param changeTime
     * @return
     */
    List< ChangeRecordModel > selectByTypeAndTime(@Param("insertId") Integer insertId, @Param( "projectId" ) Integer projectId, @Param( "companyId" ) Integer companyId, @Param( "projectName" ) String insertContent, @Param( "changeTime" ) Date changeTime);
}
