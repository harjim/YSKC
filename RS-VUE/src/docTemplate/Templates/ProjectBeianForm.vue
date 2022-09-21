<!--
 * @Author: ldx
 * @Date: 2021-08-03 10:14:43
 * @LastEditTime: 2022-09-14 11:50:36
 * @LastEditors: hm
 * @Description:研发项目备案表 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\ProjectBeianForm.vue
-->
<template>
  <div class="container">
    <div class="title">
      <h2>2021计划内研发项目备案表</h2>
    </div>
    <div class="info">
      <div class="left">项目单位：{{ userInfo.companyName }}
        <!-- <a-input v-model="content.projectUnits" placeholder="请输入项目单位" style="width: 200px;"/> -->
      </div>
      <div class="right">项目编号： 创新备
        <a-input v-model="content.projectNo" placeholder="请输入项目编号" style="width:150px;"/> 号
      </div>
    </div>
    <div class="table_wrap">
      <table style="border-collapse: collapse; width: 100%;" border="1">
        <tbody>
          <tr>
            <td style="width: 150px;;" align="center" valign="middle">项目名称</td>
            <td style="padding:0.5em;" align="center" valign="middle">{{ project.pname }}</td>
          </tr>
          <tr>
            <td style="width: 150px;;" align="center" valign="middle">项目主要研究内容、技术路线、创新点、建设方案</td>
            <td style="padding:0.5em;" align="left">
              <div class="td_title">主要研究内容：</div>
              <div class="doc-section">
                <a-form-item :extra="onComputeTextarea('primaryContent',200)" :wrapperCol="{sm: {span: 24}}">
                  <a-textarea
                    :rows="4"
                    v-model="content.primaryContent"
                    palceholder="请输入主要研究内容"/>
                </a-form-item>
              </div>
              <div class="td_title">技术路线：</div>
              <div class="doc-section">
                <a-form-item :extra="onComputeTextarea('techLine',200)" :wrapperCol="{sm: {span: 24}}">
                  <a-textarea
                    :rows="4"
                    v-model="content.techLine"
                    palceholder="请输入技术路线"/>
                </a-form-item>
              </div>
              <div class="td_title">创新点：</div>
              <div class="doc-section">
                <a-form-item :extra="onComputeTextarea('innovation',200)" :wrapperCol="{sm: {span: 24}}">
                  <a-textarea
                    :rows="4"
                    v-model="content.innovation"
                    palceholder="请输入创新点"/>
                </a-form-item>
              </div>
              <div class="td_title">建设方案：</div>
              <div class="doc-section">
                <a-form-item :extra="onComputeTextarea('advice',200)" :wrapperCol="{sm: {span: 24}}">
                  <a-textarea
                    :rows="4"
                    v-model="content.advice"
                    palceholder="请输入建设方案"/>
                </a-form-item>
              </div>
            </td>
          </tr>
          <tr>
            <td style="width: 150px;;" align="center" valign="middle">项目经费投入</td>
            <td style="padding:0.5em;" align="left" valign="middle">
              总投入： <a-input v-model="content.putInto" placeholder="请输入总投入" style="width: 200px; margin: 0 8px;"/>万元，年度计划投入
              <a-input v-model="content.planPutInto" placeholder="请输入计划投入" style="width: 200px; margin: 0 8px;"/>万元。
              <div style="text-align:right; padding:3em;">
                子（分）公司财务部门：（签字盖章）
              </div>
            </td>
          </tr>
          <tr>
            <td style="width: 150px;;" align="center" valign="middle">
              项目进度安排
            </td>
            <td style="padding:0.5em;" align="left">
              <div class="doc-section">
                <a-form-item :extra="onComputeTextarea('progressArrange',200)" :wrapperCol="{sm: {span: 24}}">
                  <a-textarea
                    :rows="4"
                    v-model="content.progressArrange"
                    palceholder="请输入建设方案"/>
                </a-form-item>
              </div>
            </td>
          </tr>
          <tr>
            <td style="width: 150px;;" align="center" valign="middle">预期目标</td>
            <td style="padding:0.5em;" align="left" >
              <div class="doc-section">
                <a-form-item :extra="onComputeTextarea('target',200)" :wrapperCol="{sm: {span: 24}}">
                  <a-textarea
                    :rows="4"
                    v-model="content.target"
                    palceholder="请输入建设方案"/>
                </a-form-item>
              </div>
            </td>
          </tr>
          <tr>
            <td style="width: 150px;;" align="center" valign="middle">
              申请单位意见
            </td>
            <td style="padding:0.5em;" align="left">
              <div class="doc-section">
                <a-form-item :extra="onComputeTextarea('unitOpinion',200)" :wrapperCol="{sm: {span: 24}}">
                  <a-textarea
                    :rows="3"
                    v-model="content.unitOpinion"
                    palceholder="请输入建设方案"/>
                </a-form-item>
              </div>
              <div style="display: flex; justify-content: space-between;padding:1em; ">
                <span>负责人：（签字）</span>
                <span>（公章）</span></div>
              <div style="text-align:right; padding:0.5em;">
                <span style="margin-left: 4em;">年</span><span style="margin-left: 3em;">月</span><span style="margin-left: 3em;">日</span>
              </div>
            </td>
          </tr>
          <tr>
            <td style="width: 150px;;" align="center" valign="middle">研发中心意见
            </td>
            <td style="padding:0.5em;" align="left">
              <div class="doc-section">
                <a-form-item :extra="onComputeTextarea('devCenterOpinion',200)" :wrapperCol="{sm: {span: 24}}">
                  <a-textarea
                    :rows="3"
                    v-model="content.devCenterOpinion"
                    palceholder="请输入建设方案"/>
                </a-form-item>
              </div>
              <div style="text-align:right; padding:0.5em;">（公章）</div>
              <div style="text-align:right; padding:0.5em;">
                <span style="margin-left: 4em;">年</span><span style="margin-left: 3em;">月</span><span style="margin-left: 3em;">日</span>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- <a-divider/>
    <audit-footer
      ref="audtiFooter"
      :projectId="projectId"
      :docId="docId"
      :year="year"
      :employeeMap="employeeMap"
    /> -->
  </div>
</template>
<script>
import AuditFooter from './modules/AuditFooter'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import dateMixin from '../Templates/js/dateMixin'
import { mapGetters } from 'vuex'
export default {
  name: 'ProjectBeianForm',
  components: {
    AuditFooter
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
    },
    docFile: {
      type: Object,
      required: true
    },
    project: {
      type: Object,
      default: () => {}
    }
  },
  created () {
    this.content = getTemplateContent('projectBeianForm')
    this.BPContent = getTemplateContent('projectBeianForm')
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  data () {
    return {
      content: {},
      BPContent: {},
      // project: {},
      fileInfo: {}
    }
  },
  methods: {
    // 计算文本域的个数
    onComputeTextarea (fieldName, limitNumber = 200) {
      const content = this.content[fieldName]
      const contentLenght = content ? content.length : 0
      // if (contentLenght > limitNumber) {
      //   this.content[fieldName] = content.substring(0, limitNumber)
      // }
      return `内容：${contentLenght}`
    }

  }
}
</script>
<style lang="less" scoped>
  .doc-section {
     font-size :14px;
      line-height: 2;
  }
  .container {
    display: felx;
    flex-direction: column;
    .title {
      text-align: center;
    }
    .info {
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      margin-bottom: 12px;
      font-size: 16px;
      .left {
        flex: 1;
        text-align:center;
      }
      .right {
        flex: 1;
        text-align:center;
        .underline {
          display: inline-block;
          width: 3em;
        }
      }
    }
    .table_wrap {
      .td_title{
        font-weight: 650;
      }
    }
  }
</style>
