<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="800"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-table
        size="middle"
        :columns="columns"
        bordered
        rowKey="id"
        :pagination="false"
        :dataSource="dataSource"
      >
        <template slot="endowment" slot-scope="text,record">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              v-decorator="['endowment'+record.pix, {rules:[{required: true, message: '请输入比例'}],initialValue:text}]"
              :min="0"
              :max="100"
              key="endowment"
              @change="e => handleChange(e, record, 'endowment')"
            />
          </a-form-item>
        </template>
        <template slot="medical" slot-scope="text,record">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              v-decorator="['medical'+record.pix, {rules:[{required: true, message: '请输入比例'}],initialValue:text}]"
              :min="0"
              :max="100"
              key="medical"
              @change="e => handleChange(e, record, 'medical')"
            />
          </a-form-item>
        </template>
        <template slot="unemployment" slot-scope="text,record">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              v-decorator="['unemployment'+record.pix, {rules:[{required: true, message: '请输入比例'}],initialValue:text}]"
              :min="0"
              :max="100"
              key="unemployment"
              @change="e => handleChange(e, record, 'unemployment')"
            />
          </a-form-item>
        </template>
        <template slot="injury" slot-scope="text,record">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              v-decorator="['injury'+record.pix, {rules:[{required: true, message: '请输入比例'}],initialValue:text}]"
              :min="0"
              :max="100"
              key="injury"
              @change="e => handleChange(e, record, 'injury')"
            />
          </a-form-item>
        </template>
        <template slot="maternity" slot-scope="text,record">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              v-decorator="['maternity'+record.pix, {rules:[{required: true, message: '请输入比例'}],initialValue:text}]"
              :min="0"
              :max="100"
              key="maternity"
              @change="e => handleChange(e, record, 'maternity')"
            />
          </a-form-item>
        </template>
        <template slot="house" slot-scope="text,record">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              v-decorator="['house'+record.pix, {rules:[{required: true, message: '请输入比例'}],initialValue:text}]"
              :min="0"
              :max="100"
              key="house"
              @change="e => handleChange(e, record, 'house')"
            />
          </a-form-item>
        </template>
      </a-table>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'SettingInsuranceConfigModal',
  deptTree: [],
  data () {
    return {
      dataSource: [],
      columns: [{
        align: 'right',
        title: '',
        dataIndex: 'type'
      }, {
        align: 'center',
        title: '养老保险',
        width: '100px',
        dataIndex: 'endowment',
        scopedSlots: { customRender: 'endowment' }
      }, {
        align: 'center',
        title: '医疗保险',
        width: '100px',
        dataIndex: 'medical',
        scopedSlots: { customRender: 'medical' }
      }, {
        align: 'center',
        title: '失业保险',
        width: '100px',
        scopedSlots: { customRender: 'unemployment' },
        dataIndex: 'unemployment'
      }, {
        align: 'center',
        title: '工伤保险',
        width: '100px',
        scopedSlots: { customRender: 'injury' },
        dataIndex: 'injury'
      }, {
        align: 'center',
        title: '生育保险',
        width: '100px',
        scopedSlots: { customRender: 'maternity' },
        dataIndex: 'maternity'
      }, {
        align: 'center',
        title: '公积金',
        width: '100px',
        scopedSlots: { customRender: 'house' },
        dataIndex: 'house'
      }],
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 24 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 24 }
      },
      id: 0,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      entityList: {}
    }
  },
  mounted () {
  },
  methods: {
    handleChange (value, record, column) {
      record[column] = value
    },
    getDataSource () {
      var data = []
      data.push({
        id: 1,
        pix: '',
        type: '个人',
        endowment: 0,
        medical: 0,
        unemployment: 0,
        injury: 0,
        maternity: 0,
        house: 0
      })
      data.push({
        id: 2,
        pix: 'OfCom',
        type: '公司',
        endowment: 0,
        medical: 0,
        unemployment: 0,
        injury: 0,
        maternity: 0,
        house: 0
      })
      return data
    },
    add (record) {
      this.title = '调整五险一金比例'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = 0
      this.entityList = record
      this.$http.get('/insuranceConfig/getInsuranceConfig', { params: {} })
        .then(res => {
          this.dataSource = this.getDataSource()
          this.dataSource[0].endowment = res.data.endowment
          this.dataSource[0].medical = res.data.medical
          this.dataSource[0].unemployment = res.data.unemployment
          this.dataSource[0].injury = res.data.injury
          this.dataSource[0].house = res.data.house
          this.dataSource[0].maternity = res.data.maternity
          this.dataSource[1].maternity = res.data.maternityOfCom
          this.dataSource[1].endowment = res.data.endowmentOfCom
          this.dataSource[1].medical = res.data.medicalOfCom
          this.dataSource[1].unemployment = res.data.unemploymentOfCom
          this.dataSource[1].injury = res.data.injuryOfCom
          this.dataSource[1].house = res.data.houseOfCom
          return res.data
        })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      if (this.entityList.length === 0) {
        this.$message.info('请选择需要设置的数据')
        this.confirmLoading = false
        return
      }
      validateFields((errors, values) => {
        if (!errors) {
          values.salaryIds = this.entityList.map(a => { return a.id })
          this.$http.post('/insuranceConfig/set', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '设置失败')
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>

<style scoped>
</style>
