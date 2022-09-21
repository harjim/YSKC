<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-09-14 11:52:17
 * @LastEditors: hm
 * @Description: 项目评级表 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\GradeForm.vue
-->
<template>
  <a-card>

    <a-form>
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
          <a-form-item label="建议人" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px">
            <select-employee
              :mountDom="domName"
              :selected="content.advice"
              :year="year"
              @select="advice=>content.advice = advice"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="日期" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0px" required>
            <a-date-picker
              :getCalendarContainer="getCalendarContainer"
              :disabledDate="disabledDate"
              :value="content.d ? moment(content.d,'YYYY-MM-DD') : content.d"
              format="YYYY-MM-DD"
              style="width:100%"
              @change="onDateChange"
              @openChange="(status) => onOpenChange(status,'d')"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-divider/>
      <a-row>
        <table class="t_content" cellspacing="0" cellpadding="0">
          <thead>
            <tr>
              <th style="width:15%;">要素</th>
              <th style="width:40%;">评价</th>
              <th style="width:15%;">评估分值</th>
              <th style="width:15%;"><i class="required-field"/>评估得分</th>
              <th style="width:15%;"><i class="required-field"/>签字</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td rowspan="3">
                技术含量
                (10分)
              </td>
              <td>发明专利产品</td>
              <td>10分</td>
              <td style="padding:0;" rowspan="3">
                <a-input-number
                  :min="0"
                  placeholder="请输入分值"
                  class="radiusInput"
                  :precision="0"
                  :max="10"
                  v-model="content.score1"
                  @blur="scoreChange"
                />
              </td>
              <td rowspan="3" style="padding:0;">
                <select-employee
                  :mountDom="domName"
                  :selected="content.emp1"
                  :year="year"
                  @select="emp1=>content.emp1 = emp1"
                />
              </td>
            </tr>
            <tr>
              <td>实用新型/外观专利产品</td>
              <td>5分-9分</td>
            </tr>
            <tr>
              <td>一般产品</td>
              <td>&lt;5分</td>
            </tr>
            <tr>
              <td rowspan="8">
                指标先进性
                (15分)
              </td>
              <td>国际领先</td>
              <td>15分</td>
              <td rowspan="8" style="padding:0;">
                <a-input-number
                  :min="0"
                  placeholder="请输入分值"
                  class="radiusInput"
                  :max="15"
                  :precision="0"
                  @blur="scoreChange"
                  v-model="content.score2"
                />
              </td>
              <td rowspan="8" style="padding:0;">
                <select-employee
                  :mountDom="domName"
                  :selected="content.emp2"
                  :year="year"
                  @select="emp2=>content.emp2 = emp2"
                />
              </td>
            </tr>
            <tr>
              <td>国内领先</td>
              <td>14分</td>
            </tr>
            <tr>
              <td>省内领先</td>
              <td>13分</td>
            </tr>
            <tr>
              <td>国内先进/同行先进</td>
              <td>11-12分</td>
            </tr>
            <tr>
              <td>省内先进</td>
              <td>9-10分</td>
            </tr>
            <tr>
              <td>填补公司空白</td>
              <td>7-8分</td>
            </tr>
            <tr>
              <td>较先进</td>
              <td>5-6分</td>
            </tr>
            <tr>
              <td>一般产品</td>
              <td>5分以下</td>
            </tr>
            <tr>
              <td rowspan="3">
                工作量
                （20分）
              </td>
              <td>工作量大，开发进度要求紧</td>
              <td>16-20分</td>
              <td rowspan="3" style="padding:0;">
                <a-input-number
                  :min="0"
                  placeholder="请输入分值"
                  class="radiusInput"
                  :max="20"
                  :precision="0"
                  @blur="scoreChange"
                  v-model="content.score3"
                />
              </td>
              <td rowspan="3" style="padding:0;">
                <select-employee
                  :mountDom="domName"
                  :selected="content.emp3"
                  :year="year"
                  @select="emp3=>content.emp3 = emp3"
                />
              </td>
            </tr>
            <tr>
              <td>工作量较大，开发进度要求紧</td>
              <td>12-16分</td>
            </tr>
            <tr>
              <td>一般</td>
              <td>&lt;12分</td>
            </tr>
            <tr>
              <td rowspan="3">
                自主开发难度
                (10分)
              </td>
              <td>自主开发难度大</td>
              <td>8-10分</td>
              <td rowspan="3" style="padding:0;">
                <a-input-number
                  :min="0"
                  placeholder="请输入分值"
                  class="radiusInput"
                  :max="10"
                  :precision="0"
                  @blur="scoreChange"
                  v-model="content.score4"
                />
              </td>
              <td rowspan="3" style="padding:0;">
                <select-employee
                  :mountDom="domName"
                  :selected="content.emp4"
                  :year="year"
                  @select="emp4=>content.emp4 = emp4"
                />
              </td>
            </tr>
            <tr>
              <td>自主开发难度较大</td>
              <td>5-8分</td>
            </tr>
            <tr>
              <td>一般</td>
              <td>&lt;5</td>
            </tr>
            <tr>
              <td rowspan="3">
                对科技进步的贡献
                (7分)
              </td>
              <td>大幅提升公司自主开发能力</td>
              <td>6-7分</td>
              <td rowspan="3" style="padding:0;">
                <a-input-number
                  :min="0"
                  placeholder="请输入分值"
                  class="radiusInput"
                  :max="7"
                  :precision="0"
                  @blur="scoreChange"
                  v-model="content.score5"
                />
              </td>
              <td rowspan="3" style="padding:0;">
                <select-employee
                  :mountDom="domName"
                  :selected="content.emp5"
                  :year="year"
                  @select="emp5=>content.emp5 = emp5"
                />
              </td>
            </tr>
            <tr>
              <td>有效提升公司开发</td>
              <td>4-6分</td>
            </tr>
            <tr>
              <td>一般</td>
              <td>&lt;4分</td>
            </tr>
            <tr>
              <td rowspan="3">
                对品牌形象提升效果
                (8分)
              </td>
              <td>显著提升品牌知名度和质量形象等</td>
              <td>7-8分</td>
              <td rowspan="3" style="padding:0;">
                <a-input-number
                  :min="0"
                  placeholder="请输入分值"
                  class="radiusInput"
                  :max="8"
                  :precision="0"
                  @blur="scoreChange"
                  v-model="content.score6"
                />
              </td>
              <td rowspan="3" style="padding:0;">
                <select-employee
                  :mountDom="domName"
                  :selected="content.emp6"
                  :year="year"
                  @select="emp6=>content.emp6 = emp6"
                />
              </td>
            </tr>
            <tr>
              <td>有利于提升品牌知名度和质量形象等</td>
              <td>5-7分</td>
            </tr>
            <tr>
              <td>一般</td>
              <td>&lt;5分</td>
            </tr>
            <tr>
              <td rowspan="3">
                潜在经济效益
                (15分
              </td>
              <td>预期销量、收入及利润高</td>
              <td>12-15分</td>
              <td rowspan="3" style="padding:0;">
                <a-input-number
                  :min="0"
                  placeholder="请输入分值"
                  class="radiusInput"
                  :max="15"
                  :precision="0"
                  @blur="scoreChange"
                  v-model="content.score7"
                />
              </td>
              <td rowspan="3" style="padding:0;">
                <select-employee
                  :mountDom="domName"
                  :selected="content.emp7"
                  :year="year"
                  @select="emp7=>content.emp7 = emp7"
                />
              </td>
            </tr>
            <tr>
              <td>预期销量、收入及利润较高</td>
              <td>8-12分</td>
            </tr>
            <tr>
              <td>一般</td>
              <td>&lt;8分</td>
            </tr>
            <tr>
              <td rowspan="3">
                市场竞争力
                (10分)
              </td>
              <td>市场竞争力强，有效提高市场占有率</td>
              <td>8-10分</td>
              <td rowspan="3" style="padding:0;">
                <a-input-number
                  :min="0"
                  placeholder="请输入分值"
                  class="radiusInput"
                  :max="10"
                  :precision="0"
                  @blur="scoreChange"
                  v-model="content.score8"
                />
              </td>
              <td rowspan="3" style="padding:0;">
                <select-employee
                  :mountDom="domName"
                  :selected="content.emp8"
                  :year="year"
                  @select="emp8=>content.emp8 = emp8"
                />
              </td>
            </tr>
            <tr>
              <td>市场竞争力较强，有利于提高市场占有率</td>
              <td>5-8分</td>
            </tr>
            <tr>
              <td>一般</td>
              <td>&lt;5分</td>
            </tr>
            <tr>
              <td>其它积极因素</td>
              <td>项目对成本与环境因素的考虑、发展前景等</td>
              <td>≤5分</td>
              <td style="padding:0;">
                <a-input-number
                  :min="0"
                  class="radiusInput"
                  :max="5"
                  :precision="0"
                  @blur="scoreChange"
                  placeholder="请输入分值"
                  v-model="content.score9"
                />
              </td>
              <td style="padding:0;">
                <select-employee
                  :mountDom="domName"
                  :selected="content.emp9"
                  :year="year"
                  @select="emp9=>content.emp9 = emp9"
                />
              </td>
            </tr>
            <tr>
              <td>合计</td>
              <td></td>
              <td>评估得分</td>
              <td>{{ content.total }}</td>
              <td style="padding:0;">
                <select-employee
                  :mountDom="domName"
                  :selected="content.emp10"
                  :year="year"
                  @select="emp10=>content.emp10 = emp10"
                />
              </td>
            </tr>
            <tr>
              <td>序号</td>
              <td>分数档次</td>
              <td>项目等级</td>
              <td colspan="2">项目等级确定为：</td>
            </tr>
            <tr>
              <td>1</td>
              <td>90分以上</td>
              <td>A级</td>
              <td colspan="2" rowspan="4">
                <h3>{{ content.grade }}级</h3>
              </td>
            </tr>
            <tr>
              <td>2</td>
              <td>80-90</td>
              <td>B级</td>
            </tr>
            <tr>
              <td>3</td>
              <td>65-80</td>
              <td>C级</td>
            </tr>
            <tr>
              <td>4</td>
              <td>65分以下</td>
              <td>D级</td>
            </tr>
          </tbody>
        </table>
      </a-row>
      <a-divider/>
      <!-- 2021-02-26 要求删除 -->
      <!-- <a-row >
        <a-col :span="12">
          <a-form-item :label="titleData.master" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom:0px;">
            <select-employee
              :mountDom="domName"
              :selected="content.master"
              :year="project.beginYear"
              @select="master=>content.master = master"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" :label="titleData.manager" style="margin-bottom:0px;">
            <select-employee
              :mountDom="domName"
              :selected="content.manager"
              :year="project.beginYear"
              @select="manager=>content.manager = manager"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <a-divider/> -->
      <audit-footer
        ref="audtiFooter"
        :projectId="projectId"
        :docId="docId"
        :year="year"
        :employeeMap="employeeMap"
      />
    </a-form></a-card>
</template>
<script>
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'
import SelectEmployee from '@/components/SelectEmployee'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'GradeForm',
  components: {
    SelectEmployee,
    AuditFooter
  },
  mixins: [dateMixin],
  created () {
    this.content = getTemplateContent('gradeForm')
    this.BPContent = getTemplateContent('gradeForm')
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
      requiredFields: ['d', 'emp1.enumber', 'emp2.enumber', 'emp3.enumber', 'emp4.enumber', 'emp5.enumber', 'emp6.enumber', 'emp7.enumber', 'emp8.enumber', 'emp9.enumber', 'emp10.enumber', 'score1', 'score2', 'score3', 'score4', 'score5', 'score6', 'score7', 'score8', 'score9']
    }
  },
  watch: {

  },
  methods: {
    moment,
    getPopupContainer (triggerNode) {
      return popupContainer(this.domName)
    },
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    scoreChange () {
      const c = this.content
      const score = 'score'
      var total = 0
      for (let i = 1; i <= 9; i++) {
        if (c[score + i]) {
          total += Number(c[score + i])
        }
      }
      if (total >= 90) {
        c.grade = 'A'
      } else if (total >= 80 && total < 90) {
        c.grade = 'B'
      } else if (total >= 65 && total < 80) {
        c.grade = 'C'
      } else {
        c.grade = 'D'
      }
      c.total = total
    },
    onDateChange (date, dateString) {
      if (!dateString) {
        this.content.d = date
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
      this.content.d = dateString
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

<style>
.t_content {
  border: #ccc 1px solid;
  width: 100%;
}
.t_content td {
  border: #ccc 1px solid;
  text-align: center;
  font-size: 16px;
  color: #666;
  padding-left: 5px;
}
.t_content th {
  border: #ccc 1px solid;
  text-align: center;
  font-size: 16px;
  color: #666;
}
.radiusInput {
  width: 100%;
  border-radius: 0;
}
</style>
