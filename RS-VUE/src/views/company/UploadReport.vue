<template>
  <a-card :bordered="false">
    <span v-if="isSearch">
      <a-form layout="inline">
        <a-form-item label="文件名">
          <a-input v-model="fileName" placeholder="请输入文件名" />
        </a-form-item>
        <a-form-item label="年份">
          <year-select @change="v=>year = v" placeholder="请选择年份" style="width:100px;" />
        </a-form-item>
        <a-form-item style="margin-right:10px;">
          <a-button v-if="isSearch" type="primary" @click="$refs.table.refresh(true)">查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          rowId="id"
          ref="table"
          highlight-hover-row
          show-overflow
          show-header-overflow
          :toolbar="{zoom:true,custom:true,refresh:true}"
          :params="{ fileName: this.fileName, fileType: this.fileType, year: this.year }"
          queryUrl="/sysDocument/queryDocument"
        >
          <template v-slot:buttons>
            <span>
              <a-button v-if="isUpload" type="primary" @click="openUploadModal">上传</a-button>
            </span>
          </template>
          <vxe-table-column title="序号" type="seq" :width="50" />
          <vxe-table-column title="年份" field="year" :width="100" align="center" remoteSort />
          <vxe-table-column title="文件名" field="fileName" :min-width="200" remoteSort />
          <vxe-table-column title="上传者" field="userName" :width="130" remoteSort>
            <template slot-scope="{row}">
              <span>{{ row.userName!==null?row.userName:'--' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="上传时间" field="createTime" :width="150" align="center" remoteSort />
          <vxe-table-column title="操作" :width="160" align="center" fixed="right">
            <template slot-scope="{row}">
              <span>
                <a href="javascript:;" v-if="isView" @click="preview(row)">预览</a>
                <a-divider type="vertical" v-if="isDownload" />
                <a href="javascript:;" v-if="isDownload" @click="downloadFile(row)">下载</a>
                <a-divider type="vertical" v-if="isDelete"/>
                <a-popconfirm title="是否确定删除?" @confirm="del(row)" v-if="isDelete">
                  <a>删除</a>
                </a-popconfirm>
              </span>
            </template>
          </vxe-table-column>
        </ystable>

      </div>
      <upload-file-modal
        ref="uploadFileModal"
        :showProgress="true"
        :paramData="paramData"
        paramKey="tableField"
        :title="uploadTitle"
        action="/sysDocument/uploadFile"
        @onSuccess="success"
        @error="error"
      />
      <preview-modal ref="previewModal"></preview-modal>
    </span>
  </a-card>
</template>
<script>
import UploadFileModal from './modules/UploadFileModal'
import { ystable, PreviewModal, YearSelect } from '@/components'

export default {
  name: 'Rules',
  components: {
    PreviewModal,
    UploadFileModal,
    YearSelect,
    ystable
  },
  props: {
    fileType: {
      type: Number,
      required: true
    },
    typeName: {
      type: String,
      required: true
    },
    pageName: {
      type: String,
      default: ''
    },
    isSearch: { // 控制查询按钮
      type: [Object, Boolean],
      default: undefined
    },
    isUpload: { // 控制上传按钮
      type: [Object, Boolean],
      default: undefined
    },
    isDownload: { // 控制下载按钮
      type: [Object, Boolean],
      default: undefined
    },
    isView: { // 控制预览按钮
      type: [Object, Boolean],
      default: undefined
    },
    isDelete: { // 控制删除按钮
      type: [Object, Boolean],
      default: undefined
    }
  },
  data () {
    return {
      uploadTitle: `上传文件[${this.pageName}]`,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      fileName: '',
      year: 0,
      paramData: { fileType: this.fileType, projectType: 0, projectId: 0 }
    }
  },
  methods: {
    preview (record) {
      if (record.filePath === null || record.filePath === '') {
        this.$message.warning('文件路径不对，请联系管理员')
        return
      }
      this.$refs.previewModal.show(record.filePath, record.fileName)
    },
    downloadFile (record) {
      this.$exportData('/sysDocument/downloadFile', { id: record.id }, record.fileName, this.$message)
    },
    openUploadModal () {
      this.$refs.uploadFileModal.show(this.tableField)
    },
    success (fileName, resData) {
      if (resData) {
        this.$message.success(fileName + '上传成功')
      } else {
        this.$message.error(fileName + '上传失败')
      }
      this.$refs.table.refresh(true)
    },
    error (fileName, errorMessage) {
      this.$message.error(fileName + '上传失败')
    },
    search () { },
    del (record) {
      this.$http.post('/sysDocument/del', { id: record.id })
        .then(res => {
          this.$message.success('删除成功')
        }).finally(res => {
          this.$refs.table.refresh(false)
        })
    }
  }
}
</script>
