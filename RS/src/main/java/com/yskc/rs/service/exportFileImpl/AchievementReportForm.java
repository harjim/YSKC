package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.service.ExportDocFileService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/15 20:03
 * description:科技成果报告导出
 */
@Component
public class AchievementReportForm implements ExportDocFileService {

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String,Object> dataMap=new HashMap<>();
        if(!StringUtils.isEmpty(fileData)) {
            dataMap=JsonUtils.jsonToPojo(fileData, Map.class);
            Map<String, Object> achievementMap = new HashMap<>();
            List<String> achievementTypes = JsonUtils.jsonToPojo(JsonUtils.objectToJson(dataMap.get("checkedOptionArr")), List.class);
            List<String> checkachievementTypes = JsonUtils.jsonToPojo(JsonUtils.objectToJson(dataMap.get("proveOptionArr")), List.class);
            for (int i = 0; i < achievementTypes.size(); i++) {
                String key = MessageFormat.format("achievement{0}_chk", i);
                achievementMap.put(key, achievementTypes.get(i));
            }
            for (int i = 0; i < checkachievementTypes.size(); i++) {
                String key = MessageFormat.format("achievementCheck{0}_chk", i);
                achievementMap.put(key, checkachievementTypes.get(i));
            }
            dataMap.putAll(achievementMap);
        }
        return dataMap;
    }
}
