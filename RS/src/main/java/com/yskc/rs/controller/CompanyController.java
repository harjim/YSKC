package com.yskc.rs.controller;

import cn.hutool.core.io.IoUtil;
import com.yskc.common.annotation.NotLoginRequired;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.AppCompanyModel;
import com.yskc.rs.models.company.CompanyNameHistoryModel;
import com.yskc.rs.models.company.CountDataModel;
import com.yskc.rs.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

/**
 * 公司类接口
 *
 * @author huronghua
 */
@Api(tags = "公司类接口", value = "公司类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/company")
public class CompanyController extends BaseController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private RsConfig rsConfig;

    /**
     * 保存公司信息
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/save")
    @PermissionRequired(perms = "company:info:base:save")
    @SystemLog(description = "保存公司信息", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "保存公司信息", notes = "保存公司信息", response = boolean.class)
    public boolean saveCompany(@RequestBody @Validated AppCompanyModel model) throws Exception {
        UserInfo userInfo = getUserInfo();
        return companyService.saveCompany(userInfo, model, getToken());
    }


    @GetMapping("/getCompanyHistoryName")
    @PermissionRequired(perms = "company:info:base:updateName")
    @ApiOperation(value = "获取公司历史名称", notes = "获取公司历史名称", response = String.class)
    public List< CompanyNameHistoryModel > updateCompanyName() throws Exception {
        UserInfo userInfo = getUserInfo();
        return companyService.getCompanyHistoryName(userInfo.getCompanyId());
    }

    @PostMapping("/delCompanyHistoryName")
    @PermissionRequired(perms = "company:info:base:updateName")
    @ApiOperation(value = "删除公司历史对应名称", notes = "删除公司历史对应名称", response = String.class)
    public CompanyNameHistoryModel delCompanyHistoryName(@RequestBody CompanyNameHistoryModel nameHistoryModel) throws Exception {
        UserInfo userInfo = getUserInfo();
        return companyService.delCompanyHistoryName(nameHistoryModel.getId(), userInfo);
    }

    @PostMapping("/saveCompanyHistoryName")
    @PermissionRequired(perms = "company:info:base:updateName")
    @ApiOperation(value = "保存公司历史名称", notes = "保存公司历史名称", response = String.class)
    public CompanyNameHistoryModel saveCompanyHistoryName(@RequestBody @Validated CompanyNameHistoryModel nameChangeModel) throws Exception {
        UserInfo userInfo = getUserInfo();
        return companyService.saveCompanyName(nameChangeModel, userInfo);
    }

    /**
     * 查询公司信息
     *
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryAll")
    @PermissionRequired(perms = "company:info:base:view")
    @ApiOperation(value = "查询公司信息", notes = "查询公司信息", response = String.class)
    public AppCompanyModel queryAll() throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return companyService.queryAll(userInfo.getCompanyId());
    }

    /**
     * 导入文件
     *
     * @param file
     * @return
     * @throws IOException
     * @throws OwnerException
     */
    @PostMapping("/import")
    @ApiOperation(value = "导入文件", notes = "导入文件", response = boolean.class)
    public String importFile(@RequestParam("avatar") MultipartFile file, HttpServletRequest request) throws IOException, OwnerException {
        UserInfo userInfo = getUserInfo();
        String filePath = "/" + userInfo.getCompanyId() + "/logo/";
        return setImportValue(file, filePath);

    }

    /**
     * 导入营业执照
     *
     * @param file
     * @return
     * @throws IOException
     * @throws OwnerException
     */
    @PostMapping("/importBusinessLicense")
    @SystemLog(description = "导入营业执照", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入营业执照", notes = "导入营业执照", response = boolean.class)
    public String importBusinessLicense(@RequestParam("avatar") MultipartFile file, HttpServletRequest request) throws IOException, OwnerException {
        UserInfo userInfo = getUserInfo();
        String filePath = "/" + userInfo.getCompanyId() + "/businessLicense/";
        return setImportValue(file, filePath);
    }

    /**
     * @param file
     * @param filePath
     * @return
     * @throws IOException
     */
    public String setImportValue(MultipartFile file, String filePath) throws IOException {
        InputStream in = file.getInputStream();
        String basicPath = rsConfig.getUploadConfig().getResourcePath() + Constant.IMAGES_DIR;
        File businessLicenseFile = new File(basicPath, filePath);
        if (!businessLicenseFile.exists() && !businessLicenseFile.isDirectory()) {
            businessLicenseFile.mkdirs();
        }
        String fileName = filePath + System.currentTimeMillis() + file.getOriginalFilename();
        OutputStream out = new FileOutputStream(basicPath + fileName);
        IoUtil.copy(in, out);
        out.flush();
        out.close();
        in.close();
        return fileName;
    }

    @NotLoginRequired
    @SystemLog(description = "注册检查用户名是否重复")
    @GetMapping("/registerCheckCompany")
    @ApiOperation(value = "注册检查用户名是否重复", notes = "注册检查用户名是否重复")
    public Integer registerCheckCompany(String companyName) throws OwnerException {
        return companyService.registerCheckCompany(companyName);
    }

    @GetMapping("/getCountData")
    //@PermissionRequired(perms = "company:info:base:view")
    @ApiOperation(value = "获取公司首页统计数据", notes = "获取公司首页统计数据", response = String.class)
    public CountDataModel getCountData(Integer year) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return companyService.getCountData(userInfo.getCompanyId(),year);
    }

    @GetMapping("/getCompanyFinished")
    @ApiOperation(value = "获取公司注册信息是否完善", notes = "获取公司注册信息是否完善")
    public Boolean getCompanyFinished() throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return companyService.getCompanyFinished(userInfo.getCompanyId());
    }
}
