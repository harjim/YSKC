<template>
  <a-card :bordered="false">
    <div>
      <a-form layout="inline">
        <a-form-item label="姓名">
          <a-input v-model="queryParam.selectUserName" placeholder="请输入姓名" />
        </a-form-item>
        <a-form-item label="用户名">
          <a-input v-model="queryParam.userName" placeholder="请输入用户名" />
        </a-form-item>
        <a-form-item label="性别">
          <a-select showSearch v-model="queryParam.gender">
            <a-select-option value="-1">请选择</a-select-option>
            <a-select-option value="0">男</a-select-option>
            <a-select-option value="1">女</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="电话">
          <a-input v-model="queryParam.tel" placeholder="请输入电话" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
        </a-form-item>
      </a-form>
    </div>
    <ystable
      ref="table"
      size="default"
      rowId="id"
      queryUrl="/rsUser/queryUserList"
      :params="queryParam"
      :toolbar="{ zoom: true, custom: true, refresh: true}"
    >
      <template v-slot:buttons>
        <span>
          <a-button type="primary" @click="$refs.createModal.add(customer.companyId)">添加</a-button>
        </span>
      </template>
      <vxe-table-column field="userName" :width="120" title="用户名" align="left"></vxe-table-column>
      <vxe-table-column field="realName" :width="120" title="姓名" align="left"></vxe-table-column>
      <vxe-table-column field="gender" :width="70" title="性别" align="left">
        <template v-slot="{ row }">{{ sexArr[row.gender] }}</template>
      </vxe-table-column>
      <vxe-table-column
        field="tel"
        :width="160"
        title="电话"
        show-header-overflow
        show-overflow="tooltip"
        align="left"
      ></vxe-table-column>
      <vxe-table-column
        field="email"
        :width="200"
        title="邮箱"
        show-header-overflow
        show-overflow="tooltip"
        align="left"
      ></vxe-table-column>
      <vxe-table-column field="roleType" :width="80" title="角色类型" align="left">
        <template v-slot="{ row }">{{ roleArr[row.roleType] }}</template>
      </vxe-table-column>
      <vxe-table-column field="status" :width="80" title="状态" align="left">
        <template v-slot="{ row }">{{ statuArr[row.status] }}</template>
      </vxe-table-column>
      <vxe-table-column
        field="action"
        title="操作"
        align="left"
        :width="200"
        show-header-overflow
        show-overflow="tooltip"
      >
        <template v-slot="{ row }">
          <a href="javascript:;" @click="$refs.createModal.edit(row)">编辑</a>
          <a-divider type="vertical" />

          <a @click="handelAdd(row)">重置密码</a>
          <a-divider type="vertical" />
          <a @click="setRole(row)">分配角色</a>
          <span v-if="row.type===1">
            <a-divider type="vertical" />
            <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)">
              <a>删除</a>
            </a-popconfirm>
          </span>
        </template>
      </vxe-table-column>
    </ystable>
    <user-modal ref="createModal" @ok="handleOk"></user-modal>
    <resetUserPassword-modal ref="passwordModal"></resetUserPassword-modal>
    <userRoleSelect-modal ref="setRoleModal"></userRoleSelect-modal>
    <user-role-son-modal ref="userRoleSonModal"></user-role-son-modal>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import UserModal from './UserModal'
import ResetUserPasswordModal from './ResetUserPasswordModal'
import UserRoleSelectModal from './UserRoleSelectModal'
import UserRoleSonModal from './UserRoleSonModal'
export default {
  name: 'AccountingManagementModal',
  components: {
    ystable,
    UserModal,
    ResetUserPasswordModal,
    UserRoleSelectModal,
    UserRoleSonModal
  },
  props: {
    customer: {
      type: Object,
      required: true
    },
    roleRefresh: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      sexArr: ['男', '女'],
      statuArr: ['启用', '禁用'],
      roleArr: ['工厂', '区域'],
      form: this.$form.createForm(this),
      queryParam: { gender: '-1', companyId: this.customer.companyId },
      title: '',
      visible: false,
      confirmLoading: false
    }
  },
  watch: {
    customer (cid) {
      this.queryParam.companyId = this.customer.companyId
    }
  },
  methods: {
    add (record) {
      this.title = '帐户管理'
      this.isCreat = true
      this.isShowOrHide = true
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.$refs.table.refresh(true)
    },
    handleDel (record) {
      this.$http.post('/rsUser/delUser', { id: record.id }).then(res => {
        this.$message.success('删除成功')
      }).finally(res => {
        this.$refs.table.refresh(true)
      })
    },
    handelAdd (record) {
      this.$refs.passwordModal.reset(record)
    },
    setRole (record) {
      // 若当前公司不是一个集团，则保持已有设计
      if (![1, 3].includes(this.customer.companyType)) {
        this.$refs.setRoleModal.showModal(record, this.customer, this.roleRefresh)
      } else {
        this.$refs.userRoleSonModal.showModal(record, this.customer, this.roleRefresh)
      }
    }
  }
}
</script>
