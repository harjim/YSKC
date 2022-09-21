<!--
 * @Author: ldx
 * @Date: 2021-07-14 08:56:43
 * @LastEditTime: 2021-11-06 11:12:51
 * @LastEditors: zdf
 * @Description: 专利申请抽屉
 * @FilePath: \MS-VUE\src\views\patent\modules\PatentPlanDrawer.vue
-->
<template>
  <div>
    <a-drawer
      :title="title"
      width="1184"
      placement="right"
      :closable="true"
      :destroyOnClose="true"
      :visible="visible"
      :bodyStyle="{padding: '12px 12px',height: 'calc(100% - 55px)',overflow: 'hidden',backgroundColor: '#F3F5FA'}"
      @close="onClose()"
    >
      <a-spin :spinning="spinning" tip="请稍后...">
        <a-row>
          <a-col :span="4">
            <div style="height: 92vh;">
              <a-card title="流程进度" style="width:100%;height:100%;overflowY:auto;">
                <a-steps direction="vertical" size="small" :current="curProcess">
                  <a-step v-for="(item,i) in processes" :key="i" :disabled="getStatus(i)">
                    <template #title>
                      <span v-if="curProcess === i" style="font-weight:600;font-size:14px;">{{ item.label }}</span>
                      <span v-else style="font-size:14px;">{{ item.label }}</span>
                      <template v-if="i === 0">
                        <span :style="{color: statusColor[row.status],fontSize:'12px'}">({{ getStatusName(row.status) }})</span>
                      </template>
                    </template>
                    <template v-slot:description>
                      <div style="font-size:12px;">
                        <!-- 审核状态 -->
                        <div style="padding-top:8px">
                          <template v-if="i === 0" >
                            <div style="color:#333;padding:0;padding-bottom:4px;" v-if="patentInfos.lastLog"><ellipsis :length="50" :tooltip="true">{{ patentInfos.lastLog }}</ellipsis></div>
                            <div style="color:#999;padding:0;" v-if="patentInfos.lastLogTime">
                              {{ moment(patentInfos.lastLogTime).format('YYYY-MM-DD') }}
                            </div>
                          </template>
                          <template v-else>
                            <template v-if="patentInfos[i]">
                              <div style="color:#333;padding:0;padding-bottom:4px;"><ellipsis :length="30" :tooltip="true">{{ patentInfos[i].remark }}</ellipsis></div>
                              <div style="color:#999;padding:0;">{{ moment(patentInfos[i].createTime).format('YYYY-MM-DD') }}</div>
                            </template>
                          </template>
                        </div>
                      </div>
                    </template>
                  </a-step>
                </a-steps>
              </a-card>
            </div>
          </a-col>
          <a-col :span="20">
            <div :style="{height: `${bodyVh}vh`,overflowY:'auto'}">
              <div class="infoCard" style="padding-top:0;">
                <base-info
                  :row="row"
                  :onAdd="onAdd"
                  :canEdit="canEdit"
                  :planTypes="planTypes"
                  :patentTypes="patentTypes"
                  :onPreview="onPreview"
                  :control="control"
                  :baseFiles="patentInfos.patentFiles ? patentInfos.patentFiles[0] : []"
                  @onClose="onClose"
                  @getInfo="loadInfo"/>
              </div>
              <div class="infoCard" v-if="control.process && curProcess">
                <patent-info :patentTypes="patentTypes" :row="patentInfos.patentInfo || {}" @getInfo="loadInfo" :patentPlan="row"/>
              </div>
              <div class="infoCard">
                <patent-files
                  title="资料"
                  @fileChange="fileChange"
                  :control="control"
                  :onPreview="onPreview"
                  :files="patentInfos.patentFiles || {}"
                  :row="row"
                  :canUpload="canUpload"/>
              </div>
            </div>
            <div :style="{height:`${92-bodyVh}vh`}" v-if="!onAdd && canOperation">
              <div class="infoCard">
                <patent-operation
                  ref="patentOperation"
                  :row="row"
                  :auditPermission="patentInfos.auditPermission"
                  :audit="control.audit"
                  @getInfo="loadInfo"
                  :canProcess="canUpload"
                  :processes="processes"
                  :curProcess="curProcess"
                  :files="patentInfos.patentFiles || {}"
                  :instanceId="patentInfos.instanceId"/>
              </div>
            </div>
          </a-col>
        </a-row>
      </a-spin>
    </a-drawer>
    <preview-modal ref="previewModal"/>
  </div>
</template>

<script>
import Ellipsis from '@/components/Ellipsis'
import moment from 'moment'
import BaseInfo from './patentPlan/BaseInfo'
import PatentInfo from './patentPlan/PatentInfo.vue'
import PatentFiles from '@/components/Patent/PatentFiles.vue'
import FileUploadModal from './FileUploadModal.vue'
import PreviewModal from '@/components/PreviewModal'
import PatentOperation from './patentPlan/PatentOperation.vue'
import { isEditStatus, isExportStatus, getStatusName, statusColor } from '@/utils/processDoc/auditStatus'
import Template from '@/views/doc/Template.vue'
export default {
  name: 'PatentPlanDrawer',
  components: {
    BaseInfo,
    FileUploadModal,
    PreviewModal,
    PatentInfo,
    PatentFiles,
    PatentOperation,
    Ellipsis,
    Template
  },
  props: {
    control: {
      type: Object,
      required: true
    },
    planTypes: {
      type: Array,
      required: true
    },
    processes: {
      type: Array,
      required: true
    },
    patentTypes: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      visible: false,
      title: '',
      row: {},
      onAdd: false,
      spinning: false,
      statusColor,
      patentInfos: { patentInfo: {} },
      closeFresh: false
    }
  },
  computed: {
    bodyVh () {
      return this.canOperation ? 62 : 92
    },
    canEdit () {
      return this.control.edit && this.row.id && this.isEditStatus(this.row.status)
    },
    canUpload () {
      return this.control.upload && this.row.id && this.isExportStatus(this.row.status)
    },
    canOperation () {
      return ((this.control.audit && this.patentInfos.auditPermission && this.row.process === 0) || this.canUpload) && (this.row.process < 4)
    },
    curProcess () {
      return this.row.id && this.canUpload ? this.row.process + 1 : 0
    }
  },
  methods: {
    moment,
    isEditStatus,
    isExportStatus,
    getStatusName,
    add () {
      this.onAdd = true
      this.row = { process: 0 }
      this.title = '添加专利申请'
      this.visible = true
    },
    view (row) {
      this.onAdd = false
      this.row = row
      this.title = `${row.companyName}-${row.year}-${row.patentName}`
      this.visible = true
      this.loadInfo(true)
    },
    loadInfo (open) {
      if (!open) {
        this.closeFresh = true
      }
      this.spinning = true
      this.patentInfos = { patentInfo: {} }
      this.$http.get('/patentPlan/getInfo', { params: { patentPlanId: this.row.id, patentNo: this.row.patentNo } }).then(res => {
        if (res.success && res.data) {
          if (!res.data.patentInfo) {
            res.data.patentInfo = {}
          }
          res.data.patentInfo.patentPlanId = this.row.id
          this.patentInfos = res.data
        } else {
          this.$message.error(res.errorMessage || '加载专利信息失败，请稍后重试。')
        }
        this.spinning = false
      }).finally(() => {
        this.fileChange()
      })
    },
    fileChange () {
      if (this.$refs.patentOperation && this.$refs.patentOperation.loadFilterProcess) {
        this.$refs.patentOperation.loadFilterProcess()
      }
    },
    getDesc (i) {
      if (i === 0) {
        return ''
      }
      return `2021-0${i}-0${i}`
    },
    getStatus (i) {
      return this.row.status === i
    },
    onClose (refresh) {
      this.patentInfos = {}
      this.spinning = false
      this.visible = false
      if ((refresh !== null && refresh !== undefined) || this.closeFresh) {
        this.$emit('ok', refresh || this.closeFresh)
      }
      this.closeFresh = false
    },
    onPreview (path, name) {
      this.$refs.previewModal.show(path, name)
    }
  }
}
</script>

<style lang="less" scoped>
.infoCard{
width:100%;
padding-left:1%;
padding-top:1%;
}
</style>
