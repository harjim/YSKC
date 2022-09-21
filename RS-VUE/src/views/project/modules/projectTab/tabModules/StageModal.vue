<template>
  <a-modal
    :width="200"
    :visible="visible"
    title="添加项目阶段"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="visible = false"
    :confirmLoading="confirmLoading"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <!-- <a-col :span="24">
            <a-form-item label="阶段类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select v-decorator="['stageKey', {rules:[{required: true, message: '请选择阶段类型'}]}]">
                <a-select-option v-for="item in stageData" :key="item.key">
                  <span v-if="item!=null">{{ item.value }}</span>
                </a-select-option>
              </a-select>
            </a-form-item>
        </a-col>-->
        <a-col :span="24" v-for="(item,index) in stageData" :key="item.key">
          <a-checkbox
            :disabled="disabledKeys[item.key]"
            :checked="allChecks[item.key]"
            @change="onChange(item.key)"
          >{{ (index+1) +'、'+ item.value }}</a-checkbox>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>

export default {
  components: {
  },
  data () {
    return {
      allChecks: {},
      disabledKeys: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      confirmLoading: false,
      projectId: 0,
      visible: false,
      form: this.$form.createForm(this),
      stageData: [],
      typeList: [],
      submitStatus: true
    }
  },
  created () {
    this.$getDictionary(6).then(res => {
      this.stageData = res
    })
  },
  methods: {
    onChange (key) {
      const checked = !this.allChecks[key]
      this.$set(this.allChecks, key, checked)
    },
    add (projectData, typeList) {
      this.form.resetFields()
      this.id = 0
      this.projectId = projectData.id
      this.visible = true
      this.confirmLoading = false
      this.typeList = typeList
      this.disabledKeys = {}
      this.allChecks = {}
      this.submitStatus = true
      if (typeList.length === this.stageData.length) {
        this.submitStatus = false
      }
      typeList.forEach(e => {
        this.$set(this.allChecks, e, true)
        this.$set(this.disabledKeys, e, true)
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      // this.visible = true
      // this.confirmLoading = true
      if (!this.submitStatus) {
        this.visible = false
        this.confirmLoading = false
        return
      }
      validateFields((errors, values) => {
        values.typeList = this.allChecks
        if (!errors) {
          values.projectId = this.projectId
          this.$http.post('/stage/add', values)
            .then(res => {
              if (res.success && res.data) {
                this.visible = false
                this.$emit('ok', true)
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
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
