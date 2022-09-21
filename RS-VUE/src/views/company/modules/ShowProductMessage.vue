<!-- 企业产品管理--展示产量 -->
<template>
  <a-popover :title="pname" arrow-point-at-center>
    <a>{{ poutput }}</a>
    <template slot="content">
      <div style="width:360px;height: 280px;">
        <a-spin :spinning="confirmLoading">
          <vxe-table
            :column-config="{resizable: true}"
            :data="tableData"
            max-height="260px">
            <vxe-table-column
              align="center"
              field="year"
              headerAlign="center"
              minWidth="60"
              title="年份">
            </vxe-table-column>
            <vxe-table-column
              align="center"
              field="output"
              headerAlign="center"
              minWidth="100"
              :title="`产量(${unit})`">
              <template v-slot="{ row }">{{ row.output }}</template>
            </vxe-table-column>
            <vxe-table-column
              align="center"
              field="outputValue"
              headerAlign="center"
              minWidth="60"
              title="产值(万元)">
            </vxe-table-column>
          </vxe-table>
        </a-spin>
      </div>
    </template>
  </a-popover>
</template>

<script>
// https://vxetable.cn/v3/#/table/advanced/footerSpan
export default ({
  data () {
    return {
      tableData: [],
      confirmLoading: true
    }
  },
  props: {
    pid: {
      type: Number,
      default: 0
    },
    pname: {
      type: String,
      default: ''
    },
    poutput: {
      type: Number,
      default: 0
    },
    unit: {
      type: String,
      default: ''
    }
  },
  created () {
    // this.tableData = [{ 'id': 5, 'companyId': 113, 'productId': 14, 'year': 2019, 'output': 50.00, 'outputValue': 600.00 }, { 'id': 3, 'companyId': 113, 'productId': 14, 'year': 2020, 'output': 50.00, 'outputValue': 600.00 }, { 'id': 4, 'companyId': 113, 'productId': 14, 'year': 2021, 'output': 50.00, 'outputValue': 600.00 }, { 'id': 1, 'companyId': 113, 'productId': 14, 'year': 2022, 'output': 50.00, 'outputValue': 600.00 }]
    this.selectData()
  },
  methods: {
    selectData () {
      this.confirmLoading = true
      this.$http.get('/product/getYearList', { params: { 'productId': this.pid } }).then((result) => {
        if (result.success) {
          this.tableData = result.data
        } else {
          this.tableData = []
        }
      })
      this.confirmLoading = false
    }
  }
})
</script>
