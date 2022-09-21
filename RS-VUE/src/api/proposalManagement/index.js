/*
 * @Author: ldx
 * @Date: 2021-07-16 08:42:15
 * @LastEditTime: 2021-09-04 11:56:57
 * @LastEditors: lzh
 * @Description: 提案管理
 * @FilePath: \RS-VUE\src\api\proposalManagement\index.js
 */
import { getData } from '@/api/utils'
import { axios } from '@/utils/request'

/**
 * @description: 保存提案
 * @param {*} params
 * @return {*}
 */
export function saveProposal (params) {
  return getData(axios.post('/proposalManagement/saveProposal', params))
}

/**
 * @description: 删除提案
 * @param {*} params
 * @return {*}
 */
export function delProposal (params) {
  return getData(axios.post('/proposalManagement/delProposal', params))
}

/**
 * @description: 提交提案
 * @param {*} params
 * @return {*}
 */
export function auditProposal (params) {
  return getData(axios.post('/projectAudit/submitProposal', params))
}
