<template>
  <a-date-picker
    mode="year"
    :placeholder="placeholder"
    :disabled="disabled"
    :allowClear="false"
    :open="open"
    :value="time"
    format="YYYY"
    @change="onChange"
    @openChange="onOpenChange"
    @panelChange="onPanelChange"
  />
</template>

<script>
import moment from 'moment'

export default {
  name: 'YearPicker',
  props: {
    value: [String, Object],
    placeholder: {
      type: String,
      default: '请选择年份'
    },
    disabledYear: {
      type: Function,
      default: () => () => false
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  model: {
    prop: 'value',
    event: 'change'
  },
  data() {
    return {
      time: this.value ? this.moment(this.value) : null,
      open: false
    }
  },
  watch: {
    value(val) {
      this.time = val ? moment(val) : null
    }
  },
  methods: {
    moment,
    onOpenChange(status) {
      this.open = status
    },
    onPanelChange(value) {
      this.$emit('change', moment(value).format('YYYY'))
      this.time = value
      this.open = false
    },
    onChange() {
      this.$emit('change', null)
      this.time = null
    }
  }
}
</script>

<style scoped></style>
