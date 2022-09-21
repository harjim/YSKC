package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.InitMemberExcel;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-23 17:17
 * @Description: 初始化项目人员列表业务层接口
 */
public interface InitMemberService {

    /**
     * 导入项目人员
     *
     * @param userInfo
     * @param data
     * @param year
     * @param projectId
     * @return
     * @throws OwnerException
     */
    Boolean importMember(RsUserInfo userInfo, List<InitMemberExcel> data, Integer year, Integer projectId) throws OwnerException;

}
