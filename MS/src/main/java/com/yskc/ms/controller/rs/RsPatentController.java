package com.yskc.ms.controller.rs;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.enums.PatentActionEnum;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.*;
import com.yskc.ms.service.rs.RsPatentService;
import com.yskc.ms.utils.PatentExecutor;
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
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/7/1 8:28
 * description:专利列表接口
 */
@Api(tags = "专利列表类接口", value = "专利列表类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rsPatent")
public class RsPatentController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsPatentService rsPatentService;

    @Autowired
    private PatentExecutor patentExecutor;

    @Autowired
    private MsConfig msConfig;

    @GetMapping("/queryPatentList")
    @PermissionRequired(perms = "Patent:PatentList:search")
    @ApiOperation(value = "获取所有专利列表", notes = "获取所有专利列表")
    public PageModel<List<PatentModel>> queryPatentList(QueryPatentModel model) throws OwnerException {
        Date startTime;
        Date endTime;
        Date startDate;
        Date entDate;
        if (!StringUtils.isEmpty(model.getApplyDateStartTime())) {
            startTime = DateUtil.parse(model.getApplyDateStartTime());
            startTime = DateUtil.beginOfDay(startTime);
            model.setStartTime(startTime);
        }
        if (!StringUtils.isEmpty(model.getApplyDateEndTime())) {
            endTime = DateUtil.parse(model.getApplyDateEndTime());
            endTime = DateUtil.endOfDay(endTime);
            model.setEntTime(endTime);
        }
        if (!StringUtils.isEmpty(model.getStartExpiryDate())) {
            startDate = DateUtil.parse(model.getStartExpiryDate());
            startDate = DateUtil.beginOfDay(startDate);
            model.setStartDate(startDate);
        }
        if (!StringUtils.isEmpty(model.getEndExpiryDate())) {
            entDate = DateUtil.parse(model.getEndExpiryDate());
            entDate = DateUtil.beginOfDay(entDate);
            model.setEntDate(entDate);
        }
        return rsPatentService.queryPatentList(model);
    }

    @PostMapping("/updatePatentCustomerId")
    @PermissionRequired(perms = "Patent:PatentList:edit")
    @ApiOperation(value = "专利关联客户", notes = "专利关联客户", response = boolean.class)
    public boolean updatePatentCustomerId(@RequestBody @Validated PatentModel model) throws OwnerException {
        return rsPatentService.updatePatentCustomerId(model, getUserInfo());
    }

    @PostMapping("/update")
    @PermissionRequired(perms = "Patent:PatentList:edit")
    @ApiOperation(value = "修改专利类型", notes = "修改专利类型", response = boolean.class)
    public boolean update(@RequestBody @Validated PatentModel model) throws OwnerException {
        return rsPatentService.updatePatent(getUserInfo(), model);
    }

    @GetMapping("/checkPatentNo")
    @ApiOperation(value = "检查专利号是否重复", notes = "检查专利号是否重复")
    public Boolean checkPatentNo(String patentNo) throws OwnerException {
        return rsPatentService.checkPatentNo(patentNo);
    }

    @PostMapping("/addPatent")
    @PermissionRequired(perms = "Patent:PatentList:add")
    @ApiOperation(value = "添加专利", notes = "添加专利", response = boolean.class)
    public boolean addPatent(@RequestBody @Validated PatentModel model) throws OwnerException {
        return rsPatentService.addPatent(model, getUserInfo());
    }

    @PostMapping("/delPatent")
    @PermissionRequired(perms = "Patent:PatentList:del")
    @ApiOperation(value = "删除", notes = "删除", response = boolean.class)
    public boolean delPatent(@RequestBody @Validated PatentModel model) throws OwnerException {
        return rsPatentService.delete(model);
    }

    @GetMapping("/getDataByPatentNo")
    @ApiOperation(value = "获取申请人信息", notes = "获取申请人信息")
    public List<RsPatentApplyModel> getDataByPatentNo(String patentNo) throws OwnerException {
        return rsPatentService.getDataByPatentNo(patentNo);
    }

    @PostMapping("/importPatent")
    @PermissionRequired(perms = "Patent:PatentList:import")
    @ApiOperation(value = "导入专利号", notes = "导入专利号", response = String.class)
    public boolean importPatent(@RequestParam("file") MultipartFile file) throws OwnerException {
        UserInfo info = getUserInfo();
        if (file.isEmpty()) {
            throw new OwnerException("文件没有上传成功，请重新上传");
        }
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                msConfig.getUploadConfig().getImportPath());
        String tempFileName = file.getOriginalFilename().replace(".xls", System.currentTimeMillis() + ".xls");
        File tempFile = new File(new File(tempPath).getAbsolutePath() + "/" + tempFileName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(tempFile);
        } catch (Exception ex) {
            logger.error("importPatent", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        ExcelReader reader = ExcelUtil.getReader(tempFile);
        List<Map<String, Object>> readAll = reader.readAll();
        return rsPatentService.importPatent(info, readAll);
    }

    @PostMapping("/validHeart")
    @ApiOperation(value = "验证心跳", notes = "验证心跳")
    public Boolean validHeart(@RequestBody PatentValidHeartModel model) throws OwnerException {
        return patentExecutor.validHeart(model, getUserInfo());
    }

    @GetMapping("/validData")
    @ApiOperation(value = "获取专利网站登录验证", notes = "获取专利网站登录验证")
    public Map validData() throws OwnerException {
        try {
            return (Map) patentExecutor.executor(PatentActionEnum.GET_VALID);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new OwnerException("获取验证信息失败");
        }
    }

    @PostMapping("/validPosition")
    @ApiOperation(value = "专利网站验证", notes = "专利网站验证")
    public Boolean validPosition(@RequestBody PatentValidParam params) throws Exception {
        return (Boolean) patentExecutor.executor(PatentActionEnum.SET_VALID, params, getUserInfo());
    }

    @GetMapping("/exitPatent")
    @ApiOperation(value = "退出专利网站", notes = "退出专利网站")
    public Boolean exitPatent() {
        return patentExecutor.exit();
    }

    @GetMapping("/stopPatent")
    @ApiOperation(value = "终止同步专利", notes = "终止同步专利")
    public PatentSyncModel stopPatent() {
        return patentExecutor.stop();
    }

    @GetMapping("/suspendPatent")
    @ApiOperation(value = "暂停同步专利", notes = "暂停同步专利")
    public PatentSyncModel suspendPatent() {
        return patentExecutor.suspend();
    }

//    @GetMapping("/beginSync")
//    @ApiOperation(value = "开始同步专利", notes = "开始同步专利")
//    public Boolean beginSync() {
//        return patentExecutor.beginSync();
//    }

    @GetMapping("/getSyncCount")
    @ApiOperation(value = "获取同步个数", notes = "获取同步个数")
    public PatentSyncModel getSyncCount() {
        return patentExecutor.getSyncCountModel();
    }

    @GetMapping("/getSpecification")
    @ApiOperation(value = "获取权利要求内容/说明书内容", notes = "获取权利要求内容/说明书内容")
    public PatentSpecificationModel getSpecification(Integer id) throws OwnerException {
        return rsPatentService.getSpecification(id);
    }
}
