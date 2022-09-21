<template>
  <div>
    <ellipsis :length="length" :tooltip="isShow">
      <template v-if="treeKey instanceof Array && treeKey.length > 0">
        <template v-for="(key, index) in treeKey">
          {{ keyMap[key] }}
          <template v-if="index < treeKey.length -1">,</template>
        </template>
      </template>
      <template v-else>{{ keyMap[treeKey] }}</template>
    </ellipsis>
  </div>
</template>
<script>
import { Ellipsis } from '@/components'
export default {
  components: {
    Ellipsis
  },
  props: {
    length: {
      type: Number,
      default: 34
    },
    treeKey: {
      type: [Number, String, Object, Array],
      required: true
    },
    treeType: {
      type: String,
      required: true
    },
    year: {
      type: Number,
      default: undefined
    },
    isShow: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      keyMap: {}
    }
  },
  created () {
    this.$getTree(this.treeType, false, this.year).then(res => {
      this.keyMap = res.keyMap
    })
  }
}
</script>
