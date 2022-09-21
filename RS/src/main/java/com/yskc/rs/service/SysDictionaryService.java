package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.HighTecIndustryModel;
import com.yskc.rs.models.SysDictionaryModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.TableFieldInfo;

import java.util.List;

/**
 * 字典服务
 * @author huronghua
 */
public interface SysDictionaryService {
    /**
     * 获取字典
     * @param type
     * @return
     */
    List<SysDictionaryModel> getDictionaryByType(Integer type);

    /**
     * 获取高新
     * @return
     */
    List<HighTecIndustryModel> getHighTecIndustryModels();

    /**
     * 获取表格头字段配置
     * @param userInfo
     * @param tableId
     * @return
     */
    TableFieldInfo getTableField(UserInfo userInfo, String tableId);

    /**
     *保存表格头字段配置
     * @param userInfo
     * @param tableField
     * @return
     * @throws OwnerException
     */
    boolean saveTableField(UserInfo userInfo,TableField tableField) throws OwnerException;


}
