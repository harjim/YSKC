package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.service.rs.ExportDocFileService;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/15 16:48
 * description:会议纪要
 */
@Component
public class MeetingForm implements ExportDocFileService {

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        //会议纪要修改主题,会议类别
        Map<String, Object> dataMap = new HashMap<>();
        if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
            dataMap.put("meetingTitle", dataModel.getDocName());
            List<String> meetTypes = JsonUtils.jsonToPojo(JsonUtils.objectToJson(dataMap.get("checkedOptionAry")), List.class);
            Map<String, Object> meetingTypeMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(meetTypes)) {
                for (int i = 0; i < meetTypes.size(); i++) {
                    String key = MessageFormat.format("type{0}_chk", i);
                    meetingTypeMap.put(key, meetTypes.get(i));
                }
            }
            List<String> readTypes = JsonUtils.jsonToPojo(JsonUtils.objectToJson(dataMap.get("readOptionAry")), List.class);
            if (!CollectionUtils.isEmpty(readTypes)) {
                for (int i = 0; i < readTypes.size(); i++) {
                    String key = MessageFormat.format("readType{0}_chk", i);
                    meetingTypeMap.put(key, readTypes.get(i));
                }
            }
            dataMap.putAll(meetingTypeMap);
        }
        if (dataMap.containsKey("members")) {
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
        return dataMap;
    }
}
