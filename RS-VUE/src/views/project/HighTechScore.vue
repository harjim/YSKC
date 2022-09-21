<!--
 * @Author: zdf
 * @Date: 2021-10-18 13:47:21
 * @LastEditTime: 2022-08-18 14:43:23
 * @LastEditors: zdf
 * @Description: 高新评分评价
 * @FilePath: \RS-VUE\src\views\project\HighTechScore.vue
-->
<template>
  <a-card :bordered="false" style="overflow-y:auto;" v-if="$auth('project:highTechScore:view')">
    <a-spin :spinning="spinning" tip="请稍后...">
      <div style="width:100%">
        <div style="width: 1010px;margin:0 auto;">
          <table class="normalTd" border style="border: 0;width:1000px;table-layout:fixed;">
            <tr style="border:0px #dff solid;">
              <td style="border:0;" width="20%"></td>
              <td style="border:0;" width="20%"></td>
              <td style="border:0;" width="60%"></td>
            </tr>
            <tr>
              <td colspan="3" style="background-color:#FAFAFA;font-weight:600;text-align:center;">高新技术创新能力评分评价</td>
            </tr>
            <tr>
              <td colspan="2">企业是否注册成立一年以上</td>
              <td >
                <a-checkbox :checked="Boolean(current.register)" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                <a-checkbox :checked="!Boolean(current.register)" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
              </td>
            </tr>
            <tr>
              <td colspan="2">企业是否获得符合条件的知识产权</td>
              <td>
                <a-checkbox :checked="Boolean(current.patent)" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                <a-checkbox :checked="!Boolean(current.patent)" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
              </td>
            </tr>
            <tr>
              <td colspan="2">核心技术是否属于《技术领域》规定的范围	</td>
              <td>
                <a-checkbox :checked="Boolean(current.patent)" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                <a-checkbox :checked="!Boolean(current.patent)" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
                <div style="width:100%" v-html="tecIndustries"></div>
              </td>
            </tr>
            <tr>
              <td colspan="2">科技人员占比是否符合要求</td>
              <td>
                <a-checkbox :checked="Boolean(current.fitMember)" :disabled="true"></a-checkbox><span class="spanLabel">是</span>
                <a-checkbox :checked="!Boolean(current.fitMember)" :disabled="true"></a-checkbox><span class="spanLabel">否</span>
              </td>
            </tr>
            <tr>
              <td width="20%" rowspan="2">近三年研发费用</td>
              <td width="20%">研发活动核定数(个)</td>
              <td width="60%"><a-input-number
                :precision="0"
                :min="0"
                v-model="current.rdCnt"
                size="small"
                :max="$store.state.maxOrder"
                style="width:200px;"/></td>
            </tr>
            <tr>
              <td>核定总额(万元)</td>
              <td><a-input-number
                v-model="current.rdFunds"
                :precision="2"
                size="small"
                :min="0"
                :max="$store.state.totalMax"
                style="width:200px;"/></td>
            </tr>
            <tr>
              <td rowspan="3">近一年高新技术产品(服务)收入(万元)</td>
              <td>产品(服务)核定数</td>
              <td><a-input-number
                v-model="current.highTechCnt"
                :precision="0"
                :min="0"
                size="small"
                :max="$store.state.maxOrder"
                style="width:200px;"/></td>
            </tr>
            <tr>
              <td>核除产品（服务)编号</td>
              <td><a-input
                size="small"
                v-model="current.highTechCodes" /></td>
            </tr>
            <tr>
              <td>收入核定总额</td>
              <td><a-input-number
                v-model="current.income"
                :precision="2"
                size="small"
                :min="0"
                :max="$store.state.totalMax"
                style="width:200px;"/></td>
            </tr>
            <tr>
              <td colspan="3">
                <div style="display:flex;">
                  <div style="width:70%;text-align:left;"><b>1. 知识产权（≤30分）</b></div>
                  <div style="width:30%;text-align:right;"><b>得分:{{ sumPart1 }}</b></div>
                </div>
              </td>
            </tr>
            <tr>
              <td>技术的先进程度(≤8分)</td>
              <td colspan="2">
                <div style="display:flex;">
                  <a-slider v-model="current.advanced" :min="0" :max="8" style="width:620px;" :marks="advancedOrEffectMarks" />
                  <a-input-number
                    :min="0"
                    :max="8"
                    :precision="0"
                    v-model="current.advanced"
                    size="small"
                    style="width:88px;margin:auto 0;left:34px;"/>
                </div>
              </td>
            </tr>
            <tr>
              <td>对主要产品(服务)在技术上发挥核心支持作用(≤8分)</td>
              <td colspan="2">
                <div style="display:flex;">
                  <a-slider v-model="current.effect" :min="0" :max="8" style="width:620px;" :marks="advancedOrEffectMarks" />
                  <a-input-number
                    :min="0"
                    :max="8"
                    :precision="0"
                    v-model="current.effect"
                    size="small"
                    style="width:88px;margin:auto 0;left:34px;"/>
                </div>
              </td>
            </tr>
            <tr>
              <td>知识产权数量(≤8分)</td>
              <td colspan="2" style="height: 84px;">
                <div style="display:flex;">
                  <a-slider
                    v-model="current.patentAmount"
                    :min="0"
                    :max="8"
                    style="width:620px;"
                    :marks="{0:'0项',2: getLabel('1~2项','(Ⅱ类)') ,4:getLabel('3~4项','(Ⅱ类)'),6: getLabel('5项及以上','(Ⅱ类)'),8: getLabel('1项及以上','(Ⅰ类)')}" />
                  <a-input-number
                    :min="0"
                    :max="8"
                    :precision="0"
                    v-model="current.patentAmount"
                    size="small"
                    style="width:88px;margin:auto 0;left:34px;"/>
                </div>
              </td>
            </tr>
            <tr>
              <td rowspan="2">知识产权获得方式(≤6分)</td>
              <td> <a-radio :checked="current.acquirementMode === 0" @change="radioChange(0)">A.有自主研发</a-radio></td>
              <td rowspan="2">
                <div style="display:flex;">
                  <a-slider
                    :disabled="current.acquirementMode === undefined"
                    v-model="current.acquirement"
                    :min="1"
                    :max="acquirementMax"
                    style="width:421px;"
                    :marks="{1: '1分',3: '3分',6: '6分'}"/>
                  <a-input-number
                    :min="1"
                    :max="acquirementMax"
                    :precision="0"
                    v-model="current.acquirement"
                    size="small"
                    style="width:88px;margin:auto 0;left:34px;"/>
                </div>
              </td>
            </tr>
            <tr>
              <td> <a-radio :checked="current.acquirementMode === 1" @change="radioChange(1)">B.仅有受让/收赠和并购等</a-radio></td>
            </tr>
            <tr>
              <td colspan="2">(加分项，≤2分)企业是否参与编制<br/>
                国家标准、行业标准、检测方法、技术规范的情况</td>
              <td>
                <div style="display:flex;">
                  <a-slider v-model="current.contribution" :min="0" :max="2" style="width:421px;" :marks="{0: '否' ,1:'1分',2: '2分'}"/>
                  <a-input-number
                    :min="0"
                    :max="2"
                    :precision="0"
                    v-model="current.contribution"
                    size="small"
                    style="width:88px;margin:auto 0;left:34px;"/>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="3">
                <div style="display:flex;">
                  <div style="width:70%;text-align:left;"><b>2.科技成果转化能力（≤30分）</b></div>
                  <div style="width:30%;text-align:right;"><b>得分:{{ sumPart2 }}</b></div>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="3"><div> 基础得分</div></td>
            </tr>
            <tr>
              <td colspan="3">
                <div style="display:flex;">
                  <a-slider
                    v-model="current.scienceResult"
                    :min="1"
                    :step="6"
                    :max="25"
                    style="width:792px;left:30px;"
                    :marks="{1:'转化能力弱',7: '转化能力较弱',13: '转化能力一般',19: '转化能力较强',25: '转化能力强'}"/>
                  <a-input-number
                    :min="0"
                    :max="30"
                    :disabled="true"
                    :precision="0"
                    v-model="current.scienceResult"
                    size="small"
                    style="width:88px;margin:auto 0;left:62px;"/>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="3"><div>附加得分</div></td>
            </tr>
            <tr>
              <td colspan="3">
                <div style="display:flex;">
                  <div style="width:792px;left:30px;">
                    <a-checkbox :checked="Boolean(current.testFile)" :disabled="true"></a-checkbox><span class="spanLabel">检测文件</span>
                    <a-checkbox :checked="Boolean(current.advancedFile)" :disabled="true"></a-checkbox><span class="spanLabel">国内先进证明</span>
                    <a-checkbox :checked="Boolean(current.leadFile)" :disabled="true"></a-checkbox><span class="spanLabel">国内领先证明</span>
                    <a-checkbox :checked="Boolean(current.exportFile)" :disabled="true"></a-checkbox><span class="spanLabel">出口证明</span>
                    <a-checkbox :checked="Boolean(current.exportWestFile)" :disabled="true"></a-checkbox><span class="spanLabel">出口欧美发达地区证明</span>
                  </div>
                  <a-input-number
                    :min="0"
                    :max="30"
                    :precision="0"
                    :disabled="true"
                    v-model="current.addScienceResult"
                    size="small"
                    style="width:88px;margin:auto 0;left:72px;"/>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="3">
                <div style="display:flex;">
                  <div style="width:70%;text-align:left;"><b>3.研究开发组织管理水平(≤20分)</b></div>
                  <div style="width:30%;text-align:right;"><b>得分:{{ sumPart3 }}</b></div>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="2">制定了企业研究开发的组织管理制度，建立了研发投入核
                <br/>算体系，编制了研发费用辅助账（≤6分）</td>
              <td>
                <div style="display:flex;">
                  <a-slider v-model="current.generalLedger" :min="0" :max="6" style="width:421px;" :marks="{0: '0分',6: '6分'}"/>
                  <a-input-number
                    :min="0"
                    :max="6"
                    :disabled="true"
                    :precision="0"
                    v-model="current.generalLedger"
                    size="small"
                    style="width:88px;margin:auto 0;left:34px;"/>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="2">设立了内部科学技术研究开发机构并具备相应的科研条件
                <br/>与国内外研究开发机构开展多种形式的产学研合作（≤6分）</td>
              <td>
                <div style="display:flex;">
                  <a-slider v-model="current.cooperation" :min="0" :max="6" style="width:421px;" :marks="{0: '0分',6: '6分'}"/>
                  <a-input-number
                    :min="0"
                    :max="6"
                    :precision="0"
                    v-model="current.cooperation"
                    size="small"
                    :disabled="true"
                    style="width:88px;margin:auto 0;left:34px;"/>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="2">建立了科技成果转化的组织实施与激励奖励制度
                <br/>建立开放式的创新创业平台（≤4分）</td>
              <td>
                <div style="display:flex;">
                  <a-slider v-model="current.excitation" :min="0" :max="4" style="width:421px;" :marks="{0: '0分',4: '4分'}"/>
                  <a-input-number
                    :min="0"
                    :max="4"
                    :disabled="true"
                    :precision="0"
                    v-model="current.excitation"
                    size="small"
                    style="width:88px;margin:auto 0;left:34px;"/>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="2">建立了科技人员的培养进修、职工技能培训、优秀人才
                <br/>引进，以及人才绩效评价奖励制度（≤4分）</td>
              <td>
                <div style="display:flex;">
                  <a-slider v-model="current.foster" :min="0" :max="4" style="width:421px;" :marks="{0: '0分',4: '4分'}"/>
                  <a-input-number
                    :min="0"
                    :max="4"
                    :disabled="true"
                    :precision="0"
                    v-model="current.foster"
                    size="small"
                    style="width:88px;margin:auto 0;left:34px;"/>
                </div>
              </td>
            </tr>
            <tr>
              <td colspan="3">
                <div style="display:flex;">
                  <div style="width:100%;text-align:right;"><b>合计得分:{{ totalAmount }}</b></div>
                </div>
              </td>
            </tr>
          </table>
          <div style="text-align: center;margin-top:10px;" v-if="$auth('project:highTechScore:save')">
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
      tecIndustryMap: {},
      advancedOrEffectMarks: { 0: '无', 2: '较低', 4: '一般', 6: '较高', 8: '高' },
      spinning: false,
      current: { acquirementMode: 1 },
      acquirementMax: 3
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
    totalAmount () {
      return this.sumPart1 + this.sumPart3 + (this.current.scienceResult || 0)
    }
  },
  methods: {
    getLabel (text1, text2) {
      return { label: <div>{text1}<br/>{text2}</div> }
    },
    radioChange (v) {
      this.current.acquirementMode = v
      this.acquirementMax = v === 0 ? 6 : 3
    },
    search () {
      this.loadCurrent()
    },
    loadCurrent () {
      this.spinning = true
      this.$http.get('/highScore/getTechScore', { params: { year: this.currentYear } }).then(res => {
        this.current = {}
        this.$nextTick(() => {
          if (res.success && res.data) {
            for (const key in res.data) {
              const temp = res.data[key]
              this.$set(this.current, key, temp || temp === 0 || temp === false ? temp : 0)
            }
          } else {
            this.current = { acquirementMode: 1 }
            this.$message.error(res.errorMessage || '获取得分情况失败')
          }
          this.acquirementMax = this.current.acquirementMode === 0 ? 6 : 3
        })
      }).finally(() => {
        this.spinning = false
      })
    },
    saveCurrent () {
      this.spinning = true
      this.current.year = this.currentYear
      this.current.NAVTest = this.currentYear
      this.$http.post('/highScore/saveTechScore', this.current).then(res => {
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
