<template>
  <a-card :bordered="false">
    <a-spin
      tip="请稍后..."
      :spinning="spinning"
    >
      <a-form
        layout="inline"
        class="dept_user_search"
        :form="form"
      >
        <a-form-item label="记账日期">
          <a-date-picker
            style="width: 100%"
            format="YYYY-MM-DD"
            v-model="accDate"
            :defaultPickerValue="queryParam.defaultPickerValue"
            :disabledDate="disabledDate"
            placeholder="请选择记账日期"
          />
        </a-form-item>
        <a-form-item label="部门">
          <a-input
            v-model="deptName"
            placeholder="请输入部门"
          />
        </a-form-item>
        <a-form-item label="摘要">
          <a-input
            v-model="summary"
            placeholder="请输入摘要"
          />
        </a-form-item>
        <a-form-item label="凭证号">
          <a-input
            v-model="accNumber"
            placeholder="请输入凭证号"
          />
        </a-form-item>
        <a-form-item
          label="费用类型"
          v-if="type.length > 1"
        >
          <a-select
            v-model="qtype"
            placeholder="请选择费用类型"
            :allowClear="true"
            style="width: 174px;"
          >
            <template v-if="type.length === 3">
              <a-select-option value="40000">软件摊销</a-select-option>
              <a-select-option value="40100">专利摊销</a-select-option>
              <a-select-option value="40200">其他摊销</a-select-option>
            </template>
            <template v-else>
              <a-select-option value="20700">样机</a-select-option>
              <a-select-option value="60000">资料</a-select-option>
              <a-select-option value="60100">研发成果</a-select-option>
              <a-select-option value="60200">知识产权</a-select-option>
              <a-select-option value="60300">福利</a-select-option>
              <a-select-option value="69900">其他</a-select-option>
            </template>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-button
            v-if="view"
            type="primary"
            @click="$refs.table.refresh(true)"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div v-if="view">
        <ystable
          ref="table"
          queryUrl="/inspection/queryAll"
          :params="getParams()"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          :toolbar="tableToolbar"
        >
          <template v-slot:buttons>
            <a-button
              type="primary"
              v-if="addRecord"
              @click="$refs.createModal.add(type, typeName)"
              style="margin-right: 10px"
            >
              添加
            </a-button>
            <a-button
              type="primary"
              v-if="importRecord"
              @click="openUploadModal"
              style="margin-right: 10px"
            >
              导入
            </a-button>
            <a-button
              type="primary"
              v-if="exportRecord"
              @click="exportInspection"
              style="margin-right: 10px"
            >
              导出
            </a-button>
            <a-button
              type="primary"
              v-if="delRecord"
              :disabled="selectedRowKeys.length <= 0"
              @click="delInspection"
              style="margin-right: 10px"
            >
              删除
            </a-button>
            <a-button
              type="primary"
              v-if="type.length === 3 && setFeeType"
              @click="$refs.modal.add(entityList)"
              :disabled="selectedRowKeys.length <= 0"
              style="margin-right: 10px"
            >
              设置费用类型
            </a-button>
          </template>
          <vxe-table-column
            type="checkbox"
            width="40"
            fixed="left"
          />
          <vxe-table-column
            title="凭证号"
            field="accNumber"
            width="150"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="摘要"
            field="summary"
            width="280"
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
            title="费用"
            field="expense"
            width="120"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="记账日期"
            field="accDate"
            width="140"
            remoteSort
            align="center"
          ></vxe-table-column>
          <vxe-table-column
            title="费用类型"
            field="typeName"
            v-if="type.length > 1"
            width="120"
            remoteSort
            align="center"
          >
            <template v-slot="{ row }">{{ getype(row.type) }}</template>
          </vxe-table-column>
          <vxe-table-column
            v-if="type == 60400"
            title="姓名"
            field="ename"
            width="100"
            remoteSort
            align="center"
          ></vxe-table-column>
          <vxe-table-column
            title="科目"
            field="fullAccountName"
            width="200"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="备注"
            field="remark"
            min-width="150"
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="操作"
            field="center"
            width="120"
            align="center"
            fixed="right"
          >
            <template v-slot="{ row }">
              <span v-if="editRecord">
                <a
                  href="javascript:;"
                  @click="$refs.createModal.edit(row, type, typeName)"
                >编辑</a>
                <a-divider
                  type="vertical"
                  v-if="delRecord"
                />
              </span>
              <a-popconfirm
                title="是否确定删除?"
                @confirm="handleDel(row)"
              >
                <a v-if="delRecord">删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <inspection-modal
        ref="createModal"
        @ok="handelOk"
      ></inspection-modal>
      <settingInspectionEtype-modal
        ref="modal"
        @ok="handelOk"
      ></settingInspectionEtype-modal>
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        :paramData="{ type: this.type.length === 1 ? this.type : 0, year: this.currentYear }"
        paramKey="tableField"
        :title="title"
        ref="uploadModal"
        action="/doc/inspection/importInspection"
        :templateName="templateName"
        @onSuccess="success"
        @error="error"
      />
    </a-spin>
  </a-card>
</template>
<script>
// SetRdDept,
import { STable, UploadModal, Ellipsis, EditTableTitle } from '@/components'
import InspectionModal from './modules/InspectionModal'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import SettingInspectionEtypeModal from './modules/SettingInspectionEtypeModal'
import ystable from '@/components/Table/ystable'
const typeArr = {}
typeArr['20500'] = '检测'
typeArr['20600'] = '修理'
typeArr['20700'] = '样机'
typeArr['40000'] = '软件摊销'
typeArr['40100'] = '专利摊销'
typeArr['40200'] = '其他摊销'
typeArr['20300'] = '其他试制'
typeArr['69900'] = '其他'
typeArr['60400'] = '差旅费'
typeArr['60000'] = '资料'
typeArr['60100'] = '研发成果'
typeArr['60200'] = '知识产权'
typeArr['60300'] = '福利'
export default {
  mixins: [yearMiXin],
  name: 'Inspection',
  components: {
    STable,
    UploadModal,
    InspectionModal,
    Ellipsis,
    SettingInspectionEtypeModal,
    EditTableTitle,
    ystable
  },
  props: {
    view: {
      type: [Boolean, Object],
      default: undefined
    },
    addRecord: {
      type: [Boolean, Object],
      default: undefined
    },
    editRecord: {
      type: [Boolean, Object],
      default: undefined
    },
    delRecord: {
      type: [Boolean, Object],
      default: undefined
    },
    importRecord: {
      type: [Boolean, Object],
      default: undefined
    },
    exportRecord: {
      type: [Boolean, Object],
      default: undefined
    },
    setFeeType: {
      type: [Boolean, Object],
      default: undefined
    },
    type: {
      type: Array,
      required: true
    },
    typeName: {
      type: String,
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
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      scroll: {},
      tableField: {
        tableId: 'inspectionTable',
        fieldTitleObject: {
          accNumber: { title: '凭证号', required: true, defaultTitle: '凭证号', importField: true },
          summary: { title: '摘要', required: true, defaultTitle: '摘要', importField: true },
          deptName: { title: '部门', defaultTitle: '部门', importField: true },
          expense: { title: '费用', required: true, defaultTitle: '费用', importField: true, sampleValue: '请输入纯数字，例如：10000' },
          accDate: { title: '记账日期', required: true, defaultTitle: '记账日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          typeName: { title: '费用类型', required: this.type.length > 1, defaultTitle: '费用类型', importField: true },
          ename: { title: '姓名', defaultTitle: '姓名', importField: true },
          fullAccountName: { title: '科目', defaultTitle: '科目', importField: true },
          remark: { title: '备注', defaultTitle: '备注', importField: true }
        }
      },
      sampleData: [
        {
          accNumber: 'A-1',
          summary: '购入无烟煤滤料',
          deptName: 'xxx部门',
          expense: '数字格式，例如：300000',
          accDate: '格式："年-月-日"，例如：2019-10-11',
          typeName: `请从中选择填写：${this.type.length === 3 ? '软件摊销、专利摊销、其他摊销，例如：软件摊销' : '检测、修理、样机、软件摊销、专利摊销、其他摊销、差旅费、资料、研发成果、知识产权、福利、其他、其他试制，例如：检测'}`,
          ename: '张三',
          fullAccountName: '科目',
          remark: '备注'
        }
      ],
      templateName: `${this.typeName}模板`,
      spinning: false,
      title: `导入${this.typeName}`,
      form: this.$form.createForm(this),
      accDate: undefined,
      advanced: false,
      values: {},
      selectedRowKeys: [],
      pagination: [],
      entityList: [],
      accNumber: '',
      summary: '',
      qtype: undefined,
      deptName: undefined,
      // queryParam: { startMonth: null, endMonth: null, defaultPickerValue: null },
      queryParam: {},
      getData: parameter => {
        if (this.accDate === null || this.accDate === Date.undefined) {
          var startMonth = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
          var endMonth = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
          this.queryParam.startMonth = startMonth
          this.queryParam.endMonth = endMonth
        } else {
          this.queryParam.startMonth = null
          this.queryParam.endMonth = null
        }
        return this.$http.get('/inspection/queryAll', {
          params: Object.assign(parameter, {
            startMonth: this.queryParam.startMonth,
            endMonth: this.queryParam.endMonth,
            type: this.type.toString(),
            accDate: this.accDate,
            accNumber: this.accNumber,
            summary: this.summary,
            deptName: this.deptName
          })
        })
          .then(res => {
            return res.data
          })
      },
      options: {}
    }
  },
  created () {
    this.getTableField()
    this.$set(this.queryParam, 'defaultPickerValue', moment([this.currentYear, 0, 1, 0, 0, 0, 0]))
    if (document.body.clientWidth < 1820) {
      this.scroll = { x: 1700 }
    }
  },
  mounted () {
    this.$set(this.queryParam, 'defaultPickerValue', moment([this.currentYear, 0, 1, 0, 0, 0, 0]))
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1820) {
          this.scroll = { x: 1700 }
        }
      })()
    }
    this.queryParam.type = this.type.toString()
  },
  methods: {
    getParams () {
      if (this.accDate === null || this.accDate === Date.undefined) {
        var startMonth = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
        var endMonth = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
        this.queryParam.startMonth = startMonth
        this.queryParam.endMonth = endMonth
      } else {
        this.queryParam.startMonth = null
        this.queryParam.endMonth = null
      }
      this.queryParam.type = this.type.toString()
      this.queryParam.accDate = this.accDate
      this.queryParam.accNumber = this.accNumber
      this.queryParam.summary = this.summary
      this.queryParam.deptName = this.deptName
      this.queryParam.qtype = this.qtype
      return this.queryParam
    },
    search () {
      this.$set(this.queryParam, 'defaultPickerValue', moment([this.currentYear, 0, 1, 0, 0, 0, 0]))
      this.getParams()
      this.$refs.table.refresh(true)
    },
    moment,
    disabledDate (current) {
      return current.year() !== this.currentYear
    },
    getTableField () {
      this.$http.get('/sysDictionary/getTableField', { params: { tableId: 'inspectionTable' } })
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
    getype (text) {
      return typeArr[text]
    },
    openUploadModal () {
      const table = { ...this.tableField }
      if (this.type.toString() !== '60400') {
        delete table.fieldTitleObject.ename
      }
      this.$refs.uploadModal.show(table)
    },
    success (fileName, resData) {
      if (resData != null && resData.length > 0) {
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
      this.$refs.table.refresh(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    handleDel (record) {
      this.$http.get('/inspection/delInspection', { params: { id: record.id } })
        .then(res => {
          if (res.success) {
            this.$message.success('删除成功')
            this.$refs.table.refresh()
          } else {
            this.$message.error(`${res.errorMessage ? res.errorMessage : '删除失败'}`)
          }
        })
    },
    handelOk (ok) {
      if (ok) {
        this.selectedRowKeys = []
        this.$refs.table.refresh()
      }
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.entityList = selectedRows
    },
    exportInspection () {
      this.$http.get('/inspection/exportInspection', {
        params: Object.assign({
          startMonth: this.queryParam.startMonth,
          endMonth: this.queryParam.endMonth,
          type: this.type.toString(),
          accDate: this.accDate,
          accNumber: this.accNumber,
          summary: this.summary,
          deptName: this.deptName
        })
      })
        .then(res => {
          if (res.success) {
            const table = this.$deepClone(this.tableField)
            delete table.fieldTitleObject.type
            if (this.type.toString() !== '60400') {
              delete table.fieldTitleObject.ename
            }
            var keys = Object.keys(table.fieldTitleObject).map(a => table.fieldTitleObject[a].title)
            var sheetData = res.data
            var sheetFilter = Object.keys(table.fieldTitleObject)
            this.$exportJsonData(`${this.typeName}列表数据`, keys, sheetFilter, sheetData)
          } else {
            this.$message.error(`导出失败${res.errorMessage ? ':' + res.errorMessage : ''}`)
          }
        })
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    delInspection () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的费用吗?',
        content: <b>已被加计扣除使用的费用不会删除。</b>,
        onOk () {
          return self.$http.post('/inspection/delInspetionBatch', self.entityList)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.selectedRowKeys = []
                self.entityList = {}
                self.$refs.table.refresh()
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
    selectChange ({ records }) {
      this.entityList = records
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
