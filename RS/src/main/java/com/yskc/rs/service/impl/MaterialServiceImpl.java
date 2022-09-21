package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.*;
import com.yskc.rs.entity.AccountTitleEntity;
import com.yskc.rs.entity.MaterialEntity;
import com.yskc.rs.entity.SummaryEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.entity.project.ProjectMaterialEntity;
import com.yskc.rs.enums.RdMaterialEnum;
import com.yskc.rs.enums.RsProjectStatusEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.MaterialExcel;
import com.yskc.rs.models.material.*;
import com.yskc.rs.models.project.RdStatusModel;
import com.yskc.rs.models.projectmaterial.ProjectRdMaterialTotalModel;
import com.yskc.rs.service.MaterialService;
import com.yskc.rs.service.exportFileImpl.MaterialPlanForm;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
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
public class MaterialServiceImpl extends ServiceImpl<MaterialDao, MaterialEntity> implements MaterialService {

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


    @Override
    public PageModel<List<AppMaterialModel>> queryMaterial(UserInfo userInfo, QueryMaterialModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("m.mcode");
            descs.add("acqDate");
            page.setDescs(descs);
        }
        Date selectDate = query.getSelectDate();
        if (selectDate != null) {
            Date startDate = DateUtil.getMonthFirstDay(selectDate);
            Calendar ct = Calendar.getInstance();
            ct.setTime(startDate);
            ct.add(Calendar.MONTH, +1);
            Date endDate = ct.getTime();
            query.setStartDate(startDate);
            query.setEndDate(endDate);
        }
        return PageUtils.build(page, materialDao.queryMaterial(userInfo.getCompanyId(), page, query));
    }

    @Override
    public boolean addMaterial(UserInfo userInfo, MaterialEntity entity) throws OwnerException {
        entity.setCompanyId(userInfo.getCompanyId());
        // 如果没单价
        if (StringUtils.isEmpty(entity.getUnitPrice())) {
            if (StringUtils.isEmpty(entity.getQuantity())) {
                entity.setUnitPrice(entity.getTotalAmount());
                entity.setQuantity(new BigDecimal(1));
            } else {
                entity.setUnitPrice(entity.getTotalAmount().divide(entity.getQuantity(), 20, BigDecimal.ROUND_HALF_UP));
            }
        }
        // 如果有单价
        else {
            if (StringUtils.isEmpty(entity.getQuantity())) {
                entity.setQuantity(entity.getTotalAmount().divide(entity.getUnitPrice(), 20, BigDecimal.ROUND_HALF_UP));
            }
        }
        entity.setRemainQuantity(entity.getQuantity());
        entity.setCreateTime(new Date());
        entity.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        entity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        entity.setMsCreatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        entity.setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        entity.setLastUpdateTime(new Date());
        return materialDao.insert(entity) > 0;
    }

    @Override
    public boolean editMaterial(UserInfo userInfo, MaterialEntity entity) throws OwnerException {
        entity.setCompanyId(userInfo.getCompanyId());
        // 如果没单价
        if (StringUtils.isEmpty(entity.getUnitPrice())) {
            if (StringUtils.isEmpty(entity.getQuantity())) {
                entity.setUnitPrice(entity.getTotalAmount());
                entity.setQuantity(new BigDecimal(1));
            } else {
                entity.setUnitPrice(entity.getTotalAmount().divide(entity.getQuantity(), 20, BigDecimal.ROUND_HALF_UP));
            }
        }
        // 如果有单价
        else {
            if (StringUtils.isEmpty(entity.getQuantity())) {
                entity.setQuantity(entity.getTotalAmount().divide(entity.getUnitPrice(), 20, BigDecimal.ROUND_HALF_UP));
            }
        }
        MaterialEntity oldEntity = materialDao.getOldQuantity(entity.getId());
        if (null == oldEntity) {
            throw new OwnerException("操作失败，数据不存在！");
        }
        BigDecimal remain;
        BigDecimal sub = entity.getQuantity().subtract(oldEntity.getQuantity());
        if (sub.compareTo(BigDecimal.ZERO) != 0) {
            remain = oldEntity.getRemainQuantity().add(sub);
            if (remain.compareTo(BigDecimal.ZERO) < 0) {
                throw new OwnerException("保存失败，修改数量不能低于原数量。");
            }
        } else {
            remain = oldEntity.getRemainQuantity();
        }
        entity.setRemainQuantity(remain);
        entity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        entity.setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        entity.setLastUpdateTime(new Date());
        return materialDao.updateById(entity) > 0;
    }

    @Override
    public boolean delMaterial(Integer companyId, MaterialEntity model) throws OwnerException {
        List<ProjectMaterialEntity> projectMaterialEntities = projectMaterialDao.queryProjectMaterialByMaterialDataId(companyId, model.getId());
        if (projectMaterialEntities != null && projectMaterialEntities.size() > 0) {
            throw new OwnerException("已存在[数据归集]中，不能删除!");
        }
        return materialDao.deleteById(model.getId()) > 0;
    }

    @Override
    public ImportMaterialModel importMaterial(UserInfo info, List<MaterialExcel> materialExcels, Integer type,
                                              Integer year, Boolean isRD)
            throws OwnerException {
        ImportMaterialModel importMaterialModel = new ImportMaterialModel();
        if (materialExcels.size() > 0) {
            List<String> mcodeList = materialExcels.stream().filter(a -> !StringUtils.isEmpty(a.getMcode())).map(a -> a.getMcode()).collect(Collectors.toList());
            List<MaterialEntity> byMcodes = new ArrayList<>();
            if (!CollectionUtils.isEmpty(mcodeList)) {
                byMcodes = materialDao.getByMcodes(info.getCompanyId(), mcodeList, type);
            }
            List<String> sqlList = new ArrayList<>();
            if (byMcodes.size() > 0) {
                for (MaterialEntity entity : byMcodes) {
                    String checkStr = entity.getMcode() + ":" + entity.getBillNo() + ":" + entity.getAcqDate();
                    if (!sqlList.contains(checkStr)) {
                        sqlList.add(checkStr);
                    }
                }
            }
            List<String> checkList = new ArrayList<>();
            List<String> repeatList = new ArrayList<>();
            for (int i = 0; i < materialExcels.size(); i++) {
                MaterialExcel model = materialExcels.get(i);
                String mcode = model.getMcode() == null ? "" : model.getMcode();
                String billNo = model.getBillNo() == null ? "" : model.getBillNo();
                String acqDate = model.getAcqDate() == null ? "" : model.getAcqDate() + "";
                String checkStr = mcode + ":" + billNo + ":" + acqDate;
                if (!checkList.contains(checkStr)) {
                    checkList.add(checkStr);
                } else {
                    if (!repeatList.contains((i + 2) + "")) {
                        repeatList.add((i + 2) + "");
                    }
                }
                if (sqlList.contains(checkStr)) {
                    if (!repeatList.contains((i + 2) + "")) {
                        repeatList.add((i + 2) + "");
                    }
                }
            }
            if (repeatList.size() > 0) {
                importMaterialModel.setResult(1);
                importMaterialModel.setMessage("行[" + String.join(",", repeatList) + "]已存在现有数据中,确定要导入吗?");
            } else {
                confirmImport(info, materialExcels, type, year, isRD);
            }
        } else {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        return importMaterialModel;
    }


    @Override
    public boolean confirmImport(UserInfo info, List<MaterialExcel> materialExcels, Integer type, Integer year, Boolean isRD)
            throws OwnerException {
        if (materialExcels.size() > 0) {
            List<String> fullAccountNameList = new ArrayList<>();
            Map<String, Boolean> rdTitleMap = new LinkedHashMap<>();
            for (MaterialExcel item : materialExcels) {
                if (!StringUtils.isEmpty(item.getFullAccountName())) {
                    if (!item.getFullAccountName().endsWith(Constant.PATH_SEPARATOR)) {
                        item.setFullAccountName(item.getFullAccountName() + Constant.PATH_SEPARATOR);
                    }
                    fullAccountNameList.add(item.getFullAccountName());
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
                List<ProjectEntity> projects = projectDao.getByRdTitles(rdTitleMap.keySet(), info.getCompanyId());
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
            List<AccountTitleEntity> accountTitleEntityList = fullAccountNameList.size() == 0 ? new ArrayList<>() : accountTitleDao.getByFullAccountName(info.getCompanyId(), fullAccountNameList);
            Map<String, AccountTitleEntity> accountTitleEntityMap = accountTitleEntityList.stream().collect(Collectors.toMap(AccountTitleEntity::getFullAccountName, b -> b));
            List<MaterialEntity> insertMaterialEntitys = new ArrayList<>();
            for (int i = 0; i < materialExcels.size(); i++) {
                MaterialExcel materialExcel = materialExcels.get(i);
                AccountTitleEntity accountTitleEntity = accountTitleEntityMap.get(materialExcel.getFullAccountName()) != null ? accountTitleEntityMap.get(materialExcel.getFullAccountName()) : null;
                int accountTitleId = 0;
                if (accountTitleEntity != null) {
                    accountTitleId = accountTitleEntity.getId();
                }
                MaterialEntity entity = setMaterial(info, materialExcel, 0, type, accountTitleId, isRD);
                insertMaterialEntitys.add(entity);
            }
            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
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
                TransactionUtils.commit(transactionStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(transactionStatus);
                logger.error("importMaterial", ex);
                throw ex;
            }
        } else {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        return true;
    }

    private MaterialEntity setMaterial(UserInfo info, MaterialExcel materialExcel, Integer deptId, Integer type,
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

    @Override
    public List<MaterialExcel> exportMaterialModel(AppMaterialModel model) {
        Date selectDate = model.getSelectDate();
        if (selectDate != null) {
            Date startDate = DateUtil.getMonthFirstDay(selectDate);
            Calendar ct = Calendar.getInstance();
            ct.setTime(startDate);
            ct.add(Calendar.MONTH, +1);
            Date endDate = ct.getTime();
            model.setStartDate(startDate);
            model.setEndDate(endDate);
        }
        return materialDao.getMaterialData(model);
    }

    @Override
    public boolean editDept(UserInfo userInfo, AppMaterialModel model) {
        List<MaterialEntity> materialList = model.getMaterialList();
        Integer rdDeptId = 0;
        for (int i = 0; i < materialList.size(); i++) {
            MaterialEntity materialEntity = materialList.get(i);
            materialEntity.setRdDeptId(rdDeptId);
        }
        updateBatchById(materialList);

        return true;
    }

    @Override
    public boolean setType(UserInfo userInfo, AppMaterialModel model) {
        List<MaterialEntity> materialList = model.getMaterialList();
        Integer type = model.getRdType();
        for (int i = 0; i < materialList.size(); i++) {
            MaterialEntity materialEntity = materialList.get(i);
            materialEntity.setType(type);
        }
        updateBatchById(materialList);

        return true;
    }

    @Override
    public boolean delSelect(List<Integer> ids) throws OwnerException {
        List<Integer> materialIds = projectMaterialDao.selectByMaterialIds(ids);
        Collection<Integer> result = CollUtil.disjunction(ids, materialIds);
        if (CollectionUtils.isEmpty(result)) {
            throw new OwnerException("所选物料已全部在[数据归集]中使用，不能删除!");
        }
        return deleteBatchIds(result);
    }

    private void saveRdAndSummary(List<MaterialEntity> materialList, UserInfo info, Map<String, ProjectEntity> rdMap) throws OwnerException {
        List<ProjectMaterialEntity> projectMaterialList = new ArrayList<>(materialList.size());
        Integer userId = info.getUserId();
        Integer msUserId = info.getMsUserId();
        Integer companyId = info.getCompanyId();
        Date now = new Date();
        ProjectEntity currentProject;
        Integer rdType;
        Map<String, Integer> rdMaterialMap = RdMaterialEnum.getMaterialTitleMap();
        Date maxDate = materialList.get(0).getAcqDate();
        Date minDate = maxDate;
        Map<String, String> projectMonthMap = new LinkedHashMap<>();
        Date currentAcqDate;
        int count = 1;
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
        }
        List<Integer> projectIds = rdMap.values().stream().map(a -> a.getId()).collect(Collectors.toList());
        checkProjectsStatus(projectIds, projectMonthMap);
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

    public void checkProjectsStatus(List<Integer> projectIds, Map<String, String> projectMonthMap) throws OwnerException {
        List<RdStatusModel> statusList = projectDao.getRdsStatus(projectIds);
        Integer finalStatus = RsProjectStatusEnum.FINALIZATION.getStatus();
        StringBuilder builder = new StringBuilder();
        for (RdStatusModel rdStatus : statusList) {
            String monthStr = DateUtil.format(rdStatus.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT);
            String monthKey = rdStatus.getProjectId() + monthStr;
            if (finalStatus.equals(rdStatus.getStatus()) &&
                    projectMonthMap.containsKey(monthKey)) {
                builder.append(projectMonthMap.get(monthKey)).append("：").append(monthStr).append("、");
            }
        }
        if (builder.length() > 0) {
            throw new OwnerException(MessageFormat.format("操作失败，{0} 已审核。不能进行归集。",
                    builder.substring(0, builder.length() - 1)));
        }
    }

    @Override
    public PageModel<List<MaterialPlanModel>> getMaterialPlan(QueryMaterialTrackModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("acqDate");
            page.setAscs(ascs);
        }
        List<MaterialPlanModel> list = new ArrayList<>();
        if (query.getMonth() != null) {
            Date beginDate = DateUtil.getMonthFirstDay(query.getMonth());
            Date endDate = DateUtil.getMonthLastDay(query.getMonth());
            List<Integer> costTypes = new ArrayList<>();
            int type = MaterialPlanForm.getType(query.getDocFileId(),costTypes);
            list = materialDao.getMaterialPlanData(page, query.getProjectId(), beginDate, endDate, costTypes, type);
        }
        return PageUtils.build(page, list);
    }

    @Override
    public Boolean setPicker(SetMaterialPickerModel setPicker, UserInfo userInfo) {
        return materialDao.setPicker(setPicker, userInfo.getMsUserId(), userInfo.getUserId(), new Date()) > 0;
    }
}
