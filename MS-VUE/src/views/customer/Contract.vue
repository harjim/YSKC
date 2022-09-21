<template>
  <a-card :bordered="false">
    <template v-if="$auth('customer:contract:search')">
      <a-form-model layout="inline" ref="form" :model="form">
        <a-form-model-item label="客户名称">
          <a-input v-model="form.companyName" placeholder="请输入客户名称" style="width: 240px" />
        </a-form-model-item>
        <a-form-model-item label="流程状态">
          <a-select v-model="form.status" placeholder="请选择流程状态" style="width: 240px" allowClear>
            <a-select-option v-for="(v, k) in statusMap" :key="k" :value="k">{{ v }}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="节点">
          <a-select v-model="form.nodeNumber" placeholder="请选择节点" style="width: 240px" allowClear>
            <a-select-option v-for="(item, idx) in contractProcessType" v-if="idx !== 0" :key="item.value">{{
              item.label
            }}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="所属部门">
          <DeptOnlySelect style="width: 240px" v-model="form.deptId" placeholder="请选择所属部门" />
        </a-form-model-item>
        <a-form-model-item label="业务员">
          <search-select
            style="width: 240px"
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="false"
            placeholder="请输入业务员"
            v-model="form.ownerId"
          />
        </a-form-model-item>
        <a-form-model-item>
          <a-button type="primary" @click="refresh">查询</a-button>
        </a-form-model-item>
      </a-form-model>

      <ystable
        ref="xTable"
        query-url="contract/getList"
        :params="getParams()"
        :seq-config="{ startIndex: 1 }"
        :toolbar="{
          refresh: true,
          zoom: true,
          custom: true
        }"
        show-overflow="tooltip"
        :columns="columns"
        remoteSort
        @checkbox-all="selectAllEvent"
        @checkbox-change="selectChangeEvent"
      >
        <template v-slot:buttons>
          <template v-if="$auth('customer:contract:add')">
            <a-button style="margin-right: 12px" type="primary" @click="$refs.ContractDrawer.add()">添加</a-button>
          </template>
          <template v-if="$auth('customer:contract:del')">
            <a-button
              style="margin-right: 12px"
              type="primary"
              :disabled="selectedRowKeys.length === 0"
              @click="delContract"
              >删除
            </a-button>
          </template>
        </template>
        <template #contractNo="{ row }">
          <a
            v-if="$auth('customer:contract:edit') || $auth('customer:contract:check')"
            @click="$refs.ContractDrawer.edit(row.id)"
            >{{ row.contractNo }}</a
          >
          <span v-else>{{ row.contractNo }}</span>
        </template>
        <template #status="{ row }">
          <a-badge :color="statusColor[row.status === undefined ? 5 : row.status]" :text="getStatusName(row.status)" />
        </template>
        <template #projectCnt="{ row }">
          <a-popover :autoAdjustOverflow="false" placement="bottom" trigger="click">
            <a>{{ row.projectCnt }}</a>
            <template #content>
              <div class="my-dropdown4">
                <vxe-grid
                  border
                  resizable
                  highlight-hover-row
                  auto-resize
                  max-height="244"
                  size="mini"
                  :data="row.projectList"
                  :columns="projectListCol"
                  show-overflow="title"
                >
                  <template #years="{ row }">
                    <span>{{ row.beginYear }}-{{ row.endYear }}</span>
                  </template>
                </vxe-grid>
              </div>
            </template>
          </a-popover>
        </template>
        <template #partnerCnt="{ row }">
          <span v-if="!row.partnerCnt">-</span>
          <a-popover v-else :autoAdjustOverflow="false" placement="bottom" trigger="click">
            <a>{{ row.partnerCnt }}</a>
            <template #content>
              <div class="my-dropdown4">
                <vxe-grid
                  border
                  resizable
                  highlight-hover-row
                  auto-resize
                  max-height="244"
                  size="mini"
                  :data="row.memberList"
                  :columns="memberListCol"
                  show-overflow="title"
                >
                </vxe-grid>
              </div>
            </template>
          </a-popover>
        </template>
      </ystable>
      <preview-modal ref="previewModal" />
      <contract-drawer ref="ContractDrawer" :preview="preview" @refresh="refresh" />
    </template>
    <a-empty v-else :description="false" />
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import SearchSelect from '@/components/Selects/SearchSelect'
import { getStatusName, statusColor } from '@/utils/processDoc/auditStatus'
import ContractDrawer from '@/views/customer/modules/ContractDrawer.vue'
import PreviewModal from '@/components/PreviewModal'
import { mapState } from 'vuex'
import DeptOnlySelect from '@/components/Selects/DeptOnlySelect.vue'

export default {
  name: 'Contract',
  components: { PreviewModal, ContractDrawer, SearchSelect, ystable, DeptOnlySelect },
  mounted() {
    this.$http.get('product/productForSelect').then(({ success, data, errorMessage }) => {
      if (success) {
        this.$store.commit('contract/CHANGE_PRODUCT', data)
      } else {
        this.$message.error(errorMessage)
      }
    })
  },
  computed: {
    ...mapState({
      contractProcessType: (state) => state.contract.contractProcessType,
      sealType: (state) => state.contract.sealType
    })
  },
  data() {
    const statusMap = {
      0: '进行中',
      1: '通过',
      2: '驳回',
      4: '提交',
      5: '未提交'
    }
    const columns = [
      { type: 'checkbox', width: '60', align: 'center', fixed: 'left' },
      {
        title: '合同编号',
        field: 'contractNo',
        width: '150',
        fixed: 'left',
        sortable: true,
        slots: { default: 'contractNo' }
      },
      { title: '客户名称', field: 'companyName', width: '160', fixed: 'left', sortable: true },
      { title: '所属部门', field: 'deptName', width: '90' },
      { title: '业务员', field: 'ownerName', width: '80' },
      { title: '项目数', field: 'projectCnt', width: '80', align: 'right', slots: { default: 'projectCnt' } },
      { title: '合作人员', field: 'partnerCnt', width: '80', align: 'right', slots: { default: 'partnerCnt' } },
      { title: '技术负责人', field: 'techName', width: '100' },
      { title: '财务负责人', field: 'finaName', width: '100' },
      { title: '节点', field: 'nodeName', width: '100' },
      { title: '流程状态', field: 'status', width: '100', sortable: true, slots: { default: 'status' } },
      { title: '当前处理人', field: 'auditUsers', width: '120', sortable: true },
      { title: '签署日期', field: 'signDate', width: '110', align: 'center' },
      { title: '生效日期', field: 'effectDate', width: '110', align: 'center' },
      { title: '终止日期', field: 'closeDate', width: '110', align: 'center' },
      { title: '创建时间', field: 'createTime', width: '140', align: 'center' }
    ]
    const projectListCol = [
      { type: 'seq', width: '60', align: 'center' },
      { title: '年份', width: '80', align: 'center', slots: { default: 'years' } },
      { title: '项目类型', field: 'productName', width: '120' },
      {
        title: '收费比例',
        field: 'ratio',
        width: '80',
        align: 'right',
        formatter: ({ cellValue }) => `${cellValue}%`
      },
      { title: '签约次数', field: 'signCnt', width: '80', align: 'right' },
      { title: '备注', field: 'remark', width: '160' }
    ]
    const memberListCol = [
      { type: 'seq', width: '60', align: 'center' },
      { title: '姓名', field: 'userName', width: '120' },
      { title: '所属部门', field: 'deptName', width: '80' },
      {
        title: '贡献比',
        field: 'ratio',
        width: '80',
        align: 'right',
        formatter: ({ cellValue }) => `${cellValue}%`
      },
      { title: '备注', field: 'remark', width: '160' }
    ]
    return {
      statusMap,
      statusColor,
      form: {},
      columns,
      projectListCol,
      memberListCol,
      selectedRowKeys: [],
      selectUser: []
    }
  },
  methods: {
    getStatusName,
    refresh(flag = true) {
      this.$refs.xTable.refresh(flag)
    },
    preview(filePath, filename) {
      if (filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(filePath, filename)
    },
    selectChangeEvent({ checked, records }) {
      this.selectedRowKeys = records.map((item) => {
        return item.id
      })
      this.selectUser = records
    },
    selectAllEvent({ checked, records }) {
      this.selectedRowKeys = records.map((item) => {
        return item.id
      })
      this.selectUser = records
    },
    delContract() {
      this.$http.post('/contract/delContract', { ids: this.selectedRowKeys }).then(({ success, errorMessage }) => {
        if (success) {
          this.$message.success('删除成功')
          this.refresh()
        } else {
          this.$message.error(errorMessage)
        }
      })
    },
    getParams() {
      const params = Object.assign({}, this.form)
      params.ownerId = params.ownerId ? params.ownerId.id : undefined
      return params
    }
  }
}
</script>

<style scoped></style>
