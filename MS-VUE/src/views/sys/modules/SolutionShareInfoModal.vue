<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="600"
    v-model="visible"
    :maskClosable="false"
    :footer="null"
  >
    <a-spin :spinning="spinning" tip="请稍后...">
      <vxe-grid
        id="solutionShareInfo"
        row-id="id"
        ref="table"
        size="small"
        :data="tableData"
        highlight-hover-row
        highlight-current-row
        show-overflow
        resizable
        auto-resize
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
        :toolbar="{
          custom: true,
          zoom:true,
          refresh: { query: initData }
        }"
      >
        <template #buttons>
          <a-button type="primary" @click="$refs.solutionShareModal.open([solutionId])" style="margin-right:10px;">添加分享</a-button>
          <a-button type="primary" @click="batchDelete()" :disabled="selectRowIds.length <=0" style="margin-right:10px;">移除分享</a-button>
        </template>
        <vxe-table-column
          type="checkbox"
          width="40"
          align="center"
          header-align="center"></vxe-table-column>
        <vxe-table-column
          type="seq"
          width="50"
          title="序号"
          header-align="center"/>
        <vxe-table-column field="type" width="100" title="分享类型" align="center" header-align="center">
          <template #default="{row}">
            {{ types[row.type] }}
          </template>
        </vxe-table-column>
        <vxe-table-column field="realName" width="200" title="名称" align="left" header-align="center"/>
        <vxe-table-column field="createTime" width="160" title="分享时间" align="center" header-align="center"/>
      </vxe-grid>
    </a-spin>
    <div style="margin-top:20px;text-align:right;">
      <a-button @click="closeModal">关闭</a-button>
    </div>
    <solution-share-modal ref="solutionShareModal" @ok="initData"/>
  </a-modal>
</template>

<script>
import SolutionShareModal from './SolutionShareModal'
export default {
  components: {
    SolutionShareModal
  },
  data () {
    return {
      types: ['人员', '部门'],
      visible: false,
      solutionId: undefined,
      title: '',
      tableData: [],
      spinning: false,
      selectRowIds: []
    }
  },
  methods: {
    open (solutionId, title) {
      this.solutionId = solutionId
      this.title = `[${title}]分享列表`
      this.visible = true
      this.initData()
    },
    initData () {
      this.spinning = true
      this.$http.get('/solution/getShareInfo', { params: { solutionId: this.solutionId } }).then(res => {
        if (res.success && res.data) {
          this.tableData = res.data
        } else {
          this.tableData = []
          this.$message.error(res.errorMessage || '加载分享列表失败')
        }
        this.spinning = false
      })
    },
    selectChange ({ records }) {
      this.selectRowIds = records.map(a => a.id)
    },
    batchDelete () {
      const self = this
      this.$confirm({
        title: '您确定要移除选中的分享数据吗?',
        onOk () {
          self.executorDelete(self.selectRowIds)
        },
        onCancel () {
        }
      })
    },
    executorDelete (ids) {
      this.spinning = true
      this.$http.post('/solution/deleteShare', { ids }).then(res => {
        if (res.success && res.data) {
          this.initData()
          this.$refs.table.setAllCheckboxRow(false)
          this.$message.success('移除成功')
        } else {
          this.tableData = []
          this.$message.error(res.errorMessage || '移除失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    closeModal () {
      this.visible = false
      this.spinning = false
      this.title = ''
      this.solutionId = ''
      this.tableData = []
    }
  }
}
</script>

<style>

</style>
