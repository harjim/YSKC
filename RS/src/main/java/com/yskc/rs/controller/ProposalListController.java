package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.proposal.ProposalListModel;
import com.yskc.rs.models.proposal.ProposalManagementModel;
import com.yskc.rs.models.proposal.ProposalRelevanceModel;
import com.yskc.rs.models.proposal.QueryProposalModel;
import com.yskc.rs.service.ProposalListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @DateTime: 2022/3/24 16:33
 * @Description:提案列表
 * @author: hsx
 */

@Api(tags = "提案列表接口", value = "提案列表接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/proposalList")
public class ProposalListController extends BaseController{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProposalListService proposalListService;

    @SystemLog(description = "保存提案列表信息")
    @PostMapping("/save")
    @PermissionRequired(perms = "project:proposalList:add,project:proposalList:edit")
    @ApiOperation(value = "保存提案列表信息", notes = "保存提案列表信息", response = boolean.class)
    public boolean saveProposal(@RequestBody @Validated ProposalListModel model) throws OwnerException {
        return proposalListService.save(getUserInfo(), model);
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:proposalList:search")
    @ApiOperation(value = "获取提案列表", notes = "获取提案列表", response = String.class)
    public PageModel<List<ProposalListModel>> getList(QueryProposalModel query) throws OwnerException {
        return proposalListService.getList(getUserInfo().getCompanyId(), query);
    }

    @GetMapping("/getInfo")
    @PermissionRequired(perms = "project:proposalList:edit")
    @ApiOperation(value = "获取提案列表", notes = "获取提案列表", response = String.class)
    public ProposalListModel getInfo(Integer id) throws OwnerException {
        return proposalListService.getInfo(id);
    }

    @SystemLog(description = "删除提案列表数据")
    @PostMapping("/del")
    @PermissionRequired(perms = "project:proposalList:del")
    @ApiOperation(value = "删除提案", notes = "删除提案", response = boolean.class)
    public boolean delProposal(@RequestBody @Validated BatchDeleteModel model) throws OwnerException {
        return proposalListService.del(model);
    }

    @SystemLog(description = "根据项目获取相关数据")
    @GetMapping("/selectByProjectId")
    @PermissionRequired(perms = "project:report:list:proposal")
    public List<ProposalListModel> selectByProjectId(Integer id) throws OwnerException {
        return proposalListService.getByProjectId(id);
    }

    @SystemLog(description = "关联提案列表数据")
    @PostMapping("/relevance")
    @ApiOperation(value = "关联提案列表数据", notes = "关联提案列表数据", response = boolean.class)
    public boolean relevance(@RequestBody @Validated ProposalRelevanceModel model) throws Exception {
        return proposalListService.relevance(model,getUserInfo());
    }
}
