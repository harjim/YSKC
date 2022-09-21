<!-- @Description:项目立项报告_北区复杂模板 -->
<template>
  <a-card>
    <a-form>
      <table style="border-collapse: collapse; width: 100%;">
        <tbody>
          <tr>
            <td style="width: 14%;" ></td>
            <td>
              <div style="font-size: 21px; vertical-align:top; padding-top: 7px;">
                <span>项目编号：</span><span style="border-bottom: 1px solid;">{{ publicInfo.commonMap.rdIndex }}</span>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <div style="height:100px;">
      </div>

      <div style="height:900px;">
        <div style="text-align: center;">
          <h2 style="font-size: 30px;">{{ publicInfo.commonMap.companyName }}</h2>
          <h2 style="font-size: 36px;">项目开发计划任务书</h2>
        </div>
        <div style="height:200px;"></div>
        <table style="border-collapse: collapse; width: 100%;">
          <tbody>
            <tr>
              <td style="width: 14%;" ></td>
              <td
                style="width: 15%;  vertical-align:top; padding-top: 7px; font-size: 21px; text-align:justify; text-align-last:justify;"
                align="right">项目名称</td>
              <td style="width: 2%; font-size: 21px; vertical-align:top; padding-top: 7px; font-size: 21px;" >：</td>
              <td style="width: 55%; font-size: 21px; vertical-align:top; border-bottom: 1px solid; line-height: 36px;" >{{ publicInfo.commonMap.pname }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 15%; font-size: 21px; text-align:justify; text-align-last:justify;" align="right">立项部门</td>
              <td style="width: 2%; font-size: 21px;" >：</td>
              <td style="width: 55%; font-size: 21px; border-bottom: 1px solid;" >{{ publicInfo.commonMap.parentRdDept }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 15%; font-size: 21px; text-align:justify; text-align-last:justify;" align="right">项目负责人</td>
              <td style="width: 2%; font-size: 21px;" >：</td>
              <td style="width: 55%; font-size: 21px; border-bottom: 1px solid;" >{{ publicInfo.commonMap.projectMasterName }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 15%; font-size: 21px;  text-align:justify; text-align-last:justify;" align="right">项目起止时间</td>
              <td style="width: 2%; font-size: 21px;" >：</td>
              <td style="width: 55%; font-size: 21px;  border-bottom: 1px solid;" >{{ publicInfo.commonMap.beginAndEndMonth }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;">&nbsp;</td>
              <td style="width: 15%;">&nbsp;</td>
              <td style="width: 2%;">&nbsp;</td>
              <td style="width: 55%;">&nbsp;</td>
              <td style="width: 14%;">&nbsp;</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div>
        <h2>一、立项依据</h2>
        <h3><i class="required-field"/>1、本项目国内外科技创新发展概况和最新发展趋势</h3>
        <wang-editor
          key="trend"
          v-model="content.trend"
          :projectId="project.id"
          placeholder="本项目国内外科技创新发展概况和最新发展趋势"
        />
        <h3><i class="required-field"/>2、本项目研究的目的、意义</h3>
        <wang-editor
          key="purpose"
          v-model="content.purpose"
          :projectId="project.id"
          placeholder="本项目研究的目的、意义"
        />
        <h3><i class="required-field"/>3、本项目研究现有起点及已存在的知识产权情况</h3>
        <wang-editor
          key="startPointAndIP"
          v-model="content.startPointAndIP"
          :projectId="project.id"
          placeholder="本项目研究现有起点及已存在的知识产权情况"
        />
        <h3><i class="required-field"/>4、本项目研究国内外竞争情况及产业化前景</h3>
        <wang-editor
          key="prospects"
          v-model="content.prospects"
          :projectId="project.id"
          placeholder="本项目研究国内外竞争情况及产业化前景"
        />
      </div>

      <div>
        <h2>二、研究内容</h2>
        <h3>1、具体研究开发内容和要重点解决的关键技术问题</h3>
        <h4><i class="required-field"/>(1) 研究开发内容</h4>
        <wang-editor
          key="researchContents"
          v-model="content.researchContents"
          :projectId="project.id"
          placeholder="研究开发内容"
        />
        <h4><i class="required-field"/>(2) 关键技术</h4>
        <wang-editor
          key="keyTechnology"
          v-model="content.keyTechnology"
          :projectId="project.id"
          placeholder="关键技术"
        />
        <h3><i class="required-field"/>2、项目的特色和创新之处</h3>
        <wang-editor
          key="innovation"
          v-model="content.innovation"
          :projectId="project.id"
          placeholder="项目的特色和创新之处"
        />
        <h3><i class="required-field"/>3、要达到的主要技术</h3>
        <wang-editor
          key="mainTechnology"
          v-model="content.mainTechnology"
          :projectId="project.id"
          placeholder="要达到的主要技术"
        />
        <h3><i class="required-field"/>4、要达到的经济指标及社会、经济效益</h3>
        <wang-editor
          key="indicatorsAndBenefits"
          v-model="content.indicatorsAndBenefits"
          :projectId="project.id"
          placeholder="要达到的经济指标及社会、经济效益"
        />
      </div>

      <div>
        <h2>三、研究试验方法、技术路线以及工艺流程</h2>
        <h3><i class="required-field"/>1、研究试验方法</h3>
        <wang-editor
          key="method"
          v-model="content.method"
          :projectId="project.id"
          placeholder="研究试验方法"
        />
        <h3><i class="required-field"/>2、技术路线及方案布置设计</h3>
        <wang-editor
          key="route"
          v-model="content.route"
          :projectId="project.id"
          placeholder="技术路线及方案布置设计"
        />
      </div>

      <div>
        <h2>四、工作基础和条件</h2>
        <h3><i class="required-field"/>1、承担单位概况</h3>
        <wang-editor
          key="unitOverview"
          v-model="content.unitOverview"
          :projectId="project.id"
          placeholder="承担单位概况"
        />
        <h3><i class="required-field"/>2、本项目现有的研究工作基础</h3>
        <wang-editor
          key="workBasis"
          v-model="content.workBasis"
          :projectId="project.id"
          placeholder="本项目现有的研究工作基础"
        />
        <h3><i class="required-field"/>3、项目的组织管理及相关保障措施</h3>
        <wang-editor
          key="orgManageAndMeasures"
          v-model="content.orgManageAndMeasures"
          :projectId="project.id"
          placeholder="项目的组织管理及相关保障措施"
        />
        <h3><i class="required-field"/>4、项目实施具备的人才队伍、经费配套投入能力及科技服务管理能力</h3>
        <wang-editor
          key="serviceManage"
          v-model="content.serviceManage"
          :projectId="project.id"
          placeholder="项目实施具备的人才队伍、经费配套投入能力及科技服务管理能力"
        />
        <h3><i class="required-field"/>5、本项目实施可能对环境的影响及预防治理方案</h3>
        <wang-editor
          key="environment"
          v-model="content.environment"
          :projectId="project.id"
          placeholder="本项目实施可能对环境的影响及预防治理方案"
        />
      </div>

      <div>
        <h2>五、项目研究预期成果及效益</h2>
        <h3><i class="required-field"/>1、预期成果</h3>
        <wang-editor
          key="achievements"
          v-model="content.achievements"
          :projectId="project.id"
          placeholder="预期成果"
        />
        <h3><i class="required-field"/>2、预期效益</h3>
        <wang-editor
          key="benefits"
          v-model="content.benefits"
          :projectId="project.id"
          placeholder="预期效益"
        />
      </div>

      <div>
        <h2><i class="required-field"/>六、计划进度安排与考核指标</h2>
        <div>
          <a-button type="primary" style="margin-bottom: 10px;margin-right:10px;" @click="addRow">添加</a-button>
          <a-button type="primary" style="margin-bottom: 10px;margin-right:10px;" @click="addStage">引入项目阶段</a-button>
          <vxe-grid
            ref="table"
            :data="content.stageList"
            border
            resizable
            auto-resize
            highlight-hover-row
            show-overflow
            show-header-overflow
            highlight-current-row
            header-align="center"
          >
            <vxe-table-column title="阶段" type="seq" width="55" align="center"></vxe-table-column>
            <vxe-table-column title="时间" field="beginDate" width="300" align="center" header-align="center">
              <template #default="{row}">
                <a-date-picker
                  style="width:120px;"
                  :getCalendarContainer="getCalendarContainer"
                  @change="(dates,dateString)=>onDateChange(dateString,row,'beginDate')"
                  :value="row.beginDate ? moment(row.beginDate,'YYYY-MM-DD') :row.beginDate"
                  format="YYYY-MM-DD"
                  :allowClear="false"
                  :disabledDate="(val)=>disabledBDate(val,row)"
                  @openChange="(status) => onOpenChange(status,'beginDate',row)"
                />
                -
                <a-date-picker
                  style="width:120px;"
                  :getCalendarContainer="getCalendarContainer"
                  @change="(dates,dateString)=>onDateChange(dateString,row,'endDate')"
                  :value="row.endDate ? moment(row.endDate,'YYYY-MM-DD') :row.endDate"
                  format="YYYY-MM-DD"
                  :allowClear="false"
                  :disabledDate="(val)=>disabledEDate(val,row)"
                  @openChange="(status) => onOpenChange(status,'endDate',row)"
                />
              </template>
            </vxe-table-column>
            <vxe-table-column title="完成内容" field="mainWork" min-width="300" align="left" header-align="center">
              <template #default="{row}">
                <a-textarea v-model="row.mainWork" placeholder="完成内容" :rows="2"></a-textarea>
              </template>
            </vxe-table-column>
            <vxe-table-column title="操作" width="90" align="center" header-align="center">
              <template #default="{row}">
                <a type="primary" @click="removeRow(row)">移除</a>
              </template>
            </vxe-table-column>
          </vxe-grid>
        </div>
      </div>

      <div>
        <h2>七、项目经费情况</h2>
        <h3><i class="required-field"/>1、项目经费来源（经费单位：万元）</h3>
        <table border="1" borderColor="#e8eaec" cellpadding="10" style="border-collapse: collapse; width: 100%; text-align: center;">
          <tbody>
            <tr>
              <td rowspan="2">项目经费来源</td>
              <td>单位自筹</td>
              <td>政府资助</td>
              <td>其他</td>
            </tr>
            <tr>
              <td>{{ publicInfo.commonMap.totalBudget }}</td>
              <td><a-input-number style="width: 150px;" v-model="content.govFunding" /></td>
              <td><a-input-number style="width: 150px;" v-model="content.otherFunding" /></td>
            </tr>
          </tbody>
        </table>

        <h3><i class="required-field"/>2、项目新增总投资支出（经费单位：万元）</h3>
        <vxe-grid
          ref="budgetTable"
          :data="content.tableDatas"
          size="small"
          border
          resizable
          auto-resize
          highlight-hover-row
          show-overflow
          show-header-overflow
          highlight-current-row
          show-footer
          :footer-method="footerMethod"
          :footer-span-method="footerColspanMethod"
        >
          <vxe-table-column title="项目经费预算表（单位：万元）" align="center" header-align="center" >
            <vxe-table-column title="科目" field="subjectName_1" align="left" header-align="center" min-width="100"></vxe-table-column>
            <vxe-table-column title="预算数" field="value_1" align="left" header-align="center" min-width="100">
              <template #default="{row}">
                <a-input-number v-if="row.subjectName_1" v-model="row.value_1" placeholder="预算数" style="width: 100%;" />
              </template>
            </vxe-table-column>
            <vxe-table-column title="科目" field="subjectName_2" align="left" header-align="center" min-width="100"></vxe-table-column>
            <vxe-table-column title="预算数" align="center" header-align="center" >
              <vxe-table-column
                v-for=" year in years"
                :key="year"
                :field="year+''"
                :title="`${year}年`"
                align="center"
                header-align="center" >
                <template #default="{row}">
                  <!-- <a-input-number v-if="![12,13].includes(row.key)" v-model="row[year]" placeholder="预算数" style="width: 100%;" @blur="computedTotal(year)"/> -->
                  <a-input-number v-if="![17,18].includes(row.key)" v-model="row[year]" placeholder="预算数" @blur="updateFooter" style="width: 100%;" />
                  <span v-else>{{ row[year] || 0 }}</span>
                </template>
              </vxe-table-column>
            </vxe-table-column>
          </vxe-table-column>
        </vxe-grid>
      </div>
      <div>
        <h2><i class="required-field"/>八、项目参加人员</h2>
        <chapter-7-tab
          ref="chapter7Tab"
          :content="content"
          :project="project"
          :docId="docId"
          :columns="memberColumns"
        />
      </div>
    </a-form>
  </a-card>
</template>
<script>
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import { getTemplateContent } from '@/docTemplate/Templates/js/templateContentType'
import dateMixin from './js/dateMixin'
import WangEditor from '@/components/Editor/WangEditor'
import Chapter5Tab from './projectReportTab/Chapter5Tab'
import Chapter7Tab from './projectReportTab/Chapter7Tab'
import Chapter8Tab from './projectReportTab/Chapter8Tab'
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import { mapGetters } from 'vuex'

export default {
  name: 'ProjectReportForm',
  mixins: [yearMiXin, dateMixin],
  components: { WangEditor, Chapter5Tab, Chapter7Tab, Chapter8Tab },

  created () {
    this.content = getTemplateContent('projectReportForm_6')
    this.BPContent = getTemplateContent('projectReportForm_6')
  },
  props: {
    projectId: {
      type: Number,
      required: true
    },
    docId: {
      type: Number,
      required: true
    },
    project: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      domName,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      content: {},
      BPContent: {},
      currentYear: 2020,
      row: { beginDate: undefined, endDate: undefined, mainWork: '' },
      memberColumns: [
        { field: 'index', title: '序号', width: 60, type: 'seq' },
        { field: 'ename', title: '姓名' },
        { field: 'deptName', title: '部门' },
        { field: 'position', title: '职称' },
        { field: 'etype', title: '人员类型' },
        { field: 'role', title: '项目分工' }
      ],
      requiredFields: ['trend', 'purpose', 'startPointAndIP', 'prospects', 'researchContents', 'keyTechnology', 'innovation', 'mainTechnology', 'indicatorsAndBenefits', 'method', 'route', 'unitOverview', 'workBasis', 'orgManageAndMeasures', 'serviceManage', 'environment', 'achievements', 'benefits', 'stageList', 'govFunding', 'otherFunding', 'tableDatas']
    }
  },
  mounted () {
    // this.loadDept()
  },
  watch: {
    project (v) {
      this.content._pro = v.id
      this.content._emp = v.masterENumber
    }
  },
  computed: {
    years () {
      const result = []
      const { beginYear, endYear } = this.project
      for (let i = beginYear; i <= endYear; i++) {
        result.push(i)
      }
      return result
    },
    ...mapGetters(['userInfo'])
  },
  methods: {
    search () {
      this.currentYear = this.currentYear
    },
    updateFooter () {
      this.$refs.budgetTable.updateFooter()
    },
    footerMethod ({ columns, data }) {
      const combined = []
      const total = []
      columns.map((column, index) => {
        if (index < 2) {
          combined.push('')
          total.push('')
        }
        if (index === 2) {
          combined.push('合计')
          total.push('总计')
        }
        if (index > 2) {
          combined.push(this.sumNum(data, column.property))
        }
      })
      columns.map((column, index) => {
        if (index === 3) {
          total.push(this.totalNum())
        }
      })
      return [combined, total]
    },
    sumNum (list, field) {
      const yearTotal = this.content.tableDatas.reduce((total, currentValue) => {
        if (currentValue[field]) {
          return total + Number(currentValue[field])
        } else {
          return total
        }
      }, 0)
      this.$set(this.content.sumBudget, field, yearTotal)
      return yearTotal
    },
    totalNum () {
      let count = 0
      this.years.forEach((year, index) => {
        count += Number(this.content.sumBudget[year]) || 0
      })
      this.content.totalBuget = count
      return count
    },
    footerColspanMethod ({ $rowIndex, _columnIndex }) {
      if ($rowIndex === 1) {
        if (_columnIndex <= 2) {
          return {
            rowspan: 1,
            colspan: 1
          }
        }
        if (_columnIndex === 3) {
          return {
            rowspan: 1,
            colspan: this.years.length
          }
        } else {
          return {
            rowspan: 0,
            colspan: 0
          }
        }
      }
    },
    getCalendarContainer (trigger) {
      return popupContainer(this.domName)
    },
    disabledBDate (current, record) {
      if (record.endDate !== undefined) {
        return moment(this.project.beginDate) > current || current >= moment(record.endDate).add(1, 'days')
      }
      return moment(this.project.beginDate) > current || current >= moment(this.project.endDate).add(1, 'days')
    },
    disabledEDate (current, record) {
      if (record.beginDate !== undefined) {
        return current >= moment(this.project.endDate).add(1, 'days') || current < moment(record.beginDate)
      }
      return moment(this.project.beginDate) > current || current >= moment(this.project.endDate).add(1, 'days')
    },
    callback (activeKey) {
      // 切换tab调用
      this.activeKey = activeKey
    },
    onDateChange (dateString, record, name) { this.$set(record, name, dateString) },
    addRow () {
      const tempRow = { ...this.row }
      tempRow.num = this.content.stageList.length + 1
      this.content.stageList.push(tempRow)
    },
    addStage () {
      this.$http.get('/projectDocFile/getReportStage', { params: { projectId: this.project.id } })
        .then(res => {
          if (res.data) {
            this.tableDate = res.data
            let num = this.content.stageList.length
            this.content.stageList.push(...res.data.map(item => {
              return {
                num: ++num,
                beginDate: item.beginDate,
                endDate: item.endDate,
                mainWork: (item.stageType ? item.stageType + ':' : '') + (item.workDesc ? item.workDesc : '')
              }
            }))
          }
        })
    },
    removeRow (record) {
      const tempRows = []
      var num = 0
      for (var i = 0; i < this.content.stageList.length; i++) {
        const row = this.content.stageList[i]
        if (row.num !== record.num) {
          row.num = ++num
          tempRows.push(row)
        }
      }
      this.content.stageList = tempRows
    },
    onOpenChange (status, field, record) {
      if (!record[field] && status) {
        record[field] = moment(this.project.beginDate).format('YYYY-MM-DD')
      }
    }
  }
}
</script>

<style lang="less" scoped>
h1,h2,h3,h4,h5 {
font-weight: 650;
}
.footer {
  display: flex;
  margin: 24px 0 ;
  .item {
    display: flex;
    flex: 1;
    align-items:center;
    .input {
      flex: 1;
      margin: 0 8px;
    }
  }
}
</style>
