package com.yskc.ms.controller.rs;

import com.yskc.common.annotation.PermissionRequired;
import com.yskc.ms.models.rs.CheckModel;
import com.yskc.ms.models.rs.ReportCheckModel;
import com.yskc.ms.models.rs.RsProjectBaseModel;
import com.yskc.ms.service.rs.ProjectCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/api/projectcheck")
public class ProjectCheckController {
    @Autowired
    ProjectCheckService projectCheckService;

    @GetMapping("/getDuplicateNameList")
    @PermissionRequired(perms = "customer:projectProgress:checkdup")
    public Map<Integer, List<CheckModel<RsProjectBaseModel>>> getDuplicateNameList(Integer[] ids,  Double limitRate) {
        return projectCheckService.getDuplicateNameList(ids,limitRate);
    }
    @GetMapping("/getDuplicateReportList")
    @PermissionRequired(perms = "customer:projectProgress:checkdup")
    public Map<Integer, List<CheckModel<ReportCheckModel>>> getDuplicateReportList(Integer[] ids, Double limitRate){
        return projectCheckService.getDuplicateReportList(ids,limitRate);
    }

}
