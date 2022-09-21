<template>
  <a-card :bordered="false" :bodyStyle="{ height: '100%', overflow: 'auto' }">
    <a-spin :spinning="spin" tip="请稍后..." v-if="$auth('science:active:view')">
      <vxe-grid
        id="science:active"
        :data="tableData"
        border
        rowId="id"
        highlight-hover-row
        show-overflow
        show-header-overflow
        resizable
        :toolbar="{zoom:true,custom:true}"
        :customConfig="{storage: { visible: true, resizable: true } }"
      >
        <template v-slot:buttons>
          <div v-if="$auth('science:active:export')">
            <a-button type="primary" @click="exportReport" :disabled="disabled">导出报告</a-button>
          </div>
        </template>
        <vxe-table-column title="序号" type="seq" :width="50" />
        <vxe-table-column title :min-width="200" field="active1"></vxe-table-column>
        <vxe-table-column title="计量单位" field="unit1" :width="100"></vxe-table-column>
        <vxe-table-column title :width="80" field="relation1"></vxe-table-column>
        <vxe-table-column title="数量" :width="120" field="amount1">
          <template slot-scope="{row}">
            <span v-if="row.read1">{{ row.amount1 }}</span>
            <span v-else>
              <span v-if="$auth('science:active:edit')">
                <template v-if="row.unit1 === '千元'">
                  <a-input-number
                    style="width: 100%"
                    size="small"
                    :value="row.amount1"
                    :precision="2"
                    @change="(v)=>valueChange(v,row,'1')"
                    @blur="saveValue(row,'1')"
                  />
                </template>
                <template v-else>
                  <a-input-number
                    style="width: 100%"
                    size="small"
                    :value="row.amount1"
                    @change="(v)=>valueChange(v,row,'1')"
                    @blur="saveValue(row,'1')"
                  />
                </template>
              </span>
              <span v-else>{{ row.amount1 }}</span>
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column title :min-width="200" field="active2"></vxe-table-column>
        <vxe-table-column title="计量单位" :width="100" field="unit2"></vxe-table-column>
        <vxe-table-column title :width="80" field="relation2"></vxe-table-column>
        <vxe-table-column title="数量" :width="120" field="amount2">
          <template slot-scope="{row}">
            <span v-if="row.read2">{{ row.amount2 }}</span>
            <span v-else>
              <span v-if="$auth('science:active:edit')">
                <template v-if="row.unit2 === '千元'">
                  <a-input-number
                    style="width: 100%"
                    size="small"
                    :value="row.amount2"
                    :precision="2"
                    @change="(v)=>valueChange(v,row,'2')"
                    @blur="saveValue(row,'2')"
                  />
                </template>
                <template v-else>
                  <a-input-number
                    style="width: 100%"
                    size="small"
                    :value="row.amount2"
                    @change="(v)=>valueChange(v,row,'2')"
                    @blur="saveValue(row,'2')"
                  />
                </template>
              </span>
              <span v-else>{{ row.amount2 }}</span>
            </span>
          </template>
        </vxe-table-column>
      </vxe-grid>
    </a-spin>
  </a-card>
</template>

<script>
import yearMiXin from '@/utils/yearMixin'

const defaultTableData = [
  { active1: '甲', unit1: '乙', relation1: '丙', amount1: '1', read1: true, active2: '甲', unit2: '乙', relation2: '丙', amount2: '1', id: '1', read2: true },
  { active1: '一、研究开发人员情况', unit1: '—', relation1: '—', amount1: '—', read1: true, active2: '五、研发产出及相关情况', unit2: '—', relation2: '—', amount2: '—', id: '2', read2: true },
  { active1: ' 研究开发人员合计', unit1: '人', relation1: '1', amount1: '0', key1: 'employeeNum', read1: true, active2: '(一)专利情况', unit2: '—', relation2: '—', amount2: '—', id: '3', read2: true },
  // 未获取查询方式
  { active1: '其中：管理和服务人员', unit1: '人', relation1: '2', amount1: '0', read1: true, active2: '当年专利申请数', unit2: '件', relation2: '29', amount2: '0', id: '4', key2: 'k29' },
  { active1: '其中：女性', unit1: '人', relation1: '3', amount1: '0', key1: 'female', read1: true, active2: '其中：发明专利', unit2: '件', relation2: '30', amount2: '0', id: '5', key2: 'k30' },
  { active1: '其中：全职人员', unit1: '人', relation1: '4', amount1: '0', key1: 'employeeNum', read1: true, active2: '其中：PCT专利', unit2: '件', relation2: '31', amount2: '0', id: '6', key2: 'k31' },
  { active1: '其中：本科毕业及以上人员', unit1: '人', relation1: '5', amount1: '0', key1: 'undergraduate', read1: true, active2: '期末有效发明专利数', unit2: '件', relation2: '32', amount2: '0', id: '7', key2: 'k32' },
  { active1: '其中：外聘人员', unit1: '人', relation1: '6', amount1: '0', read1: true, active2: '其中：已被实施', unit2: '件', relation2: '33', amount2: '0', id: '8', key2: 'k33' },
  { active1: '二、研究开发费用情况', unit1: '—', relation1: '—', amount1: '—', read1: true, active2: '*其中：境外授权', unit2: '件', relation2: '34', amount2: '0', id: '9', key2: 'k34' },
  { active1: '研究开发费用合计', unit1: '千元', relation1: '7', amount1: '0', key1: 'cost', read1: true, active2: '(二)新产品情况', unit2: '—', relation2: '—', amount2: '—', id: '10', read2: true },
  { active1: '1.人员人工费用', unit1: '千元', relation1: '8', amount1: '0', key1: 'employee', read1: true, active2: '*新产品产值', unit2: '千元', relation2: '35', amount2: '0', id: '11', key2: 'k35' },
  { active1: '2.直接投入费用', unit1: '千元', relation1: '9', amount1: '0', key1: 'direct', read1: true, active2: '*新产品销售收入', unit2: '千元', relation2: '36', amount2: '0', id: '12', key2: 'k36' },
  { active1: '3.折旧费用与长期待摊费用', unit1: '千元', relation1: '10', amount1: '0', key1: 'depreciation', read1: true, active2: '*其中：出口', unit2: '千元', relation2: '37', amount2: '0', id: '13', key2: 'k37' },
  { active1: '4.无形资产摊销费用', unit1: '千元', relation1: '11', amount1: '0', key1: 'amortization', read1: true, active2: '(三)其他情况', unit2: '—', relation2: '—', amount2: '—', id: '14', read2: true },
  { active1: '5.设计费用', unit1: '千元', relation1: '12', amount1: 'design', key1: 'design', read1: true, active2: '*期末拥有注册商标', unit2: '件', relation2: '38', amount2: '0', id: '15', key2: 'k38' },
  { active1: '6.装备调试费用与试验费用', unit1: '千元', relation1: '13', amount1: '0', key1: 'trial', read1: true, active2: '*其中：境外注册', unit2: '件', relation2: '39', amount2: '0', id: '16', key2: 'k39' },
  { active1: '7.委托外部研究开发费用', unit1: '千元', relation1: '14', amount1: '0', key1: 'k14', active2: '发表科技论文', unit2: '篇', relation2: '40', amount2: '0', id: '17', key2: 'k40' },
  { active1: '①委托境内研究机构', unit1: '千元', relation1: '15', amount1: '0', key1: 'k15', active2: '形成国家或行业标准', unit2: '项', relation2: '41', amount2: '0', id: '18', key2: 'k41' },
  { active1: '②委托境内高等学校', unit1: '千元', relation1: '16', amount1: '0', key1: 'k16', active2: '软件著作权', unit2: '个', relation2: '42', amount2: '0', id: '19', key2: 'k42' },
  { active1: '③委托境内企业', unit1: '千元', relation1: '17', amount1: '0', key1: 'k17', active2: '六、其他相关情况', unit2: '—', relation2: '—', amount2: '—', id: '20', read2: true },
  { active1: '④委托境外机构', unit1: '千元', relation1: '18', amount1: '0', key1: 'k18', active2: '(一)政府经费及相关政策落实情况', unit2: '—', relation2: '—', amount2: '—', id: '21', read2: true },
  { active1: '8.其他费用', unit1: '千元', relation1: '19', amount1: '0', key1: 'other', read1: true, active2: '来自政府部门的研究开发经费', unit2: '千元', relation2: '43', amount2: '0', id: '22', key2: 'k43' },
  { active1: '三、研究开发资产情况', unit1: '—', relation1: '—', amount1: '—', read1: true, active2: '研究开发费用加计扣除减免税', unit2: '千元', relation2: '44', amount2: '0', id: '23', key2: 'k44' },
  { active1: '当年形成用于研究开发的固定资产', unit1: '千元', relation1: '20', amount1: '0', key1: 'k20', active2: '高新技术企业减免税', unit2: '千元', relation2: '45', amount2: '0', id: '24', key2: 'k45' },
  { active1: '其中：仪器和设备', unit1: '千元', relation1: '21', amount1: '0', key1: 'k21', active2: '(二) 技术改造和技术获取情况', unit2: '—', relation2: '—', amount2: '—', id: '25', read2: true },
  { active1: '四、企业办研究开发机构（境内）情况', unit1: '—', relation1: '—', amount1: '—', read1: true, active2: ' *技术改造经费支出', unit2: '千元', relation2: '46', amount2: '0', id: '26', key2: 'k46' },
  { active1: '期末机构数', unit1: '个', relation1: '22', amount1: '0', key1: 'k22', active2: '*购买境内技术经费支出', unit2: '千元', relation2: '47', amount2: '0', id: '27', key2: 'k47' },
  { active1: '机构研究开发人员', unit1: '人', relation1: '23', amount1: '0', key1: 'k23', active2: '*引进境外技术经费支出', unit2: '千元', relation2: '48', amount2: '0', id: '28', key2: 'k48' },
  { active1: '其中：博士毕业', unit1: '人', relation1: '24', amount1: '0', key1: 'k24', active2: '*引进境外技术的消化吸收经费支出', unit2: '千元', relation2: '49', amount2: '0', id: '29', key2: 'k49' },
  { active1: '硕士毕业', unit1: '人', relation1: '25', amount1: '0', key1: 'k25', active2: '（三）期末企业在境外设立的研发机构数', unit2: '—', relation2: '—', amount2: '—', id: '30', read2: true },
  { active1: '机构研究开发费用', unit1: '千元', relation1: '26', amount1: '0', key1: 'k26', active2: ' 期末企业在境外设立的研究开发机构', unit2: '个', relation2: '50', amount2: '0', id: '31', key2: 'k50' },
  { active1: '期末仪器和设备原价', unit1: '千元', relation1: '27', amount1: '0', key1: 'k27', active2: '—', unit2: '—', relation2: '—', amount2: '—', id: '32', read2: true },
  { active1: '其中：进口', unit1: '千元', relation1: '28', amount1: '0', key1: 'k28', active2: '—', unit2: '—', relation2: '—', amount2: '—', id: '33', read2: true },
  { active1: '上年研究开发费用合计', unit1: '千元', relation1: '51', amount1: '0', key1: 'k51', active2: '其中：已加计扣除的研究开发费用', unit2: '千元', relation2: '52', amount2: '0', id: '34', key2: 'k52' }
]

const default2021TableData = [
  { active1: '甲', unit1: '乙', relation1: '丙', amount1: '1', read1: true, active2: '甲', unit2: '乙', relation2: '丙', amount2: '1', id: '1', read2: true },
  { active1: '一、研究开发人员情况', unit1: '—', relation1: '—', amount1: '—', read1: true, active2: '六、企业办研究开发机构（境内）情况', unit2: '—', relation2: '—', amount2: '—', id: '2', read2: true },
  { active1: ' 研究开发人员合计', unit1: '人', relation1: '1', amount1: '0', key1: 'employeeNum', read1: true, active2: '期末机构数', unit2: '个', relation2: '22', amount2: '—', id: '3', key2: 'k22' },
  // 未获取查询方式
  { active1: '其中：管理和服务人员', unit1: '人', relation1: '2', amount1: '0', key1: 'k2', active2: '机构研究开发人员', unit2: '人', relation2: '23', amount2: '0', id: '4', key2: 'k23' },
  { active1: '其中：女性', unit1: '人', relation1: '3', amount1: '0', key1: 'female', read1: true, active2: '其中：博士毕业', unit2: '人', relation2: '24', amount2: '0', id: '5', key2: 'k324' },
  { active1: '其中：全职人员', unit1: '人', relation1: '4', amount1: '0', read1: true, active2: '硕士毕业', unit2: '人', relation2: '25', amount2: '0', id: '6', key2: 'k25' },
  { active1: '其中：本科毕业及以上人员', unit1: '人', relation1: '5', amount1: '0', key1: 'undergraduate', read1: true, active2: '机构研究开发费用', unit2: '千元', relation2: '26', amount2: '0', id: '7', key2: 'rdFunds' },
  { active1: '其中：外聘人员', unit1: '人', relation1: '6', amount1: '0', read1: true, active2: '期末仪器和设备原价', unit2: '千元', relation2: '27', amount2: '0', id: '8', key2: 'equipmentAmount', read2: true },
  { active1: '二、研究开发费用情况', unit1: '—', relation1: '—', amount1: '—', read1: true, active2: '七、研究开发产出及相关情况', unit2: '—', relation2: '—', amount2: '—', id: '9', read2: true },
  { active1: '研究开发费用合计', unit1: '千元', relation1: '7', amount1: '0', key1: 'cost', read1: true, active2: '(一)专利情况', unit2: '—', relation2: '—', amount2: '—', id: '10', read2: true },
  { active1: '1.人员人工费用', unit1: '千元', relation1: '8', amount1: '0', key1: 'employee', read1: true, active2: '当年专利申请数', unit2: '件', relation2: '29', amount2: '0', id: '11', key2: 'patentCnt', read2: true },
  { active1: '2.直接投入费用', unit1: '千元', relation1: '9', amount1: '0', key1: 'direct', read1: true, active2: '其中：发明专利', unit2: '件', relation2: '30', amount2: '0', id: '12', key2: 'inventionCnt', read2: true },
  { active1: '3.折旧费用与长期待摊费用', unit1: '千元', relation1: '10', amount1: '0', key1: 'depreciation', read1: true, active2: '期末有效发明专利数', unit2: '千元', relation2: '32', amount2: '0', id: '13', key2: 'k32' },
  { active1: '4.无形资产摊销费用', unit1: '千元', relation1: '11', amount1: '0', key1: 'amortization', read1: true, active2: '其中：已被实施', unit2: '件', relation2: '33', amount2: '0', id: '14', key2: 'k33' },
  { active1: '5.设计费用', unit1: '千元', relation1: '12', amount1: 'design', key1: 'design', read1: true, active2: '专利所有权转让及许可数', unit2: '件', relation2: '53', amount2: '0', id: '15', key2: 'k53' },
  { active1: '6.装备调试费用与试验费用', unit1: '千元', relation1: '13', amount1: '0', key1: 'trial', read1: true, active2: '专利所有权转让及许可收入', unit2: '千元', relation2: '54', amount2: '0', id: '16', key2: 'k54' },
  { active1: '7.委托外部研究开发费用', unit1: '千元', relation1: '14', amount1: '0', key1: 'k14', active2: '(二)新产品情况', unit2: '—', relation2: '—', amount2: '—', id: '17', read2: true },
  { active1: '①委托境内研究机构', unit1: '千元', relation1: '15', amount1: '0', key1: 'k15', active2: '*新产品销售收入', unit2: '千元', relation2: '36', amount2: '0', id: '18', key2: 'k36' },
  { active1: '②委托境内高等学校', unit1: '千元', relation1: '16', amount1: '0', key1: 'k16', active2: '*其中：出口', unit2: '个', relation2: '37', amount2: '0', id: '19', key2: 'k37' },
  { active1: '③委托境内企业', unit1: '千元', relation1: '17', amount1: '0', key1: 'k17', active2: '(三)其他情况', unit2: '—', relation2: '—', amount2: '—', id: '20', read2: true },
  { active1: '④委托境外机构', unit1: '千元', relation1: '18', amount1: '0', key1: 'k18', active2: '*期末拥有注册商标', unit2: '件', relation2: '38', amount2: '—', id: '21', key2: 'k38' },
  { active1: '8.其他费用', unit1: '千元', relation1: '19', amount1: '0', key1: 'other', read1: true, active2: '发表科技论文', unit2: '篇', relation2: '40', amount2: '0', id: '22', key2: 'k40' },
  { active1: '三、研究开发资产情况', unit1: '—', relation1: '—', amount1: '—', read1: true, active2: '形成国家或行业标准', unit2: '项', relation2: '41', amount2: '0', id: '23', key2: 'k41' },
  { active1: '当年形成用于研究开发的固定资产', unit1: '千元', relation1: '20', amount1: '0', key1: 'equipmentAmount', read1: true, active2: '八、其他相关情况', unit2: '—', relation2: '—', amount2: '—', id: '24' },
  { active1: '其中：仪器和设备', unit1: '千元', relation1: '21', amount1: '0', key1: 'equipmentAmount', read1: true, active2: '(二) 技术改造和技术获取情况', unit2: '—', relation2: '—', amount2: '—', id: '25', read2: true },
  { active1: '四、研究开发支出资金来源', unit1: '—', relation1: '—', amount1: '—', read1: true, active2: '技术改造经费支出', unit2: '千元', relation2: '46', amount2: '0', id: '26', key2: 'k46' },
  { active1: '1.来自企业自筹', unit1: '千元', relation1: '57', amount1: '0', key1: 'k57', active2: '购买境内技术经费支出', unit2: '千元', relation2: '47', amount2: '0', id: '27', key2: 'k47' },
  { active1: '2.来自政府部门', unit1: '千元', relation1: '43', amount1: '0', key1: 'k43', active2: '引进境外技术经费支出', unit2: '千元', relation2: '48', amount2: '0', id: '28', key2: 'k48' },
  { active1: '3.来自银行贷款', unit1: '千元', relation1: '58', amount1: '0', key1: 'k58', active2: '引进境外技术的消化吸收经费支出', unit2: '千元', relation2: '49', amount2: '0', id: '29', key2: 'k49' },
  { active1: '4.来自风险投资', unit1: '千元', relation1: '55', amount1: '0', key1: 'k55', active2: '(二)企业办研究开发机构（境外）情况', unit2: '—', relation2: '—', amount2: '—', id: '30', read2: true },
  { active1: '5.来自其他渠道', unit1: '千元', relation1: '56', amount1: '0', key1: 'k56', active2: '期末企业在境外设立的研究开发机构数', unit2: '个', relation2: '50', amount2: '0', id: '31', key2: 'k50' },
  { active1: '五、相关政策落实情况', unit1: '—', relation1: '—', amount1: '—', read1: true, id: '32', read2: true },
  { active1: '申报加计扣除减免税的研究开发支出', unit1: '千元', relation1: '52', amount1: '0', key1: 'k52', id: '33', read2: true },
  { active1: '加计扣除减免税金额', unit1: '千元', relation1: '44', amount1: '0', key1: 'k44', id: '34', read2: true },
  { active1: '高新技术企业减免税金额', unit1: '千元', relation1: '45', amount1: '0', key1: 'k45', id: '35', read2: true }
]
export default {
  name: 'RdActiveReport',
  mixins: [yearMiXin],
  components: {
  },
  data () {
    return {
      spin: false,
      tableData: [],
      disabled: false
    }
  },
  created () {
    this.search()
  },
  methods: {
    saveValue (record, key) {
      if (!this.hasModify) {
        return
      }
      var value = {}
      value.pKey = record[`key${key}`]
      value.pValue = record[`amount${key}`]
      value.year = this.currentYear
      this.$http.post('/summary/saveActivity', value)
        .then(res => {
          if (!res || !res.success) {
            this.$message.error(`${record['active' + key]} 保存失败${res.errorMessage ? '：' + res.errorMessage : ''}`)
            this.search()
          }
        }).finally(res => {
          this.hasModify = false
        })
    },
    valueChange (v, record, key) {
      if (Number(v) || v === 0) {
        this.hasModify = true
        this.$set(record, `amount${key}`, v)
      }
    },
    search () {
      if (!this.$auth('science:active:view')) {
        this.$notification.error({ message: '无此权限，请跟管理员联系！' })
        return
      }
      this.spin = true
      this.$http.get('/summary/rdActive', { params: { year: this.currentYear } })
        .then(res => {
          this.disabled = false
          var dMap = res.data
          if (!res.data) {
            this.disabled = true
            dMap = {}
          }
          const defaultData = this.currentYear >= this.$store.state.generalLedgerYear ? default2021TableData : defaultTableData
          var arr = []
          arr.push({ ...defaultData[0] })
          var tempRow
          var key1
          var key2
          for (let i = 1; i < defaultData.length; i++) {
            tempRow = { ...defaultData[i] }
            key1 = tempRow.key1
            key2 = tempRow.key2
            if (key1) {
              tempRow.amount1 = dMap[key1] ? dMap[key1] : 0
            }
            if (key2) {
              tempRow.amount2 = dMap[key2] ? dMap[key2] : 0
            }
            arr.push(tempRow)
          }
          this.tableData = arr
        }).finally(r => {
          this.spin = false
        })
    },
    exportReport () {
      this.$exportData('/summary/exportActive', { year: this.currentYear }, `${this.currentYear}企业研究开发活动及相关情况.xls`, this.$message)
    }
  }
}
</script>

<style>
</style>
