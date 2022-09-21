package com.yskc.rs.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDocDao {
    String getMasterName(@Param("beginYear")Integer beginYear,@Param("companyId")Integer companyId);

    String getDeptName(@Param("companyId")Integer companyId,@Param("year")Integer year);

    String getReviewerName(@Param("companyId")Integer companyId,@Param("year")Integer year);
}
