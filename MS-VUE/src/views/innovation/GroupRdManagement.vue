<!--
 * @Author: lzh
 * @Date: 2022-01-19 15:36:14
 * @LastEditors: lzh
 * @LastEditTime: 2022-01-25 14:02:25
 * @Description: 集团研发管理
 * @FilePath: \MS-VUE\src\views\innovation\GroupRdManagement.vue
-->
<template>
  <a-card v-if="$auth('innovation:groupRdManagement:getData')">
    <vxe-grid v-bind="gridOptions">
      <template #toolbar_buttons>
        <a-form layout="inline">
          <a-form-item label="月份">
            <a-month-picker v-model="queryParams.date" :defaultValue="defaultMonthValue" valueFormat="YYYY-MM-01 00:00:00" :allowClear="false" @change="loadData"></a-month-picker>
          </a-form-item>
        </a-form>
      </template>
      <template #toolbar_tools>
        <span style="margin-right: 8px;">单位：万元/个/%</span>
      </template>
    </vxe-grid>
  </a-card>
</template>

<script>
import moment from 'moment'
export default {
  data () {
    return {
      gridOptions: {
        border: true,
        stripe: true,
        resizable: true,
        headerAlign: 'center',
        align: 'right',
        loading: false,
        toolbar: {
          refresh: {
            query: this.loadData
          },
          custom: true,
          zoom: true,
          slots: {
            buttons: 'toolbar_buttons',
            tools: 'toolbar_tools'
          }
        },
        showFooter: true,
        footerMethod: this.footerMethod,
        columns: [
          { field: 'deptName', title: '分公司', width: 150, align: 'left', headerAlign: 'center', fixed: 'left', formatter: this.formatNull },
          {
            title: '预测指标',
            children: [
              { field: 'revenueFcst', title: '本年营收预测', width: 120, formatter: this.formatterDecimal },
              { field: 'salesRdFee', title: '业务预测研发费', width: 120, formatter: this.formatterDecimal },
              { field: 'rdFee', title: '财务规划研发费', width: 120, formatter: this.formatterDecimal },
              { field: 'rdPlanCount', title: '立项规划数', width: 120, formatter: this.formatNull }
            ]
          },
          {
            title: '研发费归集',
            children: [
              { field: 'amount', title: '本月实际归集研发费', width: 120, formatter: this.formatterDecimal },
              { field: 'amount2', title: '本年累计实际归集研发费', width: 120, formatter: this.formatterDecimal },
              { field: 'material', title: '本月归集原材料费', width: 120, formatter: this.formatterDecimal },
              { field: 'material2', title: '本年累计归集原材料费', width: 120, formatter: this.formatterDecimal },
              { field: 'aMinusK', title: '本年累计预计可加计扣除研发费', width: 120, formatter: this.formatterDecimal },
              { field: 'revenue', title: '本月营收', width: 120, formatter: this.formatterDecimal },
              { field: 'revenue2', title: '本年累计营收', width: 120, formatter: this.formatterDecimal },
              { field: 'aDivideR', title: '研发费总额/累计营收入', width: 120, formatter: this.formatNull },
              { field: 'aKDivideR', title: '可加计扣除研发费/总营收', width: 120, formatter: this.formatNull },
              { field: 'amountTST', title: '研发费同期比%', width: 120, formatter: this.formatNull },
              { field: 'amountCompare', title: '本年研发费累计与上年数比%', width: 120, formatter: this.formatNull },
              { field: 'employeeAmount', title: '企业总人数', width: 120, formatter: this.formatNull }
            ]
          },
          {
            title: '研发项目信息',
            children: [
              { field: 'proposalCnt', title: '本年提案数', width: 120, formatter: this.formatNull },
              { field: 'rdCount', title: '实际研发立项数', width: 120, formatter: this.formatNull },
              { field: 'rdEmployeeCount', title: '研发人员', width: 120, formatter: this.formatNull },
              { field: 'lastRdCnt', title: '上年跨本年立项数', width: 120, formatter: this.formatNull },
              { field: 'nextRdCnt', title: '本年跨次年立项数', width: 120, formatter: this.formatNull },
              { field: 'docFileCount', title: '轨迹数量', width: 120, formatter: this.formatNull },
              { field: 'buildCount', title: '研发机构文件', width: 120, formatter: this.formatNull },
              { field: 'patentCnt', title: '知识产权数量', width: 120, formatter: this.formatNull },
              { field: 'achievementCnt', title: '成果项数', width: 120, formatter: this.formatNull },
              { field: 'levelFileCnt', title: '多层研发文件数', width: 120, formatter: this.formatNull }
            ]
          }
        ],
        data: []
      },
      queryParams: {
        date: undefined
      },
      defaultMonthValue: undefined
    }
  },
  created () {
    this.queryParams.date = this.defaultMonthValue = moment().format('YYYY-MM-01 00:00:00')
    this.loadData()
  },
  methods: {
    loadData () {
      this.gridOptions.loading = true
      this.$http.get('/groupRAndDManagement/getTableData', { params: this.queryParams }).then(res => {
        if (res.success && res.data) {
          this.gridOptions.data = res.data.data
          this.footer = res.data.footer
        } else {
          this.$message.error(res.errorMessage || '获取数据失败')
        }
      }).catch(() => {
        this.$message.error('获取数据失败')
      }).finally(() => {
        this.gridOptions.loading = false
      })
    },
    formatNull ({ cellValue }) {
      return cellValue == null ? '--' : cellValue
    },
    formatterDecimal ({ cellValue }) {
      return cellValue == null ? '--' : cellValue.toFixed(2)
    },
    footerMethod ({ columns, data }) {
      return [
        columns.map((item, index) => {
          if (index === 0) {
            return '合计'
          }
          const fixedList = ['totalRdFee', 'totalAmount', 'totalAmount2', 'totalMaterial', 'totalMaterial2', 'totalAMinusK', 'totalRevenue', 'totalRevenue2']
          const propertyName = `total${item.property.charAt(0).toUpperCase() + item.property.slice(1)}`
          if (fixedList.includes(propertyName)) {
            return (this.footer && this.footer[propertyName].toFixed(2)) || '--'
          }
          return (this.footer && this.footer[propertyName]) || '--'
        })
      ]
    }
  }
}
</script>
