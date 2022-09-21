<template>
  <a-modal
    :width="750"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
  >
    <a-spin :spinning="confirmLoading">
      <a-form @submit="handleSubmit" :form="form">
        <a-card :bordered="false">
          <a-row :gutter="24">
            <a-col :span="12">
              <a-form-item label="清单类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select
                  v-decorator="['listType', {rules:[{required: true, message: '请选择清单类型'}]}]"
                  @change="changeType"
                  :disabled="isAdd"
                >
                  <a-select-option value="6001">研发技术中心</a-select-option>
                  <a-select-option value="7001">研发项目</a-select-option>
                  <a-select-option value="1001">研发成果</a-select-option>
                  <a-select-option value="2001">知识产权</a-select-option>
                  <a-select-option value="3001">研发档案</a-select-option>
                  <!-- <a-select-option value="3002">附件清单</a-select-option> -->
                  <a-select-option value="4001">研发意识</a-select-option>
                  <a-select-option value="5001">项目其他财务信息</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="序号" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input-number
                  :min="1"
                  v-decorator="['seq', {rules:[{required: true, message: '请输入序号'}]}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  v-decorator="['classify', {rules:[{required: true, message: '请输入研发活动'}]}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="子分类" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="['subClassify']" />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="研发活动" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input
                  v-decorator="['rdActivities', {rules:[{required: true, message: '请输入研发活动'}]}]"
                />
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item label="文档名" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-input v-decorator="['docName', {rules:[{required: true, message: '请输入文档名'}]}]" />
              </a-form-item>
            </a-col>
            <a-col :span="12" v-if="childTypes[selectListType]">
              <a-form-item label="子类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-select
                  v-decorator="['childType', {rules:[{required: false, message: '请选择子类型'}]}]">
                  <a-select-option v-for="(item,index) in childTypes[selectListType]" :key="index" :value="item.value">{{ item.label }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="3" style="margin-top:5px;">
              <span style="margin-left: 15px;">样例文件:</span>
            </a-col>
            <a-col :span="18">
              <a-input placeholder="请上传样例文档" :disabled="true" :value="fileName" />
            </a-col>
            <a-col :span="3">
              <a-upload
                :fileList="fileList"
                :multiple="false"
                :beforeUpload="beforeUpload"
                @change="handleChange"
              >
                <a-button>
                  <a-icon type="upload" />选择
                </a-button>
              </a-upload>
            </a-col>
          </a-row>
          <a-row :gutter="24" style="margin-top:30px;">
            <a-col :span="24">
              <a-form-item
                label="说明"
                :labelCol="{ span: 3 }"
                :wrapperCol="{ span: 21 }"
                :help="()=>{const r = form.getFieldValue('desciption'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}"
              >
                <a-textarea v-decorator="['desciption']" :maxLength="200" />
              </a-form-item>
            </a-col>
          </a-row>
          <!-- <a-progress :percent="percent" v-if="true" /> -->
        </a-card>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
const childTypes = {
  6001: [
    { value: '0', label: '组织管理制度' },
    { value: '1', label: '研发机构' },
    { value: '2', label: '成果转化奖励制度' },
    { value: '3', label: '人才绩效制度' }
  ],
  1001: [
    { value: '4', label: '检测文件' },
    { value: '5', label: '国内先进证明' },
    { value: '6', label: '国内领先证明' },
    { value: '7', label: '出口证明' },
    { value: '8', label: '出口欧美发达地区证明' }
  ]

}
export default {
  name: 'DocListModal',
  components: {
  },
  deptTree: [],
  data () {
    return {
      childTypes,
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      id: 0,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      isAdd: false,
      fileList: [],
      fileItem: undefined,
      fileName: '',
      percent: 0,
      selectListType: undefined
    }
  },
  created () {
  },
  methods: {
    handleChange (info) {
      this.fileName = info.file.name
      this.fileItem = info.file
      // if (this.documentName === '') {
      //   const { form: { setFieldsValue } } = this
      //   setFieldsValue({ documentName: info.file.name.split('.')[0] })
      // }
    },
    beforeUpload (file) {
      return false
    },
    changeType (type) {
      this.selectListType = type
      return this.$http.get('/docList/selectMaxSeq', { params: { listType: type } })
        .then(res => {
          if (res.data) {
            this.$initForm(this.form, { seq: res.data })
          } else {
            this.$initForm(this.form, { seq: 1 })
          }
          return res.data
        })
    },
    add () {
      this.form.resetFields()
      this.selectListType = undefined
      this.fileList = []
      this.fileItem = undefined
      this.fileName = ''
      this.title = '添加'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.isAdd = false
      this.id = 0
    },
    edit (record) {
      this.form.resetFields()
      this.selectListType = record.listType
      this.fileList = []
      this.fileItem = undefined
      this.title = '编辑[' + record.docName + ']'
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        let fileName = ''
        if (record.samplePath) {
          const arr = record.samplePath.split('/')
          fileName = arr[arr.length - 1].substring(13)
        }
        this.fileName = fileName
        this.$initForm(this.form, record)
      })
      this.isAdd = true
    },
    handleSubmit () {
      if (this.fileName !== '' && this.fileItem) {
        const param = new FormData()
        param.append('fileName', this.fileName)
        param.append('file', this.fileItem)
        const { form: { validateFields } } = this
        validateFields((errors, values) => {
          param.append('id', this.id)
          param.append('listType', values.listType)
          param.append('seq', values.seq)
          param.append('classify', values.classify)
          param.append('subClassify', values.subClassify !== undefined ? values.subClassify : '')
          param.append('rdActivities', values.rdActivities)
          param.append('docName', values.docName)
          param.append('desciption', values.desciption !== undefined ? values.desciption : '')
        })
        const config = {
          // 添加请求头
          headers: { 'Content-Type': 'multipart/form-data' },
          // 添加上传进度监听事件
          onUploadProgress: e => {
            var completeProgress = ((e.loaded / e.total * 100) | 0)
            this.percent = completeProgress
          }
        }
        if (this.paramData !== undefined) {
          Object.keys(this.paramData).map(a => {
            param.append(a, this.paramData[a])
          })
        }
        if (this.tableField !== undefined) {
          Object.keys(this.tableField).map(a => {
            param.append(a, this.tableField[a])
          })
        }
        this.confirmLoading = true
        this.$http.post('/docList/uploadFile', param, config).then(res => {
          this.$emit('ok', res.data)
          // if (res.data === 1) {
          //   this.$message.success('添加成功')
          //   this.$emit('ok', true)
          // } else if (res.data === 2) {
          //   this.$message.success('修改成功')
          //   this.$emit('ok', true)
          // } else {
          //   this.$message.error('上传模板失败')
          // }
          return res
        }).catch(res => {
          this.$emit('error', this.fileItem.name)
        }).finally(res => {
          this.visible = false
          this.confirmLoading = false
        })
      } else {
        const { form: { validateFields } } = this
        this.visible = true
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            if (this.id === 0) {
              this.$http.post('/docList/addDocList', values)
                .then(res => {
                  if (res.success && res.data) {
                    this.visible = false
                    this.confirmLoading = false
                    this.$emit('ok', 1)
                  }
                }).finally(res => {
                  this.confirmLoading = false
                })
            } else {
              values.id = this.id
              this.$http.post('/docList/updateDocList', values)
                .then(res => {
                  if (res.success && res.data) {
                    this.visible = false
                    this.confirmLoading = false
                    this.$emit('ok', 2)
                  }
                }).finally(res => {
                  this.confirmLoading = false
                })
            }
          } else {
            this.confirmLoading = false
          }
        })
      }
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
