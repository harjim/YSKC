<template>
  <a-select
    :mode="multiple?'multiple' : 'default' "
    showSearch
    :allowClear="allowClear"
    :value="val"
    :placeholder="placeholder"
    :showArrow="false"
    :filterOption="false"
    :defaultActiveFirstOption="false"
    @search="getData"
    @change="change"
    :notFoundContent="null"
    :dropdownStyle="dropdownStyle"
    v-if="readonly===false"
    :disabled="disabled"
  >
    <a-select-option
      v-for="d in data"
      :key="d[sKey]"
      :oData="d"
      :value="d[sKey]"
    >{{ typeof sTitle === 'string' ? d[sTitle] : sTitle(d) }}
    </a-select-option>
  </a-select>
  <span v-else>{{ displayTitle }}</span>
</template>
<script>
export default {
  name: 'SearchSelect',
  props: {
    readonly: {
      type: Boolean,
      default: false
    },
    multiple: {
      type: Boolean,
      default: false
    },
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
    sKey: {
      type: String,
      default: 'id'
    },
    sTitle: {
      type: [String, Function],
      default: 'title'
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
    },
    dropdownStyle: {
      type: Object,
      default: () => {
      }
    },
    allowClear: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      data: [],
      prevTime: 0,
      val: undefined,
      displayTitle: undefined
    }
  },
  mounted () {
    if (this.value) {
      if (this.value) {
        if (this.multiple) {
          this.data = [...this.value]
          this.val = this.value.map(a => a[this.sKey])
          this.displayTitle = this.value.map(a => a[this.sTitle]).join(',')
        } else {
          this.data = [this.value]
          this.val = this.value[this.sKey]
          this.displayTitle = this.value[this.sTitle]
        }
      } else {
        this.data = []
        this.val = undefined
      }
    }
  },
  watch: {
    value (val) {
      if (val) {
        if (this.multiple) {
          this.data = [...val]
          this.val = val.map(a => a[this.sKey])
          this.displayTitle = val.map(a => a[this.sTitle]).join(',')
        } else {
          this.data = [val]
          this.val = val[this.sKey]
          this.displayTitle = val[this.sTitle]
        }
      } else {
        this.data = []
        this.val = undefined
      }
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
              params: {
                ...params,
                hasDept: true
              }
            })
            .then(res => {
              this.data = res.data
            })
        }
      }, 300)
    },
    change (value, opt) {
      var data
      if (value !== undefined && opt !== undefined) {
        if (Array.isArray(opt)) {
          data = opt.map(a => a.data.attrs.oData)
        } else {
          data = opt.data.attrs.oData
        }
      }
      this.$emit('change', data)
      this.$emit('input', data)
    }
  }
}
</script>
