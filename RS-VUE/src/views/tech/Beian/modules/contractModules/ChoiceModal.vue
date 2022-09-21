<!--
 * @Author: ldx
 * @Date: 2021-03-19 11:49:52
 * @LastEditTime: 2021-10-20 10:04:35
 * @LastEditors: zdf
 * @Description: 合同选择modal
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\contractModules\ChoiceModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="900"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    style="top: 0px;"
    @ok="handleSubmit">
    <a-form layout="inline">
      <a-form-item label="合同编号">
        <a-input v-model="queryParams.contractNo" style="width:150px;" placeholder="合同编号"></a-input>
      </a-form-item>
      <a-form-item label="设备名称">
        <a-input v-model="queryParams.ename" style="width:150px;" placeholder="设备名称"></a-input>
      </a-form-item>
      <a-form-item label="规格型号">
        <a-input v-model="queryParams.emodal" style="width:150px;" placeholder="规格型号"></a-input>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="onQuery" style="margin-right: 8px;">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      queryUrl="/inverstment/getContractDetail"
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
        <a-button type="primary" @click="onAdd" style="margin-right: 8px;" v-if="$auth('tech:beian:investments:saveContract')">新增合同</a-button>
      </template>
      <vxe-table-column type="checkbox" width="60" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column
        field="seq"
        title="序号"
        width="60"
        align="center"
        header-align="center"></vxe-table-column>
      <vxe-table-column title="合同编号" field="contractNo" width="120" align="left" header-align="center">
        <template #default="{row}">
          <a @click="onEdit(row)" title="点击编辑合同">{{ row.contractNo }}</a>
        </template>
      </vxe-table-column>
      <vxe-table-column title="合同日期" field="contractDate" width="120" align="center" header-align="center">
        <template #default="{row}">
          {{ moment(row.contractDate).format("YYYY-MM-DD") }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="设备名称" field="ename" min-width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="规格型号" field="emodal" width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="设备供应商" field="signTarget" min-width="120" align="left" header-align="center"></vxe-table-column>
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
    </ystable>
    <add-contract-modal v-bind="$attrs" ref="addContractModal" @refresh="onQuery"></add-contract-modal>
  </a-modal>
</template>
<script>
import { delContractInfo, getContractDetail } from '@/api/tech/BeiAnGuanLi/Invest'
import { ystable } from '@/components'
import { cloneDeep } from 'lodash'
import moment from 'moment'
import AddContractModal from './AddContractModal'
export default {
  name: 'ChoiceModel',
  components: {
    ystable,
    AddContractModal
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
      this.$refs.addContractModal.show('添加合同信息')
    },
    onEdit (record) {
      this.$refs.addContractModal.edit(`编辑合同信息 【${record.contractNo}】`, record)
    },
    onConfirmDel (row) {
      const params = [{ contractId: row.contractId, id: row.id }]
      delContractInfo(params).then(response => {
        if (response.success && response.data) {
          this.onQuery()
        } else {
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
      this.isVisible = false
      this.parentTableData = []
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
      getContractDetail(this.queryParams).then((response) => {
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
