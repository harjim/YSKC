package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ProjectFinaScheduleExcel;

import java.util.List;
/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-17 14:18
 * @Description: 项目试验试制实际工时表业务接口层
 */
public interface ProjectFinaScheduleService {

    /**
     * 导入试验试制实际工时
     *
     * @param info
     * @param data
     * @return
     * @throws OwnerException
     */
    Boolean importFinaSchedule(RsUserInfo info, List<ProjectFinaScheduleExcel> data) throws OwnerException;
}
