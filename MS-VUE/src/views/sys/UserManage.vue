<template>
  <a-layout-content class="dept_user">
    <a-card :bordered="false">
      <a-form layout="inline" class="dept_user_search" :form="form">
        <a-form-item label="姓名">
          <a-input v-model="queryParam.realName" placeholder="请输入姓名" />
        </a-form-item>
        <a-form-item label="状态">
          <a-select
            placeholder="请选择状态"
            v-model="queryParam.status"
            style="width: 130px"
            :allowClear="true"
          >
            <a-select-option key="0">启用</a-select-option>
            <a-select-option key="1">禁用</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="所属部门">
          <dept-select-only
            ref="deptSelect"
            @deptFullPath="path=>queryParam.fullPath = path"
            style="width:165px;"
          />
        </a-form-item>
        <a-form-item>
          <span v-if="$auth('sys:user:search')">
            <a-button type="primary" @click="search(true)">查询</a-button>
          </span>
        </a-form-item>
      </a-form>
      <!-- 用户列表 -->
      <ystable
        queryUrl="/dept/getUser"
        :params="queryParam"
        highlight-hover-row
        show-overflow
        resizable
        @completed="completed"
        ref="table"
        :toolbar="tableToolbar"
        @checkbox-all="selectAllEvent"
        @checkbox-change="selectChangeEvent"
      >
        <template v-slot:toolbar_buttons>
          <span style="margin-right:10px;" v-if="$auth('sys:user:add')">
            <a-button type="primary" @click="$refs.createModal.add(deptTree)">添加员工</a-button>
          </span>
          <span style="margin-right:10px;" v-if="$auth('sys:user:synchronization')">
            <a-button type="primary" @click="updateDing()">钉钉同步</a-button>
          </span>
          <span style="margin-right:10px;" v-if="$auth('sys:user:setUserRole')">
            <a-button
              type="primary"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.assignRoleModal.setRoles(selectedRowKeys)"
            >设置角色</a-button>
          </span>
          <span style="margin-right:10px;" v-if="$auth('sys:user:edit')">
            <a-button
              type="primary"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.setDeptModal.add(selectUser,deptTree)"
            >设置部门</a-button>
          </span>
        </template>
        <vxe-table-column type="checkbox" width="50" align="center" fixed="left"></vxe-table-column>
        <vxe-table-column
          field="userName"
          width="100"
          title="用户名"
          align="right"
          fixed="left"
          remoteSort/>
        <vxe-table-column
          field="realName"
          width="100"
          title="姓名"
          align="right"
          fixed="left"
          remoteSort/>
        <vxe-table-column field="deptName" width="180" title="所属部门" align="left" remoteSort></vxe-table-column>
        <vxe-table-column field="tel" width="120" title="电话" align="center" remoteSort></vxe-table-column>
        <vxe-table-column field="postion" width="150" title="职位" align="left" remoteSort></vxe-table-column>
        <vxe-table-column field="relatedPermissions" width="200" title="关联权限" align="left" remoteSort></vxe-table-column>
        <vxe-table-column field="status" width="80" title="状态" align="center" remoteSort>
          <template v-slot="{ row }">
            {{ statusData[row.status] }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="remark" title="备注" width="200" align="left" remoteSort></vxe-table-column>
        <vxe-table-column title="操作" width="320" fixed="right" align="left">
          <template v-slot="{ row }">
            <a
              href="javascript:;"
              @click="$refs.createModal.edit(row,deptTree)"
              v-if="$auth('sys:user:edit')"
            >编辑</a>
            <a-divider type="vertical" />
            <a
              href="javascript:;"
              @click="$refs.resetPassword.showReset(row.id,row.userName)"
              v-if="$auth('sys:user:resetPasseord')"
            >重置密码</a>
            <a-divider type="vertical" />
            <a @click.prevent="$refs.assignRoleModal.assign(row)">分配角色</a>
            <a-divider type="vertical" />
            <a-popconfirm
              title="是否启用用户?"
              @confirm="editStatus(row)"
              v-if="$auth('sys:user:enabled')&&row.status===1"
            >
              <a>启用</a>
            </a-popconfirm>
            <a-popconfirm
              title="是否禁用用户?"
              @confirm="editStatus(row)"
              v-if="$auth('sys:user:disabled')&&row.status===0"
            >
              <a>禁用</a>
            </a-popconfirm>
            <a-divider type="vertical" />
            <a-popconfirm
              title="是否确定删除?"
              @confirm="handleDel(row)"
              v-if="$auth('sys:user:del')"
            >
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ystable>
    </a-card>
    <user-manage-modal ref="createModal" @ok="handleOk"></user-manage-modal>
    <reset-password ref="resetPassword"></reset-password>
    <assign-role-modal ref="assignRoleModal" @ok="assignOk"></assign-role-modal>
    <set-dept-modal ref="setDeptModal" @ok="setDeptOk"></set-dept-modal>
  </a-layout-content>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, Ellipsis } from '@/components'
import userManageModal from './modules/userManageModal'
import resetPassword from './modules/ResetPassword'
import assignRoleModal from './modules/AssignRoleModal'
import setDeptModal from './modules/SetDeptModal'
import DeptSelectOnly from '@/components/DeptSelectOnly'
// 用户表格
export default {
  name: 'UserManage',
  components: {
    ystable,
    STable,
    Ellipsis,
    userManageModal,
    resetPassword,
    assignRoleModal,
    setDeptModal,
    DeptSelectOnly
  },
  data () {
    return {
      selectedRowKeys: [],
      selectUser: [],
      spinning: false,
      expandedKeys: [],
      searchValue: '',
      autoExpandParent: true,
      realname_q: '',
      deptTree: [],
      dataList: [],
      form: this.$form.createForm(this),
      fullPath: undefined,
      statusData: {
        0: '启用',
        1: '禁用'
      },
      queryParam: {},
      tableToolbar: {
        // perfect: true,
        refresh: true,
        zoom: true,
        custom: true,
        slots: {
          buttons: 'toolbar_buttons'
        }
      }
    }
  },
  created () {
    this.loadTree()
  },
  methods: {
    search (refresh) {
      this.selectedRowKeys = []
      this.selectUser = []
      this.$refs.table.refresh(refresh)
    },
    updateDing () {
      this.spinning = true
      this.$http.get('/dept/selsynDept').then(res => {
        if (res.success && res.data) {
          this.$message.success('同步成功')
        } else {
          this.$message.info(res.errorMessage)
        }
      }).finally(res => {
        this.spinning = false
        this.search(true)
      })
    },
    setDeptOk (flag) {
      if (flag) {
        this.$message.success('设置成功')
      }
      this.search(!flag)
    },
    assignOk (flag) {
      if (flag) {
        this.selectedRowKeys = []
        this.$refs.table.refresh(false)
      }
      this.$message.success('分配成功')
    },
    assignRole (record) {
      this.$refs.assignRoleModal.assign(record)
    },
    loadTree () {
      this.$http.get('/dept/tree')
        .then(res => {
          this.deptTree = res.data
        })
    },
    handleDel (record) {
      this.$http.get('/dept/delUser', { params: { id: record.id } })
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.search(false)
          } else {
            this.$message.info(res.errorMessage)
          }
        })
    },
    editStatus (record) {
      this.$http.get('/user/editStatus', { params: { id: record.id, status: record.status } })
        .then(res => {
          if (res.success && res.data) {
            if (record.status === 0) {
              this.$message.success('禁用成功')
            }
            if (record.status === 1) {
              this.$message.success('启用成功')
            }
            this.search(false)
          } else {
            this.$message.info(res.errorMessage)
          }
        })
    },
    handleOk (ok) {
      this.search(ok)
    },
    generateList (data) {
      for (let i = 0; i < data.length; i++) {
        const node = data[i]
        const key = node.key
        this.dataList.push({ key, title: node.title, scopedSlots: { title: 'title' } })
        if (node.children) {
          this.generateList(node.children)
        }
      }
    },
    selectChangeEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
      this.selectUser = records
    },
    selectAllEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
      this.selectUser = records
    },
    completed () {
      this.selectedRowKeys = []
      this.selectUser = []
    }
  }

}

</script>
<style lang="less" scoped>
.dept {
  display: block;
  .dept_tree {
    width: 20%;
    height: 720px;
    background-color: #fff;
    float: left;
  }
  .dept_user {
    width: 75%;
    margin-left: 3%;
    min-height: 720px;
    background-color: #fff;
    float: left;
  }
  .dept_tree_span {
    float: left;
    margin-left: 30px;
  }
  .dept_user_search {
    padding: 0;
    margin: 0;
    margin-top: 20px;
    margin-left: 40px;
  }
}
</style>
