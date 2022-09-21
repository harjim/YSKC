<!--
 * @Author: zdf
 * @Date: 2021-11-18 14:02:39
 * @LastEditTime: 2022-02-24 10:16:46
 * @LastEditors: zdf
 * @Description: 成果modal
 * @FilePath: \RS-VUE\src\views\project\modules\ResultModal.vue
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
        <a-form-item label="成果名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input
            placeholder="请输入成果名称"
            v-decorator="['achievementName', {rules:[{required: true, message: '请输入成果名称'}]}]"/>
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item label="项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <project-select
            style="width:100%"
            ref="creatProject"
            :year="currentYear"
            :sign="2"
            :isJoinPrjectName="true"
            v-decorator="['projectId', { rules: [{ required: true, message: '请选择项目' }] }]"
          ></project-select>
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item label="成果类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择成果类型"
            v-decorator="['type', {initialValue: '3',rules:[{required: true, message: '请选择成果类型'}]}]">
            <a-select-option v-for="(v,k) in types" :key="k" :value="k">{{ v }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item label="成果来源" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择成果来源"
            v-decorator="['source', {initialValue: '0',rules:[{required: true, message: '请选择成果来源'}]}]">
            <a-select-option v-for="(v,k) in sources" :key="k" :value="k">{{ v }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item label="转化形式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select
            placeholder="请选择转化形式"
            v-decorator="['converType', {initialValue: '0',rules:[{required: true, message: '请选择转化形式'}]}]">
            <a-select-option v-for="(v,k) in converTypes" :key="k" :value="k">{{ v }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item label="转化说明" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea
            placeholder="公司为提高“xxx生产力水平”而对“xxx科技成果”所进行的后续试验、开发、应用、推广直至形成“xxx新技术、新工艺、新材料、新产品，发展新产业”;有利于“加快实施创新驱动发展战略，促进科技与经济的结合，有利于提高经济效益、社会效益和保护环境、合理利用资源”。"
            v-decorator="['description', {rules:[{required: false, message: '请输入转化说明'}]}]"
            rows="5"
            :maxLength="2000"
            :help="()=>{const r = form.getFieldValue('description'); return `(${!r? 0 : r.length > 2000 ? 2000 : r.length}/2000)`}"
          ></a-textarea>
        </a-form-item>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
import ProjectSelect from '@/components/ProjectSelect'
export default {
  components: {
    ProjectSelect
  },
  mixins: [yearMiXin],
  props: {
    types: {
      type: Object,
      required: true
    },
    sources: {
      type: Object,
      required: true
    },
    converTypes: {
      type: Object,
      required: true
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
      isEdit: false,
      confirmLoading: false,
      id: undefined
    }
  },
  methods: {
    add () {
      this.id = undefined
      this.title = '添加成果'
      this.isEdit = false
      this.visible = true
      this.$nextTick(() => {
        this.form.resetFields()
      })
    },
    edit (row) {
      this.title = `编辑[${row.achievementName}]`
      this.isEdit = true
      this.id = row.id
      this.visible = true
      this.$nextTick(() => {
        this.$initForm(this.form, { achievementName: row.achievementName,
          projectId: [row.projectId],
          type: row.type,
          source: row.source,
          converType: row.converType,
          description: row.description })
      })
    },
    handleSubmit () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        let url = '/achievement/add'
        if (this.isEdit) {
          url = '/achievement/edit'
          values.id = this.id
        }
        if (Array.isArray(values.projectId)) {
          values.projectId = values.projectId[0]
        }
        values.year = this.currentYear
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
    }
  }
}
</script>

<style>

</style>
