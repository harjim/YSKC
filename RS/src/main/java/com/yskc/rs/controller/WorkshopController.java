package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.entity.init.TableField;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ExcelResult;
import com.yskc.rs.models.excel.WorkshopExcel;
import com.yskc.rs.models.workshop.WorkshopModel;
import com.yskc.rs.models.workshop.WorkshopTreeModel;
import com.yskc.rs.service.WorkshopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: wangxing
 * @CreateTime: 2019-11-21 10:24
 * @Description: 工艺线/车间业务层控制层
 */
@Api(tags = "工艺线/车间类接口", value = "工艺线/车间类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/workshop")
public class WorkshopController extends BaseController {
    @Autowired
    private WorkshopService workshopService;
    @Autowired
    private RsConfig rsConfig;

    /**
     * @param workshopName
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "company:workshop:search")
    @GetMapping("/queryWorkshop")
    @ApiOperation(value = "获取工艺线/车间信息", notes = "获取工艺线/车间信息")
    public List<WorkshopModel> queryWorkshop(String workshopName) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return workshopService.queryWorkshop(userInfo.getCompanyId(), workshopName);
    }

    /**
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getWorkshopTree")
    @ApiOperation(value = "工艺线/车间信息下拉数据", notes = "工艺线/车间信息下拉数据")
    public List<WorkshopTreeModel> getWorkshopTree() throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return WorkshopTreeModel.getData(workshopService.getWorkshopTree(userInfo.getCompanyId()));
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "company:workshop:add")
    @PostMapping("/addWorkshop")
    @SystemLog(description = "添加工艺线/车间", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加工艺线/车间", notes = "添加工艺线/车间", response = boolean.class)
    public boolean addWorkshop(@RequestBody @Validated WorkshopModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return workshopService.addWorkshop(userInfo, model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "company:workshop:edit")
    @SystemLog(description = "更新工艺线/车间", mode = SystemLog.SAVE_DB)
    @PostMapping("/updateWorkshop")
    @ApiOperation(value = "更新工艺线/车间", notes = "更新工艺线/车间", response = boolean.class)
    public boolean updateWorkshop(@RequestBody @Validated WorkshopModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return workshopService.updateWorkshop(userInfo, model);
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @PostMapping("/delWorkshop")
    @PermissionRequired(perms = "company:workshop:del")
    @SystemLog(description = "删除工艺线/车间", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除工艺线/车间", notes = "删除工艺线/车间")
    public boolean delWorkshop(@RequestBody @Validated WorkshopModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return workshopService.delWorkshop(userInfo.getCompanyId(), model.getId());
    }

    /**
     * @param parentId
     * @param seq
     * @return
     */
    @GetMapping("/checkSeq")
    @ApiOperation(value = "检查重复序号", notes = "检查重复序号")
    public boolean checkSeq(Integer parentId, Integer seq) throws OwnerException {
        UserInfo info = getUserInfo();
        return workshopService.checkSeq(info.getCompanyId(), parentId, seq);
    }

    /**
     * @param parentId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/selectMaxSeq")
    @ApiOperation(value = "查询该节点最大的序号", notes = "查询该节点最大的序号")
    public Integer selectMaxSeq(Integer parentId) throws OwnerException {
        UserInfo info = getUserInfo();
        return workshopService.selectMaxSeq(info.getCompanyId(), parentId);
    }

    /**
     * @param file
     * @param tableField
     * @return
     * @throws OwnerException
     */
    @PermissionRequired(perms = "company:workshop:import")
    @PostMapping("/importWorkshop")
    @SystemLog(description = "导入工艺线/车间", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "导入工艺线/车间", notes = "导入工艺线/车间", response = String.class)
    public String importWorkshop(@RequestParam("file") MultipartFile file, TableField tableField) throws OwnerException {
        UserInfo info = getUserInfo();
        String tempPath = MessageFormat.format(Constant.IMPORT_FILE_NAME_FORMAT,
                rsConfig.getUploadConfig().getImportPath(),
                info.getCompanyId());
        ExcelResult<WorkshopExcel> excelResult = excelService.getExcelResult(tempPath, file, WorkshopExcel.class, tableField);

        return workshopService.importWorkshop(info, excelResult.getData());
    }
}
