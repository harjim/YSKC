<template>
  <a-modal
    :title="title"
    :visible="visible"
    @ok="handleSubmit"
    width="300px"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @cancel="visible = false"
  >
    <a-form :form="form">
      <a-form-item
        :label-col="{ span: 0 }"
        :wrapper-col="{ span: 24 }"
      >
        <a-input
          v-decorator="['alias',{rules: [{ required: true, message: '请输入名称' }]}]"
          placeholder="请输入名称"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      confirmLoading: false,
      title: '',
      form: this.$form.createForm(this),
      currentCol: '',
      fieldConfig: undefined,
      updated: false,
      type: 0
    }
  },
  methods: {
    addCol (fieldConfig, type) {
      this.type = type
      this.updated = false
      this.fieldConfig = fieldConfig
      this.title = `添加明细名称`
      this.visible = true
      this.$nextTick(() => {
        this.form.resetFields()
        if (!this.fieldConfig) {
          this.fieldConfig = {}
        }
        if (!this.fieldConfig.number) {
          this.fieldConfig.number = 1
        } else {
          this.fieldConfig.number++
        }
      })
    },
    editCol (fieldConfig, col, type) {
      this.updated = true
      this.type = type
      this.title = `编辑明细名称[${col.alias}]`
      this.visible = true
      this.$nextTick(() => {
        this.fieldConfig = fieldConfig
        this.currentCol = col
        this.form.setFieldsValue({ alias: col.alias })
      })
    },
    handleSubmit () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          var params = {}
          params.type = this.type
          params.id = this.fieldConfig.id
          params.number = this.fieldConfig.number
          const crtArr = this.fieldConfig.config
          if (this.updated && params.id) {
            for (let i = 0; i < crtArr.length; i++) {
              const c = crtArr[i]
              if (c.alias === values.alias && this.currentCol.name !== c.name) {
                this.$message.error(`当前配置已存在名称[${values.alias}],请输入其他名称`)
                this.confirmLoading = false
                return
              }
            }
            crtArr.forEach(c => {
              if (c.name === this.currentCol.name) {
                c.alias = values.alias.trim()
              }
            })
            params.config = JSON.stringify(crtArr)
            this.$http.post('/fieldConfig/editFieldConfigCol', params)
              .then(res => {
                if (res.success && res.data) {
                  this.$emit('ok')
                  this.$message.success('更新成功')
                  this.visible = false
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
                }
              }).finally(() => {
                this.confirmLoading = false
              })
          } else {
            for (let i = 0; i < crtArr.length; i++) {
              const c = crtArr[i]
              if (c.alias === values.alias) {
                this.$message.error(`当前配置已存在名称[${values.alias}],请输入其他名称`)
                this.confirmLoading = false
                return
              }
            }
            crtArr.push({ name: `col${this.fieldConfig.number}`, alias: values.alias.trim() })
            params.config = JSON.stringify(crtArr)
            this.$http.post('/fieldConfig/addFieldConfigCol', params)
              .then(res => {
                if (res.success && res.data) {
                  this.$emit('ok')
                  this.$message.success('添加成功')
                  this.visible = false
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(() => {
                this.confirmLoading = false
              })
          }
        }
      })
    }
  }
}
</script>

<style>
</style>
