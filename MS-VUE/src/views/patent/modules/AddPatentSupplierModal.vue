<!--
    * @Author:
    * @Date: 2022-09-03 10:58:35
    * @Description:
-->
<template>
  <a-modal
    :visible="visible"
    :title="`${isAdd ? '添加' : '编辑'}专利供应商信息`"
    width="600px"
    centered
    @ok="handleSubmit"
    @cancel="closeModal"
    :confirmLoading="loading"
  >
    <a-form layout="inline" :form="form">
      <a-row class="rowLine">
        <a-col :span="12">
          <a-form-item label="供应商" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入供应商"
              v-decorator="['supplier', {rules: [{required: true, message: '请输入供应商'}, {max: 100, message: '供应商长度不能大于100'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入户名"
              v-decorator="['accountName', {rules: [{required: true, message: '请输入户名'}, {max: 100, message: '户名长度不能大于100'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row class="rowLine">
        <a-col :span="12">
          <a-form-item label="账号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入账号"
              v-decorator="['accountNumber', {rules: [{required: true, message: '请输入账号'}, {max: 80, message: '账号长度不能大于80'}]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入联系人"
              v-decorator="['linkman', {rules: [{required: true, message: '请输入联系人'}, {max: 80, message: '联系人长度不能大于80'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row class="rowLine">
        <a-col :span="12">
          <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入联系电话"
              v-decorator="['linkTel', {rules: [{required: true, message: '请输入联系电话'}, {max: 80, message: '联系电话长度不能大于80'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row class="rowLine">
        <a-col :span="24">
          <a-form-item label="备注">
            <a-textarea
              :autoSize="{minRows: 4, maxRows: 4}"
              placeholder="请输入备注"
              v-decorator="['remark', {rules: [{max: 200, message: '备注的最大长度不能大于200'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
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
      form: this.$form.createForm(this),
      loading: false,
      visible: false,
      supplierInfo: null,
      title: null
    }
  },
  computed: {
    isAdd () {
      return this.supplierInfo === null
    }
  },
  methods: {
    open (info) {
      this.supplierInfo = info
      this.visible = true
      this.form.resetFields()
      if (!this.isAdd) {
        this.$nextTick(() => {
          this.$initForm(this.form, this.supplierInfo)
        })
      }
    },
    closeModal () {
      this.visible = false
    },
    handleSubmit () {
      this.form.validateFields((error, values) => {
        if (!error) {
          this.loading = true
          const url = this.isAdd ? 'addSupplier' : 'updateSupplier'
          values.id = this.supplierInfo && this.supplierInfo.id
          this.$http.post('/patentSupplier/' + url, values).then(res => {
            if (res.success) {
              this.$message.success('保存成功')
              this.$emit('refresh', this.isAdd)
              this.closeModal()
            } else {
              this.$message.error(res.errorMessage || '保存失败，请稍后重试')
            }
          }).catch(res => {
            this.$message.error(res.errorMessage || '系统异常，请联系管理员')
          }).finally(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style lang='less' scoped>
/deep/ .ant-form-inline .ant-form-item > .ant-form-item-label {
  width: 80px;
}

/deep/ .ant-row:last-child .ant-form-item {
  width: 96%;
  display: flex;
}

/deep/ .ant-form-item-control-wrapper {
  flex: 1;
}

/deep/ .rowLine {
  padding-bottom: 20px;
}

/deep/ .ant-form-explain {
  position: absolute;
  bottom: -14px;
}

/deep/ .ant-form-item {
  margin-bottom: 0;
}
</style>
