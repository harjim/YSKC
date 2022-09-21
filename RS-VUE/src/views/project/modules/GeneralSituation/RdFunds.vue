<!--
 * @Author: ldx
 * @Date: 2021-07-16 16:21:00
 * @LastEditTime: 2021-07-27 19:00:18
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\modules\GeneralSituation\RdFunds.vue
-->
<template>
  <a-row :gutter="[12,12]" style="margin-right: 0px; margin-left: 0px;">
    <a-col :span="16" style="padding-left: 0px;">
      <a-card class="myCard" :bodyStyle="twoBodyStyleCard">
        <div style="display: flex; justify-content: space-between; margin-bottom: 14px;">
          <div class="card_title">月度研发费</div>
          <div style="font-size: 12px; text-align:center;">单位：万元</div>
        </div>
        <div id="category" style="height: calc(100% - 30px); width: 100%;"></div>
      </a-card>
    </a-col>
    <a-col :span="8" style="padding-right: 0px;">
      <a-card class="myCard" :bodyStyle="twoBodyStyleCard">
        <div style="display: flex; justify-content: space-between; margin-bottom: 14px;">
          <div class="card_title">年度研发费</div>
          <div style="font-size: 12px; text-align:center;">单位：万元</div>
        </div>
        <div id="pie" style="height: calc(100% - 30px); width: 100%;"></div>
      </a-card>
    </a-col>
  </a-row>
</template>

<script>
import * as echarts from 'echarts'
import monthRdFundsOption from './config/monthRdFunds'
import yearRdFundsOption from './config/yearRdFunds'
import { mapGetters } from 'vuex'
import { cloneDeep } from 'lodash'
export default {
  name: 'RdFunds',
  mounted () {
    this.costTypes.forEach(item => {
      if (!(item.value * 1 > 400 && item.value * 1 < 403)) {
        if (item.value * 1 === 400) {
          const temp = {}
          Object.assign(temp, item)
          temp.title = '摊销'
          this.mergeCostTypes.push(temp)
          this.costTypeMap[item.value] = '摊销'
        } else {
          this.mergeCostTypes.push(item)
          this.costTypeMap[item.value] = item.title
        }
      }
    })
    const pieDom = document.getElementById('pie')
    this.pieChart = echarts.init(pieDom)
    const categoryDom = document.getElementById('category')
    this.categoryChart = echarts.init(categoryDom)
    window.addEventListener('resize', () => {
      this.pieChart.resize()
      this.categoryChart.resize()
    })
  },
  props: {
    rdCosts: {
      type: Array,
      required: true
    },
    rdFundModels: {
      type: Array,
      required: true
    },
    costTypes: {
      type: Array,
      required: true
    }
  },
  watch: {
    rdCosts: {
      deep: true,
      handler (v) {
        this.loadLineData(v)
      }
    },
    rdFundModels: {
      deep: true,
      handler (v) {
        this.loadPieData(v)
      }
    },
    toggleLeftExpend: {
      // immediate: true,
      handler (v) {
        this.$nextTick(() => {
          setTimeout(() => {
            this.pieChart.resize()
            this.categoryChart.resize()
          }, 150)
        })
      }
    }
  },
  computed: {
    ...mapGetters(['currentYear', 'toggleLeftExpend'])
  },
  data () {
    return {
      pieChart: undefined,
      categoryChart: undefined,
      twoBodyStyleCard: {
        padding: '24px',
        height: '388px'
      },
      costTypeMap: {},
      mergeCostTypes: []
    }
  },
  methods: {
    loadLineData (data) {
      let series = []
      const xAxisData = []
      const map = {}
      if (!(data && data.length)) {
        for (let i = 1; i <= 12; i++) {
          xAxisData.push(`${this.currentYear}-${this.PrefixZero(i, 2)}`)
        }
        this.mergeCostTypes.forEach((t) => {
          const data = Array(12).fill(0)
          series.push({ name: t.title, type: 'line', smooth: true, data })
        })
      } else {
        const mergeData = this.lineEergeData(data)
        mergeData.forEach((item, index) => {
          xAxisData.push(item.month)
          this.mergeCostTypes.forEach((t) => {
            if (!map[t.value]) {
              map[t.value] = { name: t.title, type: 'line', smooth: true, data: [] }
            }
            map[t.value].data.push(Math.round(((item[t.value] || 0) / 10000) * 100) / 100)
          })
        })
        series = Object.values(map)
      }
      monthRdFundsOption.xAxis.data = xAxisData
      monthRdFundsOption.series = series
      monthRdFundsOption.series.length && this.categoryChart.setOption(monthRdFundsOption)
      this.categoryChart.resize()
    },
    loadPieData (data) {
      const keyMap = {}
      const newData = this.pieEergeData(cloneDeep(data))
      if (newData.length) {
        newData.forEach(item => { keyMap[item.rdType] = item.rdFund })
      }
      const resultAry = []
      Object.entries(this.costTypeMap).forEach(([key, value]) => {
        resultAry.push({ name: value, value: Math.round(((keyMap[key] || 0) / 10000) * 100) / 100 })
      })
      yearRdFundsOption.series[0].data = resultAry
      yearRdFundsOption.series[0].data.length && this.pieChart.setOption(yearRdFundsOption)
      this.pieChart.resize()
    },
    lineEergeData (data) {
      const returnData = []
      data.forEach(item => {
        const mergeData = {}
        Object.assign(mergeData, item)
        Object.entries(mergeData).forEach(([key, value]) => {
          if ([401, 402].includes(Number(key))) {
            mergeData[400] = Number(mergeData[400] || 0) + Number(value)
            delete mergeData[key]
          }
        })
        returnData.push(mergeData)
      })
      return returnData
    },
    pieEergeData (data) {
      const dataMap = {}
      let total = 0
      let totalRdFund = 0
      data.forEach(item => {
        dataMap[item.rdType] = item
        totalRdFund = item.totalRdFund
        if ([400, 401, 402].includes(item.rdType)) {
          total += item.rdFund
        }
      })
      if (dataMap[400]) {
        dataMap[400].rdFund = total
      } else {
        dataMap[400] = {
          month: null,
          rdFund: total,
          rdType: 400,
          totalRdFund
        }
      }
      delete dataMap[401]
      delete dataMap[402]
      const returnData = Object.values(dataMap)
      return returnData
    },
    // 位数不足前面补零
    PrefixZero (num, n) {
      return (Array(n).join(0) + num).slice(-n)
    }
  }
}
</script>

<style lang="less" scoped>
.myCard {
  border-radius: 6px;
  .card_title {
    font-size: 16px;
    font-weight: 650;
  }
}
.trend-wrapper .g2-tooltip {
    position: absolute;
    z-index: 8;
    transition: left 0.4s cubic-bezier(0.23, 1, 0.32, 1) 0s, top 0.4s cubic-bezier(0.23, 1, 0.32, 1) 0s;
    background-color: transparent;
    color: rgb(89, 89, 89);
    padding: 0px 12px;
    margin: 0px;
    overflow-x: auto;
    width: 100%;
    left: 0px;
    top: 0px;
    pointer-events: auto;
  }
  .trend-wrapper .g2-tooltip-title {
    margin: 10px 0;
    font-weight: 700;
    height: 12px;
    line-height: 12px;
  }
  .trend-wrapper .g2-tooltip-items {
    display: flex;
    flex-direction: row;
    align-items: center;
    overflow: auto;
    width: 100%;
  }
  .trend-wrapper .g2-tooltip-item {
    opacity: 1;
    cursor: pointer;
    position: relative;
    display: flex;
    flex-direction: column;
    width: 92px;
    min-width: 92px;
    padding-left: 12px;
    justify-content: space-between;
  }
  .trend-wrapper .g2-tooltip-item.inactive {
    opacity: 0.25;
  }
  .trend-wrapper .g2-tooltip-item-marker {
    width: 3px;
    position: absolute;
    top: 0px;
    right: 0px;
    bottom: 0px;
    height: 48px;
    left: 0px;
  }
  .trend-wrapper .g2-tooltip-item-label {
    font-size: 14px;
    line-height: 14px;
    margin: 2px 0px 12px;
  }
  .trend-wrapper .g2-tooltip-item-value {
    font-weight: 700;
    font-size: 18px;
    line-height: 18px;
    color: rgba(0, 0, 0, 0.65);
    margin: 0px 0px 4px;
  }
  #container {
    width: 100%;
    height: 100%;
  }
  .wrapper {
    position: relative;
    width: 100%;
    height: 100%;
  }
  .wrapper .chart-wrapper {
    position: absolute !important;
    top:108px;
    bottom: 0px;
    right: 10px;
    left: 10px;
    height: calc(100% - 120px);
  }
</style>
