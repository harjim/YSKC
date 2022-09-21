<template>
  <a-select
    :disabled="disabled"
    :placeholder="placeholder"
    @change="change"
    :value="val"
    :allowClear="allowClear"
    v-if="readonly===false"
  >
    <a-select-option v-for="y in years" :key="y">{{ y }}</a-select-option>
  </a-select>
  <span v-else>{{ val }}</span>
</template>
<script>
const nextYear = (new Date()).getFullYear() + 5
const years = []
for (let y = nextYear; y >= 2016; y--) {
  years.push(y)
}
export default {
  name: 'YearSelect',
  props: {
    // eslint-disable-next-line vue/require-default-prop
    value: [String, Number],
    placeholder: {
      type: String,
      default: '项目年份'
    },
    readonly: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    },
    allowClear: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      years,
      val: this.value
    }
  },
  watch: {
    value (val) {
      this.val = val
    }
  },
  methods: {
    change (v) {
      this.$emit('change', v)
      this.$emit('input', v)
    }
  }
}
</script>
