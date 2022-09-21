package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.tech.*;
import com.yskc.rs.entity.tech.BeianChangedEntity;
import com.yskc.rs.entity.tech.BeianEntity;
import com.yskc.rs.entity.tech.BeianSummaryEntity;
import com.yskc.rs.entity.tech.Investment;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.*;
import com.yskc.rs.service.BeianService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: hck
 * @DateTime: 2021/3/18 15:45
 * @Description:
 */
@Service
public class BeianServiceImpl implements BeianService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BeianDao beianDao;
    @Autowired
    private InvestmentDao investmentDao;
    @Autowired
    private BeianSummaryDao beianSummaryDao;
    @Autowired
    private BeianChangedDao beianChangedDao;
    @Autowired
    private TechEquipmentDao techEquipmentDao;

    @Override
    public PageModel<List<BeianInfoModel>> getList(Integer companyId, QueryBeianModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("b.lastUpdateTime");
            page.setDescs(descs);
        }
        List<BeianInfoModel> models=beianDao.getList(page, companyId, query);
//        if(!CollectionUtils.isEmpty(models)){
//            List<Integer> beianIds=models.stream().map(e->e.getId()).collect(Collectors.toList());
//         List<CountInvoiceModel> countInvoiceModels=investmentDao.getByBeianId(beianIds);
//            if(!CollectionUtils.isEmpty(countInvoiceModels)){
//                Map<Integer, Map<Integer, BigDecimal>> dataMap=new HashMap<>();
//                for (CountInvoiceModel model:countInvoiceModels){
//                    if(!dataMap.containsKey(model.getBeianId())){
//                        Map<Integer,BigDecimal> praMap=new HashMap<>();
//                        praMap.put(model.getType(),model.getCountData());
//                        dataMap.put(model.getBeianId(),praMap);
//                    }else{
//                        Map<Integer,BigDecimal> existMap=dataMap.get(model.getBeianId());
//                        existMap.put(model.getType(),model.getCountData());
//                    }
//                }
//                for (BeianInfoModel beianInfo:models){
//                    if(dataMap.containsKey(beianInfo.getId())){
//                        beianInfo.setDataMap(dataMap.get(beianInfo.getId()));
//                    }
//                }
//            }
//        }
        return PageUtils.build(page,models);
    }

    @Override
    public Boolean save(UserInfo userInfo, BeianInfoModel model) throws OwnerException {
        BeianEntity beian = beianDao.selectById(model.getId());
        if (beian == null) {
            throw new OwnerException("未查询到数据，编辑失败，请联系管理员！");
        }
        Date date = new Date();
        Integer companyId = userInfo.getCompanyId();
        BeanUtils.copyProperties(model, beian);
        beian.update(userInfo.getUserId(),userInfo.getMsUserId(),date);
        Integer summaryId = beianSummaryDao.getByBeian(beian.getId(), companyId);
        BeianSummaryEntity summaryEntity = new BeianSummaryEntity();
        BeanUtils.copyProperties(model,summaryEntity);
        List<BeianChangedModel> changedList = model.getChangedList();
        List<BeianChangedEntity> insertList = new ArrayList<>();
        List<BeianChangedEntity> updateList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(changedList)){
            beian.setChange(true);
            StringBuilder changedDates = null;
            for (BeianChangedModel changedModel : changedList) {
                if (changedDates==null){
                    changedDates = new StringBuilder(DateUtil.format(changedModel.getChangeLetterDate(), "yyyy-MM-dd"));
                }else {
                    changedDates.append(",").append(DateUtil.format(changedModel.getChangeLetterDate(), "yyyy-MM-dd"));
                }
                BeianChangedEntity entity = new BeianChangedEntity();
                BeanUtils.copyProperties(changedModel,entity);
                entity.setCompanyId(companyId);
                entity.setBeianId(model.getId());
                if (changedModel.getId()!=null){
                    ToolUtils.entityUpdate(entity,userInfo.getId(),userInfo.getMsUserId(),date);
                    updateList.add(entity);
                }else {
                    ToolUtils.entityCreate(entity,userInfo.getId(),userInfo.getMsUserId(),date);
                    insertList.add(entity);
                }
            }
            beian.setChangedDates(changedDates.toString());
        }

        TechEquipmentModel total = techEquipmentDao.getTotal(companyId, model.getId());
        if (total!=null){
            beian.setEquipmentCnt(total.getId());
            beian.setEquipmentQuantity(total.getQuantity().intValue());
            beian.setPowerUsed(total.getPowerUsed().divide(Constant.TEN_THOUSAND,2,BigDecimal.ROUND_HALF_UP));
            beian.setEnergyUsed(total.getPowerUsed().multiply(BigDecimal.valueOf(1.229)).divide(Constant.TEN_THOUSAND,2,BigDecimal.ROUND_HALF_UP));
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            beianDao.updateById(beian);
            if (summaryId!=null){
                summaryEntity.setId(summaryId);
                ToolUtils.entityUpdate(summaryEntity,userInfo.getId(),userInfo.getMsUserId(),date);
                beianSummaryDao.updateById(summaryEntity);
            }else {
                summaryEntity.setId(null);
                summaryEntity.setBeianId(model.getId());
                summaryEntity.setCompanyId(companyId);
                ToolUtils.entityCreate(summaryEntity,userInfo.getId(),userInfo.getMsUserId(),date);
                beianSummaryDao.insert(summaryEntity);
            }
            if (!CollectionUtils.isEmpty(insertList)){
                beianChangedDao.insertList(insertList);
            }
            if (!CollectionUtils.isEmpty(updateList)){
                beianChangedDao.updateList(updateList);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存数据失败");
        }

        return true;
    }

    @Override
    public BeianInfoModel getBeianInfo(Integer companyId, Integer beianId) {
        BeianInfoModel model=beianDao.getInfo(beianId,companyId);
        if (model!=null){
            List<CountInvoiceModel> invoiceModels = investmentDao.getByBeianId(model.getId());
            if (!CollectionUtils.isEmpty(invoiceModels)){
                model.setInvoiceModel(countComplete(model.getTotalAmount(),model.getTotalAmountTax(),
                        invoiceModels));
            }
            List<CountInvoiceModel> payModels = investmentDao.getPayByBeianId(model.getId());
            if (!CollectionUtils.isEmpty(invoiceModels)){
                model.setPaymentModel(countComplete(model.getTotalAmount(),model.getTotalAmountTax(),
                        payModels));
            }
            model.setChangedList(beianChangedDao.getByBeianId(model.getId(), companyId));
        }

        return model;
    }

    private BeianTaxModel countComplete(BigDecimal beianAmount,BigDecimal beianAmountTax,List<CountInvoiceModel> invoiceDetails) {
        BigDecimal equipmentTax = BigDecimal.ZERO;
        BigDecimal initWorkCapitalTax = BigDecimal.ZERO;
        BigDecimal constructionTax = BigDecimal.ZERO;
        BigDecimal equipmentAmount = BigDecimal.ZERO;
        BigDecimal initWorkCapitalAmount = BigDecimal.ZERO;
        BigDecimal constructionAmount = BigDecimal.ZERO;
        for (CountInvoiceModel invoiceModel : invoiceDetails) {
            BigDecimal data = invoiceModel.getTaxAmount().divide(Constant.TEN_THOUSAND, 4, BigDecimal.ROUND_HALF_UP);
            BigDecimal amount = invoiceModel.getAmount().divide(Constant.TEN_THOUSAND, 4, BigDecimal.ROUND_HALF_UP);
            String parentKey = invoiceModel.getType().substring(0, 4);
            int key = Integer.parseInt(parentKey);
            switch (key) {
                case 1000:
                    equipmentTax = equipmentTax.add(data);
                    equipmentAmount = equipmentAmount.add(amount);
                    break;
                case 2000:
                    constructionTax = constructionTax.add(data);
                    constructionAmount = constructionAmount.add(amount);
                    break;
                case 3000:
                    initWorkCapitalTax = initWorkCapitalTax.add(data);
                    initWorkCapitalAmount = initWorkCapitalAmount.add(amount);
                    break;
                default: break;
            }
        }
        BigDecimal totalTax = equipmentTax.add(constructionTax).add(initWorkCapitalTax);
        BigDecimal fixedTax = equipmentTax.add(constructionTax);
        BigDecimal totalAmount = equipmentAmount.add(constructionAmount).add(initWorkCapitalAmount);
        BigDecimal fixedAmount = equipmentAmount.add(constructionAmount);
        BigDecimal completion = BigDecimal.ZERO;
        BigDecimal completionTax = BigDecimal.ZERO;
        if (beianAmount!=null&&beianAmount.compareTo(BigDecimal.ZERO)>0){
            completion = totalAmount.multiply(BigDecimal.valueOf(100)).divide(beianAmount,2, BigDecimal.ROUND_HALF_UP);
        }
        if (beianAmountTax!=null&&beianAmountTax.compareTo(BigDecimal.ZERO)>0){
            completionTax = totalTax.multiply(BigDecimal.valueOf(100)).divide(beianAmountTax,2, BigDecimal.ROUND_HALF_UP);
        }

        return new BeianTaxModel(totalAmount,fixedAmount,equipmentAmount,initWorkCapitalAmount,constructionAmount,completion,
                totalTax,fixedTax,equipmentTax,constructionTax,initWorkCapitalTax,completionTax);
    }
}
