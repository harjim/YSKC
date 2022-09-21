<template>
  <a-card :bordered="false">
    <a-spin :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="技术需求名称">
          <a-input v-model="queryParam.techName" placeholder="请输入技术需求名称" style="width:180px" />
        </a-form-item>
        <a-form-item label="联系人">
          <a-input v-model="queryParam.linkName" placeholder="请输入联系人" style="width:180px" />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            @click="search(true)"
            v-if="$auth('rdorg:techRequirement:search')"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div class="table">
        <ystable
          ref="table"
          rowId="id"
          :columns="columns"
          :params="queryParam"
          queryUrl="/techRequirement/getList"
          :toolbar="{ custom: true, zoom:true, refresh:true }"
          max-height="100%"
        >
          <template v-slot:buttons>
            <span v-if="$auth('rdorg:techRequirement:add')">
              <a-button type="primary" @click="$refs.createModal.add(currentYear)">添加</a-button>
            </span>
          </template>
          <span slot="action" slot-scope="{row}">
            <a @click="detail(row)">详情</a>
            <template v-if="row.status !== 1">
              <span v-if="$auth('rdorg:techRequirement:edit')">
                <a-divider type="vertical" />
                <a @click="handleEdit(row)">编辑</a>
              </span>
              <span v-if="$auth('rdorg:techRequirement:invalid')">
                <a-divider type="vertical" />
                <a-popconfirm title="是否确定作废?" @confirm="handleInvalid(row)">
                  <a>作废</a>
                </a-popconfirm>
              </span>
            </template>
            <span v-if="$auth('rdorg:techRequirement:del')">
              <a-divider type="vertical" />
              <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)">
                <a>删除</a>
              </a-popconfirm>
            </span>
          </span>
        </ystable>
      </div>
    </a-spin>
    <tech-requirement-modal ref="createModal" @ok="handleOk" />
  </a-card>
</template>
<script>
import ystable from '@/components/Table/ystable'
import TechRequirementModal from './modules/TechRequirementModal'
import yearMixin from '@/utils/yearMixin'
const statusArr = ['正常', '作废']
export default {
  name: 'TechRequrement',
  components: {
    TechRequirementModal,
    ystable
  },
  mixins: [yearMixin],
  data () {
    return {
      spinning: false,
      queryParam: {
        year: this.currentYear
      },
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
          minWidth: 160,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip',
          fixed: 'left'
        }, {
          title: '拟投资金额(万元)',
          field: 'investment',
          align: 'right',
          width: 160,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '联系人',
          field: 'linkName',
          align: 'left',
          width: 110,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '联系号码',
          field: 'linkTel',
          align: 'center',
          width: 120,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '职位',
          field: 'position',
          align: 'left',
          width: 120,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
        }, {
          title: '邮箱',
          field: 'linkEmail',
          align: 'left',
          width: 120,
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
          title: '创建时间',
          field: 'createTime',
          align: 'center',
          width: 160,
          remoteSort: true,
          showHeaderOverflow: true,
          showOverflow: 'tooltip'
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
          width: 190,
          align: 'left',
          slots: { default: 'action' },
          fixed: 'right'
        }]
    }
  },
  created () {
    this.queryParam.year = this.currentYear
  },
  methods: {
    search (refresh) {
      this.spinning = true
      try {
        this.queryParam.year = this.currentYear
        this.$refs.table.refresh(refresh)
      } finally {
        this.spinning = false
      }
    },
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
        this.search(false)
      })
    },
    handleInvalid (record) {
      this.$http.post('/techRequirement/invalid', { ids: [record.id] }).then(res => {
        this.$message.success('作废成功')
      }).finally(res => {
        this.search(false)
      })
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.search(true)
    }
  }
}
</script>

<style lang="less" scoped>

/deep/ .ant-spin-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 220px);

}
/deep/ .ant-form-inline {
  height: auto;
  flex-shrink: 0;
}

/deep/ .table {
  flex: 1;
  overflow-y: hidden;
}

</style>
