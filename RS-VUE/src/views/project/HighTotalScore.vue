<!--
 * @Author: zdf
 * @Date: 2021-11-04 09:57:49
 * @LastEditors: zdf
 * @LastEditTime: 2022-08-18 14:44:05
 * @Description: 高新综合评分评价
 * @FilePath: \RS-VUE\src\views\project\HighTotalScore.vue
-->
<template>
  <a-card :bordered="false" style="overflow-y:auto;" v-if="$auth('project:highTotalScore:view')">
    <a-spin :spinning="spinning" tip="请稍后...">
      <div style="width:100%">
        <div style="width: 1010px;margin:0 auto;">
          <table class="normalTd" border style="border: 0;width:1000px;table-layout:fixed;">
            <tr style="border:0px #dff solid;">
              <td style="border:0;" width="12%"></td>
              <td style="border:0;" width="25%"></td>
              <td style="border:0;" width="12%"></td>
              <td style="border:0;" width="12%"></td>
              <td style="border:0;" width="12%"></td>
              <td style="border:0;" width="12%"></td>
              <td style="border:0;" width="15%"></td>
            </tr>
            <tr>
              <td colspan="7" style="background-color:#FAFAFA;font-weight:600;text-align:center;">综合评分评价</td>
            </tr>
            <tbody>
              <tr>
                <td colspan="2">企业是否注册成立一年以上</td>
                <td colspan="5">
                  <a-checkbox :checked="Boolean(current.register)" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                  <a-checkbox :checked="!Boolean(current.register)" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
                </td>
              </tr>
              <tr>
                <td colspan="2">企业是否获得符合条件的知识产权</td>
                <td colspan="5">
                  <a-checkbox :checked="Boolean(current.patent)" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                  <a-checkbox :checked="!Boolean(current.patent)" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
                </td>
              </tr>
              <tr>
                <td colspan="2">核心技术是否属于《技术领域》规定的范围</td>
                <td colspan="5">
                  <a-checkbox :checked="Boolean(current.patent)" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                  <a-checkbox :checked="!Boolean(current.patent)" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
                  <div style="width:100%" v-html="tecIndustries"></div>
                </td>
              </tr>
              <tr>
                <td colspan="4">科技人员占企业职工总数的比例（%）</td>
                <td>{{ current.memberRatio ? `${(current.memberRatio * 100).toFixed(2)}%`: '-' }}</td>
                <td rowspan="4" style="padding-right:0;">是否符合条件</td>
                <td>
                  <a-checkbox :checked="Boolean(current.fitMember)" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                  <a-checkbox :checked="!Boolean(current.fitMember)" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
                </td>
              </tr>
              <tr>
                <td colspan="4">近三年研究开发费用总额占同期销售收入总额比例（%）</td>
                <td>{{ current.rdFeeSalesRatio ? `${(current.rdFeeSalesRatio * 100).toFixed(2)}%`: '-' }}</td>
                <td>
                  <a-checkbox :checked="Boolean(current.fitRdFeeSales)" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                  <a-checkbox :checked="!Boolean(current.fitRdFeeSales)" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
                </td>
              </tr>
              <tr>
                <td colspan="4">近三年在中国境内研发费用总额占全部研发费用总额比例（%）</td>
                <td>{{ current.rdFeeRatio ? `${(current.rdFeeRatio * 100).toFixed(2)}%`: '-' }}</td>
                <td>
                  <a-checkbox :checked="Boolean(current.fitRdFee)" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                  <a-checkbox :checked="!Boolean(current.fitRdFee)" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
                </td>
              </tr>
              <tr>
                <td colspan="4">近一年高新技术产品（服务）收入占同期总收入比例（%）</td>
                <td>{{ current.highFeeIncomeRatio ? `${(current.highFeeIncomeRatio * 100).toFixed(2)}%`: '-' }}</td>
                <td>
                  <a-checkbox :checked="Boolean(current.fitHighFeeIncome)" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                  <a-checkbox :checked="!Boolean(current.fitHighFeeIncome)" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
                </td>
              </tr>
              <tr>
                <td rowspan="8">创新能力评价总分</td>
                <td>1. 知识产权得分</td>
                <td>{{ sumPart1 || '-' }}</td>
                <td colspan="3">3. 研究开发组织管理水平得分</td>
                <td>{{ sumPart3 || '-' }}</td>
              </tr>
              <tr>
                <td>&emsp;&emsp;技术先进程度</td>
                <td>{{ current.advanced || '-' }}</td>
                <td colspan="3">&emsp;&emsp;组织管理制度</td>
                <td>{{ current.generalLedger || '-' }}</td>
              </tr>
              <tr>
                <td>&emsp;&emsp;核心支持作用</td>
                <td>{{ current.effect || '-' }}</td>
                <td colspan="3">&emsp;&emsp;研发机构</td>
                <td>{{ current.cooperation || '-' }}</td>
              </tr>
              <tr>
                <td>&emsp;&emsp;知识产权数量</td>
                <td>{{ current.patentAmount || '-' }}</td>
                <td colspan="3">&emsp;&emsp;成果转化奖励制度</td>
                <td>{{ current.excitation || '-' }}</td>
              </tr>
              <tr>
                <td>&emsp;&emsp;知识产权获得方式</td>
                <td>{{ current.acquirement || '-' }}</td>
                <td colspan="3">&emsp;&emsp;人才绩效制度</td>
                <td>{{ current.foster || '-' }}</td>
              </tr>
              <tr>
                <td>&emsp;&emsp;（加分）参与标准制定</td>
                <td>{{ current.contribution || '-' }}</td>
                <td colspan="3">4. 成长指标得分</td>
                <td>{{ sumPart4 || '-' }}</td>
              </tr>
              <tr>
                <td rowspan="2">2. 科技成果转化能力得分</td>
                <td rowspan="2">{{ sumPart2 }}</td>
                <td colspan="3">&emsp;&emsp;净资产增长率</td>
                <td>{{ current.NAVScore || '' }}</td>
              </tr>
              <tr>
                <td colspan="3">&emsp;&emsp;销售收入增长率</td>
                <td>{{ current.salesScore || '' }}</td>
              </tr>
              <tr>
                <td colspan="7">综合得分：{{ totalScore }}</td>
              </tr>
              <tr>
                <td colspan="7">综合评价是否符合认定条件：
                  <a-checkbox :checked="totalScore>=71" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                  <a-checkbox :checked="totalScore <71" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
                </td>
              </tr>
            </tbody>
          </table>
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
      current: {},
      tecIndustryMap: {}
    }
  },
  created () {
    this.$highTecIndustryTreeMap(this).then((res) => {
      this.tecIndustryMap = res
    })
    this.loadCurrent()
  },
  computed: {

    tecIndustries () {
      const result = []
      if (this.current.tecIndustries) {
        this.current.tecIndustries.forEach(tecIndustry => {
          if (tecIndustry) {
            result.push(tecIndustry.split(',').map(str => this.tecIndustryMap[str]).join('/'))
          }
        })
      }
      return result.join('<br/>')
    },
    sumPart1 () {
      let total = 0
      if (this.current.advanced) {
        total += this.current.advanced
      }
      if (this.current.effect) {
        total += this.current.effect
      }
      if (this.current.patentAmount) {
        total += this.current.patentAmount
      }
      if (this.current.acquirement) {
        total += this.current.acquirement
      }
      if (this.current.contribution) {
        total += this.current.contribution
      }
      return total
    },
    sumPart2 () {
      let total = 0
      if (this.current.scienceResult) {
        total += this.current.scienceResult
      }
      if (this.current.addScienceResult) {
        total += this.current.addScienceResult
      }
      return total
    },
    sumPart3 () {
      let total = 0
      if (this.current.generalLedger) {
        total += this.current.generalLedger
      }
      if (this.current.cooperation) {
        total += this.current.cooperation
      }
      if (this.current.excitation) {
        total += this.current.excitation
      }
      if (this.current.foster) {
        total += this.current.foster
      }
      return total
    },
    sumPart4 () {
      let total = 0
      if (this.current.NAVScore) {
        total += this.current.NAVScore
      }
      if (this.current.salesScore) {
        total += this.current.salesScore
      }
      return total
    },
    totalScore () {
      return this.sumPart1 + this.sumPart2 + this.sumPart3 + this.sumPart4
    }
  },
  methods: {
    search () {
      this.loadCurrent()
    },
    loadCurrent () {
      this.spinning = true
      this.$http.get('/highScore/getTotalScore', { params: { year: this.currentYear } }).then(res => {
        let current = {}
        if (res.success && res.data) {
          current = res.data
        } else {
          this.$message.error(res.errorMessage || '获取得分情况失败')
        }
        this.current = current
      }).finally(() => {
        this.spinning = false
      })
    }
  }
}
</script>

<style lang="less" scoped>
  .normalTd tr td{
  font-size:14px;
  height:42px;
  padding-left:18px;
  word-break: break-all;
  padding-right:20px;
  border:solid #F0F0F0 1px;
  }
  .spanLabel{
    padding-left:6px;
    padding-right:16px;
  }
</style>
