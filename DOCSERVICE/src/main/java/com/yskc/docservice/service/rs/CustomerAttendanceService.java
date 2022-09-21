package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.CustomerAttendanceExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-06 09:34
 * @Description: 员工考勤业务接口层
 */
public interface CustomerAttendanceService {

    /**
     * 导入考勤
     *
     * @param userInfo
     * @param data
     * @throws OwnerException
     */
    void importAttendance(RsUserInfo userInfo, List<CustomerAttendanceExcel> data) throws OwnerException;
}
