<!-- 项目立项报告_北区通用模板 -->
<template>
  <a-card>
    <a-form>
      <div style="height:100px;"></div>
      <div style="height:900px;">
        <div style="text-align: center;">
          <h2 style="font-size: 36px;">企业研究开发项目计划书</h2>
        </div>
        <div style="height:400px;"></div>
        <table style="border-collapse: collapse; width: 100%;">
          <tbody>
            <tr>
              <td style="width: 14%;" ></td>
              <td
                style="width: 20%;  vertical-align:top; padding-top: 7px; font-size: 21px; text-align:justify; text-align-last:justify;"
                align="right">项目名称</td>
              <td style="width: 2%; font-size: 21px; vertical-align:top; padding-top: 7px; font-size: 21px;" >：</td>
              <td style="width: 50%; font-size: 21px; vertical-align:top; border-bottom: 1px solid; line-height: 36px;" >{{ publicInfo.commonMap.pname }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 20%; font-size: 21px; text-align:justify; text-align-last:justify;" align="right">企业名称</td>
              <td style="width: 2%; font-size: 21px;" >：</td>
              <td style="width: 50%; font-size: 21px; border-bottom: 1px solid;" >{{ publicInfo.commonMap.companyName }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 20%; font-size: 21px; text-align:justify; text-align-last:justify;" align="right">项目类别</td>
              <td style="width: 2%; font-size: 21px;" >：</td>
              <td style="width: 50%; font-size: 21px; border-bottom: 1px solid;" >{{ project.formula }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 20%; font-size: 21px; text-align:justify; text-align-last:justify;" align="right">项目负责人</td>
              <td style="width: 2%; font-size: 21px;" >：</td>
              <td style="width: 50%; font-size: 21px; border-bottom: 1px solid;" >{{ publicInfo.commonMap.projectMasterName }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;"></td>
              <td style="width: 20%; font-size: 21px;  text-align:justify; text-align-last:justify;" align="right">项目起止日期</td>
              <td style="width: 2%; font-size: 21px;" >：</td>
              <td style="width: 50%; font-size: 21px;  border-bottom: 1px solid;" >{{ publicInfo.commonMap.beginAndEndMonth }}</td>
              <td style="width: 14%;" ></td>
            </tr>
            <tr>
              <td style="width: 14%;">&nbsp;</td>
              <td style="width: 20%;">&nbsp;</td>
              <td style="width: 2%;">&nbsp;</td>
              <td style="width: 50%;">&nbsp;</td>
              <td style="width: 14%;">&nbsp;</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div>
        <h2>一、立项依据</h2>
        <h3><i class="required-field"/>1. 立项背景</h3>
        <wang-editor
          key="trend"
          v-model="content.trend"
          :projectId="project.id"
          placeholder="立项背景"
        />
        <h3><i class="required-field"/>2. 项目开发的目的、意义</h3>
        <wang-editor
          key="purpose"
          v-model="content.purpose"
          :projectId="project.id"
          placeholder="项目开发的目的、意义"
        />
        <h3><i class="required-field"/>3. 本项目达到的技术水平</h3>
        <wang-editor
          key="prospects"
          v-model="content.prospects"
          :projectId="project.id"
          placeholder="本项目达到的技术水平"
        />
      </div>
      <div>
        <h2>二、开发内容和目标</h2>
        <h3><i class="required-field"/>1. 项目主要研发内容</h3>
        <wang-editor
          key="primaryContent"
          v-model="content.primaryContent"
          :projectId="project.id"
          placeholder="项目主要研发内容"
        />
        <h3><i class="required-field"/>2．主要技术创新之处</h3>
        <wang-editor
          key="innovation"
          v-model="content.innovation"
          :projectId="project.id"
          placeholder="主要技术创新之处"
        />
        <h3>3．达到的主要技术和经济指标</h3>
        <h4><i class="required-field"/>技术指标</h4>
        <wang-editor
          key="indicators"
          v-model="content.indicators"
          :projectId="project.id"
          placeholder="技术指标"
        />
        <h4><i class="required-field"/>经济指标</h4>
        <wang-editor
          key="benefits"
          v-model="content.benefits"
          :projectId="project.id"
          placeholder="经济指标"
        />
      </div>
      <div>
        <h2><i class="required-field"/>三、研究开发试验方法及采用工艺技术路线</h2>
        <wang-editor
          key="methodAndRoute"
          v-model="content.methodAndRoute"
          :projectId="project.id"
          placeholder="研究开发试验方法及采用工艺技术路线"
        />
      </div>
      <div>
        <h2><i class="required-field"/>四、研究开发工作组织和分工情况</h2>
        <wang-editor
          key="orgAndDivision"
          v-model="content.orgAndDivision"
          :projectId="project.id"
          placeholder="研究开发工作组织和分工情况"
        />
      </div>
      <div>
        <h2><i class="required-field"/>五、现有开发条件和工作基础</h2>
        <wang-editor
          key="conditionsAndBasis"
          v-model="content.conditionsAndBasis"
          :projectId="project.id"
          placeholder="现有开发条件和工作基础"
        />
      </div>
      <div>
        <h2><i class="required-field"/>六、计划进度</h2>
        <div>本项目的研发进度为{{ publicInfo.commonMap.beginAndEndMonth }}，研发进度具体计划如下：</div>
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
        <h2><i class="required-field"/>七、主要研究人员名单</h2>
        <chapter-7-tab
          ref="chapter7Tab"
          :content="content"
          :project="project"
          :docId="docId"
          :columns="memberColumns"
        />
      </div>

      <div>
        <h2>八、经费概算</h2>
        <div> 项目预计总经费{{ publicInfo.commonMap.totalBudget }}万元。</div>
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
import { popupContainer, domName } from '@/docTemplate/Templates/js/screenFullMountDom'
import { mapGetters } from 'vuex'

export default {
  name: 'ProjectReportForm',
  mixins: [yearMiXin, dateMixin],
  components: { WangEditor, Chapter5Tab, Chapter7Tab },

  created () {
    this.content = getTemplateContent('projectReportForm_3')
    this.BPContent = getTemplateContent('projectReportForm_3')
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
        { field: 'position', title: '岗位' },
        { field: 'role', title: '项目分工' }
      ],
      requiredFields: ['trend', 'purpose', 'prospects', 'primaryContent', 'innovation', 'indicators', 'benefits', 'methodAndRoute', 'orgAndDivision', 'conditionsAndBasis', 'stageList']
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
