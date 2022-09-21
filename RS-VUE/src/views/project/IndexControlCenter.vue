<!--
 * @Author: lzh
 * @Date: 2022-01-19 15:32:46
 * @LastEditors: lzh
 * @LastEditTime: 2022-01-24 17:25:58
 * @Description: 指标控管中心
 * @FilePath: \RS-VUE\src\views\project\IndexControlCenter.vue
-->
<template>
  <a-card>
    <div style="display: flex; align-items: center; justify-content: space-between;">
      <a-month-picker
        v-model="queryParams.date"
        :disabledDate="setDisabledDate"
        :defaultValue="defaultMonthValue"
        :defaultPickerValue="defaultMonthPickerValue"
        :allowClear="false"
        @change="onMonthChange"></a-month-picker>
      <span>单位：万元/个/%</span>
    </div>
    <a-spin :spinning="spinning">
      <div>
        <a-divider orientation="left">项目总指标</a-divider>
        <vxe-grid v-bind="gridOptions1"></vxe-grid>
        <a-divider orientation="left">研发费归集指标</a-divider>
        <vxe-grid v-bind="gridOptions2"></vxe-grid>
        <a-divider orientation="left">项目管理指标</a-divider>
        <vxe-grid v-bind="gridOptions3"></vxe-grid>
        <a-divider orientation="left">项目占比分析指标</a-divider>
        <vxe-grid v-bind="gridOptions4"></vxe-grid>
        <a-divider orientation="left">成本分析指标</a-divider>
        <vxe-grid v-bind="gridOptions5"></vxe-grid>
        <a-divider orientation="left">项目技术指标</a-divider>
        <vxe-grid v-bind="gridOptions6"></vxe-grid>
        <a-divider orientation="left">企业成本总额</a-divider>
        <vxe-grid v-bind="gridOptions7"></vxe-grid>
      </div>
    </a-spin>
  </a-card>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
import moment from 'moment'
const gridOptions = {
  resizable: true,
  highlightHoverRow: true,
  highlightCurrentRow: true,
  headerAlign: 'center',
  align: 'right',
  border: true,
  data: []
}
const accountTypeMap = [
  { value: 0, text: '成本重分类核算研发费' },
  { value: 1, text: '冲减主营业务成本列支研发费' }
]
export default {
  data () {
    return {
      spinning: false,
      gridOptions1: {
        ...gridOptions,
        columns: [
          { field: 'accountType', title: '研发费用核算标准', width: 133, formatter: ({ cellValue }) => typeof cellValue !== 'number' ? '--' : accountTypeMap.find(item => item.value === cellValue).text },
          { field: 'revenueFcst', title: '本年营收预测', width: 133 },
          { field: 'salesRdFee', title: '业务预测研发费', width: 133 },
          { field: 'rdFee', title: '财务规划研发费', width: 133 },
          { field: 'rdPlanCount', title: '立项规划数', width: 133 },
          { field: 'finaMode', title: '财务处理方式', width: 133 },
          { field: 'employeeAmount', title: '企业总人数', width: 133 },
          { field: 'revenue', title: '本月营收', width: 133 },
          { field: 'm_revenue', title: '本年累计营收', width: 133 }
        ]
      },
      gridOptions2: {
        ...gridOptions,
        columns: [
          { field: 'amount', title: '本月实际归集研发费', width: 133 },
          { field: 'm_amount', title: '本年累计实际归集研发费', width: 133 },
          { field: 'material', title: '本月归集原材料费', width: 133 },
          { field: 'm_material', title: '本年累计归集原材料费', width: 133 },
          { field: 'estimatedCost', title: '本年累计预计可加计扣除研发费', width: 133 },
          { field: 'totalAmountIRevunue', title: '研发费总额/累计营收入', width: 133 },
          { field: 'axAmountIRevunue', title: '可加计扣除研发费/总营收', width: 133 },
          { field: 'amountIncrease', title: '研发费同期比%', width: 133 },
          { field: 'totalAmountIncrease', title: '本年研发费累计与上年数比%', width: 133 }
        ]
      },
      gridOptions3: {
        ...gridOptions,
        columns: [
          { field: 'rdFeeSales', title: '财务规划研发费/业务预测研发费', width: 133 },
          { field: 'amountIRdFee', title: '研发费总额/财务规划研发费', width: 133 },
          { field: 'amountISalesFee', title: '研发费总额/业务预测研发费', width: 133 },
          { field: 'amountIRevenue', title: '研发费总额/累计营收入%', width: 133 },
          { field: 'exAmountIFcst', title: '可加计扣除研发费/业务预测研发费%', width: 133 },
          { field: 'exAmountIRevunue', title: '可加计扣除研发费/总营收%', width: 133 },
          { field: 'materialIRdFunds', title: '原材料费/研发费总额%', width: 133 },
          { field: 'amountIncrease', title: '研发费同期比%', width: 133 },
          { field: 'totalAmountIncrease', title: '本年研发费累计与上年数比%', width: 133 },
          { field: 'member', title: '研发人员/总人数%', width: 133 }
        ]
      },
      gridOptions4: {
        ...gridOptions,
        columns: [
          { field: 'wages', title: '研发人员工资/总研发费%', width: 133 },
          { field: 'insuranceAndFund', title: '五险一金/总研发费%', width: 133 },
          { field: 'material', title: '可加计扣除原材料/总研发费%', width: 133 },
          { field: 'depreciation', title: '折旧费/总研发费%', width: 133 },
          { field: 'power', title: '动力费/总研发费%', width: 133 },
          { field: 'fuel', title: '燃料费/总研发费%', width: 133 },
          { field: 'trial', title: '试制费/总研发费%', width: 133 },
          { field: 'test', title: '检测费/总研发费%', width: 133 },
          { field: 'repair', title: '修理费/总研发费%', width: 133 },
          { field: 'machine', title: '样机费/总研发费%', width: 133 },
          { field: 'design', title: '设计费/总研发费%', width: 133 },
          { field: 'software', title: '软件费/总研发费%', width: 133 },
          { field: 'otherAmortization', title: '其他摊销费/总研发费%', width: 133 },
          // { field: '', title: '房屋折旧费/总研发费%', width: 133 },
          { field: 'other', title: '其他费/总研发费%', width: 133 }
        ]
      },
      gridOptions5: {
        ...gridOptions,
        columns: [
          { field: 'wages', title: '研发人员工资/对应成本总额%', width: 133 },
          { field: 'insuranceAndFund', title: '五险一金/对应成本总额%', width: 133 },
          { field: 'material', title: '可加计扣除原材料/对应成本总额%', width: 133 },
          { field: 'depreciation', title: '折旧费/对应成本总额%', width: 133 },
          { field: 'power', title: '动力费/对应成本总额%', width: 133 },
          { field: 'fuel', title: '燃料费/对应成本总额%', width: 133 },
          { field: 'trial', title: '试制费/对应成本总额%', width: 133 },
          { field: 'test', title: '检测费/对应成本总额%', width: 133 },
          { field: 'repair', title: '修理费/对应成本总额%', width: 133 },
          { field: 'machine', title: '样机费/对应成本总额%', width: 133 },
          { field: 'design', title: '设计费/对应成本总额%', width: 133 },
          { field: 'software', title: '软件费/对应成本总额%', width: 133 },
          { field: 'otherAmortization', title: '其他摊销费/对应成本总额%', width: 133 },
          // { field: '', title: '房屋折旧费/对应成本总额%', width: 133 },
          { field: 'name_14', title: '其他费/对应成本总额%', width: 133 }
        ]
      },
      gridOptions6: {
        ...gridOptions,
        columns: [
          { field: 'proposalCnt', title: '本年提案数', width: 133 },
          { field: 'rdCount', title: '实际研发立项数', width: 133 },
          { field: 'rdEmployeeCount', title: '研发人员', width: 133 },
          { field: 'lastRdCnt', title: '上年跨本年立项数', width: 133 },
          { field: 'nextRdCnt', title: '本年跨次年立项数', width: 133 },
          { field: 'docFileCount', title: '轨迹数量', width: 133 },
          { field: 'buildCount', title: '研发机构文件', width: 133 },
          { field: 'patentCnt', title: '知识产权数量', width: 133 },
          { field: 'achievementCnt', title: '成果项数', width: 133 },
          { field: 'levelFileCnt', title: '多层研发文件数', width: 133 }
        ]
      },
      gridOptions7: {
        ...gridOptions,
        columns: [
          { field: 'wages', title: '全年工资总额', width: 133 },
          { field: 'insuranceAndFund', title: '全年五险一金总额', width: 133 },
          { field: 'material', title: '全年原材料成本总额', width: 133 },
          { field: 'depreciation', title: '全年折旧费总额', width: 133 },
          { field: 'power', title: '全年动力费总额%', width: 133 },
          { field: 'fuel', title: '全年燃料费总额', width: 133 },
          { field: 'trial', title: '全年度备品件总额', width: 133 },
          { field: 'test', title: '全年检测费对应成本总额', width: 133 },
          { field: 'repair', title: '全年修理费总额', width: 133 },
          { field: 'machine', title: '全年度样机费总额', width: 133 },
          { field: 'design', title: '全年度设计费总额', width: 133 },
          { field: 'software', title: '全年软件费总额', width: 133 },
          { field: 'otherAmortization', title: '全年其他摊销费总额', width: 133 },
          // { field: '', title: '全年房屋折旧费总额', width: 133 },
          { field: 'other', title: '全年其他费用总额', width: 133 }
        ]
      },
      queryParams: {
        date: undefined
      },
      defaultMonthValue: undefined,
      defaultMonthPickerValue: undefined
    }
  },
  mixins: [yearMiXin],
  watch: {
    currentYear: {
      immediate: true,
      handler () {
        this.queryParams.date = this.defaultMonthValue = this.defaultMonthPickerValue = moment([this.currentYear, 0])
        this.loadData()
      }
    }
  },
  methods: {
    onMonthChange () {
      this.loadData()
    },
    loadData () {
      const params = {
        month: moment(this.queryParams.date).format('YYYY-MM-01 00:00:00')
      }
      this.spinning = true
      return this.$http.get('/reportForm/getIndex', { params: params }).then((res) => {
        if (res.success && res.data) {
          const data = {}
          Object.keys(res.data).forEach(key => {
            const innerObj = res.data[key]
            Object.keys(innerObj).forEach(ikey => {
              innerObj[ikey] = innerObj[ikey] == null ? '--' : innerObj[ikey]
            })
            data[key] = innerObj
          })
          this.gridOptions1.data = [data.totalIndex]
          this.gridOptions2.data = [data.rdFundsIndex]
          this.gridOptions3.data = [data.rdManageIndex]
          this.gridOptions4.data = [data.proportIndex]
          this.gridOptions5.data = [data.costIndex]
          this.gridOptions6.data = [data.techIndex]
          this.gridOptions7.data = [data.cost]
        } else {
          this.$message.error(res.errorMessage || '获取数据失败')
        }
      }).catch(() => {
        this.$message.error('获取数据失败')
      }).finally(() => {
        this.spinning = false
      })
    },
    setDisabledDate (current) {
      const start = moment([this.currentYear, 0, 1])
      const end = moment([this.currentYear + 1, 0, 1])
      return !(current >= start && current < end)
    }
  }
}
</script>
