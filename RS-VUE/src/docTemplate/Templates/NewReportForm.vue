<!--
 * @Author: hck
 * @Date: 2021-05-10 11:22:35
 * @LastEditTime: 2022-09-14 12:00:55
 * @LastEditors: hm
 * @Description: 查新报告 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\NewReportForm.vue
-->
<template>
  <a-card>
    <div>
      <vxe-grid
        rowId="id"
        ref="table"
        border
        resizable
        auto-resize
        highlight-hover-row
        show-overflow
        show-header-overflow
        highlight-current-row
        header-align="center"
        :data="tableData"
      >
        <template>
          <vxe-table-column type="seq" title="序号" width="50" align="center" header-aligin="center" />
          <vxe-table-column title="文件名" field="fileName" key="fileName" align="left" header-align="center">
            <template v-slot="{ row }">
              <span v-if="$auth('project:result:download')">
                <a @click="downloadFile(row)">{{ row.fileName }}</a>
              </span>
              <span v-else>
                {{ row.fileName }}
              </span>
              <span v-if="$auth('project:result:preview')">
                <a-tooltip style="cursor:pointer" placement="top" @click="preview(row)">
                  <template slot="title">
                    <span>预览</span>
                  </template>
                  <a-icon type="eye" style="margin-left:5px" />
                </a-tooltip>
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            v-if="isEdit && $auth('project:result:del')"
            title="操作"
            align="center"
            width="90"
            header-align="center">
            <template v-slot="{row}">
              <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)">
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </template>
      </vxe-grid>
    </div>
    <div class="operate">
      <a-button type="dashed" style="width: 100%" v-if="isEdit" icon="plus" @click="openUploadModal()">上传文件</a-button>
    </div>
    <result-upload-modal
      :year="currentYear"
      :haveMonth="true"
      :isNewReportCommit="false"
      paramKey="tableField"
      title="上传文件"
      ref="uploadModal"
      action="/sysDocument/uploadResult"
      @onSuccess="success"
      @error="error"
    />
  </a-card>
</template>

<script>
// TODO 提交问题、权限问题要处理
import { mapGetters } from 'vuex'
import ResultUploadModal from '@/views/project/modules/ResultUploadModal'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import { saveData } from '@/api/doc/index'
import { cloneDeep } from 'lodash'

// import { cloneDeep } from 'lodash'
export default {
  name: 'NewReportForm',
  components: {
    ResultUploadModal
  },
  props: {
    projectId: {
      type: Number,
      required: true
    },
    docId: {
      type: Number,
      required: true
    },
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  created () {
    this.content = getTemplateContent('appendixForm')
    this.BPContent = getTemplateContent('appendixForm')
  },
  mounted () {
    this.$getDictionary(6).then(res => {
      this.stageLists = res
      for (const stage of res) {
        this.stageMap[stage.key] = stage.value
      }
    })
    this.paramData.projectId = this.projectId
  },
  watch: {
    projectId (val) {
      this.paramData.projectId = val
      this.querylist()
    },
    docId () {
      this.content = getTemplateContent('appendixForm')
      this.BPContent = getTemplateContent('appendixForm')
    }
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  data () {
    return {
      content: {},
      BPContent: {},
      project: {},
      fileInfo: {},
      stageMap: {},
      tableData: [],
      paramData: {
        projectId: undefined
      },
      // requiredFields: ['list'],
      BkList: []
    }
  },
  methods: {
    handleTemplateEvent () {
      this.querylist()
    },
    querylist () {
      return this.$http.get('/projectDocFileData/getProjectReports', { params: this.paramData }).then((res) => {
        if (res.data && res.success) {
          this.content.list = this.tableData = res.data
          this.bkList = cloneDeep(this.tableData)
          return Promise.resolve(true)
        } else {
          this.tableData = this.BkList
          this.$message.error(res.errorMessage)
          return Promise.resolve(false)
        }
      }).catch((error) => {
        this.tableData = this.BkList
        this.$message.error(error.message)
        return Promise.resolve(false)
      }).finally((res) => {
        this.$listeners.onControlCommitBtn(this.tableData.length > 0)
      })
    },
    preview (row) {
      if (row.filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$preview({
        filePath: row.filePath,
        docName: row.fileName || '',
        visible: true
      })
    },
    downloadFile (row) {
      this.$exportData('/sysDocument/downloadFile', { id: row.id }, row.fileName, this.$message)
    },
    refresh () {
      this.querylist()
    },
    loading (spinning) {
      this.$emit('loading', spinning)
    },
    completed (response) {
      const { footer, data } = response
      this.$emit('completed', footer, data)
    },
    onDel (record) {
      return this.$http.post('/sysDocument/del', { id: record.id })
        .then(res => {
          if (res.data) {
            return Promise.resolve(true)
          } else {
            this.$message.error('删除失败')
            return Promise.resolve(false)
          }
        })
    },
    async handleDel (record) {
      const resultDel = await this.onDel(record)
      if (!resultDel) { return }
      const queryDel = await this.querylist()
      if (!queryDel) { return }
      this.handleSaveData(this.bkList, this.tableData)
    },
    async success () {
      const queryDel = await this.querylist()
      if (!queryDel) { return }
      this.handleSaveData(this.bkList, this.tableData)
    },
    error (message) {
      this.tableData = this.BkList
      this.$message.error(message)
    },
    openUploadModal () {
      this.$refs.uploadModal.show(undefined, this.projectId)
    },
    handleSaveData (bkList, tempRows) {
      if (tempRows) {
        this.content.list = tempRows
      }
      const postData = {
        finished: this.tableData && this.tableData.length,
        pDocFileId: this.fileInfo.docId,
        projectId: this.projectId,
        data: JSON.stringify(this.content),
        month: undefined,
        wordLength: 0,
        filledItems: 0,
        totalItems: 0
      }
      saveData(postData).then((res) => {
        if (res.data) {
          this.$message.success('保存成功')
        } else {
          this.$message.error('保存失败')
          this.tableData = bkList
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
