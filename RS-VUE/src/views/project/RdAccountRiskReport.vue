<!--
 * @Author: lzh
 * @Date: 2022-01-13 14:16:59
 * @LastEditors: lzh
 * @LastEditTime: 2022-01-18 11:32:50
 * @Description: 核算风险报告
 * @FilePath: \RS-VUE\src\views\project\RdAccountRiskReport.vue
-->
<template>
  <a-card :bordered="false" class="content-page" :bodyStyle="{height: '100%'}" v-if="control.view">
    <a-spin id="spin" :spinning="spinning" tip="请稍后...">
      <div class="container">
        <div class="title-wrap" style="position: relative;">
          <div style="text-align: center; font-size: 18px">核算风险报告</div>
          <div class="btn-action" style="position: absolute; right: 0; top: 0">
            <template v-if="!isEdit">
              <a-button type="primary" size="small" @click="changeEditStatus(true)" v-if="control.edit">编辑</a-button>
              <a-button type="primary" size="small" style="margin-left: 8px" @click="handleExport" v-if="control.export">导出</a-button>
            </template>
            <template v-else>
              <a-button type="default" size="small" @click="changeEditStatus(false)">返回</a-button>
              <a-button type="primary" size="small" style="margin-left: 8px" @click="handleSave">保存</a-button>
            </template>
          </div>
        </div>
        <div class="content-wrap" ref="contentWrap">
          <div class="preview-wrap" v-if="!isEdit && control.preview" v-html="docInfo.htmlData"></div>
          <div class="edit-wrap" v-if="isEdit">
            <h2 class="title">{{ companyName }}公司{{ currentYear }}年度研究开发费用所得税前加计扣除风控报告</h2>
            <a-form :form="form" ref="form">
              <section>
                <div>
                  <span class="call">{{ companyName }}公司财务总监/经理：</span>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      关于贵公司XXX年实施研发费用税前加计扣除项目，对实施研发加计扣除项目。<br/>贵公司成立于XXXXX，经营范围为XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX,公司总人数XX人，无需按营业执照上面做法。<br/>XXXX年申请加计扣除项目整体符合国家鼓励企业自主创新以及相关法规要求享受企业所得税优惠。
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="" :rows="4" v-model="form.value_0"></wang-editor>
                </div>
              </section>
              <section>
                <div class="first-title">一、申请加计扣除项目符合国家鼓励政策</div>
                <div>
                  <span class="second-title">1、经营范围属于国家高新技术企业鼓励领域</span>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      公司经营范围为精品钢生产制造：属于国家高新领域第四条新材料/（一）金属材料/1. 精品钢材制备技术，提高资源能源利用效率、促进减排的可循环钢铁流程技术；生态型非高炉炼铁技术，二次含铁资源和贫、难选铁矿的高效提取冶金技术，氧化物冶金技术，第三代TMCP技术，高合金钢铸轧一体化技术，薄带连铸产业化通用成套技术；高温合金制备技术；高附加值、特殊性能钢材、合金及制品的先进制备加工技术等。<br/><b style="color: red;">引导税务思路，我们技术属于国家鼓励行业。</b>
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="经营范围属于国家高新技术企业鼓励领域" :rows="4" v-model="form.value_1_1"></wang-editor>
                </div>
                <div>
                  <span class="second-title">2、研发项目符合技术创新要求</span>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      根据完善研究开发费用税前加计扣除政策的通知（财税[2015]119号）规定，企业为了获得科学与技术新知识，创造性运用科学技术新知识，或实质性改进技术、产品（服务）、工艺而持续进行的具有明确目标的系统性活动为研发活动，而我公司2018年6个研发项目均是利用自身掌握的核心技术进行高效开采、环保开采、高效洗煤、高技术选煤等资源环境领域技术创新，解决了“矿井水复用技术、瓦斯抽采支系统和瓦斯钻孔高效管理技术、超高水充填残采区复采关键技术、近距离煤层采空区下沿空留巷技术、陷落柱位置分析定位工艺应用、采空区瓦斯抽放技术创新升级,本6项项目全部为技术创新研究，符合国家《完善研究开发费用税前加计扣除政策的通知（财税[2015]119号）》规定研发活动要求。<br/><b style="color: red;">判断项目是否属于负面清单核查：该公司无行业负面清单和活动负面清单</b>
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="研发项目符合技术创新要求" :rows="4" v-model="form.value_1_2"></wang-editor>
                </div>
                <div>
                  <span class="second-title">3、公司具有很高的科研管理能力</span>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      建有自己的研发技术中心，具有完整的研发组织架构，研发人员共315人（研究人员、技术人员、辅助人员），技术负责人为博士、高级工程师，其他均具有煤矿开采专业及丰富经验的人员，与同行业，大学进行煤矿开采技术交流，研发技术中心承担着研发与生产工作。<br/>建设各类研发制度，如研发项目管理制度、研发经费核算制度、研发辅助帐核算制度、研发成果转化制度、技术人员培制度、技术秘密制度、知识产权管理制度等，形成具有较高的科研管理水平体系，使研发得到保障。如附件《研发相关制度》。<br/>研发能力，可以承载研发费用的能力
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="公司具有很高的科研管理能力" :rows="4" v-model="form.value_1_3"></wang-editor>
                </div>
                <div>
                  <span class="second-title">4、研发项目存在及管理</span>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      公司严格按照公司的研发项目相关管理制度，对研发的过程作出真实记录，并做到科研项目管理，提升科研项目的管理能力,如《研发项目记录清单》真实记载着我公司研发项目管理的真实性（每个项目附相关研发记录情况资料）。<br/>研发具有流程化的管理。
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="研发项目存在及管理" :rows="4" v-model="form.value_1_4"></wang-editor>
                </div>
                <div>
                  <span class="second-title">5、研发费用总结构</span>
                  <wang-editor placeholder="研发费用总结构" :rows="4" v-model="form.value_1_5"></wang-editor>
                </div>
                <div>
                  <span class="second-title">6、研发费用核算交付依据</span>
                  <wang-editor placeholder="研发费用核算交付依据" :rows="4" v-model="form.value_1_6"></wang-editor>
                </div>
              </section>
              <section>
                <div class="first-title">二、研发费用归集情况符合税务依据</div>
                <div>
                  <span class="second-title">1、研发活动与生产活动有明确区分</span>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      根据国家税务总局公告2015年第97号国家税务总局关于企业研究开发费用税前加计扣除政策有关问题的公告中第二条“研发费用归集”中第（二）项中“多用途对象费用的归集”：“企业从事研发活动的人员和用于研发活动的仪器、设备、无形资产，同时从事或用于非研发活动的，应对其人员活动及仪器设备、无形资产使用情况做必要记录，并将其实际发生的相关费用按实际工时占比等合理方法在研发费用和生产经营费用间分配，未分配的不得加计扣除”。<br/>XXXX年度我公司按项目研发过程进行管理，体现项目的立项、进度管理、试验试制的记录依据等研发事实为依据，每月做好生产与研发人员工时记录；使用仪器与设备的工作记录；研制试验现场行了工时登记，研发用备品备件进行使用登记等方式进行区分研发与生产。<br/>因此符合“国家税务总局公告2015年第97号 国家税务总局关于企业研究开发费用税前加计扣除政策有关问题的公告要求”。
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="研发活动与生产活动有明确区分" :rows="4" v-model="form.value_2_1"></wang-editor>
                </div>
                <div>
                  <span class="second-title">2、研发与生产费用分配标准明确</span>
                  <div>
                    <span class="third-title">1）研发工资核算</span>
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        《国家税务总局关于研发费用税前加计扣除归集范围有关问题的公告国家税务总局公告》（2017年第40号）规定：“从事非研发活动的，企业应对其人员活动情况做必要记录，并将其实际发生的相关费用按实际工时占比等合理方法在研发费用和生产经营费用间分配”。<br/>2018年，我公司对从事研发与生产活动人员按生产与研发工作天数记录表进行研发工资与生产工资分配（参工资分配表、2018年研发技术人员1-12月份工作打卡记录表）。
                      </template>
                    </a-tooltip>
                    <wang-editor placeholder="研发工资核算" :rows="4" v-model="form.value_2_2_1"></wang-editor>
                  </div>
                  <div>
                    <span class="third-title">2）研发材料费核算</span>
                    <wang-editor placeholder="研发材料费核算" :rows="4" v-model="form.value_2_2_2"></wang-editor>
                  </div>
                  <div>
                    <span class="third-title">3）研发仪器设备折旧费核算</span>
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        根据《国家税务总局关于研发费用税前加计扣除归集范围有关问题的公告国家税务总局公告》（2017年第40号）规定：“用于研发活动的仪器、设备，同时用于非研发活动的，企业应对其仪器设备使用情况做必要记录，并将其实际发生的折旧费按实际工时占比等合理方法在研发费用和生产经营费用间分配” ，2018年，我公司对从事研发与生产活动仪器设备按生产与研发工作时数记录表进行研发折旧与生产折旧分配（参2018年1-12月研发仪器设备折旧表、2018年1-12月研发设备使用记录明细表）。
                      </template>
                    </a-tooltip>
                    <wang-editor placeholder="研发仪器设备折旧费核算" :rows="4" v-model="form.value_2_2_3"></wang-editor>
                  </div>
                  <div>
                    <span class="third-title">4）研发燃动力费</span>
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        按照我公司煤矿开采业的研发项目，均为在开采技术创新，需要开采过程验证试验，因为2018年的研发项目全部工艺技术开发，因此我们的研发耗用动力与生产是混用，但依据每月对研发过程进行有效管理，严格区分了研发试验试制的时间，因此研发与生产动力费用按工时进行分配（请参试制排程记录表、2018年研发电费工时分摊表）。
                      </template>
                    </a-tooltip>
                    <wang-editor placeholder="研发燃动力费" :rows="4" v-model="form.value_2_2_4"></wang-editor>
                  </div>
                  <div>
                    <span class="third-title">5）研发用于中间试验和产品试制的模具、工艺装备开发及制造费归集</span>
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        XXXX 年研发项目全部工艺及装备技术创新项目，因为研发过程中领用的中间试验试制的模工具、工艺装备开发领用的各类配套件，是根据研发过程中的领用工具及装备开发配套件记入研发费（参2018年1-12月研发中间试验试制及工艺装备用料登记表）。
                      </template>
                    </a-tooltip>
                    <wang-editor placeholder="研发用于中间试验和产品试制的模具、工艺装备开发及制造费归集" :rows="4" v-model="form.value_2_2_5"></wang-editor>
                  </div>
                </div>
              </section>
              <section>
                <div class="first-title">三、研发支出设置辅助帐核算及存底资料情况
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      <b style="color: red;">帐务核算情况，特别是专帐、辅助帐、备查资料情况</b>
                    </template>
                  </a-tooltip>
                </div>
                <div>
                  <span class="second-title">1、研发的的帐务情况</span>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      依据研发事实发生过程，将生产与研发进行明确费用区分，同时，依据国家税务总局公告2015年第97号 国家税务总局关于企业研究开发费用税前加计扣除政策有关问题的公告中第五条：核算要求中规定：<br/>企业应按照国家财务会计制度要求，结合公司财务部对研发费用核算内部制度，对研发支出进行会计处理。研发项目立项时应设置研发支出辅助账，由企业留存备查；年末汇总分析填报研发支出辅助账汇总表，并在报送《年度财务会计报告》的同时随附注一并报送主管税务机关。研发支出辅助账、研发支出辅助账汇总表可参照本公告所附样式（见附件）编制。<br/>我公司2018年申请的加计扣除项目，严格进行了生产与研发区分，进行会计处理并设置了辅助帐核算，做好了备存资料（如下第4条），年末汇总填报了研发支出辅助账汇总表
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="研发的的帐务情况" :rows="4" v-model="form.value_3_1"></wang-editor>
                </div>
                <div>
                  <span class="second-title">2、按国家税务要求备底资料完整</span>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      我公司备底资料完整，依据关于发布修订后的《企业所得税优惠政策事项办理办法》的公告国家税务总局公告2018年第23号规定：企业享受优惠事项采取“自行判别、申报享受、相关资料留存备查”的办理方式。企业应当根据经营情况以及相关税收规定自行判断是否符合优惠事项规定的条件，符合条件的可以按照《目录》列示的时间自行计算减免税额，并通过填报企业所得税纳税申报表享受税收优惠。同时，按照本办法的规定归集和留存相关资料备查。
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="按国家税务要求备底资料完整" :rows="4" v-model="form.value_3_2"></wang-editor>
                </div>
                <div>
                  <span class="second-title">3、资料留存备查</span>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      国家税务总局公告2015年第97号 国家税务总局关于企业研究开发费用税前加计扣除政策有关问题的公告中提出备底资料要求：<br/>
                      1）.自主、委托、合作研究开发项目计划书和企业有权部门关于自主、委托、合作研究开发项目立项的决议文件；<br/>
                      2）.自主、委托、合作研究开发专门机构或项目组的编制情况和研发人员名单；<br/>
                      3）.从事研发活动的人员和用于研发活动的仪器、设备、无形资产的费用分配说明（包括工作使用情况记录）；<br/>
                      4）.“研发支出”辅助明细帐及总表<br/>
                      5）其他资料
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="资料留存备查" :rows="4" v-model="form.value_3_3"></wang-editor>
                </div>
              </section>
              <section>
                <div class="first-title">四、研发费用核算总述</div>
                <wang-editor placeholder="研发费用核算总述" :rows="4" v-model="form.value_4"></wang-editor>
              </section>
            </a-form>
          </div>
        </div>
      </div>
    </a-spin>
  </a-card>
</template>

<script>
import { mapGetters } from 'vuex'
import yearMixin from '@/utils/yearMixin'
import WangEditor from '@/components/Editor/WangEditor.vue'

export default {
  components: {
    WangEditor
  },
  mixins: [yearMixin],
  data () {
    return {
      spinning: false,
      isEdit: true,
      docInfo: {
        htmlData: undefined
      },
      form: {},
      control: {
        view: this.$auth('project:rdAccountRiskReport:view'),
        edit: this.$auth('project:rdAccountRiskReport:edit'),
        preview: this.$auth('project:rdAccountRiskReport:preview'),
        export: this.$auth('project:rdAccountRiskReport:export')
      }
    }
  },
  computed: {
    companyName () {
      return this.userInfo().companyName
    }
  },
  created () {
    this.search()
  },
  methods: {
    ...mapGetters(['userInfo']),
    search () {
      this.isEdit = false
      this.loadHtmlData()
      this.$nextTick(() => {
        this.$refs['contentWrap'].scrollTop = 0
      })
    },
    changeEditStatus (isEdit) {
      this.isEdit = isEdit
      this.form = {}
      this.$refs['contentWrap'].scrollTop = 0
      if (this.isEdit) {
        this.loadData()
      } else {
        this.loadHtmlData()
      }
    },
    loadData () {
      this.spinning = true
      this.$http
        .get('/project/getRdAccountRiskData', { params: { year: this.currentYear } })
        .then((res) => {
          if (res.success) {
            this.form = res.data
          } else {
            this.$message.error(res.errorMessage || '加载失败')
          }
        })
        .finally(() => {
          this.spinning = false
        })
    },
    loadHtmlData () {
      const params = {
        year: this.currentYear
      }
      this.spinning = true
      this.$http.get('/project/previewRdAccountRiskReport', { params: params }).then((res) => {
        if (res.data && res.success) {
          this.docInfo.htmlData = res.data
        } else {
          this.$message.error(res.errorMessage || '加载失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    handleSave () {
      // 数据转化为JSON
      const data = JSON.stringify(this.form)
      const params = {
        type: 0,
        companyId: this.userInfo().companyId,
        year: this.currentYear,
        data: data
      }
      this.spinning = true
      this.$http.post('/project/saveRdAccountRiskData', params).then(res => {
        if (res.success && res.data) {
          this.$message.success('保存成功')
        } else {
          this.$message.error(res.errorMessage || '操作失败')
        }
      }).finally(() => { this.spinning = false })
    },
    handleExport () {
      this.spinning = true
      this.$exportData('/project/exportRdAccountRiskReport', { year: this.currentYear }).then(() => {
        this.spinning = false
      })
    }
  }
}
</script>

<style lang="less" scoped>
.content-page {
  height: 100%;
}
.container {
  height: 100%;
  .title-wrap {
    position: relative;
    padding-bottom: 10px;
    border-bottom: 1px solid #e8e8e8;
  }
  .content-wrap {
    height: calc(100% - 38px);
    overflow: auto;
    .title {
      text-align: center;
      font-weight: 700;
    }
    .call, .first-title {
      font-size: 18px;
      font-weight: 700;
      margin-top: 16px;
    }
    .second-title {
      font-size: 16px;
      font-weight: 700;
      margin-top: 12px;
      display: inline-block;
    }
    .third-title {
      font-size: 15px;
      font-weight: 700;
      margin-top: 8px;
      display: inline-block;
    }
  }
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
