<template>
  <a-modal
    :width="1000"
    :visible="visible"
    :title="title"
    :footer="null"
    :maskClosable="false"
    @cancel="visible = false"
  >
    <div>
      <a-table
        ref="table"
        size="small"
        rowKey="id"
        :pagination="false"
        :dataSource="datas"
        bordered
      >
        <a-table-column
          align="center"
          title="摘要"
          data-index="remark"
          key="remark"
          :width="100"
          class="word-wrap"
        >
          <template slot-scope="text,record">
            <span>{{ remarkMap[record.rdType] }}</span>
          </template>
        </a-table-column>
        <a-table-column
          align="center"
          title="科目"
          data-index="accountName"
          key="accountName"
          :width="100"
          validateStatus="success"
          class="word-wrap"
        >
          <template slot-scope="text,record">
            <a-tree-select
              v-if="!record.sum"
              style="width:250px"
              v-model="record.accountName"
              :dropdownStyle="{ maxHeight: '400px', overflow: 'auto' }"
              :treeData="accountTree"
              placeholder="选择科目"
              treeNodeFilterProp="title"
              treeDefaultExpandAll
              @change="(value)=>onTreeChange(value,record)"
            ></a-tree-select>
          </template>
        </a-table-column>
        <a-table-column
          align="center"
          title="借方金额"
          data-index="debitAmount"
          key="debitAmount"
          :width="100"
          class="word-wrap"
        >
          <template slot-scope="text">
            <span>{{ text | MoneyFormat }}</span>
          </template>
        </a-table-column>
        <a-table-column
          align="center"
          title="贷方金额"
          data-index="creditAmount"
          key="creditAmount"
          :width="100"
          class="word-wrap"
        >
          <template slot-scope="text">
            <span>{{ text | MoneyFormat }}</span>
          </template>
        </a-table-column>
      </a-table>
    </div>
    <div style="text-align: right;margin-top:10px">
      <a-button type="primary" @click="handleSubmit" style="margin-left:20px">确认</a-button>
    </div>
  </a-modal>
</template>

<script>
import { STable, Ellipsis } from '@/components'

export default {
  name: 'SheetsModal',
  components: {
    STable,
    Ellipsis
  },
  mounted () {
    this.loadTree()
  },
  data () {
    return {
      accountTree: [],
      remarkMap: {
        10000: '工资费用',
        10001: '奖金费用',
        10100: '五险一金费用',
        30000: '设备费用',
        20000: '研发材料费用',
        20301: '中间试制费用',
        20601: '修理材料费用',
        20100: '动力费用',
        20200: '燃料费用',
        50000: '设计费用',
        20500: '检测费用',
        20600: '修理费用',
        60400: '差旅费用',
        40000: '摊销费用',
        20300: '其他试制',
        69900: '其他费用',
        1: '合计'
      },
      datas: [],
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      confirmLoading: false,
      visible: false,
      form: this.$form.createForm(this)
    }
  },
  created () {
  },
  methods: {
    handleSubmit () {
      this.$emit('ok', true)
      this.visible = false
    },
    loadTree () {
      this.$getTree('account')
        .then(res => {
          this.accountTree = res.tree
        })
    },
    onTreeChange (value, record) {
      this.$http.post('/workSheet/saveVoucherAccount', {
        projectId: record.projectId,
        rdType: record.rdType,
        month: record.month,
        accountId: value
      })
        .finally(res => {
          this.$emit('ok', true)
        })
    },
    show (datas, record) {
      this.title = '凭证号：' + record.workNo
      var debitAmount = 0
      var creditAmount = 0
      for (var i = 0; i < datas.length; i++) {
        debitAmount += datas[i].debitAmount
        creditAmount += datas[i].creditAmount
      }
      datas.push({
        rdType: 1,
        sum: true,
        debitAmount: debitAmount,
        creditAmount: creditAmount,
        id: 1111111111
      })
      this.datas = datas
      this.visible = true
    }
  }
}
</script>
