<!--
 * @Author: hck
 * @Date: 2021-05-10 11:22:35
 * @LastEditTime: 2022-05-17 10:04:18
 * @LastEditors: zdf
 * @Description: 查新报告
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\NewReport.vue
-->
<template>
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
            <span >
              <a @click="downloadFile(row)">{{ row.fileName }}</a>
            </span>
            <span>
              <a-tooltip style="cursor:pointer" placement="top" @click="preview(row)">
                <template slot="title">
                  <span>预览</span>
                </template>
                <a-icon type="eye" style="margin-left:5px" />
              </a-tooltip>
            </span>
          </template>
        </vxe-table-column>
      </template>
    </vxe-grid>
    <preview-modal ref="previewModal"></preview-modal>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { PreviewModal } from '@/components'
// import { cloneDeep } from 'lodash'
export default {
  name: 'NewReport',
  components: {
    PreviewModal
  },
  props: {
    projectId: {
      type: Number,
      required: true
    }
  },
  mounted () {
    this.$getDictionary(6).then(res => {
      this.stageLists = res
      for (const stage of res) {
        this.stageMap[stage.key] = stage.value
      }
    })
    this.paramData.projectId = this.projectId
    this.querylist()
  },
  watch: {
    projectId (val) {
      this.paramData.projectId = val
      this.querylist()
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
      }
    }
  },
  methods: {
    handleTemplateEvent () {
      this.querylist()
    },
    querylist () {
      this.$http.get('/backupData/getProjectReports', { params: this.paramData }).then((res) => {
        if (res.data && res.success) {
          this.tableData = res.data
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally((res) => {
      })
    },
    preview (row) {
      if (row.filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      const fileName = row.fileName !== undefined ? row.fileName : ''
      this.$refs.previewModal.show(row.filePath, fileName, `${row.pname}${fileName ? '-' + fileName : ''}`)
    },
    downloadFile (row) {
      this.$exportData('/docList/downloadFile', { fileName: row.fileName, filePath: row.filePath }, row.fileName, this.$message)
    },
    refresh () {
      this.querylist()
    }
  }
}
</script>

<style scoped>
</style>
