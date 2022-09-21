<!--
 * @Author: zdf
 * @Date: 2022-04-14 15:00:54
 * @LastEditTime: 2022-04-19 18:47:09
 * @LastEditors: zdf
 * @Description: 研发试制计划
 * @FilePath: \RS-VUE\src\views\project\modules\RdTrialAggModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="650"
    v-model="visible"
    :maskClosable="false"
    :footer="null"
    @ok="handleSubmit"
    @cancel="closeModal">
    <a-form :form="form">
      <a-row :gutter="24">
        <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择月份"
            v-decorator="['month', { rules: [{ required: true, message: '请选择月份' }] }]"
            :options="monthOptions"
          >
          </a-select>
        </a-form-item>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="归集方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-radio-group v-decorator="['aggType', { rules: [{ required: true, message: '请选择归集方式' }],initialValue:'0' }]" @change="aggTypeChange">
              <a-radio value="0">研发工时</a-radio>
              <a-radio value="1">试制量</a-radio>
            </a-radio-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="归集类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-checkbox-group v-decorator="['rdTypes', { rules: [{ required: true, message: '请选择归集类型' }] }]" @change="rdTypeChange">
              <a-checkbox :disabled="disabledMap[item.value]" v-for="item in curAggs" :key="item.value" :value="item.value">{{ item.title }}</a-checkbox>
            </a-checkbox-group>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24" v-if="aggType === '0'">
        <a-col :span="24">
          <a-form-item label="生成工时记录" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-checkbox-group v-decorator="['hours', { rules: [{ required: false }] }]">
              <a-checkbox value="employeeHour">人员工时</a-checkbox>
              <a-checkbox value="equipmentHour">设备工时</a-checkbox>
            </a-checkbox-group>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <a-alert message="当前操作会清空原归集费用及研发工时记录，请谨慎操作！" type="warning" style="margin:0 0 24px 0;" banner/>
    <template v-if="msgs.length">
      <a-alert
        v-for="(m,index) in msgs"
        :key="index"
        :message="m.msg"
        :type="m.type"
        style="margin:0 0 24px 0;"
        closable/>
    </template>
    <div
      style="text-align: right;margin-top:10px"
    >
      <a-button
        style="margin-right:20px"
        @click="closeModal"
      >关闭</a-button>
      <a-popconfirm title="您确定要归集吗?" @confirm="handleSubmit">
        <a-button
          type="primary"
          :loading="confirmLoading"
        >归集</a-button>
      </a-popconfirm>

    </div>
  </a-modal>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
const mutexMap = { '20302': ['20100'], '20100': ['20302'] }
const aggMap = { '0': [{ value: '10000', title: '辅助人员' }, { value: '30000', title: '设备折旧' },
  { value: '20100', title: '研发动力' }, { value: '20302', title: '试制动力' }, { value: '20200', title: '研发燃料' }],
'1': [ { value: '20102', title: '流程动力' }, { value: '20201', title: '流程燃料' } ] }
export default {
  mixins: [yearMiXin],
  data () {
    return {
      aggMap,
      mutexMap,
      aggType: '0',
      labelCol: {
        span: 4
      },
      wrapperCol: {
        span: 19
      },
      title: '',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      msgs: [],
      disabledMap: {}
    }
  },
  computed: {
    curAggs () {
      return this.aggMap[this.aggType]
    }
  },
  methods: {
    show () {
      this.title = `研发试制计划归集[${this.currentYear}]研发费用`
      this.visible = true
      this.form.resetFields()
    },
    handleSubmit () {
      this.confirmLoading = true
      this.msgs = []
      this.form.validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        values.month = Number(values.month) + 1
        const params = { aggType: values.aggType, rdTypes: values.rdTypes, month: `${this.currentYear}-${values.month < 10 ? '0' + values.month : values.month}-01 00:00:00` }
        if (values.hours && values.hours.length) {
          values.hours.forEach(item => { params[item] = true })
        }

        this.$http.post('/projectYieldConfig/aggFee', params)
          .then(res => {
            const msgs = []
            if (res.success && res.data) {
              if (res.data.length) {
                res.data.forEach(item => {
                  msgs.push({ msg: item.msg, type: item.success ? 'success' : 'error' })
                })
              }
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : '归集失败')
            }
            this.msgs = msgs
          }).finally(() => {
            this.confirmLoading = false
          })
      })
    },
    aggTypeChange (e) {
      this.aggType = e.target.value
      this.disabledMap = {}
      this.form.setFields({ 'rdTypes': { value: undefined } })
    },
    rdTypeChange (values) {
      const disabledMap = {}
      values.forEach(v => {
        if (this.mutexMap[v]) {
          this.mutexMap[v].forEach(k => { disabledMap[k] = true })
        }
      })
      this.disabledMap = disabledMap
    },
    closeModal () {
      this.aggType = '0'
      this.disabledMap = []
      this.msgs = []
      this.visible = false
      this.confirmLoading = false
    }
  }
}
</script>

<style>

</style>
