package com.yskc.docservice.service.rd.doc;

import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/docservice/service/rd/doc
 * @Author: hm
 * @CreateTime: 2022/9/24
 * @Description: 研发项目备案表
 */
@Component( "ProjectBeianForm_Doc" )
@Scope( "prototype" )
public class ProjectBeianFormDocument extends RDDocument {
    @Override
    public boolean hasDocName() {
        return false;
    }
}
