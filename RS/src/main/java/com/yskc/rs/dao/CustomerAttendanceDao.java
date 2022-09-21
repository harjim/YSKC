package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.CustomerAttendanceEntity;
import com.yskc.rs.models.customerattendance.CustomerAttendanceModel;
import com.yskc.rs.models.customerattendance.QueryCustomerAttendanceModel;
import com.yskc.rs.models.projectattendance.BatchProjectHourAttendance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-06 09:05
 * @Description: 员工考勤dao
 */
@Repository
public interface CustomerAttendanceDao extends BaseMapper<CustomerAttendanceEntity> {
    /**
     * 查询考勤列表
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<CustomerAttendanceModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                          @Param("query") QueryCustomerAttendanceModel query);

    /**
     * 检测唯一性(返回id)
     *
     * @param companyId
     * @param enumber
     * @param workDate
     * @return
     */
    Integer checkedUnique(@Param("companyId") Integer companyId, @Param("enumber") String enumber, @Param("workDate") Date workDate);

    /**
     * 插入或更新
     *
     * @param list
     * @return
     */
    Integer insertOrUpdate(@Param("list") List<CustomerAttendanceEntity> list);

    /**
     * 查询考勤（不分页）
     *
     * @param companyId
     * @param query
     * @return
     */
    List<CustomerAttendanceModel> getList(@Param("companyId") Integer companyId,
                                          @Param("query") QueryCustomerAttendanceModel query);

    /**
     * 获取初始考勤，已使用考勤
     *
     * @param companyId
     * @param monthBegin
     * @param monthEnd
     * @param enumbers
     * @return
     */
    List<CustomerAttendanceEntity> getAttendanceInfo(@Param("companyId") Integer companyId,
                                                     @Param("monthBegin") Date monthBegin,
                                                     @Param("monthEnd") Date monthEnd,
                                                     @Param("enumbers") Set<String> enumbers);

    /**
     * 获取相同rdenumber的数据
     *
     * @param companyId
     * @param enumbers
     * @param monthBegin
     * @param monthEnd
     * @return
     */
    List<BatchProjectHourAttendance> getRdAttendanceInfo(@Param("companyId") Integer companyId,
                                                         @Param("enumbers") List<String> enumbers,
                                                         @Param("monthBegin") Date monthBegin,
                                                         @Param("monthEnd") Date monthEnd);

    /**
     * 更新单条记录
     *
     * @param entity
     * @return
     */
    int updateAttendance(@Param("entity") CustomerAttendanceEntity entity);

    /**
     * 获取任意一个Id
     *
     * @param companyId
     * @return
     */
    Integer getLimitOneId(@Param("companyId") Integer companyId);
}
