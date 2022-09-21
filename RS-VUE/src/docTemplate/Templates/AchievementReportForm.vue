<!--
 * @Author:
 * @Date: 2020-09-23 10:01:15
 * @LastEditTime: 2022-09-14 12:00:10
 * @LastEditors: hm
 * @Description: 科技成果转化报告 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\AchievementReportForm.vue
-->
<template>
  <a-card>
    <a-form>
      <a-row>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="技术成果名称"
            required
            style="margin-bottom: 0px">
            <a-input
              v-model="content.resultName"
              placeholder="技术成果名称"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="来源"
            required
            style="margin-bottom: 0px">
            <a-input
              v-model="content.source"
              placeholder="来源"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="转化成果"
            required
            style="margin-bottom: 0px"
          >
            <a-checkbox-group
              v-model="content.checkedList"
              :options="projectOptions"
              @change="onChange"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="负责人"
            style="margin-bottom: 0px">
            <span>{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : ''
            }}</span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="转化形式"
            required
            style="margin-bottom: 0px">
            <a-input
              v-model="content.transformForm"
              placeholder="转化形式"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="转化年度"
            required
            style="margin-bottom: 0px">
            <a-input
              v-model="content.transformYear"
              placeholder="转化年度"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-divider />

      <a-row>
        <a-tabs defaultActiveKey="1">
          <a-tab-pane key="1">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="技术核心"
                title="参考《项目计划书》中的关键技术，并对各关键技术进行简单介绍、说明。"
              />
            </template>
            <a-textarea
              v-model="content.technologyCore"
              :rows="10"
              placeholder=" 参考《项目计划书》中的关键技术，并对各关键技术进行简单介绍、说明。" />
          </a-tab-pane>
          <a-tab-pane key="2">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="技术创新点"
                title="参考《项目计划书》中的创新点，结合项目实施过程中，遇到问题对接的解决方案，总结出项目在“科技成果转化”阶段的创新点，展现创新能力。"
              />
            </template>
            <a-textarea
              v-model="content.innovation"
              :rows="10"
              placeholder="参考《项目计划书》中的创新点，结合项目实施过程中，遇到问题对接的解决方案，总结出项目在“科技成果转化”阶段的创新点，展现创新能力。" />
          </a-tab-pane>
          <a-tab-pane key="3">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="转化成果"
                title="常见的写法：
                  本项目研发成功后形成一项新产品/新工艺/新技术：xxx，其主要特征是：XXXX。预计会产生发明/实用新型/外观专利1件（或软件著作权、论文、标准、著作权或版权、新药证书、植物新品种、集成电路布图设计专有权）"
              />
            </template>
            <a-textarea
              v-model="content.result"
              :rows="10"
              placeholder="常见的写法：
本项目研发成功后形成一项新产品/新工艺/新技术：xxx，其主要特征是：XXXX。预计会产生发明/实用新型/外观专利1件（或软件著作权、论文、标准、著作权或版权、新药证书、植物新品种、集成电路布图设计专有权）" />
          </a-tab-pane>
          <a-tab-pane key="4">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="技术指标"
                title="直接引用项目最后一次试制的检测数据即可。"
              />
            </template>
            <a-textarea
              v-model="content.technicalIndex"
              :rows="10"
              placeholder="直接引用项目最后一次试制的检测数据即可。" />
          </a-tab-pane>
          <a-tab-pane key="5">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="经济效益分析"
                title="结合项目实施完工情况及客户试用样品反馈情况，分析、预计项目年度收益。"
              />
            </template>
            <a-textarea
              v-model="content.benefit"
              :rows="10"
              placeholder="结合项目实施完工情况及客户试用样品反馈情况，分析、预计项目年度收益。" />
          </a-tab-pane>
          <a-tab-pane key="6">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="成果转化证明"
              />
            </template>
            <a-row :gutter="[24,8]">
              <a-col :span="24">
                <a-checkbox-group
                  v-model="content.checkedProveList"
                  :options="proveOptions"
                  @change="onProveChange"
                />
              </a-col>
            </a-row>
            <!-- <a-row :gutter="[24,8]">
              <a-col :span="8">
                <a-upload
                  :fileList="content.fileList"
                  :multiple="true"
                  @change="UploadHandleChange"
                  :beforeUpload="beforeUpload"
                  @preview="downloadfile"
                >
                  <a-button>
                    <a-icon type="upload" />上传附件
                  </a-button>
                </a-upload>
              </a-col>
            </a-row> -->
          </a-tab-pane>
        </a-tabs>
      </a-row>
      <a-divider />
      <audit-footer
        ref="audtiFooter"
        :docId="docId"
        :employeeMap="employeeMap"
        :projectId="projectId"
        :year="year"
      />
    </a-form>
  </a-card>
</template>
<script>
import moment from 'moment'
import {
  getTemplateContent
} from '@/docTemplate/Templates/js/templateContentType'
import {
  domName,
  popupContainer
} from '@/docTemplate/Templates/js/screenFullMountDom'
import AuditFooter from './modules/AuditFooter'
import dateMixin from '../Templates/js/dateMixin'
import YsTooltip from '@/components/YsTooltip/YsTooltip'

const projectOptions = ['新产品', '新技术', '新工艺']
const proveOptions = ['查新报告', '专利', '检验报告', '产品图片', '销售合同及发票', '其它自定义转化成果']
export default {
  name: 'AchievementReportForm',
  components: {
    AuditFooter,
    YsTooltip
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
    this.content = getTemplateContent('achievementReportForm')
    this.BPContent = getTemplateContent('achievementReportForm')
  },
  data () {
    return {
      domName,
      projectOptions,
      proveOptions,
      checkedList: [],
      checkedProveList: [],
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
      computedFields: ['technologyCore', 'innovation', 'result', 'technicalIndex', 'benefit'],
      requiredFields: ['resultName', 'source', 'checkedList', 'transformForm', 'transformYear', 'technologyCore', 'innovation', 'result', 'technicalIndex', 'benefit', 'checkedProveList']
    }
  },
  watch: {
    checkedList (list) {
      const self = this
      self.content.checkedOptionArr = []
      this.projectOptions.map(item => {
        if (list.indexOf(item) > -1) {
          self.content.checkedOptionArr.push(true)
        } else {
          self.content.checkedOptionArr.push(false)
        }
      })
    },
    checkedProveList (list) {
      const self = this
      self.content.proveOptionArr = []
      this.proveOptions.map(item => {
        if (list.indexOf(item) > -1) {
          self.content.proveOptionArr.push(true)
        } else {
          self.content.proveOptionArr.push(false)
        }
      })
    }
  },
  methods: {
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    // 上传改变事件监听
    // UploadHandleChange (file) {
    //   if (file.file.status === 'removed') {
    //     this.content.fileList = file.fileList
    //   }
    // },
    // 上传前事件监听
    // beforeUpload (file, key) {
    //   const param = new FormData()
    //   param.append('file', file)
    //   param.append('dir', '/docFileData/')
    //   const config = {
    //     // 添加请求头
    //     headers: { 'Content-Type': 'multipart/form-data' }
    //   }
    //   this.$http.post('/projectDocFileData/upload', param, config).then(res => {
    //     if (res.success) {
    //       this.content.fileList.push({
    //         uid: res.data.fileName,
    //         name: res.data.fileName,
    //         status: 'done',
    //         url: res.data.filePath
    //       })
    //     }
    //   }).catch(res => {
    //   })
    //   return false
    // },
    // 下载
    // downloadfile (file) {
    //   this.$exportData('/projectDocFileData/downloadFile', { fileName: file.name, path: file.url }, file.name, this.$message)
    // },
    handleDel (record) {
      const tempList = []
      this.content.list.forEach(item => {
        if (item.enumber !== record.enumber) {
          tempList.push(item)
        }
      })
      this.content.list = tempList
    },
    disabledDate (current) {
      return current && (current < moment(this.project.beginDate) || current > moment(this.project.endDate))
    },
    handleApproveChange (value) {
      if (this.employeeMap[value]) {
        this.content.approveName = this.employeeMap[value].ename
      }
    },
    onReceiveTimeChange (dateString, record) {
      record.receiveTime = moment(dateString).format('YYYY-MM-DD')
    },
    moment,
    onDateChange (date, dateString) {
      if (!dateString) {
        dateString = undefined
      }
      this.content.reviewTime = dateString
    },
    onPlainOptionsChange (e) {

    },
    onChange (checkedList) {
      this.checkedList = checkedList
    },
    onProveChange (checkedProveList) {
      this.checkedProveList = checkedProveList
    },
    handleOk (items) {
      this.$getTree('rdDept', false, this.project.beginYear).then(res => {
        const keyMap = res.keyMap
        const arr = []
        items.forEach(t => {
          arr.push({
            enumber: t.enumber,
            ename: t.ename,
            position: t.position,
            rdDeptName: keyMap[t.rdDeptId]
          })
        })
        if (this.content.list) {
          this.content.list.push(...arr)
        } else {
          this.content.list = arr
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.icon-interval {
  margin-left: 8px;
}
</style>
