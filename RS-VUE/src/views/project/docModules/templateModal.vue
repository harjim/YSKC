<template>
  <a-modal
    title="选择要添加的文档"
    :width="800"
    :maskClosable="false"
    v-model="visible"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
  >
    <a-table
      rowKey="templateId"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,getCheckboxProps:getCheckboxProps}"
      :dataSource="docData"
      :pagination="false"
      size="small"
    >
      <a-table-column title="文档名" data-index="docName" :width="600"></a-table-column>
      <a-table-column title="数量" data-index="templateId" :width="600">
        <template slot-scope="text,record">
          <a-input-number v-if="record.multiple" :min="1" :value="templateMap[text]" @change="v=>numberChange(v,text)" />
          <span v-else>1</span>
        </template>
      </a-table-column>
      <a-table-column title="是否多选" data-index="multiple" :width="600">
        <template slot-scope="text">
          <span v-if="text">是</span>
          <span v-else>否</span>
        </template>
      </a-table-column>
    </a-table>
  </a-modal>
</template>

<script>
export default {
  name: 'TemplateModal',
  components: {
  },
  data () {
    return {
      title: '',
      docData: [],
      templateIds: [],
      projectId: 0,
      visible: false,
      confirmLoading: false,
      selectedRowKeys: [],
      selectedRows: [],
      templateMap: {},
      propsMap: {}
    }
  },
  created () {
    this.$http.get('/document/templateList')
      .then(res => {
        this.docData = res.data
        for (let index = 0; index < this.docData.length; index++) {
          const element = this.docData[index]
          this.propsMap[element.templateId] = { disabled: false, multiple: element.multiple }
          this.$set(this.templateMap, element.templateId, 1)
        }
      })
  },
  methods: {
    open (templateIds, projectId) {
      for (const k in this.propsMap) {
        this.propsMap[k].disabled = false
        this.templateMap[k] = 1
      }
      for (let i = 0; i < templateIds.length; i++) {
        const tId = templateIds[i]
        if (this.propsMap[tId] && !this.propsMap[tId].multiple) {
          this.propsMap[tId].disabled = true
        }
      }
      this.projectId = projectId
      this.selectedRowKeys = []
      this.visible = true
    },
    numberChange (value, templateId) {
      this.templateMap[templateId] = value
    },
    handleSubmit () {
      if (this.selectedRows.length > 0) {
        this.confirmLoading = true
        const docList = []
        for (let index = 0; index < this.selectedRows.length; index++) {
          const element = this.selectedRows[index]
          const tId = element.templateId
          docList.push({
            processId: element.processId,
            projectId: this.projectId,
            docNumber: element.seq,
            docName: element.docName,
            templateId: tId,
            quantity: this.templateMap[tId],
            stageId: -1
          })
        }
        return this.$http.post('/document/addDoc', docList).then(res => {
          if (res.success && res.data) {
            this.$message.success('添加成功')
            this.visible = false
            this.$emit('ok')
          } else {
            this.$message.error('添加失败')
          }
          this.confirmLoading = false
        })
      } else {
        this.$message.warn('请选择要添加的模板文档！')
      }
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    getCheckboxProps (record) {
      return {
        props: this.propsMap[record.templateId] // record.multiple === false && this.templateIds.indexOf(record.templateId) != -1
      }
    }
  }
}
</script>
