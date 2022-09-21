package com.yskc.rs.service;

import com.yskc.rs.entity.Expert;

/**
 * 专家业务接口层
 *
 * @author zhangdingfu
 */
public interface ExpertService {

    /**
     * 通过uuid获取专家信息
     *
     * @param expertUuid
     * @return
     */
    Expert getExpertByUuid(String expertUuid);

}
