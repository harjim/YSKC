package com.yskc.rs.service;

import com.yskc.rs.models.fieldconfig.FieldConfigModel;
import com.yskc.rs.models.UserInfo;

import java.util.Map;

/**
 * @author wjy
 */
public interface FieldConfigService {
    /**
     * 添加配置明细名称
     *
     * @param model
     * @param userInfo
     * @return
     */
    boolean addFieldConfigCol(FieldConfigModel model, UserInfo userInfo);

    /**
     * 修改配置明细
     * @param model
     * @param userInfo
     * @return
     */
    boolean editFieldConfigCol(FieldConfigModel model, UserInfo userInfo);

    /**
     * 获取配置
     *
     * @param companyId
     * @param type
     * @return
     */
    FieldConfigModel getTypeConfig(Integer companyId, Integer type);
}
