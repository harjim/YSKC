<template>
  <a-modal
    :visible="isVisible"
    :afterClose="onAfterClose"
    :maskClosable="false"
    :width="950"
    :title="title"
    @cancel="isVisible = false"
    @ok="onHandleSubmit"
  >
    <vxe-grid
      ref="table"
      highlight-hover-row
      highlight-current-row
      show-overflow="title"
      resizable
      auto-resize
      :data="data"
    >
      <vxe-table-column title="序号" type="seq" width="60" header-align="center" align="center"></vxe-table-column>
      <vxe-table-column
        title="项目阶段"
        field="stageKey"
        width="100"
        header-align="center"
        align="left"
        remoteSort
      >
        <template v-slot="{ row }">
          {{ stagesObj[row.stageKey] }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="项目状态"
        field="status"
        width="100"
        header-align="center"
        align="left"
        remoteSort
      >
        <template v-slot="{ row }">
          {{ statussObj[row.status] }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="项目标签"
        field="labelKey"
        width="100"
        header-align="center"
        align="left"
        remoteSort
      >
        <template v-slot="{ row }">
          {{ labelsObj[row.labelKey] }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="进度情况"
        field="process"
        min-width="200"
        header-align="center"
        align="left"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column
        title="项目问题反馈"
        field="feedback"
        min-width="200"
        header-align="center"
        align="left"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column
        title="工程师"
        field="techName"
        width="100"
        header-align="center"
        align="left"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column
        title="备注"
        field="remark"
        min-width="120"
        header-align="center"
        align="left"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column
        title="更新时间"
        field="lastUpdateTime"
        min-width="120"
        header-align="center"
        align="center"
        remoteSort
      >
      </vxe-table-column>
      <vxe-table-column title="操作" min-width="120" header-align="center" align="center" fixed="right">
        <template v-slot="{ row }">
          <a v-if="$auth('project:tech:edit')" @click="OnEditProgressDetail(row)">编辑</a>
          <span v-if="$auth('project:tech:del')">
            <a-divider v-if="$auth('project:tech:edit')" type="vertical"/>
            <a-popconfirm title="是否确定删除?" @confirm="handleDelete(row)">
              <a>删除</a>
            </a-popconfirm>
          </span>
        </template>
      </vxe-table-column>
    </vxe-grid>
    <add-progess-detail-modal ref="addProgressDetailModal" @ok="onRefreshTable" />
    <template slot="footer">
      <a-button type="primary" @click="isVisible = false">关闭</a-button>
    </template>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'
import addProgessDetailModal from './AddProgressDetailModal'
export default {
  name: 'ProgressListModal',
  components: {
    ystable,
    addProgessDetailModal
  },
  data () {
    return {
      isVisible: false,
      title: '',
      queryParams: {},
      projectId: -1,
      data: [],
      stagesObj: {},
      statussObj: {},
      labelsObj: {},
      stages: [],
      labels: [],
      statuss: [],
      isParentRefresh: true // true 不刷新父数据 false 刷新父数据
    }
  },
  methods: {
    show (row, stages, labels, statuss, stagesObj, statussObj, labelsObj) {
      this.isVisible = true
      this.title = '进度详情'
      this.data = row.logList
      this.stagesObj = stagesObj
      this.statussObj = statussObj
      this.labelsObj = labelsObj
      this.stages = stages
      this.labels = labels
      this.statuss = statuss
      this.projectId = row.id
      this.isParentRefresh = true
    },
    // modal 关闭之后的方法
    onAfterClose () {
      this.isVisible = false
      this.title = ''
      this.projectId = -1
      this.data = []
      this.stagesObj = {}
      this.statussObj = {}
      this.labelsObj = {}
      this.stages = []
      this.labels = []
      this.statuss = []
      this.$emit('refreshTabel', false, this.isParentRefresh)
    },
    // 显示 编辑 进度情况modal
    OnEditProgressDetail (row) {
      this.$refs.addProgressDetailModal.edit(row, this.stages, this.labels, this.statuss)
    },
    // 提交事件
    onHandleSubmit () {
      this.isVisible = false
    },
    onRefreshTable (isrefresh, record) {
      this.isParentRefresh = false
      this.data = this.data.map(item => {
        if (item.id === record.id) {
          Object.assign(item, record)
        }
        return item
      })
    },
    // 删除阶段日志
    handleDelete (row) {
      this.isParentRefresh = false
      this.$http.post('projectTechLog/delTechLog', { id: row.id, projectId: row.projectId }).then((res) => {
        if (res.data && res.success) {
          this.data = this.data.filter(item => {
            if (item.id === row.id) {
              return false
            }
            return true
          })
          this.$message.success('删除成功')
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
    }
  }
}
</script>

<style>
</style>
