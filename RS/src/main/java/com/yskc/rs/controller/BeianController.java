package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.BeianInfoModel;
import com.yskc.rs.models.tech.QueryBeianModel;
import com.yskc.rs.service.BeianService;
import com.yskc.rs.service.DocumentService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/3/18 15:28
 * @Description:
 */
@Api(tags = "备案管理接口", value = "备案管理接口")
@CrossOrigin(origins = "*", allowedHeaders = "", allowCredentials = "true", methods = {})
@RestController
@RequestMapping("/api/beian")
public class BeianController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BeianService beianService;
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private DocumentService documentService;

    @GetMapping("/getList")
    @PermissionRequired(perms = "tech:beian:basicInfo:view")
    @ApiOperation(notes = "获取备案列表", value = "获取备案列表")
    public PageModel<List<BeianInfoModel>> getList(QueryBeianModel query) throws OwnerException {
        return beianService.getList(getUserInfo().getCompanyId(), query);
    }


    @GetMapping("/getBeianInfo")
    @PermissionRequired(perms = "tech:beian:basicInfo:view")
    @ApiOperation(notes = "获取备案信息", value = "获取备案信息")
    public BeianInfoModel getBeianInfo(Integer beianId) throws OwnerException {
        return beianService.getBeianInfo(getUserInfo().getCompanyId(), beianId);
    }

    @PostMapping("/save")
    @PermissionRequired(perms = "tech:beian:basicInfo:save")
    @SystemLog(description = "保存备案信息", mode = SystemLog.SAVE_DB)
    @ApiOperation(notes = "保存备案信息", value = "保存备案信息")
    public Boolean save(@RequestBody @Validated BeianInfoModel model) throws OwnerException {
        return beianService.save(getUserInfo(), model);
    }

    /**
     * @param
     * @return
     * @throws OwnerException
     */
    @PostMapping("/upload")
//    @PermissionRequired(perms = "tech:beian:basicInfo:save")
    @SystemLog(description = "上传备案相关文件", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "上传备案相关文件", notes = "上传备案相关文件", response = String.class)
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) throws OwnerException {
        Map<String, String> result = new HashMap<>();
        UserInfo userInfo = getUserInfo();
        String filePath = MessageFormat.format("/techProject/{0,number,#}/beian/{1}", userInfo.getCompanyId(), System.currentTimeMillis() + file.getOriginalFilename());
        File tempFile = new File(rsConfig.getUploadConfig().getDocPath() + filePath);
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
     * 通用的docPath 下载文件
     *
     * @param filePath
     * @return
     * @throws OwnerException
     */
    @GetMapping("/download")
    //@SystemLog(description = "通用的docPath 下载文件", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "通用的docPath 下载文件", notes = "通用的docPath 下载文件", response = String.class)
    public void download(String filePath) throws OwnerException, IOException {
        if (StringUtils.isEmpty(filePath)) {
            throw new OwnerException("下载失败，文件不存在。");
        }
        download(rsConfig.getUploadConfig().getDocPath() + filePath, filePath);
    }

}
