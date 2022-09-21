package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EquipmentExcel;

import java.util.List;


/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-02 09:14
 * @Description: 研发设备业务层接口
 */
public interface RdEquipmentService {

    /**
     * 导入研发设备
     * @param info
     * @param equipmentExcelList
     * @param year
     * @return
     * @throws OwnerException
     */
    String importRdEquipment(RsUserInfo info, List<EquipmentExcel> equipmentExcelList, Integer year) throws OwnerException;
}
