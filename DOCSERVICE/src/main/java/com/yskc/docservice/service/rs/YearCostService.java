package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.MonthCostExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelogsPackage: src/main/java/com/yskc/docservice/service/rs
 * @Author: hm
 * @CreateTime: 2022/8/16
 * @Description: 月度成本业务接口层
 */
public interface YearCostService {
    /**
     * 导入月度成本
     *
     * @param userInfo
     * @param data
     * @throws OwnerException
     */
    void importMonthCost(RsUserInfo userInfo, List< MonthCostExcel > data) throws OwnerException;
}
