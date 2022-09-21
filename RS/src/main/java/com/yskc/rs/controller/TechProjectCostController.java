package com.yskc.rs.controller;

import cn.hutool.core.date.DateUtil;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.entity.tech.TechProjectCostEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.TechProjectCostExcel;
import com.yskc.rs.models.tech.cost.QueryTechProjectCostModel;
import com.yskc.rs.models.tech.cost.TechProjectCostModel;
import com.yskc.rs.service.TechProjectCostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-25 08:29
 * @Description: 项目支出control
 */
@RestController
@Api(value = "项目支出接口类", tags = "项目支出接口类")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RequestMapping("/api/techProjectCost")
public class TechProjectCostController extends BaseController {

    @Autowired
    private TechProjectCostService techProjectCostService;

    @Autowired
    private RsConfig rsConfig;

    /**
     * @param query
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getList")
    @ApiOperation(value = "查询项目支出", notes = "查询项目支出")
    public PageModel<List<TechProjectCostModel>> getList(QueryTechProjectCostModel query) throws OwnerException {
        return techProjectCostService.getList(getUserInfo().getCompanyId(), query);
    }

    /**
     * @param techProjectCostModel
     * @return
     * @throws OwnerException
     */
    @PostMapping("/add")
    @SystemLog(description = "添加项目支出",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加项目支出", notes = "添加项目支出")
    public boolean add(@RequestBody @Validated TechProjectCostModel techProjectCostModel) throws OwnerException {
        return techProjectCostService.add(getUserInfo(), techProjectCostModel);
    }

    /**
     * @param techProjectCostModel
     * @return
     * @throws OwnerException
     */
    @PostMapping("/update")
    @SystemLog(description = "更新项目支出",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "更新项目支出", notes = "更新项目支出")
    public boolean update(@RequestBody @Validated TechProjectCostModel techProjectCostModel) throws OwnerException {
        return techProjectCostService.update(getUserInfo(), techProjectCostModel);
    }

    /**
     * @param techProjectCostEntity
     * @return
     */
    @PostMapping("/del")
    @SystemLog(description = "删除项目支出",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除项目支出", notes = "删除项目支出")
    public boolean del(@RequestBody @Validated TechProjectCostEntity techProjectCostEntity) {
        return techProjectCostService.del(techProjectCostEntity.getId());
    }

    /**
     * @param techProjectCostEntities
     * @return
     */
    @PostMapping("/dels")
    @SystemLog(description = "删除多条项目支出",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除多条项目支出", notes = "删除多条项目支出")
    public boolean dels(@RequestBody @Validated List<TechProjectCostEntity> techProjectCostEntities) {
        return techProjectCostService.dels(techProjectCostEntities);
    }

    /**
     * @param file
     * @param tableField
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @PostMapping("/importInfo")
    @SystemLog(description = "导入项目支出",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入项目支出", notes = "导入项目支出")
    public String importInfo(@RequestParam("file") MultipartFile file, TableField tableField, Integer projectId) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                userInfo.getCompanyId());
        ExcelResult<TechProjectCostExcel> techProjectCostExcelExcels = excelService.getExcelResult(tempPath, file, TechProjectCostExcel.class, tableField);
        techProjectCostService.importInfo(userInfo, techProjectCostExcelExcels.getData(), projectId);
        return techProjectCostExcelExcels.msg;
    }

    /**
     * @param projectId
     * @throws OwnerException
     * @throws IOException
     */
    @GetMapping("/exportCost")
    @SystemLog(description = "导出项目支出明细",mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导出项目支出明细", notes = "导出项目支出明细")
    public void exportCost(Integer projectId) throws OwnerException, IOException {
        try (OutputStream out = outGeneralFile(MessageFormat.format("项目支出明细表{0}.xls", DateUtil.format(new Date(), "yyyy-MM-dd-HH:mm:ss")))) {
            techProjectCostService.exportCost(projectId, getUserInfo(), out);
        } catch (Exception e) {
            throw new OwnerException("导出失败");
        }
    }

    /**
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getProjectCost")
    @ApiOperation(value = "获取项目支出", notes = "获取项目支出")
    public Map<String, Object> getProjectCost(Integer projectId) throws OwnerException {
        return techProjectCostService.getProjectCost(getUserInfo().getCompanyId(), projectId);
    }

}
