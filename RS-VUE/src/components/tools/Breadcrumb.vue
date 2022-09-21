<!--
 * @Author: ldx
 * @Date: 2020-11-25 16:36:08
 * @LastEditTime: 2022-05-20 08:12:26
 * @LastEditors: zdf
 * @Description:
 * @FilePath: \RS-VUE\src\components\tools\Breadcrumb.vue
-->
<template>
  <a-breadcrumb class="breadcrumb">
    <a-breadcrumb-item v-if="hasYearSelect">
      <year-select
        @change="yearChange"
        ref="yearChange"
        style="width: 90px;height: 16px;"
        :allowClear="false"
      />
    </a-breadcrumb-item>
    <a-breadcrumb-item v-for="(item, index) in breadList" :key="item.name">
      <router-link
        v-if="item.name != name && index != 1"
        :to="{ path: item.path === '' ? '/' : item.path }"
      >{{ item.meta.title }}</router-link>
      <span v-else>{{ item.meta.title }}</span>
    </a-breadcrumb-item>
  </a-breadcrumb>
</template>

<script>
import YearSelect from '@/components/YearSelect'
export default {
  components: {
    YearSelect
  },
  data () {
    return {
      name: '',
      breadList: []
    }
  },
  props: {
    hasYearSelect: {
      type: Boolean,
      default: false
    }
  },
  created () {
    this.getBreadcrumb()
    this.setYear()
  },
  updated () {
    this.setYear()
  },
  methods: {
    setYear () {
      this.$nextTick(() => {
        if (this.$refs.yearChange) {
          this.$refs.yearChange.setVal(this.$store.state.currentYear)
        }
      })
    },
    getBreadcrumb () {
      this.breadList = []
      // this.breadList.push({name: 'index', path: '/dashboard/', meta: {title: '首页'}})
      this.name = this.$route.name
      this.$route.matched.forEach(item => {
        // item.name !== 'index' && this.breadList.push(item)
        if (!this.hasYearSelect || item.meta.title !== '') { this.breadList.push(item) }
      })
    },
    yearChange (y) {
      this.$store.dispatch('changeYear', y)
    }
  },
  watch: {
    $route (to, from) {
      this.getBreadcrumb()
    }
  }
}
</script>

<style scoped>
</style>
