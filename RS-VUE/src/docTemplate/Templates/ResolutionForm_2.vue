<!--
 * @Author: ldx
 * @Date: 2021-04-12 13:43:28
 * @LastEditTime: 2022-09-14 11:48:54
 * @LastEditors: hm
 * @Description: 立项决议 —— 董事会决议
 * @FilePath: \RS-VUE\src\docTemplate\Templates\ResolutionForm_2.vue
-->
<template>
  <a-card>
    <!-- <a-form> -->
    <table style="border-collapse: collapse; width: 100%;" border="0">
      <tbody>
        <tr>
          <td style="width: 100%; vertical-align:top; font-size: 16px;" colspan="5">
            <p style="text-indent:2em;">为了推动公司科技创新，研究解决公司产品、工艺、技术问题。</p>
            <p style="text-indent:2em;">公司董事会成员于<i class="required-field"/><a-date-picker style="text-indent: 0; width: 130px;" v-model="meetingDate" @change="onChange"/>在公司会议室召开了董事会会议，董事会成员<i class="required-field"/><a-input v-model="content.meetingMember" placeholder="请输入董事会成员" style="width: 250px;" />出席了本次会议，全体董事均已到会。</p>
            <p style="text-indent:2em;">本次董事会议的召集与召开程序、出席会议人员资格及表决程序符合公司的会议章程规定。会议就我公司开发“{{ project.pname }}”项目一致同意以下决议：</p>
            <p style="text-indent:2em;">一、公司决定自主研发“{{ project.pname }}”项目，时间自{{ beginDate }}开始，预计{{ endDate }}结束；</p>
            <p style="text-indent:2em;">二、公司计划投入{{ publicInfo.commonMap && publicInfo.commonMap.totalBudget ? publicInfo.commonMap.totalBudget: 0 }}万元用于该项目的研发；</p>
            <p style="text-indent:2em;">三、公司成立项目组，由{{ publicInfo.commonMap && publicInfo.commonMap.projectMasterName ? publicInfo.commonMap.projectMasterName : '' }}为项目负责人，承担该项目的研究开发工作；</p>
            <p style="text-indent:2em;">四、财务部门按照国家财务会计制度要求，对研发支出进行会计核算处理；同时，对列入加计扣除的研发费用按研发项目设置辅助账，通过“研发支出”辅助账汇总表、“研发支出”辅助账进行准确归集核算当年可加计扣除的各项研发费用实际发生额，明显区分研发与生产费用；研发技术部门加强项目研发与管理，确保项目顺利实施。</p>
          </td>
        </tr>
        <tr>
          <!-- <td style="width: 5%; vertical-align:top; text-align:right; font-size: 16px;"></td> -->
          <td style="width: 19%; vertical-align:top; text-align:right; font-size: 16px;">
            董事会成员签字：
          </td>
          <td style="border-bottom: 1px solid; font-size: 16px;" colspan="3">
          </td>
          <td style="width: 5%; vertical-align:top; text-align:right; font-size: 16px;"></td>
        </tr>
        <tr>
          <td colspan="5"></td>
        </tr>
        <tr>
          <td style="width: 19%; vertical-align:top; text-align:right; font-size: 16px;" colspn="2"></td>
          <td style="width: 81%; vertical-align:top; text-align:right; font-size: 16px;" colspan="3">
            <p>{{ userInfo.companyName }}</p>
            <p>{{ content.meetingDate ? moment(content.meetingDate).format('YYYY年MM月DD日') : '' }}</p>
          </td>
        </tr>
      </tbody>
    </table>
    <!-- </a-form> -->
  </a-card>
</template>
<script>
import AuditFooter from './modules/AuditFooter'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import { mapGetters } from 'vuex'
import moment from 'moment'
import dateMixin from '../Templates/js/dateMixin'

export default {
  name: 'ResolutionForm',
  components: {
    AuditFooter
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
    this.content = getTemplateContent('resolutionForm2')
    this.BPContent = getTemplateContent('resolutionForm2')
  },
  computed: {
    ...mapGetters(['userInfo']),
    beginDate () {
      return moment(this.project.beginDate).format('YYYY年MM月')
    },
    endDate () {
      return moment(this.project.endDate).format('YYYY年MM月')
    },
    meetingDate: {
      get () {
        if (this.content && this.content.meetingDate) {
          return moment(this.content.meetingDate)
        } else {
          return undefined
        }
      },
      set (v) {
        this.content.meetingDate = v
      }
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
      content: {
        meetingDate: undefined
      },
      BPContent: {},
      project: {
        pname: undefined,
        ename: undefined,
        beginDate: undefined,
        endDate: undefined,
        estimateExpense: undefined
      },
      computedFields: ['resolution'],
      requiredFields: ['meetingDate', 'meetingMember']
    }
  },
  methods: {
    moment,
    onChange (date, dateStr) {
      if (!dateStr) {
        this.content.meetingDate = date
        if (this.stage && this.stage.beginDate) {
          this.fileDate = moment(this.stage.beginDate).startOf('month')
        } else {
          this.fileDate = moment(this.BUfileDate).startOf('month')
        }
        this.hsaFileDataNull = true
        return
      }
      this.hsaFileDataNull = false
      this.content.meetingDate = dateStr
      this.fileDate = moment(dateStr).startOf('month')
    }
  }
}
</script>

<style>
</style>
