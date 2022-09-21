/*
 * @Author: ldx
 * @Date: 2021-07-03 08:16:39
 * @LastEditTime: 2021-07-05 09:24:47
 * @LastEditors: ldx
 * @Description:专利文件接口
 * @FilePath: \MS-VUE\src\api\patent\patentFile.js
 */

import { axios } from '@/utils/request'
import { getData } from '@/api/utils/getData'

/**
 * @description: 获取专利文件列表
 * @param {*} params
 * @return {*}
 */
export function getPatentFiles (params) {
  return getData(axios({
    url: '/PatentFile/getPatentFiles',
    method: 'get',
    params
  }))
}

/**
 * @description: 上传专利文件
 * @param {*} params
 * @return {*}
 */
export function uploadPatentFile (params) {
  return getData(axios({
    url: '/PatentFile/uploadPatentFile',
    method: 'post',
    data: params
  }))
}

/**
 * @description: 删除专利文件
 * @param {*} params
 * @return {*}
 */
export function delPatentFile (params) {
  return getData(axios({
    url: '/PatentFile/delPatentFile',
    method: 'post',
    data: params
  }))
}

/**
 * @description: 获取专利文件意见列表
 * @param {*} params
 * @return {*}
 */
export function getOpinions (params) {
  return getData(axios({
    url: '/PatentFile/getOpinions',
    method: 'get',
    params
  }))
}
