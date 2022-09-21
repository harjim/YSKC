<!-- 企业产品管理--添加产品 -->
<template>
  <a-modal
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    :visible="visible"
    :width="800"
    destroyOnClose
    title="添加产品"
    @cancel="visible = false"
    @ok="handleSubmit"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form" @submit="handleSubmit">
        <a-card :bordered="false">
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品名称">
                <a-input
                  v-decorator="['pname', {rules:[{required: true, message: '请输入产品名称'}]}]"
                  placeholder="请输入产品名称"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品编码">
                <a-input
                  v-decorator="['pcode', {rules:[{required: true, message: '请输入产品编码'}, { validator: pcodeExist}], validateTrigger: ['blur', 'submit'] }]"
                  placeholder="请输入产品编码"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品创建时间">
                <a-date-picker
                  v-decorator="['creationDate', {rules:[{required: true, message: '请输入产品创建时间'}]}]"
                  placeholder="请输入产品创建时间"
                  :disabled-date="(current) => current > moment().endOf('day')"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="产品型号">
                <a-input
                  v-decorator="['model', {rules: [{ required: true, message: '请输入产品型号' }], validateTrigger: ['change', 'blur']}]"
                  placeholder="请输入产品型号"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
                label="单位">
                <a-input
                  v-decorator="['unit', {rules:[{required: true, message: '请输入产品单位'}]}]"
                  placeholder="请输入单位"
                />
              </a-form-item>
            </a-col>
          </a-row>
        </a-card>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import moment from 'moment'
export default {
  data () {
    return {
      labelCol: {
        xs: { span: 14 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 10 },
        sm: { span: 16 }
      },
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    moment,
    showModal () {
      this.visible = true
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          this.$http.post('/product/add', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.confirmLoading = false
              } else {
                this.$message.error(res.errorMessage)
              }
            }).finally(() => {
              this.confirmLoading = false
              this.$emit('tableRefresh', true)
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    pcodeExist (rule, value, callback) {
      if (value) {
        this.$http.get('/product/checkPcode', { params: { pcode: value } }).then(res => {
          if (res.success) {
            if (res.data) {
              callback()
            } else {
              callback(new Error('产品编码已存在,请重新输入'))
            }
          } else {
            callback(new Error(res.errorMessage))
          }
        })
      } else {
        callback()
      }
    }
  }
}
</script>

<style lang="less" scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}

/deep/ .ant-col-sm-6 {
  width: 32%;
}
</style>
