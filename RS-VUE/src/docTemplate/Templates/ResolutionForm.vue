<!--
 * @Author:
 * @Date: 2020-09-11 15:58:47
 * @LastEditTime: 2022-09-14 11:48:18
 * @LastEditors: hm
 * @Description: 立项决议 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\ResolutionForm.vue
-->
<template>
  <a-card>
    <a-form>
      <a-row >
        <a-textarea v-model="content.resolution" placeholder="立项决议" :rows="15" />
      </a-row>
      <!-- 20210204 邓总要求去除 -->
      <!-- <a-divider/>
      <audit-footer :year="currentYear" :content.sync="content" :titleData="titleData"></audit-footer> -->
    </a-form>
  </a-card>
</template>
<script>
import AuditFooter from './modules/AuditFooter'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
export default {
  name: 'ResolutionForm',
  components: {
    AuditFooter
  },
  created () {
    this.content = getTemplateContent('resolutionForm')
    this.BPContent = getTemplateContent('resolutionForm')
  },
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
  data () {
    return {
      width: 960,
      url: '/reviewCommittee/getReviewsSelect',
      currentYear: this.$store.state.currentYear,
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
      computedFields: ['resolution']
    }
  },
  project (v) {
    this.content._pro = v.id
  },
  methods: {

  }
}
</script>

<style>
</style>
