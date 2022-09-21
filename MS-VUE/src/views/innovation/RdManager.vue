<!--
 * @Author: zdf
 * @Date: 2021-07-23 13:46:33
 * @LastEditTime: 2022-03-30 08:38:10
 * @LastEditors: zdf
 * @Description: 立项管理
 * @FilePath: \MS-VUE\src\views\innovation\RdManager.vue
-->
<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input
          placeholder="请输入客户名称"
          v-model="queryParams.companyName"
        />
      </a-form-item>
      <a-form-item label="所属集团">
        <a-input
          placeholder="请输入集团名称"
          v-model="queryParams.groupName"
        />
      </a-form-item>
      <a-form-item label="项目年份">
        <year-select
          style="width:120px;"
          v-model="queryParams.year"
          placeholder="项目年份"
        />
      </a-form-item>
      <a-form-item label="立项名称">
        <a-input
          placeholder="请输入立项名称"
          v-model="queryParams.pname"
        />
      </a-form-item>
      <a-form-item>
        <span
          v-if="control.search"
          class="table-page-search-submitButtons"
        >
          <a-button
            type="primary"
            @click="search(true)"
          >查询</a-button>
        </span>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      highlight-hover-row
      show-overflow
      resizable
      show-footer
      :footer-method="footerMethod"
      @completed="completed"
      auto-resize
      :params="getParams()"
      queryUrl="/rsProjectMaster/getList"
      :toolbar="{custom: true,zoom:true,refresh:true}"
    >
      <template v-slot:buttons>
        <a-button v-if="control.export" type="primary" @click="exportData" :loading="btnLoading">导出</a-button>
      </template>
      <vxe-table-column type="seq" title="序号" width="50" fixed="left"/>
      <vxe-table-column
        title="客户名称"
        field="companyName"
        width="220"
        show-overflow
        fixed="left"
        remoteSort/>
      <vxe-table-column
        title="所属集团"
        field="groupName"
        width="220"
        fixed="left"
        show-overflow
        remoteSort/>
      <vxe-table-column
        title="年份"
        field="year"
        width="100"
        align="center"
        fixed="left"
        remoteSort/>
      <vxe-table-column
        title="RD"
        field="rdTitle"
        width="120"
        fixed="left"
        show-overflow
        remoteSort/>
      <vxe-table-column
        title="立项名称"
        field="pname"
        width="200"
        show-overflow
        align="center"
        remoteSort/>
      <vxe-table-column
        title="项目周期"
        field="pDate"
        width="200"
        show-overflow
        align="center">
        <template v-slot="{row}">
          {{ `${row.beginDate || '-'}~${row.endDate || '-'}` }}
        </template>
      </vxe-table-column>/>
      <vxe-table-column
        title="技术人员"
        field="realName"
        width="100"
        show-overflow
        align="center"/>
      <vxe-table-column
        title="财务人员"
        field="financeName"
        width="100"
        show-overflow
        align="center"/>
      <vxe-table-column
        title="项目成员"
        field="memberCnt"
        width="100"
        align="right"
        show-overflow
        remoteSort>
        <template v-slot="{row}">{{ row.memberCnt || '-' }}</template>
      </vxe-table-column>
      <vxe-table-column
        title="设备"
        field="equipmentCnt"
        width="100"
        align="right"
        show-overflow
        remoteSort>
        <template v-slot="{row}">{{ row.equipmentCnt || '-' }}</template>
      </vxe-table-column>
      <vxe-table-column
        title="部门"
        field="fullname"
        show-overflow
        width="100">
        <template v-slot="{row}">
          <!-- {{ row.fullname || '-' }} -->
          <!-- </template> -->
          <span v-if="row.fullname && row.fullname !== null">
            {{ row.fullname }}
          </span>
          <span v-else>
            {{ row.deptName || '' }}{{ row.deptName && row.workshop ? '/' : '' }}{{
              row.workshop || '' }}{{ (row.deptName || row.workshop) && row.productLine ? '/' : '' }}{{
              row.productLine || '' }}{{ (row.deptName || row.workshop || row.productLine) && row.processSection ? '/' : '' }}{{
              row.processSection || '' }}
          </span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="相关专利"
        field="patentCnt"
        width="100"
        align="right"
        show-overflow
        remoteSort>
        <template v-slot="{row}">{{ row.patentCnt || '-' }}</template>
      </vxe-table-column>
      <vxe-table-column
        title="立项报告"
        field="hasReport"
        show-overflow
        width="100">
        <template v-slot="{row}">
          {{ getSingleContent(row,'hasReport') }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="查新"
        field="hasNewReport"
        show-overflow
        width="100">
        <template v-slot="{row}">
          {{ getSingleContent(row,'hasNewReport') }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="docTotal"
        width="120"
        align="right"
        show-overflow
        remoteSort>
        <template #header>
          <span title="已编辑数/已提交数/已完成数">过程文档数</span>
        </template>
        <template #footer="{ items,columnIndex }">
          <span title="总编辑数/总提交数/总完成数">{{ items[columnIndex] }}</span>
        </template>
        <template v-slot="{row}">
          <div :title="`已编辑数：${row.docTotal || '-'} 已提交数：${row.docSubmitCnt || '-'}已完成数：${row.docDoneCnt || '-'}`">
            {{ `${row.docTotal || '-'}/${row.docSubmitCnt || '-'}/ ${row.docDoneCnt || '-'}` }}
          </div>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="备查资料"
        field="backupDataTotalCnt"
        width="110"
        show-overflow
        align="right">
        <template v-slot="{row}">{{ row.backupDataTotalCnt || '-' }}</template>
      </vxe-table-column>
      <vxe-table-column
        title="操作"
        width="120"
        fixed="right"
        v-if="control.relateMaster"
        align="center"
      >
        <template v-slot="{row}">
          <a @click="$refs.relateMaster.open(row,1)">分配负责人</a>
        </template>
      </vxe-table-column>
    </ystable>
    <relate-master-modal ref="relateMaster" @ok="search"/>
  </a-card>
</template>

<script>

import { SearchSelect, YearSelect } from '@/components/Selects'
import ystable from '@/components/Table/ystable'
import { getStatusName } from '@/utils/processDoc/auditStatus'
import RelateMasterModal from './modules/RelateMasterModal'
export default {
  components: {
    ystable,
    SearchSelect,
    YearSelect,
    RelateMasterModal
  },
  data () {
    return {
      queryParams: {},
      control: {
        search: this.$auth('innovation:rdManager:search'),
        relateMaster: this.$auth('innovation:rdManager:relateMaster'),
        export: this.$auth('innovation:rdManager:export')
      },
      footer: {},
      btnLoading: false
    }
  },
  methods: {
    footerMethod ({ columns, data }) {
      return [
        columns.map((a, index) => {
          if (index === 0) {
            return ''
          }
          if (index === 1) {
            return '合计'
          }
          if (index === 15) {
            return `${this.footer.docTotal || '-'}/${this.footer.docSubmitCnt || '-'}/ ${this.footer.docDoneCnt || '-'}`
          }
          return this.footer[a.property] || '-'
        })
      ]
    },
    completed (data) {
      if (data.data.footer) {
        this.footer = data.data.footer
      } else {
        this.footer = {}
      }
    },
    getParams () {
      const params = { companyName: this.queryParams.companyName, year: this.queryParams.year, pname: this.queryParams.pname, groupName: this.queryParams.groupName }
      if (this.queryParams.tIds) { params.tIds = this.queryParams.tIds.map(a => a.id) }
      return params
    },
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    getStatusName,
    getSingleContent (row, key) {
      if (row[key]) {
        let status = 'reportStatus'
        if (key === 'hasNewReport') {
          status = 'newReportStatus'
        }
        return this.getStatusName(row[status])
      }
      return '无'
    },
    exportData () {
      this.btnLoading = true
      this.$exportData('/rsProjectMaster/export', this.getParams(),
        `立项管理表.xlsx`, this.$message).then(res => {
        this.btnLoading = false
      })
    }
  }
}
</script>

<style>

</style>
