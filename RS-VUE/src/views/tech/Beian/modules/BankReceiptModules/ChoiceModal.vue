<!--
 * @Author: ldx
 * @Date: 2021-03-19 11:49:52
 * @LastEditTime: 2021-04-02 09:46:58
 * @LastEditors: ldx
 * @Description: 合同选择modal
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\BankReceiptModules\ChoiceModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="handleSubmit"
    style="top: 0px;"
    @ok="handleSubmit">
    <a-form layout="inline">
      <a-form-item label="转账记账凭证字号">
        <a-input v-model="queryParams.voucherNo" placeholder="转账记账凭证字号"></a-input>
      </a-form-item>
      <a-form-item label="收款单位">
        <a-input v-model="queryParams.payee" placeholder="收款单位"></a-input>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="onQuery" style="margin-right: 8px;">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      queryUrl="/inverstment/getPaymentInfo"
      :params="queryParams"
      rowId="id"
      size="small"
      border
      resizable
      auto-resize
      highlight-hover-row
      show-overflow="title"
      show-header-overflow
      highlight-current-row
      header-align="center"
      :checkbox-config="{reserve: true,}"
      @checkbox-change="checkboxChange"
      @checkbox-all="checkboxAll"
      @completed="({data:{data}})=>completed(data)"
      :toolbar="{ custom: true }"
    >
      <template #buttons>
        <a-button type="primary" @click="onAdd" style="margin-right: 8px;">添加</a-button>
      </template>
      <vxe-table-column type="checkbox" width="60" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column field="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column title="付款记账凭证字号" field="voucherNo" width="140" align="left" header-align="center">
        <template #default="{row}">
          <a @click="onEdit(row)" title="点击编辑付款">{{ row.voucherNo }}</a>
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
        <template #default="{row}">
          <a-popconfirm title="您确定要删除吗？" @confirm="onConfirmDel(row)">
            <a>删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </ystable>
    <add-BankReceipt-modal v-bind="$attrs" ref="addBankReceiptModal" @refresh="onQuery"></add-BankReceipt-modal>
  </a-modal>
</template>
<script>
import { delPaymentInfo, getReceiptInfo } from '@/api/tech/BeiAnGuanLi/Invest'
import { ystable } from '@/components'
import { cloneDeep } from 'lodash'
import moment from 'moment'
import AddBankReceiptModal from './AddBankReceiptModal'
import { mapState } from 'vuex'

export default {
  name: 'ChoiceModel',
  components: {
    ystable,
    AddBankReceiptModal
  },
  props: {
    investRecord: {
      type: Object,
      default: () => { return {} }
    }
  },
  data () {
    return {
      isVisible: false,
      title: '',
      queryParams: {},
      spinning: false,
      tableDatas: [],
      selectedIds: [],
      theFirstTime: true,
      parentTableData: []
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
    completed (data) {
      this.tableDatas = data
      this.handleSelected(data, this.selectedIds)
    },
    show (title, ids, parentTableData) {
      this.queryParams.investmentId = this.investRecord.id
      this.isVisible = true
      this.title = title
      this.selectedIds = ids
      this.parentTableData = parentTableData
      if (!this.theFirstTime) {
        this.onQuery()
      }
      this.theFirstTime = false
    },
    onAdd () {
      this.$refs.addBankReceiptModal.show('添加付款')
    },
    onEdit (record) {
      this.$refs.addBankReceiptModal.edit(`编辑付款信息`, record)
    },
    onConfirmDel (row) {
      const params = { ids: [row.id] }
      delPaymentInfo(params).then(response => {
        if (response.success && response.data) {
          this.onQuery()
        } else {
          this.$message.error(response.errorMessage)
          console.log(`response errorCode ${response.errorCode} : ${response.errorMessage}`)
        }
      }).catch(error => {
        console.log('onDel function error', error)
      })
    },
    onQuery () {
      this.$refs.table.refresh(true)
      if (this.tableDatas && this.tableDatas.length) {
        this.handleSelected(this.tableDatas, this.selectedIds)
      }
    },
    afterClose () {
      this.spinning = false
      this.isVisible = false
      this.title = ''
      this.queryParams = {}
      this.tableDatas = []
      this.selectedIds = []
      this.parentTableData = []
    },
    handleSubmit () {
      this.$emit('updateTable', this.parentTableData)
      Object.assign(this.$data, this.$options.data.call(this))
    },
    checkboxChange ({ records, reserves, checked, row, rowIndex }) {
      if (checked) {
        this.parentTableData.push(row)
      } else {
        this.parentTableData.forEach((item, index) => {
          if (item.id === row.id) {
            this.parentTableData.splice(index, 1)
          }
        })
      }
    },
    checkboxAll ({ records, checked }) {
      if (checked) {
        const tempAry = []
        records.forEach((record) => {
          tempAry.push(record.id)
        })
        const tempTable = cloneDeep(this.parentTableData)
        tempAry.forEach(item => {
          tempTable.forEach((row, index) => {
            if (item === row.id) {
              tempTable.splice(index, 1)
            }
          })
        })
        this.parentTableData = tempTable.concat(records)
      } else {
        this.tableDatas.forEach((item, index) => {
          this.parentTableData.forEach((row, i) => {
            if (item.id === row.id) {
              this.parentTableData.splice(i, 1)
            }
          })
        })
      }
    },
    onloadTableData () {
      this.spinning = true
      getReceiptInfo(this.queryParams).then((response) => {
        if (response.success && response.data) {
          this.tableDatas = response.data
        }
      }).catch(error => {
        console.log('onloadTableData function error', error)
      }).finally(() => {
        this.spinning = false
      })
    },
    handleSelected () {
      const rows = []
      this.tableDatas.forEach((row, index) => {
        this.selectedIds.forEach(id => {
          if (row.id === id) {
            rows.push(this.tableDatas[index])
          }
        })
      })
      this.$refs.table.setCheckboxRow(rows, true)
    }
  }
}
</script>
<style lang='less' scoped>
</style>
