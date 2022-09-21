package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.DataEquipmentExcel;

import java.util.List;

/**
 * 设备使用记录
 *
 * @author zhangdingfu
 */
public interface DataEquipmentService {

    /**
     * 导入设备使用
     *
     * @param equipmentExcels
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    boolean importInfo(RsUserInfo userInfo, List<DataEquipmentExcel> equipmentExcels) throws OwnerException;
}
