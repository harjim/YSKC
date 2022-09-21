<!--
 * @Author: ldx
 * @Date: 2020-12-17 14:52:16
 * @LastEditTime: 2020-12-23 08:37:22
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\flow\modules\addFlow.vue
--><template>
  <a-modal
    :title="title"
    :width="500"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @cancel="afterClose"
    @ok="handleSubmit">
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="流程名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入流程名称" v-decorator="['name',{rules: [{ required: true, whitespace: true, message: '请输入流程名称' }] }]"></a-input>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
export default {
  name: 'AddFlow',
  data () {
    return {
      isVisible: false,
      title: '',
      spinning: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 8 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 16 },
        sm: { span: 20 }
      }
    }
  },
  methods: {
    show (title) {
      this.isVisible = true
      this.title = title
    },
    afterClose () {
      this.isVisible = false
      this.form.resetFields()
    },
    handleSubmit () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          this.$http.post('/flow/addFlow', { name: values.name }).then((res) => {
            if (res.data && res.success) {
              this.$message.success('操作成功！')
              this.$emit('refreshTable')
              this.afterClose()
            } else {
              this.$message.error(res.errorMessage)
            }
          }).catch((error) => {
            this.$message.error(error.message)
          }).finally((res) => {
            this.confirmLoading = false
          })
        }
      })
    }
  }
}
</script>
<style lang='less' scoped>
</style>
