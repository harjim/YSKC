<template>
  <a-modal
    :title="title"
    style="top:20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              placeholder="请选择类型"
              style="width:100%"
              :disabled="isDisabled"
              @change="typeChange"
              v-decorator="['type', { rules: [{ required: true, message: '请输入类型' }] }]"
            >
              <a-select-option v-for="(v, k) in types" :key="k" :value="k">{{ v }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="父节点" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              show-search
              :showArrow="false"
              :default-active-first-option="false"
              :filter-option="false"
              :not-found-content="null"
              @change="getParentKey"
              @search="getParentKey"
              placeholder="请选择父节点"
              style="width:100%"
              v-decorator="['parentKey', { rules: [{ required: false, message: '请输入父节点' }] }]"
            >
              <a-select-option v-for="parentKey in parentKeys" :key="parentKey.key" :value="parentKey.key">
                {{ parentKey.value }}
                ({{ parentKey.key }})
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="键" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入键"
              v-decorator="['key', { rules: [{ required: true, message: '请输入键' }] }]"
            ></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="值" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入值"
              v-decorator="['kValue', { rules: [{ required: true, message: '请输入值' }] }]"
            ></a-input>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="排序" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              :min="0"
              placeholder="请输入排序"
              v-decorator="['order', { rules: [{ required: false, message: '请输入排序' }] }]"
            >
            </a-input-number>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :span="48">
          <a-form-item label="备注" :labelCol="{ sm: { span: 3 } }" :wrapperCol="{ sm: { span: 20 } }">
            <a-input
              placeholder="请输入备注"
              v-decorator="['remark', { rules: [{ required: false, message: '请输入备注' }] }]"
            ></a-input>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'ContractTraceability',
  data () {
    return {
      types: this.$getDictionaryTypes(),
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      title: '',
      parentKeys: [],
      visible: false,
      confirmLoading: false,
      id: 0,
      form: this.$form.createForm(this),
      currentQrPath: '',
      loading: false,
      headers: { 'Access-Token': '' },
      checked: true,
      isDisabled: false

      // selectCustomer: {}
    }
  },
  created () {},
  methods: {
    typeChange () {
      this.form.setFieldsValue({ parentKey: undefined })
      this.parentKeys = []
    },
    add () {
      this.isDisabled = false
      // this.selectCustomer = {}
      this.checked = true
      this.title = '新建字典数据'
      this.confirmLoading = false
      this.form.resetFields()
      this.parentKeys = []
      this.visible = true
      this.id = 0
    },
    edit (record) {
      if (Number(record.parentKey)) {
        this.parentKeys = [{ value: record.parentKeyValue, key: record.parentKey }]
      } else {
        this.parentKeys = []
        record.parentKey = undefined
      }
      this.isDisabled = true
      this.checked = true
      this.title = '修改字典数据'
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      const row = { ...record }
      row.kValue = row.value
      this.$nextTick(() => {
        this.$initForm(this.form, row)
      })
    },
    handleSubmit () {
      if (!this.checked) {
        return
      }
      const {
        form: { validateFields }
      } = this
      validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          values.value = values.kValue
          if (!values.parentKey) {
            values.parentKey = 0
          }
          delete values.kValue
          if (this.id === 0) {
            this.$http.post('/sysDictionary/insert', values).then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', true)
                this.form.resetFields()
              } else {
                this.$message.error(res.errorMessage)
              }
              this.confirmLoading = false
            })
          } else {
            values.id = this.id
            this.$http.post('/sysDictionary/edit', values).then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', false)
              } else {
                this.$message.error(res.errorMessage)
              }
              this.confirmLoading = false
            })
          }
        }
      })
    },
    getParentKey (key) {
      const type = this.form.getFieldValue('type')
      if (!type || !key || !key.trim()) {
        this.parentKeys = []
        return
      }
      this.$http.get('/sysDictionary/findKey', { params: { type, key } }).then(res => {
        if (res.success && res.data) {
          this.parentKeys = res.data
        } else {
          this.parentKeys = []
        }
      })
    }
  }
}
</script>

<style scoped></style>
