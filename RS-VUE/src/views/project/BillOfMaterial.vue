<template>
  <doc-month-list ref="docList" :fileType="7003" pageName="BOM表"></doc-month-list>
</template>
<script>
import { DocMonthList } from '@/components'

export default {
  name: 'AppendixList',
  components: {
    DocMonthList
  },
  data () {
    return {
    }
  },
  methods: {
  }
}
</script>
