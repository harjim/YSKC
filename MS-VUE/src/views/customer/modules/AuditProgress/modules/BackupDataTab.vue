<!--
 * @Author: ldx
 * @Date: 2021-05-13 09:45:26
 * @LastEditTime: 2022-05-14 08:55:10
 * @LastEditors: zdf
 * @Description: 留存备查资料(邓总要求压缩包显示几个文件)
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\BackupDataTab.vue
-->
<template>
  <TabLayout>
    <template #up>
      <div>
        <a-form layout="inline">
          <a-form-item label="项目">
            <ProjectSelect
              ref="projectSelect"
              :year="record.year"
              :companyId="record.companyId"
              width="450px"
              :sign="0"
              :isDefault="true"
              :isJoinPrjectName="true"
              :auditCount="2"
              mountDom="fullBox"
              @getPrjectIds="OnProjectChange"
            />
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              @click="exportFile"
              :disabled="isDownload || exportFileIds.length <= 0"
              :loading="isDownload"
              v-if="$auth('customer:projectProgress:exportDocFile')"
            > 导出
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              @click="onBatchAudit"
              v-if="$auth('customer:projectProgress:auditBatch')"
              :disabled="batchAuditList.length <= 0"
            > 批量审核
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              @click="onBatchActivate"
              v-if="$auth('customer:projectProgress:activateFlows')"
              :disabled="batchActivateList.length <= 0"
            > 批量激活
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              @click="refresh"
              :disabled="spinning"
            > 刷新
            </a-button>
          </a-form-item>
          <a-form-item v-if="$auth('customer:projectProgress:score')">
            <a-switch
              :checked="showScore"
              checked-children="评分"
              un-checked-children="评分"
              @change="onChangeSwitch"
            />
          </a-form-item>
        </a-form>
      </div>
      <div class="total-wrap">
        <span style="margin-left: 4px;">
          <a-checkbox
            @change="onAllChange"
            :checked="isAllChecked"
            :disabled="isAllDisabled"
            :indeterminate="isIndeterminate"
          /> 全选
        </span>
        <span>文档数:</span> <span
          class="value"
        >{{ currentProject.totalDocNum ? currentProject.totalDocNum : '-'
        }}</span>
        <span class="rejected">可审核:</span>
        <span
          class="value rejected"
        >{{ currentProject.auditDocNum ? currentProject.auditDocNum : '-'
        }}</span>
        <span class="audited">已通过:</span>
        <span
          class="value audited"
        >{{ currentProject.passNum ? currentProject.passNum : '-'
        }}</span>
        <span class="underwayed">已提交:</span>
        <span
          class="value underwayed"
        >{{ currentProject.submitNum ? currentProject.submitNum : '-'
        }}</span>
      </div>
      <div
        v-if="projectId > 0"
        class="doc_wrap"
      >
        <div
          ref="right"
          class="right"
        >
          <div
            v-for="(doc,index) in docs"
            :key="index"
            class="item"
            :class="{'item-active':doc.id === currentActive.id}"
          >
            <a-badge
              v-if="doc.auditStatus !== 5 && doc.id !== 3 && doc.id !== 5 && doc.auditStatus !== 2 && doc.auditStatus !== 3 "
              :numberStyle="{ width:'6px', height: '6px' }"
              :dot="true"
              :offset="[-9,1]"
              :title="doc.hasPermission ? '当前审核人' : statusMap[doc.auditStatus]"
              :color="doc.hasPermission ? '#F5222D': statusColor[doc.auditStatus]"
            >
              <a-checkbox
                @change="e => onCheckChange(e,doc)"
                :checked="doc.checked"
                :disabled="doc.checkedDisabled || doc.fixedDisabled"
              ></a-checkbox>
              {{ index + 1 }}
            </a-badge>
            <span v-else>
              <a-checkbox
                @change="e => onCheckChange(e,doc)"
                :checked="doc.checked"
                :disabled="doc.checkedDisabled || doc.fixedDisabled"
              ></a-checkbox>
              {{ index + 1 }}
            </span>
            . <span
              :title="doc.docName"
              class="title_content"
              @click="onActiveItem(doc)"
            >{{ doc.docName }}</span>
          </div>
        </div>
        <div class="content">
          <div style="position: relative; height: 100%;">
            <div class="menu_icon_box">
              <a-icon
                v-if="projectId"
                :type="isShowSider ? 'menu-fold': 'menu-unfold'"
                @click="expendRight"
              />
            </div>
            <a-spin
              :spinning="spinning"
              style="height: 100%"
              tip="加载中..."
            >
              <div
                v-show="currentActive.doc.docFile && currentActive.doc.id !== 7"
                ref="previewDoc"
                class="preview_doc"
                v-html="currentActive.htmlData"
              ></div>
              <ProjectSummary
                :projectId="currentProject.id"
                :record="record"
                v-if="currentActive.doc.id === 5"
                @loading="loader"
              />
              <NewReport
                v-if="currentActive.doc.id === 7"
                :projectId="currentProject.id"
              />
            </a-spin>
          </div>
        </div>
      </div>
      <BatchAuditModal
        ref="batchAuditModal"
        @refresh="updataStatus"
      />
      <batch-activate-modal
        ref="batchActivateModal"
        @refresh="updataStatus"
      />
      <export-doc-file-modal
        ref="exportDoc"
        @ok="exportOk"
      />
    </template>
    <template #down>
      <AuditLog
        ref="auditLog"
        @updataStatus="updataStatus"
        :full="full"
        :params="currentActive.logGetParams"
        :title="currentActive.file && currentActive.file.docFileName ? currentActive.file.docFileName : ''"
        :active="currentActive.doc.docFileId"
        :currentObj="{companyId: record.companyId, projectId: projectId}"
      />
    </template>
  </TabLayout>
</template>
<script>
import { ProjectSelect } from '@/components/Selects'
import TabLayout from './TabLayout'
import AuditLog from './AuditLog'
import BatchAuditModal from './BatchAuditModal'
import BatchActivateModal from './BatchActivateModal'
import NewReport from './NewReport'
import {
  getBackupData,
  getDocInfo,
  previewFile,
  previewReport
} from '@/api/backupDataTab'
import { statusColor, statusMap } from '@/utils/processDoc/auditStatus'
import ProjectSummary from './ProjectSummary'
import { cloneDeep } from 'lodash'
import ExportDocFileModal from '@/components/DocList/ExportDocFileModal'

const docs = [
  {
    id: 1,
    docFileId: 27,
    file: {},
    owner: false,
    pdocFileId: undefined,
    hasPermission: false,
    checked: false,
    checkedDisabled: true
  },
  {
    id: 2,
    docFileId: 3,
    file: {},
    owner: false,
    pdocFileId: undefined,
    hasPermission: false,
    checked: false,
    checkedDisabled: true
  },
  {
    id: 3,
    docFileId: 0,
    file: {},
    owner: false,
    pdocFileId: undefined,
    hasPermission: false,
    checked: false,
    checkedDisabled: false
  },
  {
    id: 4,
    docFileId: 22,
    owner: false,
    pdocFileId: undefined,
    file: {},
    hasPermission: false,
    checked: false,
    checkedDisabled: true
  },
  {
    id: 5,
    docFileId: 0,
    owner: false,
    pdocFileId: undefined,
    file: {},
    hasPermission: false,
    checked: false,
    checkedDisabled: true,
    fixedDisabled: true
  },
  {
    id: 6,
    docFileId: 33,
    file: {},
    owner: false,
    pdocFileId: undefined,
    hasPermission: false,
    checked: false,
    checkedDisabled: true
  },
  {
    id: 7,
    docFileId: 38,
    file: {},
    owner: false,
    pdocFileId: undefined,
    hasPermission: false,
    checked: false,
    checkedDisabled: true
  },
  {
    id: 8,
    docFileId: 50,
    file: {},
    owner: false,
    pdocFileId: undefined,
    hasPermission: false,
    checked: false,
    checkedDisabled: true
  }
]
export default {
  name: 'BackupDataTab',
  components: {
    ProjectSelect,
    TabLayout,
    AuditLog,
    BatchAuditModal,
    BatchActivateModal,
    ProjectSummary,
    NewReport,
    ExportDocFileModal
  },
  props: {
    record: {
      type: Object,
      default: () => {
        return {}
      }
    },
    mode: {
      type: Object,
      default: () => {
        return {}
      }
    },
    active: {
      type: Number,
      default: 1
    },
    full: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      statusMap,
      statusColor,
      docs,
      resetDocs: docs,
      projectId: -1,
      currentProject: {},
      logGetParams: {},
      isShowSider: true,
      contentSpinning: false,
      spinning: false,
      isAllChecked: false,
      isIndeterminate: false,
      isAllDisabled: false,
      currentActive: {
        id: 1,
        doc: { id: 1, docName: '研发项目计划书', docFileId: 27, docFile: true },
        htmlData: '',
        file: {},
        logGetParams: {
          docFileId: undefined
        }
      },
      exportIds: [],
      isDownload: false,
      chooseFiles: [],
      exportFileIds: [],
      hasProjectReport: false,
      showScore: false
    }
  },
  computed: {
    batchAuditList () {
      return this.chooseFiles.filter(item => item.hasPermission)
    },
    batchActivateList () {
      return this.chooseFiles.filter(item => item.auditStatus === 1 && item.docFileId)
    }
  },
  watch: {
    active (val) {
      if (val === 10) {
        this.OnProjectChange(this.$refs.projectSelect.ids, this.$refs.projectSelect.currentProject)
      }
    },
    projectId (val) {
      if (val > 0) {
        this.isDownload = false
        this.projectChangeResetValue()
        if (this.currentActive.doc.docFile) {
          this.handleGetDocInfo()
        }
      }
    },
    docs: {
      deep: true,
      handler (docs) {
        this.chooseFiles = []
        this.exportFileIds = []
        this.hasProjectReport = false
        docs.forEach(item => {
          if (item.checked && !item.owner && (item.hasPermission || item.auditStatus === 1)) {
            this.chooseFiles.push(item)
          }
          if (item.checked) {
            if (item.docFileId === 27) {
              this.hasProjectReport = true
            }
            this.exportFileIds.push(item.id)
          }
        })
      }
    }
  },
  methods: {
    /**
     * @description: 项目选择框改变回调
     * @param {*} projectId
     * @param {*} currentProject
     * @return {*} null
     */
    OnProjectChange (projectId, currentProject) {
      this.projectId = projectId
      this.currentProject = currentProject
      this.$emit('updateCurrentProject', currentProject)
    },
    /**
     * @description: 项目改变重置相关信息
     * @param {*}
     * @return {*}
     */
    projectChangeResetValue (isClearActiveFile = true) {
      this.isAllChecked = false
      this.isIndeterminate = false
      this.isAllDisabled = false
      this.docs = cloneDeep(this.resetDocs)
      this.chooseFiles = []
      this.exportFileIds = []
      if (isClearActiveFile) {
        this.currentActive = {
          id: 1,
          doc: { id: 1, docName: '研发项目计划书', docFileId: 27, docFile: true },
          htmlData: '',
          file: {},
          logGetParams: {
            docFileId: undefined
          }
        }
      }
    },
    /**
     * @description:  获取过程文档信息
     * @param {*} null
     * @return {*} null
     */
    async handleGetDocInfo () {
      this.spinning = true
      const params = { projectId: this.projectId, year: this.record.year }
      const activeDoc = await this.handlerGetBackupData(this.projectId, this.record.year)
      if (!activeDoc) {
        return
      }
      // this.currentActive.doc = activeDoc
      getDocInfo(params)
        .then((response) => {
          if (response.data && response.success) {
            const backupDataList = response.data
            const backupDataObj = {}
            const { currentActive } = this
            backupDataList.forEach(item => {
              backupDataObj[item.id] = item
            })
            this.docs.forEach(item => {
              const file = backupDataObj[item.docFileId]
              if (file) {
                item['file'] = file
                item['pdocFileId'] = file.docFileId
                item['owner'] = file.owner > 0
                item['auditStatus'] = file.hasSubmit
                item['checkedDisabled'] = false
                item['hasPermission'] = file.hasPermission
              }
            })
            if (backupDataObj[currentActive.doc.docFileId]) {
              currentActive.file = backupDataObj[currentActive.doc.docFileId]
              currentActive.doc = this.docs.filter(item => {
                return item.id === currentActive.id
              })[0]
            }
            return Promise.resolve(true)
          } else {
            return Promise.resolve(false)
          }
        })
        .then((response) => {
          const { file, doc } = this.currentActive
          this.currentActive.logGetParams.docFileId = undefined
          if (doc.id === 3) {
            this.getPreviewReport(this.projectId)
            this.currentActive.logGetParams.docFileId = file.docFileId ? file.docFileId : undefined
            return
          }
          if (doc.id === 7) {
            this.currentActive.logGetParams.docFileId = file.docFileId ? file.docFileId : undefined
            return
          }
          if (response && file && file.docFileId) {
            this.handleGetHtmlData()
            this.currentActive.logGetParams.docFileId = file.docFileId ? file.docFileId : undefined
          } else {
            this.currentActive.htmlData = `<div style="font-size: 1.5em; font-weight: bold; color: red; text-align:center; margin-top: 8%;">文档内容尚未生成！</div>`
            this.spinning = false
          }
        })
        .catch((error) => {
          console.error('[error function handleGetDocInfo]', error)
        })
        .finally(() => {
          const auditCount = this.computedAuditNum(this.docs)
          for (const key in auditCount) {
            if (Object.hasOwnProperty.call(auditCount, key)) {
              const num = auditCount[key]
              this.currentProject[key] = num
            }
          }
          this.spinning = false
        })
    },
    /**
     * @description:  获取过程文档预览内容
     * @param {*} null
     * @return {*} null
     */
    handleGetHtmlData () {
      const params = {
        currentYear: this.record.year,
        companyId: this.record.companyId,
        projectId: this.projectId,
        pDocFileId: this.currentActive.file.docFileId
      }
      previewFile(params).then((response) => {
        if (response.data && response.success) {
          this.currentActive.htmlData = response.data
        }
      }).finally(() => {
        setTimeout(() => {
          this.spinning = false
          this.renderContent()
        }, 300)
      })
    },
    exportFile () {
      this.spinning = true
      this.isDownload = true
      if (this.exportFileIds.length) {
        // this.$exportData(
        //   '/backupData/exportBackupData',
        //   { projectId: this.projectId, chooseFiles: this.exportFileIds, year: this.record.year }, null,
        //   this.$message
        // )
        //   .finally(() => {
        //     this.isDownload = false
        //     this.spinning = false
        //   })
        const datas = {
          url: '/backupData/exportBackupData',
          params: {
            projectId: this.projectId,
            chooseFiles: this.exportFileIds,
            year: this.record.year
          },
          filename: null,
          rdTitle: this.currentProject.rdTitle,
          docName: '留存备资料',
          hasCover: true,
          hasCatalogue: false, // 默认导出是否有目录
          hasBudget: this.hasProjectReport,
          hasUtility: true,
          hasMerge: true
        }
        this.$refs.exportDoc.show(datas)
      } else {
        this.isDownload = false
        this.spinning = false
      }
    },
    exportOk () {
      this.isDownload = false
      this.spinning = false
    },
    onBatchAudit () {
      this.$refs.batchAuditModal.backupDataShow('批量审核留存备查资料', this.batchAuditList, this.projectId)
    },
    onBatchActivate () {
      this.$refs.batchActivateModal.backupDataShow('批量激活留存备查资料', this.batchActivateList, this.projectId)
    },
    updataStatus () {
      this.$emit('updataStatus')
      this.refresh(false)
    },
    refresh (isClearActiveFile) {
      this.projectChangeResetValue(isClearActiveFile)
      if (this.currentActive.doc.docFile) {
        this.handleGetDocInfo()
      }
    },
    loader (isLoader) {
      this.spinning = isLoader
    },
    onAllChange (e) {
      const status = e.target.checked
      this.isAllChecked = status
      this.docs.forEach(item => {
        if (!item.checkedDisabled) {
          item['checked'] = status
        }
      })
      this.isIndeterminate = undefined
    },
    onCheckChange (e, doc) {
      const status = e.target.checked
      doc.checked = status
      const isOnechecked = this.docs.some(item => {
        if (!item.checkedDisabled) {
          return !item.checked
        }
      })
      const isAllNoChecked = this.docs.every(item => {
        return !item.checked
        // }
      })
      if (isOnechecked) {
        this.isAllChecked = false
        this.isIndeterminate = true
        if (isAllNoChecked) {
          this.isIndeterminate = undefined
        }
      } else {
        this.isAllChecked = true
        this.isIndeterminate = undefined
      }
    },
    /**
     * @description:  点击目录文档事件
     * @param {*} null
     * @return {*} null
     */
    onActiveItem (doc) {
      if (this.currentActive.id === doc.id) {
        return
      }
      this.currentActive.id = doc.id
      this.currentActive.doc = doc
      this.currentActive.file = doc.file
      this.currentActive.htmlData = ''
      if (doc.docFile) {
        this.handleGetDocInfo()
      } else {
        this.currentActive.logGetParams.docFileId = undefined
      }
    },
    getPreviewReport (projectId) {
      const params = { projectId, year: this.record.year }
      previewReport(params).then((response) => {
        if (response.data && response.success) {
          this.currentActive.htmlData = response.data
        } else {
          this.currentActive.htmlData = `<div style="font-size: 1.5em; font-weight: bold; color: red; text-align:center; margin-top: 8%;">获取内容出错！</div>`
        }
      })
    },
    /**
     * @description: 切换是否显示侧边栏
     * @param {*} null
     * @return {*} null
     */
    expendRight () {
      this.isShowSider = !this.isShowSider
      const right = this.$refs.right
      if (!this.isShowSider) {
        right.style.width = 0 + 'px'
        right.style.overflowX = 'hidden'
      } else {
        right.style.width = 210 + 'px'
      }
    },
    /**
     * @description: tab切换调用的回调函数
     * @param {*} null
     * @return {*} null
     */
    callBack () {
      this.handleGetDocInfo()
    },
    /**
     * @description: 修改模板图片的端口号
     * @param {*}
     * @return {*}
     */
    renderContent () {
      this.$nextTick(() => {
        const arr = Array.from(this.$refs.previewDoc.getElementsByTagName('img'))
        if (arr.length) {
          arr.forEach(a => {
            a.src = a.src.replace(':9000', ':8000')
          })
        }
      })
    },
    /**
     * @description:  计算审核状态数量
     * @param {*}
     * @return {*}
     */
    computedAuditNum (docs) {
      const auditCount = { auditDocNum: 0, passNum: 0, submitNum: 0 }
      if (docs && docs.length) {
        docs.map(doc => {
          const { file } = doc
          if (file && file.hasPermission) {
            auditCount.auditDocNum++
          }
          if (file && file.hasSubmit === 0) {
            auditCount.submitNum++
          }
          if (file && file.hasSubmit === 1) {
            auditCount.passNum++
          }
        })
      }
      return auditCount
    },
    handlerGetBackupData (projectId, year) {
      return getBackupData({ projectId, year }).then(data => {
        if (data) {
          const docs = data
          const docMap = {}
          docs.map(item => {
            docMap[item.id] = item
          })
          this.docs.forEach(item => {
            const id = item.id
            const doc = docMap[id]
            Object.assign(item, doc)
          })
          const reulst = docMap[this.currentActive.id]
          this.docs.sort((cItem, nItem) => {
            return cItem.seq - nItem.seq
          })
          return Promise.resolve(reulst)
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常,请联系管理员！')
        return Promise.resolve(false)
      })
    },
    onChangeSwitch (checked) {
      this.showScore = checked
      this.$emit('show', checked)
    }
  }
}
</script>
<style
  lang="less"
  scoped
>
@import '../css/DocTab.less';

.item {
  display: flex;
  flex-direction: row;
  font-size: 14px;
  padding: 4px 0px 4px 4px;

  .title_content {
    flex: 1;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  &:hover {
    cursor: pointer;
    background-color: #E6F7FF;
  }
}

.item-active {
  color: #1890ff;
  background-color: #E6F7FF;
}

.total-wrap {
  line-height: 25px;
  font-size: 14px;
  font-weight: 700;

  .value {
    margin: 0 10px;
  }

  .number {
    color: #1890ff;
    padding: 0 5px;
  }

  .noCommit {
    color: #606266;
  }

  .commited {
    color: #9AFF9A;
  }

  .underwayed {
    color: #1890ff;
  }

  .audited {
    color: #548B54;
  }

  .rejected {
    color: #FF4500;
  }
}
</style>
