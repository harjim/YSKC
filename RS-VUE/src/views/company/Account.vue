<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="科目名称">
          <a-input v-model="queryParam.accountName" placeholder="请输入科目名称" />
        </a-form-item>
        <span style="padding-left:30px;" v-if="$auth('company:account:search')">
          <a-button type="primary" @click="initializeData">查询</a-button>
        </span>
        <span style="padding-left:20px;" v-if="$auth('company:account:add')">
          <a-button type="primary" @click="handelAdds()">添加</a-button>
        </span>
        <span style="padding-left:20px;" v-if="$auth('company:account:import')">
          <a-button type="primary" @click="$refs.uploadModal.show(tableField)">导入</a-button>
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
          :scroll="scroll"
          :defaultExpandedRowKeys="defaultExpandedRowKeys"
        >
          <span slot="action" slot-scope="text, record">
            <span v-if="$auth('company:account:edit')">
              <a @click="handleEdit(record)">编辑</a>
            </span>
            <span v-if="$auth('company:account:add')">
              <a-divider type="vertical" v-if="$auth('company:account:edit')" />
              <a @click="handelAdd(record)">添加下级科目</a>
            </span>

            <span v-if="$auth('company:account:del')">
              <a-divider
                type="vertical"
                v-if="$auth('company:account:add') ||$auth('company:account:edit') "
              />
              <a-popconfirm title="是否确定删除?" @confirm="handleDel(record)">
                <a>删除</a>
              </a-popconfirm>
            </span>
          </span>
        </a-table>
      </div>
      <account-modal ref="modal" @ok="handleOk"></account-modal>
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        paramKey="tableField"
        title="导入科目"
        ref="uploadModal"
        action="/doc/accountTitle/importAccount"
        templateName="科目模板"
        @onSuccess="success"
        @error="error"
      />
    </a-spin>
  </a-card>
</template>
<script>
import AccountModal from './modules/AccountModal'
import { UploadModal } from '@/components'
const accoutTypeArr = ['资产类', '负债类', '权益类', '成本类', '损益类']
export default {
  name: 'Account',
  components: {
    AccountModal,
    UploadModal
  },
  data () {
    return {
      scroll: {},
      queryParam: {},
      tableData: [],
      spinning: false,
      defaultExpandedRowKeys: [],
      columns: [{
        title: '科目编码',
        dataIndex: 'accountNumber',
        key: 'accountNumber',
        width: 300
      },
      {
        title: '科目名称',
        dataIndex: 'accountName',
        key: 'accountName',
        width: 300
      },
      {
        title: '科目级别',
        dataIndex: 'level',
        key: 'level',
        width: 100
      },
      {
        title: '科目类别',
        dataIndex: 'accoutType',
        key: 'accoutType',
        customRender: function (text, record, index) {
          if (text < 6) {
            return accoutTypeArr[text]
          }
        },
        width: 300
      },
      {
        title: '操作',
        dataIndex: 'action',
        scopedSlots: { customRender: 'action' },
        width: 300
      }],
      tableField: {
        tableId: 'accountTable',
        fieldTitleObject: {
          accountNumber: { title: '科目编码', required: true, defaultTitle: '科目编码', importField: true },
          accountName: { title: '科目名称', required: true, defaultTitle: '科目名称', importField: true },
          level: { title: '级别', required: true, defaultTitle: '级别', importField: true },
          typeName: { title: '科目类型', defaultTitle: '科目类型', importField: true }
        }
      },
      sampleData: [{
        accountNumber: 'K001',
        accountName: '费用科目',
        level: '级别，当前科目级别，第一层为1，第二层为2...',
        typeName: '资产类, 负债类, 权益类, 成本类, 损益类'
      }]
    }
  },
  created () {
    this.initializeData()
    if (document.body.clientWidth < 1500) {
      this.scroll = { x: 1260 }
    }
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1500) {
          this.scroll = { x: 1260 }
        }
      })()
    }
  },
  methods: {
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          width: 660,
          content: resData,
          onOk () {

          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
        this.$getTree('account', true)
      }
      this.initializeData()
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    initializeData () {
      this.spinning = true
      this.$http.get('/accountTitle/queryAccountTitleList', { params: this.queryParam })
        .then(res => {
          if (res.success && res.data && res.data.length > 0) {
            this.tableData = res.data
            this.defaultExpandedRowKeys.push(res.data[0].id)
          } else {
            this.tableData = []
          }
          this.spinning = false
          return res.data
        })
    },
    handleEdit (record) {
      this.$refs.modal.edit(record)
    },
    handelAdd (record) {
      this.$refs.modal.add(record)
    },
    handelAdds () {
      this.$refs.modal.adds()
    },
    handleDel (record) {
      this.$http.post('/accountTitle/delAccountTitle', { id: record.id }).then(res => {
        if (res.success && res.data) {
          this.initializeData()
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
        }
      })
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.initializeData()
    }
  }
}
</script>
<style scoped>
</style>
