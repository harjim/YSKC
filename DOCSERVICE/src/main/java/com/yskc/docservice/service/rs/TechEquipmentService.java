package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.TechEquipmentModel;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/18 11:11
 * @Description:
 */
public interface TechEquipmentService {

    /**
     * 导入备案清单
     *
     * @param info
     * @param data
     * @return
     * @throws OwnerException
     */
    Boolean importEquipment(RsUserInfo info, List<TechEquipmentModel> data, Integer beianId) throws OwnerException;
}
