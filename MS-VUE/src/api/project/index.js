/*
 * @Author: ldx
 * @Date: 2021-06-23 09:47:16
 * @LastEditTime: 2021-06-23 09:50:36
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\api\project\index.js
 */
import { axios } from '@/utils/request'
import { getData } from '../utils/getData'

export function getProjectInfo (params) {
  return getData(axios({
    url: '/projectProgress/getProjectInfo',
    method: 'get',
    params
  }))
}
