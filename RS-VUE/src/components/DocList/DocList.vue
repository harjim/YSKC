<template>
  <a-card :bordered="false">
    <div v-if="view">
      <a-collapse
        :key="123"
        defaultActiveKey="activeKey"
        :bordered="false"
        v-if="docList!==[]"
      >
        <a-collapse-panel :key="n.id" :style="customStyle" v-for="(n,index) in docList">
          <template slot="header">
            <span>{{ index+1+'、'+n.docName }}</span>
            <!-- <a-tooltip
              style="cursor:pointer"
              placement="top"
              v-if="n.samplePath!==null && n.samplePath!==''"
            >
              <template slot="title">
                <span>样例文件</span>
              </template>
              <span>&nbsp;&nbsp;</span>
              <a-icon type="cloud-download" style="color:#1A90FF" @click="downloadTemp(n)" />
            </a-tooltip>
            <a @click="downloadTemp(n)">样例文件</a>-->
            <a-popconfirm
              title="是否确定删除?"
              @confirm="delDocList(n.id)"
              v-if="n.companyId !== 0"
              style="color:red;float:right;margin-right:10px;margin-top:-10px"
            >
              <a-tooltip placement="top" >
                <template slot="title">
                  <span>删除</span>
                </template>
                <a style="font-size:16px" v-if="delFileList" @click="(e)=>stopAction(e)">x</a>
              </a-tooltip>
            </a-popconfirm>
            <span v-if="editFile">
              <a-input
                :value="n.operators"
                style="display: inline-block;width:120px;float:right;margin-right:20px;margin-top:-5px"
                @blur="editSubmit(n)"
                @change="(e)=>onCellChange(e.target.value,n,'operators')"
                @click="(e)=>stopAction(e)"
              />
              <span style="float:right;margin-right:10px;">负责岗位:</span>
            </span>
            <span v-else style="float:right;margin-right:10px;">
              负责岗位:{{ n.operators }}
            </span>
          </template>
          <a-table
            :pagination="false"
            rowKey="id"
            ref="table"
            :dataSource="n.docList"
            :showHeader="false"
            bordered
            style="background: white;"
            v-if="n.docList.length>0"
          >
            <a-table-column title="附件" data-index="fileName" align="left">
              <template slot-scope="text,record">
                <span>{{ record.fileName }}</span>
              </template>
            </a-table-column>
            <a-table-column title="上传者" data-index="realName" align="left" :width="120">
              <template slot-scope="text,record">
                <span>{{ record.realName }}</span>
              </template>
            </a-table-column>
            <a-table-column title="上传时间" data-index="createTime" align="left" :width="160" />
            <a-table-column title="下载" data-index="download" align="left" :width="160">
              <template slot-scope="text,record">
                <span v-if="downloadDocFile">
                  <a @click="downloadFile(record)">下载</a>
                  <a-divider type="vertical" v-if="previewFile" />
                </span>
                <a v-if="previewFile" @click="preview(record)">预览</a>
                <a-popconfirm v-if="delFile" title="是否确定删除?" @confirm="del(record.id)">
                  <a-divider type="vertical" v-if="previewFile||downloadDocFile"/>
                  <a>删除</a>
                </a-popconfirm>
              </template>
            </a-table-column>
          </a-table>
          <div class="operate" v-if="uploadFile">
            <a-button type="dashed" style="width: 100%" icon="plus" @click="openUploadModal(n)">上传文件</a-button>
          </div>
        </a-collapse-panel>
      </a-collapse>
      <div class="operate" v-if="addFile">
        <a-button
          type="dashed"
          style="width: 100%"
          icon="plus"
          @click="$refs.addDocModal.add(listType,docList.length)"
        >添加</a-button>
      </div>
    </div>
    <upload-file
      :showProgress="true"
      :paramData="paramData"
      paramKey="tableField"
      :title="uploadTitle"
      ref="uploadModal"
      action="/sysDocument/uploadDocList"
      @onSuccess="success"
      @error="error"
    />
    <add-doc-modal ref="addDocModal" @ok="refreshList"></add-doc-modal>
    <preview-modal ref="previewModal"></preview-modal>
  </a-card>
</template>
<script>
import { UploadFile, PreviewModal } from '@/components'
import yearMiXin from '@/utils/yearMixin'
import AddDocModal from './AddDocModal'

export default {
  props: {
    view: {
      type: [Boolean, Object],
      default: undefined
    },
    addFile: {
      type: [Boolean, Object],
      default: undefined
    },
    uploadFile: {
      type: [Boolean, Object],
      default: undefined
    },
    downloadDocFile: {
      type: [Boolean, Object],
      default: undefined
    },
    previewFile: {
      type: [Boolean, Object],
      default: undefined
    },
    delFile: {
      type: [Boolean, Object],
      default: undefined
    },
    editFile: {
      type: [Boolean, Object],
      default: undefined
    },
    delFileList: {
      type: [Boolean, Object],
      default: undefined
    },
    listType: {
      type: Number,
      default: 0,
      required: true
    },
    patentNo: {
      type: String,
      default: ''
    },
    rdActivitiesColumn: {
      type: Boolean,
      default: true
    },
    operatorsColumn: {
      type: Boolean,
      default: true
    },
    pageName: {
      type: String,
      default: ''
    }
  },
  mixins: [yearMiXin],
  name: 'DocList',
  components: {
    UploadFile,
    PreviewModal,
    AddDocModal
  },
  data () {
    return {
      customStyle: 'border-radius: 4px;margin-bottom: 24px;border:1px solid #E1E5ED;overflow: hidden',
      uploadTitle: '上传附件',
      docList: [{ docName: '', docList: [] }],
      showAdd: true,
      paramData: { fileType: 0, year: this.currentYear, projectType: 1, projectId: 0, listId: 0, patentNo: this.patentNo },
      docNameArr: [],
      ids: ['0']
    }
  },
  created () {
    this.paramData.year = this.currentYear
    this.paramData.patentNo = this.patentNo
    this.initialize()
  },
  methods: {
    stopAction (e) {
      e.stopPropagation()
    },
    refreshList () {
      this.initialize()
    },
    editSubmit (record) {
      if (record.isNew === true) {
        return
      }
      if (record.oldOperators === record.operators) {
        return
      }
      this.$http.post('/docList/editOperators', record)
        .then(res => {
          if (res.data) {
            record.oldOperators = record.operators
          } else {
            this.$message.error('编辑失败')
          }
        })
    },
    delDocList (id) {
      this.$http.post('/docList/del', { id: id })
        .then(res => {
          if (res.data) {
            this.$message.success('删除成功')
            this.initialize()
          } else {
            this.$message.error('所选文档项已上传文件，不能删除!')
          }
        })
    },
    addDocList (record) {
      record.listType = this.listType
      if (record.docName.trim() === '') {
        this.$message.info('请输入文档名称')
        return
      }
      this.$http.post('/docList/addDocList', record)
        .then(res => {
          if (res.data) {
            this.$message.success('添加成功')
            this.initialize()
            this.showAdd = true
          } else {
            this.$message.error('添加失败')
          }
        })
    },
    handleDel (index) {
      this.docList.splice(index, 1)
      this.showAdd = true
    },
    onCellChange (value, record, name) {
      record[name] = value
    },
    handleAdd () {
      if (this.docList === null) {
        this.docList = []
        const newData = {
          id: 0,
          index: 0,
          docName: '',
          rdActivities: '',
          operators: '',
          desciption: '',
          samplePath: '',
          seq: 0 + 1,
          isNew: true
        }
        this.$set(this.docList, 0, newData)
        this.showAdd = false
      } else {
        const newData = {
          id: 0,
          index: this.docList.length,
          docName: '',
          rdActivities: '',
          operators: '',
          desciption: '',
          samplePath: '',
          seq: this.docList.length + 1,
          isNew: true
        }
        this.$set(this.docList, this.docList.length, newData)
        this.showAdd = false
      }
    },
    search () {
      this.paramData.year = this.currentYear
      this.paramData.patentNo = this.patentNo
      this.initialize()
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
    downloadTemp (record) {
      this.docNameArr = record.samplePath.split('.')
      const docName = record.docName + '样例文件.' + this.docNameArr[this.docNameArr.length - 1]
      this.$exportData('/docList/downloadTemp', { samplePath: record.samplePath }, docName, this.$message)
    },
    openUploadModal (record) {
      this.paramData.listId = record.id
      this.uploadTitle = `上传文件[${this.pageName}][${record.docName}]`
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
      this.$message.error(errorMessage)
    },
    del (id) {
      this.$http.post('/sysDocument/del', { id: id })
        .then(res => {
          if (res.data) {
            this.$message.success('删除成功')
          } else {
            this.$message.error('删除失败')
          }
        }).finally(res => {
          this.initialize()
        })
    },
    initialize () {
      this.$nextTick(() => {
        this.$http.get('/sysDocument/queryAppendixDocList', { params: { listType: this.listType, patentNo: this.patentNo, year: this.currentYear } })
          .then(res => {
            if (res.data !== null) {
              res.data.forEach(element => {
                element.oldOperators = element.operators
              })
              this.docList = res.data
              for (let index = 0; index < this.docList.length; index++) {
                const element = this.docList[index]
                this.docList[index].id = this.docList[index].id + ''
                this.$set(this.ids, index, element.id + '')
              }
            } else {
              this.docList = []
            }
            return res.data
          })
      })
    }
  }
}
</script>
<style>
.ant-collapse-header {
  background: #e1e5ed;
}
</style>
