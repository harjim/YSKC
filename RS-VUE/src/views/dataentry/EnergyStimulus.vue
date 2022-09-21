<template>
  <energy :etype="20100" typeName="动力损耗" :control="control"></energy>
</template>
<script>
import Energy from './Energy'
export default {
  components: {
    Energy
  },
  data () {
    return {
      control: {}
    }
  },
  created () {
    this.control = {
      search: this.$auth('dataentry:stimulus:search'),
      add: this.$auth('dataentry:stimulus:add'),
      edit: this.$auth('dataentry:stimulus:edit'),
      del: this.$auth('dataentry:stimulus:del'),
      import: this.$auth('dataentry:stimulus:import'),
      export: this.$auth('dataentry:stimulus:export')
    }
  }
}
</script>

<style>
</style>
