/*
 * @Author: ldx
 * @Date: 2020-12-30 08:56:47
 * @LastEditTime: 2021-06-26 09:38:44
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\api\doc\index.js
 */
import {
  GET_DOC_DATA,
  GET_ALL_STAGE,
  SAVE_DATA,
  GET_PREVIEW_DOC_DATA,
  EXPORT_PDF,
  SUBMIT_DOC_AUDIT,
  GET_COUNT_DOC_AUDIT,
  ADJUST_FILE_STAGE
} from './interfaceType'
import { axios } from '@/utils/request'
import { getData } from '../utils/index'
/**
 * @description: 过程文档数据
 * @param {*} parameter
 * @return {*}
 */
export function getDocData (parameter) {
  return axios({
    url: GET_DOC_DATA,
    method: 'get',
    params: parameter
  })
/**
 * @description: 获取项目阶段
 * @param {*}
 * @return {*}
 */
}
export function getAllStage (parameter) {
  return axios({
    url: GET_ALL_STAGE,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 保存过程文档数据
 * @param {*} parameter
 * @return {*}
 */
export function saveData (parameter) {
  return axios({
    url: SAVE_DATA,
    method: 'post',
    data: parameter
  })
}
/**
 * @description: 预览文件的数据
 * @param {*} parameter { docId, projectId, currentYear, companyId }
 * @return {*}
 */
export function getPreviewDocData (parameter) {
  return axios({
    url: GET_PREVIEW_DOC_DATA,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 导出PTF文档
 * @param {*} parameter
 * @return {*}
 */
export function exportPdf (parameter) {
  return axios({
    url: EXPORT_PDF,
    method: 'get',
    params: parameter
  })
}
/**
 * @description: 提交单个文档审核
 * @param {*} parameter
 * @return {*}
 */
export function submitDocAudit (parameter) {
  return axios({
    url: SUBMIT_DOC_AUDIT,
    method: 'post',
    data: parameter
  })
}
/**
 * @description: 获取项目文档的状态统计数据
 * @param {*} parameter
 * @return {*}
 */
export function getCountDocAudit (parameter) {
  return axios({
    url: GET_COUNT_DOC_AUDIT,
    method: 'get',
    params: parameter
  })
}
export function adjustFileStage (parameter) {
  return axios({
    url: ADJUST_FILE_STAGE,
    method: 'post',
    data: parameter
  })
}

/**
 * @description:批量删除阶段过程文件
 * @param {*} parameter
 * @return {*}
 */
export function deleteDocFiles (parameter) {
  return getData(axios({
    url: '/projectDocFile/deleteDocFiles',
    method: 'post',
    data: parameter
  }))
}
