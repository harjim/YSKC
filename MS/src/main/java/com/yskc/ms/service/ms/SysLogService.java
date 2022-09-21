package com.yskc.ms.service.ms;

import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.SysLog;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.log.QuerySysLogModel;
import com.yskc.ms.models.log.SysLogModel;
import com.yskc.ms.models.log.UserRsLogModel;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.models.user.OperationLogUserModel;
import com.yskc.ms.models.user.QueryUserListModel;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.ms
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-12 16:40
 * @Description: 日志业务层接口
 */
public interface SysLogService {
    /**
     * 获取所有日志信息
     *
     * @return
     */
    PageModel<List<SysLogModel>> querySysLogList(QuerySysLogModel query, DataPermModel dataPerm);

    /**
     * 根据用户名搜索
     *
     * @param userName
     * @param pageType
     * @return
     */
    List<MiniUserModel> getDataByUserName(String userName, Integer pageType);

    /**
     *  保存日志
     * @param sysLog
     */
    void save(SysLog sysLog);

    /**
     * 获取用户rs操作日志
     *
     * @param query
     * @return
     */
    PageModel<List<UserRsLogModel>> getUserRsLog(QuerySysLogModel query);

    /**
     * 获取操作日志用户
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel<List<OperationLogUserModel>> getOperationLogUserList(QueryUserListModel query, DataPermModel dataPerm);

    /**
     * 同步日志
     * @return
     */
    boolean syncLog();
}
