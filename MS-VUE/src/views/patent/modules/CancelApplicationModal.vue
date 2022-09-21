<!--
    * @Author:
    * @Date: 2022-07-05 10:54:15
    * @Description:
-->
<template>
  <a-modal
    title="取消申请"
    centered
    :maskClosable="false"
    :visible="visible"
    @cancel="handleCancel"
  >
    <template #footer>
      <a-button key="back" @click="handleCancel">
        取消
      </a-button>

      <a-popconfirm
        :visible="makeSurePop"
        title="您确定要取消申请吗"
        ok-text="确认"
        cancel-text="取消"
        @confirm="handleSubmit"
        @cancel="makeSurePop = false"
      >
        <a-button key="submit" type="primary" :loading="loading" @click="makeSure">确认</a-button>
      </a-popconfirm>
    </template>
    <a-form id="form" :form="form">
      <a-row :gutter="24" style="margin-bottom: 0;">
        <a-col :span="24">
          <a-form-item
            label="取消申请原因"
            :labelCol="{ xs: { span: 24 },sm: { span:6}}"
            :wrapperCol="{ xs: { span: 24 },sm: { span: 18 }}"
          >
            <a-textarea
              placeholder="请输入取消申请原因"
              allow-clear
              :autoSize="{ minRows: 5 }"
              style="width: 94%;"
              v-decorator="['suggestion', {rules:[{required: true, whitespace:true,min: 5, message: '请输入5字以上取消申请原因'}]}]"
            />
          </a-form-item>
        </a-col>
      </a-row>

    </a-form>
  </a-modal>
</template>

<script>
// import { projectAudits } from '@/api/patent/patent'

export default {
  data () {
    return {
      visible: false,
      loading: false,
      makeSurePop: false,
      form: this.$form.createForm(this),
      params: {
      }
    }
  },
  methods: {
    open (PatentPlanArr) {
      this.form.resetFields()
      this.params.patentIds = PatentPlanArr.filter(
        elem => !elem.status
      ).map(elem => elem.id)
      this.visible = true
    },
    makeSure () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          this.makeSurePop = true
          Object.assign(this.params, values)
        } else {
          this.makeSurePop = false
        }
      })
    },
    handleSubmit () {
      this.makeSurePop = false
      this.loading = true
      this.$http.post('/projectProgress/cancelPatent', this.params).then(data => {
        if (data.success) {
          this.$message.success('取消申请成功')
          this.visible = false
          this.$emit('ok')
        } else {
          this.$message.error(data.errorMessage || '取消申请失败')
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      }).finally(() => {
        this.loading = false
      })
      // projectAudits(this.params).then(data => {
      //   if (data) {
      //     this.$message.success('操作成功')
      //     this.$emit('ok')
      //     this.visible = false
      //   }
      // }).catch(error => {
      //   this.$message.error(error.message || '系统异常，请联系管理员！')
      // }).finally(() => {
      //   this.loading = false
      // })
    },
    handleCancel () {
      this.visible = false
    }
  }
}
</script>

<style lang='css' scoped>

</style>
