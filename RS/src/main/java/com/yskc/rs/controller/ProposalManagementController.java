package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.models.proposal.ProposalManagementModel;
import com.yskc.rs.models.proposal.QueryProposalModel;
import com.yskc.rs.service.ProposalManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/7/14 11:09
 * @Description:提案管理接口
 */
@Api(tags = "提案管理接口", value = "提案管理接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/proposalManagement")
public class ProposalManagementController extends BaseController{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProposalManagementService proposalManagementService;
    @Autowired
    private RsConfig rsConfig;

    @SystemLog(description = "保存提案")
    @PostMapping("/saveProposal")
    @PermissionRequired(perms = "project:proposalManagement:save")
    @ApiOperation(value = "保存提案", notes = "保存提案", response = boolean.class)
    public boolean saveProposal(@RequestBody @Validated ProposalManagementModel model) throws OwnerException {
        return proposalManagementService.saveProposal(getUserInfo(), model);
    }

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:proposalManagement:search")
    @ApiOperation(value = "获取提案列表", notes = "获取提案列表", response = String.class)
    public PageModel<List<ProposalManagementModel>> getList(QueryProposalModel query) throws OwnerException {
        return proposalManagementService.getList(getUserInfo().getCompanyId(), query);
    }

    @SystemLog(description = "删除提案")
    @PostMapping("/delProposal")
    @PermissionRequired(perms = "project:proposalManagement:del")
    @ApiOperation(value = "删除提案", notes = "删除提案", response = boolean.class)
    public boolean delProposal(@RequestBody @Validated List<ProposalManagementModel> models) throws OwnerException {
        return proposalManagementService.delProposal(models);
    }


    /**
     * 上传文件
     *
     * @return
     * @throws OwnerException
     * @throws OwnerException
     */
    @PostMapping("/upload")
    @SystemLog(description = "上传附件", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "上传附件", notes = "上传附件", response = boolean.class)
    public Map<String, String> upload(@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("dir") String dir) throws OwnerException {
        String expertPath = rsConfig.getUploadConfig().getDocPath();
        Map<String, String> result = new LinkedHashMap<>();
        Integer companyId = getUserInfo().getCompanyId();
        for (MultipartFile file : multipartFile) {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            String filePath = dir + companyId + Constant.PATH_SEPARATOR + fileName;
            File tempFile = new File(expertPath + filePath);
            if (!tempFile.getParentFile().exists()) {
                tempFile.getParentFile().mkdirs();
            }
            try {
                file.transferTo(tempFile);
                result.put("filePath", filePath);
                result.put("fileName", file.getOriginalFilename());
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                throw new OwnerException("上传失败");
            }
        }
        return result;
    }
}
