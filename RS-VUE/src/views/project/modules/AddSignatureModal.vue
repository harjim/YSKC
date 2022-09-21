<!--
 * @Author: your name
 * @Date: 2021-11-18 14:02:39
 * @LastEditTime: 2022-09-02 14:14:46
 * @LastEditors: error: git config user.name && git config user.email & please set dead value or install git
 * @Description: 签名汇总modal
 * @FilePath: \RS-VUE\src\views\project\modules\AddSignatureModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="600"
    v-model="visible"
    :maskClosable="false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :afterClose="closeModal"
  >
    <a-form :form="form">
      <a-row>
        <a-col :span="24">
          <a-form-item label="项目" :labelCol="{ sm: { span: 2 } }" :wrapperCol="{ sm: { span: 21 } }">
            <a-select
              @change="(v, event) => handleChange(v, event)"
              :disabled="isEdit"
              placeholder="请选择项目"
              v-decorator="['projectId', {rules: [{required: true, message: '请输入项目名称'}]}]"
              allowClear>
              <a-select-option
                v-for="item in rdList"
                :key="item.projectId"
                :value="item.projectId"
              >{{ `${item.rdTitle}-${item.pname}` }}</a-select-option
              >
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="编制" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px">
            <select-signature
              :selected="footer.toCompile"
              :year="year"
              :isRdAndCommittee="true"
              @select="toCompile => OnSelect('toCompile', toCompile)"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="审核" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px">
            <select-signature
              :selected="footer.audit"
              :year="year"
              :isRdAndCommittee="true"
              @select="audit => OnSelect('audit', audit)"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="批准" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 10px">
            <select-signature
              :selected="footer.approval"
              :year="year"
              :isRdAndCommittee="true"
              @select="approval => OnSelect('approval', approval)"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
import ProjectSelect from '@/components/ProjectSelect'
import SelectSignature from './SelectSignature'
export default {
  components: {
    ProjectSelect,
    SelectSignature
  },
  mixins: [yearMiXin],
  props: {
    year: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      visible: false,
      title: '',
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 19 }
      },
      rdList: [],
      isEdit: false,
      confirmLoading: false,
      id: undefined,
      postContent: undefined,
      footer: {
        toCompile: undefined,
        audit: undefined,
        approval: undefined
      }
    }
  },
  methods: {
    add () {
      this.id = undefined
      this.title = '添加项目签名'
      this.isEdit = false
      this.visible = true
      this.getRDList()
      this.$nextTick(() => {
        this.form.resetFields()
      })
    },
    edit (row) {
      this.title = `编辑[${row.rdTitle}]`
      this.isEdit = true
      this.id = row.id
      this.visible = true
      this.footer.toCompile = { ename: row.toCompile, enumber: row.toCompileEnumber }
      this.footer.audit = { ename: row.auditor, enumber: row.auditEnumber }
      this.footer.approval = { ename: row.approval, enumber: row.approvalEnumber }
      this.$nextTick(() => {
        this.$initForm(this.form, {
          projectId: [row.projectId] })
      })
    },
    getRDList () {
      this.$http.get('/project/getSignatureList', { params: { year: this.currentYear } }).then(res => {
        if (res.success) {
          this.rdList = res.data || []
        } else {
          this.$message.error(res.errorMessage || '获取数据失败')
        }
      })
    },
    OnSelect (field, selectedObject) {
      if (selectedObject) {
        this.footer[field] = selectedObject
        this.footer[field]['enumber'] = selectedObject.enumber
        this.footer[field]['ename'] = selectedObject.ename
      } else {
        this.footer[field] = {}
        this.footer[field]['enumber'] = ''
        this.footer[field]['ename'] = ''
      }
    },
    notNull (value) {
      return !(value === null || value === '' || value === undefined)
    },
    handleSubmit () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        let url = '/signatureSummary/addDocFileFooter'
        if (this.isEdit) {
          url = '/signatureSummary/editDocFileFooter'
          values.id = this.id
        }
        if (Array.isArray(values.projectId)) {
          values.projectId = values.projectId[0]
        }
        values.year = this.currentYear
        values.toCompileEnumber = this.footer.toCompile.enumber
        values.auditEnumber = this.footer.audit.enumber
        values.approvalEnumber = this.footer.approval.enumber
        if (!(this.notNull(values.toCompileEnumber) || this.notNull(values.auditEnumber) || this.notNull(values.approvalEnumber))) {
          this.confirmLoading = false
          this.$message.error('编制、审核、批准人员不可都为空！')
          return
        }
        this.$http.post(url, values).then(res => {
          const modify = this.isEdit ? '更新' : '添加'
          if (res.success && res.data) {
            this.$message.success(`${modify}成功`)
            this.$emit('ok', this.isEdit)
            this.closeModal()
          } else {
            this.$message.error(res.errorMessage || `${modify}失败，请联系管理员`)
          }
          this.confirmLoading = false
        })
      })
    },
    closeModal () {
      this.visible = false
      this.confirmLoading = false
      this.id = undefined
      this.title = ''
      this.footer.toCompile = undefined
      this.footer.audit = undefined
      this.footer.approval = undefined
    },
    handleChange (v, event) {
      const rd = this.rdList.find(item => item.projectId === v)
      this.footer.toCompile = { ename: rd.toCompile, enumber: rd.toCompileEnumber }
      this.footer.audit = { ename: rd.auditor, enumber: rd.auditEnumber }
      this.footer.approval = { ename: rd.approval, enumber: rd.approvalEnumber }
    }
  }
}
</script>

<style></style>
