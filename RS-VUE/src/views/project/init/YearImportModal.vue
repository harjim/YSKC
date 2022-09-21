<template>
  <a-modal
    :title="title"
    :width="480"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form @submit="handleSubmit" :form="form">
        <a-row :gutter="24">
          <a-col :span="24" v-if="years.length">
            <a-form-item label="年份" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="['sourceYear',{rules:[{required: true, message: '请选择年份'}]}]"
                placeholder="请选择年份">
                <a-select-option v-for="y in years" :key="y" :value="y">{{ y }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="24" v-else>
            <a-alert :message="`该项目其他年份不存在${initName}，不可引入。`" type="info" show-icon style="color: red; margin: 15px  0px;"/>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
  name: 'YearImportModal',
  props: {
    initName: {
      type: String,
      default: '项目成员'
    },
    url: {
      type: String,
      required: true
    },
    queryUrl: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      currentYear: undefined,
      projectId: undefined,
      years: [],
      spinning: false
    }
  },
  mounted () {
  },
  methods: {
    open (year, projectId) {
      this.currentYear = year
      this.projectId = projectId
      this.title = `按年引入[${this.initName}]`
      this.confirmLoading = false
      this.form.resetFields()
      this.loadYears()
      this.visible = true
    },
    loadYears () {
      this.confirmLoading = true
      this.spinning = true
      this.$http.get(this.queryUrl, { params: { year: this.currentYear, projectId: this.projectId } }).then(res => {
        if (res.success) {
          if (res.data) {
            this.years = res.data
            return
          }
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : `获取${this.initName}存在的年份失败。`)
        }
        this.years = []
      }).finally(() => {
        this.confirmLoading = false
        this.spinning = false
      })
    },
    handleSubmit () {
      if (!this.years.length) {
        this.$emit('ok', true)
        this.visible = false
        return
      }
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.targetYear = this.currentYear
          values.projectId = this.projectId
          this.$http.post(this.url, values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$message.success('引入成功')
                this.$emit('done')
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '引入失败')
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
