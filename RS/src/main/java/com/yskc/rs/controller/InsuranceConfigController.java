package com.yskc.rs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 五险一金比例类接口
 *
 * @author yezhihao
 * @version 创建时间：2019年7月30日 下午3:00:39
 */
@Api(tags = "五险一金比例类接口", value = "五险一金比例类接口")
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/insuranceConfig")
public class InsuranceConfigController extends BaseController {
    // // TODO: 20/08/19 待删除 InsuranceConfig相关代码
}
