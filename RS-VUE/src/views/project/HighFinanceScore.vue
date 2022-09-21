<!--
 * @Author: your name
 * @Date: 2021-11-04 09:57:49
 * @LastEditors: zdf
 * @LastEditTime: 2022-05-20 17:39:59
 * @Description: In User Settings Edit
 * @FilePath: \RS-VUE\src\views\project\HighFinanceScore.vue
-->
<template>
  <a-card :bordered="false" style="overflow-y:auto;" v-if="$auth('project:highFinanceScore:view')">
    <a-spin :spinning="spinning" tip="请稍后...">
      <div style="width:100%">
        <div style="width: 1010px;margin:0 auto;">
          <table border="1" borderColor="#F0F0F0">
            <thead style="backgroundColor: #fafafa; font-weight: bold; text-align: center;">
              <tr>
                <td colspan="12">高新技术企业认定财务专家评价表</td>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td colspan="1" rowspan="3">近三年销售收入（万元）</td>
                <td colspan="2">第一年</td>
                <td colspan="3">
                  <a-input-number v-model="salesYear1" :min="0" :precision="2" style="width: 120px" size="small"></a-input-number>
                </td>
                <td colspan="1" rowspan="3">近三年净资产（万元）</td>
                <td colspan="2">第一年</td>
                <td colspan="3">
                  <a-input-number v-model="nAVYear1" :min="0" :precision="2" style="width: 120px" size="small"></a-input-number>
                </td>
              </tr>
              <tr>
                <td colspan="2">第二年</td>
                <td colspan="3">
                  <a-input-number v-model="salesYear2" :min="0" :precision="2" style="width: 120px" size="small"></a-input-number>
                </td>
                <td colspan="2">第二年</td>
                <td colspan="3">
                  <a-input-number v-model="nAVYear2" :min="0" :precision="2" style="width: 120px" size="small"></a-input-number>
                </td>
              </tr>
              <tr>
                <td colspan="2">第三年</td>
                <td colspan="3">
                  <a-input-number v-model="salesYear3" :min="0" :precision="2" style="width: 120px" size="small"></a-input-number>
                </td>
                <td colspan="2">第三年</td>
                <td colspan="3">
                  <a-input-number v-model="nAVYear3" :min="0" :precision="2" style="width: 120px" size="small"></a-input-number>
                </td>
              </tr>
              <tr>
                <td colspan="3">净资产增长率</td>
                <td colspan="3">{{ netAssetsGrowthRate }}</td>
                <td colspan="3">销售收入增长率</td>
                <td colspan="3">{{ salesRevenueGrowthRate }}</td>
              </tr>
              <tr>
                <td colspan="3">近三年销售收入合计（万元）</td>
                <td colspan="3">
                  <a-input-number v-model="totalSales" :min="0" :precision="2" style="width: 120px" size="small"></a-input-number>
                </td>
                <td colspan="3">近一年企业总收入（万元）</td>
                <td colspan="3">
                  <a-input-number v-model="income" :min="0" :precision="2" style="width: 120px" size="small"></a-input-number>
                </td>
              </tr>
              <tr>
                <td colspan="12">
                  <div style="display:flex; justify-content: space-between; font-weight: bold;">
                    <div>企业成长性（≤20分）</div>
                    <div>合计得分：{{ totalScore }}</div>
                  </div>
                </td>
              </tr>
              <tr>
                <td colspan="1">
                  <div>净资产增长率（≤10分）</div>
                </td>
                <td colspan="11">
                  <a-row>
                    <a-col :span="20">
                      <a-slider v-model="nAVScore" :marks="marks" :max="10" :step="1" :default-value="0" />
                    </a-col>
                    <a-col :span="4">
                      <a-input-number
                        disabled
                        v-model="nAVScore"
                        :min="0"
                        :max="10"
                        style="marginLeft: 16px"
                        size="small"/>
                    </a-col>
                  </a-row>
                </td>
              </tr>
              <tr>
                <td colspan="1">
                  <div>销售收入增长率（≤10分）</div>
                </td>
                <td colspan="11">
                  <a-row style="display: flex; align-items: center;">
                    <a-col :span="20">
                      <a-slider v-model="salesScore" :marks="marks" :max="10" :step="1" :default-value="0" />
                    </a-col>
                    <a-col :span="4">
                      <a-input-number
                        disabled
                        v-model="salesScore"
                        :min="0"
                        :max="10"
                        style="marginLeft: 16px"
                        size="small"/>
                    </a-col>
                  </a-row>
                </td>
              </tr>
            </tbody>
          </table>
          <div style="text-align: center;margin-top:10px;" v-if="$auth('project:highFinanceScore:save')">
            <a-button type="primary" @click="saveCurrent()">保存</a-button>
          </div>
        </div>
      </div>
    </a-spin>
  </a-card>
</template>

<script>
import yearMixin from '@/utils/yearMixin'
export default {
  mixins: [yearMixin],
  data () {
    return {
      spinning: false,
      salesYear1: 0,
      salesYear2: 0,
      salesYear3: 0,
      nAVYear1: 0,
      nAVYear2: 0,
      nAVYear3: 0,
      totalSales: 0,
      income: 0,
      marks: {
        0: '≤0',
        2: '>0',
        4: '>5%',
        6: '≥15%',
        8: '≥25%',
        10: '≥35%'
      }
    }
  },
  computed: {
    netAssetsGrowthRate: {
      get () {
        // 净资产增长率＝1/2 （第二年末净资产÷第一年末净资产＋第三年末净资产÷第二年末净资产）－1
        let result = (this.nAVYear2 / this.nAVYear1 + this.nAVYear3 / this.nAVYear2) / 2 - 1
        if (!isFinite(result) || isNaN(result)) {
          result = 0
        }
        return parseFloat(result.toFixed(2))
      },
      set () {
        return this.netAssetsGrowthRate
      }
    },
    salesRevenueGrowthRate: {
      get () {
        // 销售收入增长率＝1/2 （第二年销售收入÷第一年销售收入＋第三年销售收入÷第二年销售收入）－1
        let result = (this.salesYear2 / this.salesYear1 + this.salesYear3 / this.salesYear2) / 2 - 1
        if (!isFinite(result) || isNaN(result)) {
          result = 0
        }
        return parseFloat(result.toFixed(2))
      },
      set () {
        return this.salesRevenueGrowthRate
      }
    },
    nAVScore: {
      get () {
        return this.growthRateToScore(this.netAssetsGrowthRate)
      },
      set () {
        return this.nAVScore
      }
    },
    salesScore: {
      get () {
        return this.growthRateToScore(this.salesRevenueGrowthRate)
      },
      set () {
        return this.salesScore
      }
    },
    totalScore () {
      return this.nAVScore + this.salesScore
    }
  },
  created () {
    this.loadCurrent()
  },
  methods: {
    search () {
      this.loadCurrent()
    },
    growthRateToScore (value) {
      if (value >= 0.35) {
        return 10
      } else if (value >= 0.25) {
        return 8
      } else if (value >= 0.15) {
        return 6
      } else if (value > 0.05) {
        return 4
      } else if (value > 0) {
        return 2
      } else {
        return 0
      }
    },
    loadCurrent () {
      this.spinning = true
      this.$http.get('/highScore/getFinanceScore', { params: { year: this.currentYear } }).then(res => {
        if (res.success) {
          if (res.data) {
            const { salesYear1, salesYear2, salesYear3, nAVYear1, nAVYear2, nAVYear3, totalSales, income, nAVScore, salesScore } = res.data
            this.salesYear1 = salesYear1
            this.salesYear2 = salesYear2
            this.salesYear3 = salesYear3
            this.nAVYear1 = nAVYear1
            this.nAVYear2 = nAVYear2
            this.nAVYear3 = nAVYear3
            this.totalSales = totalSales
            this.income = income
            this.nAVScore = nAVScore
            this.salesScore = salesScore
          }
        } else {
          this.$message.error(res.errorMessage || '获取得分情况失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    saveCurrent () {
      this.spinning = true
      const params = {
        year: this.currentYear,
        salesYear1: this.salesYear1,
        salesYear2: this.salesYear2,
        salesYear3: this.salesYear3,
        nAVYear1: this.nAVYear1,
        nAVYear2: this.nAVYear2,
        nAVYear3: this.nAVYear3,
        totalSales: this.totalSales,
        income: this.income,
        nAVScore: this.nAVScore,
        salesScore: this.salesScore
      }
      this.$http.post('/highScore/saveFinanceScore', params).then(res => {
        if (res.success && res.data) {
          this.$message.success('保存成功')
        } else {
          this.$message.error(res.errorMessage || '保存失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    }
  }
}
</script>

<style lang="less" scoped>
table{
  width: 100%;
  text-align: left;
  td{
    font-size:14px;
    height:42px;
    padding-left:18px;
    word-break: break-all;
    padding-right:20px;
  }
}
</style>
