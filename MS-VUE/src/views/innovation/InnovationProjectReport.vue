<!--
 * @Author: lzh
 * @Date: 2022-01-19 15:35:28
 * @LastEditors: Please set LastEditors
 * @LastEditTime: 2022-04-18 09:34:52
 * @Description: 创新项目报表
 * @FilePath: \MS-VUE\src\views\innovation\InnovationProjectReport.vue
-->
<template>
  <a-card v-if="$auth('innovation:innovationProjectReport:getData')">
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input placeholder="请输入客户名称" v-model="queryParams.companyName" />
      </a-form-item>
      <a-form-item label="所属部门">
        <dept-select style="width:165px;" v-model="queryParams.deptId" />
      </a-form-item>
      <a-form-item label="业务员">
        <search-select
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="false"
          placeholder="请输入业务员"
          style="width:165px;"
          v-model="queryParams.ownerId"
        />
      </a-form-item>
      <a-form-item label="技术人员">
        <search-select
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="true"
          placeholder="技术人员"
          style="width:165px;"
          v-model="queryParams.tIds"
        />
      </a-form-item>
      <a-form-item label="财务人员">
        <search-select
          style="width:165px;"
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="true"
          placeholder="财务人员"
          v-model="queryParams.fIds"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="query">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      :columns="columns"
      :params="getQueryParams()"
      queryUrl="/innovationProject/getTableData"
      :toolbar="{ custom: true, zoom: true, refresh: true, slots: { buttons: 'toolbar_buttons', tools: 'toolbar_tools' } }"
      stripe
      highlight-hover-row
      resizable
      border
      align="right"
      header-align="center"
      show-footer
      :footer-method="footerMethod"
      footer-align="right"
      @completed="completed"
    >
      <template #toolbar_buttons>
        <a-form layout="inline">
          <a-form-item label="月份">
            <a-month-picker
              v-model="queryParams.month"
              :defaultValue="defaultMonthValue"
              valueFormat="YYYY-MM-01 00:00:00"
              :allowClear="false"
              @change="onChangeMonth">
            </a-month-picker>
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              v-if="$auth('innovation:innovationProjectReport:export')"
              :loading="btnLoading"
              @click="exportData">导出</a-button>
          </a-form-item>
        </a-form>
      </template>
      <template #toolbar_tools>
        <span style="margin-right: 8px;">单位：万元/个/%</span>
      </template>
    </ystable>
  </a-card>
</template>

<script>
import moment from 'moment'
import ystable from '@/components/Table/ystable'
import { SearchSelect, DeptSelect } from '@/components/Selects'
export default {
  components: {
    ystable,
    SearchSelect,
    DeptSelect
  },
  data () {
    return {
      columns: [
        { field: 'deptName', title: '客户名称', width: 150, align: 'left', headerAlign: 'center', fixed: 'left', formatter: this.formatNull },
        { field: 'year', title: '年份', width: 150, align: 'left', headerAlign: 'center', formatter: this.formatNull },
        { field: 'deptName2', title: '所属部门', width: 150, align: 'left', headerAlign: 'center', formatter: this.formatNull },
        { field: 'owerName', title: '业务员', width: 150, align: 'left', headerAlign: 'center', formatter: this.formatNull },
        { field: 'techRealName', title: '技术人员', width: 150, align: 'left', headerAlign: 'center', formatter: this.formatNull },
        { field: 'financeRealName', title: '财务人员', width: 150, align: 'left', headerAlign: 'center', formatter: this.formatNull },
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
            { field: 'material', title: '本月归集原材料费', width: 110, formatter: this.formatterDecimal },
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
            { field: 'lastRdCnt', title: '上年跨本年立项数', width: 140, formatter: this.formatNull },
            { field: 'nextRdCnt', title: '本年跨次年立项数', width: 140, formatter: this.formatNull },
            { field: 'docFileCount', title: '轨迹数量', width: 120, formatter: this.formatNull },
            { field: 'buildCount', title: '研发机构文件', width: 120, formatter: this.formatNull },
            { field: 'patentCnt', title: '知识产权数量', width: 120, formatter: this.formatNull },
            { field: 'achievementCnt', title: '成果项数', width: 120, formatter: this.formatNull },
            { field: 'levelFileCnt', title: '多层研发文件数', width: 120, formatter: this.formatNull }
          ]
        }
      ],
      queryParams: { month: undefined },
      defaultMonthValue: undefined,
      btnLoading: false,
      control: {
        export: this.$auth('innovation:innovationProjectReport:export')
      }
    }
  },
  created () {
    this.queryParams.month = this.defaultMonthValue = moment().format('YYYY-MM-01 00:00:00')
  },
  methods: {
    query () {
      this.$refs['table'].refresh()
    },
    exportData () {
      this.btnLoading = true
      this.$exportData('/innovationProject/export', this.getQueryParams(),
        `创新项目报表.xls`, this.$message).then(res => {
        this.btnLoading = false
      })
    },
    onChangeMonth () {
      this.$nextTick(() => {
        this.query()
      })
    },
    getQueryParams () {
      const params = Object.assign({}, this.queryParams)
      if (params.tIds) { params.tIds = params.tIds.map(a => a.id) }
      if (params.fIds) { params.fIds = params.fIds.map(a => a.id) }
      if (params.ownerId && params.ownerId.id) {
        params.ownerId = params.ownerId.id
      }
      return params
    },
    formatNull ({ cellValue }) {
      return cellValue == null ? '--' : cellValue
    },
    formatterDecimal ({ cellValue }) {
      return cellValue == null ? '--' : cellValue.toFixed(2)
    },
    completed (data) {
      this.footer = data.data.footer
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
