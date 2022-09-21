<template>
  <a-drawer
    :visible="visible"
    :title="title"
    :bodyStyle="{
      height: 'calc(100% - 55px)',
      padding: 12,
      overflow: 'auto',
    }"
    width="1200"
    destroyOnClose
    @close="onClose"
  >
    <a-spin :spinning="spinning">
      <div style="height: 100%">
        <a-row :gutter="[0, 16]">
          <a-col :span="4">
            <a-checkbox
              :indeterminate="indeterminate"
              :checked="isAllChecked"
              :disabled="len === 0"
              @change="e => onAllCheck(e.target.checked)"
            >全选</a-checkbox>
            <a-button
              size="small"
              style="float: right;"
              type="primary"
              :disabled="checkedKeys.length <2"
              @click="auditsModal = true"
            >批量审核</a-button>
            <a-tree
              defaultExpandAll
              checkable
              :selectedKeys="selected"
              :checkedKeys="checkedKeys"
              @select="selectTreeNode"
              @check="checkTreeNode"
            >
              <a-tree-node
                disabled
                v-for="item in treeData"
                :title="item.rdTitle"
                :key="item.id"
                :disableCheckbox="item.disableCheckbox"
              >
                <a-tree-node
                  :v-if="item.children"
                  v-for="child in item.children"
                  :title="child.rdTitle"
                  :key="child.id"
                  :deliverType="child.deliverType"
                  :deliverId="child.deliverId"
                  :node="child.node"
                  :status="child.status"
                  :isShowAudit="child.disableCheckbox"
                  :disableCheckbox="child.disableCheckbox"
                />
              </a-tree-node>
            </a-tree>
          </a-col>
          <a-col :span="20">
            <div style="height: 100%">
              <ListDrawer
                :fileTabKey="fileTabKey"
                :filesList="filesList"
                :isTech="isTech"
                :tableData="finaCurrentFileData"
                :tableColumns="columns"
                :tablePager="tablePager"
                :previewHtml="previewHtml"
                @handleTabChange="tabChange"
                @handleChangePager="changePager"
              />
            </div>
            <ReviewDrawer
              ref="reviewDrawer"
              :getLogParams="{
                companyId: userInfo.companyId,
                projectId: projectId,
                deliverType: data.deliverType,
                month: data.month
              }"
              :isAudit="isShowAudit && filesList.length > 0"
              :submitParams="{
                companyId: userInfo.companyId,
                projectId: projectId,
                month: data.month,
                deliverType: data.deliverType,
                node: node,
              }"
              @close="onClose"
            />
          </a-col>
        </a-row>
      </div>

      <audits-submit-modal
        :visible="auditsModal"
        :ids="checkedNodes"
        @close="closeModal"
        @refresh="getDetailList"
      />
    </a-spin>
  </a-drawer>
</template>

<script>
import { mapGetters } from 'vuex'
import ListDrawer from './ListDrawer.vue'
import ReviewDrawer from './ReviewDrawer.vue'
import AuditsSubmitModal from './AuditsSubmitModal.vue'
import {
  assistColumns,
  energiesColumns,
  equipmentsColumns,
  inspectionColumns
} from './columns'

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
  name: 'StatisticsDrawer',
  components: {
    ListDrawer,
    ReviewDrawer,
    AuditsSubmitModal
  },
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: ''
    },
    isTech: {
      type: Boolean,
      default: false
    },
    data: {
      type: Object,
      default: () => ({})
    },
    rowIndex: {
      type: Number,
      default: 0
    },
    type: {
      type: Number,
      default: 1
    },
    currentYear: {
      type: Number,
      default: () => new Date().getFullYear()
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
    indeterminate () {
      return !!this.checkedKeys.length && this.checkedKeys.length < this.len
    },
    len () {
      let len = 0
      this.treeData.forEach(item => {
        item.children.forEach(node => {
          if (!node.disableCheckbox) len++
        })
      })
      return len
    }
  },
  watch: {
    visible (val) {
      if (val) this.getDetailList()
    }
  },
  data () {
    return {
      spinning: false,
      isShowAudit: true,
      treeData: [],
      filesList: [],
      columns: [],
      finaCurrentFileData: [],
      fileTabKey: undefined,
      previewHtml: '',
      projectId: undefined,
      node: 0,
      selected: [],
      checkedKeys: [],
      checkedNodes: [],
      isAllChecked: false,
      auditsModal: false,
      tablePager: { // 归集表分页
        currentPage: 1,
        pageSize: 10,
        total: 0
      }
    }
  },
  methods: {
    getDetailList () {
      this.spinning = true
      this.$http.get('highTechProgress/getDetailList', {
        params: {
          month: this.data.month,
          deliverType: this.data.deliverType,
          year: this.currentYear,
          node: this.data.node,
          audit: this.data.audit
        }
      }).then(({ data, success, errorMessage }) => {
        if (success) {
          const treeData = []
          Object.keys(data).forEach((item, idx) => {
            const ele = {
              rdTitle: item,
              id: item,
              children: data[item],
              disableCheckbox: false
            }
            if (this.rowIndex === 0) { // 优赛
              ele.disableCheckbox = this.userInfo.userSource === 0
              data[item].forEach(node => {
                node.disableCheckbox = this.userInfo.userSource === 0
              })
            } else if (this.rowIndex === 2) { // 工厂
              ele.disableCheckbox = this.userInfo.userSource !== 0 || this.userInfo.roleType !== 0
              data[item].forEach(node => {
                node.disableCheckbox = this.userInfo.userSource !== 0 || this.userInfo.roleType !== 0
              })
            } else if (this.rowIndex === 4) { // 区域
              ele.disableCheckbox = this.userInfo.userSource !== 0 || this.userInfo.roleType !== 1
              data[item].forEach(node => {
                node.disableCheckbox = this.userInfo.userSource !== 0 || this.userInfo.roleType !== 1
              })
            } else if (this.rowIndex === 5 || this.rowIndex === 3 || this.rowIndex === 1) { // 禁审
              ele.disableCheckbox = true
              data[item].forEach(node => {
                node.disableCheckbox = true
              })
            }
            treeData[idx] = ele
          })
          this.treeData = treeData
          this.selected = [treeData[0].children[0].id]
          this.isShowAudit = !treeData[0].children[0].disableCheckbox
          this.projectId = treeData[0].children[0].id

          if (this.isTech) {
            this.getHighTechFiles({
              deliverType: this.data.deliverType,
              projectId: treeData[0].children[0].id,
              node: treeData[0].children[0].node,
              status: treeData[0].children[0].status
            })
          } else {
            this.isTech = false
            this.fileTabKey = 0
            this.filesList = finaFilesList
            this.tablePager.currentPage = 1
            this.getTypeConfig()
          }
        } else {
          this.$message.error(errorMessage)
        }
      })
    },
    getHighTechFiles ({ deliverType, projectId, node }) {
      this.$http.get('projectDocFile/getHighTechFiles', {
        params: {
          deliverType,
          projectId,
          month: this.data.month
        }
      }).then(({ data }) => {
        this.filesList = data
        this.projectId = projectId
        this.node = node
        if (!this.isShowAudit || data.length === 0) {
          this.$refs.reviewDrawer.onChangeTabKey('log')
        }
        if (data.length > 0) {
          this.fileTabKey = data[0].id
          this.handleGetPreviewFile(data[0])
        } else {
          this.spinning = false
        }
      })
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
              projectId: this.projectId,
              month: this.data.month,
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
    onClose () {
      this.$refs.reviewDrawer.form.resetFields()
      this.$emit('update:visible', false)
      this.$emit('handleGetStatistics')
      Object.assign(this.$data, this.$options.data.call(this))
    },
    checkTreeNode (key, e) {
      const checkedNodes = []
      e.checkedNodes.forEach(item => {
        const { deliverId } = item.data.attrs
        if (!isNaN(deliverId)) checkedNodes.push(deliverId)
      })
      this.isAllChecked = this.len === checkedNodes.length
      this.checkedKeys = key
      this.checkedNodes = checkedNodes
    },
    onAllCheck (e) {
      if (e) {
        const checkedKeys = []
        const checkedNodes = []
        this.treeData.forEach(item => {
          item.children.forEach(node => {
            if (!node.disableCheckbox) {
              checkedKeys.push(node.id)
              checkedNodes.push(node.deliverId)
            }
          })
        })
        this.isAllChecked = true
        this.checkedKeys = checkedKeys
        this.checkedNodes = checkedNodes
      } else {
        this.isAllChecked = false
        this.checkedKeys = []
        this.checkedNodes = []
      }
    },
    tabChange (key) { // 点击抽屉顶部文件列表
      this.fileTabKey = key
      this.spinning = true
      this.filesList.forEach(item => {
        if (item.id === key) {
          if (this.isTech) {
            this.handleGetPreviewFile(item)
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
      let rdTitle = ''
      this.treeData.forEach(item => {
        item.children.forEach(node => {
          if (node.id === this.selected[0]) rdTitle = node.rdTitle
        })
      })
      this.$http.get(this.filesList[this.fileTabKey].url, {
        params: {
          companyId: this.userInfo.companyId,
          year: this.currentYear,
          projectId: this.projectId,
          month: this.data.month,
          rdTitle,
          etype: this.fileTabKey === 3 ? 20302 : (this.fileTabKey === 4 ? 20100 : null),
          types: this.fileTabKey === 5 && [69900, 60000, 60100, 60200, 60300],
          pageNo: this.tablePager.currentPage,
          pageSize: this.tablePager.pageSize
        }
      }).then(({ data }) => {
        this.columns = tableColumns[this.fileTabKey]
        this.finaCurrentFileData = data.data
        this.tablePager.total = data.totalCount
        if (!this.isShowAudit) {
          this.$refs.reviewDrawer.onChangeTabKey('log')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    changePager ({ currentPage, pageSize }) {
      this.spinning = true
      this.tablePager.currentPage = currentPage
      this.tablePager.pageSize = pageSize
      this.getFinaTable()
    },
    selectTreeNode (key, { selectedNodes }) {
      const { deliverType, node, status, isShowAudit } = selectedNodes[0].data.attrs
      this.spinning = true
      this.selected = key
      this.isShowAudit = !isShowAudit
      this.projectId = selectedNodes[0].data.key

      if (this.isTech) {
        this.getHighTechFiles({
          deliverType: deliverType,
          projectId: selectedNodes[0].data.key,
          node: node,
          status: status
        })
      } else {
        this.fileTabKey = 0
        this.filesList = finaFilesList
        this.tablePager.currentPage = 1
        this.getTypeConfig()
      }
    },
    closeModal () {
      this.auditsModal = false
      this.onAllCheck(false)
    }
  }
}
</script>
