package com.yskc.rs.service.exportFileImpl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.ProjectDocFileDao;
import com.yskc.rs.dao.project.ProjectRdEmployeeDao;
import com.yskc.rs.entity.project.ProjectDocFileEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.employee.RdEmployeeSummaryModel;
import com.yskc.rs.service.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/6/15 17:37
 * description:研发人员工时记录
 */
@Component
public class AttendanceAggForm implements ExportDocFileService {

    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;

    @Autowired
    private ProjectDao projectDao;
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
        Boolean isShow = true;
        if (!CollectionUtils.isEmpty(rdEmployeeSummaryModels)) {
            int signNum = 0;
            for (RdEmployeeSummaryModel model : rdEmployeeSummaryModels) {
                if (model.getMonthWorkOurs() == null || model.getMonthWorkOurs().compareTo(BigDecimal.ZERO)==0) {
                    signNum += 1;
                }
            }
            if (rdEmployeeSummaryModels.size() <= 2 && signNum > 0) {
                isShow = false;
            }
            if (signNum > 2) {
                isShow = false;
            }
        }
        dataMap.put("employeeList", rdEmployeeSummaryModels);
        dataMap.put("isShow", isShow);
        return dataMap;
    }

    private List<RdEmployeeSummaryModel> getData(ProjectEntity projectEntity, List<Integer> projectIds, Date month) {
        Integer year = cn.hutool.core.date.DateUtil.year(month);
        List<RdEmployeeSummaryModel> rdEmployeeSummaryModels = attendanceAggForm.projectRdEmployeeDao.queryEmployees(projectEntity.getCompanyId(), projectIds, month, year);
        int num = 0;
        if (!CollectionUtils.isEmpty(rdEmployeeSummaryModels)) {
            for (RdEmployeeSummaryModel rde : rdEmployeeSummaryModels) {
                num += 1;
                rde.setNum(num);
            }
        }
        return rdEmployeeSummaryModels;
    }
}
