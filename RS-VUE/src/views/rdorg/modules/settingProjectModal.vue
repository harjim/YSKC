<template>
  <a-modal
    :title="title"
    :visible="visible"
    @ok="handleOk"
    width="600px"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @cancel="closeModal"
  >
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form :form="form">
        <a-row>
          <a-form-item label="项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              v-decorator="['projectIds', {rules:[{required: true, message: '请选择项目'}]}]"
              style="width: 100%;"
              mode="multiple"
              :maxTagCount="5"
              :dropdownStyle="{ maxHeight: '420px', maxWidth: '100%' }"
              placeholder="请选择项目"
            >
              <a-select-option
                v-for="p in projects"
                :key="p.id"
                :value="p.id"
              >{{ `${p.rdTitle}-${p.pname}` }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
  props: {
    url: {
      type: String,
      required: true
    },
    title: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      form: this.$form.createForm(this),
      visible: false,
      spinning: false,
      confirmLoading: false,
      keys: [],
      year: undefined,
      projects: []
    }
  },
  methods: {
    open (keys, year) {
      if (this.year !== year) {
        this.year = year
        this.loadProject()
      }
      this.keys = keys
      this.visible = true
      this.$nextTick(() => {
        this.form.resetFields()
      })
    },
    loadProject () {
      this.spinning = true
      this.$http.get('/project/getYearSelectList', { params: { year: this.year } })
        .then(res => {
          if (res.data != null && res.data.length > 0) {
            this.projects = res.data
          } else {
            this.projects = []
          }
          this.spinning = false
        })
    },
    handleOk () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          this.$http.post(this.url, { ids: values.projectIds, keys: this.keys, year: this.year }).then(res => {
            if (res.data) {
              this.$message.success('分配成功')
              this.$emit('ok', false)
              this.closeModal()
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : '分配失败')
              this.confirmLoading = false
            }
          })
        } else {
          this.confirmLoading = false
        }
      })
    },
    closeModal () {
      this.keys = []
      this.visible = false
      this.spinning = false
      this.confirmLoading = false
    }
  }
}
</script>

<style>

</style>
