<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-09-14 12:01:47
 * @LastEditors: hm
 * @Description:研发仪器设备参与项目汇总表 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\RdEquipmentSummaryForm.vue
-->
<template>
  <a-card>
    <a-row >
      <a-col :span="12">
        <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
          <span>{{ project.pname }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="项目编号" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
          <span>{{ project.rdTitle }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-row >
      <a-col :span="12">
        <a-form-item label="统计日期" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px" required>
          <a-date-picker
            :getCalendarContainer="getCalendarContainer"
            :value="content.statisticalTime ? moment(content.statisticalTime,'YYYY-MM-DD'): content.statisticalTime"
            format="YYYY-MM-DD"
            :disabledDate="disabledDate"
            @change="onDateChange"
            @openChange="(status) => onOpenChange(status,'statisticalTime')"
          />
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
          <span>{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : '' }}</span>
        </a-form-item>
      </a-col>
    </a-row>
    <a-divider/>
    <a-table
      bordered
      rowKey="num"
      :dataSource="fileList"
      :pagination="false"
      size="small"
      :scroll="{y:480}"
    >
      <a-table-column title="序号" data-index="num" :width="60"></a-table-column>
      <a-table-column title="资产编号" data-index="ecode" :width="120"></a-table-column>
      <a-table-column title="设备名称" data-index="ename" :width="120" />
      <a-table-column title="型号" data-index="emodal" :width="120" />
      <a-table-column title="项目中的作用" data-index="effect" :width="120" />
      <a-table-column title="项目全年内使用工作量(小时)" data-index="yearHour" :width="120" />
    </a-table>
    <a-divider/>
    <audit-footer
      ref="audtiFooter"
      :projectId="projectId"
      :docId="docId"
      :year="year"
      :employeeMap="employeeMap"
    />
  </a-card>
</template>

<script>
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import { STable, Ellipsis, SelectDown, TreeKeyMap } from '@/components'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'RdEquipmentSummaryForm',
  components: {
    AuditFooter,
    STable,
    Ellipsis,
    SelectDown,
    TreeKeyMap
  },
  mixins: [dateMixin],
  created () {
    this.content = getTemplateContent('rdEquipmentSummaryForm')
    this.BPContent = getTemplateContent('rdEquipmentSummaryForm')
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
      width: 1000,
      domName,
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
      years: [],
      requiredFields: ['statisticalTime']
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
      this.initYears()
    },
    docId () {
      this.content = getTemplateContent('rdEquipmentSummaryForm')
      this.BPContent = getTemplateContent('rdEquipmentSummaryForm')
    }
  },
  methods: {
    moment,
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    initYears () {
      if (this.project == null) {
        return []
      }
      var endDate = this.project.endDate
      var beginDate = this.project.beginDate
      if (typeof endDate === 'undefined' || typeof beginDate === 'undefined') {
        return []
      }
      var beginArray = beginDate.split('-').map(Number)
      var endArray = endDate.split('-').map(Number)
      var returnArry = []
      var begin = parseInt(beginArray[0])
      var end = parseInt(endArray[0])

      for (let y = begin; y <= end; y++) {
        returnArry.push(y)
      }
      this.years = returnArry
    },
    handleTemplateEvent () {
      this.content.equipmentYear = this.years[0]
      this.queryFileList()
    },
    onSelectChange (value, option) {
      this.content._pro = this.project.id
      this.queryFileList()
    },
    queryFileList () {
      this.$nextTick(() => {
        this.$http.get('/projectRdEquipment/queryListByYear', { params: { projectId: this.project.id, pDocFileId: this.docId } })
          .then(res => {
            const data = res.data
            this.fileList = data.map((item, index) => {
              item['num'] = index + 1
              return item
            })
            // this.content.lists = this.fileList
            return res.data
          })
      })
    },
    onDateChange (date, dateString) {
      if (!dateString) {
        this.content.statisticalTime = date
        if (this.stage && this.stage.beginDate) {
          this.fileDate = moment(this.stage.beginDate).startOf('month')
        } else {
          this.fileDate = moment(this.BUfileDate).startOf('month')
        }
        this.hsaFileDataNull = true
        return
      }
      this.hsaFileDataNull = false
      this.content._pro = this.project.id
      this.fileDate = moment(dateString).startOf('month')
      if (this.$refs.audtiFooter && this.$refs.audtiFooter.handleGetDocFooter) {
        this.$refs.audtiFooter.handleGetDocFooter(this.projectId, this.fileDate.year())
      }
      this.content.statisticalTime = dateString
    },
    disabledDate (current) {
      return current && (current < moment(this.project.beginDate) || current >= moment(this.project.endDate).add(1, 'days'))
    },
    onOpenChange (status, field) {
      if (!this.content[field] && status) {
        this.content[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
      }
    }
  }
}
</script>

<style scoped>
</style>
