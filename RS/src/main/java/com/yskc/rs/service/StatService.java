package com.yskc.rs.service;

import com.yskc.rs.models.KeypointStaticModel;

import java.util.Map;

public interface StatService {

    public Map statProject(Integer companyId);

    /**
     * 返回登录页的数据统计
     * @return
     */
    KeypointStaticModel getStat();
}
