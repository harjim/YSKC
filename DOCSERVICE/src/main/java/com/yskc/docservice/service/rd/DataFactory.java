package com.yskc.docservice.service.rd;

import com.yskc.docservice.config.DocServiceConfig;
import com.yskc.docservice.dao.rs.DocFileFooterDao;
import com.yskc.docservice.dao.rs.RdDeptDao;
import com.yskc.docservice.dao.rs.RdEmployeeDao;
import com.yskc.docservice.dao.rs.StageDao;
import com.yskc.docservice.dao.rs.company.CompanyDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.dao.rs.project.ProjectDocFileDao;
import com.yskc.docservice.entity.rs.DocFileFooterEntity;
import com.yskc.docservice.entity.rs.RdDeptEntity;
import com.yskc.docservice.entity.rs.StageEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.DocParam;
import com.yskc.docservice.models.rs.EmployeeSelectModel;
import com.yskc.docservice.models.rs.PDocFile;
import com.yskc.docservice.models.rs.company.CompanyMember;
import com.yskc.docservice.models.rs.docfile.PDataListFormModel;
import com.yskc.docservice.models.rs.project.ProjectYearModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope( "prototype" )
public class DataFactory {
    private DocParam docParam;

    public void setDocParam(DocParam docParam) {
        this.docParam = docParam;
    }

    private Map projectMap;

    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private RdDeptDao rdDeptDao;

    public Map getProject() {
        if (this.projectMap == null) {
            ProjectEntity project = this.getProjectInfo();
            RdDeptEntity rdDeptEntity = rdDeptDao.getParentNode(project.getBeginYear(), project.getCompanyId());
            this.projectMap = new HashMap();
            //项目起止时间
            String beginAndEnd = MessageFormat.format("{0,date,yyyy-MM-dd} 至 {1,date,yyyy-MM-dd}", project.getBeginDate(), project.getEndDate());
            String beginAndEndMonth = MessageFormat.format("{0,date,yyyy年MM月} 至 {1,date,yyyy年MM月}", project.getBeginDate(), project.getEndDate());
            this.projectMap.put("pname", project.getPname());
            this.projectMap.put("rdIndex", project.getRdTitle());
            this.projectMap.put("beginAndEnd", beginAndEnd);
            this.projectMap.put("beginAndEndMonth", beginAndEndMonth);
            this.projectMap.put("beginYear", project.getBeginYear());
            this.projectMap.put("endYear", project.getEndYear());
            // 项目开始年的研发架构部门
            this.projectMap.put("parentRdDept", rdDeptEntity != null ? rdDeptEntity.getDeptName() : "");
            if (project.getRdDeptId() != null && project.getRdDeptId() > 0) {
                this.projectMap.put("projectRdDept", rdDeptDao.getDeptName(project.getRdDeptId()));
            }
            CompanyMember companyInfo = this.getCompanyInfo();
            this.projectMap.put("cname", companyInfo.getCompanyName());
            this.projectMap.put("companyName", companyInfo.getCompanyName());
            // 项目负责人
        }
        return this.projectMap;
    }

    private CompanyMember companyInfo;

    public CompanyMember getCompanyInfo() {
        if (companyInfo == null) {
            ProjectEntity project = this.getProjectInfo();
            this.companyInfo = companyDao.getCompanyMember(project.getCompanyId());
        }
        return this.companyInfo;
    }

    private Map<Integer, Map> yearMap;
    // 项目总预算
    private BigDecimal totalBudget = BigDecimal.ZERO;

    /**
     * 项目每年的信息,项目负责人,年总预算
     *
     * @param year 项目年份
     * @return <年份, 年份对应信息>
     */
    public Map getProjectYearInfo(Integer year) {
        if (yearMap == null) {
            yearMap = new HashMap();
            List<ProjectYearModel> yearModelList = this.projectDao.getYearInfos(this.getProjectInfo().getId());
            if (yearModelList.size() > 0) {
                for (ProjectYearModel yearModel :
                        yearModelList) {
                    Map yMap = new HashMap();
                    if (yearModel.getBudget() != null) {
                        totalBudget = totalBudget.add(yearModel.getBudget());
                    }
                    yMap.put("projectMasterName", yearModel.getMasterName()); // 先放项目负责人进来
                    yearMap.put(yearModel.getYear(), yMap);
                }
            }

        }
        if (yearMap.get(year) != null) {
            return yearMap.get(year);
        }
        return null;
    }

    public BigDecimal getTotalBudget() {
        return this.totalBudget;
    }

    @Autowired
    private ProjectDao projectDao;
    ProjectEntity projectInfo;

    public ProjectEntity getProjectInfo() {
        if (this.projectInfo == null) {
            this.projectInfo = this.projectDao.selectById(this.docParam.getProjectId());
        }
        return this.projectInfo;
    }

    public List<PDocFile> getDocFileList() {
        return projectDao.getDocFileList(this.docParam);
    }

    @Autowired
    StageDao stageDao;
    Map<String, StageEntity> stageMap;

    /**
     * 项目包含阶段 <阶段key， 阶段对象>
     *
     * @return Map<String, StageEntity>
     */
    Map<String, StageEntity> getProjectStage() {
        if (this.stageMap == null) {
            List<StageEntity> stageList = stageDao.getStageList(this.docParam.getProjectId());
            stageMap = new HashMap<>();
            for (StageEntity stage :
                    stageList) {
                stageMap.put(stage.getStageKey(), stage);
            }
        }
        return stageMap;
    }

    @Autowired
    private ProjectDocFileDao projectDocFileDao;

    private List<PDataListFormModel> allFileStageList;

    public List<PDataListFormModel> getAllFileStageList() {
        if (allFileStageList == null) {
            List<Integer> pDocFileIds = null;
            if (getDocFileList().size() > 1) {
                pDocFileIds = Arrays.asList(docParam.getpDocFileId());
            }
            allFileStageList = projectDocFileDao.queryFileList(projectInfo.getId(), pDocFileIds);
        }
        return allFileStageList;
    }

    @Autowired
    DocFileFooterDao docFileFooterDao;
    @Autowired
    RdEmployeeDao rdEmployeeDao;

    @Autowired
    public DocServiceConfig docServiceConfig;

    Map<Integer, Map> footerMap = new HashMap<>();

    // 获取底部审核
    Map getFooterMap(Integer year, String ftlPath) {
        if (!footerMap.containsKey(year)) {
            EmployeeSelectModel approval, audit, toCompile;
            approval = audit = toCompile = new EmployeeSelectModel();
            DocFileFooterEntity docFooter = docFileFooterDao.getFooter(this.docParam.getProjectId(), year);
            Map<String, EmployeeSelectModel> userMap = new HashMap<>();
            if (docFooter != null && !Objects.equals(false, docParam.getImportFooterName())) {
                List<String> enumbers = new ArrayList<>();
                if (StringUtils.hasLength(docFooter.getApprovalEnumber())) {
                    enumbers.add(docFooter.getApprovalEnumber());
                }
                if (StringUtils.hasLength(docFooter.getAuditEnumber())) {
                    enumbers.add(docFooter.getAuditEnumber());
                }
                if (StringUtils.hasLength(docFooter.getToCompileEnumber())) {
                    enumbers.add(docFooter.getToCompileEnumber());
                }
                if (!CollectionUtils.isEmpty(enumbers)) {
                    List<EmployeeSelectModel> users = rdEmployeeDao.getEmployeeByEnumber(docFooter.getCompanyId(), enumbers);
                    if (!CollectionUtils.isEmpty(users)) {

                        userMap = users.stream().collect(Collectors.toMap(e -> e.getEnumber(), e -> {
                            if (StringUtils.hasText(e.getAutographUrl())) {
                                String imgUrl = e.getAutographUrl().replace("/static/", ftlPath);
                                String imgAbsUrl = e.getAutographUrl().replace("/static/", Paths.get(docServiceConfig.getResourcePath(), "/static/").toUri().toString());
                                // 判断本地是否存在图片，无则不显示图片
                                try {
                                    if (!new UrlResource(imgAbsUrl).exists()) {
                                        imgUrl = null;
                                    }
                                } catch (MalformedURLException ex) {
                                    ex.printStackTrace();
                                }
                                e.setAutographUrl(imgUrl);
                            }
                            return e;
                        }));
                    }
                }
                approval = userMap.get(docFooter.getApprovalEnumber());
                audit = userMap.get(docFooter.getAuditEnumber());
                toCompile = userMap.get(docFooter.getToCompileEnumber());
            }
            Map<String, EmployeeSelectModel> employeeMap = new HashMap<>();
            employeeMap.put("approval", approval);
            employeeMap.put("audit", audit);
            employeeMap.put("toCompile", toCompile);
            footerMap.put(year, employeeMap);
        }

        return footerMap.get(year);
    }

}
