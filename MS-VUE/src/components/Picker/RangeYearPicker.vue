<template>
  <span class="box">
    <year-picker
      placeholder="开始年份"
      :disabled="disabled"
      v-model="by"
      :disabled-year="disBeginYear"
      style="flex: 1"
    />
    <span>-</span>
    <year-picker placeholder="结束年份" :disabled="disabled" v-model="ey" :disabled-year="disEndYear" style="flex: 1" />
  </span>
</template>

<script>
import YearPicker from '@/components/Picker/YearPicker'
import moment from 'moment'

export default {
  name: 'RangeYearPicker',
  components: { YearPicker },
  props: {
    begin: {
      type: [String, Object],
      default: () => undefined
    },
    end: {
      type: [String, Object],
      default: () => undefined
    },
    disabled: {
      type: Boolean,
      default: false
    },
    range: {
      type: Function,
      default: () => () => []
    }
  },
  data() {
    return {
      by: this.begin,
      ey: this.end
    }
  },
  watch: {
    by(val) {
      this.$emit('update:begin', val)
      if (!this.ey) {
        this.ey = val
      }
    },
    ey(val) {
      this.$emit('update:end', val)
    }
  },
  methods: {
    moment,
    disBeginYear(c) {
      let flag
      const dates = this.range()
      for (const date of dates) {
        if (date) {
          flag = c > date[0] && c < date[1].add(1, 'years')
          if (flag) break
        }
      }
      if (flag) return flag
      if (this.ey) {
        flag = c > moment(this.ey)
      }
      return flag
    },
    disEndYear(c) {
      let flag
      const dates = this.range()
      for (const date of dates) {
        if (date) {
          flag = c > date[0].subtract(1, 'days') && c < date[1].add(1, 'years')
          if (flag) break
        }
      }
      if (flag) return flag
      if (this.by) {
        flag = c < moment(this.by)
      }
      return flag
    }
  }
}
</script>

<style lang="less" scoped>
.box {
  display: flex;
  align-items: center;
  justify-content: space-around;
}
</style>
