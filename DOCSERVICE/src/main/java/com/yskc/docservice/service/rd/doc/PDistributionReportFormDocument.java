package com.yskc.docservice.service.rd.doc;

import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/docservice/service/rd/doc
 * @Author: hm
 * @CreateTime: 2022/9/24
 * @Description: RD人员设备折旧分配说明
 */
@Component("PDistributionReportForm_Doc")
@Scope( "prototype" )
public class PDistributionReportFormDocument extends RDDocument {

    @Override
    public boolean hasDocName() {
        return !this.docFile.getDocTemplateName().equals("PDistributionReportForm_1");
    }
}
