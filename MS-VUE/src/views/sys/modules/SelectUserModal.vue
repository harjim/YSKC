<template>
  <a-modal
    :width="1000"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @cancel="visible = false"
  >
    <a-form layout="inline" class="dept_user_search" :form="form">
      <a-form-item label="姓名">
        <a-input v-model="realName" placeholder="请输入姓名" />
      </a-form-item>
      <a-form-item label="所属部门">
        <dept-select-only
          ref="deptSelect"
          @deptFullPath="path=>fullPath = path"
          style="width:180px;"
        />
      </a-form-item>
      <span>
        <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
      </span>
    </a-form>
    <div>
      <a-form @submit="handleSubmit" :form="form">
        <s-table
          ref="table"
          size="default"
          rowKey="id"
          :columns="columns"
          :data="getData"
          showPagination="auto"
          :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        >
          <span slot="deptName" slot-scope="text">
            <ellipsis :length="36" tooltip>{{ text }}</ellipsis>
          </span>
          <span slot="remark" slot-scope="text">
            <ellipsis :length="10" tooltip>{{ text }}</ellipsis>
          </span>
        </s-table>
      </a-form>
    </div>
  </a-modal>
</template>

<script>
import { STable, Ellipsis } from '@/components'
import DeptSelectOnly from '@/components/DeptSelectOnly'
// 用户表格
const columns = [{
  dataIndex: 'userName',
  key: 'userName',
  title: '用户名',
  align: 'left',
  width: 100
}, {
  dataIndex: 'realName',
  key: 'realName',
  title: '姓名',
  align: 'left',
  width: 80
}, {
  dataIndex: 'deptName',
  key: 'deptName',
  title: '所属部门',
  align: 'left',
  scopedSlots: { customRender: 'deptName' },
  width: 280
}, {
  title: '电话',
  dataIndex: 'tel',
  key: 'tel',
  align: 'left',
  width: 90
}, {
  title: '职位',
  dataIndex: 'postion',
  key: 'postion',
  align: 'left',
  width: 100
}, {
  title: '备注',
  key: 'remark',
  dataIndex: 'remark',
  align: 'left',
  scopedSlots: { customRender: 'remark' }
}]
export default {
  name: 'TabulationByRoleId',
  components: {
    STable,
    Ellipsis,
    DeptSelectOnly
  },
  data () {
    return {
      selectedRowKeys: [],
      realName: '',
      selectUser: [],
      title: '用戶列表',
      visible: false,
      fullPath: undefined,
      columns,
      form: this.$form.createForm(this),
      roleId: 0,
      nextFresh: false,
      confirmLoading: false,
      getData: parameter => {
        return this.$http.get('/role/selectUserData', { params: Object.assign(parameter, { roleId: this.roleId, realName: this.realName, fullPath: this.fullPath }) })
          .then(res => {
            return res.data
          })
      }
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
    showModal (roleId, roleName) {
      if (this.nextFresh) {
        this.$refs.table.refresh(true)
      }
      this.nextFresh = false
      this.title = `添加用戶到${roleName}`
      this.visible = true
      this.roleId = roleId
    },
    onSelectChange (keys, obj) {
      this.selectedRowKeys = keys
      this.selectUser = obj
    },
    handleSubmit () {
      this.confirmLoading = true
      if (this.selectedRowKeys) {
        this.$http.post('/role/setUserRoles', { userIds: this.selectedRowKeys, roleIds: [this.roleId] })
          .then(res => {
            if (res.data && res.success) {
              this.visible = false
              this.$emit('ok', true)
              this.selectedRowKeys = []
              this.nextFresh = true
            } else {
              this.$message.error('设置角色失败')
            }
          }).finally(res => {
            this.confirmLoading = false
          })
      }
    }
  }
}
</script>
