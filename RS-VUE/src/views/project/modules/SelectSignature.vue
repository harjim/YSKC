<template>
  <a-select
    :getPopupContainer="getPopupContainer"
    :mode="multiple?'multiple' : 'default' "
    showSearch
    labelInValue
    :showArrow="false"
    :allowClear="true"
    v-model="selectVal"
    :defaultActiveFirstOption="false"
    :placeholder="placeholder"
    style="width: 100%"
    @search="search"
    :notFoundContent="null"
    @change="handleChange"
  >
    <!-- <a-spin v-if="fetching" slot="notFoundContent" size="small" /> -->
    <a-select-option
      v-for="d in list"
      :key="`${d.ename}(${d.enumber})`"
    >{{ `${d.ename}(${d.enumber})` }}</a-select-option>
  </a-select>
</template>

<script>
import { popupContainer } from '@/docTemplate/Templates/js/screenFullMountDom'
export default {
  props: {
    multiple: {
      type: Boolean,
      default: false
    },
    year: {
      type: Number,
      default: undefined
    },
    projectId: {
      type: Number,
      default: undefined
    },
    all: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: '请选择人员'
    },
    selected: {
      type: [Array, Object],
      default: () => { }
    },
    url: {
      type: String,
      default: '/project/getEmployeeSelect'
    },
    mountDom: { // 弹出选项挂载在那个DOM显示，默认body
      type: String,
      default: ''
    },
    isRdAndCommittee: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      selectVal: undefined,
      list: [],
      prevTime: undefined
    }
  },
  watch: {
    year () {
      this.list = []
    },
    selected (v) {
      this.fresh(v)
    }
  },
  created () {
    if (this.selected) {
      this.fresh(this.selected)
    }
  },
  methods: {
    getPopupContainer (triggerNode) {
      return popupContainer(this.mountDom)
    },
    fresh (v) {
      this.$nextTick(() => {
        var selectTemp
        if (v) {
          if (v instanceof Array) {
            const arr = []
            v.forEach(item => {
              arr.push({ key: `${item.ename}(${item.enumber})`, label: `${item.ename}(${item.enumber})` })
            })
            selectTemp = arr
          }
          if (v.enumber) {
            selectTemp = { key: `${v.ename}(${v.enumber})`, label: `${v.ename}(${v.enumber})` }
          }
        }
        this.selectVal = selectTemp
      })
    },
    search (v) {
      if (!v || !v.trim()) {
        return
      }
      this.list = []
      this.prevTime = new Date().getTime()
      setTimeout(() => {
        if (new Date().getTime() - this.prevTime >= 300) {
          const params = { year: this.year, projectId: this.projectId, isProject: this.isProject, ename: v, allEmployee: this.all, rdAndCommittee: this.isRdAndCommittee }
          this.$http.get(this.url, { params: params })
            .then(res => {
              if (res.success && res.data) {
                this.list = res.data
              }
            })
        }
      }, 300)
    },
    handleChange (v, l) {
      var emp
      if (v) {
        if (this.multiple) {
          emp = []
          v.forEach(item => {
            const limit = item.key.indexOf('(')
            emp.push({ enumber: item.key.substr(limit + 1, item.key.length - limit - 2), ename: item.key.substr(0, limit) })
          })
        } else {
          const limit = v.key.indexOf('(')
          emp = { enumber: v.key.substr(limit + 1, v.key.length - limit - 2), ename: v.key.substr(0, limit) }
        }
      }
      this.$emit('select', emp)
    }
  }
}
</script>
