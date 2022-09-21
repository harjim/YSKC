<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item
          label="姓名"
        >
          <a-input v-model="queryParam.ename" placeholder="请输入姓名" />
        </a-form-item>
        <a-form-item
          label="工号"
        >
          <a-input v-model="queryParam.enumber" placeholder="请输入工号" />
        </a-form-item>
        <a-form-item
          label="部门"
        >
          <a-input v-model="queryParam.deptName" placeholder="请输入部门" />
        </a-form-item>
        <a-form-item
          label="月份"
        >
          <a-select
            style="width: 174px;"
            v-model="queryParam.monthValue"
            placeholder="请选择月份"
            :allowClear="true"
            :options="monthOptions"
          ></a-select>
        </a-form-item>
        <a-form-item>
          <a-button style="margin-right: 10px;" type="primary" @click="search(true)" v-if="$auth('dataentry:design:search')">查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          queryUrl="/dataBonus/getDataBonusList"
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
              @click="$refs.createModal.add(currentYear)"
              style="margin-right:10px"
            >添加</a-button>
            <a-button type="primary" @click="openUploadModal" style="margin-right:10px">导入</a-button>
            <a-button type="primary" @click="exportBonusData" style="margin-right:10px">导出</a-button>
            <a-button
              type="primary"
              :disabled="deleteListDisabled"
              @click="deleteList"
              style="margin-right:10px"
            >删除</a-button>
          </template>
          <vxe-table-column type="checkbox" width="40" fixed="left" />
          <vxe-table-column title="月份" field="month" width="100" remoteSort align="center">
            <template v-slot="{ row }">{{ row.month | formatDate }}</template>
          </vxe-table-column>
          <vxe-table-column title="工号" field="enumber" width="100" remoteSort align="center"></vxe-table-column>
          <vxe-table-column title="姓名" field="ename" width="100" remoteSort align="center"></vxe-table-column>
          <vxe-table-column title="部门" field="deptName" width="150" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="金额(元)" field="bonus" width="110" remoteSort align="right"></vxe-table-column>
          <vxe-table-column title="科目" field="fullAccountName" width="150" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="备注" field="remark" min-width="100" remoteSort align="left"></vxe-table-column>
        </ystable>
      </div>
      <dataBonus-modal ref="createModal" @ok="handleOk"></dataBonus-modal>
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        :paramData="{year: currentYear}"
        paramKey="name"
        title="导入员工奖金"
        :templateName="templateName"
        ref="uploadModal"
        action="/dataBonus/importData"
        @onSuccess="success"
        @error="error"
      />
    </a-spin>
  </a-card>
</template>

<script>
import { UploadModal, SetRdDept, Ellipsis, EditTableTitle } from '@/components'
import DataBonusModal from './modules/DataBonusModal'
import yearMiXin from '@/utils/yearMixin'
import moment from 'moment'
import ystable from '@/components/Table/ystable'
export default {
  mixins: [yearMiXin],
  name: 'DataBonus',
  components: {
    EditTableTitle,
    DataBonusModal,
    UploadModal,
    SetRdDept,
    Ellipsis,
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
        md: { span: 8 }
      },
      wrapperCol: {
        md: { span: 16 }
      },
      scroll: {},
      spinning: false,
      tableField: {
        tableId: 'dataBonusTable',
        fieldTitleObject: {
          month: { title: '月份', required: true, defaultTitle: '月份', importField: false },
          enumber: { title: '工号', required: true, defaultTitle: '工号', importField: true },
          ename: { title: '姓名', required: true, defaultTitle: '姓名', importField: true },
          // typeValue: { title: '人员类型', defaultTitle: '人员类型', importField: false },
          deptName: { title: '部门', defaultTitle: '部门', importField: true },
          // rdDeptName: { title: '研发部门', defaultTitle: '研发部门', importField: false },
          bonus: { title: '金额(元)', required: true, defaultTitle: '金额(元)', importField: true },
          startTime: { title: '开始日期', required: true, defaultTitle: '开始日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          endTime: { title: '结束日期', required: true, defaultTitle: '结束日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          fullAccountName: { title: '科目', defaultTitle: '科目', importField: true },
          remark: { title: '备注', defaultTitle: '备注', importField: true }
        }
      },
      sampleData: [{
        enumber: 'A-01',
        ename: '张三',
        deptName: 'xxx部门',
        bonus: '数字格式，例如：300000',
        startTime: '格式："年-月-日"，例如：2019-10-11',
        endTime: '格式："年-月-日"，必需要晚于开始日期,例如：2019-12-11',
        fullAccountName: '科目',
        remark: '备注'
      }],
      templateName: '员工奖金模板',
      deleteListDisabled: true,
      a: undefined,
      queryParam: { year: null, monthValue: undefined, deptId: null, month: null, ename: '', enumber: '', etype: undefined, startMonth: null, endMonth: null },
      paramData: { name: 'aaaaa' },
      form: this.$form.createForm(this),
      selectdName: '',
      designDate: undefined,
      selectedRowKeys: [],
      pagination: [],
      values: {},
      initializeData: parameter => {
        this.initParam()
        return this.$http.get('/dataBonus/getDataBonusList', { params: Object.assign(parameter, this.queryParam) })
          .then(res => {
            this.selectedRowKeys = []
            return res.data
          })
      },
      options: {}
    }
  },
  created () {
    this.getTableField()
    if (document.body.clientWidth < 1400) {
      this.scroll = { x: 1400 }
    }
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1400) {
          this.scroll = { x: 1400 }
        }
      })()
    }
    this.initParam()
  },
  watch: {
    selectedRowKeys (newRowKeys) {
      if (newRowKeys.length > 0) {
        this.deleteListDisabled = false
      } else {
        this.deleteListDisabled = true
      }
    }
  },
  filters: {
    formatDate (value) {
      if (!value) { return '' }
      return moment(value).format('YYYY-MM')
    }
  },
  methods: {
    getParams () {
      this.initParam()
      return this.queryParam
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.selectedRows = []
      this.initParam()
      this.$refs.table.refresh(refresh)
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
    handleOk (flag) {
      this.$refs.table.refresh(flag)
    },
    deleteList () {
      const self = this
      this.$confirm({
        title: '您确定要删除选中的员工奖金记录吗?',
        content: <b>已被加计扣除使用的员工奖金记录不会删除。</b>,
        onOk () {
          self.spinning = true
          return self.$http.post('/dataBonus/deleteList', self.selectedRowKeys).then(res => {
            if (res.success && res.data) {
              self.$message.success('删除成功')
              self.$refs.table.refresh(false)
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
    moment,
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    openUploadModal () {
      this.$refs.uploadModal.show(this.tableField)
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
        this.$message.success(`${fileName}导入成功`)
      }
      this.$refs.table.refresh(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
    },
    initParam () {
      this.queryParam.year = this.currentYear
      this.queryParam.year = this.$store.state.currentYear ? this.$store.state.currentYear : null
      if (this.queryParam.monthValue === Number.undefined || this.queryParam.monthValue === '-1') {
        var startMonth = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
        var endMonth = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
        this.queryParam.startMonth = startMonth
        this.queryParam.endMonth = endMonth
        this.queryParam.month = null
      } else {
        this.queryParam.month = moment([this.currentYear, this.queryParam.monthValue, 1, 0, 0, 0, 0])
        this.queryParam.startMonth = null
        this.queryParam.endMonth = null
      }
    },
    exportBonusData () {
      this.initParam()
      this.$http.get('/dataBonus/exportBonusData', { params: this.queryParam })
        .then(res => {
          if (res.success) {
            const table = this.$deepClone(this.tableField)
            delete table.fieldTitleObject.startTime
            delete table.fieldTitleObject.endTime
            var keys = Object.keys(table.fieldTitleObject).map(a => table.fieldTitleObject[a].title)
            var sheetData = res.data
            var sheetFilter = Object.keys(table.fieldTitleObject)
            this.$exportJsonData('员工奖金列表数据', keys, sheetFilter, sheetData)
          } else {
            this.$message.error(`导出失败${res.errorMessage ? ':' + res.errorMessage : ''}`)
          }
        })
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
