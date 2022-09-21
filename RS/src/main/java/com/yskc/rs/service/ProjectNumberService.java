package com.yskc.rs.service;

import com.yskc.rs.entity.ProjectNumberEntity;
import com.yskc.rs.models.UserInfo;

public interface ProjectNumberService {
    Boolean setProjectNum(ProjectNumberEntity entity, UserInfo userInfo);

    Integer queryProjectNum(Integer companyId, Integer year);
}
