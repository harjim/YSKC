<!--
 * @Author: zdf
 * @Date: 2021-08-19 13:34:22
 * @LastEditTime: 2022-03-09 09:15:07
 * @LastEditors: lzh
 * @Description: 专利申请
 * @FilePath: \MS-VUE\src\views\patent\PatentPlan.vue
-->
<template>
  <a-card :bordered="false">
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form layout="inline">
        <a-form-item label="申请名称">
          <a-input v-model="queryParams.patentName" placeholder="请输入申请名称" />
        </a-form-item>
        <a-form-item label="客户名称">
          <a-input v-model="queryParams.companyName" placeholder="请输入客户名称" />
        </a-form-item>
        <a-form-item label="所属集团">
          <a-input
            placeholder="请输入所属集团"
            v-model="queryParams.groupName"
          />
        </a-form-item>
        <a-form-item label="年份">
          <year-select v-model="queryParams.year" style="width:165px;" placeholder="请选择年份" />
        </a-form-item>
        <a-form-item label="专利号">
          <a-input v-model="queryParams.patentNo" placeholder="请输入专利号" />
        </a-form-item>
        <a-form-item label="业务员">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="false"
            placeholder="请输入业务员"
            style="width:165px;"
            v-model="queryParams.ownerId"
          />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="search(true)">查询</a-button>
        </a-form-item>
      </a-form>
      <ystable
        ref="table"
        queryUrl="/patentPlan/getList"
        :params="getParams()"
        header-align="center"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        :toolbar="{ custom: true, zoom:true, refresh:true }"
        :checkbox-config="{ checkMethod: checCheckboxkMethod,showHeader: showHeaderChk}"
        @checkbox-all="selectChange"
        @checkbox-change="selectChange"
        @completed="({data:{data}})=>completed(data)"
      >
        <template #buttons>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="control.add"
            @click="add">
            添加
          </a-button>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="control.delete"
            @click="batchDelete"
            :disabled="selectedRowKeys.length <= 0">
            删除
          </a-button>
          <!-- <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="control.engineer"
            @click="$refs.setEngineer.showModal(selectedRowKeys)"
            :disabled="selectedRowKeys.length <= 0">
            分配工程师
          </a-button> -->
        </template>
        <vxe-table-column
          type="checkbox"
          width="40"
          align="center"
          header-align="center"
          fixed="left"></vxe-table-column>
        <vxe-table-column
          type="seq"
          title="序号"
          width="60"
          align="center"
          header-align="center"
          fixed="left"></vxe-table-column>
        <vxe-table-column
          field="patentName"
          title="申请名称"
          width="180"
          align="left"
          header-align="center"
          fixed="left"
          remoteSort>
          <template #default="{row}">
            <a @click="$refs.planDrawer.view(row)">{{ row.patentName }}</a>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="companyName"
          title="客户名称"
          width="180"
          align="left"
          header-align="center"
          remoteSort />
        <vxe-table-column
          field="groupName"
          title="所属集团"
          width="180"
          align="left"
          header-align="center"
          remoteSort>
          <template v-slot="{row}">
            {{ row.groupName || '--' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="year"
          title="年份"
          width="90"
          align="center"
          header-align="center"
          remoteSort />
        <vxe-table-column
          field="deptName"
          title="所属部门"
          width="110"
          align="left"
          header-align="center"
          remoteSort />
        <vxe-table-column
          field="patentType"
          title="专利类型"
          width="110"
          align="center"
          header-align="center"
          remoteSort>
          <template #default="{row}">{{ patentTypes[row.patentType] }}</template>
        </vxe-table-column>
        <vxe-table-column
          field="ownerName"
          title="业务员"
          width="120"
          align="left"
          header-align="center" />
        <vxe-table-column
          field="engineer"
          title="工程师"
          width="120"
          align="left"
          header-align="center" />
        <vxe-table-column
          field="planType"
          title="版本"
          width="80"
          align="center"
          header-align="center"
          remoteSort>
          <template #default="{row}">{{ planTypes[row.planType] }}</template>
        </vxe-table-column>
        <vxe-table-column
          field="process"
          title="进度"
          width="90"
          align="center"
          header-align="center">
          <template #default="{row}">
            <a-tag :color="processes[row.process] ? processes[row.process].color : '#606266'">
              {{ `${processes[row.process] ? processes[row.process].label : ''}` }}
            </a-tag>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="process"
          title="审批状态"
          width="90"
          header-align="center">
          <template #default="{row}">
            {{ getStatusName(row.status) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="auditUsers"
          title="当前审核人"
          width="110"
          header-align="center">
        </vxe-table-column>
        <vxe-table-column
          field="masterName"
          title="代理人"
          width="180"
          align="left"
          header-align="left">
          <template v-slot="{row}">
            <template v-if="control.master">
              <a @click="$refs.setMaster.showModal(row.id,row.masterId)">{{ row.masterName || '-' }}</a>
            </template>
            <template v-else>
              {{ row.masterName || '-' }}
            </template>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="firstInventor"
          title="第一发明人"
          width="180"
          align="left"
          header-align="center" />
        <vxe-table-column
          field="rdTitle"
          title="RD"
          width="110"
          align="center"
          header-align="center">
          <template #default="{row}">
            {{ row.rdTitle || '其他' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="demandDate"
          title="初稿需求日期"
          width="130"
          align="center"
          header-align="center"
          remoteSort />
        <vxe-table-column
          field="submitDate"
          title="提交国知局日期"
          width="140"
          align="center"
          header-align="center"
          remoteSort />
        <vxe-table-column
          field="patentNo"
          title="专利号"
          width="130"
          align="left"
          header-align="center" />
        <vxe-table-column
          field="remark"
          title="备注"
          width="200"
          align="left"
          header-align="left" />
        <vxe-table-column
          field="createTime"
          title="创建时间"
          width="150"
          align="center"
          header-align="center"
          remoteSort />
      </ystable>
      <patent-plan-drawer
        ref="planDrawer"
        @ok="search"
        :control="control"
        :planTypes="planTypes"
        :processes="processes"
        :patentTypes="patentTypes" />
      <set-engineer-modal url="/patentPlan/setEngineer" ref="setEngineer" @ok="search(false)" />
      <set-master-modal ref="setMaster" @ok="search(false)" />
    </a-spin>
  </a-card>
</template>

<script>
import PatentPlanDrawer from './modules/PatentPlanDrawer.vue'
import { SearchSelect, YearSelect } from '@/components/Selects'
import ystable from '@/components/Table/ystable'
import { getStatusName, isEditStatus } from '@/utils/processDoc/auditStatus'
import SetEngineerModal from './modules/SetEngineerModal'
import SetMasterModal from './modules/SetMasterModal'

export default {
  components: {
    YearSelect,
    ystable,
    PatentPlanDrawer,
    SearchSelect,
    SetEngineerModal,
    SetMasterModal
  },
  data () {
    return {
      queryParams: {},
      patentTypes: { '1': '发明专利', '2': '实用新型', '3': '外观设计' },
      planTypes: ['高新版', '保护版'],
      processes: [{ label: '审批', color: 'pink' },
        { label: '初稿', color: 'orange' },
        { label: '定稿', color: 'cyan' },
        { label: '受理', color: 'blue' },
        { label: '授权', color: 'green' }],
      selectedRowKeys: [],
      showHeaderChk: true,
      spinning: false,
      control: {
        search: this.$auth('patent:patentPlan:search'),
        add: this.$auth('patent:patentPlan:add'),
        edit: this.$auth('patent:patentPlan:edit'),
        delete: this.$auth('patent:patentPlan:delete'),
        view: this.$auth('patent:patentPlan:view'),
        audit: this.$auth('patent:patentPlan:audit'),
        process: this.$auth('patent:patentPlan:process'),
        preview: this.$auth('patent:patentPlan:preview'),
        download: this.$auth('patent:patentPlan:download'),
        deleteFile: this.$auth('patent:patentPlan:deleteFile'),
        upload: this.$auth('patent:patentPlan:upload'),
        submit: this.$auth('patent:patentPlan:submit'),
        engineer: this.$auth('patent:patentPlan:engineer'),
        master: this.$auth('patent:patentPlan:master')
      }
    }
  },
  methods: {
    isEditStatus,
    getStatusName,
    getParams () {
      const params = Object.assign({}, this.queryParams)
      params.ownerId = params.ownerId ? params.ownerId.id : undefined
      return params
    },
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    completed (data) {
      this.selectedRowKeys = []
      if (data && data.length) {
        this.showHeaderChk = data.some(item => {
          return this.isEditStatus(item.status)
        })
      } else {
        this.showHeaderChk = false
      }
    },
    checCheckboxkMethod ({ row }) {
      return this.isEditStatus(row.status)
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(a => a.id)
    },
    batchDelete () {
      const self = this
      self.spinning = true
      this.$confirm({
        title: '您确定要删除选中的专利申请吗?',
        onOk () {
          return self.$http.post('/patentPlan/del', { ids: self.selectedRowKeys })
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.search(false)
              } else {
                self.$message.error(res.errorMessage || '删除失败')
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    add () {
      this.$refs.planDrawer.add()
    }
  }
}
</script>
