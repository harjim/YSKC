<!--
 * @Author: ldx
 * @Date: 2021-03-19 11:49:52
 * @LastEditTime: 2021-10-25 15:17:30
 * @LastEditors: lzh
 * @Description: 发票选择modal
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\invoiceModules\ChoiceModal.vue
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
    <a-spin id="spin" :spinning="spinning" tip="加载中...">
      <a-form layout="inline">
        <a-form-item label="发票号">
          <a-input v-model="queryParams.invoiceNo" placeholder="发票号"></a-input>
        </a-form-item>
        <!-- <a-form-item label="开具方">
        <a-input v-model="queryParams.no" placeholder="开具方"></a-input>
      </a-form-item> -->
        <a-form-item label="设备名称">
          <a-input v-model="queryParams.ename" placeholder="设备名称"></a-input>
        </a-form-item>
        <!-- <a-form-item label="规格型号">
        <a-input v-model="queryParams.no" placeholder="规格型号"></a-input>
      </a-form-item> -->
        <a-form-item>
          <a-button type="primary" @click="onQuery" style="margin-right: 8px;">查询</a-button>

        </a-form-item>
      </a-form>
      <ystable
        ref="table"
        queryUrl="/inverstment/getInvoiceDetails"
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
          <a-button type="primary" @click="onAdd" style="margin-right: 8px;" v-if="$auth('tech:beian:investments:saveInvocie')">新增发票</a-button>
        </template>
        <vxe-table-column type="checkbox" width="60" align="center" header-align="center"></vxe-table-column>
        <vxe-table-column field="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
        <vxe-table-column title="发票号" field="invoiceNo" min-width="120" align="left" header-align="center">
          <template #default="{row}">
            <a @click="onEdit(row)" title="点击编辑发票">{{ row.invoiceNo }}</a>
          </template>
        </vxe-table-column>
        <vxe-table-column title="设备名称" field="ename" min-width="150" align="left" header-align="center"></vxe-table-column>
        <vxe-table-column title="规格型号" field="emodal" min-width="150" align="left" header-align="center"></vxe-table-column>
        <vxe-table-column title="数量" field="quantity" width="90" align="right" header-align="center"></vxe-table-column>
        <vxe-table-column title="金额（元/含税）" field="taxAmount" width="120" align="right" header-align="center"></vxe-table-column>
        <vxe-table-column title="金额（元）" field="excludeTaxAmount" width="100" align="right" header-align="center"></vxe-table-column>
        <vxe-table-column title="开具方" field="invoiceFrom" width="150" align="left" header-align="center"></vxe-table-column>
        <vxe-table-column title="发票日期" field="invoiceDate" width="120" align="center" header-align="center">
          <template #default="{row}">
            {{ moment(row.invoiceDate).format("YYYY-MM-DD") }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="记账凭证字号" field="voucherNo" width="120" align="left" header-align="center"></vxe-table-column>
        <vxe-table-column title="转固日期" field="voucherDate" width="120" align="center" header-align="center">
          <template #default="{row}">
            {{ moment(row.voucherDate).format("YYYY-MM-DD") }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="操作" width="100" align="center" header-align="center" fixed="right">
          <template #default="{row}">
            <a-popconfirm title="您确定要删除吗？" @confirm="handleDel(row)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ystable>
      <add-invoice-modal v-bind="$attrs" ref="addInvoiceModal" @refresh="onQuery" @setDelIds="setDelIds"></add-invoice-modal>
    </a-spin>
  </a-modal>
</template>
<script>
import { getInvoiceDetails, delInvoiceInfo } from '@/api/tech/BeiAnGuanLi/Invest'
import { ystable } from '@/components'
import AddInvoiceModal from './AddInvoiceModal'
import moment from 'moment'
import { cloneDeep } from 'lodash'
export default {
  name: 'ChoiceModel',
  components: {
    ystable,
    AddInvoiceModal
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
      defaultSelecteRows: [],
      theFirstTime: true,
      delIds: [],
      parentTableData: []
    }
  },
  methods: {
    moment,
    completed (data) {
      this.tableDatas = data
      if (data && data.length) {
        this.handleSelected(data, this.selectedIds)
      }
    },
    show (title, ids, parentTableData) {
      this.queryParams.investmentId = this.investRecord.id
      this.isVisible = true
      this.title = title
      this.selectedIds = ids
      this.parentTableData = parentTableData
      if (!this.theFirstTime) {
        this.onQuery()
        if (this.tableDatas && this.tableDatas.length) {
          this.handleSelected(this.tableDatas, this.selectedIds)
        }
      }
      this.theFirstTime = false
    },
    onAdd () {
      this.$refs.addInvoiceModal.show('添加发票信息')
    },
    onQuery () {
      this.$refs.table.refresh(true)
      if (this.tableDatas && this.tableDatas.length) {
        this.handleSelected(this.tableDatas, this.selectedIds)
      }
    },
    onEdit (record) {
      this.$refs.addInvoiceModal.edit(`编辑发票 【发票号：${record.invoiceNo}】`, record)
    },
    onConfirmDel (row) {
      const params = [{ invoiceId: row.invoiceId, id: row.id }]
      delInvoiceInfo(params).then(response => {
        if (response.success && response.data) {
          this.onQuery()
        } else {
          console.log(`response errorCode ${response.errorCode} : ${response.errorMessage}`)
        }
      }).catch(error => {
        console.log('onDel function error', error)
      })
    },
    afterClose () {
      this.spinning = false
      this.isVisible = false
      this.title = ''
      this.queryParams = {}
      this.tableDatas = []
      this.selectedIds = []
      this.$emit('setDelIds', this.delIds)
      this.delIds = []
      this.parentTableData = []
    },
    handleSubmit () {
      this.$emit('updateTable', this.parentTableData)
      this.isVisible = false
      this.parentTableData = []
    },
    checkboxChange ({ records, reserves, checked, row, rowIndex }) {
      console.log('checkboxChange', records)
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
      getInvoiceDetails(this.queryParams).then((response) => {
        if (response.success && response.data) {
          this.tableDatas = response.data
        }
      }).catch(error => {
        console.log('onloadTableData function error', error)
      }).finally(() => {
        this.spinning = false
      })
    },
    handleSelected (tableData, selectedIds) {
      const rows = []
      tableData.forEach((row, index) => {
        selectedIds.forEach(id => {
          if (row.id === id) {
            rows.push(tableData[index])
          }
        })
      })
      this.$refs.table.setCheckboxRow(rows, true)
    },
    setDelIds (delIds) {
      this.delIds = delIds
    },
    handleDel (record) {
      this.$http.post('/inverstment/delInvoiceDetail', [{
        id: record.id,
        invoiceId: record.invoiceId
      }]).then(res => {
        if (res.data && res.success) {
          this.$message.success('删除成功')
          this.$refs.table.refresh(true)
        } else {
          this.$message.error(res.errorMessage || '删除失败')
        }
      }).catch()
    }
  }
}
</script>
<style lang='less' scoped>
#spin {
  height: 100%;
  width: 100%;
  & /deep/ .ant-spin-nested-loading {
    height: 100%;
  }
  & /deep/ .ant-spin-container {
    height:  100%;
  }
}
</style>
