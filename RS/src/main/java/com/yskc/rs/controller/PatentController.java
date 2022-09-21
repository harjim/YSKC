package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.PatentEntity;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.Patent.PatentModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.PatentExcel;
import com.yskc.rs.service.PatentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * 专利类接口
 *
 * @author huronghua
 */
@Api(tags = "专利类接口", value = "专利类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/patent")
public class PatentController extends BaseController {

    @Autowired
    private PatentService patentService;
    @Autowired
    private RsConfig rsConfig;

    /**
     * 查询专利
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryPatent")
    @ApiOperation(value = "查询专利", notes = "查询专利", response = String.class)
    public PageModel<List<PatentModel>> queryPatent(PatentModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        model.setCompanyId(userInfo.getCompanyId());
        return patentService.queryPatent(model);
    }

    /**
     * 添加专利
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "添加专利", mode = SystemLog.SAVE_DB)
    @PostMapping("/addPatent")
    @ApiOperation(value = "添加专利", notes = "添加专利", response = boolean.class)
    public boolean addPatent(@RequestBody @Validated PatentEntity entity) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentService.addPatent(userInfo, entity);
    }

    /**
     * 编辑专利
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "编辑专利", mode = SystemLog.SAVE_DB)
    @PostMapping("/editPatent")
    @ApiOperation(value = "编辑专利", notes = "编辑专利", response = boolean.class)
    public boolean editPatent(@RequestBody @Validated PatentEntity entity) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentService.editPatent(userInfo, entity);
    }

    @GetMapping("/checkPatentNo")
    @ApiOperation(value = "检查重复项目名", notes = "检查重复项目名")
    public boolean checkPatentNo(String patentNo, Integer projectId, Integer pid) {
        return patentService.checkPatentNo(patentNo, projectId, pid);
    }

    @SystemLog(description = "删除专利", mode = SystemLog.SAVE_DB)
    @PostMapping("/delPatent")
    @ApiOperation(value = "删除专利", notes = "删除专利", response = boolean.class)
    public boolean delPatent(@RequestBody @Validated PatentEntity entity) {
        return patentService.delPatent(entity);
    }

    @SystemLog(description = "导入专利列表", mode = SystemLog.SAVE_DB)
    @PostMapping("/importPatent")
    @ApiOperation(value = "导入专利列表", notes = "导入专利列表", response = String.class)
    public String importPatent(@RequestParam("file") MultipartFile file, Integer projectId, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<PatentExcel> excelResult = excelService.getExcelResult(tempPath, file, PatentExcel.class, tableField);
        return patentService.importPatent(info, excelResult.getData(), projectId);
    }

}
