package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.service.ExportDocFileService;
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
            if (dataMap.containsKey("members") && (dataMap.get("members") instanceof ArrayList)) {
                //选择多个人员字符串返回
                List<Map<String, String>> list = JsonUtils.jsonToPojo(JsonUtils.objectToJson(dataMap.get("members")), List.class);
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
