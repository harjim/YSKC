<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-01-11 19:48:49
 * @LastEditors: zdf
 * @Description: 机构建设事项
 * @FilePath: \RS-VUE\src\views\rdorg\BuildList.vue
-->
<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spin">
      <div class="table">
        <vxe-grid
          ref="table"
          :data="tableDatas"
          border
          size="small"
          resizable
          auto-resize
          highlight-hover-row
          show-overflow
          show-header-overflow
          highlight-current-row
          :toolbar="tableToolbar"
          header-align="center"
          max-height="100%"
        >
          <template v-slot:buttons>
            <a-button
              type="primary"
              @click="$refs.addDocModal.add(listType,tableDatas.length)"
              style="margin-right:10px"
              v-if="control.add"
            >添加</a-button>
            <a-button
              type="primary"
              @click="$refs.saveBuildConfig.open(currentYear)"
              style="margin-right:10px"
              v-if="control.setting"
            >事项配置</a-button>
            <a-button
              type="primary"
              @click="$refs.yearImport.open(currentYear, undefined)"
              style="margin-right:10px"
              v-if="control.import"
            >引入</a-button>
          </template>
          <vxe-table-column
            type="seq"
            title="序号"
            width="60"
            align="center"
            header-align="center"
            fixed="left"/>
          <vxe-table-column
            title="研发活动"
            field="rdActivities"
            width="160"
            fixed="left"
            align="left"
          />
          <vxe-table-column
            title="建设事项"
            field="docName"
            width="260"
            align="left"
            fixed="left"
          />
          <vxe-table-column
            title="负责岗位"
            field="operators"
            width="160"
            align="left"
          />
          <vxe-table-column
            title="制度文件"
            field="buildFiles"
            minWidth="300"
            align="left"
          >
            <template v-slot="{row}">
              <template v-if="row.buildFiles.length">
                <span v-for="(file,index) in row.buildFiles" :key="index" style="margin-right:8px;"> <!-- buildType 为0或为空时，默认为制度文件 -->
                  <a title="点击下载" @click="downloadFile(file)" v-if="control.download">
                    {{ file.fileName }}
                  </a>
                  <span v-else>
                    {{ file.fileName }}
                  </span>
                  <a-tooltip style="cursor:pointer" placement="top" @click="preview(file)" v-if="control.preview">
                    <template slot="title">
                      <span>预览</span>
                    </template>
                    <a-icon type="eye" style="margin-left:5px" />
                  </a-tooltip>
                  <a-popconfirm
                    title="是否确定删除?"
                    @confirm="delFile(file.id)"
                    style="color:red;margin-left:5px"
                    v-if="control.delFile"
                  >
                    <a-tooltip placement="top">
                      <template slot="title">
                        <span>删除</span>
                      </template>
                      <a-icon type="close" />
                    </a-tooltip>
                  </a-popconfirm>
                </span>
              </template>
              <template v-else-if="row.subClassify">
                <a title="点击下载" @click="generateBuildFile(row)" v-if="control.download">
                  {{ row.docName+'.docx' }}
                </a>
                <span v-else>
                  {{ row.docName+'.docx' }}
                </span>
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="佐证材料"
            field="proofFiles"
            minWidth="300"
            align="left"
          >
            <template v-slot="{row}">
              <span v-for="(file,index) in row.proofFiles" :key="index" style="margin-right:8px;">
                <a title="点击下载" @click="downloadFile(file)" v-if="control.download">
                  {{ file.fileName }}
                </a>
                <span v-else>
                  {{ file.fileName }}
                </span>
                <a-tooltip style="cursor:pointer" placement="top" @click="preview(file)" v-if="control.preview">
                  <template slot="title">
                    <span>预览</span>
                  </template>
                  <a-icon type="eye" style="margin-left:5px" />
                </a-tooltip>
                <a-popconfirm
                  title="是否确定删除?"
                  @confirm="delFile(file.id)"
                  style="color:red;margin-left:5px"
                  v-if="control.delFile"
                >
                  <a-tooltip placement="top">
                    <template slot="title">
                      <span>删除</span>
                    </template>
                    <a-icon type="close" />
                  </a-tooltip>
                </a-popconfirm>
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="操作"
            width="160"
            align="left"
          >
            <template v-slot="{row,rowIndex}">
              <a v-if="control.edit" @click="$refs.addDocModal.edit(row,rowIndex)">编辑</a>
              <template v-if="control.upload">
                <a-divider type="vertical" v-if="control.edit"/>
                <a @click="openUploadModal(row)">上传</a>
              </template>
              <a-popconfirm v-if="control.del && row.companyId !== 0" title="是否确定删除?" @confirm="delDocList(row.id)">
                <a-divider type="vertical" v-if="control.upload"/>
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </vxe-grid>
      </div>
      <year-import-modal
        ref="yearImport"
        initName="项目"
        url="docList/importFiles"
        queryUrl="sysDocument/getDocuments"
      />
    </a-spin>
    <upload-file
      :showProgress="true"
      :paramData="paramData"
      :title="uploadTitle"
      :listType="listType"
      ref="uploadModal"
      action="/sysDocument/uploadDocList"
      @onSuccess="success"
    />
    <add-doc-modal ref="addDocModal" @ok="search" @editOk="editOk"></add-doc-modal>
    <preview-modal ref="previewModal"></preview-modal>
    <year-import-modal
      ref="yearImport"
      initName="建设事项"
      url="docList/importFiles"
      queryUrl="docList/getYear"
      @ok="search"
    />
    <save-build-config-modal ref="saveBuildConfig"></save-build-config-modal>
  </a-card>
</template>
<script>
import { isEditStatus } from '@/utils/processDoc/auditStatus'
import { mapGetters } from 'vuex'
import yearMiXin from '@/utils/yearMixin'
import AddDocModal from '@/components/DocList/AddDocModal'
import { UploadFile, PreviewModal } from '@/components'
import YearImportModal from '@/views/project/init/YearImportModal'
import SaveBuildConfigModal from '@/components/BuildConfig/SaveBuildConfigModal'
export default {
  name: 'BuildList',
  components: {
    UploadFile,
    PreviewModal,
    AddDocModal,
    YearImportModal,
    SaveBuildConfigModal
  },
  mixins: [yearMiXin],
  data () {
    return {
      paramData: { fileType: 0, year: this.currentYear, projectType: 1, projectId: 0, listId: 0 },
      listType: 6001,
      spin: false,
      tableDatas: [],
      control: { view: undefined, download: undefined, delFile: undefined, preview: undefined, add: undefined, edit: undefined, del: undefined, upload: undefined, setting: undefined },
      uploadTitle: '',
      tableToolbar: {
        zoom: true,
        custom: true
      }
    }
  },
  created () {
    this.paramData.year = this.currentYear
    this.control.view = this.$auth('rdorg:buildList:view')
    this.control.download = this.$auth('rdorg:buildList:download')
    this.control.preview = this.$auth('rdorg:buildList:preview')
    this.initControl(this.isEditStatus(this.auditStatus) || !this.userInfo.userSource)
    this.search()
  },
  computed: {
    ...mapGetters(['auditStatus', 'userInfo'])
  },
  watch: {
    auditStatus: {
      immediate: true,
      handler (value) {
        this.initControl(this.isEditStatus(value) || !this.userInfo.userSource)
      }
    }
  },

  methods: {
    initControl (modify) {
      this.$nextTick(() => {
        this.control.add = this.$auth('rdorg:buildList:add') && modify
        this.control.import = this.$auth('rdorg:buildList:import') && modify
        this.control.edit = this.$auth('rdorg:buildList:edit') && modify
        this.control.del = this.$auth('rdorg:buildList:del') && modify
        this.control.delFile = this.$auth('rdorg:buildList:delModel') && modify
        this.control.upload = this.$auth('rdorg:buildList:upload') && modify
        this.control.setting = this.$auth('rdorg:buildList:setting') && modify
      })
    },
    isEditStatus,
    success (fileName, resData) {
      if (resData) {
        this.$message.success(`${fileName}上传成功`)
        this.search()
      } else {
        this.$message.success(`${fileName}上传失败`)
      }
    },
    openUploadModal (record) {
      this.paramData.listId = record.id
      this.uploadTitle = `上传文件[${record.docName}]`
      this.$refs.uploadModal.show(undefined, record.samplePath)
    },
    delDocList (id) {
      this.$http.post('/docList/del', { id: id })
        .then(res => {
          if (res.data) {
            this.$message.success('删除成功')
            this.search()
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '所选文档项已上传文件，不能删除!')
          }
        })
    },
    delFile (id) {
      this.$http.post('/sysDocument/del', { id: id })
        .then(res => {
          if (res.data) {
            this.$message.success('删除成功')
          } else {
            this.$message.error('删除失败')
          }
        }).finally(res => {
          this.search()
        })
    },
    preview (file) {
      if (file.filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(file.filePath, file.fileName !== undefined ? file.fileName : '')
    },
    downloadFile (file) {
      this.spin = true
      this.$exportData('/sysDocument/downloadFile', { id: file.id }, file.fileName, this.$message).then(res => {
        this.spin = false
      })
    },
    generateBuildFile (row) {
      this.spin = true
      this.$exportData('/sysDocument/generateBuildFile', { year: this.currentYear, docName: row.docName, id: row.id }, undefined, this.$message).then(res => {
        this.spin = false
      })
    },
    editOk (data) {
      if (data) {
        this.tableDatas[data.rowIndex].operators = data.operators
        this.tableDatas[data.rowIndex].docName = data.docName
        this.tableDatas[data.rowIndex].rdActivities = data.rdActivities
      }
    },
    search () {
      this.paramData.year = this.currentYear
      if (!this.control.view) {
        this.tableDatas = []
        this.$message.error('无此权限，请跟管理员联系！')
        return
      }
      this.$nextTick(() => {
        this.spin = true
        this.$http.get('/sysDocument/queryAppendixDocList', { params: { listType: this.listType, year: this.currentYear } })
          .then(res => {
            if (res.success) {
              if (res.data) {
                res.data.forEach(item => {
                  const buildFiles = []
                  const proofFiles = []
                  if (item.docList) {
                    item.docList.forEach(file => {
                      if (file.buildType === 1) {
                        proofFiles.push(file)
                      } else {
                        buildFiles.push(file)
                      }
                    })
                    delete item.docList
                    item.buildFiles = buildFiles
                    item.proofFiles = proofFiles
                  }
                })
                this.tableDatas = res.data
              } else {
                this.tableDatas = []
              }
            } else {
              this.$$message.error(res.errorMessage ? res.errorMessage : '获取文档失败，请联系管理员。')
            }
            return res.data
          }).finally(() => {
            this.spin = false
          })
      })
    }
  }
}
</script>

<style lang="less" scoped>
/deep/ .ant-spin-container {
  height: calc(100vh - 220px);
  flex-direction: column;
  display: flex;
}

/deep/ .table {
  overflow-y: hidden;
  height: 100%;
  flex: 1;
}
</style>
