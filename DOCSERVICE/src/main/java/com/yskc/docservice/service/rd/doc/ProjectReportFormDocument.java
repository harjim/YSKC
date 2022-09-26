package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.StageDao;
import com.yskc.docservice.dao.rs.SysDictionaryDao;
import com.yskc.docservice.dao.rs.init.InitEquipmentDao;
import com.yskc.docservice.dao.rs.init.InitMemberDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.entity.rs.StageEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.enums.ProjectTypeEnum;
import com.yskc.docservice.models.rs.initequipment.InitEquipmentModel;
import com.yskc.docservice.models.rs.initmember.InitMemberListModel;
import com.yskc.docservice.models.rs.project.ProjectDetailModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * on 2020/6/15 17:26
 * description:项目立项报告
 *
 * @author hck
 */
@Component( "ProjectReportForm_Doc" )
@Scope( "prototype" )
public class ProjectReportFormDocument extends RDDocument {

    @Resource
    private ApplicationContext applicationContext;

    @Autowired
    private SysDictionaryDao sysDictionaryDao;
    @Autowired
    private StageDao stageDao;
    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private InitEquipmentDao initEquipmentDao;

    @Override
    public boolean hasDocName() {
        return false;
    }

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        String fileData = this.docFile.getData();
        List<Integer> memberIds = null;
        Integer projectId = project.getId();
        Integer companyId = project.getCompanyId();
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(fileData)) {
            if (map.containsKey("memberList")) {
                memberIds = (List<Integer>)map.get("memberList");
            }

            if (map.containsKey("img")) {
                //保存图片
                Map<String, Object> imgMap = JsonUtils.jsonToPojo(JsonUtils.objectToJson(map.get("img")), Map.class);
                Set<String> set = imgMap.keySet();
                for (String str : set) {
                    List<Map<String, String>> imgListMap = JsonUtils.jsonToPojo(JsonUtils.objectToJson(imgMap.get(str)), List.class);
                    List<String> imgList = new ArrayList<>();
                    for (Map<String, String> mapList : imgListMap) {
                        String path = mapList.get("url");
                        imgList.add(path);
                    }
                    map.put("p" + str, imgList);
                }
            }
        }
        if ("ProjectReportForm_5".equals(this.docFile.getDocTemplateName())) {
            StringJoiner tecIndustry = new StringJoiner("/");
            if (null != project.getTecIndustry() && !project.getTecIndustry().isEmpty()) {
                String[] s = project.getTecIndustry().split(",");
                List<Integer> list = new ArrayList<>();
                for (String str : s) {
                    list.add(Integer.valueOf(str));
                }
                List<String> tecList = sysDictionaryDao.getHighTecIndustry(list);
                for (String tec : tecList) {
                    tecIndustry.add(tec);
                }
            }
            map.put("tecIndustry", tecIndustry.toString());
        }
        String startDate = DateUtil.getDateTime(project.getBeginDate(), "yyyy年MM月");
        String endDate = DateUtil.getDateTime(project.getEndDate(), "yyyy年MM月");
        String projectPeriod = MessageFormat.format("{0} 至 {1}", startDate, endDate);
        map.put("projectPeriod", projectPeriod);//项目起始时间
        StageEntity stage = stageDao.getStageInfo(projectId, this.docFile.getId());
        Integer year = cn.hutool.core.date.DateUtil.year(stage != null && stage.getBeginDate() != null ? stage.getBeginDate() : project.getBeginDate());
        List<InitMemberListModel> initMemberListModels = initMemberDao.getStaffInfoList(projectId, companyId, year, memberIds);

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
                if (null != birthday && birthday.before(project.getBeginDate())) {
                    initMember.setAge(cn.hutool.core.date.DateUtil.age(birthday, project.getBeginDate()));
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
        ProjectDetailModel model = projectDao.getProject(projectId);
        Integer formula = model.getFormula();
        if (null != formula) {
            Map<Integer, String> enumMap = new HashMap<>();
            for (ProjectTypeEnum value : ProjectTypeEnum.values()) {
                enumMap.put(value.getType(), value.getTypeName());
            }
            map.put("formula", enumMap.get(formula));
        }
        map.put("mList", initMemberListModels);
        map.put("rdTitle", project.getRdTitle());
        List<InitEquipmentModel> equipmentModels = initEquipmentDao.getEquList(companyId, currentYear, project.getId());
        if (!CollectionUtils.isEmpty(equipmentModels)) {
            for (InitEquipmentModel equipmentModel : equipmentModels) {
                equipmentModel.setTotalPrice(equipmentModel.getUnitPrice().multiply(equipmentModel.getQuantity()));
            }
        }
        map.put("eList", equipmentModels);
        CostBudgetFormDocument costBudgetForm = (CostBudgetFormDocument)this.applicationContext.getBean("CostBudgetForm_Doc", RDDocument.class);
        Map budgetMap = costBudgetForm.getYearBudget(project, project.getEndYear());
        if (budgetMap != null) {
            map.putAll(budgetMap);
        }
        map.put("budgetDetail", true);
        return map;
    }

    @Override
    protected Map getDocMap() throws IOException {
        Map jsonMap = getJsonMap();
        Map resultMap = new HashMap<>();
        ProjectEntity projectInfo = dataFactory.getProjectInfo();
//        Integer currentYear = docParam.getCurrentYear();
        // 获取项目周期(项目起止时间) projectPeriod
        resultMap.put("projectPeriod", jsonMap.get("beginAndEndMonth"));

        // 获取项目预算 totalBudget
        resultMap.put("totalBudget", dataFactory.getTotalBudget());

        // 项目编号 rdTitle
        resultMap.put("rdTitle", projectInfo.getRdTitle());

        // 经费预算明细列表 budgetDetail
        CostBudgetFormDocument costBudgetForm = (CostBudgetFormDocument)this.applicationContext.getBean("CostBudgetForm_Doc", RDDocument.class);
        Map budgetMap = costBudgetForm.getYearBudget(projectInfo, projectInfo.getEndYear());
        if (budgetMap != null) {
            resultMap.putAll(budgetMap);
        }
        resultMap.put("budgetDetail", true);

        List<Integer> memberIds = null;
        // 保存图片 img p{}
        if (StringUtils.hasText(docFile.getData())) {
            memberIds = (List<Integer>)jsonMap.get("memberList");
            if (jsonMap.containsKey("img")) {
                //保存图片
                Map<String, Object> imgMap = (Map<String, Object>)jsonMap.get("img");
                Set<String> set = imgMap.keySet();
                for (String str : set) {
                    List<Map<String, String>> imgListMap = JsonUtils.jsonToPojo(JsonUtils.objectToJson(imgMap.get(str)), List.class);
                    List<String> imgList = new ArrayList<>();
                    for (Map<String, String> mapList : imgListMap) {
                        String path = mapList.get("url");
                        imgList.add(path);
                    }
                    resultMap.put("p" + str, imgList);
                }
            }
        }

        // 研发方式 formula
        Integer formula = projectInfo.getFormula();
        if (formula != null) {
            Map<Integer, String> enumMap = ProjectTypeEnum.getEnumMap();
            resultMap.put("formula", enumMap.get(formula));
        }

        // 研发项目所属技术领域 tecIndustry
        if ("ProjectReportForm_5".equals(this.docFile.getDocTemplateName())) {
            StringJoiner tecIndustry = new StringJoiner("/");
            if (projectInfo.getTecIndustry() != null && !projectInfo.getTecIndustry().isEmpty()) {
                String[] s = projectInfo.getTecIndustry().split(",");
                List<Integer> list = new ArrayList<>();
                for (String str : s) {
                    list.add(Integer.valueOf(str));
                }
                List<String> tecList = sysDictionaryDao.getHighTecIndustry(list);
                for (String tec : tecList) {
                    tecIndustry.add(tec);
                }
            }
            resultMap.put("tecIndustry", tecIndustry.toString());
        }

        // 六、项目研究开发人员 mList
        StageEntity stage = getStage();
        Integer year = cn.hutool.core.date.DateUtil.year(stage != null && stage.getBeginDate() != null ? stage.getBeginDate() : projectInfo.getBeginDate());
        List<InitMemberListModel> initMemberListModels = initMemberDao.getStaffInfoList(projectInfo.getId(), projectInfo.getCompanyId(), year, memberIds);
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
                if (null != birthday && birthday.before(projectInfo.getBeginDate())) {
                    initMember.setAge(cn.hutool.core.date.DateUtil.age(birthday, projectInfo.getBeginDate()));
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
                            break;
                        default:
                            initMember.setEtype("");
                            break;
                    }
                }
            }
        }
        resultMap.put("mList", initMemberListModels);

        // 七、主要（大中型）仪器设备清单 eList
        List<InitEquipmentModel> equipmentModels = initEquipmentDao.getEquList(projectInfo.getCompanyId(), year, projectInfo.getId());
        if (!CollectionUtils.isEmpty(equipmentModels)) {
            for (InitEquipmentModel equipmentModel : equipmentModels) {
                equipmentModel.setTotalPrice(equipmentModel.getUnitPrice().multiply(equipmentModel.getQuantity()));
            }
        }
        resultMap.put("eList", equipmentModels);

        /*CostBudgetFormDocument costBudgetForm = (CostBudgetFormDocument)this.applicationContext.getBean("CostBudgetForm_Doc", RDDocument.class);
        Map budgetMap = costBudgetForm.getYearBudget(project, project.getEndYear());
        if (budgetMap != null) {
            map.putAll(budgetMap);
        }*/
        return resultMap;
    }

}
