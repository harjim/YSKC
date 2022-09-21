<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="名称">
        <a-input v-model="processName" placeholder="请输入名称" />
      </a-form-item>
      <span
        class="table-page-search-submitButtons"
        style="padding-top:5px;"
        v-if="$auth('doc:proc:search')"
      >
        <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
      </span>
      <span style="padding-left:20px;padding-top:5px;" v-if="$auth('doc:proc:add')">
        <a-button type="primary" @click="$refs.createModal.add()">添加</a-button>
      </span>
    </a-form>

    <s-table
      rowKey="id"
      ref="table"
      :loading="loading"
      size="default"
      :data="getData"
      :columns="columns"
      :dataSource="tableData"
    >
      <a-table
        slot="expandedRowRender"
        slot-scope="record"
        size="small"
        rowKey="id"
        :dataSource="record.templateListModels"
        :pagination="false"
      >
        <a-table-column title="序号" data-index="seq" align="center">
          <template slot-scope="text">
            <span>{{ text }}</span>
          </template>
        </a-table-column>
        <a-table-column title="模板名称" data-index="docName" align="center">
          <template slot-scope="text">
            <span>{{ text }}</span>
          </template>
        </a-table-column>
        <a-table-column title="版本" data-index="version" align="center">
          <template slot-scope="text">
            <span>{{ text }}</span>
          </template>
        </a-table-column>
        <a-table-column title="创建时间" data-index="createTime" align="center">
          <template slot-scope="text">
            <span>{{ text }}</span>
          </template>
        </a-table-column>
        <a-table-column title="最后更新时间" data-index="lastUpdateTime" align="center">
          <template slot-scope="text">
            <span>{{ text }}</span>
          </template>
        </a-table-column>
        <a-table-column title="操作" data-index="key" align="center">
          <template slot-scope="text,record2">
            <a-popconfirm title="是否确定删除?" @confirm="templateHandleDel(record2)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </a-table-column>
      </a-table>
      <span slot="action" slot-scope="text, record" align="center">
        <a @click="handleEdit(record)" v-if="$auth('doc:proc:edit')">编辑</a>
        <a-divider type="vertical" v-if="$auth('doc:proc:del')" />
        <a-popconfirm title="是否确定删除?" @confirm="handleDel(record)" v-if="$auth('doc:proc:del')">
          <a>删除</a>
        </a-popconfirm>
      </span>
    </s-table>
    <process-modal ref="createModal" @ok="handleOk"></process-modal>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import ProcessModal from './modules/ProcessModal'
const columns = [{
  title: '过程名称',
  dataIndex: 'processName',
  key: 'processName',
  align: 'left'
}, {
  title: '创建人',
  dataIndex: 'msCreatorId',
  key: 'msCreatorId',
  align: 'center'
}, {
  title: '创建时间',
  dataIndex: 'createTime',
  key: 'createTime',
  align: 'center'
}, {
  title: '最后更新时间',
  dataIndex: 'lastUpdateTime',
  key: 'lastUpdateTime',
  align: 'center'
}, {
  title: '操作',
  dataIndex: 'action',
  scopedSlots: { customRender: 'action' },
  align: 'center'
}]
export default {
  name: 'Process',
  components: {
    STable,
    ProcessModal
  },
  data () {
    return {
      processName: '',
      menuTree: [],
      menuId: 0,
      columns,
      pagination: [],
      getData: parameter => {
        return this.$http.get('/docProcess/queryDocProcessList', { params: Object.assign(parameter, { processName: this.processName }) })
          .then(res => {
            return res.data
          })
      },
      // 查询参数
      queryParam: {},
      tableData: [],
      visible: false,
      loading: false,
      form: this.$form.createForm(this, { scroll: {} })
    }
  },
  mounted () {
  },
  methods: {
    openUploadModal () {
      this.$refs.uploadModal.show()
    },
    handleDel (record) {
      this.$http.post('/docProcess/delProcessDocTemplate', { id: record.id }).then(res => {
        this.$message.success('删除成功')
      }).finally(res => {
        this.$refs.table.refresh(false)
      })
    },
    handleEdit (record) {
      this.$refs.createModal.edit(record)
    },
    templateHandleDel (record2) {
      this.$http.post('/docProcess/delProcessTemplate', { id: record2.id }).then(res => {
        this.$message.success('删除成功')
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
