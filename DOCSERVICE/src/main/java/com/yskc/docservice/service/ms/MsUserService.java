package com.yskc.docservice.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.ms.MsUserInfo;

/**
 * @BelongsProject: DOCSERVICE
 * @BelongsPackage: com.yskc.docservice.service.ms
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-16 14:13
 * @Description: ms用户业务接口层
 */
public interface MsUserService {
    /**
     * 获取ms用户信息
     *
     * @param token
     * @return
     * @throws OwnerException
     */
    MsUserInfo getUserInfoByToken(String token)throws OwnerException;
}
