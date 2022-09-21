package com.yskc.rs.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.HtmlToPdfUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.hightech.HighTechFileEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightech.*;
import com.yskc.rs.models.project.ProjectInfoModel;
import com.yskc.rs.service.HighTechService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 8:20
 * @Description:高品
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/highTech")
public class HighTechController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HighTechService highTechService;
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private HtmlToPdfUtils htmlToPdfUtils;


    @GetMapping("/getList")
    @PermissionRequired(perms = "highTech:highTechIndex:search")
    @ApiOperation(value = "获取高品列表", notes = "获取高品列表")
    public List<HighTechModel> getList(QueryHighTechModel query) throws OwnerException {
        return highTechService.getList(getUserInfo().getCompanyId(), query);
    }


    @GetMapping("/getDescription")
    @PermissionRequired(perms = "highTech:highTechIndex:search")
    @ApiOperation(value = "获取高品描述", notes = "获取高品描述")
    public String getDescription(String hcode) throws OwnerException {
        return highTechService.getDescription(getUserInfo().getCompanyId(), hcode);
    }

    @SystemLog(description = "添加/更新高品", mode = SystemLog.SAVE_DB)
    @PostMapping("/save")
    @PermissionRequired(perms = "highTech:highTechIndex:save")
    @ApiOperation(value = "添加/更新高品", notes = "添加/更新高品")
    public boolean save(@RequestBody @Validated HighTechModel model) throws OwnerException {
        return highTechService.save(getUserInfo(), model);
    }

    @GetMapping("/getMaxCodeNum")
    @ApiOperation(value = "获取高品现有最大的代码", notes = "获取高品现存最大的代码")
    public Integer getMaxCodeNum(Integer year) throws OwnerException {
        return highTechService.getMaxCodeNum(getUserInfo().getCompanyId(), year);
    }

    @GetMapping("/getTechInfo")
    @PermissionRequired(perms = "highTech:highTechIndex:view")
    @ApiOperation(value = "获取高品详情", notes = "获取高品详情")
    public HighTechInfoModel getTechInfo(Integer highTechId, Integer year) throws OwnerException {
        return highTechService.getTechInfo(getUserInfo().getCompanyId(), highTechId, year);
    }

    @SystemLog(description = "添加/更新高品详情", mode = SystemLog.SAVE_DB)
    @PostMapping("/saveDetail")
    @PermissionRequired(perms = "highTech:highTechIndex:saveDetail")
    @ApiOperation(value = "添加/更新高品详情", notes = "添加/更新高品详情")
    public Integer saveDetail(@RequestBody @Validated HighTechInfoModel model) throws OwnerException {
        return highTechService.saveDetail(getUserInfo(), model);
    }

    @GetMapping("/getProjects")
    @ApiOperation(value = "获取高品可绑定的项目列表", notes = "获取高品可绑定的项目列表")
    public List<ProjectInfoModel> getProjects(Integer highTechId) throws OwnerException {
        return highTechService.getProjects(getUserInfo().getCompanyId(), highTechId);
    }

    @GetMapping("/verifyCodeAndName")
    @ApiOperation(value = "验证高品代码或名称唯一", notes = "获取高品可绑定的项目列表")
    public Boolean verifyCodeAndName(@RequestParam(value = "hname", required = false) String hname, @RequestParam(value = "hcode", required = false) String hcode, @RequestParam(value = "highTechId", required = false) Integer highTechId) throws OwnerException {
        return highTechService.verifyCodeAndName(getUserInfo().getCompanyId(), hname, hcode, highTechId);
    }

    /**
     * @param file
     * @return
     * @throws OwnerException
     */
    @PostMapping("/upload")
    @PermissionRequired(perms = "highTech:highTechIndex:upload")
    @SystemLog(description = "上传高品文件", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "上传高品文件", notes = "上传高品文件", response = String.class)
    public HighTechFileEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("highTechId") Integer highTechId, @RequestParam("type") Integer type) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String filePath = MessageFormat.format("/highTech/{0,number,#}/{1}/{2}", userInfo.getCompanyId(), type, System.currentTimeMillis() + file.getOriginalFilename());
        File tempFile = new File(rsConfig.getUploadConfig().getDocPath() + filePath);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(tempFile);
            HighTechFileEntity fileEntity = HighTechFileEntity.build(userInfo, highTechId, type, filePath, file.getOriginalFilename());
            highTechService.uploadFile(fileEntity);
            return fileEntity;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("上传失败");
        }
    }

    @SystemLog(description = "删除上传文件", mode = SystemLog.SAVE_DB)
    @PostMapping("/delTechFile")
    @PermissionRequired(perms = "highTech:highTechIndex:delFile")
    @ApiOperation(value = "删除上传文件", notes = "删除上传文件")
    public boolean delTechFile(@RequestBody @Validated HighTechFileModel model) throws OwnerException {
        return highTechService.delTechFile(model);
    }


    @PostMapping("/delHighTech")
    @ApiOperation(value = "删除高品", notes = "删除高品")
    @SystemLog(description = "删除高品", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "highTech:highTechIndex:del")
    public Boolean delHighTech(@RequestBody @Validated List<HighTechModel> models) throws OwnerException {
        return highTechService.delHighTech(models, getUserInfo().getCompanyId());
    }


    @GetMapping("/getHighTechSelect")
    @ApiOperation(value = "获取高品下拉列表", notes = "获取高品下拉列表")
    public List<HighTechSelectModel> getHighTechSelect(Integer year) throws OwnerException{
        return highTechService.getHighTechSelect(year,getUserInfo().getCompanyId());
    }

    @GetMapping("/exportHighTech")
    @PermissionRequired(perms = "highTech:highTechIndex:export")
    @ApiOperation(value = "高品详情导出pdf", notes = "高品详情导出pdf")
    public void exportHighTech(Integer highTechId, Integer year) throws Exception {
        UserInfo userInfo = getUserInfo();
        String logo = StringUtils.isEmpty(userInfo.getCompanyLogoPath()) ? null : Constant.IMAGES_DIR + userInfo.getCompanyLogoPath();
        Map<String, Object> dataMap = highTechService.getTechDataMap(getUserInfo().getCompanyId(), highTechId, year);
//        String tempPath = MessageFormat.format(Constant.HTML_TEMPLATE_DIR + "{0}.html", templateName);
//        dataMap.put("ftlDocFileName", "高新技术产品收入归集明细表");
//        OutputStream out = outGeneralFile(MessageFormat.format("{0}.pdf", tempInfo.getDocFileName()));
//        String logo = StringUtils.isEmpty(userInfo.getCompanyLogoPath()) ? null : Constant.IMAGES_DIR + userInfo.getCompanyLogoPath();
//        htmlToPdfUtils.htmlToPdf(tempPath, dataMap, userInfo.getCompanyName(), logo, out);
        String format = "{0}/highTech/{1,number,#}/{2,number,#}/{3}";
        File parentFile = new File(rsConfig.getUploadConfig().getDocPath() + "/highTech/" + highTechId + "/" + year);
        if (parentFile.exists()) {
            FileUtil.del(parentFile);
        }
        parentFile.mkdirs();
        String fileName = "高新技术产品收入归集明细表";
        dataMap.put("ftlDocFileName",fileName);
        dataMap.put("ftlPath", "/static/");
        try {
            String filePath = MessageFormat.format(format, rsConfig.getUploadConfig().getDocPath(), highTechId, year, fileName);
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            OutputStream out = new FileOutputStream(filePath+".pdf");
            String templateName = Constant.HTML_TEMPLATE_DIR + "HighTechDetail.html";
            htmlToPdfUtils.htmlToPdf(templateName, dataMap, userInfo.getCompanyName(), logo, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        List<HighTechFileModel> fileModels = highTechService.getUploadFiles(highTechId, userInfo.getCompanyId());
        if (!CollectionUtils.isEmpty(fileModels)) {
            for (HighTechFileModel model : fileModels) {
                File file = new File(MessageFormat.format("{0}/{1}", rsConfig.getUploadConfig().getDocPath(), model.getFilePath()));
                if (!file.exists()) {
                    continue;
                }
                String docName = model.getType()+"_"+model.getFileName();
                String filePath = MessageFormat.format(format, rsConfig.getUploadConfig().getDocPath(), highTechId, year, docName);
                File reportFile = new File(filePath);
                FileUtil.copy(file, reportFile, false);
            }
        }
        String sourceFile = MessageFormat.format("{0}/highTech/{1,number,#}/{2,number,#}", rsConfig.getUploadConfig().getDocPath(), highTechId, year);
        String outFileName = MessageFormat.format("{0}-{1}年高新产品收入明细.zip", userInfo.getCompanyName(), year);
        OutputStream out = outGeneralFile(outFileName);
        InputStream is = new FileInputStream(ZipUtil.zip(sourceFile));
        IoUtil.copy(is, out);
        is.close();
        out.flush();
        out.close();
    }
}
