<template>
  <a-modal
    :width="900"
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
          <a-col :span="12">
            <a-form-item label="计划试制量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                placeholder="请输入计划试制量"
                v-decorator="['planPO', {rules:[{required: true, message: '请输入计划试制量'}]}]"
                :min="0"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="实际试制量" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number placeholder="请输入实际试制量" v-decorator="['actualPO']" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入试制量单位" v-decorator="['unit']" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="地点" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入地点" v-decorator="['place']" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="试制日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                style="width:100%"
                format="YYYY-MM-DD"
                placeholder="请选择试制日期"
                v-decorator="['trialDate']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="试制机台号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入试制机台号" v-decorator="['pos']" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-time-picker
                format="HH:mm"
                style="width:100%"
                :disabledHours="()=>disabledHours('endTime',true)"
                :disabledMinutes="(h) => disabledMinutes('endTime',true,h)"
                v-decorator="['startTime']"
                placeholder="请选择开始时间"
                @openChange="checkTime('startTime','endTime',true)"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="结束时间">
              <a-time-picker
                format="HH:mm"
                style="width:100%"
                :disabledHours="()=>disabledHours('startTime',false)"
                :disabledMinutes="(h) => disabledMinutes('startTime',false,h)"
                v-decorator="['endTime']"
                placeholder="请选择结束时间"
                @openChange="checkTime('endTime','startTime',false)"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="试制班组" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input placeholder="请输入试制班组" v-decorator="['trialGroup']" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="主材消耗" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number placeholder="请输入主材消耗超正常损耗比" v-decorator="['mainMaterial']" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="辅材消耗" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number placeholder="请输入辅材消耗超正常损耗比" v-decorator="['auxMaterial']" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="燃料消耗" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number placeholder="请输入燃料消耗超正常损耗比" v-decorator="['fuel']" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="动力消耗" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number placeholder="请输入动力消耗超正常损耗比" v-decorator="['power']" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="气体" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number placeholder="请输入气体消耗超正常损耗比" v-decorator="['gas']" :min="0" />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="预计备品备件(万元)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number placeholder="请输入预计备品备件" v-decorator="['spare']" :min="0" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>
<script>
import moment from 'moment'

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
      confirmLoading: false,
      projectId: 0,
      visible: false,
      form: this.$form.createForm(this),
      submitState: false,
      beginDate: null,
      endDate: null,
      projectStart: null
    }
  },
  methods: {
    moment,
    add (projectData) {
      this.title = `添加[${projectData.pname}]项目试制`
      this.beginDate = moment(projectData.tBeginDate)
      this.endDate = moment(projectData.tEndDate)
      this.projectStart = moment(projectData.beginDate)
      this.form.resetFields()
      this.id = 0
      this.projectId = projectData.id
      this.visible = true
      this.confirmLoading = false
      this.$nextTick(() => {
        this.$initForm(this.form, { beginDate: moment(projectData.beginDate) })
      })
    },
    edit (record, projectData) {
      this.title = `编辑[${projectData.pname}]项目试制`
      this.projectId = record.projectId
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      record['startTime'] = !record['startTime'] ? null : moment(record['startTime'], 'HH:mm:ss')
      record['endTime'] = !record['endTime'] ? null : moment(record['endTime'], 'HH:mm:ss')
      this.$nextTick(() => {
        this.$initForm(this.form, record, record['trialDate'] ? ['trialDate'] : undefined)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.id
          values.projectId = this.projectId
          if (values.id === 0) {
            this.$http.post('/trialProd/addTrial', values)
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
          } else {
            this.$http.post('/trialProd/editTrial', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', false)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
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
    // 禁用时间选择框 -- 时
    disabledHours (key, le) {
      const hours = []
      const time = this.form.getFieldValue(key)
      if (time) {
        const hour = time.toDate().getHours()
        if (le) {
          for (let i = hour + 1; i < 24; i++) {
            hours.push(i)
          }
        } else {
          for (let i = hour - 1; i >= 0; i--) {
            hours.push(i)
          }
        }
      }
      return hours
    },
    disabledMinutes (key, le, h) {
      const minutes = []
      const time = this.form.getFieldValue(key)
      if (time) {
        const minute = time.toDate().getMinutes()
        if (h === time.toDate().getHours()) {
          if (le) {
            for (let i = minute + 1; i < 60; i++) {
              minutes.push(i)
            }
          } else {
            for (let i = minute - 1; i >= 0; i--) {
              minutes.push(i)
            }
          }
        }
      }
      return minutes
    },
    checkTime (currentKey, otherKey, le) {
      const other = this.form.getFieldValue(otherKey)
      if (other) {
        const current = this.form.getFieldValue(currentKey)
        if (current) {
          const o = other.toDate()
          const c = current.toDate()
          if (o.getHours() === c.getHours()) {
            if (le) {
              if (o.getMinutes() < c.getMinutes()) {
                this.setTimeVal(current, o, currentKey)
              }
            } else {
              if (o.getMinutes() > c.getMinutes()) {
                this.setTimeVal(current, o, currentKey)
              }
            }
          }
        }
      }
    },
    setTimeVal (current, o, currentKey) {
      current.minutes(o.getMinutes())
      const temp = {}
      temp[currentKey] = current
      this.form.setFieldsValue(temp)
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
</style>
