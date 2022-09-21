<template>
  <!-- <a-auto-complete
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
  </a-auto-complete> -->
  <a-select
    showSearch
    allowClear
    :defaultValue="defaultValue"
    :placeholder="placeholder"
    :defaultActiveFirstOption="false"
    :showArrow="false"
    style="width: 100%"
    :filter-option="false"
    :not-found-content="fetching ? undefined : null"
    @search="debounceSearch"
    @change="onSelect"
  >
    <a-spin v-if="fetching" slot="notFoundContent" size="small" />
    <a-select-option
      v-for="item in dataSource"
      :key="item.customerId"
      :value="item.customerId"
      :deptId="item.deptId"
      :ownerId="item.ownerId"
      :dataSource="item">
      {{ item.companyName }}
    </a-select-option>
  </a-select>
</template>

<script>
export default {
  name: 'SelectCustomer',
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
      default: '/customer/getCustomerOwner'
    },
    defaultValue: {
      type: [String, Number],
      default: () => {}
    }
  },
  data () {
    this.debounceSearch = this.debounce(this.debounceSearch)
    return {
      dataSource: [],
      fetching: false
    }
  },
  methods: {
    onSelect (value, option) {
      this.$emit('changeCustomer', value, option)
    },
    debounceSearch (searchText) {
      if (!searchText) {
        this.$emit('changeCustomer', '', { key: '', deptId: null, ownerId: null })
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
      this.fetching = true
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
      }).finally(() => {
        this.fetching = true
      })
    }
  }
}
</script>

<style scoped>

</style>
