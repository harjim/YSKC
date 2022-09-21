
<template>
  <a-modal
    :width="1000"
    :visible="visible"
    :title="title"
    @cancel="visible = false"
    :maskClosable="false"
  >
    <template v-slot:footer>
      <a-button @click="visible = false">关闭</a-button>
    </template>
    <a-spin tip="请稍后..." :spinning="spinning">
      <div>
        <a-form layout="inline">
          <a-form-item label="姓名">
            <a-input style="width:165px;" v-model="queryParam.ename" placeholder="请输入姓名" />
          </a-form-item>
          <a-form-item label="工号">
            <a-input style="width:165px;" v-model="queryParam.enumber" placeholder="请输入工号" />
          </a-form-item>
          <a-form-item label="研发部门">
            <a-input style="width:165px;" v-model="queryParam.deptName" placeholder="请输入研发部门" />
          </a-form-item>
          <a-form-item label="项目角色">
            <a-input style="width:165px;" v-model="queryParam.role" placeholder="请输入项目角色" />
          </a-form-item>
          <a-form-item label="专业">
            <a-input style="width:165px;" v-model="queryParam.specialities" placeholder="请输入专业" />
          </a-form-item>
          <a-form-item>
            <span>
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
            </span>
          </a-form-item>
        </a-form>
      </div>
      <ystable
        ref="table"
        queryUrl="/projectApproval/getProjectMemberList"
        :params="queryParam"
        highlight-hover-row
        show-overflow="title"
        resizable
        auto-resize
        @completed="completed"
        @checkbox-all="selectCheckBoxChange"
        @checkbox-change="selectCheckBoxChange"
        :toolbar="{ refresh: true, zoom: true, custom: true, slots: { buttons: 'toolbar_buttons' } }"
      >
        <template v-slot:toolbar_buttons>
          <a-popconfirm
            title="是否确定清除归集费用?"
            :disabled="selectedRowKeys.length <= 0"
            @confirm="cleanEmployeeFunds">
            <a-button
              type="primary"
              style="margin-right: 10px"
              :disabled="selectedRowKeys.length <= 0"
            >清除归集费用</a-button>
          </a-popconfirm>

        </template>
        <vxe-table-column type="checkbox" width="40" align="center" fixed="left"></vxe-table-column>
        <vxe-table-column
          field="year"
          title="年份"
          width="80"
          fixed="left"
          align="center"
          remoteSort />
        <vxe-table-column field="enumber" title="工号" width="100" fixed="left" remoteSort></vxe-table-column>
        <vxe-table-column field="ename" title="姓名" width="100" fixed="left" remoteSort />

        <vxe-table-column field="position" title="职位" width="100" remoteSort />
        <vxe-table-column field="etype" title="人员类型" width="110" align="center" remoteSort>
          <template v-slot="{ row }">
            {{ !row.etype || row.etype === -1 ? '' : $getEnums('rdEmployeeEnum').find(item => item.value === row.etype).label }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="deptName" title="研发部门" width="130"/>
        <vxe-table-column field="role" title="项目角色" width="100" remoteSort></vxe-table-column>
        <vxe-table-column field="master" title="项目负责人" width="120" align="center">
          <template v-slot="{ row }">
            <span v-if="row.master">是</span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="entryDate" title="加入日期" width="130" align="center" remoteSort/>
        <vxe-table-column field="birthday" title="出生日期" width="130" align="center" remoteSort/>
        <vxe-table-column field="title" title="职称" width="100" remoteSort></vxe-table-column>
        <vxe-table-column field="specialities" title="专业" width="100" remoteSort></vxe-table-column>
        <vxe-table-column field="edate" title="入职日期" width="130" align="center" remoteSort/>
      </ystable>
    </a-spin>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'

export default {
  name: 'MemberTab',
  components: {
    ystable
  },
  props: {
    projectData: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      visible: false,
      title: '',
      spinning: false,
      selectedRowKeys: [],
      selectedRowIds: [],
      queryParam: { year: this.projectData.year, projectId: this.projectData.id }
    }
  },

  watch: {
    projectData (item) {
      this.$nextTick(() => {
        for (const key in this.queryParam) {
          this.queryParam[key] = undefined
        }
        this.queryParam.year = item.year
        this.queryParam.projectId = item.id
        this.title = `[${item.pname}]花名册`
        if (this.$refs.table) {
          this.$refs.table.refresh(true)
        }
      })
    }
  },
  methods: {
    completed () {
      this.selectedRowKeys = []
      this.selectedRowIds = []
    },
    open () {
      this.visible = true
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.selectedRowIds = []
      this.$refs.table.refresh(refresh)
    },
    selectCheckBoxChange ({ checked, records }) {
      const ids = []
      const enumbers = []
      records.forEach(item => {
        ids.push(item.id)
        enumbers.push(item.enumber)
      })
      this.selectedRowIds = ids
      this.selectedRowKeys = enumbers
    },
    cleanEmployeeFunds () {
      const self = this
      this.$confirm({
        title: `您确定要清除[${self.selectedRowKeys.join(',')}]的归集费用吗?`,
        content: '同工号的人员将会一并清除，清除人员归集费用是不可逆转的操作，这意味着您将失去相关人员的研发工资，研发五险一金，研发考勤表。',
        onOk () {
          self.executorClean()
        },
        onCancel () {
        }
      })
    },
    executorClean () {
      this.$http.post('/projectApproval/cleanEmployeeFunds', { projectId: this.projectData.id, ids: this.selectedRowIds }).then(res => {
        if (res.success && res.data) {
          this.$message.success('清除成功')
          this.search(false)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '清除失败')
        }
      })
    }
  }
}
</script>

<style>
</style>
