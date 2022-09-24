<!--
 * @Author: ldx
 * @Date: 2021-03-19 11:36:57
 * @LastEditTime: 2021-04-02 10:15:24
 * @LastEditors: ldx
 * @Description: 银行付款
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\BankReceipts.vue
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
        <a-button size="small" type="primary" style="margin-right: 20px;" @click="onAdd" v-if="$auth('tech:beian:investments:editBankReceipt')">添加</a-button>
        <span style="margin-right: 15px;"><b>转账总金额：</b><span><b><a>{{ amountTotal }}</a></b></span></span>
      </template>
      <vxe-table-column field="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column title="付款记账凭证字号" field="voucherNo" width="140" align="left" header-align="center">
        <template #default="{row}">
          <a @click="onEditBankReceipts(row)" title="点击编辑付款">{{ row.voucherNo }}</a>
        </template>
      </vxe-table-column>
      <vxe-table-column title="记账日期" field="transferDate" width="120" align="left" header-align="center">
        <template #default="{row}">
          {{ moment(row.transferDate).format("YYYY-MM-DD") }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="金额（含税）" field="amountTax" width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="税率" field="taxRate" width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="金额（不含税）" field="amount" width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="收款单位" field="payee" min-width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="付款日期" field="payDate" width="120" align="left" header-align="center">
        <template #default="{row}">
          {{ moment(row.payDate).format("YYYY-MM-DD") }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="付款阶段" field="payStage" width="120" align="left" header-align="center">
        <template #default="{row}">
          {{ payStage[row.payStage] }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="付款比例" field="payRate" min-width="120" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="付款方式" field="payType" width="120" align="left" header-align="center">
        <template #default="{row}">
          {{ payType[row.payType] }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="承兑汇票到期时间" field="acceptDate" width="140" align="left" header-align="center">
        <template #default="{row}">
          {{ row.acceptDate ? moment(row.acceptDate).format("YYYY-MM-DD") : '-' }}
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
    <choice-modal v-bind="$attrs" :investRecord="investRecord" ref="choiceModal" @updateTable="updateTable"></choice-modal>
    <AddBankReceiptModal ref="addBankReceiptModal" />
  </div>
</template>

<script>
import { delBankReceipt, getPayments } from '@/api/tech/BeiAnGuanLi/Invest'
import { cloneDeep } from 'lodash'
import moment from 'moment'
import ChoiceModal from './BankReceiptModules/ChoiceModal'
import { mapState } from 'vuex'
import AddBankReceiptModal from './BankReceiptModules/AddBankReceiptModal.vue'

export default {
  name: 'BankReceipts',
  components: {
    ChoiceModal,
    AddBankReceiptModal
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
          this.amountTotal = 0
          value.forEach(item => {
            this.amountTotal = (this.amountTotal * 1000 + item.amount * 1000) / 1000
          })
        }
      },
      deep: true
    }
  },
  data () {
    return {
      tableDatas: [],
      amountTotal: 0
    }
  },
  computed: {
    ...mapState({
      payStage: state => state.common.payStage,
      payType: state => state.common.payType
    })
  },
  methods: {
    moment,
    onAdd () {
      const ids = []
      if (this.tableDatas && this.tableDatas.length) {
        this.tableDatas.forEach((item, index) => {
          ids.push(item.id)
        })
      }
      this.$refs.choiceModal.show('选择付款', ids, cloneDeep(this.tableDatas))
    },
    onEditBankReceipts (record) {
      this.$refs.addBankReceiptModal.edit(`编辑付款信息`, record)
    },
    resetTableData () {
      this.tableDatas = []
    },
    updateTable (selectedRecords) {
      this.tableDatas = cloneDeep(selectedRecords)
    },
    onloadTableData (investmentId) {
      getPayments({ investmentId: investmentId }).then((response) => {
        if (response.data && response.success) {
          this.tableDatas = response.data
        } else {
          this.message.error(response.errorMessage)
        }
      }).catch((error) => {
        console.log('onloadTableData function error', error)
      }).finally((response) => {
      })
    },
    onConfirm (record, index) {
      this.tableDatas.splice(index, 1)
    },
    handleDel (record, index) {
      if (this.investRecord && this.investRecord.id) {
        record['investmentId'] = this.investRecord.id
      }
      delBankReceipt([record]).then((res) => {
        if (res.data && res.success) {
          this.tableDatas.splice(index, 1)
        } else {
          this.message.error(res.errorMessage)
        }
      }).catch((error) => {
        console.log('handleDel function error', error)
      }).finally((res) => {
      })
    }
  }
}
</script>

<style lang="less" scoped>

</style>
