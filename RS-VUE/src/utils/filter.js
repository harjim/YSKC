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

Vue.filter('ZeroFormat', function (value, length = 2) {
  return value <= 9 ? (Array(length).join('0') + value).slice(-length) : value
})
Vue.filter('MoneyFormat', function (value, length = 2) {
  if (value && typeof value === 'number') {
    return value.toFixed(length)
  }
  return value
})
Vue.filter('dayjs', function (dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(dataStr).format(pattern)
})

Vue.filter('moment', function (dataStr, pattern = 'YYYY-MM-DD HH:mm:ss') {
  return moment(dataStr).format(pattern)
})
Vue.filter('MonthFormat', function (dataStr) {
  return moment(dataStr).format('YYYY-MM')
})
Vue.filter('YearFormat', function (dataStr) {
  return moment(dataStr).format('YYYY')
})
Vue.filter('MonthNumberFormat', function (dataStr) {
  return moment(dataStr).format('MM')
})
Vue.filter('DayNumberFormat', function (dataStr) {
  return moment(dataStr).format('DD')
})
Vue.filter('DayFormat', function (dataStr) {
  if (!dataStr) { return '-' }
  const formatDate = moment(dataStr).format('YYYY-MM-DD')
  return formatDate === 'Invalid date' ? dataStr : formatDate
})
Vue.filter('existDate', function (day, month) {
  var monthTime = new Date(month)
  return day <= new Date(monthTime.getFullYear(), monthTime.getMonth() + 1, 0).getDate()
})
// 查找枚举（选项）的值
Vue.filter('getLabel', function (value, options, placeholder, key = 'value', label = 'label') {
  if (value == null) return placeholder
  const item = options.find(item => item[key] === value)
  return (item && item[label]) || placeholder
})
