package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.InspectionExcel;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-11 15:05
 * @Description: 检测修理业务层接口
 */
public interface InspectionService {

    /**
     * @Description: 导入数据
     * @Param: [info, equipmentExcels]
     * @return: java.lang.Boolean
     * @Author: zhangdingfu
     * @date: 2019-07-10
     */
    String importInspection(RsUserInfo info, List<InspectionExcel> equipmentExcels, Integer type, Integer year) throws OwnerException;
}
