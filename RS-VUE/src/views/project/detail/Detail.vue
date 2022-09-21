<template>
  <a-spin :tip="tip" :spinning="spinning">
    <a-form layout="inline">
      <a-form-item label="记账日期">
        <a-date-picker v-model="queryParams.rdDate" format="YYYY-MM-DD" />
      </a-form-item>
      <a-form-item label="凭证号">
        <a-input v-model="queryParams.accNumber" placeholder="请输入凭证号" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="search(true)">查询</a-button>
      </a-form-item>
    </a-form>
    <div>
      <ystable
        ref="table"
        queryUrl="/rdAccountDetail/getList"
        :params="queryParams"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        @checkbox-all="selectCheckBoxChange"
        @checkbox-change="selectCheckBoxChange"
        :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
      >
        <template v-slot:toolbar_buttons>
          <a-button type="primary" style="margin-right: 10px;" @click="$refs.modifyModal.add(accType)">添加</a-button>
          <a-button type="primary" style="margin-right: 10px;" :disabled="selectedRowKeys.length <= 0" @click="delList">删除</a-button>
          <a-button type="primary" style="margin-right: 10px;" @click="$refs.uploadModal.show(tableField)">导入</a-button>
        </template>
        <vxe-table-column type="checkbox" width="40" align="center"></vxe-table-column>
        <vxe-table-column title="年" field="cyear" width="100" align="left">
          <template v-slot="{ row }">
            {{ renderDate(row, 'cyear') }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="月" field="cmonth" width="60" align="left">
          <template v-slot="{ row }">
            {{ renderDate(row, 'cmonth') }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="日" field="cday" width="60" align="left">
          <template v-slot="{ row }">
            {{ renderDate(row, 'cday') }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="凭证号" field="accNumber" width="120" align="left"></vxe-table-column>
        <vxe-table-column title="摘要" field="summary" min-width="200" align="left"></vxe-table-column>
        <vxe-table-column title="借方" field="debit" width="120" align="right"></vxe-table-column>
        <vxe-table-column title="贷方" field="credit" width="120" align="right"></vxe-table-column>
        <vxe-table-column title="方向" field="direction" width="100" align="left"></vxe-table-column>
        <vxe-table-column title="余额" field="balance" width="150" align="right"></vxe-table-column>
        <vxe-table-column title="操作" width="120" align="center" fixed="right">
          <template v-slot="{ row }">
            <span>
              <a href="javascript:;" @click="$refs.modifyModal.edit(row,accType)">编辑</a>
            </span>
            <a-divider type="vertical" />
            <a-popconfirm title="是否确定删除?" @confirm="handleDel(row.id)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ystable>
    </div>
    <rd-detail-modal @ok="ok=>search(ok)" ref="modifyModal" />
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      :paramData="paramData"
      paramKey="tableField"
      :title="`导入${typeName}明细帐`"
      ref="uploadModal"
      action="/doc/rdAccountDetail/importInfo"
      :templateName="`${typeName}明细帐模板`"
      @onSuccess="success"
      @error="error"
    />
  </a-spin>
</template>

<script>
import ystable from '@/components/Table/ystable'
import UploadModal from '@/components/UploadModal'
import rdDetailModal from './modules/RdDetailModal'
import yearMiXin from '@/utils/yearMixin'
import moment from 'moment'
const renderDate = (row, key) => {
  switch (key) {
    case 'cyear':
      return new Date(row.rdDate).getFullYear()
    case 'cmonth':
      return new Date(row.rdDate).getMonth() + 1
    case 'cday':
      return new Date(row.rdDate).getDate()
    default:
      return ''
  }
}
const columns = [{
  dataIndex: 'cyear',
  key: 'cyear',
  align: 'right',
  title: '年',
  customRender: (t, row, i) => renderDate(row, 'cyear'),
  width: 60
}, {
  dataIndex: 'cmonth',
  key: 'cmonth',
  align: 'right',
  title: '月',
  customRender: (t, row, i) => renderDate(row, 'cmonth'),
  width: 40
}, {
  dataIndex: 'cday',
  key: 'cday',
  align: 'right',
  title: '日',
  customRender: (t, row, i) => renderDate(row, 'cday'),
  width: 40
}, {
  dataIndex: 'accNumber',
  key: 'accNumber',
  align: 'left',
  title: '凭证号',
  width: 130
}, {
  dataIndex: 'summary',
  key: 'summary',
  align: 'left',
  title: '摘要',
  width: 300
}, {
  dataIndex: 'debit',
  key: 'debit',
  align: 'right',
  title: '借方',
  width: 130
}, {
  dataIndex: 'credit',
  key: 'credit',
  align: 'right',
  title: '贷方',
  width: 130
}, {
  dataIndex: 'direction',
  key: 'direction',
  align: 'center',
  title: '方向',
  width: 60
}, {
  dataIndex: 'balance',
  key: 'balance',
  align: 'right',
  title: '余额',
  width: 130
}, {
  dataIndex: 'action',
  key: 'action',
  align: 'center',
  title: '操作',
  scopedSlots: { customRender: 'action' },
  width: 130
}]

const tableField = {
  tableId: 'rdAccountDetailTable',
  fieldTitleObject: {
    cyear: { title: '年', defaultTitle: '年', importField: true },
    cmonth: { title: '月', defaultTitle: '月', importField: true },
    cday: { title: '日', defaultTitle: '日', importField: true },
    accNumber: { title: '凭证号', defaultTitle: '凭证号', importField: true },
    summary: { title: '摘要', required: true, defaultTitle: '摘要', importField: true },
    debit: { title: '借方', required: true, defaultTitle: '借方', importField: true },
    credit: { title: '贷方', required: true, defaultTitle: '贷方', importField: true },
    direction: { title: '方向', required: true, defaultTitle: '方向', importField: true },
    balance: { title: '余额', required: true, defaultTitle: '余额', importField: true }
  }
}
export default {
  mixins: [yearMiXin],
  name: 'Detail',
  components: {
    ystable,
    rdDetailModal,
    UploadModal
  },
  props: {
    accType: {
      type: Number,
      required: true
    },
    typeName: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      sampleData: [{
        cyear: '数字格式，例如：2019',
        cmonth: '数字格式，范围：1-12，例如：12',
        cday: '数字格式，范围：1-31，例如：12',
        accNumber: 'A-01',
        summary: '买电脑',
        debit: '数字格式，例如：300000',
        credit: '数字格式，例如：300000',
        direction: '借，平，贷，例如：借',
        balance: '数字格式，例如：300000'
      }],
      scroll: {},
      paramData: { accType: this.accType },
      queryParams: { accType: this.accType, startMonth: null, endMonth: null },
      selectedRowKeys: [],
      tip: '请稍后...',
      spinning: false,
      columns,
      tableField,
      getData: paramter => {
        var startMonth = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
        var endMonth = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
        this.queryParams.startMonth = startMonth
        this.queryParams.endMonth = endMonth
        return this.$http.get('/rdAccountDetail/getList', { params: Object.assign(paramter, this.queryParams) })
          .then(res => {
            return res.data
          })
      }
    }
  },
  created () {
    if (document.body.clientWidth < 1800) {
      this.scroll = { x: 1260 }
    }
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1800) {
          this.scroll = { x: 1260 }
        }
      })()
    }
    this.initParam()
  },
  methods: {
    renderDate,
    initParam () {
      var startMonth = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
      var endMonth = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
      this.queryParams.startMonth = startMonth
      this.queryParams.endMonth = endMonth
    },
    handleDel (id) {
      this.tip = '删除中，请稍后...'
      this.spinning = true
      this.$http.post('/rdAccountDetail/del', { id: id })
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.search(false)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
          }
        }).finally(res => {
          this.spinning = false
        })
    },
    delList () {
      this.tip = '删除中，请稍后...'
      this.spinning = true
      var values = []
      this.selectedRowKeys.forEach(id => {
        values.push({ id: id })
      })
      this.$http.post('/rdAccountDetail/dels', values)
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.search(false)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
          }
        }).finally(res => {
          this.spinning = false
        })
    },
    selectChange (keys) {
      this.selectedRowKeys = keys
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.initParam()
      this.$refs.table.refresh(refresh)
    },
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          content: resData,
          onOk () {

          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
      }
      this.search(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
    }
  }
}
</script>
<style>
</style>
