<template>
  <a-modal
    :width="640"
    :visible="visible"
    :title="title"
    @cancel="visible = false"
    :maskClosable="false"
    :footer="null"
  >
    <a-card :bordered="false">
      <div>
        <a-form layout="inline">
          <a-form-item label="客户名称">
            <a-input v-model="companyName" placeholder />
          </a-form-item>
          <span class="table-page-search-submitButtons" style="padding-top:5px;">
            <a-button type="primary" @click="$refs.table.refresh(true)" style="margin-top:3px;">查询</a-button>
          </span>
        </a-form>
        <ystable
          ref="table"
          size="default"
          rowId="companyName"
          :params="{ roleId: this.roleId, companyName: this.companyName }"
          queryUrl="/rsRole/queryCustomer"
        >
          <vxe-table-column
            title="客户名称"
            field="companyName"
            align="left"
            show-header-overflow
            show-overflow="tooltip"
          />
          <vxe-table-column
            title="分配时间"
            field="createTime"
            align="left"
            show-header-overflow
            show-overflow="tooltip"
          />
        </ystable>
      </div>
    </a-card>
  </a-modal>
</template>
<script>
import ystable from '@/components/Table/ystable'
export default {
  name: 'AssIgnedCustomers',
  components: {
    ystable
  },
  data () {
    return {
      companyName: '',
      title: '已分配客户',
      visible: false,
      roleId: 0
    }
  },
  methods: {
    showModal (record) {
      this.title = `[${record.roleName}]已分配客户`
      this.visible = true
      this.roleId = record.id
      this.companyName = ''
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
    }
  }
}
</script>
