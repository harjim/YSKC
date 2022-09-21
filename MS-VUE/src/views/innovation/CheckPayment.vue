<template>
  <a-card :bordered="false">
    <template v-if="$auth('innovation:checkPayment:search')">
      <a-form-model layout="inline" ref="form" :module="form">
        <a-form-model-item label="客户名称">
          <a-input v-model="form.companyName" placeholder="请输入客户名称" style="width: 240px" />
        </a-form-model-item>
        <a-form-model-item label="年份">
          <year-select style="width: 240px" v-model="form.year" placeholder="请选择年份" />
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
        <a-form-model-item label="当前处理人">
          <a-input v-model="form.auditUsers" placeholder="请输入当前处理人" style="width: 240px" />
        </a-form-model-item>
        <a-form-model-item label="节点">
          <a-select v-model="form.nodeNumber" placeholder="请选择节点" style="width: 240px" allowClear>
            <a-select-option v-for="(item, idx) in processType" v-if="idx !== 0" :key="item.value">{{
              item.label
            }}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="流程状态">
          <a-select v-model="form.status" placeholder="请选择流程状态" style="width: 240px" allowClear>
            <a-select-option v-for="(v, k) in statusMap" :key="k" :value="k">{{ v }}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="技术人员">
          <search-select
            style="width: 240px"
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="false"
            placeholder="请输入技术人员"
            v-model="form.techId"
          />
        </a-form-model-item>
        <a-form-model-item label="财务经理">
          <search-select
            style="width: 240px"
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="false"
            placeholder="请输入财务经理"
            v-model="form.finaManagerId"
          />
        </a-form-model-item>
        <a-form-model-item>
          <a-button type="primary" @click="refresh">查询</a-button>
        </a-form-model-item>
      </a-form-model>

      <ystable
        ref="xTable"
        query-url="checkPayment/getList"
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
          <template v-if="$auth('innovation:checkPayment:add')">
            <a-button style="margin-right: 12px" type="primary" @click="$refs.CheckDrawer.add()">添加</a-button>
          </template>
          <template v-if="$auth('innovation:checkPayment:del')">
            <a-button
              style="margin-right: 12px"
              type="primary"
              :disabled="selectedRowKeys.length === 0"
              @click="delContract"
            >
              删除
            </a-button>
          </template>
        </template>

        <template #companyName="{ row }">
          <a
            v-if="$auth('innovation:checkPayment:edit') || $auth('innovation:checkPayment:check')"
            @click="$refs.CheckDrawer.edit(row.id)"
          >
            {{ row.companyName }}
          </a>
          <span v-else>{{ row.companyName }}</span>
        </template>

        <template #rdCnt="{ row }">
          <span v-if="!row.rdCnt">-</span>
          <a-popover v-else :autoAdjustOverflow="false" placement="bottom" trigger="click">
            <a>{{ row.rdCnt }}</a>
            <template #content>
              <div class="my-dropdown4">
                <vxe-grid
                  border
                  resizable
                  highlight-hover-row
                  auto-resize
                  max-height="244"
                  :data="row.list"
                  show-overflow="title"
                >
                  <vxe-table-column title="序号" type="seq" width="60" />
                  <vxe-table-column title="项目编号" field="rdTitle" width="140" />
                  <vxe-table-column title="项目名称" field="pname" width="300" />
                </vxe-grid>
              </div>
            </template>
          </a-popover>
        </template>

        <template #status="{ row }">
          <a-badge :color="statusColor[row.status === undefined ? 5 : row.status]" :text="getStatusName(row.status)" />
        </template>
      </ystable>
      <PreviewModal ref="PreviewModal" />
      <CheckDrawer ref="CheckDrawer" @refresh="refresh" :preview="preview" />
    </template>
    <a-empty v-else :description="false" />
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import SearchSelect from '@/components/Selects/SearchSelect'
import { YearSelect } from '@/components/Selects'
import { getStatusName, statusColor } from '@/utils/processDoc/auditStatus'
import CheckDrawer from './modules/CheckDrawer.vue'
import PreviewModal from '@/components/PreviewModal/PreviewModal.vue'

export default {
  name: 'CheckPayment',
  components: {
    ystable,
    SearchSelect,
    YearSelect,
    CheckDrawer,
    PreviewModal
  },
  data() {
    const processType = [
      { value: 0, label: '业务员' },
      { value: 1, label: '分公司总经理' },
      { value: 2, label: '财务经理' },
      { value: 3, label: '财务总监' },
      { value: 4, label: '出纳支付' }
    ]
    const statusMap = {
      0: '进行中',
      1: '通过',
      2: '驳回',
      4: '提交',
      5: '未提交'
    }
    const columns = [
      { type: 'checkbox', width: '60', align: 'center', fixed: 'left' },
      { title: '年份', field: 'year', width: '100', align: 'center', fixed: 'left', sortable: true },
      { title: '客户名称', field: 'companyName', width: '160', fixed: 'left', slots: { default: 'companyName' } },
      { title: '所属部门', field: 'deptName', width: '100', sortable: true },
      { title: '业务员', field: 'ownerName', width: '100', sortable: true },
      { title: '查新数量', field: 'rdCnt', width: '80', slots: { default: 'rdCnt' } },
      { title: '技术人员', field: 'techName', width: '100', sortable: true },
      { title: '分公司总经理', field: 'ownerMangerName', width: '140', sortable: true },
      { title: '财务经理', field: 'finaManagerName', width: '100', sortable: true },
      { title: '财务总监', field: 'finaDirectorName', width: '100', sortable: true },
      { title: '节点', field: 'nodeName', width: '100' },
      { title: '流程状态', field: 'status', width: '100', slots: { default: 'status' } },
      { title: '创建人', field: 'creatorName', width: '100' },
      { title: '当前处理人', field: 'auditUsers', width: '100' },
      { title: '创建时间', field: 'createTime', width: '160', align: 'center' },
      { title: '最后修改时间', field: 'lastUpdateTime', width: '160', align: 'center' }
    ]
    return {
      statusMap,
      statusColor,
      processType,
      form: {},
      columns,
      selectedRowKeys: []
    }
  },
  methods: {
    getStatusName,
    refresh(flag = false) {
      this.$refs.xTable.refresh(flag)
    },
    preview(filePath, filename) {
      if (filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.PreviewModal.show(filePath, filename)
    },
    selectChangeEvent({ records }) {
      this.selectedRowKeys = records.map((item) => {
        return item.id
      })
    },
    selectAllEvent({ records }) {
      this.selectedRowKeys = records.map((item) => {
        return item.id
      })
    },
    delContract() {
      this.$http
        .post('/checkPayment/delCheckPayment', { ids: this.selectedRowKeys })
        .then(({ success, errorMessage }) => {
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
      params.techId = params.techId ? params.techId.id : undefined
      params.finaManagerId = params.finaManagerId ? params.finaManagerId.id : undefined
      return params
    }
  }
}
</script>
