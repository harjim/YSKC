package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.OwnershipModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/4 14:38
 * description:
 */
public interface OwnershipService {
    /**
     * 编辑股权架构信息
     *
     * @param models
     * @param userInfo
     * @return
     */
    Boolean editInfo(List<OwnershipModel> models, UserInfo userInfo) throws OwnerException;

    /**
     * 获取公司股权架构
     *
     * @param companyId
     * @return
     */
    List<OwnershipModel> getOwnership(Integer companyId);

    /**
     * 删除股权架构
     *
     * @param id
     * @return
     */
    Boolean deleteOwnership(Integer id);
}
