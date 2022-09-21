<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-09-14 12:13:15
 * @LastEditors: hm
 * @Description: 项目验收报告 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\ProjectCheckReportForm.vue
-->
<template>
  <a-card>
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="项目名称"
          style="margin-bottom: 0px"
        >
          <span>{{ project.pname }}</span>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="项目编号"
          style="margin-bottom: 0px"
        >{{ project.rdTitle }}
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="责任部门"
          style="margin-bottom: 0px;"
        >
          {{ publicInfo.commonMap.projectRdDept }}
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="项目负责人"
          style="margin-bottom: 0px;"
        >
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
          label="计划起止日期"
          style="margin-bottom:0px;"
        >
          {{ publicInfo.commonMap.beginAndEnd }}
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="实际起止日期"
          required
          style="margin-bottom:0px;"
        >
          <a-date-picker
            :disabledDate="actualEndDisabledDate"
            :getCalendarContainer="getCalendarContainer"
            :value="content.actualStartTime ? moment(content.actualStartTime,'YYYY-MM-DD'): content.actualStartTime"
            style="width: 120px"
            @change="onActualStatrTimeChange"
          />
          至
          <a-date-picker
            :disabled="isActualEndDisabled"
            :disabledDate="actualStartDisabledDate"
            :getCalendarContainer="getCalendarContainer"
            :value="content.actualEndTime ? moment(content.actualEndTime,'YYYY-MM-DD'): content.actualEndTime"
            style="width: 120px"
            @change="onActualEndTimeChange"
          />
        </a-form-item>
      </a-col>
    </a-row>
    <a-row>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="提请验收日期"
          required
          style="margin-bottom:0px;"
        >
          <a-date-picker
            :getCalendarContainer="getCalendarContainer"
            :value="content.acceptance ? moment(content.acceptance,'YYYY-MM-DD'): content.acceptance"
            @change="onAcceptanceChange"
          />
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="审核意见签章"
          style="margin-bottom: 0px;"
        >
          <a-input v-model="content.auditSign" style="width: 261px"/>
        </a-form-item>
      </a-col>
    </a-row>
    <a-divider />
    <div style="padding: 0 12px">
      <a-tabs defaultActiveKey="1">
        <a-tab-pane key="1">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="验收工作说明"
              title="先对项目进行概述，然后以下供参考：
                分别从不同角度描述项目验收的主要工作和要求。例如：
                1、截止项目申请验收日，项目各项费用支出未超标，也未申请过费用追加，符合验收要求；
                2、截止项目申请验收日，项目按照预计实施内容开展并完成的工作；
                3、截止项目申请验收日，按原预计的项目实施进度开展工作，中途略有时间耽误的阶段也均在后面的实施过程中追赶上了原来的计划时间；
                4、截止项目申请验收日，项目原计划要达到的各项技术指标，具体如下已达标，有个别指标超出预计，做到更好的指标；
                5、截止项目申请验收日，可以看到的会使用项目产品新功能/新技术/新工艺的预期客户及预计订单能完成甚至超过预计的经济效益目标。"
            />
          </template>
          <a-textarea
            v-model="content.explain"
            :rows="10"
            placeholder="先对项目进行概述，然后以下供参考：
  分别从不同角度描述项目验收的主要工作和要求。例如：
  1、截止项目申请验收日，项目各项费用支出未超标，也未申请过费用追加，符合验收要求；
  2、截止项目申请验收日，项目按照预计实施内容开展并完成的工作；
  3、截止项目申请验收日，按原预计的项目实施进度开展工作，中途略有时间耽误的阶段也均在后面的实施过程中追赶上了原来的计划时间；
  4、截止项目申请验收日，项目原计划要达到的各项技术指标，具体如下已达标，有个别指标超出预计，做到更好的指标；
  5、截止项目申请验收日，可以看到的会使用项目产品新功能/新技术/新工艺的预期客户及预计订单能完成甚至超过预计的经济效益目标。" />
        </a-tab-pane>
        <a-tab-pane key="2">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="验收指标"
              title="1、技术指标：建议撰写比立项计划书中的指标好一些；
                2、效用指标：项目完工后达到的节能、降耗、减员、增效、减排等具体指标；
                3、经济指标：结合项目实施后的技术水平和技术应用范围，评估经济效益，建议比立项计划书阶段的更好一些。

                注：如上三条可根据研发主体是产品、技术、工艺选择验收指标组合1和3、2和3,、全选。"
            />
          </template>
          <a-textarea
            v-model="content.verification"
            :rows="10"
            placeholder=" 1、技术指标：建议撰写比立项计划书中的指标好一些；
2、效用指标：项目完工后达到的节能、降耗、减员、增效、减排等具体指标；
3、经济指标：结合项目实施后的技术水平和技术应用范围，评估经济效益，建议比立项计划书阶段的更好一些。

注：如上三条可根据研发主体是产品、技术、工艺选择验收指标组合1和3、2和3,、全选。" />
        </a-tab-pane>
        <a-tab-pane key="3">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="项目研发结果"
              title="经过项目研发小组人员历时一年紧张的开展研究、开发、验证、测试、试制、开会总结，对照项目实施的预期目标，项目内部评审一致通过，认为研发项目具备验收条件。
                第二段重点写“验收指标”中不能表达的研发结果，比如：
                第一种：项目实施完成后，提升公司在某新产品领域的技术主导位置，为后续开发更高技术的产品奠定基础；
                第二种：项目实施完成后，合理整合了现有工艺资源，提高了工艺操作简便性，提升了工作效率；
                第三种：项目实施完成后，为公司节省能耗，清洁生产，提高劳动者安全性。
                ......"
            />
          </template>
          <a-textarea
            v-model="content.result"
            :rows="10"
            placeholder="        经过项目研发小组人员历时一年紧张的开展研究、开发、验证、测试、试制、开会总结，对照项目实施的预期目标，项目内部评审一致通过，认为研发项目具备验收条件。
        第二段重点写“验收指标”中不能表达的研发结果，比如：
第一种：项目实施完成后，提升公司在某新产品领域的技术主导位置，为后续开发更高技术的产品奠定基础；
第二种：项目实施完成后，合理整合了现有工艺资源，提高了工艺操作简便性，提升了工作效率；
第三种：项目实施完成后，为公司节省能耗，清洁生产，提高劳动者安全性。
......" />
        </a-tab-pane>
        <a-tab-pane key="4">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="提交验收文档名称"
              title="*《项目计划书》
                *《费用预算表》
                *《费用决算报告》
                *《验收阶段检测报告》
                《项目相关图纸》--若有设计产出则提供
                《产品图片》--若有产品产出则提供
                *《科技成果转化报告》
                ......"
            />
          </template>
          <a-textarea
            v-model="content.fileName"
            :rows="10"
            placeholder="*《项目计划书》
*《费用预算表》
*《费用决算报告》
*《验收阶段检测报告》
《项目相关图纸》--若有设计产出则提供
《产品图片》--若有产品产出则提供
*《科技成果转化报告》
......" />
        </a-tab-pane>
        <a-tab-pane key="5">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              required
              text="验收专家委员会成员名单"
            />
          </template>
          <a-button type="primary" @click="handleAdd">添加</a-button>
          <a-button
            :disabled="!selectedRowKeys || !selectedRowKeys.length"
            style="margin-left:12px;"
            type="primary"
            @click="handleDels">移除
          </a-button>
          <a-table
            :dataSource="content.list"
            :pagination="false"
            :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange,columnWidth:30 }"
            :scroll="{y:480}"
            bordered
            rowKey="enumber"
            size="small"
            style="padding-top:10px;"
          >
            <a-table-column
              :width="60"
              data-index="ename"
              title="姓名"></a-table-column>
            <a-table-column
              :width="150"
              data-index="deptName"
              title="部门"></a-table-column>
            <a-table-column
              :width="150"
              data-index="position"
              title="岗位"></a-table-column>
            <a-table-column :width="100" title="签名">
              <template #default="row">
                <img :src="row.autographUrl" alt="签名" v-if="row.autographUrl" style="width: 60px; display: inline-block; vertical-align: top;">
                <!-- {{ row }} -->
              </template>
            </a-table-column>
          </a-table>
        </a-tab-pane>
        <a-tab-pane key="6">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              text="财务审核"
              title="经核算，费用支出合理且未超支，同意验收通过！"
            />
          </template>
          <a-textarea
            v-model="content.financialAudit"
            :rows="10"
            placeholder="经核算，费用支出合理且未超支，同意验收通过！" />
        </a-tab-pane>
        <a-tab-pane key="7">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              text="专家委员会意见及验收结论"
              title="如下供参考：
                经评审，该项目已按项目计划完成了项目的研发，符合公司研发项目验收中的各项要求，达到了预期的研发目标，项目通过对现有钢材力学性能分析、对金相组织要求研究，按 GB/T 4340.1 进行维氏硬度检验、分析现有所有产品、采用精轧机组组后水箱及缓冷段的研究、对水箱工艺段的研究、轧钢控温工艺的研究，实现了成品材表面质量及外观好看无红锈，而且成品材不出现蛇型弯，达到质量和外观同时达到轧制的螺纹钢筋达到“新国标”《钢筋混凝土用钢第2部分：热轧带肋钢筋》GB/T1499.2-2018要求，同时减少产品弯头情况、废品率下降0.5%，每月可多产合格钢1400吨。项目经费也通过公司财务部门决算，未发现有违反规定的现象。综上，此项目准予验收通过！"
            />
          </template>
          <a-textarea
            v-model="content.expertOpinion"
            :rows="10"
            placeholder="如下供参考：
经评审，该项目已按项目计划完成了项目的研发，符合公司研发项目验收中的各项要求，达到了预期的研发目标，项目通过对现有钢材力学性能分析、对金相组织要求研究，按 GB/T 4340.1 进行维氏硬度检验、分析现有所有产品、采用精轧机组组后水箱及缓冷段的研究、对水箱工艺段的研究、轧钢控温工艺的研究，实现了成品材表面质量及外观好看无红锈，而且成品材不出现蛇型弯，达到质量和外观同时达到轧制的螺纹钢筋达到“新国标”《钢筋混凝土用钢第2部分：热轧带肋钢筋》GB/T1499.2-2018要求，同时减少产品弯头情况、废品率下降0.5%，每月可多产合格钢1400吨。项目经费也通过公司财务部门决算，未发现有违反规定的现象。综上，此项目准予验收通过！" />
        </a-tab-pane>
        <a-tab-pane key="8">
          <template slot="tab">
            <ys-tooltip
              :getPopupContainer="getCalendarContainer"
              text="领导审批意见"
              title="验收通过！"
            />
          </template>
          <a-textarea
            v-model="content.auditOpinion"
            :rows="10"
            placeholder="验收通过！" />
        </a-tab-pane>
      </a-tabs>
    </div>
    <a-divider />
    <!-- 2021-04-20 删除 -->
    <!-- <audit-footer :year="project.beginYear" :content.sync="content" :projectId="project.id" :titleData="titleData"></audit-footer> -->
    <employee-Modal
      ref="employeeModal"
      @addMemberList="handleOk"></employee-Modal>
  </a-card>
</template>
<script>
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import employeeModal from './modules/EmployeeModal'
import { SelectDown } from '@/components/'
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
  name: 'ProjectCheckReportForm',
  components: {
    employeeModal,
    AuditFooter,
    SelectDown,
    YsTooltip
  },
  mixins: [dateMixin],
  created () {
    this.content = getTemplateContent('projectCheckReportForm')
    this.BPContent = getTemplateContent('projectCheckReportForm')
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
      domName,
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
      computedFields: ['explain', 'verification', 'result', 'fileName', 'financialAudit', 'expertOpinion', 'auditOpinion'],
      selectedRowKeys: [],
      requiredFields: ['actualStartTime', 'actualEndTime', 'acceptance', 'explain', 'verification', 'result', 'fileName', 'list']
    }
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
    },
    docId () {
      this.content = getTemplateContent('projectCheckReportForm')
      this.BPContent = getTemplateContent('projectCheckReportForm')
    }
  },
  computed: {
    isActualEndDisabled () {
      if (this.content.actualStartTime) {
        return false
      }
      return true
    },
    planTime () {
      if (!this.content.plan.length) {
        return []
      }
      return this.content.plan.map((item) => {
        if (item) {
          return moment(item, 'YYYY-MM-DD')
        }
      })
    },
    realityTime () {
      if (!this.content.reality.length) {
        return []
      }
      return this.content.reality.map((item) => {
        if (item) {
          return moment(item, 'YYYY-MM-DD')
        }
      })
    }
  },
  methods: {
    handleTemplateEvent () {
      this.selectedRowKeys = []
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    moment,
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    onPlanTimeChange (date, dateString) {
      if (!dateString) {
        dateString = undefined
      }
      this.content.plan = dateString
    },
    onRealityTimeChange (date, dateString) {
      if (!dateString) {
        dateString = undefined
      }
      this.content.reality = dateString
      this.content.realitystr = ''
      dateString.forEach((item, index) => {
        if (index > 0) {
          this.content.realityStr += ` ~ ${item}`
        } else {
          this.content.realityStr = item
        }
      })
    },
    // 提请验收日期改变事件
    onAcceptanceChange (date, dateString) {
      if (!dateString) {
        this.content.acceptance = date
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
      this.content.acceptance = dateString
    },
    // 实际开始日期改变事件
    onActualStatrTimeChange (date, dateString) {
      if (!dateString) {
        dateString = undefined
      }
      this.content.actualStartTime = dateString
    },
    // 实际结束日期改变事件
    onActualEndTimeChange (date, dateString) {
      if (!dateString) {
        dateString = undefined
      }
      this.content.actualEndTime = dateString
    },
    actualStartDisabledDate (current) {
      return current && (current < moment(this.content.actualStartTime))
    },
    actualEndDisabledDate (current) {
      if (!this.content.actualEndTime) {
        return false
      } else {
        return current && (current > moment(this.content.actualEndTime))
      }
    },
    handleDels () {
      const tempList = []
      this.content.list.forEach(item => {
        if (!this.selectedRowKeys.includes(item.enumber)) {
          tempList.push(item)
        }
      })
      this.selectedRowKeys = []
      this.content.list = tempList
    },
    handleOk (items) {
      const arr = []
      items.forEach(t => {
        arr.push({
          enumber: t.enumber,
          ename: t.ename,
          position: t.position,
          deptName: t.deptName,
          specialities: t.specialities
        })
      })
      if (this.content.list) {
        this.content.list.push(...arr)
      } else {
        this.content.list = arr
      }
    },
    handleAdd () {
      const enumberArr = []
      if (this.content.list) {
        this.content.list.forEach(item => {
          enumberArr.push(item.enumber)
        })
      }
      this.$refs.employeeModal.add(this.project.id, this.project.beginYear, enumberArr, this.fileDate, this.docId)
    }
  }
}
</script>

<style lang="less" scoped>
.icon-interval {
  margin-left: 8px;
}
</style>
