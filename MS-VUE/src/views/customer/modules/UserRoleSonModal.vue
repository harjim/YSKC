<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="1000"
    v-model="visible"
    @ok="handleSubmit"
    @cancel="closeModal"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    destroyOnClose
  >
    <template v-slot:footer>
      <a-button @click="closeModal">关闭</a-button>
    </template>
    <a-spin :spinning="confirmLoading" tip="请稍后...">
      <ystable
        ref="table"
        rowId="id"
        queryUrl="/customer/findSonCustomerList"
        :params="{ companyId: customer.companyId, fullPath: customer.groupFullPath, userId: userRecord.id }"
        highlight-current-row
        highlight-hover-row
        resizable
        auto-resize
        @completed="() => (selectedRowKeys = [])"
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
        :toolbar="{ zoom: true, custom: true, refresh: true }"
      >
        <template v-slot:buttons>
          <a-button type="primary" @click="batchSetRole" :disabled="selectedRowKeys.length <= 0">分配角色</a-button>
        </template>
        <vxe-table-column type="checkbox" width="40" fixed="left" />
        <vxe-table-column
          field="companyName"
          :width="200"
          title="公司名称"
          align="left"
          show-header-overflow
          show-overflow="tooltip"></vxe-table-column>
        <vxe-table-column
          field="groupName"
          :width="200"
          title="所属集团"
          align="left"
          show-header-overflow
          show-overflow="tooltip"></vxe-table-column>
        <vxe-table-column
          field="roleName"
          title="关联角色"
          align="left"
          show-header-overflow
          show-overflow="tooltip"></vxe-table-column>
        <vxe-table-column
          field="action"
          :width="120"
          title="操作"
          align="left"
          show-header-overflow
          show-overflow="tooltip"
        >
          <template v-slot="{ row }">
            <a @click="setRole(row)">分配角色</a>
          </template>
        </vxe-table-column>
      </ystable>
      <UserRoleSonSelectModal ref="userRoleSonSelectModal" @update="() => { $refs.table.refresh() }" />
    </a-spin>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'
import UserRoleSonSelectModal from './UserRoleSonSelectModal.vue'

export default {
  components: {
    ystable,
    UserRoleSonSelectModal
  },
  data () {
    return {
      visible: false,
      title: undefined,
      confirmLoading: false,
      customer: {},
      selectedRowKeys: [],
      userRecord: {
        id: undefined
      }
    }
  },
  methods: {
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => item.companyId)
    },
    showModal (record, customer, refresh) {
      this.visible = true
      this.title = `分配角色[${customer.companyName}]`
      this.customer = customer
      this.userRecord = record
    },
    handleSubmit () {
      this.visible = false
    },
    closeModal () {
      this.visible = false
    },
    setRole (record) {
      this.$refs.userRoleSonSelectModal.showModal(record.companyId, this.userRecord, this.customer.companyId)
    },
    batchSetRole () {
      this.$refs.userRoleSonSelectModal.showModal(this.selectedRowKeys, this.userRecord, this.customer.companyId)
    }
  }
}
</script>
