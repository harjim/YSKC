<template>
  <a-modal
    style="top: 0px;"
    :width="600"
    v-model="visible"
    title="添加阶段文件"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    :bodyStyle="{ maxHeight:'84vh',height: '84vh'}"
  >
    <div style="height: 100%;">
      <vxe-table
        ref="lf"
        :resizable="true"
        auto-resize
        highlight-hover-row
        show-overflow
        size="small"
        :loading="load"
        height="100%"
        show-header-overflow
        :data="docFiles"
        @checkbox-change="selectChange"
        @checkbox-all="selectChange">
        <vxe-table-column align="left" :width="50" type="checkbox"/>
        <vxe-table-column title="序号" align="left" :width="50" type="seq"/>
        <vxe-table-column title="文件名" align="left" field="docName"/>
      </vxe-table>
    </div>
    <template v-slot:footer>
      <div style="text-align:right">
        <a-button @click="()=>visible = false" style="padding-right:12px;">取消</a-button>
        <a-button @click="handleSubmit" style="padding-right:12px;" type="primary">确定</a-button>
      </div>
    </template>
  </a-modal>
</template>
<script>
export default {
  name: 'DocFileListModal',
  data () {
    return {
      load: false,
      docFiles: [],
      visible: false,
      form: this.$form.createForm(this),
      confirmLoading: false,
      selectedRowKeys: [],
      stageKey: ''
    }
  },
  methods: {
    add (stageKey) {
      this.stageKey = stageKey
      this.selectedRowKeys = []
      this.confirmLoading = false
      this.visible = true
      this.getData()
    },
    getData () {
      if (!this.stageKey) {
        return
      }
      this.load = true
      this.$http.get('/docFileStage/getStageNoDocFiles', { params: { stageKey: this.stageKey } }).then(res => {
        if (res.success) {
          if (res.data) {
            this.docFiles = res.data
          } else {
            this.docFiles = []
          }
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '获取文档失败')
        }
      }).finally(() => {
        this.load = false
      })
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(a => a.id)
    },
    handleSubmit () {
      this.confirmLoading = true
      if (!this.selectedRowKeys || !this.selectedRowKeys.length) {
        this.visible = false
        this.confirmLoading = false
        return
      }
      this.$http.post('/docFileStage/saveDocFileStage', { stageKey: this.stageKey, docFileIds: this.selectedRowKeys }).then(res => {
        if (res.success) {
          if (res.data) {
            this.$emit('ok', this.stageKey)
          }
          this.visible = false
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
        }
      }).finally(res => {
        this.confirmLoading = false
      })
    }
  }
}
</script>
