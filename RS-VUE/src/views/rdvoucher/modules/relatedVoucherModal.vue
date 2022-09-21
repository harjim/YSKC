<template>
  <a-modal
    :width="450"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    :afterClose="afterClose"
    @cancel="visible = false"
  >
    <a-form ref="form" :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="关联项目"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            style="margin-bottom: 10px;">
            <project-select
              :selected="projectIds"
              :isMul="true"
              :year="year"
              :sign="2"
              width="100%"
              @getPrjectIds="getPrjectIds"
              v-decorator="['verifyProjects',{ rules: [{ required: true, message: '请选择费用类型' }] }]"/>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="费用类型"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol">
            <a-select :allowClear="true" :filter-option="filterOption" placeholder="请选择费用类型" v-decorator="['rdType',{ rules: [{ required: true, message: '请选择费用类型' }] }]">
              <a-select-option v-for="item in costTypes" :key="item.type">{{ item.title }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import moment from 'moment'
import ProjectSelect from '@/components/ProjectSelect'
export default {
  components: {
    ProjectSelect
  },
  props: {
    year: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      title: '关联项目',
      visible: false,
      ids: [],
      projectIds: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 19 }
      },
      form: this.$form.createForm(this),
      costTypes: this.$getCostTypes()
    }
  },
  methods: {
    moment,
    show (ids) {
      this.visible = true
      this.ids = ids
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          values.ids = this.ids
          values.projectIds = this.projectIds
          this.$http.post('/voucher/relatedVoucher', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', true, false) // param1 成功 param2 是否当前刷新
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '关联项目失败')
              }
            })
        }
      })
    },
    getPrjectIds (values) {
      this.projectIds = values
      let obj = {}
      if (values.length !== 0) {
        obj = { verifyProjects: values }
      } else {
        obj = { verifyProjects: undefined }
      }
      this.form.setFieldsValue(obj)
    },
    afterClose () {
      this.form.resetFields()
      this.ids = []
      this.projectIds = undefined
    },
    filterOption (input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      )
    }
  }
}
</script>

<style>
</style>
