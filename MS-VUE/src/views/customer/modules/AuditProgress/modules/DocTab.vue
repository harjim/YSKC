<!--
 * @Author: ldx
 * @Date: 2021-01-22 13:36:51
 * @LastEditTime: 2022-05-14 08:54:52
 * @LastEditors: zdf
 * @Description: 过程文档审核Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\DocTab.vue
-->
<template>
  <tab-layout
    :spinning="spinning"
    :tip="spinTip"
  >
    <template #up>
      <div>
        <a-form layout="inline">
          <a-form-item label="项目">
            <projectSelect
              ref="projectSelect"
              :year="record.year"
              :companyId="record.companyId"
              width="450px"
              :sign="0"
              :isDefault="true"
              :isJoinPrjectName="true"
              :auditCount="1"
              mountDom="fullBox"
              @getPrjectIds="OnProjectChange"
            />
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              @click="exportFile"
              :disabled="exportIds.length <= 0"
              :loading="isDownload"
              v-if="$auth('customer:projectProgress:exportDocFile')"
            > 导出
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              @click="batchAudid"
              v-if="$auth('customer:projectProgress:auditBatch')"
              :disabled="batchAuditList.length <= 0"
            > 批量审核
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              @click="batchActivate"
              v-if="$auth('customer:projectProgress:activateFlows')"
              :disabled="batchActivateList.length <= 0"
            > 批量激活
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              @click="updataStatus"
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
        <span style="margin-left: 3px;">
          <a-checkbox
            :checked="checkBoxObj['all'] ? checkBoxObj['all'].checked : false"
            @change="allSelected"
            :indeterminate="checkBoxObj['all']?checkBoxObj['all'].isIndeterminate: false"
          >全选</a-checkbox>
        </span>
        <span>文档数:</span> <span
          class="value"
        >{{
          currentProject.totalDocNum ? currentProject.totalDocNum : '-'
        }}</span>
        <span class="rejected">可审核:</span> <span
          class="value rejected"
        >{{
          currentProject.auditDocNum ? currentProject.auditDocNum : '-'
        }}</span>
        <span class="audited">已通过:</span> <span
          class="value audited"
        >{{
          currentProject.passNum ? currentProject.passNum : '-'
        }}</span>
        <span class="underwayed">已提交:</span> <span
          class="value underwayed"
        >{{
          currentProject.submitNum ? currentProject.submitNum : '-'
        }}</span>
      </div>
      <div class="doc_wrap">
        <div
          ref="right"
          class="right"
        >
          <file-item
            v-for="(item) in stageLists"
            :key="item.id"
            :currentItem="item"
            :currentObj.sync="currentObj"
            :checkBoxObj.sync="checkBoxObj"
          >
            <template #title>
              <template v-if="item.done">
                <a-badge
                  v-if="item.done"
                  :numberStyle="{ width:'6px', height: '6px' }"
                  :dot="true"
                  title="通过"
                  :offset="[-2,4]"
                  color="#008000"
                >
                  <span v-if="item.done">&nbsp;</span>
                </a-badge>
              </template>
              <template v-else>
                <a-badge
                  v-if="item.hasPermission"
                  style="text-indent:0;"
                  :numberStyle="{ width:'6px', height: '6px' }"
                  :dot="true"
                  title="当前审核人"
                  :offset="[-2,4]"
                >
                  <span v-if="item.hasPermission">&nbsp;</span>
                </a-badge>
              </template>
              <span
                :class="{'indent-title':!(item.hasPermission || item.done)}"
              >{{
                item.no
              }}</span>
              {{
                item.isTitle ? stageStr(item, item.stageType) : item.docFileName
              }}
            </template>
          </file-item>
        </div>
        <div class="content">
          <div style="position: relative; height: 100%;">
            <div class="menu_icon_box">
              <a-icon
                v-if="currentObj.id && currentObj.id > 0"
                @click="expendRight"
                :type="isShowSider ? 'menu-fold': 'menu-unfold'"
              />
            </div>
            <a-spin
              :spinning="contentSpinning"
              style="height: 100%"
              tip="加载中..."
            >
              <new-report
                v-if="currentObj.item.templateName === 'NewReportForm'"
                :projectId="projectId"
              />
              <appendix-form
                v-else-if="currentObj.item.templateName === 'AppendixForm'"
                :docFile="currentObj.item"
              />
              <trust-appendix-form
                v-else-if="currentObj.item.templateName === 'TrustAppendixForm'"
                :docFile="currentObj.item"
              />
              <trust-project-summary-form
                v-else-if="currentObj.item.templateName === 'TrustProjectSummaryForm'"
                :docFile="currentObj.item"
              />
              <trust-contract-form
                v-else-if="currentObj.item.templateName === 'TrustContractForm'"
                :docFile="currentObj.item"
              />
              <div
                v-else
                ref="previewDoc"
                class="preview_doc"
                v-html="htmlData"
              />
            </a-spin>
          </div>
        </div>
      </div>
      <batch-audit-modal
        ref="batchAuditModal"
        @refresh="refresh"
      ></batch-audit-modal>
      <batch-activate-modal
        ref="batchActivateModal"
        @refresh="refresh"
      ></batch-activate-modal>
      <export-doc-file-modal
        ref="exportDoc"
        @ok="exportOk"
      />
    </template>
    <template #down>
      <audit-log
        :full="full"
        ref="auditLog"
        @updataStatus="updataStatus"
        :params="logGetParams"
        :title="currentObj.item.docFileName"
        :active="active"
        :currentObj="currentObj"
      ></audit-log>
    </template>
  </tab-layout>
</template>
<script>
import { ProjectSelect } from '@/components/Selects'
import { isEditStatus, statusColor, statusMap } from '@/utils/processDoc/auditStatus'
import { cloneDeep } from 'lodash'
import FileItem from './FileItem'
import AuditLog from './AuditLog'
import NewReport from './NewReport'
import AppendixForm from './AppendixForm'
import TrustAppendixForm from './TrustAppendixForm'
import TrustProjectSummaryForm from './TrustProjectSummaryForm'
import TrustContractForm from './TrustContractForm'
import BatchAuditModal from './BatchAuditModal'
import BatchActivateModal from './BatchActivateModal'
import TabLayout from './TabLayout'
import ExportDocFileModal from '@/components/DocList/ExportDocFileModal'

export default {
  name: 'DocTab',
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
  components: {
    ProjectSelect,
    FileItem,
    AuditLog,
    BatchAuditModal,
    BatchActivateModal,
    TabLayout,
    NewReport,
    AppendixForm,
    TrustAppendixForm,
    TrustProjectSummaryForm,
    TrustContractForm,
    ExportDocFileModal
  },
  mounted () {
  },
  watch: {
    active (val) {
      if (val === 6) {
        this.OnProjectChange(this.$refs.projectSelect.ids, this.$refs.projectSelect.currentProject)
      }
    },
    'currentObj.id' (value) {
      this.isDownload = false
      this.getHtmlTemplateStr()
      this.logGetParams = this.currentObj.logGetParams
    },
    checkBoxObj: {
      handler (newValue, oldValue) {
        this.$set(this, 'checkedFiles', [])
        this.$set(this, 'exportIds', [])
        this.hasProjectReport = false
        for (const key in newValue) {
          if (Object.hasOwnProperty.call(newValue, key)) {
            const obj = newValue[key]
            if (!obj.isStage && obj.checked) {
              if (obj.currentObj.docFileId === 27) {
                this.hasProjectReport = true
              }
              this.exportIds.push(obj.currentObj.id)
            }
            if (!obj.isStage && obj.checked) {
              this.checkedFiles.push(obj)
            }
          }
        }
      },
      deep: true
    }
  },
  data () {
    return {
      isShowSider: true,
      statusMap,
      statusColor,
      projectId: undefined,
      currentProject: {},
      htmlData: '',
      docIds: [], // 导出需要的的ids
      spinning: false,
      contentSpinning: false,
      isDownload: false,
      spinTip: '加载中...',
      logGetParams: { // 日志请求数据
        docFileId: undefined
      },
      stageLists: [],
      checkedFiles: [],
      exportIds: [],
      checkBoxObj: {},
      currentObj: {
        id: -1,
        item: {},
        logGetParams: {
          docFileId: undefined
        }
      },
      hasProjectReport: false,
      showScore: false
    }
  },
  computed: {
    batchAuditList () {
      return this.checkedFiles.filter(item => item.currentObj.hasPermission)
    },
    batchActivateList () {
      return this.checkedFiles.filter(item => item.currentObj.auditStatus === 1)
    }
  },
  methods: {
    isEditStatus,
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
        right.style.width = 255 + 'px'
      }
    },
    /**
     * @description: 项目选择框改变回调
     * @param {*} projectId
     * @param {*} currentProject
     * @return {*} null
     */
    OnProjectChange (projectId, currentProject) {
      this.projectId = projectId
      this.currentProject = currentProject
      this.docIds = []
      this.currentObj = {
        id: -1,
        item: {},
        logGetParams: {
          docFileId: undefined
        }
      }
      this.getDocList(projectId, this.record.companyId)
      this.$emit('updateCurrentProject', currentProject)
    },
    /**
     * @description: 初始化目录
     * @param {*} projectId
     * @param {*} companyId
     * @param {*} isRefresh
     * @return {*} promise
     */
    getDocList (projectId, companyId, isRefresh = false) {
      // hasAudit
      this.spinning = true
      return this.$http.get('/projectProgress/getStageAudit', {
        params: {
          projectId,
          companyId
        }
      })
        .then(res => {
          this.docIds = []
          if (!res.success) {
            this.$message.error('加载数据失败！')
            return Promise.resolve(true)
          }
          if (res.data && res.data.length) {
            this.checkBoxObj = {}
            this.stageLists = res.data.map(item => {
              return {
                ...item,
                expand: true
              }
            })
            const ISTITLE = 'isTitle'
            const NO = 'no'
            const docLists = []
            this.setCheckBoxObjValue(-1, { id: 'all' }, false, true)
            this.stageLists.forEach((stage, index) => {
              stage[ISTITLE] = true
              if (stage.projectDocList && stage.projectDocList.length) {
                this.setCheckBoxObjValue(stage.id, stage, false, true)
              } else {
                this.setCheckBoxObjValue(stage.id, stage, true, true)
              }
              stage[NO] = `${index + 1}.`
              if (stage.projectDocList && stage.projectDocList.length) {
                const docList = stage.projectDocList
                docLists.push(docList)
                // const isAllDisableAry = []
                docList.forEach((doc, subIndex) => {
                  this.docIds.push(doc.id)
                  doc[ISTITLE] = false
                  doc[NO] = `${index + 1}.${subIndex + 1}`
                  // const isDisabled = !doc.hasPermission
                  this.setCheckBoxObjValue(stage.id, doc, false, false)
                  // isAllDisableAry.push(isDisabled)
                })
                // 判断文档是否全部不可以勾选，全部不可以勾选时禁止阶段勾选
                // this.checkBoxObj[stage.id].isDisabled = isAllDisableAry.every((currentValue) => {
                //   return currentValue
                // })
              }
            })
            if (isRefresh) {
              this.resetCurrentFile()
            } else {
              if (docLists.length) {
                const firstDoc = docLists[0][0]
                this.currentObj.id = firstDoc.id
                this.currentObj.item = firstDoc
                this.currentObj.companyId = this.record.companyId
                this.currentObj.logGetParams = { docFileId: firstDoc.id }
              } else {
                this.currentObj.id = -1
                this.currentObj.item = {}
                this.currentObj.logGetParams = { docFileId: undefined }
              }
            }
          } else {
            this.checkBoxObj = {}
            this.currentObj.id = -1
            this.currentObj.item = {}
            this.currentObj.logGetParams = { docFileId: undefined }
            this.stageLists = []
            this.htmlData = ''
            this.docIds = []
            this.$refs.auditLog.reset()
          }
          return Promise.resolve(true)
        }).catch(() => {
          this.$message.error('加载数据失败！')
          return Promise.resolve(true)
        }).finally(() => {
          this.spinning = false
        })
    },
    /**
     * @description: 导出文件
     * @param {*} null
     * @return {*} null
     */
    exportFile () {
      this.spinning = true
      this.isDownload = true
      const datas = {
        url: '/projectProgress/exportAllDoc',
        params: {
          projectId: this.projectId,
          pDocFileIds: this.exportIds,
          currentYear: this.record.year
        },
        filename: this.record.companyName + '-' + this.currentProject.rdTitle + '-' + this.currentProject.pname + '过程文件.pdf',
        rdTitle: this.currentProject.rdTitle,
        hasCatalogue: true,
        hasBudget: this.hasProjectReport,
        hasCover: true
      }
      this.$refs.exportDoc.show(datas)
    },
    exportOk () {
      this.isDownload = false
      this.spinTip = '加载中...'
      this.spinning = false
    },
    /**
     * @description:  获取后台模板信息
     * @param {*} null
     * @return {*} null
     */
    getHtmlTemplateStr () {
      if (!this.currentObj.id || this.currentObj.id * 1 < 0) {
        return
      }
      const params = {
        pDocFileId: this.currentObj.id,
        projectId: this.projectId,
        currentYear: this.record.year,
        companyId: this.record.companyId
      }
      this.contentSpinning = true
      this.$http.get('/projectProgress/previewFile', { params: params }).then((res) => {
        if (res.data && res.success) {
          this.htmlData = res.data
        } else {
          this.htmlData = `<div style="font-size: 1.5em; font-weight: bold;">${res.errorMessage}</div>`
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.htmlData = ''
      }).finally((res) => {
        setTimeout(() => {
          this.contentSpinning = false
          this.renderContent()
        }, 300)
      })
    },
    /**
     * @description: 刷新
     * @param {*} null
     * @return {*} null
     */
    refresh () {
      this.getDocList(this.projectId, this.record.companyId, true).then(response => {
        const auditCount = this.computedAuditNum(this.stageLists)
        for (const key in auditCount) {
          if (Object.hasOwnProperty.call(auditCount, key)) {
            const num = auditCount[key]
            this.currentProject[key] = num
          }
        }
      })
    },
    /**
     * @description:  重置当前对象信息
     * @param {*}
     * @return {*}
     */
    resetCurrentFile () {
      const templateObj = cloneDeep(this.currentObj)
      const files = []
      this.stageLists.forEach(stage => {
        if (stage.projectDocList && stage.projectDocList.length) {
          stage.projectDocList.forEach(file => {
            files.push(file)
          })
        }
      })
      const newFile = files.find(file => {
        return file.id === templateObj.id
      })
      this.currentObj.id = newFile.id
      this.currentObj.item = newFile
      this.currentObj.companyId = this.record.companyId
      this.currentObj.logGetParams.docFileId = newFile.id
      this.logGetParams = this.currentObj.logGetParams
    },
    /**
     * @description:  计算审核状态数量
     * @param {*}
     * @return {*}
     */
    computedAuditNum (stageList) {
      const auditCount = { auditDocNum: 0, passNum: 0, submitNum: 0 }
      if (stageList && stageList.length) {
        stageList.map(stage => {
          if (stage && stage.projectDocList && stage.projectDocList.length) {
            const docs = stage.projectDocList
            docs.map(doc => {
              const { auditStatus, hasPermission } = doc
              if (hasPermission) {
                auditCount.auditDocNum++
              }
              if (auditStatus && auditStatus === 0) {
                auditCount.submitNum++
              }
              if (auditStatus && auditStatus === 1) {
                auditCount.passNum++
              }
            })
          }
        })
      }
      return auditCount
    },
    /**
     * @description: tab切换调用的回调函数
     * @param {*} null
     * @return {*} null
     */
    callBack () {
      // this.getHtmlTemplateStr()
      this.getDocList(this.projectId, this.record.companyId)
    },
    /**
     * @description: 处理阶段带日期
     * @param {*} currentItem
     * @param {*} title
     * @return {*}
     */
    stageStr (currentItem, title) {
      let str = `${title} (${currentItem.beginDate ? currentItem.beginDate : ' '}~${currentItem.endDate ? currentItem.endDate : ' '})`
      str = str.replace(/-/gi, '/')
      str = str.replace(/~/gi, '-')
      return str
    },
    /**
     * @description: 打开批量审核modal
     * @param {*} null
     * @return {*} null
     */
    batchAudid () {
      this.$refs.batchAuditModal.docAutidShow('批量审核文档', this.batchAuditList, this.projectId)
    },
    batchActivate () {
      this.$refs.batchActivateModal.docAutidShow('批量激活文档', this.batchActivateList, this.projectId)
    },
    /**
     * @description: 修改模板图片的端口号
     * @param {*}
     * @return {*}
     */
    renderContent () {
      this.$nextTick(() => {
        if (this.$refs.previewDoc && this.$refs.previewDoc.getElementsByTagName) {
          const arr = Array.from(this.$refs.previewDoc.getElementsByTagName('img'))
          if (arr.length) {
            arr.forEach(a => {
              a.src = a.src.replace(':9000', ':8000')
            })
          }
        }
      })
    },
    /**
     * @description: 更新标识符
     * @param {*}
     * @return {*}
     */
    updataStatus () {
      this.$emit('updataStatus')
      this.refresh()
    },
    /**
     * @description: 设置CheckBox对象的值
     * @param {*} stageId 阶段id
     * @param {*} item 当前的item
     * @param {*} isDisabled 是否禁用
     * @param {*} isStage 是否阶段
     * @return {*} null
     */
    setCheckBoxObjValue (stageId, item, isDisabled, isStage) {
      this.$set(this.checkBoxObj, item.id, {
        checked: false,
        isIndeterminate: false,
        isDisabled: isDisabled,
        isStage: isStage,
        stageId: stageId,
        currentObj: item
      })
    },
    allSelected (e) {
      if (Object.keys(this.checkBoxObj).length <= 0) {
        return
      }
      for (const key in this.checkBoxObj) {
        if (Object.hasOwnProperty.call(this.checkBoxObj, key)) {
          const item = this.checkBoxObj[key]
          if (!item.isDisabled) {
            this.$set(item, 'checked', e.target.checked)
            this.$set(item, 'isIndeterminate', undefined)
          }
        }
      }
      this.checkBoxObj['all']['checked'] = e.target.checked
      this.checkBoxObj['all']['isIndeterminate'] = undefined
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

* /deep/ .vxe-table .vxe-body--expanded-column.col--ellipsis > .vxe-body--expanded-cell {
  overflow: auto;
}

.indent-title {
  padding-left: 3px;
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
