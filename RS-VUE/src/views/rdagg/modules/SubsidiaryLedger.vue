<!--
    * @Author: hm
    * @Date: 2022-08-18 15:43:03
    * @Description:
-->
<template>
  <a-drawer
    :title="`费用归集明细--${title}`"
    placement="right"
    :closable="true"
    :visible="visible"
    @close="onClose"
    width="80vw"
  >
    <a-spin :spinning="spinning">
      <vxe-grid
        :data="tableDatas"
        highlight-hover-row
        show-overflow="tooltip"
        show-footer
        resizable
        auto-resize
        :footer-method="showSummary"
        max-height="95%"
      >
        <vxe-table-column
          show-header-overflow
          show-footer-overflow
          show-overflow="tooltip"
          field="rdTitle"
          title="RD"
          width="100"
          align="left"
          fixed="left"
          sortable
        >
        </vxe-table-column>
        <vxe-table-column
          show-header-overflow
          show-footer-overflow
          show-overflow="tooltip"
          field="rdPname"
          title="项目名称"
          width="150"
          align="left"
          fixed="left"
          sortable
        >
        </vxe-table-column>
        <vxe-table-column
          show-header-overflow
          show-footer-overflow
          show-overflow="tooltip"
          field="month"
          title="项目周期"
          width="180"
          align="center"
          fixed="left"
          sortable
        >
        </vxe-table-column>
        <vxe-table-column
          show-header-overflow
          show-overflow="tooltip"
          show-footer-overflow
          :field="`${index}`"
          :title="`${year}-${index+1}`"
          v-for="(funds, index) in footerData.funds"
          :key="index"
          width="110"
          align="right"
          sortable
        >
          <template v-slot="{ row }">
            {{ row.funds[index] | NumberFormatHasNull }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          show-header-overflow
          show-overflow="tooltip"
          show-footer-overflow
          field="sum"
          title="合计"
          width="110"
          align="right"
          sortable
        >
          <template v-slot="{ row }">
            {{ row.sum | NumberFormatHasNull }}
          </template>
        </vxe-table-column>
      </vxe-grid>
    </a-spin>
  </a-drawer>
</template>

<script>
import { ystable } from '@/components'
import VXETable from 'vxe-table'
VXETable.setup({
  zIndex: 9999 // 层级
})
export default {
  data () {
    return {
      title: null,
      costTypes: this.$getCostTypes(),
      spinning: false,
      tableDatas: [],
      // monthList: [],
      footerData: [[]]
    }
  },
  props: {
    rdType: {
      type: String,
      default: '100'
    },
    visible: {
      type: Boolean,
      default: false
    },
    year: {
      type: Number,
      default: 0
    },
    projectList: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    visible (newValue, oldValue) {
      if (newValue && this.rdType) {
        this.spinning = true
        this.$http.get('/rdAgg/getMonthCostByRdType', { params: { rdType: this.rdType, year: this.year } }).then(res => {
          if (res.data && res.success) {
            this.title = res.data[0].costTitle
            // this.monthList = Object.keys(res.data[0].funds).sort((a, b) => a.split('-')[1] - b.split('-')[1])
            this.footerData = res.data.pop()
            this.tableDatas = this.joinData(res.data)
          }
        }).catch(r => {
          this.$message.error(r.errorMessage || '系统异常，请联系管理员!')
        }).finally(() => {
          this.spinning = false
        })
      }
    }
  },
  components: {
    ystable
  },

  computed: {},

  methods: {
    onClose () {
      this.$emit('update:visible', false)
    },
    showSummary () {
      const footer = this.footerData
      // 对应列数据
      const arr = Array.from({ length: Object.keys(this.projectList[0]).length - 1 }, () => '')
      const data = [].concat([footer.rdTitle], arr)
      // this.monthList.forEach(month => {
      //   data.push(this.$options.filters['NumberFormatHasNull'](footer.funds[month]))
      // })
      // footer.funds.forEach((funds, index) => {
      //   data.push(this.$options['NumberFormatHasNull'](funds))
      // })
      for (let index = 0; index < 12; index++) {
        const fund = footer.funds ? footer.funds[index] : null
        data.push(this.$options.filters['NumberFormatHasNull'](fund))
      }
      data.push(this.$options.filters['NumberFormatHasNull'](footer.sum))
      return [data]
    },
    joinData (data) {
      const tableData = []
      // 将项目赋数据 rdPname = rdTitle
      this.projectList.forEach(item => {
        const detail = data.find(values => values.rdTitle === item.rdTitle)
        if (detail) {
          tableData.push({ ...detail, ...item })
        } else {
          tableData.push({ ...item, funds: {} })
        }
      })
      return tableData
    }
  }
}
</script>

<style lang='less' scoped>

 .no-touch {
  overflow: hidden;
 }
 /deep/ .ant-card-body {
  height: 100%;
 }
</style>
