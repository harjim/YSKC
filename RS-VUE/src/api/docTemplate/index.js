/*
 * @Author: ldx
 * @Date: 2021-02-19 14:01:12
 * @LastEditTime: 2021-02-20 10:33:39
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\api\docTemplate\index.js
 */
import { axios } from '@/utils/request'
/**
 * @description: 设置文档审核栏数据
 */
const SET_DOC_FOOTER = '/projectDocFileData/setDocFooter'
/**
 * @description: 获取文档审核栏数据
 */
const GET_DOC_FOOTER = '/projectDocFileData/getDocFooter'

export function setDocFooter (parameter) {
  return axios({
    url: SET_DOC_FOOTER,
    method: 'post',
    data: parameter
  })
}

export function getDocFooter (parameter) {
  return axios({
    url: GET_DOC_FOOTER,
    method: 'get',
    params: parameter
  })
}
