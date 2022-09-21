package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.company.*;
import com.yskc.ms.service.rs.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/12/4 14:31
 * description:公司信息接口
 */
@Api(tags = "公司信息接口", value = "公司信息接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/companyInfo")
public class CompanyInfoController extends BaseController {

    @Autowired
    private BankInfoService bankInfoService;
    @Autowired
    private ExtraService extraService;
    @Autowired
    private SupportService supportService;
    @Autowired
    private FinancialInfoService financialInfoService;
    @Autowired
    private OwnershipService ownershipService;
    @Autowired
    private EmploymentInfoService employmentInfoService;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private CompanyService companyService;

    @GetMapping("/queryBasicInfo")
    @ApiOperation(value = "查询基本信息", notes = "查询基本信息", response = String.class)
    public CompanyInfoModel queryBasicInfo(Integer companyId) {
        return companyService.getInfo(companyId);
    }

    @PostMapping("/editBasicInfo")
    @SystemLog(description = "编辑基本信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:edit")
    @ApiOperation(value = "编辑基本信息", notes = "编辑基本信息", response = boolean.class)
    public Boolean editBasicInfo(@RequestBody @Validated CompanyInfoModel model) throws OwnerException {
        return companyService.editInfo(model, getUserInfo());
    }

    @GetMapping("/queryExtra")
    @ApiOperation(value = "查询拓展信息", notes = "查询拓展信息", response = String.class)
    public ExtraModel queryExtra(Integer companyId) {
        return extraService.getExtraInfo(companyId);
    }

    @PostMapping("/editExtra")
    @SystemLog(description = "编辑拓展信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:edit")
    @ApiOperation(value = "编辑拓展信息", notes = "编辑拓展信息", response = boolean.class)
    public Integer editExtra(@RequestBody @Validated ExtraModel model) throws OwnerException {
        return extraService.editInfo(model, getUserInfo());
    }

    @GetMapping("/getOwnership")
    @ApiOperation(value = "查询股权架构", notes = "查询股权架构", response = String.class)
    public List<OwnershipModel> getOwnership(Integer companyId) throws OwnerException {
        return ownershipService.getOwnership(companyId);
    }

    @PostMapping("/editOwnership")
    @SystemLog(description = "编辑股权架构", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:edit")
    @ApiOperation(value = "编辑股权架构", notes = "编辑股权架构", response = boolean.class)
    public Boolean editOwnership(@RequestBody @Validated List<OwnershipModel> models) throws OwnerException {
        return ownershipService.editInfo(models, getUserInfo());
    }

    @PostMapping("/deleteOwnership")
    @SystemLog(description = "删除股权架构", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:edit")
    @ApiOperation(value = "删除股权架构", notes = "删除股权架构", response = boolean.class)
    public Boolean deleteOwnership(@RequestBody OwnershipModel model) {
        return ownershipService.deleteOwnership(model.getId());
    }

    @GetMapping("/getBankInfo")
    @ApiOperation(value = "查询银行账户信息", notes = "查询银行账户信息", response = String.class)
    public BankInfoModel getBankInfo(int companyId) {
        return bankInfoService.getBankInfo(companyId);
    }

    @PostMapping("/editBankInfo")
    @SystemLog(description = "编辑银行账户信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:edit")
    @ApiOperation(value = "编辑银行账户信息", notes = "编辑银行账户信息", response = boolean.class)
    public Boolean editBankInfo(@RequestBody @Validated BankInfoModel model) throws OwnerException {
        return bankInfoService.editInfo(model, getUserInfo());
    }

    @GetMapping("/getSupport")
    @ApiOperation(value = "查询财政扶持情况", notes = "查询财政扶持情况", response = String.class)
    public List<SupportModel> getSupport(Integer companyId, int year) {
        return supportService.getSupport(companyId, year);
    }

    @PostMapping("/editSupport")
    @SystemLog(description = "编辑省市财政扶持情况", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:edit")
    @ApiOperation(value = "编辑省市财政扶持情况", notes = "编辑省市财政扶持情况", response = boolean.class)
    public Boolean editSupport(@RequestBody @Validated List<SupportModel> models) throws OwnerException {
        return supportService.editSupport(models, getUserInfo());
    }

    @PostMapping("/deleteSupport")
    @SystemLog(description = "删除省市财政扶持情况", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除省市财政扶持情况", notes = "删除省市财政扶持情况")
    @PermissionRequired(perms = "customer:all:edit")
    public Boolean deleteSupport(@RequestBody SupportModel model) {
        return supportService.deleteSupport(model.getId());
    }

    @GetMapping("/getFinanceInfo")
    @ApiOperation(value = "获取单位财务信息", notes = "获取单位财务信息", response = String.class)
    public List<FinanceInfoModel> getFinanceInfo(int companyId, int currentYear) {
        return financialInfoService.getFinanceInfo(companyId, currentYear);
    }

    @PostMapping("/editFinancialInfo")
    @SystemLog(description = "编辑单位财务信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:edit")
    @ApiOperation(value = "编辑单位财务信息", notes = "编辑单位财务信息", response = boolean.class)
    public Boolean editFinancialInfo(@RequestBody @Validated List<FinanceInfoModel> models) throws OwnerException {
        return financialInfoService.editFinance(models, getUserInfo());
    }

    @GetMapping("/queryEmploymentInfo")
    @ApiOperation(value = "获取从业人员信息", notes = "获取从业人员信息", response = String.class)
    public EmploymentInfoModel queryEmploymentInfo(int companyId, int year) {
        return employmentInfoService.getEmploymentInfo(companyId, year);
    }

    @PostMapping("/editEmployment")
    @SystemLog(description = "编辑从业人员信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "customer:all:edit")
    @ApiOperation(value = "编辑从业人员信息", notes = "编辑从业人员信息", response = boolean.class)
    public Integer editEmployment(@RequestBody @Validated EmploymentInfoModel model) throws OwnerException {
        return employmentInfoService.editEmploymentInfo(model, getUserInfo());
    }

    /**
     * 上传文件
     *
     * @return
     * @throws OwnerException
     * @throws OwnerException
     */
    @PostMapping("/uploadBankInfo")
    @ApiOperation(value = "上传银行账户附件", notes = "上传银行账户附件", response = boolean.class)
    public Map<String, String> upload(@RequestParam("file") MultipartFile file, Integer companyId) throws OwnerException {
        String expertPath = msConfig.getUploadConfig().getRsBasicPath();
        Map<String, String> result = new LinkedHashMap<>();
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        String filePath = "/bankInfo/" + companyId + "/" + fileName;
        File tempFile = new File(expertPath + filePath);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(tempFile);
            result.put("filePath", filePath);
            result.put("fileName", file.getOriginalFilename());
        } catch (Exception ex) {
            throw new OwnerException("上传失败");
        }
        return result;
    }

    @GetMapping("/download")
    @ApiOperation(value = "下载附件", notes = "下载附件", response = boolean.class)
    public void download(Integer companyId) throws OwnerException, IOException {
        BankInfoModel model = bankInfoService.getBankInfo(companyId);
        String filePath = model.getFilePath();
        if (!StringUtils.isEmpty(filePath)) {
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            Path path = Paths.get(msConfig.getUploadConfig().getRsBasicPath(), filePath);
            String fullPath = path.toString();
            download(fullPath, fileName);
        } else {
            throw new OwnerException("下载失败");
        }
    }

}
