package com.yskc.docservice.controller.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.controller.rs.RsBaseController;
import com.yskc.docservice.models.DocParam;
import com.yskc.docservice.service.rs.RDFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/docservice/controller/ms
 * @CreateTime: 2022/9/21
 * @Description: 过程文档导出、预览
 */
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/msRdfile")
public class MsRDFileController extends RsBaseController {
    @Autowired
    RDFileService rdFileService;

    @GetMapping("/preview")
    public String preview(DocParam docParam) throws OwnerException {
        return rdFileService.preview(docParam);
    }
    @GetMapping("/export")
    public void export(DocParam docParam) throws Exception {

        OutputStream out = response.getOutputStream();
        response.addHeader("content-type", "text/html;charset=UTF-8");
        response.addHeader("filename", URLEncoder.encode(docParam.getpDocFileId().toString(), "UTF-8"));
        response.addHeader("Access-Control-Expose-Headers", "filename");
        rdFileService.export(docParam,out);
    }
}
