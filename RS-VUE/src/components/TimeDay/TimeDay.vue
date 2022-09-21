<template>
  <span>
    <span v-if="!editable && chk==false">{{ val }}</span>
    <input
      v-if="editable && chk===false"
      size="small"
      :maxlength="hour===8?1:2"
      style="width:16px;border: none 0;border-bottom: 1px solid #ccc;"
      :value="val"
      @change="handleInputChange"
    />
    <!-- <input
      type="checkbox"
      :disabled="!editable"
      v-if="chk"
      :checked="checked"
      @change="handleCheckedChange"
    />-->
    <a-checkbox
      size="small"
      :disabled="!editable"
      v-if="chk"
      :checked="checked"
      @change="handleCheckedChange"
      style="width:8px"
    />
  </span>
</template>

<script>
/* eslint-disable */
export default {
  name: 'TimeDay',
  props: {
    value: {
      type: Number
    },
    mode: {
      type: String,
      default: 'checkbox'
    },
    editable: {
      type: Boolean,
      default: false
    },
    hour: {
      type: Number,
      default: 8
    }
  },
  data () {
    return {
      chk: this.mode === 'checkbox' && (this.value === 0 || this.value === this.hour),
      val: this.value
    }
  },
  computed: {
    checked () {
      return this.value === this.hour
    }
  },
  watch: {
    value (v) {
      if (this.val !== v) {
        this.val = v
        if (v > 0 && v < this.hour && this.chk) {
          this.chk = false
        }
      }
    },
    mode (m) {
      this.chk = m === 'checkbox' && (this.value === 0 || this.value === this.hour)
    }
  },
  methods: {
    handleCheckedChange (e) {
      this.val = this.val === 0 ? this.hour : 0
      this.triggerChange(this.val)
    },
    handleInputChange (e) {
      const { value } = e.target
      const testNumber = /^(([0-1]{0,1}[0-9])|(2[0-4]))$/
      let changedValue = 0
      const iv = testNumber.test(value) ? parseInt(value, 10) : -1
      if (iv >= 0 && iv <= this.hour) {
        this.val = iv
        changedValue = this.val
      } else {
        e.target.value = this.val
        return false
        // this.val = 0
      }
      this.triggerChange(changedValue)
    },
    triggerChange (changedValue) {
      this.$emit('change', changedValue)
    }
  }
}
</script>

<style scoped>
</style>
