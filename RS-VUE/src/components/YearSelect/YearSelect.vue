<template>
  <a-select :placeholder="placeholder" @change="change" v-model="val" :allowClear="allowClear" :disabled="disabled">
    <a-select-option v-for="y in years" :key="y">{{ y }}</a-select-option>
  </a-select>
</template>
<script>
export default {
  name: 'YearSelect',
  props: {
    // eslint-disable-next-line vue/require-default-prop
    value: [String, Number],
    allowClear: {
      type: Boolean,
      default: true
    },
    placeholder: {
      type: String,
      default: '项目年份'
    },
    nextYear: {
      type: Number,
      default: (new Date()).getFullYear() + 5
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      years: [],
      val: this.value
    }
  },
  created () {
    for (let y = this.nextYear; y >= 2016; y--) {
      this.years.push(y)
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
    },
    setVal (v) {
      if (this.years.indexOf(v) >= 0) {
        this.val = v
      }
    }
  }
}
</script>
