<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="角色名称">
        <a-input v-model="queryParam.roleName" style="width:180px;" placeholder="请输入角色名称" />
      </a-form-item>
      <a-form-item label="客户名称">
        <a-input placeholder="请输入客户名称" v-model="queryParam.companyName" />
      </a-form-item>
      <a-form-item>
        <a-button
          v-if="$auth('customer:companyrole:search')"
          type="primary"
          @click="search(true)"
        >查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      rowId="id"
      :columns="columns"
      :params="queryParam"
      queryUrl="/rsRole/getList"
      :toolbar="{
        custom: true,
        zoom:true,
        refresh:true
      }"
    >
      <template v-slot:buttons>
        <span>
          <a-button
            type="primary"
            v-if="$auth('customer:companyrole:add')"
            @click="$refs.companyRoleModal.add()"
          >添加</a-button>
        </span>
      </template>

      <span slot="action" slot-scope="{row}">
        <a @click="$refs.companyRoleModal.edit(row)" v-if="$auth('customer:companyrole:edit')">编辑</a>
        <a-divider type="vertical" v-if="$auth('customer:companyrole:collocation')" />
        <a
          @click="$refs.setCompanyRoleModal.showModal(row)"
          v-if="$auth('customer:companyrole:collocation')"
        >配置</a>
        <a-divider type="vertical" v-if="$auth('customer:companyrole:del')" />
        <a-popconfirm
          v-if="$auth('customer:companyrole:del')"
          title="是否确定删除?"
          @confirm="handleDel(row)"
        >
          <a>删除</a>
        </a-popconfirm>
        <a-divider type="vertical" v-if="$auth('customer:companyrole:assignedCustomers')" />
        <a
          @click="$refs.assignedCustomersModal.showModal(row)"
          v-if="$auth('customer:companyrole:assignedCustomers')"
        >已分配客户</a>
        <!--
        <a-divider type="vertical" v-if="$auth('customer:companyrole:servicePersonnel')" />
       <a
          @click="$refs.servicePersonalModal.showModal(row)"
          v-if="$auth('customer:companyrole:servicePersonnel')"
        >服务人员列表</a> -->
      </span>
    </ystable>
    <company-role-modal ref="companyRoleModal" @ok="handleOk"></company-role-modal>
    <set-company-role-modal ref="setCompanyRoleModal" @ok="setHandleOk"></set-company-role-modal>
    <assigned-customers-modal ref="assignedCustomersModal"></assigned-customers-modal>
    <service-personal-modal ref="servicePersonalModal"></service-personal-modal>
  </a-card>
</template>
<script>
import ystable from '@/components/Table/ystable'
import CompanyRoleModal from './modules/CompanyRoleModal'
import SetCompanyRoleModal from './modules/SetCompanyRoleModal'
import AssignedCustomersModal from './modules/AssignedCustomersModal'
import ServicePersonalModal from './modules/ServicePersonalModal'
export default {
  name: 'ComapnyRole',
  components: {
    ystable,
    CompanyRoleModal,
    SetCompanyRoleModal,
    AssignedCustomersModal,
    ServicePersonalModal
  },
  data () {
    return {
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [{
        type: 'seq',
        width: 50,
        title: '序号'
      }, {
        title: '角色名称',
        field: 'roleName',
        key: 'roleName',
        align: 'left',
        remoteSort: true,
        showHeaderOverflow: true,
        showOverflow: 'tooltip'
      }, {
        title: '客户名称',
        field: 'companyName',
        key: 'companyName',
        align: 'left',
        remoteSort: true,
        showHeaderOverflow: true,
        showOverflow: 'tooltip'
      }, {
        title: '描述',
        field: 'remark',
        key: 'remark',
        align: 'left',
        showHeaderOverflow: true,
        showOverflow: 'tooltip'
      }, {
        title: '创建人',
        field: 'userName',
        key: 'userName',
        align: 'left',
        showHeaderOverflow: true,
        showOverflow: 'tooltip'
      }, {
        title: '创建时间',
        field: 'createTime',
        key: 'createTime',
        align: 'center',
        remoteSort: true,
        showHeaderOverflow: true,
        showOverflow: 'tooltip'
      }, {
        title: '操作',
        field: 'action',
        slots: { default: 'action' },
        showHeaderOverflow: true,
        showOverflow: 'tooltip',
        fixed: 'right'
      }],
      selectedRowKeys: [],
      selectedRows: [],
      options: {
        alert: { show: true, clear: () => { this.selectedRowKeys = [] } },
        rowSelection: {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange
        }
      },
      optionAlertShow: false
    }
  },
  filters: {
  },
  methods: {
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    handleDel (record) {
      this.$http.post('/rsRole/del', record).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.errorMessage)
        }
      }).finally(res => {
        this.search(false)
      })
    },
    setHandleOk (flag) {
      this.search(false)
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.search(flag)
    }
  }
}
</script>
