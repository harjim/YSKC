/*
 * @Author: ldx
 * @Date: 2021-06-18 08:23:23
 * @LastEditTime: 2021-06-21 08:57:42
 * @LastEditors: ldx
 * @Description: 记录及附件接口
 * @FilePath: \RS-VUE\src\api\doc\appendixForm.js
 */
import { getData } from '@/api/utils/index'
import { axios } from '@/utils/request'

/**
 * @description: 获取附件列表
 * @param {*} params
 * @return {*}
 */
export function getAttachments (params) {
  return getData(axios({
    url: '/docFileAttachment/getAttachments',
    method: 'get',
    params
  }))
}
/**
 * @description: 删除上传文件
 * @param {*} params
 * @return {*}
 */
export function delUploadFile (params) {
  return getData(axios({
    url: '/docFileAttachment/delUploadFile',
    method: 'post',
    params
  }))
}
