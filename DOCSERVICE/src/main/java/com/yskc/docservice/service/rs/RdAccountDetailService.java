package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.RdAccountDetailExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-30 10:54
 * @Description: 研发费用明细业务的接口
 */
public interface RdAccountDetailService {
    /**
     * 导入研发费用明细
     *
     * @param userInfo
     * @param rdAccountDetailExcels
     * @param accType
     * @return
     * @throws OwnerException
     */
    boolean importInfo(RsUserInfo userInfo, List<RdAccountDetailExcel> rdAccountDetailExcels, int accType) throws OwnerException;
}
