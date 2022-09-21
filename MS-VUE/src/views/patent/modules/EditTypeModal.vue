<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="600"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="专利号"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >{{ project.patentNo }}</a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            label="专利名称"
          >{{ project.patentName }}</a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="申请人">
            <a-table
              bordered
              :dataSource="companyData"
              :pagination="false"
              size="small"
              rowKey="num"
            >
              <a-table-column
                align="left"
                title="申请人"
                data-index="applyName"
                key="applyName"
                :width="100"
              />
              <a-table-column
                align="left"
                title="地址"
                data-index="address"
                key="address"
                :width="100"
              />
            </a-table>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="关联客户" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <mini-select
              v-decorator="['companyId', {rules:[{required: true, message: '请输入客户名称搜索'}]}]"
              url="/customer/getCompanySelect"
              sKey="id"
              :sTitle="['title']"
              queryKey="customerName"
              ref="miniSelect"
              placeholder="输入客户名称搜索"
              @change="selCustomer"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import MiniSelect from '@/components/MiniSelect'
export default {
  components: {
    MiniSelect
  },
  data () {
    return {
      title: '',
      customerList: [],
      companyName: [],
      patentNo: undefined,
      companyData: [],
      project: {},
      customerId: undefined,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 19 }
      },
      visible: false,
      isCreat: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    edit (record) {
      this.isCreat = false
      this.title = `[${record.patentNo}]关联客户`
      this.visible = true
      this.id = record.id
      this.patentNo = record.patentNo
      this.project = record
      this.$nextTick(() => {
        this.$initForm(this.form, record)
        if (record.companyId) {
          this.companyId = record.companyId
          this.$refs.miniSelect.setValue(this.companyId, { id: this.companyId, title: record.companyName })
        } else {
          this.$refs.miniSelect.setValue()
        }
        this.customerList.push()
        this.initialize()
      })
    },
    selCustomer (value, row) {
      this.companyId = value
      this.form.setFieldsValue({ companyId: value })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.id
          values.patentNo = this.patentNo
          // values.companyId = this.companyId
          this.$http.post('/rsPatent/updatePatentCustomerId', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.confirmLoading = false
                this.$emit('ok', false)
              } else {
                this.$message.info(res.errorMessage)
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    initialize () {
      this.$nextTick(() => {
        this.$http.get('/rsPatent/getDataByPatentNo', { params: { patentNo: this.patentNo } })
          .then(res => {
            this.companyData = res.data
            var num = 0
            this.companyData.forEach(e => {
              e.num = ++num
              if (e.companyId === -1) {
                e.companyId = null
              }
            })
            return res.data
          })
      })
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
