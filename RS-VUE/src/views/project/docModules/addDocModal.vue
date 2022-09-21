<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="1200"
    :maskClosable="false"
    v-model="visible"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-form-item label="项目列表:" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-button type="primary" @click="handleAdd">添加</a-button>
        <a-table bordered :dataSource="docData" :pagination="false" size="small" :scroll="{y:480}">
          <a-table-column title="编号" data-index="docNumber" :width="60">
            <template slot-scope="text,record">
              <a-input
                :value="text"
                :key="`docNumber${record.key}`"
                @change="(e)=>onCellChange(e.target.value,record,'docNumber')"
              />
            </template>
          </a-table-column>
          <a-table-column title="文档名" data-index="docName" :width="600">
            <template slot-scope="text,record">
              <a-input
                :value="text"
                :key="`docName${record.key}`"
                @change="(e)=>onCellChange(e.target.value,record,'docName')"
              />
            </template>
          </a-table-column>
          <a-table-column title="模板" data-index="templateId" :width="300">
            <template slot-scope="text,record">
              <a-select
                :value="text"
                :key="`templateId${record.key}`"
                style="float:left"
                @change="(value)=>onTemplateChange(value,record,'templateId')"
              >
                <a-select-option :value="1">项目计划书</a-select-option>
                <a-select-option :value="2">资金预算表</a-select-option>
                <a-select-option :value="3">立项报告</a-select-option>
              </a-select>
            </template>
          </a-table-column>
          <a-table-column title="操作" data-index="key" :width="50">
            <template slot-scope="text,record,index">
              <a @click="handleDel(index)">移除</a>
            </template>
          </a-table-column>
        </a-table>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  name: 'AddDocModal',
  components: {
  },
  data () {
    return {
      deptTree: [],
      title: '',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 2 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 22 }
      },
      docData: [],
      id: 0,
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      count: 1,
      deptIdsArr: [],
      rdDeptId: '',
      employeeList: [],
      rdDeptTree: {},
      projectId: 0,
      stageId: 0
    }
  },
  created () {
  },
  mounted () {
    this.loadDept()
    this.loadRdDept()
  },
  methods: {
    onTemplateChange (value, record, name) {
      record[name] = value
      this.templateId = value
    },
    loadRdDept () {
      return this.$getTree('rdDept')
        .then(res => {
          this.rdDeptTree = res.tree
          return this.rdDeptTree
        })
    },
    getName (record) {
      return record.ename + ' ' + record.masterENumber
    },
    loadDept () {
      return this.$getTree('dept')
        .then(res => {
          this.deptTree = res.tree
        })
    },
    deptSelect (value) {
      this.$emit('deptSelect', value)
    },
    rdDeptSelect (value) {
      this.$emit('rdDeptSelect', value)
    },
    handleSearch (value) {
      this.$http.get('/project/getEmployeeList', { params: { ename: value } })
        .then(res => {
          this.employeeList = res.data
        })
    },
    onCellChange (value, record, name) {
      record[name] = value
    },
    onrdDeptIdChange (value, record, name) {
      record[name] = value
      this.rdDeptId = value
    },
    handleDel (index) {
      this.docData.splice(index, 1)
    },
    handleAdd () {
      const { count } = this
      const newData = {
        key: count,
        docNumber: count,
        docName: '',
        templateId: ''
      }
      this.$set(this.docData, this.docData.length, newData)
      this.count = count + 1
    },
    add (record, ryear, pname) {
      this.title = ryear + '--' + pname
      this.projectId = record.projectId
      this.stageId = record.id
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.docData = record.docData
      this.count = 1
      this.id = 0
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.id
          const data = { ...values }
          data.docData = []

          for (let index = 0; index < this.docData.length; index++) {
            if (this.docData[index].docNumber.length === 0) {
              this.$message.info('请输入第' + parseFloat(index + 1) + '行的编号')
              this.confirmLoading = false
              return
            }
            if (this.docData[index].docName.length === 0) {
              this.$message.info('请输入第' + parseFloat(index + 1) + '行的文档名')
              this.confirmLoading = false
              return
            }
            if (this.docData[index].templateId.length === 0) {
              this.$message.info('请选择第' + parseFloat(index + 1) + '行的模板')
              this.confirmLoading = false
              return
            }
            this.docData[index].projectId = this.projectId
            this.docData[index].stageId = this.stageId
            const itemData = { ...this.docData[index] }
            data.docData.push(itemData)
          }
          this.$http.post('/document/save', data)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>
