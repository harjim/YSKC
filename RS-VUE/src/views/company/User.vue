<template>
  <a-card :bordered="false" :bodyStyle="{ height: '100%', overflow: 'auto' }">
    <a-form layout="inline">
      <a-form-item label="姓名">
        <a-input v-model="queryParam.realName" placeholder="请输入姓名" />
      </a-form-item>
      <a-form-item label="用户名">
        <a-input v-model="queryParam.userName" placeholder="请输入用户名" />
      </a-form-item>
      <a-form-item label="性别">
        <a-select
          showSearch
          v-model="queryParam.gender"
          :allowClear="true"
          placeholder="请选择性别"
          style="width:165px;"
        >
          <a-select-option value="-1">请选择</a-select-option>
          <a-select-option value="0">男</a-select-option>
          <a-select-option value="1">女</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="电话">
        <a-input v-model="queryParam.tel" placeholder="请输入电话" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" style="margin-right: 10px;" v-if="$auth('company:user:search')" @click="$refs.table.refresh(true)">查询</a-button>
      </a-form-item>
    </a-form>
    <div>
      <ystable
        queryUrl="/user/queryUserList"
        :params="queryParam"
        highlight-hover-row
        show-overflow
        resizable
        ref="table"
        auto-resize
        :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
      >
        <template v-slot:toolbar_buttons>
          <a-button style="margin-right: 10px" type="primary" v-if="$auth('company:user:add')" @click="$refs.createModal.add()">添加</a-button>
        </template>
        <vxe-table-column
          field="userName"
          title="用户名"
          width="120"
          min-width="120"
          align="left"
          remoteSort
          fixed="left"></vxe-table-column>
        <vxe-table-column
          field="realName"
          title="名称"
          width="120"
          min-width="120"
          align="left"
          fixed="left"
          remoteSort></vxe-table-column>
        <vxe-table-column field="gender" title="性别" width="80" align="center" remoteSort>
          <template v-slot="{ row }">
            {{ sexArr[row.gender] }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="tel"
          title="电话"
          width="150"
          min-width="150"
          align="center"
          remoteSort></vxe-table-column>
        <vxe-table-column
          field="email"
          title="邮箱"
          width="200"
          min-width="200"
          align="left"
          remoteSort></vxe-table-column>
        <vxe-table-column field="status" title="状态" width="80" align="center" remoteSort>
          <template v-slot="{ row }">
            {{ statuArr[row.status] }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="操作" min-width="250" align="center" fixed="right" >
          <template v-slot="{ row }">
            <a
              href="javascript:;"
              @click="$refs.createModal.edit(row)"
              v-if="$auth('company:user:edit')"
            >编辑</a>
            <a-divider type="vertical" />
            <a v-if="$auth('company:user:resettingPassword')" @click="handelAdd(row)">重置密码</a>
            <a-divider type="vertical" />
            <a @click="$refs.setRoleModal.showModal(row)">分配角色</a>
            <a-divider type="vertical" />
            <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)">
              <a v-if="$auth('company:user:del')">删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ystable>
    </div>
    <user-modal ref="createModal" @ok="handleOk"></user-modal>
    <resetUserPassword-modal ref="modal"></resetUserPassword-modal>
    <set-role-modal ref="setRoleModal"></set-role-modal>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable } from '@/components'
import UserModal from './modules/UserModal'
import ResetUserPasswordModal from './modules/ResetUserPasswordModal'
import SetRoleModal from './modules/SetRoleModal'
/// 0,启用  1 禁用
const statuArr = ['启用', '禁用']
const sexArr = ['男', '女']
export default {
  name: 'User',
  components: {
    STable,
    UserModal,
    ResetUserPasswordModal,
    SetRoleModal,
    ystable
  },
  data () {
    return {
      queryParam: {},
      statuArr,
      sexArr,
      scroll: {},
      form: this.$form.createForm(this)
    }
  },
  created () {
    if (document.body.clientWidth < 1600) {
      this.scroll = { x: 1260 }
    }
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1600) {
          this.scroll = { x: 1260 }
        }
      })()
    }
  },
  methods: {
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
        this.$refs.table.refresh(true)
      } else {
        this.$message.success('更新成功')
        this.$refs.table.refresh()
      }
    },
    handleDel (record) {
      this.$http.post('/user/delChilduser', { id: record.id }).then(res => {
        this.$message.success('删除成功')
      }).finally(res => {
        this.$refs.table.refresh()
      })
    },
    handelAdd (record) {
      this.$refs.modal.reset(record)
    }
  }
}
</script>

<style scoped>
</style>
