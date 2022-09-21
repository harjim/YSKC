<template>
  <a-modal
    title="审核"
    :width="500"
    :zIndex="1001"
    :getContainer="popupContainer"
    style="top: 0px;"
    v-model="visible"
    :maskClosable="false"
    :footer="null">
    <div id="box">
      <a-spin tip="请稍后..." :spinning="spin">
        <a-form id="form" :form="form">
          <a-form-item label="审批意见">
            <a-textarea
              v-decorator="['suggestion', {rules:[{required: true, whitespace:true,min: 5, message: '请输入5个字以上审批意见'}]}]"
              placeholder="请输入审批意见"
              :rows="4"
              :maxLength="200">
            </a-textarea>
          </a-form-item>
          <div style="text-align:center;">
            <a-popconfirm
              style="margin-right:12px;"
              title="您确定要驳回审核吗?"
              @confirm="audit(false)"
            >
              <a-button class="reject">驳回</a-button>
            </a-popconfirm>
            <a-popconfirm
              title="您确定要通过审核吗?"
              @confirm="audit(true)"
            >
              <a-button class="success">通过</a-button>
            </a-popconfirm>
          </div>
        </a-form>
      </a-spin>
    </div>
  </a-modal>
</template>
<script>
import { popupContainer } from '@/views/customer/modules/AuditProgress/modules/js/screenFullMountDom'

export default {
  data () {
    return {
      spin: false,
      visible: false,
      instanceIds: [],
      form: this.$form.createForm(this)
    }
  },
  methods: {
    popupContainer,
    open (instanceIds) {
      this.instanceIds = instanceIds
      this.visible = true
      this.form.resetFields()
    },
    audit (pass) {
      this.spin = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (errors) {
          this.spin = false
          return
        }
        this.$http.post('/projectProgress/projectAudits', {
          suggestion: values.suggestion,
          instanceIds: this.instanceIds,
          pass
        }).then(res => {
          if (res.success && res.data) {
            this.$message.success('操作成功')
            this.visible = false
            this.$emit('ok')
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '审核失败')
          }
          this.spin = false
        })
      })
    }
  }
}
</script>
<style lang="less" scoped>
.hover(@color:#fff) {
  color: #fff;
  box-shadow: #fff;
  border: 1px solid #fff;
  background: @color;
}

#form {
  .reject {
    &:hover {
      .hover(red);
    }
  }

  .success {
    &:hover {
      .hover(#1890ff);
    }
  }
}
</style>
