<!--
 * @Author: lzh
 * @Date: 2022-01-13 14:16:59
 * @LastEditors: lzh
 * @LastEditTime: 2022-01-24 09:22:45
 * @Description: 核算标准方法
 * @FilePath: \RS-VUE\src\views\project\RdAccountMethod.vue
-->
<template>
  <a-card :bordered="false" class="content-page" :bodyStyle="{height: '100%'}" v-if="control.view">
    <a-spin id="spin" :spinning="spinning" tip="请稍后...">
      <div class="container">
        <div class="title-wrap" style="position: relative;">
          <div style="text-align: center; font-size: 18px">核算标准方法</div>
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
            <h2 class="title">{{ companyName }}有限公司研发费用核算标准化方案</h2>
            <a-form :form="form" ref="form">
              <section>
                <div>
                  <div class="first-title">
                    <span>一、前序</span>
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        对标国内大型与民营及国企钢铁行业，结合我公司对贵公司的实地调研情况，特制定贵公司适用于钢铁行业的研发费用归集标准化方案；同时，符合年度审计遵循会计准则、科技创新等法律法规，结合钢铁制造企业的研发特性分为新产品研发、新工艺技术及装备技术的研发活动，研发费用核算方法采用优化改进的方式。
                      </template>
                    </a-tooltip>
                  </div>
                  <wang-editor placeholder="前序" :rows="4" v-model="form.value_1"></wang-editor>
                </div>
              </section>
              <section>
                <div>
                  <div class="first-title">二、研发项目核算管理顶层设计方法
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        建立研发技术中心，建制度，定研发人员，建立研发生态环境，建立起研发生态文化体系，技术部门在XX月XX日完成并与企业技术中心总监确定，形成研发技术中心组织架构信息转转给财务部门。
                      </template>
                    </a-tooltip>
                  </div>
                  <wang-editor placeholder="研发项目核算管理顶层设计方法" :rows="4" v-model="form.value_2"></wang-editor>
                </div>
              </section>
              <section>
                <div class="first-title">三、研发项目立项与核算层级管理方法</div>
                <div>
                  <div class="second-title">1、项目立项与费用信息</div>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      各车间研发组已将项目全部梳理，将在XXX月XXX号前全部确定完成，交给技术部门，研发项目按工艺段、车间进行立项，财务规划立项数据及研发费总额信息转流给技术部门；
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="项目立项与费用信息" :rows="4" v-model="form.value_3_1"></wang-editor>
                </div>
                <div>
                  <div class="second-title">2、多层级部门研发立项确定</div>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      技术部门按项目所在工艺段/车间（球团、烧结、炼铁、炼钢、轧钢，以及白灰、发电、动力等）工艺改善、质量改善、装备能力、材料改良、新产品等为研究开发的事实；
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="多层级部门研发立项确定" :rows="4" v-model="form.value_3_2"></wang-editor>
                </div>
                <div>
                  <div class="second-title">3、月度研发活动证据收集管理</div>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      每月完成研发过程证据收集，YS整理反馈过程记录，企业定稿存档；
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="月度研发活动证据收集管理" :rows="4" v-model="form.value_3_3"></wang-editor>
                </div>
                <div>
                  <div class="second-title">4、研发费用与生产报表数据</div>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      研发活动分为几大阶段：按YS制作研发总经费，各月研发试制量，企业提供生产报表；
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="研发费用与生产报表数据" :rows="4" v-model="form.value_3_4"></wang-editor>
                </div>
              </section>
              <section>
                <div class="first-title">四、研发费用计算标准方法</div>
                <div>
                  <div class="second-title">1、研发费用核算总思路</div>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      采用流程型的技术研发路线,费用核算采用研发技术所处阶段核算（工艺段为本阶段短流程成本法，新产品为全流程段的原本还原法），即随技术工艺阶进则材料成本与研发项目技术匹配法；
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="研发费用核算总思路" :rows="4" v-model="form.value_4_1"></wang-editor>
                </div>
                <div>
                  <div class="second-title">2、研发耗用原物料核算方法</div>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      研发用原料、辅料：研发试制用料登记表记载的日用料与试制量/日总产量，分配原料、物料[新产品研发类项目采用直接领用材料、工艺装备技术类研发项目比正常生产消耗招损耗5-10%（采用行业投入产出比差异），全消耗性]计入研发投入；通过“研发支出科目”记载研发投入，形成产品的材料转入成本，研发活动损耗的材料费记入“研发费用科目”中的材料费，并进行加计扣除。
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="研发耗用原物料核算方法" :rows="4" v-model="form.value_4_2"></wang-editor>
                </div>
                <div>
                  <div class="second-title">3、研发活动耗用的燃料、动力核算方法</div>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      研发试制用料登记表记载的研制产出量/日总产量，比例计算研发耗用焦炭、煤、电力、氧、氮气等燃动力费（按加计扣除政策，全部计入研发费用）；
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="研发活动耗用的燃料、动力核算方法" :rows="4" v-model="form.value_4_3"></wang-editor>
                </div>
                <div>
                  <div class="second-title">4、人员工资及五险一金年金的核算方法</div>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      项目负责人制定项目组人员，并做好项目组人员表，专职研发人员、技术人员、辅助人员，做研发工时预算与实际参与开发工时，分全时与非全时研发，YS提供工时分配依据；
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="人员工资及五险一金年金的核算方法" :rows="4" v-model="form.value_4_4"></wang-editor>
                </div>
                <div>
                  <div class="second-title">5、研发活动用折旧费核算方法</div>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      （1） 研发活动用仪器与设备折旧费核算方法<br/>项目负责人制定项目所用仪器、设备，并做好项目组使用仪器与设备表，YS提供分配依据（与试制量时间一致）；<br/>（2）研发活动用房屋折旧费或摊销费核算方法
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="研发活动用折旧费核算方法" :rows="4" v-model="form.value_4_5"></wang-editor>
                </div>
                <div>
                  <div class="second-title">6、研发活动工艺装备及中间试制模具备件核算方法</div>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      与研试时间配比所有的各类备品备件视为研发耗用，改善工艺装备能力涉及的工冶具装备修善能力；
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="研发活动工艺装备及中间试制模具备件核算方法" :rows="4" v-model="form.value_4_6"></wang-editor>
                </div>
                <div>
                  <div class="second-title">7、研发用专利与非专利的摊销核算方法</div>
                  <a-tooltip>
                    <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                    <template slot="title">
                      项目负责人制定项目专利与非专利技术无形资产摊销，并做好项目组使用专利与非专利技术记录，YS提供分配依据（与试制量时间一致）；
                    </template>
                  </a-tooltip>
                  <wang-editor placeholder="研发用专利与非专利的摊销核算方法" :rows="4" v-model="form.value_4_7"></wang-editor>
                </div>
                <div>
                  <div class="second-title">8、其他费用，研发技术测试、认证、交流、专利、差旅等费用。</div>
                </div>
              </section>
              <section>
                <div>
                  <div class="first-title">五、研发管理资料对接时间表
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        （1）YS每月XXX日提供次月研发工时计划、研发试制计划总表；<br/>
                        （2）企业XXXX日前提供当月研发配比的生产日报表；<br/>
                        （3）财务提供每日单笔领用单、每日研发领用与超消耗表（由技术按预算区分研发、生产量）、每月汇总（区研发、生产量），并作入生产报表，体现研发轨迹，每月XXX日前研发用料核算完成；<br/>
                        （2）研制产出量/日总产量：待各车间技术立项后确定研发周期，YS财务与技术制作出各月的研发量、结合生产量，采用占比方法分配各月研发,XXXX号反馈给企业入帐；<br/>
                        （3）人员工资表提供：HR人事XXX号提供对应研发人员名单折工资明细；YS进行研发工时分配研发工资，并返馈给企业存档，企业财务入研发工资总额，每月XXX日前完成月度研发工资核算,XXXX号反馈给企业入帐；<br/>
                        （4）折旧表提供：企业于XXXX号前提供月度折旧明细，YS于XXX 号分配研发折旧费明细及依据，提供给企业入帐；<br/>
                        （5）能源表提供：企业根据YS各月的研发制试量/总产量计算能源成本；<br/>
                        （6）材料单价提供：企业根据YS各月的研发制试量/总产量计算超损耗的研发材料成本；<br/>
                        （7）备品备件：与研试时间配比所有的各类备品备件视为研发耗用（按预算数、车间技术部归集单据入系统为研发耗用），企业当月内弄完；<br/>
                        （8）YS于XXX 月XXX日前将完成XXX年XXX月的研发费用核算，企业入帐号前完成。
                      </template>
                    </a-tooltip>
                  </div>
                  <wang-editor placeholder="研发管理资料对接时间表" :rows="4" v-model="form.value_5"></wang-editor>
                </div>
              </section>
              <section>
                <div>
                  <div class="first-title">六、项目主要对接人员及资料管理安排
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        （1）人事部：提供研发人员的工资明细（含公司承担五险一金明细），YS按照研发工时分配研发人工费，核算明细返回人事存档。<br/>
                        （2）财务部：设备折旧，备件消耗由大东海提供给YS进行费用分配；YS提供预测研发试制量、大东海按预测研发试制量在某月某日相近量注明研发量，YS按与生产日报表上的体现的研发试制量数进行费用分配；研发资料返回财务部存档。<br/>
                        （3）烧结、炼铁、炼钢、轧钢三部门技术人：研发立项的梳理，研发轨迹体现。<br/>
                      </template>
                    </a-tooltip>
                  </div>
                  <wang-editor placeholder="项目主要对接人员及资料管理安排" :rows="4" v-model="form.value_6"></wang-editor>
                </div>
              </section>
              <section>
                <div class="first-title">七、与企业财务确定的研发帐务处理方式</div>
                <div>
                  <div class="second-title">1、帐务处理方式</div>
                  <div>
                    <div class="third-title">（1）财务使用的财务系统</div>
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        请描述：金蝶、用友、SAP等
                      </template>
                    </a-tooltip>
                    <wang-editor placeholder="财务使用的财务系统" :rows="4" v-model="form.value_7_1_1"></wang-editor>
                  </div>
                  <div>
                    <div class="third-title">（2）专帐形式</div>
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        根据研发费用核算，采用按月核算，按月进行会计处理，建立研发支出科目、研发支出月末费用转研发费用，按月完成，建立专帐；
                      </template>
                    </a-tooltip>
                    <wang-editor placeholder="专帐形式" :rows="4" v-model="form.value_7_1_2"></wang-editor>
                  </div>
                  <div>
                    <div class="third-title">（3）辅助帐</div>
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        采用辅助帐核算，原因：XXXXXXXXXXXXXXXXXXXXXX.
                      </template>
                    </a-tooltip>
                    <wang-editor placeholder="辅助帐" :rows="4" v-model="form.value_7_1_3"></wang-editor>
                  </div>
                </div>
                <div>
                  <div class="second-title">2、研发投入的会计帐务处理方法
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        以上按月核算按月处理，建议采用方案一帐务处理方式，从源头梳理研发费，同进库存商品与利润不会有影响，核算更健全；方案二核算不完全健全缺乏全流程的思路。
                      </template>
                    </a-tooltip>
                  </div>
                  <div>
                    <a-radio-group style="display: block;" v-model="accountType" :default-value="1">
                      <div>
                        <div class="third-title"><a-radio :value="0">1）选择方法一：成本重分类核算研发费</a-radio></div>
                        <a-tooltip>
                          <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                          <template slot="title">
                            1）归集<br/>借：研发支出--材料100/工资/折旧/动力/试制<br/>贷：原材料/应付工资/折旧/动力/备品备件<br/>（2）研发形成产品转成本（符合会计准则）及研发超损耗材料处理（税法加计扣除）<br/>借：生产成本--材料 95<br/>生产成本--材料 5（做报废料处理，若有）<br/>借：研发支出--材料 90<br/>（3）研发支出费用化<br/>借：管理费用--研发费用<br/>贷：研发支出---材料10/工资/折旧/动力/试制
                          </template>
                        </a-tooltip>
                        <wang-editor key="accountType-0" placeholder="成本重分类核算研发费" :rows="4" v-model="form.value_7_2_1"></wang-editor>
                      </div>
                      <div>
                        <div class="third-title"><a-radio :value="1">2）选择方法二：冲减主营业务成本列支研发费</a-radio></div>
                        <a-tooltip>
                          <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                          <template slot="title">
                            （1）归集 STEP1<br/>借：研发支出--材料100/工资/折旧/动力/试制<br/>贷：原材料100/应付工资/折旧/动力/备品备件<br/>（2）重塑研发核算STEP2<br/>借：生产成本--原材料100/工资/折旧/动力/备品备件<br/>贷：原材料100/应付工资/折旧/动力/备品备件<br/>（3）不影响资产负债STEP3<br/>借：库存商品<br/>贷：生产成本--原材料100/工资/折旧/动力/备品备件<br/>（4）不影响利润STEP4<br/>借：主营业务成本<br/>贷：库存商品<br/>（5）符合会计准则与税法及高新技术企业STEP4<br/>研发形成产品转成本（符合会计准则）及研发超损耗材料处理（税法加计扣除）<br/>借：生产成本--材料90<br/>贷：研发支出--材料90<br/>（5）研发支出费用化（研发费用占比达到3%）STEP6<br/>借：管理费用--研发费用<br/>贷：研发支出--材料10/工资/折旧/动力/试制
                          </template>
                        </a-tooltip>
                        <wang-editor key="accountType-1" placeholder="冲减主营业务成本列支研发费" :rows="4" v-model="form.value_7_2_2"></wang-editor>
                      </div>
                    </a-radio-group>
                  </div>
                </div>
              </section>
              <section>
                <div>
                  <div class="first-title">八、明年核算计划
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        今年采用事后核算，明年（2023年）采用按月核算，更规范的方法，企业愿意配合。
                      </template>
                    </a-tooltip>
                  </div>
                  <wang-editor placeholder="明年核算计划" :rows="4" v-model="form.value_8"></wang-editor>
                </div>
              </section>
              <section>
                <div>
                  <div class="first-title">九、核算方法总结
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        优赛集团采用研发创新标准化服务模式，并与企业XXX负责人共同确定：从2020年规范研发活动，规范研发核算，实现研发活动与研发费用互联互通，全程0风险。
                      </template>
                    </a-tooltip>
                  </div>
                  <wang-editor placeholder="核算方法总结" :rows="4" v-model="form.value_9"></wang-editor>
                </div>
              </section>
              <section>
                <div>
                  <div class="first-title">十、国家高新技术企业规划情况
                    <a-tooltip>
                      <a-icon type="info-circle" style="color: rgba(0,0,0,.45)" />
                      <template slot="title">
                        2022年开始向国家高新技术企业进行规划，争取2023年升级为国家高新技术企业，提升行业地位与技术水平，企业主要人员对此共同非常认可（或有什么修改意见）。
                      </template>
                    </a-tooltip>
                  </div>
                  <wang-editor placeholder="国家高新技术企业规划情况" :rows="4" v-model="form.value_10"></wang-editor>
                </div>
              </section>
              <section>
                <div style="text-align: right; margin-top: 20px;font-size: 15px; font-weight: 700;">
                  创新体系标准化管理中心<br/>
                  财务部<br/>
                  <a-date-picker format="YYYY年MM月DD日" v-model="form.date"/>
                </div>
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
import moment from 'moment'

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
      control: {
        view: this.$auth('project:rdAccountMethod:view'),
        edit: this.$auth('project:rdAccountMethod:edit'),
        preview: this.$auth('project:rdAccountMethod:preview'),
        export: this.$auth('project:rdAccountMethod:export')
      },
      form: {},
      accountType: 0,
      accountTypeDefaultText1: '<p>（1）归集</p><p>借：研发支出--材料100/工资/折旧/动力/试制</p><p>贷：原材料/应付工资/折旧/动力/备品备件</p><p>（2）研发形成产品转成本（符合会计准则）及研发超损耗材料处理（税法加计扣除）</p><p>借：生产成本--材料 95</p><p>生产成本--材料 5（做报废料处理，若有）</p><p>借：研发支出--材料 90</p><p>（3）研发支出费用化</p><p>借：管理费用--研发费用</p><p>贷：研发支出---材料10/工资/折旧/动力/试制</p>',
      accountTypeDefaultText2: '<p>（1）归集 STEP1</p><p>借：研发支出--材料100/工资/折旧/动力/试制</p><p>贷：原材料100/应付工资/折旧/动力/备品备件</p><p>（2）重塑研发核算STEP2</p><p>借：生产成本--原材料100/工资/折旧/动力/备品备件</p><p>贷：原材料100/应付工资/折旧/动力/备品备件</p><p>（3）不影响资产负债STEP3</p><p>借：库存商品</p><p>贷：生产成本--原材料100/工资/折旧/动力/备品备件</p><p>（4）不影响利润STEP4</p><p>借：主营业务成本</p><p>贷：库存商品</p><p>（5）符合会计准则与税法及高新技术企业STEP4</p><p>研发形成产品转成本（符合会计准则）及研发超损耗材料处理（税法加计扣除）</p><p>借：生产成本--材料90</p><p>贷：研发支出--材料90</p><p>（5）研发支出费用化（研发费用占比达到3%）STEP6</p><p>借：管理费用--研发费用</p><p>贷：研发支出--材料10/工资/折旧/动力/试制</p>'
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
        .get('/project/getRdAccountData', { params: { year: this.currentYear } })
        .then((res) => {
          if (res.success) {
            if (res.data.date) {
              res.data.date = moment(res.data.date, 'YYYY-MM-DD')
            }
            this.form = res.data
            if (Object.keys(this.form).length <= 0) {
              this.form.value_7_2_1 = this.accountTypeDefaultText1
              this.form.value_7_2_2 = this.accountTypeDefaultText2
            }
            this.accountType = this.form.accountType || 0
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
      this.$http.get('/project/previewRdAccountMethod', { params: params }).then((res) => {
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
      const data = { ...this.form, accountType: this.accountType }
      if (this.form.date) {
        data.date = moment(this.form.date).format('YYYY年MM月DD日')
      }
      const params = {
        type: 1,
        companyId: this.userInfo().companyId,
        year: this.currentYear,
        data: JSON.stringify(data),
        accountType: this.accountType
      }
      this.spinning = true
      this.$http.post('/project/saveRdAccountData', params).then(res => {
        if (res.success && res.data) {
          this.$message.success('保存成功')
        } else {
          this.$message.error(res.errorMessage || '操作失败')
        }
      }).finally(() => { this.spinning = false })
    },
    handleExport () {
      this.spinning = true
      this.$exportData('/project/exportRdAccountMethod', { year: this.currentYear }).then(() => {
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
    }
    .first-title {
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
