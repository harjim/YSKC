package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by hck
 * on 2020/6/15 20:03
 * description:科技成果报告导出
 */
@Component("AchievementReportForm_Doc")
@Scope("prototype")
public class AchievementReportFormDocument extends RDDocument {

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String,Object> map=new HashMap<>();
        if(!StringUtils.isEmpty(this.docFile.getData())) {
            map=JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
            Map<String, Object> achievementMap = new HashMap<>();
            List<String> achievementTypes = JsonUtils.jsonToPojo(JsonUtils.objectToJson(map.get("checkedOptionArr")), List.class);
            List<String> checkachievementTypes = JsonUtils.jsonToPojo(JsonUtils.objectToJson(map.get("proveOptionArr")), List.class);
            for (int i = 0; i < achievementTypes.size(); i++) {
                String key = MessageFormat.format("achievement{0}_chk", i);
                achievementMap.put(key, achievementTypes.get(i));
            }
            for (int i = 0; i < checkachievementTypes.size(); i++) {
                String key = MessageFormat.format("achievementCheck{0}_chk", i);
                achievementMap.put(key, checkachievementTypes.get(i));
            }
            map.putAll(achievementMap);
        }
        return map;
    }

    @Override
    protected Map getDocMap() throws IOException {
        Map jsonMap = getJsonMap();
        Map resultMap = new HashMap<>();
        ArrayList< Boolean > achievementArr = new ArrayList<>();
        if (jsonMap.get("checkedOptionArr") != null) {
            achievementArr = (ArrayList< Boolean >) jsonMap.get("checkedOptionArr");
        }
        ArrayList<Boolean> checkachievementArr = new ArrayList<>();
        if (jsonMap.get("proveOptionArr") != null) {
            checkachievementArr = (ArrayList< Boolean >) jsonMap.get("proveOptionArr");
        }

        for (int i = 0; i < checkachievementArr.size(); i++) {
            if (i < achievementArr.size()) {
                String achievementKey = MessageFormat.format("achievement{0}_chk", i);
                resultMap.put(achievementKey, achievementArr.get(i));
            }
            String achievementCheckKey = MessageFormat.format("achievementCheck{0}_chk", i);
            resultMap.put(achievementCheckKey, checkachievementArr.get(i));
        }
        return resultMap;
    }
}
