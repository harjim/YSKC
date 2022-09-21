package com.yskc.ms.controller.ms;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.models.patent.PatentAccountModel;
import com.yskc.ms.models.patent.PatentDataModel;
import com.yskc.ms.models.patent.QueryPatentDataModel;
import com.yskc.ms.models.patent.QueryPatentListModel;
import com.yskc.ms.service.ms.PatentDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by hck
 * on 2020/9/11 8:36
 * description:专利数据接口
 */
@Api(tags = "专利数据接口", value = "专利数据接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/patentData")
public class PatentDataController extends BaseController{

    @Autowired
    private PatentDataService patentDataService;


    @GetMapping("/getList")
    @PermissionRequired(perms = "patent:patentData:search")
    @ApiOperation(value = "获取专利数据", notes = "获取专利数据")
    public PageModel<List<PatentDataModel>> getList(QueryPatentDataModel query) throws OwnerException {
        return patentDataService.getList(query,getDataPerm());
    }
}
