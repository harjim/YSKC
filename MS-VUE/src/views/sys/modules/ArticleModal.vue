<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="900"
    v-model="visible"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
  >
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="文章标题" style="padding:0;">
            <a-input placeholder="请输入文章标题" v-decorator="['title', {rules:[{required: true, message: '请输入文章标题'}]}]" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="文章作者" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入文章作者" v-decorator="['author', {rules:[{required: true, message: '请输入文章作者'}]}]" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="文章类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              style="width: 282px;"
              placeholder="请选择文章类型"
              v-decorator="['type', {rules:[{required: true, message: '请输入文章类型'}]}]">
              <a-select-option :value="String(0)" :key="String(0)">公司新闻</a-select-option>
              <a-select-option :value="String(1)" :key="String(1)">行业动态</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="文章来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入文章来源" v-decorator="['source', {rules:[{required: false, message: '请输入文章来源'}]}]" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="发行日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker placeholder="请输入发行日期" v-decorator="['issueDate', {rules:[{required: true}]}]" format="YYYY-MM-DD"></a-date-picker>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="置顶权重" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number style="width:100%" placeholder="请输入置顶权重" v-decorator="['top', {rules:[{required: false, message: '请输入置顶权重'}]}]" :max="1000" :precision="0" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            :labelCol=" { xs: { span: 24 },sm: { span: 24 }}"
            :wrapperCol=" { xs: { span: 24 },sm: { span: 23 }}"
            label="文本内容">
            <!-- <wang-editor key="content" v-decorator="['content', {rules:[{required: true, message: '解决方案'}],validateTrigger: ['blur','submit']}]" :dir="dir" placeholder="解决方案" /> -->
            <wang-editor key="content" v-decorator="['content', {rules:[{required: true, message: contentLabel}]}]" :dir="dir" :placeholder="contentLabel" />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
import WangEditor from '@/components/Editor/WangEditor.vue'
import moment from 'moment'
export default {
  components: {
    WangEditor
  },
  data () {
    return {
      fileList: [],
      visible: false,
      id: undefined,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 24 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 22 }
      },
      dir: `/solution/${moment().format('YYYY-MM-DD')}/`,
      form: this.$form.createForm(this),
      title: '',
      rate: 0,
      confirmLoading: false,
      contentLabel: ''
    }
  },
  methods: {
    moment,
    add () {
      this.fileList = []
      this.title = '添加文章'
      this.id = undefined
      this.visible = true
      this.confirmLoading = false
      this.$nextTick(() => {
        this.form.resetFields()
      })
    },
    edit (row) {
      this.fileList = []
      this.author = row.author
      this.title = `编辑[${row.title}]`
      this.id = row.id
      this.visible = true
      this.confirmLoading = false
      this.issueDate = row.issueDate
      this.source = row.source
      this.content = row.content
      this.$nextTick(() => {
        this.form.resetFields()
        if (row.filePath) {
          const temp = []
          row.filePath.split(',').forEach(item => {
            const name = item.substring(item.lastIndexOf('/') + 14, item.length)
            temp.push({
              uid: name,
              name,
              status: 'done',
              url: item
            })
          })
          this.fileList = temp
        }
        this.$initForm(this.form, row, ['issueDate'])
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        let url = '/article/addArt'
        const edit = Boolean(this.id)
        if (edit) {
          values.id = this.id
          url = '/article/editArt'
        }
        this.$http.post(url, values).then(res => {
          if (res.success && res.data) {
            this.visible = false
            this.$emit('ok', !edit)
          } else {
            this.$message.error('保存失败')
          }
        }).finally(() => {
          this.confirmLoading = false
        })
      })
    },
    show (row) {
      this.row = row
      this.visible = true
      this.title = row.title
      this.author = row.author
      this.type = (row.type === 0 ? '公司新闻' : '行业动态')
      this.source = row.source
      this.content = row.content
    }
  }
}
</script>

<style>
.ant-col{
  margin-bottom:-9px;
}
  .ant-form-item-label{
    text-align:left;
    padding-bottom: 8px;
  }
  .ant-form-item-label > label::after {
    content: '';
}
.ant-form{
  padding-left: 36px;
  padding-right: 36px;
}
</style>
