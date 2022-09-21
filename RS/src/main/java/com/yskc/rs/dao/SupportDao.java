package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.SupportEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-25 11:23:27
 */
@Repository
public interface SupportDao extends BaseMapper<SupportEntity> {
    /**
     *
     * @param companyId
     * @param checkTime
     * @param supportTime
     * @param supportDeptName
     * @param projectName
     * @return
     */
    List<SupportEntity> querySupportList(@Param("companyId") Integer companyId, @Param("checkTime") Date checkTime, @Param("supportTime") String supportTime, @Param("supportDeptName") String supportDeptName, @Param("projectName") String projectName);


}
