<!--
 * @Author: ldx
 * @Date: 2021-03-11 10:38:12
 * @LastEditTime: 2022-07-27 10:44:00
 * @LastEditors: zdf
 * @Description: 留存备查资料(邓总要求压缩包显示几个文件)
 * @FilePath: \RS-VUE\src\views\project\ForFutureReferenceData.vue
-->
<template>
  <a-spin id="spin" :spinning="spinning" tip="加载中...">
    <a-card
      v-show="$auth('project:forFutureReferenceData:view')"
      :bodyStyle="{height: '100%', overflow: 'auto'}"
      class="contentPage">
      <a-form layout="inline">
        <a-form-item label="项目">
          <project-select
            :isDefault="true"
            :isJoinPrjectName="true"
            :sign="0"
            :year="currentYear"
            style="width:600px;"
            @getPrjectIds="projectSelected" />
        </a-form-item>
        <a-form-item>
          <a-button
            v-if="$auth('project:forFutureReferenceData:export')"
            :disabled="selectedCount <= 0"
            type="primary"
            @click="onExport">导出
          </a-button>
        </a-form-item>
      </a-form>
      <div v-show="projectId > 0" id="fullBox" class="container">
        <div id="wrap_box" class="wrap_box">
          <div id="article_left" class="menu_tree">
            <a-checkbox
              :checked="isAllChecked"
              :disabled="isAllDisabled"
              :indeterminate="isIndeterminate"
              style="padding-left: 4px;"
              @change="onAllChange"></a-checkbox>
            全选
            <div
              v-for="(doc,index) in docs"
              :key="index"
              :class="{'item-active':doc.id === currentActive.id}"
              class="item">
              <a-badge
                v-if="doc.auditStatus !== 5 && doc.id !==3 && isMsUser"
                :color="statusColor[doc.auditStatus]"
                :dot="true"
                :numberStyle="{ width:'6px', height: '6px' }"
                :offset="[-9,1]"
                :title="statusMap[doc.auditStatus]">
                <a-checkbox
                  :checked="doc.checked"
                  :disabled="doc.checkedDisabled || doc.fixedDisabled"
                  @change="e => onCheckChange(e,doc)"></a-checkbox>
                {{ index + 1 }}
              </a-badge>
              <span v-else>
                <a-checkbox
                  :checked="doc.checked"
                  :disabled="doc.checkedDisabled || doc.fixedDisabled"
                  @change="e => onCheckChange(e,doc)"></a-checkbox>
                {{ index + 1 }}
              </span>
              . <span
                :title="doc.docName"
                class="title_content"
                @click="onActiveItem(doc)">{{ doc.docName }}</span>
            </div>
          </div>
          <div id="resize" class="middle">
            <div class="icon">⋮</div>
          </div>
          <div id="article_right" class="right_content">
            <div class="content_box">
              <div class="content_title">
                <div class="audit_box">
                  <template v-if="isMsUser">
                    <template
                      v-if="(currentActive.doc.docFile && !!currentActive.docInfo.hasSubmit && !isFinance(currentActive.docInfo.owner)) && currentActive.doc.id !==3 ">
                      状态:
                      <span
                        class="file_status">{{ statusMap[currentActive.docInfo.hasSubmit]
                      }}</span>
                      <a-button
                        v-if="$auth('project:forFutureReferenceData:submit') && isEditStatus(currentActive.docInfo.hasSubmit) && (!isEdit || currentActive.docInfo.templateName === 'NewReportForm') "
                        :disabled="!isCommit"
                        :loading="confirmLoading"
                        size="small"
                        title="提交"
                        type="primary"
                        @click="submitOneDocBtn">
                        提交
                      </a-button>
                    </template>
                  </template>
                </div>
                <div class="title">{{ currentActive.doc.docName }}</div>
                <div class="screen_icon">
                  <template
                    v-if="!isEdit && ![3,5].includes(currentActive.doc.id) && ((!currentActive.docInfo.hasSubmit && currentActive.docInfo.hasSubmit!=0) || isEditStatus(currentActive.docInfo.hasSubmit)) && $auth('project:forFutureReferenceData:setTemplate')">
                    模板：
                    <a-select
                      :value="currentActive.docInfo && currentActive.docInfo.templateId ? currentActive.docInfo.templateId : undefined"
                      placeholder="请选择模板"
                      style="width: 150px; margin-right: 10px;"
                      @change="selectedChange">
                      <a-select-option
                        v-for=" template in currentActive.doc.templates"
                        :key="template.id"
                        :docFileId="template.docFileId"
                        :template="template"
                        :value="template.id">
                        {{ template.templateName }}
                      </a-select-option>
                    </a-select>
                  </template>
                  <template v-if="!isFinance(currentActive.docInfo.owner)">
                    <a-button
                      v-show="isEdit && currentActive.docInfo.templateName !== 'NewReportForm'"
                      :loading="confirmLoading"
                      size="small"
                      style="margin-right: 5px;"
                      title="返回"
                      type="primary"
                      @click="onGoBack"
                    >
                      返回
                    </a-button>
                    <a-button
                      v-show="isEdit && currentActive.docInfo.templateName !== 'NewReportForm'&& (isEditStatus(currentActive.docInfo.hasSubmit)|| !isMsUser )"
                      :loading="confirmLoading"
                      size="small"
                      style="margin-right: 5px;"
                      title="保存"
                      type="primary"
                      @click="onSave"
                    >
                      保存{{ currentActive.docInfo.status }}
                    </a-button>
                    <a-button
                      v-show="!isEdit && currentActive.doc.docFile && ( isEditStatus(currentActive.docInfo.hasSubmit) || !isMsUser ) && $auth('project:forFutureReferenceData:edit') "
                      :loading="this.confirmLoading"
                      size="small"
                      style="margin-right: 5px;"
                      title="编辑"
                      type="primary"
                      @click="onEdit"
                    >
                      编辑
                    </a-button>
                  </template>
                  <template>
                    <a-button
                      v-show="!isEdit && $auth('project:forFutureReferenceData:export') && currentActive.docInfo.docFileId && currentActive.doc.docFile && !isFinance(currentActive.docInfo.owner) && (!isMsUser || isExportStatus(currentActive.docInfo.hasSubmit))"
                      :loading="confirmLoading"
                      size="small"
                      style="margin-right: 5px;"
                      title="导出"
                      type="primary"
                      @click="exportFile"
                    >导出
                    </a-button> <!-- 财务文档不给导出 -->
                    <a-button
                      icon="fullscreen"
                      size="small"
                      @click="full"></a-button>
                  </template>
                </div>
              </div>
              <div class="content">
                <div class="left_box">
                  <div
                    v-show="!isEdit && currentActive.doc.docFile"
                    :class="{preview_width: true}"
                    v-html="currentActive.htmlData"></div>
                  <!-- :entroy="false" // 进入方式（从哪里进） true： 备查资料； false: 过程文档 -->
                  <component
                    :is="currentActive.docInfo.templateName"
                    v-if="isEdit && currentActive.doc.docFile"
                    ref="comp"
                    :auditStatus="currentActive.docInfo.hasSubmit"
                    :dataMonth="currentActive.docInfo.dataMonth"
                    :docId="currentActive.docInfo.docFileId"
                    :entory="true"
                    :isEdit="isEditStatus(currentActive.docInfo.hasSubmit) || !isMsUser"
                    :project="projectInfo"
                    :projectId="projectId"
                    class="preview_width"
                    v-on="{onControlCommitBtn}"
                  />
                  <project-summary
                    v-if="currentActive.doc.id === 5"
                    :projectId="projectId"
                    @loading="loader"></project-summary>
                  <!-- <prepare-the-situation :projectId="projectId" :projectInfo="projectInfo" ref="prepareTheSituation" v-if="currentActive.doc.id === 3" @loading="loader"></prepare-the-situation> -->
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <result-upload-modal
        ref="uploadModal"
        :haveMonth="true"
        :isNewReportCommit="false"
        :year="currentYear"
        action="/sysDocument/uploadResult"
        paramKey="tableField"
        title="上传文件"
        @error="error"
        @onSuccess="success"
      />
      <export-doc-file-modal ref="exportDoc" />
    </a-card>
  </a-spin>
</template>

<script>
import {
  isEditStatus,
  isExportStatus,
  isFinance,
  isMsUser,
  statusColor,
  statusMap
} from '@/utils/processDoc/auditStatus'
import ProjectSummary from './modules/ProjectSummary'
import PrepareTheSituation from './modules/PrepareTheSituation'
import screenfull from 'screenfull'
import allComp from '@/docTemplate/Templates'
import {
  getBackupData,
  getDocInfo,
  previewFile
} from '@/api/project/FinalExpenseReport'
import {
  getAuditStatus,
  previewReport,
  setDocTemplate
} from '@/api/ForFutureReferenceData'
import { ProjectSelect } from '@/components'
import { mapGetters } from 'vuex'
import { cloneDeep, isEmpty, isString, trim } from 'lodash'
import { saveData } from '@/api/doc/index'
import resultUploadModal from './modules/ResultUploadModal'
import ExportDocFileModal from '@/components/DocList/ExportDocFileModal'
import moment from 'moment'

const docs = [
  { id: 1, checked: false, checkedDisabled: true },
  { id: 2, checked: false, checkedDisabled: true },
  { id: 3, checked: false, checkedDisabled: false },
  { id: 4, checked: false, checkedDisabled: false },
  { id: 5, checked: false, checkedDisabled: true, fixedDisabled: true },
  { id: 6, checked: false, checkedDisabled: true },
  {
    id: 7,
    compName: 'NewReport',
    isCommit: false,
    checked: false,
    checkedDisabled: true
  },
  { id: 8, checked: false, checkedDisabled: true }
]
export default {
  name: 'ForFutureReferenceData',
  components: {
    ProjectSelect,
    ...allComp,
    ProjectSummary,
    PrepareTheSituation,
    resultUploadModal,
    ExportDocFileModal
  },
  mounted () {
    this.isEnabledFullscreen = screenfull.isEnabled
    this.reSize()
  },
  computed: {
    ...mapGetters(['currentYear', 'companyId', 'userInfo']),
    isMsUser () {
      return isMsUser()
    }
  },
  watch: {
    docs: {
      handler (val) {
        this.selectedCount = 0
        val.forEach(item => {
          if (item.checked) {
            this.selectedCount += 1
          }
        })
      },
      deep: true,
      immediate: true
    }
  },
  data () {
    return {
      statusMap,
      statusColor,
      projectId: undefined,
      projectInfo: undefined,
      confirmLoading: false,
      spinning: false,
      isEnabledFullscreen: false,
      isFullscreen: false,
      isAllChecked: false,
      isIndeterminate: false,
      isAllDisabled: false,
      isEdit: false,
      isCommit: true,
      isShowTemplate: false,
      currentActive: {
        id: 1,
        doc: { id: 1, docName: '研发项目计划书', docFileId: 27, docFile: true },
        htmlData: '',
        docInfo: {}
      },
      docs,
      resetDocs: docs,
      selectedCount: 0
    }
  },
  methods: {
    isEditStatus,
    isExportStatus,
    isFinance,
    /**
     * @description: 给组件赋值
     * @param {*} null
     * @return {*} null
     */
    initComponentData () {
      this.spinning = true
      this.confirmLoading = true
      const compSync = this.$refs.comp
      const fileInfo = {
        docName: this.currentActive.docInfo.docFileName,
        docId: this.currentActive.docInfo.docFileId,
        formName: this.currentActive.docInfo.templateName,
        currentYear: this.currentYear,
        saveDocId: this.currentActive.docInfo.pdocFileId,
        docFileId: this.currentActive.docInfo.docFileId
      }
      if (this.currentActive.docInfo.docDate && compSync.hasOwnProperty('fileDate')) {
        compSync.fileDate = moment(this.currentActive.docInfo.docDate)
        compSync.BUfileDate = cloneDeep(this.currentActive.docInfo.docDate)
      }
      if (compSync.publicInfo && this.currentActive.docInfo) {
        Object.assign(compSync.publicInfo, this.currentActive.docInfo)
      }
      if (compSync) {
        compSync.fileInfo = fileInfo // 给组件赋文件信息数据
        Object.assign(compSync.project, this.projectInfo) // 给组件赋项目对象数据
        compSync.content = cloneDeep(compSync.BPContent) // 重置组件content内容
        if (this.currentActive.docInfo.data) { // 给组件赋表单内容数据
          const tempContent = JSON.parse(this.currentActive.docInfo.data)
          Object.assign(compSync.content, tempContent)
        }
        if (compSync.employeeMap && this.currentActive.docInfo.footerMap) {
          Object.assign(compSync.employeeMap, this.currentActive.docInfo.footerMap)
        }
        if (compSync.handleTemplateEvent) {
          compSync.handleTemplateEvent()
        }
      }
      this.confirmLoading = false
      this.spinning = false
    },
    /**
     * @description:  编辑按钮
     * @param {*} null
     * @return {*} null
     */
    onEdit () {
      this.isEdit = true
      this.$nextTick(() => {
        this.initComponentData()
      })
    },
    /**
     * @description:  返回按钮
     * @param {*} null
     * @return {*} null
     */
    onGoBack () {
      this.confirmLoading = true
      this.isEdit = false
      this.confirmLoading = false
      if (this.currentActive.doc.docFile && this.projectId > 0) {
        this.handleGetDocInfo(this.currentActive.doc.docFileId)
      }
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
    /**
     * @description:  获取过程文档信息
     * @param {*} null
     * @return {*} null
     */
    async handleGetDocInfo (docTemplateId = 27) {
      this.spinning = true
      this.isCommit = false
      const docInfoResult = await getDocInfo({
        projectId: this.projectId,
        docFileId: docTemplateId,
        year: this.currentYear
      })
        .then((response) => {
          if (response.success) {
            if (!response.data) {
              return Promise.resolve(2)
            }
            this.isCommit = response.data.finished
            this.currentActive.docInfo = {}
            Object.assign(this.currentActive.docInfo, response.data)
            return Promise.resolve(1)
          } else {
            return Promise.resolve(0)
          }
        })
      if (docInfoResult === 0) {
        this.currentActive.docInfo = {}
        this.currentActive.htmlData = `<div style="font-size: 1.5em; font-weight: bold; color: red; text-align:center; margin-top: 8%;">获取信息出错！</div>`
        this.spinning = false
        return
      }
      if (docInfoResult === 2) {
        this.currentActive.docInfo = {}
        this.currentActive.htmlData = `<div style="font-size: 1.5em; font-weight: bold; color: red; text-align:center; margin-top: 8%;">请选择模板！</div>`
        this.isShowTemplate = true
        this.spinning = false
        return
      }
      if (this.currentActive.docInfo.templateName === 'NewReportForm') {
        this.onEdit()
      } else {
        await this.handleGetHtmlData()
      }
      this.isShowTemplate = false
      this.spinning = false
    },
    /**
     * @description:  获取过程文档预览内容
     * @param {*} null
     * @return {*} null
     */
    handleGetHtmlData () {
      const { currentYear, companyId, projectId } = this
      const params = {
        currentYear,
        companyId,
        projectId,
        pDocFileId: this.currentActive.docInfo.docFileId
      }
      return previewFile(params).then((response) => {
        if (response.data && response.success) {
          this.currentActive.htmlData = response.data
        } else {
          this.currentActive.htmlData = `<div style="font-size: 1.5em; font-weight: bold; color: red; text-align:center; margin-top: 8%;">获取信息出错！</div>`
        }
      }).finally(() => {
        return Promise.resolve()
      })
    },
    /**
     * @description:  项目改变事件
     * @param {*} null
     * @return {*} null
     */
    async projectSelected (projectId, projectInfo) {
      this.isAllChecked = false
      this.isIndeterminate = false
      this.isAllDisabled = false
      this.projectId = projectId
      this.projectInfo = projectInfo
      this.isEdit = false
      this.currentActive.htmlData = ''
      // console.log(this.currentActive)
      // if (this.currentActive.doc.docFile && this.projectId > 0) {
      const result = await this.handleGetBackupData(projectId, this.currentYear)
      if (!result) {
        return
      }
      this.currentActive.doc = result
      if (this.currentActive.doc.id === 3) {
        this.getPreviewReport(this.projectId)
      } else {
        this.handleGetDocInfo(this.currentActive.doc.docFileId)
      }
      this.updateAuditStatus()
      // }
    },
    /**
     * @description:  更新审核状态
     * @param {*} null
     * @return {*} null
     */
    updateAuditStatus () {
      if (isMsUser()) {
        getAuditStatus({
          projectId: this.projectId,
          year: this.currentYear
        }).then((response) => {
          if (response.data && response.success) {
            this.docs.forEach(item => {
              if (item.id === 3) {
                item.auditStatus = 1
              } else {
                item.auditStatus = 5
              }
            })
            const status = response.data
            for (const key in status) {
              this.docs.forEach(item => {
                if (item.id === Number(key)) {
                  item['auditStatus'] = status[key]
                  item['checkedDisabled'] = !isExportStatus(status[key])
                }
              })
            }
            this.docs[3].checkedDisabled = false
            const allDisabled = this.docs.some(item => {
              return !item.checkedDisabled && !item.fixedDisabled
            })
            this.isAllDisabled = !allDisabled
          } else {
            this.$message.error(`${response.errorCode}: ${response.errorMessage}`)
            console.error(`getAuditStauts function error ${response.errorCode}: ${response.errorMessage}`)
          }
        }).catch((error) => {
          this.$message.error(error.message)
          console.error(`getAuditStauts function error`, error.message)
        })
      } else {
        this.docs.forEach(item => {
          item.auditStatus = 1
          item.checkedDisabled = false
        })
      }
    },
    /**
     * @description:点击目录文档事件
     * @param {*} null
     * @return {*} null
     */
    onActiveItem (doc) {
      if (this.currentActive.id === doc.id) {
        return
      }
      this.currentActive.id = doc.id
      this.currentActive.doc = doc
      this.currentActive.docInfo = {}
      this.currentActive.htmlData = ''
      if (doc.docFile && doc.id !== 3) {
        this.handleGetDocInfo(doc.docFileId)
      }
      if (doc.id === 3) {
        this.getPreviewReport(this.projectId)
      }
      this.isEdit = false
    },
    getPreviewReport () {
      this.spinning = true
      const params = { projectId: this.projectId, year: this.currentYear }
      previewReport(params).then((response) => {
        if (response.data && response.success) {
          this.currentActive.htmlData = response.data
        } else {
          this.currentActive.htmlData = `<div style="font-size: 1.5em; font-weight: bold; color: red; text-align:center; margin-top: 8%;">获取信息出错！</div>`
        }
      }).finally(() => {
        this.spinning = false
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
    /**
     * @description: 统计字数
     * @param {Array} computedFields
     * @param {Object} content
     * @return {number} totalContentLength
     */
    handleComputedFields (computedFields, content) {
      let totalContentLength = 0
      if (!Array.isArray(computedFields) || isEmpty(content)) {
        return
      }
      computedFields.forEach((item, index) => {
        const textContent = trim(content[item])
        if (isEmpty(textContent)) {
          return false
        }
        if (isString(textContent)) {
          totalContentLength += textContent.length
        }
      })
      return totalContentLength
    },
    onSave () {
      this.handleSaveData()
    },
    /**
     * @description: 保存文档
     * @param {*} null
     * @return {*} null
     */
    handleSaveData () {
      this.spinning = true
      this.confirmLoading = true
      const content = this.$refs.comp.content
      // 必填项校验
      const finished = this.validateRequiredFields(content)
      const cStr = JSON.stringify(content)
      const fileDate = this.$refs.comp.fileDate ? this.$refs.comp.fileDate.startOf('month') : undefined
      // 1表示项目建议书 3表示立项决议 1和3文档都不在项目日期范围内不用传月份
      const docFileIds = [1, 3, 31, 13, 34, 38, 24, 26, 27, 29, 5, 20, 30]
      // const docFileIds = [1, 31, 15, 13, 34, 38, 24, 26, 27, 29, 30, 5, 20]
      let month
      const file = this.currentActive.docInfo
      if ((docFileIds.includes(file.id)) || file.templateName === 'ResolutionForm' || this.$refs.comp.hsaFileDataNull) { // 文档日期不用指定日期的不传日期
        month = undefined
      } else {
        month = fileDate
      }
      const postData = {
        id: this.currentActive.docInfo.pdocFileId ? this.currentActive.docInfo.pdocFileId : undefined,
        pDocFileId: this.currentActive.docInfo.docFileId,
        projectId: this.projectId,
        month,
        data: cStr,
        wordLength: 0,
        filledItems: 0,
        totalItems: 0,
        finished
      }
      saveData(postData).then(res => {
        if (res.success && res.data) {
        } else {
          this.currentActive.htmlData = ''
          this.$message.error(res.errorMessage || '操作失败!')
        }
      }).finally(() => {
        this.spinning = false
        this.confirmLoading = false
      })
    },
    /**
     * @description:导出过程材料
     * @param {*} null
     *@return {*} null
     */
    exportFile () {
      const datas = {
        url: '/projectDocFileData/exportPdf',
        params: {
          pDocFileId: this.currentActive.docInfo.docFileId,
          docName: this.currentActive.docInfo.docFileName,
          projectId: this.projectId,
          currentYear: this.currentYear
        },
        filename: `${this.userInfo.companyName}-${this.projectInfo.rdTitle}-${this.currentActive.docInfo.docFileName}.pdf`,
        rdTitle: this.projectInfo.rdTitle,
        docName: this.currentActive.docInfo.docFileName,
        hasBudget: this.currentActive.doc.docFileId === 27
      }
      this.$refs.exportDoc.show(datas)
    },
    loader (isLoader) {
      this.spinning = isLoader
    },
    /**
     * @description: 提交单个文档审核的二次确认框
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
      this.confirmLoading = true
      const paramObj = {
        projectId: this.projectId,
        docFileId: [this.currentActive.docInfo.docFileId]
      }
      this.$http.post('/projectAudit/submitDocAudit', paramObj).then((res) => {
        if (res.data && res.success) {
          this.handleGetDocInfo(this.currentActive.doc.docFileId)
          this.updateAuditStatus()
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    success () {
      this.$refs.comp.success()
    },
    error (message) {
      this.$message.error(message)
    },
    openUploadModal () {
      this.$refs.uploadModal.show(undefined, this.projectId)
    },

    /**
     * @description: 导出备查资料
     */
    onExport () {
      this.spinning = true
      const chooseFiles = []
      let hasBudget = false
      this.docs.forEach(item => {
        if (item.checked) {
          if (item.docFileId === 27) {
            hasBudget = true
          }
          chooseFiles.push(item.id)
        }
      })
      if (chooseFiles.length) {
        this.spinning = false
        const datas = {
          url: '/projectDocFileData/exportBackupData',
          params: {
            projectId: this.projectId,
            chooseFiles,
            year: this.currentYear
          },
          rdTitle: this.projectInfo.rdTitle,
          docName: '留存备查资料',
          hasCover: true,
          hasBudget,
          hasUtility: true,
          hasMerge: true
        }
        this.$refs.exportDoc.show(datas)
        // this.$exportData(
        //   ,
        //   , null,
        //   this.$message).then(() => {
        // }).finally(() => {
        //   this.spinning = false
        // })
      }
    },
    onCheckChange (e, doc) {
      const status = e.target.checked
      let isSomeChecked
      doc.checked = status
      if (isMsUser()) {
        isSomeChecked = this.docs.some(item => {
          if (!item.fixedDisabled && isExportStatus(item.auditStatus)) {
            return !item.checked
          }
        })
      } else {
        isSomeChecked = this.docs.some(item => {
          if (!item.fixedDisabled) {
            return !item.checked
          }
        })
      }
      const isAllNoChecked = this.docs.every(item => {
        return !item.checked
      })
      if (isSomeChecked) {
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
    onAllChange (e) {
      const status = e.target.checked
      this.isAllChecked = status
      if (isMsUser()) {
        this.docs.forEach(item => {
          if (isExportStatus(item.auditStatus) && !item.fixedDisabled && !item.checkedDisabled) {
            item['checked'] = status
          }
        })
      } else {
        this.docs.forEach(item => {
          if (!item.fixedDisabled && !item.checkedDisabled) {
            item['checked'] = status
          }
        })
      }
      this.docs[3].checked = status
      if (!status) {
        this.isIndeterminate = undefined
      }
    },
    /**
     * @description: 控制提交按钮是否禁用
     * @param {*} status
     * @return {*}
     */
    onControlCommitBtn (status) {
      this.isCommit = status
    },
    /**
     * @description: 获取目录列表
     * @param {*} projectId
     * @param {*} year
     * @return {*}
     */
    handleGetBackupData (projectId, year) {
      if (!projectId) {
        return Promise.resolve(null)
      }
      return getBackupData({ projectId, year }).then(data => {
        if (data) {
          const docs = data
          const docMap = {}
          docs.map(item => {
            docMap[item.id] = item
          })
          this.docs = cloneDeep(this.resetDocs)
          this.docs.forEach(item => {
            const id = item.id
            const doc = docMap[id]
            Object.assign(item, doc)
          })
          const result = docMap[this.currentActive.id]
          this.docs.sort((cItem, nItem) => {
            return cItem.seq - nItem.seq
          })
          return Promise.resolve(result)
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常,请联系管理员！')
        return Promise.resolve(false)
      })
    },
    selectedChange (value, option) {
      const { docInfo } = this.currentActive
      const { data: { attrs: { docFileId } } } = option
      // const { data: { attrs: { docFileId, template } } } = option
      const params = {
        id: docInfo.docFileId || undefined, // 数据的id,有才传
        year: this.currentYear, // 年份
        docFileId: docFileId, // 文档id
        templateId: value, // 模板id
        projectId: this.projectId // 项目id
      }
      if (docInfo.data) {
        this.$confirm({
          title: '警告',
          content: '切换模板会删除文档的内容，您确定要切换模板？',
          onOk: () => {
            this.handleSetDocTemplate(params, docFileId)
          }
        })
      } else {
        this.handleSetDocTemplate(params, docFileId)
      }
    },
    handleSetDocTemplate (params, docFileId) {
      setDocTemplate(params).then(data => {
        if (data) {
          this.handleGetDocInfo(docFileId)
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
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
@import './less/forFutureReferenceData.less';
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
