<template>
  <a-modal
    :destroyOnClose="true"
    :width="1000"
    :visible="visible"
    :title="title"
    :footer="null"
    :maskClosable="false"
    @cancel="closeModal"
  >
    <div>
      <a-tabs v-model="actvieKey" @change="callback">
        <a-tab-pane v-if="$auth('project:report:base:view')" :tab="`基本信息`" key="1">
          <base-tab
            ref="baseTab"
            :projectData="projectData"
            :modify="modify"
            @updated="updateProjectData"
            :modifyStatus="modifyStatus"
            :deptTree="deptTree"
          ></base-tab>
        </a-tab-pane>
        <a-tab-pane v-if="$auth('project:report:member:search')" :tab="`项目成员`" key="2">
          <member-tab ref="memberTab" :projectData="projectData" @update="this.refresh"></member-tab>
        </a-tab-pane>
        <a-tab-pane v-if="$auth('project:report:equipment:search')" :tab="`资产清单`" key="3">
          <equipment-tab ref="equipmentTab" :projectData="projectData"></equipment-tab>
        </a-tab-pane>
        <a-tab-pane v-if="$auth('project:report:stage:search')" :tab="`阶段`" key="4">
          <stage-tab
            ref="stageTab"
            :projectData="projectData"
            :modifyStatus="true"
            @showEdit="onShowEdit"
          ></stage-tab>
        </a-tab-pane>
        <a-tab-pane v-if="$auth('project:report:changeLog:view')" tab="变更记录" key="5">
          <change-log-tab ref="changeLogTab" :projectData="projectData" :isEditChange.sync="isEditChange"></change-log-tab>
        </a-tab-pane>
      </a-tabs>
    </div>
    <a-modal
      title="自定义名称"
      cancelText="应用"
      destroyOnClose
      :maskClosable="false"
      :visible="showEdit"
      @cancel="() => this.showEdit = false"
    >
      <template slot="footer">
        <a-button @click="onEditStage(false)">应用</a-button>
        <a-button type="primary" :loading="loading" @click="onEditStage(true)">确认</a-button>
      </template>
      <a-row>
        <a-col :span="10">
          <p>默认名称</p>
          <a-input
            v-for="item in deName"
            :key="item.stageKey"
            :defaultValue="item.stageName"
            :disabled="true"
            style="margin-bottom:10px;"
          />
        </a-col>
        <a-col :span="4"></a-col>
        <a-col :span="10">
          <p>自定义名称</p>
          <a-input
            v-for="(item, idx) in edit"
            :key="idx"
            v-model="item.stageName"
            @change="() => isChange = true"
            style="margin-bottom:10px;"
          />
        </a-col>
      </a-row>
    </a-modal>
  </a-modal>
</template>

<script>
import BaseTab from './projectTab/BaseTab'
import MemberTab from './projectTab/MemberTab'
import EquipmentTab from './projectTab/EquipmentTab'
import StageTab from './projectTab/StageTab'
import ChangeLogTab from './projectTab/ChangeLogTab'
import OutSourceModal from './projectTab/OutSourceModal'
import TrialProdTab from './projectTab/TrialProdTab'
import moment from 'moment'
import { isEditStatus, isMsUser } from '@/utils/processDoc/auditStatus'
export default {
  components: {
    BaseTab,
    MemberTab,
    EquipmentTab,
    StageTab,
    ChangeLogTab,
    OutSourceModal,
    TrialProdTab
  },
  data () {
    return {
      title: '',
      id: 0,
      projectData: {},
      record: {},
      visible: false,
      formula: { value: '1' },
      actvieKey: undefined,
      showEdit: false,
      loading: false,
      isChange: false,
      deName: [],
      edit: [],
      tabsValue: {
        projectId: 0,
        attendance: 0,
        equipment: 0,
        material: 0,
        energy: 0,
        design: 0,
        inspection: 0
      },
      showTrial: false,
      modify: false,
      modifyStatus: false,
      deptTree: null,
      isEditChange: false
    }
  },
  created () {
    if (this.$auth('project:report:base:view')) {
      this.actvieKey = '1'
      return
    }
    if (this.$auth('project:report:member:search')) {
      this.actvieKey = '2'
      return
    }
    if (this.$auth('project:report:equipment:search')) {
      this.actvieKey = '3'
      return
    }
    if (this.$auth('project:report:stage:search')) {
      this.actvieKey = '4'
      return
    }
    if (this.$auth('project:report:outSource')) {
      this.actvieKey = '5'
    }
  },
  methods: {
    isMsUser,
    showModal (record, modify, deptTree) {
      this.isEditChange = false
      this.modify = modify
      this.$set(this, 'projectData', record)
      this.record = record
      this.id = record.id
      this.deptTree = deptTree
      this.title = `项目设置[${record.pname}] [${record.beginYear}年${record.rdTitle}]`
      this.visible = true
      this.showTrial = record.trialProd
      this.modifyStatus = ((record.status === null || isEditStatus(record.status)) && this.isMsUser()) || !this.isMsUser()
    },
    callback (activeKey) { // 切换tab调用
      this.actvieKey = activeKey
      // 切换到阶段tab 刷新数据
      if (activeKey === '4' && this.$refs.stageTab) {
        this.$refs.stageTab.refresheTable()
        this.$refs.stageTab.resetData()
        this.$refs.stageTab.refresheProjectData(this.projectData)
      }
    },
    refresh () {
      this.$refs.baseTab.refresh()
      this.$emit('ok')
    },
    closeModal () {
      this.actvieKey = '1'
      this.visible = false
      if (this.isEditChange) {
        this.$emit('ok')
      }
    },
    updateProjectData (data) {
      for (const key in data) {
        if (data[key] && moment.isMoment(data[key])) {
          this.projectData[key] = data[key].format('YYYY-MM-DD')
        } else {
          this.projectData[key] = data[key]
        }
      }
      // 来源调整项目周期
      if (data.rdTitle) {
        this.projectData['rdTitle'] = data.rdTitle
      }
      if (data.beginYear) {
        this.projectData['beginYear'] = data.beginYear
      }
      Object.assign(this.record, this.projectData)
      this.$emit('ok')
    },
    onShowEdit () {
      this.$http.get('stage/getStage').then(({ data }) => {
        this.deName = [...data.defaultStageList]
        this.edit = [...(data.companyStageList.length === 0 ? data.defaultStageList : data.companyStageList)]
      })
      this.showEdit = true
    },
    onEditStage (flag) {
      this.loading = true
      this.saveStage(flag)
    },
    saveStage (flag) {
      // flag: true 表示保存，false表示应用
      this.$http.post('stage/saveStage', { list: this.edit, projectId: flag ? 0 : this.projectData.id, changeCStage: this.isChange }).then(({ data }) => {
        if (data === true) {
          this.loading = false
          this.showEdit = false
          this.$message.success(flag ? '保存成功' : '应用成功')
          this.deName = []
          this.edit = []
        }
      }).finally(() => {
        flag || this.$emit('onChangeRefresh')
        this.$refs.stageTab.change2Refresh()
      })
    }
  }
}
</script>

<style scoped>
</style>
