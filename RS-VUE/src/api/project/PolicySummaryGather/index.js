/*
 * @Author: ldx
 * @Date: 2021-02-27 09:01:36
 * @LastEditTime: 2021-02-27 09:27:09
 * @LastEditors: ldx
 * @Description:年度研发项目标准化实施情况汇总表
 * @FilePath: \RS-VUE\src\api\project\PolicySummaryGather\index.js
 */
import { axios } from '@/utils/request'
const PREFIX_URL = '/situationSummary'
/**
 * @description: 查询政策及要求情况汇总
 * @param {*}
 * @return {*}
 */
const QUERY_POLICY_URL = '/queryPolicy'
/**
 * @description: 保存政策及要求情况
 * @param {*}
 * @return {*}
 */
const SAVE_POLICY_URL = '/savePolicy'
/**
 * @description: 查询政策及要求情况汇总
 * @param {*} parameter null
 * @return {*}
 */
export function queryPolicy (parameter) {
  return axios({
    url: PREFIX_URL + QUERY_POLICY_URL,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 保存政策及要求情况
 * @param {*} parameter
 * @return {*}
 */
export function savePolicy (parameter) {
  return axios({
    url: PREFIX_URL + SAVE_POLICY_URL,
    method: 'post',
    data: parameter
  })
}
