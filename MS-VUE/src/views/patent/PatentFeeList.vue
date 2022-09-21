<template>
  <a-spin tip="请稍后..." :spinning="spin">
    <a-card :bordered="false">
      <a-form layout="inline">
        <a-form-item label="专利号">
          <a-input v-model="queryParam.patentNo" placeholder="请输入专利号" style="width: 180px" />
        </a-form-item>
        <a-form-item label="缴费期限">
          <a-select
            v-model="queryParam.type"
            :dropdownStyle="{ width: '550px' }"
            placeholder
            :allowClear="true"
            style="width: 180px"
          >
            <a-select-option value="1">已过缴费期限</a-select-option>
            <a-select-option value="2">距离缴费期限为0-15天</a-select-option>
            <a-select-option value="3">距离缴费期限为16-30天</a-select-option>
            <a-select-option value="4">距离缴费期限为31-60天</a-select-option>
            <a-select-option value="5">距离缴费期限60天以上</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="redata" v-if="$auth('patent:PatentFeeList:search')">查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          queryUrl="/rsPatentCost/queryPatentCostList"
          :params="queryParam"
          @completed="completed"
          highlight-hover-row
          show-overflow
          resizable
          ref="table"
          :toolbar="{
            refresh: true,
            zoom: true,
            custom: true,
            slots: {
              buttons: 'toolbar_buttons'
            }
          }"
          @checkbox-all="selectAllEvent"
          @checkbox-change="selectChangeEvent"
        >
          <template v-slot:toolbar_buttons>
            <span style="margin-right: 10px;" v-if="$auth('patent:PatentFeeList:setRemark')">
              <a-button
                type="primary"
                @click="$refs.editRemark.add(rsEntityList)"
                :disabled="selectedRowKeys.length <= 0"
              >设置备注</a-button>
            </span>
            <span style="margin-right: 10px;" v-if="$auth('patent:PatentFeeList:setReminderdate')">
              <a-button
                type="primary"
                @click="$refs.editOneData.edit(rsEntityList)"
                :disabled="selectedRowKeys.length <= 0"
              >设置提醒日期</a-button>
            </span>
            <span style="margin-right: 10px;" v-if="$auth('patent:PatentFeeList:del')">
              <a-button type="primary" :disabled="selectedRowKeys.length <= 0" @click="dels">删除</a-button>
            </span>
            <span style="margin-right: 10px;" v-if="$auth('patent:PatentFeeList:export')">
              <a-button type="primary" @click="exportFile" :loading="exportLoading">导出</a-button>
            </span>
          </template>
          <vxe-table-column type="checkbox" width="40" align="center" fixed="left"/>
          <vxe-table-column
            field="patentNo"
            width="160"
            title="专利号"
            align="center"
            remoteSort
            fixed="left"></vxe-table-column>
          <vxe-table-column
            field="patentName"
            width="230"
            title="专利名称"
            align="center"
            remoteSort
            fixed="left"></vxe-table-column>
          <vxe-table-column field="costType" width="200" title="费用类型" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="limitDate" width="160" title="缴费期限" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="amount" width="100" title="缴费金额" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="mainTypeNo" width="260" title="主分类号" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="companyName" width="260" title="客户名称" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="pay" width="100" title="是否缴费" align="center" remoteSort>
            <template v-slot="{ row }">
              {{ row.pay? '是': '否' }}
            </template>
          </vxe-table-column>
          <vxe-table-column field="payDateTime" width="120" title="缴费日期" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="remind" width="100" title="是否提醒" align="center" remoteSort>
            <template v-slot="{ row }">
              {{ row.pay? '是': '否' }}
            </template>
          </vxe-table-column>
          <vxe-table-column width="160" title="专利权人" align="center">-</vxe-table-column>
          <vxe-table-column field="remindDateTime" width="160" title="提醒日期" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="remark" width="100" title="备注" align="center" remoteSort></vxe-table-column>
          <vxe-table-column width="100" title="操作" align="center" fixed="right">
            <template v-slot="{ row }">
              <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)">
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <editRemark-modal ref="editRemark" @ok="handleOk"></editRemark-modal>
      <editOneData-modal ref="editOneData" @ok="handleOk"></editOneData-modal>
    </a-card>
  </a-spin>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, Ellipsis } from '@/components'
import EditRemarkModal from './modules/EditRemarkModal'
import EditOneDataModal from './modules/EditOneDataModal'
const columns = [{
  dataIndex: 'patentNo',
  key: 'patentNo',
  title: '专利号',
  align: 'left',
  width: '160px',
  fixed: 'left'
}, {
  dataIndex: 'patentName',
  key: 'patentName',
  title: '专利名称',
  align: 'left',
  width: '230px',
  fixed: 'left'
}, {
  dataIndex: 'costType',
  key: 'costType',
  title: '费用类型',
  align: 'left',
  width: '200px'
}, {
  dataIndex: 'limitDate',
  key: 'limitDate',
  title: '缴费期限',
  align: 'center',
  width: '160px'
},
{
  dataIndex: 'amount',
  key: 'amount',
  title: '缴费金额',
  align: 'right',
  width: '100px'
}, {
  dataIndex: 'mainTypeNo',
  key: 'mainTypeNo',
  title: '主分类号',
  align: 'center',
  width: '260px'
}, {
  dataIndex: 'companyName',
  key: 'companyName',
  title: '客户名称',
  align: 'center',
  width: '260px'
}, {
  dataIndex: 'pay',
  key: 'pay',
  title: '是否缴费',
  align: 'center',
  width: '80px',
  customRender: function (text, record, index) {
    if (text) {
      return '是'
    } else {
      return '否'
    }
  }
}, {
  dataIndex: 'payDateTime',
  key: 'payDateTime',
  title: '缴费日期',
  align: 'center',
  width: '120px'
}, {
  dataIndex: 'remind',
  key: 'remind',
  title: '是否提醒',
  align: 'center',
  width: '80px',
  customRender: function (text, record, index) {
    if (text) {
      return '是'
    } else {
      return '否'
    }
  }
},
{
  dataIndex: 'remindDateTime',
  key: 'remindDateTime',
  title: '提醒日期',
  align: 'center',
  width: '160px'
}, {
  dataIndex: 'remark',
  key: 'remark',
  title: '备注',
  align: 'center',
  width: '100px',
  scopedSlots: { customRender: 'remarkVal' }
},
{
  title: '操作',
  key: 'action',
  align: 'center',
  width: '100px',
  fixed: 'right',
  scopedSlots: { customRender: 'action' }
}]

export default {
  name: 'PatentFeeList',
  components: {
    STable,
    Ellipsis,
    EditRemarkModal,
    EditOneDataModal,
    ystable
  },
  data () {
    return {
      spin: false,
      form: this.$form.createForm(this),
      columns,
      pagination: [],
      type: undefined,
      selectedRowKeys: [],
      rsEntityList: [],
      queryParam: {},
      value: 0,
      initializeData: parameter => {
        return this.$http.get('/rsPatentCost/queryPatentCostList', { params: Object.assign(parameter, this.queryParam) })
          .then(res => {
            return res.data
          })
      },
      options: {},
      exportLoading: false
    }
  },
  methods: {
    handleOk (flag) {
      if (flag) {
        this.$message.success('设置成功')
      }
      this.$refs.table.refresh(false)
    },
    handleDel (record) {
      this.$http.post('/rsPatentCost/delPatentCost', { id: record.id }).then(res => {
        this.$message.success('删除成功')
      }).finally(res => {
        this.$refs.table.refresh(false)
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.rsEntityList = selectedRows
    },
    redata () {
      this.$refs.table.refresh(true)
      this.selectedRowKeys = []
    },
    exportFile () {
      this.exportLoading = true
      // this.$exportData('/projectProgress/export', this.getParams(), `归集审核列表.xls`, this.$message)
      this.$exportData('/rsPatentCost/export', this.queryParam, '专利费用列表.xls', this.$message).finally(() => {
        this.exportLoading = false
      })
    },
    dels () {
      this.spin = true
      var values = []
      this.selectedRowKeys.forEach(id => {
        values.push({ id: id })
      })
      this.$http.post('/rsPatentCost/deletePatentCosts', values)
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.selectedRowKeys = []
            this.$refs.table.refresh(false)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
          }
        }).finally(() => {
          this.spin = false
        })
    },
    selectChangeEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
      this.rsEntityList = records
    },
    selectAllEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
      this.rsEntityList = records
    },
    completed () {
      this.selectedRowKeys = []
      this.rsEntityList = []
    }
  }
}
</script>
<style scoped>
</style>
