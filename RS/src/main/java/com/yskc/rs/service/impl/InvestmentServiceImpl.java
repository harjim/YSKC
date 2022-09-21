package com.yskc.rs.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.yskc.rs.dao.tech.*;
import com.yskc.rs.entity.tech.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.AddInvestmentModel;
import com.yskc.rs.models.tech.BankReceiptModel;
import com.yskc.rs.models.tech.ContractDetailModel;
import com.yskc.rs.models.tech.ContractInfoModel;
import com.yskc.rs.models.tech.ContractModel;
import com.yskc.rs.models.tech.CountInvoiceModel;
import com.yskc.rs.models.tech.InvestmentInfoModel;
import com.yskc.rs.models.tech.InvestmentModel;
import com.yskc.rs.models.tech.InvoiceDetailModel;
import com.yskc.rs.models.tech.InvoiceInfoModel;
import com.yskc.rs.models.tech.InvoiceModel;
import com.yskc.rs.models.tech.NameplateModel;
import com.yskc.rs.models.tech.PaymentModel;
import com.yskc.rs.models.tech.QueryBankReceiptModel;
import com.yskc.rs.models.tech.QueryContractModel;
import com.yskc.rs.models.tech.QueryInverstmentModel;
import com.yskc.rs.models.tech.QueryInvoiceModel;
import com.yskc.rs.service.InvestmentService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;

/**
 * @Author: hck
 * @DateTime: 2021/3/19 9:12
 * @Description:
 */
@Service
public class InvestmentServiceImpl implements InvestmentService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InvestmentDao investmentDao;
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private BankReceiptDao bankReceiptDao;
    @Autowired
    private InvoiceDetailDao invoiceDetailDao;
    @Autowired
    private BeianSummaryDao beianSummaryDao;
    @Autowired
    private InvestmentInvoiceDao investmentInvoiceDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ContractDetailDao contractDetailDao;
    @Autowired
    private InvestmentContractDao investmentContractDao;
    @Autowired
    private InvestmentBankReceiptDao investmentBankReceiptDao;
    @Autowired
    private NameplateDao nameplateDao;
    @Autowired
    private BeianDao beianDao;
    @Autowired
    private PaymentDao paymentDao;


    @Override
    public PageModel<List<InvestmentModel>> getList(Integer companyId, QueryInverstmentModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("lastUpdateTime");
            page.setAscs(Arrays.asList("`order`"));
            page.setDescs(descs);
        }
        List<InvestmentModel> models = investmentDao.getList(page, companyId, query);
        if (!CollectionUtils.isEmpty(models)) {
            Map<Integer, InvestmentInfoModel> invoiceMap = new HashMap<>();
            Map<Integer, BigDecimal> bankMap = new HashMap<>();
            List<Integer> ids = models.stream().map(e -> e.getId()).collect(Collectors.toList());
            List<InvestmentInfoModel> invoiceInfos = invoiceDao.countInfo(ids, companyId);
            if (!CollectionUtils.isEmpty(invoiceInfos)) {
                invoiceMap = invoiceInfos.stream().collect(Collectors.toMap(e -> e.getInvestmentId(), e -> e));
            }
            List<InvestmentInfoModel> bankInfos = paymentDao.countInfo(companyId, ids);
            if (!CollectionUtils.isEmpty(bankInfos)) {
                bankMap = bankInfos.stream().collect(Collectors.toMap(e -> e.getInvestmentId(), e -> e.getTotalBank()));
            }
            List<NameplateModel> nameplates = nameplateDao.getByInvestmentIds(ids);
            Map<Integer, List<NameplateModel>> nameplateMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(nameplates)) {
                for (NameplateModel model : nameplates) {
                    if (!nameplateMap.containsKey(model.getInvestmentId())) {
                        nameplateMap.put(model.getInvestmentId(), new ArrayList<>());
                    }
                    nameplateMap.get(model.getInvestmentId()).add(model);
                }
            }
            for (InvestmentModel model : models) {
                if (invoiceMap.containsKey(model.getId())) {
                    model.setCountQuantity(invoiceMap.get(model.getId()).getTotalQuantity());
                    model.setCountAmount(invoiceMap.get(model.getId()).getTotalAmount());
                }
                if (bankMap.containsKey(model.getId())) {
                    model.setCountBank(bankMap.get(model.getId()));
                }
                if (nameplateMap.containsKey(model.getId())) {
                    model.setNameplateModels(nameplateMap.get(model.getId()));
                }
            }

        }
        return PageUtils.build(page, models);
    }

    @Override
    public List<InvoiceModel> getInvoices(Integer companyId, QueryInvoiceModel query) {
        return invoiceDao.getInvoices(companyId, query);
    }

    @Override
    public Boolean delInvoiceDetail(List<InvoiceModel> models,Integer companyId) throws OwnerException{
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<Integer> delIds = models.stream().map(e -> e.getId()).collect(Collectors.toList());
        Integer id = models.get(0).getId();
        List<InvoiceDetail> details = invoiceDetailDao.getRalatedInvocie(delIds, null);

        if (!CollectionUtils.isEmpty(details)) {
            throw new OwnerException(MessageFormat.format("设备名称：{0}，规格型号：{1}，已关联投资清单，不能删除！",
                    details.get(0).getEname(), details.get(0).getEmodal()));
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            invoiceDetailDao.deleteById(id);
            List<InvoiceDetailModel> detailModels = invoiceDetailDao.getByInvoiceId(models.get(0).getInvoiceId(), companyId);
            if (CollectionUtils.isEmpty(detailModels)) {
                invoiceDao.deleteBatchIds(Arrays.asList(models.get(0).getInvoiceId()));
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PageModel<List<InvoiceModel>> getInvoiceDetails(Integer companyId, QueryInvoiceModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("ti.invoiceNo");
            ascs.add("tid.ename");
            page.setAscs(ascs);
        }
        return PageUtils.build(page, invoiceDao.getInvoiceDetails(page, companyId, query));
    }

//    @Override
//    public Boolean relatedInvoice(List<InvoiceModel> models, UserInfo userInfo) {
//        if(CollectionUtils.isEmpty(models)){
//            return true;
//        }
//        List<InvestmentInvoice> invoices=new ArrayList<>();
//        for (InvoiceModel model:models) {
//            InvestmentInvoice investmentInvoice=new InvestmentInvoice();
//            investmentInvoice.create(userInfo.getUserId(),userInfo.getMsUserId(),new Date());
//            investmentInvoice.setInvestmentId(model.getInvestmentId());
//            investmentInvoice.setInvoiceDetailId(model.getId());
//            invoices.add(investmentInvoice);
//        }
//        return investmentInvoiceDao.insertList(invoices);
//
//    }

    @Override
    public Boolean saveInvoice(InvoiceInfoModel model, UserInfo userInfo) throws OwnerException {
        if (model == null) {
            return true;
        }
        Date date = new Date();
        Invoice invoice;
        List<Integer> delIds = new ArrayList<>();
        List<InvoiceDetailModel> models = model.getModels();
        if (CollectionUtils.isEmpty(models)) {
            throw new OwnerException("发票缺少设备信息，请先添加后再保存！");
        }
        List<Integer> detailIds = models.stream().filter(e -> e.getId() != null).map(e -> e.getId()).collect(Collectors.toList());
        Map<Integer, InvoiceDetail> detailMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(detailIds)) {
            List<InvoiceDetail> details = invoiceDetailDao.selectBatchIds(detailIds);
            detailMap = details.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        }
        List<InvoiceDetail> insertList = new ArrayList<>();
        List<InvoiceDetail> updateList = new ArrayList<>();
        if (model.getId() != null) {
            invoice = invoiceDao.selectById(model.getId());
            BeanUtils.copyProperties(model, invoice);
            invoice.update(userInfo.getUserId(), userInfo.getMsUserId(), date);
            List<InvoiceDetailModel> details = invoiceDetailDao.getByInvoiceId(model.getId(), userInfo.getCompanyId());
            List<Integer> existIds = details.stream().map(e -> e.getId()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(detailIds)) {
                existIds.removeAll(detailIds);
                delIds = existIds;
            }
        } else {
            Boolean existInvoice = verifyInvoice(userInfo.getCompanyId(), model.getInvoiceNo());
            if (!existInvoice) {
                throw new OwnerException("存在重复的发票号，请修改！");
            }
            invoice = new Invoice();
            BeanUtils.copyProperties(model, invoice);
            invoice.setCompanyId(userInfo.getCompanyId());
            invoice.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
        }
        for (InvoiceDetailModel detail : model.getModels()) {
            if (detail.getId() != null) {
                InvoiceDetail updateDetail = detailMap.get(detail.getId());
                BeanUtils.copyProperties(detail, updateDetail);
                updateDetail.update(userInfo.getUserId(), userInfo.getMsUserId(), date);
                updateList.add(updateDetail);
            } else {
                InvoiceDetail insertDetail = new InvoiceDetail();
                BeanUtils.copyProperties(detail, insertDetail);
                insertDetail.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
                insertDetail.setCompanyId(userInfo.getCompanyId());
                insertList.add(insertDetail);
            }
        }

        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (model.getId() != null) {
                invoiceDao.updateById(invoice);
            } else {
                invoiceDao.insert(invoice);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                invoiceDetailDao.updateList(updateList);
            }
            if (!CollectionUtils.isEmpty(delIds)) {
                List<InvoiceDetail> details = invoiceDetailDao.getRalatedInvocie(delIds, null);
                if (!CollectionUtils.isEmpty(details)) {
                    throw new OwnerException(MessageFormat.format("设备名称：{0}，规格型号：{1}，已关联投资清单，不能删除！",
                            details.get(0).getEname(), details.get(0).getEmodal()));
                }
                invoiceDetailDao.deleteBatchIds(delIds);
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                for (InvoiceDetail invoiceDetail : insertList) {
                    invoiceDetail.setInvoiceId(invoice.getId());
                }
                invoiceDetailDao.insertList(insertList);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (OwnerException o) {
            throw o;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存数据失败");
        }
        return true;
    }

    @Override
    public List<ContractInfoModel> getContracts(Integer companyId, QueryContractModel query) {
        return contractDao.getContracts(companyId, query);
    }

    @Override
    public Boolean delContractDetail(List<ContractInfoModel> models) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<Integer> delIds = models.stream().filter(e -> e.getId() != null).map(e -> e.getId()).collect(Collectors.toList());
        Integer investmentId = models.get(0).getInvestmentId();
        if (CollectionUtils.isEmpty(delIds)) {
            throw new OwnerException("删除数据异常，请联系管理员！");
        }
        return contractDetailDao.updateByInvestmentIds(delIds) > 0;
    }

    @Override
    public PageModel<List<ContractInfoModel>> getContractDetail(Integer companyId, QueryContractModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("tc.contractNo");
            ascs.add("tcd.ename");
            page.setAscs(ascs);
        }
        return PageUtils.build(page, contractDao.getContractDetails(page, companyId, query));
    }

//    @Override
//    public Boolean relatedContract(List<ContractInfoModel> models, UserInfo userInfo) {
//        if(CollectionUtils.isEmpty(models)){
//            return true;
//        }
//        List<InvestmentContract> contracts=new ArrayList<>();
//        for (ContractInfoModel model:models){
//            InvestmentContract contract=new InvestmentContract();
//            contract.setContactDetailId(model.getId());
//            contract.setInvestmentId(model.getInvestmentId());
//            contract.create(userInfo.getUserId(),userInfo.getMsUserId(),new Date());
//            contracts.add(contract);
//        }
//        return investmentContractDao.insertList(contracts)>0;
//    }

    @Override
    public Boolean saveContract(ContractModel model, UserInfo userInfo) throws OwnerException {
        if (model == null) {
            return true;
        }
        List<ContractDetailModel> detailModels = model.getDetailModels();
        if (CollectionUtils.isEmpty(detailModels)) {
            throw new OwnerException("合同缺少设备信息，请先添加后再保存！");
        }
        List<Integer> ids = detailModels.stream().filter(e -> e.getId() != null).map(e -> e.getId()).collect(Collectors.toList());
        Map<Integer, ContractDetail> detailMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(ids)) {
            List<ContractDetail> details = contractDetailDao.selectBatchIds(ids);
            detailMap = details.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        }
        Date date = new Date();
        Contract contract;
        List<Integer> delIds = new ArrayList<>();
        if (model.getId() != null) {
            contract = contractDao.selectById(model.getId());
            BeanUtils.copyProperties(model, contract);
            contract.update(userInfo.getUserId(), userInfo.getMsUserId(), date);
            List<ContractDetailModel> contractDetails = contractDetailDao.getByContractId(model.getId());
            if (!CollectionUtils.isEmpty(contractDetails)) {
                List<Integer> existIds = contractDetails.stream().map(e -> e.getId()).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(ids)) {
                    existIds.removeAll(ids);
                    delIds = existIds;
                }
            }
        } else {
            Boolean existContract = verifyContract(userInfo.getCompanyId(), model.getContractNo());
            if (!existContract) {
                throw new OwnerException("存在重复的合同编号，请修改！");
            }
            contract = new Contract();
            BeanUtils.copyProperties(model, contract);
            contract.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
            contract.setCompanyId(userInfo.getCompanyId());
        }
        List<ContractDetail> insertList = new ArrayList<>();
        List<ContractDetail> updateList = new ArrayList<>();
        for (ContractDetailModel detailModel : model.getDetailModels()) {
            if (detailModel.getId() != null) {
                if (!detailMap.containsKey(detailModel.getId())) {
                    throw new OwnerException("数据异常，请联系管理员");
                }
                ContractDetail detail = detailMap.get(detailModel.getId());
                BeanUtils.copyProperties(detailModel, detail);
                detail.update(userInfo.getUserId(), userInfo.getMsUserId(), date);
                updateList.add(detail);
            } else {
                ContractDetail detail = new ContractDetail();
                BeanUtils.copyProperties(detailModel, detail);
                detail.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
                detail.setCompanyId(userInfo.getCompanyId());
                insertList.add(detail);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (model.getId() != null) {
                contractDao.updateById(contract);
            } else {
                contractDao.insert(contract);
            }

            if (!CollectionUtils.isEmpty(delIds)) {
                List<ContractDetail> contractList = contractDetailDao.getRelatedContract(delIds, null);
                if (!CollectionUtils.isEmpty(contractList)) {
                    throw new OwnerException(MessageFormat.format("设备名称：{0}，规格型号：{1}，已关联投资清单，不能删除！",
                            contractList.get(0).getEname(), contractList.get(0).getEmodal()));
                }
                contractDetailDao.deleteBatchIds(delIds);
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                contractDetailDao.updateList(updateList);
            }
            if (!CollectionUtils.isEmpty(insertList)) {
                for (ContractDetail contractInfo : insertList) {
                    contractInfo.setContractId(contract.getId());
                }
                contractDetailDao.insertList(insertList);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (OwnerException o) {
            TransactionUtils.rollback(transactionStatus);
            throw o;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存数据失败");
        }
        return true;
    }

    @Override
    public InvoiceInfoModel getInvoiceInfo(Integer companyId, Integer invoiceId) throws OwnerException {
        Invoice invoice = invoiceDao.selectById(invoiceId);
        if (invoice == null) {
            throw new OwnerException("发票已删除或不存在！");
        }
        InvoiceInfoModel model = new InvoiceInfoModel();
        BeanUtils.copyProperties(invoice, model);
        List<InvoiceDetailModel> detailModels = invoiceDetailDao.getByInvoiceId(invoice.getId(), companyId);
        if (!CollectionUtils.isEmpty(detailModels)) {
            model.setModels(detailModels);
        }

        return model;
    }

    @Override
    public ContractModel getContractInfo(Integer contractId, Integer companyId) throws OwnerException {
        Contract contract = contractDao.selectById(contractId);
        if (contract == null) {
            throw new OwnerException("数据已删除或不存在！");
        }
        ContractModel model = new ContractModel();
        BeanUtils.copyProperties(contract, model);
        List<ContractDetailModel> models = contractDetailDao.getByContractId(contract.getId());
        if (!CollectionUtils.isEmpty(models)) {
            model.setDetailModels(models);
        }
        return model;
    }

    @Override
    public List<BankReceiptModel> getBankReceipts(Integer investmentId, Integer companyId) {
        List<BankReceiptModel> models = bankReceiptDao.getByInvestmentId(investmentId);
        return models;
    }

    @Override
    public List<PaymentModel> getPayments(Integer investmentId, Integer companyId) {
        return paymentDao.getByInvestmentId(investmentId);
    }

    @Override
    public PageModel<List<BankReceiptModel>> getReceiptInfo(QueryBankReceiptModel query, Integer companyId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("tb.voucherNo");
            page.setDescs(descs);
        }
        return PageUtils.build(page, bankReceiptDao.getReceiptInfo(page, query, companyId));
    }

    @Override
    public PageModel<List<PaymentModel>> getPaymentInfo(QueryBankReceiptModel query, Integer companyId) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("voucherNo");
            page.setDescs(descs);
        }
        return PageUtils.build(page, paymentDao.getReceiptInfo(page, query, companyId));
    }

    @Override
    public Boolean saveBankReceipt(BankReceiptModel model, UserInfo userInfo) {
        if (model == null) {
            return true;
        }
        Date date = new Date();
        if (model.getId() != null) {
            BankReceipt bankReceipt = bankReceiptDao.selectById(model.getId());
            BeanUtils.copyProperties(model, bankReceipt);
            bankReceipt.update(userInfo.getUserId(), userInfo.getMsUserId(), date);
            return bankReceiptDao.updateById(bankReceipt) > 0;
        } else {
            BankReceipt bankReceipt = new BankReceipt();
            BeanUtils.copyProperties(model, bankReceipt);
            bankReceipt.create(userInfo.getUserId(), userInfo.getMsUserId(), date);
            bankReceipt.setCompanyId(userInfo.getCompanyId());
            return bankReceiptDao.insert(bankReceipt) > 0;
        }
    }

    @Override
    public Boolean savePayment(PaymentModel model, UserInfo userInfo) {
        if (model == null) {
            return true;
        }
        Date date = new Date();
        model.setAmount(model.getAmountTax().divide(model.getTaxRate().add(BigDecimal.valueOf(1)),2,BigDecimal.ROUND_HALF_UP));
        if (model.getId() != null) {
            PaymentEntity entity = new PaymentEntity();
            BeanUtils.copyProperties(model, entity);
            ToolUtils.entityUpdate(entity,userInfo.getUserId(), userInfo.getMsUserId(), date);
            return paymentDao.updateById(entity)>0;
        } else {
            PaymentEntity entity = new PaymentEntity();
            BeanUtils.copyProperties(model, entity);
            ToolUtils.entityCreate(entity,userInfo.getUserId(), userInfo.getMsUserId(), date);
            entity.setCompanyId(userInfo.getCompanyId());
            return paymentDao.insert(entity) > 0;
        }
    }

    @Override
    public Boolean delBankReceipt(List<ContractInfoModel> models, UserInfo userInfo) {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<Integer> delIds = models.stream().map(e -> e.getId()).collect(Collectors.toList());
        Integer investmentId = models.get(0).getInvestmentId();
        return bankReceiptDao.delRelated(delIds, investmentId);
    }

    @Override
    public Boolean delPayment(List<ContractInfoModel> models) {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<Integer> delIds = models.stream().map(e -> e.getId()).collect(Collectors.toList());
        Integer investmentId = models.get(0).getInvestmentId();
        return paymentDao.delRelated(delIds, investmentId);
    }

    @Override
    public Boolean delInvoiceInfo(List<InvoiceDetailModel> models) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<Integer> detailIds = models.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<InvoiceDetail> details = invoiceDetailDao.getRalatedInvocie(detailIds, null);
        if (!CollectionUtils.isEmpty(details)) {
            throw new OwnerException("存在已关联投资清单的发票数据，不能删除！");
        }
        List<InvoiceDetail> detailList = invoiceDetailDao.selectBatchIds(detailIds);
        List<Integer> invoiceIds = detailList.stream().map(e -> e.getInvoiceId()).distinct().collect(Collectors.toList());
        List<InvoiceDetail> exists = invoiceDetailDao.getOtherDetail(invoiceIds, detailIds);
        List<Integer> delInvoices = new ArrayList<>();
        if (CollectionUtils.isEmpty(exists)) {
            delInvoices = invoiceIds;
        } else {
            Map<Integer, Boolean> dataMap = new HashMap<>();
            for (InvoiceDetail detail : exists) {
                if (!dataMap.containsKey(detail.getInvoiceId())) {
                    dataMap.put(detail.getInvoiceId(), true);
                }
            }
            for (InvoiceDetail detail : detailList) {
                if (!dataMap.containsKey(detail.getInvoiceId())) {
                    delInvoices.add(detail.getInvoiceId());
                }
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            invoiceDetailDao.deleteBatchIds(detailIds);
            if (!CollectionUtils.isEmpty(delInvoices)) {
                invoiceDao.deleteBatchIds(delInvoices);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("删除数据失败");
        }
        return true;
    }

    @Override
    public Boolean delContractInfo(List<ContractDetailModel> models) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<Integer> detailIds = models.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<ContractDetail> contractDetails = contractDetailDao.getRelatedContract(detailIds, null);
        if (!CollectionUtils.isEmpty(contractDetails)) {
            throw new OwnerException("存在已关联投资清单的合同数据，不能删除！");
        }
        List<ContractDetail> details = contractDetailDao.selectBatchIds(detailIds);
        List<Integer> contractIds = details.stream().map(e -> e.getContractId()).distinct().collect(Collectors.toList());

        List<ContractDetail> detailList = contractDetailDao.getOtherContract(contractIds, detailIds);
        List<Integer> delContractIds = new ArrayList<>();
        if (CollectionUtils.isEmpty(detailList)) {
            delContractIds = details.stream().map(e -> e.getContractId()).distinct().collect(Collectors.toList());
        } else {
            Map<Integer, Boolean> existContractMap = new HashMap<>();
            for (ContractDetail contractDetail : detailList) {
                if (!existContractMap.containsKey(contractDetail.getContractId())) {
                    existContractMap.put(contractDetail.getContractId(), true);
                }
            }
            for (ContractDetail detail : details) {
                if (!existContractMap.get(detail.getContractId())) {
                    delContractIds.add(detail.getContractId());
                }
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {

            contractDetailDao.deleteBatchIds(detailIds);
            if (!CollectionUtils.isEmpty(delContractIds)) {
                contractDao.deleteBatchIds(delContractIds);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("删除数据失败");
        }
        return true;
    }

    @Override
    public Boolean delBankInfo(List<BankReceiptModel> models) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<Integer> bankIds = models.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<BankReceipt> receipts = bankReceiptDao.getRelatedReceipt(bankIds, null);
        if (!CollectionUtils.isEmpty(receipts)) {
            throw new OwnerException("存在已关联投资清单的银行水单，不能删除！");
        }
        return bankReceiptDao.deleteBatchIds(bankIds) > 0;
    }

    @Override
    public Boolean delPaymentInfo(List<Integer> ids) throws OwnerException {
        if (CollectionUtils.isEmpty(ids)) {
            return true;
        }
        List<Integer> receipts = paymentDao.getRelated(ids, null);
        if (!CollectionUtils.isEmpty(receipts)) {
            throw new OwnerException("存在已关联投资清单的付款单，不能删除！");
        }
        return paymentDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean verifyInvoice(Integer companyId, String invoiceNo) {
        Invoice invoice = invoiceDao.verifyInvoice(companyId, invoiceNo);
        if (invoice != null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean verifyContract(Integer companyId, String contractNo) {
        Contract contract = contractDao.verifyContract(companyId, contractNo);
        if (contract != null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean delInvestment(List<InvestmentModel> models) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<Integer> investmentIds = models.stream().map(e -> e.getId()).collect(Collectors.toList());
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (!CollectionUtils.isEmpty(investmentIds)) {
                investmentDao.deleteBatchIds(investmentIds);
                invoiceDetailDao.updateByInvestmentIds(investmentIds);
                contractDetailDao.updateByInvestmentIds(investmentIds);
                paymentDao.updateByInvestmentIds(investmentIds);
                nameplateDao.delByInvestmentId(investmentIds);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("删除数据失败");
        }
        return true;
    }

    @Override
    public List<String> getCompanyName(String companyName, Integer companyId) {
       /* List<String> result=new ArrayList<>();
        List<String> datas=null;
        List<String> invoiceForms=investmentDao.getInvoiceFroms(companyName,beianId);
        if(!CollectionUtils.isEmpty(invoiceForms)){
            result.addAll(invoiceForms);
        }
        List<String> signTargets=investmentDao.getSignTargets(companyName,beianId);
        if(!CollectionUtils.isEmpty(signTargets)){
            result.addAll(signTargets);
        }
        List<String> payees=investmentDao.getPayees(companyName,beianId);
        if(!CollectionUtils.isEmpty(payees)){
            result.addAll(payees);
        }
        if(!CollectionUtils.isEmpty(result)){
            datas= result.stream().distinct().collect(Collectors.toList());
            if(datas.size()>10){
               return datas.subList(0,10);
            }
        }*/
        /*return  CollectionUtils.isEmpty(datas)?Arrays.asList(companyName):datas;*/
        List<String> companyNames = investmentDao.getCompanyName(companyName, companyId);
        return CollectionUtils.isEmpty(companyNames) ? Arrays.asList(companyName) : companyNames;
    }

    @Override
    public List<String> getDeviceName(String deviceName, Integer companyId) {
        List<String> deviceNames = investmentDao.getDeviceName(deviceName, companyId);
        return CollectionUtils.isEmpty(deviceNames)?Arrays.asList(deviceName):deviceNames;
    }


    @Override
    public Boolean saveInvestment(AddInvestmentModel model, UserInfo userInfo) throws OwnerException {
        if (model == null) {
            return true;
        }
        Date date = new Date();
        Integer companyId = userInfo.getCompanyId();
        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();
        Investment investment;
        if (model.getId() == null) {
            investment = new Investment();
            BeanUtils.copyProperties(model, investment);
            investment.create(userId, msUserId, date);
            investment.setCompanyId(companyId);
        } else {
            investment = investmentDao.selectById(model.getId());
            BeanUtils.copyProperties(model, investment);
            investment.update(userId, msUserId, date);
        }
        if (!CollectionUtils.isEmpty(model.getInvoiceIds())) {
            List<InvoiceDetail> details = invoiceDetailDao.getRalatedInvocie(model.getInvoiceIds(), model.getId());
            if (!CollectionUtils.isEmpty(details)) {
                throw new OwnerException("存在已关联投资清单的发票数据，保存失败！");
            }
        }
        if (!CollectionUtils.isEmpty(model.getContractIds())) {
            List<ContractDetail> details = contractDetailDao.getRelatedContract(model.getContractIds(), model.getId());
            if (!CollectionUtils.isEmpty(details)) {
                throw new OwnerException("存在已关联投资清单的合同数据，保存失败！");
            }
        }
        if (!CollectionUtils.isEmpty(model.getBankReceiptIds())) {
            List<Integer> paymentIds = paymentDao.getRelated(model.getBankReceiptIds(),model.getId());
            if (!CollectionUtils.isEmpty(paymentIds)) {
                throw new OwnerException("存在已关联投资清单的付款单数据，保存失败！");
            }
        }
        List<Nameplate> insertNameplates = new ArrayList<>();

        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (model.getId() == null) {
                investmentDao.insert(investment);
            } else {
                investmentDao.updateById(investment);
            }
            List<Integer> invoices = invoiceDetailDao.getByInvestment(investment.getId());
            List<Integer> contracts = contractDetailDao.getByInvestment(investment.getId());
            List<Integer> paymentIds = paymentDao.getByInvestment(investment.getId());
            //删除发票
            if (!CollectionUtils.isEmpty(invoices)) {
                if (!CollectionUtils.isEmpty(model.getInvoiceIds())) {
                    invoices.removeAll(model.getInvoiceIds());
                }
                if (!CollectionUtils.isEmpty(invoices)) {
                    invoiceDetailDao.updateByInvestmentIds(invoices);
                }
            }
            if (!CollectionUtils.isEmpty(model.getInvoiceIds())) {
                invoiceDetailDao.updateByInvestment(model.getInvoiceIds(), investment.getId());
            }
            //删除合同
            if (!CollectionUtils.isEmpty(contracts)) {
                if (!CollectionUtils.isEmpty(model.getContractIds())) {
                    contracts.removeAll(model.getContractIds());
                }
                if (!CollectionUtils.isEmpty(contracts)) {
                    contractDetailDao.updateByInvestmentIds(contracts);
                }
            }
            if (!CollectionUtils.isEmpty(model.getContractIds())) {
                contractDetailDao.updateByInvestment(model.getContractIds(), investment.getId());
            }
            //删除银行水单
            if (!CollectionUtils.isEmpty(paymentIds)) {
                if (!CollectionUtils.isEmpty(model.getBankReceiptIds())) {
                    paymentIds.removeAll(model.getBankReceiptIds());
                }
                if (!CollectionUtils.isEmpty(paymentIds)) {
                    paymentDao.updateByInvestmentIds(paymentIds);
                }
            }
            if (!CollectionUtils.isEmpty(model.getBankReceiptIds())) {
                paymentDao.updateList(model.getBankReceiptIds(), investment.getId());
            }
            if (!CollectionUtils.isEmpty(model.getNameplateModels())) {
                for (NameplateModel nameplateModel : model.getNameplateModels()) {
                    Nameplate nameplate = new Nameplate();
                    BeanUtils.copyProperties(nameplateModel, nameplate);
                    nameplate.create(userId, msUserId, date);
                    nameplate.setCompanyId(companyId);
                    nameplate.setInvestmentId(investment.getId());
                    insertNameplates.add(nameplate);
                }
            }
            nameplateDao.delByInvestmentId(Arrays.asList(investment.getId()));
            if (!CollectionUtils.isEmpty(insertNameplates)) {
                nameplateDao.insertList(insertNameplates);
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException("保存数据失败");
        }
        return true;
    }

}
