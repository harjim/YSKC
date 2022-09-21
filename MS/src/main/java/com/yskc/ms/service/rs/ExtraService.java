package com.yskc.ms.service.rs;

import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.ExtraModel;
import com.yskc.ms.models.company.OwnershipModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/4 14:37
 * description:
 */
public interface ExtraService {
    /**
     * 编辑拓展信息
     * @param model
     * @param userInfo
     * @return
     */
    Integer editInfo(ExtraModel model, UserInfo userInfo);

    /**
     * 获取公司拓展信息
     * @param companyId
     * @return
     */
    ExtraModel getExtraInfo(Integer companyId);


}
