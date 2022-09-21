/*
 * @Author: ldx
 * @Date: 2021-02-22 14:11:36
 * @LastEditTime: 2021-02-22 14:48:44
 * @LastEditors: ldx
 * @Description:  覆盖ant design的弹出挂载
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\js\screenFullMountDom.js
 */
const DOM_NAME = 'fullBox'
export const domName = DOM_NAME
/**
 * @description: 覆盖ant design的弹出挂载
 * @param {*} domName 默认值'fullBox' 找不到对应的DOM去找最大的body
 * @return {*} element|Dom
 */
export function popupContainer (domName) {
  if (!domName) {
    domName = DOM_NAME
  }
  const dom = document.getElementById(domName)
  if (dom) {
    return dom
  } else {
    return document.body
  }
}
