<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="上级工艺线/车间">
        <select-down
          treeType="workshop"
          ref="workshopSelect"
          placeholder="请选择上级工艺线/车间"
          v-decorator="['parentId',{rules:[{required: true, message: '请选择上级工艺线/车间'}]}]"
          @select="changePId"
        />
      </a-form-item>
      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="序号">
        <a-input
          v-decorator="['seq', {rules:[{required: true, message: '请输入序号'}]}]"
          :disabled="parentId===undefined"
          @blur="o=>checkSeq(o)"
        />
      </a-form-item>

      <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="工艺线名称">
        <a-input v-decorator="['workshopName', {rules:[{required: true, message: '请输入工艺线名称'}]}]" />
      </a-form-item>
      <a-form-item
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
        label="工艺描述"
        :help="()=>{const r = form.getFieldValue('remark'); return `(${!r? 0 : r.length > 300 ? 300 : r.length}/300)`}"
      >
        <a-textarea :rows="5" v-decorator="['remark']" maxlength="300" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import SelectDown from '@/components/SelectDown'
export default {
  name: 'WorkshopModal',
  components: {
    SelectDown
  },
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
      form: this.$form.createForm(this),
      parentId: undefined,
      submitState: false,
      oldSeq: undefined
    }
  },
  methods: {
    add (record) {
      this.title = '添加下级工艺线/车间'
      const { form: { setFieldsValue } } = this
      this.confirmLoading = false
      this.form.resetFields()
      this.id = -1
      this.parentId = record.id
      this.changePId(this.parentId)
      this.visible = true
      this.$nextTick(() => {
        setFieldsValue({ parentId: record.id.toString() })
        this.$refs.workshopSelect.setValue(this.parentId)
      })
    },
    adds () {
      this.title = '添加工艺线/车间'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
      this.$nextTick(() => {
        this.$refs.workshopSelect.setValue(0)
      })
    },
    edit (record) {
      this.oldSeq = undefined
      this.title = `编辑科目[${record.workshopName}]`
      const { form: { setFieldsValue } } = this
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.parentId = record.parentId
      this.oldSeq = record.seq
      this.$nextTick(() => {
        setFieldsValue({ parentId: record.parentId.toString(), seq: record.seq, workshopName: record.workshopName, remark: record.remark })
        this.$nextTick(() => {
          if (this.$refs.workshopSelect) {
            this.$refs.workshopSelect.setValue(this.parentId)
          }
        })
      })
    },
    handleSubmit () {
      if (this.submitState) {
        return
      }
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === -1) {
            this.$http.post('/workshop/addWorkshop', { parentId: values.parentId, workshopName: values.workshopName, remark: values.remark, seq: values.seq })
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                  this.$getTree('workshop', true).then(res => {
                    this.$refs.workshopSelect.loadTree()
                  })
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              })
          } else {
            if (Number(values.parentId) === Number(this.id)) {
              this.$message.info('上级工艺线/车间不能为本身')
              this.confirmLoading = false
              return
            }
            this.$http.post('/workshop/updateWorkshop', { parentId: values.parentId, workshopName: values.workshopName, remark: values.remark, id: this.id, seq: values.seq })
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', false)
                  this.$getTree('workshop', true).then(res => {
                    this.$refs.workshopSelect.loadTree()
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
    changePId (pid) {
      this.parentId = pid
      return this.$http.get('/workshop/selectMaxSeq', { params: { parentId: this.parentId } })
        .then(res => {
          if (res.data) {
            this.$initForm(this.form, { seq: res.data })
          } else {
            this.$initForm(this.form, { seq: 1 })
          }
          return res.data
        }).finally(res => {
          this.form.setFieldsValue({ parentId: pid })
        })
    },
    checkSeq (o) {
      if (this.oldSeq + '' !== o.target.value) {
        return this.$http.get('/workshop/checkSeq', { params: { seq: o.target.value, parentId: this.parentId } })
          .then(res => {
            if (!res.data) {
              this.form.setFields({ 'seq': { value: o.target.value, errors: [new Error('已存在相同的序号,请重新输入。')] } })
              this.submitState = true
            } else {
              this.form.setFields({ 'seq': { value: o.target.value } })
              this.submitState = false
            }
            return res.data
          })
      }
    }
  }
}
</script>

<style scoped>
</style>
