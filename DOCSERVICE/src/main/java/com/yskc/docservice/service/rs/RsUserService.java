package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.service.rs
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-16 13:44
 * @Description: rs用户service
 */
public interface RsUserService {
    /**
     * 通过token获取用户信息
     *
     * @param token
     * @param companyId
     * @return
     * @throws OwnerException
     */
    RsUserInfo getUserInfoByToken(String token, String companyId)throws OwnerException;
}
