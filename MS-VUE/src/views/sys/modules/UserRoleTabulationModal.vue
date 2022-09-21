<template>
  <a-modal
    :width="1200"
    :visible="visible"
    :title="title"
    @cancel="visible = false"
    :maskClosable="false"
    :footer="null"
  >
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
          style="width:180px;"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
      </a-form-item>
    </a-form>
    <div>
      <ystable
        queryUrl="/role/getUserByRoleId"
        :params="queryParam"
        highlight-hover-row
        show-overflow
        resizable
        ref="table"
        :toolbar="tableToolbar"
        @checkbox-all="selectAllEvent"
        @checkbox-change="selectChangeEvent"
      >
        <template v-slot:toolbar_buttons>
          <span style="margin-right:10px;">
            <a-button type="primary" @click="$refs.selectModal.showModal(roleId,roleName)">添加</a-button>
          </span>
          <span style="margin-right:10px;">
            <a-button type="primary" :disabled="selectedRowKeys.length <= 0" @click="$refs.deptSelectModal.open()">设置服务部门</a-button>
          </span>
          <span style="margin-right:10px;">
            <a-button type="primary" :disabled="selectedRowKeys.length <= 0" @click="delUser">删除</a-button>
          </span>
        </template>
        <vxe-table-column type="checkbox" width="50" align="center"></vxe-table-column>
        <vxe-table-column field="userName" title="用户名" width="150" align="center"></vxe-table-column>
        <vxe-table-column field="realName" title="姓名" width="120" align="right"></vxe-table-column>
        <vxe-table-column field="status" width="80" title="状态" align="center" remoteSort>
          <template v-slot="{ row }">
            {{ statusData[row.status] }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="deptName" title="所属部门" width="290" align="left"></vxe-table-column>
        <vxe-table-column field="serviceDeptNames" title="服务部门" width="290" align="left"></vxe-table-column>
      </ystable>
    </div>
    <selectUserModal ref="selectModal" @ok="handleOk"></selectUserModal>
    <deptSelectModal ref="deptSelectModal" @ok="setServiceDepts"></deptSelectModal>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, Ellipsis } from '@/components'
import DeptSelectOnly from '@/components/DeptSelectOnly'
import SelectUserModal from './SelectUserModal'
import DeptSelectModal from './DeptSelectModal'
export default {
  name: 'TabulationByRoleId',
  components: {
    STable,
    Ellipsis,
    SelectUserModal,
    DeptSelectOnly,
    ystable,
    DeptSelectModal
  },
  data () {
    return {
      realName: '',
      title: '用戶列表',
      roleName: undefined,
      visible: false,
      selectedRowKeys: [],
      selectUser: [],
      statusData: {
        0: '启用',
        1: '禁用'
      },
      queryParam: {},
      form: this.$form.createForm(this),
      roleId: 0,
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
  methods: {
    showModal (record) {
      this.title = `[${record.roleName}]用戶列表`
      this.visible = true
      this.roleId = record.id
      this.roleName = record.roleName
      this.queryParam.roleId = record.id
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加用户成功')
      }
      this.$refs.table.refresh(flag)
    },
    onSelectChange (keys, obj) {
      this.selectedRowKeys = keys
      this.selectUser = obj
    },
    setServiceDepts (deptIds) {
      const pData = {
        roleId: this.roleId,
        userIds: this.selectUser.map(a => a.id),
        deptStr: deptIds.join(',')
      }
      this.spinning = true
      this.$http.post('/role/updateServiceDept', pData)
        .then(res => {
          this.selectedRowKeys = []
          this.selectUser = []
          this.$refs.table.refresh()
        })
        .finally(() => {
          this.spinning = false
        })
    },
    delUser () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的用户吗?',
        onOk () {
          self.selectUser.roleId = self.roleId
          return self.$http.post('/role/deleteBatchUser', self.selectUser)
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
    },
    selectChangeEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
      this.selectUser = records
    },
    selectAllEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
      this.selectUser = records
    }
  }

}
</script>
