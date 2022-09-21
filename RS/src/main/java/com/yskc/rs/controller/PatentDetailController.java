package com.yskc.rs.controller;

/**
 * Created by hck
 * on 2020/6/29 11:39
 * description:专利详情
 */

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.entity.project.PatentFileEntity;
import com.yskc.rs.models.Patent.PatentDetailModel;
import com.yskc.rs.models.Patent.QueryPatentDetialModel;
import com.yskc.rs.models.PatentPlan.PatentFileModel;
import com.yskc.rs.models.PatentPlan.RelatedProjectModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.PatentDetailExcel;
import com.yskc.rs.service.PatentDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "专利详情接口", value = "专利详情接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/patentDetail")
public class PatentDetailController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatentDetailService patentDetailService;
    @Autowired
    private RsConfig rsConfig;

    @SystemLog(description = "添加专利")
    @PermissionRequired(perms = "project:patent:list:add")
    @PostMapping("/addPatent")
    @ApiOperation(value = "添加专利 ", notes = "添加专利")
    public Boolean addPatent(@RequestBody PatentDetailModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentDetailService.addPatent(model, userInfo);
    }

    @GetMapping("/checkPatentSole")
    @ApiOperation(value = "检查专利号唯一 ", notes = "检查专利号唯一")
    public Boolean checkPatentSole(String patentNo) throws OwnerException {
        return patentDetailService.checkPatentSole(patentNo, getUserInfo());
    }

    @SystemLog(description = "专利关联项目")
    @PostMapping("/relatedProject")
    @ApiOperation(value = "专利关联项目 ", notes = "专利关联项目")
    @PermissionRequired(perms = "project:patent:list:relevance")
    public Boolean relatedProject(@RequestBody @Validated RelatedProjectModel model) throws OwnerException {
        return patentDetailService.relatedProject(model, getUserInfo());
    }

    @SystemLog(description = "修改专利")
    @PermissionRequired(perms = "project:patent:list:edit")
    @PostMapping("/editPatent")
    @ApiOperation(value = "修改专利 ", notes = "修改专利")
    public Boolean editPatent(@RequestBody PatentDetailModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentDetailService.updatePatent(model, userInfo);
    }

    @PermissionRequired(perms = "project:patent:list:search")
    @GetMapping("/getPatentList")
    @ApiOperation(value = "查询专利列表 ", notes = "查询专利列表")
    public PageModel<List<PatentDetailModel>> getPatentList(QueryPatentDetialModel query) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentDetailService.getPatentList(query, userInfo);
    }

    @SystemLog(description = "删除专利", mode = SystemLog.SAVE_DB)
    @PostMapping("/delPatent")
    @PermissionRequired(perms = "project:patent:list:del")
    @ApiOperation(value = "删除专利", notes = "删除专利", response = boolean.class)
    public Boolean delPatent(@RequestBody @Validated PatentDetailModel entity) throws OwnerException {
        return patentDetailService.delPatent(entity);
    }

    @SystemLog(description = "导入专利列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/importPatent")
    @PermissionRequired(perms = "project:patent:list:import")
    @ApiOperation(value = "导入专利列表", notes = "导入专利列表", response = String.class)
    public String importPatent(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<PatentDetailExcel> excelResult = excelService.getExcelResult(tempPath, file, PatentDetailExcel.class, tableField);
        return patentDetailService.importPatent(info, excelResult.getData());
    }

    @GetMapping("/getSpecification")
    @ApiOperation(value = "获取权利要求内容/说明书内容", notes = "获取权利要求内容/说明书内容")
    public Map<String, String> getSpecification(Integer id) throws OwnerException {
        return patentDetailService.getSpecification(id);
    }

    @GetMapping("/getPatentFiles")
    @ApiOperation(value = "获取专利文件列表 ", notes = "获取专利文件列表")
    public Map<Integer, List<PatentFileModel>> getPatentFiles(@RequestParam(value = "patentNo", required = true) String patentNo) throws OwnerException {
        return patentDetailService.getPatentFiles(patentNo);
    }

    @SystemLog(description = "上传专利文件", mode = SystemLog.SAVE_DB)
    @PostMapping("/uploadPatentFile")
    @ApiOperation(value = "上传专利文件", notes = "上传专利文件", response = boolean.class)
    public Map<String, String> uploadPatentFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("fileType") Integer fileType,
                                                @RequestParam(value = "patentNo", required = true) String patentNo) throws OwnerException {
        if (StringUtils.isEmpty(patentNo)) {
            throw new OwnerException("获取专利失败，请稍后再试");
        }
        UserInfo userInfo = getUserInfo();
        String expertPath = rsConfig.getUploadConfig().getDocPath();
        Map<String, String> result = new LinkedHashMap<>();
        String filePath = MessageFormat.format("/patent/{0}/{1,number,#}{2}", userInfo.getCompanyId(), System.currentTimeMillis(), file.getOriginalFilename());
        File tempFile = new File(expertPath + filePath);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(tempFile);
            Integer id = patentDetailService.addFile(fileType, filePath, file.getOriginalFilename(), userInfo, patentNo);
            result.put("filePath", filePath);
            result.put("fileName", file.getOriginalFilename());
            result.put("id", id.toString());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("上传失败");
        }
        return result;
    }

    @SystemLog(description = "删除专利附件列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/delPatentFile")
    @ApiOperation(value = "删除专利附件列表", notes = "删除专利附件列表", response = boolean.class)
    public Boolean delPatentFile(@RequestBody PatentFileEntity patentFile) {
        return patentDetailService.delPatentFile(patentFile);
    }

    @SystemLog(description = "下载专利文件", mode = SystemLog.SAVE_DB)
    @GetMapping("/downloadPatentFile")
    @ApiOperation(notes = "下载专利文件", value = "下载专利文件")
    public void downloadPatentFile(Integer id) throws OwnerException, IOException {
        PatentFileEntity patentFile = patentDetailService.getPatentFile(id);
        if (patentFile == null) {
            throw new OwnerException("文件已删除或不存在，下载失败！");
        }
        download(rsConfig.getUploadConfig().getDocPath() + patentFile.getFilePath(), patentFile.getFileName());
    }

    @PostMapping("/batchUpdate")
    public String batchUpdate(@RequestBody List<PatentDetailExcel> patentList) throws OwnerException {
        UserInfo userInfo = this.getUserInfo();
        return  patentDetailService.importPatent(userInfo,patentList);
    }

}
