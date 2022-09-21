<!--
 * @Author: ldx
 * @Date: 2020-12-28 11:38:46
 * @LastEditTime: 2022-09-21 14:01:39
 * @LastEditors: hm
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\modules\ProcessDoc\Index.vue
-->
<template>
  <div style="height: 100%;  border-radius: 10px;">
    <div id="fullBox" style="height: 100%; background-color: white;">
      <a-spin :spinning="allSpinning" style="height: 100%" tip="加载中...">
        <div class="setting_box">
          <div class="btn_box">
            <a-button
              v-if=" $auth('project:report:stage:search') "
              class="btn"
              type="primary"
              @click="showProjectStage"
            >项目阶段
            </a-button>
            <a-button
              v-if="$auth('project:doc:submit') && !isRSUser && stageList.length"
              :disabled="selectedCount <= 0"
              class="btn"
              type="primary"
              @click="showAuditModal"
            >提交审核
            </a-button>
            <a-button
              v-if="$auth('project:doc:changeStage') && stageList.length"
              :disabled="selectedCount <= 0"
              class="btn"
              type="primary"
              @click="showAdjustFileStage"
            >调整文档阶段
            </a-button>
            <a-button
              v-if=" stageList.length && $auth('project:doc:delEmpty') "
              :disabled="selectedCount <= 0"
              class="btn"
              type="primary"
              @click="onBatchDel"
            >删除空文档
            </a-button>
          </div>
          <div v-if="!isRSUser && stageList.length" class="doc_status">
            <div class="noCommit">
              <span class="noCommit">未提交:</span>
              <span>{{ countAudit['5'] ? countAudit['5'] : '-' }}</span>
            </div>
            <div class="underwayed">
              <span class="underwayed">进行中:</span>
              <span>{{ countAudit['0'] ? countAudit['0'] : '-' }}</span>
            </div>
            <div class="audited">
              <span class="audited">已通过:</span>
              <span>{{ countAudit['1'] ? countAudit['1'] : '-' }}</span>
            </div>
            <div class="rejected">
              <span class="rejected">已驳回:</span>
              <span>{{ countAudit['2'] ? countAudit['2'] : '-' }}</span>
            </div>
          </div>
        </div>
        <div v-if="stageList.length" id="wrap_box" class="wrap_box">
          <div id="article_left" class="menu_tree">
            <list-item
              v-for="(stage, index) in stageList"
              :key="stage.id"
              :active.sync="active"
              :brothers.sync="stageList"
              :checkBoxObj.sync="checkBoxObj"
              :currentItem="stage"
              :isFile="false"
              :seq="index"
              :stage="stage"
              :title.sync="stage.stageName"
              v-on="{showDoc,updateFileName,openDocFileModal}"
            />
          </div>
          <div v-show="stageList.length" id="resize" class="middle">
            <div class="icon">⋮</div>
          </div>
          <div id="article_right" class="right_content">
            <a-spin :spinning="spinning" style="height: 100%" tip="加载中...">
              <div class="content_box">
                <div v-if="stageList.length" class="content_title">
                  <div v-if="fileName" class="audit_box">
                    <template
                      v-if="!isEmptyTemplate && !isRSUser && !isFinance(active.file.owner)">
                      状态:
                      <span
                        :style="{ color: statusColor[active.file.status] }"
                        class="file_status"
                      >{{ statusMap[active.file.status] }}</span>
                      <a-button
                        v-if="$auth('project:doc:submit') && isEditStatus(active.file.status) && (isPreviewDoc || onlySaveForms.includes(active.file.templateName) ||active.file.templateName === 'NewReportForm')"
                        :disabled="!isCommit || active.file.needEdit || !active.file.finished"
                        :loading="confirmLoading"
                        size="small"
                        title="提交"
                        type="primary"
                        @click="submitOneDocBtn"
                      >提交
                      </a-button>
                    </template>
                  </div>
                  <div class="title">{{ fileName }}</div>
                  <div class="screen_icon">
                    <template
                      v-if="$auth('project:doc:edit') && isShowRightBtn && !isEmptyTemplate && !isFinance(active.file.owner)"
                    >
                      <a-button
                        v-if="!onlySaveForms.includes(active.file.templateName) && active.file.templateName !== 'NewReportForm' && !isPreviewDoc && ((isRSUser || isEditStatus(active.file.status)))"
                        :loading="confirmLoading"
                        size="small"
                        style="margin-right: 8px;"
                        title="返回"
                        @click="handleBack"
                      >返回
                      </a-button>
                      <a-button
                        v-if="active.file.templateName !== 'NewReportForm' && !isPreviewDoc && ((isRSUser || isEditStatus(active.file.status)))"
                        :disabled="!isSave"
                        :loading="confirmLoading"
                        size="small"
                        style="margin-right: 8px;"
                        title="保存"
                        type="primary"
                        @click="handleSaveData"
                      >保存
                      </a-button>
                      <a-button
                        v-if="isPreviewDoc && (isRSUser || isEditStatus(active.file.status)) && active.file.templateName !== 'PDataListForm'"
                        size="small"
                        style="margin-right: 8px;"
                        title="编辑"
                        type="primary"
                        @click="switchBtn(false)"
                      >编辑
                      </a-button>
                    </template>
                    <template
                      v-if="isShowRightBtn && !isEmptyTemplate && $auth('project:doc:export')"
                    >
                      <a-button
                        v-if=" isPreviewDoc && ( isRSUser || isExportStatus(active.file.status) || isFinance(active.file.owner))"
                        size="small"
                        style="margin-right: 8px;"
                        title="导出"
                        type="primary"
                        @click="exportFile"
                      >导出
                      </a-button>
                      <a-button
                        :title="isFullscreen ? '还原' : '全屏'"
                        icon="fullscreen"
                        size="small"
                        @click="full()"
                      ></a-button>
                    </template>
                  </div>
                </div>
                <div v-if="stageList.length" class="content">
                  <div class="left_box">
                    <!-- :entroy="false" // 进入方式（从哪里进） true： 备查资料； false: 过程文档 -->
                    <component
                      :is="compName"
                      v-if="!isPreviewDoc"
                      ref="comp"
                      :callbackEvent="handleSaveData"
                      :dataMonth="active.file.dataMonth"
                      :docFile="active.file"
                      :docFileId="active.file.docFileId"
                      :docId="active.currentId"
                      :entory="false"
                      :isEdit="isEditStatus(active.file.status)"
                      :project="project"
                      :projectId="project.id"
                      :stage="active.stage"
                      class="preview_width"
                      v-on="{onControlSaveBtn,onControlCommitBtn}"
                    />
                    <div
                      v-show="!spinning && isPreviewDoc"
                      :class="{preview_width: isPreviewDoc}"
                      v-html="previewHtml"
                    ></div>
                  </div>
                  <div
                    v-if="!isRSUser && active.file.suggestion && stageList.length"
                    class="right_box"
                  >
                    <div
                      class="expendRight"
                      title="点击查看审核意见"
                      @click="expendRight">
                      审批意见
                      <a-icon
                        v-if="isAuditLog"
                        style="color:#1890FF;"
                        type="right" />
                      <a-icon
                        v-if="!isAuditLog"
                        style="color:#1890FF;"
                        type="left" />
                    </div>
                    <div ref="auditLog" class="audit_log">
                      {{ active.file.suggestion }}
                    </div>
                  </div>
                </div>
              </div>
            </a-spin>
          </div>
        </div>
        <div
          v-if="isShowAlert"
          calss="alert_wrap"
          style="height: calc(100% - 42px);">
          <div class="alert_section">
            <a-alert
              message="该项目未配置阶段，请先设置阶段。"
              show-icon
              type="warning" />
          </div>
        </div>
        <doc-file-modal ref="docFileModal" @ok="showDoc(active,false)" />
        <!-- 审核modal -->
        <audit-modal
          ref="auditModal"
          :currentYear="currentYear"
          @refresh="showDoc(active,false)"></audit-modal>
        <stage-modal
          ref="stageModal"
          @onChangeRefresh="() => this.$emit('onChangeRefresh')"
          @refresh="StageModalrefresh"
        ></stage-modal>
        <!-- 调整modal -->
        <adjust-file-stage-modal
          ref="adjustFileStageModal"
          :stageList="stageList"
          @refresh="showDoc(active,false)"
        ></adjust-file-stage-modal>
        <AddMultipleFileConfirmModal
          ref="addMultipleFileConfirmModal"
          @onAddMultipleFile="onAddMultipleFile"
        ></AddMultipleFileConfirmModal>
        <export-doc-file-modal ref="exportDoc" @ok="exportOk" />
      </a-spin>
    </div>
  </div>
</template>

<script>
import DocFileModal from '../DocFileModal'
import screenfull from 'screenfull'
import ListItem from './modules/ListItem'
import { mapActions, mapGetters, mapState } from 'vuex'
import allComp from '@/docTemplate/Templates'
import {
  deleteDocFiles,
  getAllStage,
  getDocData,
  saveData
} from '@/api/doc/index'
// import { isEmpty, trim, isString, cloneDeep } from 'lodash'
import { cloneDeep } from 'lodash'
import moment from 'moment'
import {
  getStatusName,
  isEditStatus,
  isExportStatus,
  isFinance,
  statusColor,
  statusMap
} from '@/utils/processDoc/auditStatus'
import AuditModal from './modules/AuditModal'
import StageModal from './modules/StageModal'
import AdjustFileStageModal from './modules/AdjustFileStageModal.vue'
import AddMultipleFileConfirmModal
  from './modules/AddMultipleFileConfirmModal.vue'
import ExportDocFileModal from '@/components/DocList/ExportDocFileModal'
import { isEmpty } from '@/utils/util'

const devTest = localStorage.getItem('dev_test') === 'true'
const previewUrl = devTest ? '/doc/rdfile/preview' : '/projectDocFileData/previewFile'
const exportUrl = devTest ? '/doc/rdfile/export' : '/projectDocFileData/exportPdf'
const exportMultiUrl = devTest ? '/doc/rdfile/export' : '/projectDocFileData/exportAllDoc'
const exportFormat = devTest ? '.docx' : '.pdf'
const pDocFileId = devTest ? 'pDocFileId' : 'pDocFileIds'
export default {
  name: 'Index',
  components: {
    ListItem,
    DocFileModal,
    AuditModal,
    StageModal,
    AdjustFileStageModal,
    AddMultipleFileConfirmModal,
    ...allComp,
    ExportDocFileModal
  },
  props: {
    project: {
      type: Object,
      required: true
    }
  },
  data () {
    const onlySaveForms = ['AppendixForm', 'TrustAppendixForm', 'TrustContractForm', 'TrustProjectSummaryForm']
    return {
      isRSUser: true, // 是否RS用户登录
      statusMap, // 审核状态名称
      statusColor, // 审核状态颜色
      stageList: [], // 阶段及文件集合
      existingStages: [], // 记录当前项目有什么阶段
      compName: '', // 组件名称
      fileName: '', // 文档名称
      fileDataId: undefined,
      confirmLoading: false,
      isFullscreen: false,
      isEnabledFullscreen: false,
      countAudit: {}, // 审核状态的统计数据
      isShowRightBtn: false,
      isPreviewDoc: true, // 是否时预览界面
      spinning: false, // 文档内容区域的loading
      allSpinning: false, // 整个组件区域的loading
      previewHtml: '', // 预览内容
      isAuditLog: false, // 是否显示审核信息
      checkBoxObj: {}, // 复选框对象
      checkedFiles: [], // 提交审核选中的文档
      selectedFiles: [], // 选中的文档
      selectedCount: 0,
      isEmptyTemplate: false,
      isShowAlert: false,
      isSave: true,
      isCommit: true,
      active: { // 记录选中的文件
        currentId: -1,
        editId: -1,
        stage: {},
        file: {},
        project: {}
      },
      onlySaveForms
    }
  },
  mounted () {
    this.isRSUser = !this.userInfo.userSource
    this.isEnabledFullscreen = screenfull.isEnabled
    this.active.project = this.project
    if (this.project.id) {
      this.showDoc(undefined, true)
    }
  },
  computed: {
    ...mapState(['currentYear']),
    ...mapGetters(['userInfo']),
    commitBtn () {
      return ''
    }
  },
  watch: {
    project: {
      deep: true,
      handler (value) {
        if (value.id > 0) {
          this.showDoc(undefined, true)
        }
      }
    },
    'active.currentId' (val) {
      if (val > 0) {
        this.handleHowToDisplay(this.active, false)
      }
    },
    checkBoxObj: {
      handler (val) {
        const temp = []
        this.selectedFiles = []
        for (const key in val) {
          if (Object.hasOwnProperty.call(val, key)) {
            const obj = val[key]
            if (!obj.isStage && obj.checked) {
              temp.push(obj)
              this.selectedFiles.push(obj)
            }
          }
        }
        this.selectedCount = temp.length
        this.$emit('getSelectedCount', this.selectedCount)
      },
      deep: true
    }
  },
  methods: {
    ...mapActions(['setFile', 'setSelectedProject', 'setDocId', 'setProjectId', 'setStage']),
    isEditStatus,
    isExportStatus,
    getStatusName,
    isFinance,
    // getFileDate (fileDate) {
    //   this.fileDate = fileDate
    // },
    /**
     * @description:  控制有月份文档否禁用保存按钮
     * @param {*} status
     * @return {*}
     */
    onControlSaveBtn (status) {
      this.isSave = status
    },
    /**
     * @description: 控制提交按钮是否禁用
     * @param {*} status
     * @return {*}
     */
    onControlCommitBtn (status) {
      if (this.onlySaveForms.includes(this.active.file.templateName) || this.active.file.templateName === 'NewReportForm') {
        if (this.active.file.needEdit) {
          this.active.file.needEdit = !status
        }
        this.isCommit = status
      }
      this.isCommit = status
    },
    /**
     * @description: 全屏
     * @param {*} null
     * @return {*} null
     */
    full () {
      if (!this.isEnabledFullscreen) {
        this.$message.warning('您现在使用的浏览器不支持全屏显示！')
        return
      }
      const element = document.getElementById('fullBox')
      screenfull.toggle(element)
      this.isFullscreen = !this.isFullscreen
    },
    updateFileName (fileName) {
      this.fileName = fileName
    },
    /**
     * @description:  打开文档编辑modal
     * @param {*} stage
     * @return {*}
     */
    openDocFileModal (stage) {
      let templateIds = []
      const chooseFiles = []
      const chooseFileIds = []
      if (stage.projectDocList && stage.projectDocList.length) {
        const set = new Set()
        stage.projectDocList.forEach(file => {
          set.add(file.docFileId)
          if (this.checkBoxObj[file.id].checked) {
            chooseFiles.push({
              id: file.id,
              checked: this.checkBoxObj[file.id].checked,
              name: file.docFileName
            })
            chooseFileIds.push(file.id)
          }
        })
        templateIds = Array.from(set)
      }
      if (chooseFileIds.length) {
        this.$refs.addMultipleFileConfirmModal.show(chooseFiles, stage)
      } else {
        this.$refs.docFileModal.add(stage.projectId, stage, templateIds, chooseFileIds)
      }
    },
    onAddMultipleFile (chooseFiles, stage) {
      let templateIds = []
      if (stage.projectDocList && stage.projectDocList.length) {
        const set = new Set()
        stage.projectDocList.forEach(file => {
          set.add(file.docFileId)
        })
        templateIds = Array.from(set)
      }
      const chooseFileIds = []
      chooseFiles.forEach(file => {
        if (file.checked) {
          chooseFileIds.push(file.id)
        }
      })
      this.$refs.docFileModal.add(stage.projectId, stage, templateIds, chooseFileIds)
    },
    /**
     * @description: 显示文档信息
     * @param {*} activeFile 当前活动的对象
     * @param {*} isFirst 是否首次加载
     * @return {*}
     */
    async showDoc (active = undefined, isFirst = false) {
      this.isShowAlert = false
      this.allSpinning = true
      this.resetDocBaseData()
      const stageList = this.stageList = await this.initStage()
      if (!(stageList && stageList.length)) { // 没阶段
        this.isShowAlert = true
        this.allSpinning = false
        return
      }
      // 下面保证有阶段列表
      const { file, stage } = this.getPrivewDocInfo(isFirst, stageList, active)
      if (!file) {
        this.transFormCurrentObject()
        this.getCountDocAuditData()
        this.$nextTick(this.reSize)
        this.allSpinning = false
        return
      }
      this.transFormCurrentObject()
      this.getCountDocAuditData()
      this.setPrivewDocInfo(file, stage)
      this.$nextTick(this.reSize)
      this.allSpinning = false
    },
    async initStage () {
      const { currentYear } = this
      return getAllStage({
        projectId: this.project.id,
        year: currentYear
      }).then(response => {
        if (response.success && response.data && response.data.length) {
          const result = response.data.map(item => {
            return {
              ...item,
              expand: true
            }
          })
          return Promise.resolve(result)
        } else {
          return Promise.resolve([])
        }
      }).catch(error => {
        this.$message.error('获取阶段信息出错')
        console.error('initStage function error：' + error.message || '获取阶段信息出错')
        return Promise.resolve([])
      })
    },
    /**
     * @description: 获取回显文档信息
     * @param {*} isFirst 是否第一次
     * @param {*} stageList 阶段集合
     * @param {*} activeFile 当前使用的文档信息
     * @return {*}
     */
    getPrivewDocInfo (isFirst, stageList, active) {
      const result = { file: undefined, stage: undefined }
      if (!isFirst && active) {
        let stage, file
        const newStage = stageList.find(stage => {
          return stage.id === active.stage.id
        })
        if (newStage && newStage.projectDocList.length) {
          stage = newStage
          file = newStage.projectDocList.find(file => {
            return file.id === active.file.id
          })
          result.file = file || newStage.projectDocList[0]
          result.stage = stage
          if (result.file) {
            return result
          }
        }
      }
      const stage = stageList.find(item => {
        return item.projectDocList && item.projectDocList.length
      })
      result.file = stage ? stage.projectDocList[0] : undefined
      result.stage = stage
      return result
    },
    /**
     * @description: 设置预览文档信息
     * @param {*} file
     * @param {*} stage
     * @return {*}
     */
    setPrivewDocInfo (file, stage) {
      this.active.currentId = file.id
      this.active.file = file
      this.active.stage = stage
      this.active.project = this.project
      this.compName = file.templateName
      this.fileName = file.docFileName
    },
    /**
     * @description:  处理怎么显示文档
     * @param {*} isSaveAction 是否保存操作 默认是false
     * @return {*}
     */
    handleHowToDisplay (active = this.active, isSaveAction = false) {
      if (isSaveAction) {
        this.$set(active.file, 'needEdit', false)
      }
      this.isCommit = true
      this.isSave = true
      if (this.onlySaveForms.includes(this.active.file.templateName) || active.file.templateName === 'NewReportForm') {
        this.isPreviewDoc = false
        this.initCompData(active.currentId)
        return
      }
      if (active.file.needEdit && this.isEditStatus(active.file.status)) {
        this.isPreviewDoc = false
        this.initCompData(active.currentId)
      } else {
        this.isPreviewDoc = true
        this.gethtml()
      }
    },
    /**
     * @description:  获取过程文件数据
     * @param {*} docId
     * @return {*} null
     */
    initCompData (docId) {
      if (docId < 0) {
        return
      }
      this.spinning = true
      const { file, stage } = this.active
      const tempName = this.compName = this.handleUndetermined(file) // 处理待定模板
      if (tempName === 'empty') {
        this.fileName = file.docName
        this.compName += 'Template'
        return
      }
      this.fileName = file.docFileName
      const fileInfo = {
        docName: this.fileName,
        docId: file.id,
        formName: file.templateName,
        stageKey: stage.stageKey,
        stageType: stage.stageType,
        currentYear: this.currentYear,
        saveDocId: null,
        docFileId: null
      }
      getDocData({
        pDocFileId: docId,
        projectId: this.project.id
      }).then(res => {
        const data = res.data
        const compSync = this.$refs.comp
        this.fileDataId = data.id ? data.id : undefined // 保存文件的id
        if (compSync.fileInfo) { // 给组件赋文件信息数据
          fileInfo.saveDocId = this.fileDataId
          fileInfo.docFileId = this.active.file.docFileId
          compSync.fileInfo = fileInfo
        }
        if (compSync.project) { // 给组件赋项目对象数据
          Object.assign(compSync.project, this.project)
        }
        if (compSync.BPContent) { // 重置组件content内容
          compSync.content = cloneDeep(compSync.BPContent)
        }
        if (compSync.employeeMap && data) {
          Object.assign(compSync.employeeMap, data.employeeMap)
        }
        if (compSync.publicInfo && data) {
          Object.assign(compSync.publicInfo, data)
        }
        if (data && compSync.hasOwnProperty('fileDate')) {
          compSync.fileDate = moment(data.docDate)
          compSync.BUfileDate = cloneDeep(data.docDate)
        }
        if (data && data.data) { // 给组件赋表单内容数据
          const tempContent = JSON.parse(data.data)
          // 处理试验试制通知单，的试验试制时间 （将老数据改为新的字段保存） 2021-08-14
          if (fileInfo.formName === 'TrialProductionNoticeForm') {
            if (tempContent.pilotTime) {
              if (tempContent.trialDates) {
                tempContent.trialDates.push(tempContent.pilotTime)
              } else {
                tempContent.trialDates = [tempContent.pilotTime]
              }
            }
            if (tempContent.endPilotTime && tempContent.pilotTime !== tempContent.endPilotTime) {
              if (tempContent.trialDates) {
                tempContent.trialDates.push(tempContent.endPilotTime)
              } else {
                tempContent.trialDates = [tempContent.endPilotTime]
              }
            }
          }
          // 立项报告图片处理,其实应当是写到立项报告组件当中去的
          if (this.active.file.templateName === 'ProjectReportForm' && tempContent.img) {
            for (const imgKey in tempContent.img) {
              let tHtml = tempContent[imgKey] ? tempContent[imgKey] : ''
              if (tempContent.img[imgKey].length > 0) {
                for (let i = 0; i < tempContent.img[imgKey].length; i++) {
                  tHtml = tHtml + `<br/><img width="170" src="/static${tempContent.img[imgKey][i].url}" />`
                }
              }
              tempContent[imgKey] = tHtml
            }
            // 清除本来的img属性
            delete tempContent.img
          }
          Object.assign(compSync.content, tempContent)
        }
      }).catch((error) => {
        this.$message.error('请求接口出错!', error)
      }).finally((res) => {
        const compSync = this.$refs.comp
        if (compSync.handleTemplateEvent) { // 统一将模板里面要执行的函数（事件）,放到此函数调用 PS:此事件最好不要带参数
          compSync.handleTemplateEvent()
        }
        this.spinning = false
      })
    },

    /**
     * @description: 保存过程材料
     * @param {*} null
     * @return {*} null
     */
    handleSaveData () {
      if (this.confirmLoading) {
        return
      }
      this.spinning = true
      this.confirmLoading = true
      const content = this.$refs.comp.content
      // 必填项校验
      const finished = this.validateRequiredFields(content)
      // 处理试验试制通知单，的试验试制时间 （将老数据改为新的字段保存） 2021-08-14
      if (this.active.file.templateName === 'TrialProductionNoticeForm') {
        delete content.pilotTime
        delete content.endPilotTime
      }
      const cStr = JSON.stringify(content)
      const fileDate = this.$refs.comp.fileDate ? this.$refs.comp.fileDate.startOf('month') : undefined
      // 1表示项目建议书 3表示立项决议 1和3文档都不在项目日期范围内不用传月份
      // const docFileIds = [1, 31, 15, 13, 34, 38, 24, 26, 27, 29, 30, 5, 20]
      const docFileIds = [1, 3, 31, 13, 34, 38, 24, 26, 27, 29, 5, 20, 30]
      let month
      if ((docFileIds.includes(this.active.file.docFileId)) || this.active.file.templateName === 'ResolutionForm' || this.$refs.comp.hsaFileDataNull) { // 文档日期不用指定日期的不传日期
        month = undefined
      } else {
        if (this.active.file.docFileId === 53) {
          month = moment(this.$refs.comp.content.hostTime).startOf('month')
        } else {
          month = fileDate
        }
      }
      const postData = {
        pDocFileId: this.active.currentId,
        projectId: this.project.id,
        data: cStr,
        month,
        wordLength: 0,
        filledItems: 0,
        totalItems: 0,
        finished
      }

      saveData(postData).then(res => {
        if (res.success && res.data) {
          this.confirmLoading = false
          this.spinning = false
          this.$set(this.active.file, 'needEdit', false)
          this.$set(this.active.file, 'deleteLogo', false)
          this.$set(this.active.file, 'finished', finished)
        } else {
          this.$message.error(res.errorMessage || '操作失败!')
        }
      }).finally(() => {
        this.confirmLoading = false
        this.spinning = false
      })
    },
    /**
     * @description:  切换预览
     * @param {*} boolean
     * @return {*} null
     */
    switchBtn (boolean) {
      this.isPreviewDoc = boolean
      if (this.isPreviewDoc) {
        this.gethtml()
      } else {
        this.initCompData(this.active.currentId)
      }
    },
    /**
     * @description:  返回
     * @param {*} null
     * @return {*} nuul
     */
    handleBack () {
      this.isPreviewDoc = true
      this.gethtml()
    },
    /**
     * @description: 处理阶段带日期
     * @param {*} stage
     * @return {*} String
     */
    stageStr (stage) {
      let str = ''
      str += stage.stageType
      if (stage.beginDate) {
        str += stage.beginDate + '~'
      }
      if (stage.endDate) {
        str += stage.endDate
      }
      return str
    },
    /**
     * @description:导出过程材料
     * @param {*} null
     *@return {*} null
     */
    exportFile () {
      this.$emit('OnSpinLoad', true)
      const datas = {
        url: exportUrl,
        params: {
          pDocFileId: this.active.currentId,
          docName: this.fileName,
          projectId: this.project.id,
          currentYear: this.currentYear
        },
        filename: this.userInfo.companyName + '-' + this.project.rdTitle + '-' + this.project.pname + this.fileName + exportFormat,
        rdTitle: this.project.rdTitle,
        docName: this.fileName,
        hasBudget: this.active.file && this.active.file.docFileId === 27,
        hasCover: true
      }
      this.$refs.exportDoc.show(datas)
    },
    /**
     * @description: 导出过程文件
     */
    exportAllFile () {
      const self = this
      const ids = []
      let hasBudget = false
      let docName = ''
      for (const key in this.checkBoxObj) {
        const obj = this.checkBoxObj[key]
        if (obj.checked && !obj.isStage) {
          if (!this.isRSUser) {
            if (isExportStatus(obj.currentObj.status) || obj.currentObj.owner > 0) {
              if (obj.currentObj.docFileId === 27) {
                hasBudget = true
              }
              ids.push(obj.currentObj.id)
            } else {
              docName += obj.currentObj.docFileName + '; '
            }
          } else {
            if (obj.currentObj.docFileId === 27) {
              hasBudget = true
            }
            ids.push(obj.currentObj.id)
          }
        }
      }
      if (!ids.length) {
        this.$confirm({
          title: '您选中的文档全部是未审核通过的文档，系统只会导出已经审核通过的文档！',
          onOk () {
            self.$emit('OnSpinLoad', false)
          },
          onCancel () {
            self.$emit('OnSpinLoad', false)
          }
        })
      } else {
        if (docName) {
          this.$confirm({
            title: '您选中了,包含未审核通过的文档，系统只会导出已经审核通过的文档！',
            content: '不会导出的文档：' + docName,
            onOk () {
              self.handleExportAllFile(ids, hasBudget)
            },
            onCancel () {
              self.$emit('OnSpinLoad', false)
            }
          })
        } else {
          this.handleExportAllFile(ids, hasBudget)
        }
      }
    },
    handleExportAllFile (ids, hasBudget) {
      const datas = {
        url: exportMultiUrl,
        params: {
          projectId: this.project.id,
          // pDocFileIds: ids,
          currentYear: this.currentYear
        },
        filename: this.userInfo.companyName + '-' + this.project.rdTitle + '-' + this.project.pname + '过程文件' + exportFormat,
        rdTitle: this.project.rdTitle,
        hasCatalogue: true,
        hasBudget,
        hasCover: true
      }
      datas.params[pDocFileId] = ids
      this.$refs.exportDoc.show(datas)
    },
    exportOk () {
      this.spinning = false
      this.$emit('OnSpinLoad', false)
    },
    /**
     * @description: 处理待定模板
     * @param {*} file
     * @return {*} String
     */
    handleUndetermined (file) {
      const undeterminedTemplate = [
        '工作分解结构',
        '项目人员组织架构',
        '项目研究报告',
        '研发试制产量计划',
        '试验证报告',
        '进度管理表',
        '项目年度投入经费总结'
      ]
      if (undeterminedTemplate.includes(file.docName)) {
        return 'empty'
      } else {
        return file.templateName
      }
    },
    /**
     * @description:  获取后台传过来的HTML
     * @param {*} null
     * @return {*} nuul
     */
    gethtml () {
      const { file } = this.active
      this.fileName = file.docFileName
      this.spinning = true
      const paramObj = {
        pDocFileId: this.active.currentId,
        projectId: this.project.id,
        currentYear: this.currentYear,
        companyId: this.userInfo.companyId
      }
      this.$http.get(previewUrl, { params: paramObj }).then((res) => {
        if (res.data && res.success) {
          this.previewHtml = res.data
          this.isEmptyTemplate = false
        } else {
          this.isEmptyTemplate = true
          this.previewHtml = `<div style="font-size: 1.5em; font-weight: bold;">${res.errorMessage}</div>`
          // this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error('请求接口错误!' + error.message)
        console.error('请求接口错误:' + previewUrl, paramObj, error.message)
      }).finally((res) => {
        setTimeout(() => { // 处理加载checkBoxcss延迟问题
          this.spinning = false
        })
      })
    },
    /**
     * @description: 审核日志
     * @param {*}
     * @return {*}
     */
    expendRight () {
      const auditLog = this.$refs.auditLog
      if (this.isAuditLog) {
        auditLog.style.width = 0
        auditLog.style.paddingLeft = 0
        auditLog.style.paddingRight = 0
        auditLog.style.paddingTop = 0
        auditLog.style.paddingBottom = 0
      } else {
        auditLog.style.width = 200 + 'px'
        auditLog.style.paddingLeft = 10 + 'px'
        auditLog.style.paddingRight = 10 + 'px'
        auditLog.style.paddingTop = 5 + 'px'
        auditLog.style.paddingBottom = 5 + 'px'
        auditLog.style.paddingBottom = 5 + 'px'
        auditLog.style.overflowY = 'auto'
        auditLog.style.overflowX = 'auto'
      }
      this.isAuditLog = !this.isAuditLog
    },
    /**
     * @description: 提交单个文档审核二次确认框
     * @param {*} null
     * @return {*} null
     */
    submitOneDocBtn () {
      const self = this
      this.$confirm({
        title: '您确认提交?',
        onOk () {
          self.handleSubmitOneDoc()
        }
      })
    },
    /**
     * @description:  提交单个文档审核
     * @param {*} null
     * @return {*} null
     */
    handleSubmitOneDoc () {
      const paramObj = {
        projectId: this.project.id,
        docFileId: [this.active.file.id],
        year: this.currentYear
      }
      this.$http.post('/projectAudit/submitDocAudit', paramObj).then((res) => {
        if (res.data && res.success) {
          this.showDoc(this.active, false)
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      })
    },
    /**
     * @description:  获取文档审核状态的统计数量
     * @param {*} null
     * @return {*} null
     */
    getCountDocAuditData () {
      if (this.isRSUser) {
        return
      }
      this.countAudit = {}
      this.$http.get('/projectAudit/countDocAuditData', { params: { projectId: this.project.id } }).then((res) => {
        if (res.data && res.success) {
          this.countAudit = res.data
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally((res) => {
      })
    },
    /**
     * @description:  转化当前选中的对象和统计文档ids
     * @param {*} null
     * @return {*} null
     */
    transFormCurrentObject () {
      const docIds = []
      this.checkBoxObj = {}
      this.existingStages = [] // 统计当前项目存在的阶段
      this.$set(this, 'checkedFiles', [])
      this.stageList.forEach((stage) => {
        this.existingStages.push({
          label: stage.stageName ? stage.stageName : stage.stageType,
          value: stage.stageKey
        })
        this.$set(this.checkBoxObj, stage.id, {
          checked: false,
          isIndeterminate: false,
          isStage: true,
          currentObj: stage
        })
        if (stage.projectDocList && stage.projectDocList.length) {
          stage.projectDocList.forEach((item) => {
            if (!isFinance(item.owner)) {
              this.checkedFiles.push(item)
            }
            docIds.push(item.id)
            this.$set(this.checkBoxObj, item.id, {
              checked: false,
              isIndeterminate: false,
              isStage: false,
              currentObj: item
            })
          })
        }
      })
      this.isShowRightBtn = docIds.length > 0
      this.$emit('getDocIds', docIds)
    },
    /**
     * @description: 重置界面信息
     * @param {*} null
     * @return {*} null
     */
    resetDocBaseData () {
      this.active = {
        currentId: -1,
        editId: -1,
        stage: {},
        file: {},
        project: {}
      }
      this.isCommit = true
      this.isSave = true
      this.compName = ''
      this.fileName = ''
      this.previewHtml = ''
    },
    /**
     * @description: 打开提交审核modal
     * @param {*} null
     * @return {*} null
     */
    showAuditModal () {
      this.checkedFiles.forEach(item => {
        const obj = this.checkBoxObj[item.id]
        if (!obj.isStage && obj.checked && isEditStatus(obj.currentObj.status) && obj.currentObj.finished) {
          item['isSelected'] = obj.checked
        } else {
          item['isSelected'] = false
        }
      })
      this.$refs.auditModal.show(`提交审核【${this.project.rdTitle}-${this.project.pname}】`, this.checkedFiles, this.project.id)
    },
    /**
     * @description: 显示设置项目阶段modal
     * @param {*} null
     * @return {*} null
     */
    showProjectStage () {
      this.$refs.stageModal.show('阶段设置', this.project)
    },
    /**
     * @description:  调整文件到指定阶段modal
     * @param {*} null
     * @return {*} null
     */
    showAdjustFileStage () {
      const self = this
      const ids = []
      let docName = ''
      const { project } = this
      const tempStages = [] // 记录选中有什么阶段
      const stage = cloneDeep(this.existingStages)
      for (const key in this.checkBoxObj) {
        const obj = this.checkBoxObj[key]
        if (obj.checked && !obj.isStage) {
          if (!tempStages.includes(obj.currentObj.stage)) {
            tempStages.push(obj.currentObj.stage)
          }
          if (!this.isRSUser) {
            if (!isEditStatus(obj.currentObj.status)) {
              docName += obj.currentObj.docFileName + '; '
            } else {
              ids.push(obj.currentObj.id)
            }
          } else {
            ids.push(obj.currentObj.id)
          }
        }
      }
      // tempStages.forEach((tempItem) => { // 过滤出可以选择的阶段
      //   stage = stage.filter((item) => {
      //     if (item.value !== tempItem) {
      //       return item
      //     }
      //   })
      // })
      if (docName) {
        this.$confirm({
          title: '您选中了,包含审核中的文档，系统只会调整未审核的文档！',
          content: '不会调整的文档：' + docName,
          onOk () {
            if (ids.length > 0) {
              self.$refs.adjustFileStageModal.show(`调整文档阶段【${project.rdTitle}-${project.pname}】`, project.id, ids, stage)
            } else {
              self.$message.warning('所选文档已全部处于审核状态，不可调整。')
            }
          },
          onCancel () {
          }
        })
      } else {
        this.$refs.adjustFileStageModal.show(`调整文档阶段【${project.rdTitle}-${project.pname}】`, project.id, ids, stage)
      }
    },
    StageModalrefresh (isRefresh) {
      if (isRefresh) {
        this.showDoc(this.active, false)
      }
    },
    onBatchDel () {
      const docNames = []
      const ids = []
      let nameStr = ''
      this.selectedFiles.forEach((item, index) => {
        const doc = item.currentObj
        if (!this.isRSUser) {
          if (doc.deleteLogo && isEditStatus(doc.status) && doc.docFileId !== 4) { // 预算不可以删除
            ids.push(doc.id)
            docNames.push(doc.docFileName)
          }
        } else {
          if (item.docFileId !== 4) {
            ids.push(doc.id)
            docNames.push(doc.docFileName)
          }
        }
      })
      if (!ids.length) {
        this.$message.info('您选中的文档不能删除！')
        return
      }
      docNames.forEach((name, index) => {
        nameStr += `${index + 1}. ${name}; `
      })
      this.$confirm({
        title: '您确定要删除以下文档？',
        content: nameStr,
        onOk: () => {
          this.handleBatchDel(ids)
        },
        onCancel () {
        }
      })
    },
    handleBatchDel (ids) {
      deleteDocFiles(ids).then(data => {
        this.showDoc(this.active, false)
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      }).finally(() => {

      })
    },
    /**
     * @description:  实现拖拽改变宽度
     * @param {*} null
     * @return {*} null
     */
    reSize () {
      const resize = document.getElementById('resize')
      const left = document.getElementById('article_left')
      const right = document.getElementById('article_right')
      const box = document.getElementById('wrap_box')
      resize.onmousedown = function (e) {
        const startX = e.clientX
        resize.left = left.offsetWidth
        document.onmousemove = function (e) {
          const endX = e.clientX
          let moveLen = resize.left + (endX - startX)
          const maxT = box.clientWidth - resize.offsetWidth
          if (moveLen < 160) moveLen = 160
          if (moveLen > maxT - 850) moveLen = maxT - 850
          resize.style.left = moveLen
          left.style.width = moveLen + 'px'
          right.style.width = (box.clientWidth - moveLen - 5) + 'px'
        }
        document.onmouseup = function (evt) {
          evt.stopPropagation()
          document.onmousemove = null
          document.onmouseup = null
          resize.releaseCapture && resize.releaseCapture()
        }
        resize.setCapture && resize.setCapture()
        return false
      }
    },
    // 必填项校验
    validateRequiredFields (content) {
      const requiredFields = this.$refs.comp.requiredFields
      if (requiredFields && requiredFields.length) {
        return requiredFields.every(key => {
          const list = key.split('.')
          let data = content
          while (list.length > 0) {
            const k = list.shift()
            if (data.hasOwnProperty(k)) {
              data = data[k]
            } else {
              return false
            }
          }
          return !isEmpty(data)
        })
      } else {
        // 没有必填项
        return true
      }
    }
  }
}
</script>
<style lang="less" scoped>
@import './less/index.less';

.alert_wrap {
  position: relative;
}

.alert_section {
  position: absolute;
  top: 15%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 500px;
}
</style>

<style lang="less">
#fullBox .required-field {
  font-style: normal;

  &::before {
    display: inline;
    margin-right: 4px;
    color: #f5222d;
    font-size: 14px;
    line-height: 1;
    content: '*';
  }
}
</style>
