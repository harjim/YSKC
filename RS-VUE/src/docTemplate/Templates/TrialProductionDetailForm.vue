<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-09-14 12:01:30
 * @LastEditors: hm
 * @Description:试验试制研发工时记录表 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\TrialProductionDetailForm.vue
-->
<template>
  <a-card id="wrap_box">
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="项目名称"
          style="margin-bottom: 0px;">
          <span>{{ project.pname }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="项目编号"
          style="margin-bottom: 0px;">
          <span>{{ project.rdTitle }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <!-- 2021-04-21 要删除 -->
    <!-- <a-row >
      <a-col :span="12">
        <a-form-item :label="titleData.environment" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px;">
          <a-input v-model="content.environment" placeholder="请输入试制环境"></a-input>
        </a-form-item>
      </a-col>
      <a-col :span="12">
      </a-col>
    </a-row> -->
    <a-divider />
    <a-table
      :dataSource="fileList"
      :pagination="false"
      :scroll="{x:1200, y:480}"
      bordered
      rowKey="id"
      size="small"
    >
      <a-table-column
        :width="120"
        align="center"
        data-index="month"
        title="月份"></a-table-column>
      <a-table-column :width="120" align="center" data-index="type" title="类型">
      </a-table-column>
      <a-table-column
        :width="120"
        align="center"
        data-index="status"
        title="状态">
      </a-table-column>
      <a-table-column
        :width="120"
        align="center"
        data-index="startDate"
        title="开始日期" />
      <a-table-column
        :width="120"
        align="center"
        data-index="startTime"
        title="开始时间">
        <template slot-scope="text">
          {{ text === null || text === '' || text === 'undefined' ? '-' : text
          }}
        </template>
      </a-table-column>
      <a-table-column
        :width="120"
        align="center"
        data-index="endDate"
        title="结束日期" />
      <a-table-column
        :width="120"
        align="center"
        data-index="endTime"
        title="结束时间">
        <template slot-scope="text">
          {{ text === null || text === '' || text === 'undefined' ? '-' : text
          }}
        </template>
      </a-table-column>
      <a-table-column
        :width="120"
        align="center"
        data-index="workHours"
        title="时长(h)" />
      <!-- <a-table-column title="记录人" :width="120" align="center"/>
      <a-table-column title="负责人" /> -->
    </a-table>
    <a-divider />
    <audit-footer
      ref="audtiFooter"
      :docId="docId"
      :employeeMap="employeeMap"
      :projectId="projectId"
      :year="year"
    />
  </a-card>
</template>

<script>
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import { Ellipsis, STable, TreeKeyMap } from '@/components'
import {
  getTemplateContent
} from '@/docTemplate/Templates/js/templateContentType'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'TrialProductionDetailForm',
  components: {
    AuditFooter,
    STable,
    Ellipsis,
    TreeKeyMap
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
    this.content = getTemplateContent('trialProductionDetailForm')
    this.BPContent = getTemplateContent('trialProductionDetailForm')
  },
  data () {
    return {
      width: 960,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      id: 0,
      content: {},
      BPContent: {},
      project: {},
      fileList: [],
      years: []
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
    },
    docId () {
      this.content = getTemplateContent('trialProductionDetailForm')
      this.BPContent = getTemplateContent('trialProductionDetailForm')
    }
  },
  methods: {
    moment,
    handleTemplateEvent () {
      this.queryFileList()
    },
    queryFileList () {
      this.$nextTick(() => {
        this.$http.get('/trialProd/getSummaryList', {
          params: {
            projectId: this.project.id,
            pDocFileId: this.docId
          }
        })
          .then(res => {
            this.fileList = res.data
            // const data = res.data
            // this.fileList = data.map((item, index) => {
            //   item['type'] = '试验试制'
            //   item['status'] = '开机'
            //   item['startDate'] = moment(item.trialDate).format('YYYY-MM-DD') // '开始日期'
            //   if (item.startTime == null) {
            //     item['startTime'] = '-'
            //   } else {
            //     item['startTime'] = moment(item.startTime).format('HH:mm:ss') // '开始时间'
            //   }
            //   item['endDate'] = moment(item.trialDate).format('YYYY-MM-DD') // '结束日期'
            //   if (item.endTime == null) {
            //     item['endTime'] = '-'
            //   } else {
            //     item['endTime'] = moment(item.endTime).format('HH:mm:ss') // '开始时间'
            //   }
            //   return item
            // })
            // this.content.fileList = this.fileList
            return res.data
          })
      })
    }
  }
}
</script>

<style lang="less" scoped>
#wrap_box /deep/ .ant-table {
  width: 100%;
}
</style>
