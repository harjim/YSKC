<!--
 * @Author: your name
 * @Date: 2022-02-21 16:30:36
 * @LastEditors: lzh
 * @LastEditTime: 2022-03-19 11:19:48
 * @Description: In User Settings Edit
 * @FilePath: \MS-VUE\src\views\patent\modules\PatentPlanDrawerNew.vue
-->
<template>
  <div>
    <a-drawer
      :title="title"
      width="1240"
      :closable="true"
      :destroyOnClose="true"
      :visible="visible"
      @close="onDrawerClose()"
      :bodyStyle="{ background:' #f3f5fa'}"
    >
      <a-spin :spinning="spinning">
        <a-row :gutter="16" class="row-h100">
          <a-col :span="4" class="col-h100">
            <a-card title="节点" class="node-card">
              <a-steps :current="currentStep" direction="vertical" size="small">
                <a-step v-for="stepItem in stepItemList" :key="stepItem.value">
                  <template #title>
                    <span :style="{ fontWeight: stepItem.value === currentStep ? '700' : '' }">
                      <div>{{ stepItem.label }}</div>
                      <div v-if="stepItem.value === currentStep" :style="{ fontSize: '12px', paddingBottom: '4px', color: statusColor[row.status]}">{{ getStatusName(row.status) }}</div>
                    </span>
                  </template>
                  <template #description>
                    <ellipsis style="padding-top: 8px;" :length="50" :tooltip="true">{{ stepItem.remark }}</ellipsis>
                    <div class="create-time">{{ stepItem.createTime }}</div>
                  </template>
                </a-step>
              </a-steps>
            </a-card>
          </a-col>
          <a-col :span="20" class="col-h100" v-if="!spinning">
            <a-card size="small" class="info-card">
              <!-- 基本信息 -->
              <base-info
                ref="baseInfo"
                :currentStep="currentStep"
                :record="row"
                @preview="({url,name}) => {onPreview(url,name)}"
                @update="update"
                :baseFiles="patentInfos.patentFiles ? patentInfos.patentFiles[0] : []"/>
              <!-- 代理人分配 -->
              <AgentAssign :currentStep="currentStep" :row="row" :patentInfo="patentInfos.patentInfo || {}" @update="update" :auditPermission="patentInfos.auditPermission"></AgentAssign>
              <!-- 专利信息 受理信息 -->
              <patent-info
                :currentStep="currentStep"
                :row="row"
                :patentPlan="patentInfos.patentInfo || {}"
                :patentPlanInfo="patentInfos.patentPlanInfo || {}"
                :patentFiles="patentInfos.patentFiles || {}"
                :auditPermission="patentInfos.auditPermission"
                @update="update"></patent-info>
              <!-- 授权信息 -->
              <authorize
                :currentStep="currentStep"
                :row="row"
                :patentFiles="patentInfos.patentFiles || {}"
                :patentPlan="patentInfos.patentInfo || {}"
                :patentPlanInfo="patentInfos.patentPlanInfo || {}"
                :auditPermission="patentInfos.auditPermission"
                @update="update"></authorize>
            </a-card>
            <!-- 审批 -->
            <PatentAudit
              ref="patentAudit"
              :currentStep="currentStep"
              :row="row"
              :auditPermission="patentInfos.auditPermission"
              :files="patentInfos.patentFiles || {}"
              @update="update"></PatentAudit>
            <!-- 资料上传 -->
            <div>
              <patent-files
                v-if="currentStep >= 3"
                style="margin-top: 16px;"
                title="资料"
                :control="control"
                :onPreview="onPreview"
                :files="patentInfos.patentFiles || {}"
                :row="row"
                :fileChange="fileChange"
                :canUpload="canUpload"/>
            </div>
            <!-- 定稿 -->
            <Finalization :files="patentInfos.patentFiles || {}" :currentStep="currentStep" :row="row" :auditPermission="patentInfos.auditPermission" @update="update"></Finalization>
          </a-col>
        </a-row>
      </a-spin>
    </a-drawer>
    <preview-modal ref="previewModal"/>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import moment from 'moment'
import Ellipsis from '@/components/Ellipsis'
import { isEditStatus, isExportStatus, getStatusName, statusColor } from '@/utils/processDoc/auditStatus'
import BaseInfo from './patentPlan/BaseInfoNew'
import PatentAudit from './patentPlan/PatentAudit.vue'
import AgentAssign from './patentPlan/AgentAssign'
import Finalization from './patentPlan/Finalization'
import PatentInfo from './patentPlan/PatentInfoNew'
import Authorize from './patentPlan/Authorize'
import PreviewModal from '@/components/PreviewModal'
import PatentFiles from '@/components/Patent/PatentFiles.vue'

export default {
  components: {
    Ellipsis,
    BaseInfo,
    PreviewModal,
    PatentAudit,
    AgentAssign,
    Finalization,
    PatentInfo,
    Authorize,
    PatentFiles
  },
  data () {
    return {
      statusColor,
      title: '',
      visible: false,
      currentStep: 0,
      stepItemList: [],
      demandForm: this.$form.createForm(this),
      recordId: undefined,
      row: {}, // 打开抽屉再次请求得到的数据
      id: undefined,
      patentInfos: { patentInfo: {}, patentPlanInfo: {} },
      spinning: true,
      control: {
        search: this.$auth('patent:patentPlanNew:search'),
        add: this.$auth('patent:patentPlanNew:add'),
        edit: this.$auth('patent:patentPlanNew:edit'),
        patent: this.$auth('patent:patentPlanNew:patent'),
        // 兼容文件上传
        download: this.$auth('patent:patentPlanNew:edit'),
        preview: this.$auth('patent:patentPlanNew:edit'),
        deleteFile: this.$auth('patent:patentPlanNew:edit'),
        upload: this.$auth('patent:patentPlanNew:edit')
      }
    }
  },
  computed: mapState({
    patentPlanProcessType: state => state.enums.patentPlanProcessType,
    patentPlanStatus: state => state.enums.patentPlanStatus,
    patentPlanType: state => state.enums.patentPlanType,
    canUpload () {
      return Boolean(this.control.edit)
    }
  }),
  methods: {
    moment,
    isEditStatus,
    isExportStatus,
    getStatusName,
    onDrawerClose () {
      this.visible = false
    },
    add () {
      this.spinning = false
      this.title = '添加专利申请'
      this.row = {}
      this.record = {}
      this.initNodeData()
      this.visible = true
    },
    view (record) {
      this.stepItemList = this.patentPlanProcessType
      this.row = { ...record }
      this.title = `编辑专利申请【${this.row.patentName}】`
      this.currentStep = this.row.nodeNumber || 0
      this.load()
      this.visible = true
    },
    load () {
      this.spinning = true
      this.$http.get('/patentPlanNew/getInfo', {
        params: {
          patentPlanId: this.row.id,
          patentNo: this.row.patentNo
        }
      }).then(res => {
        if (res.success && res.data) {
          this.patentInfos = res.data
          this.handleNodeData(this.patentInfos)
        } else {
          this.$message.error(res.errorMessage || '加载专利信息失败，请稍后重试。')
        }
      }).catch(err => {
        this.$message.error(err.message || '加载专利信息失败，请稍后重试。')
      }).finally(() => {
        this.spinning = false
      })
    },
    // 初始化节点数据
    initNodeData () {
      this.currentStep = 0
      this.patentInfos.auditPermission = false
      this.stepItemList = [...this.patentPlanProcessType].map(item => {
        if (item.value === 0) {
          item.createTime = null
          item.remark = null
          item.auditPermission = null
        }
        return item
      })
    },
    // 处理节点数据
    handleNodeData (data) {
      this.stepItemList = [...this.patentPlanProcessType].map(item => {
        // 单独处理驳回状态，驳回日志在外层，驳回日志需要显示在驳回节点
        if (this.row.status === 2) {
          if (this.row.nodeNumber === 0 && item.value === 0) {
            // 第一个节点驳回
            item = {
              ...item,
              createTime: this.patentInfos.lastLogTime.slice(0, 10),
              remark: this.patentInfos.lastLog
            }
          } else if (this.row.nodeNumber === 1) {
            // 第二个节点驳回
            if (item.value === 1) {
              item = {
                ...item,
                createTime: this.patentInfos.lastLogTime.slice(0, 10),
                remark: this.patentInfos.lastLog
              }
            } else {
              if (data[item.value]) {
                data[item.value].createTime = data[item.value].createTime && data[item.value].createTime.slice(0, 10)
                item = {
                  ...item,
                  ...data[item.value]
                }
              }
            }
          }
        } else {
          if (data[item.value]) {
            data[item.value].createTime = data[item.value].createTime && data[item.value].createTime.slice(0, 10)
            item = {
              ...item,
              ...data[item.value]
            }
          }
        }
        return item
      })
    },
    onPreview (path, name) {
      this.$refs.previewModal.show(path, name)
    },
    update () {
      this.visible = false
      setTimeout(() => {
        this.$emit('update')
      }, 500)
    },
    fileChange () {
      if (this.$refs.patentAudit && this.$refs.patentAudit.loadFilterProcess) {
        this.$refs.patentOperation.loadFilterProcess()
      }
    }
  }
}
</script>

<style lang="less" scoped>
/deep/ .ant-drawer-body {
  height: calc(100% - 55px);
  // overflow: auto;
  padding: 10px;
}
.row-h100, .col-h100, .node-content, .node-card {
  height: 100%;
  overflow: auto;
}
/deep/ .ant-spin-nested-loading, /deep/ .ant-spin-container {
  height: 100%;
}
/deep/ .info-card {
  .ant-form-item-label {
    line-height: 34px;
    label {
      color: #99aec2;
    }
  }
  .ant-form-item-control-wrapper {
    color: #000000;
    height: 27px;
  }
  // 不是编辑状态
  .status-not-edit {
    // .ant-form-item {
    //   margin-bottom: 12px;
    // }
    .ant-form-item-label {
      line-height: 24px;
    }
  }
}

/deep/ .ant-divider {
  margin-bottom: 8px;
  &::before {
    width: 2%;
  }
  .ant-divider-inner-text {
    color: #000000;
    padding: 0 6px;
  }
}
/deep/ .node-card {
  .ant-steps-item-finish > .ant-steps-item-container > .ant-steps-item-content > .ant-steps-item-title {
    color: #000000;
  }
  .ant-steps-item-container > .ant-steps-item-content > .ant-steps-item-description {
    color: #000000d9;
    font-size: 12px;
    .create-time {
      padding-top: 2px;
      color: #00000073;
    }
  }
}

/deep/ .ant-form-item-control{
  line-height: 24px;
}
/deep/ .textarea .ant-form-item-control-wrapper{
  height: auto !important;
}

</style>
