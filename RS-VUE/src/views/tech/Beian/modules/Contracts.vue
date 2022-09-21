<!--
 * @Author: ldx
 * @Date: 2021-03-19 11:34:59
 * @LastEditTime: 2021-04-01 16:22:16
 * @LastEditors: ldx
 * @Description: 合同列表
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\Contracts.vue
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
      :toolbar="{custom: true }"
    >
      <template #buttons>
        <a-button size="small" type="primary" @click="onAdd" v-if="$auth('tech:beian:investments:relatedContract')">添加</a-button>
      </template>
      <vxe-table-column field="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column title="合同编号" field="contractNo" width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="合同日期" field="contractDate" width="120" align="center" header-align="center">
        <template #default="{row}">
          {{ moment(row.contractDate).format('YYYY-MM-DD') }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="设备供应商" field="signTarget" min-width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column
        title="设备名称"
        field="ename"
        width="120"
        align="left"
        header-align="center"></vxe-table-column>
      <vxe-table-column title="规程型号" field="emodal" width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="数量" field="quantity" width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="金额" field="amount" width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="是否关联方购置" field="partPurchase" width="120" align="left" header-align="center">
        <template #default="{ row }">{{ row.partPurchase ? '是' : '否' }}</template>
      </vxe-table-column>
      <vxe-table-column title="是否二手" field="secondHand" width="120" align="left" header-align="center">
        <template #default="{ row }">{{ row.secondHand ? '是' : '否' }}</template>
      </vxe-table-column>
      <vxe-table-column title="是否经销商购置" field="traderPurchase" width="120" align="left" header-align="center">
        <template #default="{ row }">{{ row.traderPurchase ? '是' : '否' }}</template>
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
  </div>
</template>

<script>
import { delContractDetail, getContracts } from '@/api/tech/BeiAnGuanLi/Invest'
import { ystable } from '@/components/'
import { cloneDeep } from 'lodash'
import moment from 'moment'
import ChoiceModal from './contractModules/ChoiceModal'
export default {
  name: 'Contracts',
  components: {
    ChoiceModal,
    ystable
  },
  props: {
    investRecord: {
      type: Object,
      default: () => { return {} }
    }
  },
  data () {
    return {
      tableDatas: [ ]
    }
  },
  mounted () {
    // this.onloadTableData()
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
      this.$refs.choiceModal.show('选择合同', ids, cloneDeep(this.tableDatas))
    },
    onEdit (record) {

    },
    resetTableData () {
      this.tableDatas = []
    },
    updateTable (selectedRecords) {
      this.tableDatas = cloneDeep(selectedRecords)
    },
    onloadTableData (investmentId) {
      getContracts({ investmentId: investmentId }).then((response) => {
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
      delContractDetail([record]).then((res) => {
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
