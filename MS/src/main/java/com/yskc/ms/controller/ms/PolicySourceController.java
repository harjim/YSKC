package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.PolicySource;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.policy.PolicySourceModel;
import com.yskc.ms.models.policy.PolicySubscriptionModel;
import com.yskc.ms.models.policy.QueryPolicyContent;
import com.yskc.ms.models.policy.QuerySourceUser;
import com.yskc.ms.service.ms.PolicySourceService;
import com.yskc.ms.utils.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 10:23
 * @Description: 政策来源接口
 */
@Api(tags = "政策来源接口", value = "政策来源接口")
@RequestMapping("/api/policySource")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
public class PolicySourceController extends BaseController {

    @Autowired
    private PolicySourceService policySourceService;

    @PermissionRequired(perms = "sys:policySource:search")
    @GetMapping("/getList")
    @ApiOperation(value = "政策来源列表", notes = "政策来源列表")
    public PageModel<List<PolicySourceModel>> getList(QueryPolicyContent query) throws OwnerException {
        return policySourceService.getList(getUserInfo(), query);
    }

    @PermissionRequired(perms = "sys:policySource:subscription")
    @SystemLog(description = "取消订阅政策来源", mode = SystemLog.SAVE_DB)
    @PostMapping("/unsubscription")
    @ApiOperation(value = "取消订阅政策来源", notes = "取消订阅政策来源")
    public Boolean unsubscription(@RequestBody @Validated PolicySource source) throws OwnerException {
        return policySourceService.unsubscription(getUserInfo().getId(), source);
    }

    @PermissionRequired(perms = "sys:policySource:subscription")
    @SystemLog(description = "订阅政策来源", mode = SystemLog.SAVE_DB)
    @PostMapping("/subscription")
    @ApiOperation(value = "订阅政策来源", notes = "订阅政策来源")
    public Boolean subscription(@RequestBody @Validated PolicySource source) throws OwnerException {
        return policySourceService.subscription(getUserInfo().getId(), source);
    }

    @PermissionRequired(perms = "sys:policySource:sublist")
    @GetMapping("/getSourceUserList")
    @ApiOperation(value = "政策订阅人员列表", notes = "政策订阅人员列表")
    public PageModel<List<DeptUserInfo>> getSourceUserList(QuerySourceUser query) throws OwnerException {
        return policySourceService.getSourceUserList(query, getDataPerm());
    }

    @GetMapping("/checkTypeUrl")
    @ApiOperation(value = "检查政策TypeUrl是否重复", notes = "检查政策TypeUrl是否重复")
    public Boolean checkTypeUrl(Integer sourceId, String typeUrl) throws OwnerException {
        return policySourceService.checkTypeUrl(sourceId, typeUrl);
    }

    @SystemLog(description = "添加政策来源", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:policySource:add")
    @PostMapping("/add")
    @ApiOperation(value = "添加政策来源", notes = "添加政策来源")
    public Boolean add(@RequestBody @Validated PolicySource source) throws OwnerException {
        return policySourceService.add(getUserInfo().getId(), source);
    }

    @SystemLog(description = "更新政策来源", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:policySource:edit")
    @PostMapping("/update")
    @ApiOperation(value = "更新政策来源", notes = "更新政策来源")
    public Boolean update(@RequestBody @Validated PolicySource source) throws OwnerException {
        return policySourceService.update(getUserInfo().getId(), source);
    }

    /**
     * 这是一个关联操作，将同时删除policySourceUser，policyContent关联数据
     *
     * @param deleteModel
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除政策来源", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "sys:policySource:del")
    @PostMapping("/del")
    @ApiOperation(value = "删除政策来源", notes = "删除政策来源")
    public Boolean del(@RequestBody @Validated BatchDeleteModel deleteModel) throws OwnerException {
        return policySourceService.del(deleteModel);
    }

    @GetMapping("/getSource")
    @ApiOperation(value = "获取政策来源(单条)", notes = "获取政策来源(单条)")
    public Map<String, Object> getSource(Integer sourceId) {
        return policySourceService.getSource(sourceId);
    }

    @PostMapping("/addSubscription")
    @PermissionRequired(perms = "sys:policySource:addSubs")
    @SystemLog(description = "添加订阅人员", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加订阅人员", notes = "添加订阅人员")
    public boolean addSubscription(@RequestBody @Validated PolicySubscriptionModel subscriptionModel) {
        return policySourceService.addSubscription(subscriptionModel);
    }

    @GetMapping("/runPolicy")
    @PermissionRequired(perms="sys:policySource:run")
    @ApiOperation(value = "运行当前政策来源",notes = "运行当前政策来源")
    public String run(Integer sourceId)throws Exception{
        return ToolUtil.runPolicy(sourceId);
    }
}
