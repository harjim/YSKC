package com.yskc.rs.service;


import com.yskc.rs.entity.OwnershipEntity;
import com.yskc.rs.models.UserInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator
 */
public interface OwnershipService {
    /**
     * 查询股权架构
     *
     * @param companyId
     * @return
     */
    List<OwnershipEntity> queryOwnershipList(Integer companyId);

    /**
     * 保存股东
     *
     * @param userInfo
     * @param entity
     * @return
     */
    boolean addOwnership(UserInfo userInfo, OwnershipEntity entity);

    /**
     * 编辑股东
     *
     * @param userInfo
     * @param entity
     * @return
     */
    boolean editOwnership(UserInfo userInfo, OwnershipEntity entity);

    /**
     * 删除股东
     *
     * @param entity
     * @return
     */
    boolean delOwnership(OwnershipEntity entity);

    /**
     * 检查所剩股权比例
     *
     * @param userInfo
     * @param proportion
     * @param oldProportion
     * @return
     */
    BigDecimal checkProportion(UserInfo userInfo, BigDecimal proportion, BigDecimal oldProportion);
}
