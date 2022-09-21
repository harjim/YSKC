/*
 * @Author: ldx
 * @Date: 2020-08-03 17:41:28
 * @LastEditTime: 2022-03-03 20:41:50
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \MS-VUE\src\utils\filter.js
 */

import Vue from 'vue'
import moment from 'moment'
import 'moment/locale/zh-cn'
moment.locale('zh-cn')

Vue.filter('NumberFormat', function (value) {
  if (!value) {
    return '0.00'
  }
  const intPartFormat = value.toString().replace(/\d(?=(?:\d{3})+\b)/g, '$&,') // 将整数部分逢三一断
  return intPartFormat
})

Vue.filter('NumberFormatHasNull', function (value) {
  if (!(value !== null && /^[0-9]+.?[0-9]*/.test(value))) {
    return '--'
  }
  const intPartFormat = (+value).toFixed(2).toString().replace(/\d(?=(?:\d{3})+\b)/g, '$&,') // 将整数部分逢三一断
  return intPartFormat
})

Vue.filter('dayjs', function (dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(dataStr).format(pattern)
})

Vue.filter('DayFormat', function (dataStr) {
  if (!dataStr) { return '-' }
  const formatDate = moment(dataStr).format('YYYY-MM-DD')
  return formatDate === 'Invalid date' ? dataStr : formatDate
})
Vue.filter('DateFormat', function (dataStr) {
  if (!dataStr) { return '' }
  const formatDate = moment(dataStr).format('YYYY-MM-DD')
  return formatDate === 'Invalid date' ? dataStr : formatDate
})
Vue.filter('moment', function (dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(dataStr).format(pattern)
})
// 数值位数不足前面补N零
Vue.filter('prefixZero', function (num, n) {
  return (Array(n).join(0) + num).slice(-n)
})
// 查找枚举（选项）的值
Vue.filter('getLabel', function (value, options, placeholder, key = 'value', label = 'label') {
  if (value == null) return placeholder
  const item = options.find(item => item[key] === value)
  return (item && item[label]) || placeholder
})
