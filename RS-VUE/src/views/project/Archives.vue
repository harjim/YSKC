<template>
  <doc-project-list
    ref="docProjectList"
    :listType="3001"
    :view="$auth('project:archives:view')"
    :addFile="$auth('project:archives:add')"
    :editFile="$auth('project:archives:edit')"
    :delFile="$auth('project:archives:del')"
    :uploadFile="$auth('project:archives:upload')"
    :downloadDocFile="$auth('project:archives:download')"
    :delFileList="$auth('project:archives:delModel')"
    :previewFile="$auth('project:archives:preview')"
    pageName="研发档案管理"></doc-project-list>
</template>
<script>
import { DocProjectList } from '@/components'

export default {
  name: 'Archives',
  components: {
    DocProjectList
  },
  data () {
    return {
    }
  },
  methods: {
  }
}
</script>
