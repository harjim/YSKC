<template>
  <a-modal
    :width="640"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="closeModal"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
  >
    <a-form
      @submit="handleSubmit"
      :form="form"
    >
      <a-form-item
        label="角色名称"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-input
          v-decorator="['roleName', {rules:[{required: true, message: '请输入角色名称'}]}]"
          placeholder="请输入角色名称"
        />
      </a-form-item>
      <a-form-item
        label="公司名称"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <mini-select
          :disabled="isEdit"
          style="width:100%"
          placeholder="请输入公司名称查询"
          url="/rsRole/getCompany"
          sKey="id"
          queryKey="companyName"
          ref="miniCompany"
          @change="(v,row)=>v ? companyId = v : companyId = 0"
        />
      </a-form-item>
      <a-form-item
        label="人员类型"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-select
          @change="(v)=>selectChange(v,'mType')"
          :allowClear="true"
          placeholder="请选择人员类型"
          v-decorator="['mType', {rules:[{required: hasRequired, message: '请选择人员类型'}]}]"
        >
          <a-select-option value="1">技术人员</a-select-option>
          <a-select-option value="2">财务人员</a-select-option>
          <a-select-option value="3">业务员</a-select-option>
          <a-select-option value="4">谈单经理</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item
        label="项目类型"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-select
          :allowClear="true"
          placeholder="请选择项目类型"
          @change="(v)=>selectChange(v,'productType')"
          v-decorator="['productType', {rules:[{required: hasRequired, message: '请选择项目类型'}]}]"
        >
          <a-select-option value="0">投资项目</a-select-option>
          <a-select-option value="1">创新项目</a-select-option>
          <a-select-option value="2">高新项目</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item
        label="描述"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol"
      >
        <a-textarea v-decorator="['remark']"></a-textarea>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import MiniSelect from '@/components/MiniSelect'
export default {
  components: {
    MiniSelect
  },
  name: 'CompanyRoleModal',
  data () {
    return {
      hasRequired: false,
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      id: 0,
      visible: false,
      confirmLoading: false,
      companyName: '',
      companyId: 0,
      isEdit: true,
      form: this.$form.createForm(this),
      selectMap: {}
    }
  },
  methods: {
    selectChange (v, key) {
      this.selectMap[key] = v
      var required = false
      for (const k in this.selectMap) {
        if (this.selectMap[k]) {
          required = true
          break
        }
      }
      this.hasRequired = required
      if (!required) {
        this.$nextTick(() => {
          for (const k in this.selectMap) {
            const obj = {}
            obj[k] = { value: this.selectMap[k] }
            this.form.setFields(obj)
          }
        })
      }
    },
    add () {
      this.isEdit = false
      this.companyName = ''
      this.companyId = 0
      this.title = '添加角色'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = -1
      this.$nextTick(() => {
        if (this.$refs.miniCompany) {
          this.$refs.miniCompany.setValue(null, null)
        }
      })
    },
    edit (record) {
      if (record.mType || (record.productType !== undefined && record.productType !== null)) {
        this.selectMap = { mType: record.mType, productType: record.productType }
        this.hasRequired = true
      } else {
        record.mType = undefined
        record.productType = undefined
      }
      this.isEdit = true
      this.title = `修改角色[${record.companyName === null ? '公共角色' : record.companyName}][${record.roleName}]`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.companyId = record.companyId
      this.$nextTick(() => {
        if (this.$refs.miniCompany) {
          this.$refs.miniCompany.setValue(record.companyName)
        }
        this.$initForm(this.form, record)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        values.companyId = this.companyId
        if (!errors) {
          if (this.id === -1) {
            this.$http.post('/rsRole/add', values)
              .then(res => {
                if (res.success && res.data) {
                  this.closeModal()
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.id = this.id
            this.$http.post('/rsRole/update', values)
              .then(res => {
                if (res.success && res.data) {
                  this.closeModal()
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
    closeModal () {
      this.visible = false
      this.hasRequired = false
    }
  }
}
</script>
