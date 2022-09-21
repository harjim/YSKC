package com.yskc.docservice.service.impl.rs;


import com.google.common.base.Joiner;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.DeptDao;
import com.yskc.docservice.dao.rs.RdEmployeeDao;
import com.yskc.docservice.dao.rs.ReportDao;
import com.yskc.docservice.dao.rs.init.InitMemberDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.dao.rs.project.ProjectYearInfoDao;
import com.yskc.docservice.entity.rs.Dept;
import com.yskc.docservice.entity.rs.init.InitMemberEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.entity.rs.project.ProjectYearInfoEntity;
import com.yskc.docservice.enums.OrgEnum;
import com.yskc.docservice.models.rs.RdEmployeeModel;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ProjectExcel;
import com.yskc.docservice.models.rs.project.PlanInfo;
import com.yskc.docservice.models.rs.project.ProjectListModel;
import com.yskc.docservice.service.rs.ProjectService;
import com.yskc.docservice.utils.ToolUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-15 10:35
 * @Description: 项目业务实现层
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SHANG_HAI_CODE = "310000";
    @Autowired
    private ProjectYearInfoDao projectYearInfoDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private InitMemberDao initMemberDao;
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private DeptDao deptDao;

    public static String trimBothEndsChars(String srcStr, String splitter) {
        String regex = "^" + splitter + "*|" + splitter + "*$";
        return srcStr.replaceAll(regex, "");
    }

    @Override
    public String importProject(RsUserInfo info, List<ProjectExcel> projectExcels, Integer year) throws OwnerException {
        // commonService.checkAuditModify(info.getCompanyId(), year, AuditModeEnum.RD_PROJECT, info.getUserSource());
        if (projectExcels.size() > 0) {
            Calendar c = Calendar.getInstance();
            for (ProjectExcel projectExcel : projectExcels) {
                //检测RD格式是否正确
                int rdNum = 0;
                if (projectExcel.getRdStr().length() > 6) {
                    if (Pattern.compile("[0-9]*").matcher(projectExcel.getRdStr().substring(0, 4)).matches()) {
                        if (year != Integer.parseInt(projectExcel.getRdStr().substring(0, 4))) {
                            throw new OwnerException("RD:" + projectExcel.getRdStr() + "不是当前年份的项目,请修改后重新导入!");
                        }
                    } else {
                        throw new OwnerException("RD:" + projectExcel.getRdStr() + "格式不对,请修改后重新导入!例如:1或2019RD01");
                    }
                    rdNum = Integer.parseInt(projectExcel.getRdStr().substring(6));
                } else if (projectExcel.getRdStr().length() < 3) {
                    if (Pattern.compile("[0-9]*").matcher(projectExcel.getRdStr()).matches()) {
                        rdNum = Integer.parseInt(projectExcel.getRdStr());
                    } else {
                        throw new OwnerException("RD:" + projectExcel.getRdStr() + "格式不对,请修改后重新导入!例如:1或2019RD01");
                    }
                } else {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "格式不对,请修改后重新导入!例如:1或2019RD01");
                }

                c.setTime(projectExcel.getBeginDate());
                int beginYear = c.get(Calendar.YEAR);
                if (year != beginYear) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的开始日期不是当前年份的日期,请修改后重新导入!");
                }
                if (projectExcel.getBeginDate().compareTo(projectExcel.getEndDate()) > 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的开始日期大于结束日期,请修改后重新导入!");
                }
                if ("是".equals(projectExcel.getTrialProd())) {
                    if (projectExcel.gettBeginDate() == null) {
                        throw new OwnerException("RD:" + projectExcel.getRdStr() + "选择试制则必须指定试制开始时间");
                    }
                    if (projectExcel.gettEndDate() == null) {
                        throw new OwnerException("RD:" + projectExcel.getRdStr() + "选择试制则必须指定试制结束时间");
                    }
                }
                if (projectExcel.gettBeginDate() != null && projectExcel.gettEndDate() != null && projectExcel.gettBeginDate().compareTo(projectExcel.gettEndDate()) > 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的开始试制日期大于结束试制日期,请修改后重新导入!");
                }
                if (projectExcel.gettBeginDate() != null && projectExcel.gettBeginDate().compareTo(projectExcel.getEndDate()) > 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的开始试制日期大于项目结束日期,请修改后重新导入!");
                }
                if (projectExcel.gettEndDate() != null && projectExcel.gettEndDate().compareTo(projectExcel.getEndDate()) > 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的结束试制日期大于项目结束日期,请修改后重新导入!");
                }
                if (projectExcel.gettBeginDate() != null && projectExcel.gettBeginDate().compareTo(projectExcel.getBeginDate()) < 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的开始试制日期小于项目开始日期,请修改后重新导入!");
                }
                if (projectExcel.gettEndDate() != null && projectExcel.gettEndDate().compareTo(projectExcel.getBeginDate()) < 0) {
                    throw new OwnerException("RD:" + projectExcel.getRdStr() + "的结束试制日期小于项目开始日期,请修改后重新导入!");
                }

                projectExcel.setRdStr(rdNum + "");
            }

            //根据项目名判断是否重复
            Map<String, ProjectExcel> map = new HashMap<>();
            for (ProjectExcel projectExcel : projectExcels) {
                if (map.get(projectExcel.getRdStr()) != null) {
                    throw new OwnerException("有重复的RD:" + projectExcel.getRdStr() + ",请修改后重新导入!");
                }
                if (map.get(projectExcel.getPname()) != null) {
                    throw new OwnerException("有重复的RD:" + projectExcel.getPname() + ",请修改后重新导入!");
                }
                map.put(projectExcel.getRdStr(), projectExcel);
                map.put(projectExcel.getPname(), projectExcel);

            }
            Date date = new Date();
            Map<String, ProjectListModel> projectModelMap = new HashMap<>();
            Map<String, Integer> rdDeptMap = commonService.initOrgFullPathMap(info.getCompanyId(), OrgEnum.RD_DEPT, year);
            Map<Integer, ProjectListModel> rdIndexMap = new HashMap<>();
            List<ProjectListModel> projectListModels = projectDao.getProjectsForImport(year, info.getCompanyId());
            if (projectListModels != null && projectListModels.size() > 0) {
                for (ProjectListModel pjl : projectListModels) {
                    projectModelMap.put(pjl.getPname(), pjl);
                    rdIndexMap.put(pjl.getRdIndex(), pjl);
                }
            }
            List<ProjectEntity> insertProjectEntitys = new ArrayList<>();
            List<ProjectEntity> updateProjectEntitys = new ArrayList<>();
            List<String> rdList = new ArrayList<>();
            List<String> pnameRdList = new ArrayList<>();
            List<RdEmployeeModel> rdEmployeeModels = rdEmployeeDao.queryByCompanyIdAndYear(info.getCompanyId(), year);
            Map<String, RdEmployeeModel> rdEmployeeMap = rdEmployeeModels.stream().collect(Collectors.toMap(RdEmployeeModel::getEname, Function.identity(), (existing, replacement) -> existing));

            Map<String, Integer> deptMap = new HashMap<>();
            PlanInfo planInfo = reportDao.getPlanInfo(info.getCompanyId(), year);
            if (planInfo!=null){
                if (!StringUtils.isEmpty(planInfo.getDeptIds())){
                    List<Integer> list = JsonUtils.jsonToList(planInfo.getDeptIds(), Integer.class);
                    List<Dept> deptList = deptDao.selectBatchIds(list);
                    List<Dept> allDeptList = deptDao.getChildByDeptList(deptList, info.getCompanyId());
                    deptMap = allDeptList.stream().collect(Collectors.toMap(Dept::getFullname, Dept::getId));
                }/*else {
                    throw new OwnerException("请添加规划项目的相关部门！");
                }*/
            }
            Dept parentDept = deptDao.selectParentDept(info.getCompanyId());
            /*if (parentDept==null){
                throw new OwnerException("该公司尚无组织架构，请先导入组织架构!");
            }*/
            for (int i = 0; i < projectExcels.size(); i++) {
                ProjectExcel projectExcel = projectExcels.get(i);
                int rdNum = Integer.parseInt(projectExcel.getRdStr());
                String rd = projectExcel.getRdStr();
                if (Integer.parseInt(projectExcel.getRdStr()) < 10) {
                    rd = "0" + projectExcel.getRdStr();
                }
                //不存在该项目名称
                if (projectModelMap.get(projectExcel.getPname()) == null) {
                    ProjectEntity projectEntity = new ProjectEntity();
                    projectEntity.setPname(projectExcel.getPname());
                    projectEntity.setRdIndex(rdNum);
                    projectEntity.setBeginDate(projectExcel.getBeginDate());
                    projectEntity.setEndDate(projectExcel.getEndDate());
                    c.setTime(projectEntity.getBeginDate());
                    projectEntity.setBeginYear(c.get(Calendar.YEAR));
                    c.setTime(projectEntity.getEndDate());
                    projectEntity.setEndYear(c.get(Calendar.YEAR));
                    projectEntity.setTrialProd(projectExcel.getTrialProd() != null && projectExcel.getTrialProd().equals("是"));
                    projectEntity.settBeginDate(projectExcel.gettBeginDate());
                    projectEntity.settEndDate(projectExcel.gettEndDate());
                    projectEntity.setWorkshop(projectExcel.getWorkshop());
                    projectEntity.setProductLine(projectExcel.getProductLine());
                    projectEntity.setProcessSection(projectExcel.getProcessSection());
                    projectEntity.setRdNumber(projectExcel.getRdNumber());


                    if (rdIndexMap.get(rdNum) != null) {
                        rdList.add(projectEntity.getBeginYear() + "RD" + rd + "[" + projectExcel.getPname() + "]");
                        continue;
                    }
                    if (projectExcel.getFullname()==null||!deptMap.containsKey(projectExcel.getFullname())){
//                        throw new OwnerException("【"+projectExcel.getFullname()+"】不是规划部门,请检查!");
                        projectEntity.setDeptId(0);
                    }else {
                        String fullname = projectExcel.getFullname();
                        fullname = fullname.replaceAll(parentDept.getDeptName()+"/", "");
                        fullname = trimBothEndsChars(fullname,"/");
                        projectExcel.setFullname(fullname);
                        projectEntity.setDeptId(deptMap.get(projectExcel.getFullname()));
                    }
                    projectEntity.setDeptName(projectExcel.getFullname());
                    projectEntity.setCompanyId(info.getCompanyId());
                    projectEntity.setCreateTime(date);
                    projectEntity.setCreatorId(info.getUserId());
                    projectEntity.setMsCreatorId(info.getMsUserId());
                    projectEntity.setLastUpdateTime(date);
                    projectEntity.setLastUpdatorId(info.getUserId());
                    projectEntity.setMsLastUpdatorId(info.getMsUserId());
                    if (projectExcel.getEstimateExpense() != null) {
                        projectEntity.setEstimateExpense(projectExcel.getEstimateExpense() * 10000);
                    }

                    if (StringUtils.isEmpty(projectExcel.getEname())) {
                        projectEntity.setMasterENumber("");
                    } else {
                        RdEmployeeModel employeeModel = rdEmployeeMap.get(projectExcel.getEname());
                        if (employeeModel != null) {
                            projectEntity.setMasterENumber(employeeModel.getEnumber());
                        } else {
                            pnameRdList.add(projectEntity.getBeginYear() + "RD" + rd + "[" + projectExcel.getEname() + "]");
                            continue;
                        }
                    }
                    Integer rdDeptId = ToolUtils.getOrgId(rdDeptMap, projectExcel.getRdDeptName());
                    projectEntity.setRdDeptId(rdDeptId != null ? rdDeptId : -1);
                    projectEntity.setProjectSource(1);
                    projectEntity.setFormula(10);
                    projectEntity.setRdTitle(getRdTitle(projectEntity.getBeginYear(), projectEntity.getRdIndex(), info.getCompanyId(), null));
                    insertProjectEntitys.add(projectEntity);
                } else {
                    //存在该项目名
                    ProjectEntity projectEntity = new ProjectEntity();
                    ProjectListModel projectListModel = projectModelMap.get(projectExcel.getPname());
                    projectEntity.setBeginDate(projectExcel.getBeginDate());
                    projectEntity.setEndDate(projectExcel.getEndDate());
                    c.setTime(projectEntity.getBeginDate());
                    projectEntity.setBeginYear(c.get(Calendar.YEAR));
                    c.setTime(projectEntity.getEndDate());
                    projectEntity.setEndYear(c.get(Calendar.YEAR));
                    projectEntity.setTrialProd("是".equals(projectExcel.getTrialProd()));
                    projectEntity.settBeginDate(projectExcel.gettBeginDate());
                    projectEntity.settEndDate(projectExcel.gettEndDate());
                    projectEntity.setWorkshop(projectExcel.getWorkshop());
                    projectEntity.setProductLine(projectExcel.getProductLine());
                    projectEntity.setProcessSection(projectExcel.getProcessSection());


                    if (rdIndexMap.get(rdNum) != null && projectListModel.getRdIndex() != rdNum) {
                        rdList.add(projectEntity.getBeginYear() + "RD" + rd + "[" + projectExcel.getPname() + "]");
                        continue;
                    }
                    if (!deptMap.containsKey(projectExcel.getFullname())||projectExcel.getFullname()==null){
//                        throw new OwnerException("【"+projectExcel.getFullname()+"】不是规划部门,请检查!");
                        projectEntity.setDeptId(0);
                    }else {
                        String fullname = projectExcel.getFullname();
                        fullname = fullname.replaceAll(parentDept.getDeptName()+"/", "");
                        fullname = trimBothEndsChars(fullname,"/");
                        projectExcel.setFullname(fullname);
                        projectEntity.setDeptId(deptMap.get(projectExcel.getFullname()));
                    }
                    projectEntity.setDeptName(projectExcel.getFullname());

                    BeanUtils.copyProperties(projectExcel, projectEntity);
                    if (projectExcel.getEstimateExpense() != null) {
                        projectEntity.setEstimateExpense(projectExcel.getEstimateExpense() * 10000);
                    }
                    projectEntity.setRdIndex(rdNum);
                    projectEntity.setId(projectListModel.getId());

                    if (StringUtils.isEmpty(projectExcel.getEname())) {
                        projectEntity.setMasterENumber("");
                    } else {
                        RdEmployeeModel employeeModel = rdEmployeeMap.get(projectExcel.getEname());
                        if (employeeModel != null) {
                            projectEntity.setMasterENumber(employeeModel.getEnumber());
                        } else {
                            pnameRdList.add(projectEntity.getBeginYear() + "RD" + rd + "[" + projectExcel.getEname() + "]");
                            continue;
                        }
                    }
                    projectEntity.setLastUpdatorId(info.getUserId());
                    projectEntity.setMsLastUpdatorId(info.getMsUserId());
                    projectEntity.setLastUpdateTime(date);
                    projectEntity.setProjectSource(1);
                    projectEntity.setFormula(10);
                    Integer rdDeptId = ToolUtils.getOrgId(rdDeptMap, projectExcel.getRdDeptName().trim());
                    projectEntity.setRdDeptId(rdDeptId != null ? rdDeptId : -1);
                    projectEntity.setRdNumber(projectExcel.getRdNumber());
                    projectEntity.setRdTitle(getRdTitle(projectEntity.getBeginYear(), projectEntity.getRdIndex(), info.getCompanyId(), null));
                    updateProjectEntitys.add(projectEntity);
                }
            }

            TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            try {
                List<InitMemberEntity> insertMembers = new ArrayList<>();
                List<ProjectYearInfoEntity> infos = new ArrayList<>();
                List<ProjectYearInfoEntity> updateInfos = new ArrayList<>();
                if (insertProjectEntitys.size() > 0) {
                    projectDao.insertProjectList(insertProjectEntitys);
                    for (ProjectEntity projectEntity : insertProjectEntitys) {
                        if (!StringUtils.isEmpty(projectEntity.getMasterENumber())) {
                            InitMemberEntity initMember = new InitMemberEntity(info.getUserId(), info.getMsUserId(), date, projectEntity.getMasterENumber(),
                                    true, info.getCompanyId(), projectEntity.getId(), year, null);
                            insertMembers.add(initMember);
                            ProjectYearInfoEntity infoEntity = new ProjectYearInfoEntity(year, projectEntity.getMasterENumber(), null, projectEntity.getId(), info.getCompanyId());
                            infoEntity.create(info.getUserId(), info.getMsUserId(), date);
                            infos.add(infoEntity);
                        }
                    }
                }
                if (updateProjectEntitys.size() > 0) {
                    projectDao.updateProjectList(updateProjectEntitys);
                    List<Integer> projectIds = updateProjectEntitys.stream().map(e -> e.getId()).collect(Collectors.toList());
                    initMemberDao.cleanMasters(projectIds, year, info.getUserId(), info.getMsUserId(), date);
                    List<ProjectEntity> dels = new ArrayList<>();
                    for (ProjectEntity project : updateProjectEntitys) {
                        if (!StringUtils.isEmpty(project.getMasterENumber())) {
                            dels.add(project);
                            InitMemberEntity member = new InitMemberEntity(info.getUserId(), info.getMsUserId(), date, project.getMasterENumber(),
                                    true, info.getCompanyId(), project.getId(), year, null);
                            insertMembers.add(member);
                            ProjectYearInfoEntity infoEntity = new ProjectYearInfoEntity(year, project.getMasterENumber(), null, project.getId(), info.getCompanyId());
                            infoEntity.update(info.getUserId(), info.getMsUserId(), date);
                            updateInfos.add(infoEntity);
                        }
                    }
                    if (!CollectionUtils.isEmpty(dels)) {
                        initMemberDao.delMasterByProject(dels, year);
                    }
                }
                if (!CollectionUtils.isEmpty(insertMembers)) {
                    initMemberDao.addbatch(insertMembers);
                }
                if (!CollectionUtils.isEmpty(infos)) {
                    projectYearInfoDao.addBatchMaster(infos);
                }
                if (!CollectionUtils.isEmpty(updateInfos)){
                    projectYearInfoDao.updateBatch(updateInfos);
                }
                TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
                logger.error("importProject", ex);
            } finally {
                String message = "";
                if (rdList.size() > 0) {
                    message += Joiner.on(",").join(rdList) + "的RD已存在,请检查!<br/>";
                }
                if (pnameRdList.size() > 0) {
                    message += Joiner.on(",").join(pnameRdList) + "的负责人不在研发花名册内,请检查!";
                }
                if (!message.equals("")) {
                    throw new OwnerException(message);
                }
            }
        } else {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        return "";
    }
    private String getRdTitle(Integer year, Integer rdIndex, Integer companyId, String rdNumber) {
        String rdTitle = "";
        String yearStr = String.valueOf(year);
        String rdIndexStr = rdIndex > 9 ? String.valueOf(rdIndex) : "0" + rdIndex;
        //rdNumber存在“”情况
        if (rdNumber != null) {
            rdTitle = MessageFormat.format("{0}RD{1}.{2}", yearStr, rdIndexStr, rdNumber);
        } else {
            rdTitle = MessageFormat.format("{0}RD{1}", yearStr, rdIndexStr);
        }
        return rdTitle;
    }
}
