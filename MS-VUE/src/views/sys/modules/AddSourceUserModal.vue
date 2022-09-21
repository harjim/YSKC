<template>
  <a-modal
    :width="500"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    @cancel="visible = false"
  >
    <search-select
      style="width: 440px;margin:20px 0;"
      url="/user/userForSelect"
      searchField="realName"
      :multiple="true"
      sTitle="realName"
      placeholder="请选择人员"
      v-model="selectUsers"
    />
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
      title: '',
      selectUsers: [],
      sourceId: undefined,
      confirmLoading: false
    }
  },
  methods: {
    show (sourceId, sourceName) {
      this.selectUsers = []
      this.sourceId = sourceId
      this.title = `添加[${sourceName}]订阅人员`
      this.visible = true
    },
    handleSubmit () {
      if (!this.selectUsers.length) {
        this.visible = false
        return
      }
      this.confirmLoading = true
      const params = {
        sourceId: this.sourceId,
        userIds: this.selectUsers.map(a => a.id)
      }
      this.$http.post('/policySource/addSubscription', params).then(res => {
        if (res.success && res.data) {
          this.$message.success('操作成功')
          this.$emit('ok', true)
          this.visible = false
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '操作失败')
        }
        this.confirmLoading = false
      })
    }
  }
}
</script>
<style>
</style>
