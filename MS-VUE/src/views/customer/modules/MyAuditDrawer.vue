<!--
 * @Author: ldx
 * @Date: 2021-04-20 10:10:04
 * @LastEditTime: 2022-05-21 11:01:51
 * @LastEditors: zdf
 * @Description: 我的审核抽屉
 * @FilePath: \MS-VUE\src\views\customer\modules\MyAuditDrawer.vue
-->
<template>
  <div>
    <a-drawer
      :visible="isVisible"
      placement="right"
      :maskClosable="true"
      :bodyStyle="{padding: '12px' ,height: 'calc(100% - 55px)'}"
      @close="onClose"
      :width="componentName === 'docAudit' || componentName === 'noveltySearchAudit' ? 1000 : 800"
    >
      <template #title>
        {{ title }}
      </template>
      <div id="tab">
        <component
          v-if="isLoadComponent"
          :record="record"
          :mode="mode"
          :ref="componentName"
          :is="componentName"
        >
        </component>
      </div>
    </a-drawer>
  </div>
</template>

<script>
import FrameworkTab from './AuditProgress/modules/FrameworkTab.vue'
import employeeTab from './AuditProgress/modules/EmployeeTab'
import equipmentTab from './AuditProgress/modules/EquipmentTab'
import projectTab from './AuditProgress/modules/ProjectTab'
import patentTab from './AuditProgress/modules/PatentTab'
import patent from './AuditProgress/modules/Patent'
import noveltySearchAudit from './AuditProgress/modules/NoveltySearchAudit'
import innovateTab from './AuditProgress/modules/InnovateTab'
import docAudit from './AuditProgress/modules/DocAudit'
import ProjectDetail from './AuditProgress/modules/ProjectDetail'
import Proposal from './AuditProgress/modules/Proposal'
import Achievement from './AuditProgress/modules/Achievement'
import RdFee from './AuditProgress/modules/RdFee'
import ServiceApply from './AuditProgress/modules/ServiceApply'
import ServiceRecord from './AuditProgress/modules/ServiceRecord'

const componentNames = {
  1: 'frameworkTab',
  2: 'employeeTab',
  3: 'equipmentTab',
  4: 'ProjectDetail',
  7: 'patent',
  8: 'noveltySearchTab',
  9: 'innovateTab',
  11: 'Proposal',
  12: 'Achievement',
  13: 'RdFee',
  14: 'ServiceApply',
  15: 'ServiceRecord'
}
export default {
  name: 'MyAuditDrawer',
  components: {
    FrameworkTab,
    employeeTab,
    equipmentTab,
    projectTab,
    patentTab,
    noveltySearchAudit,
    innovateTab,
    docAudit,
    patent,
    ProjectDetail,
    Proposal,
    Achievement,
    RdFee,
    ServiceApply,
    ServiceRecord
  },
  data () {
    return {
      isVisible: false,
      title: '',
      auditRecord: undefined,
      componentNames,
      componentName: undefined,
      mode: { moduleId: undefined },
      isLoadComponent: false,
      record: {
        id: undefined,
        year: undefined,
        companyId: undefined
      }
    }
  },
  methods: {
    onShow (title, auditRecord) {
      this.isVisible = true
      this.title = title
      this.auditRecord = auditRecord
      this.mode['moduleId'] = auditRecord.moduleId
      this.record['companyId'] = auditRecord.companyId
      if (auditRecord.type === 2) {
        this.componentName = 'docAudit'
        if (auditRecord.moduleId === 8) {
          this.componentName = 'noveltySearchAudit'
        }
      } else {
        this.componentName = this.componentNames[auditRecord.moduleId]
      }
      this.getInstanceInfo()
    },
    getInstanceInfo () {
      const params = { id: this.auditRecord.id, type: this.auditRecord.type }
      this.$http.get('/myAudit/getInstanceInfo', { params }).then((res) => {
        if (res.data && res.success) {
          Object.assign(this.record, res.data)
          this.record['id'] = res.data.projectId
          this.record['rsProjectId'] = res.data.rsProjectId
          this.record['patentPlanId'] = res.data.patentPlanId
          this.record['dataId'] = res.data.dataId
          this.record['rdType'] = res.data.rdType
          this.record['moduleId'] = res.data.moduleId
          this.isLoadComponent = true
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally((res) => {
      })
    },
    onClose () {
      this.isVisible = false
      this.record = {
        id: undefined,
        year: undefined,
        companyId: undefined
      }
      this.mode = { moduleId: undefined }
      this.isLoadComponent = false
      this.$emit('refresh')
    }
  }
}
</script>
<style
  lang="less"
  scoped
>
@import './AuditProgress/css/AuditDrawerTab.less';
</style>
