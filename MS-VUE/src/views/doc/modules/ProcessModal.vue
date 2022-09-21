<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="1450"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item label="阶段名称:" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input
          style="width:500px"
          v-decorator="['processName', {rules:[{required: true, message: '请输入阶段名称'}]}]"
        />
      </a-form-item>
      <a-form-item label="文件模板配置:" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-button type="primary" @click="addSelectTemplate()">添加</a-button>
      </a-form-item>
      <a-form-item>
        <a-table
          ref="table"
          size="default"
          rowKey="id"
          :columns="columns"
          :dataSource="initializeData"
          :pagination="false"
        >
          <span slot="action" slot-scope="text, record">
            <a-popconfirm title="是否确定删除?" @confirm="handleDel(record)">
              <a>删除</a>
            </a-popconfirm>
          </span>
        </a-table>
      </a-form-item>
    </a-form>
    <selectTemplate-modal ref="templateModal" @ok="handleOk"></selectTemplate-modal>
  </a-modal>
</template>

<script>
import SelectTemplateModal from './SelectTemplateModal'
const columns = [
  {
    dataIndex: 'seq',
    key: 'seq',
    title: '序号',
    align: 'center'
  }, {
    dataIndex: 'docName',
    key: 'docName',
    title: '名称',
    align: 'center'
  }, {
    dataIndex: 'version',
    key: 'version',
    title: '版本',
    align: 'center'
  }, {
    dataIndex: 'createTime',
    key: 'createTime',
    title: '创建时间',
    align: 'center'
  }, {
    title: '操作',
    key: 'action',
    align: 'center',
    scopedSlots: { customRender: 'action' }
  }]
export default {
  name: 'ReportingModal',
  components: {
    SelectTemplateModal
  },
  data () {
    return {
      title: '',
      confirmLoading: false,
      columns,
      visible: false,
      flag: 0,
      processId: 0,
      // 查询参数
      queryParam: {},
      templateList: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      id: 0,
      form: this.$form.createForm(this),
      initializeData: []
    }
  },
  created () {
  },
  mounted () {
  },
  methods: {
    handleOk (list) {
      this.svalueList = list
      for (var i = 0; i < list.length; i++) {
        list[i].seq = i + 1
      }

      list.forEach(item => {
        var check = false
        this.initializeData.forEach(init => {
          if (init.templateId === item.id) {
            check = true
          }
        })
        if (!check) {
          item.templateId = item.id
          this.initializeData.push(item)
        }
      })
    },
    add () {
      this.title = '添加过程文件'
      this.confirmLoading = false
      this.initializeData = []
      this.visible = true
      this.form.resetFields()
      this.projectData = []
      this.count = 1
      this.id = -1
    },
    edit (record) {
      this.title = `编辑过程文件[${record.processName}]`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.processId = record.processId
      this.$nextTick(() => {
        this.$initForm(this.form, record)
        this.initializeData = record.templateListModels
      })
    },
    addSelectTemplate () {
      this.$refs.templateModal.add(this.initializeData)
    },
    getData () {
      this.initializeData = []
    },
    handleDel (record) {
      this.initializeData.splice(this.initializeData.findIndex(item => item === record.id), 1)
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.id === -1) {
            values.templateListModels = this.initializeData
            this.$http.post('/docProcess/addProcessDocTemplate', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                } else {
                  this.$message.info(res.errorMessage)
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            values.templateListModels = this.initializeData
            values.id = this.id
            this.$http.post('/docProcess/updateProcessDocTemplate', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                } else {
                  this.$message.info(res.errorMessage)
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
</script>
