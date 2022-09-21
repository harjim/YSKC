<template>
  <doc-month-list ref="docList" :fileType="7002" pageName="PO单"></doc-month-list>
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
