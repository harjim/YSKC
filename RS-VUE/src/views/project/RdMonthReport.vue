<!--
 * @Author: lzh
 * @Date: 2022-01-19 15:32:46
 * @LastEditors: lzh
 * @LastEditTime: 2022-01-24 14:12:12
 * @Description: 研发月报表
 * @FilePath: \RS-VUE\src\views\project\RdMonthReport.vue
-->
<template>
  <a-card :bordered="false">
    <vxe-grid v-bind="gridOptions">
      <template #toolbar_tools>
        <span style="margin-right: 8px;">单位：万元/个/%</span>
      </template>
    </vxe-grid>
  </a-card>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
export default {
  mixins: [yearMiXin],
  data () {
    return {
      gridOptions: {
        resizable: true,
        highlightHoverRow: true,
        highlightCurrentRow: true,
        border: true,
        stripe: true,
        align: 'right',
        headerAlign: 'center',
        loading: false,
        toolbar: {
          refresh: {
            query: this.loadData
          },
          custom: true,
          zoom: true,
          slots: {
            tools: 'toolbar_tools'
          }
        },
        columns: [
          { field: 'month', title: '月份', width: 120, fixed: 'left', align: 'center' },
          { field: 'revenue', title: '本月营收', width: 120, formatter: this.formatterNull },
          { field: 'm_revenue', title: '本年累计营收', width: 120, formatter: this.formatterNull },
          { field: 'amount', title: '本月实际归集研发费', width: 120, formatter: this.formatterNull },
          { field: 'm_amount', title: '本年累计实际归集研发费', width: 120, formatter: this.formatterNull },
          { field: 'material', title: '本月归集原材料费', width: 120, formatter: this.formatterNull },
          { field: 'm_material', title: '本年累计归集原材料费', width: 120, formatter: this.formatterNull },
          { field: 'exAmount', title: '本年累计预计可加计扣除研发费', width: 120, formatter: this.formatterNull },
          { field: 'amountIRevenue', title: '研发费总额/累计营收入', width: 120, formatter: this.formatterNull },
          { field: 'exAmountIRevenue', title: '可加计扣除研发费/总营收', width: 120, formatter: this.formatterNull },
          { field: 'amountIncrease', title: '研发费同期比%', width: 120, formatter: this.formatterNull },
          { field: 'mAmountIcnrease', title: '本年研发费累计与上年数比%', width: 120, formatter: this.formatterNull }
        ],
        data: []
      }
    }
  },
  watch: {
    currentYear: {
      immediate: true,
      handler () {
        this.loadData()
      }
    }
  },
  methods: {
    loadData () {
      const params = { year: this.currentYear }
      this.gridOptions.loading = true
      this.$http
        .get('/reportForm/getMonthReport', { params })
        .then(res => {
          if (res.success && res.data) {
            const data = [...Array(12).keys()].map(m => {
              const param = res.data.find(item => item.month === m)
              if (param) {
                return { ...param, month: `${m + 1}月` }
              }
              return { month: `${m + 1}月` }
            })
            this.gridOptions.data = data
          } else {
            this.$message.error(res.errorMessage || '获取数据失败')
          }
        })
        .catch(() => {
          this.$message.error('获取数据失败')
        })
        .finally(() => {
          this.gridOptions.loading = false
        })
    },
    formatterNull ({ cellValue }) {
      return cellValue == null ? '--' : cellValue
    }
  }
}
</script>
