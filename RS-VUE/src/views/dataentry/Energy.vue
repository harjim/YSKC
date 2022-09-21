<template>
  <a-card :bordered="false">
    <a-spin
      tip="请稍后..."
      :spinning="spinning"
    >
      <div>
        <a-form
          layout="inline"
          :form="form"
        >
          <a-form-item label="能源名称">
            <a-input
              style="width:165px;"
              v-model="queryParams.ename"
              placeholder="请输入能源名称"
            />
          </a-form-item>
          <a-form-item label="部门">
            <a-input
              style="width:165px;"
              v-model="queryParams.deptName"
              placeholder="请输入部门"
            />
          </a-form-item>
          <a-form-item label="发生月份">
            <a-select
              style="width:165px;"
              v-model="queryParams.monthValue"
              placeholder="请选择月份"
              :allowClear="true"
              :options="monthOptions"
            ></a-select>
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              v-if="control.search"
              @click="search(true)"
            >查询</a-button>
          </a-form-item>
        </a-form>
      </div>
      <div>
        <ystable
          ref="table"
          queryUrl="/energy/queryAll"
          :params="getParams()"
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          :toolbar="tableToolbar"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
        >
          <template v-slot:buttons>
            <a-button
              type="primary"
              v-if="control.add"
              @click="$refs.createModal.add(etype)"
              style="margin-right:10px"
            >添加</a-button>
            <a-button
              type="primary"
              v-if="control.import"
              @click="$refs.uploadModal.show(tableField)"
              style="margin-right:10px"
            >导入</a-button>
            <a-button
              type="primary"
              v-if="control.export"
              @click="exportEnergy"
              style="margin-right:10px"
            >导出</a-button>
            <a-button
              type="primary"
              v-if="control.del"
              :disabled="selectedRowKeys.length <= 0"
              @click="delList"
              style="margin-right:10px"
            >删除</a-button>
          </template>
          <vxe-table-column
            type="checkbox"
            width="40"
            fixed="left"
          />
          <vxe-table-column
            :title="etype === 20100 ? '动力名称' : '燃料名称'"
            field="ename"
            width="140"
            remoteSort
            align="left"
            fixed="left"
          ></vxe-table-column>
          <vxe-table-column
            title="凭证号"
            field="accNumber"
            width="130"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="部门"
            field="deptName"
            width="140"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="发生日期"
            field="occDate"
            width="140"
            remoteSort
            align="center"
          ></vxe-table-column>
          <vxe-table-column
            title="单价"
            field="unitPrice"
            width="110"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="数量"
            field="quantity"
            width="110"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="金额"
            field="totalAmount"
            width="120"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="分配金额"
            field="amount"
            width="120"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="单位"
            field="unit"
            width="100"
            remoteSort
            align="center"
          ></vxe-table-column>
          <vxe-table-column
            title="科目"
            field="fullAccountName"
            width="160"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="备注"
            field="remark"
            min-width="140"
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="操作"
            field="action"
            width="120"
            align="center"
            fixed="right"
          >
            <template v-slot="{ row }">
              <span v-if="control.edit">
                <a
                  href="javascript:;"
                  @click="$refs.createModal.edit(row)"
                >编辑</a>
              </span>
              <span v-if="control.del">
                <a-divider
                  type="vertical"
                  v-if="control.edit"
                />
                <a-popconfirm
                  title="是否确定删除?"
                  @confirm="handleDel(row)"
                >
                  <a>删除</a>
                </a-popconfirm>
              </span>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <energy-modal
        ref="createModal"
        @ok="handleOk"
      ></energy-modal>
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        :paramData="paramData"
        paramKey="name"
        :title="importTitle"
        ref="uploadModal"
        action="/doc/energy/importEnergy"
        :templateName="templateName"
        @onSuccess="success"
        @error="error"
      />
    </a-spin>
  </a-card>
</template>

<script>
import { UploadModal, Ellipsis, EditTableTitle } from '@/components'
import EnergyModal from './modules/EnergyModal'
import yearMiXin from '@/utils/yearMixin'
import moment from 'moment'
import ystable from '@/components/Table/ystable'

const columns = [
  {
    dataIndex: 'accNumber',
    key: 'accNumber',
    align: 'left',
    scopedSlots: { title: 'accNumber' },
    width: 130
  }, {
    dataIndex: 'ename',
    key: 'ename',
    align: 'left',
    scopedSlots: { title: 'ename' },
    width: 130
  }, {
    dataIndex: 'deptName',
    key: 'deptName',
    align: 'center',
    scopedSlots: { title: 'deptName' },
    width: 130
  }, {
    dataIndex: 'occDate',
    key: 'occDate',
    align: 'center',
    scopedSlots: { title: 'occDate' },
    width: 130
  }, {
    dataIndex: 'unitPrice',
    key: 'unitPrice',
    align: 'right',
    scopedSlots: { title: 'unitPrice' },
    width: 100
  }, {
    dataIndex: 'quantity',
    key: 'quantity',
    align: 'right',
    scopedSlots: { title: 'quantity' },
    width: 100
  }, {
    dataIndex: 'amount',
    key: 'amount',
    align: 'right',
    scopedSlots: { title: 'amount' },
    width: 130
  }, {
    dataIndex: 'fullAccountName',
    key: 'fullAccountName',
    align: 'left',
    scopedSlots: {
      customRender: 'accountVal',
      title: 'fullAccountName'
    },
    width: 130
  }, {
    dataIndex: 'unit',
    key: 'unit',
    align: 'left',
    scopedSlots: { title: 'unit' },
    width: 80
  }, {
    dataIndex: 'remark',
    key: 'remark',
    align: 'left',
    scopedSlots: {
      customRender: 'remarkVal',
      title: 'remark'
    }
  }, {
    title: '操作',
    key: 'action',
    align: 'center',
    scopedSlots: { customRender: 'action' },
    width: 150
  }]

export default {
  mixins: [yearMiXin],
  name: 'Energy',
  components: {
    UploadModal,
    EnergyModal,
    Ellipsis,
    EditTableTitle,
    ystable
  },
  props: {
    etype: {
      type: Number,
      required: true
    },
    typeName: {
      type: String,
      required: true
    },
    control: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      tableToolbar: {
        refresh: true,
        zoom: true,
        custom: true
      },
      templateName: this.typeName + '模板',
      scroll: {},
      importTitle: '导入' + this.typeName,
      paramData: { type: this.etype, year: this.currentYear },
      spinning: false,
      form: this.$form.createForm(this),
      queryParams: { type: this.etype, monthValue: undefined },
      columns,
      tableField: {
        tableId: `${this.etype === 20100 ? 'fuel' : 'stimulus'}Table`,
        fieldTitleObject: {
          accNumber: { title: '凭证号', defaultTitle: '凭证号', importField: true },
          ename: { title: `${this.etype === 20100 ? '动力' : '燃料'}名称`, required: true, defaultTitle: `${this.etype === 20100 ? '动力' : '燃料'}名称`, importField: true },
          deptName: { title: '部门', defaultTitle: '部门', importField: true },
          occDate: { title: '发生日期', required: true, defaultTitle: '发生日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          unitPrice: { title: '单价', defaultTitle: '单价', importField: true },
          quantity: { title: '数量', defaultTitle: '数量', importField: true },
          totalAmount: { title: '金额', required: true, defaultTitle: '金额', importField: true },
          amount: { title: '分配金额', required: true, defaultTitle: '分配金额', importField: true },
          fullAccountName: { title: '科目', defaultTitle: '科目', importField: true },
          unit: { title: '单位', defaultTitle: '单位', importField: true },
          remark: { title: '备注', defaultTitle: '备注', importField: true }
        }
      },
      sampleData: [
        {
          ename: `${this.etype === 20100 ? '电费' : '煤气'}`,
          deptName: 'xxx部门',
          occDate: '格式："年-月-日"，例如：2019-10-11',
          unitPrice: '数字格式，例如：300000',
          quantity: '数字格式，例如：300000',
          amount: '数字格式，例如：300000',
          unit: '个',
          fullAccountName: '科目',
          accNumber: 'XX-2020XX-XX',
          remark: '备注'
        }
      ],
      selectedRowKeys: [],
      getData: parameter => {
        this.initParam()
        return this.$http.get('/energy/queryAll', { params: Object.assign(parameter, this.queryParams) })
          .then(res => {
            return res.data
          })
      }
    }
  },
  created () {
    this.getTableField()
    if (document.body.clientWidth < 1780) {
      this.scroll = { x: 1800 }
    }
    this.paramData.year = this.currentYear
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1780) {
          this.scroll = { x: 1800 }
        }
      })()
    }
  },
  methods: {
    getParams () {
      this.initParam()
      return this.queryParams
    },
    moment,
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.search(flag)
    },
    getTableField () {
      this.$http.get('/sysDictionary/getTableField', { params: { tableId: this.tableField.tableId } })
        .then(res => {
          if (res.success && res.data != null) {
            this.tableField = res.data
          }
        })
    },
    onSaveTitle (value, name) {
      this.tableField.fieldTitleObject[name].title = value
      this.tableField.fieldTitle = JSON.stringify(this.tableField.fieldTitleObject)
      this.$http.post('/sysDictionary/saveTableField', this.tableField).then(res => {
        this.$message.success('保存成功')
        this.getTableField()
      })
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
      this.$message.error(`${fileName} 导入失败${msg ? ':' + msg : ''} `)
    },
    handleDel (record) {
      this.spinning = true
      this.$http.get('/energy/delEnergy', { params: { id: record.id } })
        .then(res => {
          if (res.success) {
            this.$message.success('删除成功')
            this.search(false)
          } else {
            this.$message.error(`[${record.ename}]${res.errorMessage ? res.errorMessage : '删除失败'} `)
          }
          this.spinning = false
        })
    },
    delList () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的能源吗?',
        content: <b>已被加计扣除使用的能源不会删除。</b>,
        onOk () {
          var values = {}
          values.ids = self.selectedRowKeys
          return self.$http.post('/energy/delEnergys', values)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.search(false)
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.initParam()
      this.$refs.table.refresh(refresh)
      // this.paramData.year = this.currentYear
    },
    initParam () {
      if (this.queryParams.monthValue === Number.undefined || this.queryParams.monthValue === '-1') {
        var startMonth = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
        var endMonth = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
        this.queryParams.startMonth = startMonth
        this.queryParams.endMonth = endMonth
      } else {
        this.queryParams.startMonth = moment([this.currentYear, this.queryParams.monthValue, 1, 0, 0, 0, 0])
        var value = parseInt(this.queryParams.monthValue)
        if (value <= 12) {
          this.queryParams.endMonth = moment([this.currentYear, parseInt(this.queryParams.monthValue), 1, 0, 0, 0, 0])
        } else {
          this.queryParams.endMonth = moment([this.currentYear + 1, this.queryParams.monthValue, 1, 0, 0, 0, 0])
        }
      }
    },
    exportEnergy () {
      this.spinning = true
      this.initParam()
      this.$http.get('/energy/exportEnergy', { params: this.queryParams })
        .then(res => {
          if (res.success) {
            var keys = Object.keys(this.tableField.fieldTitleObject).map(a => this.tableField.fieldTitleObject[a].title)
            var sheetData = res.data
            var sheetFilter = Object.keys(this.tableField.fieldTitleObject)
            this.$exportJsonData(this.typeName + '列表数据', keys, sheetFilter, sheetData)
          } else {
            this.$message.error(`导出失败${res.errorMessage ? ':' + res.errorMessage : ''}`)
          }
        }).finally(() => {
          this.spinning = false
        })
    },
    onSelectChange (selectedRowKeys, rows) {
      this.selectedRowKeys = selectedRowKeys
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    }
  }
}
</script>

<style>
.table-page-search-wrapper .ant-form-inline .ant-form-item > .ant-form-item-label {
  width: 33%;
}
</style>
