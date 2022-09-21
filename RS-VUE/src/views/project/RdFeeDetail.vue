<template>
  <a-card :bordered="false">
    <a-spin :spinning="spinning" tip="请稍后...">
      <div style="text-align: center; width: 100%" v-if="control.view">
        <div style="width: 1000px; margin: 0 auto">
          <div style="position: relative">
            <span style="position: absolute; display: block" v-if="control.export">
              <a-button type="primary" @click="exportData">导出</a-button>
            </span>
            <b style="font-size: 24px">研发费用加计扣除优惠明细表（A107012）</b>
          </div>
          <table border style="border: solid #333 1px; width: 100%">
            <tr>
              <th style="width: 8%">行次</th>
              <th style="width: 72%">项 目</th>
              <th style="width: 20%">金额（数量）</th>
            </tr>
            <tr v-for="(row, index) in tableData" :key="row.num" :class="row.class">
              <td style="text-align: center">{{ row.num }}</td>
              <td style="text-align: left">{{ row.label }}</td>
              <td
                :style="{ textAlign: index === 0 ? 'center' : 'right' }"
              >
                <template v-if="editNums[row.num] && control.save">
                  <a @click="showModal">{{ getValue(row.keys, row.num) | NumberFormat }}</a>
                </template>
                <template v-else>{{ getValue(row.keys, row.num) | NumberFormat }}</template>
              </td>
            </tr>
          </table>
          <a-modal
            v-if="control.save"
            v-model="visible"
            title="编辑优惠明细"
            okText="保存"
            :width="700"
            @ok="handleOk"
            @cancel="handleCancel"
            :closable="false"
            destroyOnClose
            :confirmLoading="loading"
          >
            <a-row>
              <a-col :span="20">
                <p class="lgtxt">46:(减：特殊收入部分)</p>
              </a-col>
              <a-col :span="4">
                <a-input-number
                  :min="-$store.state.totalMax"
                  :max="$store.state.totalMax"
                  :precision="2"
                  :defaultValue="saveValue.k46"
                  @change="(value) => { saveValue.k46 = value }"
                />
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="20">
                <p class="lgtxt">48:(减：当年销售研发活动直接形成产品（包括组成部分）对应的材料部分)</p>
              </a-col>
              <a-col :span="4">
                <a-input-number
                  :min="-$store.state.totalMax"
                  :max="$store.state.totalMax"
                  :precision="2"
                  :defaultValue="saveValue.k48"
                  @change="(value) => { saveValue.k48 = value }"
                />
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="20">
                <p class="lgtxt">49:(减：以前年度销售研发活动直接形成产品（包括组成部分）对应材料部分结转金额)</p>
              </a-col>
              <a-col :span="4">
                <a-input-number
                  :min="-$store.state.totalMax"
                  :max="$store.state.totalMax"
                  :precision="2"
                  :defaultValue="saveValue.k49"
                  @change="(value) => { saveValue.k49 = value }"
                />
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="20">
                <p class="lgtxt">50:(八、加计扣除比例)</p>
              </a-col>
              <a-col :span="4">
                <a-input-number
                  :min="0"
                  :max="1"
                  :precision="2"
                  step="0.01"
                  :defaultValue="saveValue.rdRatio"
                  @change="(value) => { saveValue.rdRatio = value }"
                />
              </a-col>
            </a-row>
          </a-modal>
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
      visible: false,
      loading: false,
      saveValue: {
        k46: 0,
        k48: 0,
        k49: 0,
        rdRatio: 0
      },
      editNums: { 46: true, 48: true, 49: true, 50: true },
      totalFeeKey: [
        'salary',
        'insurance',
        'material',
        'fuel',
        'stimulus',
        'trialProd',
        'sampleMachine',
        'trialTest',
        'inspection',
        'lab',
        'prod',
        'softAmortization',
        'patentAmortization',
        'otherAmortization',
        'design',
        'techProcedure',
        'clinicalTrials',
        'explore',
        'travel'
      ],
      tableData: [
        { num: 1, label: '本年可享受研发费用加计扣除项目数量', keys: ['rdCount'] },
        { num: 2, label: '一、自主研发、合作研发、集中研发（3+7+16+19+23+34）', keys: ['totalFeeKey'], class: 'gray' },
        { num: 3, label: '（一）人员人工费用（4+5+6）', keys: ['salary', 'insurance'], class: 'gray' },
        { num: 4, label: '1.直接从事研发活动人员工资薪金', keys: ['salary'] },
        { num: 5, label: '2.直接从事研发活动人员五险一金', keys: ['insurance'] },
        { num: 6, label: '3.外聘研发人员的劳务费用' },
        {
          num: 7,
          label: '（二）直接投入费用（8+9+10+11+12+13+14+15）',
          keys: ['material', 'fuel', 'stimulus', 'trialProd', 'sampleMachine', 'trialTest', 'inspection'],
          class: 'gray'
        },
        { num: 8, label: '1.研发活动直接消耗材料费用', keys: ['material'] },
        { num: 9, label: '2.研发活动直接消耗燃料费用', keys: ['fuel'] },
        { num: 10, label: '3.研发活动直接消耗动力费用', keys: ['stimulus'] },
        { num: 11, label: '4.用于中间试验和产品试制的模具、工艺装备开发及制造费', keys: ['trialProd'] },
        { num: 12, label: '5.用于不构成固定资产的样品、样机及一般测试手段购置费', keys: ['sampleMachine'] },
        { num: 13, label: '6.用于试制产品的检验费', keys: ['trialTest'] },
        { num: 14, label: '7.用于研发活动的仪器、设备的运行维护、调整、检验、维修等费用', keys: ['inspection'] },
        { num: 15, label: '8.通过经营租赁方式租入的用于研发活动的仪器、设备租赁费' },
        { num: 16, label: '（三）折旧费用（17+18）', keys: ['lab', 'prod'], class: 'gray' },
        { num: 17, label: '1.用于研发活动的仪器的折旧费', keys: ['lab'] },
        { num: 18, label: '2.用于研发活动的设备的折旧费', keys: ['prod'] },
        {
          num: 19,
          label: '（四）无形资产摊销（20+21+22）',
          keys: ['softAmortization', 'patentAmortization', 'otherAmortization'],
          class: 'gray'
        },
        { num: 20, label: '1.用于研发活动的软件的摊销费用', keys: ['softAmortization'] },
        { num: 21, label: '2.用于研发活动的专利权的摊销费用', keys: ['patentAmortization'] },
        {
          num: 22,
          label: '3.用于研发活动的非专利技术（包括许可证、专有技术、设计和计算方法等）的摊销费用',
          keys: ['otherAmortization']
        },
        {
          num: 23,
          label: '（五）新产品设计费等（24+25+26+27）',
          keys: ['design', 'techProcedure', 'clinicalTrials', 'explore'],
          class: 'gray'
        },
        { num: 24, label: '1.新产品设计费', keys: ['design'] },
        { num: 25, label: '2.新工艺规程制定费', keys: ['techProcedure'] },
        { num: 26, label: '3.新药研制的临床试验费', keys: ['clinicalTrials'] },
        { num: 27, label: '4.勘探开发技术的现场试验费', keys: ['explore'] },
        {
          num: 28,
          label: '（六）其他相关费用(29+30+31+32+33)',
          keys: ['book', 'rdProduction', 'copyright', 'benefits', 'travel'],
          class: 'gray'
        },
        { num: 29, label: '1.技术图书资料费、资料翻译费、专家咨询费、高新科技研发保险费', keys: ['book'] },
        { num: 30, label: '2.研发成果的检索、分析、评议、论证、鉴定、评审、评估、验收费用', keys: ['rdProduction'] },
        { num: 31, label: '3.知识产权的申请费、注册费、代理费', keys: ['copyright'] },
        { num: 32, label: '4.职工福利费、补充养老保险费、补充医疗保险费', keys: ['benefits'] },
        { num: 33, label: '5.差旅费、会议费', keys: ['travel'] },
        { num: 34, label: '（七）经限额调整后的其他相关费用', keys: ['other'], class: 'gray' },
        { num: 35, label: '二、委托研发[36+37+39]', keys: ['inside', 'outside'], class: 'gray' },
        { num: 36, label: '（一）委托境内机构或个人进行研发活动所发生的费用', keys: ['inside'] },
        { num: 37, label: '（二）委托境外机构进行研发活动发生的费用', keys: ['outside'] },
        { num: 38, label: '其中：允许加计扣除的委托境外机构进行研发活动发生的费用' },
        { num: 39, label: '（三）委托境外个人进行研发活动发生的费用' },
        { num: 40, label: '三、年度研发费用小计(2+36×80%+38)', keys: ['totalFeeInside80'], class: 'gray' },
        { num: 41, label: '（一）本年费用化金额', keys: ['totalFeeInside80'] },
        { num: 42, label: '（二）本年资本化金额' },
        { num: 43, label: '四、本年形成无形资产摊销额' },
        { num: 44, label: '五、以前年度形成无形资产本年摊销额' },
        { num: 45, label: '六、允许扣除的研发费用合计（41+43+44）', keys: ['totalFeeInside80'], class: 'gray' },
        { num: 46, label: '减：特殊收入部分', keys: ['k46'] },
        { num: 47, label: '七、允许扣除的研发费用抵减特殊收入后的金额(45-46)', keys: ['k47'], class: 'gray' },
        { num: 48, label: '减：当年销售研发活动直接形成产品（包括组成部分）对应的材料部分', keys: ['k48'] },
        { num: 49, label: '减：以前年度销售研发活动直接形成产品（包括组成部分）对应材料部分结转金额', keys: ['k49'] },
        { num: 50, label: '八、加计扣除比例（%）', keys: ['rdRatio'], class: 'gray' },
        { num: 51, label: '九、本年研发费用加计扣除总额（47-48-49）× 75%', keys: ['k51'], class: 'gray' },
        { num: 52, label: '本年可享受研发费用加计扣除项目数量', class: 'gray' }
      ],
      dataMap: {},
      spinning: false,
      control: {
        view: this.$auth('project:rdFeeDetail:view'),
        export: this.$auth('project:rdFeeDetail:export'),
        save: this.$auth('project:rdFeeDetail:save')
      }
    }
  },
  created () {
    this.loadData()
  },
  methods: {
    search () {
      this.loadData()
    },
    loadData () {
      this.spinning = true
      this.$http
        .get('/projectYearFee/getDetailData', { params: { year: this.currentYear } })
        .then(res => {
          if (res.success && res.data) {
            this.dataMap = res.data
            let total = 0
            this.totalFeeKey.forEach(key => {
              total = Number(total) + (this.dataMap[key] ? Number(this.dataMap[key]) : 0)
            })
            this.dataMap['totalFeeKey'] = total.toFixed(2)
            this.dataMap['totalFeeInside80'] = (total + (this.dataMap['inside'] || 0) * 0.8).toFixed(2)
            this.freshData()
          } else {
            this.dataMap = {}
            this.$message.error(res.errorMessage || '加载失败')
          }
        })
        .finally(() => {
          this.spinning = false
        })
    },
    freshData () {
      this.tableData[50].label = `九、本年研发费用加计扣除总额（47-48-49）×${this.dataMap['rdRatioStr']}`
      this.dataMap['k47'] = this.dataMap['totalFeeInside80'] - Number(this.dataMap['k46'])
      this.dataMap['k51'] = (
        (this.dataMap['k47'] - Number(this.dataMap['k48']) - Number(this.dataMap['k49'])) *
              this.dataMap['rdRatio']
      ).toFixed(2)
    },
    exportData () {
      this.spinning = true
      this.$exportData(
        '/projectYearFee/exportDetailData',
        { year: this.currentYear },
        `${this.currentYear}年优惠明细表.xls`,
        this.$message
      ).then(res => {
        this.spinning = false
      })
    },
    getValue (keys, num) {
      if (!keys || !keys.length) {
        return '0.00'
      }
      let total = 0
      keys.forEach(key => {
        total = Number(total) + (this.dataMap[key] ? Number(this.dataMap[key]) : 0)
      })
      if (keys[0]) {
      }
      // if (num === 51) {
      //   total = total * (this.dataMap['rdRatio'] || 0)
      // }
      if (num !== 1) {
        total = total.toFixed(2)
      }
      if (num === 50) {
        total = (total * 100).toFixed(2) + '%'
      }
      return total
    },
    showModal () {
      this.saveValue = {
        k46: this.dataMap['k46'],
        k48: this.dataMap['k48'],
        k49: this.dataMap['k49'],
        rdRatio: this.dataMap['rdRatio']
      }
      this.visible = true
    },
    handleOk () {
      this.loading = true
      this.saveValue.year = this.currentYear
      this.$http.post('/projectYearFee/save', this.saveValue).then(res => {
        this.loading = false
        if (res.success) {
          this.dataMap['k46'] = this.saveValue.k46
          this.dataMap['k48'] = this.saveValue.k48
          this.dataMap['k49'] = this.saveValue.k49
          this.dataMap['rdRatio'] = this.saveValue.rdRatio
          this.dataMap['rdRatioStr'] = (this.saveValue.rdRatio * 100).toFixed(2) + '%'
          this.freshData()
          this.visible = false
          this.$message.success('保存成功')
        }
      })
    },
    handleCancel () {
      this.saveValue = {
        k46: 0,
        k48: 0,
        k49: 0,
        rdRatio: 0
      }
      this.visible = false
    }
  }
}
</script>

<style>
.gray {
  background-color: rgb(192, 192, 192);
}
.lgtxt {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
