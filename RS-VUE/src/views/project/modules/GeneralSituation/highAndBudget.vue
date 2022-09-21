<!--
 * @Author: ldx
 * @Date: 2021-07-16 16:21:00
 * @LastEditTime: 2021-07-29 15:11:38
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\modules\GeneralSituation\highAndBudget.vue
-->
<template>
  <a-row :gutter="[12,12]" style="margin-right: 0px; margin-left: 0px;">
    <a-col :span="16" style="padding-left: 0px;">
      <a-card class="myCard" :bodyStyle="twoBodyStyleCard">
        <div style="display: flex; justify-content: space-between; margin-bottom: 14px;">
          <div class="card_title">归集费占总预算比(万元)</div>
          <div style="font-size: 12px; text-align:center;">总预算：{{ totalBuget }} 万元</div>
        </div>
        <div style="height: calc(100% - 30px); width: 100%;">
          <div id="budgetCategory" style="height: 100%; width: 100%;"></div>
        </div>
      </a-card>
    </a-col>
    <a-col :span="8" style="padding-right: 0px;">
      <a-card class="myCard" :bodyStyle="twoBodyStyleCard">
        <div style="display: flex; justify-content: space-between; margin-bottom: 14px;">
          <div class="card_title">高品收入</div>
          <div style="font-size: 12px; text-align:center;">单位：万元</div>
        </div>
        <div style="height: calc(100% - 30px); width: 100%;">
          <div id="highPie" style="height: 100%; width: 100%;"></div>
        </div>
      </a-card>
    </a-col>
  </a-row>
</template>

<script>
import * as echarts from 'echarts'
import { mapGetters } from 'vuex'
export default {
  name: 'HighAndBudget',
  props: {
    highModels: {
      type: Array,
      required: true
    },
    countBudgetModels: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      highPieChart: undefined,
      budgetCategoryChart: undefined,
      twoBodyStyleCard: {
        padding: '24px',
        height: '388px'
      },
      categoryOption: {
        color: ['#3AA1FF', '#78C4F8', '#59D9B4', '#66D487', '#E6DA46', '#EBAA6E', '#6DB486', '#51609B', '#8990E2', '#3AC9CD', '#84E0E5', '#6C9AE3', '#6C6DE2', '#BE94F3', '#ED729C', '#F98A9C', '#DBA7DF'],
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: [],
          axisLabel: { interval: 0, rotate: 20 }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: function (val) {
              return val + '%'
            }
          }
        },
        tooltip: {
          trigger: 'axis',
          textStyle: {
            fontSize: 12
          },
          formatter: (params, ticket, callback) => {
            const monthData = params[0].name
            const data = this.countBudgetModels.find(item => {
              return item.monthStr === monthData
            })
            if (!data) {
              return ''
            }
            return `
        <div style="width: 212px;padding: 10px;">
          <div style="padding: 5px 0; font-weight: 650;">${data.monthStr}</div>
          <div style="margin: 10px 0; display: flex;justify-content: space-between;">
          <span>
          <span style="border-radius: 50%;background-color: ${params[0].color};display: inline-block;width: 10px; height: 10px; line-height: 1;"></span>
          本月归集费用
          </span> <span>${data.rdFunds ? this.transFormData(data.rdFunds) : 0}万元</span>
          </div>
          <div style="margin: 10px 0; display: flex;justify-content: space-between;">
          <span>
          <span style="display: inline-block;width: 6px; height: 6px; line-height: 1;"></span>
          累计归集费用
          </span>
          <span>${data.totalRdFunds ? this.transFormData(data.totalRdFunds) : 0}万元</span>
          </div>
          <div style="margin: 10px 0; display: flex;justify-content: space-between;">
          <span>
          <span style="display: inline-block;width: 6px; height: 6px; line-height: 1;"></span>
          已支出额度比
          </span>
          <span>${data.allocations ? (Number(data.allocations) * 100).toFixed(0) : 0}%</span>
          </div>
        </div>
        `
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          top: '3%',
          containLabel: true
        },
        series: [{
          name: '本月归集费用',
          data: [],
          type: 'line',
          // areaStyle: {},
          lineStyle: {
            width: 2,
            shadowColor: 'rgba(0, 0, 0, 0.2)',
            shadowBlur: 10,
            shadowOffsetY: 5
          },
          smooth: true
        }]
      },
      pieOption: {
        color: ['#3AA1FF', '#78C4F8', '#59D9B4', '#66D487', '#E6DA46', '#EBAA6E', '#6DB486', '#51609B', '#8990E2', '#3AC9CD', '#84E0E5', '#6C9AE3', '#6C6DE2', '#BE94F3', '#ED729C', '#F98A9C', '#DBA7DF'],
        tooltip: {
          trigger: 'item',
          textStyle: {
            fontSize: 12
          },
          formatter: (params, ticket, callback) => {
            const hcode = params.name
            const data = this.highModels.find(item => {
              return item.hcode === hcode
            })
            if (!data) {
              return
            }
            return `
            <div style="width: 225px;padding: 5px;">
            <div style="padding: 5px 0; font-weight: 650;">${data.hcode}</div>
            <div style="margin: 10px 0; display: flex;justify-content: flex-start;">
            <span>
            <span style="display: inline-block;width: 6px; height: 6px; line-height: 1;"></span>
              ${data.hname}
            </span>
            </div>
            <div style="margin: 10px 0; display: flex;justify-content: space-between;">
            <span>
            <span style="border-radius: 50%;background-color: ${params.color};display: inline-block;width: 10px; height: 10px; line-height: 1;"></span>
            收入
            </span> <span>${data.income ? Math.round(((data.income || 0) / 10000) * 100) / 100 : 0}万元</span>
            </div>
            <div style="margin: 10px 0; display: flex;justify-content: space-between;">
            <span>
            <span style="display: inline-block;width: 6px; height: 6px; line-height: 1;"></span>
            占年收入
            </span>
            <span>${data.income ? ((data.income / data.totalIncome) * 100).toFixed(2) : 0}%</span>
            </div>
        </div>
            `
          }
        },
        legend: {
          orient: 'horizontal',
          top: 'top',
          icon: 'circle',
          left: 'center',
          itemWidth: 12,
          itemHeight: 12,
          textStyle: {
            height: 13,
            fontSize: 12,
            lineHeight: 13
          }
        },
        grid: {
          left: 0,
          right: 0,
          bottom: 0,
          top: '3%',
          containLabel: true
        },
        series: [
          {
            type: 'pie',
            radius: ['30%', '60%'],
            avoidLabelOverlap: true,
            center: ['50%', '55%'],

            itemStyle: {
              borderRadius: 5,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: true,
              position: 'outside',
              fontSize: 12,
              fontWeight: 650,
              formatter: '{b}\n{d}%'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '16',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: true
            },
            data: []
          }
        ]
      }
    }
  },
  watch: {
    highModels: {
      deep: true,
      handler (v) {
        this.loadData(v)
      }
    },
    countBudgetModels: {
      deep: true,
      handler (v) {
        this.loadLineData(v)
      }
    },
    toggleLeftExpend: {
      handler (v) {
        this.$nextTick(() => {
          setTimeout(() => {
            this.highPieChart.resize()
            this.budgetCategoryChart.resize()
          }, 150)
        })
      }
    }
  },
  mounted () {
    const highPieDom = document.getElementById('highPie')
    const budgetCategoryDom = document.getElementById('budgetCategory')
    this.highPieChart = echarts.init(highPieDom)
    this.budgetCategoryChart = echarts.init(budgetCategoryDom)
    window.addEventListener('resize', () => {
      this.highPieChart.resize()
      this.budgetCategoryChart.resize()
    })
  },
  computed: {
    ...mapGetters(['currentYear', 'toggleLeftExpend']),
    totalBuget () {
      let total = 0
      if (this.countBudgetModels && this.countBudgetModels[0] && this.countBudgetModels[0].budget) {
        total = this.transFormData(this.countBudgetModels[0].budget)
      } else {
        total = '-'
      }
      return total
    }
  },
  methods: {
    loadData (data) {
      const result = []
      if (!(data && data.length)) {
        result.push({ name: '', value: 0 })
      } else {
        data.forEach((item) => {
          result.push({ name: item.hcode, value: this.transFormData(item.income) })
        })
      }
      this.pieOption.series[0].data = result
      this.pieOption.series[0].data.length && this.highPieChart.setOption(this.pieOption)
      this.highPieChart.resize()
    },
    loadLineData (data) {
      const xAxisData = []
      const yAxisData = []
      if (!(data && data.length)) {
        for (let i = 1; i <= 12; i++) {
          xAxisData.push(`${this.currentYear}-${this.PrefixZero(i, 2)}`)
          yAxisData.push(0)
        }
      } else {
        data.forEach(item => {
          xAxisData.push(item.monthStr)
          // yAxisData.push(this.transFormData(item.rdFunds))
          yAxisData.push((item.allocations * 100).toFixed(0))
          // yAxisData.push(Math.round((item.allocations || 0) * 100) / 100)
        })
      }
      this.categoryOption.xAxis.data = xAxisData
      this.categoryOption.series[0].data = yAxisData
      this.categoryOption.series[0].data.length && this.budgetCategoryChart.setOption(this.categoryOption)
      this.budgetCategoryChart.resize()
    },
    transFormData (value) {
      return Math.round(((value || 0) / 10000) * 100) / 100
    },
    PrefixZero (num, n) {
      return (Array(n).join(0) + num).slice(-n)
    }
  }
}
</script>

<style lang="less" scoped>
.myCard {
  border-radius: 6px;
  & /deep/ .vxe-header--row {
    background-color: #F9FAFB;
  }
  .card_title {
    font-size: 16px;
    font-weight: 650;
  }
}
</style>
