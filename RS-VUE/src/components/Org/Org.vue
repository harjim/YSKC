<!--
 * @Author:
 * @Date: 2021-05-06 14:16:22
 * @LastEditTime: 2021-05-17 18:42:38
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\components\Org\Org.vue
-->
<template>
  <Org-Node
    :data="data"
    :isRoot="true"
    :currentYear="currentYear"
    :onClick="onClick"
    :modify="modify"
    :sortNode="sortNode"></Org-Node>
</template>
<script>
import OrgNode from './OrgNode'
export default {
  name: 'Org',
  components: {
    OrgNode
  },
  props: {
    data: {
      type: Object,
      required: true
    },
    currentYear: {
      type: Number,
      required: true
    },
    onClick: {
      type: Function,
      required: true
    },
    sortNode: {
      type: Function,
      required: true
    },
    modify: {
      type: Boolean,
      required: true
    }
  },
  data () {
    return {}
  },
  mounted () {
  },
  methods: {

  }
}
</script>
<style lang="css" scoped>

</style>
