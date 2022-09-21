package com.yskc.rs.dao;

import com.yskc.rs.models.StatInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatDao {
    List<StatInfo> getProjectStat(Integer companyId);
    List<StatInfo> getProjectRDStat(Integer companyId);
    List<StatInfo> getTechProjectStat(Integer companyId);
}
