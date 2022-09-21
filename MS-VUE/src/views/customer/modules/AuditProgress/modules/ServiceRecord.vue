<template>
  <tab-layout :spinning="spinning">
    <template #up>
      <a-descriptions :column="2">
        <a-descriptions-item label="客户名称">
          {{ detail.companyName }}
        </a-descriptions-item>
        <a-descriptions-item label="关联单号">
          {{ detail.serviceNo || '-' }}
        </a-descriptions-item>
        <a-descriptions-item label="业务员">
          {{ detail.ownerName }}
        </a-descriptions-item>
        <a-descriptions-item label="所属部门">
          {{ detail.deptName }}
        </a-descriptions-item>
        <a-descriptions-item label="创建人">
          {{ detail.creatorName }}
        </a-descriptions-item>
        <a-descriptions-item label="创建时间">
          {{ detail.createTime }}
        </a-descriptions-item>
      </a-descriptions>

      <vxe-grid
        :data="detail.list"
        :columns="columns"
        resizable
        stripe
        show-overflow="tooltip"
        show-footer
        :footer-method="footerMethod"
      >
        <template #itemTypeSlot="{ row }">
          {{ type2value(row.itemType) }}
        </template>
      </vxe-grid>
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
  name: 'ServiceRecord',
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
      { title: '事项', field: 'itemType', minWidth: 100, slots: { default: 'itemTypeSlot' } },
      { title: '起止时间', field: 'date', width: 250, align: 'center' },
      { title: '费用', field: 'amount', minWidth: 80, align: 'right' },
      { title: '备注', field: 'remark', minWidth: 180 }
    ]
    return {
      spinning: false,
      columns,
      dictionary: [],
      detail: {}
    }
  },
  computed: {},
  watch: {},
  methods: {
    type2value (key) {
      let result = '-'
      if (key) {
        this.dictionary.forEach(item => {
          if (item.key === key) {
            result = item.value
            return false
          }
        })
      }
      return result
    },
    footerMethod ({ columns, data }) {
      return [columns.map((col, _colI) => {
        if (_colI === 0) {
          return '合计'
        }
        if (_colI === 3) {
          return data.reduce((total, row) => {
            if (isNaN(row.amount)) return total
            return total + row.amount
          }, 0).toFixed(2)
        }
        return ''
      })]
    }
  },
  beforeCreate () {
  },
  created () {
  },
  beforeMount () {
    this.$http.get('/sysDictionary/getDictionary', {
      params: { type: 17 }
    }).then(({ data }) => {
      this.dictionary = data
    })
  },
  mounted () {
    this.spinning = true
    this.$http.get('/serviceRecord/getRecordInfo', {
      params: { recordId: this.record.formId }
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
