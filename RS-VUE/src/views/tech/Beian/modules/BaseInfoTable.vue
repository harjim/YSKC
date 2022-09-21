<template>
  <table border="1" width="100%" class="table">
    <thead>
      <tr>
        <th rowspan="2" colspan="1">类别</th>
        <th rowspan="1" colspan="3">不含税</th>
        <th rowspan="1" colspan="3">含税</th>
      </tr>
      <tr>
        <th>计划投资</th>
        <th>发票已完成</th>
        <th>付款已完成</th>
        <th>计划投资</th>
        <th>发票已完成</th>
        <th>付款已完成</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>备案总投资</td>
        <td>
          <a-form-item>
            <a-input-number
              v-if="saveAuth"
              v-decorator="['totalAmount', { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.totalMax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ record.totalAmount }}</span>
          </a-form-item>
        </td>
        <td>{{ get(record, 'invoiceModel.totalAmount', '') }}</td>
        <td>{{ get(record, 'paymentModel.totalAmount', '') }}</td>
        <td>
          <a-form-item>
            <a-input-number
              v-if="saveAuth"
              v-decorator="['totalAmountTax', { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.totalMaxTax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ record.totalAmountTax }}</span>
          </a-form-item>
        </td>
        <td>{{ get(record, 'invoiceModel.totalAmountTax', '') }}</td>
        <td>{{ get(record, 'paymentModel.totalAmountTax', '') }}</td>
      </tr>
      <tr>
        <td>固定资产投资</td>
        <td>
          <a-form-item>
            <a-input-number
              v-if="saveAuth"
              v-decorator="['amount', { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.totalMax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ record.amount }}</span>
          </a-form-item>
        </td>
        <td>{{ get(record, 'invoiceModel.amount', '') }}</td>
        <td>{{ get(record, 'paymentModel.amount', '') }}</td>
        <td>
          <a-form-item>
            <a-input-number
              v-if="saveAuth"
              v-decorator="['amountTax', { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.totalMaxTax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ record.amountTax }}</span>
          </a-form-item>
        </td>
        <td>{{ get(record, 'invoiceModel.amountTax', '') }}</td>
        <td>{{ get(record, 'paymentModel.amountTax', '') }}</td>
      </tr>
      <tr>
        <td>设备费</td>
        <td>
          <a-form-item>
            <a-input-number
              v-if="saveAuth"
              v-decorator="['equipment', { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.totalMax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ record.equipment }}</span>
          </a-form-item>
        </td>
        <td>{{ get(record, 'invoiceModel.equipment', '') }}</td>
        <td>{{ get(record, 'paymentModel.equipment', '') }}</td>
        <td>
          <a-form-item>
            <a-input-number
              v-if="saveAuth"
              v-decorator="['equipmentTax', { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.equipmentTax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ record.equipmentTax }}</span>
          </a-form-item>
        </td>
        <td>{{ get(record, 'invoiceModel.equipmentTax', '') }}</td>
        <td>{{ get(record, 'paymentModel.equipmentTax', '') }}</td>
      </tr>
      <tr>
        <td>建设费</td>
        <td>
          <a-form-item>
            <a-input-number
              v-if="saveAuth"
              v-decorator="['construction', { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.totalMax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ record.construction }}</span>
          </a-form-item>
        </td>
        <td>{{ get(record, 'invoiceModel.construction', '') }}</td>
        <td>{{ get(record, 'paymentModel.construction', '') }}</td>
        <td>
          <a-form-item>
            <a-input-number
              v-if="saveAuth"
              v-decorator="['constructionTax', { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.constructionTax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ record.constructionTax }}</span>
          </a-form-item>
        </td>
        <td>{{ get(record, 'invoiceModel.constructionTax', '') }}</td>
        <td>{{ get(record, 'paymentModel.constructionTax', '') }}</td>
      </tr>
      <tr>
        <td>铺底流动资金</td>
        <td>
          <a-form-item>
            <a-input-number
              v-if="saveAuth"
              v-decorator="['initWorkCapital', { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.totalMax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ record.initWorkCapital }}</span>
          </a-form-item>
        </td>
        <td>{{ get(record, 'invoiceModel.initWorkCapital', '') }}</td>
        <td>{{ get(record, 'paymentModel.initWorkCapital', '') }}</td>
        <td>
          <a-form-item>
            <a-input-number
              v-if="saveAuth"
              v-decorator="['initWorkCapitalTax', { rules: [{ required: true, message: '请输入该项' }] }]"
              :precision="2"
              :min="0"
              :max="$store.state.constructionTax"
              style="width:100%"
              placeholder="请输入"
            />
            <span v-else>{{ record.initWorkCapitalTax }}</span>
          </a-form-item>
        </td>
        <td>{{ get(record, 'invoiceModel.initWorkCapitalTax', '') }}</td>
        <td>{{ get(record, 'paymentModel.initWorkCapitalTax', '') }}</td>
      </tr>
      <tr>
        <td>完工率</td>
        <td></td>
        <td>{{ get(record, 'invoiceModel.completion', '') }}</td>
        <td>{{ get(record, 'invoiceModel.completionTax', '') }}</td>
        <td></td>
        <td>{{ get(record, 'paymentModel.completion', '') }}</td>
        <td>{{ get(record, 'paymentModel.completionTax', '') }}</td>
      </tr>
    </tbody>
  </table>
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
