package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.FormAuditModel;
import com.yskc.ms.models.finaDaily.FinaDailyModel;
import com.yskc.ms.models.finaDaily.QueryDailyModel;
import com.yskc.ms.models.finaDaily.SaveFinaDailyModel;
import com.yskc.ms.service.ms.FinaDailyService;
import com.yskc.ms.service.ms.FlowInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/ms/controller/ms
 * @Author: hm
 * @CreateTime: 2022/9/6
 * @Description: 财务日报对应controller
 */
@Api( tags = "财务日报", value = "财务日报" )
@CrossOrigin( origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {} )
@RestController
@RequestMapping( "/api/finaDaily" )
public class FinaDailyController extends BaseController {
    @Autowired
    private FinaDailyService finaDailyService;

    @Autowired
    private FlowInstanceService flowInstanceService;

    @GetMapping( "/getList" )
    @SystemLog( description = "根据条件查询财务日报" )
    @ApiOperation( value = "根据条件查询财务日报", notes = "根据条件查询财务日报" )
    @PermissionRequired( perms = "innovation:finaDaily:search" )
    public PageModel< List< FinaDailyModel > > getList(QueryDailyModel query) throws OwnerException {
        return finaDailyService.getDailyList(query, getUserInfo(), getDataPerm());
    }

    @PostMapping( "/addFinaDaily" )
    @SystemLog( description = "添加暂存财务日报" )
    @ApiOperation( value = "添加暂存财务日报", notes = "添加暂存财务日报" )
    @PermissionRequired( perms = "innovation:finaDaily:add" )
    public Boolean addFinaDaily(@RequestBody @Validated SaveFinaDailyModel model) throws OwnerException {
        return finaDailyService.saveFinaDaily(model, getUserInfo(), true);
    }

    @PostMapping( "/editFinaDaily" )
    @SystemLog( description = "编辑暂存财务日报" )
    @ApiOperation( value = "编辑暂存财务日报", notes = "编辑暂存财务日报" )
    @PermissionRequired( perms = "innovation:finaDaily:edit" )
    public Boolean editFinaDaily(@RequestBody @Validated SaveFinaDailyModel model) throws OwnerException {
        return finaDailyService.saveFinaDaily(model, getUserInfo(), false);
    }

    @PostMapping( "/submitFinaDaily" )
    @SystemLog( description = "提交财务日报" )
    @ApiOperation( value = "提交财务日报", notes = "提交财务日报" )
    @PermissionRequired( perms = "innovation:finaDaily:submit" )
    public Boolean submitFinaDaily(@RequestBody @Validated SaveFinaDailyModel model) throws OwnerException {
        return finaDailyService.submitFinaDaily(model, getUserInfo());
    }

    @PostMapping( "/delFinaDaily" )
    @SystemLog( description = "删除财务日报" )
    @ApiOperation( value = "删除财务日报", notes = "删除财务日报" )
    @PermissionRequired( perms = "innovation:finaDaily:del" )
    public Boolean delFinaDaily(@RequestBody @Validated BatchDeleteModel model) throws OwnerException {
        return finaDailyService.delFinaDaily(model.getIds());
    }

    @PostMapping( "/reviewFinaDaily" )
    @SystemLog( description = "审核财务日报" )
    @ApiOperation( value = "审核财务日报", notes = "审核财务日报" )
    @PermissionRequired( perms = "innovation:finaDaily:review" )
    public Boolean reviewFinaDaily(@RequestBody @Validated FormAuditModel model) throws OwnerException {
        return flowInstanceService.audit(model, getUserInfo());
    }
}
