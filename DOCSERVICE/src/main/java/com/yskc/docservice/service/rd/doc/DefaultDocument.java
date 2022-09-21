package com.yskc.docservice.service.rd.doc;

import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.context.annotation.Scope;

import java.util.*;

@Scope("prototype")
public class DefaultDocument extends RDDocument {

    @Override
    protected Map getDocMap(ProjectEntity project,Integer currentYear) {
        return null;
    }

}
