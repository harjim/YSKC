package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.HighTechIncomeModel;
import com.yskc.docservice.models.rs.RsUserInfo;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 8:18
 * @Description:
 */
public interface HighTechIncomeService {

    /**
     * 导入高品收入
     *
     * @param userInfo
     * @param data
     * @return
     */
    boolean importIncome(RsUserInfo userInfo, List<HighTechIncomeModel> data) throws OwnerException;

}
