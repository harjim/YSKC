<template>
  <a-modal
    :width="550"
    :visible="visible"
    :title="title"
    destroyOnClose
    @ok="handleSubmit"
    @cancel="close()"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-spin :spinning="confirmLoading">
      <a-form @submit="handleSubmit" :form="form">
        <a-card :bordered="false">
          <a-row :gutter="24">
            <a-col>
              <a-form-item label="跟进类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-radio-group
                  v-decorator="['status', { rules: [{ required: true, message: '必须选择' }], initialValue: 10 }]"
                >
                  <a-radio :value="10"> 邀约</a-radio>
                  <a-radio :value="20"> 拜访</a-radio>
                </a-radio-group>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col>
              <a-form-item label="跟进情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-textarea
                  :auto-size="{ minRows: 3}"
                  v-decorator="[
                    'info',
                    { rules: [{ required: true, message: '跟进情况不可为空' }], validateTrigger: ['change'] },
                  ]"
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
import { Ellipsis, STable } from '@/components'

export default {
  components: {
    STable,
    Ellipsis
  },
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 6 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 25 },
        sm: { span: 20 }
      },
      id: 0,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      customerId: null,
      checked: true,
      status: 10
    }
  },
  props: {
    unsigned: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    open (companyName, customerId) {
      this.title = companyName === '' ? '跟进' : '跟进 [' + companyName + ']'
      this.confirmLoading = false
      this.visible = true
      this.customerId = customerId
    },
    handleSubmit () {
      if (!this.checked) {
        return
      }
      const {
        form: { validateFields }
      } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.customerId = this.customerId
          const url = this.unsigned ? '/customer/followUpList' : '/customer/followUp'
          this.$http
            .post(url, values)
            .then((res) => {
              if (res.success && res.data) {
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', true)
                this.form.resetFields()
                this.$message.success('提交成功')
              } else {
                this.$message.error(res.errorMessage)
              }
            })
            .finally((res) => {
              this.confirmLoading = false
              this.$emit('refresh', false)
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    close () {
      this.form.resetFields()
      this.visible = false
    }
  }
}
</script>

<style lang="less" scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}

/deep/ .ant-col-sm-20 {
  width: 74%;
}

/deep/ .ant-col-sm-6 {
  width: 19%;
}
</style>
