package com.yskc.ms.service.impl.rs;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.rs.CompanyDao;
import com.yskc.ms.dao.rs.RsPatentApplyDao;
import com.yskc.ms.dao.rs.RsPatentCostDao;
import com.yskc.ms.dao.rs.RsPatentDao;
import com.yskc.ms.entity.rs.RsPatentApplyEntity;
import com.yskc.ms.entity.rs.RsPatentCostEntity;
import com.yskc.ms.entity.rs.RsPatentEntity;
import com.yskc.ms.entity.rs.models.CompanyModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.*;
import com.yskc.ms.service.rs.RsPatentService;
import com.yskc.ms.utils.TransactionUtils;
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
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/7/1 8:38
 * description:
 */
@Service
public class RsPatentServiceImpl implements RsPatentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsPatentDao rsPatentDao;

    @Autowired
    private RsPatentCostDao rsPatentCostDao;

    @Autowired
    private RsPatentApplyDao rsPatentApplyDao;

    @Autowired
    private CompanyDao companyDao;

    @Override
    public PageModel<List<PatentModel>> queryPatentList(QueryPatentModel queryPatentModel) {
        Pagination page = queryPatentModel.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("p.createTime");
            page.setDescs(desc);
        }
        List<PatentModel> patentModels = rsPatentDao.queryPatentList(page, queryPatentModel);
        if (!CollectionUtils.isEmpty(patentModels)) {
            List<String> patentNos = patentModels.stream().map(PatentModel::getPatentNo).collect(Collectors.toList());
            List<PatentModel> list = rsPatentApplyDao.queryListByPatentNos(patentNos);
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, PatentModel> patentModelMap = list.stream().collect(Collectors.toMap(PatentModel::getPatentNo, b -> b));
                for (PatentModel model : patentModels) {
                    if (patentModelMap.containsKey(model.getPatentNo())) {
                        model.setCompanyIdArry(patentModelMap.get(model.getPatentNo()).getCompanyIdArry());
                        model.setAddress(patentModelMap.get(model.getPatentNo()).getAddress());
                        model.setApplyName(patentModelMap.get(model.getPatentNo()).getApplyName());
                    }
                }
            }
        }
        return PageUtils.build(page, patentModels);
    }

    @Override
    public Boolean updatePatentCustomerId(PatentModel model, UserInfo userInfo) {
        RsPatentEntity rsPatentEntity = rsPatentDao.selectById(model.getId());
        rsPatentEntity.setCompanyId(model.getCompanyId());
        rsPatentEntity.setLastUpdateTime(new Date());
        rsPatentEntity.setMsLastUpdatorId(userInfo.getId());
        return rsPatentDao.updateById(rsPatentEntity) > 0;
    }

    @Override
    public boolean updatePatent(UserInfo info, PatentModel model) {
        RsPatentEntity rsPatentEntity = rsPatentDao.selectById(model.getId());
        buildPatent(rsPatentEntity,model,info);
        return rsPatentDao.updateById(rsPatentEntity) > 0;
    }

    private void buildPatent(RsPatentEntity patent,PatentModel model,UserInfo info){
        patent.setMainType(model.getMainType());
        patent.setMsLastUpdatorId(info.getId());
        patent.setLastUpdateTime(new Date());
        patent.setPatentNo(model.getPatentNo());
        patent.setPatentName(model.getPatentName());
        patent.setApplyDateTime(model.getApplyDateTime());
        patent.setInventor(model.getInventor());
        patent.setLastUpdatorId(-1);
        patent.setClaimNum(model.getClaimNum());
        patent.setClaimContent(model.getClaimContent());
        patent.setUsedCnt(model.getUsedCnt());
        patent.setSpecification(model.getSpecification());
        patent.setAuthDate(model.getAuthDate());
        patent.setAuthDate(model.getAuthDate());
    }

    @Override
    public boolean addPatent(PatentModel model, UserInfo info) throws OwnerException {
        RsPatentEntity rsPatentEntity = rsPatentDao.queryPatentByNo(model.getPatentNo());
        if (rsPatentEntity != null) {
            throw new OwnerException("专利号已存在，请修改后再添加");
        }
        rsPatentEntity = new RsPatentEntity();
        rsPatentEntity.setCreateTime(new Date());
        rsPatentEntity.setMsCreatorId(info.getId());
        rsPatentEntity.setCreatorId(-1);
        rsPatentEntity.setLastUpdatorId(-1);
        rsPatentEntity.setSource(0);
        buildPatent(rsPatentEntity,model,info);
        return rsPatentDao.insert(rsPatentEntity) > 0;
    }

    @Override
    public Boolean checkPatentNo(String patentNo) throws OwnerException {
        RsPatentEntity rsPatentEntity = rsPatentDao.queryPatentByNo(patentNo);
        if (rsPatentEntity != null) {
            throw new OwnerException("专利号已存在");
        }
        return true;
    }

    @Override
    public boolean delete(PatentModel model) throws OwnerException {
        RsPatentEntity rsPatentEntity = rsPatentDao.selectById(model.getId());
        if (rsPatentEntity.getProjectId() != null && rsPatentEntity.getProjectId() != 0) {
            throw new OwnerException("该专利已关联项目，删除失败");
        }

        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            rsPatentDao.deleteById(model.getId());
            rsPatentApplyDao.deleteByPatentNo(model.getPatentNo());
            TransactionUtils.commit(DataSourceEnum.RS, status);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.RS, status);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }
    }

    @Override
    public List<RsPatentApplyModel> getDataByPatentNo(String patentNo) {
        List<RsPatentApplyModel> models = rsPatentApplyDao.getDataByPatentNo(patentNo);
        return models;
    }

    @Override
    public boolean importPatent(UserInfo info, List<Map<String, Object>> list) throws OwnerException {
        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        List<RsPatentImportModel> patentImportModels = new ArrayList<>();
        List<String> companyNames = new ArrayList<>();
        for (Map<String, Object> map : list) {
            String patentNo = String.valueOf(map.get("专利号") != null ? map.get("专利号") : "");
            String patentName = String.valueOf(map.get("专利名称") != null ? map.get("专利名称") : "");
            String mainType = String.valueOf(map.get("专利类型") != null ? map.get("专利类型") : "");
            String companyName = String.valueOf(map.get("客户名称") != null ? map.get("客户名称") : "");
            if (StringUtils.isEmpty(patentNo) || StringUtils.isEmpty(patentName) || StringUtils.isEmpty(mainType)||StringUtils.isEmpty(companyName)) {
                throw new OwnerException("专利号,专利名称,专利类型,客户名称不能为空,请检查后再导入");
            }
            companyNames.add(companyName);
            RsPatentImportModel rsPatentImportModel = new RsPatentImportModel();
            rsPatentImportModel.setPatentNo(patentNo);
            rsPatentImportModel.setPatentName(patentName);
            rsPatentImportModel.setMainType(mainType);
            rsPatentImportModel.setCompanyName(companyName);
            rsPatentImportModel.setInventor(String.valueOf(map.get("发明人") != null ? map.get("发明人") : ""));
            String dateStr = String.valueOf(map.get("申请日期"));
            Date date = DateUtil.getDateByString(dateStr);
            rsPatentImportModel.setApplyDateTime(date);
            patentImportModels.add(rsPatentImportModel);
        }

        List<CompanyModel> companys = companyDao.getCompanyByNames(companyNames);
        if (CollectionUtils.isEmpty(companys)){
            throw new OwnerException("所有导入的客户名称都不存在，请检查！");
        }
        Map<String, Integer> companyMap = companys.stream().collect(Collectors.toMap(CompanyModel::getCompanyName, CompanyModel::getId));

        List<String> patentNos = patentImportModels.stream().map(RsPatentImportModel::getPatentNo).collect(Collectors.toList());
        Map<String, RsPatentImportModel> dataMap = new HashMap<>();
        String mainType;
        for (RsPatentImportModel model : patentImportModels) {
            if (dataMap.containsKey(model.getPatentNo())) {
                throw new OwnerException("存在重复的专利号：" + model.getPatentNo() + ",请修改后再导入");
            }
            dataMap.put(model.getPatentNo(), model);
            mainType = model.getMainType();
            if (!mainType.equals("发明专利") && !mainType.equals("实用新型") && !mainType.equals("外观设计")) {
                String message = MessageFormat.format("专利号：{0}的类型应为发明专利,实用新型,外观设计三种之一", model.getPatentNo());
                throw new OwnerException(message);
            }
            if (companyMap.containsKey(model.getCompanyName())){
                model.setCompanyId(companyMap.get(model.getCompanyName()));
            }else {
                throw new OwnerException("客户名称：【"+model.getCompanyName()+"】不在客户列表中！");
            }

        }
        List<RsPatentEntity> patentEntities = rsPatentDao.queryListByPatentNos(patentNos);
        List<RsPatentImportModel> insertModels = new ArrayList<>();
        List<RsPatentEntity> updateModels = new ArrayList<>();
        Map<String, RsPatentEntity> existPatentMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(patentEntities)) {
            existPatentMap = patentEntities.stream().collect(Collectors.toMap(a -> a.getPatentNo(), a -> a));
        }
        for (RsPatentImportModel patentModel : patentImportModels) {
            if (existPatentMap.containsKey(patentModel.getPatentNo())) {
                RsPatentEntity rsPatentEntity = existPatentMap.get(patentModel.getPatentNo());
                rsPatentEntity.setPatentName(patentModel.getPatentName());
                rsPatentEntity.setInventor(patentModel.getInventor());
                rsPatentEntity.setApplyDateTime(patentModel.getApplyDateTime());
                rsPatentEntity.setMainType(patentModel.getMainType());
                rsPatentEntity.setLastUpdateTime(new Date());
                rsPatentEntity.setMsLastUpdatorId(info.getId());
                rsPatentEntity.setCompanyId(patentModel.getCompanyId());
                updateModels.add(rsPatentEntity);
            } else {
                patentModel.setLastUpdateTime(new Date());
                patentModel.setMsLastUpdatorId(info.getId());
                patentModel.setCreateTime(new Date());
                patentModel.setCreatorId(-1);
                patentModel.setLastUpdatorId(-1);
                patentModel.setMsCreatorId(info.getId());
                insertModels.add(patentModel);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (insertModels.size() > 0) {
                rsPatentDao.insertPatentList(insertModels);
            }
            if (updateModels.size() > 0) {
                rsPatentDao.updatePatentList(updateModels);
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }


    @Override
    public List<RsPatentEntity> getAll(List<Integer> ids, String patentNo) {
        if (CollectionUtils.isEmpty(ids)) {
            if (StringUtils.isEmpty(patentNo)) {
                return null;
            }
            ids = null;
        }
        List<String> patentNos = null;
        if (!StringUtils.isEmpty(patentNo)) {
            patentNo = patentNo.replaceAll("，", ",");
            String[] arr = patentNo.split(",");
            patentNos = Arrays.asList(arr);
            if (CollectionUtils.isEmpty(patentNos)) {
                patentNos = null;
            }
        }
        return rsPatentDao.getAll(ids, patentNos);
    }


    @Override
    public void savePatent(PatentUnionModel unionModel, UserInfo info) {
        Date now = new Date();
        String patentNo = unionModel.getPatent().getPatentNo();
        try {
            if (!CollectionUtils.isEmpty(unionModel.getIsPatentCostList())) {
                BigDecimal amount = BigDecimal.ZERO;
                Date expiryDate = unionModel.getIsPatentCostList().get(0).getLimitDate();
                long currentTime = now.getTime();
                long tempTime = 0;
                // 正序 截至日期大于当前日期第一条数据为最近数据
                for (int i = 0; i < unionModel.getIsPatentCostList().size(); i++) {
                    RsPatentCostEntity patentCost = unionModel.getIsPatentCostList().get(i);
                    tempTime = patentCost.getLimitDate().getTime();
                    if (tempTime <= currentTime) {
                        amount = amount.add(patentCost.getAmount());
                        if ((i + 1) < unionModel.getIsPatentCostList().size()) {
                            expiryDate = unionModel.getIsPatentCostList().get(i + 1).getLimitDate();
                        }
                        continue;
                    }
                    if (tempTime <= expiryDate.getTime()) {
                        amount = amount.add(patentCost.getAmount());
                    } else {
                        break;
                    }
                }

                unionModel.getPatent().setExpiryDate(expiryDate);
                unionModel.getPatent().setExpiryAmount(amount);
            } else {
                unionModel.getPatent().setExpiryDate(null);
                unionModel.getPatent().setExpiryAmount(BigDecimal.ZERO);
            }
            rsPatentDao.updateAllColumnById(unionModel.getPatent());
        } catch (Exception e) {
            logger.error(MessageFormat.format("专利号{0}保存失败。", patentNo));
            logger.error(e.getMessage(), e);
        }
        setPatentApply(patentNo, unionModel);

        savePatentCost(patentNo, now, info, unionModel);
    }

    @Override
    public PatentSpecificationModel getSpecification(Integer id) throws OwnerException {
        if (id == null) {
            throw new OwnerException("无法获取权利要求内容及说明书内容");
        }
        return rsPatentDao.getSpecification(id);
    }

    private void setPatentApply(String patentNo, PatentUnionModel unionModel) {
        if (!CollectionUtils.isEmpty(unionModel.getPatentApplyList())) {
            try {
                List<RsPatentApplyEntity> applyList = rsPatentApplyDao.getEntityDataByPatentNo(patentNo);
                if (CollectionUtils.isEmpty(applyList)) {
                    rsPatentApplyDao.insertBatch(unionModel.getPatentApplyList());
                } else {
                    Map<String, Integer> existApplyMap = new HashMap<>();
                    applyList.forEach(item -> {
                        existApplyMap.put(item.getApplyName(), item.getCompanyId());
                    });
                    Set<Integer> existIdSet = new HashSet<>();
                    List<RsPatentApplyEntity> insertApplyList = new ArrayList<>();
                    unionModel.getPatentApplyList().forEach(item -> {
                        if (!existApplyMap.containsKey(item.getApplyName())) {
                            insertApplyList.add(item);
                        } else {
                            existIdSet.add(existApplyMap.get(item.getApplyName()));
                        }
                    });
                    Set<Integer> delIdSet = new HashSet<>();
                    existApplyMap.values().forEach(id -> {
                        if (!existIdSet.contains(id)) {
                            delIdSet.add(id);
                        }
                    });
                    if (!CollectionUtils.isEmpty(delIdSet)) {
                        rsPatentApplyDao.deleteBatchIds(delIdSet);
                    }
                    if (!CollectionUtils.isEmpty(insertApplyList)) {
                        rsPatentApplyDao.insertBatch(insertApplyList);
                    }
                }
            } catch (Exception e) {
                logger.error(MessageFormat.format("专利号{0},保存申请人失败。", patentNo));
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 保存专利费用
     *
     * @param patentNo
     * @param now
     * @param info
     * @param unionModel
     */
    private void savePatentCost(String patentNo, Date now, UserInfo info, PatentUnionModel unionModel) {

        List<RsPatentCostEntity> modifyCostList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(unionModel.getIsPatentCostList())) {
            modifyCostList.addAll(unionModel.getIsPatentCostList());
        }
        if (!CollectionUtils.isEmpty(unionModel.getPatentCostList())) {
            modifyCostList.addAll(unionModel.getPatentCostList());
        }
        if (!CollectionUtils.isEmpty(modifyCostList)) {
            List<RsPatentCostEntity> existCostList = rsPatentCostDao.getPatentCost(patentNo);
            Map<String, RsPatentCostEntity> costMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(existCostList)) {
                for (RsPatentCostEntity cost : existCostList) {
                    costMap.put(cost.getCostType(), cost);
                }
            }
            try {
                List<RsPatentCostEntity> updateCostList = new ArrayList<>();
                List<RsPatentCostEntity> insertCostList = new ArrayList<>();
                modifyCostList.forEach(item -> {
                    if (costMap.containsKey(item.getCostType())) {
                        RsPatentCostEntity cost = costMap.get(item.getCostType());
                        cost.setPay(item.getPay());
                        cost.setPayDateTime(item.getPayDateTime());
                        cost.setAmount(item.getAmount());
                        cost.setLastUpdateTime(now);
                        cost.setMsLastUpdatorId(info.getId());
                        cost.setReceiptNo(item.getReceiptNo());
                        cost.setStatus(item.getStatus());
                        cost.setPayer(item.getPayer());
                        updateCostList.add(cost);
                    } else {
                        item.setLastUpdatorId(-1);
                        item.setLastUpdateTime(now);
                        item.setCreateTime(now);
                        item.setCreatorId(-1);
                        item.setMsCreatorId(info.getId());
                        item.setMsLastUpdatorId(info.getId());
                        item.setRemind(false);
                        insertCostList.add(item);
                    }
                });
                if (!CollectionUtils.isEmpty(updateCostList)) {
                    rsPatentCostDao.updateBatch(updateCostList);
                }

                if (!CollectionUtils.isEmpty(insertCostList)) {
                    rsPatentCostDao.insertBatch(insertCostList);
                }
            } catch (Exception e) {
                logger.error(MessageFormat.format("专利号{0},保存费用失败。", patentNo));
                logger.error(e.getMessage(), e);
            }
        }
    }


}
