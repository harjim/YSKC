/*
 * @Author: ldx
 * @Date: 2021-03-18 16:21:19
 * @LastEditTime: 2021-03-26 08:41:57
 * @LastEditors: ldx
 * @Description: 备案管理
 * @FilePath: \RS-VUE\src\api\tech\BeiAnGuanLi\index.js
 */
import { axios } from '@/utils/request'
const PREFIX_URL = '/beian'
const GET_LIST_URL = '/getList'
const GET_BEIAN_INFO = '/getBeianInfo'
/**
 * @description: 获取备案列表
 * @param {*} parameter
 * @return {*}
 */
export function getList (parameter) {
  return axios({
    url: PREFIX_URL + GET_LIST_URL,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 获取备案信息
 * @param {*} parameter
 * @return {*}
 */
export function getBeianInfo (parameter) {
  return axios({
    url: PREFIX_URL + GET_BEIAN_INFO,
    method: 'get',
    params: parameter
  })
}
