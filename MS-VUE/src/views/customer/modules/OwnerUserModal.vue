<template>
  <a-modal
    :width="431"
    :zIndex="100"
    :visible="visible"
    :dialog-style="{ top: '300px' }"
    title="指定业务员"
    destroyOnClose
    @ok="handleSubmit"
    @cancel="close()"
    :maskClosable="false"
  >
    <a-spin tip="请稍后..." :spinning="spin">
      <a-form layout="inline" :form="form">
        <a-form-item label="业务员">
          <mini-select
            style="width: 300px"
            placeholder="请输入业务员查询"
            url="/user/userForSelect"
            sKey="id"
            :sTitle="['realName', 'userName']"
            queryKey="realName"
            ref="miniCustomer"
            @change="
              (v, row) => {
                userId = v
              }
            "
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
import { DeptSelectOnly, MiniSelect } from '@/components'

export default {
  components: {
    MiniSelect,
    DeptSelectOnly
  },
  data () {
    return {
      spin: false,
      visible: false,
      customerIds: [],
      userId: 0,
      form: this.$form.createForm(this)
    }
  },
  methods: {
    showModal (customerIds) {
      this.customerIds = customerIds
      this.visible = true
      // this.search()
    },
    handleSubmit () {
      this.spin = true
      this.$http
        .post('/customer/setOwnerUser', { userId: this.userId, customerIds: this.customerIds })
        .then((res) => {
          if (res.success && res.data) {
            this.$message.success('指定业务员成功')
            this.$emit('ok', false)
            this.visible = false
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '指定业务员失败')
          }
        })
        .finally(() => {
          this.spin = false
        })
    },
    close (events) {
      this.visible = false
    }
  }
}
</script>

<style scoped>
</style>
