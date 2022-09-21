/*
 * @Author: ldx
 * @Date: 2021-07-28 09:57:14
 * @LastEditTime: 2021-07-28 10:12:22
 * @LastEditors: ldx
 * @Description: 申请列表
 * @FilePath: \MS-VUE\src\api\patent\applyList.js
 */

import { axios } from '@/utils/request'
import { getData } from '@/api/utils/getData'

/**
 * @description: 设置定稿名称
 * @param {*} params
 * @return {*}
 */
export function setName (params) {
  return getData(axios.post('/rsPatentPlan/setName', params))
}
