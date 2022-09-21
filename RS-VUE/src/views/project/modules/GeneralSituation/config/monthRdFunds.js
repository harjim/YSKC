/*
 * @Author: ldx
 * @Date: 2021-07-26 08:26:16
 * @LastEditTime: 2021-07-27 10:11:41
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\modules\GeneralSituation\config\monthRdFunds.js
 */
export default {
  color: ['#3AA1FF', '#59D9B4', '#78C4F8', '#66D487', '#E6DA46', '#EBAA6E', '#6DB486', '#51609B', '#8990E2', '#3AC9CD', '#84E0E5', '#6C9AE3', '#6C6DE2', '#BE94F3', '#ED729C', '#F98A9C', '#DBA7DF'],
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: [],
    axisLabel: { interval: 0, rotate: 20 }
  },
  yAxis: {
    type: 'value'
  },
  lineStyle: {
    type: 'solid'
    // shadowBlur: 10,
    // shadowColor: 'rgba(0, 0, 0, 0.2)',
    // shadowOffsetX: 10
  },
  tooltip: {
    trigger: 'axis',
    position: function (point, params, dom, rect, size) {
      // 固定在顶部
      return [point[0] + 20, '10%']
    },
    textStyle: {
      fontSize: 12,
      lineHeight: 13
    }
  },
  legend: {
    orient: 'horizontal',
    top: 'top'
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  series: []
}
