
<template>
  <div style="height: 100%;padding: 12px;">
    <div v-if="!($auth('index:total') || $auth('index:month') || $auth('index:district') || $auth('index:branch'))">
      <page-view
        :avatar="avatar"
        :title="false"
      >
        <div slot="headerContent">
          <div class="title">{{ timeFix }}，{{ user.realName }}</div>
        </div>
      </page-view>
    </div>
    <div style="height: 100%">
      <a-row
        :gutter="[8,8]"
        v-if="$auth('index:total')"
      >
        <a-col :span="8">
          <div style="display:flex; background-color: #fff; width: 100%; padding: 6px;">
            <div style="width: 40%">
              <a-icon
                type="cloud"
                theme="twoTone"
                style="width: 100%; height: 100%; fontSize: 85px;"
              />
            </div>
            <div style="width: 100%; text-align:right; padding-right: 10px;">
              <div>
                <span style="font-size: 22px">客户数</span>
              </div>
              <div>
                <b style="font-size: 22px">{{ summaryCount.customerCount }}</b>
              </div>
            </div>
          </div>
        </a-col>
        <a-col :span="8">
          <div style="display:flex; background-color: #fff; width: 100%; padding: 6px;">
            <div style="width: 40%">
              <a-icon
                type="profile"
                theme="twoTone"
                style="width: 100%; height: 100%; fontSize: 85px;"
              />
            </div>
            <div style="width: 100%; text-align:right; padding-right: 10px;">
              <div>
                <span style="font-size: 22px">研发立项数</span>
              </div>
              <div>
                <b style="font-size: 22px">{{ summaryCount.rdCount }}</b>
              </div>
            </div>
          </div>
        </a-col>
        <a-col :span="8">
          <div style="display:flex; background-color: #fff; width: 100%; padding: 6px;">
            <div style="width: 40%">
              <a-icon
                type="money-collect"
                theme="twoTone"
                style="width: 100%; height: 100%; fontSize: 85px;"
              />
            </div>
            <div style="width: 100%; text-align:right; padding-right: 10px;">
              <div>
                <span style="font-size: 22px">研发费用(万元)</span>
              </div>
              <div>
                <b style="font-size: 22px">{{ toThousands(summaryCount.rdFundsSum) }}</b>
              </div>
            </div>
          </div>
        </a-col>
      </a-row>
      <a-row :gutter="[8,8]">
        <a-col
          :span="16"
          v-if="$auth('index:month')"
        >
          <a-card :bodyStyle="{padding: '12px 12px'}">
            <div class="clearfix">
              <span>
                <b>月度研发费用(万元)</b>
              </span>
              <div style="float: right;">
                <year-select
                  style="width: 110px "
                  placeholder="请选择年份"
                  :value="lineYear"
                  @change="val => selectChange(val, 'line')"
                  :allowClear="false"
                ></year-select>
              </div>
            </div>
            <ve-line
              ref="veLine"
              :grid="grid"
              :extend="extend"
              :colors="viserInfographicThem"
              :data="lineData"
              :settings="lineSettings"
              height="30vh"
            ></ve-line>
          </a-card>
        </a-col>
        <a-col
          :span="8"
          v-if="$auth('index:district')"
        >
          <a-card
            :bordered="true"
            :bodyStyle="{padding: '12px 12px', dsiplay: 'flex'}"
          >
            <div class="clearfix">
              <span>
                <b>区域研发费用(万元)</b>
              </span>
              <div style="float: right;">
                <year-select
                  style="width: 110px "
                  placeholder="请选择年份"
                  :value="pieYear"
                  @change="val => selectChange(val, 'pie')"
                  :allowClear="false"
                ></year-select>
              </div>
            </div>
            <ve-pie
              ref="vePie"
              :data-empty="peiDataEmpty"
              :colors="viserInfographicThem"
              height="30vh"
              :data="pieData"
              :settings="pieSettings"
            ></ve-pie>
          </a-card>
        </a-col>
      </a-row>
      <a-row
        :gutter="[8,8]"
        v-if="$auth('index:branch')"
      >
        <a-col :span="24">
          <a-card :bodyStyle="{padding: '12px 12px'}">
            <div class="clearfix">
              <span>
                <b>子公司汇总</b>
              </span>
              <div style="float: right;">
                <year-select
                  style="width: 110px "
                  placeholder="请选择年份"
                  :value="histogramYear"
                  @change=" val => selectChange(val, 'histogram')"
                  :allowClear="false"
                ></year-select>
              </div>
            </div>
            <ve-histogram
              :grid="grid"
              height="30vh"
              :colors="colorsH"
              ref="veHistogram"
              :extend="histogramExtend"
              :settings="histogramSettings"
              :data="histogramData"
            ></ve-histogram>
          </a-card>
        </a-col>
      </a-row>
      <!-- <a-row
        :gutter="[8,8]"
      >
        <a-col :span="24">
          <a-card :bodyStyle="{padding: '12px 12px'}">
            <div class="clearfix">
              <span>
                <b>未归集项目</b>
              </span>
            </div>
            <ystable
              queryUrl="/projectUnCollected/getList"
              :params="queryParam"
              highlight-hover-row
              show-overflow
              resizable
              ref="table"
              :toolbar="tableToolbar"
              size="small"
            >
              <vxe-table-column type="seq" title="序号" fixed="left"></vxe-table-column>
              <vxe-table-column field="rdTitle" title="RD" align="left" remoteSort></vxe-table-column>
              <vxe-table-column field="pname" title="项目名称" min-width="200" align="left" remoteSort></vxe-table-column>
              <vxe-table-column field="customerName" title="客户名称" min-width="200" align="left" remoteSort></vxe-table-column>
              <vxe-table-column field="year" title="年份" width="160" align="center" remoteSort></vxe-table-column>
              <vxe-table-column field="createTime" title="创建时间" width="160" align="center" remoteSort></vxe-table-column>
            </ystable>
          </a-card>
        </a-col>
      </a-row> -->
    </div>
  </div>
</template>
<script>
import ystable from '@/components/Table/ystable'
import { PageView } from '@/layouts'
import { timeFix } from '@/utils/util'
import YearSelect from '@/components/Selects/YearSelect'
import HeadInfo from '@/components/tools/HeadInfo'
// const colorsH = ['#399FFE', '#4ECB73', '#F5D03C']
const colorsH = ['#399FFE', '#4ECB73', '#84E0E5']
const viserInfographicThem = ['#3AA1FF', '#59D9B4', '#78C4F8', '#66D487', '#E6DA46', '#EBAA6E', '#6DB486', '#51609B', '#8990E2', '#3AC9CD', '#84E0E5', '#6C9AE3', '#6C6DE2', '#BE94F3', '#ED729C', '#F98A9C', '#DBA7DF']

const histogramSettings = {
  label: {
    show: true,
    position: 'top'
  }
}
const lineSettings = {
  label: {
    normal: {
      // show: true
    }
  },
  labelMap: {
    month: '月份',
    salary: '工资',
    insurance: '五险一金',
    prod: '设备折旧',
    lab: '仪器折旧',
    material: '材料',
    power: '动力',
    fuel: '燃料',
    trial: '试制',
    repair: '修理',
    inspection: '检测',
    sampleMachine: '样机',
    design: '设计',
    amortization: '摊销',
    travel: '差旅费',
    other: '其他'
  }
}
const pieSettings = {
  radius: 70,
  // roseType: 'area',
  offsetY: 165,
  labelLine: {
    // smooth: true
    // opacity: 0
  },
  label: {
    normal: {
      show: true
    }
  }
}
const histogramExtend = {
  series (v) {
    if (Array.isArray(v)) {
      v.forEach(i => {
        i.barWidth = 12
      })
    }
    return v
  },
  'xAxis.0.axisLabel.rotate': 25,
  'xAxis.0.axisLabel.interval': 0
}
const grid = {
  bottom: 0
}
const extend = {
  'xAxis.0.axisLabel.rotate': 25,
  'xAxis.0.axisLabel.interval': 0
}
export default {
  name: 'Workplace',
  components: {
    ystable,
    YearSelect,
    HeadInfo,
    PageView
  },
  created () {
    this.user = this.$store.getters.userInfo
    this.avatar = this.user.avatar
  },
  mounted () {
    this.refreshData()
  },
  updated () {
    this.resetCharts()
  },
  destoryed () {
    clearInterval(this.timeId)
    // if (this.timeId) {
    //   clearInterval(this.timeId)
    // }
  },
  watch: {
    '$route' (to, from) {
      if (to.name === 'Workplace') {
        this.refreshData()
      }
    }
  },
  computed: {
    // userInfo () {
    //   return this.$store.getters.userInfo
    // }
  },
  data () {
    return {
      timeFix: timeFix(),
      grid,
      user: {},
      avatar: '',
      peiDataEmpty: false,
      histogramExtend,
      colorsH,
      viserInfographicThem,
      lineYear: 0,
      pieYear: 0,
      histogramYear: 0,
      summaryCount: {},
      loading: true,
      histogramSettings,
      lineSettings,
      pieSettings,
      extend,
      pieData: [],
      histogramData: [],
      lineData: [],
      tableToolbar: {
        refresh: true,
        zoom: true,
        custom: true
      }
      // chartData
    }
  },
  methods: {
    selectChange (val, chartName) {
      switch (chartName) {
        case 'line':
          this.lineYear = val
          this.getLineData(val)
          break
        case 'pie':
          this.pieYear = val
          this.getPieData(val)
          break
        case 'histogram':
          this.histogramYear = val
          this.getHistogramData(val)
          break
        default:
          break
      }
    },
    // 饼状图数据 获取各部门区域的年度费用
    getPieData (year) {
      this.$http.get('/summary/getDistrictSummary', { params: { year } }).then(
        res => {
          // 饼状图数据处理
          const cds = res.data
          if (cds.length === 0) {
            this.peiDataEmpty = true
            return
          }
          this.peiDataEmpty = false
          this.pieData = []
          const pieChartRows = []
          cds.forEach(item => {
            pieChartRows.push({ '区域部门名称': item['title'], '归集额': item['value'] })
          })
          this.pieData.columns = ['区域部门名称', '归集额']
          this.pieData.rows = pieChartRows
        })
    },
    // 折线图数据 获取研发费用
    getLineData (year) {
      this.$http.get('/summary/getSummaryCost', { params: { year } }).then(
        res => {
          // 折线图数据处理
          const cds = res.data
          this.lineData = []
          // const colsMap = { 'salary': '工资', 'insurance': '五险一金', 'prod': '设备折旧', 'lab': '仪器折旧', 'material': '材料', 'power': '动力', 'fuel': '燃料', 'trial': '试制', 'repair': '修理', 'inspection': '检测', 'design': '设计', 'amortization': '摊销', 'travel': '差旅费', 'other': '其他' }
          const emptyRow = { 'salary': 0, 'insurance': 0, 'material': 0, 'power': 0, 'fuel': 0, 'trial': 0, 'inspection': 0, 'repair': 0, 'sampleMachine': 0, 'prod': 0, 'lab': 0, 'amortization': 0, 'design': 0, 'travel': 0, 'other': 0 }
          this.lineData.columns = ['month', 'salary', 'insurance', 'material', 'power', 'fuel', 'trial', 'inspection', 'repair', 'sampleMachine', 'prod', 'lab', 'amortization', 'design', 'travel', 'other']
          this.lineData.rows = []
          if (!cds) {
            // 数据为空
            for (let i = 1; i <= 12; i++) {
              const tempRow = Object.assign({ 'month': `${this.lineYear}-${this.PrefixZero(i, 2)}` }, emptyRow)
              this.lineData.rows.push(tempRow)
            }
          } else {
            if (cds.length < 12) {
            // 数据不为空，并且不足12个月
              cds.forEach(item => {
                const tempRow = Object.assign({ 'month': item.month }, emptyRow)
                for (const key in item.dataMap) {
                  tempRow[key] = item.dataMap[key]
                }
                this.lineData.rows.push(tempRow)
              })

              const sortRow = []
              for (let i = 1; i <= 12; i++) {
                const yearMonth = `${this.lineYear}-${this.PrefixZero(i, 2)}`
                this.lineData.rows.forEach(item => {
                  if (item['month'] === yearMonth) {
                    sortRow.push(item)
                  }
                })
              }
              this.lineData.rows = sortRow
              const tempObj = {}
              this.lineData.rows.forEach(item => {
                tempObj[item['month']] = item['month']
                tempObj[item['month'] + 'data'] = item
              })
              this.lineData.rows = []
              for (let i = 1; i <= 12; i++) {
                const yearMonth = `${this.lineYear}-${this.PrefixZero(i, 2)}`
                if (tempObj[yearMonth]) {
                  this.lineData.rows.push(tempObj[yearMonth + 'data'])
                } else {
                  const tempRow = Object.assign({ 'month': yearMonth }, emptyRow)
                  this.lineData.rows.push(tempRow)
                }
              }
            } else {
              // 数据不为空，并且满足12个月
              cds.forEach(item => {
                const tempRow = Object.assign({ 'month': item.month }, emptyRow)
                for (const key in item.dataMap) {
                  tempRow[key] = item.dataMap[key]
                }
                this.lineData.rows.push(tempRow)
              })
              const sortRow = []
              for (let i = 1; i <= 12; i++) {
                this.lineData.rows.forEach(item => {
                  const yearMonth = `${this.lineYear}-${this.PrefixZero(i, 2)}`
                  if (item['month'] === yearMonth) {
                    sortRow.push(item)
                  }
                })
              }
              this.lineData.rows = sortRow
            }
          }
        })
    },
    // 柱形图数据 获取分公司客户数、年项目数、年归集额
    getHistogramData (year) {
      this.$http.get('/summary/getBranchTotal', { params: { year } }).then(
        res => {
          // 柱状图数据处理
          const cds = res.data
          this.histogramData = []
          const histogramRows = []
          cds.forEach(item => {
            const tempRow = {}
            item.map(subItem => {
              tempRow[subItem['title']] = subItem['value']
            })
            histogramRows.push(tempRow)
          })
          this.histogramData.columns = ['分公司', '客户数', '立项数', '研发费用(万元)']
          this.histogramData.rows = histogramRows
        })
    },
    // 重置图表尺寸
    resetCharts () {
      if (this.$auth('index:month')) {
        this.$refs.veLine.echarts.resize()
      }
      if (this.$auth('index:district')) {
        this.$refs.vePie.echarts.resize()
      }
      if (this.$auth('index:branch')) {
        this.$refs.veHistogram.echarts.resize()
      }
    },
    // 位数不足前面补零
    PrefixZero (num, n) {
      return (Array(n).join(0) + num).slice(-n)
    },
    // 千分符号
    toThousands (num) {
      if (!num) {
        return
      }
      if (Number(num) < 1000) {
        return num
      }
      let decimal = 0
      if (num.toString().indexOf('.') !== -1) {
        const ary = num.toString().split('.')
        num = ary[0]
        decimal = ary[1]
      }
      var result = ''; var counter = 0
      num = (num || 0).toString()
      for (var i = num.length - 1; i >= 0; i--) {
        counter++
        result = num.charAt(i) + result
        if (!(counter % 3) && i !== 0) { result = ',' + result }
      }
      if (decimal > 0) {
        return `${result}.${decimal}`
      } else {
        return result
      }
    },
    refreshData () {
      this.histogramYear = this.lineYear = this.pieYear = new Date().getFullYear()
      if (this.timeId) {
        clearInterval(this.timeId)
      } else {
      // this.timeId = setInterval(() => {
      //   this.histogramData.rows[4]['访问用户'] = this.histogramData.rows[4]['访问用户'] + 100
      // }, 5000)
      }
      this.$nextTick(() => {
      // this.resetCharts()
        this.$forceUpdate()
      })
      // 获取 客户数、立项数、归集数 汇总数
      if (this.$auth('index:total')) {
        this.$http.get('/summary/getCustomerTotal').then(
          res => {
            this.summaryCount = res.data
          })
      }
      if (this.$auth('index:month')) {
        this.getLineData(this.lineYear)
      }
      if (this.$auth('index:district')) {
        this.getPieData(this.pieYear)
      }
      if (this.$auth('index:branch')) {
        this.getHistogramData(this.histogramYear)
      }
    }
  }
}
</script>

<style lang="less" scoped>
#customerCard /deep/ .ant-card-head-title,
#rdFundCard /deep/ .ant-card-head-title,
#rdCard /deep/ .ant-card-head-title {
  padding: 8px 0;
}
.clearfix {
  *zoom: 1;
}
.clearfix:after {
  display: bolck;
  content: '';
  clear: both;
  visibility: hidden;
  height: 0;
}
.chartsContent {
  height: 100%;
}
</style>
