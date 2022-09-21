package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.ms.entity.ms.FlowInstanceLog;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.customer.CustomerOwnerModel;
import com.yskc.ms.models.serviceApply.ServiceApplyModel;
import com.yskc.ms.models.serviceApply.ServiceNoModel;
import com.yskc.ms.models.serviceRecord.QueryRecordModel;
import com.yskc.ms.models.serviceRecord.RecordCustomerModel;
import com.yskc.ms.models.serviceRecord.WorkRecordModel;
import com.yskc.ms.service.ms.ServiceApplyService;
import com.yskc.ms.service.ms.ServiceRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: ms
 * @description: 客户服务记录（新）
 * @author: cyj
 * @create: 2022-08-13 09:04
 **/
@Api(tags = "客户服务记录（新）", value = "客户服务记录（新）")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/serviceRecord")
public class ServiceRecordController extends BaseController{

    @Autowired
    private ServiceRecordService serviceRecordService;
    @Autowired
    private ServiceApplyService serviceApplyService;

    @GetMapping("/getList")
    @SystemLog(description = "根据条件查询服务记录")
    @ApiOperation(value = "根据条件查询服务记录", notes = "根据条件查询服务记录")
    @PermissionRequired(perms = "customer:workRecord:search")
    public PageResult getList(QueryRecordModel query) throws OwnerException {

        return serviceRecordService.getList(query,getUserInfo(),getDataPerm());
    }

    @GetMapping("/getCustomerList")
    @SystemLog(description = "根据条件查询客户数据")
    @ApiOperation(value = "根据条件查询客户数据", notes = "根据条件查询客户数据")
    public List<CustomerOwnerModel> getCustomerList(String companyName) throws OwnerException {

        return serviceRecordService.getCustomerList(companyName);
    }

    @GetMapping("/getServiceNo")
    @SystemLog(description = "根据客户查询单号")
    @ApiOperation(value = "根据客户查询单号", notes = "根据客户查询单号")
    public PageModel<List<ServiceNoModel>> getServiceNo(QueryRecordModel query) throws OwnerException {

        return serviceRecordService.getServiceNo(query);
    }

    @GetMapping("/getApplyInfo")
    @SystemLog(description = "根据单号查询服务申请")
    @ApiOperation(value = "根据单号查询服务申请", notes = "根据单号查询服务申请")
    public ServiceApplyModel getApplyInfo(String serviceNo) throws OwnerException {

        return serviceApplyService.getApplyInfo(serviceNo,null,getUserInfo());
    }
    @GetMapping("/getRecordInfo")
    @SystemLog(description = "根据id查询服务记录")
    @ApiOperation(value = "根据id查询服务记录", notes = "根据id查询服务记录")
    public WorkRecordModel getRecordInfo(Integer recordId) throws OwnerException {

        return serviceRecordService.getRecordInfo(recordId,getUserInfo());
    }

    @PostMapping("/addServiceRecord")
    @SystemLog(description = "添加服务记录", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "添加服务记录", notes = "添加服务记录")
    @PermissionRequired(perms = "customer:workRecord:add")
    public Boolean addServiceRecord(@RequestBody @Validated WorkRecordModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return serviceRecordService.addRecord(model,userInfo);
    }

    @PostMapping("/editServiceRecord")
    @SystemLog(description = "编辑服务记录", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "编辑服务记录", notes = "编辑服务记录")
    @PermissionRequired(perms = "customer:workRecord:edit")
    public Boolean editServiceRecord(@RequestBody @Validated WorkRecordModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return serviceRecordService.editRecord(model,userInfo);
    }

    @PostMapping("/delServiceRecord")
    @SystemLog(description = "删除服务记录", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "删除服务记录", notes = "删除服务记录")
    @PermissionRequired(perms = "customer:workRecord:del")
    public Boolean delServiceRecord(@RequestBody @Validated BatchDeleteModel model) throws OwnerException {
        return serviceRecordService.delRecord(model.getIds());
    }

    @PostMapping("/submit")
    @SystemLog(description = "提交服务记录", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "提交服务记录", notes = "提交服务记录")
    @PermissionRequired(perms = "customer:workRecord:submit")
    public Boolean submit(@RequestBody @Validated WorkRecordModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return serviceRecordService.submit(model,userInfo);
    }
}
