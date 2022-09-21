package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.CustomerAttendanceEntity;
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
     * 插入或更新
     *
     * @param list
     * @return
     */
    Integer insertOrUpdate(@Param("list") List<CustomerAttendanceEntity> list);

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
}
