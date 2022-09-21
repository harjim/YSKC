<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :destroyOnClose="true"
    :confirmLoading="confirmLoading"
  >
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input v-model="selectEname" placeholder="请输入客户名称" style="width:180px" />
      </a-form-item>
      <a-form-item>
        <span>
          <a-button type="primary" @click="search">查询</a-button>
        </span>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      queryUrl="/customer/findSonCustomerList"
      :params="{ companyName: selectEname, companyId: companyId }"
      highlight-current-row
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      @completed="() => (selectedRowKeys = [])"
      @checkbox-change="selectChange"
      @checkbox-all="selectChange"
      :toolbar="{ zoom: true, custom: true, refresh: true }"
    >
      <vxe-table-column type="checkbox" width="40" fixed="left" />
      <vxe-table-column
        title="客户名称"
        field="companyName"
        key="companyName"
        show-header-overflow
        :min-width="300"
        remoteSort
        show-overflow="tooltip"
      ></vxe-table-column>
      <vxe-table-column
        title="所属部门"
        field="deptName"
        key="deptName"
        show-header-overflow
        show-overflow="tooltip"
      ></vxe-table-column>
    </ystable>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'
export default {
  components: {
    ystable
  },
  props: {
    groupId: {
      type: Number || String,
      default: 0
    }
  },
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      id: 0,
      companyId: undefined,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      selectEname: undefined,
      selectedRowKeys: []
    }
  },
  methods: {
    findNotSonCustomer (companyId) {
      this.selectEname = undefined
      this.title = '添加子公司'
      this.confirmLoading = false
      this.visible = true
      this.selectedRowKeys = []
      this.companyId = companyId
    },
    handleSubmit () {
      if (!this.selectedRowKeys || !this.selectedRowKeys.length) {
        this.$message.warning('请选择子公司')
        return
      }
      this.confirmLoading = true
      this.$http
        .post('/customer/insertSonCustomer', { groupId: this.groupId, ids: this.selectedRowKeys, companyId: this.companyId })
        .then(res => {
          if (res.success && res.data) {
            this.visible = false
            this.confirmLoading = false
            this.$emit('ok', true)
            this.form.resetFields()
          } else {
            this.$message.error(res.errorMessage)
          }
        })
        .finally(res => {
          this.confirmLoading = false
        })
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => item.id)
    },
    search () {
      this.selectedRowKeys = []
      if (this.$refs.table) {
        this.$refs.table.refresh(true)
      }
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
