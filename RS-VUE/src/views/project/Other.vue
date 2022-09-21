<template>
  <doc-list
    ref="docList"
    :listType="5001"
    pageName="项目其他财务信息"
    :view="$auth('project:other:view')"
    :addFile="$auth('project:other:add')"
    :editFile="$auth('project:other:edit')"
    :delFile="$auth('project:other:del')"
    :uploadFile="$auth('project:other:upload')"
    :downloadDocFile="$auth('project:other:download')"
    :delFileList="$auth('project:other:delModel')"
    :previewFile="$auth('project:other:preview')"
  ></doc-list>
</template>
<script>
import { DocList } from '@/components'

export default {
  name: 'Other',
  components: {
    DocList
  },
  data () {
    return {
    }
  },
  methods: {
  }
}
</script>
