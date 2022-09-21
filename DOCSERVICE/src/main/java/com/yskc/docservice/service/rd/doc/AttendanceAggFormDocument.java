package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.dao.rs.project.ProjectDocFileDao;
import com.yskc.docservice.dao.rs.project.ProjectRdEmployeeDao;
import com.yskc.docservice.entity.rs.StageEntity;
import com.yskc.docservice.entity.rs.project.ProjectDocFileEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.RdEmployeeSummaryModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/6/15 17:37
 * description:研发人员工时记录
 */
@Component("AttendanceAggForm_Doc")
@Scope("prototype")
public class AttendanceAggFormDocument extends RDDocument {

    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;

    @Override
    protected Map getDocMap() {
        Map<String, Object> map = new HashMap<>();
        ProjectEntity projectInfo = this.dataFactory.getProjectInfo();
        List<Integer> projectIds = new ArrayList<>();
        if (projectInfo.getHasChild() && projectInfo.getParentId() == 0) {
            List<ProjectEntity> children = projectDao.getChildrenById(projectInfo.getId());
            for (ProjectEntity p :
                    children) {
                projectIds.add(p.getId());
            }
        } else {
            projectIds.add(projectInfo.getId());
        }
        Date month = this.getDocDate();
        int y = month.getYear() + 1900;
        List<RdEmployeeSummaryModel> rdEmployeeSummaryModels = projectRdEmployeeDao.queryEmployees(projectInfo.getCompanyId(), projectIds, month, y);
        int num = 0;
        if (!CollectionUtils.isEmpty(rdEmployeeSummaryModels)) {
            for (RdEmployeeSummaryModel rde : rdEmployeeSummaryModels) {
                num += 1;
                rde.setNum(num);
            }
        }
        map.put("employeeList", rdEmployeeSummaryModels);
        return map;
    }

    @Override
    protected Date getDocDate() {
        if (this.docFile.getMonth() != null) {
            return this.docFile.getMonth();
        } else if (this.getJsonMap() != null) {
            String dStr = (String) this.getJsonMap().get("employeeMonth");
            if (StringUtils.hasLength(dStr)) {
                return DateUtil.getDateByString(dStr + "-01 00:00:00");
            }
        }
        StageEntity stage = this.getStage();
        if (stage != null && stage.getBeginDate() != null) {
            return stage.getBeginDate();
        } else {
            return this.dataFactory.getProjectInfo().getBeginDate();
        }
    }

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String, Object> map = new HashMap<>();
        ProjectDocFileEntity docFileEntity = projectDocFileDao.selectById(this.docFile.getId());
        List<Integer> projectIds = new ArrayList<>();
        if (project.getHasChild() && project.getParentId() == 0) {
            List<ProjectEntity> list = projectDao.getChildrenById(project.getId());
            List<Integer> projects = list.stream().map(ProjectEntity::getId).collect(Collectors.toList());
            projectIds.addAll(projects);
        } else {
            projectIds.add(project.getId());
        }
        List<RdEmployeeSummaryModel> rdEmployeeSummaryModels = new ArrayList<>();
        if (StringUtils.isEmpty(this.docFile.getData()) && docFileEntity.getMonth() != null) {
            rdEmployeeSummaryModels = getData(project, projectIds, docFileEntity.getMonth());
            map.put("employeeMonth", cn.hutool.core.date.DateUtil.format(docFileEntity.getMonth(), "yyyy年MM"));
        } else if (!StringUtils.isEmpty(this.docFile.getData())) {
            map = JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
            String dateStr = (String) map.get("employeeMonth");
            if (!StringUtils.isEmpty(dateStr)) {
                Date date = DateUtil.getDateByString(dateStr + "-01 00:00:00");
                map.put("employeeMonth", dateStr.replace("-", "年"));
                rdEmployeeSummaryModels = getData(project, projectIds, date);
            } else if (docFileEntity.getMonth() != null) {
                rdEmployeeSummaryModels = getData(project, projectIds, docFileEntity.getMonth());
                map.put("employeeMonth", cn.hutool.core.date.DateUtil.format(docFileEntity.getMonth(), "yyyy年MM"));
            }

        }
        Boolean isShow = true;
        if (!CollectionUtils.isEmpty(rdEmployeeSummaryModels)) {
            int signNum = 0;
            for (RdEmployeeSummaryModel model : rdEmployeeSummaryModels) {
                if (model.getMonthWorkOurs() == null || model.getMonthWorkOurs().compareTo(BigDecimal.ZERO) == 0) {
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
        map.put("employeeList", rdEmployeeSummaryModels);
        map.put("isShow", isShow);
        return map;
    }

    private List<RdEmployeeSummaryModel> getData(ProjectEntity projectEntity, List<Integer> projectIds, Date month) {
        Integer year = cn.hutool.core.date.DateUtil.year(month);
        List<RdEmployeeSummaryModel> rdEmployeeSummaryModels = projectRdEmployeeDao.queryEmployees(projectEntity.getCompanyId(), projectIds, month, year);
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
