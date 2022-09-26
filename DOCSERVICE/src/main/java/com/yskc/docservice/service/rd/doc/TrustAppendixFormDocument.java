package com.yskc.docservice.service.rd.doc;

import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/docservice/service/rd/doc
 * @Author: hm
 * @CreateTime: 2022/9/24
 * @Description: 委托资料
 */
@Component("TrustAppendixForm_Doc")
@Scope("prototype")
public class TrustAppendixFormDocument extends RDDocument {

    @Override
    public boolean isAttachment() {
        return true;
    }

/*    @Override
    protected Map getDocMap() throws IOException {
        Map<String, Object> map = new HashMap<>();
        Map jsonMap = this.getJsonMap();
//        map.put("list", this.getAttachments());
        map.put("list", (List)jsonMap.get("list"));
        return map;
    }*/

    @Override
    public List<String> getAttachments() throws IOException {
        List<Map> fPathList = (List<Map>)this.getJsonMap().get("list");
        if (!CollectionUtils.isEmpty(fPathList)) {
            List<String> imgUrls = new ArrayList<>();
            for (Map model : fPathList) {
                String fPath = (String)model.get("filePath");
                String realPath = fPath.replace("/static/", ftlPath);
                String uriPath = fPath.replace("/static/", this.uriFilePath);
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
