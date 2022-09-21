package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectAttendance;
import com.yskc.docservice.models.rs.BatchProjectHourAttendance;
import com.yskc.docservice.models.rs.projectattendance.BatchProjectAttendance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 研发人员Dao
 *
 * @author huronghua
 */
@Repository
public interface ProjectAttendanceDao extends BaseMapper<ProjectAttendance> {

    /**
     * 获取无用的研发考勤（修改过的归集数据已存在的研发考勤无效）
     *
     * @param enumbers
     * @param projectId
     * @param monthBegin
     * @param monthEnd
     * @return
     */
    List<BatchProjectAttendance> getUseless(@Param("enumbers") Set<String> enumbers, @Param("monthBegin") Date monthBegin,
                                            @Param("monthEnd") Date monthEnd, @Param("projectId") Integer projectId);

    /**
     * 获取存在的id
     *
     * @param enumbers
     * @param companyId
     * @param begin
     * @param end
     * @return
     */
    List<BatchProjectHourAttendance> getUsedData(@Param("enumbers") Set<String> enumbers,
                                                 @Param("companyId") Integer companyId, @Param("begin") Date begin,
                                                 @Param("end") Date end);

    /**
     * 统计当前月是否存在打卡记录
     *
     * @param companyId
     * @param start
     * @param end
     * @return
     */
    int countOwner(@Param("companyId") Integer companyId, @Param("start") Date start, @Param("end") Date end);

    /**
     * 通过id 获取（部分关键字段）
     *
     * @param ids
     * @return
     */
    List<ProjectAttendance> getByIds(@Param("ids") List<Integer> ids);

    /**
     * 插入研发考勤
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<ProjectAttendance> list);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<ProjectAttendance> list);
}
