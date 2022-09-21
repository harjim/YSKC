<!--
 * @Author: ldx
 * @Date: 2021-03-19 10:53:27
 * @LastEditTime: 2021-10-14 16:38:22
 * @LastEditors: zdf
 * @Description: 发票列表
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\Invoices.vue
-->
<template>
  <div>
    <vxe-grid
      ref="table"
      :data="tableDatas"
      border
      resizable
      auto-resize
      highlight-hover-row
      show-overflow="title"
      show-header-overflow
      highlight-current-row
      size="small"
      :toolbar="{ custom: true }"
    >
      <template #buttons>
        <a-button size="small" type="primary" style="margin-right: 20px;" @click="onAdd" v-if="$auth('tech:beian:investments:relatedInvoice')">添加</a-button>
        <span style="margin-right: 15px;"><b>不含税总金额：</b><span><b><a>{{ excludeTaxAmountTotal }}（元）</a></b></span></span>
        <span><b>含税总金额：</b><span><b><a>{{ countAmountTotal }}（元）</a></b></span></span>
      </template>
      <vxe-table-column field="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column title="发票号" field="invoiceNo" min-width="120" align="left" header-align="center">
        <template #default="{row}">
          <a @click="onEditInvoice(row)" title="点击编辑发票">{{ row.invoiceNo }}</a>
        </template>
      </vxe-table-column>
      <vxe-table-column title="设备名称" field="ename" min-width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="规格型号" field="emodal" min-width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="设备供应商" field="invoiceFrom" min-width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="数量" field="quantity" width="90" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="金额(元/含税)" field="taxAmount" width="120" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="金额(元)" field="excludeTaxAmount" width="130" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="发票日期" field="invoiceDate" width="120" align="center" header-align="center">
        <template #default="{row}">
          {{ moment(row.invoiceDate).format("YYYY-MM-DD") }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="记账凭证字号" field="voucherNo" width="100" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="转固日期" field="voucherDate" width="120" align="center" header-align="center">
        <template #default="{row}">
          {{ moment(row.voucherDate).format("YYYY-MM-DD") }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="操作" width="100" align="center" header-align="center" fixed="right">
        <template #default="{row,rowIndex}">
          <a-popconfirm title="您确定要删除吗？" @confirm="onConfirm(row,rowIndex)">
            <a>删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </vxe-grid>
    <choice-modal v-bind="$attrs" :investRecord="investRecord" ref="choiceModal" @updateTable="updateTable" @setDelIds="setDelIds"></choice-modal>
    <add-invoice-modal ref="addInvoiceModal" @setDelIds="setDelIds"></add-invoice-modal>
  </div>
</template>

<script>
import { ystable } from '@/components/'
import ChoiceModal from './invoiceModules/ChoiceModal.vue'
import AddInvoiceModal from './invoiceModules/AddInvoiceModal'
import { getInvoices, delInvoiceDetail } from '@/api/tech/BeiAnGuanLi/Invest'
import { cloneDeep } from 'lodash'
import moment from 'moment'
export default {
  name: 'Invoices',
  components: {
    ChoiceModal,
    ystable,
    AddInvoiceModal
  },
  props: {
    investRecord: {
      type: Object,
      default: () => { return {} }
    }
  },
  mounted () {
    // this.onloadTableData()
  },
  watch: {
    'tableDatas': {
      handler: function (value) {
        if (value && value.length) {
          this.excludeTaxAmountTotal = 0
          this.countAmountTotal = 0
          value.forEach(item => {
            this.excludeTaxAmountTotal = (this.excludeTaxAmountTotal * 1000 + item.excludeTaxAmount * 1000) / 1000
            this.countAmountTotal = (this.countAmountTotal * 1000 + item.taxAmount * 1000) / 1000
          })
        }
      },
      deep: true
    }
  },
  data () {
    return {
      tableDatas: [],
      countAmountTotal: 0,
      excludeTaxAmountTotal: 0
    }
  },
  methods: {
    moment,
    onEditInvoice (record) {
      this.$refs.addInvoiceModal.edit(`编辑发票 【发票号：${record.invoiceNo}】`, record)
    },
    onAdd () {
      const ids = []
      if (this.tableDatas && this.tableDatas.length) {
        this.tableDatas.forEach((item, index) => {
          ids.push(item.id)
        })
      }
      this.$refs.choiceModal.show('选择发票明细', ids, cloneDeep(this.tableDatas))
    },
    onloadTableData (investmentId) {
      this.spinning = true
      getInvoices({ investmentId: investmentId }).then((response) => {
        if (response.success && response.data) {
          this.tableDatas = response.data
        }
      }).catch(error => {
        console.log('onloadTableData function error', error)
      }).finally(() => {
        this.spinning = false
      })
    },
    onConfirm (record, index) {
      this.tableDatas.splice(index, 1)
    },
    updateTable (selectedRecords) {
      this.tableDatas = cloneDeep(selectedRecords)
    },
    resetTableData () {
      this.tableDatas = []
    },
    handleDel (record, index) {
      if (this.investRecord && this.investRecord.id) {
        record['investmentId'] = this.investRecord.id
      }
      delInvoiceDetail([record]).then((res) => {
        if (res.data && res.success) {
          this.tableDatas.splice(index, 1)
        } else {
          this.message.error(res.errorMessage)
        }
      }).catch((error) => {
        console.log('handleDel function error', error)
      }).finally((res) => {
      })
    },
    setDelIds (ids) {
      this.tableDatas = this.tableDatas.filter(item => {
        return !ids.includes(item.id)
      })
    }
  }
}
</script>

<style>

</style>
