/**
 * @Author        : hzp
 * @Date          : 2022-09-21 14:07:25
 * @FilePath      : \YSIS\RS-VUE\src\views\tech\Beian\modules\BaseInfoTable.vue
 * @Description   : 基本信息经费表
 * @LastEditors   : hzp
 * @LastEditTime  : 2022-09-24 09:11:33
 */
<template>
  <vxe-grid
    show-overflow
    highlight-hover-row
    auto-resize
    resizable
    header-align="center"
    :data="tableData"
  >
    <vxe-table-column fixed="left" field="title" title="分类" minWidth="120" />
    <vxe-table-colgroup title="不含税">
      <vxe-table-column title="计划投资" field="custom" minWidth="140" align="right" header-align="center">
        <template #default="{ row, $rowIndex }">
          <a-form-item>
            <a-input-number
              v-if="saveAuth && $rowIndex !== 5"
              :disabled="$rowIndex === 3 || $rowIndex === 4"
              v-decorator="[get(fieldTable, `[${$rowIndex}].custom`), { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.totalMax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ row.custom }}</span>
          </a-form-item>
        </template>
      </vxe-table-column>
      <vxe-table-column title="发票已完成" field="invoice" minWidth="120" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="付款已完成" field="payment" minWidth="120" align="right" header-align="center"></vxe-table-column>
    </vxe-table-colgroup>
    <vxe-table-colgroup title="含税">
      <vxe-table-column title="计划投资" field="customTax" minWidth="140" align="right" header-align="center">
        <template #default="{ row, $rowIndex }">
          <a-form-item>
            <a-input-number
              v-if="saveAuth && $rowIndex !== 5"
              :disabled="$rowIndex === 3 || $rowIndex === 4"
              v-decorator="[get(fieldTable, `[${$rowIndex}].customTax`), { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.totalMax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ row.custom }}</span>
          </a-form-item>
        </template>
      </vxe-table-column>
      <vxe-table-column title="发票已完成" field="invoiceTax" minWidth="120" align="right" header-align="center"></vxe-table-column>
      <vxe-table-column title="付款已完成" field="paymentTax" minWidth="120" align="right" header-align="center"></vxe-table-column>
    </vxe-table-colgroup>
  </vxe-grid>
</template>

<script>
import { get } from 'lodash'

export default {
  name: 'BaseInfoTable',
  props: {
    record: {
      type: Object,
      default: () => ({})
    },
    saveAuth: Boolean
  },
  data () {
    return {
      tableData: [],
      fieldTable: [
        { custom: 'equipment', customTax: 'equipmentTax' },
        { custom: 'construction', customTax: 'constructionTax' },
        { custom: 'initWorkCapital', customTax: 'initWorkCapitalTax' },
        { custom: 'amount', customTax: 'amountTax' },
        { custom: 'totalAmount', customTax: 'totalAmountTax' }
      ]
    }
  },
  watch: {
    record (newV) {
      if (!newV) return
      this.tableData = [
        {
          title: '设备费',
          custom: get(newV, 'equipment', ''),
          customTax: get(newV, 'equipmentTax', ''),
          invoice: get(newV, 'invoiceModel.equipment', '-'),
          payment: get(newV, 'paymentModel.equipment', '-'),
          invoiceTax: get(newV, 'invoiceModel.equipmentTax', '-'),
          paymentTax: get(newV, 'paymentModel.equipmentTax', '-')
        },
        {
          title: '建设费',
          custom: get(newV, 'construction', ''),
          customTax: get(newV, 'constructionTax', ''),
          invoice: get(newV, 'invoiceModel.construction', '-'),
          payment: get(newV, 'paymentModel.construction', '-'),
          invoiceTax: get(newV, 'invoiceModel.constructionTax', '-'),
          paymentTax: get(newV, 'paymentModel.constructionTax', '-')
        },
        {
          title: '铺底流动资金',
          custom: get(newV, 'initWorkCapital', ''),
          customTax: get(newV, 'initWorkCapitalTax', ''),
          invoice: get(newV, 'invoiceModel.initWorkCapital', '-'),
          payment: get(newV, 'paymentModel.initWorkCapital', '-'),
          invoiceTax: get(newV, 'invoiceModel.initWorkCapitalTax', '-'),
          paymentTax: get(newV, 'paymentModel.initWorkCapitalTax', '-')
        },
        {
          title: '固定资产投资',
          custom: get(newV, 'amount', ''),
          customTax: get(newV, 'amountTax', ''),
          invoice: get(newV, 'invoiceModel.amount', '-'),
          payment: get(newV, 'paymentModel.amount', '-'),
          invoiceTax: get(newV, 'invoiceModel.amountTax', '-'),
          paymentTax: get(newV, 'paymentModel.amountTax', '-')
        },
        {
          title: '备案总投资',
          custom: get(newV, 'totalAmount', ''),
          customTax: get(newV, 'totalAmountTax', ''),
          invoice: get(newV, 'invoiceModel.totalAmount', '-'),
          payment: get(newV, 'paymentModel.totalAmount', '-'),
          invoiceTax: get(newV, 'invoiceModel.totalAmountTax', '-'),
          paymentTax: get(newV, 'paymentModel.totalAmountTax', '-')
        },
        {
          title: '完工率',
          invoice: get(newV, 'invoiceModel.completion', ''),
          payment: get(newV, 'paymentModel.completion', ''),
          invoiceTax: get(newV, 'invoiceModel.completionTax', ''),
          paymentTax: get(newV, 'paymentModel.completionTax', '')
        }
      ]
    }
  },
  methods: {
    get
  }
}
</script>

<style lang="less" scoped>
.table {
  th {
    text-align: center;
    background-color: #f8f8f9;
  }
  tr td:first-child {
    text-align: center;
  }
  td {
    text-align: right;
    padding: 0 6px;
  }

  & /deep/ .ant-form-item {
    margin-bottom: 0;
  }
}
</style>
