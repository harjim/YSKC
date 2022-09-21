<!--
 * @Author:
 * @Date: 2020-08-03 18:57:11
 * @LastEditTime: 2022-09-14 12:02:17
 * @LastEditors: hm
 * @Description: RD人员设备折旧分配说明 —— 人员设备折旧分配说明
 * @FilePath: \RS-VUE\src\docTemplate\Templates\PDistributionReportForm_1.vue
-->
<template>
  <a-card>
    <a-form>
      <a-row >
        <a-textarea v-model="content.distributionReport" placeholder="RD人员设备折旧分配说明" :rows="20" />
      </a-row>
      <template v-if="isShowAuditFooter" >
        <a-divider/>
        <audit-footer
          ref="audtiFooter"
          :projectId="projectId"
          :docId="docId"
          :year="year"
          :employeeMap="employeeMap"
        />
      </template>
    </a-form>
  </a-card>
</template>
<script>
import AuditFooter from './modules/AuditFooter'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'PDistributionReportForm',
  components: {
    AuditFooter
  },
  mixins: [dateMixin],
  created () {
    this.content = getTemplateContent('pDistributionReportForm')
    this.BPContent = getTemplateContent('pDistributionReportForm')
  },
  props: {
    isShowAuditFooter: {
      type: Boolean,
      default: true
    },
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
      content: {},
      BPContent: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      project: {},
      fileInfo: {},
      computedFields: ['distributionReport']
    }
  },
  methods: {
  }
}
</script>

<style>
</style>
