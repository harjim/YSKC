<template>
  <a-drawer
    :visible="visible"
    :title="title"
    :bodyStyle="{
      height: 'calc(100% - 55px)',
      padding: 12,
      overflow: 'auto',
    }"
    @close="onClose"
    width="1000"
    destroyOnClose
  >
    <a-spin :spinning="spinning">
      <div style="height: 100%">
        <ListDrawer
          :fileTabKey="fileTabKey"
          :filesList="filesList"
          :isTech="isTech"
          :tableData="finaCurrentFileData"
          :tableColumns="columns"
          :tablePager="tablePager"
          :previewHtml="previewHtml"
          @handleTabChange="handleTabChange"
          @handleChangePager="changePager"
        />
      </div>
      <ReviewDrawer
        ref="reviewDrawer"
        :getLogParams="{
          companyId: userInfo.companyId,
          projectId: currentNode.projectId,
          deliverType: currentNode.deliverType,
          month: currentNode.month
        }"
        :isAudit="isShowAudit && filesList.length > 0"
        :submitParams="{
          companyId: userInfo.companyId,
          projectId: currentNode.projectId,
          month: currentNode.month,
          deliverType: currentNode.deliverType,
          node: currentNode.node,
        }"
        @close="onClose"
      />
    </a-spin>
  </a-drawer>
</template>

<script>
import { mapGetters } from 'vuex'
import ListDrawer from './ListDrawer.vue'
import { assistColumns, energiesColumns, equipmentsColumns, inspectionColumns } from './columns'
import ReviewDrawer from './ReviewDrawer.vue'

const tableColumns = {
  2: equipmentsColumns,
  3: energiesColumns,
  4: energiesColumns,
  5: inspectionColumns,
  6: assistColumns
}
const finaFilesList = [
  { docName: '工资明细表', id: 0, url: '/projectRdEmployee/getFeeDetail' },
  { docName: '五险一金明细表', id: 1, url: '/projectRdEmployee/getFeeDetail' },
  { docName: '设备折旧明细表', id: 2, url: '/projectRdEquipment/getEquipments' },
  { docName: '研发试制明细表', id: 3, url: '/projectEnergy/getEnergies' },
  { docName: '研发动力明细表', id: 4, url: '/projectEnergy/getEnergies' },
  { docName: '其他费用明细表', id: 5, url: 'projectInspection/getProjectInspectionList' },
  { docName: '月度研发支出辅助账及汇总表', id: 6, url: 'highTechProgress/getAssistData' }
]

export default {
  name: 'DetailDrawer',
  components: {
    ListDrawer,
    ReviewDrawer
  },
  props: {
    title: {
      type: String,
      default: ''
    },
    currentNode: {
      type: Object,
      default: () => { }
    },
    visible: {
      type: Boolean,
      default: false
    },
    currentYear: {
      type: Number,
      default: () => new Date().getFullYear()
    },
    isTech: {
      type: Boolean,
      default: true
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
    isShowAudit () {
      return ((this.isTech && this.control.techAudit) || (!this.isTech && this.control.finaAudit)) && this.handleIsShowAudit(this.currentNode.status, this.currentNode.node, this.userInfo.userSource, this.userInfo.roleType)
    }
  },
  data () {
    return {
      spinning: false,
      filesList: [],
      columns: [],
      finaCurrentFileData: [],
      fileTabKey: undefined,
      previewHtml: '',
      tablePager: { // 归集表分页
        currentPage: 1,
        pageSize: 10,
        total: 0
      },
      control: {
        allot: this.$auth('deliver:detail:allot'),
        techView: this.$auth('deliver:detail:techView'),
        finaView: this.$auth('deliver:detail:finaView'),
        techAudit: this.$auth('deliver:detail:techAudit'),
        finaAudit: this.$auth('deliver:detail:finaAudit')
      }
    }
  },
  watch: {
    visible (val) {
      if (val) this.onGetTechOrFina()
    }
  },
  methods: {
    onGetTechOrFina () {
      this.spinning = true
      if (this.isTech) {
        this.getTechDetail()
      } else {
        this.getFinaDetail()
      }
    },
    handleIsShowAudit (status, node, userSource, roleType) { // 是否具备审核权限
      if ((status === 0 || status === 2 || status === null) && node === 0 && userSource === 1) return true
      if (status === 0 && node === 10 && roleType === 0 && userSource === 0) return true
      return status === 0 && node === 20 && roleType === 1 && userSource === 0
    },
    getTechDetail () { // 获取技术评审详情
      this.$http.get('projectDocFile/getHighTechFiles', {
        params: {
          deliverType: this.currentNode.deliverType,
          projectId: this.currentNode.projectId,
          month: this.currentNode.month
        }
      }).then(({ data, success, errorMessage }) => {
        if (success) {
          this.filesList = data
          if (!this.isShowAudit || data.length === 0) {
            this.$refs.reviewDrawer.onChangeTabKey('log')
          }
          if (data.length > 0) {
            this.fileTabKey = data[0].id
            this.getPreviewFile(data[0])
          }
        } else {
          this.$message.error(errorMessage)
        }
      })
    },
    getFinaDetail () {
      this.fileTabKey = 0
      this.filesList = finaFilesList
      this.tablePager.currentPage = 1
      this.getTypeConfig()
    },
    getTypeConfig () { // 获取配置
      this.$http.get('/fieldConfig/getTypeConfig', {
        params: {
          companyId: this.userInfo.companyId,
          type: this.fileTabKey
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
          columns.push({ title: this.fileTabKey ? '研发五险一金' : '研发工资', field: 'rdFunds' })
          this.columns = columns
          this.$http.get('projectRdEmployee/getFeeDetail', {
            params: {
              companyId: this.userInfo.companyId,
              projectId: this.currentNode.projectId,
              month: this.currentNode.month,
              type: this.fileTabKey
            }
          }).then(({ data, success, errorMessage }) => {
            if (success) {
              this.finaCurrentFileData = data
              if (!this.isShowAudit) {
                this.$refs.reviewDrawer.onChangeTabKey('log')
              }
            } else {
              this.$message.error(errorMessage)
            }
          })
        } else {
          this.$message.error(errorMessage)
        }
      }).catch(e => this.$message.error(e.message)).finally(() => {
        this.spinning = false
      })
    },
    getPreviewFile (data) { // 获取文档预览
      this.$http.get('projectDocFileData/previewFile', {
        params: {
          pDocFileId: data.id,
          projectId: data.projectId,
          currentYear: this.currentYear,
          companyId: this.userInfo.companyId
        }
      }).then(({ data, success, errorMessage }) => {
        if (success) {
          this.previewHtml = data
        } else {
          this.previewHtml = `<div style="font-size: 1.5em;font-weight:bold;>${errorMessage}</div>`
        }
      }).catch(({ message }) => {
        this.$message.error('请求接口错误！', message)
      }).finally(() => {
        this.spinning = false
      })
    },
    onClose () {
      this.$refs.reviewDrawer.form.resetFields()
      this.$emit('update:visible', false)
      this.$emit('handleGetList')
      Object.assign(this.$data, this.$options.data.call(this))
    },
    handleTabChange (key) { // 点击抽屉顶部文件列表
      this.fileTabKey = key
      this.spinning = true
      this.filesList.forEach(item => {
        if (item.id === key) {
          if (this.isTech) {
            this.getPreviewFile(item)
          } else {
            this.tablePager.currentPage = 1
            if (key === 0 || key === 1) {
              this.getTypeConfig()
            } else {
              this.getFinaTable()
            }
          }
        }
      })
    },
    getFinaTable () { // 获取归集数据
      this.$http.get(this.filesList[this.fileTabKey].url, {
        params: {
          companyId: this.userInfo.companyId,
          year: this.currentYear,
          projectId: this.currentNode.projectId,
          month: this.currentNode.month,
          rdTitle: this.title,
          etype: this.fileTabKey === 3 ? 20302 : (this.fileTabKey === 4 ? 20100 : null),
          types: this.fileTabKey === 5 && [69900, 60000, 60100, 60200, 60300],
          pageNo: this.tablePager.currentPage,
          pageSize: this.tablePager.pageSize
        }
      }).then(({ data, success, errorMessage }) => {
        if (success) {
          this.columns = tableColumns[this.fileTabKey]
          this.finaCurrentFileData = data.data
          this.tablePager.total = data.totalCount
          if (!this.isShowAudit) {
            this.$refs.reviewDrawer.onChangeTabKey('log')
          }
        } else {
          this.$message.error(errorMessage)
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    changePager ({ currentPage, pageSize }) {
      this.spinning = true
      this.tablePager.pageSize = pageSize
      this.tablePager.currentPage = currentPage
      this.getFinaTable()
    }
  }
}
</script>
