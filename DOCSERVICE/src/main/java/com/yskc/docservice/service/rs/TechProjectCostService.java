package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.TechProjectCostExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-25 08:24
 * @Description: 项目支出业务接口层
 */
public interface TechProjectCostService {

    /**
     * 导入项目支出
     *
     * @param userInfo
     * @param data
     * @param projectId
     * @return
     * @throws OwnerException
     */
    boolean importInfo(RsUserInfo userInfo, List<TechProjectCostExcel> data, Integer projectId) throws OwnerException;
}
