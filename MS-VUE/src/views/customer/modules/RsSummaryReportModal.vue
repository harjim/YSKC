<template>
  <a-modal
    :width="1300"
    style="top:0;"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    :footer="null"
    @cancel="closeModal"
    :bodyStyle="{ maxHeight:'84vh',height: '84vh'}"
  >
    <vxe-grid
      id="/rsProject/getDataReportFunds"
      ref="table"
      :data="tableData"
      max-height="94%"
      :customConfig="{storage: { visible: true, resizable: true } }"
    >
      <vxe-table-column
        title="RD"
        field="pname"
        align="left"
        :width="200"
        fixed="left"
      >
        <template v-slot="{row}">{{ row.rdTitle ? `${row.rdTitle}--${row.pname}` : row.pname }}</template>
      </vxe-table-column>
      <vxe-table-column
        title="起止时间"
        field="beginAndEnd"
        align="center"
        :width="190"
      >
        <template v-slot="{ row }">
          <span v-if="row.beginDate!==null">{{ row.beginDate + '~' + row.endDate }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="工资"
        field="salary"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="五险一金"
        field="insurance"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="设备折旧"
        field="prod"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="仪器折旧"
        field="lab"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="材料"
        field="material"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="动力"
        field="stimulus"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="燃料"
        field="fuel"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="试制"
        field="trial"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="修理"
        field="repair"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="检测"
        field="inspection"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="样机"
        field="sampleMachine"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="设计"
        field="design"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="摊销"
        field="amortization"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="差旅费"
        field="travel"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="其他"
        field="other"
        align="right"
        :width="100"
      />
      <vxe-table-column
        title="总计"
        field="rdTotal"
        align="right"
        :width="120"
      />
    </vxe-grid>
  </a-modal>
</template>

<script>

export default {
  components: {
  },
  data () {
    return {
      title: '',
      visible: false,
      existProjectNum: 0,
      estimateExpenseNum: 0,
      tableData: [],
      year: undefined
    }
  },
  created () {
  },
  methods: {
    show (record) {
      if (record.productName) {
        this.title = '[' + record.companyName + '][' + record.productName + '][' + record.year + '年]归集总表'
      } else {
        this.title = '[' + record.companyName + '][' + record.year + '年]归集总表'
      }
      this.visible = true
      this.getData(record)
    },
    getData (record) {
      this.$http.get('/rsProject/getDataReportFunds', { params: { companyId: record.companyId, year: record.year } })
        .then(res => {
          if (res.data === null) {
            this.tableData = []
            return
          }
          this.errorMsg = ''
          this.tableData = res.data
          return this.tableData
        })
    },
    closeModal () {
      this.visible = false
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
</style>
