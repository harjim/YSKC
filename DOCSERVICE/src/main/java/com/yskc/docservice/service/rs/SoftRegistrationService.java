package com.yskc.docservice.service.rs;


import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.SoftRegistrationExcel;

import java.util.List;

/**
 * @DateTime: 2021/11/2 14:44
 * @Description:计算机软件著作类service层
 * @author: hsx
 */
public interface SoftRegistrationService {

    /**
     * 导入计算机软件著作excel文件
     * @param info
     * @param list
     * @return
     * @throws OwnerException
     */
    Boolean importSoftRegistration(RsUserInfo info, List<SoftRegistrationExcel> list) throws OwnerException;

}
