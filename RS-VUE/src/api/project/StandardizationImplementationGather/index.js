/*
 * @Author: ldx
 * @Date: 2021-02-26 14:18:51
 * @LastEditTime: 2021-02-26 14:27:17
 * @LastEditors: ldx
 * @Description: 年度研发项目标准化实施情况汇总表
 * @FilePath: \RS-VUE\src\api\project\StandardizationImplementationGather\index.js
 */
import { axios } from '@/utils/request'
const prefixUrl = '/situationSummary'
/**
 * @description: 查询政策及要求情况汇接口
 * @param {*}
 * @return {*}
 */
const queryPolicyUrl = '/queryStandard'

/**
 * @description: 保存标准化实施情况
 * @param {*}
 * @return {*}
 */
const saveStandardUrl = '/saveStandard'

/**
 * @description: 查询政策及要求情况汇
 * @param {*} parameter {year}
 * @return {*}
 */
export function queryStandard (parameter) {
  return axios({
    url: prefixUrl + queryPolicyUrl,
    method: 'get',
    params: parameter
  })
}

/**
 * @description: 保存标准化实施情况
 * @param {*} parameter
 * @return {*}
 */
export function saveStandard (parameter) {
  return axios({
    url: prefixUrl + saveStandardUrl,
    method: 'post',
    data: parameter
  })
}
