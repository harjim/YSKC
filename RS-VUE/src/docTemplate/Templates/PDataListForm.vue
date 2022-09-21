<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-09-14 12:12:25
 * @LastEditors: hm
 * @Description:研发项目资料清单 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\PDataListForm.vue
-->
<template>
  <a-card>
    <a-form>
      <a-row >
        <a-col :span="12">
          <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span>{{ project.pname }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="项目编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span>{{ project.rdTitle }}</span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row >
        <a-table
          bordered
          rowKey="id"
          :dataSource="fileList"
          :pagination="false"
          size="small"
          :scroll="{y:480}"
        >
          <a-table-column title="序号" data-index="num" :width="60">
            <template slot-scope="text,record,index">{{ index + 1 }}</template>
          </a-table-column>
          <a-table-column title="阶段" data-index="stageType" :width="100">
            <template slot-scope="text">
              <span>{{ text }}</span>
            </template>
          </a-table-column>
          <a-table-column title="文件名称" data-index="docFileName" :width="180"/>
          <a-table-column title="文件编号" data-index="documentNumber" />
        </a-table>
      </a-row>
      <!-- <a-divider/>
      <audit-footer
        ref="audtiFooter"
        :projectId="projectId"
        :docId="docId"
        :year="year"
        :employeeMap="employeeMap"
      /> -->
    </a-form>
  </a-card>
</template>
<script>
// import AuditFooter from './modules/AuditFooter'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'PDataListForm',
  components: {
    // AuditFooter
  },
  mixins: [dateMixin],
  created () {
    this.content = getTemplateContent('pDataListForm')
    this.BPContent = getTemplateContent('pDataListForm')
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
      width: 600,
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
      fileList: [],
      fileInfo: {}
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
    },
    docId () {
      this.content = getTemplateContent('pDataListForm')
      this.BPContent = getTemplateContent('pDataListForm')
    }
  },
  methods: {
    handleTemplateEvent () {
      this.load()
    },
    load () {
      this.content.currentYear = this.fileInfo.currentYear
      this.queryFileList()
    },
    queryFileList () {
      this.$nextTick(() => {
        this.$http.get('/projectDocFile/queryFileList', { params: { projectId: this.project.id, year: this.fileInfo.currentYear } })
          .then(res => {
            this.fileList = res.data
            return res.data
          })
      })
    }
  }
}
</script>

<style>
</style>
