package com.yskc.ms.controller.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageResult;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.ProposalManagementModel;
import com.yskc.ms.models.QueryProposalModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.service.rs.ProposalManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @DateTime: 2021/9/6 17:59
 * @Description:提案管理
 */
@Api(tags = "提案管理类接口", value = "提案管理类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/proposalManagement")
public class ProposalManagementController extends BaseController {

    @Autowired
    private ProposalManagementService proposalManagementService;

    @GetMapping("/getList")
    @ApiOperation(notes = "获取审核列表", value = "获取审核列表")
    public PageResult getList(QueryProposalModel query) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return proposalManagementService.getList(query, userInfo);

    }

    @GetMapping("/getProposal")
    @ApiOperation(value = "获取提案信息", notes = "获取提案信息")
    public ProposalManagementModel getProposal(Integer id) {
        return proposalManagementService.getProposal(id);
    }
}
