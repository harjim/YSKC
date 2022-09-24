<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="close"
    :confirmLoading="confirmLoading"
  >
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form :form="form">
        <a-row>
          <a-col :span="12">
            <a-form-item label="设计登记号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入设计登记号"
                v-decorator="['registerNo', {rules:[{required: true, message: '请输入登记号'}]}]"
                :disabled="isEdit"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="设计名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入设计名称"
                v-decorator="['desginName', {rules:[{required: true, message: '请输入设计名称'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="关联项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select placeholder="请选择关联项目" v-decorator="['projectId']" allowClear>
                <a-select-option v-for="(item) in rdProjectList" :key="item.id" :value="item.id">
                  {{ item.rdTitle }} - {{ item.pname }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="申请日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                style="width: 100%"
                placeholder="请选择申请日期"
                v-decorator="['applyDate', {rules:[{required: true, message: '请选择申请日期'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="权利人名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入权利人名称"
                v-decorator="['ownerName']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="权利人地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea
                placeholder="请输入权利人地址"
                v-decorator="['ownerAddress']"
              ></a-textarea>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="颁证日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                style="width: 100%"
                placeholder="请选择颁证日期"
                v-decorator="['issueDate', {rules:[{required: true, message: '请选择颁证日期'}]}]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="登记证书号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入登记证书号"
                v-decorator="['certificateNo']"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select placeholder="请选择来源" v-decorator="['source', {rules:[{required: true, message: '请选择来源'}]}]">
                <a-select-option v-for="(value, key) in sourceMap" :key="key">
                  {{ value }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <ys-upload
                :multiple="true"
                url="proposalManagement/upload"
                :params="{dir: '/ICRegistration/'}"
                @path="getPath"
                :previwFun="onPreview"
                :downloadFun="onDownloadFile"
                v-decorator="['fileList', { initialValue:[] }]"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import moment from 'moment'
import YsUpload from '@/components/YsUpload'

export default {
  components: {
    YsUpload
  },
  props: {
    rdProjectList: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      visible: false,
      title: '添加集成电路设计登记',
      confirmLoading: false,
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      isEdit: false,
      // 来源类型
      sourceMap: { 0: '自主', 1: '购买' },
      spinning: false
    }
  },
  methods: {
    moment,
    add () {
      this.form.resetFields()
      this.title = '添加集成电路设计登记'
      this.isEdit = false
      this.id = undefined
      this.visible = true
    },
    edit (record) {
      this.form.resetFields()
      this.title = '编辑集成电路设计登记'
      this.isEdit = true
      this.id = record.id
      const formData = {
        registerNo: record.registerNo,
        desginName: record.desginName,
        projectId: record.projectId || undefined,
        applyDate: moment(record.applyDate),
        ownerName: record.ownerName,
        ownerAddress: record.ownerAddress,
        issueDate: moment(record.issueDate),
        certificateNo: record.certificateNo,
        source: record.source.toString(),
        fileList: this.transFormStrToAry(record.fileList)
      }

      this.$nextTick(() => {
        this.form.setFieldsValue(formData)
      })
      this.visible = true
    },
    close () {
      this.visible = false
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        const params = {
          ...values,
          id: this.isEdit ? this.id : undefined,
          applyDate: moment(values.applyDate),
          issueDate: moment(values.issueDate),
          fileList: values.fileList.map(item => item.url)
        }
        this.$http.post(this.isEdit ? '/ICRegistration/edit' : '/ICRegistration/add', params).then(res => {
          if (res.success && res.data) {
            this.$message.success('操作成功')
            this.visible = false
            this.$emit('ok', true)
          } else {
            this.$message.error(res.errorMessage || '操作失败！')
          }
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
      this.$preview({
        filePath: path,
        docName: name || '',
        visible: true
      })
    },
    onDownloadFile ({ name, path }) {
      this.$exportData('/beian/download', { filePath: path }, name, this.$message)
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
    }
  }
}
</script>

<style lang="less" scoped></style>
