package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.StageDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.dao.rs.project.ProjectRdEmployeeDao;
import com.yskc.docservice.entity.rs.StageEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.RdEmployeeSummaryModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/6/15 17:52
 * description:年度员工统计
 */
@Component("RdEmploeeSummaryForm_Doc")
@Scope("prototype")
public class RdEmploeeSummaryFormDocument extends RDDocument {

    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private StageDao stageDao;

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String, Object> dataMap;
        if (StringUtils.isEmpty(this.docFile.getData())) {
            dataMap = new HashMap<>();
        } else {
            dataMap = JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
        }
        dataMap.put("employeeYearList", countRdEmployee(project.getCompanyId(), project,this.docFile.getId()));
        return dataMap;
    }

    /**
     * 统计人员列表
     *
     * @param companyId
     * @param projectEntity
     * @return
     */
    private List<RdEmployeeSummaryModel> countRdEmployee(Integer companyId, ProjectEntity projectEntity, Integer pDocFileId) {
        List<Integer> projectIds = new ArrayList<>();
        if (projectEntity.getParentId() == 0 && projectEntity.getHasChild()) {
            List<ProjectEntity> entityList = projectDao.getChildrenById(projectEntity.getId());
            if (!CollectionUtils.isEmpty(entityList)) {
                List<Integer> projects = entityList.stream().map(ProjectEntity::getId).collect(Collectors.toList());
                projectIds.addAll(projects);
            } else {
                return new ArrayList<>();
            }
        } else {
            projectIds.add(projectEntity.getId());
        }
        StageEntity stage=stageDao.getStageInfo(projectEntity.getId(),pDocFileId);
        Integer year= cn.hutool.core.date.DateUtil.year(stage!=null && stage.getBeginDate()!=null?stage.getBeginDate():projectEntity.getBeginDate());
        List<RdEmployeeSummaryModel> rdEmployeeSummaryModels = projectRdEmployeeDao.queryListByYear(companyId, projectIds,year);
        if (!CollectionUtils.isEmpty(rdEmployeeSummaryModels)) {
            int seq = 0;
            for (RdEmployeeSummaryModel rde : rdEmployeeSummaryModels) {
                seq += 1;
                rde.setNum(seq);
            }
        } else {
            rdEmployeeSummaryModels = new ArrayList<>();
        }
        return rdEmployeeSummaryModels;
    }

    @Override
    protected Map getDocMap() throws IOException {
        Map resultMap = new HashMap<>();
        resultMap.put("employeeYearList", countRdEmployee(dataFactory.getProjectInfo().getCompanyId(), dataFactory.getProjectInfo(), this.docFile.getId()));
        return resultMap;
    }
}
