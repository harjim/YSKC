<template>
  <a-modal
    title="分享解决方案"
    style="top: 20px;"
    :width="600"
    v-model="visible"
    @ok="handleSubmit"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form">
      <a-row :gutter="24">
        <a-form-item label="分享类型" :labelCol="{span:4}" :wrapperCol="{span:19}">
          <a-radio-group v-model="type">
            <a-radio :value="0">分享给人员</a-radio>
            <a-radio :value="1">分享给部门</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-row>
      <a-row :gutter="24">
        <a-form-item label="分享" :labelCol="{span:4}" :wrapperCol="{span:19}">
          <template v-if="type === 1">
            <dept-select
              style="width:100%;"
              :multiple="true"
              placeholder="请选择"
              v-decorator="['deptIds', {rules:[{required: true, message: '请选择'}]}]" />
          </template>
          <template v-else>
            <search-select
              style="width:100%;"
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              :multiple="true"
              placeholder="请选择"
              v-decorator="['userIds', {rules:[{required: true, message: '请选择'}]}]"
            />
          </template>
        </a-form-item></a-row>
    </a-form>
  </a-modal>
</template>

<script>
import { DeptSelect, SearchSelect } from '@/components/Selects'
export default {
  components: {
    DeptSelect,
    SearchSelect
  },
  data () {
    return {
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      ids: undefined,
      type: 0
    }
  },
  watch: {
    type (t) {
      this.$nextTick(() => {
        this.form.resetFields()
      })
    }
  },
  methods: {
    open (ids) {
      this.ids = ids
      this.confirmLoading = false
      this.visible = true
      this.$nextTick(() => {
        this.form.resetFields()
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          const dataIds = []
          if (this.type === 1) {
            if (!values.deptIds || !values.deptIds.length) {
              this.$message.error('操作失败，请重新选择')
              return
            }
            dataIds.push(...values.deptIds)
          } else {
            if (!values.userIds || !values.userIds.length) {
              this.$message.error('操作失败，请重新选择')
              return
            }
            values.userIds.forEach(a => dataIds.push(a.id))
          }
          const datas = []
          this.ids.forEach(id => {
            dataIds.forEach(dId => {
              datas.push({ solutionId: id, dataId: dId, type: this.type })
            })
          })
          this.$http.post('/solution/share', datas).then(res => {
            if (res.success && res.data) {
              this.visible = false
              this.$emit('ok')
            } else {
              this.$message.error(res.errorMessage || '分享失败')
            }
          }).finally(() => {
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

<style>

</style>
