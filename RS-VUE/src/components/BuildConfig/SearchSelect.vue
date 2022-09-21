<template>
  <a-select
    show-search
    :value="value"
    :default-active-first-option="false"
    :show-arrow="false"
    :filter-option="false"
    :not-found-content="null"
    @blur="clearData"
    @search="handleSearch"
    @change="handleChange"
    option-label-prop="label"
  >
    <a-select-option v-for="d in data" :key="d.value" :value="d.value" :title="d.value" :label="d.value">
      {{ d.text }}
    </a-select-option>
  </a-select>
</template>
<script>
export default {
  name: 'SearchSelect',
  model: {
    prop: 'value',
    event: 'change'
  },
  props: {
    url: {
      type: String,
      required: true
    },
    // eslint-disable-next-line vue/require-default-prop
    value: {
      type: String
    },
    // eslint-disable-next-line vue/require-default-prop
    year: {
      type: String
    }
  },
  data () {
    return {
      data: []
    }
  },
  methods: {
    clearData () {
      this.data = []
    },
    handleSearch (value) {
      if (this.year && this.year != null) {
        this.$http.get(this.url, { params: { ename: value, year: this.year } }).then(res => {
          if (res.data) {
            const result = res.data
            const data = []
            result.forEach(element => {
              data.push({
                value: element.ename,
                text: element.enumber == null ? element.ename : element.ename + '(' + element.enumber + ')'
              })
            })
            this.data = data
          }
        })
      } else {
        this.$http.get(this.url, { params: { ename: value } }).then(res => {
          if (res.data) {
            const result = res.data
            const data = []
            result.forEach(element => {
              data.push({
                value: element.ename,
                text: element.enumber == null ? element.ename : element.ename + '(' + element.enumber + ')'
              })
            })
            this.data = data
          }
        })
      }
    },
    handleChange (e) {
      this.$emit('change', e)
    }
  }
}
</script>
