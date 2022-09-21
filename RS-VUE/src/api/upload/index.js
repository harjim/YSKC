/*
 * @Author: ldx
 * @Date: 2021-06-18 08:47:04
 * @LastEditTime: 2021-07-15 14:56:24
 * @LastEditors: ldx
 * @Description: 上传图片
 * @FilePath: \RS-VUE\src\api\upload\index.js
 */
import { getData } from '../utils/index'
import { axios } from '@/utils/request'
/**
 * @description: 上传图片
 * @param {*} params
 * @return {*}
 */
export function upload (params) {
  const data = new FormData()
  for (const key in params) {
    if (Object.hasOwnProperty.call(params, key)) {
      const val = params[key]
      data.append(key, val)
    }
  }
  return getData(axios.post(
    '/docFileAttachment/upload',
    data,
    { headers: { 'Content-Type': 'multipart/form-data' } }
  ))
}
/**
 * @description: 提案管理上传
 * @param {*} params
 * @return {*}
 */
export function uploadFile (params) {
  const data = new FormData()
  const ary = Object.entries(params)
  if (ary.length) {
    ary.forEach(([key, val]) => {
      data.append(key, val)
    })
  }
  return getData(axios.post('/projectDocFileData/upload',
    data,
    { headers: { 'Content-Type': 'multipart/form-data' } }
  ))
}
