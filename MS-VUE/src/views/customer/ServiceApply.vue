<template>
  <a-card :bordered="false">
    <template v-if="$auth('customer:serviceApply:search')">
      <a-form-model layout="inline" ref="form" :model="form">
        <a-form-model-item label="客户名称">
          <a-input v-model="form.companyName" placeholder="请输入客户名称" style="width: 240px" />
        </a-form-model-item>
        <a-form-model-item label="单号">
          <a-input v-model="form.serviceNo" placeholder="请输入单号" style="width: 240px" />
        </a-form-model-item>
        <a-form-model-item label="财务人员">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            placeholder="请输入财务人员"
            style="width: 240px"
            v-model="search.fina"
            @change="
              (v) => {
                if (v) {
                  form.finaId = v.id
                } else {
                  form.finaId = null
                }
              }
            "
          />
        </a-form-model-item>
        <a-form-model-item label="技术人员">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            placeholder="请输入技术人员"
            style="width: 240px"
            v-model="search.tech"
            @change="
              (v) => {
                if (v) {
                  form.techId = v.id
                } else {
                  form.techId = null
                }
              }
            "
          />
        </a-form-model-item>
        <a-form-model-item label="申请人">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            placeholder="请输入申请人"
            style="width: 240px"
            v-model="search.owner"
            @change="
              (v) => {
                if (v) {
                  form.ownerId = v.id
                } else {
                  form.ownerId = null
                }
              }
            "
          />
        </a-form-model-item>
        <a-form-model-item label="处理人">
          <a-input v-model="form.auditUsers" placeholder="请输入处理人" style="width: 240px" />
        </a-form-model-item>
        <a-form-model-item label="所属部门">
          <DeptOnlySelect style="width: 240px" v-model="form.deptId" placeholder="请输入所属部门" />
        </a-form-model-item>
        <a-form-model-item label="流程状态">
          <a-select v-model="form.status" placeholder="请输入流程状态" style="width: 240px" allowClear>
            <a-select-option v-for="(v, k) in statusMap" :key="k" :value="k">{{ v }}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item>
          <a-button type="primary" @click="onSearch">查询</a-button>
        </a-form-model-item>
      </a-form-model>

      <ystable
        ref="xTable"
        query-url="/serviceApply/getList"
        :params="form"
        :seq-config="{ startIndex: 1 }"
        :toolbar="{
          refresh: true,
          zoom: true,
          custom: true
        }"
        @checkbox-all="selectAllEvent"
        @checkbox-change="selectChangeEvent"
        show-overflow="tooltip"
      >
        <template v-slot:buttons>
          <template v-if="$auth('customer:serviceApply:add')">
            <a-button style="margin-right: 12px" type="primary" @click="$refs.ServiceOrderAdd.open()">添加</a-button>
          </template>
          <template v-if="$auth('customer:serviceApply:del')">
            <a-button
              style="margin-right: 12px"
              type="primary"
              :disabled="selectedRowKeys.length === 0"
              @click="delServices"
            >
              删除
            </a-button>
          </template>
        </template>
        <vxe-table-column type="checkbox" width="64" align="center" fixed="left" />
        <vxe-table-column title="服务单号" field="serviceNo" align="center" fixed="left" width="150" remoteSort>
          <template v-slot="{ row }">
            <a v-if="$auth('customer:serviceApply:check')" @click="openDetail(row)">{{ row.serviceNo }}</a>
            <div v-else>{{ row.serviceNo }}</div>
          </template>
        </vxe-table-column>
        <vxe-table-column title="客户名称" field="customers" fixed="left" width="260" remoteSort></vxe-table-column>
        <vxe-table-column title="预计起止日期" field="date" align="center" width="170" />
        <vxe-table-column title="申请人" field="ownerName" width="100" remoteSort />
        <vxe-table-column title="所属部门" field="deptName" width="100" remoteSort />
        <vxe-table-column title="技术人员" field="techList" width="100">
          <template v-slot="{ row }">
            {{ row.techList ? row.techList.map((i) => i.userName).join(', ') : '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="财务人员" field="finaList" width="100">
          <template v-slot="{ row }">
            {{ row.finaList ? row.finaList.map((i) => i.userName).join(', ') : '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="其他人员" field="otherList" width="100">
          <template v-slot="{ row }">
            {{ row.otherList ? row.otherList.map((i) => i.userName).join(', ') : '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="流程状态" field="status" width="110" remoteSort>
          <template v-slot="{ row }">
            <a-badge :color="statusColor[row.status === null ? 5 : row.status]" :text="getStatusName(row.status)" />
          </template>
        </vxe-table-column>
        <vxe-table-column title="当前处理人" field="auditUsers" width="120" remoteSort />
        <vxe-table-column title="创建时间" field="createTime" align="center" width="140" remoteSort />
        <vxe-table-column title="最后修改时间" field="lastUpdateTime" width="140" remoteSort />
      </ystable>
      <ServiceOrderDetail ref="ServiceOrderDetail" @refresh="onSearch" />
      <ServiceOrderAdd ref="ServiceOrderAdd" @refresh="onSearch" />
    </template>
    <template v-else>
      <a-empty :description="false" />
    </template>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import ServiceOrderDetail from '@/views/customer/modules/ServiceOrderDetail'
import { getStatusName, statusColor } from '@/utils/processDoc/auditStatus'
import SearchSelect from '@/components/Selects/SearchSelect'
import SelectCompany from '@/components/Selects/SelectCompany'
import ServiceOrderAdd from '@/views/customer/modules/ServiceOrderAdd'
import DeptOnlySelect from '@/components/Selects/DeptOnlySelect.vue'

export default {
  name: 'ServiceApply',
  components: {
    ServiceOrderAdd,
    SelectCompany,
    SearchSelect,
    ServiceOrderDetail,
    ystable,
    DeptOnlySelect
  },
  data() {
    const statusMap = {
      0: '进行中',
      1: '通过',
      2: '驳回',
      4: '提交',
      5: '未提交'
    }
    return {
      statusMap,
      statusColor,
      form: {},
      search: {},
      selectedRowKeys: [],
      selectUser: []
    }
  },
  methods: {
    getStatusName,
    onSearch(flag = true) {
      this.selectedRowKeys = []
      this.selectUser = []
      this.$refs.xTable.refresh(flag)
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
    openDetail(row) {
      this.$store.commit('service/SET_DETAIL', row)
      this.$refs.ServiceOrderDetail.open()
    },
    delServices() {
      this.$http
        .post('/serviceApply/delServiceApply', { ids: this.selectedRowKeys })
        .then(({ success, errorMessage }) => {
          if (success) {
            this.$message.success('删除成功')
            this.onSearch()
          } else {
            this.$message.error(errorMessage)
          }
        })
    }
  }
}
</script>

<style lang="less">
.txt-hide {
  width: 100%;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>
