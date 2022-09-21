<!--
 * @Author: ldx
 * @Date: 2021-03-02 08:09:44
 * @LastEditTime: 2022-05-17 10:09:17
 * @LastEditors: zdf
 * @Description: 查新报告Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\NoveltySearchTab.vue
-->
<template>
  <tab-layout>
    <template #up>
      <div style="margin-bottom: 8px; padding-left: 3px;">
        <a-checkbox
          :checked="isAllChecked"
          @change="onCheckboxChange"
          :indeterminate="isIndeterminate"
        >全选
        </a-checkbox>
        <a-button
          v-if="$auth('customer:projectProgress:auditBatch')"
          type="primary"
          @click="onBatchAudid"
          size="small"
          :disabled="batchAuditList.length <= 0"
        >批量审核
        </a-button>
        <a-button
          style="margin-left: 10px;"
          v-if="$auth('customer:projectProgress:activateFlows')"
          type="primary"
          @click="onBatchActivate"
          size="small"
          :disabled="batchActivateList.length <= 0"
        >批量激活
        </a-button>
        <a-button
          style="margin-left: 10px;"
          type="primary"
          size="small"
          @click="updataStatus"
        >刷新
        </a-button>
      </div>
      <div
        style="height: calc(100% - 32px) ;"
        class="table_wrap"
      >
        <div
          class="right"
          ref="right"
        >
          <div
            v-for="(item, index) in projectList"
            :key="item.id"
            class="row_wrap"
            :class="{'active': activeProject.projectId === item.projectId }"
            :title="`${item.rdTitle }-${ item.pname }`"
          >
            <a-checkbox
              style="margin-right: 5px;"
              :checked="item.checked"
              @change="(event) => itemCheckboxChange(event,item)"
            ></a-checkbox>
            <div
              @click="onClickProject(item)"
              style="width: 100% ; padding-left: 4px; overflow:hidden; text-overflow: ellipsis; white-space: nowrap;"
            >
              <a-badge
                v-if="item.auditStatus === 1"
                :numberStyle="{ width:'6px', height: '6px' }"
                :dot="true"
                title="通过"
                :offset="[-2,4]"
                color="#008000"
              >
                <span>&nbsp;</span>
              </a-badge>
              <template v-else>
                <a-badge
                  v-if="item.hasPermission"
                  :numberStyle="{ width:'6px', height: '6px' }"
                  :dot="true"
                  title="当前审核人"
                  :offset="[-2,4]"
                >
                  <span>&nbsp;</span>
                </a-badge>
                <template v-else>
                  <a-badge
                    v-if="item.auditStatus === 0"
                    :numberStyle="{ width:'6px', height: '6px' }"
                    :dot="true"
                    title="进行中"
                    :offset="[-2,4]"
                    color="#1890ff"
                  >
                    <span>&nbsp;</span>
                  </a-badge>
                </template>
              </template>
              <span :class="{'indent-title':!(item.hasPermission || item.done)}">{{ index + 1
              }}.</span>
              {{ item.rdTitle }}
            </div>
          </div>
        </div>
        <div class="content">
          <div class="header">
            <!-- <div class="menu_icon_box">
              <a-icon @click="expendRight" :type="isShowSider ? 'menu-fold': 'menu-unfold'" />
            </div> -->
            <template>
              {{ activeProject && activeProject.pname || '' }}
              <hr>
            </template>
          </div>
          <div class="table_box">
            <ystable
              ref="table"
              height="auto"
              highlight-hover-row
              highlight-current-row
              show-overflow="title"
              resizable
              auto-resize
              size="small"
              @completed="completed"
              queryUrl="/docList/getDocList"
              :autoLoad="false"
              :params="params"
            >
              <vxe-table-column
                title="序号"
                type="seq"
                :width="70"
                align="center"
                headerAlign="center"
              ></vxe-table-column>
              <!-- <vxe-table-column title="RD" field="rdTitle" :width="150" align="left" headerAlign="center"></vxe-table-column>
            <vxe-table-column title="项目名称" field="pname" :width="150" align="left" headerAlign="center"></vxe-table-column>
            <vxe-table-column title="项目阶段" field="stageKey" :width="120" align="left" headerAlign="center">
              <template #default="{ row }">
                {{ stagesKV[row.stageKey] }}
              </template>
            </vxe-table-column> -->
              <vxe-table-column
                title="文档名"
                field="fileName"
                align="left"
                headerAlign="center"
              >
                <template #default="{ row }">
                  <a
                    v-if="$auth('customer:projectProgress:download')"
                    @click="download(row)"
                    title="点击下载"
                  >{{ row.fileName }}</a>
                  <span v-else> {{ row.fileName }}</span>
                  <span v-if="$auth('customer:projectProgress:preview')">
                    <a-tooltip
                      style="cursor:pointer"
                      placement="top"
                      @click="preview(row)"
                    >
                      <template slot="title">
                        <span>预览</span>
                      </template>
                      <a-icon
                        type="eye"
                        style="margin-left:5px"
                      />
                    </a-tooltip>
                  </span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="上传时间"
                field="createTime"
                :width="180"
                align="center"
                headerAlign="center"
              >
                <template #default="{ row }">
                  {{ row.createTime | DayFormat }}
                </template>
              </vxe-table-column>
            </ystable>
          </div>
          <preview-modal ref="previewModal" />
        </div>
      </div>
      <batch-audit-modal
        ref="batchAuditModal"
        @refresh="updataStatus"
      ></batch-audit-modal>
      <batch-activate-modal
        ref="batchActivateModal"
        @refresh="updataStatus"
      />
    </template>
    <template #down>
      <audit-log
        ref="auditLog"
        :full="full"
        :active="active"
        :currentObj="activeProject"
        :configParams="{
          rsProjectId: activeProject.projectId,
          companyId: activeProject.companyId,
          year: activeProject.year
        }"
        @updataStatus="updataStatus"
        :params="logGetParams"
        :title="activeProject.rdTitle && activeProject.pname ? activeProject.rdTitle + '-' + activeProject.pname : ''"
      />
    </template>
  </tab-layout>
</template>

<script>
import BatchAuditModal from './BatchAuditModal'
import BatchActivateModal from './BatchActivateModal'
import TabLayout from './TabLayout'
import ystable from '@/components/Table/ystable'
import PreviewModal from '@/components/PreviewModal'
import AuditLog from './AuditLog'

export default {
  name: 'NoveltySearchTab',
  components: {
    ystable,
    AuditLog,
    TabLayout,
    PreviewModal,
    BatchAuditModal,
    BatchActivateModal
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
    // 获取项目阶段字典
    this.$getDictionary(6).then(res => {
      if (res) {
        res.forEach(item => {
          this.stagesKV[item.key] = item.value
        })
      }
    })
    this.loadPojrectListData()
  },
  data () {
    return {
      tableData: [],
      btnLoading: false,
      stagesKV: {},
      projectId: undefined,
      headerInfo: {
        researchCnt: '-',
        techCnt: '-',
        assistCnt: '-'
      },
      projectList: [], // 项目列表
      activeProject: {}, // 当前项目
      params: {
        year: undefined,
        companyId: undefined,
        projectId: undefined
      },
      logGetParams: {
        docFileId: undefined
      },
      isShowSider: true,
      isFirst: true,
      isIndeterminate: false,
      isAllChecked: false
    }
  },
  computed: {
    batchAuditList () {
      return this.projectList.filter(item => item.checked && item.hasPermission)
    },
    batchActivateList () {
      return this.projectList.filter(item => item.checked && item.auditStatus === 1)
    }
  },
  methods: {
    completed (data) {
      Object.assign(this.headerInfo, data.data.header)
    },
    callBack () {
      this.loadPojrectListData()
    },
    onCheckboxChange (e) {
      this.isAllChecked = e.target.checked
      this.projectList.forEach(item => {
        this.$set(item, 'checked', e.target.checked)
      })
      if (!e.target.checked) {
        this.isIndeterminate = false
      } else {
        this.isIndeterminate = undefined
      }
    },
    itemCheckboxChange (e, item) {
      item.checked = e.target.checked
      const isOnechecked = this.projectList.some(item => {
        return !item.checked
      })
      const isAllNoChecked = this.projectList.every(item => {
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
    },
    updataStatus () {
      this.$emit('updataStatus')
      this.loadPojrectListData()
    },
    resetValue () {
      this.isIndeterminate = false
      this.isAllChecked = false
    },
    /**
     * @description: 加载项目列表数据
     * @param {*} {year,companyId}
     * @return {*} null
     */
    loadPojrectListData () {
      this.resetValue()
      const { year, companyId } = this.record
      this.$http.get('/projectProgress/getProjectNewReport', {
        params: {
          year,
          companyId
        }
      }).then((response) => {
        if (response.data && response.success) {
          this.projectList = response.data
          if (!this.projectList.length) {
            return
          }
          this.projectList.map((item, index) => {
            this.$set(item, 'checked', false)
          })
          this.activeProject = this.projectList[0]
          this.params.year = this.record.year
          this.params.companyId = this.record.companyId
          this.params.projectId = this.activeProject.projectId
          this.logGetParams.docFileId = this.activeProject.docFileId
          if (!this.isFirst && this.$refs.auditLog && this.$refs.auditLog.handleGetAuditLog) {
            this.$refs.auditLog.handleGetAuditLog()
          } else {
            this.isFirst = false
          }
          this.$refs.table.refresh()
        } else {
          this.$message.error(`${response.errorCode}: ${response.errorMessage}`)
          console.log(`loadPojectListData function error ${response.errorCode}: ${response.errorMessage}`)
        }
      }).catch((error) => {
        this.$message.error(error.message)
        console.log(`loadPojectListData function error`, error.message)
      }).finally((res) => {
      })
    },
    onClickProject (item) {
      this.activeProject = item
      this.params.projectId = item.projectId
      this.logGetParams.docFileId = item.docFileId
      this.$refs.table.refresh()
    },
    /**
     * @description: 下载
     * @param {*} null
     * @return {*} null
     */
    download (record) {
      this.$exportData('/docList/downloadFile', {
        fileName: record.fileName,
        filePath: record.filePath
      }, record.fileName, this.$message).then(res => {
      })
    },
    preview (row) {
      if (row.filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      const fileName = row.fileName ? row.fileName : ''
      this.$refs.previewModal.show(row.filePath, fileName, `${row.pname}${fileName ? '-' + fileName : ''}`)
    },
    /**
     * @description: 打开批量审核modal
     * @param {*} null
     * @return {*} null
     */
    onBatchAudid () {
      this.$refs.batchAuditModal.noveltSearchShow('批量查新报告审核', this.batchAuditList)
    },
    onBatchActivate () {
      this.$refs.batchActivateModal.noveltSearchShow('批量查新报告激活', this.batchActivateList)
    }
    // expendRight () {
    //   this.isShowSider = !this.isShowSider
    //   const right = this.$refs.right
    //   if (!this.isShowSider) {
    //     right.style.width = 0 + 'px'
    //     right.style.overflowX = 'hidden'
    //   } else {
    //     right.style.width = 255 + 'px'
    //   }
    // }
  }
}
</script>

<style
  lang="less"
  scoped
>
@import '../css/AuditDrawerTab.less';
@import '../css/NoveltySearchTab.less';

* /deep/ .vxe-table .vxe-body--expanded-column.col--ellipsis > .vxe-body--expanded-cell {
  overflow: auto;
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
