<template>
  <vxe-table
    ref="xTable"
    resizable
    keep-source
    auto-resize
    show-header-overflow="title"
    show-overflow="title"
    highlightHoverRow
    highlightCurrentRow
    :height="height"
    :loading="loading"
    align="center"
    column-width="100px"
    :data="tableData"
    :edit-config="{ trigger: 'manual', mode: 'row', autoClear: false }">
    <vxe-table-column width="120" field="month" title="评分月份" fixed="left" :edit-render="{ immediate: true }">
      <template v-slot="{ row }">
        <span>{{ row.month.substring(0, 7) }}</span>
      </template>
      <template v-slot:edit="{ row }">
        <a-month-picker
          v-model="row.month"
          size="small"
          placeholder="评分月份"
          type="month"
          format="YYYY-MM"
          :allowClear="false"
          :getCalendarContainer="getPopupContainer" />
      </template>
    </vxe-table-column>
    <vxe-table-column width="100" field="engineer" title="工程师" fixed="left" />
    <vxe-table-column width="60" field="scoreCount" title="次数" fixed="left" />
    <vxe-table-column
      v-if="params.type === 6 || params.type === 5"
      v-for="n in 6"
      :key="n"
      :field="`scores[${n}]`"
      :title="`要素${n}`"
      :edit-render="{ immediate: true }">
      <template v-slot:edit="{ row }">
        <vxe-input
          type="integer"
          :min="0"
          :value="row.scores[n]"
          :max="params.type === 6 ? (n === 1 || n === 6 ? 10 : 20) : n === 4 ? 50 : 10"
          :placeholder="`0~${params.type === 6 ? (n === 1 || n === 6 ? 10 : 20) : n === 4 ? 50 : 10}`"
          @change="(e) => updateTotalScore(e.value, n, row)" />
      </template>
    </vxe-table-column>
    <vxe-table-column
      field="totalScore"
      :title="params.type === 6 || params.type === 5 ? '总分' : '得分'"
      :edit-render="{
        enabled: !(params.type === 6 || params.type === 5),
        immediate: true,
      }">
      <template v-slot="{ row }">
        <span>{{ +row.totalScore }}</span>
      </template>
      <template v-slot:edit="{ row }">
        <vxe-input type="integer" :min="0" :max="100" placeholder="0~100" v-model="row.totalScore" />
      </template>
    </vxe-table-column>
    <vxe-table-column field="weight" title="权值" :edit-render="{ immediate: true }">
      <template v-slot="{ row }">
        <span>{{ +row.weight }}</span>
      </template>
      <template v-slot:edit="{ row }">
        <vxe-input
          type="float"
          v-model="row.weight"
          :digits="3"
          :min="0"
          :max="1"
          placeholder="0.00~1.00" />
      </template>
    </vxe-table-column>
    <vxe-table-column field="scorer" title="评分人" />
    <vxe-table-column v-if="params.type === 6" align="left" field="months" title="评分明细" />
    <vxe-table-column field="passed" title="是否通过" :edit-render="{ immediate: true }">
      <template v-slot="{ row }">
        <span>{{ row.passed ? '是' : '否' }}</span>
      </template>
      <template v-slot:edit="{ row }">
        <vxe-switch
          v-model="row.passed"
          open-label="是"
          :open-value="1"
          close-label="否"
          :close-value="0"
          @change="(e) => updateIsPassed(e, row)" />
      </template>
    </vxe-table-column>
    <vxe-table-column field="isFinal" title="是否完成" :edit-render="{ immediate: true }">
      <template v-slot="{ row }">
        <span>{{ row.isFinal ? '是' : '否' }}</span>
      </template>
      <template v-slot:edit="{ row }">
        <vxe-switch
          v-model="row.isFinal"
          open-label="是"
          :open-value="1"
          close-label="否"
          :close-value="0"
          @change="(e) => updateIsFinal(e, row)" />
      </template>
    </vxe-table-column>
    <vxe-table-column field="scoreTime" title="审批时间">
      <template v-slot="{ row }">
        <span>{{ row.scoreTime.substring(0, 10) }}</span>
      </template>
    </vxe-table-column>
    <vxe-table-column width="140" title="操作" fixed="right">
      <template v-slot="{ row, rowIndex }">
        <template v-if="$refs.xTable.isActiveByRow(row)">
          <vxe-button type="text" :loading="loadbtn" status="primary" @click="saveRowEvent(row)">保存 </vxe-button>
          <vxe-button type="text" @click="cancelRowEvent(row)">取消 </vxe-button>
        </template>
        <template v-else-if="row.editable">
          <vxe-button type="text" content="编辑" status="primary" :disabled="!row.editable" @click="editRowEvent(row)" />
        </template>
      </template>
    </vxe-table-column>
  </vxe-table>
</template>

<script>
import { editScore, getList } from '@/api/qualityScore'
import moment from 'moment'

export default {
  name: 'ScoresLog',
  props: {
    params: {
      type: Object,
      default: () => ({})
    },
    height: {
      type: String,
      default: '400'
    },
    isRefresh: {
      type: Boolean,
      default: false
    },
    visible: {
      type: Boolean,
      default: false
    }
  },
  async created () {
    await this.getList()
  },
  watch: {
    async visible (val) {
      if (val) {
        await this.getList()
      }
    },
    async 'params.rsProjectId' () {
      if (this.visible) await this.getList()
    }
  },
  data () {
    return {
      tableData: [],
      loading: false,
      loadbtn: false
    }
  },
  methods: {
    getPopupContainer (triggerNode) {
      return triggerNode.parentNode
    },
    async getList () {
      this.loading = true
      this.tableData = await getList(this.params)
      this.loading = false
    },
    editRowEvent (row) {
      this.$refs.xTable.setActiveRow(row)
    },
    async saveRowEvent (row) {
      this.loadbtn = true
      row.month = moment(row.month).format('YYYY-MM-DD HH:mm:ss')
      if (await editScore(row)) {
        await this.$refs.xTable.clearActived()
        this.$message.success('修改成功')
        this.$emit('update:isRefresh', true)
      }
      this.loadbtn = false
    },
    cancelRowEvent (row) {
      const xTable = this.$refs.xTable
      xTable.clearActived().then(() => {
        // 还原行数据
        xTable.revertData(row)
      })
    },
    updateTotalScore (v, n, row) {
      row.scores[n] = +v
      let totalScore = 0
      for (let i = 1; i < 7; i++) {
        totalScore += +row.scores[i]
      }
      row.totalScore = totalScore
    },
    updateIsPassed ({ value }, row) {
      if (row.scoreCount === 1) row.isFinal = value
    },
    updateIsFinal ({ value }, row) {
      if (row.scoreCount === 1) row.passed = value
    }
  }
}
</script>

<style scoped>
</style>
