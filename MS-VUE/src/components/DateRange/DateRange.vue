<template>
  <div class="rang-box">
    <a-date-picker
      :defaultValue="defaultValue[0]"
      v-model="v0"
      :disabled="disabled"
      :show-time="showTime"
      :format="showTime? 'YYYY-MM-DD HH:mm' : 'YYYY-MM-DD'"
      @change="(v,s)=>onChange(v,s,0)"
      :disabledDate="disBeginDate"
      style="flex: 1;"
      placeholder="请选择开始日期"
    />
    <span>~</span>
    <a-date-picker
      :defaultValue="defaultValue[1]"
      v-model="v1"
      :disabled="disabled"
      :show-time="showTime"
      :format="showTime? 'YYYY-MM-DD HH:mm' : 'YYYY-MM-DD'"
      @change="(v,s)=>onChange(v,s,1)"
      :disabledDate="disEndDate"
      style="flex: 1;"
      placeholder="请选择结束日期"
    />
  </div>
</template>

<script>
export default {
  name: 'DateRange',
  props: {
    disabled: {
      type: Boolean,
      default: false
    },
    defaultValue: {
      type: Array,
      default: () => [null, null]
    },
    showTime: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      v0: undefined,
      v1: undefined,
      rangeDate: [],
      rangeStr: []
    }
  },
  methods: {
    onChange (v, s, index) {
      this.rangeDate[index] = v
      this.rangeStr[index] = s
      // this.$emit('input', this.rangeDate)
      this.$emit('onChange', this.rangeDate, this.rangeStr)
    },
    disBeginDate (c) {
      if (this.v1) {
        return c > this.v1
      }
      return !c
    },
    disEndDate (c) {
      if (this.v0) {
        return c < this.v0
      }
      return !c
    },
    setValue (v, s) {
      if (v) {
        this.v0 = v[0]
        this.v1 = v[1]
        this.rangeDate = v
        this.rangeStr = [v[0].format('YYYY-MM-DD'), v[1].format('YYYY-MM-DD')]
      } else {
        this.v0 = undefined
        this.v1 = undefined
        this.rangeDate = []
        this.rangeStr = []
      }
    }
  }
}
</script>

<style lang="less" scoped>
.rang-box {
  display: flex;
  align-items: center;
}
</style>
