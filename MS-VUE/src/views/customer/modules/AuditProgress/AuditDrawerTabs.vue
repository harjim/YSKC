<!--
 * @Author: ldx
 * @Date: 2021-01-21 15:25:33
 * @LastEditTime: 2022-08-08 14:40:32
 * @LastEditors: zdf
 * @Description: 审核抽屉界面
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\AuditDrawerTabs.vue
-->
<template>
  <section>
    <a-drawer
      id="fullBox"
      :visible="isVisible"
      placement="right"
      :maskClosable="true"
      :headerStyle="{paddingBottom:'0',marginBottom: '0'}"
      :bodyStyle="{padding: '0px 7px' ,height: 'calc(100% - 55px)'}"
      @close="onClose"
      :destroyOnClose="true"
      :width="width"
    >
      <template #title>
        <div style="display:flex;flex-direction: row;">
          <span>{{ title }}</span>
          <div style="padding-left:10px;flex:1;text-align:right;padding-right:36px;padding-bottom:10px;">
            <a-button
              :title="isFullscreen ? '还原' : '全屏'"
              size="small"
              icon="fullscreen"
              @click="isFullscreen=!isFullscreen"
            ></a-button>
          </div>
        </div>
      </template>
      <div style="height: 100%; background-color: white; padding:5px 5px;">
        <a-spin
          :spinning="spinning"
          class="spin"
        >
          <a-tabs
            v-if="modes.length"
            id="tab"
            :activeKey="activeKey"
            @change="callback"
            size="small"
            :tabBarStyle="{ marginBottom: '10px' }"
          >
            <a-tab-pane
              v-for="(mode) in this.modes"
              :key="mode.moduleId * 1"
            >
              <template v-slot:tab>
                <template v-if="mode.hasPermission">
                  <a-badge
                    v-if="showBadgeCount(mode.moduleId)"
                    :count="mode.DotCount"
                    showZero
                    :dot="mode.dot"
                    :offset="[8,0]"
                    :title="mode.DotCount + ''"
                  >
                    {{ mode.modeName }}
                  </a-badge>
                  <a-badge
                    v-else
                    :numberStyle="{ width:'8px', height: '8px' }"
                    :offset="[0,0]"
                    dot
                    :title="mode.modeName"
                  >
                    {{ mode.modeName }}
                  </a-badge>
                </template>
                <template v-else>
                  {{ mode.modeName }}
                </template>
              </template>
              <div style="height: 100%">
                <component
                  :record="record"
                  :mode="mode"
                  :active="activeKey"
                  :full="isFullscreen"
                  @updataStatus="updataStatus"
                  @show="showScore"
                  @updateCurrentProject="updateCurrentProject"
                  :ref="componentNames[mode.moduleId * 1]"
                  :is="componentNames[mode.moduleId * 1]"
                >
                </component>
              </div>
            </a-tab-pane>
          </a-tabs>
        </a-spin>
      </div>
      <score-modal
        :visible="isShowScore"
        :title="activeKey === 9 ? '创新体系' : `${currentProject.rdTitle}-${currentProject.pname}`"
        :params="{
          companyId: record.companyId,
          type: activeKey,
          year: record.year,
          rsProjectId: currentProject.id,
          beginDate: currentProject.beginDate,
          endDate: currentProject.endDate,
        }"
        @close="closeModal"
      />
    </a-drawer>
  </section>
</template>
<script>
import ystable from '@/components/Table/ystable'
import { cloneDeep } from 'lodash'
import AuditPreviewModal from './AuditPreviewModal'
import AuditModal from './AuditModal.vue'
import frameworkTab from './modules/FrameworkTab'
import employeeTab from './modules/EmployeeTab'
import equipmentTab from './modules/EquipmentTab'
import projectTab from './modules/ProjectTab'
import reportTab from './modules/ReportTab'
import docTab from './modules/DocTab'
import patentTab from './modules/PatentTab'
import noveltySearchTab from './modules/NoveltySearchTab'
import innovateTab from './modules/InnovateTab'
import backupDataTab from './modules/BackupDataTab'
import screenfull from 'screenfull'
import ProposalTab from './modules/ProposalTab'
import AchievementTab from './modules/AchievementTab'
import ScoreModal
  from '@/views/customer/modules/AuditProgress/modules/ScoreModal'

const componentNames = {
  1: 'frameworkTab', // 研发组织架构
  2: 'employeeTab', // 研发花名册
  3: 'equipmentTab', // 研发设备
  4: 'projectTab', // 项目名称
  5: 'reportTab', // 立项报告
  6: 'docTab', // 过程文件
  7: 'patentTab', // 专利管理
  8: 'noveltySearchTab', // 查新报告
  9: 'innovateTab', // 创新体系
  10: 'backupDataTab', // 留存备查
  11: 'ProposalTab', // 提案管理
  12: 'AchievementTab' // 成果管理
}

export default {
  name: 'AuditDrawerTabs',
  components: {
    ScoreModal,
    ystable,
    AuditPreviewModal,
    AuditModal,
    frameworkTab,
    employeeTab,
    equipmentTab,
    projectTab,
    reportTab,
    docTab,
    patentTab,
    noveltySearchTab,
    innovateTab,
    screenfull,
    backupDataTab,
    ProposalTab,
    AchievementTab
  },
  mounted () {
    this.isEnabledFullscreen = screenfull.isEnabled
    const self = this
    window.onresize = () => {
      var isFull = screenfull.isFullscreen
      if (isFull === undefined) {
        isFull = false
      }
      if (!isFull) {
        self.$nextTick(() => {
          self.width = 1000
          self.isFullscreen = false
        })
      }
    }
  },
  props: {
    search: {
      type: Function,
      required: true
    }
  },
  watch: {
    isFullscreen (n) {
      this.full()
    }
  },
  data () {
    return {
      width: 1000,
      isVisible: false,
      title: '',
      activeKey: 1,
      isAuditIconIn: false, // 是否点击审核图标进入
      spinning: true,
      modes: [],
      componentNames,
      record: {},
      isFullscreen: false,
      isEnabledFullscreen: false,
      isShowScore: false,
      currentProject: {}
    }
  },
  methods: {
    updateCurrentProject (obj) {
      this.currentProject = obj
    },
    show (record, activeKey, isAuditIconIn) {
      this.title = `${record.companyName}【${record.year}】`
      if (isAuditIconIn) {
        this.isAuditIconIn = isAuditIconIn
      }
      this.activeKey = activeKey
      this.record = record
      this.projectId = record.id
      this.modes = []
      this.isVisible = true
      this.spinning = false
      this.getAuditInfo()
    },
    onClose () {
      this.search()
      this.afterClose()
      this.isFullscreen = false
      this.isShowScore = false
      this.width = 1000
    },
    afterClose () {
      this.isVisible = false
      this.title = undefined
      this.spinning = false
      this.modes = []
      this.record = {}
    },
    callback (activeKey) {
      this.closeModal()
      this.activeKey = activeKey
      if (this.$refs[componentNames[activeKey]]) {
        const comp = this.$refs[componentNames[activeKey]]
        let obj
        if ((Array.isArray(comp) && comp.length)) {
          obj = comp[0]
        } else {
          obj = comp
        }
        if (obj.callBack) {
          obj.callBack()
        }
        if (obj.onChangeSwitch) {
          obj.onChangeSwitch(false)
        }
      }
      this.getProjectAudit()
    },
    /**
     * @description: 处理模块进度显示内容-核心内容
     * @param {*}
     * @return {*}
     */
    getAuditInfo () {
      const projectId = this.record.id
      this.$http.get('/projectProgress/getAuditInfo', { params: { projectId } }).then((res) => {
        if (res.data && res.success) {
          res.data.sort(function (a, b) {
            return a.seq - b.seq
          })
          this.modes = res.data
          this.getInitModeValue()
          this.spinning = false
        } else {
          this.$message.error(res.errorMessage)
          this.isVisible = false
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.isVisible = false
        this.spinning = false
      })
    },
    /**
     * @description: 初始化模块的值
     * @param {*}
     * @return {*}
     */
    getInitModeValue () {
      this.spinning = true
      this.$http.get('/projectProgress/getCountData', {
        params: {
          year: this.record.year,
          customerId: this.record.customerId,
          companyId: this.record.companyId
        }
      }).then((res) => {
        if (res.data && res.success) {
          const obj = res.data
          const modeValue = {
            1: obj.rdDeptLevel,
            2: obj.rdEmployeeCount,
            3: obj.rdEquipmentCount,
            4: obj.rdCount,
            5: obj.reportNum,
            6: obj.docFileCount,
            7: obj.patentNum
          }
          for (const row of this.modes) {
            if (modeValue[row.moduleId]) {
              row.value = modeValue[row.moduleId]
            }
          }
          this.modes = this.modes.filter((item) => { // 过滤没有权限的
            return this.isAuths(item.moduleId * 1)
          })
          this.modes = cloneDeep(this.modes)
          if (this.modes.length && this.isAuditIconIn) {
            this.activeKey = this.modes[0].moduleId * 1
          }
          this.getProjectAudit()
        } else {
          this.$message.error(res.errorMessage)
          this.isVisible = false
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.isVisible = false
        this.spinning = false
      })
    },
    getProjectAudit () {
      this.spinning = true
      const obj = {
        projectId: this.record.id,
        companyId: this.record.companyId,
        year: this.record.year
      }
      this.$http.get('/projectProgress/getProjectAudit', { params: obj }).then((res) => {
        if (res.data && res.success) {
          const data = res.data
          this.modes.forEach((item, index) => {
            if (this.showBadgeCount(item.moduleId)) {
              item['dot'] = false
              item['DotCount'] = data[item.moduleId]
              item['hasPermission'] = !!data[item.moduleId]
            } else {
              item['dot'] = true
              item['hasPermission'] = data[item.moduleId]
            }
          })
        } else {
          this.$message.error(res.errorMessage)
          this.isVisible = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.isVisible = false
      }).finally((res) => {
        this.spinning = false
      })
    },
    /**
     * @description: 显示徽标属性
     * @param {*} moduleId
     * @return {*}
     */
    showBadgeCount (moduleId) {
      const moduleIds = [4, 5, 6, 8, 7, 10, 11, 12]
      return moduleIds.includes(Number(moduleId))
    },
    /**
     * @description: 判断有没有权限
     * @param {*} moduleId
     * @return {*}
     */
    isAuths (moduleId) {
      let reulst = !!this.$auth('customer:projectProgress:view')
      switch (moduleId) {
        case 1:
          reulst = !!this.$auth('project:projectProgress:rdOrg')
          break
        case 4:
          reulst = !!this.$auth('project:projectProgress:rdDetail')
          break
        case 5:
          reulst = !!this.$auth('customer:projectProgress:preview')
          break
        case 6:
          reulst = !!this.$auth('customer:projectProgress:preview')
          break
        default:
          break
      }
      return reulst
    },
    // // 全屏
    // full () {
    //   if (!this.isEnabledFullscreen) {
    //     this.$message.warning('您现在使用的浏览器不支持全屏显示！')
    //     return
    //   }
    //   const element = document.getElementById('fullBox')
    //   screenfull.toggle(element)
    //   this.isFullscreen = !this.isFullscreen
    // },
    // 全屏
    full () {
      if (!this.isEnabledFullscreen) {
        this.$message.warning('您现在使用的浏览器不支持全屏显示！')
        return
      }

      const element = document.getElementById('fullBox')
      if ((this.isFullscreen && !screenfull.isFullscreen) || (!this.isFullscreen && screenfull.isFullscreen)) {
        screenfull.toggle(element)
      }
      if (!this.isFullscreen) {
        this.width = 1000
      } else {
        this.width = '100%'
      }
    },
    updataStatus () {
      this.getInitModeValue()
    },
    showScore (status) {
      this.isShowScore = status
    },
    closeModal () {
      if (this.$refs[componentNames[this.activeKey]]) {
        const comp = this.$refs[componentNames[this.activeKey]]
        let obj
        if ((Array.isArray(comp) && comp.length)) {
          obj = comp[0]
        } else {
          obj = comp
        }
        if (obj.onChangeSwitch) {
          obj.onChangeSwitch(false)
        }
      }
    }
  }
}
</script>
<style
  lang="less"
  scoped
>
@import './css/AuditDrawerTab.less';

.body-wrap {
  display: flex;
  flex-direction: row;
}

.status-wrap {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 5px;
}

.stautsColor {
  display: inline-block;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  margin: 0 3px;
}
</style>
