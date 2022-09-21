<template>
  <a-card :bordered="false">
    <a-form @submit="handleSubmit" :form="form">
      <a-card>
        <a-row :gutter="48">
          <a-col :md="24" :sm="24">
            <span>企业需提供市工业和信息化局或委托的第三方机构进行业务调研的项目实施地具体地址：</span>
          </a-col>
        </a-row>
        <a-card>
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="所在地" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-cascader
                  :fieldNames="{label:'value',value:'key',children:'children'}"
                  v-decorator="['addressCode', {rules:[{required: true, message: '请选择所在地'}]}]"
                  :options="addressCode"
                  placeholder="请选择所在地"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  v-decorator="['address', {rules:[{required: true, message: '请输入地址'}]}]"
                  style="width:800px"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
        <a-row :gutter="48">
          <a-col :md="10" :sm="24">
            <span>企业需提供如下内容摘要</span>
          </a-col>
        </a-row>
        <a-card>
          <a-row :gutter="48">
            <a-col :md="24" :sm="24">
              <a-form-item
                label="单位简介及建设依据（限300字以内）"
                :help="()=>{const r = form.getFieldValue('synopsis'); return `(${!r? 0 : r.length > 300 ? 300 : r.length}/300)`}"
              >
                <a-textarea
                  rows="6"
                  maxlength="300"
                  v-decorator="['synopsis', {rules:[{required: true, message: '请输入建设依据'}]}]"
                ></a-textarea>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="48">
            <a-col :md="24" :sm="24">
              <a-form-item
                label="主要建设内容和目标（限300字以内）"
                :help="()=>{const r = form.getFieldValue('targetAndContent'); return `(${!r? 0 : r.length > 300 ? 300 : r.length}/300)`}"
              >
                <a-textarea
                  rows="6"
                  maxlength="300"
                  v-decorator="['targetAndContent', {rules:[{required: true, message: '请输入内容和目标'}]}]"
                ></a-textarea>
              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
      </a-card>
    </a-form>
    <div class="table-operator">
      <a-button type="primary" @click="handleSubmit">提交</a-button>
    </div>
  </a-card>
</template>

<script>
export default {
  name: 'OtherInfo',
  props: {
    projectId: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      form: this.$form.createForm(this),
      id: 0,
      addressCode: [],
      address: '',
      synopsis: '',
      targetAndContent: ''
    }
  },
  watch: {
    projectId (newId) {
      this.initialize()
    }
  },
  created () {
    this.initialize()
    this.$addressCode(this).then(res => {
      this.addressCode = res
    })
  },
  methods: {
    handleSubmit () {
      const self = this
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = self.id
          values.projectId = self.projectId
          this.$http.post('/projectOther/saveOther', values)
            .then(res => {
              if (res.success && res.data) {
                this.id = res.data
                this.visible = false
                this.$emit('ok', true)
                this.$message.success('保存成功')
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    initialize () {
      this.$nextTick(() => {
        this.$http.get('/projectOther/queryAll', { params: { projectId: this.projectId } })
          .then(res => {
            this.form.resetFields()
            const result = res.data
            if (result !== null) {
              this.id = result.id
              this.$initForm(this.form, result)
            }
            this.loading = false
            return result
          })
      })
    }
  }

}
</script>
