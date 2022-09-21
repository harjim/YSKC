<!-- 项目立项计划-规划项目 -->
<template>
  <a-modal
    :title="title"
    style="top: 200px;"
    :width="600"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form" ref="form">
      <a-row :gutter="24" style="height: 40px; overflow: hidden;">
        <a-col :span="12">
          <a-form-item label="总项目数:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span>{{ countProjects }}</span>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="上年延续项目数:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <span>{{ countRenewalProject }}</span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="本年新增项目数:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入本年新增项目数"
              v-decorator="['cnt', {rules:[{required: true, message: '请输入项目数'}, { validator: this.cntChange } ], validateTrigger: ['change'], initialValue: plan.cnt ? plan.cnt : 0 }]"
              :min="0"
              :precision="0"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="研发费(万元):" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入研发费"
              v-decorator="['rdFee', {rules:[{required: true, message: '请输入研发费'}], initialValue: plan.rdFee ? plan.rdFee : 0}]"
              :min="0"
              :max="9999999"
              :precision="0"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <!-- <a-row :gutter="24">

      </a-row> -->
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="营收预测(万元):" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入营收预测"
              v-decorator="['revenueFcst', {rules:[{required: true, message: '请输入营收预测'}], initialValue: plan.revenueFcst ? plan.revenueFcst : 0}]"
              :min="0"
              :max="9999999"
              :precision="0"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="企业总人数:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入企业总人数"
              v-decorator="['employeeAmount', {rules:[{required: true, message: '请输入企业总人数'}], initialValue: plan.employeeAmount ? plan.employeeAmount : 0}]"
              :min="0"
              :precision="0"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <!-- <a-row :gutter="24">

      </a-row> -->
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item
            label="相关部门:"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol">
            <a-tree-select
              multiple
              treeDefaultExpandAll
              :dropdown-style="{ maxHeight: '250px', overflow: 'auto' }"
              :tree-data="treeData"
              v-decorator="['deptIds', {rules:[{required: true, message: '请选择相关部门'}]}]"
              placeholder="请选择相关部门"
              style="width: 300%"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>

export default {
  components: {
  },
  name: 'ProjectNum',
  data () {
    return {
      title: '',
      // labelCol1: {
      //   xs: { span: 24 },
      //   sm: { span: 5 }
      // },
      // wrapperCol1: {
      //   xs: { span: 24 },
      //   sm: { span: 19 }
      // },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 11 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      id: 0,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      year: undefined,
      plan: { cnt: 0, rdFee: 0, revenueFcst: 0, employeeAmount: 0 },
      countRenewalProject: undefined,
      treeData: []
      // deptIds: []
    }
  },
  computed: {
    countProjects () {
      // const cnt = this.$refs.form
      return this.countRenewalProject + (this.plan.cnt ? this.plan.cnt : 0)
    }
  },
  methods: {
    set (year, planNum, plan, countRenewalProject) {
      this.title = year + '年项目规划'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = 0
      this.year = year
      this.plan = plan
      this.countRenewalProject = countRenewalProject
      // this.deptIds = plan.deptIds
      this.loadTree()
      if (planNum !== -1) {
        this.$nextTick(() => {
          this.$initForm(this.form, { projectNum: planNum })
        })
      }
      this.$nextTick(() => {
        this.form.setFieldsValue({ deptIds: this.plan.deptIds })
      })
    },
    loadTree () {
      this.$getTree('dept')
        .then(res => {
          this.treeData = res.tree
        })
    },
    cntChange (_, value, callback) {
      this.plan.cnt = value
      callback()
    },
    countProject () {
      return 1
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.ryear = this.year
          values.deptIds = JSON.stringify(values.deptIds)
          this.$http.post('/report/setPlanInfo', values)
            .then(res => {
              if (res.success && res.data) {
                this.$message.success('保存成功')
                this.visible = false
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
                this.form.setFieldsValue({ deptIds: this.plan.deptIds })
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
