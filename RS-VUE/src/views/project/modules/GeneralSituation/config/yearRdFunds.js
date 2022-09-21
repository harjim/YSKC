/*
 * @Author: ldx
 * @Date: 2021-07-26 08:26:30
 * @LastEditTime: 2021-07-26 17:09:53
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\modules\GeneralSituation\config\yearRdFunds.js
 */

export default {
  color: ['#3AA1FF', '#59D9B4', '#78C4F8', '#66D487', '#E6DA46', '#EBAA6E', '#6DB486', '#51609B', '#8990E2', '#3AC9CD', '#84E0E5', '#6C9AE3', '#6C6DE2', '#BE94F3', '#ED729C', '#F98A9C', '#DBA7DF'],
  tooltip: {
    trigger: 'item',
    textStyle: {
      fontSize: 12
    },
    formatter: '{b}:{c}({d}%)'
  },
  legend: {
    orient: 'horizontal',
    icon: 'circle',
    top: 'top',
    itemWidth: 12,
    itemHeight: 12,
    textStyle: {
      height: 13,
      fontSize: 12,
      lineHeight: 13
    }
  },
  series: [
    {
      type: 'pie',
      radius: '45%',
      data: [],
      center: ['50%', '70%'],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}
