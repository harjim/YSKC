/*
 * @Author: ldx
 * @Date: 2020-09-10 10:23:41
 * @LastEditTime: 2020-09-10 10:25:15
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\utils\print\index.js
 */
import Print from './print.js'
Print.install = function (Vue) {
  Vue.directive('print', Print)
}

export default Print
