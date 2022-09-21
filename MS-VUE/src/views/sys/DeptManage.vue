<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="部门名称">
          <a-input v-model="queryParam.deptName" placeholder="请输入部门名称" style="width:180px" />
        </a-form-item>

        <span class="table-page-search-submitButtons">
          <a-button type="primary" @click="getDatas" v-if="$auth('sys:dept:search')">查询</a-button>
          <span>
            <a-button
              style="margin-left:10px"
              v-if="$auth('sys:dept:add')"
              @click="handelAdd()"
              type="primary"
            >添加</a-button>
          </span>
          <a-button
            type="primary"
            style="margin-left: 8px"
            @click="selsynDepts"
            v-if="$auth('sys:dept:synchronization')"
          >钉钉同步</a-button>
        </span>
      </a-form>
      <a-table
        rowKey="id"
        ref="table"
        size="default"
        :columns="columns"
        :dataSource="tableData"
        :pagination="false"
        :defaultExpandedRowKeys="defaultExpandedRowKeys"
      >
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-if="$auth('sys:dept:edit') && record.parentId !== -1">编辑</a>
          <a-divider type="vertical" v-if="$auth('sys:dept:edit') && record.parentId !== -1" />
          <a @click="$refs.userDeptModal.showModal(record)">用户列表</a>
          <a-divider type="vertical" v-if="$auth('sys:dept:del') && record.parentId !== -1" />
          <a-popconfirm
            title="是否确定删除?"
            @confirm="handleDel(record)"
            v-if="$auth('sys:dept:del') && record.parentId !== -1"
          >
            <a>删除</a>
          </a-popconfirm>
        </span>
      </a-table>
      <dept-modal ref="modal" @ok="handleOk"></dept-modal>
      <userDeptlist-modal ref="userDeptModal" @ok="handleOk"></userDeptlist-modal>
    </a-spin>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import DeptModal from './modules/DeptModal'
import UserDeptlistModal from './modules/UserDeptlistModal'
export default {
  components: {
    STable,
    DeptModal,
    UserDeptlistModal
  },
  data () {
    return {
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      tableData: [],
      visible: false,
      spinning: false,
      defaultExpandedRowKeys: [],
      // 表头
      columns: [
        {
          title: '组织机构层级',
          dataIndex: 'deptName',
          align: 'left',
          key: 'deptName'
        },
        {
          title: '部门主管',
          dataIndex: 'realName',
          align: 'left',
          key: 'realName'
        },
        {
          title: '备注',
          dataIndex: 'remark',
          align: 'left',
          key: 'remark'
        },
        {
          title: '操作',
          width: '350px',
          align: 'center',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' }
        }
      ],
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
  created () {
    this.getDatas()
  },

  methods: {
    handleEdit (record) {
      if (record.parentId === -1) {
        this.$message.info('不能修改根节点!')
      } else {
        this.$refs.modal.edit(record)
      }
    },
    handelAdd (record) {
      this.$refs.modal.add(record)
    },
    handleAddSN (record) {
      if (record.parentId === -1) {
        this.$message.info('不能添加与根节点同级的节点!')
      } else {
        this.$refs.modal.addSNDept(record)
      }
    },
    handleDel (record) {
      if (record.parentId === -1) {
        this.$message.info('不能删除根节点!')
      } else {
        this.$http.post('/dept/delDept', { id: record.id }).then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
          } else {
            this.$message.info(res.errorMessage)
          }
        }).finally(res => {
          this.getDatas()
        })
      }
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.getDatas()
    },
    getDatas () {
      this.spinning = true
      this.$http.get('/dept/queryDeptList', { params: this.queryParam })
        .then(res => {
          this.tableData = []
          if (res.data && res.data.length > 0) {
            this.tableData = res.data
            this.defaultExpandedRowKeys.push(res.data[0].id)
          }
          this.spinning = false
        })
    },
    selsynDepts () {
      this.spinning = true
      this.$http.get('/dept/selsynDept').then(res => {
        if (res.success) {
          this.$message.success('同步成功!')
          this.getDatas()
          this.spinning = false
        }
      })
    }

  }
}
</script>
