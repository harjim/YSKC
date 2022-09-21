package com.yskc.rs.controller;

import com.yskc.common.annotation.NotLoginRequired;
import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.KeypointStaticModel;
import com.yskc.rs.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/home")
public class HomeController extends BaseController {

    @Autowired
    StatService statService;
    @GetMapping("/statInfo")
    public Map statInfo() throws OwnerException {
        return statService.statProject(this.getUserInfo().getCompanyId());
    }

    @NotLoginRequired
    @GetMapping("/getStat")
    public KeypointStaticModel getStat() throws OwnerException {
        return statService.getStat();
    }
}
