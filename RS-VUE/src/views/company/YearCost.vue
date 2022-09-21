<template>
  <a-spin :spinning="spin" tip="请稍后...">
    <a-card :bordered="false" :bodyStyle="{ height: '100%', overflow: 'auto' }">
      <div>
        <vxe-grid
          resizable
          ref="table"
          highlight-hover-row
          show-overflow
          auto-resize
          :data="tableData"
          @checkbox-all="selectAllEvent"
          @checkbox-change="selectChangeEvent"
          :toolbar="{ refresh: {query: queryData}, zoom: true, custom: true,slots: { buttons: 'buttons' } }"
        >
          <template v-slot:buttons>
            <a-button type="primary" @click="$refs.yearCostModal.open(false)" v-if="$auth('company:yearcost:add')">添加</a-button>&nbsp;
            <a-button type="primary" @click="batchDel(true, null)" :disabled="!deletable" v-if="$auth('company:yearcost:del')">删除</a-button>&nbsp;
            <a-button type="primary" @click="$refs.uploadModal.show(tableField)" v-if="$auth('company:yearCost:import')">导入</a-button>
            <span style="float:right;">单位：万元</span>
          </template>
          <vxe-table-column
            type="checkbox"
            width="60"
            fixed="left"></vxe-table-column>
          <vxe-table-column
            title="年份"
            field="year"
            :width="80"
            align="center"
            sortable
            fixed="left"></vxe-table-column>
          <vxe-table-column
            title="月份"
            field="month"
            :width="80"
            align="center"
            sortable
            fixed="left"
          ></vxe-table-column>
          <vxe-table-column title="工资" field="wages" :width="150" align="center" sortable>
            <template v-slot="{ row }">{{
              row.wages || row.wages == 0 ? Number(row.wages).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="五险一金" field="insuranceAndFund" :width="160" align="center" sortable>
            <template v-slot="{ row }">{{
              row.insuranceAndFund || row.insuranceAndFund == 0 ? Number(row.insuranceAndFund).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="原材料成本" field="material" :width="170" align="center" sortable>
            <template v-slot="{ row }">{{
              row.material || row.material == 0 ? Number(row.material).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="折旧费" field="depreciation" :width="150" align="center" sortable>
            <template v-slot="{ row }">{{
              row.depreciation || row.depreciation == 0 ? Number(row.depreciation).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="动力费" field="power" :width="150" align="center" sortable>
            <template v-slot="{ row }">{{
              row.power || row.power == 0 ? Number(row.power).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="燃料费" field="fuel" :width="150" align="center" sortable>
            <template v-slot="{ row }">{{ row.fuel || row.fuel == 0 ? Number(row.fuel).toFixed(2) : '--' }}</template>
          </vxe-table-column>
          <vxe-table-column title="备品件" field="trial" :width="160" align="center" sortable>
            <template v-slot="{ row }">{{
              row.trial || row.trial == 0 ? Number(row.trial).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="检测费对应成本" field="test" :width="200" align="center" sortable>
            <template v-slot="{ row }">{{ row.test || row.test == 0 ? Number(row.test).toFixed(2) : '--' }}</template>
          </vxe-table-column>
          <vxe-table-column title="修理费" field="repair" :width="150" align="center" sortable>
            <template v-slot="{ row }">{{
              row.repair || row.repair == 0 ? Number(row.repair).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="样机费" field="machine" :width="160" align="center" sortable>
            <template v-slot="{ row }">{{
              row.machine || row.machine == 0 ? Number(row.machine).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="设计费" field="design" :width="160" align="center" sortable>
            <template v-slot="{ row }">{{
              row.design || row.design == 0 ? Number(row.design).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="软件费" field="software" :width="160" align="center" sortable>
            <template v-slot="{ row }">{{
              row.software || row.software == 0 ? Number(row.software).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="其他摊销费" field="otherAmortization" :width="170" align="center" sortable>
            <template v-slot="{ row }">{{
              row.otherAmortization || row.otherAmortization == 0 ? Number(row.otherAmortization).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="其他费用" field="other" :width="150" align="center" sortable>
            <template v-slot="{ row }">{{
              row.other || row.other == 0 ? Number(row.other).toFixed(2) : '--'
            }}</template>
          </vxe-table-column>
          <vxe-table-column title="操作" :width="120" align="center" fixed="right">
            <span slot-scope="{ row }">
              <a @click="$refs.yearCostModal.open(true, row)" v-if="$auth('company:yearcost:edit')">编辑</a>
              <a-divider type="vertical" />
              <a-popconfirm title="是否确定删除?" @confirm="del(false, row)" v-if="$auth('company:yearcost:del')">
                <a>删除</a>
              </a-popconfirm>
            </span>
          </vxe-table-column>
        </vxe-grid>
      </div>
    </a-card>
    <year-cost-modal ref="yearCostModal" @ok="search"></year-cost-modal>
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      paramKey="month"
      title="导入月度成本"
      templateName="月度成本模板"
      ref="uploadModal"
      action="/doc/yearCost/importYearCost"
      @onSuccess="success"
      @error="error"
    ></upload-modal>
  </a-spin>
</template>
<script>
import YearCostModal from './modules/YearCostModal'
import { UploadModal } from '@/components'
export default {
  components: {
    YearCostModal,
    UploadModal
  },
  data () {
    return {
      spin: false,
      showCheckBox: false,
      tableData: [],
      selectedYears: [],
      sampleData: [
        {
          year: '年份,例如 2022',
          month: '月份,例如 1',
          wages: '',
          insuranceAndFund: '',
          material: '',
          depreciation: '',
          power: '',
          fuel: '',
          trial: '',
          test: '',
          repair: '',
          machine: '',
          design: '',
          software: '',
          otherAmortization: '',
          other: ''
        }
      ],
      tableField: {
        tableId: 'dataYearCostTable',
        fieldTitleObject: {
          year: { title: '年份', required: true, defaultTitle: '年份', importField: true },
          month: { title: '月份', required: true, defaultTitle: '月份', importField: true },
          wages: { title: '工资 (单位/万元)', required: true, defaultTitle: '工资', importField: true },
          insuranceAndFund: { title: '五险一金 (单位/万元)', required: false, defaultTitle: '五险一金', importField: true },
          material: { title: '原材料成本 (单位/万元)', required: false, defaultTitle: '原材料成本', importField: true },
          depreciation: { title: '折旧费 (单位/万元)', required: false, defaultTitle: '折旧费', importField: true },
          power: { title: '动力费 (单位/万元)', required: false, defaultTitle: '动力费', importField: true },
          fuel: { title: '燃料费 (单位/万元)', required: false, defaultTitle: '燃料费', importField: true },
          trial: { title: '备品件 (单位/万元)', required: false, defaultTitle: '备品件', importField: true },
          test: { title: '检测费对应成本 (单位/万元)', required: false, defaultTitle: '检测费对应成本', importField: true },
          repair: { title: '修理费 (单位/万元)', required: false, defaultTitle: '修理费', importField: true },
          machine: { title: '样机费 (单位/万元)', required: false, defaultTitle: '样机费', importField: true },
          design: { title: '设计费 (单位/万元)', required: false, defaultTitle: '设计费', importField: true },
          software: { title: '软件费 (单位/万元)', required: false, defaultTitle: '软件费', importField: true },
          otherAmortization: { title: '其他摊销费 (单位/万元)', required: false, defaultTitle: '其他摊销费', importField: true },
          other: { title: '其他费用 (单位/万元)', required: false, defaultTitle: '其他费用', importField: true }
        }
      }
    }
  },
  created () {
    this.queryData()
  },
  computed: {
    deletable () {
      return this.selectedYears.length > 0
    }
  },
  methods: {
    search () {
      this.selectedYears = []
      this.queryData()
    },
    selectChangeEvent ({ checked, records }) {
      this.selectedYears = records.map(item => {
        return { year: item.year, month: item.month }
      })
    },
    selectAllEvent ({ checked, records }) {
      this.selectedYears = records.map(item => {
        return { year: item.year, month: item.month }
      })
    },
    batchDel () {
      this.$confirm({
        title: '是否确定删除?',
        onOk () {
          this.del(true, null)
        },
        onCancel () {}
      })
    },
    del (isBatch, row) {
      let years = []
      if (isBatch) {
        if (this.selectedYears.length > 0) {
          years = this.selectedYears
        }
      } else {
        years.push({ year: row.year, month: row.month })
      }
      this.$http.post('/yearCost/del', years).then(res => {
        if (res.success) {
          this.$message.success('删除成功')
          this.search()
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
        }
      })
    },
    queryData () {
      this.spin = true
      this.$http.get('/yearCost/getList').then(res => {
        this.tableData = res.data
        this.spin = false
        return true
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
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    }
  }
}
</script>
