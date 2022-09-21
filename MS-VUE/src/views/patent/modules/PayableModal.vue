<template>
  <a-modal
    :width="800"
    :visible="visible"
    title="查询费用信息"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @cancel="visible = false"
    :footer="null"
    style="overflow:auto"
  >
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="专利号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <span style="color:red">{{ project.patentNo }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="专利名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <span style="color:red">{{ project.patentName }}</span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="法律状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <span style="color:red">{{ project.caseStatus }}</span>
            </a-form-item>
          </a-col>
        </a-row>
        <div style="padding-bottom:10px;">
          <span style="font-size:14px;font-weight:bold;">未缴费信息</span>
        </div>
        <a-table
          bordered
          ref="table"
          size="small"
          rowKey="id"
          showPagination="auto"
          :dataSource="isPayData"
          :pagination="false"
        >
          <a-table-column title="费用种类" data-index="costType" key="costType" align="center"></a-table-column>
          <a-table-column title="应缴金额(元)" data-index="amount" key="amount" align="right"></a-table-column>
          <a-table-column title="缴费截止日" data-index="limitDate" key="limitDate" align="center"></a-table-column>
          <a-table-column title="费用状态" data-index="status" key="status" align="center"></a-table-column>
        </a-table>
        <br />
        <div style="padding-bottom:10px;">
          <span style="font-size:14px;font-weight:bold;">已缴费信息</span>
        </div>
        <a-table
          bordered
          ref="table"
          size="small"
          rowKey="id"
          showPagination="auto"
          :pagination="false"
          :dataSource="payTableData"
        >
          <a-table-column title="费用种类" data-index="costType" key="costType" align="center"></a-table-column>
          <a-table-column title="应缴金额(元)" data-index="amount" key="amount" align="right"></a-table-column>
          <a-table-column title="缴费日期" data-index="payDateTime" key="payDateTime" align="center"></a-table-column>
          <a-table-column title="缴费人姓名" data-index="payer" key="payer" align="center"></a-table-column>
          <a-table-column title="收据号" data-index="receiptNo" key="receiptNo" align="center"></a-table-column>
        </a-table>
        <div style="padding-top:10px;">
          <span style="font-size:14px;font-weight:bold;color:red">
            注意：如需核对费用信息，请前往
            <a
              href="http://cpquery.cnipa.gov.cn/"
              style="color:red;text-decoration:underline"
              target="_blank"
            >专利局官方网站</a> 查询
          </span>
        </div>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
  components: {
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      spinning: false,
      visible: false,
      isPay: false,
      payTableData: [],
      isPayData: [],
      confirmLoading: false,
      project: {},
      form: this.$form.createForm(this)
    }
  },
  created () {
  },
  methods: {
    showModal (record) {
      this.project = record
      this.visible = true
      this.payTableData = []
      this.isPayData = []
      this.$nextTick(() => {
        this.initializePayData()
      })
    },
    initializePayData () {
      this.spinning = true
      this.$http.get('/rsPatentCost/getPayDataBypatentNo', { params: Object.assign({ patentNo: this.project.patentNo }) })
        .then(res => {
          for (var i = 0; i < res.data.length; i++) {
            if (res.data[i].pay) {
              this.payTableData.push(res.data[i])
            } else {
              this.isPayData.push(res.data[i])
            }
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
