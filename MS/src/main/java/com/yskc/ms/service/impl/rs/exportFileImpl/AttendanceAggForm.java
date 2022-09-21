package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.dao.rs.ProjectDocFileDao;
import com.yskc.ms.dao.rs.ProjectRdEmployeeDao;
import com.yskc.ms.dao.rs.RsProjectDao;
import com.yskc.ms.entity.rs.ProjectDocFileEntity;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.models.rs.RdEmployeeSummaryModel;
import com.yskc.ms.service.rs.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/6/15 17:37
 * description:阶段人员安排成员导出
 */
@Component
public class AttendanceAggForm implements ExportDocFileService {

    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;

    @Autowired
    private RsProjectDao projectDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;

    public static AttendanceAggForm attendanceAggForm;

    @PostConstruct
    public void init() {
        attendanceAggForm = this;
        attendanceAggForm.projectRdEmployeeDao = this.projectRdEmployeeDao;
        attendanceAggForm.projectDao = this.projectDao;
        attendanceAggForm.projectDocFileDao = this.projectDocFileDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap = new HashMap<>();
        ProjectDocFileEntity docFileEntity = attendanceAggForm.projectDocFileDao.selectById(dataModel.getpDocFileId());
        List<Integer> projectIds = new ArrayList<>();
        if (projectEntity.getHasChild() && projectEntity.getParentId() == 0) {
            List<ProjectEntity> list = attendanceAggForm.projectDao.getChildsById(projectEntity.getId());
            List<Integer> projects = list.stream().map(ProjectEntity::getId).collect(Collectors.toList());
            projectIds.addAll(projects);
        } else {
            projectIds.add(projectEntity.getId());
        }
        List<RdEmployeeSummaryModel> rdEmployeeSummaryModels = new ArrayList<>();
        if (StringUtils.isEmpty(fileData) && docFileEntity.getMonth() != null) {
            rdEmployeeSummaryModels = getData(projectEntity, projectIds, docFileEntity.getMonth());
            dataMap.put("employeeMonth", cn.hutool.core.date.DateUtil.format(docFileEntity.getMonth(), "yyyy年MM"));
        } else if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
            String dateStr = (String) dataMap.get("employeeMonth");
            if (!StringUtils.isEmpty(dateStr)) {
                Date date = DateUtil.getDateByString(dateStr + "-01 00:00:00");
                dataMap.put("employeeMonth", dateStr.replace("-", "年"));
                rdEmployeeSummaryModels = getData(projectEntity, projectIds, date);
            } else if (docFileEntity.getMonth() != null) {
                rdEmployeeSummaryModels = getData(projectEntity, projectIds, docFileEntity.getMonth());
                dataMap.put("employeeMonth", cn.hutool.core.date.DateUtil.format(docFileEntity.getMonth(), "yyyy年MM"));
            }

        }
        dataMap.put("employeeList", rdEmployeeSummaryModels);
        return dataMap;
    }

    private List<RdEmployeeSummaryModel> getData(ProjectEntity projectEntity, List<Integer> projectIds, Date month) {
        Integer year = cn.hutool.core.date.DateUtil.year(month);
        List<RdEmployeeSummaryModel> rdEmployeeSummaryModels = attendanceAggForm.projectRdEmployeeDao.queryEmployeeList(projectEntity.getCompanyId(), projectIds, month, year);
        int num = 0;
        if (!CollectionUtils.isEmpty(rdEmployeeSummaryModels)) {
            for (RdEmployeeSummaryModel rde : rdEmployeeSummaryModels) {
                num += 1;
                rde.setNum(num);
            }
        }
        return rdEmployeeSummaryModels;
    }

//    @Override
//    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
//        Map<String, Object> dataMap = new HashMap<>();
//        List<RdEmployeeSummaryModel> rdEmployeeSummaryModels = new ArrayList<>();
//        if (!StringUtils.isEmpty(fileData)) {
//            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
//            String dateStr = (String) dataMap.get("employeeMonth");
//            if (!StringUtils.isEmpty(dateStr)) {
//                Date date = DateUtil.getDateByString(dateStr + "-01 00:00:00");
//                List<Integer> projectIds = new ArrayList<>();
//                if (projectEntity.getHasChild() && projectEntity.getParentId() == 0) {
//                    List<ProjectEntity> list = attendanceAggForm.projectDao.getChildsById(projectEntity.getId());
//                    List<Integer> projects = list.stream().map(ProjectEntity::getId).collect(Collectors.toList());
//                    projectIds.addAll(projects);
//                } else {
//                    projectIds.add(projectEntity.getId());
//                }
//                rdEmployeeSummaryModels = attendanceAggForm.projectRdEmployeeDao.queryEmployeeList(projectEntity.getCompanyId(), projectIds, DateUtil.getMonthFirstDay(date), projectEntity.getBeginYear());
//                int num = 0;
//                if (!CollectionUtils.isEmpty(rdEmployeeSummaryModels)) {
//                    for (RdEmployeeSummaryModel rde : rdEmployeeSummaryModels) {
//                        num += 1;
//                        rde.setNum(num);
//                    }
//                }
//                dataMap.put("employeeMonth", dateStr.replace("-", "年"));
//            }
//        }
//        dataMap.put("employeeList", rdEmployeeSummaryModels);
//
//        return dataMap;
//    }
}
