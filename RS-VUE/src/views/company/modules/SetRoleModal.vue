<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    @cancel="closeModal"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-table
      ref="table"
      size="small"
      rowKey="id"
      :columns="columns"
      :dataSource="roles"
      bordered
      :pagination="false"
      :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
    ></a-table>
  </a-modal>
</template>
<script>
const columns = [
  {
    title: '角色名称',
    dataIndex: 'roleName',
    key: 'roleName'
  }, {
    title: '描述',
    dataIndex: 'remark',
    key: 'remark'
  }, {
    title: '创建时间',
    dataIndex: 'createTime',
    key: 'createTime'
  }]
export default {
  name: 'SetRoleModal',
  data () {
    return {
      userId: 0,
      columns,
      visible: false,
      title: '',
      confirmLoading: false,
      selectedRowKeys: [],
      roles: []
    }
  },
  created () {
    this.$http.get('/role/getRoles', { params: { roleName: this.roleName } })
      .then(res => {
        this.roles = res.data
        return this.roles
      })
  },
  methods: {
    showModal (record) {
      this.title = `分配角色[${record.userName}][${record.realName}]`
      this.userId = record.id
      this.getRoleId()
      this.visible = true
    },
    getRoleId () {
      this.$http.get('/role/getUserRole', { params: { userId: this.userId } })
        .then(res => {
          this.selectedRowKeys = res.data
          return this.selectedRowKeys
        })
    },
    handleSubmit () {
      this.confirmLoading = true
      this.$http.post('/role/setUserRole', { userId: this.userId, roleIds: this.selectedRowKeys })
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('分配成功')
            this.closeModal()
            this.$emit('ok')
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '分配失败')
          }
        }).finally(res => {
          this.confirmLoading = false
        })
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    closeModal () {
      this.visible = false
      this.userId = 0
      this.selectedRowKeys = []
    }
  }
}
</script>

<style>
</style>
