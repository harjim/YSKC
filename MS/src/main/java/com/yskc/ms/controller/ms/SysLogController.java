package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.log.QuerySysLogModel;
import com.yskc.ms.models.log.SysLogModel;
import com.yskc.ms.models.log.UserRsLogModel;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.models.user.OperationLogUserModel;
import com.yskc.ms.models.user.QueryUserListModel;
import com.yskc.ms.service.ms.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-12 16:29
 * @Description: 日志接口层
 */

@Api(tags = "日志接口", value = "日志接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/api/sysLog")
public class SysLogController extends BaseController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/querySysLogList")
    @PermissionRequired(perms = "sys:syslog:search")
    @ApiOperation(value = "获取所有日志信息", notes = "获取所有日志信息")
    public PageModel<List<SysLogModel>> querySysLogList(QuerySysLogModel query) throws OwnerException {
        return sysLogService.querySysLogList(query, getDataPerm());
    }

    @ApiOperation(value = "搜索用戶名", notes = "搜索用戶名")
    @GetMapping("/getDataByUserName")
    public List<MiniUserModel> getDataByUserName(String userName, Integer pageType) {
        return sysLogService.getDataByUserName(userName, pageType);
    }

    @GetMapping("/getOperationLogUserList")
    @PermissionRequired(perms = "customer:operationLog:search")
    @ApiOperation(value = "获取操作日志用户列表")
    public PageModel<List<OperationLogUserModel>> getOperationLogUserList(QueryUserListModel query) throws OwnerException {
        return sysLogService.getOperationLogUserList(query, getDataPerm());
    }

    @GetMapping("/getUserRsLog")
    @PermissionRequired(perms = "customer:operationLog:viewLog")
    @ApiOperation(value = "获取操作日志用户列表")
    public PageModel<List<UserRsLogModel>> getUserRsLog(QuerySysLogModel query) {
        return  sysLogService.getUserRsLog(query);
    }

    @PostMapping("/syncLog")
    @SystemLog(description = "同步rs日志统计",mode = SystemLog.SAVE_DB)
//    @PermissionRequired(perms = "customer:operationLog:syncLog")
    @ApiOperation(value = "同步rs日志统计",notes = "同步rs日志统计")
    public boolean syncLog(){
        return sysLogService.syncLog();
    }

}
