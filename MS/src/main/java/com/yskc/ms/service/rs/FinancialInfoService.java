package com.yskc.ms.service.rs;

import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.FinanceInfoModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/4 14:37
 * description:
 */
public interface FinancialInfoService {

    /**
     * 编辑单位财务信息
     *
     * @param models
     * @param userInfo
     * @return
     */
    Boolean editFinance(List<FinanceInfoModel> models, UserInfo userInfo);

    /**
     * 获取单位财务信息
     *
     * @param companyId
     * @param currentYear
     * @return
     */
    List<FinanceInfoModel> getFinanceInfo(int companyId, int currentYear);
}
