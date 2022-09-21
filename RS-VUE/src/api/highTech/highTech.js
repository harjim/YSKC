/*
 * @Author: ldx
 * @Date: 2021-05-29 08:18:12
 * @LastEditTime: 2021-06-02 13:59:06
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\api\highTech\highTech.js
 */
import { axios } from '@/utils/request'

const PREFIX_URL = '/highTech'

/**
 * @description: 获取高品列表
 * @param {*} params
 * @return {*}
 */
export function getList (params) {
  return axios({
    url: PREFIX_URL + '/getList',
    method: 'get',
    params
  })
}
/**
 * @description: 添加/更新高品详情
 * @param {*} params
 * @return {*}
 */
export function save (params) {
  return axios({
    url: PREFIX_URL + '/save',
    method: 'post',
    data: params
  })
}
/**
 * @description: 获取高品现有最大的代码
 * @param {*} params
 * @return {*}
 */
export function getMaxCodeNum (params) {
  return axios({
    url: PREFIX_URL + '/getMaxCodeNum',
    method: 'get',
    params
  })
}

/**
 * @description: 获取高品详情
 * @param {*} params
 * @return {*}
 */
export function getTechInfo (params) {
  return axios({
    url: PREFIX_URL + '/getTechInfo',
    method: 'get',
    params
  })
}
/**
 * @description: 添加/更新高品详情
 * @param {*} params
 * @return {*}
 */
export function saveDetail (params) {
  return axios({
    url: PREFIX_URL + '/saveDetail',
    method: 'post',
    data: params
  })
}

/**
 * @description: 获取高品可绑定的项目列表
 * @param {*} params
 * @return {*}
 */
export function getProjects (params) {
  return axios({
    url: PREFIX_URL + '/getProjects',
    method: 'get',
    params
  })
}

/**
 * @description: 删除上传文件
 * @param {*} params
 * @return {*}
 */
export function delTechFile (params) {
  return axios({
    url: PREFIX_URL + '/delTechFile',
    method: 'post',
    data: params
  })
}
/**
 * @description: 上传备案相关文件
 * @param {*} params
 * @return {*}
 */
export function upload (params) {
  return axios({
    url: PREFIX_URL + '/upload',
    method: 'post',
    data: params
  })
}

/**
 * @description: 验证高品代码或名称唯一
 * @param {*} params
 * @returns
 */
export function verifyCodeAndName (params) {
  return axios({
    url: PREFIX_URL + '/verifyCodeAndName',
    method: 'get',
    params
  })
}
/**
 * @description: 删除高品
 * @param {*} params
 * @returns
 */
export function delHighTech (params) {
  return axios({
    url: PREFIX_URL + '/delHighTech',
    method: 'post',
    data: params
  })
}

/**
 * @description: 导出高品明细
 * @param {*} params
 * @returns
 */
export function exportHighTech (params) {
  return axios({
    url: PREFIX_URL + '/exportHighTech',
    method: 'get',
    params
  })
}
