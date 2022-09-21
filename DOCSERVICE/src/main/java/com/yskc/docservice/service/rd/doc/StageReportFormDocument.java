package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/15 16:57
 * description:阶段验收评价导出
 */
@Component("StageReportForm_Doc")
@Scope("prototype")
public class StageReportFormDocument extends RDDocument {

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(this.docFile.getData())) {
            map.put("TRLList", new ArrayList<>());
        } else {
            //阶段验收评价数据处理
            map = JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
            if (map.containsKey("members") && (map.get("members") instanceof ArrayList)) {
                //选择多个人员字符串返回
                List<Map<String, String>> list = JsonUtils.jsonToPojo(JsonUtils.objectToJson(map.get("members")), List.class);
                if (!CollectionUtils.isEmpty(list)) {
                    List<String> strs = new ArrayList<>();
                    for (Map<String, String> m : list) {
                        strs.add(m.get("ename"));
                    }
                    map.put("member", String.join(",", strs));
                }
            }

        }
        return map;
    }
}
