<template>
  <vxe-modal
    id="XSModal"
    resize
    remember
    :mask="false"
    :height="height"
    min-width="420"
    destroy-on-close
    :min-height="380"
    :lock-view="false"
    :loading="loading"
    :show-zoom="false"
    :value.sync="visible"
    :position="{ top: 1, left: 520 }"
    @close="$emit('close')"
  >
    <template #header>
      <div class="modal-header">
        <span style="width: 60%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap">{{ title }}</span>
        <span>
          <a-button size="small" shape="circle" style="margin-right: 10px" @click="changeZoom">
            <a-icon :type="zoom ? 'down' : 'up'" />
          </a-button>
          <a-button size="small" shape="circle" @click="close">
            <a-icon type="close" />
          </a-button>
        </span>
      </div>
    </template>
    <a-form-model ref="form" layout="inline" :model="form" :rules="rules">
      <a-space direction="vertical" style="width: 100%">
        <div class="box-item" style="justify-content: flex-end" v-if="$auth('customer:projectProgress:scoreLog')">
          <a :disabled="engineerList.length === 0" @click="showModal = true">评分记录</a>
        </div>
        <div class="box-item" style="height: 60px; align-items: stretch">
          <a-form-model-item label="评分月份">
            <a-month-picker
              style="width: 100px"
              v-model="form.month"
              :disabled="disabled"
              :allowClear="false"
              :getCalendarContainer="getPopupContainer"
            />
          </a-form-model-item>
          <a-form-model-item label="权值" prop="weight">
            <a-input-number
              placeholder="0~1"
              :min="0.0"
              :max="1"
              :step="0.05"
              :disabled="disabled"
              v-model="form.weight"
            />
          </a-form-model-item>
        </div>
        <div class="box-item" :style="{ height: params.type !== 6 && '60px', alignItems: 'stretch' }">
          <a-form-model-item label="工程师">
            <a-select
              style="width: 100px"
              :options="engineerList"
              :disabled="disabled"
              v-model="form.engineerId"
              :getPopupContainer="getPopupContainer"
            />
          </a-form-model-item>
          <a-form-model-item v-if="params.type === 6" label="项目年份">
            <a-select
              style="width: 90px"
              v-model="form.year"
              :options="allYears"
              :disabled="disabled"
              :getPopupContainer="getPopupContainer"
            />
          </a-form-model-item>
          <a-form-model-item v-else label="得分" prop="totalScore">
            <a-input-number
              :min="0"
              :precision="0"
              :max="100"
              :step="5"
              placeholder="0~100"
              :disabled="disabled"
              v-model="form.totalScore"
            />
          </a-form-model-item>
        </div>

        <div
          v-if="params.type === 6"
          v-for="(v, k) in allMonths"
          :key="k"
          :style="{
            height: v.length > 6 ? '60px' : '30px',
            marginLeft: '10px',
            justifyContent: 'flex-start',
            alignItems: 'flex-start',
          }"
          class="box-item"
        >
          <a-button type="link" @click="checkedAllMonth(k)">{{ k }}： </a-button>
          <div class="btn-box">
            <div v-for="(i, j) in v" :key="i.title" style="flex: 0 0 16%">
              <a-badge
                :status="i.isFinal ? 'success' : i.scoreCount > 2 ? 'error' : i.scoreCount === 2 ? 'warning' : null"
              >
                <a-button :disabled="i.disabled" :type="i.type" size="small" @click="clickMonth(k, i.title, j)">
                  {{ i.title }}
                </a-button>
              </a-badge>
            </div>
          </div>
        </div>

        <div class="box-item">
          <!-- 前两次评分可选通过与否, 后续评分只能选择是否完成 -->
          <a-form-model-item v-if="scoreCount < 3" label="是否通过">
            <a-checkbox :disabled="cantCheck" v-model="form.passed" />
          </a-form-model-item>
          <a-form-model-item v-else label="是否完成">
            <a-checkbox :disabled="cantCheck" v-model="form.isFinal" />
          </a-form-model-item>
          <div>
            <span style="margin-right: 10px">评分次数：{{ scoreCount }}</span>
            <span>当前得分：{{ form.totalScore || 0 }}</span>
          </div>
        </div>

        <div
          v-if="params.type === 6"
          v-for="n in 3"
          :key="n"
          class="box-item"
          style="height: 60px; align-items: stretch"
        >
          <div v-for="i in 2" :key="i">
            <a-form-model-item :prop="`scores[${returnIndex(n - 1, i - 1)}]`">
              <template slot="label">
                <a-tooltip :getPopupContainer="getPopup">
                  <template slot="title">
                    {{ resultRemark(returnIndex(n - 1, i - 1)) }}
                  </template>
                  <span>要素{{ returnIndex(n - 1, i - 1) }}</span>
                </a-tooltip>
              </template>
              <a-input-number
                :min="0"
                :precision="0"
                :max="returnIndex(n - 1, i - 1) === 1 || returnIndex(n - 1, i - 1) === 6 ? 10 : 20"
                :disabled="disabled"
                v-model="form.scores[returnIndex(n - 1, i - 1)]"
                :placeholder="returnIndex(n - 1, i - 1) === 1 || returnIndex(n - 1, i - 1) === 6 ? '0~10' : '0-20'"
                :step="5"
                @change="addTotal"
              />
            </a-form-model-item>
          </div>
        </div>

        <div style="display: flex; justify-content: flex-end">
          <a-form-model-item>
            <a-button type="primary" :disabled="canSave" @click="saveScore">提交 </a-button>
          </a-form-model-item>
        </div>
      </a-space>
    </a-form-model>
    <scores-log-modal :visible="showModal" :params="params" :title="title" @close="closeModal" />
  </vxe-modal>
</template>

<script>
import { getScoreConfig, saveScore } from '@/api/qualityScore'
import moment from 'moment'
import ScoresLogModal from '@/views/customer/modules/AuditProgress/modules/ScoresLogModal'

const defaultScores = {
  1: undefined, // 质量要素1
  2: undefined, // 质量要素2
  3: undefined, // 质量要素3
  4: undefined, // 质量要素4
  5: undefined, // 质量要素5
  6: undefined, // 质量要素6
}
const defaultFormValue = {
  passed: false, // 是否通过
  isFinal: false, // 是否完成
  month: moment(), // 评分月份
  monthsId: undefined,
  year: '', // 项目年份
  weight: 0.083, // 评分权重
  engineerId: '', // 工程师id
  scores: { ...defaultScores }, // 质量要素得分
  totalScore: undefined, // 得分
}

export default {
  name: 'ScoreModal',
  components: { ScoresLogModal },
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    params: {
      type: Object,
      default: () => ({}),
    },
    title: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      loading: false,
      cantCheck: false, // 能否选择
      monthsFinal: false, // 月份组是否完成
      showModal: false, // 是否显示评分记录弹窗
      zoom: false, // 是否是缩放状态
      height: '380', // 弹窗高度
      scoreCount: 1, // 评分次数
      month: '',
      qualityId: undefined, // 质量id
      engineerList: [], // 工程师列表
      allYears: [], // 全部年份
      allMonths: [], // 全部月份
      form: { ...defaultFormValue },
      rules: {
        weight: [{ required: true, message: '请输入权重', trigger: 'change' }],
        totalScore: [{ required: true, message: '请输入得分', trigger: 'change' }],
        'scores[1]': [{ required: true, message: '请输入要素1', trigger: 'change' }],
        'scores[2]': [{ required: true, message: '请输入要素2', trigger: 'change' }],
        'scores[3]': [{ required: true, message: '请输入要素3', trigger: 'change' }],
        'scores[4]': [{ required: true, message: '请输入要素4', trigger: 'change' }],
        'scores[5]': [{ required: true, message: '请输入要素5', trigger: 'change' }],
        'scores[6]': [{ required: true, message: '请输入要素6', trigger: 'change' }],
      },
    }
  },
  watch: {
    async visible(n) {
      if (n) {
        this.loading = true
        await this.getScoreConfig()
      } else {
        this.$refs.form.resetFields()
        Object.assign(this.$data, this.$options.data.call(this))
      }
    },
    async 'params.rsProjectId'() {
      if (this.visible) {
        this.loading = true
        await this.getScoreConfig()
      }
    },
    'params.type'() {
      this.height = '380'
    },
    setMonths(n) {
      if (n.length === 0) {
        Object.keys(this.allMonths).forEach((y) => {
          this.allMonths[y].forEach((m) => {
            m.disabled = false
            m.type = 'default'
          })
        })
        this.month = ''
        this.scoreCount = 1
        this.cantCheck = false
        this.monthsFinal = false
        this.qualityId = false
        this.form.monthsId = undefined
        this.form.weight = 0.083
        this.form.engineerId = this.engineerList.length ? this.engineerList[this.engineerList.length - 1].key : ''
        this.form.year = this.allYears.length ? this.allYears[0].key : ''
        this.form.scores = { ...defaultScores }
        this.form.totalScore = 0
      }
    },
  },
  computed: {
    disabled() {
      return this.cantCheck || this.scoreCount > 2 || this.monthsFinal
    },
    canSave() {
      const flag = this.engineerList.length === 0 || this.cantCheck || (this.scoreCount > 2 && !this.form.isFinal)
      if (this.params.type === 6) {
        return flag || this.setMonths.length === 0
      }
      return flag
    },
    setMonths() {
      const temp = []
      Object.keys(this.allMonths).forEach((year) => {
        this.allMonths[year].forEach((month) => {
          if (month.type === 'primary') temp.push(`${year}-${month.title}`)
        })
      })
      return temp
    },
  },
  methods: {
    getPopupContainer(triggerNode) {
      return triggerNode.parentNode
    },
    getPopup() {
      return document.getElementById('fullBox')
    },
    returnIndex(n, i) {
      const idxs = [
        [1, 2],
        [3, 4],
        [5, 6],
      ]
      return idxs[n][i]
    },
    async getScoreConfig() {
      const data = await getScoreConfig(this.params)
      if (this.params.type === 6) {
        this.updateMonths()
        data.months.forEach((item) => {
          Object.keys(this.allMonths).forEach((y) => {
            this.allMonths[y].forEach((m) => {
              if (item.months.indexOf(`${y}-${m.title}`) !== -1) {
                m.id = item.id
                m.scoreCount = item.scoreCount
                m.isFinal = !!item.isFinal
                m.weight = item.weight
                m.qualityId = item.qualityId
                m.month = item.month
                m.engineerId = item.engineerId
                m.year = item.year
                m.scores = item.scores
                m.totalScore = item.totalScore
              }
            })
          })
        })
      } else {
        this.month = data.month
        this.qualityId = data.qualityId
        this.scoreCount = data.scoreCount
        this.form.scores = data.scores ? data.scores : { ...defaultScores }
        this.form.totalScore = data.totalScore ? data.totalScore : 0
      }
      this.engineerList = data.engineerList
      this.form = {
        ...this.form,
        weight: data.weight,
        engineerId: data.engineerList.length
          ? data.engineerId || data.engineerList[data.engineerList.length - 1].key
          : '',
      }
      this.cantCheck = !!data.isFinal // 如果是最终评分，或者评分次数大于2次，则不能评分
      this.loading = false
    },
    addTotal() {
      let total = 0
      for (let i = 1; i < 7; i++) {
        total += this.form.scores[i] ? this.form.scores[i] : 0
      }
      this.form.totalScore = total
    },
    updateMonths() {
      // 更新月份列表
      const allYears = []
      const allMonths = {}
      const { beginDate, endDate } = this.params
      const begin = moment(beginDate)
      const end = moment(endDate)
      // eslint-disable-next-line no-unmodified-loop-condition
      while (end > begin || begin.format('M') === end.format('M')) {
        const year = begin.format('YYYY')
        if (!allMonths[year]) {
          allYears.push({
            key: year,
            title: year,
            allCheck: false,
          })
          allMonths[year] = []
        }
        allMonths[year].push({
          title: begin.format('MM'),
          type: 'default',
          disabled: false,
          id: undefined,
          qualityId: undefined,
          scoreCount: 1,
          isFinal: false,
          weight: 0.083,
          month: '',
          year: undefined,
          engineerId: undefined,
          scores: { ...defaultScores },
          totalScore: 0,
        })
        begin.add(1, 'month')
      }
      this.form.year = allYears[0].key
      this.allYears = allYears
      this.allMonths = allMonths
    },
    checkedAllMonth(y) {
      let checked = false
      this.allYears.forEach((item) => {
        if (item.title === y) {
          checked = !item.allCheck
          item.allCheck = !item.allCheck
        }
      })
      for (const allMonthElement of this.allMonths[y]) {
        if (!allMonthElement.id) {
          allMonthElement.type = checked ? 'primary' : 'default'
        } else {
          allMonthElement.type = 'default'
          allMonthElement.disabled = checked
        }
      }
      this.scoreCount = 1
      this.month = ''
      this.cantCheck = false
      this.monthsFinal = false
      this.qualityId = undefined
      this.form.monthsId = undefined
    },
    /**
     * 点击月份确认月份组
     * @param k 年 key
     * @param t 月 title
     * @param j 下标
     */
    clickMonth(k, t, j) {
      const all = this.allMonths
      // if (all[k][j].isFinal) this.disabled = true
      if (all[k][j].id === undefined) {
        // 如果以前没有分组
        all[k][j].type = all[k][j].type === 'primary' ? 'default' : 'primary'
        this.scoreCount = 1
        this.month = ''
        this.cantCheck = false
        this.monthsFinal = false
        this.qualityId = undefined
        this.form.monthsId = undefined
        this.form.scores = { ...defaultScores }
        this.form.totalScore = 0
        Object.keys(all).forEach((y) => {
          all[y].forEach((m) => {
            if (m.id && all[k][j].type === 'primary') {
              m.disabled = true
              m.type = 'default'
            }
          })
        })
      } else {
        Object.keys(all).forEach((y) => {
          all[y].forEach((m) => {
            if (m.id !== all[k][j].id) {
              // 如果不是同一组
              m.disabled = !m.disabled
              m.type = 'default'
            } else {
              m.type = m.type === 'primary' ? 'default' : 'primary'
              this.form.monthsId = m.id
              this.form.weight = m.weight
              this.form.year = m.year
              this.form.engineerId = m.engineerId
              this.form.month = m.month
              this.form.scores = m.scores
              this.form.totalScore = m.totalScore
              this.month = m.month
              this.qualityId = m.qualityId
              this.cantCheck = !!m.isFinal
              this.scoreCount = m.scoreCount
              this.monthsFinal = m.isFinal
            }
          })
        })
      }
      this.allMonths = all
    },
    async saveScore() {
      if (this.form.isFinal) {
        // 如果是两次评分之后的最终评分，则只传递isFinal参数
        this.loading = true
        await saveScore(this.resultParams())
        this.$message.success('项目已完成')
        this.$refs.form.resetFields()
        Object.assign(this.$data, this.$options.data.call(this))
        await this.getScoreConfig()
      } else {
        this.$refs.form.validate(async (valid) => {
          if (valid) {
            this.loading = true
            if (await saveScore(this.resultParams())) {
              this.$refs.form.resetFields()
              this.$message.success('提交成功')
              Object.assign(this.$data, this.$options.data.call(this))
              await this.getScoreConfig()
            } else {
              this.loading = false
            }
          }
        })
      }
    },
    async closeModal(status) {
      this.showModal = false
      if (status) {
        this.$refs.form.resetFields()
        Object.assign(this.$data, this.$options.data.call(this))
        await this.getScoreConfig()
      }
    },
    changeZoom() {
      if (this.zoom) {
        this.height = '380'
      } else {
        this.height = '50'
      }
      this.zoom = !this.zoom
    },
    close() {
      this.$emit('close')
    },
    resultParams() {
      // 获取提交参数
      if (this.form.isFinal) {
        return {
          ...this.params,
          isFinal: this.form.isFinal ? 1 : 0,
          qualityId: this.qualityId,
          months: JSON.stringify(this.setMonths),
          monthsId: this.form.monthsId,
        }
      } else {
        return {
          ...this.params,
          ...this.form,
          year: this.params.type === 9 ? this.params.year : this.form.year,
          scoreCount: this.scoreCount,
          passRate: this.scoreCount === 1 ? 1 : 0.8,
          passed: this.form.passed ? 1 : 0,
          isFinal: this.form.isFinal ? 1 : 0,
          month: moment(this.form.month).format('YYYY-MM'),
          months: JSON.stringify(this.setMonths),
          monthsId: this.form.monthsId,
        }
      }
    },
    resultRemark(n) {
      switch (n) {
        case 1:
          return '基本信息正确性：企业名称、logo、研发人员、研发设备、组织架构等；'
        case 2:
          return '完整性，包括但不限于：研究阶段、设计阶段、验证阶段、试制阶段、验收阶段，每个对应阶段都有相应的研发轨迹文件；各阶段文档的多少，是否齐全；各文档必填项内容完整。'
        case 3:
          return '合理性：项目阶段、各模块内容、问题点、人员设置、设备选择、试制产量等具体参数设计合理；内容描述符合大纲要求且逻辑合理。'
        case 4:
          return '时间逻辑：确保人员、设备、建议时间、会议时间、计划进度、试制数据、成果转化年度等，每个节点的日期前后逻辑正确。'
        case 5:
          return '研发轨迹层层推进，研发问题点层层深入。'
        case 6:
          return '其他：错别字、乱码、排版、时态（将来时、完成时区分）等问题。'
      }
    },
  },
}
</script>

<style lang="less" scoped>
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-right: -3em;
}

.box-item {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.btn-box {
  height: 100%;
  width: 80%;
  margin-left: 10px;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: flex-start;
}
</style>
