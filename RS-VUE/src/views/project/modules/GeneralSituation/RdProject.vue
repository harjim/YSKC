<!--
 * @Author: ldx
 * @Date: 2021-07-16 16:21:00
 * @LastEditTime: 2021-07-29 15:14:16
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\modules\GeneralSituation\RdProject.vue
-->
<template>
  <a-row :gutter="[12,12]" style="margin-right: 0px; margin-left: 0px;">
    <a-col :span="24" style="padding-left: 0px; padding-right: 0px;">
      <a-card class="myCard" :bodyStyle="threeBodyStyleCard">
        <div style="display: flex; justify-content: space-between; margin-bottom: 14px;">
          <div class="card_title">项目情况(万元)</div>
          <div style="font-size: 12px; text-align:center;">总预算：{{ totalBuget }} 万元</div>
        </div>
        <div id="ProjectCategory" style="height: calc(100% - 30px);"></div>
        <!-- <div style="height: 100%; width: 100%;">
        </div> -->
      </a-card>
    </a-col>
  </a-row>
</template>

<script>
import { mapGetters } from 'vuex'
import * as echarts from 'echarts'
export default {
  name: 'RdProject',
  mounted () {
    const categoryDom = document.getElementById('ProjectCategory')
    this.categoryChart = echarts.init(categoryDom)
    window.addEventListener('resize', () => { this.categoryChart.resize() })
  },
  computed: {
    ...mapGetters(['toggleLeftExpend']),
    totalBuget () {
      let total = 0
      if (this.projectDataModel && this.projectDataModel[0] && this.projectDataModel[0].totalBudget) {
        total = this.transFormData(this.projectDataModel[0].totalBudget)
      } else {
        total = '-'
      }
      return total
    }
  },
  props: {
    projectDataModel: {
      type: Array,
      required: true
    }
  },
  watch: {
    projectDataModel: {
      deep: true,
      handler (v) {
        this.loadData(v)
      }
    },
    toggleLeftExpend: {
      handler (v) {
        this.$nextTick(() => {
          setTimeout(() => {
            this.categoryChart.resize()
          }, 150)
        })
      }
    }
  },
  data () {
    return {
      categoryChart: undefined,
      threeBodyStyleCard: {
        padding: '24px',
        height: '368px'
      },
      projectChartOption: {
        color: ['#3AA1FF', '#78C4F8', '#59D9B4', '#66D487', '#E6DA46', '#EBAA6E', '#6DB486', '#51609B', '#8990E2', '#3AC9CD', '#84E0E5', '#6C9AE3', '#6C6DE2', '#BE94F3', '#ED729C', '#F98A9C', '#DBA7DF'],
        xAxis: {
          type: 'category',
          data: [],
          axisLabel: { interval: 0, rotate: 40 }
        },
        yAxis: {
          type: 'value'
        },
        tooltip: {
          trigger: 'axis',
          textStyle: {
            fontSize: 12
          },
          formatter: (params, ticket, callback) => {
            const rdTitle = params[0].name
            const data = this.projectDataModel.find(item => {
              return item.rdTitle === rdTitle
            })
            if (!data) {
              return
            }
            return `
              <div style="width: 240px;padding: 5px; word-wrap:break-word; word-break:normal; word-break:break-all;">
                <div style="padding: 5px 0; font-weight: 650;">${data.rdTitle}</div>
                <div style=" width: 220px; margin: 10px 0; margin-left: 14px; overflow: hidden; white-space: nowrap; text-overflow:ellipsis;">
                    ${data.pname}
                </div>
                <div style="margin: 10px 0; display: flex;justify-content: space-between;">
                  <span>
                  <span style="border-radius: 50%;background-color: #5B8FF9;display: inline-block;width: 10px; height: 10px; line-height: 1;"></span>
                  归集费用
                  </span> <span>${data.rdFunds ? Math.round(((data.rdFunds || 0) / 10000) * 100) / 100 : 0}万元</span>
                </div>
                <div style="margin: 10px 0; display: flex;justify-content: space-between;">
                  <span>
                  <span style="display: inline-block;width: 6px; height: 6px; line-height: 1;"></span>
                  占总预算
                  </span>
                  <span>${data.percent * 100}%</span>
                </div>
            </div>
                `
          }
        },
        barMaxWidth: '24',
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          top: '3%',
          containLabel: true
        },
        series: [{
          data: [],
          type: 'bar',
          label: {
            show: true,
            position: 'top'
          }
        }]
      }
    }
  },
  methods: {
    loadData (data) {
      const xAxisData = []
      const yAxisData = []
      if (!(data && data.length)) {
        yAxisData.push(0)
        xAxisData.push('')
      }
      data.forEach(item => {
        xAxisData.push(item.rdTitle)
        yAxisData.push(this.transFormData(item.rdFunds))
      })
      this.projectChartOption.xAxis.data = xAxisData
      this.projectChartOption.series[0].data = yAxisData
      this.projectChartOption.series[0].data.length && this.categoryChart.setOption(this.projectChartOption)
      this.categoryChart.resize()
    },
    transFormData (value) {
      return Math.round(((value || 0) / 10000) * 100) / 100
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
</style>
