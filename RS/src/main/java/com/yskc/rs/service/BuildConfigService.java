package com.yskc.rs.service;

import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.buildconfig.BuildConfigModel;

import java.util.List;

public interface BuildConfigService {
    boolean saveBuildConfig(BuildConfigModel[] models, UserInfo info);

    List<BuildConfigModel> getBuildConfig(Integer companyId,Integer year);
}
