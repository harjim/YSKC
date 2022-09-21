<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="handleCancel"
    :maskClosable="false"
    :destroyOnClose="true"
    :confirmLoading="confirmLoading"
  >
    <template v-slot:footer>
      <a-button @click="handleCancel">关闭</a-button>
    </template>
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input v-model="selectEname" placeholder="请输入客户名称" style="width:180px" />
      </a-form-item>
      <a-form-item>
        <span v-if="$auth('customer:all:sonCustomer')">
          <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
        </span>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      queryUrl="/customer/findSonCustomerList"
      :params="{ companyId: companyId, companyName: selectEname, fullPath: entity.groupFullPath, ignoreSelf: 1 }"
      highlight-current-row
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      @checkbox-change="selectChange"
      @checkbox-all="selectChange"
      :toolbar="{ zoom: true, custom: true, refresh: true }"
    >
      <template v-slot:buttons>
        <a-button
          style="margin-right: 10px;"
          type="primary"
          @click="$refs.notSonCustomer.findNotSonCustomer(companyId)"
          v-if="$auth('customer:all:addSon')"
        >添加</a-button
        >
        <a-popconfirm
          v-if="$auth('customer:all:delSon')"
          title="是否确定删除?"
          :disabled="!selectedRowKeys.length"
          @confirm="delAll"
        >
          <a-button type="primary" :disabled="!selectedRowKeys.length">删除</a-button>
        </a-popconfirm>
      </template>
      <vxe-table-column type="checkbox" width="40" fixed="left" v-if="$auth('customer:all:delSon')" />
      <vxe-table-column
        title="客户名称"
        field="companyName"
        key="companyName"
        show-header-overflow
        :min-width="200"
        remoteSort
        show-overflow="tooltip"
      >
        <template v-slot="{ row }">
          {{ row.companyName }}
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="所属部门"
        field="deptName"
        key="deptName"
        :width="220"
        show-header-overflow
        show-overflow="tooltip"
      >
      </vxe-table-column>
      <vxe-table-column
        title="操作"
        field="key"
        :width="180"
        align="center"
        header-align="center"
        fixed="right"
        v-if="$auth('customer:all:delSon')"
      >
        <template v-slot="{ row }">
          <a-popconfirm title="是否确定删除?" @confirm="del(row.id)" v-if="row.groupId===companyId">
            <a>删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </ystable>
    <not-son-customer-modal
      :groupId="companyId"
      ref="notSonCustomer"
      @ok="search(true)"
    ></not-son-customer-modal>
  </a-modal>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import ystable from '@/components/Table/ystable'
import NotSonCustomerModal from './NotSonCustomerModal'
import Template from '@/views/doc/Template.vue'
export default {
  components: {
    STable,
    Ellipsis,
    ystable,
    NotSonCustomerModal,
    Template
  },
  data () {
    return {
      companyId: undefined,
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
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      entity: {},
      checked: true,
      selectEname: '',
      selectedRowKeys: [],
      searchFlag: false // 更新客户列表标记
    }
  },
  methods: {
    findSonCustomer (record) {
      this.title = '集团管理[' + record.companyName + ']'
      this.confirmLoading = false
      this.visible = true
      this.selectedRowKeys = []
      this.entity = record
      this.selectEname = undefined
      this.companyId = record.companyId
      this.searchFlag = false
    },
    handleSubmit () {
      if (!this.checked) {
        return
      }
      const {
        form: { validateFields }
      } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values = Object.assign(this.entity, values)
          values.customerId = this.entity.id
          this.$http
            .post('/customer/openingAccount', values)
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
        } else {
          this.confirmLoading = false
        }
      })
    },
    del (id) {
      this.$http.post('/customer/delSonCustomer', { ids: [id], companyId: this.companyId }).then(res => {
        if (res.data) {
          this.$message.success('删除成功')
          this.selectedRowKeys = []
          this.$refs.table.refresh(true)
          this.searchFlag = true
          // this.queryProject()
        } else {
          this.$message.error('删除失败!')
        }
      })
    },
    delAll () {
      this.$http.post('/customer/delSonCustomer', { ids: this.selectedRowKeys, companyId: this.companyId }).then(res => {
        if (res.data) {
          this.$message.success('删除成功')
          this.selectedRowKeys = []
          this.$refs.table.refresh(true)
          this.searchFlag = true
          // this.queryProject()
        } else {
          this.$message.error('删除失败!')
        }
      })
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => item.id)
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.$refs.table.refresh(refresh)
      this.searchFlag = true
    },
    handleCancel () {
      this.visible = false
      if (this.searchFlag) {
        this.$emit('search')
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
