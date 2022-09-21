package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.employee.EmployeeSelectModel;
import com.yskc.rs.models.employee.RdEmployeeModel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import com.yskc.rs.models.project.QueryProjectEmployeeModel;
import com.yskc.rs.models.reviewCommittee.QueryReviewModel;
import com.yskc.rs.models.reviewCommittee.ReviewAddModel;
import com.yskc.rs.models.reviewCommittee.ReviewModel;
import com.yskc.rs.service.ReviewCommitteeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hck
 * on 2020/9/1 10:26
 * description:评审委员名单接口
 */
@Api(tags = "评审委员会名单接口", value = "评审委员会名单接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/reviewCommittee")
public class ReviewCommitteeController extends BaseController {

    @Autowired
    private ReviewCommitteeService reviewCommitteeService;

    @GetMapping("/getReviews")
    @PermissionRequired(perms = "rdorg:reviewCommittee:search")
    @ApiOperation(value = "获取评审人员列表", notes = "获取评审人员列表", response = PageModel.class)
    public PageModel<List<ReviewModel>> getReviews(QueryReviewModel query) throws OwnerException {
        return reviewCommitteeService.getReviews(getUserInfo().getCompanyId(), query);
    }


    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "rdorg:reviewCommittee:add")
    @SystemLog(description = "添加评审委员", mode = SystemLog.SAVE_DB)
    @PostMapping("/addReviews")
    @ApiOperation(value = "添加评审委员", notes = "添加评审委员", response = boolean.class)
    public boolean addReviews(@RequestBody @Validated ReviewAddModel model) throws OwnerException {
        return reviewCommitteeService.addReviews(getUserInfo(), model);
    }

    @PermissionRequired(perms = "rdorg:reviewCommittee:dept")
    @SystemLog(description = "设置评审委员部门职位", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateDeptPosition")
    @ApiOperation(value = "设置评审委员部门职位", notes = "设置评审委员部门职位", response = boolean.class)
    public boolean updateDeptPosition(@RequestBody @Validated RdEmployeeModel model) throws OwnerException {
        return reviewCommitteeService.updateDeptPosition(getUserInfo(),model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "rdorg:reviewCommittee:del")
    @SystemLog(description = "删除评审人员", mode = SystemLog.SAVE_DB)
    @PostMapping("/delReview")
    @ApiOperation(value = "删除评审人员", notes = "删除评审人员", response = boolean.class)
    public boolean delReview(@RequestBody @Validated ReviewModel model) throws OwnerException {
        return reviewCommitteeService.delReview(getUserInfo().getCompanyId(), model.getReviewIds());
    }

    @PermissionRequired(perms = "rdorg:reviewCommittee:editSeq")
    @SystemLog(description = "编辑评审人员排序", mode = SystemLog.SAVE_DB)
    @PostMapping("/editSeq")
    @ApiOperation(value = "编辑评审人员排序", notes = "编辑评审人员排序", response = boolean.class)
    public boolean editSeq(@RequestBody @Validated List<ReviewModel> models) throws OwnerException {
        return reviewCommitteeService.editSeq(getUserInfo(), models);
    }

    @GetMapping("/getReviewsSelect")
    @ApiOperation(value = "获取评审人员下拉列表", notes = "获取评审人员下拉列表", response = PageModel.class)
    public List<EmployeeSelectModel> getReviewsSelect(QueryProjectEmployeeModel model) throws OwnerException {
        return reviewCommitteeService.getReviewsSelect(getUserInfo().getCompanyId(), model.getYear(),model.getEname());
    }


    @GetMapping("/getReviewList")
    @ApiOperation(value = "获取评审委员会名单", notes = "获取评审委员会名单", response = PageModel.class)
    public PageModel<List<ProjectEmployeeInfo>> getReviewList(QueryReviewModel query) throws OwnerException {
        return reviewCommitteeService.getReviewList(getUserInfo().getCompanyId(), query);
    }

}
