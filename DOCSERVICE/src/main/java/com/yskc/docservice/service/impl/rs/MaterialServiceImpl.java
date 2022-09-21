package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.AccountTitleDao;
import com.yskc.docservice.dao.rs.MaterialDao;
import com.yskc.docservice.dao.rs.SummaryDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.dao.rs.project.ProjectMaterialDao;
import com.yskc.docservice.entity.rs.AccountTitleEntity;
import com.yskc.docservice.entity.rs.MaterialEntity;
import com.yskc.docservice.entity.rs.SummaryEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.entity.rs.project.ProjectMaterialEntity;
import com.yskc.docservice.enums.RdMaterialEnum;
import com.yskc.docservice.models.rs.CheckStatusModel;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.MaterialExcel;
import com.yskc.docservice.models.rs.material.ImportMaterialModel;
import com.yskc.docservice.models.rs.material.ProjectRdMaterialTotalModel;
import com.yskc.docservice.service.rs.MaterialService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.ToolUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MaterialDao materialDao;
    @Autowired
    private AccountTitleDao accountTitleDao;
    @Autowired
    private ProjectMaterialDao projectMaterialDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private CommonService commonService;


    @Override
    public ImportMaterialModel importMaterial(RsUserInfo info, List<MaterialExcel> materialExcels, Integer type,
                                              Integer year, Boolean isRD)
            throws OwnerException {
        ImportMaterialModel importMaterialModel = new ImportMaterialModel();
        if (materialExcels.size() > 0) {
            List<String> mcodeList = materialExcels.stream().filter(a -> StringUtils.hasLength(a.getMcode())).map(MaterialExcel::getMcode).collect(Collectors.toList());
            List<MaterialEntity> byMcodes = new ArrayList<>();
            if (!CollectionUtils.isEmpty(mcodeList)) {
                byMcodes = materialDao.getByMcodes(info.getCompanyId(), mcodeList, type);
            }
            Map<String, Boolean> existAndRepeatMap = new HashMap<>();
            String keyFormat = "{0}_{1}_{2}";
            String mcode, billNo;
            Date acqDate;
            if (!CollectionUtils.isEmpty(byMcodes)) {
                // 取出的数据，若无值，默认为空串。acqDate不为空
                for (MaterialEntity model : byMcodes) {
                    mcode = model.getMcode();
                    billNo = model.getBillNo();
                    acqDate = model.getAcqDate();
                    existAndRepeatMap.put(MessageFormat.format(keyFormat, mcode != null ? mcode : "", billNo != null ? billNo : "", acqDate != null ? acqDate.getTime() : ""), true);
                }
            }
            List<String> repeatRows = new ArrayList<>();
            for (int i = 0; i < materialExcels.size(); i++) {
                MaterialExcel model = materialExcels.get(i);
                mcode = model.getMcode();
                billNo = model.getBillNo();
                acqDate = model.getAcqDate();
                String checkStr = MessageFormat.format(keyFormat, mcode != null ? mcode : "", billNo != null ? billNo : "", acqDate != null ? acqDate.getTime() : "");
                if (existAndRepeatMap.containsKey(checkStr)) {
                    repeatRows.add((i + 2) + "");
                } else {
                    existAndRepeatMap.put(checkStr, true);
                }
            }
            if (repeatRows.size() > 0) {
                importMaterialModel.setResult(1);
                importMaterialModel.setMessage("行[" + String.join(",", repeatRows) + "]已存在现有数据中,确定要导入吗?");
            } else {
                confirmImport(info, materialExcels, type, year, isRD);
            }
        } else {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        return importMaterialModel;
    }


    @Override
    public boolean confirmImport(RsUserInfo info, List<MaterialExcel> materialExcels, Integer type, Integer year, Boolean isRD)
            throws OwnerException {
        if (materialExcels.size() > 0) {
            Set<String> fullAccountNameSet = new HashSet<>();
            Map<String, Boolean> rdTitleMap = new LinkedHashMap<>();
            for (MaterialExcel item : materialExcels) {
                if (StringUtils.hasLength(item.getFullAccountName())) {
                    if (!item.getFullAccountName().endsWith(Constant.PATH_SEPARATOR)) {
                        item.setFullAccountName(item.getFullAccountName() + Constant.PATH_SEPARATOR);
                    }
                    fullAccountNameSet.add(item.getFullAccountName());
                }
                if (isRD) {
                    if (!Pattern.matches("\\d{4}RD\\d{2,}.*", item.getRdTitle())) {
                        throw new OwnerException("RD：" + item.getRdTitle() + " 格式错误，请修改后再导入，RD格式：2020RD01");
                    }
                    rdTitleMap.put(item.getRdTitle(), false);
                }
            }
            Map<String, ProjectEntity> rdMap = null;
            if (isRD) {
                List<ProjectEntity> projects = projectDao.getTrialByRdTitles(rdTitleMap.keySet(), info.getCompanyId());
                rdMap = projects.stream().collect(Collectors.toMap(a -> a.getRdTitle(), b -> b, (c, d) -> d));
                StringBuilder existBuilder = new StringBuilder();
                StringBuilder parentBuilder = new StringBuilder();
                for (String rdTitle : rdTitleMap.keySet()) {
                    ProjectEntity pe = rdMap.get(rdTitle);
                    if (pe == null) {
                        existBuilder.append(rdTitle).append("、");
                    } else if (pe.getHasChild()) {
                        parentBuilder.append(rdTitle).append("、");
                    }
                }
                if (existBuilder.length() > 0) {
                    throw new OwnerException(MessageFormat.format("RD：{0} 不存在，请先添加对应的RD后再导入。",
                            existBuilder.substring(0, existBuilder.length() - 1)));
                }
                if (parentBuilder.length() > 0) {
                    throw new OwnerException(MessageFormat.format("RD：{0} ，父项目不能归集费用。",
                            parentBuilder.substring(0, parentBuilder.length() - 1)));
                }
            }
            List<AccountTitleEntity> accountTitleEntityList = fullAccountNameSet.size() == 0 ? new ArrayList<>() : accountTitleDao.getByFullAccountName(info.getCompanyId(), fullAccountNameSet);
            Map<String, AccountTitleEntity> accountTitleEntityMap = accountTitleEntityList.stream().collect(Collectors.toMap(AccountTitleEntity::getFullAccountName, b -> b, (o, n) -> o));
            List<MaterialEntity> insertMaterialEntitys = new ArrayList<>();
            for (int i = 0; i < materialExcels.size(); i++) {
                MaterialExcel materialExcel = materialExcels.get(i);
                AccountTitleEntity accountTitleEntity = accountTitleEntityMap.get(materialExcel.getFullAccountName());
                int accountTitleId = 0;
                if (accountTitleEntity != null) {
                    accountTitleId = accountTitleEntity.getId();
                }
                MaterialEntity entity = setMaterial(info, materialExcel, 0, type, accountTitleId, isRD);
                insertMaterialEntitys.add(entity);
            }
            TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            try {
                if (insertMaterialEntitys.size() > 0) {
                    List<List<MaterialEntity>> insertList = ListUtils.subList(insertMaterialEntitys, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                    for (List<MaterialEntity> items : insertList) {
                        materialDao.addBatch(items);
                    }
                    if (isRD) {
                        saveRdAndSummary(insertMaterialEntitys, info, rdMap);
                    }
                }
                TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            } catch (OwnerException ex) {
                TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
                throw ex;
            } catch (Exception ex) {
                TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
                logger.error("importMaterial", ex);
                throw ex;
            }
        } else {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        return true;
    }

    private MaterialEntity setMaterial(RsUserInfo info, MaterialExcel materialExcel, Integer deptId, Integer type,
                                       Integer accountTitleId, Boolean isRD) {
        MaterialEntity entity = new MaterialEntity();
        entity.setType(type);
        entity.setMcode(materialExcel.getMcode() != null ? materialExcel.getMcode() : "");
        entity.setMname(materialExcel.getMname());
        entity.setBillNo(materialExcel.getBillNo() != null ? materialExcel.getBillNo() : "");
        entity.setAccNumber(materialExcel.getAccNumber() != null ? materialExcel.getAccNumber() : "");
        entity.setAcqDate(materialExcel.getAcqDate());
        entity.setTotalAmount(materialExcel.getTotalAmount() != null ? materialExcel.getTotalAmount() : BigDecimal.valueOf(0));
        BigDecimal unitPrice;
        //数量
        BigDecimal quantity;
        //总金额
        BigDecimal totalAmount = materialExcel.getTotalAmount();
        //如果没单价
        if (materialExcel.getUnitPrice() == null || materialExcel.getUnitPrice().compareTo(BigDecimal.ZERO) == 0) {
            if (materialExcel.getQuantity() == null || materialExcel.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                unitPrice = materialExcel.getTotalAmount();
                quantity = new BigDecimal(1);
                materialExcel.setQuantity(quantity);
            } else {
                unitPrice = totalAmount.divide(materialExcel.getQuantity(), 20, BigDecimal.ROUND_HALF_UP);
                quantity = materialExcel.getQuantity();
            }
        }
        //如果有单价
        else {
            unitPrice = materialExcel.getUnitPrice();
            if (StringUtils.isEmpty(materialExcel.getQuantity()) || materialExcel.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                quantity = totalAmount.divide(unitPrice, 20, BigDecimal.ROUND_HALF_UP);
            } else {
                quantity = materialExcel.getQuantity();
            }
        }
        entity.setUnitPrice(unitPrice);
        entity.setQuantity(quantity);
        if (isRD) {
            entity.setRemainQuantity(quantity.subtract(materialExcel.getUsed()));
            entity.setRD(materialExcel.getRdTitle());
            entity.setRdTypeName(materialExcel.getRdTypeName());
            entity.setUsed(materialExcel.getUsed());
        } else {
            entity.setRemainQuantity(quantity);
        }
        entity.setUnit(materialExcel.getUnit() != null ? materialExcel.getUnit() : "");
        entity.setSpecification(materialExcel.getSpecification() != null ? materialExcel.getSpecification() : "");
        entity.setDeptId(deptId);
        entity.setWarehouse(materialExcel.getWarehouse() != null ? materialExcel.getWarehouse() : "");
        entity.setBiller(materialExcel.getBiller() != null ? materialExcel.getBiller() : "");
        entity.setAuditor(materialExcel.getAuditor() != null ? materialExcel.getAuditor() : "");
        entity.setBooker(materialExcel.getBooker() != null ? materialExcel.getBooker() : "");
        entity.setRemark(materialExcel.getRemark() != null ? materialExcel.getRemark() : "");
        entity.setCompanyId(info.getCompanyId());
        entity.setPicker(materialExcel.getPicker() != null ? materialExcel.getPicker() : "");
        //默认设为0
        entity.setRdDeptId(0);
        entity.setDeptName(materialExcel.getDeptName());
        entity.setPurpose(materialExcel.getPurpose());
        entity.setAccountTitleId(accountTitleId);
        entity.setCreateTime(new Date());
        entity.setCreatorId(info.getUserSource() == 0 ? info.getId() : -1);
        entity.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
        entity.setMsCreatorId(info.getUserSource() == 1 ? info.getId() : -1);
        entity.setMsLastUpdatorId(info.getUserSource() == 1 ? info.getId() : -1);
        entity.setLastUpdateTime(new Date());
        return entity;
    }


    private void saveRdAndSummary(List<MaterialEntity> materialList, RsUserInfo info, Map<String, ProjectEntity> rdMap) throws OwnerException {
        List<ProjectMaterialEntity> projectMaterialList = new ArrayList<>(materialList.size());
        Integer userId = info.getUserId();
        Integer msUserId = info.getMsUserId();
        Integer companyId = info.getCompanyId();
        Date now = new Date();
        ProjectEntity currentProject;
        Integer rdType, rdTrialType = RdMaterialEnum.TRIAL_MATERIAL.getType();
        Map<String, Integer> rdMaterialMap = RdMaterialEnum.getMaterialTitleMap();
        Date maxDate = materialList.get(0).getAcqDate();
        Date minDate = maxDate;
        Map<String, String> projectMonthMap = new LinkedHashMap<>();
        Date currentAcqDate;
        int count = 1;
        List<Integer> projectIds = rdMap.values().stream().map(ProjectEntity::getId).collect(Collectors.toList());
        for (MaterialEntity material : materialList) {
            count++;
            currentProject = rdMap.get(material.getRD());
            currentAcqDate = material.getAcqDate();
            if (currentAcqDate.before(currentProject.getBeginDate()) ||
                    currentProject.getEndDate().before(currentAcqDate)) {
                throw new OwnerException(MessageFormat.format(
                        "第{0,number,#}行，领用日期：{1}，不在RD：{2} 的起止时间：{3}~{4} 中，请修改后再导入。",
                        count, DateUtil.format(currentAcqDate, DateUtil.DEFAULT_DATE_FORMAT),
                        material.getRD(), DateUtil.format(currentProject.getBeginDate(), DateUtil.DEFAULT_DATE_FORMAT),
                        DateUtil.format(currentProject.getEndDate(), DateUtil.DEFAULT_DATE_FORMAT)));
            }
            if (material.getUsed().abs().compareTo(material.getQuantity().abs()) > 0) {
                throw new OwnerException(MessageFormat.format(
                        " 第{0,number,#}行，研发数量：{1} 不能超过材料数量：{2}。", count,
                        material.getUsed(), material.getQuantity()));
            }
            rdType = rdMaterialMap.get(material.getRdTypeName());
            if (rdType == null) {
                throw new OwnerException(MessageFormat.format(
                        "第{0,number,#}行，不存在研发类型：{1}，请从中选择：研发材料、中间试制、修理材料，如：研发材料。",
                        count, material.getRdTypeName()));
            }
            // 导入中间试制，但立项信息无试制或领料日期不在试制日期内
            if (rdTrialType.equals(rdType) && (!currentProject.getTrialProd() || currentProject.gettBeginDate().compareTo(currentAcqDate) > 0
                    || currentProject.gettEndDate().compareTo(currentAcqDate) < 0)) {
                throw new OwnerException(MessageFormat.format(
                        "第{0,number,#}行，领用日期：{1}，不在当前项目的试制起止日期区间内，请修改后再导入。",
                        count, DateUtil.format(currentAcqDate, DateUtil.DEFAULT_DATE_FORMAT)));
            }
            projectMaterialList.add(ProjectMaterialEntity.build(userId, msUserId, now, material.getId(), currentProject.getId(),
                    rdMaterialMap.get(material.getRdTypeName()), companyId, material.getUsed(),
                    material.getUsed().multiply(material.getUnitPrice()), material.getUnitPrice()));
            String monthKey = currentProject.getId() + DateUtil.format(currentAcqDate, DateUtil.DEFAULT_YYMM_FORMAT);
            if (!projectMonthMap.containsKey(monthKey)) {
                projectMonthMap.put(monthKey, currentProject.getRdTitle());
            }
            if (currentAcqDate.before(minDate)) {
                minDate = currentAcqDate;
                continue;
            }
            if (maxDate.before(currentAcqDate)) {
                maxDate = currentAcqDate;
            }
            checkProjectsStatus(currentProject.getId(), DateUtil.getMonthFirstDay(currentAcqDate),rdType,info);
        }
        for (List<ProjectMaterialEntity> projectMaterialInsert :
                ListUtils.subList(projectMaterialList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
            projectMaterialDao.addBatch(projectMaterialInsert);
        }
        List<ProjectRdMaterialTotalModel> totalList = projectMaterialDao.getRdMaterialTotal(projectIds, DateUtil.getMonthFirstDay(minDate), DateUtil.getMonthLastDay(maxDate));
        Map<String, SummaryEntity> summaryMap = new HashMap<>();
        String format = "{0}_{1}_{2}";
        totalList.forEach(item -> {
            String key = MessageFormat.format(format, item.getProjectId(), item.getRdType(),
                    DateUtil.format(item.getAcqDate(), DateUtil.DEFAULT_YYMM_FORMAT));
            if (!summaryMap.containsKey(key)) {
                summaryMap.put(key, ToolUtils.build(now, DateUtil.getMonthFirstDay(item.getAcqDate()), item.getProjectId(),
                        item.getRdType(), BigDecimal.ZERO, info));
            }
            summaryMap.get(key).setRdFunds(summaryMap.get(key).getRdFunds().add(item.getTotal()));
        });
        summaryDao.insertOrUpdate(new LinkedList<>(summaryMap.values()));
    }
//
    public void checkProjectsStatus(Integer projectId, Date month, Integer rdType,RsUserInfo info) throws OwnerException {
        List<Integer> rdTypes = new ArrayList<>();
        rdTypes.add(rdType);
        List<CheckStatusModel> models = new ArrayList<>();
        CheckStatusModel checkStatusModel = new CheckStatusModel();
        checkStatusModel.setMonth(month);
        checkStatusModel.setProjectId(projectId);
        models.add(checkStatusModel);
        commonService.checkStatus(models, rdTypes, info);

        /*List<RdStatusModel> statusList = projectDao.getRdsStatus(projectIds);
        Integer finalStatus = RsProjectStatusEnum.FINALIZATION.getStatus();
        StringBuilder builder = new StringBuilder();
        for (RdStatusModel rdStatus : statusList) {
            String monthStr = DateUtil.format(rdStatus.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT);
            String monthKey = rdStatus.getProjectId() + monthStr;
            if (finalStatus.equals(rdStatus.getStatus()) &&
                    projectMonthMap.containsKey(monthKey)) {
                builder.append(projectMonthMap.get(monthKey)).append("：").append(monthStr).append("、");
            }
        }*/
//        if (builder.length() > 0) {
//            throw new OwnerException(MessageFormat.format("操作失败，{0} 已审核。不能进行归集。",
//                    builder.substring(0, builder.length() - 1)));
//        }
    }

}
