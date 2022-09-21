package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.RsPatentPlanEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentPlan.ChangePatentNoModel;
import com.yskc.ms.models.patentPlan.PlanToPatentModel;
import com.yskc.ms.models.patentPlan.QueryPatentPlanModel;
import com.yskc.ms.models.patentPlan.RsPatentPlanModel;
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
import java.util.*;

/**
 * Created by hck
 * on 2020/7/7 8:21
 * description:专利立项接口
 */
@Api(tags = "专利立项类接口", value = "专利立项类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rsPatentPlan")
public class RsPatentPlanController extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RsPatentPlanService rsPatentPlanService;
    @Autowired
    private MsConfig msConfig;


    @PermissionRequired(perms = "patent:applyList:search")
    @GetMapping("/queryPatentPlan")
    @ApiOperation(value = "查询专利立项列表 ", notes = "查询专利立项列表")
    public PageModel<List<RsPatentPlanModel>> queryPatentPlan(QueryPatentPlanModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return rsPatentPlanService.queryPatentPlans(model, userInfo, getDataPerm());
    }

    @SystemLog(description = "分配负责人", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:applyList:allocation")
    @GetMapping("/allocationMaster")
    @ApiOperation(value = "分配负责人 ", notes = "分配负责人")
    public Boolean allocationMaster(@RequestParam(value = "ids") List<Integer> ids, @RequestParam(value = "masterId") Integer masterId) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        rsPatentPlanService.judgeAuditPass(ids);
        return rsPatentPlanService.allocationMaster(ids, userInfo, masterId);
    }

    @SystemLog(description = "更新申请状态为已完成", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:applyList:audit,patent:applyList:actiovePatent")
    @PostMapping("/updateStatus")
    @ApiOperation(value = "更新申请状态 ", notes = "更新申请状态")
    public Boolean updateStatus(@RequestBody @Validated PlanToPatentModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        rsPatentPlanService.judgeAuditPass(Arrays.asList(model.getId()));
        return rsPatentPlanService.updateStatus(model, userInfo);
    }

    /**
     * @param id
     * @param path
     * @param fileName
     * @throws OwnerException
     * @throws IOException
     */
    @SystemLog(description = "下载专利交底书/申请资料", mode = SystemLog.SAVE_DB)
    @GetMapping("/download")
    @ApiOperation(notes = "下载专利交底书/申请资料", value = "下载专利交底书/申请资料")
    @PermissionRequired(perms = "patent:applyList:download")
    public void download(@RequestParam(value = "id", required = false) Integer id, String path, String fileName, Integer type, @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException, IOException {
        RsPatentPlanEntity patent = rsPatentPlanService.getById(id, patentNo);
        if (patent == null) {
            throw new OwnerException("不是申请专利,下载失败！");
        }
        String filePath;
        if (2 == type) {
            filePath = patent.getFilepath();
        } else if (1 == type) {
            filePath = patent.getDisclosureParperPath();
        } else {
            filePath = patent.getInventorInfo();
        }
        if (StringUtils.isEmpty(filePath)) {
            throw new OwnerException("文件不存在，下载失败。");
        }
        String loadFileName = fileName;
        if (StringUtils.isEmpty(fileName)) {
            loadFileName = path.substring(path.lastIndexOf("/") + 14);
        }
        if (filePath.contains(path)) {
            download(msConfig.getUploadConfig().getDocPath() + path, loadFileName);
        } else {
            throw new OwnerException("下载失败，请稍后再试。");
        }
    }

    @SystemLog(description = "申请专利驳回", mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms = "patent:applyList:audit")
    @PostMapping("/rejectPatent")
    @ApiOperation(value = "申请专利驳回 ", notes = "申请专利驳回")
    public Boolean rejectPatent(@RequestBody @Validated List<RsPatentPlanModel> models) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return rsPatentPlanService.rejectPatent(models, userInfo);
    }

    @SystemLog(description = "修改专利号", mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms = "patent:applyList:audit")
    @PostMapping("/updatePatentNo")
    @ApiOperation(value = "修改专利号 ", notes = "修改专利号")
    public Boolean updatePatentNo(@RequestBody @Validated ChangePatentNoModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return rsPatentPlanService.updatePatentNo(model, userInfo);
    }


    @SystemLog(description = "上传专利申请资料", mode = SystemLog.SAVE_DB)
    @PostMapping("/upload")
    @ApiOperation(value = "上传专利申请资料", notes = "上传专利申请资料", response = boolean.class)
    public Map<String, String> upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "patentId", required = false) Integer patentId, @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        RsPatentPlanEntity patent = rsPatentPlanService.getById(patentId, patentNo);
        if (patent == null) {
            throw new OwnerException("不是申请专利,不能上传申请资料！");
        }
        rsPatentPlanService.judgeAuditPass(Arrays.asList(patent.getId()));
        String expertPath = msConfig.getUploadConfig().getDocPath();
        Map<String, String> result = new LinkedHashMap<>();
        String filePath = MessageFormat.format("/patent/{0}/{1,number,#}{2}", patent.getCompanyId(), System.currentTimeMillis(), file.getOriginalFilename());
        File tempFile = new File(expertPath + filePath);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(tempFile);
            String newFilePath;
            if (StringUtils.isEmpty(patent.getFilepath())) {
                newFilePath = filePath;
            } else {
                newFilePath = patent.getFilepath() + "," + filePath;
            }
            patent.setFilepath(newFilePath);
            patent.setLastUpdateTime(new Date());
            patent.setLastUpdatorId(-1);
            patent.setMsLastUpdatorId(userInfo.getId());
            rsPatentPlanService.update(patent);
            result.put("filePaths", newFilePath);
            result.put("filePath", filePath);
            result.put("fileName", file.getOriginalFilename());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("上传失败");
        }
        return result;
    }

    @SystemLog(description = "删除专利申请资料", mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms = "patent:applyList:audit")
    @PostMapping("/delUploadFile")
    @ApiOperation(value = "删除专利申请资料 ", notes = "删除专利申请资料")
    public Boolean delUploadFile(@RequestBody @Validated RsPatentPlanModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return rsPatentPlanService.updateFilePath(model, getUserInfo());
    }


    // @PermissionRequired(perms = "patent:applyList:search")
    @GetMapping("/getPatentInfo")
    @ApiOperation(value = "查询专利申请信息 ", notes = "查询专利申请信息")
    public RsPatentPlanModel getPatentInfo(Integer patentPlanId) {
        return rsPatentPlanService.getPatentInfo(patentPlanId);
    }

    @SystemLog(description = "设置定稿名称", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "patent:applyList:setFinalizedName")
    @PostMapping("/setName")
    @ApiOperation(value = "设置定稿名称 ", notes = "设置定稿名称")
    public Boolean setName(@RequestBody @Validated RsPatentPlanModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return rsPatentPlanService.setName(model, getUserInfo());
    }

}
