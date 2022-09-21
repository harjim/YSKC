/*
 * @Author: ldx
 * @Date: 2021-03-18 15:25:34
 * @LastEditTime: 2021-03-18 15:36:40
 * @LastEditors: ldx
 * @Description: 备案清单
 * @FilePath: \RS-VUE\src\api\tech\BeiAnGuanLi\ForReference\index.js
 */
import { axios } from '@/utils/request'
const PREFIX_URL = '/techEquipment'

const GET_LIST_URL = '/getList'
const EDIT_EQUIPMENT = '/editEquipment'
const DEL_EQUIPMENTS = ' /delEquipments'
const SAVE_EQUIPMENTS = '/saveEquipments'
const IMPORT_EQUIPMENT = '/importEquipment'

/**
 * @description: 获取备案清单
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
 * @description: 编辑备案清单
 * @param {*} parameter
 * @return {*}
 */
export function editEquipment (parameter) {
  return axios({
    url: PREFIX_URL + EDIT_EQUIPMENT,
    method: 'post',
    data: parameter
  })
}
/**
 * @description: 删除备案清单
 * @param {*} parameter
 * @return {*}
 */
export function delEquipments (parameter) {
  return axios({
    url: PREFIX_URL + DEL_EQUIPMENTS,
    method: 'post',
    data: parameter
  })
}
/**
 * @description: 保存备案清单
 * @param {*} parameter
 * @return {*}
 */
export function saveEquipments (parameter) {
  return axios({
    url: PREFIX_URL + SAVE_EQUIPMENTS,
    method: 'post',
    data: parameter
  })
}
/**
 * @description: 导入备案清单
 * @param {*} parameter
 * @return {*}
 */
export function importEquipment (parameter) {
  return axios({
    url: PREFIX_URL + IMPORT_EQUIPMENT,
    method: 'post',
    data: parameter
  })
}
