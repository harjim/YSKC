<!--
 * @Author: ldx
 * @Date: 2021-03-29 16:13:19
 * @LastEditTime: 2021-10-27 17:50:04
 * @LastEditors: lzh
 * @Description: 添加铭牌
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\nameplateModules\AddNameplateModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="900"
    style="top:0px;"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    @ok="handleSubmit"
  >
    <a-spin id="spin" :spinning="spinning" tip="加载中...">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="序号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                placeholder="请输入序号"
                style="width: 100%;"
                v-decorator="['seq', { rules: [{ required: true, message: '请输入序号' }] }]"
                :min="-$store.state.maxOrder"
                :max="$store.state.maxOrder"
              ></a-input-number>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="设备制造商" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                style="width: 100%;"
                placeholder="请输入设备制造商"
                v-decorator="['manufacturer', { rules: [{ required: true, message: '请输入设备制造商' }] }]"
              ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="设备名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <search-select
                url="/inverstment/getDeviceName"
                searchField="deviceName"
                placeholder="请输入设备名称"
                v-decorator="[
                  'ename',
                  { rules: [{ required: true, message: '请输入设备名称' }], validateTrigger: ['change', 'blur'] }
                ]"
              />
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="规格型号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                style="width: 100%;"
                v-decorator="['emodal', { rules: [{ required: true, message: '请输入规格型号' }] }]"
                placeholder="请输入规格型号"
              ></a-input>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="设备出厂编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                style="width: 100%;"
                v-decorator="['factoryNo', { rules: [{ required: true, message: '请输入设备出厂编号' }] }]"
                placeholder="请输入设备出厂编号"
              ></a-input>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="设备出厂日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                style="width: 100%;"
                v-decorator="['factoryDate', { rules: [{ required: true, message: '请输入设备出厂日期' }] }]"
              ></a-date-picker>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="设备安装位置" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-auto-complete
                :dataSource="address"
                placeholder="请输入设备安装位置"
                v-decorator="['setPlace', { rules: [{ required: true, message: '请输入设备安装位置' }] }]"
              />
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="铭牌图片附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-upload
                :fileList="files['filePath']"
                :multiple="false"
                @preview="preview"
                @download="download('filePath')"
                @change="file => handleChange(file, 'filePath')"
                :beforeUpload="file => beforeUpload(file, 'filePath')"
                :showUploadList="{ showPreviewIcon: true, showRemoveIcon: true, showDownloadIcon: true }"
                v-decorator="[
                  'filePathUpload',
                  { rules: [{ required: true, type: 'array', transform: transformUpload, message: '请上传附件' }] }
                ]"
              >
                <a-button> <a-icon type="upload" />上传</a-button>
              </a-upload>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
// import { initForm } from '@/utils/util'
import moment from 'moment'
import { mapState } from 'vuex'
import SearchSelect from '../SearchSelect'
import { get } from 'lodash'

export default {
  name: '',
  components: {
    SearchSelect
  },
  data () {
    return {
      form: this.$form.createForm(this),
      isVisible: false,
      title: '',
      spinning: false,
      files: { filePath: [] },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      tableDatas: [],
      nameplateRecord: {},
      investRecord: {}
    }
  },
  computed: {
    ...mapState({
      address: state => state.common.address
    })
  },
  methods: {
    moment,
    show (title, tableDatas, investRecord) {
      this.form.resetFields()
      this.isVisible = true
      this.title = title
      this.tableDatas = tableDatas
      this.investRecord = investRecord
    },
    edit (title, nameplateRecord, investRecord) {
      this.form.resetFields()
      this.isVisible = true
      this.title = title
      this.nameplateRecord = nameplateRecord
      this.investRecord = investRecord
      this.initFormData(nameplateRecord)
    },
    initFormData (nameplateRecord) {
      this.$nextTick(() => {
        const filePath = nameplateRecord['filePath']
        const arr = filePath.split('/')
        const fileName = arr[arr.length - 1].substr(13)
        this.files['filePath'] = [
          {
            uid: fileName,
            name: fileName,
            status: 'done',
            url: filePath
          }
        ]
        nameplateRecord.ename = [nameplateRecord.ename]
        this.$initForm(this.form, nameplateRecord, ['factoryDate'])
        this.form.setFieldsValue({ filePathUpload: { fileList: this.files['filePath'] } })
      })
    },
    afterClose () {
      this.isVisible = false
      this.title = ''
      this.tableDatas = []
      this.files = { filePath: [] }
      this.nameplateRecord = {}
      this.investRecord = undefined
    },
    handleSubmit () {
      this.form.validateFields((error, values) => {
        if (!error) {
          values['filePath'] = this.files['filePath'][0].url
          values['ename'] = values['ename'][0]
          this.tableDatas.push(values)
          this.$emit('updateTableDatas', this.tableDatas)
          this.isVisible = false
        }
      })
    },
    beforeUpload (file, key) {
      if (!this.$checkFileSize(file, this.$message)) {
        return
      }
      const param = new FormData()
      param.append('file', file)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http
        .post('/beian/upload', param, config)
        .then(res => {
          if (res.success) {
            this.files[key] = [
              {
                uid: res.data.fileName,
                name: res.data.fileName,
                status: 'done',
                url: res.data.filePath
              }
            ]
            this.$emit('changeSaveBtnStatus')
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '上传失败')
          }
        })
        .catch(res => {})
      return false
    },
    handleChange (file, key) {
      this.btnDisabled = false
      if (file.file.status === 'removed') {
        this.files[key] = file.fileList
      }
    },
    preview (file) {
      if (file.url) {
        this.$preview({
          filePath: file.url,
          docName: get(file, 'name', ''),
          visible: true
        })
      }
    },
    download (key) {
      const file = this.files[key][0]
      if (file) {
        const filePath = file.url
        const arr = filePath.split('/')
        const fileName = arr[arr.length - 1]
        this.$exportData(
          '/beian/download',
          { filePath },
          fileName.length > 13 ? fileName.substr(13) : fileName,
          this.$message
        )
      }
    },
    /**
     * @description: 转化上传的验证值的回调函数
     * @param {*} 当前的值
     * @return {*} 验证的内容
     */
    transformUpload (value) {
      if (value && value.fileList && value.fileList.length) {
        return value.fileList
      }
      return null
    }
  }
}
</script>
<style lang="less" scoped></style>
