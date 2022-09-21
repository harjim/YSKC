<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-spin :spinning="confirmLoading">
      <a-form @submit="handleSubmit" :form="form">
        <a-card :bordered="false">
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="用户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  :disabled="!isCreat"
                  @blur="checkuserName"
                  placeholder="请输入用户名"
                  v-decorator="['userName', {rules:[{required: true, message: '请输入用户名'}]}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item
                v-show="isShowOrHide"
                label="密码"
                :labelCol="labelCol"
                :wrapperCol="wrapperCol"
              >
                <a-input
                  placeholder="请输入密码"
                  v-show="isShowOrHide"
                  v-decorator="['password', {rules: [{ required: true, message: '至少6位密码，区分大小写' }], validateTrigger: ['change', 'blur']}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="性别">
                <a-select showSearch v-decorator="['gender']" placeholder="请选择性别">
                  <a-select-option value="0">男</a-select-option>
                  <a-select-option value="1">女</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="姓名">
                <a-input
                  placeholder="请输入姓名"
                  v-decorator="['realName', {rules:[{required: true, message: '请输入姓名'}]}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select
                  placeholder="请选择状态"
                  showSearch
                  v-decorator="['status', {rules:[{required: true, message: '请选择状态'}]}]"
                >
                  <a-select-option value="0">启用</a-select-option>
                  <a-select-option value="1">禁用</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="电话">
                <a-input
                  placeholder="请输入电话"
                  v-decorator="['tel', {rules:[{required: true, message: '请输入电话',pattern: /^((0\d{3,4}-\d{7,8})|(1[3584]\d{9}))|(0\d{2,3}-?\d{7,8})|(\d{7,8})$/}]}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="邮箱">
                <a-input
                  placeholder="请输入邮箱"
                  v-decorator="['email', {rules:[{required: true, message: '请输入邮箱',pattern:/(\S)+[@]{1}(\S)+[.]{1}(\w)+/}]}]"
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
import { DeptSelect } from '@/components'
export default {
  components: {
    DeptSelect
  },
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      id: 0,
      checked: true,
      deptId: undefined,
      visible: false,
      isCreat: false,
      isShowOrHide: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add () {
      this.title = '添加子帐户'
      this.isCreat = true
      this.isShowOrHide = true
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.checked = true
      this.id = -1
    },
    edit (record) {
      this.checked = true
      this.isCreat = false
      this.isShowOrHide = false
      this.title = `编辑子帐户][${record.userName}]`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, record)
      })
    },
    handleSubmit () {
      if (!this.checked) {
        return
      }
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.deptId = this.deptId
          if (this.id === -1) {
            this.$http.post('/user/addChilduser', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加子账户失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.id = this.id
            this.$http.post('/user/updateChilduser', values)
              .finally(res => {
                this.confirmLoading = false
                this.visible = false
                this.$emit('ok', false)
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    deptSelected (deptId) {
      this.deptId = deptId
    },
    checkuserName () {
      const userName = this.form.getFieldValue('userName')
      if (userName) {
        this.$http.get('/user/checkuserName', { params: { userName: userName, id: this.id } })
          .then(res => {
            if (!res.data) {
              this.checked = false
              this.form.setFields({ 'userName': { value: userName, errors: [new Error('用户名已被使用，请输入新用户名。')] } })
            } else {
              this.checked = true
              this.form.setFields({ 'userName': { value: userName } })
            }
          })
      }
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
