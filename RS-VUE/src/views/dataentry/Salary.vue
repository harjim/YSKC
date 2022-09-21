<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="姓名">
          <a-input v-model="queryParam.ename" placeholder="请输入姓名" />
        </a-form-item>
        <a-form-item label="工号">
          <a-input v-model="queryParam.enumber" placeholder="请输入工号" />
        </a-form-item>
        <a-form-item label="部门">
          <a-input v-model="queryParam.deptName" placeholder="请输入部门" />
        </a-form-item>
        <a-form-item label="月份">
          <a-select
            style="width: 174px;"
            v-model="queryParam.monthValue"
            placeholder="请选择月份"
            :allowClear="true"
            :options="monthOptions"
          />
        </a-form-item>
        <a-form-item>
          <a-button
            style="margin-right: 10px;"
            type="primary"
            @click="search(true)"
            v-if="$auth('dataentry:salary:search')"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          queryUrl="/salary/querySalaryList"
          :params="queryParam"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          border
          @checkbox-all="selectCheckBoxChange"
          @checkbox-change="selectCheckBoxChange"
          :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
          @completed="({data:{data}})=>completed(data)"
        >
          <template v-slot:toolbar_buttons>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              @click="$refs.createModal.add(salaryArr,insuranceArr)"
              v-if="$auth('dataentry:salary:add')"
            >添加</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              @click="openUploadModal"
              v-if="$auth('dataentry:salary:import')"
            >导入</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              @click="exportSalary"
              v-if="$auth('dataentry:salary:export')"
            >导出</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              :disabled="selectedRowKeys.length <= 0"
              @click="delSalary"
              v-if="$auth('dataentry:salary:del')"
            >删除</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              @click="$refs.salaryConfig.showModal(0)"
              v-if="$auth('dataentry:salary:config')"
            >配置薪资明细</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              @click="$refs.salaryConfig.showModal(1)"
              v-if="$auth('dataentry:salary:config')"
            >配置五险一金</a-button>
          </template>
          <vxe-table-column type="checkbox" width="40" align="center" fixed="left"></vxe-table-column>
          <vxe-table-column field="month" title="月份" width="100" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="enumber" title="工号" width="100" align="left" remoteSort></vxe-table-column>
          <vxe-table-column field="ename" title="姓名" width="90" align="left" remoteSort></vxe-table-column>
          <vxe-table-column field="deptName" title="部门" width="120" align="left" remoteSort></vxe-table-column>
          <vxe-table-column field="workHours" title="总工时" width="100" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="workDays" title="工作天数" width="100" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="pay" title="应发工资" width="100" align="center" remoteSort></vxe-table-column>
          <vxe-table-column
            v-for="config in salaryArr"
            :key="config.name"
            align="center"
            :title="config.alias"
            width="80"
          >
            <template v-slot="{ row }">{{ row[config.name] }}</template>
          </vxe-table-column>
          <vxe-table-column
            field="insuranceFund"
            title="五险一金"
            width="100"
            align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            v-for="config in insuranceArr"
            :key="config.name"
            align="center"
            :title="config.alias"
            width="80"
          >
            <template v-slot="{ row }">{{ row[config.name] }}</template>
          </vxe-table-column>
          <!-- <vxe-table-column title="个人部分" align="center">
            <vxe-table-column field="endowment" title="养老" width="80" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="medical" title="医疗" width="80" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="unemployment" title="失业" width="80" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="injury" title="工伤" width="80" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="maternity" title="生育" width="80" align="center" remoteSort></vxe-table-column>
            <vxe-table-column field="house" title="公积金" width="90" align="center" remoteSort></vxe-table-column>
          </vxe-table-column>
          <vxe-table-column title="企业部分" align="center">
            <vxe-table-column
              field="endowmentOfCom"
              title="养老"
              width="80"
              align="center"
              remoteSort
            ></vxe-table-column>
            <vxe-table-column field="medicalOfCom" title="医疗" width="80" align="center" remoteSort></vxe-table-column>
            <vxe-table-column
              field="unemploymentOfCom"
              title="失业"
              width="80"
              align="center"
              remoteSort
            ></vxe-table-column>
            <vxe-table-column field="injuryOfCom" title="工伤" width="80" align="center" remoteSort></vxe-table-column>
            <vxe-table-column
              field="maternityOfCom"
              title="生育"
              width="80"
              align="center"
              remoteSort
            ></vxe-table-column>
            <vxe-table-column field="houseOfCom" title="公积金" width="90" align="center" remoteSort></vxe-table-column>
          </vxe-table-column>-->
          <vxe-table-column field="fullAccountName" title="科目" width="150" align="left" remoteSort></vxe-table-column>
          <vxe-table-column title="操作" width="100" align="center" fixed="right">
            <template v-slot="{ row }">
              <a
                href="javascript:;"
                @click="$refs.createModal.edit(row,salaryArr,insuranceArr)"
                v-if="$auth('dataentry:salary:edit')"
              >编辑</a>
              <a-divider
                type="vertical"
                v-if="$auth('dataentry:salary:del')&&$auth('dataentry:salary:edit')"
              />
              <a-popconfirm
                title="是否确定删除?"
                @confirm="handleDel(row)"
                v-if="$auth('dataentry:salary:del')"
              >
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <salary-modal ref="createModal" @ok="handleOk"></salary-modal>
      <insurance-config-modal ref="configModel" @ok="handleOk"></insurance-config-modal>
      <salary-config-modal ref="salaryConfig" @ok="reloadTable" />
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        paramKey="tableField"
        title="导入薪资"
        :templateName="templateName"
        ref="uploadModal"
        action="/doc/salary/importSalary"
        @onSuccess="success"
        @error="error"
      />
    </a-spin>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, UploadModal, Ellipsis } from '@/components'
import SalaryModal from './modules/SalaryModal'
import InsuranceConfigModal from './modules/InsuranceConfigModal'
import yearMiXin from '@/utils/yearMixin'
import moment from 'moment'
import SalaryConfigModal from './modules/SalaryConfigModal'

const defaultTitleObj = [{
  month: '格式："年-月-日"，一般为当月第一天，例如：2019-10-01',
  enumber: 'A-01',
  ename: '张三',
  deptName: 'xxx部门',
  workDays: '数字格式，当月工作天数，例如：23',
  workHours: '数字格式，总工时',
  pay: '数字格式，例如：300000',
  insuranceFund: '数字格式，例如：300000',
  // endowment: '数字格式，例如：300000',
  // medical: '数字格式，例如：300000',
  // injury: '数字格式，例如：300000',
  // unemployment: '数字格式，例如：300000',
  // maternity: '数字格式，例如：300000',
  // house: '数字格式，例如：300000',
  // endowmentOfCom: '数字格式，例如：300000',
  // medicalOfCom: '数字格式，例如：300000',
  // unemploymentOfCom: '数字格式，例如：300000',
  // maternityOfCom: '数字格式，例如：300000',
  // injuryOfCom: '数字格式，例如：300000',
  // houseOfCom: '数字格式，例如：300000',
  fullAccountName: '科目',
  remark: '备注'
}]

const defaultTableField = {
  tableId: 'salayTable',
  fieldTitleObject: {
    month: { title: '月份', required: true, defaultTitle: '月份', importField: true, sampleValue: '格式："年-月-日"，一般为当月第一天，例如：2019-10-01' },
    enumber: { title: '工号', required: true, defaultTitle: '工号', importField: true },
    ename: { title: '姓名', required: true, defaultTitle: '姓名', importField: true },
    deptName: { title: '部门', defaultTitle: '部门', importField: true },
    workHours: { title: '总工时', required: true, defaultTitle: '总工时', importField: true },
    workDays: { title: '工作天数', required: true, defaultTitle: '工作天数', importField: true },
    pay: { title: '应发工资', required: true, defaultTitle: '应发工资', importField: true, sampleValue: '请输入纯数字，例如：10000' },
    // endowment: { title: '养老保险(个人)', defaultTitle: '养老保险(个人)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    // medical: { title: '医疗保险(个人)', defaultTitle: '医疗保险(个人)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    // injury: { title: '工伤保险(个人)', defaultTitle: '工伤保险(个人)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    // unemployment: { title: '失业保险(个人)', defaultTitle: '失业保险(个人)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    // maternity: { title: '生育保险(个人)', defaultTitle: '生育保险(个人)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    // house: { title: '公积金(个人)', defaultTitle: '公积金(个人)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    // endowmentOfCom: { title: '养老保险(公司)', defaultTitle: '养老保险(公司)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    // medicalOfCom: { title: '医疗保险(公司)', defaultTitle: '医疗保险(公司)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    // unemploymentOfCom: { title: '失业保险(公司)', defaultTitle: '失业保险(公司)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    // maternityOfCom: { title: '生育保险(公司)', defaultTitle: '生育保险(公司)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    // injuryOfCom: { title: '工伤保险(公司)', defaultTitle: '工伤保险(公司)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    // houseOfCom: { title: '公积金(公司)', defaultTitle: '公积金(公司)', importField: true, sampleValue: '请输入纯数字，例如：100' },
    insuranceFund: { title: '五险一金', required: true, defaultTitle: '五险一金', importField: true, sampleValue: '请输入纯数字，例如：10000' },
    fullAccountName: { title: '科目', defaultTitle: '科目', importField: true },
    remark: { title: '备注', defaultTitle: '备注', importField: true }
  },
  hasMonth: true,
  hasCol: false,
  hasICol: false
}

export default {
  mixins: [yearMiXin],
  name: 'Salary',
  components: {
    ystable,
    STable,
    SalaryModal,
    UploadModal,
    InsuranceConfigModal,
    Ellipsis,
    SalaryConfigModal
  },
  data () {
    return {
      labelCol: {
        md: { span: 8 }
      },
      wrapperCol: {
        md: { span: 16 }
      },
      scroll: { x: 1700 },
      tableField: {},
      sampleData: [],
      spinning: false,
      templateName: '薪资模板',
      paramData: { name: 'aaaaa' },
      form: this.$form.createForm(this),
      selectEname: '',
      etypes: [],
      enumber: undefined,
      selectmoth: undefined,
      advanced: false,
      salaryList: [],
      selectedRowKeys: [],
      queryParam: { year: null, month: null, monthValue: undefined, startMonth: null, endMonth: null },
      salaryArr: [],
      insuranceArr: []
    }
  },
  created () {
    this.getSalaryConfig()
  },
  mounted () {
    this.initParam()
  },
  computed: {
  },
  methods: {
    loadTitleObject () {
      this.sampleData = this.$deepClone(defaultTitleObj)
      this.tableField = this.$deepClone(defaultTableField)
      var titleObj = {}
      var fieldObj = {}
      const fieldTitleObject = this.tableField.fieldTitleObject
      const tempObj = defaultTitleObj[0]
      for (const key in tempObj) {
        titleObj[key] = tempObj[key]
        fieldObj[key] = fieldTitleObject[key]
        if (key === 'pay') {
          this.insertCols(titleObj, fieldObj, this.salaryArr)
        }
        if (key === 'insuranceFund') {
          this.insertCols(titleObj, fieldObj, this.insuranceArr)
        }
      }
      this.tableField.hasCol = this.salaryArr.length > 0
      this.tableField.hasICol = this.insuranceArr.length > 0
      this.sampleData = [titleObj]
      this.tableField.fieldTitleObject = fieldObj
    },
    insertCols (titleObj, fieldObj, arr) {
      const sampleValue = '数字格式，例如：300000'
      for (let i = 0; i < arr.length; i++) {
        titleObj[arr[i].name] = sampleValue
        fieldObj[arr[i].name] = { title: arr[i].alias, defaultTitle: arr[i].alias, importField: true, sampleValue: sampleValue, required: true }
      }
    },
    completed (data) {
      if (data && this.insuranceArr !== undefined && this.salaryArr !== undefined && (this.insuranceArr.length > 0 || this.salaryArr.length > 0)) {
        data.forEach(item => {
          if (typeof (item.payDetail) === 'string') {
            item = Object.assign(item, JSON.parse(item.payDetail))
          }
          if (typeof (item.insuranceDetail) === 'string') {
            item = Object.assign(item, JSON.parse(item.insuranceDetail))
          }
        })
      }
    },
    getSalaryConfig (isRefresh) {
      this.$getSalaryConfig(this, isRefresh).then(res => {
        ({ salary: this.salaryArr, insurance: this.insuranceArr } = res)
      })
    },
    search () {
      this.initParam()
      this.$refs.table.refresh(true)
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.$refs.table.refresh(flag)
    },
    setOk (flag) {
      if (flag) {
        this.$message.success('设置成功')
      }
      this.selectedRowKeys = []
      this.$refs.table.refresh(false)
    },
    handleDel (record) {
      this.$http.post('/salary/delSalary', { id: record.id }).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.$refs.table.refresh(false)
        } else {
          this.$message.error(`${res.errorMessage ? res.errorMessage : '删除失败'}`)
        }
      })
    },
    moment,
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    openUploadModal () {
      this.loadTitleObject()
      this.$refs.uploadModal.show(this.tableField)
    },
    initParam () {
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
      this.queryParam.year = this.currentYear
    },
    exportSalary () {
      this.initParam()
      this.loadTitleObject()
      this.$http.get('/salary/salaryExport', { params: this.queryParam })
        .then(res => {
          if (res.success) {
            var keys = Object.keys(this.tableField.fieldTitleObject).map(a => this.tableField.fieldTitleObject[a].title)
            var sheetData = res.data
            sheetData.forEach(r => {
              if (r.payDetail && typeof (r.payDetail) === 'string') {
                r = Object.assign(r, JSON.parse(r.payDetail))
              }
              if (r.insuranceDetail && typeof (r.insuranceDetail) === 'string') {
                r = Object.assign(r, JSON.parse(r.insuranceDetail))
              }
            })
            var sheetFilter = Object.keys(this.tableField.fieldTitleObject)
            this.$exportJsonData('薪资列表数据', keys, sheetFilter, sheetData)
          } else {
            this.$message.error(`导出失败${res.errorMessage ? ':' + res.errorMessage : ''}`)
          }
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
        this.$message.success('导入成功')
      }
      this.$refs.table.refresh(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    suminsuranceFund: (record) => {
      record.insuranceFund = record.endowment + record.medical + record.unemployment + record.injury + record.maternity + record.house
      return record.insuranceFund
    },
    suminsuranceFundCom: (record) => {
      return record.endowmentOfCom + record.medicalOfCom + record.unemploymentOfCom + record.injuryOfCom + record.maternityOfCom + record.houseOfCom
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.salaryList = selectedRows
    },
    delSalary () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的薪资吗?',
        onOk () {
          return self.$http.post('/salary/delSalaryBatch', self.salaryList)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.selectedRowKeys = []
                self.salaryList = {}
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
    toggleAdvanced () {
      this.advanced = !this.advanced
    },

    reloadTable (edit) {
      if (edit) {
        this.getSalaryConfig(true)
      }
      this.$refs.table.refresh(true)
      this.$refs.table.refreshColumn()
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
      this.salaryList = records
    }
  }
}
</script>

<style>
.table-page-search-wrapper .ant-form-inline .ant-form-item > .ant-form-item-label {
  width: 33%;
}
</style>
