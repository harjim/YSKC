package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.EmploymentInfoModel;

/**
 * Created by hck
 * on 2020/12/4 14:38
 * description:
 */
public interface EmploymentInfoService {
    /**
     * 编辑从业人员信息
     * @param model
     * @param userInfo
     * @return
     */
    Integer editEmploymentInfo(EmploymentInfoModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 获取年从业人员信息
     * @param companyId
     * @param year
     * @return
     */
    EmploymentInfoModel getEmploymentInfo(int companyId, int year);
}
