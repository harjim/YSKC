/*
 * @Author: ldx
 * @Date: 2021-07-16 11:46:44
 * @LastEditTime: 2021-07-17 11:50:39
 * @LastEditors: ldx
 * @Description: 概况接口
 * @FilePath: \RS-VUE\src\api\generalSituation\index.js
 */
import { axios } from '@/utils/request'
import { getData } from '@/api/utils'
/**
 * @description:获取公司首页统计数据
 * @param {*} params {year}
 * @return {*}
 */
export function getCountData (params) {
  return getData(axios.get('/company/getCountData', { params }))
}
