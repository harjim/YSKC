/*
 * @Author: zdf
 * @Date: 2021-02-08 16:06:14
 * @LastEditTime: 2021-02-24 18:19:04
 * @LastEditors: zdf
 * @Description: 日历【农历】转换
 * @FilePath: \RS-VUE\src\utils\calendar.js
 */
import moment from 'moment'
// 2009-2049 16进制
const lunarHexing = [
  '0cab5', '0a950', '0b4a0', '0baa4', '0ad50', '055d9', '04ba0', '0a5b0', '15176', '052b0',
  '0a930', '07954', '06aa0', '0ad50', '05b52', '04b60', '0a6e6', '0a4e0', '0d260', '0ea65',
  '0d530', '05aa0', '076a3', '096d0', '04bd7', '04ad0', '0a4d0', '1d0b6', '0d250', '0d520',
  '0dd45', '0b5a0', '056d0', '055b2', '049b0', '0a577', '0a4b0', '0aa50', '1b255', '06d20', '0ada0'
]

// 开始年
const beginYear = 2009
// 2021
const beginDate = moment('2010-01-01')
const endDate = moment('2049-12-31')
const beginLunar = { y: 2009, m: 11, d: 17 }
// 节日
const festival = {
  '0101': '元旦节',
  '0214': '情人节',
  '0501': '劳动节',
  '0504': '青年节',
  '0601': '儿童节',
  '0910': '教师节',
  '1001': '国庆节',
  '1225': '圣诞节',
  '0308': '妇女节',
  '0312': '植树节',
  '0401': '愚人节',
  '0512': '护士节',
  '0701': '建党节',
  '0801': '建军节',
  '1224': '平安夜'
}
// 农历节日
const lunarFestival = {
  '0101': '春节',
  '0115': '元宵节',
  '0202': '龙头节',
  '0505': '端午节',
  '0707': '七夕节',
  '0715': '中元节',
  '0815': '中秋节',
  '0909': '重阳节',
  '1208': '腊八节',
  '1223': '小年'
}
const dayCall = [ null,
  null, '初二', '初三', '初四', '初五', '初六', '初七', '初八', '初九', '初十',
  '十一', '十二', '十三', '十四', '十五', '十六', '十七', '十八', '十九', '二十',
  '廿一', '廿二', '廿三', '廿四', '廿五', '廿六', '廿七', '廿八', '廿九', '三十'
]
const monthCall = [
  null, '正月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '冬月', '腊月'
]

function getMarkDay (m, d, date, leapMonth, y) {
  // 12月最后一天为除夕
  if (m === 12 && d === lunarInfo[y].days[m - 1]) {
    return '除夕'
  }
  let result = lunarFestival[`${m < 10 ? '0' + m : m}${d < 10 ? '0' + d : d}`]
  if (result) {
    return result
  }
  const dateM = date.month() + 1
  const dateD = date.date()
  if (dateM === 4 && dateD > 3 & dateD < 6) {
    const mod = y % 4
    // 有效判断：2016~2041年
    if ((mod <= 1 && dateD === 4) || (mod > 1 && dateD === 5)) {
      return '清明节'
    }
  }
  result = festival[`${dateM < 10 ? '0' + dateM : dateM}${dateD < 10 ? '0' + dateD : dateD}`]
  if (result) {
    return result
  }
  result = dayCall[d]
  if (result) {
    return result
  }
  return leapMonth ? '润' + monthCall[m] : monthCall[m]
}

/**
 * 加载开始年的天数
 * 通过beginLunar对象确定开始月份，减去beginLunar开始前的所有天数
 */
function loadBeginYearDays (beginInfo) {
  let i = 0
  for (; i < beginLunar.m - 1; i++) {
    beginInfo.yearDays -= beginInfo.days[i]
    beginInfo.days[i] = 0
  }
  if (beginInfo.leapMonth > 0 && beginInfo.leapMonth < beginLunar.m) {
    beginInfo.yearDays -= beginInfo.leapDay
    beginInfo.leapDay = 0
  }
  return beginInfo
}

/**
 * 年份从2010年开始
 * 获取农历16进制转换为对象
 * return current{leapDay:闰月天数，days:每月天数，leapMonth:闰月月份,为-1,不存在闰月，yearDays:每年总天数}
 */
function getLunarInfo () {
  const result = {}
  const full = '0000'
  for (const i in lunarHexing) {
    const lunars = lunarHexing[i].split('')
    const current = { leapDay: undefined, days: [], leapMonth: undefined, yearDays: 0 }
    for (const l in lunars) {
      let binary = parseInt(lunars[l], 16).toString(2)
      binary = full.substr(0, full.length - binary.length) + binary
      // leapDay,润月天数
      if (Number(l) === 0) {
        current.leapDay = parseInt(binary, 2) ? 30 : 29
      } else if (l < lunars.length - 1) {
        // 每月天数
        for (const bit of binary) {
          const monthDay = parseInt(bit, 2) ? 30 : 29
          current.days.push(monthDay)
          current.yearDays += monthDay
        }
      } else {
        // leapMonth小于0时，闰月不存在
        current.leapMonth = parseInt(binary, 2) - 1
      }
    }
    if (current.leapMonth >= 0) {
      current.yearDays += current.leapDay
    }
    result[`${beginYear + Number(i)}`] = current
  }
  loadBeginYearDays(result[beginYear])
  return result
}
// 农历对象
const lunarInfo = getLunarInfo()

/**
 * @param {moment} date moment对象
 * 转换农历对象
 */
function lunarDay (date) {
  // 如果date < 开始日期，返回空对象
  if (date < beginDate || date > endDate) {
    return {}
  }
  let diffDay = date.diff(beginDate, 'day') + beginLunar.d
  let y = beginYear
  while (diffDay > lunarInfo[y].yearDays) {
    diffDay -= lunarInfo[y].yearDays
    y++
  }
  let m = 0
  const leapMonth = lunarInfo[y].leapMonth
  let leapCount = 0
  for (; m <= lunarInfo[y].days.length; m++) {
    if (m === 12) {
      m = 0
      y++
    }
    let mDay = lunarInfo[y].days[m]
    if (leapCount === 1) {
      mDay = lunarInfo[y].leapDay
      leapCount++
      m--
    }
    if (m === leapMonth && leapCount === 0) {
      leapCount++
    }
    if (diffDay <= mDay) {
      break
    } else {
      diffDay = diffDay - mDay
    }
  }
  const result = { y: y, m: m + 1, d: diffDay }
  result.mark = getMarkDay(result.m, result.d, date, leapMonth === m && leapCount > 1, y)
  return result
}
export { lunarDay, lunarInfo }
