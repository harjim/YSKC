package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.dao.rs.ReviewCommitteeDao;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.models.rs.ProjectEmployeeInfo;
import com.yskc.ms.service.rs.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/6/15 19:54
 * description:立项评审报告
 */
@Component
public class ReviewForm implements ExportDocFileService {
    @Autowired
    private ReviewCommitteeDao reviewCommitteeDao;

    public static ReviewForm reviewForm;

    @PostConstruct
    public void init() {
        reviewForm = this;
        reviewForm.reviewCommitteeDao = this.reviewCommitteeDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
            Map<String, Object> reviewMap = new HashMap<>();
            List<String> checkTypes = JsonUtils.jsonToPojo(JsonUtils.objectToJson(dataMap.get("checkedOptionArr")), List.class);
            if (!CollectionUtils.isEmpty(checkTypes)) {
                for (int i = 0; i < checkTypes.size(); i++) {
                    String key = MessageFormat.format("reviewCheckType{0}_chk", i);
                    reviewMap.put(key, checkTypes.get(i));
                }
                dataMap.putAll(reviewMap);
            }
            if (dataMap.containsKey("list")) {
                List<ProjectEmployeeInfo> infos = JsonUtils.jsonToList(JsonUtils.objectToJson(dataMap.get("list")), ProjectEmployeeInfo.class);
                if (!CollectionUtils.isEmpty(infos)) {
                    List<String> enumbers = infos.stream().map(e -> e.getEnumber()).collect(Collectors.toList());
                    List<ProjectEmployeeInfo> employeeInfos = reviewForm.reviewCommitteeDao.getReviews(enumbers, projectEntity.getBeginYear(), projectEntity.getCompanyId());
                    Map<String, ProjectEmployeeInfo> infoMap = new HashMap<>();
                    if (!CollectionUtils.isEmpty(employeeInfos)) {
                        infoMap = employeeInfos.stream().collect(Collectors.toMap(e -> e.getEnumber(), e -> e));
                    }
                    for (ProjectEmployeeInfo info : infos) {
                        if (infoMap.containsKey(info.getEnumber())) {
                            ProjectEmployeeInfo employeeInfo = infoMap.get(info.getEnumber());
                            info.setDeptName(employeeInfo.getDeptName());
                            info.setPosition(employeeInfo.getPosition());
                            info.setEname(employeeInfo.getEname());
                        }
                    }
                    dataMap.put("list", infos);
                }
            }
        } else {
            dataMap.put("list", new ArrayList<>());
        }
        return dataMap;
    }

//    @Override
//    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
//        Map<String, Object> dataMap = new HashMap<>();
//        if (!StringUtils.isEmpty(fileData)) {
//            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
//            Map<String, Object> reviewMap = new HashMap<>();
//            List<String> checkTypes = JsonUtils.jsonToPojo(JsonUtils.objectToJson(dataMap.get("checkedOptionArr")), List.class);
//            if (!CollectionUtils.isEmpty(checkTypes)) {
//                for (int i = 0; i < checkTypes.size(); i++) {
//                    String key = MessageFormat.format("reviewCheckType{0}_chk", i);
//                    reviewMap.put(key, checkTypes.get(i));
//                }
//            }
//            dataMap.putAll(reviewMap);
//        }
//        return dataMap;
//    }
}
