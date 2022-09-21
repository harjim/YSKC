package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.InitEquipmentExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-23 17:19
 * @Description: 初始化项目设备列表业务接口层
 */
public interface InitEquipmentService {

    /**
     * 导入设备清单
     *
     * @param userInfo
     * @param data
     * @param year
     * @param projectId
     * @return
     * @throws OwnerException
     */
    boolean importInitEquipment(RsUserInfo userInfo, List<InitEquipmentExcel> data, int year, Integer projectId) throws OwnerException;
}
