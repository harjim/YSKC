<!--
 * @Author: ldx
 * @Date: 2021-03-02 08:28:13
 * @LastEditTime: 2022-06-09 18:34:57
 * @LastEditors: zdf
 * @Description: 创新体系审核Tab
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\InnovateTab.vue
-->
<template>
  <tab-layout>
    <template #up>
      <div class="table_wrap">
        <a-spin
          :spinning="spin"
          style="height:100%"
          tip="请稍后..."
        >
          <div style="display: flex;align-content: center;justify-content: flex-end;">
            <a-switch
              v-if="$auth('customer:projectProgress:score')"
              checked-children="评分"
              un-checked-children="评分"
              :checked="showScore"
              @change="onChangeSwitch"
            />
          </div>
          <vxe-grid
            ref="table"
            :data="tableDatas"
            border
            size="mini"
            resizable
            auto-resize
            max-height="96%"
            highlight-hover-row
            show-overflow
            show-header-overflow
            highlight-current-row
            header-align="center"
          >
            <vxe-table-column
              type="seq"
              title="序号"
              width="60"
              align="center"
              header-align="center"
              fixed="left"
            />
            <vxe-table-column
              align="left"
              field="rdActivities"
              fixed="left"
              title="研发活动"
              width="160"
            />
            <vxe-table-column
              align="left"
              field="docName"
              title="建设事项"
              width="160"
            />
            <vxe-table-column
              align="left"
              field="operators"
              title="负责岗位"
              width="160"
            />
            <vxe-table-column
              align="left"
              field="buildFiles"
              minWidth="300"
              title="制度文件"
            >
              <template v-slot="{ row }">
                <template v-if="row.buildFiles.length">
                  <span
                    v-for="(file, index) in row.buildFiles"
                    :key="index"
                    style="margin-right:8px;"
                  >
                    <!-- buildType 为0或为空时，默认为制度文件 -->
                    <a
                      v-if="control.download"
                      title="点击下载"
                      @click="downloadFile(file)"
                    >
                      {{ file.fileName }}
                    </a>
                    <span v-else>
                      {{ file.fileName }}
                    </span>
                    <a-tooltip
                      v-if="control.preview"
                      placement="top"
                      style="cursor:pointer"
                      @click="preview(file)"
                    >
                      <template slot="title">
                        <span>预览</span>
                      </template>
                      <a-icon
                        style="margin-left:5px"
                        type="eye"
                      />
                    </a-tooltip>
                  </span>
                </template>
                <template v-else-if="row.subClassify">
                  <a
                    v-if="control.download"
                    title="点击下载"
                    @click="generateBuildFile(row)"
                  >
                    {{ row.docName + '.docx' }}
                  </a>
                  <span v-else>
                    {{ row.docName + '.docx' }}
                  </span>
                </template>
              </template>
            </vxe-table-column>
            <vxe-table-column
              align="left"
              field="proofFiles"
              minWidth="300"
              title="佐证材料"
            >
              <template v-slot="{ row }">
                <span
                  v-for="(file, index) in row.proofFiles"
                  :key="index"
                  style="margin-right:8px;"
                >
                  <a
                    v-if="control.download"
                    title="点击下载"
                    @click="downloadFile(file)"
                  >
                    {{ file.fileName }}
                  </a>
                  <span v-else>
                    {{ file.fileName }}
                  </span>
                  <a-tooltip
                    v-if="control.preview"
                    placement="top"
                    style="cursor:pointer"
                    @click="preview(file)"
                  >
                    <template slot="title">
                      <span>预览</span>
                    </template>
                    <a-icon
                      style="margin-left:5px"
                      type="eye"
                    />
                  </a-tooltip>
                </span>
              </template>
            </vxe-table-column>
          </vxe-grid>
        </a-spin>
        <rs-preview-modal ref="rsPreviewModal"></rs-preview-modal>
      </div>
    </template>
    <template #down>
      <audit-log
        ref="auditLog"
        :params="logGetParams"
        title="创新体系"
        @updataStatus="updataStatus"
      ></audit-log>
    </template>
  </tab-layout>
</template>

<script>
import TabLayout from './TabLayout'
import ystable from '@/components/Table/ystable'
import RsPreviewModal from '@/components/PreviewModal'
import { isEditStatus } from '@/utils/processDoc/auditStatus'
import AuditLog from './AuditLog'

export default {
  name: 'InnovateTab',
  components: {
    ystable,
    AuditLog,
    TabLayout,
    RsPreviewModal,
    isEditStatus
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
    }
  },
  mounted () {
    this.params.year = this.record.year
    this.params.companyId = this.record.companyId
    this.params.companyName = this.record.companyName
    this.logGetParams.companyId = this.record.companyId
    this.logGetParams.year = this.record.year
    this.logGetParams.moduleId = this.mode.moduleId
    // this.callBack()
  },
  data () {
    return {
      paramData: {
        fileType: 0,
        year: this.currentYear,
        projectType: 1,
        projectId: 0,
        listId: 0
      },
      listType: 6001,
      spin: false,
      tableDatas: [],
      control: {
        download: this.$auth('customer:projectProgress:download'),
        preview: this.$auth('customer:projectProgress:preview')
      },
      uploadTitle: '',
      headerInfo: {
        researchCnt: '-',
        techCnt: '-',
        assistCnt: '-'
      },
      params: {
        year: undefined,
        companyId: undefined
      },
      logGetParams: {
        companyId: undefined,
        year: undefined,
        moduleId: undefined
      },
      showScore: false
    }
  },
  created () {
    this.paramData.year = this.currentYear
    this.search()
  },
  methods: {
    completed (data) {
      Object.assign(this.headerInfo, data.data.header)
    },
    callBack () {
      if (this.$refs.auditLog && this.$refs.auditLog.handleGetAuditLog) {
        this.$refs.auditLog.handleGetAuditLog()
      }
    },
    updataStatus () {
      this.$emit('updataStatus')
    },
    /**
     * @description: 加载数据
     * @param {*} {year,companyId}
     * @return {*} null
     */
    preview (file) {
      if (file.filePath === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.rsPreviewModal.show(file.filePath, file.fileName !== undefined ? file.fileName : '')
    },
    downloadFile (file) {
      this.spin = true
      this.$exportData('/sysDocument/downloadFile', { id: file.id }, file.fileName, this.$message).then(res => {
        this.spin = false
      })
    },
    generateBuildFile (row) {
      this.spin = true
      this.$exportData(
        '/sysDocument/generateBuildFile',
        {
          year: this.params.year,
          docName: row.docName,
          companyId: this.params.companyId,
          companyName: this.params.companyName
        },
        undefined,
        this.$message
      ).then(res => {
        this.spin = false
      })
    },
    search () {
      this.paramData.year = this.currentYear
      this.$nextTick(() => {
        this.spin = true
        this.$http
          .get('/sysDocument/queryAppendixDocList', {
            params: {
              listType: this.listType,
              year: this.params.year,
              companyId: this.params.companyId
            }
          })
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
              this.$message.error(res.errorMessage ? res.errorMessage : '获取文档失败，请联系管理员。')
            }
            return res.data
          })
          .finally(() => {
            this.spin = false
          })
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
* /deep/ .vxe-table .vxe-body--expanded-column.col--ellipsis > .vxe-body--expanded-cell {
  overflow: auto;
}
</style>
