/*
 * @Author: ldx
 * @Date: 2021-06-21 08:46:36
 * @LastEditTime: 2021-11-22 10:33:32
 * @LastEditors: zdf
 * @Description: 多层级研发管理
 * @FilePath: \RS-VUE\src\api\project\RDLevelManange\index.js
 */

import { getData } from '@/api/utils/index'
import { axios } from '@/utils/request'

/**
 * @description: 获取多层级研发管理菜单列表
 * @param {*} params
 * @return {*}
 */
export function getRdManagerMenu (params) {
  return getData(axios({
    url: '/docFileAttachment/getRdManagerMenu',
    method: 'get',
    params
  }))
}
/**
 * @description: 获取多级研发管理列表
 * @param {*} params
 * @return {*}
 */
export function getList (params) {
  return getData(axios({
    url: '/docFileAttachment/getList',
    method: 'get',
    params
  }))
}

// export function delUploadFile (params) {
//   return getData(axios({
//     url: '/docFileAttachment/delUploadFile',
//     method: 'post',
//     data: params
//   }))
// }
