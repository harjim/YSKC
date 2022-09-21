package com.yskc.ms.service.ms;

import com.yskc.ms.models.GroupRAndDManagementModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2022/1/20 13:42
 * @Description:
 * @author: hsx
 */
public interface GroupRAndDManagementService {

    /**
     * 获取集团研发管理报表数据
     * @param date
     * @return
     * @throws Exception
     */
    Map<String, Object> getTableData(Date date) throws Exception;
}
