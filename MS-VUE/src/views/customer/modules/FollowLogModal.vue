<template>
  <a-modal
    destroyOnClose
    :width="900"
    :visible="visible"
    :maskClosable="false"
    :title="`跟进记录 [${companyName}]`"
    @cancel="closeModal"
  >
    <template slot="footer">
      <a-button key="back" @click="closeModal">
        关闭
      </a-button>
    </template>
    <ystable
      ref="followLog"
      queryUrl="/customer/getTraceList"
      :params="logParams"
      highlight-current-row
      highlight-hover-row
      resizable
      :sync-resize="true"
      :toolbar="{ zoom: true, custom: true, refresh: true }"
    >
      <vxe-table-column
        title="跟进情况"
        field="info"
        key="info"
        :width="480"
        show-header-overflow
        show-overflow="tooltip"/>
      <vxe-table-column
        title="跟进类型"
        field="type"
        key="type"
        :width="100"
        show-header-overflow
        show-overflow="tooltip"
      >
        <template v-slot="{ row }">{{ typeData[row.type] }}</template>
      </vxe-table-column>
      <vxe-table-column
        title="操作人"
        field="effectUser"
        key="effectUser"
        :width="95"
        show-overflow="tooltip"
        show-header-overflow />
      <vxe-table-column
        title="操作时间"
        field="effectDate"
        key="effectDate"
        :width="180"
        align="center"
        show-overflow="tooltip"
        show-header-overflow />
    </ystable>
  </a-modal>
</template>
<script>
import ystable from '@/components/Table/ystable'

export default {
  components: {
    ystable
  },
  data () {
    return {
      typeData: {
        0: '未签',
        1: '添加客户',
        2: '变更业务员',
        3: '逾期未跟进',
        4: '变更名称',
        5: '修改项目信息',
        10: '邀约',
        20: '拜访',
        30: '已签'
      },
      companyName: '',
      customerId: '',
      visible: false
    }
  },
  computed: {
    logParams () {
      return { 'customerId': this.customerId }
    }
  },
  methods: {
    open (companyName, customerId) {
      this.companyName = companyName
      this.customerId = customerId
      this.visible = true
    },
    closeModal () {
      this.visible = false
    }
  }
}
</script>
