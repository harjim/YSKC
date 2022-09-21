<!--
 * @Author: ldx
 * @Date: 2021-01-15 13:55:43
 * @LastEditTime: 2021-02-24 16:10:26
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\project\modules\ProcessDoc\modules\PreviewModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="800"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    style="top:0px;"
    :bodyStyle="{ height: '80vh', maxHeight: '80vh', overflowY: 'auto' }"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <a-spin id="spin" :spinning="spinning" style="height:100%;">
      <div v-html="html"></div>
    </a-spin>
    <template slot="footer">
      <a-button type="primary" @click="handleSubmit">
        关闭
      </a-button>
    </template>
  </a-modal>
</template>
<script>
export default {
  name: '',
  data () {
    return {
      isVisible: false,
      spinning: true,
      title: '',
      docId: '',
      paramObj: '',
      html: ''
    }
  },
  methods: {
    show (title, paramObj) {
      this.paramObj = paramObj
      this.isVisible = true
      this.spinning = true
      this.gethtml()
      // this.isVisible = true
      this.title = '预览'
    },
    afterClose () {
      this.html = ''
      this.docId = ''
      this.title = ''
    },
    gethtml () {
      this.$http.get('/projectDocFileData/previewAllDoc', { params: this.paramObj }).then((res) => {
        if (res.data && res.success) {
          this.html = res.data
        } else {
          this.$message.error(res.errorMessage)
          this.isVisible = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally((res) => {
        this.spinning = false
      })
    },
    handleSubmit () {
      this.isVisible = false
      this.html = ''
    }
  }
}
</script>
<style lang='less' scoped>
.ant-spin-nested-loading {
  height: 100%;
}
#spin /deep/ .ant-spin-container {
  height: 100%;
}
</style>
