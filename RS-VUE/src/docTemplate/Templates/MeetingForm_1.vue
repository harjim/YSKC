<!--
 * @Author:
 * @Date: 2020-09-11 15:58:47
 * @LastEditTime: 2022-09-17 09:04:53
 * @LastEditors: hm
 * @Description: 会议纪要 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\MeetingForm_1.vue
-->
<template>
  <a-card id="container">
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form style="width: 100%">
        <a-row >
          <a-col :span="12">
            <a-form-item label="项目名称" :labelCol="{xs: { span: 24 },sm: { span: 4}}" :wrapperCol="{xs: { span: 24 },sm: { span: 14 }}" style="margin-bottom: 8px;">
              <span>{{ project.pname }}</span>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="负责人" :labelCol="{xs: { span: 24 },sm: { span: 4}}" :wrapperCol="{xs: { span: 24 },sm: { span: 14 }}" style="margin-bottom: 8px;">
              <span>{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : '' }}</span>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              label="会议类别"
              :labelCol="{xs: { span: 24 },sm: { span: 2 }}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 22 }}"
              style="margin-bottom: 8px;"
              required
            >
              <a-select
                mode="multiple"
                :getPopupContainer="getCalendarContainer"
                style="width: 86%"
                placeholder="请选择会议类别"
                :default-value="content.checkedList"
                v-model="content.checkedList"
                @change="onChange"
              >
                <a-select-option v-for="i in plainOptions" :key="i">
                  {{ i }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              label="会议主题"
              :labelCol="{xs: { span: 24 },sm: { span: 2 }}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 19 }}"
              style="margin-bottom: 8px;"
              required
            >
              <a-input v-model="content.theme" placeholder="请输入会议主题" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row >
          <a-col :span="12">
            <a-form-item
              label="主持部门"
              :labelCol="{xs: { span: 24 },sm: { span: 4}}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 14 }}"
              style="margin-bottom: 8px;"
              required
            >
              <select-down
                :mountDom="domName"
                :year="year"
                treeType="rdDept"
                placeholder="请选择部门"
                :value="content.rdDept? content.rdDept.id: undefined"
                @select="(v,n)=>{content.rdDept = {id:v,name: n}}"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="主持人"
              :labelCol="{xs: { span: 24 },sm: { span: 4}}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 14 }}"
              style="margin-bottom: 8px;"
              required
            >
              <select-employee
                :mountDom="domName"
                :selected="content.host"
                :year="year"
                @select="host=>content.host = host"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row >
          <a-col :span="12">
            <a-form-item
              label="会议时间"
              :labelCol="{xs: { span: 24 },sm: { span: 4}}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 14 }}"
              style="margin-bottom: 8px;"
              required
            >
              <!-- :disabledDate="disabledDate" -->
              <a-date-picker
                :getCalendarContainer="getCalendarContainer"
                :value="content.hostTime ? moment(content.hostTime,'YYYY-MM-DD') : content.hostTime"
                format="YYYY-MM-DD"
                style="width:100%"
                @change="onDateChange"
                @openChange="(status) => onOpenChange(status,'hostTime')"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              label="会议地点"
              :labelCol="{xs: { span: 24 },sm: { span: 4}}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 14 }}"
              style="margin-bottom: 8px;"
              required
            >
              <a-input v-model="content.place" placeholder="请输入会议地点" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row >
          <a-col>
            <a-form-item
              label="参会人员"
              :labelCol="{xs: { span: 24 },sm: { span: 2 }}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 22 }}"
              style="margin-bottom: 8px;"
              required
            >
              <select-employee
                :mountDom="domName"
                :selected="content.members"
                :year="year"
                :multiple="true"
                style="width:600px;"
                @select="selectMember"
              />
              <a-tooltip placement="top" :getPopupContainer="getCalendarContainer">
                <template slot="title">
                  <span v-if="!this.hasAudit">默认引入该项目技术人员与研究人员</span>
                  <span v-else>默认引入评审委员会成员</span>
                </template>
                <a-button type="primary" @click="importBath" style="margin-left:6px;" :loading="loading">
                  引入参会人员
                </a-button>
              </a-tooltip>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row >
          <a-col>
            <a-form-item
              :labelCol="{xs: { span: 24 },sm: { span: 2 }}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 19 }}"
              style="margin-bottom: 8px;"
              required
            >
              <template slot="label">
                <ys-tooltip
                  :getPopupContainer="getCalendarContainer"
                  style="display: inline-block;"
                  text="会议内容"
                  title=" 以记录形式，分点描述会议的内容及形成的结论；问题和结论一一对应。例如：
                    1、建议人XX针对项目的情况进行陈述，目的是让所有项目组成员了解项目的主要思路；
                    2、建议人说明研究阶段的主要工作，和需要的研发资源，包括人、仪器设备、物料等；
                    3、研发总监或项目组成员，提出XXX细节方面如何考虑？XX（建议人）回复会从如下几个方面进行处理：........
                    4、针对项目组主要成员及分工，进行统一部署，资源不足的及时提出。
                    ..............."
                />
              </template>
              <a-textarea
                v-model="content.mattersInvolved"
                placeholder="以记录形式，分点描述会议的内容及形成的结论；问题和结论一一对应。例如：
1、建议人XX针对项目的情况进行陈述，目的是让所有项目组成员了解项目的主要思路；
2、建议人说明研究阶段的主要工作，和需要的研发资源，包括人、仪器设备、物料等；
3、研发总监或项目组成员，提出XXX细节方面如何考虑？XX（建议人）回复会从如下几个方面进行处理：........
4、针对项目组主要成员及分工，进行统一部署，资源不足的及时提出。
..............."
                :rows="10" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              label="阅知形式"
              :labelCol="{xs: { span: 24 },sm: { span: 4 }}"
              :wrapperCol="{xs: { span: 24 },sm: { span: 14 }}"
              style="margin-bottom: 8px;"
            >
              <a-checkbox-group :options="readOptions" v-model="content.readList" @change="readChange" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-divider/>
        <audit-footer
          ref="audtiFooter"
          :projectId="projectId"
          :docId="docId"
          :year="year"
          :employeeMap="employeeMap"
        />
      </a-form>
    </a-spin>
  </a-card>
</template>
<script>
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import YsTooltip from '@/components/YsTooltip/YsTooltip'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import SelectEmployee from '@/components/SelectEmployee'
import SelectDown from '@/components/SelectDown'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import dateMixin from './js/dateMixin'
// import { cloneDeep } from 'lodash'
const plainOptions = ['研发工作例会', '技术评审会', '研发质量专题会', '研究阶段专题会', '设计开发专题会', '设计验证专题会', '实验验证专题会', '试验证专题会', '试制专题会', '项目验收专题会', '项目收尾专题会', '其它临时性会议']
const readOptions = ['书面', '网络', '公布']
export default {
  name: 'MeetingForm',
  components: {
    SelectEmployee,
    SelectDown,
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
    },
    docFile: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      spinning: false,
      domName,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      plainOptions,
      readOptions,
      checkedList: [],
      readList: [],
      width: 960,
      content: {},
      BPContent: {},
      // theme: '', // 会议主题
      project: {},
      fileInfo: {},
      // computedFields: ['mattersInvolved'],
      hasAudit: false,
      loading: false,
      requiredFields: ['checkedList', 'theme', 'rdDept.id', 'host.enumber', 'hostTime', 'place', 'members', 'mattersInvolved']
    }
  },
  created () {
    this.content = getTemplateContent('meetingForm')
    this.BPContent = getTemplateContent('meetingForm')
  },
  watch: {
    checkedList (list) {
      this.setHasAudit(list)
    },
    readList (list) {
      const self = this
      self.content.readOptionAry = []
      this.readOptions.map(item => {
        if (list.indexOf(item) > -1) {
          self.content.readOptionAry.push(true)
        } else {
          self.content.readOptionAry.push(false)
        }
      })
    },
    docId () {
      // this.content = getTemplateContent('attendanceAggForm')
      // this.BPContent = getTemplateContent('attendanceAggForm')
    }
  },
  methods: {
    moment,
    selectMember (members) {
      this.content.members = members
      if (!this.hasAudit) {
        this.saveMeetMember(members)
      }
    },
    saveMeetMember (members) {
      this.spinning = true
      this.$http.post('/docFile/setMeetMember', { members: JSON.stringify(members), projectId: this.project.id }).then(res => {
        if (!res.success || !res.data) {
          this.$message.error(res.errorMessage || '保存参会人员失败')
        }
        this.spinning = false
      })
    },
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    handleTemplateEvent () {
      this.setTheme()
    },
    onDateChange (date, dateString) {
      if (!dateString) {
        this.content.hostTime = date
        if (this.stage && this.stage.beginDate) {
          this.fileDate = moment(this.stage.beginDate).startOf('month')
        } else {
          this.fileDate = moment(this.BUfileDate).startOf('month')
        }
        this.hsaFileDataNull = true
        return
      }
      this.hsaFileDataNull = false
      this.fileDate = moment(dateString).startOf('month')
      if (this.$refs.audtiFooter && this.$refs.audtiFooter.handleGetDocFooter) {
        this.$refs.audtiFooter.handleGetDocFooter(this.projectId, this.fileDate.year())
      }
      this.content.hostTime = dateString
    },
    onChange (checkedList) {
      this.checkedList = checkedList
    },
    readChange (readList) {
      this.readList = readList
    },
    // disabledDate (current) {
    //   return current && (current < moment(this.project.beginDate) || current >= moment(this.project.endDate).add(1, 'days'))
    // },
    onOpenChange (status, field) {
      if (!this.content[field] && status) {
        this.content[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
      }
    },
    setTheme () {
      // this.theme = this.fileInfo.docName
      this.hasAudit = false
      if (this.checkedList.indexOf('技术评审会') >= 0) {
        this.hasAudit = true
      }
      if (!this.hasAudit && (!this.content.members || !this.content.members.length)) {
        this.$http.get('/docFile/getMeetMember', { params: { projectId: this.project.id } }).then(res => {
          if (res.success) {
            if (res.data) {
              this.content.members = JSON.parse(res.data)
            }
          } else {
            this.$message.error(res.errorMessage || '获取参会人员失败')
          }
        })
      }

      this.setHasAudit(this.content.checkedList)
    },
    // updateFlieName () {
    //   this.$http.post('/projectDocFile/editDocFileName', { id: this.fileInfo.docId, docFileName: this.content.theme }).then(res => {
    //     if (res.success && res.data) {}
    //   })
    // },
    importBath () {
      this.loading = true
      const params = { projectId: this.project.id, docDate: this.fileDate, pDocFileId: this.docId }
      this.$http.get(this.hasAudit ? '/projectDocFile/getAuditor' : '/projectDocFile/getMeetingEmployee', { params })
        .then(res => {
          if (res.success && res.data) {
            if (res.data.length) {
              this.content.members = res.data
              if (!this.hasAudit) {
                this.saveMeetMember(res.data)
              }
            } else {
              this.$message.warn(this.hasAudit ? '请先添加评审人员' : '该项目尚未添加研究人员和技术人员')
            }
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '获取数据异常')
          }
          this.loading = false
        })
    },
    setHasAudit (list) {
      const self = this
      if (!list) {
        self.hasAudit = false
        return
      }
      self.content.checkedOptionStr = ''
      self.content.checkedOptionAry = []
      this.plainOptions.map(item => {
        if (list.indexOf(item) > -1) {
          self.content.checkedOptionAry.push(true)
        } else {
          self.content.checkedOptionAry.push(false)
        }
      })
      if (list.indexOf('技术评审会') >= 0) {
        self.hasAudit = true
      } else {
        self.hasAudit = false
      }
    }
  }
}
</script>

<style lang="less" scoped>
#container /deep/ .ant-tooltip-inner {
  width: 650px;
}
</style>
