package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.policy.PolicyContentModel;
import com.yskc.ms.models.policy.QueryPolicyContent;
import com.yskc.ms.service.ms.PolicyContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-25 09:09
 * @Description: 政策内容接口
 */
@Api(tags = "政策内容接口", value = "政策内容接口")
@RequestMapping("/api/policyContent")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
public class PolicyContentController extends BaseController {

    @Autowired
    private PolicyContentService policyContentService;

    @PermissionRequired(perms = "sys:policyContent:search")
    @GetMapping("/getList")
    @ApiOperation(value = "获取政策内容列表", notes = "获取政策内容列表")
    public PageModel<List<PolicyContentModel>> getList(QueryPolicyContent query) throws OwnerException {
        return policyContentService.getList(query,getDataPerm(),getUserInfo().getId());
    }
}
