<!--
 * @Author: ldx
 * @Date: 2021-03-15 10:40:43
 * @LastEditTime: 2021-08-13 15:15:19
 * @LastEditors: ldx
 * @Description: 研发支出辅助帐及汇总表
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\ProjectSummary.vue
-->
<template>
  <div style="height: 100%;">
    <vxe-grid
      ref="table"
      id="ProjectSummary"
      :data="tableDatas"
      :columns="columns"
      border
      resizable
      auto-resize
      highlight-hover-row
      show-overflow
      show-header-overflow
      highlight-current-row
      height="100%"
    >
      <vxe-table-column
        field="monthStr"
        title="月份"
        width="100"
        align="center"
        header-align="center"
        fixed="left"
      >
      </vxe-table-column>
      <vxe-table-column
        v-for="item in costTypes"
        :key="item.value"
        :field="`${item.type}`"
        :title="item.title"
        width="90"
        align="right"
        header-align="center"
      >
        <template v-slot="{row,column}">
          {{ row[column.property]? row[column.property]: '-' }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="colTotal"
        title="合计"
        width="120"
        align="right"
        fixed="right"
        header-align="center"
      >
      </vxe-table-column>
    </vxe-grid>
  </div>
</template>

<script>
import { getProjectSummary } from '@/api/backupDataTab'
import moment from 'moment'
export default {
  name: 'ProjectSummary',
  props: {
    projectId: {
      type: Number,
      default: undefined
    },
    record: {
      type: Object,
      required: true
    }
  },
  watch: {
    projectId (val) {
      this.handleGetProjectSummary()
    }
  },
  mounted () {
    this.costTypes = this.$getCostTypes()
    this.handleGetProjectSummary()
  },
  data () {
    return {
      tableDatas: [],
      columns: [],
      costTypes: []
    }
  },
  methods: {
    moment,
    handleGetProjectSummary () {
      this.$emit('loading', true)
      const params = { year: this.record.year, projectId: this.projectId }
      getProjectSummary(params).then((response) => {
        if (response.success && response.data) {
          this.tableDatas = []
          const obj = {}
          const totalRow = { monthStr: '合计' }
          response.data.forEach((item) => {
            const monthStr = moment(item.month).format('YYYY年MM月')
            item['monthStr'] = monthStr
            obj[monthStr] = item
            const types = [...this.costTypes]
            types.push({ type: 'colTotal' })
            types.forEach((costType) => {
              totalRow[costType.type] = ((totalRow[costType.type] ? totalRow[costType.type] : 0) * 1000 + (item[costType.type] ? item[costType.type] : 0) * 1000) / 1000
            })
          })
          for (let i = 1; i <= 12; i++) {
            const key = `${this.record.year}年${i > 9 ? i : '0' + i}月`
            if (obj[key]) {
              this.tableDatas.push(obj[key])
            } else {
              const newRow = { monthStr: key }
              this.tableDatas.push(newRow)
            }
          }
          this.tableDatas.push(totalRow)
        } else {
          this.tableDatas = []
        }
      }).catch((error) => {
        this.tableDatas = []
        console.log('handleGetProjectSummary function error', error)
      }).finally(() => {
        this.$emit('loading', false)
      })
    }
  }
}
</script>

<style>

</style>
