<!--
 * @Author:
 * @Date: 2020-09-11 15:58:47
 * @LastEditTime: 2022-09-14 11:53:02
 * @LastEditors: hm
 * @Description: 研发项目任务书 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\TaskForm.vue
-->
<template>
  <a-card>
    <a-form>
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
      <a-row>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="开发周期"
            style="margin-bottom: 0px;"
          >
            {{ handleDate(project.beginDate) }} 至 {{ handleDate(project.endDate)
            }}
            <!-- 20210205 邓总要求取项目起止日期 -->
            <!-- <a-month-picker
              :getCalendarContainer="getCalendarContainer"
              :disabledDate="c=>disabledRangeDate(c,true)"
              style="width:45%"
              :value="content.beginDate ? moment(content.beginDate) : undefined"
              format="YYYY-MM"
              @change="(d,dStr)=>content.beginDate = dStr"
              @openChange="(status) => onOpenChange(status,'beginDate')"
            />~
            <a-month-picker
              :getCalendarContainer="getCalendarContainer"
              :disabledDate="c=>disabledRangeDate(c,false)"
              style="width:45%"
              :value="content.endDate ? moment(content.endDate) : undefined"
              format="YYYY-MM"
              @change="(d,dStr)=>content.endDate = dStr"
              @openChange="(status) => onOpenChange(status,'endDate')"
            /> -->
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="项目负责人"
            style="margin-bottom: 0px;">
            <span>{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : ''
            }}</span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="24">
          <a-form-item
            :labelCol="{ xs: { span: 24 },sm: { span: 3 }}"
            :wrapperCol="{ xs: { span: 24 },sm: { span: 21 }}"
            label="项目组成员"
            required
            style="margin-bottom: 0 px;"
          >
            <show-member-str ref="sms" :docId="docId" :projectId="project.id" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-tabs defaultActiveKey="1">
          <a-tab-pane key="1">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="项目简介"
                title="简单的描述清楚项目的情况，例如（如下供参考）：
                  基于我司XX产品/技术/工艺存在的XXX问题，提出XXXX的项目，项目主要是XXXXX，通过项目的研发可以达到XXXXX"
              />
            </template>
            <a-textarea
              v-model="content.description"
              :rows="10"
              placeholder="简单的描述清楚项目的情况，例如（如下供参考）：基于我司XX产品/技术/工艺存在的XXX问题，提出XXXX的项目，项目主要是XXXXX，通过项目的研发可以达到XXXXX"
            />
          </a-tab-pane>
          <a-tab-pane key="2">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="开发要求"
                title="1、 确定最优的开发方案，确保项目的成功率；
                  2、 制定合理的开发计划，按目标执行研发项目；
                  3、 提前统筹研发资源，推动研发进度；
                  4、 及时保留保存开发试验试产试制过程中的开发资料和数据；
                  5、 合理使用研发费用，跟财务做好研发费用核算；
                  6、 能够申请知识产权技术细节的请及时申报。"
              />
            </template>
            <a-textarea
              v-model="content.development "
              :rows="10"
              placeholder="1、 确定最优的开发方案，确保项目的成功率；
2、 制定合理的开发计划，按目标执行研发项目；
3、 提前统筹研发资源，推动研发进度；
4、 及时保留保存开发试验试产试制过程中的开发资料和数据；
5、 合理使用研发费用，跟财务做好研发费用核算；
6、 能够申请知识产权技术细节的请及时申报。"
            />
          </a-tab-pane>
          <a-tab-pane key="3">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="项目进度计划要求"
              />
            </template>
            <a-button type="primary" @click="handleAdd">添加</a-button>
            <a-button
              style="margin-left: 8px;"
              type="primary"
              @click="handleAddStage">引入项目阶段
            </a-button>
            <a-table
              :dataSource="content.allData"
              :pagination="false"
              :scroll="{y:480}"
              bordered
              rowKey="index"
              size="small"
              style="padding-top:10px;"
            >
              <a-table-column
                :width="60"
                data-index="index"
                title="序号"></a-table-column>
              <a-table-column
                :width="350"
                align="center"
                data-index="projectStage"
                title="项目阶段">
                <template slot-scope="text,record">
                  <a-input
                    :value="text"
                    @change="(e)=>onCellChange(e.target.value,record,'projectStage')"
                  />
                </template>
              </a-table-column>
              <a-table-column
                :width="300"
                align="center"
                data-index="completionTimeNode"
                title="要求完成时间节点"
              >
                <template slot-scope="text,record">
                  <a-date-picker
                    :disabledDate="disabledDate"
                    :getCalendarContainer="getCalendarContainer"
                    :value="text ? moment(text,'YYYY-MM-DD') : text"
                    format="YYYY-MM-DD"
                    style="width:100%"
                    @change="(d,dStr)=>onCellChange(dStr,record,'completionTimeNode')"
                  />
                </template>
              </a-table-column>
              <a-table-column
                :width="100"
                align="center"
                data-index="key"
                title="操作">
                <template slot-scope="text,record">
                  <a @click="handleDel(record.index)">移除</a>
                </template>
              </a-table-column>
            </a-table>
          </a-tab-pane>
          <a-tab-pane key="4">
            <template slot="tab">
              <ys-tooltip
                :getPopupContainer="getCalendarContainer"
                required
                text="研发负责人建议"
                title="如下几种可参考：
                  第一种：请严格按照项目计划推进项目进度，各相关人员相互配合，避免资源浪费，确保项目顺利进行！
                  第二种：请项目负责人合理安排开发计划，提前统筹研发资源，确保研发项目保质保量按时完成，希望研发成果转化的同时也能取得良好的经济效益。
                  第三种：请项目负责人带头做好研发技术风险分析及预防，统筹各方资源，确保项目顺利进行！"
              />
            </template>
            <a-textarea
              v-model="content.propose"
              :rows="10"
              placeholder=" 如下几种可参考：
  第一种：请严格按照项目计划推进项目进度，各相关人员相互配合，避免资源浪费，确保项目顺利进行！
  第二种：请项目负责人合理安排开发计划，提前统筹研发资源，确保研发项目保质保量按时完成，希望研发成果转化的同时也能取得良好的经济效益。
  第三种：请项目负责人带头做好研发技术风险分析及预防，统筹各方资源，确保项目顺利进行！" />
          </a-tab-pane>
        </a-tabs>
      </a-row>
      <a-divider></a-divider>
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
import AuditFooter from './modules/AuditFooter'
import ShowMemberStr from './modules/ShowMemberStr'
import {
  getTemplateContent
} from '@/docTemplate/Templates/js/templateContentType'
import {
  domName,
  popupContainer
} from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'
import YsTooltip from '@/components/YsTooltip/YsTooltip'

export default {
  name: 'TaskForm',
  components: {
    ShowMemberStr,
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
    this.content = getTemplateContent('taskForm')
    this.BPContent = getTemplateContent('taskForm')
  },
  data () {
    return {
      width: 960,
      domName,
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
      computedFields: ['description', 'development', 'propose', 'allData'],
      requiredFields: ['description', 'development', 'allData']
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content.masterName = v.ename
    },
    docId () {
      this.content = getTemplateContent('taskForm')
      this.BPContent = getTemplateContent('taskForm')
    }
  },
  methods: {
    moment,
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    disabledRangeDate (current, begin) {
      var d
      if (begin) {
        d = this.content.endDate ? this.content.endDate : this.project.endDate
        return current && (current < moment(this.project.beginDate) || current >= moment(d).add(1, 'days'))
      } else {
        d = this.content.beginDate ? this.content.beginDate : this.project.beginDate
        return current && (current < moment(d) || current >= moment(this.project.endDate).add(1, 'days'))
      }
    },
    disabledDate (current) {
      return current && (current < moment(this.project.beginDate) || current >= moment(this.project.endDate).add(1, 'days'))
    },
    onChange (dates, dateStr) {
      this.content.beginDate = moment(dates[0]).format('YYYY-MM-DD')
      this.content.endDate = moment(dates[1]).format('YYYY-MM-DD')
      this.content.period = dateStr[0] + ' - ' + dateStr[1]
    },
    onCellChange (val, record, fld) {
      if (!val) {
        val = undefined
      }
      this.$set(record, fld, val)
    },
    handleAdd () {
      const newData = {
        index: this.content.allData ? (this.content.allData.length) + 1 : 1,
        projectStage: '',
        completionTimeNode: undefined
      }
      if (!this.content.allData) {
        this.content.allData = [newData]
      } else {
        this.content.allData.push(newData)
      }
    },
    handleDel (rowIndex) {
      const tempRows = []
      var num = 0
      for (var i = 0; i < this.content.allData.length; i++) {
        const row = this.content.allData[i]
        if (row.index !== rowIndex) {
          row.index = ++num
          tempRows.push(row)
        }
      }
      this.content.allData = tempRows
    },
    handleTemplateEvent () {
      this.content.memberStr = this.$refs.sms.memberStr
    },
    onOpenChange (status, field) {
      if (!this.content[field] && status) {
        this.content[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
      }
    },
    handleDate (date) {
      if (!date) return
      const dateAry = date.split('-')
      return `${dateAry[0]}年${dateAry[1]}月`
    },
    handleAddStage () {
      this.$http.get('/projectDocFile/getReportStage', { params: { projectId: this.projectId } })
        .then(res => {
          if (res.data) {
            if (this.content.allData == null) {
              this.content.allData = []
            }
            let num = this.content.allData.length
            this.content.allData.push(...res.data.map(item => {
              return {
                index: ++num,
                projectStage: item.stageType,
                completionTimeNode: item.endDate
              }
            }))
          }
        })
    }
  }
}
</script>

<style>
</style>
