<template>
  <doc-list ref="docList" :listType="3001" pageName="备查资料清单"></doc-list>
</template>
<script>
import { DocList } from '@/components'

export default {
  name: 'BackList',
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
