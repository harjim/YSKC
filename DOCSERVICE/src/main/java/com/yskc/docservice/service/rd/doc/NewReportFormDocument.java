package com.yskc.docservice.service.rd.doc;

import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.dao.rs.project.DocFileAttachmentDao;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/docservice/service/rd/doc
 * @Author: hm
 * @CreateTime: 2022/9/24
 * @Description: 查新报告
 */
@Component("NewReportForm_Doc")
@Scope("prototype")
public class NewReportFormDocument extends RDDocument {
    @Autowired
    private DocFileAttachmentDao docFileAttachmentDao;

    @Override
    protected Map getDocMap() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("newReports", this.getAttachments());
        return map;
    }

    @Override
    public boolean isAttachment() {
        return true;
    }

    @Autowired
    private DocServiceConfig docServiceConfig;
    @Override
    public List<String> getAttachments() throws IOException {
        // FIXME: 修改自己的文件地址
        List<Map> fPathList = (List<Map>)this.getJsonMap().get("list");
        if (!CollectionUtils.isEmpty(fPathList)) {
            List<String> imgUrls = new ArrayList<>();
            for (Map model : fPathList) {
                String fPath = (String)model.get("filePath");
                String realPath = docServiceConfig.getMsImportPath() + System.getProperty("file.separator") + fPath;
                String uriPath = "file:///" + docServiceConfig.getMsImportPath() + System.getProperty("file.separator") + fPath;
                String imgPath = this.pdfToImg(fPath, realPath, uriPath);
                if (StringUtils.hasText(imgPath)) {
                    imgUrls.add(imgPath);
                }
            }
            return imgUrls;
        }
        return null;
    }

}
