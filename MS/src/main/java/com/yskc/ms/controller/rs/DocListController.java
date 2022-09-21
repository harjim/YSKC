package com.yskc.ms.controller.rs;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.entity.rs.DocListEntity;
import com.yskc.ms.entity.rs.RsDocFileTemplateEntity;
import com.yskc.ms.entity.rs.models.DocListModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.doctemplate.QueryDocModel;
import com.yskc.ms.models.rs.DocumentListModel;
import com.yskc.ms.models.rs.NewReportsModel;
import com.yskc.ms.models.rs.QueryDocListModel;
import com.yskc.ms.models.rs.RsDocFileTemplateModel;
import com.yskc.ms.service.rs.DocListService;
import com.yskc.ms.service.rs.RsDocFileTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.List;

/**
 * 文档管理接口
 *
 * @author huronghua
 */
@Api(tags = "附件列表接口", value = "附件列表接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/docList")
public class DocListController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DocListService docListService;
    @Autowired
    private MsConfig msConfig;

    @Autowired
    private RsDocFileTemplateService rsDocFileTemplateService;

    @GetMapping("/queryDocList")
    @PermissionRequired(perms = "doc:docList:search")
    @ApiOperation(value = "查询附件清单", notes = "查询附件清单", response = String.class)
    public PageModel<List<DocListModel>> queryDocList(QueryDocModel queryDocModel) throws OwnerException {
        return docListService.queryDocList(queryDocModel, getDataPerm());
    }

    @SystemLog(description = "删除文件")
    @PostMapping("/del")
    @PermissionRequired(perms = "doc:docList:del")
    @ApiOperation(value = "删除文件", notes = "删除文件", response = boolean.class)
    public boolean del(@RequestBody @Validated DocListEntity entity) throws OwnerException {
        return docListService.del(entity.getId());
    }

    @SystemLog(description = "新增附件列表项")
    @PostMapping("/addDocList")
    @PermissionRequired(perms = "doc:docList:add")
    @ApiOperation(value = "新增附件列表项", notes = "新增附件列表项", response = boolean.class)
    public boolean addDocList(@RequestBody @Validated DocListEntity entity) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        entity.setSamplePath("");
        docListService.addDocList(userInfo, entity);
        return true;
    }

    @SystemLog(description = "修改附件列表项")
    @PostMapping("/updateDocList")
    @PermissionRequired(perms = "doc:docList:edit")
    @ApiOperation(value = "修改附件列表项", notes = "修改附件列表项", response = boolean.class)
    public boolean updateDocList(@RequestBody @Validated DocListEntity entity) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        docListService.updateDocList(userInfo, entity);
        return true;
    }

    @GetMapping("/selectMaxSeq")
    @ApiOperation(value = "获取委外费用", notes = "获取委外费用")
    public Integer selectMaxSeq(Integer listType) throws OwnerException {
        return docListService.selectMaxSeq(listType);
    }

    @PostMapping("/uploadFile")
    @ApiOperation(value = "上传文件", notes = "上传文件", response = String.class)
    public Integer uploadFile(@RequestParam("file") MultipartFile file, DocListEntity entity) throws OwnerException {

        UserInfo userInfo = getUserInfo();
        if(null != file){
            String samplePath = MessageFormat.format(Constant.IMPORT_IMAGE_FILE_NAME_FORMAT,
                    "/docList", entity.getListType().toString(), System.currentTimeMillis() + file.getOriginalFilename());
            File tempFile = new File(msConfig.getUploadConfig().getDocPath(), samplePath);
            try {
                if(!tempFile.getParentFile().exists()){
                    FileUtil.mkParentDirs(tempFile);
                }
                file.transferTo(tempFile);
                entity.setSamplePath(samplePath);
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }
        if (entity.getId() == 0) {
            return docListService.addDocList(userInfo, entity);
        } else {
            return docListService.updateDocList(userInfo, entity);
        }
    }

    @GetMapping("/getDocList")
    @ApiOperation(value = "获取查新报告列表", notes = "获取查新报告列表")
    public PageModel<List<NewReportsModel>> getDocList(QueryDocListModel model) {
        return docListService.getDocList(model);
    }


    @GetMapping("/getDocumentList")
    @ApiOperation(value = "获取创新体系资料列表", notes = "获取创新体系资料列表")
    public PageModel<List<DocumentListModel>> getDocumentList(QueryDocListModel model) {
        return docListService.getDocumentList(model);
    }


    @GetMapping("/downloadFile")
    @PermissionRequired(perms = "customer:projectProgress:download")
    @SystemLog(description = "下载文档", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "下载文档", notes = "下载文档", response = String.class)
    public void downloadFile(String fileName, String filePath) throws IOException {
        OutputStream os = outGeneralFile(fileName);
        Path path = Paths.get(msConfig.getUploadConfig().getDocPath(), filePath);
        String fullPath = path.toString();
        InputStream fis = new BufferedInputStream(new FileInputStream(fullPath));
        IoUtil.copy(fis, os);
        os.flush();
        os.close();
        fis.close();
    }

    @PostMapping("/save")
    @SystemLog(description = "新增模板", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "doc:tempt:add")
    @ApiOperation(value = "新增模板", notes = "新增模板")
    public Integer save(@RequestBody @Validated RsDocFileTemplateEntity entity) throws OwnerException {
        return docListService.insert(entity, getUserInfo().getId());
    }

    @PostMapping("/edit")
    @SystemLog(description = "编辑模板", mode = SystemLog.SAVE_DB)
    @PermissionRequired(perms = "doc:tempt:edit")
    @ApiOperation(value = "编辑", notes = "编辑")
    public Boolean edit(@RequestBody @Validated RsDocFileTemplateModel model) throws OwnerException {
        return docListService.update(model, getUserInfo().getId());
    }

    @GetMapping("/findDocFileId")
    @ApiOperation(value = "查询", notes = "查询")
    public List<RsDocFileTemplateEntity> findDocFileId(Integer docFileId) {
        return rsDocFileTemplateService.findDocFileId(docFileId);
    }
}
