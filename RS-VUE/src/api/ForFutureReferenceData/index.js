/*
 * @Author: ldx
 * @Date: 2021-03-11 10:39:55
 * @LastEditTime: 2021-08-10 08:40:01
 * @LastEditors: ldx
 * @Description: 留存备查资料接口
 * @FilePath: \RS-VUE\src\api\ForFutureReferenceData\index.js
 */
import { axios } from '@/utils/request'
import { getData as handleGetData } from '@/api/utils/index'

/**
 * @description: 获取项目查新报告列表
 * @param {*} paramter
 * @return {*}
 */
export function getData (paramter) {
  return axios({
    url: '/projectDocFileData/getNewReports',
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
    url: '/summary/getProjectSummary',
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
    url: '/projectDocFileData/getAuditStatus',
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
    url: '/projectDocFileData/exportBackupData',
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 预览获取项目组编制data
 * @param {*} parameter
 * @return {*}
 */
export function previewReport (parameter) {
  return axios({
    url: '/projectDocFileData/previewReport',
    method: 'get',
    params: parameter
  })
}

/**
 * @description: 切换过程文档模板
 * @param {*} params
 * @return {*}
 */
export function setDocTemplate (params) {
  return handleGetData(axios({
    url: '/projectDocFileData/setDocTemplate',
    method: 'post',
    data: params
  }))
}
