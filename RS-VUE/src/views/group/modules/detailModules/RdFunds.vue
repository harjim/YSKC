<template>
  <div style="padding-top:20px;">
    <a-spin :spinning="spinning" tip="请稍后...">
      <vxe-grid
        id="groupRdFunds"
        ref="table"
        :data="tableData"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        size="small"
        :toolbar="{ custom: true, zoom:true, refresh:{query:loadData} }"
        :customConfig="{storage: { visible: true, resizable: true } }"
      >
        <template v-slot:buttons>单位：元</template>
        <vxe-table-column
          :title="companyName || '集团'"
          field="title"
          :width="180"
          show-header-overflow
          show-overflow="tooltip"/>
        <vxe-table-column
          v-for="i in 12"
          :key="i"
          :title="`${i}月`"
          :field="`m${i}`"
          width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
        />
        <vxe-table-column
          title="合计"
          field="total"
          width="140"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
        /></vxe-grid>
    </a-spin>
  </div>
</template>

<script>
export default {
  props: {
    companyId: {
      type: Number,
      default: undefined
    },
    companyName: {
      type: String,
      default: undefined
    },
    year: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      tableData: [],
      spinning: false,
      types: this.$getCostTypes()
    }
  },
  created () {
    this.loadData()
  },
  swatch: {
    year () {
      this.loadData()
    },
    companyId () {
      this.loadData()
    }
  },
  methods: {
    loadData () {
      this.spinning = true
      this.$http.get('/companyRdSummary/getGroupFunds', { params: { companyId: this.companyId, year: this.year } }).then(res => {
        const tableData = []
        if (res.success && res.data) {
          for (const t of this.types) {
            const row = { title: t.title, type: t.value }
            let total = 0
            for (let i = 1; i <= 12; i++) {
              const cur = res.data[`${t.value}_${i}`]
              if (cur) {
                row[`m${i}`] = Number(cur).toFixed(2)
                total = Number(total) + Number(cur)
              } else {
                row[`m${i}`] = '-'
              }
            }
            row.total = total ? total.toFixed(2) : '-'
            tableData.push(row)
          }
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '加载失败')
        }
        this.tableData = tableData
      }).finally(() => {
        this.spinning = false
      })
    }
  }
}
</script>

<style>

</style>
