<template>
  <a-modal
    :title="title"
    :width="1000"
    :visible="visible"
    :maskClosable="false"
    @cancel="visible = false"
    :footer="null"
  >
    <doc-list
      :view="$auth('project:patent:doc:view')"
      :addFile="$auth('project:patent:doc:add')"
      :editFile="$auth('project:patent:doc:edit')"
      :delFile="$auth('project:patent:doc:del')"
      :uploadFile="$auth('project:patent:doc:upload')"
      :downloadDocFile="$auth('project:patent:doc:download')"
      :delFileList="$auth('project:patent:doc:delModel')"
      :previewFile="$auth('project:patent:doc:preview')"
      ref="docList"
      :listType="2001"
      :patentNo="patentNo"
      :rdActivitiesColumn="false"
      :operatorsColumn="false"
    ></doc-list>
  </a-modal>
</template>

<script>
import { DocList } from '@/components'

export default {
  components: {
    DocList
  },
  data () {
    return {
      visible: false,
      title: '',
      patentNo: ''
    }
  },
  methods: {
    showModal (record, projectName) {
      this.title = `专利资料[${record.patentName}]`
      this.confirmLoading = false
      this.visible = true
      this.patentNo = record.patentNo
      this.$nextTick(() => {
        this.$refs.docList.search()
      })
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
</style>
