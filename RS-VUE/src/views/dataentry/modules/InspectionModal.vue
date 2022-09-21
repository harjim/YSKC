<template>
  <a-modal
    :width="800"
    :visible="visible"
    :title="title"
    @ok="handleSubmit"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-card :bordered="false">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="凭证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                placeholder="请输入凭证号"
                v-decorator="['accNumber', { rules: [{ required: true, message: '请输入凭证号' }] }]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="记账日期">
              <a-date-picker
                format="YYYY-MM-DD"
                placeholder="请选择记账日期"
                v-decorator="['accDate', { rules: [{ required: true, message: '请选择记账日期' }] }]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="费用">
              <a-input-number
                :max="$store.state.totalMax"
                :precision="2"
                placeholder="请输入费用"
                v-decorator="['expense', { rules: [{ required: true, message: '请输入费用' }] }]"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="部门">
              <a-input
                v-decorator="['deptName', { rules: [{ required: false, message: '请输入部门' }] }]"
                placeholder="请输入部门"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12" v-if="types.length > 1">
            <a-form-item label="费用类型" :labelCol="labelCol" :wrapperCol="wrapperCol" placeholder="请选择费用类型">
              <a-select
                v-decorator="['type', { rules: [{ required: true, message: '请选择费用类型' }] }]"
                placeholder="请选择费用类型"
              >
                <template v-if="types.length === 3">
                  <a-select-option value="40000">软件摊销</a-select-option>
                  <a-select-option value="40100">专利摊销</a-select-option>
                  <a-select-option value="40200">其他摊销</a-select-option>
                </template>
                <template v-if="types.length === 6">
                  <a-select-option value="20700">样机</a-select-option>
                  <a-select-option value="60000">资料</a-select-option>
                  <a-select-option value="60100">研发成果</a-select-option>
                  <a-select-option value="60200">知识产权</a-select-option>
                  <a-select-option value="60300">福利</a-select-option>
                  <a-select-option value="69900">其他</a-select-option>
                </template>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12" v-if="isShowOrHideTrve">
            <a-form-item label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                v-decorator="['enumber']"
                placeholder="请输入姓名搜索"
                style="width: 100%"
                :defaultActiveFirstOption="false"
                :showArrow="false"
                :filterOption="false"
                @search="handleSearch"
                @change="handleChange"
                :notFoundContent="null"
              >
                <a-select-option v-for="d in employeeList" :key="d.enumber">{{
                  d.ename + '(' + d.enumber + ')'
                }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="科目" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <select-down
                ref="selectDown"
                treeType="account"
                v-decorator="['accountTitleId', { rules: [{ required: false, message: '请选择科目' }] }]"
                placeholder="请选择科目"
                @select="getSelectVal"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              label="摘要"
              :labelCol="{ xs: { span: 24 }, sm: { span: 3 } }"
              :wrapperCol="{ xs: { span: 24 }, sm: { span: 19 } }"
            >
              <a-textarea
                v-decorator="['summary', { rules: [{ required: true, message: '请输入摘要' }] }]"
                :rows="1"
                placeholder="请输入摘要"
              ></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row>
          <a-col :span="24">
            <a-form-item
              label="备注"
              :labelCol="{ xs: { span: 24 }, sm: { span: 3 } }"
              :wrapperCol="{ xs: { span: 24 }, sm: { span: 19 } }"
              :help="
                () => {
                  const r = form.getFieldValue('remark')
                  return `(${!r ? 0 : r.length > 200 ? 200 : r.length}/200)`
                }
              "
            >
              <a-textarea v-decorator="['remark']" :rows="3" maxlength="200"></a-textarea>
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
  </a-modal>
</template>

<script>
import SelectDown from '@/components/SelectDown'
export default {
  components: {
    SelectDown
  },
  data () {
    return {
      title: '',
      isShowOrHideTrve: false,
      selectType: [],
      employeeList: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      id: 0,
      typeValue: undefined,
      deptTree: [],
      accountTree: [],
      visible: false,
      isCreat: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      types: []
    }
  },
  methods: {
    add (type, typeName) {
      this.title = `添加${typeName}`
      this.isCreat = true
      this.confirmLoading = false
      this.visible = true
      this.employeeList = []
      this.form.resetFields()
      this.id = -1
      this.selectType = type
      this.types = type
      if (type.toString() === '60400') {
        this.isShowOrHideTrve = true
      }
      this.$nextTick(() => {
        this.$refs.selectDown.setValue(0)
      })
    },
    edit (record, type, typeName) {
      this.isCreat = false
      this.title = `编辑${typeName}[${record.accNumber}]`
      this.form.resetFields()
      this.employeeList = []
      this.visible = true
      this.id = record.id
      this.selectType = type
      this.types = type
      if (type.toString() === '60400') {
        this.isShowOrHideTrve = true
      }
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['accDate'])
        this.$refs.selectDown.setValue(record.accountTitleId)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.id
          if (this.selectType.length < 3) {
            this.typeValue = this.selectType.toString()
            values.type = this.typeValue
          }
          this.$http.post('/inspection/modifyInspection', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                if (this.id === -1) {
                  this.$message.success('添加成功')
                } else {
                  this.$message.success('更新成功')
                }
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : this.id === -1 ? '添加失败' : '更新失败')
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleSearch (value) {
      this.$http.get('/project/getBaseEmployeeSelect', { params: { ename: value } })
        .then(res => {
          this.employeeList = res.data
        })
    },
    handleChange (value) {
      this.enumber = value
    },
    getSelectVal (val) {
      this.form.setFieldsValue({ accountTitleId: val })
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
