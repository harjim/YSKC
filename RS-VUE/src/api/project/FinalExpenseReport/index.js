/*
 * @Author: ldx
 * @Date: 2021-03-11 09:01:12
 * @LastEditTime: 2021-06-04 14:09:22
 * @LastEditors: ldx
 * @Description: 费用决算报告接口
 * @FilePath: \RS-VUE\src\api\project\FinalExpenseReport\index.js
 */
import { axios } from '@/utils/request'
const PREFIX_URL = '/projectDocFileData'

const GET_DOC_INFO = '/getDocInfo'
const PREVIEW_FILE = '/previewFile'
/**
 * @description:  获取过程文档信息
 * @param {*} parameter Integer projectId,Integer docFileId(立项报告27，立项决议3，RD人员设备22，项目总结报告33，决算报告34)
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
    url: PREFIX_URL + PREVIEW_FILE,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 获取备查资料列表
 * @param {*} params
 * @return {*}
 */
// export function getBackupData (params) {
//   return axios({
//     url: PREFIX_URL + '/getBackupData',
//     method: 'get',
//     params
//   })
// }
/**
 * @description: 获取备查资料列表
 * @param {*} params
 * @return {*}
 */
export function getBackupData (params) {
  return axios({
    url: PREFIX_URL + '/getBackupData',
    method: 'get',
    params
  }).then(response => {
    if (response.success) {
      return Promise.resolve(response.data)
    } else {
      throw new Error(response.errorMessage || '系统请求接口异常，请联系管理员！')
    }
  })
}
