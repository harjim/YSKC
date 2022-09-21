package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EquipmentExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-09 09:21
 * @Description: 设备业务层接口
 */
public interface EquipmentService {


    /**
     * 导入数据
     *
     * @param info
     * @param equipmentExcels
     * @return
     * @throws OwnerException
     */
    String importEquipment(RsUserInfo info, List<EquipmentExcel> equipmentExcels) throws OwnerException;

}
