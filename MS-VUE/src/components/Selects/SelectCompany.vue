<template>
  <a-auto-complete
    backfill
    allowClear
    optionLabelProp="value"
    :placeholder="placeholder"
    :defaultValue="defaultValue"
    @select="onSelect"
    @search="debounceSearch"
  >
    <template slot="dataSource">
      <a-select-option
        v-for="item in dataSource"
        :key="`${item.customerId}`"
        :value="`${item[prop]}`"
        :datasource="item"
      >
        {{ item.companyName }}
      </a-select-option>
    </template>
  </a-auto-complete>
</template>

<script>
export default {
  name: 'SelectCompany',
  props: {
    placeholder: {
      type: String,
      default: '请输入客户名称'
    },
    prop: {
      type: String,
      default: 'customerId'
    },
    url: {
      type: String,
      default: '/serviceApply/getCustomerList'
    },
    defaultValue: {
      type: String,
      default: ''
    }
  },
  data () {
    this.debounceSearch = this.debounce(this.debounceSearch)
    return {
      dataSource: []
    }
  },
  mounted () {
  },
  methods: {
    onSelect (value, option) {
      this.$emit('changeCompany', value, option)
    },
    debounceSearch (searchText) {
      if (!searchText) {
        this.$emit('changeCompany', '', { key: '' })
        return
      }
      this.getCustomerList(searchText)
    },
    debounce (fn, delay = 1000) {
      let timer = null
      return function () {
        if (timer) clearTimeout(timer)
        timer = setTimeout(() => {
          fn.apply(this, arguments)
        }, delay)
      }
    },
    getCustomerList (companyName) {
      this.$http.get(this.url, { params: { companyName } }).then(({
        success,
        errorMessage,
        data
      }) => {
        if (success) {
          this.dataSource = data
        } else {
          this.$message.error(errorMessage)
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
