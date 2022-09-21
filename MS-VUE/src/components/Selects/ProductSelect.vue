<template>
  <a-select
    :placeholder="placeholder"
    showSearch
    :filterOption="filterProduct"
    @change="change"
    :value="val"
    :disabled="readonly"
  >
    <a-select-option
      v-for="d in result"
      :key="d.id"
      :data="d"
    >{{ d.productName }}</a-select-option>
  </a-select>
</template>
<script>

export default {
  name: 'ProductSelect',
  props: {
    // eslint-disable-next-line vue/require-default-prop
    value: [String, Number],
    placeholder: {
      type: String,
      default: '项目类型'
    },
    filterOption: {
      type: Object,
      default: function () {
        return {}
      }
    },
    readonly: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      products: [],
      result: [],
      val: undefined
    }
  },
  watch: {
    value: function (v) {
      this.val = v
    },
    filterOption: {
      handler: function (v, oldVal) {
        this.filter(v)
      },
      deep: true
    }
  },
  created () {
    this.$getProductList()
      .then(res => {
        this.products = res
        this.filter(this.filterOption)
      })
  },
  methods: {
    change (v, option) {
      this.$emit('change', v, option)
      this.$emit('input', v)
    },
    filter (v) {
      if (v) {
        this.result = this.products.filter(d => {
          return (d.year === null || typeof v.year === 'undefined' || d.year === v.year) &&
       (d.address === null || typeof v.address === 'undefined' || d.address.indexOf(v.address) > -1)
        })
      } else {
        this.result = this.products
      }
    },
    filterProduct (input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      )
    }
  }
}
</script>
