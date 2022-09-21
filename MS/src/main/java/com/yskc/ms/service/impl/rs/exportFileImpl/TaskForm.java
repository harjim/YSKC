package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.service.rs.ExportDocFileService;
import com.yskc.ms.service.rs.ProjectDocFileDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hck
 * on 2020/6/23 11:52
 * description:项目任务书
 */
@Component
public class TaskForm implements ExportDocFileService {
    @Autowired
    private ProjectDocFileDataService projectDocFileDataService;

    public static TaskForm taskForm;

    @PostConstruct
    public void init() {
        taskForm = this;
        taskForm.projectDocFileDataService = this.projectDocFileDataService;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        String memberStr = taskForm.projectDocFileDataService.getMemberStr(projectEntity.getId(), dataModel.getpDocFileId());
        if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
        }
        if (!dataMap.containsKey("allData")) {
            dataMap.put("allData", new ArrayList<>());
        }
        if (dataMap.containsKey("beginDate")) {
            String startDate = DateUtil.getDateTime(projectEntity.getBeginDate(), "yyyy年MM月");
            dataMap.put("beginDate", startDate);
//      dataMap.put("beginDate", MessageFormat.format("{0}年{1}月", dataMap.get("beginDate").toString().split(Constant.HYPHEN)));
        }
        if (dataMap.containsKey("endDate")) {
            String endDate = DateUtil.getDateTime(projectEntity.getEndDate(), "yyyy年MM月");
            dataMap.put("endDate", endDate);
//      dataMap.put("endDate", MessageFormat.format("{0}年{1}月", dataMap.get("endDate").toString().split(Constant.HYPHEN)));
        }
        dataMap.put("memberStr", memberStr);
        return dataMap;
    }
}
