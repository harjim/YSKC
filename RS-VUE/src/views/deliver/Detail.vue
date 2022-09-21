<template>
  <a-card :bordered="false">
    <template slot="title">
      <a-button
        v-if="control.allot"
        type="primary"
        @click="() => (visible.allot = true)"
        :disabled="selectedRow.length === 0"
      >分配人员
      </a-button>
    </template>

    <ystable
      ref="table"
      queryUrl="highTechProgress/getList"
      :params="{ year: currentYear }"
      row-id="projectId"
      size="small"
      border
      resizable
      auto-resize
      highlight-hover-row
      show-overflow="title"
      show-header-overflow
      highlight-current-row
      header-align="center"
      :checkbox-config="{
        labelField: 'companyName',
        highlight: true
      }"
      @checkbox-all="selectChangeEvent"
      @checkbox-change="selectChangeEvent"
      @completed="({ data }) => completed(data)"
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
          <a v-if="control.techView" @click="handleClickTech(row.pApplication, row.rdTitle)">{{
            handleRenderText(row.pApplication, null)
          }}</a>
          <span v-else>{{ handleRenderText(row.pApplication, null) }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column v-for="n in 12" :key="n" :title="`${n}月轨迹材料`" align="center">
        <vxe-table-column title="技术部分" :width="140" resizable>
          <template slot-scope="{ row }">
            <a v-if="control.techView" @click="handleClickTech(row.techNodeMap[n], row.rdTitle)">{{
              handleRenderText(row.techNodeMap && row.techNodeMap[n], n)
            }}</a>
            <span v-else>{{ handleRenderText(row.techNodeMap && row.techNodeMap[n], n) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column title="财务部分" :width="140" resizable>
          <template slot-scope="{ row }">
            <a v-if="control.finaView" @click="handleClickFina(row.finaNodeMap[n], row.rdTitle)">{{
              handleRenderText(row.finaNodeMap && row.finaNodeMap[n], n)
            }}</a>
            <span v-else>{{ handleRenderText(row.finaNodeMap && row.finaNodeMap[n], n) }}</span>
          </template>
        </vxe-table-column>
      </vxe-table-column>
      <vxe-table-column title="验收材料" field="pAccept" :width="140" resizable>
        <template slot-scope="{ row }">
          <a v-if="control.techView" @click="handleClickTech(row.pAccept, row.rdTitle)">{{
            handleRenderText(row.pAccept, null)
          }}</a>
          <span v-else>{{ handleRenderText(row.pAccept, null) }}</span>
        </template>
      </vxe-table-column>
    </ystable>

    <allot-modal
      :visible="visible.allot"
      :currentYear="currentYear"
      :selectedRow="selectedRow"
      @handleGetList="() => $refs.table.refresh(false)"
      @close="onCloseAllotModal"
    />

    <detail-drawer
      :visible.sync="visible.drawer"
      :isTech="drawerProps.isTech"
      :title="drawerProps.title"
      :currentNode="currentNode"
      :currentYear="currentYear"
      @close="visible.drawer = false"
      @handleGetList="() => $refs.table.refresh(false)"
    />
  </a-card>
</template>

<script>
import { mapGetters } from 'vuex'
import { ystable } from '@/components'
import AllotModal from './modules/AllotModal.vue'
import DetailDrawer from './modules/DetailDrawer.vue'

const defaultDrawerProps = {
  title: '', // 标题
  isTech: true
}

export default {
  name: 'HighTechDetail',
  components: {
    ystable,
    DetailDrawer,
    AllotModal
  },
  data () {
    return {
      control: {
        allot: this.$auth('deliver:detail:allot'),
        techView: this.$auth('deliver:detail:techView'),
        finaView: this.$auth('deliver:detail:finaView'),
        techAudit: this.$auth('deliver:detail:techAudit'),
        finaAudit: this.$auth('deliver:detail:finaAudit')
      },
      loading: false,
      visible: {
        allot: false,
        drawer: false
      },
      tableData: [],
      selectedRow: [],
      drawerProps: {
        ...defaultDrawerProps
      },
      currentNode: {}
    }
  },
  computed: {
    ...mapGetters(['currentYear', 'userInfo'])
  },
  watch: {
    currentYear () {
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
    }
  },
  methods: {
    completed (data) {
      this.selectedRow = []
      this.tableData = data && data.data
    },
    resultTitle (status, node) {
      // 阶段文本
      if ((!status && !node) || status === 2) {
        return '优赛准备中'
      } else if (status === 0 && node !== null) {
        if (node === 0) {
          return '优赛准备中'
        } else if (node === 10) {
          return '工厂审核中'
        } else if (node === 20) return '区域审核中'
      } else if (status === 1) return '已完成'
    },
    handleRenderText (r) {
      // 渲染文本
      if (r) return this.resultTitle(r.status, r.node)
      return ''
    },
    selectChangeEvent ({ records }) {
      // 表格选中
      const result = []
      for (let i = 0; i < records.length; i++) {
        result.push(records[i].projectId)
      }
      this.selectedRow = result
    },
    handleClickTech (node, title) {
      // 点击某个月轨迹材料技术部分
      this.currentNode = node
      this.visible.drawer = true
      this.drawerProps.isTech = true
      this.drawerProps.title = `${title}-${this.renderTitle(node)}`
    },
    handleClickFina (node, title) {
      // 点击某个月轨迹材料的财务部分
      this.currentNode = node
      this.visible.drawer = true
      this.drawerProps.isTech = false
      this.drawerProps.title = `${title}-${this.renderTitle(node)}`
    },
    renderTitle ({ node, deliverType, month }) {
      const n = node === 0 ? '优赛' : node === 10 ? '工厂' : '区域'
      const t =
        deliverType === 0
          ? '立项材料'
          : deliverType === 1
            ? '轨迹材料（技术部分）'
            : deliverType === 3
              ? '验收材料'
              : '轨迹材料（财务部分）'
      if (deliverType === 1 || deliverType === 4) {
        return `【${n}${new Date(month).getMonth() + 1}月份】${t}`
      }
      return `【${n}】${t}`
    },
    onCloseAllotModal () {
      this.visible.allot = false
    }
  }
}
</script>

<style lang="less" scoped></style>
