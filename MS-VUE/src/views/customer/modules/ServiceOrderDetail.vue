<template>
  <a-drawer
    destroyOnClose
    :visible="visible"
    :title="`${getIsEditStatus ? '编辑服务单' : '服务单详情'}-${currentOrder.serviceNo}`"
    :width="1184"
    :drawerStyle="{ height: '100vh' }"
    :bodyStyle="{ overflowY: 'hidden' }"
    @close="close"
  >
    <tab-layout :class="{ 'not-log': currentOrder.instanceId }">
      <template #up>
        <div style="height: 100%">
          <ServiceOrderDetailCheck ref="ServiceOrderDetailCheck"/>
        </div>
      </template>
      <template #down v-if="currentOrder.instanceId">
        <template
          v-if="$auth('customer:serviceApply:review') || $auth('customer:serviceApply:audit')"
        >
          <ServiceOrderDetailAudit @refresh="$emit('refresh')" storeName="service"/>
        </template>
      </template>
    </tab-layout>

    <div
      v-if="getIsEditStatus"
      :style="{
        position: 'absolute',
        right: 0,
        bottom: 0,
        width: '100%',
        height: '56px',
        borderTop: '1px solid #e9e9e9',
        padding: '20px 20px',
        background: '#fff',
        textAlign: 'right',
        zIndex: 999,
      }"
    >
      <a-button
        :style="{ marginRight: '8px' }"
        @click="handleSaveForm('/serviceApply/editServiceApply')"
      >
        暂存
      </a-button>
      <a-popconfirm
        title="是否确认提交?"
        :autoAdjustOverflow="false"
        placement="topRight"
        @confirm="handleSaveForm('/serviceApply/submit')"
      >
        <a-button type="primary">
          提交
        </a-button>
      </a-popconfirm>
    </div>
  </a-drawer>
</template>

<script>
import ServiceOrderDetailCheck from '@/views/customer/modules/ServiceOrderDetailCheck'
import ServiceOrderDetailAudit from '@/views/customer/modules/ServiceOrderDetailAudit'
import TabLayout from '@/views/customer/modules/AuditProgress/modules/TabLayout'
import { isEditStatus } from '@/utils/processDoc/auditStatus'
import { mapGetters, mapState } from 'vuex'

export default {
  name: 'ServiceOrderDetail',
  components: {
    TabLayout,
    ServiceOrderDetailAudit,
    ServiceOrderDetailCheck
  },
  data () {
    return {
      visible: false
    }
  },
  computed: {
    ...mapState({
      currentOrder: state => state.service.currentOrder
    }),
    ...mapGetters('service', ['getIsEditStatus'])
  },
  methods: {
    isEditStatus,
    open () {
      this.visible = true
    },
    close () {
      this.$store.dispatch({
        type: 'service/closeDrawer',
        edit: false
      })
      this.visible = false
    },
    handleSaveForm (url) {
      if (!(this.currentOrder.techList && this.currentOrder.techList.length) && !(this.currentOrder.finaList && this.currentOrder.finaList.length)) {
        this.$message.error('技术人员与财务人员至少选择一个')
        return
      }
      if (!this.currentOrder.begin || !this.currentOrder.end) {
        this.$message.error('请选择预计起止日期')
        return
      }
      const [flag, customerList] = this.$refs.ServiceOrderDetailCheck.$refs.ServiceEditTable.validAllEvent()
      if (!flag) return
      this.$nextTick(() => {
        this.$http.post(url, { ...this.currentOrder, customerList }).then(({ success, errorMessage }) => {
          if (success) {
            this.$message.success('操作成功')
            this.close()
            this.$emit('refresh', false)
          } else {
            this.$message.error(errorMessage)
          }
        })
      })
    }
  }
}
</script>

<style lang="less" scoped>
& /deep/ .ant-spin-container {
  height: calc(100vh - 64px);
}

.not-log /deep/ .center_wrap {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 110px);

  .up {
    height: 70%;
    width: 100%;

    &::-webkit-scrollbar {
      display: none;
    }
  }

  & > .midden {
    width: 100%;
    height: 6px;
    background-color: #F0F2F5;
    border-radius: 3px;

    &:hover {
      cursor: n-resize;
      background-color: #d6d6d6;
    }
  }

  .down {
    overflow: auto;
    flex: 1;
    height: 100%;

    &::-webkit-scrollbar {
      display: none;
    }

    & /deep/ .ant-tabs-tabpane {
      overflow: auto;
    }
  }
}
</style>
