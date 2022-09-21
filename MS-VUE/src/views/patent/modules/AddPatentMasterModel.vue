<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="600"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :afterClose="afterClose"
  >
    <a-form @submit="handleSubmit" :form="form" v-bind="{ labelCol, wrapperCol }">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="名称">
            <a-input
              :disabled="isDisabled"
              v-decorator="['masterName', {rules:[{required: true, message: '请输入名称', whitespace: true },{ validator: verifyMasterName }]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item
            :labelCol="{ xs: { span: 24 },sm: { span:8}}"
            :wrapperCol="{ xs: { span: 24 },sm: { span: 16 }}"
            label="电话">
            <a-input v-decorator="['linkTel', {rules:[{required: true, message: '请输入电话'}]}]" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="邮箱">
            <a-input v-decorator="['linkEmail']" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="地址">
            <a-input v-decorator="['address']" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="流程负责人">
            <search-select
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              :multiple="false"
              placeholder="请输入流程负责人"
              v-decorator="['userId', {rules:[{required: true, message: '请输入流程负责人'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="备注">
            <a-textarea v-decorator="['remark']" :rows="4"></a-textarea>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import { checkMasterName } from '@/api/patent/patent'
import { SearchSelect } from '@/components/Selects'

export default {
  components: {
    SearchSelect
  },
  data () {
    return {
      title: '',
      isDisabled: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 20 }
      },
      visible: false,
      confirmLoading: false,
      id: 0,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add () {
      this.title = '添加负责人'
      this.form.resetFields()
      this.visible = true
      this.id = 0
    },
    edit (record) {
      this.id = record.id
      this.title = '修改负责人'
      this.visible = true
      this.form.resetFields()
      this.isDisabled = true
      this.$nextTick(() => {
        this.$initForm(this.form, { ...record, userId: { id: record.userId, realName: record.userName } })
      })
    },
    verifyMasterName (rule, value, callback) {
      if (this.isDisabled) {
        callback()
        return
      }
      if (value) {
        checkMasterName({ masterName: value }).then(data => {
          if (data) {
            callback()
          } else {
            callback(new Error(`${value}已存在，不可重复！`))
          }
        }).catch(error => {
          callback(new Error(error.message || '系统异常，验证唯一失败，请联系管理员！'))
        })
      } else {
        callback()
      }
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.userId = values.userId.id
          if (this.id === 0) {
            this.$http.post('/patentMaster/add', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                  this.form.resetFields()
                } else {
                  this.$message.info(res.errorMessage)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.id = this.id
            this.$http.post('/patentMaster/update', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', false)
                } else {
                  this.$message.error(res.errorMessage)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    afterClose () {
      this.isDisabled = false
      this.form.resetFields()
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
