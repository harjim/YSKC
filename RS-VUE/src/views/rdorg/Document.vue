<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="文件名">
        <a-input v-model="fileName" placeholder="请输入文件名" />
      </a-form-item>
      <span style="padding-left:10px;">
        <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
      </span>
      <span style="padding-left:20px;">
        <a-button type="primary" @click="openUploadModal">上传</a-button>
      </span>
    </a-form>
    <div>
      <s-table rowKey="id" ref="table" :data="getData">
        <a-table-column title="文件名" data-index="fileName" key="fileName" />
        <a-table-column title="上传者" data-index="userName" key="userName">
          <template slot-scope="text,record">
            <span>{{ record.userName!==null?record.userName:'--' }}</span>
          </template>
        </a-table-column>
        <a-table-column title="上传时间" data-index="createTime" key="createTime" />
        <a-table-column title="下载次数" data-index="downloadTimes" key="downloadTimes" />
        <a-table-column title="操作" key="key">
          <template slot-scope="text,record">
            <span>
              <a href="javascript:;" @click="preview(record)">预览</a>
              <a-divider type="vertical" />
              <a href="javascript:;" @click="downloadFile(record)">下载</a>
              <a-divider type="vertical" />
              <a-popconfirm title="是否确定删除?" @confirm="del(record)">
                <a href="javascript:;">删除</a>
              </a-popconfirm>
            </span>
          </template>
        </a-table-column>
      </s-table>
    </div>
    <doc-edit
      ref="docEdit"
      :showProgress="true"
      :paramData="paramData"
      paramKey="tableField"
      title="上传文件"
      action="/sysDocument/uploadFile"
      @onSuccess="success"
      @error="error"
    />
  </a-card>
</template>
<script>
import DocEdit from './modules/DocEdit'
import { STable } from '@/components'
import yearMiXin from '@/utils/yearMixin'

export default {
  mixins: [yearMiXin],
  name: 'Rules',
  components: {
    DocEdit,
    STable
  },
  props: {
    fileType: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      fileName: '',
      paramData: { fileType: this.fileType, year: this.currentYear, projectType: 1, projectId: 0 },
      getData: parameter => {
        return this.$http.get('/sysDocument/queryDocument', { params: Object.assign(parameter, { fileName: this.fileName, fileType: this.fileType, year: this.currentYear }) })
          .then(res => {
            return res.data
          })
      }
    }
  },
  created () {
    this.paramData.year = this.currentYear
  },
  methods: {
    preview (record) {
      if (record.filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$preview({
        filePath: record.filePath,
        docName: record.fileName || '',
        visible: true
      })
    },
    search () {
      this.paramData.year = this.currentYear
      this.$refs.table.refresh(true)
    },
    downloadFile (record) {
      this.$exportData('/sysDocument/downloadFile', { id: record.id }, record.fileName, this.$message)
    },
    openUploadModal () {
      this.$refs.docEdit.show(this.tableField)
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
    del (record) {
      this.$http.post('/sysDocument/del', { id: record.id })
        .then(res => {
          this.$message.success('删除成功')
        }).finally(res => {
          this.$refs.table.refresh(true)
        })
    }
  }
}
</script>
