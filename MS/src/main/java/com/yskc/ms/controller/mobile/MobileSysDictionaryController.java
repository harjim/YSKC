package com.yskc.ms.controller.mobile;

import com.yskc.ms.controller.ms.BaseController;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.service.rs.SysDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: mobile
 * @description: 字典类接口
 * @author: wjy
 * @create: 2022-06-30 11:35
 **/
@Api(tags = "系统字典类接口", value = "系统字典类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/mobile/sysDictionary")
public class MobileSysDictionaryController extends BaseController {
    @Autowired
    private SysDictionaryService sysDictionaryService;

    /**
     * 获取字典
     * @param type
     * @return
     */
    @GetMapping("/getDictionary")
    @ApiOperation(value = "获取字典", notes = "获取字典", response = List.class)
    public List<SysDictionaryModel> getDictionary(Integer type)  {
        return sysDictionaryService.getDictionaryByType(type);
    }

    /**
     * 获取添加客户字典
     * @return
     */
    @GetMapping("/getCustomerDictionary")
    @ApiOperation(value = "获取添加客户字典", notes = "获取添加客户字典", response = List.class)
    public Map<String, List<SysDictionaryModel>> getCustomerDictionary()  {
        return sysDictionaryService.getCustomerDictionary();
    }
}
