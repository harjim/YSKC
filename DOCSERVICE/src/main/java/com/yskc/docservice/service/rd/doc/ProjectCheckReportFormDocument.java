package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.ReviewCommitteeDao;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.project.ProjectEmployeeInfo;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: hck
 * @DateTime: 2021/4/22 15:30
 * @Description:项目验收报告
 */
@Component("ProjectCheckReportForm_Doc")
@Scope("prototype")
public class ProjectCheckReportFormDocument extends RDDocument {

    @Autowired
    private ReviewCommitteeDao reviewCommitteeDao;

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String,Object> map=new HashMap<>();
        if(!StringUtils.isEmpty(this.docFile.getData())) {
            map=JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
            if(map.containsKey("list")){
                List<ProjectEmployeeInfo> infos= JsonUtils.jsonToList(JsonUtils.objectToJson(map.get("list")), ProjectEmployeeInfo.class);
                if(!CollectionUtils.isEmpty(infos)){
                    List<String> enumbers=infos.stream().map(e->e.getEnumber()).collect(Collectors.toList());
                    List<ProjectEmployeeInfo> employeeInfos=reviewCommitteeDao.getReviews(enumbers,project.getBeginYear(),project.getCompanyId());
                    Map<String,ProjectEmployeeInfo> infoMap=new HashMap<>();
                    if(!CollectionUtils.isEmpty(employeeInfos)){
                        infoMap=employeeInfos.stream().collect(Collectors.toMap(e->e.getEnumber(),e->e));
                    }
                    for (ProjectEmployeeInfo info:infos){
                        if(infoMap.containsKey(info.getEnumber())){
                            ProjectEmployeeInfo employeeInfo=infoMap.get(info.getEnumber());
                            info.setDeptName(employeeInfo.getDeptName());
                            info.setPosition(employeeInfo.getPosition());
                            info.setEname(employeeInfo.getEname());
                        }
                    }
                    map.put("list",infos);
                }
            }
        }
        return map;
    }

    @Override
    protected Map getDocMap() throws IOException {
        Map jsonMap = getJsonMap();
        Map resultMap = new HashMap<>();
        if (jsonMap.containsKey("list")) {
            List<ProjectEmployeeInfo> employeeInfoList = JsonUtils.jsonToList(JsonUtils.objectToJson(jsonMap.get("list")), ProjectEmployeeInfo.class);
            List<String> enumbers=employeeInfoList.stream().map(e->e.getEnumber()).collect(Collectors.toList());
            Integer beginYear = this.dataFactory.getProjectInfo().getBeginYear();
            Integer companyId = this.dataFactory.getCompanyInfo().getCompanyId();
            if (!CollectionUtils.isEmpty(employeeInfoList)) {
                List<ProjectEmployeeInfo> employeeInfos = reviewCommitteeDao.getEmployees(enumbers, companyId);
                Map<String,ProjectEmployeeInfo> infoMap=new HashMap<>();
                if(!CollectionUtils.isEmpty(employeeInfos)){
                    infoMap=employeeInfos.stream().collect(Collectors.toMap(e->e.getEnumber(),e->e));
                }
                for (ProjectEmployeeInfo info:employeeInfoList){
                    if(infoMap.containsKey(info.getEnumber())){
                        ProjectEmployeeInfo employeeInfo=infoMap.get(info.getEnumber());
                        info.setDeptName(employeeInfo.getDeptName());
                        info.setPosition(employeeInfo.getPosition());
                        info.setEname(employeeInfo.getEname());
                        info.setAutographUrl(employeeInfo.getAutographUrl());
                    }
                }
                resultMap.put("list",employeeInfoList);

            }
        }
        return resultMap;
    }
}
