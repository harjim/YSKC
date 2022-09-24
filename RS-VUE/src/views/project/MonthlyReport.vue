<template>
  <a-card :bordered="false">
    <span v-if="$auth('project:monthlyReport:search')">
      <a-form layout="inline">
        <a-form-item label="文件名">
          <a-input v-model="fileName" placeholder="请输入文件名" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="$refs.table.refresh(true)" v-if="$auth('project:monthlyReport:search')">查询</a-button>
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
          queryUrl="/sysDocument/queryDocument"
          :params="{fileName: this.fileName, fileType: 1, year: this.currentYear }"
        >
          <template v-slot:buttons>
            <span>
              <a-button type="primary" @click="openUploadModal" v-if="$auth('project:monthlyReport:upload')">上传</a-button>
            </span>
          </template>
          <vxe-table-column type="seq" title="序号" :width="50" />
          <vxe-table-column title="年月" field="yearAndMonth" :width="100">
            <template slot-scope="{row}">
              <span>{{ row.year + '-'+ (row.month &lt; 10? '0'+row.month : row.month) }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="文件名" field="fileName" :min-width="200" />
          <vxe-table-column title="上传者" field="userName" :width="140">
            <template slot-scope="{row}">
              <span>{{ row.userName!==null?row.userName:'--' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="上传时间" field="createTime" :width="140" />
          <vxe-table-column title="操作" :width="140" align="center">
            <template slot-scope="{row}">
              <span>
                <a href="javascript:;" @click="preview(row)" v-if="$auth('project:monthlyReport:view')">预览</a>
                <a-divider type="vertical" v-if="$auth('project:monthlyReport:download')" />
                <a href="javascript:;" @click="downloadFile(row)" v-if="$auth('project:monthlyReport:download')">下载</a>
                <a-divider type="vertical" v-if="$auth('project:monthlyReport:delete')" />
                <a-popconfirm title="是否确定删除?" @confirm="del(row)" v-if="$auth('project:monthlyReport:delete')">
                  <a>删除</a>
                </a-popconfirm>
              </span>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <monthly-report-upload
        ref="monthlyReportUpload"
        :showProgress="true"
        :paramData="paramData"
        paramKey="tableField"
        title="上传文件"
        action="/sysDocument/uploadFile"
        @onSuccess="success"
        @error="error"
      />
    </span>
  </a-card>
</template>
<script>
import MonthlyReportUpload from './modules/MonthlyReportUpload'
import { ystable } from '@/components'
import yearMiXin from '@/utils/yearMixin'

export default {
  mixins: [yearMiXin],
  name: 'MonthlyReport',
  components: {
    MonthlyReportUpload,
    ystable
  },
  data () {
    return {
      fileName: '',
      paramData: { fileType: 1, year: this.currentYear, projectType: 0, projectId: 0 }
      // getData: parameter => {
      //   return this.$http.get('/sysDocument/queryDocument', { params: Object.assign(parameter, { fileName: this.fileName, fileType: 1, year: this.currentYear }) })
      //     .then(res => {
      //       return res.data
      //     })
      // }
    }
  },
  created () {
    this.paramData.year = this.currentYear
  },
  methods: {
    search () {
      this.paramData.year = this.currentYear
      this.$refs.table.refresh(true)
    },
    preview (record) {
      if (record.filePath === null || record.filePath === '') {
        this.$message.warning('文件路径不对，请联系管理员')
        return
      }
      this.$preview({
        filePath: record.filePath,
        docName: record.fileName,
        visible: true
      })
    },
    downloadFile (record) {
      this.$exportData('/sysDocument/downloadFile', { id: record.id }, record.fileName, this.$message)
    },
    openUploadModal () {
      this.$refs.monthlyReportUpload.show(this.tableField, this.currentYear)
    },
    success (fileName, resData) {
      if (resData) {
        this.$message.success(fileName + '上传成功')
      } else {
        this.$message.success(fileName + '上传失败')
      }
      this.$refs.table.refresh(true)
    },
    error (fileName, msg) {
      this.$message.success(fileName + '上传失败')
    },
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
