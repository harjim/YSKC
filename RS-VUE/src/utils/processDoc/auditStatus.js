/*
 * @Author: ldx
 * @Date: 2021-01-27 08:23:07
 * @LastEditTime: 2022-04-28 09:34:27
 * @LastEditors: zdf
 * @Description:
 * @FilePath: \RS-VUE\src\utils\processDoc\auditStatus.js
 */
import store from '@/store'
const IS_EDIT = [2, 3, 5, 6, 7]
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
  '9999': '等待中'
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
  // if (!status) { return true }
  return status === null || status === undefined || IS_EDIT.includes(Number(status))
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
  if (status === null && status === undefined) return ''
  return statusMap[Number(status)]
}
/**
 * @description: 判断是否MS用户 0：RS用户 1: MS用户
 * @param {*}
 * @return {*} boolean false:RS用户；true:MS 用户
 */
function isMsUser () {
  const result = store.getters.userInfo.userSource
  return result > 0
}
function canModify (status) {
  return !isMsUser() || isEditStatus(status)
}
export {
  getStatusName,
  isExportStatus,
  isAuth,
  isFinance,
  isEditStatus,
  isMsUser,
  status,
  statusMap,
  statusColor,
  canModify
}
