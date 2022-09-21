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
      <a-tabs
        v-model="actvieKey"
        @change="callback"
      >
        <a-tab-pane
          v-if="$auth('project:report:base:view')"
          :tab="`基本信息`"
          key="1"
        >
          <parent-base-tab
            ref="baseTab"
            :projectData="projectData"
            :modify="modify"
            @updated="updataProjectData"
            :deptTree="deptTree"
            @refresh="refresh"
          ></parent-base-tab>
        </a-tab-pane>
        <a-tab-pane
          v-if="$auth('project:report:stage:search') && formula.value!=='30'"
          :tab="`阶段`"
          key="4"
        >
          <stage-tab
            ref="stageTab"
            :projectData="projectData"
          ></stage-tab>
        </a-tab-pane>
      </a-tabs>
    </div>
  </a-modal>
</template>

<script>
import BaseTab from './projectTab/BaseTab'
import ParentBaseTab from './projectTab/ParentBaseTab'
import MemberTab from './projectTab/MemberTab'
import EquipmentTab from './projectTab/EquipmentTab'
import StageTab from './projectTab/StageTab'
import OutSourceModal from './projectTab/OutSourceModal'
import TrialProdTab from './projectTab/TrialProdTab'

export default {
  components: {
    BaseTab,
    ParentBaseTab,
    MemberTab,
    EquipmentTab,
    StageTab,
    OutSourceModal,
    TrialProdTab
  },
  data () {
    return {
      title: '',
      id: 0,
      projectData: {},
      visible: false,
      formula: { value: '1' },
      data: 0,
      actvieKey: undefined,
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
      deptTree: null
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
    refresh () {
      this.$emit('ok')
    },
    showModal (record, modify, deptTree) {
      this.modify = modify
      this.$set(this, 'projectData', record)
      this.id = record.id
      this.deptTree = deptTree
      const rd = record.rdIndex < 10 ? 'RD0' + record.rdIndex : 'RD' + record.rdIndex
      this.title = `项目设置[${record.pname}] [${record.beginYear}年${rd}]`
      this.visible = true
      this.data = record.formula
      this.showTrial = record.trialProd
      this.$nextTick(() => {
        if (this.data === 30) {
          this.data = '30'
        }
        this.$set(this.formula, 'value', this.data)
      })
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
    closeModal () {
      this.actvieKey = '1'
      this.visible = false
    },
    getProjecSource (value) {
      this.$set(this.formula, 'value', value)
    },
    updataProjectData (projectData) {
      Object.assign(this.projectData, projectData)
    }
  }
}
</script>

<style scoped>
</style>
