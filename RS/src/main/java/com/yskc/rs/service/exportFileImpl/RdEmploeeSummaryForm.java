package com.yskc.rs.service.exportFileImpl;

import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.StageDao;
import com.yskc.rs.dao.project.ProjectRdEmployeeDao;
import com.yskc.rs.entity.StageEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.document.DataModel;
import com.yskc.rs.models.employee.RdEmployeeSummaryModel;
import com.yskc.rs.service.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/6/15 17:52
 * description:年度员工统计
 */
@Component
public class RdEmploeeSummaryForm implements ExportDocFileService {

    @Autowired
    private ProjectRdEmployeeDao projectRdEmployeeDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private StageDao stageDao;

    public static RdEmploeeSummaryForm rdEmploeeSummaryForm;

    @PostConstruct
    public void init() {
        rdEmploeeSummaryForm = this;
        rdEmploeeSummaryForm.projectRdEmployeeDao = this.projectRdEmployeeDao;
        rdEmploeeSummaryForm.projectDao = this.projectDao;
        rdEmploeeSummaryForm.stageDao = this.stageDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        Map<String, Object> dataMap;
        if (StringUtils.isEmpty(fileData)) {
            dataMap = new HashMap<>();
        } else {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
        }
        dataMap.put("employeeYearList", countRdEmployee(projectEntity.getCompanyId(), projectEntity,dataModel.getpDocFileId()));
        return dataMap;
    }

    /**
     * 统计人员列表
     *
     * @param companyId
     * @param projectEntity
     * @return
     */
    private List<RdEmployeeSummaryModel> countRdEmployee(Integer companyId, ProjectEntity projectEntity,Integer pDocFileId) {
        List<Integer> projectIds = new ArrayList<>();
        if (projectEntity.getParentId() == 0 && projectEntity.getHasChild()) {
            List<ProjectEntity> entityList = rdEmploeeSummaryForm.projectDao.getChildsById(projectEntity.getId());
            if (!CollectionUtils.isEmpty(entityList)) {
                List<Integer> projects = entityList.stream().map(ProjectEntity::getId).collect(Collectors.toList());
                projectIds.addAll(projects);
            } else {
                return new ArrayList<>();
            }
        } else {
            projectIds.add(projectEntity.getId());
        }
        StageEntity stage=rdEmploeeSummaryForm.stageDao.getStageInfo(projectEntity.getId(),pDocFileId);
        Integer year= cn.hutool.core.date.DateUtil.year(stage!=null && stage.getBeginDate()!=null?stage.getBeginDate():projectEntity.getBeginDate());
        List<RdEmployeeSummaryModel> rdEmployeeSummaryModels = rdEmploeeSummaryForm.projectRdEmployeeDao.queryListByYear(companyId, projectIds,year);
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
}
