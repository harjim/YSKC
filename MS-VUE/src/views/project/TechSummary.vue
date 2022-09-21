<template>
  <a-card :bordered="false">
    <a-spin
      tip="请稍后..."
      :spinning="spin"
    >
      <a-form layout="inline">
        <a-form-item label="客户名称">
          <a-input
            style="width: 160px"
            placeholder="客户名称"
            v-model="queryParams.companyName"
          />
        </a-form-item>
        <a-form-item label="年份">
          <year-select
            style="width: 160px"
            v-model="queryParams.year"
            placeholder="项目年份"
          />
        </a-form-item>
        <a-form-item label="所属部门">
          <dept-select-only
            ref="deptSelect"
            @deptFullPath="path=>queryParams.fullPath = path"
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
        <a-form-item>
          <span
            class="table-page-search-submitButtons"
            style="padding-top: 5px"
          >
            <a-button
              type="primary"
              @click="refresh(true)"
              v-if="$auth('tech:summary:search')"
            >查询</a-button>
          </span>
        </a-form-item>
      </a-form>
      <ystable
        ref="table"
        highlight-hover-row
        show-overflow
        show-header-overflow
        :loading="false"
        queryUrl="/techSummary/getList"
        :params="getParams()"
        @completed="({ data: { header } }) => (headerData = header)"
        export-config
        :toolbar="{
          custom: true,
          zoom: true,
          refresh: true,
        }"
      >
        <template v-slot:buttons>
          <div style="padding: 8px 0">
            <b>
              阶段数:
              <a>{{ headerData.stageTotal ? headerData.stageTotal : '--' }}</a>
            </b>
            <b class="pleft">
              应上传数:
              <a>{{ headerData.uploadTotal ? headerData.uploadTotal : '--' }}</a>
            </b>
            <b class="pleft">
              已上传数:
              <a>{{ headerData.uploadedTotal ? headerData.uploadedTotal : '--' }}</a>
            </b>
          </div>
        </template>
        <vxe-table-column
          type="seq"
          width="50"
          title="序号"
        />
        <vxe-table-column
          field="companyName"
          title="客户名称"
          remoteSort
          :min-width="200"
        />
        <vxe-table-column
          title="年份"
          field="year"
          :width="100"
          remoteSort
          align="center"
        />
        <vxe-table-column
          title="所属部门"
          field="fullname"
          :width="160"
        />
        <vxe-table-column
          title="技术人员"
          field="techRealName"
          :width="140"
        />
        <vxe-table-column
          title="阶段数"
          field="stageCount"
          remoteSort
          align="right"
          :width="120"
        >
          <template v-slot="{row}">
            <span v-if="row.stageCount">
              <a @click="$refs.stageFiles.show(row.projectId,row.companyName,row.year)">{{ row.stageCount }}</a>
            </span>
            <span v-else>-</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="应上传数"
          field="uploadCount"
          remoteSort
          align="right"
          :width="120"
        >
          <template v-slot="{row}">
            <span v-if="row.uploadCount">
              <a @click="$refs.stageFiles.show(row.projectId,row.companyName,row.year)">{{ row.uploadCount }}</a>
            </span>
            <span v-else>-</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="已上传数"
          field="uploadedCount"
          remoteSort
          align="right"
          :width="120"
        >
          <template v-slot="{row}">
            <span v-if="row.uploadedCount">
              <a @click="$refs.stageFiles.show(row.projectId,row.companyName,row.year)">{{ row.uploadedCount }}</a>
            </span>
            <span v-else>-</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="操作人"
          field="operationName"
          remoteSort
          :width="140"
        />
        <vxe-table-column
          title="操作时间"
          field="operationTime"
          remoteSort
          :width="160"
          align="center"
        />
      </ystable>
    </a-spin>
    <view-stage-files ref="stageFiles" />
  </a-card>
</template>

<script>
import ViewStageFiles from './modules/ViewStageFiles'
import { SearchSelect, YearSelect } from '@/components/Selects'
import DeptSelectOnly from '@/components/DeptSelectOnly'
import ystable from '@/components/Table/ystable'
export default {
  components: {
    SearchSelect,
    YearSelect,
    DeptSelectOnly,
    ystable,
    ViewStageFiles
  },
  data () {
    return {
      spin: false,
      queryParams: { fullPath: undefined },
      headerData: {}
    }
  },
  methods: {
    refresh (refresh) {
      this.$refs.table.refresh(refresh)
    },
    getParams () {
      const params = {
        companyName: this.queryParams.companyName,
        year: this.queryParams.year,
        fullPath: this.queryParams.fullPath
      }
      if (this.queryParams.tIds) params.tIds = this.queryParams.tIds.map(a => a.id)
      return params
    }
  }
}
</script>

<style>
</style>
