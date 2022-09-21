/*
 * @Author: ldx
 * @Date: 2021-05-13 14:44:42
 * @LastEditTime: 2021-06-06 21:14:17
 * @LastEditors: ldx
 * @Description: 留存备查资料
 * @FilePath: \MS-VUE\src\api\backupDataTab\index.js
 */
import { axios } from '@/utils/request'
const PREFIX_URL = '/backupData'

const GET_DOC_INFO = '/getDocInfo'
const PREVIEW_FILE = '/projectProgress/previewFile'
const GET_AUDIT_STATUS = '/getAuditStatus'
const GET_NEW_REPORTS = '/getNewReports'
const EXPORT_BACKUP_DATA = '/exportBackupData'
const GET_LIST = '/getList'
const GET_PROJECT_SUMMARY = '/getProjectSummary'
const PREVIEW_REPORT = '/previewReport'
const GET_BACKUP_DATA = '/getBackupData'
/**
 * @description:  获取过程文档信息
 * @param {*} parameter Integer projectId,Integer docFileId(立项报告27，立项决议3，RD人员设备22，项目总结报告33，决算报告34，查新报告:38)
 * @return {*}
 */
export function getDocInfo (parameter) {
  return axios({
    url: PREFIX_URL + GET_DOC_INFO,
    method: 'get',
    params: parameter
  })
}

/**
 * @description: 获取文档预览
 * @param {*} parameter  pDocFileId projectId currentYear companyId
 * @return {*}
 */
export function previewFile (parameter) {
  return axios({
    url: PREVIEW_FILE,
    method: 'get',
    params: parameter
  })
}

/**
 * @description: 获取项目查新报告列表
 * @param {*} paramter
 * @return {*}
 */
export function getData (paramter) {
  return axios({
    url: PREFIX_URL + GET_NEW_REPORTS,
    method: 'get',
    params: paramter
  })
}

/**
 * @description: 研发支出辅助帐及汇总表
 * @param {*} parameter
 * @return {*}
 */
export function getProjectSummary (parameter) {
  return axios({
    url: PREFIX_URL + GET_PROJECT_SUMMARY,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 获取备查资料审核状态
 * @param {*} parameter
 * @return {*}
 */
export function getAuditStatus (parameter) {
  return axios({
    url: PREFIX_URL + GET_AUDIT_STATUS,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 导出备查资料
 * @param {*} parameter
 * @return {*}
 */
export function exportBackupData (parameter) {
  return axios({
    url: PREFIX_URL + EXPORT_BACKUP_DATA,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 项目成员列表
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
/**
 * @description: 项目成员列表
 * @param {*} parameter
 * @return {*}
 */
export function previewReport (parameter) {
  return axios({
    url: PREFIX_URL + PREVIEW_REPORT,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 获取备查资料列表
 * @param {*} parameter
 * @return {*}
 */
export function getBackupData (parameter) {
  return axios({
    url: PREFIX_URL + GET_BACKUP_DATA,
    method: 'get',
    params: parameter
  }).then(response => {
    if (response.success) {
      return Promise.resolve(response.data)
    } else {
      throw new Error(response.errorMessage || '系统异常，请联系管理员！')
    }
  })
}
