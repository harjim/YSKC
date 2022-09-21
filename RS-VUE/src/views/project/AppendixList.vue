<template>
  <doc-list ref="docList" :listType="3002" pageName="附件清单"></doc-list>
</template>
<script>
import { DocList } from '@/components'

export default {
  name: 'AppendixList',
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
