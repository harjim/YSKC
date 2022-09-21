package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.trialprod.DocFileTrialModel;
import com.yskc.rs.service.DocFileTrialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/3 14:41
 * description:
 */
@Api(tags = "过程文档试制接口", value = "过程文档试制接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/docFileTrial")
public class DocFileTrialController extends BaseController{

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DocFileTrialService docFileTrialService;

    /**
     * @param models
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "验证报告添加试制", mode = SystemLog.SAVE_DB)
    @PostMapping("/addDocFileTrial")
    // @PermissionRequired(perms = "project:report:trial:add")
    @ApiOperation(value = "验证报告添加试制", notes = "验证报告添加试制", response = boolean.class)
    public boolean addDocFileTrial(@RequestBody @Validated List<DocFileTrialModel> models) throws OwnerException {
        UserInfo userInfo=getUserInfo();
        return docFileTrialService.addDocFileTrial(userInfo, models);
    }

    /**
     * @param
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "验证报告删除试制", mode = SystemLog.SAVE_DB)
    @PostMapping("/delDocFileTrial")
    //@PermissionRequired(perms = "project:report:trial:add")
    @ApiOperation(value = "验证报告删除试制", notes = "验证报告删除试制", response = boolean.class)
    public Boolean delDocFileTrial(@RequestBody @Validated DocFileTrialModel model) throws OwnerException{
        return docFileTrialService.delDocFileTrial(getUserInfo(),model.getTrialId(),model.getPdocFileId());
    }

}
