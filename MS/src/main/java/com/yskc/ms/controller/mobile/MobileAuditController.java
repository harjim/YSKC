package com.yskc.ms.controller.mobile;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.models.flowInstance.FeeFlowInstanceLogModel;
import com.yskc.ms.service.ms.FlowInstanceLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-22 14:27
 **/
@Api(tags = "移动端审批接口", value = "移动端审批接口")
@RestController
@RequestMapping("/api/mobile/audit")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
public class MobileAuditController {
    @Autowired
    private FlowInstanceLogService flowInstanceLogService;

    /**
     *  审核日志查询
     * @param instanceId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getAuditLog")
    @ApiOperation(notes = "审核日志查询", value = "审核日志查询")
    @SystemLog(description = "审核日志查询")
    @PermissionRequired(perms = "mobile:serviceApply:log,mobile:workRecord:log")
    public List<FeeFlowInstanceLogModel> getAuditLog(Integer instanceId) {
        return flowInstanceLogService.getAuditLog(instanceId);
    }
}
