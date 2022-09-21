package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.entity.ms.ContractTraceability;
import com.yskc.ms.entity.ms.models.ContractTraceabilityModel;
import com.yskc.ms.models.QueryContractTraceabilityModel;
import com.yskc.ms.service.ms.ContractTraceabilityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.controller.ms
 * @Author: wangxing
 * @CreateTime: 2020-03-09 10:43
 * @Description: ContractTraceabilityController
 */
@Api(tags = "合同追溯类接口", value = "合同追溯类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/contractTraceability")
public class ContractTraceabilityController extends BaseController {
    @Autowired
    private ContractTraceabilityService traceabilityService;

    @Autowired
    private MsConfig msConfig;

    @GetMapping("/queryConTraceabilityList")
    @PermissionRequired(perms = "sys:contractTraceability:search")
    @ApiOperation(value = "获取所有合同追溯信息", notes = "获取所有合同追溯信息")
    public PageModel<List<ContractTraceabilityModel>> queryConTraceabilityList(QueryContractTraceabilityModel query) throws OwnerException {
        return traceabilityService.queryConTraceabilityList(query, getDataPerm());
    }

    /**
     * 上传封面
     *
     * @return
     * @throws IOException
     * @throws OwnerException
     */
    @PostMapping("/uploadThecoverUrl")
    @ApiOperation(value = "上传封面", notes = "上传封面", response = boolean.class)
    public String uploadThecoverUrl(@RequestParam("avatar") MultipartFile multipartFile) {
        String expertPath = msConfig.getUploadConfig().getImagePath();
        String fileName = "/thecover/" + System.currentTimeMillis() + multipartFile.getOriginalFilename();
        File tempFile = new File(expertPath + fileName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(tempFile);
        } catch (Exception ex) {
        }
        return fileName;
    }

    @SystemLog(description = "添加合同追溯", mode = SystemLog.SAVE_DB)
    @PostMapping("/addConTraceability")
    @PermissionRequired(perms = "sys:contractTraceability:add")
    @ApiOperation(value = "添加合同追溯", notes = "添加合同追溯", response = boolean.class)
    public boolean addConTraceability(@RequestBody @Validated ContractTraceability ct) throws OwnerException {
        return traceabilityService.addConTraceability(getUserInfo(), ct);
    }


    @SystemLog(description = "修改合同追溯", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateConTraceability")
    @PermissionRequired(perms = "sys:contractTraceability:edit")
    @ApiOperation(value = "修改合同追溯", notes = "修改合同追溯", response = boolean.class)
    public boolean updateConTraceability(@RequestBody @Validated ContractTraceabilityModel model) throws OwnerException {
        return traceabilityService.updateConTraceability(getUserInfo(), model);
    }

    @SystemLog(description = "检查是否存在相同项目合同编号")
    @GetMapping("/checkContractNo")
    @ApiOperation(value = "检查是否存在相同项目合同编号", notes = "检查是否存在相同项目合同编号", response = boolean.class)
    public boolean checkContractNo(String contractNo) {
        return traceabilityService.checkContractNo(contractNo);
    }

    @SystemLog(description = "删除合同追溯", mode = SystemLog.SAVE_DB)
    @PostMapping("/delete")
    @PermissionRequired(perms = "sys:contractTraceability:del")
    @ApiOperation(value = "删除合同追溯", notes = "删除合同追溯")
    public Boolean delete(@RequestBody @Validated ContractTraceability traceability) {
        return traceabilityService.delete(traceability.getId());
    }
}
