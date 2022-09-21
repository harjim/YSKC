package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectFinaSchedule;
import com.yskc.docservice.models.rs.excel.ProjectFinaScheduleExcel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-17 14:19
 * @Description: 项目试验试制实际工时表dao层
 */
@Repository
public interface ProjectFinaScheduleDao extends BaseMapper<ProjectFinaSchedule> {

    /**
     * 插入或更新
     *
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<ProjectFinaSchedule> list);

    /**
     * 获取存在id
     *
     * @param list
     * @param companyId
     * @return
     */
    List<Integer> getExistIds(@Param("list") List<ProjectFinaScheduleExcel> list, @Param("companyId") Integer companyId);

}
