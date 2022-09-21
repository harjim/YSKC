<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input v-model="queryParam.companyName" placeholder="请输入客户名称" style="width:180px" />
      </a-form-item>
      <a-form-item label="技术需求名称">
        <a-input v-model="queryParam.techName" placeholder="请输入技术需求名称" style="width:180px" />
      </a-form-item>
      <a-form-item label="联系人">
        <a-input v-model="queryParam.linkName" placeholder="请输入联系人" style="width:180px" />
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          @click="$refs.table.refresh(true)"
          v-if="$auth('customer:requirement:search')"
        >查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      rowId="id"
      :columns="columns"
      :params="queryParam"
      queryUrl="/techRequirement/getList"
      :toolbar="{
        custom: true,
        zoom:true,
        refresh:true
      }"
    >
      <template v-slot:buttons>
        <span v-if="$auth('customer:requirement:add')">
          <a-button type="primary" @click="$refs.createModal.add()">添加</a-button>
        </span>
      </template>
      <span slot="action" slot-scope="{row}" fixed="right">
        <a @click="detail(row)">详情</a>
        <template v-if="row.status !== 1">
          <span v-if="$auth('customer:requirement:edit')">
            <a-divider type="vertical" />
            <a @click="handleEdit(row)">编辑</a>
          </span>
          <span v-if="$auth('customer:requirement:invalid')">
            <a-divider type="vertical" />
            <a-popconfirm title="是否确定作废?" @confirm="handleInvalid(row)">
              <a>作废</a>
            </a-popconfirm>
          </span>
        </template>
        <span v-if="$auth('customer:requirement:del')">
          <a-divider type="vertical" />
          <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)">
            <a>删除</a>
          </a-popconfirm>
        </span>
      </span>
    </ystable>
    <tech-requirement-modal ref="createModal" @ok="handleOk" />
  </a-card>
</template>
<script>
import ystable from '@/components/Table/ystable'
import TechRequirementModal from './modules/TechRequirementModal'
const statusArr = ['正常', '作废']
export default {
  name: 'TechRequrement',
  components: {
    TechRequirementModal,
    ystable
  },
  data () {
    return {
      queryParam: {},
      // 表头
      columns: [
        {
          type: 'seq',
          width: 50,
          title: '序号',
          fixed: 'left'
        }, {
          title: '技术需求名称',
          field: 'techName',
          align: 'left',
          width: 200,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          fixed: 'left'
        }, {
          title: '客户名称',
          field: 'companyName',
          align: 'left',
          width: 200,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '注册地',
          field: 'companyAddress',
          align: 'left',
          width: 200,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '年份',
          field: 'year',
          align: 'left',
          width: 80,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        },
        {
          title: '拟投资金额(万元)',
          field: 'investment',
          align: 'right',
          width: 160,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '状态',
          field: 'status',
          align: 'center',
          width: 80,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          slots: { default: ({ row }) => { return statusArr[row.status] } }
        }, {
          title: '创建人',
          field: 'createName',
          align: 'left',
          width: 100,
          slots: { default: ({ row }) => { return row.createName ? row.createName : '-' } }
        }, {
          title: '创建时间',
          field: 'createTime',
          align: 'center',
          width: 160,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '更新人',
          field: 'updateName',
          align: 'left',
          width: 100,
          slots: { default: ({ row }) => { return row.updateName ? row.updateName : '-' } }
        }, {
          title: '更新时间',
          field: 'lastUpdateTime',
          align: 'center',
          width: 160,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '操作',
          field: 'action',
          width: 200,
          align: 'left',
          slots: { default: 'action' },
          fixed: 'right'
        }]
    }
  },
  methods: {
    handleEdit (record) {
      this.$refs.createModal.edit(record)
    },
    detail (record) {
      this.$refs.createModal.detail(record)
    },
    handleDel (record) {
      this.$http.post('/techRequirement/del', { ids: [record.id] }).then(res => {
        this.$message.success('删除成功')
      }).finally(res => {
        this.$refs.table.refresh(false)
      })
    },
    handleInvalid (record) {
      this.$http.post('/techRequirement/invalid', { ids: [record.id] }).then(res => {
        this.$message.success('作废成功')
      }).finally(res => {
        this.$refs.table.refresh(false)
      })
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.$refs.table.refresh(flag)
    }
  }
}
</script>
