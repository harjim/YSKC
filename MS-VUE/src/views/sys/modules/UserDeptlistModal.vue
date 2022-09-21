<template>
  <a-modal
    :width="1000"
    :visible="visible"
    :title="title"
    @cancel="visible = false"
    :maskClosable="false"
    :footer="null"
    destroyOnClose
  >
    <a-form layout="inline" class="dept_user_search" :form="form">
      <a-form-item label="姓名">
        <a-input v-model="realName" placeholder="请输入姓名" />
      </a-form-item>
      <span>
        <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
      </span>
      <span style="padding-left:20px;">
        <a-button type="primary" @click="$refs.selectModal.showModal(deptId,deptName)">添加</a-button>
      </span>
      <span style="padding-left:20px;">
        <a-button
          type="primary"
          :disabled="selectedRowKeys.length <= 0"
          style="padding-left:20px;"
          @click="delUser"
        >删除</a-button>
      </span>
      <span style="padding-left:20px;">
        <a-button
          type="primary"
          :disabled="selectedRowKeys.length <= 0"
          style="padding-left:20px;"
          @click="$refs.addPrincipal.open(deptId, deptName, selectUser)"
        >设置负责人</a-button>
      </span>
    </a-form>
    <div>
      <s-table
        ref="table"
        size="default"
        rowKey="id"
        :columns="columns"
        :data="getData"
        showPagination="auto"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
      >
        <span slot="remark" slot-scope="text">
          <ellipsis :length="10" tooltip>{{ text }}</ellipsis>
        </span>
        <span slot="status" slot-scope="text">
          {{ text===1?'禁用':'启用' }}
        </span>
      </s-table>
    </div>
    <deptSelectUser-modal ref="selectModal" @ok="$refs.table.refresh(flag)"></deptSelectUser-modal>
    <add-principal-modal ref="addPrincipal" @ok="handleOk" />
  </a-modal>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import DeptSelectUserModal from './DeptSelectUserModal'
import AddPrincipalModal from './AddPrincipalModal'
// 用户表格
const columns = [{
  dataIndex: 'userName',
  key: 'userName',
  title: '用户名',
  align: 'left',
  width: 150
}, {
  dataIndex: 'realName',
  key: 'realName',
  title: '姓名',
  align: 'left',
  width: 120
}, {
  dataIndex: 'deptName',
  key: 'deptName',
  title: '所属部门',
  align: 'left',
  width: 160
}, {
  title: '电话',
  dataIndex: 'tel',
  key: 'tel',
  align: 'left',
  width: 140
}, {
  title: '职位',
  dataIndex: 'postion',
  key: 'postion',
  align: 'left',
  width: 120
}, {
  title: '状态',
  key: 'status',
  dataIndex: 'status',
  align: 'left',
  scopedSlots: { customRender: 'status' }
}, {
  title: '备注',
  key: 'remark',
  dataIndex: 'remark',
  align: 'left',
  scopedSlots: { customRender: 'remark' }
}]
export default {
  name: 'UserListByDeptId',
  components: {
    STable,
    Ellipsis,
    DeptSelectUserModal,
    AddPrincipalModal
  },
  data () {
    return {
      realName: '',
      title: '用戶列表',
      visible: false,
      selectedRowKeys: [],
      selectUser: [],
      columns,
      form: this.$form.createForm(this),
      deptId: 0,
      deptName: undefined,
      getData: parameter => {
        this.selectedRowKeys = []
        return this.$http.get('/dept/getUserDataByDeptId', { params: Object.assign(parameter, { deptId: this.deptId, realName: this.realName }) })
          .then(res => {
            return res.data
          })
      }
    }
  },
  methods: {
    showModal (record) {
      this.title = `[${record.deptName}]用戶列表`
      this.visible = true
      this.deptId = record.id
      this.deptName = record.deptName
      /*
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
      */
    },
    handleOk (flag) {
      // if (flag) {
      //   this.$message.success('操作成功')
      // }
      this.$refs.table.refresh(flag)
      this.$emit('ok')
    },
    onSelectChange (keys, obj) {
      this.selectedRowKeys = keys
      this.selectUser = obj
    },
    delUser () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的用户吗?',
        onOk () {
          return self.$http.post('/dept/deleteDeptUser', self.selectUser)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.selectedRowKeys = []
                self.selectUser = []
                self.$refs.table.refresh()
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
                self.selectedRowKeys = []
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    }
  }

}
</script>
