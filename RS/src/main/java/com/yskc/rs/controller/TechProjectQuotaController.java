package com.yskc.rs.controller;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.tech.TechProjectQuotaEntity;
import com.yskc.rs.service.TechProjectQuotaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.controller
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-24 09:32
 * @Description: 改造指标control层
 */
@RestController
@Api(value = "改造指标接口", tags = "改造指标接口")
@RequestMapping("/api/techProjectQuota")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
public class TechProjectQuotaController extends BaseController {

    @Autowired
    private TechProjectQuotaService techProjectQuotaService;

    /**
     * 获取项目改造指标
     *
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @GetMapping("/getQuota")
    @ApiOperation(value = "获取项目改造指标", notes = "获取项目改造指标")
    public TechProjectQuotaEntity getQuota(Integer projectId) throws OwnerException {
        return techProjectQuotaService.getQuota(getUserInfo().getCompanyId(), projectId);
    }

    /**
     * @param projectQuotaEntity
     * @return
     * @throws OwnerException
     */
    @PostMapping("/save")
    @SystemLog(description = "保存项目改造指标", mode = SystemLog.SAVE_DB)
    @ApiOperation(value = "保存项目改造指标", notes = "保存项目改造指标")
    public Integer save(@RequestBody @Validated TechProjectQuotaEntity projectQuotaEntity) throws OwnerException {
        return techProjectQuotaService.save(getUserInfo(), projectQuotaEntity);
    }

}
