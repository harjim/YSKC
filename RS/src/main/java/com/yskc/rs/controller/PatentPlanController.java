package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.PatentPlanEntity;
import com.yskc.rs.models.PatentPlan.AddPatentPlanModel;
import com.yskc.rs.models.PatentPlan.PatentOpinionModel;
import com.yskc.rs.models.PatentPlan.PatentPlanModel;
import com.yskc.rs.models.PatentPlan.QueryPatentPlanModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.DocumentService;
import com.yskc.rs.service.PatentPlanService;
import com.yskc.rs.service.PatentService;
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
 * Created by hck
 * on 2020/7/6 10:49
 * description:专利立项接口
 */

@Api(tags = "专利立项接口", value = "专利立项接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/patentPlan")
public class PatentPlanController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PatentPlanService patentPlanService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private PatentService patentService;

    @Autowired
    private RsConfig rsConfig;

    @PermissionRequired(perms = "project:patentPlan:search")
    @GetMapping("/queryPatentPlan")
    @ApiOperation(value = "查询专利立项 ", notes = "查询专利立项")
    public PageModel<List<PatentPlanModel>> queryPatentPlan(QueryPatentPlanModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentPlanService.queryPatentPlan(model, userInfo);
    }


    @SystemLog(description = "添加专利立项", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:patentPlan:add")
    @PostMapping("/addPatentPlan")
    @ApiOperation(value = "添加专利立项 ", notes = "添加专利立项")
    public Boolean addPatentPlan(@RequestBody @Validated AddPatentPlanModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentPlanService.addPatentPlan(model, userInfo);
    }

    @SystemLog(description = "删除专利立项", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:patentPlan:del")
    @PostMapping("/delPatentPlan")
    @ApiOperation(value = "删除专利立项 ", notes = "删除专利立项")
    public Boolean delPatentPlan(@RequestBody @Validated PatentPlanModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentPlanService.delPatentPlan(model, userInfo);
    }

    @SystemLog(description = "编辑专利立项", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:patentPlan:edit")
    @PostMapping("/editPatentPlan")
    @ApiOperation(value = "编辑专利立项 ", notes = "编辑专利立项")
    public Boolean editPatentPlan(@RequestBody @Validated AddPatentPlanModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentPlanService.editPatentPlan(model, userInfo);
    }

    /**
     * 上传交底书
     *
     * @return
     * @throws OwnerException
     * @throws OwnerException
     */
    @SystemLog(description = "上传交底书", mode = SystemLog.SAVE_DB)
    @PostMapping("/upload")
    @ApiOperation(value = "上传交底书", notes = "上传交底书", response = boolean.class)
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) throws OwnerException {
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
            result.put("filePath", filePath);
            result.put("fileName", file.getOriginalFilename());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new OwnerException("上传失败");
        }
        return result;
    }

    /**
     * 下载交底书/申请资料
     *
     * @param id
     * @param path
     * @param fileName
     * @param type     1:下载交底书 2：下载申请资料
     * @throws OwnerException
     * @throws IOException
     */
    @SystemLog(description = "下载交底书/申请资料", mode = SystemLog.SAVE_DB)
    @GetMapping("/download")
    @ApiOperation(notes = "下载交底书/申请资料", value = "下载交底书/申请资料")
    @PermissionRequired(perms = "project:patentPlan:download")
    public void download(@RequestParam(value = "id", required = false) Integer id, String path,
                         String fileName, Integer type, @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException, IOException {
        String filePath;
        PatentPlanEntity entity = patentPlanService.getPatent(id, patentNo);
        if (null == entity) {
            throw new OwnerException("未获取到文件，下载失败！");
        }
        if (2 == type) {
            filePath = entity.getFilepath();
        } else if (1 == type) {
            filePath = entity.getDisclosureParperPath();
        } else {
            filePath = entity.getInventorInfo();
        }
        if (StringUtils.isEmpty(filePath)) {
            throw new OwnerException("文件不存在，下载失败。");
        }
        String loadFileName = fileName;
        if (StringUtils.isEmpty(fileName)) {
            loadFileName = path.substring(path.lastIndexOf("/") + 14);
        }
        if (filePath.contains(path)) {
            download(rsConfig.getUploadConfig().getDocPath() + path, loadFileName);
        } else {
            throw new OwnerException("下载失败，请稍后再试。");
        }
    }

    @PostMapping("/submitPatents")
    @ApiOperation(value = "提交专利交底书审核", notes = "提交专利交底书审核")
    @PermissionRequired(perms = "project:patentPlan:submit")
    @SystemLog(description = "提交专利交底书审核", mode = SystemLog.SAVE_DB)
    public Boolean submitPatents(@RequestBody @Validated List<Integer> ids) throws OwnerException {
        UserInfo info = getUserInfo();
        if (info.getMsUserId() <= 0) {
            throw new OwnerException("管理员身份验证失败，请稍后重试。");
        }
        return patentPlanService.submitPatents(info, ids);
    }

    //@PermissionRequired(perms = "project:patentPlan:search")
    @GetMapping("/getPatentOpinions")
    @PermissionRequired(perms = "project:patentPlan:view")
    @ApiOperation(value = "查询专利资料意见列表 ", notes = "查询专利资料意见列表")
    public List<PatentOpinionModel> getPatentOpinions(@RequestParam(value = "patentPlanId", required = false) Integer patentPlanId, @RequestParam(value = "patentNo", required = false) String patentNo) throws OwnerException {
        if (null == patentPlanId && StringUtils.isEmpty(patentNo)) {
            throw new OwnerException("请选择要查询的专利或申请！");
        }
        return patentPlanService.getPatentOpinions(patentPlanId, patentNo);
    }

    @SystemLog(description = "添加/编辑专利资料意见", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "project:patentPlan:publish")
    @PostMapping("/saveOpinion")
    @ApiOperation(value = "添加/编辑专利资料意见 ", notes = "添加/编辑专利资料意见")
    public Boolean saveOpinion(@RequestBody @Validated PatentOpinionModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return patentPlanService.saveOpinion(model, userInfo);
    }

    @SystemLog(description = "删除专利资料意见", mode = SystemLog.SAVE_DB)
    //@PermissionRequired(perms = "project:patentPlan:add")
    @PostMapping("/delOpinions")
    @ApiOperation(value = "删除专利资料意见 ", notes = "添加/删除专利资料意见")
    public Boolean delOpinions(@RequestBody @Validated List<Integer> opinionIds) throws OwnerException {
        return patentPlanService.delOpinions(opinionIds);
    }
}
