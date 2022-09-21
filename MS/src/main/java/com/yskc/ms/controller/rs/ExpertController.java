package com.yskc.ms.controller.rs;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import com.yskc.common.annotation.NotLoginRequired;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.expert.QueryExpertModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.*;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.yskc.ms.entity.rs.Expert;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.expert.ExpertModel;
import com.yskc.ms.service.rs.ExpertService;


/**
 * 专家接口类
 *
 * @author Administrator
 */
@Api(tags = "获取专家接口", value = "获取专家接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/expert")
public class ExpertController extends BaseController {

    @Autowired
    private ExpertService expertService;

    @Autowired
    private MsConfig msConfig;


    @GetMapping("/queryExpertList")
    @PermissionRequired(perms = "sys:Expert:search")
    @ApiOperation(value = "获取专家列表", notes = "获取专家列表", response = List.class)
    public PageModel<List<ExpertModel>> queryExpertList(QueryExpertModel query) throws OwnerException {
        Date beginTime = DateUtil.parse(query.getBeginDate());
        Date endTime = DateUtil.parse(query.getEndDate());
        return expertService.queryExpert(query,getDataPerm(), beginTime, endTime);

    }

    @PostMapping("/delExpert")
    @PermissionRequired(perms = "sys:Expert:del")
    @ApiOperation(value = "删除专家", notes = "删除专家", response = boolean.class)
    public boolean delExpert(@RequestBody @Validated ExpertModel model) throws OwnerException {
        return expertService.delExpert(model);
    }


    @PostMapping("/updateExpertStatus")
    @ApiOperation(value = "更新专家状态", notes = "更新专家状态", response = boolean.class)
    public boolean updateExpertStatus(@RequestBody @Validated ExpertModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return expertService.updateExpertStatus(userInfo, model);
    }

    @PostMapping("/addExpert")
    @PermissionRequired(perms = "sys:Expert:add")
    @ApiOperation(value = "添加专家", notes = "添加专家", response = boolean.class)
    public boolean addExpert(@RequestBody @Validated ExpertModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return expertService.addExpert(userInfo, model);
    }

    @PostMapping("/getMaxExpertNumber")
    @ApiOperation(value = "获取最新编号", notes = "获取最新编号", response = String.class)
    public String getMaxExpertNumber() throws OwnerException {
        return expertService.getMaxExpertNumber();
    }

    /**
     * 导入头像
     *
     * @param file
     * @param request
     * @return
     * @throws IOException
     * @throws OwnerException
     */
    @PostMapping("/importPhotoUrl")
    @ApiOperation(value = "导入头像照片", notes = "导入头像照片", response = boolean.class)
    public String importPhotoUrl(@RequestParam("avatar") MultipartFile multipartFile, HttpServletRequest request) throws IOException, OwnerException {
        String expertPath = msConfig.getExpertConfig().getExpertPath();
        String fileName = "/photo/" + System.currentTimeMillis() + multipartFile.getOriginalFilename();
        File tempFile = new File(expertPath + fileName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(tempFile);
        } catch (Exception ex) {
        }
        return fileName;
    }

    @PostMapping("/updateExpert")
    @PermissionRequired(perms = "sys:Expert:edit")
    @ApiOperation(value = "更新专家", notes = "更新专家", response = boolean.class)
    public boolean updateExpert(@RequestBody @Validated ExpertModel model) throws OwnerException {
        UserInfo userInfo = getUserInfo();
        return expertService.updateExpert(userInfo, model);
    }

    @NotLoginRequired
    @GetMapping("/detail/{uuid}")
    @ApiOperation(value = "查询详情", notes = "查询详情", response = String.class)
    public Expert getExperModelByuuid(@PathVariable("uuid") String expertUuid) {
        return expertService.getExpertByUuid(expertUuid);
    }

    /**
     * 通过id获取专家信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getExpertById")
    @ApiOperation(value = "更获取专家信息", notes = "获取专家信息", response = String.class)
    public Expert getExpertById(Integer id) {
        return expertService.getExpertById(id);
    }
}
