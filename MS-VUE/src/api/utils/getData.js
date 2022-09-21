/*
 * @Author: ldx
 * @Date: 2021-06-10 09:45:27
 * @LastEditTime: 2021-07-14 08:45:28
 * @LastEditors: ldx
 * @Description: 获取数据
 * @FilePath: \MS-VUE\src\api\utils\getData.js
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
