<!--
 * @Author: ldx
 * @Date: 2021-07-14 15:57:14
 * @LastEditTime: 2021-07-26 15:51:32
 * @LastEditors: ldx
 * @Description: 概况
 * @FilePath: \RS-VUE\src\views\project\GeneralSituation.vue
-->
<template>
  <div class="contentPage" :bodyStyle="{height: '100%', overflow: 'auto', padding: '5px 5px'}">
    <a-spin id="spin" :spinning="spinning" tip="加载中...">
      <!-- 第一行 -->
      <info-row :countProjectModel="countProjectModel"></info-row>
      <!-- 第三行 -->
      <!-- <three-row v-if="rdFundModels.length && rdCosts.length" :rdCosts="rdCosts" :rdFundModels="rdFundModels" :costTypes="costTypes"></three-row> -->
      <rd-funds :rdCosts="rdCosts" :rdFundModels="rdFundModels" :costTypes="costTypes"></rd-funds>
      <!-- 第二行 -->
      <high-and-budget :highModels="highModels" :countBudgetModels="countBudgetModels"></high-and-budget>
      <!-- 第四行 -->
      <rd-project :projectDataModel="projectDataModel"></rd-project>
    </a-spin>
  </div>
</template>

<script>
import InfoRow from './modules/GeneralSituation/InfoRow'
import highAndBudget from './modules/GeneralSituation/highAndBudget'
import RdFunds from './modules/GeneralSituation/RdFunds'
import RdProject from './modules/GeneralSituation/RdProject'
import { getCountData } from '@/api/generalSituation'
import { mapGetters } from 'vuex'
export default {
  name: 'GeneralSituation',
  components: {
    InfoRow,
    highAndBudget,
    RdFunds,
    RdProject
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  watch: {
    currentYear (val) {
      this.handlerGetCountData(val)
    }
  },
  created () {
    this.costTypes = this.$getCostTypes()
  },
  mounted () {
    this.handlerGetCountData(this.currentYear)
  },
  data () {
    return {
      spinning: false,
      costTypes: [],
      countBudgetModels: [], // 归集费占总预算比
      countProjectModel: {}, // 第一个信息
      highModels: [], // 高品个数与年收入占比
      projectDataModel: [], // 项目情况
      rdCosts: [], // 年度研发费
      rdFundModels: [] // 研发费占比
    }
  },
  methods: {
    handlerGetCountData (year) {
      getCountData({ year }).then(data => {
        if (data) {
          Object.entries(data).forEach(([key, value]) => {
            if (value) {
              this[key] = value
            } else {
              key === 'countProjectModel' ? this[key] = {} : this[key] = []
            }
          })
        }
      }).catch(error => {
        this.message.error(error.message || '系统异常，请联系管理员！')
      })
    }
  }
}
</script>
<style lang="less" scoped>
// @import './less/generalSituation.less';
.contentPage {
  height: 100%;
  overflow: auto;
}
#spin {
  height: 100%;
  width: 100%;
  & /deep/ .ant-spin-nested-loading {
    height: 100%;
  }
  & /deep/ .ant-spin-container {
    height:  100%;
  }
}
</style>
