package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.*;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/19 9:11
 * @Description:
 */
public interface InvestmentService {
    /**
     * 获取投资清单
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<InvestmentModel>> getList(Integer companyId, QueryInverstmentModel query);

    /**
     * 获取发票清单
     * @param companyId
     * @param query
     * @return
     */
    List<InvoiceModel> getInvoices(Integer companyId, QueryInvoiceModel query);

    /**
     * 删除关联清单的发票设备,如果发票下不存在设备则删除发票
     * @param models
     * @param companyId
     * @return
     */
    Boolean delInvoiceDetail(List<InvoiceModel> models,Integer companyId) throws OwnerException;

    /**
     * 获取发票设备详情列表
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<InvoiceModel>> getInvoiceDetails(Integer companyId, QueryInvoiceModel query);

    /**
     * 关联发票设备到投资列表
     * @param models
     * @param userInfo
     * @return
     */
    //Boolean relatedInvoice(List<InvoiceModel> models, UserInfo userInfo);

    /**
     * 保存发票信息
     * @param model
     * @param userInfo
     * @return
     */
    Boolean saveInvoice(InvoiceInfoModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 获取合同信息列表
     * @param companyId
     * @param query
     * @return
     */
    List<ContractInfoModel> getContracts(Integer companyId, QueryContractModel query);

    /**
     * 删除合同信息
     * @param models
     * @return
     * @throws OwnerException
     */
    Boolean delContractDetail(List<ContractInfoModel> models) throws OwnerException;

    /**
     * 获取可关联的合同列表
     * @param companyId
     * @param query
     * @return
     */
    PageModel<List<ContractInfoModel>> getContractDetail(Integer companyId, QueryContractModel query);

    /**
     *  创建关联
     * @param models
     * @param userInfo
     * @return
     */
    //Boolean relatedContract(List<ContractInfoModel> models, UserInfo userInfo);

    /**
     * 保存合同信息
     * @param model
     * @param userInfo
     * @return
     */
    Boolean saveContract(ContractModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 根据发票id获取发票信息
     * @param companyId
     * @param invoiceId
     * @return
     */
    InvoiceInfoModel getInvoiceInfo(Integer companyId, Integer invoiceId) throws OwnerException;

    /**
     * 获取合同信息
     * @param contractId
     * @param companyId
     * @return
     */
    ContractModel getContractInfo(Integer contractId, Integer companyId) throws OwnerException;

    /**
     * 获取投资清单（原银行水单）
     * @param investmentId
     * @param companyId
     * @return
     */
    List<BankReceiptModel> getBankReceipts(Integer investmentId, Integer companyId);

    /**
     * 获取投资清单 投资清单付款（原银行水单）
     * @param investmentId
     * @param companyId
     * @return
     */
    List<PaymentModel> getPayments(Integer investmentId, Integer companyId);

    /**
     * 获取可添加的（原银行水单）列表
     * @param query
     * @param companyId
     * @return
     */
    PageModel<List<BankReceiptModel>> getReceiptInfo(QueryBankReceiptModel query, Integer companyId);

    /**
     * 获取可添加的 投资清单付款（原银行水单）列表
     * @param query
     * @param companyId
     * @return
     */
    PageModel<List<PaymentModel>> getPaymentInfo(QueryBankReceiptModel query, Integer companyId);

    /**
     * 保存（原银行水单）信息
     * @param model
     * @param userInfo
     * @return
     */
    Boolean saveBankReceipt(BankReceiptModel model, UserInfo userInfo);

    /**
     * 保存 投资清单付款（原银行水单）信息
     * @param model
     * @param userInfo
     * @return
     */
    Boolean savePayment(PaymentModel model,UserInfo userInfo);

    /**
     * 删除投资清单关联（原银行水单）
     * @param models
     * @param userInfo
     * @return
     */
    Boolean delBankReceipt(List<ContractInfoModel> models, UserInfo userInfo);

    /**
     * 删除投资清单关联 投资清单付款（原银行水单）
     * @param models
     * @return
     */
    Boolean delPayment(List<ContractInfoModel> models);

    /**
     * 保存投资清单
     * @param model
     * @param userInfo
     * @return
     */
    Boolean saveInvestment(AddInvestmentModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 删除发票设备
     * @param models
     * @return
     */
    Boolean delInvoiceInfo(List<InvoiceDetailModel> models) throws OwnerException;

    /**
     * 删除合同详情
     * @param models
     * @return
     */
    Boolean delContractInfo(List<ContractDetailModel> models) throws OwnerException;

    /**
     * 删除（原银行水单）
     * @param models
     * @return
     */
    Boolean delBankInfo(List<BankReceiptModel> models) throws OwnerException;

    /**
     *  删除 投资清单付款（原银行水单）
     * @param ids
     * @return
     * @throws OwnerException
     */
    Boolean delPaymentInfo(List<Integer> ids) throws OwnerException;

    /**
     * 验证发票号唯一
     * @param companyId
     * @param invoiceNo
     * @return
     */
    Boolean verifyInvoice(Integer companyId, String invoiceNo);

    /**
     * 验证合同号唯一
     * @param companyId
     * @param contractNo
     * @return
     */
    Boolean verifyContract(Integer companyId, String contractNo);

    /**
     * 删除投资清单
     * @param models
     * @return
     */
    Boolean delInvestment(List<InvestmentModel> models) throws OwnerException;

    /**
     * 模糊查询发票开具方、合同签订对象、付款单 收款单位
     * @param companyName
     * @param beianId
     * @return
     */
    List<String> getCompanyName(String companyName, Integer beianId);

    /**
     * 查询公司设备名称
     * @param deviceName
     * @param companyId
     * @return
     */
    List<String> getDeviceName(String deviceName, Integer companyId);
}
