<!--
 * @Author:
 * @Date: 2020-12-01 09:42:56
 * @LastEditTime: 2022-09-14 11:47:00
 * @LastEditors: hm
 * @Description:立项报告_陕西模板
 * @FilePath: \RS-VUE\src\docTemplate\Templates\ProjectReportForm_1.vue
-->
<template>
  <a-card>
    <a-form>
      <div style="height:100px;"></div>
      <div style="height:900px;">
        <div style="text-align: center;">
          <h2 style="font-size: 36px;">企业技术开发项目计划书</h2>
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
              <td style="width: 50%; font-size: 21px; vertical-align:top; border-bottom: 1px solid; line-height: 36px;" >{{ project.pname }}</td>
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
        <h2>一、项目立项依据</h2>
        <h3><i class="required-field"/>（一）国内外现状、水平和发展趋势</h3>
        <wang-editor
          key="trend"
          v-model="content.trend"
          :projectId="project.id"
          placeholder="国内外现状、水平和发展趋势"
        />
        <h3><i class="required-field"/>（二）项目开发的目的、意义</h3>
        <wang-editor
          key="purpose"
          v-model="content.purpose"
          :projectId="project.id"
          placeholder="项目开发的目的、意义"
        />
        <h3><i class="required-field"/>（三）本项目达到的技术水平及市场前景</h3>
        <wang-editor
          key="prospects"
          v-model="content.prospects"
          :projectId="project.id"
          placeholder="本项目达到的技术水平及市场前景"
        />
      </div>
      <div>
        <h2>二、开发内容和目标</h2>
        <h3><i class="required-field"/>（一）项目主要内容</h3>
        <wang-editor
          key="primaryContent"
          v-model="content.primaryContent"
          :projectId="project.id"
          placeholder="项目主要内容"
        />
        <h3><i class="required-field"/>（二）技术创新之处</h3>
        <wang-editor
          key="innovation"
          v-model="content.innovation"
          :projectId="project.id"
          placeholder="技术创新之处"
        />
        <h3>（三）主要技术指标或社会效益</h3>
        <!-- <wang-editor
          key="f"
          v-model="content.f"
          :projectId="project.id"
          placeholder="主要技术指标或社会效益"
        /> -->
        <h4><i class="required-field"/>1.技术指标</h4>
        <wang-editor
          key="indicators"
          v-model="content.indicators"
          :projectId="project.id"
          placeholder="技术指标"
        />
        <h4><i class="required-field"/>2.社会效益</h4>
        <wang-editor
          key="benefits"
          v-model="content.benefits"
          :projectId="project.id"
          placeholder="社会效益"
        />
      </div>
      <div>
        <h2>三、研究开发方法及技术路线</h2>
        <h3><i class="required-field"/>（一）研究开发方法</h3>
        <wang-editor
          key="method"
          v-model="content.method"
          :projectId="project.id"
          placeholder="研究开发方法"
        />
        <h3><i class="required-field"/>（二）技术路线</h3>
        <wang-editor
          key="route"
          v-model="content.route"
          :projectId="project.id"
          placeholder="技术路线"
        />
      </div>
      <div>
        <h2><i class="required-field"/>四、现有开发条件和工作基础</h2>
        <wang-editor
          key="conditionsAndBasis"
          v-model="content.conditionsAndBasis"
          :projectId="project.id"
          placeholder="现有开发条件和工作基础"
        />
      </div>
      <div>
        <h2><i class="required-field"/>五、经费预算</h2>
        <div> 项目预计总经费<i class="required-field"/><a-input v-model="content.planPutInto" placeholder="请输入预计总经费入" style="width: 200px; padding: 0 5px; margin: 0 8px;" />万元。</div>
        <div> 项目经费支出预算表     	                      单位：万元</div>
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
                :title="`${year}年份`"
                align="center"
                header-align="center" >
                <template #default="{row}">
                  <!-- <a-input-number v-if="![12,13].includes(row.key)" v-model="row[year]" placeholder="预算数" style="width: 100%;" @blur="computedTotal(year)"/> -->
                  <a-input-number v-if="![12,13].includes(row.key)" v-model="row[year]" placeholder="预算数" @blur="updateFooter" style="width: 100%;" />
                  <span v-else>{{ row[year] || 0 }}</span>
                </template>
              </vxe-table-column>
            </vxe-table-column>
          </vxe-table-column>

        </vxe-grid>
      </div>
      <div>
        <h2><i class="required-field"/>六、项目研究开发人员</h2>
        <chapter-7-tab
          ref="chapter7Tab"
          :content="content"
          :project="project"
          :docId="docId"
          :columns="memberColumns"
        />
      </div>
      <div>
        <h2><i class="required-field"/>七、计划进度（包括总的研究期限、年度计划进度）</h2>
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
            <vxe-table-column title="序号" type="seq" width="55" align="center"></vxe-table-column>
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
            <vxe-table-column title="主要工作内容" field="mainWork" min-width="300" align="left" header-align="center">
              <template #default="{row}">
                <a-textarea v-model="row.mainWork" placeholder="主要工作内容" :rows="2"></a-textarea>
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
      <div class="footer">
        <div class="item">
          审核人：
          <a-input class="input" v-model="content.auditMan" placeholder="请输入审核人"></a-input>
        </div>
        <div class="item">
          审核意见：
          <a-input class="input" v-model="content.auditOpinion" placeholder="请输入审核意见"></a-input>
        </div>
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

export default {
  name: 'ProjectReportForm',
  mixins: [yearMiXin, dateMixin],
  components: { WangEditor, Chapter5Tab, Chapter7Tab },

  created () {
    this.content = getTemplateContent('projectReportForm_1')
    this.BPContent = getTemplateContent('projectReportForm_1')
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
        { field: 'role', title: '项目角色' }
      ],
      requiredFields: ['trend', 'purpose', 'prospects', 'primaryContent', 'innovation', 'indicators', 'benefits', 'method', 'route', 'conditionsAndBasis', 'planPutInto', 'stageList']
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
    }
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
