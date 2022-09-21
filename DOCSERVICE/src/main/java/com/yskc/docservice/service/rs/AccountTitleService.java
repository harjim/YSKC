package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.AccountExcel;

import java.util.List;

/**
 * @author Administrator
 */
public interface AccountTitleService {

    /**
     * 导入科目
     * @param userInfo
     * @param data
     * @return
     * @throws OwnerException
     */
    String importAccount(RsUserInfo userInfo, List<AccountExcel> data) throws OwnerException;
}
