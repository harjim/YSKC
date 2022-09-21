<template>
  <div style="padding-top:20px;">
    <a-spin :spinning="spinning" tip="请稍后...">
      <vxe-grid
        id="RdList"
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
        <vxe-table-column
          title="序号"
          :width="50"
          show-header-overflow
          show-overflow="tooltip"
          type="seq"/>
        <vxe-table-column
          title="项目名称"
          field="pname"
          min-width="140"
          show-header-overflow
          show-overflow="tooltip"/>
        <vxe-table-column field="rdTitle" title="RD" width="120" align="left"></vxe-table-column>
        <vxe-table-column field="deptName" title="部门" width="120" align="left"></vxe-table-column>
        <vxe-table-column field="workshop" title="车间" width="120" align="left"></vxe-table-column>
        <vxe-table-column field="productLine" title="产线" width="120" align="left"></vxe-table-column>
        <vxe-table-column field="processSection" title="工艺段" width="120" align="left"></vxe-table-column>
        <vxe-table-column field="beginAndEndDate" title="起止日期" width="180" align="center">
          <template v-slot=" { row }">{{ `${row.beginDate} ~ ${row.endDate}` }}</template>
        </vxe-table-column>
        <vxe-table-column field="rdDeptName" title="研发部门" width="120" align="left"/>
        <vxe-table-column field="ename" title="负责人" width="120" align="center"></vxe-table-column>
      </vxe-grid>
    </a-spin>
  </div>
</template>

<script>
export default {
  props: {
    companyId: {
      type: Number,
      required: true
    },
    year: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      tableData: [],
      spinning: false
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
      this.$http.get('/companyRdSummary/getCompanyRdList', { params: { companyId: this.companyId, year: this.year } }).then(res => {
        if (res.success && res.data) {
          this.tableData = res.data
        } else {
          this.tableData = []
          this.$message.error(res.errorMessage ? res.errorMessage : '加载失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    }
  }
}
</script>

<style>

</style>
