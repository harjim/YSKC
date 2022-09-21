package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.accounttitle.AccountTitleModel;
import com.yskc.rs.models.accounttitle.AccountTree;
import com.yskc.rs.models.excel.AccountExcel;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.service.AccountTitleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-11-23 09:34:08
 * @Description: 科目类接口
 */
@Api(tags = "科目类接口", value = "科目类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/accountTitle")
public class AccountTitleController extends BaseController {

    @Autowired
    private AccountTitleService accountTitleService;

    @Autowired
    private RsConfig rsConfig;

    /**
     * 获取原科目信息
     * @param accountName
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAccountTitleList")
    @PermissionRequired(perms = "company:account:search")
    @ApiOperation(value = "获取原科目信息", notes = "获取原科目信息")
    public List<AccountTitleModel> queryAccountTitleList(String accountName) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return accountTitleService.queryAccountTitle(userInfo.getCompanyId(), accountName);
    }

    /**
     * 添加原科目信息
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/addAccountTitle")
    @PermissionRequired(perms = "company:account:add")
    @ApiOperation(value = "添加原科目信息", notes = "添加原科目信息")
    @SystemLog(description = "添加原科目信息", mode = SystemLog.SAVE_DB)
    public boolean addAccountTitle(@RequestBody @Validated AccountTitleModel model) throws OwnerException {
        return accountTitleService.addAccountTitle(getUserInfo(), model);
    }

    /**
     * 删除原科目信息
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/delAccountTitle")
    @SystemLog(description = "删除原科目信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "company:account:del")
    @ApiOperation(value = "删除原科目信息", notes = "删除原科目信息")
    public boolean delAccountTitle(@RequestBody @Validated AccountTitleModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return accountTitleService.delAccountTitle(userInfo, model);
    }

    /**
     * 更新原科目信息
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/updateAccountTitle")
    @SystemLog(description = "更新原科目信息", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "company:account:edit")
    @ApiOperation(value = "更新原科目信息", notes = "更新原科目信息", response = boolean.class)
    public boolean updateAccountTitle(@RequestBody @Validated AccountTitleModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return accountTitleService.updateAccountTitle(userInfo, model);
    }

    /**
     * 科目树
     * @return
     * @throws OwnerException
     */
    @GetMapping("/accountTrees")
    @ApiOperation(value = "科目树", notes = "科目树")
    public List<AccountTree> accountTrees() throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return AccountTree.getTree(accountTitleService.queryAllAccountTitle(userInfo.getCompanyId()));
    }

    /**
     *
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @PostMapping("/importAccount")
    @SystemLog(description = "导入科目", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入科目", notes = "导入科目")
    @PermissionRequired(perms = "company:account:import")
    public String importAccount(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<AccountExcel> excelResult = excelService.getExcelResult(tempPath, file, AccountExcel.class, tableField);
        String error = accountTitleService.importAccount(userInfo, excelResult.getData());
        return excelResult.msg + error;
    }
}
