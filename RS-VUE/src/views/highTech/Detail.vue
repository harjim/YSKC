<template>
  <a-card :bordered="false">
    <template slot="title">
      <a-button
        v-if="control.allot"
        type="primary"
        @click="visible.allot = true"
        :disabled="selectedRow.length === 0"
      >分配人员</a-button>
    </template>

    <vxe-grid
      border
      size="small"
      row-id="projectId"
      :data="tableData"
      :loading="load.table"
      highlight-hover-row
      stripe
      cell-class-name="detailDefault"
      show-overflow="title"
      show-header-overflow="title"
      :checkbox-config="{
        labelField: 'companyName',
        highlight: true
      }"
      @checkbox-all="selectChangeEvent"
      @checkbox-change="selectChangeEvent"
    >
      <vxe-table-column title="公司" type="checkbox" :width="200" fixed="left" resizable />
      <vxe-table-column title="项目名称" field="pname" :width="140" fixed="left" resizable />
      <vxe-table-column title="项目编号" field="rdTitle" :width="140" fixed="left" resizable />
      <vxe-table-column title="EAS编号" field="rdNumber" :width="140" resizable />
      <vxe-table-column title="优赛技术" field="ysTech" :width="140" resizable />
      <vxe-table-column title="优赛财务" field="ysFina" :width="140" resizable />
      <vxe-table-column title="审核" align="center">
        <vxe-table-column title="工厂技术" field="ftyTech" :width="120" resizable />
      </vxe-table-column>
      <vxe-table-column title="批准" align="center">
        <vxe-table-column title="运行总监" field="areaTech" :width="120" resizable />
      </vxe-table-column>
      <vxe-table-column title="审核" align="center">
        <vxe-table-column title="工厂财务" field="ftyFina" :width="120" resizable />
      </vxe-table-column>
      <vxe-table-column title="批准" align="center">
        <vxe-table-column title="财务经理" field="areaFina" :width="120" resizable />
      </vxe-table-column>
      <vxe-table-column title="立项材料" field="pApplication" :width="140" resizable>
        <template slot-scope="{ row }">
          <a
            v-if="control.techView"
            @click="handleClickTech(row.pApplication, row.rdTitle)"
          >{{ handleRenderText(row.pApplication, null) }}</a>
          <span v-else>{{ handleRenderText(row.pApplication, null) }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column v-for="n in 12" :key="n" :title="`${n}月轨迹材料`" align="center">
        <vxe-table-column title="技术部分" :width="140" resizable>
          <template slot-scope="{ row }">
            <a
              v-if="control.techView"

              @click="handleClickTech(row.techNodeMap[n], row.rdTitle)"
            >{{ handleRenderText(row.techNodeMap, n) }}</a>
            <span v-else>{{ handleRenderText(row.techNodeMap, n) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="财务部分" :width="140" resizable>
          <template slot-scope="{ row }">
            <a
              v-if="control.finaView"
              @click="handleClickFina(row.finaNodeMap[n], row.rdTitle)"
            >{{ handleRenderText(row.finaNodeMap, n) }}</a>
            <span v-else>{{ handleRenderText(row.finaNodeMap, n) }}</span>
          </template>
        </vxe-table-column>
      </vxe-table-column>
      <vxe-table-column title="验收材料" field="pAccept" :width="140" resizable >
        <template slot-scope="{ row }">
          <a
            v-if="control.techView"
            @click="handleClickTech(row.pAccept, row.rdTitle)"
          >{{ handleRenderText(row.pAccept, null) }}</a>
          <span v-else>{{ handleRenderText(row.pAccept, null) }}</span>
        </template>
      </vxe-table-column>
    </vxe-grid>
    <vxe-pager
      :current-page="tablePage.currentPage"
      :page-size="tablePage.pageSize"
      :total="tablePage.totalCount"
      :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
      :loading="load.table"
      @page-change="handlePageChange"
    />

    <vxe-modal v-model="visible.allot" title="分配人员" destroy-on-close>
      <template v-slot>
        <vxe-form
          title-colon
          title-asterisk
          ref="allotForm"
          title-align="right"
          title-width="100"
          size="small"
          :data="allotForm"
          :loading="load.allot"
          @submit="handleAllotSubmit"
          @reset="handleAllotReset"
        >
          <vxe-form-item title="工厂技术" field="ftyTech" span="24">
            <template v-slot="scope">
              <vxe-input
                v-model="allotForm.ftyTech"
                type="txt"
                placeholder="请输入工厂技术人员"
                clearable
                @change="$refs.allotForm.updateStatus(scope)"
              />
            </template>
          </vxe-form-item>
          <vxe-form-item title="工厂财务" field="ftyFina" span="24">
            <template v-slot="scope">
              <vxe-input
                v-model="allotForm.ftyFina"
                type="txt"
                placeholder="请输入工厂财务人员"
                clearable
                @change="$refs.allotForm.updateStatus(scope)"
              />
            </template>
          </vxe-form-item>
          <vxe-form-item title="运行总监" field="areaTech" span="24">
            <template v-slot="scope">
              <vxe-input
                v-model="allotForm.areaTech"
                type="txt"
                placeholder="请输入工厂技术人员"
                clearable
                @change="$refs.allotForm.updateStatus(scope)"
              />
            </template>
          </vxe-form-item>
          <vxe-form-item title="财务经理" field="areaFina" span="24">
            <template v-slot="scope">
              <vxe-input
                v-model="allotForm.areaFina"
                type="txt"
                placeholder="请输入工厂技术人员"
                clearable
                @change="$refs.allotForm.updateStatus(scope)"
              />
            </template>
          </vxe-form-item>
          <vxe-form-item align="right" span="24">
            <template v-slot>
              <vxe-button type="reset">重置</vxe-button>
              <vxe-button type="submit" status="primary">提交</vxe-button>
            </template>
          </vxe-form-item>
        </vxe-form>
      </template>
    </vxe-modal>

    <detail-drawer
      :visible="visible.drawer"
      :isTech="drawerProps.isTech"
      :title="drawerProps.title"
      :previewHtml="drawerProps.previewHtml"
      :filesList="drawerProps.filesList"
      :logList="drawerProps.logList"
      :fileTabKey="drawerProps.fileTabKey"
      :auditTabKey="drawerProps.auditTabKey"
      :spinning="drawerProps.spinning"
      :isShowAudit="drawerProps.isShowAudit"
      :tableColumns="drawerProps.columns"
      :tableData="drawerProps.finaCurrentFileData"
      :tablePager="drawerProps.tablePager"
      @close="handleCloseDrawer"
      @tabChange="handleTabChange"
      @submitAudit="submitAudit"
      @changeAuditTabKey="handleChangeAuditTabKey"
      @pagerChange="handleTablePagerChange"
    />
  </a-card>
</template>

<script>
import { mapGetters } from 'vuex'
import DetailDrawer from './modules/DetailDrawer'
import { equipmentsColumns, energiesColumns, inspectionColumns, assistColumns } from './modules/columns'

const finaFilesList = [
  { docName: '工资明细表', id: 0, url: '/projectRdEmployee/getFeeDetail' },
  { docName: '五险一金明细表', id: 1, url: '/projectRdEmployee/getFeeDetail' },
  { docName: '设备折旧明细表', id: 2, url: '/projectRdEquipment/getEquipments' },
  { docName: '研发试制明细表', id: 3, url: '/projectEnergy/getEnergies' },
  { docName: '研发动力明细表', id: 4, url: '/projectEnergy/getEnergies' },
  { docName: '其他费用明细表', id: 5, url: 'projectInspection/getProjectInspectionList' },
  { docName: '月度研发支出辅助账及汇总表', id: 6, url: 'highTechProgress/getAssistData' }
]
const tableColumns = {
  2: equipmentsColumns,
  3: energiesColumns,
  4: energiesColumns,
  5: inspectionColumns,
  6: assistColumns
}
const defaultAllotValue = {
  ftyTech: '',
  ftyFina: '',
  areaTech: '',
  areaFina: ''
}
const defaultDrawerProps = {
  title: '', // 标题
  spinning: false, // 抽屉加载
  isShowAudit: false, // 有无审核权限
  isTech: true,
  filesList: [],
  logList: [], // 日志列表
  previewHtml: '', // 文档HTML
  fileTabKey: undefined, // 当前点击文件
  auditTabKey: 'audit', // 审核、日志
  month: '', // 月份
  projectId: '', // 项目ID
  deliverType: '',
  rdTitle: '',
  node: '', // 阶段
  columns: [], // 归集表列配置
  finaCurrentFileData: [], // 归集表数据
  tablePager: { // 归集表分页
    currentPage: 1,
    pageSize: 10,
    total: 0
  }
}

export default {
  name: 'HighTechDetail',
  components: {
    DetailDrawer
  },
  data () {
    return {
      control: {
        allot: this.$auth('highTech:detail:allot'),
        techView: this.$auth('highTech:detail:techView'),
        finaView: this.$auth('highTech:detail:finaView'),
        techAudit: this.$auth('highTech:detail:techAudit'),
        finaAudit: this.$auth('highTech:detail:finaAudit')
      },
      tablePage: {
        currentPage: 1,
        pageSize: 10,
        totalCount: 0
      },
      load: {
        table: false,
        allot: false
      },
      visible: {
        allot: false,
        drawer: false
      },
      tableData: [],
      selectedRow: [],
      allotForm: {
        ...defaultAllotValue
      },
      drawerProps: {
        ...defaultDrawerProps
      }
    }
  },
  computed: {
    ...mapGetters(['currentYear', 'userInfo'])
  },
  watch: {
    currentYear () {
      this.handleGetList()
    }
  },
  created () {
    this.handleGetList()
  },
  methods: {
    handleGetList () { // 获取列表
      this.load.table = true
      this.selectedRow = []
      this.$http.get('highTechProgress/getList', {
        params: {
          year: this.currentYear,
          pageNo: this.tablePage.currentPage,
          pageSize: this.tablePage.pageSize
        }
      }).then(({ data }) => {
        this.tablePage.totalCount = data && data.totalCount
        this.tableData = data.data
      }).finally(() => {
        this.load.table = false
      })
    },
    resultTitle (status, node) { // 阶段文本
      if ((!status && !node) || status === 2) {
        return '优赛准备中'
      } else if (status === 0 && node !== null) {
        if (node === 0) return '优赛准备中'
        else if (node === 10) return '工厂审核中'
        else if (node === 20) return '区域审核中'
      } else if (status === 1) return '已完成'
    },
    handleRenderText (r, v) { // 渲染文本
      if (r) {
        if (r[v]) return this.resultTitle(r[v].status, r[v].node)
      }
      return ''
    },
    handlePageChange ({ currentPage, pageSize }) { // 表格分页
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.handleGetList(currentPage)
    },
    selectChangeEvent ({ records }) { // 表格选中
      const result = []
      for (let i = 0; i < records.length; i++) {
        result.push(records[i].projectId)
      }
      this.selectedRow = result
    },
    handleAllotReset () { // 分配表单重置
      this.allotForm = {
        ...defaultAllotValue
      }
    },
    handleAllotSubmit () { // 分配表单提交
      this.load.allot = true
      this.$http.post('highTechProgress/allot', {
        projectIds: this.selectedRow,
        year: this.currentYear,
        ...this.allotForm
      }).then(({ data, errorMessage }) => {
        if (data) this.handleGetList(this.tablePage.currentPage)
        else this.$message.error(errorMessage)
      }).finally(() => {
        this.load.allot = false
        this.visible.allot = false
        this.handleAllotReset()
      })
    },
    handleClickTech ({ deliverType, projectId, month, node, status }, title) { // 点击某个月轨迹材料技术部分
      this.drawerProps.spinning = true
      this.visible.drawer = true
      this.handleUpdateDrawerProps({
        title,
        deliverType,
        projectId,
        month,
        node,
        status,
        type: 0
      })
      this.$http.get('projectDocFile/getHighTechFiles', {
        params: {
          deliverType,
          projectId,
          month
        }
      }).then(({ data }) => {
        this.drawerProps.filesList = data

        if (!this.drawerProps.isShowAudit || this.drawerProps.filesList.length === 0) {
          this.drawerProps.auditTabKey = 'log'
          this.getLogList('log')
        }
        if (data.length > 0) {
          this.drawerProps.fileTabKey = data[0].id
          this.handleGetPreviewFile(data[0])
        }
      }).finally(() => {
        setTimeout(() => {
          this.drawerProps.spinning = false
        }, 500)
      })
    },
    handleClickFina ({ deliverType, projectId, month, node, status }, title) { // 点击某个月轨迹材料的财务部分
      this.drawerProps.spinning = true
      this.drawerProps.isTech = false
      this.drawerProps.fileTabKey = 0
      this.drawerProps.filesList = finaFilesList
      this.visible.drawer = true
      this.drawerProps.tablePager.currentPage = 1
      this.handleUpdateDrawerProps({
        title,
        deliverType,
        projectId,
        month,
        node,
        status,
        type: 1
      })
      this.getTypeConfig()
    },
    handleUpdateDrawerProps ({ title, month, projectId, deliverType, node, status, type }) {
      this.drawerProps.title = `${title}`
      this.drawerProps.month = month
      this.drawerProps.projectId = projectId
      this.drawerProps.deliverType = deliverType
      this.drawerProps.node = node
      this.drawerProps.rdTitle = title
      this.drawerProps.isShowAudit = ((type === 0 && this.control.techAudit) || (type === 1 && this.control.finaAudit)) && this.handleIsShowAudit(status, node, this.userInfo.userSource, this.userInfo.roleType)
    },
    handleCloseDrawer () { // 关闭抽屉
      this.visible.drawer = false
      this.drawerProps = {
        ...defaultDrawerProps
      }
    },
    handleGetPreviewFile (data) { // 获取文档预览
      this.$http.get('projectDocFileData/previewFile', {
        params: {
          pDocFileId: data.id,
          projectId: data.projectId,
          currentYear: this.currentYear,
          companyId: this.userInfo.companyId
        }
      }).then(({ data, success, errorMessage }) => {
        if (success) {
          this.drawerProps.previewHtml = data
        } else {
          this.drawerProps.previewHtml = `<div style="font-size: 1.5em;font-weight:bold;>${errorMessage}</div>`
        }
      }).catch(({ message }) => {
        this.$message.error('请求接口错误！', message)
      })
    },
    handleTabChange (key) { // 点击抽屉顶部文件列表
      this.drawerProps.fileTabKey = key
      this.drawerProps.spinning = true
      const { filesList, isTech } = this.drawerProps
      filesList.forEach(item => {
        if (item.id === key) {
          if (isTech) {
            this.handleGetPreviewFile(item)
            setTimeout(() => {
              this.drawerProps.spinning = false
            }, 500)
          } else {
            this.drawerProps.tablePager.currentPage = 1
            if (key === 0 || key === 1) {
              this.getTypeConfig()
            } else {
              this.getFinaTable()
            }
          }
        }
      })
    },
    handleIsShowAudit (status, node, userSource, roleType) { // 是否具备审核权限
      if ((status === 0 || status === 2 || status === null) && node === 0 && userSource === 1) return true
      if (status === 0 && node === 10 && roleType === 0 && userSource === 0) return true
      if (status === 0 && node === 20 && roleType === 1 && userSource === 0) return true
      return false
    },
    submitAudit ({ suggestion, status }) { // 审核提交
      const params = {
        companyId: this.userInfo.companyId,
        projectId: this.drawerProps.projectId,
        month: this.drawerProps.month,
        deliverType: this.drawerProps.deliverType,
        node: this.drawerProps.node,
        suggestion,
        status
      }
      this.$http.post('/highTechProgress/submitAudit', params).then(({ success, errorMessage }) => {
        if (success) {
          this.$message.success('操作成功！')
          this.handleGetList(this.tablePage.currentPage)
        } else {
          this.$message.error(errorMessage)
        }
      }).catch(e => this.$message.error(e.message))
    },
    handleChangeAuditTabKey (key) {
      this.drawerProps.auditTabKey = key
      if (key === 'log') this.getLogList()
    },
    getLogList () { // 获取日志列表
      const params = {
        companyId: this.userInfo.companyId,
        projectId: this.drawerProps.projectId,
        deliverType: this.drawerProps.deliverType,
        month: this.drawerProps.month
      }
      this.$http.get('highTechProgress/getLogList', {
        params
      }).then(({ data, success, errorMessage }) => {
        if (success) {
          this.drawerProps.logList = data
        } else {
          this.$message.error(errorMessage)
        }
      }).catch(e => this.$message.error(e.message))
    },
    getFinaTable () { // 获取归集数据
      this.visible.drawer = true
      this.$http.get(this.drawerProps.filesList[this.drawerProps.fileTabKey].url, {
        params: {
          companyId: this.userInfo.companyId,
          year: this.currentYear,
          projectId: this.drawerProps.projectId,
          month: this.drawerProps.month,
          rdTitle: this.drawerProps.rdTitle,
          etype: this.drawerProps.fileTabKey === 3 ? 20302 : (this.drawerProps.fileTabKey === 4 ? 20100 : null),
          types: this.drawerProps.fileTabKey === 5 && [69900, 60000, 60100, 60200, 60300],
          pageNo: this.drawerProps.tablePager.currentPage,
          pageSize: this.drawerProps.tablePager.pageSize
        }
      }).then(({ data }) => {
        this.drawerProps.columns = tableColumns[this.drawerProps.fileTabKey]
        this.drawerProps.finaCurrentFileData = data.data
        this.drawerProps.tablePager.total = data.totalCount
        if (!this.drawerProps.isShowAudit) {
          this.drawerProps.auditTabKey = 'log'
          this.getLogList('log')
        }
      }).finally(() => {
        this.drawerProps.spinning = false
      })
    },
    handleTablePagerChange ({ currentPage, pageSize }) {
      this.drawerProps.spinning = true
      this.drawerProps.tablePager.pageSize = pageSize
      this.drawerProps.tablePager.currentPage = currentPage
      this.getFinaTable()
    },
    getTypeConfig () { // 获取配置
      this.$http.get('/fieldConfig/getTypeConfig', {
        params: {
          companyId: this.userInfo.companyId,
          type: this.drawerProps.fileTabKey
        }
      }).then(({ data, success, errorMessage }) => {
        if (success) {
          const config = JSON.parse(data.config)
          const columns = [{
            title: '费用类型',
            field: 'accountName'
          }]
          for (let i = 0; i < config.length; i++) {
            columns.push({
              title: config[i]['alias'],
              field: `feeDetail.${config[i]['name']}`
            })
          }
          columns.push({ title: this.drawerProps.fileTabKey ? '研发五险一金' : '研发工资', field: 'rdFunds' })
          this.drawerProps.columns = columns
          this.$http.get('projectRdEmployee/getFeeDetail', {
            params: {
              companyId: this.userInfo.companyId,
              projectId: this.drawerProps.projectId,
              month: this.drawerProps.month,
              type: this.drawerProps.fileTabKey
            }
          }).then(({ data, success, errorMessage }) => {
            if (success) {
              this.drawerProps.finaCurrentFileData = data
              if (!this.drawerProps.isShowAudit) {
                this.drawerProps.auditTabKey = 'log'
                this.getLogList('log')
              }
            } else {
              this.$message.error(errorMessage)
            }
          }).finally(() => {
            this.drawerProps.spinning = false
          })
        } else {
          this.$message.error(errorMessage)
        }
      }).catch(e => this.$message.error(e.message))
    }
  }
}

</script>

<style lang="less" scoped>
  /deep/ .detailDefault:hover {
    cursor: default;
  }
  // /deep/ .detailHasDrawer:hover {
  //   color: #1890ff;
  //   cursor: pointer;
  // }
</style>
