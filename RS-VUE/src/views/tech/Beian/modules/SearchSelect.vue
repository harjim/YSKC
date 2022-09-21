<!--
 * @Author:
 * @Date: 2021-04-07 15:29:31
 * @LastEditTime: 2021-10-27 09:53:23
 * @LastEditors: lzh
 * @Description:
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\SearchSelect.vue
-->
<template>
  <a-select
    mode="default"
    showSearch
    :allowClear="true"
    :value="val"
    :placeholder="placeholder"
    :showArrow="false"
    :filterOption="false"
    :defaultActiveFirstOption="false"
    @search="getData"
    @change="change"
    :notFoundContent="null"
    :disabled="disabled"
  >
    <a-select-option
      v-for="(d,index) in data"
      :key="index"
    >{{ d }}</a-select-option>
  </a-select>
</template>
<script>
export default {
  name: 'SearchSelect',
  props: {
    placeholder: {
      type: String,
      default: ''
    },
    // 请求URL
    url: {
      type: String,
      required: true
    },
    // 参数
    paramData: {
      type: [Object, Function],
      default: function () {
        return {}
      }
    },
    // 文本框键入值对应的字段名
    searchField: {
      type: String,
      default: 'key'
    },
    // eslint-disable-next-line vue/require-default-prop
    value: {
      type: [Object, Array]
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      data: [],
      prevTime: 0,
      val: undefined
    }
  },
  watch: {
    value: {
      handler: function (val) {
        if (val) {
          if (this.multiple) {
            this.data = [...val]
            this.val = val.map(a => a)
          } else {
            this.data = val
            this.val = val[0]
          }
        } else {
          this.data = []
          this.val = undefined
        }
      },
      immediate: true
    }
  },
  methods: {
    getData (v) {
      if (!v || !v.trim()) {
        this.data = []
        return
      }
      this.prevTime = new Date().getTime()
      // 延时300ms执行 输入间隔小于300毫秒，不执行查询
      setTimeout(() => {
        if (new Date().getTime() - this.prevTime >= 300) {
          const params = typeof this.paramData === 'function' ? this.paramData() : { ...this.paramData }
          params[this.searchField] = v
          this.$http
            .get(this.url, {
              params
            })
            .then(res => {
              this.data = res.data
              if (this.data && this.data.length === 1) {
                this.$emit('change', this.data)
                this.$emit('input', this.data)
              }
            })
        }
      }, 300)
    },
    change (value, opt) {
      let data
      if (value !== undefined && opt !== undefined) {
        data = opt.context.data
        this.$emit('change', [data[value]])
        this.$emit('input', [data[value]])
      } else {
        this.$emit('change', data)
        this.$emit('input', data)
      }
    }
  }
}
</script>
