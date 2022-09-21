package com.yskc.rs.controller;

import cn.hutool.core.io.IoUtil;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.DocListEntity;
import com.yskc.rs.models.init.ImportProjectInfoModel;
import com.yskc.rs.models.sysDocument.DocListModel;
import com.yskc.rs.service.DocListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Autowired
    private DocListService docListService;
    @Autowired
    private RsConfig rsConfig;

    /**
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "新增附件列表项")
    @PostMapping("/addDocList")
//    @PermissionRequired(perms = "rdorg:buildList:add")
    @ApiOperation(value = "新增附件列表项", notes = "新增附件列表项", response = boolean.class)
    public boolean addDocList(@RequestBody @Validated DocListModel entity) throws OwnerException {
        return docListService.addDocList(getUserInfo(), entity);
    }

    /**
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "批量导入对应年份的建设事项文件", mode = SystemLog.SAVE_DB)
    @PostMapping("/importFiles")
    @PermissionRequired(perms = "rdorg:buildList:import")
    @ApiOperation(value = "批量导入对应年份的建设事项文件", notes = "批量导入对应年份的建设事项文件", response = boolean.class)
    public boolean importFiles(@RequestBody ImportProjectInfoModel model) throws OwnerException ,IOException{
        return docListService.importFiles(getUserInfo(), model.getSourceYear(), model.getTargetYear());
    }

    /**
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "查询建设事项 存在文件的之前的年份")
    @GetMapping("/getYear")
    @ApiOperation(value = "查询建设事项 存在文件的之前的年份", notes = "查询建设事项 存在文件的之前的年份", response = boolean.class)
    public List<Integer> getYear(Integer year) throws OwnerException ,IOException{
        return docListService.getYear(year,getUserInfo().getCompanyId());
    }

    /**
     *
     * @param entity
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "修改负责岗位")
    @PostMapping("/editOperators")
    //@PermissionRequired(perms = "rdorg:buildList:edit")
    @ApiOperation(value = "修改负责岗位", notes = "修改负责岗位", response = boolean.class)
    public boolean editOperators(@RequestBody @Validated DocListModel entity) throws OwnerException {
        return docListService.editOperators(getUserInfo(), entity);
    }

    /**
     *
     * @param model
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "删除文件",mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @ApiOperation(value = "删除文件", notes = "删除文件", response = boolean.class)
    public boolean del(@RequestBody @Validated DocListEntity model) throws OwnerException {
        return docListService.del(model.getId());
    }

    /**
     *
     * @param samplePath
     * @throws OwnerException
     * @throws IOException
     */
    @GetMapping("/downloadTemp")
    @SystemLog(description = "下载文档",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "下载文档", notes = "下载文档", response = String.class)
    public void downloadTemp(String samplePath) throws OwnerException, IOException {
        OutputStream os = outGeneralFile("样例模板");
        Path path = Paths.get(rsConfig.getUploadConfig().getImportPath(), samplePath);
        String fullPath = path.toString();
        InputStream fis = new BufferedInputStream(new FileInputStream(fullPath));
        IoUtil.copy(fis, os);
        os.flush();
        os.close();
        fis.close();
    }
}
