<!--
    * @Author:
    * @Date: 2022-07-06 08:29:09
    * @Description:
-->
<template>
  <a-modal
    :title="`设置【${deptName}】负责人`"
    width="400px"
    :visible="visible"
    @cancel="visible = false"
    centered
    :maskClosable="false"
  >
    <template #footer>
      <a-button key="back" @click="visible = false">
        取消
      </a-button>

      <a-popconfirm
        :title="`您确定要${ addTo? '追加':'重新设置' }负责人吗`"
        ok-text="确认"
        cancel-text="取消"
        @confirm="handleSubmit"
      >
        <a-button key="submit" type="primary" :loading="loading">确认</a-button>
      </a-popconfirm>
    </template>
    <a-row>
      是否追加：<a-checkbox v-model="addTo" />
    </a-row>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      loading: false,
      deptName: null,
      deptId: null,
      userIds: null,
      addTo: false
    }
  },
  methods: {
    open (deptId, deptName, userArr) {
      this.addTo = false
      this.deptId = deptId
      this.deptName = deptName
      this.userIds = userArr.map(elem => elem.id)
      this.loading = false
      this.visible = true
    },
    handleSubmit () {
      const params = { deptId: this.deptId, userIds: this.userIds, addTo: this.addTo }
      this.loading = true
      this.$http.post('/dept/deptAddPrincipal', params).then((data) => {
        if (data.success) {
          this.visible = false
          this.$emit('ok', true)
        } else {
          this.$message.error(data.errorMessage)
        }
      }).catch((err) => {
        this.$message.error(err || '系统异常，请联系管理员')
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>

<style lang='css' scoped>

</style>
