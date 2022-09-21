<template>
  <a-card :loading="loading">
    <a-spin :spinning="spinning">
      <a-form-model
        layout="inline"
        :model="form"
      >
        <a-form-model-item label="客户名称">
          <a-input
            v-model="form.companyName"
            @pressEnter="onSearch"
            placeholder="请输入客户名称"
          />
        </a-form-model-item>
        <a-form-model-item label="所属部门">
          <dept-select
            style="width:180px;"
            v-model="form.deptId"
            placeholder="请选择所属部门"
          />
        </a-form-model-item>
        <a-form-model-item label="项目年份">
          <a-date-picker
            format="YYYY"
            mode="year"
            placeholder="请选择项目年份"
            v-model="form.year"
            :open="isOpen"
            @openChange="changeOpen"
            @panelChange="changeYear"
          />
        </a-form-model-item>
        <a-form-model-item label="工程师">
          <a-input
            v-model="form.engineerName"
            @pressEnter="onSearch"
            placeholder="请输入工程师"
          />
        </a-form-model-item>
        <a-form-model-item label="项目类型">
          <a-select
            style="width:180px;"
            allowClear
            v-model="form.type"
            :options="projectType"
            placeholder="请选择项目类型"
          />
        </a-form-model-item>
        <a-form-model-item label="评分月份">
          <a-month-picker
            v-model="form.scoreMonth"
            placeholder="请选择评分月份"
          />
        </a-form-model-item>
        <a-form-model-item>
          <a-button
            type="primary"
            @click="onSearch"
          >查询
          </a-button>
        </a-form-model-item>
      </a-form-model>
      <ystable
        ref="table"
        :params="{
          ...form,
          year: form.year ? +moment(form.year).format('YYYY') : '',
        }"
        :toolbar="{
          custom: true,
          zoom: true,
          refresh: true
        }"
        empty-text="-"
        query-url="qualityScore/getCollectList"
        @completed="({ data }) => completed(data)"
      >
        <template #buttons>
          <div style="padding:8px 0;">
            <span
              v-if="control.weightSet"
              style="margin-right: 10px"
            >
              <a-button
                type="primary"
                @click="visible = true"
              >权值设置</a-button>
            </span>
            <span
              v-if="control.export"
              style="margin-right: 10px"
            >
              <a-button
                type="primary"
                @click="exportDataList()"
              >导出</a-button>
            </span>
            <span style="margin-right: 10px">
              <b>立项报告：</b><a>{{ header.reportNum || 0 }}</a>
            </span>
            <span style="margin-right: 10px">
              <b>留存备查：</b><a>{{ header.backupFileNum || 0 }}</a>
            </span>
            <span style="margin-right: 10px">
              <b>过程文档：</b><a>{{ header.docNum || 0 }}</a>
            </span>
            <span style="margin-right: 10px">
              <b>创新体系建设：</b><a>{{ header.innovationNum || 0 }}</a>
            </span>
            <span style="margin-right: 10px">
              <b>查新报告：</b><a>{{ header.noveltyNum || 0 }}</a>
            </span>
            <span style="margin-right: 10px">
              <b>完成数：</b><a>{{ header.finishNum || 0 }}</a>
            </span>
            <span style="margin-right: 10px">
              <b>未完成数：</b><a>{{ header.unfinishNum || 0 }}</a>
            </span>
            <span style="margin-right: 10px">
              <b>平均分：</b><a>{{ header.avgScore || 0 }}</a>
            </span>
            <span style="margin-right: 10px">
              <b>权值合计：</b><a>{{ header.totalWeight || 0 }}</a>
            </span>
            <span style="margin-right: 10px">
              <b>J值：</b><a>{{ header.jValue || 0 }}</a>
            </span>
          </div>
        </template>
        <vxe-table-column
          width="200"
          field="companyName"
          title="客户名称"
        />
        <vxe-table-column
          width="100"
          field="year"
          align="center"
          title="项目年份"
        />
        <vxe-table-column
          width="120"
          field="deptName"
          title="所属部门"
        />
        <vxe-table-column
          width="100"
          field="type"
          title="项目类型"
        >
          <template v-slot="{ row }">
            <span>{{ type2Text(row.type) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          width="100"
          field="month"
          align="center"
          title="评分月份"
        />
        <vxe-table-column
          width="120"
          field="engineers"
          title="工程师"
        />
        <vxe-table-column
          width="120"
          field="coverage"
          title="服务区域"
        />
        <vxe-table-column
          width="100"
          field="scorer"
          title="评分人"
        />
        <vxe-table-column
          width="100"
          field="count"
          title="总数量"
          type="expand"
        >
          <template v-slot="{ row }">
            <span>{{ row.count }}</span>
          </template>
          <template v-slot:content="{ row }">
            <ystable
              ref="childTable"
              :params="{
                companyId: row.companyId,
                type: row.type,
                year: row.year,
                month: row.month,
                engineerId: row.engineerId
              }"
              empty-text="-"
              auto-resize
              border
              highlight-hover-row
              :columns="childTableColumns"
              resizable
              header-align="center"
              query-url="qualityScore/getScoreList"
            />
          </template>
        </vxe-table-column>
        <vxe-table-column
          width="100"
          field="finishNum"
          title="完成数"
        />
        <vxe-table-column
          width="100"
          field="unfinishNum"
          title="未完成数"
        />
        <vxe-table-column
          width="100"
          field="onePassNum"
          title="一审通过数"
        />
        <vxe-table-column
          width="100"
          field="twoPassNum"
          title="二审通过数"
        />
        <vxe-table-column
          width="100"
          field="avgScore"
          title="平均分"
        />
        <vxe-table-column
          width="100"
          field="weight"
          title="权值"
        />
        <vxe-table-column
          width="100"
          field="totalWeight"
          title="权值合计"
        />
        <vxe-table-column
          width="100"
          field="jValue"
          title="J值"
        />
      </ystable>
    </a-spin>

    <weight-set
      :visible="visible"
      @close="closeModal"
    />
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import DeptSelect from '@/components/Selects/DeptSelect'
import WeightSet from '@/views/innovation/modules/WeightSet'
import moment from 'moment'

const projectType = [
  { key: 5, title: '立项报告' },
  { key: 6, title: '过程文档' },
  { key: 8, title: '查新报告' },
  { key: 9, title: '创新体系' },
  { key: 10, title: '留存备查' }
]
const childTableColumns = [
  { title: 'RD', field: 'rdTitle' },
  { title: '项目名称', field: 'pname' },
  {
    title: '完成状态',
    field: 'status',
    slots: {
      default: ({ row }) => {
        return row.status === 1 ? '完成' : '未完成'
      }
    }
  },
  { title: '一提日期', field: 'oneSubmitDate' },
  { title: '创新一审日期', field: 'oneAuditDate' },
  { title: '卓效一审日期', field: 'oneScoreDate' },
  { title: '一审得分', field: 'oneScore' },
  { title: '二提日期', field: 'twoSubmitDate' },
  { title: '创新二审日期', field: 'twoAuditDate' },
  { title: '卓效二审日期', field: 'twoScoreDate' },
  { title: '二审得分', field: 'twoScore' },
  { title: '平均分', field: 'avgScore' },
  { title: '权值', field: 'totalWeight' },
  { title: '通过情况', field: 'passRate' },
  { title: 'J值', field: 'jValue' },
  { title: '备注', field: 'months' }
]

export default {
  name: 'TimeAccount',
  components: { WeightSet, DeptSelect, ystable },
  data () {
    return {
      loading: false,
      spinning: false,
      isOpen: false,
      visible: false,
      form: {
        companyName: undefined, // 客户名称
        deptId: undefined, // 所属部门
        year: undefined, // 项目年份
        type: undefined, // 项目类型
        engineerName: undefined, // 工程师
        scoreMonth: undefined // 评分月份
      },
      header: {},
      projectType,
      childTableColumns,
      control: {
        search: this.$auth('innovation:TimeAccount:search'),
        weightSet: this.$auth('innovation:TimeAccount:weightSet'),
        export: this.$auth('innovation:TimeAccount:export')
      }
    }
  },
  created () {},
  methods: {
    moment,
    onSearch () {
      this.$refs.table.refresh(true)
    },
    changeYear (v) {
      this.form.year = v
      this.isOpen = false
    },
    changeOpen (status) {
      this.isOpen = status
    },
    completed (data) {
      this.header = data.header
    },
    closeModal () {
      return new Promise(resolve => {
        this.visible = false
        return resolve()
      })
    },
    exportDataList () {
      this.spinning = true
      this.$exportData('/qualityScore/export', {
        ...this.form,
        year: this.form.year ? moment(this.form.year).format('YYYY') : ''
      }, `审核序时帐.xlsx`, this.$message).finally(() => {
        this.spinning = false
      })
    },
    type2Text (type) {
      return projectType.find(item => item.key === type).title
    }
  }
}
</script>

<style scoped>
.table-btn {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-right: 20px;
}
</style>
