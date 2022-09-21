<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="设计名称" >
          <a-input v-model="queryParam.dName" placeholder="请输入设计名称" />
        </a-form-item>
        <a-form-item label="部门" >
          <a-input v-model="queryParam.deptName" placeholder="请输入部门" />
        </a-form-item>
        <a-form-item label="设计月份">
          <a-select
            style="width: 174px;"
            v-model="queryParam.monthValue"
            placeholder="请选择月份"
            :allowClear="true"
            :options="monthOptions"
          ></a-select>
        </a-form-item>
        <a-form-item>
          <a-button style="margin-right: 10px;" type="primary" @click="$refs.table.refresh(true)" v-if="$auth('dataentry:design:search')">查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          queryUrl="/design/queryDesignList"
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
              v-if="$auth('dataentry:design:add')"
              @click="$refs.createModal.add()"
              style="margin-right:10px"
            >添加</a-button>
            <a-button
              type="primary"
              v-if="$auth('dataentry:design:import')"
              @click="openUploadModal"
              style="margin-right:10px"
            >导入</a-button>
            <a-button
              type="primary"
              v-if="$auth('dataentry:design:export')"
              @click="exportDesign"
              style="margin-right:10px"
            >导出</a-button>
            <a-button
              type="primary"
              v-if="$auth('dataentry:design:del')"
              :disabled="selectedRowKeys.length <= 0"
              @click="delDesign"
              style="margin-right:10px"
            >删除</a-button>
          </template>
          <vxe-table-column type="checkbox" width="40" fixed="left" />
          <vxe-table-column title="设计名称" field="dname" width="150" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="设计日期" field="designDate" width="140" remoteSort align="center"></vxe-table-column>
          <vxe-table-column title="设计费用" field="dFee" width="100" remoteSort align="right"></vxe-table-column>
          <vxe-table-column title="部门" field="deptName" width="150" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="科目" field="fullAccountName" width="220" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="备注" field="remark" min-width="150" align="left"></vxe-table-column>
          <vxe-table-column title="操作" field="action" width="120" align="center" fixed="right">
            <template v-slot="{ row }">
              <a
                href="javascript:;"
                v-if="$auth('dataentry:design:edit')"
                @click="$refs.createModal.edit(row)"
              >编辑</a>
              <span v-if="$auth('dataentry:design:del')">
                <a-divider type="vertical" v-if="$auth('dataentry:design:edit')" />
                <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)">
                  <a>删除</a>
                </a-popconfirm>
              </span>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <design-modal ref="createModal" @ok="handleOk"></design-modal>
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        :paramData="{ year: this.currentYear }"
        paramKey="tableField"
        title="导入研发设计"
        ref="uploadModal"
        action="/doc/design/importDesignData"
        :templateName="templateName"
        @onSuccess="success"
        @error="error"
      />
    </a-spin>
  </a-card>
</template>

<script>
import { UploadModal, Ellipsis, EditTableTitle } from '@/components'
import DesignModal from './modules/DesignModal'
import yearMiXin from '@/utils/yearMixin'
import moment from 'moment'
import ystable from '@/components/Table/ystable'
export default {
  name: 'Design',
  mixins: [yearMiXin],
  components: {
    DesignModal,
    UploadModal,
    Ellipsis,
    EditTableTitle,
    ystable
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
        tableId: 'designTable',
        fieldTitleObject: {
          dname: { title: '设计名称', required: true, defaultTitle: '设计名称', importField: true },
          designDate: { title: '设计日期', required: true, defaultTitle: '设计日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          dFee: { title: '设计费用', required: true, defaultTitle: '设计费用', importField: true, sampleValue: '请输入纯数字，例如：10000' },
          deptName: { title: '部门', defaultTitle: '部门', importField: true },
          fullAccountName: { title: '科目', defaultTitle: '科目', importField: true },
          remark: { title: '备注', defaultTitle: '备注', importField: true }
        }
      },
      sampleData: [
        {
          dname: '设计名称',
          designDate: '格式："年-月-日"，例如：2019-10-11',
          dFee: '数字格式，例如：300000',
          deptName: 'xxx部门',
          fullAccountName: '科目',
          remark: '备注'
        }
      ],
      templateName: '研发设计模板',
      form: this.$form.createForm(this),
      selectdName: '',
      spinning: false,
      deptName: undefined,
      designDate: undefined,
      designEntities: {},
      selectedRowKeys: [],
      queryParam: { monthValue: undefined },
      values: {},
      initializeData: parameter => {
        this.initParam()
        return this.$http.get('/design/queryDesignList', {
          params: Object.assign(parameter, {
            startMonth: this.queryParam.startMonth,
            endMonth: this.queryParam.endMonth,
            dName: this.selectdName,
            deptName: this.deptName
          })
        }).then(res => {
          return res.data
        })
      },
      options: {}
    }
  },
  created () {
    this.getTableField()
    if (document.body.clientWidth < 1780) {
      this.scroll = { x: 1600 }
    }
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1780) {
          this.scroll = { x: 1600 }
        }
      })()
    }
  },
  methods: {
    getParams () {
      this.initParam()
      return this.queryParam
    },
    search () {
      this.initParam()
      this.$refs.table.refresh(true)
    },
    getTableField () {
      this.$http.get('/sysDictionary/getTableField', { params: { tableId: 'designTable' } })
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
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
        this.$refs.table.refresh(true)
      } else {
        this.$message.success('更新成功')
        this.$refs.table.refresh(false)
      }
    },
    handleDel (record) {
      this.$http.post('/design/delDesign', { id: record.id }).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.$refs.table.refresh()
        } else {
          this.$message.error(`[${record.dname}]${res.errorMessage ? res.errorMessage : '删除失败'}`)
        }
      })
    },
    moment,
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    openUploadModal () {
      this.$refs.uploadModal.show(this.tableField)
    },
    initParam () {
      if (this.queryParam.monthValue === Number.undefined || this.queryParam.monthValue === '-1') {
        var startMonth = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
        var endMonth = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
        this.queryParam.startMonth = startMonth
        this.queryParam.endMonth = endMonth
      } else {
        this.queryParam.startMonth = moment([this.currentYear, this.queryParam.monthValue, 1, 0, 0, 0, 0])
        var value = parseInt(this.queryParam.monthValue)
        if (value <= 12) {
          this.queryParam.endMonth = moment([this.currentYear, parseInt(this.queryParam.monthValue), 1, 0, 0, 0, 0])
        } else {
          this.queryParam.endMonth = moment([this.currentYear + 1, this.queryParam.monthValue, 1, 0, 0, 0, 0])
        }
      }
    },
    exportDesign () {
      this.initParam()
      this.$http.get('/design/exportDesign', {
        params: Object.assign({
          startMonth: this.queryParam.startMonth,
          endMonth: this.queryParam.endMonth,
          dName: this.selectdName,
          deptName: this.deptName

        })
      })
        .then(res => {
          if (res.success) {
            var keys = Object.keys(this.tableField.fieldTitleObject).map(a => this.tableField.fieldTitleObject[a].title)
            var sheetData = res.data
            var sheetFilter = Object.keys(this.tableField.fieldTitleObject)
            this.$exportJsonData('研发设计列表数据', keys, sheetFilter, sheetData)
          } else {
            this.$message.error(`导出失败${res.errorMessage ? res.errorMessage : ''}`)
          }
        })
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
        this.$message.success('导入成功')
      }
      this.$refs.table.refresh(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.designEntities = selectedRows
    },
    delDesign () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的设计费用吗?',
        content: <b>已被加计扣除使用的设计费用不会删除。</b>,
        onOk () {
          return self.$http.post('/design/delDesignBatch', self.designEntities)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.selectedRowKeys = []
                self.designEntities = {}
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
      this.designEntities = records
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    }
  }
}
</script>

<style scoped>
</style>
