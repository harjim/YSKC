package com.yskc.ms.service.impl.rs.exportFileImpl;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.rs.*;
import com.yskc.ms.entity.rs.ProjectEntity;
import com.yskc.ms.entity.rs.RsStageEntity;
import com.yskc.ms.enums.ProjectTypeEnum;
import com.yskc.ms.models.rs.DataModel;
import com.yskc.ms.models.rs.InitMemberListModel;
import com.yskc.ms.models.rs.summary.InitEquipmentModel;
import com.yskc.ms.service.rs.ExportDocFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by hck
 * on 2020/6/15 17:26
 * description:项目立项报告导出
 */
@Component
public class ProjectReportForm implements ExportDocFileService {

    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private RsProjectDao projectDao;
    @Autowired
    private RsStageDao stageDao;
    @Autowired
    private InitEquipmentDao initEquipmentDao;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    public static ProjectReportForm projectReportForm;

    @PostConstruct
    public void init() {
        projectReportForm = this;
        projectReportForm.initMemberDao = this.initMemberDao;
        projectReportForm.msConfig = this.msConfig;
        projectReportForm.projectDao = this.projectDao;
        projectReportForm.stageDao = this.stageDao;
        projectReportForm.initEquipmentDao = this.initEquipmentDao;
    }

    @Override
    public Map<String, Object> exportDocFile(ProjectEntity projectEntity, Integer currentYear, String fileData, DataModel dataModel) {
        List<Integer> memberIds = null;
        Integer projectId = projectEntity.getId();
        Integer companyId = projectEntity.getCompanyId();
        Map<String, Object> dataMap = new HashMap<>();
        if (!StringUtils.isEmpty(fileData)) {
            dataMap = JsonUtils.jsonToPojo(fileData, Map.class);
//            List<Integer> projectIds=new ArrayList<>();
//            //大项目获取所有子项目成员列表
//            if(projectEntity.getHasChild()&&projectEntity.getParentId()==0){
//                List<ProjectEntity> projectEntities=projectReportForm.projectDao.getChildsById(projectId);
//                if(!CollectionUtils.isEmpty(projectEntities)){
//                   List<Integer> projects=projectEntities.stream().map(ProjectEntity::getId).collect(Collectors.toList());
//                   projectIds.addAll(projects);
//                }
//            }else {
//                projectIds.add(projectId);
//            }
            String templateName = dataModel.getTemplateName();
            if ("ProjectReportForm_5".equals(dataModel.getTemplateName())) {
                StringJoiner tecIndustry = new StringJoiner("/");
                if (null != projectEntity.getTecIndustry() && !projectEntity.getTecIndustry().isEmpty()) {
                    String[] s = projectEntity.getTecIndustry().split(",");
                    List<Integer> list = new ArrayList<>();
                    for (String str : s) {
                        list.add(Integer.valueOf(str));
                    }
                    List<String> tecList = sysDictionaryDao.getHighTecIndustry(list);
                    for (String tec : tecList) {
                        tecIndustry.add(tec);
                    }
                    dataMap.put("tecIndustry", tecIndustry.toString());
                }
            }

            if (dataMap.containsKey("memberList")) {
                memberIds = (List<Integer>) dataMap.get("memberList");
            }

            if (dataMap.containsKey("img")) {
                //保存图片
                Map<String, Object> imgMap = JsonUtils.jsonToPojo(JsonUtils.objectToJson(dataMap.get("img")), Map.class);
                Set<String> set = imgMap.keySet();
                for (String str : set) {
                    List<Map<String, String>> imgListMap = JsonUtils.jsonToPojo(JsonUtils.objectToJson(imgMap.get(str)), List.class);
                    List<String> imgList = new ArrayList<>();
                    for (Map<String, String> mapList : imgListMap) {
                        String path = mapList.get("url");
                        imgList.add(path);
                    }
                    dataMap.put("p" + str, imgList);
                }
            }
        }
        Integer formula = projectEntity.getFormula();
        if (null != formula) {
            Map<Integer, String> enumMap = new HashMap<>();
            for (ProjectTypeEnum value : ProjectTypeEnum.values()) {
                enumMap.put(value.getType(), value.getTypeName());
            }
            dataMap.put("formula", enumMap.get(formula));
        }
        String startDate = DateUtil.getDateTime(projectEntity.getBeginDate(), "yyyy年MM月");
        String endDate = DateUtil.getDateTime(projectEntity.getEndDate(), "yyyy年MM月");
        String projectPeriod = MessageFormat.format("{0} 至 {1}", startDate, endDate);
        dataMap.put("projectPeriod", projectPeriod);//项目起始时间
        RsStageEntity stage = projectReportForm.stageDao.getStageInfo(projectId, dataModel.getpDocFileId());
        Integer year = cn.hutool.core.date.DateUtil.year(stage != null && stage.getBeginDate() != null ? stage.getBeginDate() : projectEntity.getBeginDate());
        List<InitMemberListModel> initMemberListModels = projectReportForm.initMemberDao.getStaffInfoList(projectId, companyId, year, memberIds);
        if (!CollectionUtils.isEmpty(initMemberListModels)) {
            int num = 0;
            for (InitMemberListModel initMember : initMemberListModels) {
                num += 1;
                initMember.setId(num);
                if (initMember.getEdate() != null) {
                    initMember.setEstrDate(DateUtil.getDateTime(initMember.getEdate(), ""));
                } else {
                    initMember.setEstrDate("-");
                }

                Date birthday = initMember.getBirthday();
                if (null != birthday && birthday.before(projectEntity.getBeginDate())) {
                    initMember.setAge(cn.hutool.core.date.DateUtil.age(birthday, projectEntity.getBeginDate()));
                }
                String gender = initMember.getGender();
                if (StringUtils.hasLength(gender)) {
                    initMember.setGender(gender.equals("1") ? "女" : "男");
                }
                String etype = initMember.getEtype();
                if (StringUtils.hasLength(etype)) {
                    switch (etype) {
                        case "1":
                            initMember.setEtype("研究人员");
                            break;
                        case "2":
                            initMember.setEtype("技术人员");
                            break;
                        case "3":
                            initMember.setEtype("辅助人员");
                        default:
                            break;
                    }
                }
            }
        }
        dataMap.put("mList", initMemberListModels);
        List<InitEquipmentModel> equipmentModels = initEquipmentDao.getEquList(companyId, currentYear, projectEntity.getId());
        if (!CollectionUtils.isEmpty(equipmentModels)) {
            for (InitEquipmentModel equipmentModel : equipmentModels) {
                equipmentModel.setTotalPrice(new BigDecimal(equipmentModel.getUnitPrice().toString()).multiply(new BigDecimal(equipmentModel.getQuantity().toString())));
            }
        }
        dataMap.put("eList", equipmentModels);
        CostBudgetForm costBudgetForm = new CostBudgetForm();
        // Map<String, Object> budgetMap = costBudgetForm.getProjectBudget(projectEntity);
        Map<String, Map<String, Object>> budgetMap = costBudgetForm.getYearBudget(projectEntity, projectEntity.getEndYear());
        if (budgetMap != null) {
            dataMap.putAll(budgetMap);
        }
        dataMap.put("budgetDetail", dataModel.getBudgetDetail());
        return dataMap;
    }
}
