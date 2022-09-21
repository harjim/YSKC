package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.tech.BeianInfoModel;
import com.yskc.ms.models.tech.QueryBeianModel;
import com.yskc.ms.service.rs.BeianService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/29 8:05
 * @Description:备案管理
 */
@Api(tags = "备案管理接口", value = "备案管理接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/beian")
public class BeianController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BeianService beianService;
    @Autowired
    private MsConfig msConfig;

    @GetMapping("/getList")
    @PermissionRequired(perms = "project:register:search")
    @ApiOperation(value = "获取备案列表", notes = "获取备案列表")
    public PageModel<List<BeianInfoModel>> getList(QueryBeianModel query) throws OwnerException {
        return beianService.getList(query, getDataPerm());
    }

    @GetMapping("/export")
    @PermissionRequired(perms = "project:register:export")
    @ApiOperation(notes = "导出备案列表",value = "导出备案列表")
    public void export(QueryBeianModel query) throws OwnerException {
        String fileName;
        String templateName;
        fileName = "备案列表.xlsx";
        templateName = "备案列表模板.xlsx";
        String path = msConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + templateName;
        try (OutputStream out = outGeneralFile(fileName)) {
            beianService.getExport(query,getDataPerm(),out,path);
            out.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new OwnerException("导出失败");
        }
    }

}
