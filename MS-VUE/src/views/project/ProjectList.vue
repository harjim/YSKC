<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spin">
      <a-form layout="inline">
        <a-form-item label="客户名称">
          <a-input style="width: 160px" placeholder="客户名称" v-model="queryParams.companyName" />
        </a-form-item>
        <a-form-item label="项目类型">
          <a-input style="width: 160px" v-model="queryParams.productName" placeholder="项目类型" />
        </a-form-item>
        <a-form-item label="年份">
          <year-select style="width: 160px" v-model="queryParams.year" placeholder="项目年份" />
        </a-form-item>
        <a-form-item label="所属部门">
          <dept-select-only
            ref="deptSelect"
            @deptFullPath="(path) => (queryParams.fullPath = path)"
            style="width: 160px"
          />
        </a-form-item>
        <a-form-item label="技术人员">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="true"
            placeholder="技术人员"
            style="width: 160px"
            v-model="queryParams.tIds"
          />
        </a-form-item>
        <a-form-item label="财务人员">
          <search-select
            style="width: 160px"
            url="/user/userForSelect"
            searchField="realName"
            :multiple="true"
            sTitle="realName"
            placeholder="财务人员"
            v-model="queryParams.fIds"
          />
        </a-form-item>
        <a-form-item label="业务员">
          <search-select
            style="width: 160px"
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            placeholder="业务员"
            v-model="queryParams.ownId"
          />
        </a-form-item>
        <a-form-item label="谈单经理">
          <search-select
            style="width: 160px"
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            placeholder="谈单经理"
            v-model="queryParams.mId"
          />
        </a-form-item>
        <span class="table-page-search-submitButtons" style="padding-top: 5px">
          <a-button type="primary" @click="refresh(true)" v-if="$auth('project:projectList:search')">查询</a-button>
        </span>
      </a-form>
      <!-- <div style="width: 100px;">
        <a-slider range :default-value="[10, 11]" :disabled="true" />
      </div> -->
      <ys-table
        @completed="()=>selectRowIds=[]"
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
        ref="yst"
        highlight-hover-row
        show-overflow
        show-header-overflow
        :loading="loading"
        queryUrl="/project/list"
        :params="getParams()"
        export-config
        :toolbar="{
          custom: true,
          zoom: true,
          refresh: true,
        }"
      >
        <template v-slot:buttons>
          <!-- <span v-if="true" style="padding-right: 10px">
            <a-button type="primary" @click="showDrawer">进度表</a-button>
          </span> -->
          <span v-if="$auth('project:projectList:add')" style="padding-right: 10px">
            <a-button type="primary" @click="$refs.projectModal.open()">添加</a-button>
          </span>
          <span v-if="$auth('project:projectList:tech')" style="padding-right: 10px">
            <a-button
              :disabled="selectRowIds.length <= 0"
              type="primary"
              @click="$refs.staffModal.showModal(selectRowIds, 1)"
            >
              分配技术人员
            </a-button>
          </span>
          <span style="padding-right: 10px" v-if="$auth('project:projectList:fina')">
            <a-button
              :disabled="selectRowIds.length <= 0"
              type="primary"
              @click="$refs.staffModal.showModal(selectRowIds, 2)"
            >
              分配财务人员
            </a-button>
          </span>
          <span style="padding-right: 10px" v-if="$auth('project:projectList:ower')">
            <a-button
              :disabled="selectRowIds.length <= 0"
              type="primary"
              @click="$refs.staffModal.showModal(selectRowIds, 3)"
            >
              分配业务人员
            </a-button>
          </span>
        </template>
        <vxe-table-column type="checkbox" width="40" />
        <vxe-table-column field="companyName" title="客户名称" remoteSort :min-width="200">
          <template v-slot="{ row }">
            <a title="点击设置项目信息" @click="edit(row)">{{ row.companyName }}</a>
          </template>
        </vxe-table-column>
        <vxe-table-column title="项目类型" field="productName" remoteSort :min-width="160" >
          <template v-slot="{row}">
            <a v-if="row.productType == 1" title="点击查看跟踪表信息" @click="showDrawer(row.id,row)" >{{ row.productName }} </a>
            <span v-else>{{ row.productName }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="年份" field="year" :width="80" remoteSort />
        <vxe-table-column title="所属部门" field="deptName" :width="120" />
        <vxe-table-column title="业务员" field="owerName" :width="110" />
        <vxe-table-column title="谈单经理" field="businessName" :width="110" />
        <vxe-table-column title="技术人员" field="techRealName" :width="110" align="center" />
        <vxe-table-column title="财务人员" field="financeRealName" :width="110" align="center" />
      </ys-table>
    </a-spin>
    <project-modal ref="projectModal" @ok="refresh(false)" />
    <set-staff-modal ref="staffModal" @ok="refresh(false)" />
    <project-service-table ref="serviceTable" ></project-service-table>
  </a-card>
</template>

<script>
import SetStaffModal from './modules/SetStaffModal'
import { STable, Ellipsis, DeptSelectOnly } from '@/components'
import { YearSelect, SearchSelect } from '@/components/Selects'
import YsTable from '@/components/Table/ystable'
import ProjectModal from './modules/ProjectModal'
import ProjectServiceTable from './ProjectServiceTable'
export default {
  name: 'Project',
  components: {
    STable,
    Ellipsis,
    YearSelect,
    ProjectModal,
    YsTable,
    DeptSelectOnly,
    SearchSelect,
    SetStaffModal,
    ProjectServiceTable
  },
  data () {
    return {
      selectRowIds: [],
      spin: false,
      queryParams: { fullPath: undefined },
      loading: false,
      drawerVisible: false
    }
  },
  created () {
    this.$nextTick(() => {
      // 手动将表格和工具栏进行关联
      // this.$refs.yst.connect(this.$refs.xToolbar)
    })
  },
  methods: {
    getParams () {
      const params = {
        companyName: this.queryParams.companyName,
        productName: this.queryParams.productName,
        year: this.queryParams.year,
        fullPath: this.queryParams.fullPath
      }
      if (this.queryParams.mId) params.mId = this.queryParams.mId.id
      if (this.queryParams.tIds) params.tIds = this.queryParams.tIds.map(a => a.id)
      if (this.queryParams.fIds) params.fIds = this.queryParams.fIds.map(a => a.id)
      if (this.queryParams.ownId) params.ownId = this.queryParams.ownId.id
      return params
    },
    refresh (refresh) {
      this.selectRowIds = []
      this.$refs.yst.refresh(refresh)
    },
    selectChange ({ records }) {
      this.selectRowIds = records.map(item => {
        return item.id
      })
    },
    edit (record) {
      this.$refs.projectModal.open(record.id)
    },
    showDrawer (projectId, row) {
      this.$refs.serviceTable.show(projectId, row)
    }
  }
}
</script>
<style lang="less" scoped>
</style>
