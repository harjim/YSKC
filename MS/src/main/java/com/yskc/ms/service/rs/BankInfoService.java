package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.BankInfoModel;

/**
 * Created by hck
 * on 2020/12/4 14:36
 * description:
 */
public interface BankInfoService {
    /**
     * 编辑银行账户信息
     * @param model
     * @param userInfo
     * @return
     */
    Boolean editInfo(BankInfoModel model, UserInfo userInfo);

    /**
     * 获取银行账户信息
     * @param companyId
     * @return
     */
    BankInfoModel getBankInfo(Integer companyId);

}
