<template>
  <a-select
    v-model="currentValue"
    showSearch
    :placeholder="placeholder"
    :disabled="disabled"
    :defaultActiveFirstOption="false"
    :showArrow="false"
    :filterOption="false"
    :allowClear="true"
    @search="searchData"
    @change="onChange"
    :notFoundContent="null"
  >
    <a-select-option v-for="d in selectData" :key="d[sKey]" :data="d">{{ getTitle(d) }}</a-select-option>
  </a-select>
</template>
<script>
export default {
  name: 'MiniSelect',
  props: {
    url: {
      type: String,
      required: true
    },
    sKey: {
      type: String,
      required: true
    },
    // 显示格式 {0}({1})({2})
    sTitle: {
      type: Array,
      default: () => ['title']
    },
    queryKey: {
      type: String,
      required: true
    },
    disabled: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: ''
    }
  },
  data () {
    return {
      prevTime: 0,
      selectData: [],
      queryParam: {},
      currentValue: undefined
    }
  },
  methods: {
    searchData (v) {
      if (!v || !v.trim()) {
        this.selectData = []
        return
      }
      this.prevTime = new Date().getTime()
      // 延时300ms执行 输入间隔小于300毫秒，不执行查询
      setTimeout(() => {
        if (new Date().getTime() - this.prevTime >= 300) {
          this.queryParam[this.queryKey] = v
          this.$http.get(this.url, { params: this.queryParam })
            .then(res => {
              if (res.success) {
                this.selectData = res.data
              } else {
                this.selectData = []
              }
              return this.selectData
            })
        }
      }, 300)
    },
    getTitle (d) {
      var result = d[this.sTitle[0]]
      for (let i = 1; i < this.sTitle.length; i++) {
        result += `(${d[this.sTitle[i]]})`
      }
      return result
    },
    onChange (value, option) {
      this.$emit('change', value, option ? option.data.attrs.data : undefined)
    },
    setValue (v, row) {
      // 设置值通常用于回显，建议v值为一个title值
      // row为对象，若v为一个id值,建议传入对应的row以方便回显
      if (v) {
        this.currentValue = v
      } else {
        this.currentValue = undefined
      }
      this.selectData = []
      if (row) {
        this.selectData.push(row)
      }
    }
  }
}
</script>
<style scoped>
</style>
