<!-- @Description:项目立项报告_上海浦东模板 -->
<template>
  <a-card>
    <a-form>
      <div style="height:100px;">
      </div>
      <div style="height:900px;">
        <div style="text-align: center;">
          <h2 style="font-size: 36px;">项目开发计划任务书</h2>
        </div>
        <div style="height:200px;"></div>
        <table style="border-collapse: collapse; width: 100%;">
          <tbody>
            <tr>
              <td style="width: 14%;" ></td>
              <td style="width: 72%;  vertical-align:top; padding-top: 7px; font-size: 21px;">项目名称：{{ publicInfo.commonMap.pname }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 72%; font-size: 21px;">起止年月：{{ publicInfo.commonMap.beginAndEndMonth }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 72%; font-size: 21px;">承担部门：{{ publicInfo.commonMap.parentRdDept }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 72%; font-size: 21px;">课题负责人：{{ publicInfo.commonMap.projectMasterName }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 72%; font-size: 21px;">
                <i class="required-field"/>联系电话：<a-input style="width: 200px; margin-right: 8px;" v-model="content.tel"></a-input><i class="required-field"/>手机：<a-input style="width: 200px;" v-model="content.mobile"></a-input>
              </td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 72%; font-size: 21px;"><i class="required-field"/>Email：<a-input style="width: 200px" v-model="content.email"></a-input></td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;">&nbsp;</td>
              <td style="width: 72%;">&nbsp;</td>
              <td style="width: 14%;">&nbsp;</td>
            </tr>
          </tbody>
        </table>
        <div style="height: 50px"></div>
        <div style="text-align: center; font-size: 21px;">{{ publicInfo.commonMap.companyName }}</div>
        <div style="height: 50px"></div>
        <div style="text-align: center; font-size: 21px;">
          <i class="required-field"/>
          <a-month-picker
            :getCalendarContainer="getCalendarContainer"
            :value="content.fillDate ? moment(content.fillDate,'YYYY年MM月') : content.fillDate"
            format="YYYY年MM月"
            @change="onfillDateChange"
            placeholder="选择月份"
          />
        </div>
      </div>
      <h2 style="text-align:center;">项目开发计划任务书</h2>
      <div>
        <h4>研发项目所属技术领域：{{ publicInfo.commonMap.tecIndustry }}</h4>
        <h4>研发方式：{{ project.formula }}</h4>
      </div>
      <div>
        <h2><i class="required-field"/>一、立项依据</h2>
        <wang-editor
          key="ProjectBasis"
          v-model="content.ProjectBasis"
          :projectId="project.id"
          placeholder="国内外现状、水平和发展趋势；项目开发的目的、意义；本项目对本市相关行业的技术、工艺领先具有推动作用之处；项目的市场前景。"
        />
      </div>
      <div>
        <h2><i class="required-field"/>二、研发内容和目标</h2>
        <wang-editor
          key="rdContentAndTarget"
          v-model="content.rdContentAndTarget"
          :projectId="project.id"
          placeholder="项目主要内容、目标及关键技术；主要技术指标或经济指标。"
        />
      </div>
      <div>
        <h2><i class="required-field"/>三、现有研发条件和工作基础</h2>
        <wang-editor
          key="advantage"
          v-model="content.advantage"
          :projectId="project.id"
          placeholder="承担单位开展项目的优势（人才、设施等条件）"
        />
      </div>
      <div>
        <h2><i class="required-field"/>四、计划进度</h2>
        <wang-editor
          key="schedule"
          v-model="content.schedule"
          :projectId="project.id"
          placeholder="包括总的研究期限、年度计划进度以及已经取得的阶段成果；集中研究开发项目，请注明集团企业研发机构（部门）的名称。"
        />
      </div>

      <div>
        <h2><i class="required-field"/>五、研究开发专门机构或项目组的编制情况和研发人员名单</h2>
        <wang-editor
          key="orgDesc"
          v-model="content.orgDesc"
          :projectId="project.id"
          placeholder="描述组织架构"
        />
        <chapter-7-tab
          ref="chapter7Tab"
          :content="content"
          :project="project"
          :docId="docId"
          :columns="memberColumns"
        />
      </div>

      <div>
        <h2><i class="required-field"/>六、预期成果归属</h2>
        <wang-editor
          key="achievements"
          v-model="content.achievements"
          :projectId="project.id"
          placeholder="预期成果归属"
        />
      </div>

      <div>
        <h2>七、项目经费预算</h2>
        <wang-editor
          key="budgetDesc"
          v-model="content.budgetDesc"
          :projectId="project.id"
          placeholder="包括项目预计总经费和本年度预算经费。对于委托研究开发项目，还需提供本企业和受托方经费预算；对于合作研究开发项目，还需提供协议或合同约定的费用分摊方法，及协议或合同约定的分享收益的比例；对于集中研究开发项目，还需提供协议或合同约定的费用分摊方法，以及参与研发的各成员企业情况。"
        />
        <div style="height: 20px"></div>
        <div> 项目预计总经费{{ publicInfo.commonMap.totalBudget }}万元。</div>
        <div style="text-align: center;">项目经费支出预算表</div>
        <div style="text-align: right;">单位：万元</div>
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
          <vxe-table-column title="经费支出预算" align="center" header-align="center" >
            <vxe-table-column title="科目" field="subjectName" align="left" header-align="center" min-width="200"></vxe-table-column>
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
                  <a-input-number v-if="![13].includes(row.key)" v-model="row[year]" placeholder="预算数" @blur="updateFooter" style="width: 100%;" />
                  <span v-else>{{ row[year] || 0 }}</span>
                </template>
              </vxe-table-column>
            </vxe-table-column>
          </vxe-table-column>
        </vxe-grid>
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
    this.content = getTemplateContent('projectReportForm_5')
    this.BPContent = getTemplateContent('projectReportForm_5')
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
        { title: '姓名', field: 'ename' },
        { title: '部门', field: 'deptName' },
        { title: '项目角色', field: 'role' }
      ],
      requiredFields: ['tel', 'mobile', 'email', 'fillDate', 'ProjectBasis', 'rdContentAndTarget', 'advantage', 'schedule', 'orgDesc', 'achievements']
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
        if (index === 0) {
          combined.push('合计')
          total.push('总计')
        }
        if (index >= 1) {
          combined.push(this.sumNum(data, column.property))
        }
      })
      columns.map((column, index) => {
        if (index === 1) {
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
        if (_columnIndex < 1) {
          return {
            rowspan: 1,
            colspan: 1
          }
        }
        if (_columnIndex === 1) {
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
    onfillDateChange (dateString, record) {
      this.content.fillDate = record
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
