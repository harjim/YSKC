<!--
 * @Author: zdf
 * @Date: 2020-07-21 16:06:30
 * @LastEditTime: 2021-08-18 13:44:50
 * @LastEditors: zdf
 * @Description: 原料清单
 * @FilePath: \RS-VUE\src\views\dataentry\MaterialRaw.vue
-->
<template>
  <material
    :type="0"
    typeName="原料清单"
    :control="{
      search:$auth('dataentry:materialRaw:search'),
      add: $auth('dataentry:materialRaw:add'),
      import: $auth('dataentry:materialRaw:import'),
      export: $auth('dataentry:materialRaw:export'),
      edit:$auth('dataentry:materialRaw:edit'),
      delete:$auth('dataentry:materialRaw:del'),
      rdImport:$auth('dataentry:materialRaw:rdImport'),
      setPicker:$auth('dataentry:materialRaw:setPicker')
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
