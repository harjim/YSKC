package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectAttendanceUsed;
import com.yskc.rs.models.projectattendance.BatchProjectHourAttendance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 使用工时记录表
 *
 * @author huronghua
 */
@Repository
public interface ProjectAttendanceUsedDao extends BaseMapper<ProjectAttendanceUsed> {

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<ProjectAttendanceUsed> list);

    /**
     * 获取已使用考勤记录
     *
     * @param companyId
     * @param monthBegin
     * @param monthEnd
     * @param enumbers
     * @return
     */
    List<ProjectAttendanceUsed> getByEnumbers(@Param("companyId") Integer companyId, @Param("monthBegin") Date monthBegin,
                                              @Param("monthEnd") Date monthEnd, @Param("enumbers") Set<String> enumbers);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<ProjectAttendanceUsed> list);

    /**
     * 查询研发使用剩余数据
     *
     * @param companyId
     * @param enumbers
     * @param monthBegin
     * @param monthEnd
     * @return
     */
    List<BatchProjectHourAttendance> getRdAttendanceUsed(@Param("companyId") Integer companyId,
                                                         @Param("enumbers") List<String> enumbers,
                                                         @Param("monthBegin") Date monthBegin,
                                                         @Param("monthEnd") Date monthEnd);
}
