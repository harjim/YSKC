/*
 * @Author: ldx
 * @Date: 2021-07-01 11:03:51
 * @LastEditTime: 2021-07-01 11:06:58
 * @LastEditors: ldx
 * @Description: 预算
 * @FilePath: \RS-VUE\src\api\project\budget\index.js
 */

import { axios } from '@/utils/request'
import { getData } from '@/api/utils/index'

export function setBudget (params) {
  return getData(axios({
    url: '/project/setBudget',
    method: 'post',
    data: params
  }))
}
