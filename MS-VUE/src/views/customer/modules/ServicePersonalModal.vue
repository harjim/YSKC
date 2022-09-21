<template>
  <a-modal
    :width="660"
    :visible="visible"
    :title="title"
    @cancel="visible = false"
    :maskClosable="false"
    :footer="null"
  >
    <a-card :bordered="false">
      <div>
        <a-form layout="inline">
          <a-form-item label="服务人员">
            <a-input v-model="realName" placeholder />
          </a-form-item>
          <a-form-item label="客户名称">
            <a-input v-model="companyName" placeholder />
          </a-form-item>
          <span class="table-page-search-submitButtons">
            <a-button type="primary" @click="$refs.table.refresh(true)" style="margin-top:3px;">查询</a-button>
          </span>
        </a-form>
        <ystable
          ref="table"
          size="default"
          rowId="id"
          :params="{ roleId: this.roleId, realName: this.realName, companyName: this.companyName }"
          queryUrl="/rsRole/queryServicePersonal"
        >
          <vxe-table-column
            title="服务人员"
            key="realName"
            field="realName"
            align="left"
            show-header-overflow
            show-overflow="tooltip"
          />
          <vxe-table-column
            title="客户名称"
            key="companyName"
            field="companyName"
            align="left"
            show-header-overflow
            show-overflow="tooltip"
          />
          <vxe-table-column
            title="分配时间"
            key="lastUpdateTime"
            field="lastUpdateTime"
            show-header-overflow
            show-overflow="tooltip"
            align="left"
          />
        </ystable>
      </div>
    </a-card>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'

export default {
  name: 'ServicePersonal',
  components: {
    ystable
  },
  data () {
    return {
      realName: '',
      companyName: '',
      title: '服务人员列表',
      visible: false,
      roleId: 0
    }
  },
  watch: {
    roleId () {
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
    }
  },
  methods: {
    showModal (record) {
      this.title = `[${record.roleName}]服务人员列表`
      this.visible = true
      this.roleId = record.id
      this.companyName = ''
      this.realName = ''
    }
  }
}
</script>
