/*
 * @Author: ldx
 * @Date: 2021-06-10 09:42:56
 * @LastEditTime: 2021-08-23 14:27:56
 * @LastEditors: zdf
 * @Description: 专利接口
 * @FilePath: \MS-VUE\src\api\patent\patent.js
 */
import { axios } from '@/utils/request'
import { getData } from '@/api/utils/getData'

const PREFIX_URL = '/projectProgress'

/**
 * @description: 批量审核流程
 * @param {*} params
 * @return {*}
 */
export function projectAudits (params) {
  return getData(axios({
    url: PREFIX_URL + '/projectAudits',
    method: 'post',
    data: params
  }))
}
/**
 * @description: 获取专利交底书审核列表
 * @param {*} params
 * @return {*}
 */
export function getPatentList (params) {
  return getData(axios({
    url: PREFIX_URL + '/getPatentList',
    method: 'get',
    params
  }))
}

/**
 * @description: 删除专利申请资料
 * @param {*} params
 * @return {*}
 */
export function delUploadFile (params) {
  return getData(axios({
    url: '/rsPatentPlan' + '/delUploadFile',
    method: 'post',
    data: params
  }))
}
/**
 * @description: 查询专利申请信息
 * @param {*} params
 * @return {*}
 */
export function getPatentInfo (params) {
  return getData(axios({
    url: '/patentPlan' + '/getPatentInfo',
    method: 'get',
    params
  }))
}
/**
 * @description: 负责人名称唯一
 * @param {*} params
 * @return {*}
 */
export function checkMasterName (params) {
  return getData(axios({
    url: 'patentMaster/checkMasterName',
    method: 'get',
    params
  }))
}
