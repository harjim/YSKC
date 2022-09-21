package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.rs.enums.CompanyGroupEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.companyrdsummary.QueryCompanyRdSummaryModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.hightech.HighTechModel;
import com.yskc.rs.models.hightech.QueryHighTechModel;
import com.yskc.rs.models.project.ProjectListModel;
import com.yskc.rs.models.projectrdemployee.QueryProjectRdEmployeeModel;
import com.yskc.rs.models.projectrdemployee.RdEmployeeAggModel;
import com.yskc.rs.models.rdequipment.RdEquipmentInfoModel;
import com.yskc.rs.models.sysDocument.DocListModel;
import com.yskc.rs.service.CompanyRdSummaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-05 08:33
 * @Description: 公司研发汇总接口
 */
@RequestMapping("/api/companyRdSummary")
@RestController
@Api(tags = "公司研发汇总接口", value = "公司研发汇总接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
public class CompanyRdSummaryController extends BaseController {

    @Autowired
    private CompanyRdSummaryService companyRdSummaryService;


    @GetMapping("/getList")
    @ApiOperation(notes = "获取公司研发汇总", value = "获取公司研发汇总")
    @PermissionRequired(perms = "group:companyRdSummary:search")
    public PageResult getList(QueryCompanyRdSummaryModel query) throws OwnerException {
        UserInfo info = getUserInfo();
        checkLoginType(info, null);
        return companyRdSummaryService.getList(query, info);
    }

    @GetMapping("/getGroupFunds")
    @ApiOperation(notes = "获取研发费用", value = "获取研发费用")
    public Map<String, BigDecimal> getGroupFunds(Integer year, Integer companyId) throws OwnerException {
        if (null == year) {
            throw new OwnerException("获取研发费用失败。");
        }
        UserInfo info = getUserInfo();
        checkLoginType(info, companyId);
        return companyRdSummaryService.getGroupFunds(year, companyId, info);
    }

    @GetMapping("/getCompanyRdList")
    @ApiOperation(notes = "根据公司获取研发项目列表", value = "根据公司获取研发项目列表")
    public List<ProjectListModel> getCompanyRdList(Integer year, Integer companyId) throws OwnerException {
        UserInfo info = getUserInfo();
        checkLoginType(info, companyId);
        return companyRdSummaryService.getCompanyRdList(year, companyId);
    }

    @GetMapping("/getCompanyRdEmployeeList")
    @ApiOperation(notes = "根据公司获取研发人员列表", value = "根据公司获取研发人员列表")
    public PageModel<List<RdEmployeeAggModel>> getCompanyRdEmployeeList(Integer companyId, QueryProjectRdEmployeeModel query) throws OwnerException {
        UserInfo info = getUserInfo();
        checkLoginType(info, companyId);
        return companyRdSummaryService.getCompanyRdEmployeeList(companyId, query);
    }

    @GetMapping("/getCompanyRdEquipmentList")
    @ApiOperation(notes = "根据公司获取研发设备列表", value = "根据公司获取研发设备列表")
    public PageModel<List<RdEquipmentInfoModel>> getCompanyRdEquipmentList(Integer companyId, QueryEquipmentModel query) throws OwnerException {
        UserInfo info = getUserInfo();
        checkLoginType(info, companyId);
        return companyRdSummaryService.getCompanyRdEquipmentList(companyId, query);
    }

    @GetMapping("/getCompanyHighTechList")
    @ApiOperation(notes = "根据公司获取高品列表", value = "根据公司获取高品列表")
    public List<HighTechModel> getCompanyHighTechList(Integer companyId, QueryHighTechModel query) throws OwnerException {
        UserInfo info = getUserInfo();
        checkLoginType(info, companyId);
        return companyRdSummaryService.getCompanyHighTechList(companyId, query);
    }

    @GetMapping("/getCompanyBuildList")
    @ApiOperation(notes = "根据公司获取机构建设列表", value = "根据公司获取机构建设列表")
    public List<DocListModel> getCompanyBuildList(Integer year, Integer companyId) throws OwnerException {
        UserInfo info = getUserInfo();
        checkLoginType(info, companyId);
        return companyRdSummaryService.getBuildList(year, companyId, 6001);
    }

    private void checkLoginType(UserInfo info, Integer companyId) throws OwnerException {
        if (1 == info.getUserSource() || !CompanyGroupEnum.isGroup(info.getCompanyType())) {
            throw new OwnerException("当前登录账户不是集团账户。");
        }
        if (companyId != null) {
            companyRdSummaryService.checkChildCompany(companyId, info.getGroupId(), info.getGroupFullPath());
        }
    }

}
