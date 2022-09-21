<!--
 * @Author: zdf
 * @Date: 2020-07-21 16:06:30
 * @LastEditTime: 2021-08-18 13:44:07
 * @LastEditors: zdf
 * @Description: 辅料清单
 * @FilePath: \RS-VUE\src\views\dataentry\MaterialDataentry.vue
-->
<template>
  <material
    :type="1"
    typeName="辅料清单"
    :control="{
      search:$auth('dataentry:materialDataentry:search'),
      add: $auth('dataentry:materialDataentry:add'),
      import: $auth('dataentry:materialDataentry:import'),
      export: $auth('dataentry:materialDataentry:export'),
      edit:$auth('dataentry:materialDataentry:edit'),
      delete:$auth('dataentry:materialDataentry:del'),
      rdImport:$auth('dataentry:materialDataentry:rdImport'),
      setPicker:$auth('dataentry:materialDataentry:setPicker')
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
