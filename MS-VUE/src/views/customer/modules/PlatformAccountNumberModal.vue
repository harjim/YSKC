<template>
  <a-modal
    :destroyOnClose="true"
    :width="1200"
    :zIndex="100"
    :visible="visible"
    :title="title"
    :footer="null"
    :maskClosable="false"
    @cancel="closeModal"
  >
    <div>
      <a-tabs v-model="actvieKey" @change="callback">
        <a-tab-pane :tab="`角色管理`" key="1" v-if="$auth('customer:all:setRole')">
          <role-select-modal @success="accountRoleRefresh = true" ref="roleModal" :companyId="companyId"></role-select-modal>
        </a-tab-pane>
        <a-tab-pane :tab="`帐号管理`" key="2" v-if="$auth('customer:all:platformAccount')">
          <accountingManagement-modal ref="accountingModal" :roleRefresh="accountRoleRefresh" :customer="customer"></accountingManagement-modal>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-modal>
</template>
<script>
import AccountingManagementModal from './AccountingManagementModal'
import RoleSelectModal from './RoleSelectModal'
export default {
  components: {
    AccountingManagementModal,
    RoleSelectModal
  },
  data () {
    return {
      title: '',
      id: 0,
      visible: false,
      accountRoleRefresh: false,
      formula: { value: '1' },
      data: 0,
      actvieKey: '1',
      companyId: undefined,
      customer: {}

    }
  },
  methods: {
    open (record) {
      this.customer = record
      this.actvieKey = '1'
      this.visible = true
      this.id = record.id
      this.title = '[' + record.companyName + ']'
      this.companyId = record.companyId
    },
    callback (activeKey) { // 切换tab调用
      if (activeKey === '1' && this.$refs.roleModal) {
        this.$refs.roleModal.search()
      }
      this.actvieKey = activeKey
    },
    closeModal () {
      this.$emit('ok', true)
      this.visible = false
    }
  }
}
</script>

<style scoped>
</style>
