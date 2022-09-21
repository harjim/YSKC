<!-- 项目立项计划 -->
<template>
  <a-card :bordered="false" style="height: 100%;" :bodyStyle="{ height:'100%' }">
    <a-form layout="inline">
      <a-form-item label="项目名称">
        <a-input v-model="pname" placeholder="请输入项目名称" />
      </a-form-item>
      <a-form-item>
        <a-button
          type="primary"
          v-if="$auth('project:report:list:search')"
          @click="queryProject"
        >查询</a-button>
      </a-form-item>
    </a-form>
    <div id="wrapperTable">
      <a-spin style="height: 100%;" :spinning="isSpinning">
        <a-checkbox-group
          @change="checkBoxGroup"
          style="height: 100%; width: 100%;"
          :value="selectedRows"
        >
          <vxe-grid
            class="mytable-style"
            id="project:report:list"
            ref="table"
            :data="projectTableData"
            highlight-hover-row
            highlight-current-row
            show-overflow
            resizable
            auto-resize
            max-height="99%"
            row-id="id"
            @cell-click="cellClickEvent"
            :cell-class-name="cellClassName"
            :tree-config="{ children: 'children', trigger:'cell', indent: 0}"
            :toolbar="{ refresh: { query: queryProject }, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
            :customConfig="{storage: { visible: true, resizable: true } }"
          >
            <template v-slot:toolbar_buttons>
              <a-button
                type="primary"
                style="margin-right: 10px;"
                v-if="$auth('project:report:list:number') && modify"
                @click="$refs.projectNumModal.set(currentYear, planNum, plan, countRenewalProject)"
              >规划项目</a-button>
              <a-button
                type="primary"
                style="margin-right: 10px;"
                v-if="$auth('project:report:list:add') && modify"
                @click="addProject()"
              >添加</a-button>
              <a-button
                type="primary"
                v-if="$auth('project:report:list:merge') && modify"
                style="margin-right: 10px;"
                @click="showMergeProjectModal"
                :disabled="selectedRowKeys.length <= 0"
              >合并</a-button>
              <a-button
                type="primary"
                style="margin-right: 10px;"
                v-if="$auth('project:report:list:import') && modify"
                @click="openUploadModal"
              >导入</a-button>
              <a-button
                type="primary"
                style="margin-right: 10px;"
                v-if="$auth('project:report:list:export')"
                @click="exportProject"
              >导出</a-button>
              <a-button
                v-if="$auth('project:report:list:del') && modify"
                type="primary"
                style="margin-right: 10px;"
                :disabled="selectedRowKeys.length <= 0"
                @click="handleDel(true)"
              >删除</a-button>
              <a-button
                v-if="$auth('project:report:list:submit') && modify && isMsUser"
                type="primary"
                style="margin-right: 10px;"
                :disabled="selectedRowKeys.length <= 0"
                @click="commitAudit"
              >提交</a-button>
              <span style="margin-right: 10px;">
                规划项目数:
                <a style="font-weight: 600">{{ plan.cnt?plan.cnt:'--' }}</a>
              </span>
              <span style="margin-right: 10px;">
                规划研发费:
                <a style="font-weight: 600">{{ plan.rdFee?plan.rdFee:'--' }}万元</a>
              </span>
              <span style="margin-right: 10px;">
                现有项目数:
                <a style="font-weight: 600">{{ projectTableData.length }}</a>
              </span>
              <span style="margin-right: 10px;">
                总预算:
                <a style="font-weight: 600">{{ sumBugdet(projectTableData) }}万元</a>
              </span>
              <span style="margin-right: 10px;">
                营收预测:
                <a style="font-weight: 600">{{ plan.revenueFcst?plan.revenueFcst:'--' }}万元</a>
              </span>
              <span style="margin-right: 10px;">
                公司总人数:
                <a style="font-weight: 600">{{ plan.employeeAmount?plan.employeeAmount:'--' }}</a>
              </span>
            </template>
            <vxe-table-column
              title="RD"
              width="120"
              align="left"
              header-align="left"
              fixed="left"
              tree-node
            >
              <template v-slot="{ row }">
                <a-badge
                  v-if="row.status !== null && isMsUser"
                  :dot="true"
                  :title="row.suggestion ? row.suggestion: undefined"
                  :offset="[4,3]"
                  :color=" row.status !== null ? statusColor[row.status] : '#FFF'"
                >
                  <span v-if="!(row.children && row.children.length)">
                    <a-tooltip v-if="row.beginYear !== currentYear">
                      <template slot="title">不能合并、删除、提交{{ currentYear }}年之前的项目</template>
                      <a-checkbox :value="row" :disabled="row.beginYear !== currentYear"></a-checkbox>
                    </a-tooltip>
                    <a-checkbox v-else :value="row" :disabled="row.status !== null && isMsUser ? !(isEditStatus(row.status)) : false" ></a-checkbox>
                  </span>
                  <i
                    v-else
                    class="ant-table-row-expand-icon"
                    :class="$refs.table.isTreeExpandByRow(row) ? 'ant-table-row-expanded' : 'ant-table-row-collapsed'"
                  />
                </a-badge>
                <template v-else >
                  <span v-if="!(row.children && row.children.length)">
                    <a-tooltip v-if="row.beginYear !== currentYear">
                      <template slot="title">不能合并、删除、提交{{ currentYear }}年之前的项目</template>
                      <a-checkbox :value="row" :disabled="row.beginYear !== currentYear"></a-checkbox>
                    </a-tooltip>
                    <a-checkbox v-else :value="row" :disabled="row.status !== null && isMsUser ? !(isEditStatus(row.status)) : false" ></a-checkbox>
                  </span>
                  <i
                    v-else
                    class="ant-table-row-expand-icon"
                    :class="$refs.table.isTreeExpandByRow(row) ? 'ant-table-row-expanded' : 'ant-table-row-collapsed'"
                  />
                </template>
                <span :style="{marginLeft:row.parentId > 0 ? '55px' : '0'}">
                  {{ handleRD(row) }}
                </span>
              </template>
            </vxe-table-column>
            <vxe-table-column field="pname" title="项目名称" min-width="140" align="left" fixed="left">
              <template v-slot="{ row }">
                <span
                  v-if="$auth('project:report:base:view') || $auth('project:report:member:search') || $auth('project:report:equipment:search')
                    || $auth('project:report:stage:search') || ($auth('project:report:outSource:view') && row.formula === 30)"
                >
                  <a title="点击查看项目设置" @click="showSetProjectModal(row)">{{ row.pname }}</a>
                </span>
                <span v-else>{{ row.pname }}</span>
              </template>
            </vxe-table-column>
            <vxe-table-column field="fullname" title="部门" width="140" align="left">
              <template v-slot="{ row }">
                <span v-if="row.deptId">{{ row.fullname }}</span>
                <span v-else>
                  {{ row.deptName ? `${row.deptName}` : '' }}{{ row.workshop && row.deptName ? '/' : '' }}{{
                    row.workshop ? `${row.workshop}` : '' }}{{ row.productLine && (row.deptName || row.workshop) ? '/' : '' }}{{
                    row.productLine ? `${row.productLine}` : '' }}{{ row.processSection && (row.deptName || row.workshop || row.productLine) ? '/' : '' }}{{
                    row.processSection ? `${row.processSection}` : '' }}
                </span>
              </template>
            </vxe-table-column>
            <vxe-table-column field="beginAndEndDate" title="起止日期" width="190" align="center">
              <template v-slot=" { row }">{{ `${row.beginDate} ~ ${row.endDate}` }}</template>
            </vxe-table-column>
            <vxe-table-column field="haveTrialProd" title="是否试制" width="190" align="center">
              <template v-slot="{ row }">
                <span v-if="row.hasChild"></span>
                <span v-else>
                  <span v-if="$auth('project:report:trial:search')">
                    <!-- 2020-10-09 开会禁用点击事件 -->
                    <!-- <a
                      title="点击进入试制详情"
                      @click="$refs.trialProdDataModal.showModal(row)"
                      v-if="row.trialProd"
                    >{{ row.tBeginDate + ' - ' + row.tEndDate }}</a> -->
                    <span v-if="row.trialProd">{{ row.tBeginDate + ' - ' + row.tEndDate }}</span>
                    <span v-else>无</span>
                  </span>
                  <span v-else>
                    <span v-if="row.trialProd">{{ row.tBeginDate + ' - ' + row.tEndDate }}</span>
                    <span v-else>无</span>
                  </span>
                </span>
              </template>
            </vxe-table-column>
            <vxe-table-column field="estimateExpense" title="预算(万元)" width="110" align="right">
              <template v-slot="{ row }">
                <span v-if="row.hasChild">{{ sumBugdet(row.children) }}</span>
                <span v-else>
                  <a
                    title="点击查看预算详情"
                    @click="$refs.budgetModal.add(row)"
                    v-if="$auth('project:report:budget:search')">
                    {{ formatBugdet(row) }}
                  </a>
                  <span v-else> {{ formatBugdet(row) }}
                  </span>
                </span>
              </template>
            </vxe-table-column>
            <vxe-table-column field="rdDeptName" title="研发部门" width="150" align="left">
            </vxe-table-column>
            <vxe-table-column field="ename" title="负责人" width="120" align="center"></vxe-table-column>
            <vxe-table-column field="formula" title="项目开展形式" width="120">
              <template v-slot="{row}">{{ getformula(row.formula) }}</template>
            </vxe-table-column>
            <vxe-table-column title="提案名称" width="120">
              <template v-slot="{row}">
                <a v-if="$auth('project:report:list:proposal')" @click="$refs.proposalModal.show(row)">{{ row.proName || '--' }}</a>
                <span v-else>{{ row.proName || '--' }}</span>
              </template>
            </vxe-table-column>
            <vxe-table-column
              title="操作"
              min-width="100"
              align="left"
              header-align="center"
              fixed="right"
            >
              <template v-slot="{ row }">
                <span v-if="$auth('project:report:list:del') && row .children === null && row.beginYear === currentYear && modify">
                  <a-popconfirm title="是否确定删除?" @confirm="handleDel(false,row)">
                    <a :disabled="row .children !== null">删除</a>
                  </a-popconfirm>
                </span>
                <a-divider
                  type="vertical"
                  v-if="($auth('project:report:list:del') && $auth('project:report:list:remove') && row .children ===null ) && (row .parentId > 0) && row.rdTitle.includes(currentYear) && modify"
                />
                <span v-if="$auth('project:report:list:remove') && modify">
                  <a
                    v-if="row.parentId > 0 && row.beginYear === currentYear && row.rdTitle.includes(currentYear)"
                    :disabled="row .parentId === 0"
                    @click="showmoveOutProjectModal(row)"
                  >移出</a>
                </span>
              </template>
            </vxe-table-column>
          </vxe-grid>
        </a-checkbox-group>
      </a-spin>
    </div>
    <project-num-modal ref="projectNumModal" @ok="queryProjectNum" />
    <add-project-modal ref="addProjectModal" @ok="handleOk" />
    <set-project-modal ref="setProjectModal" @ok="handleOk" />
    <set-parent-project-modal ref="setParentProjectModal" @ok="handleOk" />
    <budget-Modal ref="budgetModal" @refreshBudget="refreshData" />
    <trial-prod-data-modal ref="trialProdDataModal" @ok="handleOk" />
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      :paramData="paramData"
      paramKey="tableField"
      title="导入项目"
      :templateName="templateName"
      ref="uploadModal"
      action="/doc/project/importProject"
      @onSuccess="success"
      @error="error"
    />
    <merge-project-modal
      ref="mergeProjectModal"
      @ok="handleMergeProjectOk"
      @clearSelectKeys="clearSelectKeys"
    />
    <move-out-project-modal ref="moveOutProjectModal" @ok="handleMoveoutProjectOk" />
    <proposal-modal ref="proposalModal" @ok="handleOk" />
  </a-card>
</template>

<script>
import { isEditStatus, isExportStatus, isMsUser, statusColor } from '@/utils/processDoc/auditStatus'
import { mapGetters } from 'vuex'
import yearMiXin from '@/utils/yearMixin'
import { UploadModal } from '@/components'
import AddProjectModal from './modules/AddProjectModal'
import SetProjectModal from './modules/SetProjectModal'
import SetParentProjectModal from './modules/SetParentProjectModal'
import ProjectNumModal from './modules/ProjectNumModal'
import BudgetModal from './modules/BudgetModal'
import TrialProdDataModal from './modules/TrialProdDataModal'
import MergeProjectModal from './modules/MergeProjectModal'
import MoveOutProjectModal from './modules/MoveOutProjectModal'
import ProposalModal from './modules/ProposalModal'
const formula = { '10': '自主完成',
  '21': '与境内研究机构合作',
  '22': '与境内高等学校合作',
  '23': '与境内其他企业或单位合作',
  '24': '与境外机构合作',
  '40': '其他形式',
  '30': '委托其他企业或单位' }
export default {
  mixins: [yearMiXin],
  name: 'Reporting',
  components: {
    UploadModal,
    AddProjectModal,
    SetProjectModal,
    SetParentProjectModal,
    ProjectNumModal,
    BudgetModal,
    TrialProdDataModal,
    MergeProjectModal,
    MoveOutProjectModal,
    ProposalModal
  },
  data () {
    return {
      scroll: { x: 1600 },
      tableField: {
        tableId: 'projectTable',
        fieldTitleObject: {
          rdStr: { title: 'RD', required: true, defaultTitle: 'RD', importField: true, sampleValue: '格式：1或2019RD01' },
          pname: { title: '项目名称', required: true, defaultTitle: '项目名称', importField: true },
          rdNumber: { title: '内部编号', required: false, defaultTitle: '内部编号', importField: true },
          // fullname: { title: '部门', required: true, defaultTitle: '部门', importField: true },
          fullname: { title: '部门', defaultTitle: '部门', importField: true },
          rdDeptName: { title: '研发部门', required: true, defaultTitle: '研发部门', importField: true },
          ename: { title: '负责人', defaultTitle: '负责人', importField: true },
          beginDate: { title: '项目开始日期', required: true, defaultTitle: '项目开始日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          endDate: { title: '项目结束日期', required: true, defaultTitle: '项目结束日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          trialProd: { title: '是否试制', defaultTitle: '是否试制', importField: true, sampleValue: '格式：是或否' },
          tBeginDate: { title: '开始试制日期', defaultTitle: '开始试制日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          tEndDate: { title: '结束试制日期', defaultTitle: '结束试制日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' }
        }
      },
      sampleData: [{
        rdStr: '格式：1或2019RD01',
        pname: '恒大都市生活广场商业综合体',
        fullname: '部门',
        rdDeptName: '/xxxx研发中心/xxxx组/',
        ename: '张三',
        beginDate: '格式："年-月-日"，例如：2019-01-01',
        endDate: '格式："年-月-日"，必须要晚于开始日期,例如：2019-12-31',
        trialProd: '格式：是或否',
        tBeginDate: '格式："年-月-日"，必须在项目起止日期内,例如：2019-03-01',
        tEndDate: '格式："年-月-日"，必须在项目起止日期内,且晚于开始试制日期,例如：2019-03-10'
      }],
      queryParams: {},
      pname: undefined,
      projectTableData: [],
      visible: false,
      formula,
      form: this.$form.createForm(this, { scroll: {} }),
      templateName: 'RD列表模板',
      paramData: { year: this.currentYear },
      existProjectNum: 0,
      planNum: -1,
      selectedRowKeys: [],
      selectedRows: [],
      rdDeptMap: {},
      highMap: {},
      isSpinning: true,
      expandKeys: undefined, // vxeTree 展开index
      modify: true,
      plan: { // 规划项目所用数据
        cnt: undefined,
        rdFee: undefined,
        revenueFcst: undefined,
        employeeAmount: undefined,
        deptIds: []
      },
      deptTree: [],
      // deptTree: null,
      statusColor
    }
  },
  computed: {
    ...mapGetters(['auditStatus', 'userInfo']),
    isMsUser () {
      return isMsUser()
    },
    countRenewalProject () {
      return this.projectTableData.filter(row => this.currentYear - row.beginYear).length
    }
  },
  created () {
    this.queryProject()
    this.$highTecIndustryTreeMap(this).then(res => { this.highMap = res })
    this.paramData.year = this.currentYear
    this.queryParams.year = this.currentYear
    this.queryProjectNum()
  },
  watch: {
    plan: {
      immediate: true,
      deep: true,
      handler () {
        this.loadTree()
      }
    }
  },
  methods: {
    isEditStatus,
    isExportStatus,
    addProject () {
      if (this.planNum !== -1 && this.planNum < this.existProjectNum) {
        this.$message.info('当前项目数已超过规划数')
      } else if (this.planNum !== -1 && this.planNum === this.existProjectNum) {
        this.$message.info('当前项目数已达到规划数')
      }
      this.$refs.addProjectModal.add(this.currentYear, this.deptTree)
    },
    search () {
      this.queryProject()
      this.paramData.year = this.currentYear
      this.queryProjectNum()
    },
    /**
     * @description:  删除项目
     * @param isMultiple {type: Boolean}
     * @param record {type: object}
     * @return null
     */
    handleDel (isMultiple, record) {
      const self = this
      this.$confirm({
        title: '是否确定删除?',
        onOk () {
          let projectIdsAry
          if (isMultiple) {
            const tempAry = []
            self.selectedRowKeys.forEach(id => {
              tempAry.push(id)
            })
            projectIdsAry = tempAry
          } else {
            projectIdsAry = [ record.id ]
          }
          return self.$http.post('/project/delete', { projectIds: projectIdsAry, currentYear: self.currentYear }).then(res => {
            if (res.success && res.data) {
              self.$message.success('删除成功')
              self.queryProject()
            } else {
              self.$message.error(`${res.errorMessage ? res.errorMessage : '删除失败'}`)
            }
          })
        },
        onCancel () {
        }
      })
    },
    selectYear () {
      this.queryProject()
    },
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          content: resData,
          onOk () {
          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
      }
      this.queryProject()
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    queryProject () {
      this.isSpinning = true
      if (!this.currentYear) {
        return
      }
      this.$http.get('/project/getList', { params: { year: this.currentYear, pname: this.pname } })
        .then(async (res) => {
          if (res.data) {
            const data = res.data
            await this.getRdDeptMap(data)
            this.projectTableData = data
          }
        }).then(() => {
          if (this.$refs.table && this.expandKeys) {
            this.$refs.table.setTreeExpansion(this.projectTableData[(this.expandKeys - 1)], true)
          }
          this.isSpinning = false
          this.$nextTick(() => {})
        })
      this.selectedRowKeys = []
      this.selectedRows = []
    },
    async getRdDeptMap (data) {
      let startYear, enYear
      for (let i = 0; i < data.length; i++) {
        const dYear = data[i].beginYear
        if (typeof startYear === 'undefined' || startYear > dYear) {
          startYear = dYear
        }
        if (typeof enYear === 'undefined' || enYear < dYear) {
          enYear = dYear
        }
      }
      for (let rYear = startYear; rYear <= enYear; rYear++) {
        // 获取研发部门数据
        await this.$getTree('rdDept', false, rYear).then((res) => {
          this.rdDeptMap = Object.assign(this.rdDeptMap, res.keyMap)
        })
        data.forEach(item => { item.rdDeptName = this.rdDeptMap[item.rdDeptId] })
      }
    },
    openUploadModal () {
      this.$refs.uploadModal.show(this.tableField)
    },
    exportProject () {
      this.isSpinning = true
      this.$exportData('/project/exportProject', { year: this.currentYear },
        `${this.userInfo.companyName}${this.currentYear}年项目列表`, this.$message).then(res => {
        this.isSpinning = false
      })
      /* this.$http.get('/project/exportProject', { params: { year: this.currentYear } })
        .then(res => {
          if (res.success) {
            const cols = {
              pname: '项目名称',
              rdTitle: 'RD',
              rdNumber: '内部编号',
              deptName: '部门',
              workshop: '车间',
              productLine: '产线',
              processSection: '工艺段',
              rdDeptName: '研发部门',
              ename: '负责人',
              beginDate: '项目开始日期',
              endDate: '项目结束日期',
              tBeginDate: '开始试制日期',
              tEndDate: '结束试制日期',
              formula: '项目开展形式',
              result: '项目当年成果形式',
              targets: '项目技术经济目标',
              projectSource: '项目来源',
              tecIndustry: '高新领域' }
            const resultMap = { '01': '论文或专著',
              '02': '新产品、新工艺等推广与示范活动',
              '03': '对已有产品、工艺等进行一般性改进',
              '04': '对已有产品、工艺等实现突破性变革',
              '05': '软件著作权',
              '06': '应用软件',
              '07': '中间件或新算法',
              '08': '基础软件',
              '09': '发明专利',
              '10': '实用新型专利或外观设计专利',
              '11': '带有技术、工艺参数的图纸、技术标准、操作规范、技术论证、研究报告、咨询评价',
              '12': '自主研制的新产品原型或样机、样件、样品、配方、新装置',
              '13': '自主开发的新技术或新工艺、新工法、新服务',
              '14': '其他' }
            const targetsMap = { '1': '科学原理的探索、发现',
              '2': '技术原理的研究',
              '3': '开发全新产品',
              '4': '增加产品功能或提高性能',
              '5': '提高劳动生产率',
              '6': '减少能源消耗或提高能源使用效率',
              '7': '节约原材料',
              '8': '减少环境污染',
              '9': '其他' }
            const projectSourceMap = {
              '1': '本企业自选项目',
              '2': '政府部门科技项目',
              '3': '其他企业（单位）委托项目',
              '4': '境外项目',
              '5': '其他项目'
            }
            var keys = []
            var sheetFilter = []
            for (const k in cols) {
              keys.push(cols[k])
              sheetFilter.push(k)
            }
            var sheetData = res.data && res.data.length ? res.data.map(item => {
              item.formula = this.getformula(item.formula)
              item.result = resultMap[item.result]
              item.targets = targetsMap[item.targets]
              item.projectSource = projectSourceMap[item.projectSource]
              if (item.tecIndustry) {
                item.tecIndustry = item.tecIndustry.split(',').map(item => { return this.highMap[item] }).join('/')
              }
              return item
            }) : []
            this.$exportJsonData(`${this.userInfo.companyName}${this.currentYear}-项目列表`, keys, sheetFilter, sheetData)
          } else {
            this.$message.error(`导出失败${res.errorMessage ? ':' + res.errorMessage : ''}`)
          }
        }).finally(res => {
          this.isSpinning = false
          // this.queryProject()
        }) */
    },
    handleEdit (record) {
      this.$refs.modal.edit(record)
    },
    handleOk (flag) {
      this.queryProject()
    },
    // 头部汇总数据
    queryProjectNum () {
      if (!this.currentYear) {
        return
      }
      this.$http.get('/report/getPlanInfo', { params: { ryear: this.currentYear } })
        .then(res => {
          if (res.data) {
            this.plan.cnt = res.data.cnt
            this.plan.rdFee = res.data.rdFee
            this.plan.revenueFcst = res.data.revenueFcst
            this.plan.employeeAmount = res.data.employeeAmount
            this.plan.deptIds = res.data.deptIds ? JSON.parse(res.data.deptIds) : []
            // this.plan.deptTree = res.data.deptTree || []
          } else {
            this.plan.cnt = undefined
            this.plan.rdFee = undefined
            this.plan.revenueFcst = undefined
            this.plan.employeeAmount = undefined
            this.plan.deptIds = []
            // this.plan.deptTree = []
          }
        })
    },
    getformula (index) {
      return formula[index]
    },
    // 显示 项目合并 模态框
    showMergeProjectModal () {
      this.$refs.mergeProjectModal.show(this.selectedRowKeys, this.getMinYearAndRdIndexProject(), this.currentYear, this.deptTree)
    },
    // 显示 项目移出 模态框
    showmoveOutProjectModal (row) {
      this.$refs.moveOutProjectModal.show(row, this.planNum, this.existProjectNum, this.currentYear)
    },
    // 显示 项目设置 模态框
    showSetProjectModal (row) {
      let deptTree = null
      this.$http.get('report/getDeptIds', { params: { year: row.beginYear } }).then(res => {
        this.$getTree('dept')
          .then(r => {
            deptTree = r.tree
          }).finally(() => {
            deptTree = this.deptFilter(deptTree, res.data)
            if (row.hasChild) {
              this.$refs.setParentProjectModal.showModal(row, this.modify, deptTree)
            } else {
              this.$refs.setProjectModal.showModal(row, this.modify, deptTree)
            }
          })
      }).finally(() => {

      })
    },
    // 合并项目后刷新表格
    handleMergeProjectOk () {
      this.queryProject()
    },
    // 删除项目后刷新表格
    handleMoveoutProjectOk () {
      this.queryProject()
    },
    // 处理项目RD显示
    handleRD (record) {
      if (record.parentId > 0) {
        return '  ' + record.seq
      } else {
        return '  ' + record.rdTitle
      }
    },
    // 获取最小年份
    getMinYear (ary) {
      return Math.min(...ary)
    },
    // 获取复选框选中的最小年且最小RdIndex的项目
    getMinYearAndRdIndexProject () {
      return this.selectedRows.reduce((accumulator, current) => {
        if (accumulator.beginYear > current.beginYear) {
          return current
        } else if (accumulator.beginYear === current.beginYear) {
          if (accumulator.rdIndex > current.rdIndex) {
            return current
          }
          return accumulator
        }
        return accumulator
      })
    },
    // Reduce计算函数
    getMinRdIndex (accumulator, currentValue) {
      if (accumulator.beginYear > currentValue.beginYear) {
        return currentValue
      } else if (accumulator.beginYear === currentValue.beginYear) {
        if (accumulator.rdIndex > currentValue.rdIndex) {
          return currentValue
        }
        return accumulator
      }
      return accumulator
    },
    // 复选框Change事件
    checkBoxGroup (checkedValue) {
      this.selectedRows = checkedValue
      this.selectedRowKeys = this.selectedRows.map((item) => { return item.id })
    },
    // vxe表格添加类方法
    cellClassName ({ row, rowIndex, column, columnIndex }) {
      if (row.hasChild) {
        return 'col-parent'
      }
    },
    // 点击vxe表格cell的事件
    cellClickEvent (event) {
      if (event.level === 0) {
        this.expandKeys = event.seq
      } else {
        this.expandKeys = event.$seq
      }
    },
    clearSelectKeys () {
      this.selectedRows = []
      this.selectedRowKeys = []
    },
    refreshData (row, data) {
      Object.assign(row, data)
    },
    formatBugdet (row) {
      if (row.estimateExpense) {
        return row.estimateExpense.toFixed(2)
      } else {
        return '--'
      }
    },
    // 汇总预算
    sumBugdet (rows) {
      if (!rows) {
        return
      }
      let total = 0
      for (let i = 0; i < rows.length; i++) {
        const d = rows[i]
        if (d.hasChild && d.children) {
          for (let j = 0; j < d.children.length; j++) {
            if (d.children[j].estimateExpense) {
              total += d.children[j].estimateExpense
            }
          }
        } else if (!d.hasChild) {
          if (d.estimateExpense) {
            total += d.estimateExpense
          }
        }
      }
      return (total).toFixed(2)
    },
    popCancel () {
      // this.selectedRowKeys = []
      // this.selectedRows = []
    },
    onSubmit () {

    },
    commitAudit () {
      const self = this
      this.$confirm({
        title: '是否确定提交?',
        onOk () {
          const postData = {
            projectIds: self.selectedRowKeys,
            moduleId: 4
          }
          self.isSpinning = true
          return self.$http.post('/projectAudit/submitProjectAudit ', postData).then((res) => {
            if (res.data && res.success) {
              self.$message.success('操作成功')
              self.queryProject()
            } else {
              self.$message.error(res.errorMessage)
            }
          }).catch((error) => {
            self.$message.error(error.message)
          }).finally(() => {
            self.isSpinning = false
          })
        },
        onCancel () {
        }
      })
    },
    loadTree (deptIds = this.plan.deptIds) {
      this.$getTree('dept')
        .then(res => {
          this.deptTree = res.tree
        }).finally(() => {
          this.deptTree = this.deptFilter(this.deptTree, deptIds)
        })
    },
    deptFilter (deptTree = [], deptIds = []) {
      let tree = []
      if (deptIds === null) {
        deptIds = []
      }
      deptTree.forEach(elem => {
        if (deptIds.includes(elem.value)) {
          tree = tree.concat(elem)
        } else {
          if (elem.children && elem.children.length !== 0) {
            tree = tree.concat(this.deptFilter(elem.children, deptIds))
          }
        }
      })
      return tree
    }
  }
}
</script>
<style lang="less" scoped>
#wrapperTable {
  height: 97%;
  overflow: auto;
}
#wrapperTable {
  & /deep/ .vxe-tree--btn-wrapper {
    font-size: 0px;
  }
  & /deep/ .vxe-tree-cell {
    padding: 0px;
  }
  & /deep/ .ant-spin-container {
    height: 100%;
  }
}
</style>
