<!--
 * @Description: 效用报告 —— 默认模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\UtilityReportForm.vue
-->
<template>
  <div class="container">
    <div style="height:900px;">
      <div style="text-align: center;">
        <h1>{{ publicInfo.commonMap.companyName }}</h1>
        <h3>研发项目效用、研究成果报告及核算情况说明</h3>
      </div>
      <div style="height:400px;"></div>
      <table style="border-collapse: collapse; width: 100%;">
        <tbody>
          <tr>
            <td style="width: 14%;" ></td>
            <td
              style="width: 20%;vertical-align:top; padding-top: 7px; font-size: 21px; text-align:justify; text-align-last:justify;"
              align="right">项目名称</td>
            <td style="width: 2%; font-size: 21px; vertical-align:top; padding-top: 7px; font-size: 21px;" >：</td>
            <td style="width: 50%; font-size: 21px; vertical-align:top; border-bottom: 1px solid; line-height: 36px;" >{{ project.pname }}</td>
            <td style="width: 14%;" ></td>
          </tr>
          <tr>
            <td style="width: 14%;"></td>
            <td style="width: 20%; font-size: 21px; text-align:justify; text-align-last:justify;" align="right">负责人</td>
            <td style="width: 2%; font-size: 21px;" >：</td>
            <td style="width: 50%; font-size: 21px; border-bottom: 1px solid;" >{{ publicInfo.commonMap.projectMasterName }}</td>
            <td style="width: 14%;" ></td>
          </tr>
          <tr>
            <td style="width: 14%;"></td>
            <td style="width: 20%; font-size: 21px;  text-align:justify; text-align-last:justify;" align="right">时间</td>
            <td style="width: 2%; font-size: 21px;" >：</td>
            <td style="width: 50%; font-size: 21px;  border-bottom: 1px solid;" >
              <i class="required-field"/>
              <a-date-picker
                @change="(date, dateString) => onDateChange(dateString)"
                :value="content.month ? moment(content.month, 'YYYY-MM-DD') : undefined"
                placeholder="请输入日期"
              />
            </td>
            <td style="width: 14%;"></td>
          </tr>
        </tbody>
      </table>
    </div>
    <h3><i class="required-field"/>一、项目概况</h3>
    <wang-editor
      key="overview"
      v-model="content.overview"
      :projectId="project.id"
      placeholder="项目概况"
    />
    <h3><i class="required-field"/>二、主要技术指标或经济指标</h3>
    <wang-editor
      key="target"
      v-model="content.target"
      :projectId="project.id"
      placeholder="主要技术指标或经济指标"
    />
    <h3><i class="required-field"/>三、项目技术创新情况</h3>
    <wang-editor
      key="innovation"
      v-model="content.innovation"
      :projectId="project.id"
      placeholder="项目技术创新情况"
    />
    <h3>四、项目核算情况</h3>
    <div>
      <p>公司严格规定研发费用的开支范围和标准，并严格审批程序。由研发部门提出研发项目建议，有研发部门完成项目的调研和立项，并评估所涉及到的研发投入费用计划，在项目立项决策时由研发部门，技术领导，总经理，财务经理等相关部门会议决策研发投入费用，决策通过后，由研发部门提出申请，技术领导审批，财务经理审批，总经理批准。</p>
      <p>本项目严格按照财税〔2015〕119号文第五条以及省、市相关规定，建立单独核算科目及明细科目，并建立研发辅助账。同时每月发生的研发经费总额与明细做一汇总表，对研发经费进行月度发生额与累计额的监控，以便于研发项目的经费控制，经核算本项目开发费实际发生额：{{ publicInfo.commonMap.rdFundsSum }}元。详见《研发项目加计扣除研究开发费用情况归集表》。</p>
      <p>按照研发项目财务设立台账归集核算研发费用，公司研发部门发生的各项开支纳入研发费用管理，同时承担生产任务的，合理划分研发与生产费用。财务协助研发部门做好研发投入费用的预算编制和控制。</p>
      <p>公司对研发费用的入账方式做了规定：执行《企业会计准则》的企业设立“研发支出”账户，在“费用化支出”科目下，将公司根据研发项目编号分项目进行划分：规定研究阶段的支出全部费用化，计入当期损益(管理费用)，研究开发费用科目的设置要求如下：</p>
    </div>
    <table border="1">
      <tbody>
        <tr>
          <td width="15%">费用类别</td>
          <td width="15%">二级子科目</td>
          <td width="70%">归集内容</td>
        </tr>
        <tr>
          <td rowspan="4">人员人工</td>
          <td>人员工资</td>
          <td class="text-left">直接从事研发活动的本企业在职人员的工资、薪金。</td>
        </tr>
        <tr>
          <td>津贴补贴</td>
          <td class="text-left">直接从事研发活动的本企业在职人员的津贴、补贴。</td>
        </tr>
        <tr>
          <td>奖金、年终</td>
          <td class="text-left">直接从事研发活动的本企业在职人员的奖金、年终加薪。</td>
        </tr>
        <tr>
          <td>其他</td>
          <td class="text-left">研发人员等与其任职或者受雇有关的其他支出，包括：企业缴纳的研发人员基本社会保险费用（含企业按照国务院及其他有关部门规定的标准和范围为研发人员支付的补充养老保险、补充医疗保险费）和公积金补助费，以及外聘研发人员的劳务费用等。</td>
        </tr>
        <tr>
          <td rowspan="5">直接投入</td>
          <td>研发材料</td>
          <td class="text-left">从事研发活动直接消耗的材料、半成品。</td>
        </tr>
        <tr>
          <td>燃料和动力费</td>
          <td class="text-left">从事研发活动直接消耗的燃料和动力费用</td>
        </tr>
        <tr>
          <td>工装及检验费</td>
          <td class="text-left">工艺装备开发制造费，设备调整检验费，试制产品检验费</td>
        </tr>
        <tr>
          <td>仪器设备维护费</td>
          <td class="text-left">用于研发活动的仪器设备简单维护费</td>
        </tr>
        <tr>
          <td>仪器设备租赁费</td>
          <td class="text-left">专门用于研发活动的仪器、设备的租赁费</td>
        </tr>
        <tr>
          <td rowspan="3">折旧费用与长期费用摊销</td>
          <td>研发仪器设备折旧费</td>
          <td class="text-left">专门用于研发活动的仪器、设备的折旧费。</td>
        </tr>
        <tr>
          <td>长期待摊费用</td>
          <td class="text-left">研发仪器、设备在改装、修理过程中发生的长期待摊费用</td>
        </tr>
        <tr>
          <td>其他</td>
          <td class="text-left">研究开发项目在用建筑物的折旧费用，包括研发设施改建、改装、装修和修理过程中发生的长期待摊费用。</td>
        </tr>
        <tr>
          <td rowspan="2">装备调试和试制费</td>
          <td>试制费</td>
          <td class="text-left">专门用于中间试验和产品试制的模具、工艺装备开发及制造费</td>
        </tr>
        <tr>
          <td>设计制定费</td>
          <td class="text-left">新产品设计费、新工艺规程制定费。</td>
        </tr>
        <tr>
          <td rowspan="3">其他</td>
          <td>研发成果相关费用</td>
          <td class="text-left">研发成果的论证、鉴定、评审、验收费。</td>
        </tr>
        <tr>
          <td>图书资料及翻译费</td>
          <td class="text-left">技术图书资料费、资料翻译费。</td>
        </tr>
        <tr>
          <td>其他</td>
          <td class="text-left">为研究开发活动所发生的会议费、差旅费、办公费、外事费、研发人员培训费、培养费、专家咨询费、通讯费、专利申请维护费、高新科技研发保险费用等。</td>
        </tr>
      </tbody>
    </table>
    <div>
      <p>公司提供证明费用实际已经发生的足够、适当的有效凭证。研发费明细账与有效凭证一一对应，确保研发费用的真实、准确归集：</p>
      <p>1.研发人员工资薪金等，以职能部门(研发或财务部门)按月单独编制的工资清单(或分配表)，作为有效凭证；</p>
      <p>2.为研发项目直接耗用的原材料，以研发材料成本核算表（领料单）作为有效凭证；</p>
      <p>3.燃料、动力等间接费用，以分配表作为有效凭证；</p>
      <p>4.租赁费以租赁合同、发票及分配表作为有效凭证；</p>
      <p>5.折旧费等，以研发仪器设备折旧表（分配表）作为有效凭证；</p>
      <p>6.装备调试和试制费以装备调试和试制表（领料单）作为有效凭证；</p>
      <p>7.其他项目根据实际情况凭相关有效凭证进行归集。</p>
    </div>
    <a-divider/>
    <audit-footer
      ref="audtiFooter"
      :projectId="projectId"
      :docId="docId"
      :year="year"
      :employeeMap="employeeMap"
    />
  </div>
</template>
<script>
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import dateMixin from './js/dateMixin'
import WangEditor from '@/components/Editor/WangEditor'
import moment from 'moment'
import AuditFooter from './modules/AuditFooter'

export default {
  name: 'UtilityReportForm',
  components: { WangEditor, AuditFooter },
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
    project: {
      type: Object,
      default: () => {}
    }
  },
  data () {
    return {
      content: {},
      BPContent: {},
      requiredFields: ['month', 'overview', 'target', 'innovation']
    }
  },
  created () {
    this.content = getTemplateContent('UtilityReportForm')
    this.BPContent = getTemplateContent('UtilityReportForm')
  },
  methods: {
    moment,
    onDateChange (dateString) {
      if (dateString) {
        this.content.month = moment(dateString).format('YYYY-MM-DD')
      } else {
        this.content.month = undefined
      }
    }
  }
}
</script>

<style lang="less" scoped>
.text-left {
  text-align: left;
}
</style>
