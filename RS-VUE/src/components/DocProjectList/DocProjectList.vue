<template>
  <a-card :bordered="false" style="height: 100%" :bodyStyle="{ height: '100%', overflow: 'auto'}">
    <a-form layout="inline">
      <select-project ref="creatProject" @projectSelect="projectSelected" :year="currentYear" :sign="1"></select-project>
    </a-form>
    <div id="scrollContent" v-if="view">
      <div v-if="showTable">
        <a-collapse :key="123" :bordered="false" v-if="docList!==[]">
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
              </a-tooltip>-->
              <a-popconfirm
                title="是否确定删除?"
                @confirm="delDocList(n.id)"
                v-if="n.companyId !== 0"
                style="color:red;float:right;margin-right:10px;margin-top:-10px"
              >
                <a-tooltip placement="top">
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
              rowKey="month"
              size="small"
              :dataSource="n.monthModelList"
              :pagination="false"
              bordered
              :showHeader="false"
              style="background: white;"
            >
              <a-table-column title="月份" data-index="month" align="center" :width="100">
                <template slot-scope="text">
                  <span>{{ currentYear+'-'+text }}</span>
                </template>
              </a-table-column>
              <a-table-column title="附件" data-index="deptName" align="left">
                <template slot-scope="text,rec">
                  <span v-for="(d,i) in rec.docList" :key="d.id">
                    <span v-if="downloadDocFile">
                      <a @click="downloadFile(d)">{{ d.fileName }}</a>
                    </span>
                    <span v-else>{{ d.fileName }}</span>
                    <a-tooltip v-if="previewFile" style="cursor:pointer" placement="top" @click="preview(d)">
                      <template slot="title">
                        <span>预览</span>
                      </template>
                      <a-icon type="eye" style="margin-left:5px" />
                    </a-tooltip>
                    <a-popconfirm
                      title="是否确定删除?"
                      @confirm="del(d.id)"
                      style="color:red;margin-left:5px"
                    >
                      <a-tooltip placement="top">
                        <template slot="title">
                          <span>删除</span>
                        </template>
                        <a style="font-size:18px" v-if="delFile">x</a>
                      </a-tooltip>
                    </a-popconfirm>
                    <span v-if="i<rec.docList.length-1">,&nbsp;&nbsp;</span>
                  </span>
                </template>
              </a-table-column>
              <a-table-column title="操作" data-index="key" align="center" :width="120">
                <template slot-scope="text,rec">
                  <span>
                    <a href="javascript:;" v-if="uploadFile" @click="openUploadModal(rec,n)">选择附件</a>
                  </span>
                </template>
              </a-table-column>
            </a-table>
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
  </a-card>
</template>
<script>
import { UploadFile, SelectProject } from '@/components'
import yearMiXin from '@/utils/yearMixin'
import AddDocModal from '../DocList/AddDocModal'

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
    pageName: {
      type: String,
      default: ''
    }
  },
  mixins: [yearMiXin],
  name: 'DocProjectList',
  components: {
    UploadFile,
    SelectProject,
    AddDocModal
  },
  data () {
    return {
      customStyle: 'background: #f7f7f7;border-radius: 4px;margin-bottom: 24px;border: 0;overflow: hidden',
      uploadTitle: '上传附件',
      title: '',
      visible: false,
      docList: [],
      showAdd: true,
      projectId: 0,
      paramData: { fileType: 0, year: this.currentYear, projectType: 1, projectId: this.projectId, month: 0, listId: 0 },
      showTable: true,
      docNameArr: [],
      ids: ['0']
    }
  },
  created () {
    this.paramData.year = this.currentYear
    // this.initialize()
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
    projectSelected (value, project) {
      this.projectId = value
      this.paramData.projectId = value
      if (value === 0) {
        this.showTable = false
      } else {
        this.showTable = true
        this.title = '上传文件[' + this.pageName + '][' + project.pname + ']'
        this.initialize()
      }
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
      // this.initialize()
    },
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
    downloadFile (record) {
      this.$exportData('/sysDocument/downloadFile', { id: record.id }, record.fileName, this.$message)
    },
    downloadTemp (record) {
      this.docNameArr = record.samplePath.split('.')
      const docName = record.docName + '样例文件.' + this.docNameArr[this.docNameArr.length - 1]
      this.$exportData('/docList/downloadTemp', { samplePath: record.samplePath }, docName, this.$message)
    },
    openUploadModal (rec, record) {
      this.paramData.listId = record.id
      this.paramData.month = rec.month
      this.uploadTitle = `${this.title}[${record.docName}][${this.currentYear}-${rec.month}]`
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
    error (fileName, msg) {
      this.$message.error(fileName + '上传失败')
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
      if (this.projectId === 0) {
        return
      }
      this.$nextTick(() => {
        this.$http.get('/sysDocument/queryDocList', { params: { listType: this.listType, projectId: this.projectId } })
          .then(res => {
            if (res.data !== null) {
              res.data.forEach(element => {
                element.oldOperators = element.operators
              })
            }
            this.docList = res.data
            for (let index = 0; index < this.docList.length; index++) {
              const element = this.docList[index]
              this.docList[index].id = this.docList[index].id + ''
              this.$set(this.ids, index, element.id + '')
            }
            return res.data
          })
      })
    }
  }
}
</script>
<style lang="less" scoped>
#scrollContent {
  // padding: 8px;
  // border: 1px solid #e3e3e3;
  // border-radius: 8px;
  height: calc(100% - 39px);
  overflow: auto;
}
</style>
