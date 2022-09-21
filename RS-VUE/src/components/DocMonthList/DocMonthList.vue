<template>
  <a-card style="height: 100%" :bodyStyle="{ height: '100%', overflow: 'auto'}">
    <a-form layout="inline">
      <select-project ref="creatProject" @projectSelect="projectSelected" :year="currentYear" :sign="2"></select-project>
    </a-form>
    <div v-if="showTable && projectId" style="height: calc(100% - 39px); overflow: auto;">
      <a-form layout="inline">
        <a-form-item label="文件名">
          <a-input v-model="fileName" placeholder="请输入文件名" />
        </a-form-item>
        <a-form-item label="月份">
          <a-month-picker style="width:230px" v-model="docMonth" placeholder="请选择月份" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
        </a-form-item>
      </a-form>
      <ystable
        rowId="id"
        ref="table"
        highlight-hover-row
        show-overflow
        show-header-overflow
        :toolbar="{zoom:true,custom:true,refresh:true}"
        :params="{ fileName: this.fileName, fileType: this.fileType, year: this.currentYear, projectId: this.projectId, docMonth: this.docMonth }"
        queryUrl="/sysDocument/queryDocument"
      >
        <template v-slot:buttons>
          <span>
            <a-button type="primary" @click="openUploadModal()">上传</a-button>
          </span>
        </template>
        <vxe-table-column type="seq" title="序号" :width="50" />
        <vxe-table-column title="月份" field="docMonth" key="docMonth" remoteSort align="center" />
        <vxe-table-column title="文件名" field="fileName" key="fileName" remoteSort />
        <vxe-table-column title="上传者" field="userName" key="userName" remoteSort>
          <template slot-scope="{row}">
            <span>{{ row.userName!==null?row.userName:'--' }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="上传时间"
          field="createTime"
          align="center"
          key="createTime"
          remoteSort
        />
        <vxe-table-column title="操作" key="key" align="center" :width="160">
          <template slot-scope="{row}">
            <span>
              <a href="javascript:;" @click="preview(row)">预览</a>
              <a-divider type="vertical" />
              <a href="javascript:;" @click="downloadFile(row)">下载</a>
              <a-divider type="vertical" />
              <a-popconfirm title="是否确定删除?" @confirm="del(row)" v-if="row.companyId !== 0">
                <a>删除</a>
              </a-popconfirm>
            </span>
          </template>
        </vxe-table-column>
      </ystable>
    </div>
    <preview-modal ref="previewModal"></preview-modal>
    <upload-file
      :paramData="paramData"
      :haveMonth="true"
      paramKey="tableField"
      :title="uploadTitle"
      ref="uploadModal"
      action="/sysDocument/uploadDocList"
      @onSuccess="success"
      @error="error"
    />
  </a-card>
</template>

<script>
import { UploadFile, PreviewModal, SelectProject } from '@/components'
import ystable from '@/components/Table/ystable'
import yearMiXin from '@/utils/yearMixin'

export default {
  props: {
    fileType: {
      type: Number,
      default: 7002
    },
    pageName: {
      type: String,
      default: ''
    }
  },
  mixins: [yearMiXin],
  name: 'PurchaseOrder',
  components: {
    ystable,
    UploadFile,
    PreviewModal,
    SelectProject
  },
  data () {
    return {
      uploadTitle: '上传附件',
      fileName: '',
      month: '',
      docMonth: undefined,
      projectId: 0,
      paramData: { fileType: this.fileType, year: this.currentYear, projectType: 1, projectId: this.projectId, month: 0, listId: 0 },
      // getData: parameter => {
      //   if (this.projectId === 0) {
      //     return
      //   }
      //   return this.$http.get('/sysDocument/queryDocument', { params: Object.assign(parameter, { fileName: this.fileName, fileType: this.fileType, year: this.currentYear, projectId: this.projectId, docMonth: this.docMonth }) })
      //     .then(res => {
      //       return res.data
      //     })
      // },
      showTable: true
    }
  },
  created () {
    this.paramData.year = this.currentYear
  },
  methods: {
    openUploadModal () {
      this.$refs.uploadModal.show(this.tableField)
    },
    projectSelected (value, project) {
      this.projectId = value
      this.paramData.projectId = value
      if (value === 0) {
        this.showTable = false
      } else {
        this.showTable = true
        this.uploadTitle = `上传文件[${this.pageName}][${project.pname}]`
        if (this.$refs.table) {
          this.$refs.table.refresh(true)
        }
      }
    },
    search () {
      this.paramData.year = this.currentYear
    },
    preview (record) {
      if (record.filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(record.filePath, record.fileName !== undefined ? record.fileName : '')
    },
    downloadFile (record) {
      this.$exportData('/sysDocument/downloadFile', { id: record.id }, record.fileName, this.$message)
    },
    success (fileName, resData) {
      if (resData) {
        this.$message.success(fileName + '上传成功')
      } else {
        this.$message.error(fileName + '上传失败')
      }
      this.$refs.table.refresh(true)
    },
    error (fileName, msg) {
      this.$message.error(fileName + '上传失败')
    },
    del (record) {
      this.$http.post('/sysDocument/del', { id: record.id })
        .then(res => {
          if (res.data) {
            this.$message.success('删除成功')
          } else {
            this.$message.error('删除失败')
          }
        }).finally(res => {
          this.$refs.table.refresh(true)
        })
    }
  }
}
</script>
