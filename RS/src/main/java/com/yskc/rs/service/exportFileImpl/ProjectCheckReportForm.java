package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.project.ReviewCommitteeDao;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.project.ProjectEmployeeInfo;
import com.yskc.rs.service.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: hck
 * @DateTime: 2021/4/22 15:30
 * @Description:项目验收报告
 */
@Component
public class ProjectCheckReportForm implements ExportDocFileService {

    @Autowired
    private ReviewCommitteeDao reviewCommitteeDao;

    public static ProjectCheckReportForm projectCheckReportForm;

    @PostConstruct
    public void init(){
        projectCheckReportForm=this;
        projectCheckReportForm.reviewCommitteeDao=this.reviewCommitteeDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String,Object> dataMap=new HashMap<>();
        if(!StringUtils.isEmpty(fileData)) {
            dataMap=JsonUtils.jsonToPojo(fileData, Map.class);
            if(dataMap.containsKey("list")){
                List<ProjectEmployeeInfo> infos= JsonUtils.jsonToList(JsonUtils.objectToJson(dataMap.get("list")), ProjectEmployeeInfo.class);
                if(!CollectionUtils.isEmpty(infos)){
                    List<String> enumbers=infos.stream().map(e->e.getEnumber()).collect(Collectors.toList());
                    List<ProjectEmployeeInfo> employeeInfos=projectCheckReportForm.reviewCommitteeDao.getEmployees(enumbers,projectEntity.getCompanyId());
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
                            info.setAutographUrl(employeeInfo.getAutographUrl());
                        }
                    }
                    dataMap.put("list",infos);
                }
            }
        }
        return dataMap;
    }
}
