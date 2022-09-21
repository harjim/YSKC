<!--
 * @Author: ldx
 * @Date: 2021-05-27 16:54:25
 * @LastEditTime: 2021-06-02 16:39:39
 * @LastEditors: ldx
 * @Description: 高新技术列表
 * @FilePath: \RS-VUE\src\views\highTech\HighTechIndex.vue
-->
<template>
  <a-card class="contentPage" :bodyStyle="{height: '100%', overflow: 'auto'}">
    <template v-if="$auth('highTech:highTechIndex:search')">
      <HighTechList @showDetail="showDetail" v-if="isShow"/>
      <HighTechDetail v-if="!isShow && record" :record="record" @showList="showList" />
    </template>
  </a-card>
</template>
<script>
import { mapGetters } from 'vuex'
import HighTechList from './modules/HighTechList'
import HighTechDetail from './modules/HighTechDetail'
export default {
  name: 'HighTechIndex',
  components: {
    HighTechList,
    HighTechDetail
  },
  data () {
    return {
      isShow: true,
      record: undefined
    }
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  methods: {
    showList (isShow) {
      this.isShow = isShow
      this.record = undefined
    },
    showDetail (record, isShow) {
      this.record = record
      this.isShow = isShow
    }
  }
}
</script>
<style lang="less" scoped>
.contentPage {
  height: 100%;
  overflow: auto;
  position: relative;
}
</style>
