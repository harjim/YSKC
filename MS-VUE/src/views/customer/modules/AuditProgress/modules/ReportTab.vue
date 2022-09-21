<!--
 * @Author: ldx
 * @Date: 2021-01-22 14:16:08
 * @LastEditTime: 2022-05-23 09:49:15
 * @LastEditors: zdf
 * @Description: 项目立项审核Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\ReportTab.vue
-->
<template>
  <tab-layout
    :spinning="spinning"
    tip="加载中..."
  >
    <template #up>
      <div style="margin-bottom: 8px; padding-left: 3px;">
        <a-checkbox
          :checked="isAllChecked"
          :indeterminate="isIndeterminate"
          @change="onAllChange"
        />
        全选
        <a-button
          v-if="$auth('customer:projectProgress:activateFlows')"
          :disabled="batchActivateList.length <= 0"
          size="small"
          style="margin-left: 10px;"
          type="primary"
          @click="onBatchActivate"
        >批量激活
        </a-button>
        <a-button
          size="small"
          style="margin-left: 10px;"
          type="primary"
          @click="updataStatus"
        >刷新
        </a-button>
        <a-button
          v-if="$auth('customer:projectProgress:checkdup')"
          size="small"
          style="margin-left: 10px;"
          type="primary"
          :disabled="checkedList.length<=0"
          @click="$refs.checkModal.open(checkedList)"
        >查重
        </a-button>
      </div>
      <div
        class="doc_wrap"
        style="height: calc(100% - 32px);"
      >
        <div
          ref="right"
          class="right"
        >
          <div
            v-for="(item, index) in projectReportLists"
            :key="item.id ? item.id : index"
            :class="{'active': currentObj.id === item.projectId,'disabled': !item.docFileId }"
            :title="`${item.rdTitle }-${ item.pname }`"
            class="row_wrap"
          >
            <div style="width: 100%; padding-left: 4px; overflow:hidden; text-overflow: ellipsis; white-space: nowrap;">
              <a-checkbox
                :checked="item.checked"
                @change="e => onCheckChange(e,item)"
              ></a-checkbox>
              <template v-if="item.done">
                <a-badge
                  v-if="item.done"
                  :dot="true"
                  :numberStyle="{ width:'6px', height: '6px' }"
                  :offset="[-2,4]"
                  color="#008000"
                  title="通过"
                >
                  <span>&nbsp;</span>
                </a-badge>
              </template>
              <template v-else>
                <a-badge
                  v-if="item.hasPermission"
                  :dot="true"
                  :numberStyle="{ width:'6px', height: '6px' }"
                  :offset="[-2,4]"
                  title="当前审核人"
                >
                  <span>&nbsp;</span>
                </a-badge>
                <template v-else>
                  <a-badge
                    v-if="item.auditStatus === 0"
                    :dot="true"
                    :numberStyle="{ width:'6px', height: '6px' }"
                    :offset="[-2,4]"
                    color="#1890ff"
                    title="进行中"
                  >
                    <span>&nbsp;</span>
                  </a-badge>
                </template>
              </template>
              <span :class="{'indent-title':!(item.hasPermission || item.done)}">{{ index + 1
              }}.</span>
              <span @click="clickRow(item)">{{ item.rdTitle }}</span>
            </div>
          </div>
        </div>
        <div class="content">
          <div style="position: relative;">
            <div class="header">
              <div
                v-if="currentObj.item && currentObj.item.docFileId"
                class="menu_icon_box"
              >
                <a-icon
                  :type="isShowSider ? 'menu-fold': 'menu-unfold'"
                  @click="expendRight"
                />
              </div>
              <template v-if="currentObj.item && currentObj.item.docFileId">
                {{ currentObj.item && currentObj.item.pname + ' - ' ? currentObj.item.pname : ''
                }}{{ currentObj.item && currentObj.item.docFileName ? currentObj.item.docFileName : ''
                }}
                <hr>
              </template>
            </div>
            <div
              ref="previewDoc"
              class="preview_doc"
              v-html="htmlData"
            ></div>
          </div>
        </div>
      </div>
      <batch-activate-modal
        ref="batchActivateModal"
        @refresh="updataStatus"
      />
      <project-check-modal v-if="$auth('customer:projectProgress:checkdup')" ref="checkModal" checkUrl="/projectcheck/getDuplicateReportList" checkId="docFileId" @viewDiff="viewDiff" />
      <report-diff-modal v-if="$auth('customer:projectProgress:checkdup')" ref="diffModal" />
    </template>
    <template #down>
      <audit-log
        ref="auditLog"
        :full="full"
        :active="active"
        :currentObj="currentObj.item"
        :params="logGetParams"
        :configParams="configParams"
        :title="currentObj.item.rdTitle && currentObj.item.pname ? currentObj.item.rdTitle +'-'+ currentObj.item.pname : ''"
        @updataStatus="updataStatus"
      />
    </template>
  </tab-layout>
</template>

<script>
import FileItem from './FileItem'
import AuditLog from './AuditLog'
import { statusColor, statusMap } from '@/utils/processDoc/auditStatus'
import TabLayout from './TabLayout.vue'
import BatchActivateModal from './BatchActivateModal'
import ProjectCheckModal from './duplicate/ProjectCheckModal.vue'
import ReportDiffModal from './duplicate/ReportDiffModal.vue'
export default {
  name: 'ProjectTab',
  components: {
    FileItem,
    AuditLog,
    TabLayout,
    BatchActivateModal,
    ProjectCheckModal,
    ReportDiffModal
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
  mounted () {
    this.loadData()
  },
  watch: {
    active (v) {
      if (v === 5) {
        this.logGetParams = {
          projectId: undefined,
          docFileId: undefined,
          moduleId: undefined
        }
        this.loadData()
      }
    }
  },
  data () {
    return {
      isShowSider: true,
      statusMap,
      statusColor,
      tableData: [],
      projectReportLists: [],
      spinning: false,
      htmlData: '',
      auditStatus: {},
      logGetParams: { // 日志请求数据
        projectId: undefined,
        docFileId: undefined,
        moduleId: undefined
      },
      configParams: {
        rsProjectId: undefined,
        year: undefined,
        companyId: undefined
      },
      currentObj: {
        id: -1,
        item: {},
        auditStatus: {}
      },
      isAllChecked: false,
      isIndeterminate: false
    }
  },
  computed: {
    batchActivateList () {
      return this.projectReportLists.filter(item => item.checked && item.auditStatus === 1)
    },
    checkedList () {
      return this.projectReportLists.filter(item => item.checked)
    }
  },
  methods: {
    viewDiff (source, target) {
      this.$refs.diffModal.open(source, target)
    },
    getHtmlTemplateStr () {
      if (this.currentObj.id < 0 || !(this.currentObj.item && this.currentObj.item.docFileId)) {
        return
      }
      const params = {
        pDocFileId: this.currentObj.item.docFileId,
        projectId: this.currentObj.item.projectId,
        // currentYear: this.currentObj.item.year,
        currentYear: this.record.year, // 20210707要求更改
        companyId: this.currentObj.item.companyId
      }
      this.spinning = true
      this.htmlData = ''
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
          this.spinning = false
          this.renderContent()
        }, 300)
      })
    },
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
    loadData () {
      const params = {
        year: this.record.year,
        companyId: this.record.companyId
      }
      this.isIndeterminate = false
      this.isAllChecked = false
      this.$http.get('/projectProgress/getProjectReport', { params })
        .then(res => {
          if (res.success && res.data) {
            if (res.data && res.data.length > 0) {
              this.projectReportLists = res.data
              let temCurrentObj
              for (let i = 0; i < res.data.length; i++) {
                if (res.data[i].projectId === this.currentObj.id) {
                  temCurrentObj = res.data[i]
                  break
                }
              }
              if (!temCurrentObj) {
                temCurrentObj = this.projectReportLists[0]
              }
              this.currentObj.id = temCurrentObj.projectId
              this.currentObj.item = temCurrentObj
              this.currentObj.auditStatus = temCurrentObj.auditStatus
              this.auditStatus = temCurrentObj.auditStatus
              this.logGetParams.docFileId = temCurrentObj.docFileId
              this.configParams.rsProjectId = temCurrentObj.projectId
              this.configParams.companyId = temCurrentObj.companyId
              this.configParams.year = temCurrentObj.year
              this.getHtmlTemplateStr()
            } else {
              this.currentObj = { id: -1, item: {}, auditStatus: {} }
              this.logGetParams.docFileId = undefined
              this.$refs.auditLog.reset()
            }
          }
        }).catch((error) => {
          console.log('error', error)
        })
    },
    clickRow (item) {
      if (!item.docFileId) {
        return
      }
      this.currentObj.id = item.projectId
      this.currentObj.item = item
      this.currentObj.auditStatus = item.auditStatus
      this.auditStatus = item.auditStatus
      this.logGetParams.docFileId = item.docFileId
      this.configParams.rsProjectId = item.projectId
      this.configParams.companyId = item.companyId
      this.configParams.year = item.year
      this.getHtmlTemplateStr()
    },
    callBack () {
      this.loadData()
    },
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
    updataStatus () {
      this.$emit('updataStatus')
      this.loadData()
    },
    onBatchActivate () {
      this.$refs.batchActivateModal.reportShow('批量激活立项报告', this.batchActivateList)
    },
    onAllChange (e) {
      const status = e.target.checked
      this.isAllChecked = status
      this.projectReportLists.forEach(item => {
        this.$set(item, 'checked', status)
      })
      this.isIndeterminate = undefined
    },
    onCheckChange (e, item) {
      this.$set(item, 'checked', e.target.checked)
      const isOnechecked = this.projectReportLists.some(item => {
        return !item.checked
      })
      const isAllNoChecked = this.projectReportLists.every(item => {
        return !item.checked
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
    }
  }
}
</script>

<style
  lang="less"
  scoped
>
@import '../css/AuditDrawerTab.less';
@import '../css/ReportTab.less';

* /deep/ .vxe-table .vxe-body--expanded-column.col--ellipsis > .vxe-body--expanded-cell {
  overflow: auto;
}

.indent-title {
  padding-left: 3px;
}

#up {
  .row_wrap {
    padding: 3px 3px;
    width: 100%;
    display: flex;
    flex-direction: row;

    &:hover {
      cursor: pointer;
      background-color: #E6F7FF;
    }
  }

  .active {
    background-color: #E6F7FF;
  }

  .disabled {
    color: #B8B8B8 !important;
    cursor: not-allowed !important;
  }
}
</style>
