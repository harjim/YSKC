package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.finaDaily.FinaDailyModel;
import com.yskc.ms.models.finaDaily.QueryDailyModel;
import com.yskc.ms.models.finaDaily.SaveFinaDailyModel;

import java.util.List;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/service/ms
 * @Author: hm
 * @CreateTime: 2022/9/6
 * @Description: 财务日报对应service
 */
public interface FinaDailyService {
    /**
     * 获取财务日志内容 包含数据权限
     * @param query
     * @param userInfo
     * @param dataPerm
     * @return
     */
    PageModel< List< FinaDailyModel>> getDailyList(QueryDailyModel query, UserInfo userInfo, DataPermModel dataPerm);

    /**
     * 暂存财务日志(添加/编辑)
     * @param model
     * @param userInfo
     * @return
     */
    Boolean saveFinaDaily(SaveFinaDailyModel model, UserInfo userInfo, Boolean isAdd) throws OwnerException;

    Boolean submitFinaDaily(SaveFinaDailyModel model, UserInfo userInfo) throws OwnerException;

    Boolean delFinaDaily(List< Integer > delIdArr) throws OwnerException;
}
