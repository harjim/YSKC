<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    :maskClosable="false"
    @cancel="visible = false"
  >
    <a-spin :spinning="confirmLoading">
      <a-form @submit="handleSubmit" :form="form">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="客户名称"
              :labelCol="{ xs: { span: 24 },sm: { span:3}}"
              :wrapperCol="{ xs: { span: 24 },sm: { span: 19 }}"
            >
              <a-select
                :disabled="!isShowOrHide"
                showSearch
                v-decorator="['customerId', {rules:[{required: true, message: '请输入客户名称搜索'}]}]"
                placeholder="请输入客户名称搜索"
                style="width: 100%"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="getCustomer"
                @change="customerChange"
                :notFoundContent="null"
              >
                <a-select-option v-for="d in customerList" :key="d.id">{{ d.title }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="系统名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <!-- <a-input v-decorator="['platform', {rules:[{required: true, message: '请输入系统名称'}]}]" /> -->
              <a-select
                showSearch
                v-decorator="['platform', {rules:[{required: true, message: '请输入系统名称'}]}]"
                placeholder="请输入系统名称搜索"
                style="width: 100%"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="getPlatform"
                :notFoundContent="null"
              >
                <a-select-option v-for="d in platformList" :key="d.platform">{{ d.platform }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="网址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                v-decorator="['pUrl', {rules:[{required: true, message: '请输入网址'}]}]"
                placeholder="请输入网址搜索"
                style="width: 100%"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="getpUrl"
                :notFoundContent="null"
              >
                <a-select-option v-for="d in pUrlList" :key="d.pUrl">{{ d.pUrl }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="用户名">
              <a-input
                v-decorator="['username', {rules:[{required: true, message: '请输入用户名'}]}]"
                placeholder="请输入用户名"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['password', {rules:[{required: true, message: '请输入密码'}]}]"
                placeholder="请输入密码"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="备注"
              :labelCol="{ xs: { span: 24 },sm: { span:3}}"
              :wrapperCol="{ xs: { span: 24 },sm: { span: 19 }}"
            >
              <a-textarea v-decorator="['remark']" :rows="4"></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
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
      deptTree: [],
      isShowOrHide: false,
      visible: false,
      resultParam: {},
      customerList: [],
      platformList: [],
      pUrlList: [],
      isCreat: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      customerId: undefined
    }
  },
  methods: {
    add () {
      this.title = '添加客户账号'
      this.isCreat = true
      this.confirmLoading = false
      this.visible = true
      this.isShowOrHide = true
      this.form.resetFields()
      this.id = -1
    },
    edit (record) {
      this.customerId = record.customerId
      this.isCreat = false
      this.title = '修改客户账号[' + record.companyName + ']'
      this.form.resetFields()
      this.visible = true
      this.isShowOrHide = false
      this.id = record.id
      this.getCustomerMangetMent(record.id, record)
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.id
          if (!values.id || values.id <= 0) {
            this.$http.post('/customerAccount/insertCustomerAccount', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.customerId = this.customerId
            this.$http.post('/customerAccount/updateCustomerAccount', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
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
    getCustomerMangetMent (id, row) {
      this.$http.get('/customerAccount/getDataById', { params: { id: id } })
        .then(res => {
          this.form.resetFields()
          this.visible = true
          this.id = res.data.id
          res.data.customerId = row.companyName
          this.$nextTick(() => {
            this.$initForm(this.form, res.data)
          })
        })
    },
    getCustomer (value) {
      return this.$http.get('/customer/getCustomerSelect', { params: { customerName: value } })
        .then(res => {
          this.customerList = res.data
          return this.customerList
        })
    },
    customerChange (value) {
      this.customerId = value
    },
    getPlatform (value) {
      this.$nextTick(() => {
        this.form.setFieldsValue({ platform: value })
      })

      if (!this.checkInput(value)) {
        this.platformList = []
        return
      }
      const params = { platform: value }
      return this.$http.get('/customerAccount/getDataByPlatform', { params: params })
        .then(res => {
          let platformList
          if (res.data && res.data.length) {
            platformList = res.data
          } else {
            platformList = [params]
          }
          this.platformList = platformList
          return this.platformList
        })
    },
    getpUrl (value) {
      this.$nextTick(() => {
        this.form.setFieldsValue({ pUrl: value })
      })
      if (!this.checkInput(value)) {
        this.pUrlList = []
        return
      }
      const params = { pUrl: value }
      return this.$http.get('/customerAccount/getDataBypUrl', { params: params })
        .then(res => {
          let pUrlList
          if (res.data && res.data.length) {
            pUrlList = res.data
          } else {
            pUrlList = [params]
          }
          this.pUrlList = pUrlList
          return this.pUrlList
        })
    },
    checkInput (v) {
      return v && v.trim().length
    }
  }
}
</script>
