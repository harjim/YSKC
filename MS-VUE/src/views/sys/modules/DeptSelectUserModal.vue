<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    :maskClosable="false"
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
  width: 110
}, {
  dataIndex: 'realName',
  key: 'realName',
  title: '姓名',
  align: 'left',
  width: 100
}, {
  title: '电话',
  dataIndex: 'tel',
  key: 'tel',
  align: 'left',
  width: 120
}, {
  title: '职位',
  dataIndex: 'postion',
  key: 'postion',
  align: 'left',
  width: 110
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
      deptId: 0,
      deptIds: [],
      getData: parameter => {
        return this.$http.get('/dept/getSelectUserData', { params: Object.assign(parameter, { deptId: this.deptId, realName: this.realName, fullPath: this.fullPath }) })
          .then(res => {
            return res.data
          })
      }
    }
  },
  watch: {
    deptId () {
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
    }
  },
  methods: {
    showModal (deptId, deptName) {
      this.title = `添加用戶到${deptName}`
      this.visible = true
      this.deptId = deptId
    },
    onSelectChange (keys, obj) {
      this.selectedRowKeys = keys
      this.selectUser = obj
    },
    handleSubmit () {
      this.spinning = true
      if (this.selectedRowKeys) {
        this.$http.post('/dept/deptAddUsers', { userIds: this.selectedRowKeys, deptId: this.deptId })
          .then(res => {
            if (res.data && res.success) {
              this.visible = false
              this.$message.success('添加用户成功')
              this.$emit('ok', true)
              this.selectedRowKeys = []
            } else {
              this.$message.error('部门添加用户失败')
            }
          }).finally(res => {
            this.spinning = false
          })
      }
    }
  }
}
</script>
