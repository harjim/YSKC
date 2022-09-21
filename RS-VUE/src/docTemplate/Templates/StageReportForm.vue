<!--
 * @Author:
 * @Date: 2020-12-12 09:27:35
 * @LastEditTime: 2022-09-14 11:55:29
 * @LastEditors: hm
 * @Description:阶段性验证验收评价 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\StageReportForm.vue
-->
<template>
  <a-card>
    <a-row>
      <a-col :span="12">
        <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
          <span>{{ project.pname }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="项目编号" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
          <span>{{ project.rdTitle }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="12">
        <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
          <span>{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : '' }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="项目阶段" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
          {{ fileInfo.stageType }}
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="12">
        <a-form-item label="评价日期" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;" required>
          <a-date-picker
            :getCalendarContainer="getCalendarContainer"
            :value="content.d ? moment(content.d,'YYYY-MM-DD') : content.d"
            format="YYYY-MM-DD"
            style="width:100%"
            :disabledDate="disabledDate"
            @change="onDateChange"
            @openChange="(status) => onOpenChange(status,'d')"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-divider/>
    <a-row>
      <a-col :span="24">
        <i class="required-field"/><a-button type="primary" @click="$refs.TRLModal.showModal(content.TRLs)">添加TRL</a-button>
      </a-col>
    </a-row>
    <a-row>
      <div v-for="item in content.TRLs" :key="item.key" style="padding-top: 20px;">
        <table class="t_content" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="3" valign="top">
              {{ item.TRL }}
              <span style="float:right;padding-right:10px;">
                <a-popconfirm title="是否确定删除?" @confirm="handleDel(item.key)">
                  <a-tooltip title="删除" :getPopupContainer="getCalendarContainer">
                    <a style="color:red;">
                      <a-icon type="close"/>
                    </a>
                  </a-tooltip>
                </a-popconfirm>
              </span>
            </td>
          </tr>
          <tr>
            <td width="70%" style="text-align:center">评价细则</td>
            <td width="10%" style="text-align:center">权重</td>
            <td width="20%"></td>
          </tr>
          <tr v-for="(r,i) in item.row" :key="i">
            <td>{{ r.title }}</td>
            <td style="text-align:center">{{ r.weight }}</td>
            <td style="padding:0;">
              <a-input-number
                :min="0"
                class="radiusInput"
                :max="10"
                v-model="r.v"
                @blur="weightChange"
                :precision="2"
              />
            </td>
          </tr>
        </table>
      </div>
      <div style="padding-top:20px;">
        <table class="t_content" cellspacing="0" cellpadding="0">
          <tr>
            <th width="30%"></th>
            <th width="50%"></th>
            <th width="20%"></th>
          </tr>
          <tr>
            <td colspan="2">以上合计</td>
            <td>{{ content.total }}</td>
          </tr>
          <tr>
            <td colspan="3">
              备注:
              <br />1.各项评分全部采用1-10分制
              <br />
              2.每个阶段总分>{{ content.passScore }}分方可进入下一研发阶段
            </td>
          </tr>
          <!-- 20210417要求删除 -->
          <!-- <tr>
            <td>评价人</td>
            <td colspan="2" style="padding:0;">
              <select-employee
                :mountDom="domName"
                :selected="content.members"
                :year="project.beginYear"
                :multiple="true"
                @select="members=>content.members = members"
              />
            </td>
          </tr>
          <tr>
            <td>项目负责人签字</td>
            <td colspan="2" style="padding:0;">
              <select-employee
                :mountDom="domName"
                :selected="content.pMaster"
                :year="project.beginYear"
                @select="pMaster=>content.pMaster = pMaster"
              />
            </td>
          </tr>
          <tr>
            <td>研发负责人评定</td>
            <td colspan="2" style="padding:0;">
              <select-employee
                :mountDom="domName"
                :selected="content.rdMaster"
                :year="project.beginYear"
                @select="rdMaster=>content.rdMaster = rdMaster"
              />
            </td>
          </tr> -->
        </table>
      </div>
    </a-row>
    <a-divider/>
    <audit-footer
      ref="audtiFooter"
      :projectId="projectId"
      :docId="docId"
      :year="year"
      :employeeMap="employeeMap"
    />
    <TRL-list-modal ref="TRLModal" @ok="handleTRL" />
  </a-card>
</template>
<script>
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import TRLListModal from './modules/TRLListModal'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'

const stageMap = {
  TRL1: {
    TRL: 'TRL1:明确该技术有关的基本原理，形成报告',
    row: [
      { title: '在学术刊物、会议论文、研究报吿，专利申请等资料中公布了可作为项目研究基础的基本原理', weight: '50%' },
      { title: '明确了基本原理的假设条件、应用范围', weight: '50%' }
    ]
  },
  TRL2: {
    TRL: 'TRL2：基于科学原理提出实际应用设想，形成技术方案',
    row: [
      { title: '明确技术的基本要素及构成特性', weight: '30%' },
      { title: '初步明确技术可实现的主要功能', weight: '50%' },
      { title: '明确产品预期应用环境', weight: '20%' }
    ]
  },
  TRL3: {
    TRL: 'TRL3：关健功能和特性在实验室条件下通过试验或仿真完成了原理性验证',
    row: [
      { title: '形成完善的实施方案，有明确的目标和指标要求', weight: '30%' },
      { title: '通过试验或仿真分析手段验证了关鍵功能的可行性', weight: '40%' },
      { title: '理论分析了系统集成方案的可行性', weight: '10%' },
      { title: '形成完善的项目开发计划', weight: '10%' },
      { title: '评估产品预期需要的制造条件和现有的制造能力', weight: '10%' }
    ]
  },
  TRL4: {
    TRL: 'TRL4：关键功能试样/模块在实验室通过了试验或仿真验证',
    row: [
      { title: '完成基础关键功能试样/模块/部件的开发', weight: '30%' },
      { title: '在实验室环境下通过各基础关键功能试样/模块/部件的功能、性能试验或仿真验证', weight: '30%' },
      { title: '试制了关键功能试样/模块/部件', weight: '10%' },
      { title: '对各关键功能试样/模块/部件进行系统集成', weight: '10%' },
      { title: '评估关键制造工艺', weight: '10%' },
      { title: '关键功能试样/模块/部件设计过程文档清晰', weight: '10%' }
    ]
  },
  TRL5: {
    TRL: 'TRL5：形成产品初样（部件级），在模拟使用环境中进行了试验或仿真验证',
    row: [
      { title: '完成各功能部件开发，形成产品初样', weight: '35%' },
      { title: '在模拟使用环境条件下完成产品初样的功能、性能试验或仿真验证', weight: '35%' },
      { title: '功能部件设计过程文档清晰', weight: '10%' },
      { title: '确定部件生产所需机械设备、测试工装夹具、人员技能等', weight: '10%' },
      { title: '确定部件关键制造工艺和部件集成所需的装配条件', weight: '10%' }]
  },
  TRL6: {
    TRL: 'TRL6：形成产品正样（系统级），通过高逼真度的模拟使用环境中进行验证',
    row: [
      { title: '形成产品正样，产品/样机技术状态接近最终状态', weight: '35%' },
      { title: '在高逼真度的模拟使用环境下通过系统产品/样机的功能、性能试验或仿真验证', weight: '35%' },
      { title: '设计工程试验验证及应用方案', weight: '5%' },
      { title: '系统设计过程文档清晰，完成需求检验', weight: '10%' },
      { title: '确定系统产品/样机的生产工艺及装配流程', weight: '10%' },
      { title: '确定生产成本及投资需求', weight: '5%' }
    ]
  },
  TRL7: {
    TRL: 'TRL7：形成整机产品工程样机、在真实使用环境下通过试验验证',
    row: [
      { title: '完成系统产品/样机的工程化开发', weight: '30%' },
      { title: '在实际使用环境下完成系统产品/样机的功能、性能试验验证', weight: '30%' },
      { title: '系统产品/样机开展应用测试', weight: '10%' },
      { title: '产品/样机生产装配流程、制造工艺和检测方法等通过验证', weight: '10%' },
      { title: '建立初步的产品/样机质量控制体系或标准', weight: '10%' },
      { title: '验证目标成本设计', weight: '10%' }
    ]
  },
  TRL8: {
    TRL: 'TRL8：实际产品设计定型,通过功能、性能测试：可进行产品小批量生产',
    row: [
      { title: '实际产品开发全部完成，技术状态固化', weight: '30%' },
      { title: '产品各项功能、性能指标在实际环境、条件下通过测试', weight: '30%' },
      { title: '完成产品使用维护说明书', weight: '10%' },
      { title: '所有的制造设备、工装、检测和分析系统通过小批量生产验证', weight: '15%' },
      { title: '关键材料或零部件具备穏定的供货渠道', weight: '15%' }
    ]
  },
  TRL9: {
    TRL: 'TRL9：系统产品批量生产，功能、性能、质量等特性在实际任务中得到充分验证',
    row: [
      { title: '产品的功能、性能在实际任务执行中得到-验证', weight: '30%' },
      { title: '所有文件归档', weight: '10%' },
      { title: '所有的制造设备、工装、检测和分析系统准备完毕', weight: '10%' },
      { title: '产品批量生产', weight: '20%' },
      { title: '产品合格率可控', weight: '20%' },
      { title: '建立售后服务计划', weight: '10%' }
    ]
  }
}
export default {
  name: 'StageReportForm',
  components: {
    AuditFooter,
    TRLListModal
  },
  mixins: [dateMixin],
  props: {
    projectId: {
      type: Number,
      required: true
    },
    docId: {
      type: Number,
      required: true
    }
  },
  created () {
    this.content = getTemplateContent('stageReportForm')
    this.BPContent = getTemplateContent('stageReportForm')
  },
  watch: {
    docId () {
      this.content = getTemplateContent('stageReportForm')
      this.BPContent = getTemplateContent('stageReportForm')
    }
  },
  data () {
    return {
      stageMap,
      domName,
      width: 960,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      content: {},
      BPContent: {},
      project: {},
      fileInfo: {},
      requiredFields: ['d', 'TRLs']
    }
  },
  methods: {
    moment,
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    handleTRL (keyObj) {
      const tempArr = []
      if (keyObj) {
        for (const key in keyObj) {
          const temp = this.$deepClone(stageMap[key])
          temp.key = key
          tempArr.push(temp)
        }
        if (this.content.TRLs) {
          this.content.TRLs.push(...tempArr)
        } else {
          this.content.TRLs = tempArr
        }
      }
      this.content.passScore = (Math.round((this.content.TRLs.length * 7.1) * 100) / 100).toFixed(1)
    },
    disabledDate (current) {
      return current && (current < moment(this.project.beginDate) || current >= moment(this.project.endDate).add(1, 'days'))
    },
    onDateChange (date, dateString) {
      if (!dateString) {
        if (!dateString) {
          this.content.d = date
          if (this.stage && this.stage.beginDate) {
            this.fileDate = moment(this.stage.beginDate).startOf('month')
          } else {
            this.fileDate = moment(this.BUfileDate).startOf('month')
          }
          this.hsaFileDataNull = true
          return
        }
      }
      this.hsaFileDataNull = false
      this.fileDate = moment(dateString).startOf('month')
      if (this.$refs.audtiFooter && this.$refs.audtiFooter.handleGetDocFooter) {
        this.$refs.audtiFooter.handleGetDocFooter(this.projectId, this.fileDate.year())
      }
      this.content.d = dateString
    },
    weightChange () {
      var tempTotal = 0
      this.content.TRLs.forEach(item => {
        item.row.forEach(r => {
          if (r.v) {
            tempTotal += Number(r.v) * Number(r.weight.substr(0, r.weight.length - 1)) / 100
          }
        })
      })
      this.content.total = (Math.round(tempTotal * 100) / 100).toFixed(1)
    },
    handleDel (key) {
      const tempArr = []
      this.content.TRLs.forEach(item => {
        if (item.key !== key) {
          tempArr.push(item)
        }
      })
      this.content.TRLs = tempArr
      this.weightChange()
      this.content.passScore = (Math.round((this.content.TRLs.length * 7.1) * 100) / 100).toFixed(2)
    },
    onOpenChange (status, field) {
      if (!this.content[field] && status) {
        this.content[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
      }
    },
    handleTemplateEvent () {
      this.content.stageName = this.fileInfo.stageType
    }
  }
}
</script>
<style>
.t_content {
  border: #ccc 1px solid;
  width: 100%;
}
.t_content td {
  border: #ccc 1px solid;
  text-align: left;
  font-size: 16px;
  color: #666;
  padding-left: 5px;
}
.radiusInput {
  width: 100%;
  border-radius: 0;
}
</style>
