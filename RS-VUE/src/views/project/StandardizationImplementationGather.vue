<!--
 * @Author: ldx
 * @Date: 2021-02-26 13:43:10
 * @LastEditTime: 2021-03-01 16:29:46
 * @LastEditors: ldx
 * @Description: 年度研发项目标准化实施情况汇总表
 * @FilePath: \RS-VUE\src\views\project\StandardizationImplementationGather.vue
-->
<template>
  <a-card :bodyStyle="{height: '100%', overflow: 'auto'}" class="contentPage">
    <div class="btn_wrap">
      <a-button
        v-show="!isEdit"
        class="btn_spcoe"
        size="small"
        type="primary"
        @click="changeMode(true)">编辑
      </a-button>
      <a-button
        v-show="isEdit"
        class="btn_spcoe"
        size="small"
        type="primary"
        @click="onReturn">返回
      </a-button>
      <a-button
        v-show="isEdit"
        class="btn_spcoe"
        size="small"
        type="primary"
        @click="changeMode(false)">保存
      </a-button>
    </div>
    <a-row align="middle" justify="center" type="flex">
      <a-col><span class="title">{{ currentYear }}年度研发项目标准化实施情况汇总表</span>
      </a-col>
    </a-row>
    <a-divider />
    <div class="form_wrap">
      <a-spin :spinning="spinning" tip="加载中...">
        <a-form :form="form">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="研发中心建设标准化">
            <a-checkbox-group
              v-decorator="['rdCenterBuild']"
              :disabled="!isEdit"
              :options="rdCenterBuildOptions"></a-checkbox-group>
          </a-form-item>
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="研发项目管理标准化">
            <a-checkbox-group
              v-decorator="['rdProjectManage']"
              :disabled="!isEdit"
              :options="rdProjectManageOptions"></a-checkbox-group>
          </a-form-item>
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="研发费用核算标准化">
            <a-checkbox-group
              v-decorator="['rdBudgetOptions']"
              :disabled="!isEdit"
              :options="rdBudgetOptions"></a-checkbox-group>
          </a-form-item>
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="研发成果管理标准化">
            <a-checkbox-group
              v-decorator="['rdResultManage']"
              :disabled="!isEdit"
              :options="rdResultManageOptions"></a-checkbox-group>
          </a-form-item>
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="知识产权管理标准化">
            <a-checkbox-group
              v-decorator="['intellectualManage']"
              :disabled="!isEdit"
              :options="intellectualManageOptions"></a-checkbox-group>
          </a-form-item>
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="高新技术产品标准化">
            <a-checkbox-group
              v-decorator="['highTech']"
              :disabled="!isEdit"
              :options="highTechOptions"></a-checkbox-group>
          </a-form-item>
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="研发档案管理标准化">
            <a-checkbox-group
              v-decorator="['fileManage']"
              :disabled="!isEdit"
              :options="fileManageOptions"></a-checkbox-group>
          </a-form-item>
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="研发意识管理标准化">
            <a-checkbox-group
              v-decorator="['thoughtManage']"
              :disabled="!isEdit"
              :options="thoughtManageOptions"></a-checkbox-group>
          </a-form-item>
        </a-form>
      </a-spin>
    </div>
  </a-card>
</template>

<script>
import {
  queryStandard,
  saveStandard
} from '@/api/project/StandardizationImplementationGather/index'
import { mapGetters } from 'vuex'
import { cloneDeep } from 'lodash'

export default {
  name: 'StandardizationImplementationGather',
  created () {
    this.handleQueryStandard()
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  watch: {
    currentYear () {
      this.handleQueryStandard()
      this.isEdit = false
    }
  },
  data () {
    return {
      spinning: false,
      form: this.$form.createForm(this),
      labelCol: { xs: { span: 24 }, sm: { span: 6 }, md: { span: 4 } },
      wrapperCol: { xs: { span: 24 }, sm: { span: 18 }, md: { span: 20 } },
      isEdit: false,
      formData: {},
      backupFormData: {},
      // 研发中心建设标准化
      rdCenterBuildOptions: [
        { label: '研发机构部门建立（牌匾、发光字、公章、标语、宣传栏等）', value: '1' },
        { label: '研发机构组织架构+职能', value: '2' },
        { label: '研发技术人员花名册（资料档案）', value: '3' },
        { label: '研发机构相关制度', value: '4' },
        { label: '研发费核算制度', value: '5' }
      ],
      // 研发项目管理标准化
      rdProjectManageOptions: [
        { label: '研发意识与组织（对应项目已交付给对应项目负责人明确项目情况）', value: '1' },
        { label: '研发过程梳理', value: '2' }
      ],
      // 研发费用核算标准化
      rdBudgetOptions: [
        { label: '会计准则基本情况培训', value: '1' },
        { label: '企业财务管理制度', value: '2' },
        { label: '研发费用专帐建立', value: '3' },
        { label: '辅助帐核算', value: '4' }
      ],
      //  知识产权管理标准化
      intellectualManageOptions: [
        { label: '专利解释', value: '1' },
        { label: '专利交底书', value: '2' },
        { label: '专利申报流程', value: '3' },
        { label: '专利申请管理', value: '4' }
      ],
      // 高新技术产品标准化
      highTechOptions: [
        { label: '高新技术产品费用归集', value: '1' },
        { label: '高新技术产品设置合理', value: '2' }
      ],
      // 研发档案管理标准化
      fileManageOptions: [
        { label: '档案室', value: '1' },
        { label: '分项目建档', value: '2' },
        { label: '建档资料目录', value: '3' },
        { label: '有效管理（管理流程清晰）', value: '4' }
      ],
      // 研发意识管理标准化
      thoughtManageOptions: [
        { label: '组织研发项目培训会议', value: '1' },
        { label: '明确参会人员', value: '2' },
        { label: '项目重要信息培训、交流', value: '3' },
        { label: '评审答辩培训', value: '4' },
        { label: '与主管部门的对应措施', value: '5' },
        { label: '部门数据及时性整理', value: '6' },
        { label: '主要部门确定', value: '7' },
        { label: '主要部门走访', value: '8' }
      ],
      // 研发成果管理标准化
      rdResultManageOptions: [
        { label: '产品鉴定报告', value: '1' },
        { label: '知识产权受理、授权通知书或证书', value: '2' },
        { label: '试验报告', value: '3' },
        { label: '标准', value: '4' },
        { label: '工艺、装置流程图', value: '5' },
        { label: '技术查新报告', value: '6' },
        { label: '用户意见书', value: '7' },
        { label: '产品图片', value: '8' },
        { label: '产品销售合同、发票', value: '9' }
      ]
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
      if (this.backupFormData) {
        this.$nextTick(() => {
          this.$initForm(this.form, this.backupFormData)
        })
      }
    },
    /**
     * @description: 切换模式按钮
     * @param {*} model 编辑true  保存false
     * @return {*}null
     */
    changeMode (model) {
      if (model) {
        this.isEdit = model
        this.spinning = !this.spinning
        this.transformFormData()
        this.spinning = !this.spinning
      } else {
        this.handleSaveStandard()
      }
    },
    handleQueryStandard () {
      this.spinning = !this.spinning
      queryStandard({ year: this.currentYear }).then((resolve, reject) => {
        if (resolve.success) {
          if (resolve.data) {
            const fields = ['rdCenterBuild', 'rdProjectManage', 'rdBudget', 'intellectualManage', 'highTech', 'fileManage', 'thoughtManage', 'rdResultManage']
            this.formData = resolve.data
            for (const field of fields) {
              this.formData[field] = JSON.parse(this.formData[field])
            }
          } else {
            this.formData = {}
          }
        } else {
          this.formData = {}
          this.$message.info('数据查询出错!', resolve.errorCode, resolve.errorMessage)
          console.log('handleQueryStandard function error' + resolve.errorCode, resolve.errorMessage)
        }
        this.backupFormData = cloneDeep(this.formData)
        this.transformFormData()
      }).catch((error) => {
        this.$message.info('数据查询出错', error)
        console.log('handleQueryStandard function error: ', +error)
      }).finally(() => {
        this.spinning = !this.spinning
      })
    },
    handleSaveStandard () {
      this.form.validateFields((error, values) => {
        if (!error) {
          this.spinning = !this.spinning
          const postObj = {}
          for (const key in values) {
            postObj[key] = JSON.stringify(values[key])
          }
          postObj.year = this.currentYear
          saveStandard(postObj).then((resolve, reject) => {
            if (resolve.success && resolve.data) {
              Object.assign(this.formData, values)
              this.backupFormData = cloneDeep(this.formData)
              this.isEdit = false
              this.$message.success('操作成功！')
            } else {
              this.$message.info('操作失败！')
              this.formData = cloneDeep(this.backupFormData)
              console.log('handleSaveStandard function error' + resolve.errorCode, resolve.errorMessage)
            }
          }).catch(error => {
            this.$message.error('系统异常！')
            console.log('handleSaveStandard function error' + error)
            this.formData = cloneDeep(this.backupFormData)
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
      if (this.formData) {
        this.$nextTick(() => {
          this.$initForm(this.form, this.formData)
        })
      }
    }
  }
}
</script>

<style lang="less" scoped>
.contentPage {
  height: 100%;
  overflow: auto;
  position: relative;

  .title {
    font-size: 18px;
    font-weight: bold;
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

    & /deep/ .ant-checkbox-disabled + span {
      color: #000000a6;
    }
  }
}
</style>>
