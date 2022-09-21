/*
 * @Author: ldx
 * @Date: 2021-06-08 11:25:13
 * @LastEditTime: 2021-07-05 11:05:05
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\api\patent\patent.js
 */
import { axios } from '@/utils/request'
import { getData } from '@/api/utils/index'

const PREFIX_URL = '/patentPlan'

/**
 * @description: 提交专利交底书审核
 * @param {*} params
 * @return {*}
 */
export function submitPatents (params) {
  return axios({
    url: PREFIX_URL + '/submitPatents',
    method: 'post',
    data: params
  }).then(response => {
    if (response.success) {
      return Promise.resolve(response.data)
    } else {
      throw new Error(response.errorMessage || '系统异常，请联系管理员！')
    }
  })
}
/**
 * @description: 查询专利资料意见列表
 * @param {*} params
 * @return {*}
 */
export function getPatentOpinions (params) {
  return axios({
    url: PREFIX_URL + '/getPatentOpinions',
    method: 'get',
    params
  }).then(response => {
    if (response.success) {
      return Promise.resolve(response.data)
    } else {
      throw new Error(response.errorMessage || '系统异常，请联系管理员！')
    }
  })
}
/**
 * @description: 添加/编辑专利资料意见
 * @param {*} params
 * @return {*}
 */
export function saveOpinion (params) {
  return axios({
    url: PREFIX_URL + '/saveOpinion',
    method: 'post',
    data: params
  }).then(response => {
    if (response.success) {
      return Promise.resolve(response.data)
    } else {
      throw new Error(response.errorMessage || '系统异常，请联系管理员！')
    }
  })
}
/**
 * @description: 删除专利资料意见
 * @param {*} params
 * @return {*}
 */
export function delOpinions (params) {
  return axios({
    url: PREFIX_URL + '/delOpinions',
    method: 'post',
    data: params
  }).then(response => {
    if (response.success) {
      return Promise.resolve(response.data)
    } else {
      throw new Error(response.errorMessage || '系统异常，请联系管理员')
    }
  })
}
/**
 * @description: 获取专利文件列表
 * @param {*} params
 * @return {*}
 */
export function getPatentFiles (params) {
  return getData(axios({
    url: '/patentPlan/getPatentFiles',
    method: 'get',
    params
  }))
}
