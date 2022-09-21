/*
 * @Author: ldx
 * @Date: 2021-06-18 08:24:34
 * @LastEditTime: 2021-06-18 08:24:45
 * @LastEditors: ldx
 * @Description: 工具
 * @FilePath: \RS-VUE\src\api\utils\index.js
 */
export function getData (promise) {
  return promise.then(response => {
    if (response.success) {
      return Promise.resolve(response.data)
    } else {
      throw new Error(response.errorMessage || '系统异常，请联系管理员！')
    }
  }).catch(error => {
    throw new Error(error.message || '系统异常，请联系管理员！')
  })
}
