<template>
  <a-modal
    :title="title"
    style="top: 20px;height:600px;"
    :width="600"
    v-model="visible"
    @ok="close"
    @cancel="close"
    :maskClosable="false"
  >
    <a-spin :spinning="spinning" tip="请稍等...">
      <div style="height:600px;overflow:auto;scroll-x:none;"><span>{{ msg }}</span></div>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      spinning: false,
      msg: '',
      title: ''
    }
  },
  methods: {
    show (row) {
      this.title = `运行[${row.sname}-${row.stype}]`
      this.visible = true
      this.runPolicy(row.id)
    },
    runPolicy (id) {
      this.spinning = true
      this.$http.get('/policySource/runPolicy', { params: { sourceId: id } }).then(res => {
        if (res.success) {
          this.msg = res.data
        } else {
          this.msg = res.errorMessage
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    close () {
      this.visible = false
      this.msg = ''
    }
  }
}
</script>

  <style>

  </style>
