package com.yskc.rs.dao.data;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.data.DataAttendanceEntity;
import com.yskc.rs.models.attendance.DataAttendanceModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

/**
 * 员工考勤
 * @author huronghua
 */
@Repository
public interface DataAttendanceDao extends BaseMapper<DataAttendanceEntity> {
    /**
     * 获取员工考勤
     * @param page
     * @param companyId
     * @param ename
     * @param month
     * @return
     */
    List<DataAttendanceModel> getAttendanceList(Pagination page, @Param("companyId") Integer companyId, @Param("ename") String ename, @Param("month") Date month);

    /**
     * 检查指定月份的员工考勤记录
     * @param companyId
     * @param month
     * @param enumber
     * @return
     */
    Integer checkRecordByMonth(@Param("companyId") Integer companyId,@Param("month") Date month,@Param("enumber") String enumber);

    /**
     * 获取指定月份的员工考勤记录
     * @param companyId
     * @param month
     * @param enumber
     * @return
     */
    DataAttendanceEntity selectAttendanceByMonth(@Param("companyId") Integer companyId, @Param("month") Date month, @Param("enumber") String enumber);
    /**
     * 批量插入
     * @param attendances
     * @return
     */
    Integer addBatch(@Param("attendances") List<DataAttendanceEntity> attendances);

    /**
     * 批量重置
     * @param ids
     * @return
     */
    Integer updateFinishByIds(@Param("ids") List<Integer> ids );

    /**
     * 批量更新
     * @param list
     * @return
     */
    Integer updateBatch(@Param("list") List<DataAttendanceEntity> list);
}
