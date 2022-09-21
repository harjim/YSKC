<template>
  <a-card :bordered="false">
    <div>
      <a-table :pagination="false" rowKey="index" ref="table" :columns="columns" :dataSource="data">
        <template slot="fileNameAction" slot-scope="text,record">
          <span>
            <a href="javascript:;" @click="downloadFile(record)">{{ formData[record.type] }}</a>
          </span>
        </template>
        <template slot="action" slot-scope="text,record">
          <span>
            <div style="float:left">
              <a href="javascript:;" @click="openUploadModal(record)">选择附件</a>
            </div>
            <div v-if="showDel" style="float:left">
              <a-divider type="vertical" />
              <a href="javascript:;" @click="preview(record)">预览</a>
              <a-divider type="vertical" />
              <a-popconfirm title="是否确定删除?" @confirm="del(record)">
                <a>删除</a>
              </a-popconfirm>
            </div>
          </span>
        </template>
      </a-table>
    </div>
    <upload-file
      :showProgress="true"
      :paramData="paramData"
      paramKey="tableField"
      title="上传附件"
      ref="uploadModal"
      action="/sysDocument/insertOrUpdateFile"
      @onSuccess="success"
      @error="error"
    />
    <preview-modal ref="previewModal"></preview-modal>
  </a-card>
</template>
<script>
import { UploadFile, PreviewModal } from '@/components'

const data = [{
  key: '2001',
  index: '1',
  docName: '审计报告',
  type: 2001,
  fileName: ''
}
  // , {
  //   key: '2',
  //   index: '2',
  //   docName: '技术改造项目备案表',
  //   type: 2,
  //   fileName: ''
  // }, {
  //   key: '3',
  //   index: '3',
  //   docName: '纳税证明',
  //   type: 3,
  //   fileName: ''
  // }, {
  //   key: '4',
  //   index: '4',
  //   docName: '财务审计报告',
  //   type: 4,
  //   fileName: ''
  // }, {
  //   key: '5',
  //   index: '5',
  //   docName: '技术改造投资项目投入明细清单',
  //   type: 5,
  //   fileName: ''
  // }
]

export default {
  name: 'Rules',
  components: {
    UploadFile,
    PreviewModal
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    },
    year: {
      type: Number,
      default: 0
    }
  },
  data () {
    const columns = [{
      title: '序号',
      dataIndex: 'index',
      width: 60
    }, {
      title: '材料名称',
      dataIndex: 'docName',
      width: 100
    }, {
      title: '附件',
      dataIndex: 'fileName',
      scopedSlots: { customRender: 'fileNameAction' },
      width: 400
    }, {
      title: '操作',
      dataIndex: 'action',
      scopedSlots: { customRender: 'action' }
    }]
    return {
      id: 0,
      type: 1,
      columns,
      data,
      fileName: '',
      fileNameMap: [],
      filePath: '',
      paramData: { fileType: 2001, year: this.year, projectType: 2, projectId: this.projectId },
      formData: {
        '2001': '',
        '2': '',
        '3': '',
        '4': '',
        '5': ''
      },
      showDel: true
    }
  },
  watch: {
    projectId (newId) {
      this.paramData.projectId = newId
      this.initialize()
    }
  },
  created () {
    this.initialize()
  },
  methods: {
    preview (record) {
      this.$refs.previewModal.show(this.filePath, this.formData[2001])
    },
    downloadFile (record) {
      this.$exportData('/sysDocument/downloadFile', { id: this.id }, this.formData[record.type], this.$message)
    },
    openUploadModal (record) {
      this.type = record.type
      this.$refs.uploadModal.show(this.tableField)
    },
    success (fileName, resData) {
      if (resData) {
        this.$message.success(fileName + '上传成功')
      } else {
        this.$message.error(fileName + '上传失败')
      }
      this.initialize()
    },
    error (fileName, errorMessage) {
      this.$message.error(fileName + '上传失败')
    },
    del (record) {
      this.$http.post('/sysDocument/del', { id: this.id })
        .then(res => {
          this.$message.success('删除成功')
        }).finally(res => {
          this.initialize()
        })
    },
    initialize () {
      const self = this
      this.$nextTick(() => {
        this.$http.get('/sysDocument/queryDocument', { params: { projectId: this.projectId, fileType: 2001, year: this.year, pageNo: 0, pageSize: 10 } })
          .then(res => {
            self.formData[2001] = ''
            if (res.data.data.length > 0) {
              for (let index = 0; index < res.data.data.length; index++) {
                const element = res.data.data[index]
                self.formData[element.fileType] = element.fileName
                if (element.fileType === 2001) {
                  self.id = element.id
                  self.filePath = element.filePath
                }
              }
              this.showDel = true
            } else {
              this.showDel = false
            }
            this.loading = false
            return res.data
          })
      })
    }
  }
}
</script>
