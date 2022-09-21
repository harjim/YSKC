package com.yskc.docservice.controller.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.DocParam;
import com.yskc.docservice.service.rd.DataFactory;
import com.yskc.docservice.service.rs.RDFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "", methods = {})
@RestController
@RequestMapping("/doc/rdfile")
public class RDFileController extends RsBaseController {
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
