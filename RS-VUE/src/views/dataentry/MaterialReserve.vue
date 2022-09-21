<template>
  <material
    :type="2"
    typeName="备品备件"
    :control="{
      search:$auth('dataentry:materialReserve:search'),
      add: $auth('dataentry:materialReserve:add'),
      import: $auth('dataentry:materialReserve:import'),
      export: $auth('dataentry:materialReserve:export'),
      edit:$auth('dataentry:materialReserve:edit'),
      delete:$auth('dataentry:materialReserve:del'),
      rdImport:$auth('dataentry:materialReserve:rdImport'),
      setPicker:$auth('dataentry:materialReserve:setPicker')
    }"
  />
</template>
<script>
import Material from './Material'
export default {
  components: {
    Material
  }
}
</script>

<style>
</style>
