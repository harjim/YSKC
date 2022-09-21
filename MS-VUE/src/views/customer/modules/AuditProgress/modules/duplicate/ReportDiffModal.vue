<template>
  <a-modal
    title="立项报告"
    :width="1280"
    :height="600"
    :visible="visible"
    :maskClosable="false"
    style="top: 0px;"
    :destroyOnClose="true"
    :footer="null"
    :mask="false"
    @cancel="visible = false"
  >
    <div style="display:flex;height: 600px;">
      <a-card size="small" :title="left.title" style="width: 50%">
        <div
          style="height:540px;overflow: auto;"
          v-html="left.content"
        ></div>
      </a-card>
      <a-card size="small" :title="right.title" style="width: 50%">
        <div
          style="height:540px;overflow: auto;"
          v-html="right.content"
        ></div>
      </a-card>
    </div>
  </a-modal>
</template>
<script>
export default {
  data () {
    return {
      visible: false,
      left: {
        docId: 0,
        title: '',
        content: ''
      },
      right: {
        docId: 0,
        title: '',
        content: ''
      },
      source: undefined,
      target: undefined
    }
  },
  watch: {
    'left.docId' (newVal) {
      if (newVal > 0) {
        this.loadData({
          pDocFileId: this.source.docFileId,
          projectId: this.source.projectId,
          currentYear: this.source.year,
          companyId: this.source.companyId
        }, this.left)
      }
    },
    'right.docId' (newVal) {
      if (newVal > 0) {
        this.loadData({
          pDocFileId: this.target.docId,
          projectId: this.target.projectId,
          currentYear: this.target.year,
          companyId: this.target.companyId
        }, this.right)
      }
    }
  },
  methods: {
    open (source, target) {
      this.left.docId = source.docFileId
      this.right.docId = target.docId

      this.left.title = `${source.rdTitle} ${source.pname}`
      this.right.title = `${target.rdTitle} ${target.pname}--${target.companyName}`
      this.visible = true
      this.source = source
      this.target = target
    },
    loadData (params, targetObj) {
      this.$http.get('/projectProgress/previewFile', { params: params }).then((res) => {
        if (res.data && res.success) {
          targetObj.content = res.data
        } else {
          targetObj.content = `<div style="font-size: 1.5em; font-weight: bold;">${res.errorMessage}</div>`
        }
      }).catch((error) => {
        this.$message.error(error.message)
        targetObj.content = ''
      }).finally((res) => {
      })
    }
  }
}
</script>
