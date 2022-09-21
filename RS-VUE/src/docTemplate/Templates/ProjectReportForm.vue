<!--
 * @Author:
 * @Date: 2020-12-01 09:42:56
 * @LastEditTime: 2022-03-12 11:09:16
 * @LastEditors: lzh
 * @Description:项目立项报告
 * @FilePath: \RS-VUE\src\docTemplate\Templates\ProjectReportForm.vue
-->
<template>
  <a-card>
    <a-form>
      <a-row >
        <a-col :span="12">
          <a-form-item
            label="项目名称"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            style="margin-bottom: 0px;"
          >
            <span>{{ project.pname }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            label="项目编号"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            style="margin-bottom: 0px;"
          >
            <span>{{ project.rdTitle }}</span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row >
        <a-col :span="12">
          <a-form-item
            label="项目责任单位/部门"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            style="margin-bottom: 0px;"
            required
          >
            <!-- <select-down
              :mountDom="domName"
              ref="rdDept"
              treeType="rdDept"
              :year="year"
              @select="(v,n)=>{content.dept = {id: v,name: n}}"
              placeholder="请选择部门"
              :value="content.dept.id"
            /> -->
            <span>{{ publicInfo.commonMap && publicInfo.commonMap.projectRdDept }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            label="负责人"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            required
          >{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : '' }}</a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            label="计划起止时间"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            style="margin-bottom: 0px;"
          >
            <span>{{ handleDate(project.beginDate) + ' 至 ' + handleDate(project.endDate) }}</span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-divider />
      <a-tabs
        v-model="activeKey"
        @change="callback"
      >
        <a-tab-pane
          key="1"
        >
          <template #tab>
            <i class="required-field"/>一、项目立项依据
          </template>
          <chapter-1-tab
            @ok="(list,key)=>content.img[key] = list"
            ref="chapter1Tab"
            :content="content"
            :project="project"
          />
        </a-tab-pane>
        <a-tab-pane
          key="2"
        >
          <template #tab>
            <i class="required-field"/>二、研究开发内容和目标
          </template>
          <chapter-2-tab
            @ok="(list,key)=>content.img[key] = list"
            ref="chapter2Tab"
            :content="content"
            :project="project"
          />
        </a-tab-pane>
        <a-tab-pane
          key="3"
        >
          <template #tab>
            <i class="required-field"/>三、研究开发方法及技术路线
          </template>
          <chapter-3-tab
            @ok="(list,key)=>content.img[key] = list"
            ref="chapter3Tab"
            :content="content"
            :project="project"
          />
        </a-tab-pane>
        <a-tab-pane
          key="4"
        >
          <template #tab>
            <i class="required-field"/>四、企业开发基础
          </template>
          <chapter-4-tab
            @ok="(list,key)=>content.img[key] = list"
            ref="chapter4Tab"
            :content="content"
            :project="project"
          />
        </a-tab-pane>
        <a-tab-pane
          key="5"
        >
          <template #tab>
            <i class="required-field"/>五、研发项目进度计划
          </template>
          <chapter-5-tab
            ref="chapter5Tab"
            :content="content"
            :project="project"
          />
        </a-tab-pane>
        <a-tab-pane
          key="6"
        >
          <template #tab>
            六、项目经费预算表
          </template>
          <chapter-6-tab
            ref="chapter6Tab"
            :content="content"
            :year="currentYear"
            :project="project"
          />
        </a-tab-pane>
        <a-tab-pane
          key="7"
        >
          <template #tab>
            <i class="required-field"/>七、主要成员名单
          </template>
          <chapter-7-tab
            ref="chapter7Tab"
            :content="content"
            :project="project"
            :docId="docId"
            :columns="memberColumns"
          />
        </a-tab-pane>
      </a-tabs>
      <a-divider />
      <audit-footer
        ref="audtiFooter"
        :projectId="projectId"
        :docId="docId"
        :year="year"
        :employeeMap="employeeMap"
      />
    </a-form>
  </a-card>
</template>
<script>
import AuditFooter from './modules/AuditFooter'
import Chapter1Tab from './projectReportTab/Chapter1Tab'
import Chapter2Tab from './projectReportTab/Chapter2Tab'
import Chapter3Tab from './projectReportTab/Chapter3Tab'
import Chapter4Tab from './projectReportTab/Chapter4Tab'
import Chapter5Tab from './projectReportTab/Chapter5Tab'
import Chapter6Tab from './projectReportTab/Chapter6Tab'
import Chapter7Tab from './projectReportTab/Chapter7Tab'
import yearMiXin from '@/utils/yearMixin'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import { domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'ProjectReportForm',
  mixins: [yearMiXin, dateMixin],
  components: {
    AuditFooter,
    Chapter1Tab,
    Chapter2Tab,
    Chapter3Tab,
    Chapter4Tab,
    Chapter5Tab,
    Chapter6Tab,
    Chapter7Tab
  },

  created () {
    this.content = getTemplateContent('projectReportForm')
    this.BPContent = getTemplateContent('projectReportForm')
    this.content.dept = undefined
    this.BPContent.dept = undefined
  },

  props: {
    projectId: {
      type: Number,
      required: true
    },
    docId: {
      type: Number,
      required: true
    },
    project: {
      type: Object,
      required: true
    },
    entory: { // 进入方式（从哪里进） true： 备查资料； false: 过程文档
      type: Boolean,
      required: true
    }
  },
  data () {
    return {
      activeKey: '1',
      domName,
      width: 1100,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      content: {},
      BPContent: {},
      // project: { beginDate: '', endDate: '' },
      currentYear: 2020,
      computedFields: ['background', 'meaning', 'researchContents', 'innovation', 'economicIndicators', 'developmentMethod', 'technicalRoute', 'institutionBuilding', 'projectStatus', 'scienceResult'],
      memberColumns: [
        { field: 'index', title: '序号', width: 60, type: 'seq' },
        { title: '姓名', field: 'ename' },
        { title: '部门', field: 'deptName' },
        { title: '项目角色', field: 'role' }
      ],
      requiredFields: ['background', 'meaning', 'researchContents', 'innovation', 'economicIndicators', 'developmentMethod', 'technicalRoute', 'institutionBuilding', 'projectStatus', 'scienceResult', 'chapter5List']
    }
  },
  mounted () {
    this.currentYear = this.currentYear
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
      this.content.dept = undefined
    }
  },
  methods: {
    handleTemplateEvent () {
      this.content.dept = undefined
      this.BPContent.dept = undefined
    },
    search () {
      this.currentYear = this.currentYear
    },
    callback (activeKey) {
      // 切换tab调用
      this.activeKey = activeKey
    },
    handleDate (date) {
      if (!date) return
      const dateAry = date.split('-')
      return `${dateAry[0]}年${dateAry[1]}月`
    }
  }
}
</script>

<style>
</style>
