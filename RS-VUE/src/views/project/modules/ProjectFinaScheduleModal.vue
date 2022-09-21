<!--
 * @Author: zdf
 * @Date: 2022-02-24 16:04:17
 * @LastEditTime: 2022-02-25 08:40:47
 * @LastEditors: zdf
 * @Description: 研发实际工时modal
 * @FilePath: \RS-VUE\src\views\project\modules\ProjectFinaScheduleModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="620"
    v-model="visible"
    :maskClosable="false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :afterClose="closeModal"
  >
    <a-form :form="form">
      <a-row>
        <a-form-item label="项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <project-select
            style="width:100%"
            :disabled="isEdit"
            ref="creatProject"
            :year="currentYear"
            @getPrjectIds="projectSelected"
            :sign="2"
            :isJoinPrjectName="true"
            v-decorator="['projectId', { rules: [{ required: true, message: '请选择项目' }] }]"
          ></project-select>
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item label="月份:" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择月份"
            :disabled="isEdit"
            v-decorator="['month', { rules: [{ required: true, message: '请选择月份' }] }]"
          >
            <a-select-option v-for="item in monthOptions" :key="item.value" :value="item.value" :disabled="item.value <= minMonth || item.value >= maxMonth">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="请输入部门"
            v-decorator="['deptName', { rules: [{ required: true, message: '请输入部门' }] }]"
          />
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item label="总运行工时" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            placeholder="请输入总运行工时"
            v-decorator="['workHours', { rules: [{ required: false, message: '请输入总运行工时' }] }]"
            :precision="2"
            style="width: 100%"
            :max="$store.state.totalMax"
            :min="0"
          />
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item label="试验实际工时" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            placeholder="请输入试验实际工时"
            v-decorator="['testHour', { rules: [{ required: false, message: '请输入试验实际工时' }] }]"
            :precision="2"
            style="width: 100%"
            :max="$store.state.totalMax"
            :min="0"
          />
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item label="试制实际工时" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number
            placeholder="请输入试制实际工时"
            v-decorator="['trialHour', { rules: [{ required: false, message: '请输入试制实际工时' }] }]"
            :precision="2"
            style="width: 100%"
            :max="$store.state.totalMax"
            :min="0"
          />
        </a-form-item>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>

import yearMiXin from '@/utils/yearMixin'
import ProjectSelect from '@/components/ProjectSelect'
import moment from 'moment'
export default {
  components: {
    ProjectSelect
  },
  mixins: [yearMiXin],
  data () {
    return {
      minMonth: -1,
      maxMonth: 12,
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 19 }
      },
      isEdit: false,
      visible: false,
      title: '',
      confirmLoading: false,
      id: undefined
    }
  },
  methods: {
    add () {
      this.title = `添加研发实际工时`
      this.id = undefined
      this.form.resetFields()
      this.visible = true
      this.isEdit = false
    },
    edit (row) {
      this.id = row.id
      this.isEdit = true
      this.title = `编辑[${row.rdTitle}-${row.month}${row.deptName ? '-' + row.deptName : ''}]研发实际工时`
      this.visible = true
      this.$nextTick(() => {
        this.form.resetFields()
        const month = moment(row.month + '-01')
        const record = {
          deptName: row.deptName,
          projectId: [row.projectId],
          month: month.month() + '',
          workHours: row.workHours,
          testHour: row.testHour,
          trialHour: row.trialHour
        }
        this.$initForm(this.form, record)
      })
    },
    handleSubmit () {
      this.confirmLoading = true
      this.form.validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        if (!values.workHours && !values.testHour && !values.trialHour) {
          this.$message.warning('请至少输入[总运行工时、试验实际工时、试制实际工时]其中一个。')
          this.confirmLoading = false
          return
        }
        if (Array.isArray(values.projectId)) {
          values.projectId = values.projectId[0]
        }
        values.id = this.id
        values.month = moment([this.currentYear, values.month, 1, 0, 0, 0, 0])
        this.$http.post(this.id ? '/projectFinaSchedule/edit' : '/projectFinaSchedule/add', values).then(res => {
          if (res.success && res.data) {
            this.$message.success(this.id ? '更新成功' : '添加成功')
            this.$emit('ok')
            this.closeModal()
          } else {
            this.$message.error(res.errorMessage || '操作失败')
            this.confirmLoading = false
          }
        })
      })
    },
    closeModal () {
      this.id = undefined
      this.visible = false
      this.confirmLoading = false
    },
    projectSelected (ids, project) {
      this.project = project
      if (ids) {
        this.form.setFieldsValue({ projectId: ids })
        if (this.project.beginYear < this.currentYear) {
          this.minMonth = -1
        } else {
          this.minMonth = moment(this.project.beginDate).month() - 1
        }
        if (this.project.endYear > this.currentYear) {
          this.maxMonth = 12
        } else {
          this.maxMonth = moment(this.project.endDate).month() + 1
        }
      } else {
        this.form.setFieldsValue({ projectId: undefined })
      }
      this.form.setFieldsValue({ month: undefined })
    }
  }
}
</script>

<style>

</style>
