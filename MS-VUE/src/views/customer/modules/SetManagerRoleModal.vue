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
    <ystable
      ref="table"
      rowId="id"
      :columns="columns"
      :params="{ companyId: this.companyId }"
      queryUrl="/rsRole/getRoles"
      @checkbox-change="onSelectChange"
      @checkbox-all="onSelectChange"
      @completed="({data:{data}})=>{tableData = data;setSelect()}"
    ></ystable>
  </a-modal>
</template>
<script>
import ystable from '@/components/Table/ystable'
const columns = [
  {
    type: 'checkbox',
    width: 40
  }, {
    title: '角色名称',
    field: 'roleName',
    key: 'roleName',
    showOverflow: 'tooltip'
  }, {
    title: '描述',
    field: 'remark',
    key: 'remark',
    showOverflow: 'tooltip'
  }, {
    title: '创建时间',
    field: 'createTime',
    key: 'createTime',
    showOverflow: 'tooltip'
  }]

export default {
  components: {
    ystable
  },
  name: 'SetManagerRoleModal',
  data () {
    return {
      rows: [],
      userId: 0,
      tableData: [],
      columns,
      visible: false,
      title: '',
      confirmLoading: false,
      companyId: undefined,
      selectedRowKeys: [],
      userName: undefined,
      realName: undefined,
      pageRoleIds: []
    }
  },
  methods: {
    setSelect () {
      const selectRows = []
      const tempPageRoleIds = []
      this.tableData.forEach(item => {
        tempPageRoleIds.push(item.id)
        if (this.selectedRowKeys.indexOf(item.id) >= 0) {
          selectRows.push(item)
        }
      })
      this.$refs.table.setCheckboxRow(selectRows, true)
      this.pageRoleIds = tempPageRoleIds
    },
    showModal (record, companyId) {
      this.title = `分配平台角色[${record.realName}(${record.userName})]`
      this.userName = record.userName
      this.realName = record.realName
      this.userId = record.userId
      this.companyId = companyId
      this.visible = true
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
        this.getRoleId()
      })
    },
    batch (rows, companyId) {
      this.title = `分配平台角色`
      this.companyId = companyId
      this.userId = undefined
      this.rows = rows
      this.visible = true
      this.$nextTick(() => {
        if (!this.first) {
          this.$refs.table.refresh(true)
        }
        this.first = false
      })
    },
    getRoleId () {
      this.$http.get('/managerRole/getManagerRoleByTerm', { params: { userId: this.userId, companyId: this.companyId } })
        .then(res => {
          this.selectedRowKeys = res.data
          this.setSelect()
          return this.selectedRowKeys
        })
    },
    handleSubmit () {
      this.confirmLoading = true
      if (this.rows && this.rows.length > 0) {
        const userList = []
        this.rows.forEach(u => {
          // 截取有用字段
          userList.push({ userId: u.userId, userName: u.userName, realName: u.realName })
        })
        this.$http.post('/managerRole/deployManagerRoles', { userList: userList, roleIds: this.selectedRowKeys, companyId: this.companyId })
          .then(res => {
            if (res.success && res.data) {
              this.$message.success('分配角色成功')
              this.$emit('ok')
              this.closeModal()
            } else {
              this.$message.error(res.errorMessage)
            }
          }).finally(() => {
            this.confirmLoading = false
          })
      } else {
        this.$http.post('/managerRole/deployManagerRole', { userId: this.userId, roleIds: this.selectedRowKeys, companyId: this.companyId, pageRoleIds: this.pageRoleIds })
          .then(res => {
            if (res.success && res.data) {
              this.$message.success('分配角色成功')
              this.$emit('ok')
              this.closeModal()
            } else {
              this.$message.error(res.errorMessage)
            }
          }).finally(() => {
            this.confirmLoading = false
          })
      }
    },
    onSelectChange ({ records }) {
      this.selectedRowKeys = records.map(item => item.id)
    },
    closeModal () {
      this.visible = false
      this.userId = 0
      this.realName = undefined
      this.userName = undefined
      this.rows = []
      this.selectedRowKeys = []
    }
  }
}
</script>

<style>
</style>
