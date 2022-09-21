package com.yskc.ms.service.rs;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.projectApproval.QueryProjectEquipmentModel;
import com.yskc.ms.models.rs.summary.InitEquipmentModel;

import java.util.List;

/**
 * @DateTime: 2021/9/15 14:16
 * @Description:
 * @author: hsx
 */
public interface InitEquipmentService {

    /**
     * 获取项目设备列表
     *
     * @param query
     * @return
     */
    PageModel<List<InitEquipmentModel>> getList(QueryProjectEquipmentModel query);
}
