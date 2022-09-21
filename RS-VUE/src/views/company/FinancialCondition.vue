<template>
  <a-card :bordered="false" style="height: 100%" :bodyStyle="{ height: '100%', overflow: 'auto' }">
    <a-form layout="inline">
      <a-form-item label="年份" style="widht:200px">
        <year-select @change="v=>year = v" style="width:200px;" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="loadData" v-if="$auth('company:financialCondition:search')">查询</a-button>
      </a-form-item>
      <a-form-item>
        <!-- <a-button type="primary" @click="$refs.creatModal.add()">添加</a-button> -->
      </a-form-item>
    </a-form>
    <div id="scrollContent">
      <vxe-grid
        id="company:financialCondition"
        ref="table"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        height="108%"
        border="full"
        :data="tableDatas"
        :loading="loading"
        :customConfig="{storage: { visible: true, resizable: true } }"
        :toolbar="{ refresh: {query:loadData}, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }">
        <template v-slot:toolbar_buttons>
          <a-button type="primary" style="margin-right: 10px;" @click="$refs.creatModal.add()" v-if="$auth('company:financialCondition:add')">添加</a-button>
          <a-button type="primary" style="margin-right: 10px;" @click="openUploadModal" v-if="$auth('company:financialCondition:import')">导入</a-button>
        </template>
        <vxe-table-column type="seq" title="序号" width="60" fixed="left"></vxe-table-column>
        <vxe-table-column field="name" title="项目类别" width="220" align="left" fixed="left"></vxe-table-column>
        <vxe-table-column
          :field="`${n}`"
          v-for="n in years"
          :key="n"
          :title="`${n}`"
          min-width="120"
          width="120"
          align="right"
          header-align="center">
          <template v-slot:header>
            <div
              slot="title"
              @mouseenter="showIcon(n)"
              @mouseleave="closeIcon(n)"
            >
              <div v-if="ckObj[`ck${n}`]">
                <span>{{ n+"年" }}</span>
                <a-tooltip placement="top">
                  <template slot="title">
                    <span>确定</span>
                  </template>
                  <a style="margin-left:3px;" @click="clickSubmit(n)">
                    <a-icon type="check" />
                  </a>
                </a-tooltip>
                <a-tooltip placement="top">
                  <template slot="title">
                    <span>取消</span>
                  </template>
                  <a style="margin-left:3px;" @click="clickClose(n)">
                    <a-icon type="close" />
                  </a>
                </a-tooltip>
              </div>
              <div v-else>
                <span>{{ n+"年" }}</span>
                <span v-if="icon[`icon${n}`]">
                  <span v-if="$auth('company:financialCondition:edit')">
                    <a-tooltip placement="top">
                      <template slot="title">
                        <span>编辑</span>
                      </template>
                      <a style="margin-left:3px;" @click="clickRow(n)">
                        <a-icon type="edit" />
                      </a>
                    </a-tooltip>
                  </span>
                  <span v-if="$auth('company:financialCondition:delete')">
                    <a-tooltip placement="top">
                      <template slot="title">
                        <span>删除</span>
                      </template>
                      <a style="margin-left:3px;" @click="delRow(n)">
                        <a-icon type="minus" />
                      </a>
                    </a-tooltip>
                  </span>
                </span>
                <span style="margin-left:3px;" v-else></span>
              </div>
            </div>
          </template>
          <template v-slot="{row}">
            <span v-if="ckObj[`ck${n}`]">
              <a-input-number
                :value="row[n]"
                :min="0"
                :key="`n${row.key}`"
                @change="(val)=>onCellChange(val,row,n)"
              />
            </span>
            <span v-else>{{ row[n] }}</span>
          </template>
        </vxe-table-column>
      </vxe-grid>
      <a-spin tip="请稍后..." :spinning="spinning"></a-spin>
    </div>
    <financialConditioin-modal ref="creatModal" @ok="handleOk"></financialConditioin-modal>
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      paramKey="tableField"
      title="导入财务状况"
      :templateName="templateName"
      ref="uploadModal"
      action="/doc/financialCondition/importFinancialCondition"
      @onSuccess="success"
      @error="error"
    />
  </a-card>
</template>
<script>
import { UploadModal, YearSelect } from '@/components'
import FinancialConditioinModal from './modules/FinancialConditionModal'
import moment from 'moment'
const tableColumn = [
  { field: 'year', title: '项目类别' },
  { field: 'businessIncome', title: '营业收入(万元)' },
  { field: 'mainBusinessIncome', title: '其中：主营业务收入（万元）' },
  { field: 'netProfit', title: '净利润（万元）' },
  { field: 'totalAssets', title: '工业总产值（万元）' },
  { field: 'netAssets', title: '工业增加值（万元）' },
  { field: 'corporateIncomeTax', title: '总资产(万元)' },
  { field: 'addedOfIndustrial', title: '其中：固定资产投资总额(万元)' },
  { field: 'totalFixedAssets', title: '	净资产(万元)' },
  { field: 'fixedAssetsOfInvestment', title: '固定资产投资源(万元)' },
  { field: 'netTotalCashFlow', title: '总现金流量资源(万元)' },
  { field: 'netCashFlowOfOperating', title: '经营活动现金流量净额(万元)' },
  { field: 'assetLiabilityRatio', title: '资产负载率(万元)' },
  { field: 'totalExpenditureOfRD', title: 'R&D支出总额(万元)' },
  { field: 'loanAmountOfGovernment', title: '政府借款金额(万元)' },
  { field: 'dueLoanOfGovernment', title: '到期末还的政府借款额(万元)' },
  { field: 'totalTax', title: '纳税总额(万元)' },
  { field: 'grossOfIndustrial', title: '企业所得税(万元)' },
  { field: 'totalProfit', title: '利润总额' }
]
export default {
  components: {
    FinancialConditioinModal,
    UploadModal,
    moment,
    YearSelect
  },
  data () {
    return {
      width: 300,
      scroll: {},
      fixed: true,
      tableField: {
        tableId: 'financialConditionTable',
        fieldTitleObject: {
          year: { title: '年份', required: true, defaultTitle: '年份', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：2019' },
          businessIncome: { title: '营业收入(万元)', required: true, defaultTitle: '营业收入(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          mainBusinessIncome: { title: '主营业务收入(万元)', required: true, defaultTitle: '主营业务收入(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          netProfit: { title: '净利润(万元)', required: true, defaultTitle: '净利润(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          totalAssets: { title: '总资产(万元)', required: true, defaultTitle: '总资产(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          netAssets: { title: '净资产(万元)', required: true, defaultTitle: '净资产(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          corporateIncomeTax: { title: '企业所得税(万元)', required: true, defaultTitle: '企业所得税(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          grossOfIndustrial: { title: '工业总产值(万元)', defaultTitle: '职称', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          addedOfIndustrial: { title: '工业增加值(万元)', defaultTitle: '身份证号', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          totalFixedAssets: { title: '固定资产总额(万元)', defaultTitle: '学历', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          fixedAssetsOfInvestment: { title: '固定资产投资额(万元)', defaultTitle: '固定资产投资额(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          netTotalCashFlow: { title: '总现金流量净额(万元)', defaultTitle: '总现金流量净额(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          netCashFlowOfOperating: { title: '经营活动现金流量净额(万元)', defaultTitle: '经营活动现金流量净额(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          assetLiabilityRatio: { title: '资产负债率（%）', defaultTitle: '资产负债率（%）', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          totalExpenditureOfRD: { title: 'R&D支出总额(万元)', defaultTitle: 'R&D支出总额(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          loanAmountOfGovernment: { title: '政府借款金额(万元)', defaultTitle: '政府借款金额(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          dueLoanOfGovernment: { title: '到期未还的政府借款额(万元)', defaultTitle: '到期未还的政府借款额(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          totalTax: { title: '纳税总额(万元)', defaultTitle: '纳税总额(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' },
          totalProfit: { title: '利润总额(万元)', defaultTitle: '利润总额(万元)', importField: true, sampleValue: '数字格式："请输入纯数字"，例如：1000' }
        }
      },
      sampleData: [
        {
          year: '数字格式，例如：2019',
          businessIncome: '数字格式，例如：300000',
          mainBusinessIncome: '数字格式，例如：300000',
          netProfit: '数字格式，例如：300000',
          totalAssets: '数字格式，例如：300000',
          netAssets: '数字格式，例如：300000',
          corporateIncomeTax: '数字格式，例如：300000',
          grossOfIndustrial: '数字格式，例如：300000',
          addedOfIndustrial: '数字格式，例如：300000',
          totalFixedAssets: '数字格式，例如：300000',
          fixedAssetsOfInvestment: '数字格式，例如：300000',
          netTotalCashFlow: '数字格式，例如：300000',
          netCashFlowOfOperating: '数字格式，例如：300000',
          assetLiabilityRatio: '数字格式，例如：300000',
          totalExpenditureOfRD: '数字格式，例如：300000',
          loanAmountOfGovernment: '数字格式，例如：300000',
          dueLoanOfGovernment: '数字格式，例如：300000',
          totalTax: '数字格式，例如：300000',
          totalProfit: '数字格式，例如：300000'
        }
      ],
      defaultData: [{
        num: '1',
        name: `营业收入(万元)`,
        dataFiled: 'businessIncome',
        key: 'businessIncome'

      }, {
        id: 0,
        num: '2',
        name: `其中：主营业务收入（万元）`,
        dataFiled: 'mainBusinessIncome',
        key: 'mainBusinessIncome'

      }, {
        num: '3',
        name: `净利润（万元）`,
        dataFiled: 'netProfit',
        key: 'netProfit'

      }, {
        num: '4',
        name: `工业总产值（万元）`,
        dataFiled: 'grossOfIndustrial',
        key: 'grossOfIndustrial'
      }, {
        num: '5',
        name: `工业增加值（万元）`,
        dataFiled: 'addedOfIndustrial',
        key: 'addedOfIndustrial'
      }, {
        num: '6',
        name: `总资产(万元)`,
        dataFiled: 'totalAssets',
        key: 'totalAssets'
      },
      {
        num: '7',
        name: `其中：固定资产投资总额(万元)`,
        dataFiled: 'totalFixedAssets',
        key: 'totalFixedAssets'
      },
      {
        num: '8',
        name: `净资产(万元)`,
        dataFiled: 'netAssets',
        key: 'netAssets'
      },
      {
        num: '9',
        name: `固定资产投资源(万元)`,
        dataFiled: 'fixedAssetsOfInvestment',
        key: 'fixedAssetsOfInvestment'
      },
      {
        num: '10',
        name: `总现金流量资源(万元)`,
        dataFiled: 'netTotalCashFlow'
      }, {
        num: '11',
        name: `经营活动现金流量净额(万元)`,
        dataFiled: 'netCashFlowOfOperating'
      },
      {
        num: '12',
        name: `资产负载率(万元)`,
        dataFiled: 'assetLiabilityRatio'

      }, {
        num: '13',
        name: `R&D支出总额(万元)`,
        dataFiled: 'totalExpenditureOfRD'

      },
      {
        num: '14',
        name: `政府借款金额(万元)`,
        dataFiled: 'loanAmountOfGovernment'

      },
      {
        num: '15',
        name: `到期末还的政府借款额(万元)`,
        dataFiled: 'dueLoanOfGovernment'

      },
      {
        num: '16',
        name: `纳税总额(万元)`,
        dataFiled: 'totalTax'

      }, {
        num: '17',
        name: `企业所得税(万元)`,
        dataFiled: 'corporateIncomeTax'
      },
      {
        num: '18',
        name: `利润总额`,
        dataFiled: 'totalProfit'
      }],
      tableColumn,
      tableData: [],
      tableDatas: [],
      years: [],
      year: undefined,
      templateName: '财务状况模板',
      spinning: false,
      dataBak: {},
      icon: {},
      ckObj: {},
      loading: false
    }
  },

  created () {
    this.loadData()
  },
  methods: {
    showIcon (y) {
      if (!this.ckObj[`ck${y}`]) {
        this.icon[`icon${y}`] = true
      }
    },
    closeIcon (y) {
      this.icon[`icon${y}`] = false
    },
    loadData () {
      this.years = []
      this.tableDatas = JSON.parse(JSON.stringify(this.defaultData))
      this.$nextTick(() => {
        this.loading = true
        this.$http.get('/financialCondition/queryFinancialCondList', { params: { year: this.year } })
          .then(res => {
            this.tableData = res.data
            this.reverseTable()
            if (res.success && res.data && res.data.length > 0) {
              this.width = 300
              this.width = this.width + (res.data.length * 150)
              this.scroll = { x: this.width }
              for (let i = 0; i < res.data.length; i++) {
                const d = res.data[i]
                this.years.push(d.year)
                for (var j = 0; j < this.tableDatas.length; j++) {
                  var row = this.tableDatas[j]
                  this.$set(row, d.year, d[row.dataFiled])
                  this.$set(this.tableDatas, j, row)
                }
                this.$set(this.ckObj, `ck${d.year}`, false)
                this.$set(this.icon, `icon${d.year}`, false)
              }
            }
            if (res.data.length <= 0) {
              this.scroll = {}
              this.fixed = false
            }
            return res.data
          }).finally(() => {
            this.loading = false
          })
      })
    },
    delRow (year) {
      const self = this
      self.fixed = false
      this.$confirm({
        title: '是否确定删除?',
        onOk () {
          return self.$http.post('/financialCondition/delYear', { year: year })
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
              }
            }).finally(res => {
              self.loadData()
            })
        },
        onCancel () {
        }
      })
    },
    clickRow (y) {
      this.icon[`icon${y}`] = false
      this.ckObj[`ck${y}`] = true
      var value = {}
      for (var i = 0; i < this.tableDatas.length; i++) {
        var row = this.tableDatas[i]
        value[row.dataFiled] = row[y]
      }
      this.dataBak[y] = JSON.parse(JSON.stringify(value))
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.loadData()
    },
    openUploadModal () {
      this.$refs.uploadModal.show(this.tableField)
    },
    onCellChange (value, record, name) {
      this.$set(record, name, value)
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
        this.$message.success(`${fileName}导入成功`)
      }
      this.loadData()
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    clickSubmit (year) {
      var value = {}
      for (var i = 0; i < this.tableDatas.length; i++) {
        var row = this.tableDatas[i]
        value[row.dataFiled] = row[year]
        value.year = year
      }
      this.$http.post('/financialCondition/updateFinancialCond', value)
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('更新成功')
            this.ckObj[`ck${year}`] = false
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
          }
        })
        .finally(res => {
          this.confirmLoading = false
        })
    },
    clickClose (y) {
      const bak = this.dataBak[y]
      for (var i = 0; i < this.tableDatas.length; i++) {
        this.tableDatas[i][y] = bak[this.tableDatas[i].dataFiled]
      }
      this.ckObj[`ck${y}`] = false
    },
    reverseTable () {
      const tableData = this.tableData
      this.tableData = this.tableColumn.map(column => {
        const item = { 0: column.title }
        tableData.forEach((row, rowIndex) => {
          item[rowIndex + 1] = row[column.field]
        })
        return item
      })
      this.tableColumn = [{
        field: '0',
        fixed: 'left',
        width: 250
      }].concat(tableData.map((item, index) => {
        return {
          field: `${index + 1}`,
          minWidth: 120
        }
      }))
    }
  }
}
</script>
<style lang="less" scoped>
#scrollContent {
  height: calc(100% - 40px);
  overflow: auto;
}
</style>
