<template>
  <a-tree-select
    v-model="selectVal"
    showSearch
    :getPopupContainer="getPopupContainer"
    :dropdownStyle="{ maxHeight: '350px', overflow: 'scroll', width: '150px' }"
    :treeData="tree"
    :allowClear="allowClear"
    :placeholder="placeholder"
    treeDefaultExpandAll
    @change="onChange"
  ></a-tree-select>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'
import { popupContainer } from '@/docTemplate/Templates/js/screenFullMountDom'
export default {
  mixins: [yearMiXin],
  name: 'SelectDown',
  props: {
    placeholder: {
      type: String,
      default: ''
    },
    allowClear: {
      type: Boolean,
      default: true
    },
    // 树类型，默认4种（account,workshop,rdDept,dept），对应相应类型的树
    treeType: {
      type: String,
      required: true
    },
    year: {
      type: Number,
      required: false,
      default: undefined
    },
    value: {
      type: [Number, String],
      default: undefined
    },
    mountDom: { // 弹出选项挂载在那个DOM显示，默认body
      type: String,
      default: ''
    }
  },
  data () {
    return {
      tree: [],
      selectVal: undefined,
      required: true
    }
  },
  mounted () {
    setTimeout(() => { this.loadTree() }, 300)
  },
  watch: {
    value (v) {
      this.$nextTick(() => {
        this.setValue(v)
      })
    },
    year (y) {
      this.search()
    }
  },
  methods: {
    getPopupContainer (triggerNode) {
      return popupContainer(this.mountDom)
    },
    search () {
      this.$nextTick(() => {
        if (this.treeType === 'rdDept') {
          setTimeout(() => { this.loadTree() }, 300)
        }
      })
    },
    loadTree () {
      this.$getTree(this.treeType, false, this.year)
        .then(res => {
          this.tree = res.tree
          if (this.value && this.value > 0) {
            this.$nextTick(() => {
              this.selectVal = String(this.value)
            })
          }
        })
    },
    onChange (v, l, e) {
      var fullPath = ''
      var title = ''
      if (e.triggerNode) {
        fullPath = e.triggerNode.eventKey
        title = e.triggerNode.title
      }
      this.$emit('fullPath', fullPath)
      this.$emit('select', v, title)
    },
    setValue (val) {
      if (!val || val === '0' || val === null || val === undefined) {
        this.selectVal = undefined
      } else {
        this.selectVal = val.toString()
      }
    }
  }
}
</script>
