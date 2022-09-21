<template>
  <a-card :bordered="false" :bodyStyle="{ height: '100%', overflow: 'auto' }" style="height: 100%">
    <a-tabs v-model="actvieKey" @change="callback" id="tabs">
      <a-tab-pane :tab="`组织架构`" key="1">
        <a-form layout="inline">
          <a-form-item label="部门">
            <a-input v-model="queryParam.deptName" placeholder="请输入部门" />
          </a-form-item>
          <span class="table-page-search-submitButtons" v-if="$auth('company:org:search')">
            <a-button type="primary" @click="initializeData">查询</a-button>
          </span>
          <span style="padding-left:20px;">
            <a-button
              type="primary"
              v-if="$auth('company:org:import')"
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
            <template slot="remark" slot-scope="text">
              <ellipsis :length="10" tooltip>{{ text }}</ellipsis>
            </template>
            <span slot="action" slot-scope="text, record">
              <span v-if="record.parentId == -1"></span>
              <span v-else>
                <span v-if="$auth('company:org:edit')">
                  <a @click="handleEdit(record)">编辑</a>
                </span>
                <span v-if="$auth('company:org:add')">
                  <a-divider type="vertical" v-if="$auth('company:org:edit')" />
                  <a @click="handelAdd(record)">添加下级部门</a>
                  <a-divider type="vertical" />
                  <a @click="handleAddSN(record)">添加平级部门</a>
                </span>
                <span v-if="$auth('company:org:del')">
                  <a-divider
                    type="vertical"
                    v-if=" $auth('company:org:add') || $auth('company:org:edit')"
                  />
                  <a-popconfirm title="是否确定删除?" @confirm="handleDel(record)">
                    <a>删除</a>
                  </a-popconfirm>
                </span>
              </span>
            </span>
          </a-table>
        </div>
        <org-modal ref="modal" @ok="handleOk"></org-modal>
        <upload-modal
          :sampleData="sampleData"
          :showProgress="true"
          paramKey="tableField"
          title="导入组织架构"
          templateName="组织架构模板"
          ref="uploadModal"
          action="/doc/dept/importDept"
          @onSuccess="success"
          @error="error"
        />
      </a-tab-pane>
      <a-tab-pane :tab="`附件`" key="2" v-if="$auth('company:org:attachView')">
        <org-upload-file-modal ref="orgModal"></org-upload-file-modal>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>
<script>
import OrgModal from './modules/OrgModal'
import OrgUploadFileModal from './modules/OrgUploadFileModal'
import { UploadModal, Ellipsis } from '@/components'
export default {
  components: {
    OrgModal,
    UploadModal,
    Ellipsis,
    OrgUploadFileModal
  },
  data () {
    return {
      actvieKey: '1',
      queryParam: {},
      tableData: [],
      defaultExpandedRowKeys: [],
      columns: [
        {
          title: '部门',
          dataIndex: 'deptName',
          key: 'deptName'
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
          deptName: { title: '部门', required: true, defaultTitle: '部门', importField: true },
          remark: { title: '备注', defaultTitle: '备注', importField: true }
        }
      },
      sampleData: [
        {
          deptName: '集团总部/测试中心',
          remark: '备注:多级部门用/区分，上级部门在前面。'
        }
      ]
    }
  },
  created () {
    this.initializeData()
  },
  methods: {
    callback (activeKey) { // 切换tab调用
      this.actvieKey = activeKey
    },
    initializeData () {
      this.$http.get('/dept/queryDeptList', { params: this.queryParam })
        .then(res => {
          if (res.success && res.data && res.data.length > 0) {
            this.defaultExpandedRowKeys.push(res.data[0].id)
          }
          this.spinning = false
          this.tableData = res.data
          return res.data
        }).finally(() => {
          this.$getTree('dept', true)
        })
    },
    handleEdit (record) {
      this.$refs.modal.edit(record)
    },
    handelAdd (record) {
      this.$refs.modal.add(record)
    },
    handleAddSN (record) {
      this.$refs.modal.addSNDept(record)
    },
    handleDel (record) {
      if (record.children != null) {
        this.$message.info('该部门有子部门不能进行删除操作')
      } else {
        this.$http.post('/dept/delDept', { id: record.id }).then(res => {
          if (res.success) {
            this.$message.success('删除成功')
            this.initializeData()
          } else {
            this.$message.error(`${res.errorMessage ? res.errorMessage : '删除失败'}`)
          }
        })
      }
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
<style lang="less" scoped>
#tabs {
  height: 100%;
}
#tabs /deep/ .ant-tabs-content {
  height: 100%;
}
#tabs /deep/ .ant-tabs-tabpane-active {
  height: calc(100% - 62px);
  overflow: auto;
}
</style>
