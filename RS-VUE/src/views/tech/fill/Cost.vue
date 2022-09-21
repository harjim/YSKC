<template>
  <a-spin :tip="tip" :spinning="spinning">
    <a-form layout="inline">
      <a-form-item label="支出内容">
        <a-input v-model="queryParams.cname" placeholder="请输入支出内容" style="width:180px" />
      </a-form-item>
      <a-form-item label="发票号码">
        <a-input v-model="queryParams.invoiceNumber" placeholder="请输入发票号码" style="width:180px" />
      </a-form-item>
      <a-form-item label="支出类别">
        <a-select
          v-model="queryParams.ctype"
          :allowClear="true"
          placeholder="请选择支出类别"
          style="width:180px"
        >
          <a-select-option value="1">设备</a-select-option>
          <a-select-option value="2">建设费</a-select-option>
          <a-select-option value="3">铺底流动资金</a-select-option>
        </a-select>
      </a-form-item>
      <span style="padding-left: 18px;">
        <a-button type="primary" @click="search(true)">查询</a-button>
      </span>
      <div>
        <span>
          <a-button type="primary" @click="$refs.costModal.add(projectId)">添加</a-button>
        </span>
        <span style="padding-left: 18px;">
          <a-button type="primary" @click="$refs.uploadModal.show(tableField)">导入</a-button>
        </span>
        <span style="padding-left: 18px;">
          <!-- <a-button type="primary" @click="exportCost">导出支出明细</a-button> -->
          <a-button type="primary" @click="exportCost">导出</a-button>
        </span>
        <span style="padding-left: 18px;">
          <a-button :disabled="selectedRowKeys.length <= 0" type="primary" @click="dels">删除</a-button>
        </span>
      </div>
    </a-form>
    <s-table
      ref="table"
      size="small"
      rowKey="id"
      :data="getList"
      showPagination="auto"
      :scroll="{x:1900}"
      :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
    >
      <a-table-column align="left" data-index="ctype" key="ctype" title="支出类别" :width="80">
        <template slot-scope="text">
          <span v-if="text === 1">设备</span>
          <span v-else-if="text === 2">建设费</span>
          <span v-else-if="text === 3">铺底流动资金</span>
        </template>
      </a-table-column>
      <a-table-column align="left" data-index="cname" key="cname" title="支出内容" :width="200" />
      <a-table-column align="left" data-index="model" key="model" title="规格 型号" :width="120" />
      <a-table-column align="right" data-index="quantity" key="quantity" title="数量" :width="80" />
      <a-table-column
        align="right"
        data-index="fillAmount"
        key="fillAmount"
        title="企业填报金额"
        :width="120"
      />
      <a-table-column align="left" data-index="payDates" key="payDates" title="支出时间" :width="140">
        <template slot-scope="t">
          <ellipsis :length="80" tooltip>{{ t }}</ellipsis>
        </template>
      </a-table-column>
      <a-table-column align="left" data-index="payee" key="payee" title="收款单位" :width="140" />
      <a-table-column
        align="left"
        data-index="invoiceVoucher"
        key="invoiceVoucher"
        title="发票记账凭证字号"
        :width="120"
      >
        <template slot-scope="t">
          <ellipsis :length="80" tooltip>{{ t }}</ellipsis>
        </template>
      </a-table-column>
      <a-table-column
        align="left"
        data-index="invoiceNumber"
        key="invoiceNumber"
        title="发票号码"
        :width="120"
      />
      <a-table-column
        align="center"
        data-index="invoiceDate"
        key="invoiceDate"
        title="发票日期"
        :width="120"
      />
      <a-table-column
        align="left"
        data-index="isBankTransfer"
        key="isBankTransfer"
        title="是否银行转账"
        :width="80"
      >
        <template slot-scope="text">
          <span v-if="text">是</span>
          <span v-else>否</span>
        </template>
      </a-table-column>
      <a-table-column
        align="left"
        data-index="bankVoucher"
        key="bankVoucher"
        title="银行转账记账凭证字号"
        :width="140"
      >
        <template slot-scope="t">
          <ellipsis :length="80" tooltip>{{ t }}</ellipsis>
        </template>
      </a-table-column>
      <a-table-column
        align="left"
        data-index="bankTransferDates"
        key="bankTransferDates"
        title="银行转账时间"
        :width="140"
      >
        <template slot-scope="t">
          <ellipsis :length="80" tooltip>{{ t }}</ellipsis>
        </template>
      </a-table-column>
      <a-table-column
        align="left"
        data-index="contractNumber"
        key="contractNumber"
        title="合同/协议编号"
        :width="100"
      />
      <a-table-column
        align="center"
        data-index="contractDate"
        key="contractDate"
        title="合同/协议日期"
        :width="120"
      />
      <a-table-column
        align="right"
        data-index="auditAmount"
        key="remauditAmountark"
        title="审计确定金额"
        :width="100"
      />
      <a-table-column align="center" data-index="action" key="action" title="操作" :width="120">
        <template slot-scope="text,record">
          <a @click="$refs.costModal.edit(record)">编辑</a>
          <a-divider type="vertical" />
          <a-popconfirm title="是否确定删除?" @confirm="del(record)">
            <a>删除</a>
          </a-popconfirm>
        </template>
      </a-table-column>
    </s-table>
    <cost-modal ref="costModal" @ok="ok=>search(ok)" />
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      :paramData="paramData"
      paramKey="tableField"
      title="导入项目支出"
      ref="uploadModal"
      action="/doc/techProjectCost/importInfo"
      templateName="项目支出模板"
      @onSuccess="success"
      @error="error"
    />
  </a-spin>
</template>
<script>
import { STable, UploadModal, Ellipsis } from '@/components'
import CostModal from './modules/CostModal'
export default {
  name: 'Cost',
  components: {
    STable,
    CostModal,
    UploadModal,
    Ellipsis
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      paramData: { projectId: this.projectId },
      spinning: false,
      tip: '请稍后...',
      firstSearch: true,
      queryParams: {},
      selectedRowKeys: [],
      tableField: {
        tableId: 'costTable',
        fieldTitleObject: {
          ctypeName: { title: '支出类别', required: true, defaultTitle: '支出类别', importField: true },
          cname: { title: '支出内容', required: true, defaultTitle: '支出内容', importField: true },
          model: { title: '规格 型号', defaultTitle: '规格 型号', importField: true },
          quantity: { title: '数量', defaultTitle: '数量', importField: true },
          fillAmount: { title: '企业填报金额', required: true, defaultTitle: '企业填报金额', importField: true },
          payDates: { title: '支出时间', required: true, defaultTitle: '支出时间', importField: true },
          payee: { title: '收款单位', required: true, defaultTitle: '收款单位', importField: true },
          invoiceVoucher: { title: '发票记账凭证字号', required: true, defaultTitle: '发票记账凭证字号', importField: true },
          invoiceNumber: { title: '发票号码', required: true, defaultTitle: '发票号码', importField: true },
          invoiceDate: { title: '发票日期', required: true, defaultTitle: '发票日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          isBankTransferStr: { title: '是否银行转账', defaultTitle: '是否银行转账', importField: true },
          bankVoucher: { title: '银行转账记账凭证字号', defaultTitle: '银行转账记账凭证字号', importField: true },
          bankTransferDates: { title: '银行转账时间', defaultTitle: '银行转账时间', importField: true },
          contractNumber: { title: '合同/协议编号', required: true, defaultTitle: '合同/协议编号', importField: true },
          contractDate: { title: '合同/协议日期', required: true, defaultTitle: '合同/协议日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          auditAmount: { title: '审计确定金额', required: true, defaultTitle: '审计确定金额', importField: true }
        }
      },
      sampleData: [{
        ctypeName: '请从中选择填写：设备/建设费/铺底流动资金，例如：设备',
        cname: '上煤皮带机',
        model: 'J001',
        quantity: '数字格式，例如：1',
        fillAmount: '数字格式，例如：300000',
        payDates: '格式：2019.01.01，2019.02.02',
        payee: '格式：xxxxxxxxx公司',
        invoiceVoucher: '1402付161，1402转305',
        invoiceNumber: '000001',
        invoiceDate: '格式："年-月-日"，例如：2019-10-11',
        isBankTransferStr: '请从中选择填写：是/否，例如：是',
        bankVoucher: '格式：1402付161，1402转305',
        bankTransferDates: '格式：2019.01.01，2019.02.02',
        contractNumber: '格式：xxxxxxxxxxxxxxx',
        contractDate: '格式："年-月-日"，例如：2019-10-11',
        auditAmount: '数字格式，例如：300000'
      }],
      getList: parameter => {
        if (!this.firstSearch) {
          this.firstSearch = false
          return
        }
        this.queryParams.projectId = this.projectId
        return this.$http.get('/techProjectCost/getList', { params: Object.assign(parameter, this.queryParams) })
          .then(res => {
            return res.data
          })
      }
    }
  },
  watch: {
    projectId (nowId) {
      this.paramData.projectId = nowId
      this.search(true)
    }
  },
  methods: {
    search (refresh) {
      this.selectedRowKeys = []
      this.$refs.table.refresh(refresh)
    },
    onSelectChange (keys) {
      this.selectedRowKeys = keys
    },
    del (record) {
      this.spinning = true
      this.tip = '删除中，请稍后...'
      this.$http.post('/techProjectCost/del', { id: record.id })
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.search(false)
          } else {
            this.$message.error('删除失败')
          }
          this.spinning = false
        })
    },
    dels () {
      this.spinning = true
      this.tip = '删除中，请稍后...'
      const self = this
      this.$confirm({
        title: '您确定要删除选中的项目支出数据吗?',
        onOk () {
          var values = []
          self.selectedRowKeys.forEach(id => {
            values.push({ id: id })
          })
          self.$http.post('/techProjectCost/dels', values)
            .then(res => {
              if (res.success && res.data) {
                self.$message.success('删除成功')
                self.search(false)
              } else {
                self.$message.error('删除失败')
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          content: resData,
          onOk () {

          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
      }
      this.search(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    exportCost () {
      this.spinning = true
      this.tip = '正在导出，请稍后...'
      this.$exportData('/techProjectCost/exportCost', { projectId: this.projectId }, `项目支出明细表.xls`, this.$message).finally(res => {
        this.spinning = false
      })
    }
  }
}
</script>
