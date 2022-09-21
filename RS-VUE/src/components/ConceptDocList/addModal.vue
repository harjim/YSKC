<template>
  <div>
    <a-modal
      :width="500"
      :visible="visible"
      :title="title"
      :maskClosable="false"
      @ok="handleSubmit"
      @cancel="handleCancel"
      :confirmLoading="confirmLoading"
    >
      <a-form @submit="handleSubmit" :form="form">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="宣讲时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker :disabled="isEdit" style="width: 100%" :disabled-date="disabledDate" v-decorator="['preachDate', { rules: [{ required: true, message: '请选择宣讲时间' }] }]" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="关联项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                :maxTagCount="5"
                mode="multiple"
                v-decorator="['rds', { rules: [{ required: false, message: '请选择关联项目' }] }]"
                placeholder="请选择关联项目"
              >
                <a-select-option :value="item.rdTitle" v-for="(item) in rdsList" :key="item.rdTitle">
                  {{ item.rdTitle }}-{{ item.pname }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="附件:" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <ys-upload
                :multiple="true"
                :lists="fileLists"
                url="proposalManagement/upload"
                :params="{dir: '/senseManagement/'}"
                @path="getPath"
                :previwFun="onPreview"
                :downloadFun="onDownloadFile"
                v-decorator="['filePaths', { initialValue:[] , rules: [{required: true, message: '请上传附件'}]} ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item :extra="onComputeTextarea('remark',200)" label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['remark']" name="" id="" style="width: 100%;" rows="3"></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-modal>
    <!-- 预览 -->
    <preview-modal class="preview-modal" ref="previewModal"></preview-modal>
  </div>
</template>

<script>
import YsUpload from '@/components/YsUpload'
import moment from 'moment'
import { PreviewModal } from '@/components'

export default {
  components: {
    YsUpload,
    PreviewModal
  },
  props: {
    rdsList: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      title: '添加',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      confirmLoading: false,
      visible: false,
      form: this.$form.createForm(this),
      isEdit: false,
      fileLists: [],
      path: '',
      type: undefined
    }
  },
  methods: {
    moment,
    add (type) {
      this.visible = true
      this.type = type
      this.id = 0
      this.isEdit = false
      this.fileLists = []
      this.title = '添加'
    },
    edit (record) {
      this.isEdit = true
      this.id = record.id
      this.visible = true
      this.type = parseInt(record.type)
      this.title = '编辑'
      const formData = {
        preachDate: moment(record.preachDate),
        filePaths: this.transFormStrToAry(record.filePaths),
        remark: record.remark,
        rds: record.rds
      }
      this.$nextTick(() => {
        this.form.setFieldsValue(formData)
      })
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      validateFields((errors, values) => {
        if (errors) return
        const params = {
          preachDate: moment(values.preachDate).format('YYYY-MM-DD'),
          filePaths: this.path.split(','),
          remark: values.remark,
          rds: values.rds,
          type: this.type,
          id: this.isEdit ? this.id : undefined
        }
        this.confirmLoading = true
        const apiPath = this.isEdit ? '/senseManagement/editSense' : '/senseManagement/addSense'
        this.$http.post(apiPath, params).then(res => {
          if (res.success && res.data) {
            this.$message.success('操作成功')
            this.visible = false
            this.form.resetFields()
            this.$emit('ok')
          } else {
            this.$message.error(res.errorMessage || '操作失败！')
          }
        }).catch(error => {
          this.$message.error(error.message || '系统异常，请联系管理员！')
        }).finally(() => {
          this.confirmLoading = false
        })
      })
    },
    getPath (path) {
      this.path = path
    },
    onPreview ({ name, path }) {
      if (path === '') {
        this.$message.info('请先上传文件')
        return
      }
      this.$refs.previewModal.show(path, name)
    },
    onDownloadFile ({ name, path }) {
      this.$exportData('/beian/download', { filePath: path }, name, this.$message)
    },
    // 计算文本域的个数
    onComputeTextarea (fieldName, limitNumber = 200) {
      const content = this.form.getFieldValue(fieldName)
      const contentLenght = content ? content.length : 0
      if (contentLenght > limitNumber) {
        const obj = {}
        obj[fieldName] = { value: content.substring(0, limitNumber) }
        this.form.setFields(obj)
      }
      return `(${contentLenght}/${limitNumber})`
    },
    transFormStrToAry (filePaths) {
      const files = []
      if (filePaths != null) {
        filePaths.forEach(path => {
          const name = path.substring(path.lastIndexOf('/') + 14)
          files.push({
            uid: path,
            name,
            status: 'done',
            url: path
          })
        })
      }
      return files
    },
    handleCancel () {
      this.visible = false
      this.form.resetFields()
    },
    disabledDate (current) {
      return current > moment().endOf('day')
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
</style>
