<template>
  <a-modal
    :title="title"
    :width="500"
    :maskClosable="false"
    v-model="visible"
    @ok="handleSubmit"
    @cancel="visible = false"
    :afterClose="afterClose"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form">
      <a-form-item label="父项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-select placeholder="请选择父项目" @change="select_change" v-decorator="['parentId', {rules:[{required: true, message: '请选择父项目'}]}]">
          <a-select-option :value="item.id" v-for="item in parentProject" :key="item.id">{{ item.rdTitle }} {{ item.id > 0 ? '-': '' }} {{ item.pname }}</a-select-option>
        </a-select>
      </a-form-item >
      <a-form-item v-if="isShowRD" label="RD" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input-group compact>
          <span style="margin-top:5px">{{ project.beginYear }} RD&nbsp;</span>
          <a-input-number
            :min="1"
            style="width: 120;"
            placeholder="请输入RD"
            v-decorator="['rdIndex', {rules:[{required: true, message: '请输入RD编号'}]}]"
            @blur="o=>checkRD(o)"
          />
        </a-input-group>
      </a-form-item>
    </a-form>
    <template slot="footer">
      <a-button @click="visible = false">
        取消
      </a-button>
      <a-button type="primary" @click="handleSubmit">
        确定
      </a-button>
    </template>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      title: '',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      parentProject: [],
      parentId: undefined,
      isSubimt: true,
      isShowRD: false,
      project: undefined,
      planNum: -1,
      existProjectNum: -1
    }
  },
  methods: {
    show (record, planNum, existProjectNum, currentYear) {
      this.isShowRD = false
      this.title = '移出项目'
      this.visible = true
      this.project = record
      this.planNum = planNum
      this.existProjectNum = existProjectNum
      this.form.resetFields()
      const projectIds = [ record.parentId ]
      if (record.beginYear !== currentYear) {
        this.parentProject.unshift({ id: 0, pname: '无' })
      } else {
        this.$http.get('/project/queryParentList', { params: { projectIds, currentYear } }).then((res) => {
          if (res.success && res.data) {
            this.parentProject = res.data
            this.parentProject.unshift({ id: 0, pname: '无' })
          } else {
            this.$message.info('获取父项目失败，请联系管理员')
          }
        })
      }
    },
    afterClose () {
      this.visible = false
      this.title = ''
      this.project = undefined
      this.isSubimt = true
      this.isShowRD = false
      this.planNum = -1
      this.existProjectNum = -1
      this.parentProject = []
      this.form.resetFields()
    },
    handleSubmit () {
      if (this.submitState) {
        return
      }
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          values.id = this.project.id
          this.$http.post('project/removeProject', values).then(res => {
            if (res.data && res.success) {
              this.visible = false
              this.$message.success('操作成功')
              this.$emit('ok', true)
            } else {
              this.$message.error(res.errorMessage)
            }
          }).catch(error => {
            this.$message.error(error)
          })
        }
      })
    },
    select_change (value, option) {
      if (value === 0) {
        this.isShowRD = true
        if (this.planNum !== -1 && this.planNum < this.existProjectNum) {
          this.$message.info('当前项目数已超过规划数')
        } else if (this.planNum !== -1 && this.planNum === this.existProjectNum) {
          this.$message.info('当前项目数已达到规划数')
        }
      } else {
        this.isShowRD = false
      }
      this.$nextTick(() => {})
    },
    checkRD (evet) {
      return this.$http.get('/project/checkRD', { params: { rdIndex: evet.target.value, year: this.project.beginYear } })
        .then(res => {
          if (!res.data) {
            this.form.setFields({ 'rdIndex': { value: evet.target.value, errors: [new Error('该年份存在相同RD,请重新输入。')] } })
            this.submitState = true
          } else {
            this.form.setFields({ 'rdIndex': { value: evet.target.value } })
            this.submitState = false
          }
          return res.data
        })
    }
  }
}
</script>

<style>

</style>
