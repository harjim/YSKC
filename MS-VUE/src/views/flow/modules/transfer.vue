<!--
 * @Author: your name
 * @Date: 2022-03-16 15:48:12
 * @LastEditors: zdf
 * @LastEditTime: 2022-04-07 16:47:24
 * @Description: 转交
 * @FilePath: \MS-VUE\src\views\flow\modules\transfer.vue
-->
<template>
  <a-modal :title="title" v-model="visible" @ok="handleSubmit" :okButtonProps="{props: {loading: confirmLoading}}" destroyOnClose>
    <a-form :form="form" v-bind="{ labelCol, wrapperCol }">
      <a-form-item label="处理人">
        <search-select
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="true"
          placeholder="请输入处理人"
          v-decorator="['userIds', {rules:[{required: true, message: '请输入处理人'}]}]"
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
      title: '转交',
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
      this.form.validateFields((errors, values) => {
        if (!errors) {
          const parmas = {
            instanceIds: this.instanceIds,
            userIds: values.userIds.map(item => item.id)
          }
          this.confirmLoading = true
          this.$http.post('/flowList/deliverMaster', parmas)
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
        }
      })
    }
  }
}
</script>
