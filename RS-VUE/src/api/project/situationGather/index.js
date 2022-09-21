/*
 * @Author: ldx
 * @Date: 2021-02-25 15:23:47
 * @LastEditTime: 2021-02-25 16:15:57
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\api\situationGather\index.js
 */
import { axios } from '@/utils/request'
const prefixUrl = '/situationSummary'
/**
 * @description: 查询项目情况总结地址
 * @param {*}
 * @return {*}
 */
const querySituationUrl = '/querySituation'
/**
 * @description: 保存项目情况总结
 * @param {*}
 * @return {*}
 */
const saveSituationUrl = '/saveSituation'
/**
 * @description:  查询项目情况总结数据
 * @param {*} { year }
 * @return {*} SituationSummaryModel
 */
export function querySituation (parameter) {
  return axios({
    url: prefixUrl + querySituationUrl,
    method: 'get',
    params: parameter
  })
}
/**
 * @description:  保存项目情况总结数据
 * @param {*} {}
 * @return {*} boolean
 */
export function saveSituation (parameter) {
  return axios({
    url: prefixUrl + saveSituationUrl,
    method: 'post',
    data: parameter
  })
}
