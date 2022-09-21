<template>
  <a-card :bordered="false">
    <template v-if="$auth('project:tech:search')">
      <a-form layout="inline">
        <a-form-item label="客户名称">
          <a-input style="width:160px;" placeholder="请输入客户名称" v-model="queryParams.companyName" />
        </a-form-item>
        <a-form-item label="项目名称">
          <a-input style="width:160px;" v-model="queryParams.ptName" placeholder="请输入项目名称" />
        </a-form-item>
        <a-form-item label="阶段">
          <a-select style="width: 160px;" placeholder="请选择项目阶段" :allowClear="true" v-model="queryParams.stageKey">
            <a-select-option :value="item.key" v-for="item in stages" :key="item.key">
              {{ item.value }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="状态">
          <a-select style="width: 160px;" placeholder="请选择状态" :allowClear="true" v-model="queryParams.status">
            <a-select-option :value="item.key" v-for="item in statuss" :key="item.key">
              {{ item.value }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="年份">
          <year-select style="width:160px;" v-model="queryParams.year" placeholder="请选择年份" />
        </a-form-item>
        <a-form-item label="所属部门">
          <dept-select-only ref="deptSelect" @deptFullPath="path => (queryParams.fullPath = path)" style="width:160px;" />
        </a-form-item>
        <a-form-item label="技术人员">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="true"
            placeholder="请输入技术人员"
            style="width:160px;"
            v-model="queryParams.tIds"
          />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="onQuery" v-if="$auth('project:tech:search')">查询</a-button>
        </a-form-item>
      </a-form>
      <ystable
        ref="table"
        queryUrl="/projectTechLog/getTechList"
        :params="getParams ()"
        @completed="()=>selectRowIds=[]"
        max-height="100%"
        highlight-current-row
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        @checkbox-all="selectChange"
        @checkbox-change="selectChange"
        :tree-config="{children: 'logList'}"
        :checkbox-config="{ checkMethod: checkMethod }"
        :toolbar="{ refresh:true, zoom: true, custom: true, slots: { buttons: 'toolbar_buttons' } }"
      >
        <template v-slot:toolbar_buttons>
          <a-button
            type="primary"
            :disabled="selectRowIds.length <= 0"
            @click="onBatchSetSatageEndDate"
            v-if="$auth('project:tech:setTime')"
          >设置阶段截止日期</a-button
          >
        </template>
        <vxe-table-column type="checkbox" width="50" header-align="center" align="center"> </vxe-table-column>
        <vxe-table-column title="序号" type="seq" width="55" header-align="center" align="center"></vxe-table-column>
        <vxe-table-column
          title="客户名称"
          width="150"
          header-align="center"
          align="left"
          tree-node
          remoteSort>
          <template v-slot="{ row }">
            <span v-if="$auth('project:tech:search')">
              <a title="点击查看项目信息" @click="onViewProject(row)">{{ row.companyName }}</a>
            </span>
            <span v-else>
              {{ row.companyName }}
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="所属部门"
          field="deptName"
          width="150"
          header-align="center"
          align="left"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="年份"
          field="year"
          width="100"
          header-align="center"
          align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="项目名称"
          field="productName"
          width="150"
          header-align="center"
          align="left"
          remoteSort>
          <template v-slot="{ row }">
            <span v-if="$auth('project:tech:searchStage')">
              <a title="点击设置项目阶段" @click="onConfigStage(row)">{{ row.productName }}</a>
            </span>
            <span v-else>
              {{ row.productName }}
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="项目接收日期"
          field="projectCreateTime"
          width="150"
          header-align="center"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            {{ handleProjectCreateTime(row.projectCreateTime) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="技术人员"
          width="120"
          header-align="center"
          align="left"
          remoteSort
        >
          <template v-slot="{ row }">
            {{ processTechs(row) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="项目阶段"
          field="stageKey"
          width="150"
          header-align="center"
          align="left"
          remoteSort>
          <template v-slot="{ row }">
            {{ stagesObj[row.stageKey] }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="预投入金额（万）"
          field="budget"
          width="160"
          header-align="center"
          align="right"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="截止日期"
          field="deadline"
          width="120"
          header-align="center"
          align="center"
          remoteSort>
          <template v-slot="{ row }">
            <span v-if="row.hasStage">
              <span v-if="row.deadline && $auth('project:tech:searchTime')">
                <a title="点击设置截止日期" @click="onSetStageEndDate(row)">{{ row.deadline | DayFormat }}</a>
              </span>
              <span v-else>
                <a v-if="row.hasStage && $auth('project:tech:searchTime')" title="点击设置截止日期" @click="onSetStageEndDate(row)"> - - </a>
                <span v-else> - - </span>
              </span>
            </span>
            <span v-else>
              <span v-if="row.deadline"> {{ row.deadline | DayFormat }} </span>
              <span v-else>- -</span>
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="进度情况"
          field="process"
          width="150"
          header-align="center"
          align="left"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="问题反馈"
          field="feedback"
          width="150"
          header-align="center"
          align="left"
          remoteSort>
          <template v-slot="{ row }">
            {{ row.feedback === null ? '' : row.feedback }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="完成日期"
          field="completedDate"
          width="120"
          header-align="center"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            {{ row.completedDate | DayFormat }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="更新时间"
          field="lastUpdateTime"
          width="150"
          header-align="center"
          align="center"
          remoteSort
        >
        </vxe-table-column>
        <vxe-table-column title="操作" width="120" header-align="center" align="center" fixed="right">
          <template v-slot="{ row }">
            <span v-if="row.hasStage">
              <span v-if="$auth('project:tech:add')">
                <a v-if="row.hasStage" @click="onUpdataProgress(row)" :disabled="!row.hasStage" >更新进度</a>
                <a-tooltip v-else>
                  <template slot="title">
                    请配置阶段
                  </template>
                  <span style="color: #BFBFBF">更新进度</span>
                </a-tooltip>
              </span>
            </span>
            <span v-else>
              <a v-if="$auth('project:tech:edit')" @click="OnEditProgressDetail(row)">编辑</a>
              <span v-if="$auth('project:tech:del')">
                <a-divider v-if="$auth('project:tech:edit')" type="vertical"/>
                <a-popconfirm title="是否确定删除?" @confirm="handleDelete(row)">
                  <a>删除</a>
                </a-popconfirm>
              </span>
            </span>
          </template>
        </vxe-table-column>
      </ystable>
      <config-stage ref="configStageModal" @ok="()=> { onQuery(false) }" />
      <set-stage-end-date-modal ref="setStageEndDateModal" @ok="()=> { onQuery(false) }" />
      <add-progess-detail-modal ref="addProgressDetailModal" @ok="onQuery" @refreshData="refreshData" />
      <batch-set-satage-end-date-modal ref="batchSetSatageEndDateModal" @ok="()=> { onQuery(false) }" />
      <view-project-modal ref="viewProjectModal"/>
    </template>
  </a-card>
</template>

<script>
import { YearSelect, SearchSelect } from '@/components/Selects'
import { DeptSelectOnly } from '@/components'
import ystable from '@/components/Table/ystable'
import ConfigStage from './modules/ConfigStage'
import SetStageEndDateModal from './modules/SetStageEndDateModal'
import addProgessDetailModal from './modules/AddProgressDetailModal'
import BatchSetSatageEndDateModal from './modules/BatchSetSatageEndDateModal'
import ViewProjectModal from './modules/ViewProjectModal'
import moment from 'moment'
export default {
  name: 'TechnicalProgress',
  components: {
    ystable,
    // ProgressListModal,
    YearSelect,
    SearchSelect,
    DeptSelectOnly,
    ConfigStage,
    SetStageEndDateModal,
    addProgessDetailModal,
    BatchSetSatageEndDateModal,
    ViewProjectModal
  },
  data () {
    return {
      queryParams: {
        companyName: undefined,
        ptName: undefined,
        fullPath: undefined
      },
      stages: [],
      stagesObj: {}, // 用于显示
      labels: [],
      labelsObj: {}, // 用于显示
      statuss: [],
      statussObj: {}, // 用于显示
      selectRowIds: [],
      row: {}
    }
  },
  mounted () {
    // 获取阶段字典
    this.$getDictionary(7).then(res => {
      this.stages = res
      this.stages.forEach(item => {
        this.stagesObj[item.key] = item.value
      })
    })
    // 获取标签字典
    this.$getDictionary(8).then(res => {
      this.labels = res.sort((a, b) => {
        return a.order - b.order
      })
      this.labels.forEach(item => {
        this.labelsObj[item.key] = item.value
      })
    })
    // 获取状态字典
    this.$getDictionary(9).then(res => {
      this.statuss = res
      this.statuss.forEach(item => {
        this.statussObj[item.key] = item.value
      })
    })
  },
  methods: {
    moment,
    getParams () {
      const params = {
        companyName: this.queryParams.companyName,
        ptName: this.queryParams.ptName,
        stageKey: this.queryParams.stageKey,
        status: this.queryParams.status,
        year: this.queryParams.year,
        fullPath: this.queryParams.fullPath
      }
      if (this.queryParams.tIds) { params.tIds = this.queryParams.tIds.map(a => a.id) }
      return params
    },
    // 刷新表格数据
    onQuery (refresh = true) {
      this.$refs.table.clearCheckboxRow()
      this.$refs.table.refresh(refresh)
      this.selectRowIds = []
    },
    // 修改刷新当前行数据
    refreshData (record, data) {
      Object.assign(record, data)
    },
    // 添加（更新）进度
    onUpdataProgress (row) {
      this.$refs.addProgressDetailModal.show(row, this.stages, this.labels, this.statuss, this.stagesObj)
    },
    // 编辑进度
    onEditProgress (row) {
      this.$refs.addProgressDetailModal.edit(row, this.stages, this.labels, this.statuss)
    },
    // 显示 项目配置阶段Modal
    onConfigStage (record) {
      this.$refs.configStageModal.show(record, this.stages)
    },
    // 显示 设置项目阶段截止日期
    onSetStageEndDate (record) {
      this.$refs.setStageEndDateModal.show(record)
    },
    // 显示 批量设置截止日期
    onBatchSetSatageEndDate () {
      this.$refs.batchSetSatageEndDateModal.show(this.selectRowIds, this.stages)
    },
    // 表格复选框事件
    selectChange ({ records }) {
      this.selectRowIds = records.map(item => {
        return item.id
      })
    },
    // 批量分配modal关闭后，重置selectRowIds
    onResetSelectedRow () {
      this.selectRowIds = []
    },
    // 处理禁用复选框
    checkMethod ({ row }) {
      return row.hasStage
    },
    // 处理技术人员显示
    processTechs (record) {
      if (record.techs) {
        let techNames = ''
        record.techs.forEach((item, index) => {
          techNames += item.realName + ','
        })
        return techNames.substring(0, techNames.length - 1)
      } else if (record.techName) {
        return record.techName
      } else {
        return ''
      }
    },
    // 处理显示表格的项目日期
    handleProjectCreateTime (dateStr) {
      if (dateStr) {
        return moment(dateStr).format('YYYY-MM-DD')
      } else {
        return ''
      }
    },
    // 显示 编辑 进度情况modal
    OnEditProgressDetail (row) {
      this.$refs.addProgressDetailModal.edit(row, this.stages, this.labels, this.statuss)
    },
    // 删除阶段日志
    handleDelete (row) {
      this.isParentRefresh = false
      this.$http.post('projectTechLog/delTechLog', { id: row.id, projectId: row.projectId }).then((res) => {
        if (res.data && res.success) {
          this.$message.success('删除成功')
          this.onQuery()
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        if (error.message.includes('403')) {
          this.$message.error('无此权限，请跟管理员联系！')
        } else {
          this.$message.error('操作失败：' + error.message)
        }
      })
    },
    // 查看项目信息
    onViewProject (row) {
      this.$refs.viewProjectModal.open(row.id)
    }
  }
}
</script>

<style></style>
