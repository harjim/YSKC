<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="角色名称">
        <a-input v-model="queryParam.roleName" placeholder="请输入角色名称" style="width:180px" />
      </a-form-item>
      <span>
        <a-button v-if="$auth('sys:role:search')" type="primary" @click="search()">查询</a-button>
      </span>
    </a-form>
    <ystable
      queryUrl="/role/getAppRoleList"
      :params="queryParam"
      highlight-hover-row
      show-overflow
      resizable
      ref="table"
      @completed="completed"
      :toolbar="tableToolbar"
      @checkbox-all="selectAllEvent"
      @checkbox-change="selectChangeEvent"
    >
      <template v-slot:toolbar_buttons>
        <span style="margin-right:10px;">
          <a-button type="primary" v-if="$auth('sys:role:add')" @click="$refs.createModal.add()">添加</a-button>
        </span>
        <span style="margin-right::10px;">
          <a-button
            type="primary"
            v-if="$auth('sys:role:del')"
            @click="delRoleBatch"
            :disabled="selectedRowKeys.length <= 0"
          >删除</a-button>
        </span>
      </template>
      <vxe-table-column type="checkbox" width="50" align="center" fixed="left"></vxe-table-column>
      <vxe-table-column field="roleName" title="角色名称" width="150" align="left" remoteSort></vxe-table-column>
      <vxe-table-column field="remark" title="描述"></vxe-table-column>
      <vxe-table-column field="creatorName" title="创建人" width="150" align="center" remoteSort></vxe-table-column>
      <vxe-table-column field="createTime" title="创建时间" width="180" align="center" remoteSort></vxe-table-column>
      <vxe-table-column title="操作" width="250" align="center">
        <template v-slot="{ row }">
          <a @click="handleEdit(row)" v-if="$auth('sys:role:edit')">编辑</a>
          <a-divider type="vertical" v-if="$auth('sys:role:set')" />
          <a @click="openRoleModal(row)" v-if="$auth('sys:role:set')">配置</a>
          <a-divider type="vertical" v-if="$auth('sys:role:set')" />
          <a @click="$refs.userTabulation.showModal(row)" v-if="$auth('sys:role:userList')">用户列表</a>
          <a-divider type="vertical" v-if="$auth('sys:role:userList')" />
          <a-popconfirm v-if="$auth('sys:role:del')" title="是否确定删除?" @confirm="handleDel(row)">
            <a>删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </ystable>
    <role-modal ref="createModal" @ok="handleOk" />
    <set-role-modal ref="setRoleModal"></set-role-modal>
    <userRoleTabulationModal ref="userTabulation" @ok="handleOk"></userRoleTabulationModal>
  </a-card>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import RoleModal from './modules/RoleModal'
import SetRoleModal from './modules/SetRoleModal'
import UserRoleTabulationModal from './modules/UserRoleTabulationModal'
import ystable from '@/components/Table/ystable'
export default {
  name: 'RoleManage',
  components: {
    ystable,
    STable,
    RoleModal,
    SetRoleModal,
    Ellipsis,
    UserRoleTabulationModal
  },
  data () {
    return {
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      tableToolbar: {
        // perfect: true,
        refresh: true,
        zoom: true,
        custom: true,
        slots: {
          buttons: 'toolbar_buttons'
        }
      },
      selectedRowKeys: [],
      entityList: [],
      options: {}
    }
  },
  filters: {
  },
  created () {
  },
  methods: {
    search () {
      this.$refs.table.refresh(true)
    },
    handleEdit (record) {
      this.$refs.createModal.edit(record)
    },
    openRoleModal (record) {
      this.$refs.setRoleModal.set(record)
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.entityList = selectedRows
    },
    handleDel (record) {
      this.$http.post('/role/del', { roleId: record.id }).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
        }
      }).finally(res => {
        this.$refs.table.refresh(false)
      })
    },
    delRoleBatch () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的角色吗?',
        content: <b>已被用户关联的不会删除。</b>,
        onOk () {
          return self.$http.post('/role/delRoleBatch', self.entityList)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.selectedRowKeys = []
                self.entityList = {}
                self.$refs.table.refresh()
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
                self.selectedRowKeys = []
                self.entityList = {}
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.$refs.table.refresh(flag)
    },
    resetSearchForm () {
      this.queryParam = {
        date: moment(new Date())
      }
    },
    selectChangeEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
      this.entityList = records
    },
    selectAllEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
      this.entityList = records
    },
    completed () {
      this.selectedRowKeys = []
      this.selectUser = []
    }
  }
}
</script>
