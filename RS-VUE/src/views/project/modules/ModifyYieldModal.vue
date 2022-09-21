<template>
  <a-modal
    :title="title"
    centered
    :width="900"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    @cancel="closeModal"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="项目" :labelCol="{sm:{span: 3}}" :wrapperCol="{sm:{span: 19}}">
            <project-select
              :disabled="isEdit"
              style="width:100%"
              ref="creatProject"
              @getPrjectIds="projectSelected"
              :year="currentYear"
              :sign="2"
              :isJoinPrjectName="true"
              v-decorator="['projectId', { rules: [{ required: true, message: '请选择项目' }] }]"
            ></project-select>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="月份:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              @change="monthSelect"
              placeholder="请选择月份"
              :disabled="isEdit || trialProd"
              v-decorator="['month', { rules: [{ required: true, message: '请选择月份' }] }]"
            >
              <a-select-option v-for="item in monthOptions" :key="item.value" :value="item.value" :disabled="monthDisabled(item)">{{ item.label }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="部门:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入部门"
              :disabled="trialProd"
              v-decorator="['deptName', { rules: [{ required: true, message: '请输入部门' }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="总量:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入总量"
              :disabled="trialProd"
              v-decorator="['totalYield', { rules: [{ required: true, message: '请输入总量' }] }]"
              :precision="2"
              style="width: 100%"
              :max="$store.state.totalMax"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="单位:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入单位"
              :disabled="trialProd"
              v-decorator="['unit', { rules: [{ required: true, message: '请输入单位' }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="计划量" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入计划量"
              v-decorator="['planYield', { rules: [{ required: true, message: '请输入计划量' }] }]"
              :precision="2"
              :disabled="trialProd"
              style="width: 100%"
              :max="$store.state.totalMax"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="试制量:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入试制量"
              style="width: 100%"
              :disabled="trialProd"
              v-decorator="['rdYield', { rules: [{ required: true, message: '请输入试制量' }] }]"
              :max="getMax()"
              :precision="2"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="试制日期:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker
              style="width: 100%"
              v-decorator="['trialDate', { rules: [{ required: true, message: '请输入试制日期' }] }]"
              :disabled="trialProd"
              :disabledDate="disabledDate"
              :defaultPickerValue="getDefaultDateValue()"
            /></a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="试制品名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              placeholder="请输入试制品名"
              :disabled="trialProd"
              v-decorator="['trialProduct', { rules: [{ required: false, message: '请输入试制品名' }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="试制工时:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入试制工时"
              style="width: 100%"
              :disabled="trialProd"
              v-decorator="['trialHour', { rules: [{ required: true, message: '请输入试制工时' }] }]"
              :precision="2"
              :max="24"
              @change="computedRdHour"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="试制起止时间:" :labelCol="labelCol" :wrapperCol="wrapperCol" required>
            <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)', marginBottom: 0 }">
              <a-time-picker
                style="width: 100%"
                format="HH:mm"
                :disabled="trialProd"
                v-decorator="['startTime', { rules: [{ required: true, message: '请输入试制开始时间' }] }]"
                :disabledHours="() => disabledHours('startTime')"
                :disabledMinutes="h => disabledMinutes('startTime', h)"
              />
            </a-form-item>
            <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
              -
            </span>
            <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)', marginBottom: 0 }">
              <a-time-picker
                style="width: 100%"
                format="HH:mm"
                :disabled="trialProd"
                v-decorator="['endTime', { rules: [{ required: true, message: '请输入试制结束时间' }] }]"
                :disabledHours="() => disabledHours('endTime')"
                :disabledMinutes="h => disabledMinutes('endTime', h)"
              />
            </a-form-item>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="检验工时:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入检验工时"
              style="width: 100%"
              :disabled="trialProd"
              v-decorator="['testHour']"
              :precision="2"
              :max="getMaxHour()"
              @change="computedRdHour"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="检验起止时间:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)', marginBottom: 0 }">
              <a-time-picker
                style="width: 100%"
                format="HH:mm"
                :disabled="trialProd"
                v-decorator="['testStartTime', {rules:[{required: testTimeRequired, message: '请选择时间'}, { validator: validatorTestTime }]}]"
                :disabledHours="() => disabledHours('testStartTime')"
                :disabledMinutes="h => disabledMinutes('testStartTime', h)"
                @change="onTestTimeChange"
              />
            </a-form-item>
            <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
              -
            </span>
            <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)', marginBottom: 0 }">
              <a-time-picker
                style="width: 100%"
                format="HH:mm"
                :disabled="trialProd"
                v-decorator="['testEndTime', {rules:[{required: testTimeRequired, message: '请选择时间'}, { validator: validatorTestTime }]}]"
                :disabledHours="() => disabledHours('testEndTime')"
                :disabledMinutes="h => disabledMinutes('testEndTime', h)"
                @change="onTestTimeChange"
              />
            </a-form-item>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="研发工时:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              :disabled="true"
              placeholder="请输入研发工时"
              style="width: 100%"
              v-decorator="['rdHour']"
              :precision="2"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="运行工时:" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              placeholder="请输入运行工时"
              style="width: 100%"
              :disabled="trialProd"
              v-decorator="['totalHour', { rules: [{ required: true, message: '请输入运行工时' }] }]"
              :precision="2"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <a-alert v-if="trialProd" message="当前项目无试制或试制周期不存在当前年，请重新选择" banner />
  </a-modal>
</template>

<script>
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import ProjectSelect from '@/components/ProjectSelect'
// 每个时间框之前的时间
const beforeMap = { endTime: ['startTime'], testStartTime: ['endTime', 'startTime'], testEndTime: ['testStartTime', 'endTime', 'startTime'] }
// 每个时间框之后的时间
const afterMap = { startTime: ['endTime', 'testStartTime', 'testEndTime'], endTime: ['testStartTime', 'testEndTime'], testStartTime: ['testEndTime'] }
export default {
  mixins: [yearMiXin],
  components: {
    ProjectSelect
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      form: this.$form.createForm(this),
      visible: false,
      confirmLoading: false,
      id: undefined,
      title: '',
      isEdit: false,
      trialProd: false,
      currentMonth: undefined,
      project: undefined,
      minMonth: -1,
      maxMonth: 12,
      testTimeRequired: false
    }
  },
  methods: {
    monthSelect (v) {
      if (!v) {
        this.currentMonth = undefined
      } else {
        this.currentMonth = moment([this.currentYear, v, 1, 0, 0, 0, 0])
        const date = this.form.getFieldValue('trialDate')
        if (date && (this.currentMonth.year() !== date.year() || this.currentMonth.month() !== date.month())) {
          this.form.setFieldsValue({ trialDate: undefined })
        }
      }
    },
    getDefaultDateValue () {
      if (this.currentMonth) {
        return moment(this.currentMonth).startOf('month')
      }
    },
    projectSelected (ids, project) {
      this.project = project
      if (ids) {
        this.form.setFieldsValue({ projectId: ids })
        if (this.trialProdTrue()) {
          return
        }
        if (Number(this.project.tBeginDate.substr(0, 4)) < this.currentYear) {
          this.minMonth = -1
        } else {
          this.minMonth = this.moment(this.project.tBeginDate).month() - 1
        }
        if (Number(this.project.tEndDate.substr(0, 4)) > this.currentYear) {
          this.maxMonth = 12
        } else {
          this.maxMonth = this.moment(this.project.tEndDate).month() + 1
        }
      } else {
        this.form.setFieldsValue({ projectId: undefined })
      }
      this.form.setFieldsValue({ month: undefined, trialDate: undefined })
    },
    trialProdTrue () {
      this.trialProd = false
      if (!(this.project && this.project.trialProd && (this.project.tBeginDate && this.project.tEndDate && Number(this.project.tBeginDate.substr(0, 4)) <= this.currentYear && Number(this.project.tEndDate.substr(0, 4)) >= this.currentYear))) {
        this.trialProd = true
        return true
      }
      return false
    },
    add () {
      this.title = `添加`
      this.id = undefined
      this.currentMonth = undefined
      this.visible = true
      this.isEdit = false
      this.minMonth = -1
      this.maxMonth = 12
      this.project = undefined
      this.form.resetFields()
    },
    edit (row) {
      this.isEdit = true
      this.title = `编辑[${row.pname}]总量/试制量`
      this.id = row.id
      this.visible = true
      this.$refs.creatProject && this.$refs.creatProject.onChange(row.projectId)
      this.$nextTick(() => {
        this.form.resetFields()
        const startTime = row.startTime ? moment().format('YYYY-MM-DD') + ' ' + row.startTime : undefined
        const endTime = row.endTime ? moment().format('YYYY-MM-DD') + ' ' + row.endTime : undefined
        const testStartTime = row.testStartTime ? moment().format('YYYY-MM-DD') + ' ' + row.testStartTime : undefined
        const testEndTime = row.testEndTime ? moment().format('YYYY-MM-DD') + ' ' + row.testEndTime : undefined
        const month = moment(row.month + '-01')
        this.currentMonth = moment([month.year(), month.month(), 1, 0, 0, 0, 0])
        const record = {
          deptName: row.deptName,
          unit: row.unit,
          totalYield: row.totalYield,
          planYield: row.planYield,
          rdYield: row.rdYield,
          trialDate: row.trialDate ? moment(row.trialDate) : null,
          startTime: startTime ? moment(startTime) : undefined,
          endTime: endTime ? moment(endTime) : undefined,
          projectId: [row.projectId],
          month: month.month() + '',
          trialProduct: row.trialProduct,
          testHour: row.testHour,
          testStartTime: testStartTime ? moment(testStartTime) : undefined,
          testEndTime: testEndTime ? moment(testEndTime) : undefined,
          totalHour: row.totalHour,
          rdHour: row.rdHour,
          trialHour: row.trialHour
        }
        this.$initForm(this.form, record, ['startTime', 'endTime', 'trialDate', 'testStartTime', 'testEndTime'])
      })
    },
    getMax () {
      const max = Number(this.form.getFieldValue('totalYield'))
      if (max) {
        return max
      }
      return 0
    },
    getMaxHour () {
      const max = 24 - Number(this.form.getFieldValue('trialHour'))
      if (max) {
        return max
      }
      return 0
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      validateFields((errors, values) => {
        if (!errors) {
          if (values.startTime) {
            values.startTime = values.startTime.format('HH:mm')
          }
          if (values.endTime) {
            values.endTime = values.endTime.format('HH:mm')
          }
          if (values.testStartTime) {
            values.testStartTime = values.testStartTime.format('HH:mm')
          }
          if (values.testEndTime) {
            values.testEndTime = values.testEndTime.format('HH:mm')
          }
          if (Array.isArray(values.projectId)) {
            values.projectId = values.projectId[0]
          }
          values.month = moment([this.currentYear, values.month, 1, 0, 0, 0, 0])
          this.confirmLoading = true
          if (!this.id) {
            this.$http.post('/projectYieldConfig/add', values).then(res => {
              if (res.success && res.data) {
                this.$message.success('添加成功')
                this.closeModal()
                this.$emit('ok')
              } else {
                this.confirmLoading = false
                this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
              }
            })
          } else {
            values.id = this.id
            this.$http.post('/projectYieldConfig/edit', values).then(res => {
              if (res.success && res.data) {
                this.$message.success('更新成功')
                this.$emit('ok')
                this.closeModal()
              } else {
                this.confirmLoading = false
                this.$message.error(res.errorMessage ? res.errorMessage : '更新失败')
              }
            })
          }
        }
      })
    },
    closeModal () {
      this.id = undefined
      this.visible = false
      this.trialProd = false
      this.confirmLoading = false
      this.testTimeRequired = false
    },
    /**
     * 日期需要在当月的试制周期范围内
     * @param {moment} currentDate 需要判断的时间
     */
    disabledDate (currentDate) {
      const beginDate = moment.max(moment(this.currentMonth).add(-1, 'M').endOf('M'), moment(this.project && this.project.tBeginDate).add(-1, 'd'))
      const endDate = moment.min(moment(this.currentMonth).add(1, 'M').startOf('M'), moment(this.project && this.project.tEndDate).add(1, 'd'))
      return !((!this.project && !this.currentMonth) || currentDate.isBetween(beginDate, endDate))
    },
    monthDisabled (item) {
      const projectSelect = this.form.getFieldsValue(['projectId'])
      return projectSelect && (item.value <= this.minMonth || item.value >= this.maxMonth)
    },
    onTestTimeChange () {
      this.$nextTick(() => {
        const testStartTime = this.form.getFieldValue('testStartTime')
        const testEndTime = this.form.getFieldValue('testEndTime')
        if (Boolean(testStartTime) ^ Boolean(testEndTime)) {
          this.testTimeRequired = true
        } else {
          this.testTimeRequired = false
        }
        this.$nextTick(() => {
          this.form.validateFields(['testStartTime', 'testEndTime'], { force: true }, (errors) => {})
        })
      })
    },
    validatorTestTime (rule, value, callback) {
      this.$nextTick(() => {
        const { startTime, endTime, testStartTime, testEndTime } = this.form.getFieldsValue(['startTime', 'endTime', 'testStartTime', 'testEndTime'])
        if (startTime && endTime && testStartTime && testEndTime) {
          if ((testStartTime <= startTime && testEndTime <= startTime) || (testStartTime >= endTime && testEndTime >= endTime)) {
            return callback()
          } else {
            return callback(new Error('不能与试制起止时间重合'))
          }
        }
        return callback()
      })
    },
    computedRdHour () {
      this.$nextTick(() => {
        const { trialHour, testHour } = this.form.getFieldsValue(['trialHour', 'testHour'])
        this.form.setFieldsValue({ rdHour: (Number(trialHour) || 0) + (Number(testHour) || 0) })
        this.form.validateFields(['rdHour'], { force: true }, (errors) => {})
      })
    },
    // 禁用时间选择框 -- 时
    disabledHours (key) {
      const hours = []
      this.handlerDisabled(key, hours, true)
      return hours
    },
    // 禁用时间选择框 -- 分
    disabledMinutes (key, h) {
      const minutes = []
      this.handlerDisabled(key, minutes, false, h)
      return minutes
    },

    /**
     * 处理时间限制[调用层]
     * @param key 当前key
     * @param arr 限制数组
     * @param isTime true处理时间，false处理分钟
     * @param h isTime 为true时，传入当前hour
     */
    handlerDisabled (key, arr, isTime, h) {
      this.doHandlerDisabled(beforeMap[key], arr, false, isTime, h)
      this.doHandlerDisabled(afterMap[key], arr, true, isTime, h)
    },
    /**
     * 处理时间限制[执行代码]
     * 默认认为时间是有序的，取上一个时间及下一个时间做当前时间的限制
     * @param key 当前key
     * @param arr 限制数组
     * @param isTime true处理时间，false处理分钟
     * @param le false 处理小于当前时间的限制，true 处理大于当前时间的限制
     * @param h isTime 为true时，传入当前hour
     */
    doHandlerDisabled (keys, arr, le, isTime, h) {
      let time
      if (keys && keys.length) {
        for (const key of keys) {
          time = this.form.getFieldValue(key)
          if (time) {
            break
          }
        }
        if (isTime) {
          this.handlerTime(time, le, arr)
        } else {
          this.handlerMinutes(time, le, arr, h)
        }
      }
    },
    /*
    * 处理小时限制
    */
    handlerTime (time, le, arrs) {
      if (time) {
        const hour = time.toDate().getHours()
        if (le) {
          for (let i = hour + 1; i < 24; i++) {
            arrs.push(i)
          }
        } else {
          for (let i = hour - 1; i >= 0; i--) {
            arrs.push(i)
          }
        }
      }
    },
    /**
     * 处理分钟限制
     */
    handlerMinutes (time, le, arr, h) {
      if (time) {
        const minute = time.toDate().getMinutes()
        if (h === time.toDate().getHours()) {
          if (le) {
            for (let i = minute + 1; i < 60; i++) {
              arr.push(i)
            }
          } else {
            for (let i = minute - 1; i >= 0; i--) {
              arr.push(i)
            }
          }
        }
      }
    }
  }
}
</script>

<style></style>
