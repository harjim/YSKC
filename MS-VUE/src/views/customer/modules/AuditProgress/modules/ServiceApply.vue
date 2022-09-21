<template>
  <tab-layout :spinning="spinning">
    <template #up>
      <a-descriptions :column="2">
        <a-descriptions-item label="申请人">
          {{ detail.ownerName }}
        </a-descriptions-item>
        <a-descriptions-item label="所属部门">
          {{ detail.deptName }}
        </a-descriptions-item>
        <a-descriptions-item label="技术人员">
          {{ detail.techList ? detail.techList.map(i => i.userName).join(', ') : '-' }}
        </a-descriptions-item>
        <a-descriptions-item label="财务人员">
          {{ detail.finaList ? detail.finaList.map(i => i.userName).join(', ') : '-' }}
        </a-descriptions-item>
        <a-descriptions-item label="技术经理">
          {{ detail.techManagerName || '-' }}
        </a-descriptions-item>
        <a-descriptions-item label="财务经理">
          {{ detail.finaManagerName || '-' }}
        </a-descriptions-item>
        <a-descriptions-item label="技术总监">
          {{ detail.techDirectorName || '-' }}
        </a-descriptions-item>
        <a-descriptions-item label="财务总监">
          {{ detail.finaDirectorName || '-' }}
        </a-descriptions-item>
        <a-descriptions-item label="其他人员">
          {{ detail.otherList ? detail.otherList.map(i => i.userName).join(', ') : '-' }}
        </a-descriptions-item>
        <a-descriptions-item label="预计起止日期">
          {{ detail.date || '-' }}
        </a-descriptions-item>
      </a-descriptions>

      <vxe-grid
        :data="detail.customerList"
        :columns="columns"
        resizable
        stripe
        show-overflow="tooltip"
      />
    </template>

    <template #down>
      <template
        v-if="$auth('customer:serviceApply:review') || $auth('customer:serviceApply:audit')"
      >
        <ServiceOrderDetailAudit :record="detail" storeName="service"/>
      </template>
    </template>
  </tab-layout>
</template>

<script>
import TabLayout from '@/views/customer/modules/AuditProgress/modules/TabLayout'
import ServiceOrderDetailAudit from '@/views/customer/modules/ServiceOrderDetailAudit'

export default {
  name: 'ServiceApply',
  components: { ServiceOrderDetailAudit, TabLayout },
  props: {
    record: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data () {
    const columns = [
      { title: '序号', type: 'seq', width: 60, align: 'center' },
      { title: '公司名称', field: 'companyName', width: 180 },
      { title: '事由', field: 'causes', minWidth: 180 }
    ]
    return {
      spinning: false,
      columns,
      detail: {}
    }
  },
  computed: {},
  watch: {},
  methods: {},
  beforeCreate () {
  },
  created () {
  },
  beforeMount () {
  },
  mounted () {
    this.spinning = true
    this.$http.get('/serviceApply/getServiceInfo', {
      params: { serviceId: this.record.formId }
    }).then(({ success, data, errorMessage }) => {
      if (success) {
        this.detail = data
      } else {
        this.$message.error(errorMessage)
      }
    }).finally(() => {
      this.spinning = false
    })
  },
  beforeUpdate () {
  },
  updated () {
  },
  beforeDestroy () {
  },
  destroyed () {
  }
}
</script>

<style lang="less" scoped>
& /deep/ .ant-descriptions-item-content {
  width: auto;
}

& /deep/ .center_wrap {
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
    height: 320px;
    min-height: 260px;

    &::-webkit-scrollbar {
      display: none;
    }

    & /deep/ .ant-tabs-tabpane {
      overflow: auto;
    }
  }
}
</style>
