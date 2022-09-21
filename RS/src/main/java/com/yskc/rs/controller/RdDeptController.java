package com.yskc.rs.controller;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.rddept.*;
import com.yskc.rs.service.RdDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: wangxing
 * @CreateTime: 2019-08-08 15:53
 * @Description: 研发部门Controller
 */
@Api(tags = "研发部门接口", value = "研发部门接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/rdDept")
public class RdDeptController extends BaseController {
    @Autowired
    private RdDeptService rdDeptService;

    /**
     * 获取最大年份 <= 传入年份
     * 获取研发部门
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getList")
    @ApiOperation(value = "获取研发部门", notes = "获取研发部门", response = List.class)
    @PermissionRequired(perms = "rdorg:arch:rd:view")
    public ResultRdDeptModel getList(Integer year) throws OwnerException {
        return rdDeptService.getList(getUserInfo().getCompanyId(), year);
    }

    /**
     * 获取最大年份 <= 传入年份
     * 研发部门树
     *
     * @param year
     * @return
     * @throws OwnerException
     */
    @GetMapping("/queryRdDeptTree")
    @ApiOperation(value = "研发部门树", notes = "研发部门树", response = List.class)
    public List<RdDeptTree> queryRdDeptTree(Integer year) throws OwnerException {
        List<RdDeptTree> rdDeptTrees = rdDeptService.queryRdDeptTree(getUserInfo().getCompanyId(), year);
        //评审委员会map
        Map<Integer, List<RdDeptTree>> treeMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(rdDeptTrees)) {
            for (RdDeptTree tree : rdDeptTrees) {
                if (tree.getNodeType() == 1) {
                    if (!treeMap.containsKey(tree.getParentId())) {
                        List<RdDeptTree> list = new ArrayList<>();
                        treeMap.put(tree.getParentId(), list);
                    }
                    treeMap.get(tree.getParentId()).add(tree);
                }
            }
        }
        return RdDeptTree.getTree(rdDeptTrees, treeMap);
    }

    /**
     * @param rdDeptModel
     * @return
     * @throws OwnerException
     */
    @SystemLog(description = "新增/修改研发部门", mode = SystemLog.SAVE_DB)
    @PostMapping("/modifyRdDept")
    @PermissionRequired(perms = "rdorg:arch:rd:add,rdorg:arch:rd:edit")
    @ApiOperation(value = "新增/修改研发部门", notes = "新增/修改研发部门")
    public Boolean modifyRdDept(@RequestBody @Validated RdDeptModel rdDeptModel) throws OwnerException {
        return rdDeptService.modifyRdDept(rdDeptModel, getUserInfo());
    }

    @SystemLog(description = "删除研发部门", mode = SystemLog.SAVE_DB)
    @PostMapping("/del")
    @PermissionRequired(perms = "rdorg:arch:rd:del")
    @ApiOperation(value = "删除研发部门", notes = "删除研发部门")
    public Boolean del(@RequestBody RdDeptModel rdDeptModel) throws OwnerException {
        return rdDeptService.delete(rdDeptModel, getUserInfo());
    }

    @SystemLog(description = "导入已存在年份的研发部门", mode = SystemLog.SAVE_DB)
    @PostMapping("/importYear")
    @PermissionRequired(perms = "rdorg:arch:rd:import")
    @ApiOperation(value = "导入已存在年份的研发部门", notes = "导入已存在年份的研发部门")
    public Boolean importYear(@RequestBody @Validated RdYearModel rdYearModel) throws OwnerException {
        return rdDeptService.importYear(getUserInfo(), rdYearModel);
    }

    @PostMapping("/cloneNode")
    @PermissionRequired(perms = "rdorg:arch:rd:clone")
    @SystemLog(description = "clone研发部门", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "clone节点", notes = "clone节点")
    public Boolean cloneNode(@RequestBody @Validated CloneRdDeptModal cloneRdDept) throws OwnerException {
        return rdDeptService.cloneNode(cloneRdDept, getUserInfo());
    }

    @PostMapping("/sortNode")
    @PermissionRequired(perms = "rdorg:arch:rd:edit")
    @SystemLog(description = "排序研发部门", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "排序研发部门", notes = "排序研发部门")
    public Boolean sortNode(@RequestBody @Validated RdDeptSortModel rdDeptSort) throws OwnerException {
        return rdDeptService.sortNode(rdDeptSort, getUserInfo());
    }
}
