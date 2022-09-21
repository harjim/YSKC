<!--
 * @Author: ldx
 * @Date: 2021-06-18 11:03:13
 * @LastEditTime: 2022-07-28 09:52:12
 * @LastEditors: zdf
 * @Description: 上传记录附件
 * @FilePath: \RS-VUE\src\components\UploadModal\Upload.vue
-->
<template>
  <a-modal
    v-model="visible"
    :afterClose="afterClose"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    :title="title"
    :width="width"
    @ok="handleSubmit"
  >
    <a-spin :spinning="spin" :tip="tip">
      <div class="clearfix">
        <a-form :form="form">
          <a-row v-if="isSelectProject" :gutter="24">
            <a-col :md="8" :sm="24">
              <a-form-item
                v-if="isSelectProject"
                :labelCol=" {xs: { span: 24 },sm: { span: 12 } }"
                :wrapperCol="{xs: { span: 24 },sm: { span: 12 } }"
                label="项目"
              >
                <project-select
                  v-decorator="['projectId', {rules:[{required: true, message: '请选择项目'}]}]"
                  :filterField="{deptName: active.deptName,
                                 workshop:active.workshop ,
                                 processSection:active.processSection ,
                                 productLine:active.productLine}"
                  :isFilter="true"
                  :isJoinPrjectName="true"
                  :sign="2"
                  :year="currentYear"
                  style="width: 433px;"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :md="8" :sm="24">
              <a-form-item
                :labelCol=" {xs: { span: 24 },sm: { span: 12 } }"
                :wrapperCol="{xs: { span: 24 },sm: { span: 12 } }"
                label="记录日期"
              >
                <a-date-picker
                  v-decorator="['uploadTime', {rules:[{required: true, message: '请选择日期'}]}]"
                  :defaultPickerValue="defaultPickerValue()"
                  :disabledDate="disabledDate"
                  placeholder="请选择日期"
                  style="width:433px;"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col :md="8" :sm="24">
              <a-form-item
                :labelCol=" {xs: { span: 24 },sm: { span: 12 } }"
                :wrapperCol="{xs: { span: 24 },sm: { span: 12 } }"
                label="文件名"
              >
                <a-input
                  v-decorator="['fileName', {rules:[{required: true, message: '请输入文件名'}]}]"
                  placeholder="请输入文件名"
                  style="width:433px"
                  @change="documentNameChange"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-form-item
              :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 20 } }"
              label="附件:"
              style="margin-left:-10px"
            >
              <a-col :span="18">
                <a-input
                  v-decorator="['name', { rules:[{required: true, message: '请上传附件'}] }]"
                  :disabled="true"
                  placeholder="请上传附件"
                />
              </a-col>
              <a-col :span="2">
                <a-upload
                  :accept="accept"
                  :beforeUpload="beforeUpload"
                  :fileList="fileList"
                  :multiple="false"
                  @change="handleChange"
                >
                  <a-button>
                    <a-icon type="upload" />
                    选择
                  </a-button>
                </a-upload>
              </a-col>
            </a-form-item>
          </a-row>
          <a-row :gutter="24">
            <a-col :md="8" :sm="24">
              <a-form-item
                :labelCol=" {xs: { span: 24 },sm: { span: 12 } }"
                :wrapperCol="{xs: { span: 24 },sm: { span: 12 } }"
                label="附件类型"
              >
                <a-select
                  v-decorator="['fileType', {rules:[{required: true, message: '请选择附件类型'}]}]"
                  placeholder="请选择附件类型"
                  style="width:433px"
                >
                  <a-select-option
                    v-for="(value, key) in fileTypeList"
                    :key="key"
                    :value="key">{{ value }}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import moment from 'moment'
import { upload } from '@/api/upload'
import { mapGetters } from 'vuex'
import ProjectSelect from '@/components/ProjectSelect/index.vue'

export default {
  name: 'Upload',
  components: {
    ProjectSelect
  },
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
      default: () => {
        return undefined
      }
    },
    active: {
      type: Object,
      default: () => undefined
    }
  },
  data () {
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
      file: null,
      fileTypeList: {
        1: '会议纪要',
        2: '实验记录',
        3: '试制排期',
        4: '试制报表',
        5: '试制报告',
        6: '技术培训',
        7: '物料清单（BOM）',
        8: '作业指导书（SOP）',
        9: '发明专利',
        10: '实用新型专利',
        11: '外观设计专利',
        12: '计算机软件著作权',
        13: '集成电路布图设计',
        14: '科技查新报告',
        15: '论文期刊',
        16: '国家标准',
        17: '行业标准',
        18: '地方标准',
        19: '团体标准',
        20: '企业标准',
        21: '技术原理',
        22: '技术图纸',
        23: '技术方案',
        24: '技术规范',
        25: '工艺规程',
        26: '检测报告',
        27: '样品样机',
        99: '其他'
      }
    }
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  methods: {
    moment,
    show (stage) {
      this.visible = true
      if (!this.isSelectProject) {
        this.stage = stage
      }
    },
    // edit (stage, record) {
    //   this.isEdit = true
    //   this.visible = true
    //   this.stage = stage
    //   this.record = record
    //   const name = record.fileName + this.getExtension(record.filePath)
    //   const formData = {
    //     name: name,
    //     fileName: record.fileName,
    //     uploadTime: record.uploadTime ? moment(record.uploadTime) : undefined
    //   }
    //   this.$nextTick(() => {
    //     this.form.setFieldsValue(formData)
    //   })
    // },
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
      if (!this.$checkFileSize(this.file, this.$message, 5242880)) { // 5242880 5M
        return
      }
      const { validateFields } = this.form
      validateFields((error, values) => {
        if (error) {
          return
        }
        const params = {}
        params.fileType = values.fileType
        params.file = this.file
        // if (values.file) {
        //   params['file'] = values.file.file
        // }
        if (this.isEdit) {
          params['id'] = this.record.id
        }
        params['uploadTime'] = values.uploadTime.format('YYYY-MM-DD HH:mm:ss')
        params['fileName'] = values.fileName
        if (this.paramData) {
          Object.assign(params, this.paramData)
        }
        if (this.isSelectProject) {
          params['projectId'] = values.projectId
        }

        upload(params).then(data => {
          this.visible = false
          this.$message.success('操作成功！')
          this.$emit('success', data)
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
      this.file = info.file
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
    documentNameChange () {
    },
    beforeUpload (file) {
      return false
    },
    disabledDate (current) {
      // if (this.isSelectProject) {
      return current < moment(this.month).startOf('month') || current >= moment(this.month).endOf('month')
      // }
    },
    defaultPickerValue () {
      return this.month ? moment(this.month).startOf('month') : undefined
      // if (this.isSelectProject) {
      //   return this.month ? moment(this.month).startOf('month') : undefined
      // } else {
      //   return this.stage.beginDate ? moment(this.stage.beginDate).startOf('month') : undefined
      // }
    }
  }
}
</script>

<style lang="less" scoped>
/deep/ .ant-form-explain {
  width: 30em;
}
</style>
