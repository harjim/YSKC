<template>
  <a-spin :spinning="spinning" >
    <a-layout >
      <a-layout-sider class="bkg" :width="120">
        <a-menu mode="inline" v-model="activeKey" @click="menuClick">
          <a-menu-item v-for="(item,index) in stageDatas" :key="item.stageKey">
            <span>{{ index+1 }}、{{ stagesKV[item.stageKey] }}</span>
          </a-menu-item>
        </a-menu>
      </a-layout-sider>
      <a-layout >
        <a-layout-content class="bkg" >
          <a-row v-if="stageDatas.length ===0" type="flex" justify="center" align="middle">
            <h3>{{ !spinning ? '请先配置阶段' :'' }}</h3>
          </a-row>
          <a-tabs v-else type="card" >
            <a-tab-pane key="1" tab="对接">
              <vxe-grid
                ref="yst"
                auto-resize
                :resizable="true"
                highlight-hover-row
                show-overflow="title"
                show-header-overflow
                export-config
                :data="dockingData"
                height="400px"
              >
                <vxe-table-column type="seq" title="序号" width="50" header-align="center" ></vxe-table-column>
                <vxe-table-column title="资料名" field="itemName" width="150" header-align="center">
                  <template v-slot="{row}">
                    <span v-if="row.required" style="color: red;">*</span>{{ row.itemName }}
                  </template>
                </vxe-table-column>
                <vxe-table-column title="文件格式" field="pattern" width="100" header-align="center"></vxe-table-column>
                <vxe-table-column title="附件" :min-width="400" >
                  <template v-slot="{row}">
                    <span v-for="(file,index) in row.files" :key="index" style="margin-right:10px;">
                      <a title="点击下载" @click="download(row,file)" v-if="$auth('tech:pro:attachment:download')">
                        {{ file.fileName }}
                      </a>
                      <span v-else>
                        {{ file.fileName }}
                      </span>
                      <a-tooltip style="cursor:pointer" placement="top" @click="previewFile(file)" v-if="$auth('tech:pro:attachment:preview')">
                        <template slot="title">
                          <span>预览</span>
                        </template>
                        <a-icon type="eye" style="margin-left:5px" />
                      </a-tooltip>
                      <a-popconfirm
                        title="是否确定删除?"
                        @confirm="del(row,file)"
                        style="color:red;margin-left:5px"
                        v-if="$auth('tech:pro:attachment:del')"
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
                <vxe-table-column title="操作" width="140" v-if="$auth('tech:pro:attachment:upload')" header-align="center">
                  <template v-slot="{ row }">
                    <a-button @click="openUploadModal(row)"> <a-icon type="upload" /> 上传附件 </a-button>
                  </template>
                </vxe-table-column>
              </vxe-grid>
            </a-tab-pane>
            <a-tab-pane key="0" tab="交付">
              <vxe-grid
                ref="yst"
                auto-resize
                :resizable="true"
                highlight-hover-row
                show-overflow="title"
                show-header-overflow
                export-config
                :data="deliverData"
                height="400px"
              >
                <vxe-table-column type="seq" title="序号" width="50" header-align="center"></vxe-table-column>
                <vxe-table-column title="资料名" field="itemName" width="150" header-align="center">
                  <template v-slot="{row}">
                    <span v-if="row.required" style="color: red;">*</span>{{ row.itemName }}
                  </template>
                </vxe-table-column>
                <vxe-table-column title="文件格式" field="pattern" width="100" header-align="center"></vxe-table-column>
                <vxe-table-column title="附件" :min-width="400">
                  <template v-slot="{row}">
                    <span v-for="(file,index) in row.files" :key="index" style="margin-right:10px;">
                      <a title="点击下载" @click="download(row,file)" v-if="$auth('tech:pro:attachment:download')">
                        {{ file.fileName }}
                      </a>
                      <span v-else>
                        {{ file.fileName }}
                      </span>
                      <a-tooltip style="cursor:pointer" placement="top" @click="previewFile(file)" v-if="$auth('tech:pro:attachment:preview')">
                        <template slot="title">
                          <span>预览</span>
                        </template>
                        <a-icon type="eye" style="margin-left:5px" />
                      </a-tooltip>
                      <a-popconfirm
                        title="是否确定删除?"
                        @confirm="del(row,file)"
                        style="color:red;margin-left:5px"
                        v-if="$auth('tech:pro:attachment:del')"
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
                <vxe-table-column title="操作" width="140" v-if="$auth('tech:pro:attachment:upload')" header-align="center">
                  <template v-slot="{ row }">
                    <a-button @click="openUploadModal(row)"> <a-icon type="upload" /> 上传附件 </a-button>
                  </template>
                </vxe-table-column>
              </vxe-grid>
            </a-tab-pane>
          </a-tabs>
        </a-layout-content>
      </a-layout>
      <upload-modal ref="uploadModal" :patternMap="patternMap"></upload-modal>
      <preview-modal url="/techAttachments/preview" ref="previewModal"></preview-modal>
    </a-layout>
  </a-spin>
</template>
<script>
import moment from 'moment'
import { PreviewModal } from '@/components'
import uploadModal from '../uploadModal'
export default {
  name: 'DataListTab',
  components: {
    PreviewModal,
    uploadModal
  },
  created () {
    // 获取阶段字典
    this.$getDictionary(7).then(res => {
      for (const stage of res) {
        this.stagesKV[stage.key] = stage.value
      }
    })
    // 获取文件类型
    this.$getFileTypes().then(res => {
      for (const fileType of res) {
        this.patternMap[fileType.label] = fileType.value
        this.fileTypes.push({ value: fileType.label, label: fileType.label })
      }
      this.patternOptions = this.fileTypes
    })
    this.getDirectionStage().then((res) => {
      this.spinning = false
      if (!res) {
        return
      }
      this.getFileList(res, this.projectId)
    })
  },
  props: {
    directionId: {
      type: [String, Number],
      default: 0,
      require: true
    },
    projectId: {
      type: [String, Number],
      default: 0,
      require: true
    }
  },
  data () {
    return {
      isVisible: false,
      productIds: undefined, // 申报项目id,
      stagesKV: {},
      fileTypes: [],
      patternOptions: [],
      patternMap: {},
      stageDatas: [],
      stageMap: {},
      activeKey: [],
      dockingData: [],
      deliverData: [],
      uploadFiles: undefined,
      spinning: true
    }
  },
  methods: {
    moment,
    // 菜单点击事件
    menuClick ({ item, key, keyPath }) {
      this.activeKey = [key]
      this.dockingData = this.stageMap[this.activeKey[0]].dockingData
      this.deliverData = this.stageMap[this.activeKey[0]].deliverData
      this.getFileList(this.stageMap[key].id, this.projectId)
    },
    // 获取申报项目方向阶段
    getDirectionStage () {
      this.spinning = true
      return this.$http.get('/techAttachments/getDirectionStage', { params: { directionId: this.directionId } }).then((res) => {
        if (res.success && res.data) {
          if (!res.data.length) {
            return Promise.resolve()
          }
          this.stageDatas = res.data.sort((a, b) => a.seq - b.seq)
          const firstStage = this.stageDatas[0]
          this.activeKey[0] = firstStage.stageKey
          for (const stage of this.stageDatas) {
            let dockingData = []
            let deliverData = []
            if (stage.models) {
              dockingData = stage.models.filter((item, index) => {
                this.$set(item, 'files', [])
                return item.itemType === 1
              })
              deliverData = stage.models.filter((item, index) => {
                this.$set(item, 'files', [])
                return item.itemType === 0
              })
            }
            stage['dockingData'] = dockingData
            stage['deliverData'] = deliverData
            this.$set(this.stageMap, stage.stageKey, stage)
            // this.stageMap[stage.stageKey] = stage
          }
          this.dockingData = this.stageMap[firstStage.stageKey].dockingData
          this.deliverData = this.stageMap[firstStage.stageKey].deliverData
          return Promise.resolve(firstStage.id) // 返回stageId
        } else {
          this.$message.error(res.errorMessage)
          this.spinning = false
          return Promise.reject(res.errorMessage)
        }
      }).catch((error) => {
        this.spinning = false
        this.$message.error(error.message)
      })
    },
    // 获取当前选中阶段上传文件列表
    getFileList (stageId, projectId) {
      this.$http.get('/techAttachments/getList', { params: { stageId, projectId } }).then((res) => {
        if (res.data && res.success) {
          this.uploadFiles = res.data
          for (const stage in this.stageMap) {
            const models = this.stageMap[stage].models
            if (models) {
              for (const fileList of models) {
                for (const file in res.data) {
                  if (fileList.id === Number(file)) {
                    const items = res.data[file]
                    fileList.files = [...items]
                  }
                }
              }
            }
          }
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      })
    },
    openUploadModal (record) {
      this.$refs.uploadModal.show(record, this.projectId)
    },
    del (record, file) {
      this.$http.post('/techAttachments/del', file).then((res) => {
        if (res.data && res.success) {
          record.files.forEach((item, index) => {
            if (Number(item.id) === Number(file.id)) {
              record.files.splice(index, 1)
            }
          })
          this.$message.success('操作成功')
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      })
    },
    previewFile (file) {
      if (file.filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(file.filePath, file.fileName !== undefined ? file.fileName : '')
    },
    download (record, file) {
      this.$exportData('/techAttachments/download', { id: file.id, filePath: file.filePath, fileName: file.fileName }, file.fileName, this.$message)
    }
  }
}
</script>
<style lang='less' scoped>
  .bkg {
    background-color: #fff;
  }
  .layout {
    padding-left: 4px;
  }
  .saveDiv {
    display:flex;

  }
  * /deep/ .ant-tabs-card-content {
    height: calc(100% - 57px);
    overflow-y: auto;
  }
  * /deep/ .ant-tabs-tabpane-active {
    overflow-y: auto;
  }
  #tabs /deep/ .ant-tabs-left-bar {
    margin-bottom: 4px;
  }
  .customAddBtn {
    border-bottom-left-radius: 8px;
    border-bottom-right-radius: 8px;
    color: #fff;
    font-size: 18px;
    font-weight: bolder;
    text-align: center;
    background-color: #1890ff;
  }
.customAddBtn:hover {
  background-color: #40A9FF;
  cursor: pointer;
}
</style>
