package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.company.SupportModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/4 14:38
 * description:
 */
public interface SupportService {
    /**
     * 编辑政府扶持情况
     * @param models
     * @param userInfo
     * @return
     */
    Boolean editSupport(List<SupportModel> models, UserInfo userInfo) throws OwnerException;

    /**
     * 获取财政扶持情况
     * @param companyId
     * @param year
     * @return
     */
    List<SupportModel>  getSupport(Integer companyId,int year);


    /**
     * 删除
     * @param id
     * @return
     */
    Boolean deleteSupport(Integer id);
}
