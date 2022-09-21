package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.PatentFileEntity;
import com.yskc.ms.entity.rs.RsPatentEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentPlan.PatentFileModel;
import com.yskc.ms.models.patentPlan.PatentOpinionModel;
import com.yskc.ms.models.patentPlan.PatentPlanModel;
import com.yskc.ms.service.ms.PatentPlanService;
import com.yskc.ms.service.rs.PatentFileService;
import com.yskc.ms.service.rs.RsPatentPlanService;
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

/**
 * @Author: hck
 * @DateTime: 2021/7/2 16:14
 * @Description:专利文件类接口
 */
@Api(tags = "专利文件类接口", value = "专利文件类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/PatentFile")
public class PatentFileController extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RsPatentPlanService rsPatentPlanService;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private PatentFileService patentFileService;
    @Autowired
    private PatentPlanService patentPlanService;

    //@PermissionRequired(perms = "patent:applyList:search")
    @GetMapping("/getPatentFiles")
    @ApiOperation(value = "获取专利文件列表 ", notes = "获取专利文件列表")
    public Map<Integer, Object> getPatentFiles(@RequestParam(value = "patentId", required = false) Integer patentId, @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentFileService.getPatentFiles(patentId, patentNo);
    }

    @GetMapping("/getOpinions")
    @ApiOperation(value = "获取专利文件意见列表 ", notes = "获取专利文件意见列表")
    public List<PatentOpinionModel> getOpinions(@RequestParam(value = "patentId", required = false) Integer patentId, @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException {
        return patentFileService.getOpinions(patentId, patentNo);
    }

    @SystemLog(description = "上传专利文件", mode = SystemLog.SAVE_DB)
    @PostMapping("/uploadPatentFile")
    @ApiOperation(value = "上传专利文件", notes = "上传专利文件", response = boolean.class)
    public Map<String, String> uploadPatentFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam(value = "patentId",required = false) Integer patentId,
                                                @RequestParam("fileType") Integer fileType,
                                                @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException {
        if (StringUtils.isEmpty(patentNo) && null == patentId) {
            throw new OwnerException("请选择要上传文件的专利！");
        }
        UserInfo userInfo = getUserInfo();
        /*if (patentId != null) {
            rsPatentPlanService.judgeAuditPass(Arrays.asList(patentId));
        }*/
        String expertPath = msConfig.getUploadConfig().getDocPath();
        Map<String, String> result = new LinkedHashMap<>();
        PatentPlanModel patent =patentPlanService.getById(patentId);
        Integer companyId;
        if (patent != null) {
            companyId = patent.getCompanyId();
        } else {
            RsPatentEntity patentEntity = rsPatentPlanService.getByPatentNo(patentNo);
            if (null == patentEntity) {
                throw new OwnerException("专利已删除或不存在,上传失败！");
            }
            companyId = patentEntity.getCompanyId();
        }
        String filePath = MessageFormat.format("/patent/{0}/{1,number,#}{2}", companyId, System.currentTimeMillis(), file.getOriginalFilename());
        File tempFile = new File(expertPath + filePath);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(tempFile);
            Integer id = patentFileService.addFile(patentId, fileType, filePath, file.getOriginalFilename(), userInfo, patentNo);
            result.put("filePath", filePath);
            result.put("fileName", file.getOriginalFilename());
            result.put("id", id.toString());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("上传失败");
        }
        return result;
    }

    @SystemLog(description = "删除专利文件", mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms = "patent:applyList:audit")
    @PostMapping("/delPatentFile")
    @ApiOperation(value = "删除专利文件 ", notes = "删除专利文件")
    public Boolean delPatentFile(@RequestBody @Validated PatentFileModel model) throws OwnerException {
        return patentFileService.delPatentFile(model.getId());
    }

    @SystemLog(description = "下载专利文件", mode = SystemLog.SAVE_DB)
    @GetMapping("/download")
    @ApiOperation(notes = "下载专利文件", value = "下载专利文件")
//    @PermissionRequired(perms = "patent:applyList:download")
    public void download(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException, IOException {
        PatentFileEntity patentFile = patentFileService.getById(id, patentNo);
        if (patentFile == null) {
            throw new OwnerException("文件已删除或不存在，下载失败！");
        }
        download(msConfig.getUploadConfig().getDocPath() + patentFile.getFilepath(), patentFile.getFileName());
    }
}
