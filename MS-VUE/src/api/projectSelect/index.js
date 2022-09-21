/*
 * @Author: ldx
 * @Date: 2021-04-14 08:35:12
 * @LastEditTime: 2021-04-14 08:41:31
 * @LastEditors: ldx
 * @Description: 项目列表接口
 * @FilePath: \MS-VUE\src\api\projectSelect\index.js
 */
import { axios } from '@/utils/request'
const PREFIX_URL = '/projectProgress'

const GET_LIST_PROJECT = '/getListProject'

/**
 * @description: 项目列表
 * @param {*} parameter { year, companyId, auditCount}
 * @return {*}
 */
export function getListProject (parameter) {
  return axios({
    url: PREFIX_URL + GET_LIST_PROJECT,
    methods: 'get',
    params: parameter
  })
}
