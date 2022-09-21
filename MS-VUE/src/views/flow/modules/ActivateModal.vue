<!--
 * @Author: zdf
 * @Date: 2022-04-07 16:44:25
 * @LastEditTime: 2022-05-06 17:00:05
 * @LastEditors: zdf
 * @Description:
 * @FilePath: \MS-VUE\src\views\flow\modules\ActivateModal.vue
-->
<template>
  <a-modal title="激活" v-model="visible" @ok="handleSubmit" :okButtonProps="{props: {loading: confirmLoading}}" destroyOnClose>
    <a-form :form="form" v-bind="{ labelCol, wrapperCol }">
      <a-form-item label="激活说明" :help="()=>{const r = form.getFieldValue('suggestion'); return `(${!r? 0 : r.length > 200 ? 200 : r.length}/200)`}" style="margin-bottom: 0px;">
        <a-textarea
          :maxLength="200"
          :rows="3"
          v-decorator="['suggestion', {rules:[{required: true, whitespace:true,min: 5, message: '请输入5个字以上激活说明'}]}]"
          placeholder="请输入激活说明"
        />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { SearchSelect } from '@/components/Selects'

export default {
  components: {
    SearchSelect
  },
  data () {
    return {
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      labelCol: {
        span: 5
      },
      wrapperCol: {
        span: 19
      },
      instanceIds: undefined
    }
  },
  methods: {
    show (ids) {
      this.confirmLoading = false
      this.visible = true
      this.instanceIds = ids
    },
    handleSubmit () {
      this.confirmLoading = true
      this.form.validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        const parmas = {
          instanceIds: this.instanceIds,
          suggestion: values.suggestion
        }
        this.$http.post('/flowList/activates', parmas)
          .then(res => {
            if (res.success && res.data) {
              this.$message.success('操作成功')
              this.visible = false
              this.$emit('ok', false)
            } else {
              this.$message.error(res.errorMessage || '操作失败')
            }
          }).finally(() => {
            this.confirmLoading = false
          })
      })
    }
  }
}
</script>
