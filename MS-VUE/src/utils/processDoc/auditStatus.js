/*
 * @Author: ldx
 * @Date: 2021-01-27 08:23:07
 * @LastEditTime: 2022-09-13 16:10:22
 * @LastEditors: hm
 * @Description:
 * @FilePath: \MS-VUE\src\utils\processDoc\auditStatus.js
 */
const IS_EDIT = [2, 3, 5, 6, 7, 8]
const IS_EXPORT = [1]
const statusMap = {
  '0': '进行中',
  '1': '通过',
  '2': '驳回',
  '3': '激活',
  '4': '提交',
  '5': '未提交',
  '6': '审核失败',
  '7': '提交失败',
  '8': '取消',
  '9999': '等待中'
}
const statusMaps = {
  '进行中': [0], // ongoing
  '通过': [1], // passTrue
  '驳回': [2], // passFalse
  '激活': [3], // activated
  '提交': [4], // commit
  '未提交': [5], // uncommitted
  '审核失败': [6], // auditFailure
  '提交失败': [7], // submitFailure
  '取消': [8], // cancel
  '等待中': [999] // timing
}

const selectStatusMap = {
  '0': '进行中',
  '1': '通过',
  '2': '驳回',
  '3': '激活',
  '4': '提交',
  '5': '未提交',
  '6': '审核失败',
  '7': '提交失败',
  '8': '取消'
}

const statusColor = {
  '0': '#1890ff',
  '1': '#548B54',
  '2': '#FF4500',
  '3': '#66CDAA',
  '4': '#9AFF9A',
  '5': '#606266',
  '6': '#FF4500',
  '7': '#FF4500',
  '8': '#FF4500',
  '9999': '#606266'
}
const status = {
  KV: statusMap,
  color: statusColor
}

/**
 * @description: 判断是否可以编辑和提交
 * @param { number } status
 * @return { boolean } boolean 可以:true ;不可:false
 */
function isEditStatus (status) {
  if (status === null || status === undefined) {
    status = 5
  }
  return IS_EDIT.includes(status * 1)
}

/**
 * @description: 判断是否财务文档
 * @param {*} sign 1 是财务文档 0 不是
 * @return {*} boolean true 是财务文档 false 不是
 */
function isFinance (sign) {
  return Number(sign) === 1
}

/**
 * @description: 判断是否有权限
 * @param {*} obj 调用对象
 * @param {*} sign String 标识符
 * @return {*} boolean true 有权限 false 无权限
 */
function isAuth (obj, sign) {
  return obj.$auth(sign)
}

/**
 * @description: 判断是否可以导出
 * @param { number} status
 * @return { boolean } boolean
 */
function isExportStatus (status) {
  return IS_EXPORT.includes(status * 1)
}

/**
 * @description: 状态编码转中文名称
 * @param { number } status
 * @return { string } string
 */
function getStatusName (status) {
  if (status === undefined || status === null) {
    status = 5
  }
  // return statusMap[Number(status)]
  return Object.keys(statusMaps).filter(key => statusMaps[key].includes(Number(status)))[0]
}

export {
  getStatusName,
  isExportStatus,
  isAuth,
  isFinance,
  isEditStatus,
  status,
  statusMap,
  statusColor,
  selectStatusMap
}
