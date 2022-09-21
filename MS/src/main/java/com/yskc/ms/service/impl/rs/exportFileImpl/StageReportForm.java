package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.service.rs.ExportDocFileService;
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
@Component
public class StageReportForm implements ExportDocFileService {

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        if (StringUtils.isEmpty(fileData)) {
            dataMap.put("TRLList", new ArrayList<>());
        } else {
            //阶段验收评价数据处理
            List<Map<String, Object>> trlMaps = new ArrayList<>();
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
            if (dataMap.containsKey("TRLs")) {
                List<Map<String, Object>> trLs = JsonUtils.jsonToPojo(JsonUtils.objectToJson(dataMap.get("TRLs")), List.class);
                for (Map<String, Object> trl : trLs) {
                    List<Map<String, Object>> rows = JsonUtils.jsonToPojo(JsonUtils.objectToJson(trl.get("row")), List.class);
                    for (Map<String, Object> row : rows) {
                        Map<String, Object> trlMap = new HashMap<>();
                        trlMap.put("TRLName", trl.get("TRL"));
                        trlMap.put("rowTitle", row.get("title"));
                        trlMap.put("rowWeight", row.get("weight"));
                        trlMap.put("rowV", row.get("v"));
                        trlMaps.add(trlMap);
                    }
                }
            }
            dataMap.put("TRLList", trlMaps);
            if (dataMap.containsKey("members") && (dataMap.get("members") instanceof ArrayList)) {
                //选择多个人员字符串返回
                List<Map<String, String>> list = (List<Map<String, String>>) dataMap.get("members");
                if (!CollectionUtils.isEmpty(list)) {
                    List<String> strs = new ArrayList<>();
                    for (Map<String, String> m : list) {
                        strs.add(m.get("ename"));
                    }
                    dataMap.put("member", String.join(",", strs));
                }
            }

        }
        return dataMap;
    }
}
