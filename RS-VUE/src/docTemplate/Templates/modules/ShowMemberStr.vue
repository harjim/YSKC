<template>
  <span>{{ memberStr }}</span>
</template>
<script>
export default {
  props: {
    projectId: {
      type: Number,
      default: undefined
    },
    docId: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      memberStr: ''
    }
  },
  watch: {
    projectId (id) {
      this.loadMember()
    }
  },
  methods: {
    loadMember () {
      this.memberStr = ''
      if (!this.projectId || this.projectId < 0) {
        return
      }
      this.$http.get('/projectDocFileData/getMemberStr', { params: { projectId: this.projectId, pDocFileId: this.docId } })
        .then(res => {
          if (res.data) {
            this.memberStr = res.data
          }
        })
    }
  }
}
</script>
