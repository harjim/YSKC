package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.ReviewCommitteeDao;
import com.yskc.docservice.models.rs.project.ProjectEmployeeInfo;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
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
@Component( "ReviewForm_Doc" )
@Scope( "prototype" )
public class ReviewFormDocument extends RDDocument {

    @Autowired
    private ReviewCommitteeDao reviewCommitteeDao;

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(this.docFile.getData())) {
            map = JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
            Map<String, Object> reviewMap = new HashMap<>();
            List<String> checkTypes = JsonUtils.jsonToPojo(JsonUtils.objectToJson(map.get("checkedOptionArr")), List.class);
            if (!CollectionUtils.isEmpty(checkTypes)) {
                for (int i = 0; i < checkTypes.size(); i++) {
                    String key = MessageFormat.format("reviewCheckType{0}_chk", i);
                    reviewMap.put(key, checkTypes.get(i));
                }
                map.putAll(reviewMap);
            }
            if (map.containsKey("list")) {
                List<ProjectEmployeeInfo> infos = JsonUtils.jsonToList(JsonUtils.objectToJson(map.get("list")), ProjectEmployeeInfo.class);
                if (!CollectionUtils.isEmpty(infos)) {
                    List<String> enumbers = infos.stream().map(e -> e.getEnumber()).collect(Collectors.toList());
                    List<ProjectEmployeeInfo> employeeInfos = reviewCommitteeDao.getReviews(enumbers, project.getBeginYear(), project.getCompanyId());
                    Map<String, ProjectEmployeeInfo> infoMap = new HashMap<>();
                    if (!CollectionUtils.isEmpty(employeeInfos)) {
                        infoMap = employeeInfos.stream().collect(Collectors.toMap(e -> e.getEnumber(), e -> e));
                    }
                    for (ProjectEmployeeInfo pInfo : infos) {
                        if (infoMap.containsKey(pInfo.getEnumber())) {
                            ProjectEmployeeInfo employeeInfo = infoMap.get(pInfo.getEnumber());
                            pInfo.setDeptName(employeeInfo.getDeptName());
                            pInfo.setPosition(employeeInfo.getPosition());
                            pInfo.setEname(employeeInfo.getEname());
                        }
                    }
                    map.put("list", infos);
                }
            }
        } else {
            map.put("list", new ArrayList<>());
        }
        return map;
    }

    @Override
    protected Map getDocMap() throws IOException {
        Map jsonMap = this.getJsonMap();
        Map resultMap = new HashMap<>();
        if (jsonMap.get("checkedOptionArr") != null) {
            ArrayList<Boolean> checkList = (ArrayList<Boolean>)jsonMap.get("checkedOptionArr");
            for (int i = 0; i < checkList.size(); i++) {
                String key = MessageFormat.format("reviewCheckType{0}_chk", i);
                resultMap.put(key, checkList.get(i));
            }
        }
        return resultMap;
    }
}
