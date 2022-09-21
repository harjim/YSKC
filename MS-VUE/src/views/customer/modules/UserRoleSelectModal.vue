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
    destroyOnClose
  >
    <template v-slot:footer>
      <a-button @click="closeModal">关闭</a-button>
    </template>
    <a-spin :spinning="confirmLoading" tip="请稍后...">
      <ystable
        ref="table"
        rowId="id"
        :columns="columns"
        queryUrl="/rsRole/getRoles"
        :params="{companyId:customer.companyId}"
        @checkbox-change="onSelectChange"
        @checkbox-all="onSelectChange"
        @completed="({data:{data}})=>{tableData = data;setSelect()}"
      ></ystable>
      <br/><span>
        <a-button type="primary" @click="handleSubmit" :loading="confirmLoading">保存</a-button>
      </span>
    </a-spin>
  </a-modal>
</template>
<script>
import ystable from '@/components/Table/ystable'
const columns = [
  {
    type: 'checkbox',
    width: 40
  },
  {
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
  data () {
    return {
      defaultSelect: [],
      userId: 0,
      columns,
      tableData: [],
      visible: false,
      title: '',
      confirmLoading: false,
      companyIds: undefined,
      selectedIds: [],
      pageRoleIds: [],
      customer: {}
    }
  },
  methods: {
    setSelect () {
      this.selectedIds = []
      const selectRows = []
      const tempPageRoleIds = []
      this.$refs.table.setAllCheckboxRow(false)
      this.tableData.forEach(item => {
        tempPageRoleIds.push(item.id)
        if (this.defaultSelect.indexOf(item.id) >= 0) {
          selectRows.push(item)
          this.selectedIds.push(item.id)
        }
      })
      this.$refs.table.setCheckboxRow(selectRows, true)
      this.pageRoleIds = tempPageRoleIds
    },
    showModal (record, customer, refresh) {
      this.title = `分配角色[${record.userName}][${record.realName}]`
      this.userId = record.id
      this.customer = customer
      this.companyIds = [customer.companyId]
      this.visible = true
      this.getRoleId()
    },
    getRoleId () {
      this.$http.get('/rsRole/getRoleUserId', { params: { userId: this.userId, companyId: this.companyIds } })
        .then(res => {
          this.defaultSelect = res.data
          this.setSelect()
        })
    },
    handleSubmit () {
      this.confirmLoading = true
      this.$http.post('/rsRole/setUserRole', { userId: this.userId, roleIds: this.selectedIds, companyIds: this.companyIds, pageRoleIds: this.pageRoleIds })
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('保存成功')
            this.getRoleId()
          } else {
            this.$message.error(res.errorMessage)
          }
        }).finally(() => { this.confirmLoading = false })
    },
    onSelectChange ({ records }) {
      this.selectedIds = records.map(item => item.id)
    },
    closeModal () {
      this.visible = false
    }
  }
}
</script>

<style>
</style>
