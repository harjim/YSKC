<template>
  <a-card :bordered="false" :bodyStyle="{ height: '100%', overflow: 'auto' }">
    <a-form layout="inline">
      <a-form-item label="工艺线/车间名称">
        <a-input v-model="queryParam.workshopName" placeholder="请输入名称" />
      </a-form-item>
      <span class="table-page-search-submitButtons">
        <a-button type="primary" v-if="$auth('company:workshop:search')" @click="initializeData">查询</a-button>
      </span>

      <span style="padding-left:20px;">
        <a-button
          type="primary"
          v-if="$auth('company:workshop:add')"
          @click="$refs.create.adds()"
        >添加</a-button>
      </span>
      <span style="padding-left:20px;">
        <a-button
          type="primary"
          v-if="$auth('company:workshop:import')"
          @click="$refs.uploadModal.show(tableField)"
        >导入</a-button>
      </span>
    </a-form>
    <div>
      <a-table
        rowKey="id"
        ref="table"
        size="default"
        :columns="columns"
        :dataSource="tableData"
        :pagination="false"
        :defaultExpandedRowKeys="defaultExpandedRowKeys"
      >
        <template slot="workshopName" slot-scope="text,record">
          <span>{{ record.seq + '、' +text }}</span>
        </template>
        <template slot="remark" slot-scope="text">
          <ellipsis :length="10" tooltip>{{ text }}</ellipsis>
        </template>
        <span slot="action" slot-scope="text, record">
          <span v-if="$auth('company:workshop:edit')">
            <a @click="$refs.create.edit(record)">编辑</a>
          </span>
          <span v-if="$auth('company:workshop:add')">
            <a-divider type="vertical" v-if="$auth('company:workshop:edit')" />
            <a @click="$refs.create.add(record)">添加下级工艺线/车间</a>
          </span>
          <span v-if="$auth('company:workshop:del')">
            <a-divider
              type="vertical"
              v-if="$auth('company:workshop:add') || $auth('company:workshop:edit') "
            />
            <a-popconfirm title="是否确定删除?" @confirm="handleDel(record)">
              <a>删除</a>
            </a-popconfirm>
          </span>
        </span>
      </a-table>
    </div>
    <workshop-modal ref="create" @ok="handleOk"></workshop-modal>
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      paramKey="tableField"
      title="导入工艺线/车间"
      templateName="工艺线车间模板"
      ref="uploadModal"
      action="/workshop/importWorkshop"
      @onSuccess="success"
      @error="error"
    />
  </a-card>
</template>
<script>
import WorkshopModal from './modules/WorkshopModal'
import { UploadModal, Ellipsis } from '@/components'
export default {
  components: {
    WorkshopModal,
    UploadModal,
    Ellipsis
  },
  data () {
    return {
      queryParam: {},
      tableData: [],
      defaultExpandedRowKeys: [],
      columns: [
        {
          title: '工艺线/车间',
          dataIndex: 'workshopName',
          key: 'workshopName',
          scopedSlots: { customRender: 'workshopName' }
        },
        {
          title: '备注',
          dataIndex: 'remark',
          width: '30%',
          key: 'remark',
          scopedSlots: { customRender: 'remark' }
        },
        {
          title: '操作',
          width: '320px',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      tableField: {
        tableId: 'deptTable',
        fieldTitleObject: {
          seq: { title: '序号', defaultTitle: '序号', importField: true },
          workshopName: { title: '工艺线/车间', required: true, defaultTitle: '工艺线/车间', importField: true },
          remark: { title: '备注', defaultTitle: '备注', importField: true }
        }
      },
      sampleData: [
        {
          seq: '1',
          workshopName: '集团总部/测试中心',
          remark: '备注:多级部门用/区分，上级部门在前面。'
        }
      ]
    }
  },
  created () {
    this.initializeData()
  },
  methods: {
    initializeData () {
      this.$http.get('/workshop/queryWorkshop', { params: this.queryParam })
        .then(res => {
          if (res.success && res.data && res.data.length > 0) {
            this.defaultExpandedRowKeys.push(res.data[0].id)
          }
          this.spinning = false
          this.tableData = res.data
          return res.data
        })
    },
    handleDel (record) {
      this.$http.post('/workshop/delWorkshop', { id: record.id }).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
        }
      }).finally(res => {
        this.initializeData()
      })
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.initializeData()
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          content: resData,
          onOk () {

          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
      }
      this.initializeData()
    }
  }
}
</script>
<style scoped>
</style>
