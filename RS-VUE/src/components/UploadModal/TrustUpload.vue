
<template>
  <a-modal
    :title="title"
    :width="width"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :afterClose="afterClose"
    :confirmLoading="confirmLoading"
  >
    <a-spin :tip="tip" :spinning="spin" >
      <div class="clearfix">
        <a-form :form="form">
          <a-form-item
            label="资料类型"
            :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
            :wrapperCol="{xs: { span: 24 },sm: { span: 20 } }"
            v-if="isAppendixForm"
          >
            <a-select
              placeholder="请选择资料类型"
              v-decorator="['stage', {rules:[{required: true, message: '请选择资料类型'}]}]">
              <a-select-option
                v-for="item in projectStageTypes"
                :key="item"
                :title="item"
                :label="item"
              >{{ item }}</a-select-option>
            </a-select>
          </a-form-item>
          <a-form-item
            label="记录日期"
            :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
            :wrapperCol="{xs: { span: 24 },sm: { span: 20 } }"
            v-if="!isTrustOrgForm"
          >
            <a-date-picker
              placeholder="请选择日期"
              :defaultPickerValue="defaultPickerValue()"
              v-decorator="['uploadTime', {rules:[{required: true, message: '请选择日期'}]}]"
            />
          </a-form-item>
          <a-form-item
            label="文件名"
            :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
            :wrapperCol="{xs: { span: 24 },sm: { span: 20 } }"
          >
            <a-input
              placeholder="请输入文件名"
              v-decorator="['fileName', {rules:[{required: true, message: '请输入文件名'}]}]"
              @change="documentNameChange"
            />
          </a-form-item>

          <a-form-item
            label="附件:"
            :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
            :wrapperCol="{xs: { span: 24 },sm: { span: 20 } }"
          >
            <a-col :span="18">
              <a-input
                placeholder="请上传附件"
                v-decorator="['name', { rules:[{required: true, message: '请上传附件'}] }]"
                :disabled="true"
              />
            </a-col>
            <a-col :span="2">
              <a-upload
                :accept="accept"
                :fileList="fileList"
                :multiple="false"
                :beforeUpload="beforeUpload"
                @change="handleChange"
                v-decorator="['file']"
              >
                <a-button>
                  <a-icon type="upload" />选择
                </a-button>
              </a-upload>
            </a-col>
          </a-form-item>

          <a-form-item
            label="备注"
            :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
            :wrapperCol="{xs: { span: 24 },sm: { span: 20 } }"
            v-if="isAppendixForm"
          >
            <a-textarea
              placeholder="请输入备注"
              v-decorator="['remark', {rules:[{required: false, message: '请输入备注'}]}]"
              @change="documentNameChange"
              style="width: 100%"
            />
          </a-form-item>

        </a-form>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import moment from 'moment'
import { mapGetters } from 'vuex'
export default {
  name: 'TrustUpload',
  props: {
    // 窗口宽度
    width: {
      type: Number,
      default: 600
    },
    // 标题
    title: {
      type: String,
      default: '上传文件'
    },
    // 文本框文案
    placeholder: {
      type: String,
      default: '请选择文件'
    },
    // 上传地址
    action: {
      type: String,
      required: true
    },
    // 上传附加参数对象
    paramData: {
      type: Object,
      default: undefined
    },
    // 图片限制
    accept: {
      type: String,
      default: ''
    },
    isSelectProject: {
      type: Boolean,
      default: false
    },
    month: {
      type: Object,
      default: () => { return undefined }
    },
    active: {
      type: Object,
      default: () => undefined
    },
    projectId: {
      type: Number,
      default: 0
    },
    isAppendixForm: {
      type: Boolean,
      default: false
    },
    isTrustOrgForm: {
      type: Boolean,
      default: false
    }
  },
  data () {
    const projectStageTypes = ['技术开发合同', '设计开发文件', '试验试制文件', '阶段成果文件', '验收文件', '其他文件']
    return {
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      tip: '加载中...',
      spin: false,
      fileList: [],
      stage: {
        beginDate: undefined
      },
      isEdit: false,
      record: undefined,
      projectStageTypes
    }
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  methods: {
    moment,
    show (stage) {
      this.visible = true
      if (!this.isSelectProject) { this.stage = stage }
    },
    edit (stage, record) {
      this.isEdit = true
      this.visible = true
      this.stage = stage
      this.record = record
      const name = record.fileName + this.getExtension(record.filePath)
      const formData = {
        name: name,
        fileName: record.fileName,
        uploadTime: moment(record.uploadTime),
        stage: record.stage,
        remark: record.remark
      }
      this.$nextTick(() => {
        this.form.setFieldsValue(formData)
      })
    },
    afterClose () {
      this.form.resetFields()
      this.visible = false
      this.stage = {
        beginDate: undefined
      }
      this.fileList = []
      this.spin = false
      this.isEdit = false
      this.record = undefined
      this.year = undefined
      this.confirmLoading = false
    },
    handleSubmit () {
      const { validateFields } = this.form
      validateFields((error, values) => {
        if (error) { return }
        this.confirmLoading = true
        const params = {}
        if (values.file) {
          params['file'] = values.file.file
        }
        if (this.isEdit) {
          params['id'] = this.record.id
        }
        if (!this.isTrustOrgForm) {
          params['uploadTime'] = values.uploadTime.format('YYYY-MM-DD HH:mm:ss')
        }
        params['fileName'] = values.fileName
        params['remark'] = values.remark
        params['stage'] = values.stage
        if (this.paramData) {
          Object.assign(params, this.paramData)
        }
        const config = {
          // 添加请求头
          headers: { 'Content-Type': 'multipart/form-data' }
        }
        const param = new FormData()
        param.append('file', values.file.file)
        param.append('projectId', this.projectId)
        param.append('key', '')
        this.$http.post('/import/importImages',
          param,
          config
        ).then(data => {
          this.visible = false
          this.$message.success('操作成功！')
          const resultData = Object.assign(data.data, params)
          this.$emit('success', resultData)
        }).finally(() => {
          this.confirmLoading = false
        })
      })
    },
    // 判断是否符合接受的文件类型
    checkAccept (type) {
      if (this.accept.length === 0) {
        return true
      }
      const acceptList = this.accept.split(',')
      if (acceptList.includes(type)) {
        return true
      } else {
        return false
      }
    },
    handleChange (info) {
      const { setFieldsValue, getFieldValue } = this.form
      if (!this.checkAccept(info.file.type)) {
        setFieldsValue({ name: undefined, fileName: undefined })
        this.$message.error('不支持上传该格式的文件')
        return
      }
      const fileName = getFieldValue('fileName')
      if (!fileName) {
        const index = info.file.name.lastIndexOf('.')
        const fileName = info.file.name.substring(0, index)
        setFieldsValue({ fileName })
      }
      setFieldsValue({ name: info.file.name })
    },

    getExtension (path) {
      if (!path) return
      return path.substring(path.lastIndexOf('.'))
    },
    documentNameChange () {},
    beforeUpload (file) {
      return false
    },
    defaultPickerValue () {
      return this.month ? moment(this.month).startOf('month') : undefined
    }
  }
}
</script>

<style>

</style>
