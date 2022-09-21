<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    :maskClosable="false"
    v-model="visible"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上级科目">
        <select-down
          treeType="account"
          ref="accountSelect"
          @select="accountSelected"
          placeholder="请选择上级科目"
          v-decorator="['parentId',{rules:[{required: true, message: '请选择上级科目'}]}]"
        />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="科目编码">
        <a-input
          :disabled="!isCreat"
          v-decorator="['accountNumber', {rules:[{required: true, message: '请输入科目编码'}]}]"
        />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="科目名称">
        <a-input v-decorator="['accountName', {rules:[{required: true, message: '请输入科目名称'}]}]" />
      </a-form-item>

      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="科目类别">
        <a-select v-decorator="['accoutType', {rules:[{required: true, message: '请选择科目类别'}]}]">
          <a-select-option value="0">资产类</a-select-option>
          <a-select-option value="1">负债类</a-select-option>
          <a-select-option value="2">权益类</a-select-option>
          <a-select-option value="3">成本类</a-select-option>
          <a-select-option value="4">损益类</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import SelectDown from '@/components/SelectDown'
export default {
  components: {
    SelectDown
  },
  name: 'AccountModal',
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      visible: false,
      confirmLoading: false,
      id: 0,
      title: '',
      isCreat: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    add (record) {
      this.title = '添加下级科目'
      const { form: { setFieldsValue } } = this
      this.confirmLoading = false
      this.form.resetFields()
      this.id = -1
      this.visible = true
      this.isCreat = true
      this.$nextTick(() => {
        setFieldsValue({ parentId: record.id.toString() })
        this.$refs.accountSelect.setValue(record.id)
      })
    },
    adds () {
      this.title = '添加科目'
      this.confirmLoading = false
      this.visible = true
      this.isCreat = true
      this.form.resetFields()
      this.$nextTick(() => {
        this.$refs.accountSelect.setValue(0)
      })
      this.id = -1
    },
    edit (record) {
      this.title = `编辑科目[${record.accountName}]`
      this.isCreat = true
      const { form: { setFieldsValue } } = this
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        setFieldsValue({ parentId: record.parentId.toString(), accountNumber: record.accountNumber, accountName: record.accountName, accoutType: record.accoutType.toString() })
        this.$refs.accountSelect.setValue(record.parentId)
      })
    },
    close () {
      this.visible = false
    },
    handleCancel () {
      this.close()
    },
    onChange (value) {
      this.value = value
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === -1) {
            this.$http.post('/accountTitle/addAccountTitle', { parentId: values.parentId, accountNumber: values.accountNumber, accountName: values.accountName, accoutType: values.accoutType })
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                  this.$getTree('account', true).then(res => {
                    this.$refs.accountSelect.loadTree()
                  })
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              })
          } else {
            if (Number(values.parentId) === Number(this.id)) {
              this.$message.info('上级科目不能是当前编辑科目本身')
              this.confirmLoading = false
              return
            }
            this.$http.post('/accountTitle/updateAccountTitle', { parentId: values.parentId, accountNumber: values.accountNumber, accountName: values.accountName, accoutType: values.accoutType, id: this.id })
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', false)
                  this.$getTree('account', true).then(res => {
                    this.$refs.accountSelect.loadTree()
                  })
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
                }
              })
          }
        }
        this.confirmLoading = false
      })
    },
    accountSelected (accountId) {
      this.form.setFieldsValue({ parentId: accountId })
    }
  }
}
</script>

<style scoped>
</style>
