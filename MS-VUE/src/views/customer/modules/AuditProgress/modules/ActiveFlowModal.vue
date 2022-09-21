<!--
 * @Author: ldx
 * @Date: 2021-01-30 09:10:31
 * @LastEditTime: 2021-03-12 16:25:03
 * @LastEditors: ldx
 * @Description: 激活modal
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\ActiveFlowModal.vue
-->
<template>
  <a-modal
    :title="title"
    :zIndex="1001"
    :width="500"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    :getContainer="popupContainer"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <a-form :form="form">
      <a-form-item label="激活说明">
        <a-textarea v-decorator="['suggestion', {rules:[{required: true, whitespace:true,min: 5, message: '请输入5个字以上激活说明'}]}]" placeholder="请输入激活说明" :rows="4" :extra="onComputeTextarea('remark',200)"></a-textarea>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
import { popupContainer } from './js/screenFullMountDom'
export default {
  name: 'ActiveFlowModal',
  data () {
    return {
      isVisible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      title: ''
    }
  },
  props: {
    mode: {
      type: Object,
      default: () => { return {} }
    }
  },
  methods: {
    popupContainer,
    show (title) {
      this.form.resetFields()
      this.isVisible = true
      this.title = title
    },
    afterClose () {
      this.isVisible = false
      this.title = ''
    },
    // 提交激活流程
    handleSubmit () {
      this.form.validateFields((error, values) => {
        if (!error) {
          this.confirmLoading = true
          const paramObj = {
            suggestion: values.suggestion,
            instanceId: this.mode.instanceId,
            nodeId: this.mode.curNodeId
          }
          this.$http.post('/projectProgress/activateFlow', paramObj).then((res) => {
            if (res.data && res.success) {
              this.$message.success('操作成功')
              this.$emit('refresh')
              this.mode.status = 3
              this.confirmLoading = false
              this.isVisibleActiveFlow = false
              this.activeFlowTitle = undefined
              this.isVisible = false
            } else {
              this.$message.error(res.errorMessage)
              this.confirmLoading = false
            }
          }).catch((error) => {
            this.$message.error(error.message)
          }).finally(() => {
            this.confirmLoading = false
          })
        }
      })
    },
    // 计算文本域的个数
    onComputeTextarea (fieldName, limitNumber = 200) {
      const content = this.form.getFieldValue(fieldName)
      const contentLenght = content ? content.length : 0
      if (contentLenght > limitNumber) {
        const obj = {}
        obj[fieldName] = { value: content.substring(0, limitNumber) }
        this.form.setFields(obj)
      }
      return `(${contentLenght}/${limitNumber})`
    }
  }
}
</script>
<style lang='less' scoped>
</style>
