<template>
  <a-modal
    :width="500"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="visible = false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="文档名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                :disabled="isEdit"
                placeholder="请输入文档名称"
                v-decorator="['docName', {rules:[{required: true, message: '请输入文档名称'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="研发活动" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                :disabled="isEdit"
                placeholder="请输入研发活动"
                v-decorator="['rdActivities']" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="负责岗位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入负责岗位" v-decorator="['operators']" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>

export default {
  components: {
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
      confirmLoading: false,
      visible: false,
      form: this.$form.createForm(this),
      listType: 0,
      seq: 0,
      isEdit: false,
      oldRow: {},
      rowIndex: -1
    }
  },
  methods: {
    add (listType, length) {
      this.form.resetFields()
      this.visible = true
      this.id = 0
      this.listType = listType
      this.seq = length + 1
      this.isEdit = false
      this.oldRow = undefined
    },
    edit (row, rowIndex) {
      this.rowIndex = rowIndex
      this.oldRow = row
      this.isEdit = !row.companyId
      this.id = row.id
      this.visible = true
      this.$nextTick(() => {
        this.$initForm(this.form, row)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        values.listType = this.listType
        values.seq = this.seq
        if (!errors) {
          if (this.oldRow) {
            if (this.oldRow.operators === values.operators && this.oldRow.docName === values.docName && this.oldRow.rdActivities === values.rdActivities) {
              this.visible = false
              this.confirmLoading = false
              return
            }
            this.$http.post('/docList/editOperators', Object.assign(values, { id: this.id, companyId: this.oldRow.companyId })).then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('editOk', { rowIndex: this.rowIndex, operators: values.operators, docName: values.docName, rdActivities: values.rdActivities })
                this.$message.success('更新成功')
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
              }
              this.confirmLoading = false
            })
          } else {
            this.$http.post('/docList/addDocList', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          }
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
</style>
