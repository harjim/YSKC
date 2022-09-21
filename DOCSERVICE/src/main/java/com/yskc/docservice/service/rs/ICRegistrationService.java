package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ICRegistrationExcel;

import java.util.List;

/**
 * @DateTime: 2021/11/2 14:44
 * @Description:集成电路设计类service层
 * @author: hsx
 */
public interface ICRegistrationService {

    /**
     * 导入集成电路设计类excel文件
     * @param info
     * @param list
     * @return
     */
    Boolean importICRegistration(RsUserInfo info, List<ICRegistrationExcel> list) throws OwnerException;

}
