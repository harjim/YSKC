<!--
 * @Author: zdf
 * @Date: 2022-04-28 13:59:33
 * @LastEditTime: 2022-05-07 08:17:21
 * @LastEditors: zdf
 * @Description: 费用提交
 * @FilePath: \RS-VUE\src\views\project\dataAggregation\modules\RdFeeSubmit.vue
-->
<template>
  <a-popconfirm v-if="isMsUser && $auth('project:data:submit')" title="是否确定提交?" @confirm="submit()">
    <a-button type="primary" :loading="loading">提交</a-button>
  </a-popconfirm>
</template>

<script>
import { isMsUser } from '@/utils/processDoc/auditStatus'
import moment from 'moment'
export default {
  props: {
    projectId: {
      type: Number,
      required: true
    },
    month: {
      type: String,
      required: true
    },
    rdType: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      isMsUser: isMsUser(),
      loading: false
    }
  },
  methods: {
    moment,
    submit () {
      this.loading = true
      this.$http.post('/projectAudit/submitRdFeeAudit', { projectId: this.projectId, month: moment(this.month + '-01 00:00:00'), rdType: this.rdType }).then(res => {
        if (res.success && res.data) {
          this.$message.success('提交成功')
          this.$emit('getSummary')
        } else {
          this.$message.error(res.errorMessage || '提交失败')
        }
      }).finally(() => {
        this.loading = false
      })
    }
  }
}
</script>

<style>

</style>
