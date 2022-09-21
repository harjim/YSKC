<template>
  <a-card style="height: 100%" :bodyStyle="{ height: '100%', overflow: 'auto'}" >
    <a-spin
      tip="请稍后..."
      :spinning="spin">
      <div>
        <a-form layout="inline">
          <a-form-item label="项目">
            <project-select
              style="width:400px"
              ref="creatProject"
              :year="currentYear"
              :sign="2"
              allowClear
              :isJoinPrjectName="true"
              :selected="queryParams.projectId"
              v-model="queryParams.projectId"
            ></project-select>
          </a-form-item>
          <a-form-item label="月份" >
            <a-select
              v-model="queryParams.monthValue"
              placeholder="请选择月份"
              :allowClear="true"
              :options="monthOptions"
              style="width: 165px;"
            ></a-select>
          </a-form-item>
          <a-form-item label="部门" >
            <a-input
              v-model="queryParams.deptName"
              placeholder="请输入部门"
              style="width: 165px;"
            />
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              @click="search(true)"
              v-if="control.view"
            >查询</a-button
            >
          </a-form-item>
        </a-form>
      </div>
      <ystable
        rowId="id"
        ref="table"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        show-header-overflow
        header-align="center"
        :toolbar="{zoom:true,custom:true,refresh:true}"
        :params="getParam()"
        queryUrl="/projectFinaSchedule/getList"
        @checkbox-all="selectCheckBoxChange"
        @checkbox-change="selectCheckBoxChange"
      >
        <template v-slot:buttons>
          <a-button type="primary" v-if="control.add" @click="$refs.finaScheduleModal.add()" style="margin-right:10px;">添加</a-button>
          <a-button type="primary" @click="importData()" v-if="control.import" style="margin-right:10px;">导入</a-button>
          <a-button type="primary" @click="exportData()" v-if="control.export" style="margin-right:10px;">导出</a-button>
          <a-button type="primary" v-if="control.delete" @click="batchDelete" :disabled="selectedRowsIds.length<=0" style="margin-right:10px;">删除</a-button>
          <a-button type="primary" @click="aggFee()" v-if="control.agg" style="margin-right:10px;">归集费用</a-button>
        </template>
        <vxe-table-column
          type="checkbox"
          width="40"
          fixed="left"
          align="center"
        />
        <vxe-table-column
          field="rdTitle"
          title="RD"
          remoteSort
          width="130"
          fixed="left"
          align="center"
        />
        <vxe-table-column
          field="pname"
          remoteSort
          title="项目名称"
          width="260"
          fixed="left"
        />
        <vxe-table-column
          field="eas"
          title="EAS编号"
          remoteSort
          width="140"
          fixed="left"
        />
        <vxe-table-column
          field="month"
          title="月份"
          remoteSort
          width="120"
          align="center"
        />
        <vxe-table-column
          field="deptName"
          remoteSort
          title="部门"
          width="180"
        />
        <vxe-table-column
          field="workHours"
          title="总运行工时"
          width="140"
          align="right"
        />
        <vxe-table-column
          field="testHour"
          title="试验实际工时"
          width="180"
          align="right"
        />
        <vxe-table-column
          field="trialHour"
          title="试制实际工时"
          width="180"
          align="right"
        />
        <vxe-table-column
          title="操作"
          width="120"
          fixed="right"
        >
          <template v-slot="{row}">
            <a @click="$refs.finaScheduleModal.edit(row)" v-if="control.edit">编辑</a>
            <template v-if="control.delete">
              <a-divider type="vertical" v-if="control.edit" />
              <a-popconfirm title="是否确定删除?" @confirm="deleteRow([row.id])">
                <a>删除</a>
              </a-popconfirm>
            </template>
          </template>
        </vxe-table-column>
      </ystable>
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        :paramData="paramData"
        paramKey="name"
        :title="`导入实验试制实际工时`"
        ref="uploadModal"
        action="/doc/projectFinaSchedule/importFinaSchedule"
        templateName="实验试制实际工时模板"
        @onSuccess="success"
      />
      <project-fina-schedule-agg-modal ref="finaScheduleAgg"/>
      <project-fina-schedule-modal ref="finaScheduleModal" @ok="search(true)"/>
    </a-spin>
  </a-card>
</template>

<script>
import ProjectFinaScheduleModal from './modules/ProjectFinaScheduleModal'
import ProjectSelect from '@/components/ProjectSelect'
import ystable from '@/components/Table/ystable'
import UploadModal from '@/components/UploadModal'
import yearMixin from '@/utils/yearMixin'
import ProjectFinaScheduleAggModal from './modules/ProjectFinaScheduleAggModal'
import moment from 'moment'
export default {
  components: {
    UploadModal,
    ProjectFinaScheduleAggModal,
    ystable,
    ProjectSelect,
    ProjectFinaScheduleModal
  },
  mixins: [yearMixin],
  data () {
    return {
      queryParams: {},
      selectedRowsIds: [],
      spin: false,
      paramData: {},
      control: {
        view: this.$auth('project:finaSchedule:view'),
        add: this.$auth('project:finaSchedule:add'),
        edit: this.$auth('project:finaSchedule:edit'),
        import: this.$auth('project:finaSchedule:import'),
        export: this.$auth('project:finaSchedule:export'),
        agg: this.$auth('project:finaSchedule:agg'),
        delete: this.$auth('project:finaSchedule:delete')
      },
      tableField: {
        tableId: 'finaScheduleTable',
        fieldTitleObject: {
          rdTitle: { title: 'RD', required: true, defaultTitle: 'RD', importField: true },
          eas: { title: 'EAS编号', defaultTitle: 'EAS编号', importField: true },
          month: { title: '月份', required: true, defaultTitle: '月份', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-01' },
          deptName: { title: '部门', required: true, defaultTitle: '部门', importField: true },
          workHours: { title: '总运行工时', defaultTitle: '总运行工时', importField: true },
          testHour: { title: '试验实际工时', defaultTitle: '试验实际工时', importField: true },
          trialHour: { title: '试制实际工时', defaultTitle: '试制实际工时', importField: true }
        },
        hasMonth: true
      },
      sampleData: [
        {
          rdTitle: '2019RD01',
          eas: 'EAS0001',
          month: '格式："年-月-日"，例如：2019-10-01',
          deptName: '烧成',
          workHours: '数字格式，例如：744',
          testHour: '数字格式，例如：10',
          trialHour: '数字格式，例如：10'
        }
      ]
    }
  },
  methods: {
    getParam () {
      const params = { year: this.currentYear }
      if (this.queryParams.projectId) {
        params.projectId = this.queryParams.projectId
      }
      if (this.queryParams.deptName) {
        params.deptName = this.queryParams.deptName
      }
      if (this.queryParams.monthValue) {
        params.month = moment([this.currentYear, this.queryParams.monthValue, 1, 0, 0, 0, 0])
      }
      return params
    },
    success () {
      this.$message.success('导入成功')
      this.search()
    },
    moment,
    search (refresh) {
      this.selectedRowsIds = []
      this.$nextTick(() => {
        this.$refs.table.refresh(refresh)
      })
    },
    batchDelete () {
      this.spin = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的研发实际工时吗?',
        onOk () {
          self.deleteRow(self.selectedRowsIds)
        },
        onCancel () {
          self.spin = false
        }
      })
    },
    deleteRow (ids) {
      this.spin = true
      this.$http.post('/projectFinaSchedule/del', { ids }).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
          this.search()
        } else {
          this.$message.error(res.errorMessage || '删除失败')
        }
      }).finally(() => {
        this.spin = false
      })
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowsIds = records.map(a => a.id)
    },
    importData () {
      this.paramData.year = this.currentYear
      this.$refs.uploadModal.show(this.tableField)
    },
    exportData () {
      this.spin = true
      this.$exportData('/projectFinaSchedule/exportFinaSchedule', this.getParam(), undefined, this.$message).then(res => {
        this.spin = false
      })
    },
    aggFee () {
      this.$refs.finaScheduleAgg.show()
    }
  }
}
</script>
