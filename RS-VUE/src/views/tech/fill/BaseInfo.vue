<template>
  <a-card>
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="项目所属行业">
        <a-input
          placeholder="项目所属行业"
          v-decorator="['industryCode', {rules:[{required: true, message: '请输入项目所属行业代码'}]}]"
        />
        <a target="_blank" href="/template/国民经济行业分类(GBT 4754—2017).pdf">国民经济行业分类(GBT 4754—2017).pdf</a>
      </a-form-item>

      <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="项目备案号">
        <a-input
          v-decorator="['recordNumber', {rules:[{required: true, message: '请输入项目备案号'}]}]"
          placeholder="项目备案号"
        />
      </a-form-item>

      <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="项目属于">
        <a-checkbox-group
          v-decorator="['projectTypes', {rules:[{required: true, message: '请勾选项目属于'}]}]"
          :options="plainOptions"
          @change="onChange"
        />
      </a-form-item>

      <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="建设性质">
        <a-radio-group
          v-decorator="['conssRuctionType', {rules:[{required: true, message: '请勾选建设性质'}]}]"
          :options="radioPlainOptions"
          @change="onRadioChange"
        />
      </a-form-item>

      <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="节水改造技术领域">
        <a-checkbox-group
          v-decorator="['technicalFields', {rules:[{required: true, message: '请勾选节水改造技术领域'}]}]"
          :options="plainWaterOptions"
          @change="onwaterChange"
        />
      </a-form-item>

      <a-form-item
        v-if="showReason"
        :label-col="labelCol"
        :wrapper-col="wrapperCol"
        label="不涉及理由"
        help="如无涉及节水改造，请说明理由"
      >
        <a-textarea v-decorator="['notInvolveRemark']"></a-textarea>
      </a-form-item>

      <a-form-item :label-col="labelCol" :wrapper-col="wrapperCol" label="项目建设主要内容">
        <a-textarea
          v-decorator="['mainContents', {rules:[{required: true, message: '请填写项目建设主要内容'}]}]"
        ></a-textarea>
      </a-form-item>
      <a-form-item :wrapper-col="buttonItemLayout.wrapperCol">
        <a-button type="primary" html-type="submit" :loading="saveLoading">提交</a-button>
      </a-form-item>
    </a-form>
  </a-card>
</template>
<script>
const plainWaterOptions = [
  { label: '节水工艺', value: '1' },
  { label: '节水技术', value: '2' },
  { label: '节水装备', value: '3' },
  { label: '不涉及', value: '4' }
]
const plainOptions = [
  { label: '智能制造', value: '1' },
  { label: '绿色制造', value: '2' },
  { label: '服务制造', value: '3' },
  { label: '时尚制造', value: '4' },
  { label: '安全制造', value: '5' },
  { label: '其他', value: '6' }
]
const radioPlainOptions = [
  { label: '新建', value: '1' },
  { label: '改建', value: '2' },
  { label: '扩建', value: '3' }
]
export default {
  computed: {
    buttonItemLayout () {
      const { formLayout } = this
      return formLayout === 'horizontal' ? {
        wrapperCol: { span: 12, offset: 12 }
      } : {}
    }
  },
  props: {
    projectId: {
      type: Number,
      default: 0
    },
    year: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      saveLoading: false,
      form: this.$form.createForm(this),
      formLayout: 'horizontal',
      showReason: false,
      waterCheckedList: [],
      checkedList: [],
      plainOptions,
      plainWaterOptions,
      radioPlainOptions,
      itemData: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 12 }
      }
    }
  },
  watch: {
    projectId (newId) {
      this.loadData(newId)
    },
    waterCheckedList (list) {
      if (list.includes('4')) {
        this.showReason = true
        const { form: { setFieldsValue } } = this
        this.$nextTick(() => {
          setFieldsValue({ notInvolveRemark: this.itemData.notInvolveRemark })
        })
      } else {
        this.showReason = false
      }
    }
  },
  created () {
    this.loadData(this.projectId)
  },
  methods: {
    loadData (projectId) {
      this.$http.get('/techProject/getTechProjectBasic', { params: { projectId: projectId } })
        .then(res => {
          const { form: { setFieldsValue } } = this
          this.form.resetFields()
          this.waterCheckedList = []
          setFieldsValue({ technicalFields: [], projectTypes: [] })
          if (res.success && res.data) {
            this.$nextTick(() => {
              this.itemData = {}
              this.itemData = { ...res.data }
              if (res.data.projectType) {
                this.itemData['projectTypes'] = res.data.projectType.split(',')
              }
              if (res.data.technicalField) {
                this.itemData['technicalFields'] = res.data.technicalField.split(',')
                this.waterCheckedList = res.data.technicalField.split(',')
              }
              this.$initForm(this.form, this.itemData)
            })
          } else {
            this.itemData = { projectId: this.projectId }
          }
        }).catch(res => {
          this.itemData = {}
          this.waterCheckedList = []
          const { form: { setFieldsValue } } = this
          this.form.resetFields()
          setFieldsValue({ technicalFields: [], projectTypes: [] })
        })
    },
    handleSubmit (e) {
      this.saveLoading = true
      e.preventDefault()
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          values.technicalField = values.technicalFields.join(',')
          values.projectType = values.projectTypes.join(',')
          values.id = this.itemData.id
          values.projectId = this.projectId
          this.$http.post('/techProject/saveProjectBasic', values)
            .then(res => {
              if (res.success && res.data) {
                this.$message.success('保存成功')
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
              }
            }).finally(res => {
              this.saveLoading = false
            })
        }
      })
    },
    onChange (checkedList) {
    },
    onRadioChange () {

    },
    onwaterChange (values) {
      this.waterCheckedList = values
    }
  }
}
</script>
