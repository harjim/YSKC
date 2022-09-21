<template>
  <a-card :bordered="false">
    <a-spin :spinning="spinning" tip="请稍后...">
      <TableSearchbar :colNum="4" @search="search(true)" foldable>
        <a-form-item label="申请名称">
          <a-input v-model="queryParams.patentName" placeholder="请输入申请名称" allowClear />
        </a-form-item>
        <a-form-item label="客户名称">
          <a-input v-model="queryParams.companyName" placeholder="请输入客户名称" allowClear />
        </a-form-item>
        <a-form-item label="所属集团">
          <a-input
            placeholder="请输入所属集团"
            v-model="queryParams.groupName"
          />
        </a-form-item>
        <a-form-item label="年份">
          <year-select v-model="queryParams.year" placeholder="请选择年份" />
        </a-form-item>
        <a-form-item label="所属部门">
          <dept-select v-model="queryParams.deptId" />
        </a-form-item>
        <a-form-item label="业务员">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="false"
            placeholder="请输入业务员"
            v-model="queryParams.ownerId"
          />
        </a-form-item>
        <a-form-item label="工程师">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="false"
            placeholder="请输入工程师"
            v-model="queryParams.engineerId"
          />
        </a-form-item>
        <a-form-item label="节点">
          <a-select v-model="queryParams.nodeNumber" placeholder="请选择节点" allowClear>
            <a-select-option v-for="item in patentPlanProcessType" :key="item.value">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="状态">
          <a-select v-model="queryParams.status" placeholder="请选择状态" allowClear>
            <a-select-option v-for="item in patentPlanStatus" :key="item.value">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="代理人">
          <a-select v-model="queryParams.masterId" placeholder="请选择代理人" allowClear>
            <a-select-option v-for="item in selectList" :key="item.id">{{ item.title }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="专利号">
          <a-input v-model="queryParams.patentNo" placeholder="请输入专利号" allowClear />
        </a-form-item>
        <a-form-item label="当前处理人">
          <a-input v-model="queryParams.auditUsers" placeholder="请输入当前处理人" allowClear />
        </a-form-item>
        <a-form-item label="专利名称">
          <a-input v-model="queryParams.confirmName" placeholder="请输入专利名称" allowClear />
        </a-form-item>
      </TableSearchbar>
      <ystable
        ref="table"
        queryUrl="/patentPlanNew/getList"
        :params="getParams()"
        header-align="center"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        :toolbar="{ custom: true, zoom:true, refresh:true }"
        :checkbox-config="{checkMethod: checCheckboxkMethod,showHeader: showHeaderChk}"
        @checkbox-all="selectChange"
        @checkbox-change="selectChange"
        @completed="({data:{data, footer}})=>completed(data, footer)"
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
            :disabled="deleteBtnDisabled">
            删除
          </a-button>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="control.setMaster"
            @click="$refs.setMaster.showModal(selectedRowKeys)"
            :disabled="setMasterBtnDisabled">
            分配代理人
          </a-button>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            v-if="control.cancelApplication"
            @click="$refs.cancelApplication.open(selectedRowKeys)"
            :disabled="setCancelApplication">
            取消申请
          </a-button>
          <span class="totalNumber">
            <span><b>需求数：<a>{{ countNums.demand | NumberFormat }}</a></b></span>
            <span><b>技术交底数：<a>{{ countNums.technical | NumberFormat }}</a></b></span>
            <span><b>定稿数：<a>{{ countNums.finalized | NumberFormat }}</a></b></span>
            <span><b>受理数：<a>{{ countNums.accepled | NumberFormat }}</a></b></span>
            <span><b>授权数：<a>{{ countNums.authorize | NumberFormat }}</a></b></span>
          </span>
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
            <a v-if="control.patent" @click="$refs.planDrawer.view(row)">{{ row.patentName }}</a>
            <span v-else>{{ row.patentName }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="demandNo"
          title="需求编号"
          width="150"
          align="left"
          header-align="center"
          remoteSort />
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
          width="150"
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
          <template #default="{row}">{{ row.patentType | getLabel(patentTypes) }}</template>
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
          <template #default="{row}">{{ row.planType | getLabel(planTypes) }}</template>
        </vxe-table-column>
        <vxe-table-column
          field="nodeNumber"
          title="节点"
          width="110"
          align="center"
          header-align="center"
          remoteSort>
          <template #default="{row}">
            {{ (row.nodeNumber || 0) | getLabel(patentPlanProcessType) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="status"
          title="状态"
          width="100"
          header-align="center"
          remoteSort>
          <template #default="{row}">
            {{ getStatusName(row.status) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="auditUsers"
          title="当前处理人"
          width="110"
          header-align="center">
        </vxe-table-column>
        <vxe-table-column
          field="masterName"
          title="代理人"
          width="110"
          align="left"
          header-align="left">
          <template v-slot="{row}">
            {{ row.masterName || '-' }}
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
          title="预计提交国知局日期"
          width="170"
          align="center"
          header-align="center"
          remoteSort />
        <vxe-table-column
          field="submittedDate"
          title="实际提交国知局日期"
          width="170"
          align="center"
          header-align="center"
          remoteSort>
          <template #default="{row}">
            {{ row.submittedDate && row.submittedDate.slice(0, 10) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="acceptNoticeDate"
          title="受理通知日期"
          width="140"
          align="center"
          header-align="center"
          remoteSort>
          <template #default="{row}">
            {{ row.acceptNoticeDate && row.acceptNoticeDate.slice(0, 10) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="acceptFeeDate"
          title="受理缴费日期"
          width="140"
          align="center"
          header-align="center"
          remoteSort>
          <template #default="{row}">
            {{ row.acceptFeeDate && row.acceptFeeDate.slice(0, 10) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="patentNo"
          title="专利号"
          width="130"
          align="left"
          header-align="center"
          remoteSort />
        <vxe-table-column
          field="confirmName"
          title="专利名称"
          width="150"
          align="left"
          header-align="center"
          remoteSort />
        <vxe-table-column
          field="authDate"
          title="授权日期"
          width="130"
          align="left"
          header-align="center"
          remoteSort>
          <template #default="{row}">
            {{ row.authDate && row.authDate.slice(0, 10) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="authFeeDate"
          title="授权缴费日期"
          width="130"
          align="left"
          header-align="center"
          remoteSort>
          <template #default="{row}">
            {{ row.authFeeDate && row.authFeeDate.slice(0, 10) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="issueDate"
          title="下证日期"
          width="130"
          align="left"
          header-align="center"
          remoteSort>
          <template #default="{row}">
            {{ row.issueDate && row.issueDate.slice(0, 10) }}
          </template>
        </vxe-table-column>
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
        @update="search" />
      <set-master-modal ref="setMaster" @ok="search(false)" />
      <cancel-application ref="cancelApplication" @ok="search(false)" />
    </a-spin>
  </a-card>
</template>

<script>
import PatentPlanDrawer from './modules/PatentPlanDrawerNew.vue'
import CancelApplication from './modules/CancelApplicationModal'
import { DeptSelect, SearchSelect, YearSelect } from '@/components/Selects'
import ystable from '@/components/Table/ystable'
import { getStatusName, isEditStatus } from '@/utils/processDoc/auditStatus'
import SetMasterModal from './modules/SetMasterModalNew'
import TableSearchbar from '@/components/TableSearchbar'
import { mapState } from 'vuex'

export default {
  components: {
    YearSelect,
    ystable,
    PatentPlanDrawer,
    SearchSelect,
    SetMasterModal,
    TableSearchbar,
    DeptSelect,
    CancelApplication
  },
  data () {
    return {
      queryParams: {},
      selectedRowKeys: [],
      showHeaderChk: true,
      spinning: false,
      selectList: [],
      countNums: {},
      control: {
        search: this.$auth('patent:patentPlanNew:search'),
        add: this.$auth('patent:patentPlanNew:add'),
        edit: this.$auth('patent:patentPlanNew:edit'),
        delete: this.$auth('patent:patentPlanNew:delete'),
        setMaster: this.$auth('patent:patentPlanNew:setMaster'),
        cancelApplication: this.$auth('patent:patentPlanNew:cancel'),
        patent: this.$auth('patent:patentPlanNew:patent')
      }
    }
  },
  computed: {
    ...mapState({
      patentPlanProcessType: state => state.enums.patentPlanProcessType,
      patentPlanStatus: state => state.enums.patentPlanStatus,
      planTypes: state => state.enums.patentPlanType,
      patentTypes: state => state.enums.patentType
    }),
    deleteBtnDisabled () {
      return this.selectedRowKeys.length === 0 || this.selectedRowKeys.some(row => {
        return !this.isEditStatus(row.status)
      })
    },
    setMasterBtnDisabled () {
      return this.selectedRowKeys.length === 0 || this.selectedRowKeys.some(row => {
        return (!((row.nodeNumber || 0) > 1)) || this.isEditStatus(row.status)
      })
    },
    setCancelApplication () {
      // 选择列且列中存在状态为进行中(status = 0)
      return this.selectedRowKeys.length === 0 || this.selectedRowKeys.some(row => {
        return row.status
      })
    }
  },
  created () {
    this.getSelectList()
  },
  methods: {
    isEditStatus,
    getStatusName,
    getParams () {
      const params = Object.assign({}, this.queryParams)
      params.ownerId = params.ownerId ? params.ownerId.id : undefined
      params.engineerId = params.engineerId ? params.engineerId.id : undefined
      return params
    },
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    completed (data, footer) {
      this.selectedRowKeys = []
      if (data && data.length) {
        this.countNums = footer
        this.showHeaderChk = data.some(item => {
          return this.isEditStatus(item.status) || ((item.nodeNumber || 0) > 1)
        })
      } else {
        this.showHeaderChk = false
      }
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(v => ({
        id: v.id,
        oldUserId: v.userId,
        nodeNumber: v.nodeNumber,
        instanceId: v.instanceId,
        status: v.status
      }))
    },
    batchDelete () {
      const self = this
      self.spinning = true
      this.$confirm({
        title: '您确定要删除选中的专利申请吗?',
        onOk () {
          return self.$http.post('/patentPlanNew/del', { ids: self.selectedRowKeys.map(v => v.id) })
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
    },
    getSelectList () {
      this.$http.get('patentMaster/getSelect').then(res => {
        if (res.success && res.data) {
          this.selectList = res.data
        } else {
          this.$message.error('获取数据失败')
        }
      })
    },
    checCheckboxkMethod ({ row }) {
      // return this.isEditStatus(row.status) || ((row.nodeNumber || 0) > 1)
      return true
    }
  }
}
</script>

<style lang="less" scoped>
/deep/ .ant-col-8 {
  width: 23%;
}
.totalNumber {
  vertical-align: text-bottom;
}
.totalNumber > span {
  display: inline-block;
  padding: 0 15px 0 10px;
}
</style>
