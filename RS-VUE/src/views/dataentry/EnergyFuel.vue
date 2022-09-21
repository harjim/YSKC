<template>
  <energy :etype="20200" typeName="燃料损耗" :control="control"></energy>
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
      search: this.$auth('dataentry:fuel:search'),
      add: this.$auth('dataentry:fuel:add'),
      edit: this.$auth('dataentry:fuel:edit'),
      del: this.$auth('dataentry:fuel:del'),
      import: this.$auth('dataentry:fuel:import'),
      export: this.$auth('dataentry:fuel:export')
    }
  }
}
</script>

<style>
</style>
