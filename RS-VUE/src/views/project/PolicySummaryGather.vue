<!--
 * @Author: ldx
 * @Date: 2021-02-26 17:04:07
 * @LastEditTime: 2021-03-01 16:57:20
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\PolicySummaryGather.vue
-->
<template>
  <a-card class="contentPage" :bodyStyle="{height: '100%', overflow: 'auto'}">
    <a-row type="flex" justify="center" align="middle">
      <a-col> <span class="title">年度研发项目标准化实施情况汇总表</span> </a-col>
    </a-row>
    <a-divider/>
    <div class="form_wrap">
      <a-spin :spinning="spinning" tip="加载中...">
        <a-form :form="form">
          <span style="font-size: 18px;"> 一、项目实施依据法规</span>
          <a-list size="small" :data-source="statuteData">
            <a-list-item slot="renderItem" slot-scope="item, index">
              {{ index + 1 }}. {{ item }} ;
            </a-list-item>
          </a-list>
          <span style="font-size: 18px;">二、政府主管部门资料清单/企业备底资料/税务要求备底资料/流程/会计核算方面的要求</span>
          <a-list size="small" :data-source="filingMaterialData">
            <a-list-item slot="renderItem" slot-scope="item, index">
              <span class="two_leve_title">
                {{ index + 1 }}. {{ item }} ;
              </span>
            </a-list-item>
            <div slot="header">
              <span style="font-size: 16px;">2.1、企业备底资料</span>
            </div>
          </a-list>
          <a-list size="small" :data-source="additionalDeductionData">
            <a-list-item slot="renderItem" slot-scope="item, index">
              <span class="two_leve_title">
                {{ index + 1 }}. {{ item }} ;
              </span>
            </a-list-item>
            <div slot="header">
              <span style="font-size: 16px;">2.2、加计扣除流程</span>
            </div>
          </a-list>
        </a-form>
      </a-spin>
    </div>
  </a-card>
</template>

<script>
// import { mapGetters } from 'vuex'
import { queryPolicy, savePolicy } from '@/api/project/PolicySummaryGather/index'
import { cloneDeep } from 'lodash'
const policyData = {
  statute: undefined,
  filingMaterial: undefined,
  additionalDeduction: undefined
}
const statuteData = [
  '国家高新技术企业领域',
  '关于完善研究开发费用税前加计扣除政策的通知（财税[2015]119号）',
  '关于企业研究开发费用税前加计扣除政策有关问题的公告（国家税务总局公告[2015]97号，目前部分条款被废止）',
  '高新技术企业认定管理工作指引（ 2016 年 修 订 ） （ 国 科 发 火[2016]195号附件）',
  '关于进一步做好企业研发费用加计扣除政策落实工作的通知（国科发政[2017]211号）',
  '关于研发费用税前加计扣除归集范围有关问题的公告（国家税务总局公告[2017]40号）',
  '关于发布修订后的《企业所得税优惠政策事项办理办法》的公告（国家税务总局公告[2018]23号）',
  '关于企业委托境外研究开发费用税前加计扣除有关政策问题的通知（财税[2018]64号）',
  '关于提高研究开发费用税前加计扣除比例的通知（财税[2018]99号）',
  '指引1.0'
]
const filingMaterialData = [
  '研发机构部门、研发机构组织架构、机构简介、研发技术人员花名册（定期变动）、研发机构相关制度、研发费制度、研发费核算制度、研发准备金制度（企业自有模板）',
  '自主、委外、合作研发项目决议（企业模板）',
  '自主、委外、合作研发项目立项书（计划书）',
  '自主、委外、合作研发项目的研发费用决算情况',
  '集中研发项目的研发费有分摊说明（根据需要选择提供）',
  '研究成果、成果报告及核算情况说明',
  '研发支出辅助帐',
  '研发过程记录文件'
]
const additionalDeductionData = [
  '公司决定立项研发活动',
  '将委托或合作研发项目签订的合同在科技部门登记',
  '设立会计科目(研发支出项目及其子项目)',
  '设立该项目研发费用辅助账',
  '对研发支出进行会计核算',
  '按有关凭证填制“研发费用辅助账”',
  '填报《研发支出辅助账汇总表》作为财务报表附注报送税局',
  '填报《研发项目可加计扣除研究开发费用归集表》',
  '收集准备留存备查资料',
  '向主管税务局备案',
  '进行企业所得税汇算清缴年度纳税申报，申报享受相应优惠政策',
  '税务以有异项的项目送当地科部门鉴定',
  '留存以上整个过程中的有关材料，以备税局后续查访管理'
]
export default {
  name: 'PolicySummaryGather',
  computed: {
    // ...mapGetters(['currentYear'])
  },
  mounted () {
    // this.handleQueryPolicy()
  },
  data () {
    return {
      spinning: false,
      form: this.$form.createForm(this),
      labelCol: { xs: { span: 24 }, sm: { span: 6 }, md: { span: 4 } },
      wrapperCol: { xs: { span: 24 }, sm: { span: 18 }, md: { span: 20 } },
      isEdit: false,
      policyData,
      backupPolicyData: policyData,
      statuteData,
      filingMaterialData,
      additionalDeductionData
    }
  },
  methods: {
    /**
     * @description: 返回按钮
     * @param {*} null
     * @return {*} null
     */
    onReturn () {
      this.isEdit = false
      this.form.resetFields()
      if (this.backupPolicyData) {
        this.$nextTick(() => {
          this.$initForm(this.form, this.backupPolicyData)
        })
      }
    },
    /**
     * @description: 切换模式按钮
     * @param {*} model 编辑true  保存false
     * @return {*}null
     */
    changeMode (model) {
      this.isEdit = model
      if (model) {
        this.spinning = !this.spinning
        this.transformFormData()
        this.spinning = !this.spinning
      } else {
        this.handleSavePolicy()
      }
    },
    /**
     * @description: 执行查询政策及要求情况汇总
     * @param {*}
     * @return {*}
     */
    handleQueryPolicy () {
      queryPolicy({}).then((resolve, reject) => {
        if (resolve.success && resolve.data) {
          this.policyData = resolve.data
          this.backupPolicyData = cloneDeep(this.policyData)
        }
        this.transformFormData()
      })
    },
    /**
     * @description: 执行保存政策及要求情况
     * @param {*}
     * @return {*}
     */
    handleSavePolicy () {
      this.form.validateFields((error, values) => {
        if (!error) {
          this.spinning = !this.spinning
          savePolicy(values).then((resolve, reject) => {
            if (resolve.success && resolve.data) {
              Object.assign(this.policyData, values)
              this.backupPolicyData = cloneDeep(this.policyData)
            } else {
              this.$message.info('操作失败！', resolve.errorCode, resolve.errorMessage)
              this.policyData = cloneDeep(this.backupPolicyData)
              console.log('handleSavePolicy function error' + resolve.errorCode, resolve.errorMessage)
            }
          }).catch((error) => {
            this.policyData = cloneDeep(this.backupPolicyData)
            console.log('handleSavePolicy function error:', error)
          }).finally(() => {
            this.spinning = !this.spinning
          })
        }
      })
    },
    /**
     * @description: 初始化表单数据
     * @param {*}
     * @return {*}
     */
    transformFormData () {
      this.form.resetFields()
      if (this.policyData) {
        this.$nextTick(() => {
          this.$initForm(this.form, this.policyData)
        })
      }
    }
  }
}
</script>

<style lang="less" scoped>
.ant-divider-horizontal.ant-divider-with-text-center, .ant-divider-horizontal.ant-divider-with-text-left, .ant-divider-horizontal.ant-divider-with-text-right {
  font-size: 18px;
}
.contentPage {
  height: 100%;
  overflow: auto;
  position: relative;
  .title {
    font-size: 20px;
    font-weight: bold;
  }
  .two_leve_title{
    padding-left: 2em;
    font-size: 16px;
  }
  .three_leve_title{
    padding-left: 4em;
  }
  .ant-list {
    padding-left: 2em;
    &/deep/ .ant-list-header {
      border-bottom: 0px;
      padding-bottom: 0px;
    }
  }
  .ant-list-split {
    .ant-list-item {
      border-bottom: 0px;
    }
  }
  .ant-divider-horizontal {
    margin: 10px 0;
  }
  .btn_wrap {
    position: absolute;
    top: 28px;
    right: 26px;
    .btn_spcoe {
      margin: 0 3px;
    }
  }
  .form_wrap {
    height: calc(100% - 54px);
    overflow: auto;
    & /deep/ .ant-spin-nested-loading {
      height: 100%;
    }
    & /deep/ .ant-spin-container {
      height: 100%;;
    }
  }
}
</style>
