/*
 * @Author: ldx
 * @Date: 2021-03-19 16:14:16
 * @LastEditTime: 2021-03-30 15:38:32
 * @LastEditors: ldx
 * @Description: 投资清单
 * @FilePath: \RS-VUE\src\api\tech\BeiAnGuanLi\Invest\index.js
 */
import { axios } from '@/utils/request'
const PREFIX_URL = '/inverstment'

const GET_LIST = '/getList'
const GET_INVOICES = '/getInvoices'
const SAVE_INVOICE = '/saveInvoice'
const SAVE_CONTRACT = '/saveContract'
const GET_INVOICE_DETAILS = '/getInvoiceDetails'
const DEL_INVOICE_DETAIL = '/delInvoiceDetail'
const GET_INVOICE_INFO = '/getInvoiceInfo'
const GET_CONTRACTS = '/getContracts'
const GET_CONTRACT_DETAIL = '/getContractDetail'
const GET_CONTRACT_INFO = '/getContractInfo'
const DEL_CONTRACT_DETAIL = '/delContractDetail'
const GET_PAYMENTS = '/getPayments'
const GET_RECEIPT_INFO = '/getReceiptInfo'
const SAVE_PAYMENT = '/savePayment'
const DEL_BANK_RECEIPT = '/delBankReceipt'
const SAVE_INVESTMENT = '/saveInvestment'
const VERIFY_INVOICE = '/verifyInvoice'
const VERIFY_CONTRACT = '/verifyContract'
const DEL_INVOICE_INFO = '/delInvoiceInfo'
const DEL_CONTRACT_INFO = '/delContractInfo'
const DEL_PAYMENT_INFO = '/delPaymentInfo'
const DEL_INVESTMENT = '/delInvestment'
/**
 * @description:  获取投资清单列表
 * @param {*} parameter
 * @return {*}
 */
export function getList (parameter) {
  return axios({
    url: PREFIX_URL + GET_LIST,
    method: 'get',
    params: parameter
  })
}
export function delInvestment (parameter) {
  return axios({
    url: PREFIX_URL + DEL_INVESTMENT,
    method: 'post',
    data: parameter
  })
}

/**
 * @description: 保存发票信息
 * @param {*} parameter
 * @return {*}
 */
export function saveInvoice (parameter) {
  return axios({
    url: PREFIX_URL + SAVE_INVOICE,
    method: 'post',
    data: parameter
  })
}
/**
 * @description:  保存合同信息
 * @param {*} parameter
 * @return {*}
 */
export function saveContract (parameter) {
  return axios({
    url: PREFIX_URL + SAVE_CONTRACT,
    method: 'post',
    data: parameter
  })
}
/**
 * @description: 删除投资清单关联的发票设备
 * @param {*} parameter
 * @return {*}
 */
export function delInvoiceDetail (parameter) {
  return axios({
    url: PREFIX_URL + DEL_INVOICE_DETAIL,
    method: 'post',
    data: parameter
  })
}
/**
 * @description: 获取发票列表
 * @param {*} parameter
 * @return {*}
 */
export function getInvoiceDetails (parameter) {
  return axios({
    url: PREFIX_URL + GET_INVOICE_DETAILS,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 获取发票信息
 * @param {*} parameter
 * @return {*}
 */
export function getInvoiceInfo (parameter) {
  return axios({
    url: PREFIX_URL + GET_INVOICE_INFO,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 获取设备发票列表
 * @param {*} parameter
 * @return {*}
 */
export function getInvoices (parameter) {
  return axios({
    url: PREFIX_URL + GET_INVOICES,
    method: 'get',
    params: parameter
  })
}

/**
 * @description: 获取合同设备信息列表
 * @param {*} parameter
 * @return {*}
 */
export function getContracts (parameter) {
  return axios({
    url: PREFIX_URL + GET_CONTRACTS,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 获取合同信息列表
 * @param {*} parameter
 * @return {*}
 */
export function getContractDetail (parameter) {
  return axios({
    url: PREFIX_URL + GET_CONTRACT_DETAIL,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 获取合同信息
 * @param {*} parameter
 * @return {*}
 */
export function getContractInfo (parameter) {
  return axios({
    url: PREFIX_URL + GET_CONTRACT_INFO,
    method: 'get',
    params: parameter
  })
}

/**
 * @description: 删除合同设备
 * @param {*} parameter
 * @return {*}
 */
export function delContractDetail (parameter) {
  return axios({
    url: PREFIX_URL + DEL_CONTRACT_DETAIL,
    method: 'post',
    data: parameter
  })
}

/**
 * @description: 获取投资清单的投资付款单
 * @param {*} parameter
 * @return {*}
 */
export function getPayments (parameter) {
  return axios({
    url: PREFIX_URL + GET_PAYMENTS,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 获取可添加的银行水单列表
 * @param {*} parameter
 * @return {*}
 */
export function getReceiptInfo (parameter) {
  return axios({
    url: PREFIX_URL + GET_RECEIPT_INFO,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 保存银行水单
 * @param {*} parameter
 * @return {*}
 */
export function savePayment (parameter) {
  return axios({
    url: PREFIX_URL + SAVE_PAYMENT,
    method: 'post',
    data: parameter
  })
}
/**
 * @description: 删除投资清单关联银行水单
 * @param {*} parameter
 * @return {*}
 */
export function delBankReceipt (parameter) {
  return axios({
    url: PREFIX_URL + DEL_BANK_RECEIPT,
    method: 'post',
    data: parameter
  })
}
/**
 * @description: 保存投资清单
 * @param {*} parameter
 * @return {*}
 */
export function saveInvestment (parameter) {
  return axios({
    url: PREFIX_URL + SAVE_INVESTMENT,
    method: 'post',
    data: parameter
  })
}

/**
 * @description: 验证发票号唯一
 * @param {*} parameter
 * @return {*}
 */
export function verifyInvoice (parameter) {
  return axios({
    url: PREFIX_URL + VERIFY_INVOICE,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 验证合同号唯一
 * @param {*} parameter
 * @return {*}
 */
export function verifyContract (parameter) {
  return axios({
    url: PREFIX_URL + VERIFY_CONTRACT,
    method: 'get',
    params: parameter
  })
}
/**
 * @description:删除发票详情
 * @param {*} parameter
 * @return {*}
 */
export function delInvoiceInfo (parameter) {
  return axios({
    url: PREFIX_URL + DEL_INVOICE_INFO,
    method: 'post',
    data: parameter
  })
}
/**
 * @description: 删除合同详情
 * @param {*} parameter
 * @return {*}
 */
export function delContractInfo (parameter) {
  return axios({
    url: PREFIX_URL + DEL_CONTRACT_INFO,
    method: 'post',
    data: parameter
  })
}
/**
 * @description: 删除银行水单
 * @param {*} parameter
 * @return {*}
 */
export function delPaymentInfo (parameter) {
  return axios({
    url: PREFIX_URL + DEL_PAYMENT_INFO,
    method: 'post',
    data: parameter
  })
}
