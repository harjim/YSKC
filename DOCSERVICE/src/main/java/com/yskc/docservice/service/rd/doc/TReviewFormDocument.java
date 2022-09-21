package com.yskc.docservice.service.rd.doc;

import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/16 10:48
 * description:评审报告导出
 */
@Component("TReviewForm_Doc")
@Scope("prototype")
public class TReviewFormDocument extends RDDocument {

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(this.docFile.getData())) {
            map.put("list", new ArrayList<>());
        }
        return map;
    }
}
