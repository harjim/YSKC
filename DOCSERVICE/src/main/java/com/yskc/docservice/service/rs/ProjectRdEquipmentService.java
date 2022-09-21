package com.yskc.docservice.service.rs;

import com.yskc.docservice.models.rs.projectrdequipment.BatchProjectRdEquipmentModel;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-01 11:01
 * @Description: 项目设备研发折旧业务层接口
 */
public interface ProjectRdEquipmentService {

    /**
     * 导入研发工时
     *
     * @param info
     * @param batchModel
     * @param year
     * @return
     * @throws OwnerException
     */
    Boolean importRdHour(RsUserInfo info, BatchProjectRdEquipmentModel batchModel, int year) throws OwnerException;
}
