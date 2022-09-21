<!--
 * @Author: ldx
 * @Date: 2021-01-28 15:25:40
 * @LastEditors: zdf
 * @Description:  日志与审核
 * @FilePath: \MS-VUE\src\views\customer\modules\AuditProgress\modules\AuditLog.vue
-->
<template>
  <a-tabs
    id="tabs"
    :activeKey="activeKey"
    :tabBarStyle="{ marginBottom: '10px' }"
    style="overflow: auto;"
    size="small"
    @change="tabClick"
  >
    <template slot="tabBarExtraContent">
      <span style="padding: 12px;">{{ title }}</span>
      <a-button
        v-if="auditStatus && auditStatus.status && auditStatus.nodeStatus !== 3 && auditStatus.status === 1 && $auth('customer:projectProgress:activateFlow') && !this.patentProcess"
        size="small"
        type="danger"
        @click="activeFlow"
      >激活
      </a-button>
      <active-flow-modal
        ref="activeFlowModal"
        :mode="auditStatus"
        @refresh="refreshAuditLog"
      ></active-flow-modal>
    </template>
    <a-tab-pane
      v-if="isAuth"
      key="1"
      :disabled="!isAuth"
      tab="审核"
    >
      <div class="audit_wrap">
        <div class="audit_content">
          <a-form
            :form="form"
            layout="inline"
          >
            <a-space
              size="middle"
              align="start"
              style="height: 60px;"
              v-if="isScore"
            >
              <a-form-item label="计入评分">
                <a-checkbox
                  v-decorator="['scoring', { valuePropName: 'checked' }]"
                  :disabled="notChecked"
                />
              </a-form-item>
              <a-form-item label="评分月份">
                <a-month-picker
                  size="small"
                  style="width: 90px;"
                  v-decorator="['month']"
                  :allowClear="false"
                  :disabled="auditDisabled"
                  :getCalendarContainer="getPopupContainer"
                />
              </a-form-item>
              <a-form-item label="工程师">
                <a-select
                  style="width: 90px"
                  size="small"
                  v-decorator="['engineerId']"
                  :disabled="auditDisabled"
                  :getPopupContainer="getPopupContainer"
                  :options="engineerList"
                />
              </a-form-item>
              <a-form-item label="权值">
                <a-input-number
                  size="small"
                  style="width: 70px"
                  placeholder="0~1"
                  :min="0.000"
                  :max="1"
                  :step="0.001"
                  :disabled="auditDisabled"
                  v-decorator="['weight',{
                    rules: [{ required: !auditDisabled, message: '请输入权值' }]
                  }]"
                />
              </a-form-item>
              <a-form-item
                v-if="active === 8"
                label="得分"
              >
                <a-input-number
                  size="small"
                  :precision="0"
                  style="width: 66px"
                  :disabled="auditDisabled"
                  :min="0"
                  :max="100"
                  placeholder="0~100"
                  v-decorator="['score', {
                    rules: [{ required: !auditDisabled, message: `请输入得分` }]
                  }]"
                />
              </a-form-item>
              <div style="marginTop: 8px;">
                <span style="margin-right: 10px">当前得分：{{
                  totalScore
                }}</span>
                <span>次数：{{ scoreCount }}</span>
              </div>
            </a-space>

            <a-space
              style="height: 80px;"
              align="start"
            >
              <a-space v-if="control.score && this.active === 5">
                <a-space
                  v-for="n in 6"
                  :key="n"
                  direction="vertical"
                  align="center"
                >
                  <a-tooltip :getPopupContainer="getPopupContainer">
                    <template slot="title">
                      {{ resultRemark(n) }}
                    </template>
                    <p style="margin: auto;">要素 {{ n }}</p>
                  </a-tooltip>
                  <a-form-item style="height: 60px;margin-right: 0">
                    <a-input-number
                      style="width: 80px;"
                      :disabled="auditDisabled"
                      :precision="0"
                      :min="0"
                      :max="n === 4 ? 50 : 10"
                      :placeholder="n === 4 ? '0~50' : '0~10'"
                      v-decorator="[`${n}`, {
                        rules: [{ required: !auditDisabled, message: `输入要素${n}` }]
                      }]"
                    />
                  </a-form-item>
                </a-space>
              </a-space>
              <a-form-item>
                <a-textarea
                  :style="{
                    width: isScore ? (active !== 8 ? (full ? '100vw' : '400px') : (full ? '100vw' : '940px')) : (full ? 'calc(100vw - 60px)' : '960px'),
                    maxHeight: '140px',
                    minHeight: '66px'
                  }"
                  v-if="isAuth"
                  v-decorator="['suggestion', {
                    rules:[{required: true, whitespace:true,min: 5, message: '请输入5个字以上审批意见'}],
                    initialValue: ''
                  }]"
                  :auto-size="{ minRows: 2, maxRows: 6 }"
                  :extra="onComputeTextarea('remark',200)"
                  placeholder="请输入审批意见"
                />
              </a-form-item>
            </a-space>

            <div class="audit_btn">
              <a-popconfirm
                :getPopupContainer="getPopupContainer"
                cancel-text="取消"
                ok-text="确定"
                placement="right"
                @confirm="handleSubmit(false)"
              >
                <template slot="title">
                  <p>您确定要驳回审核吗?</p>
                </template>
                <a-button
                  v-if="isAuth"
                  :loading="btnLoading"
                  class="btn_space reject"
                  size="small"
                >驳回
                </a-button>
              </a-popconfirm>
              <a-popconfirm
                :getPopupContainer="getPopupContainer"
                cancel-text="取消"
                ok-text="确定"
                placement="right"
                @confirm="handleSubmit(true)"
              >
                <template slot="title">
                  <p>您确定要通过审核吗?</p>
                </template>
                <a-button
                  v-if="isAuth"
                  :loading="btnLoading"
                  class="btn_space success"
                  size="small"
                >通过
                </a-button>
              </a-popconfirm>
            </div>

          </a-form>
        </div>
      </div>
    </a-tab-pane>
    <a-tab-pane
      key="2"
      force-render
      tab="日志"
    >
      <div
        class="log"
        style="height: 100%;"
      >
        <a-spin
          :spinning="spinningLog"
          style="height: 100%;"
        >
          <div style="height: 100%;">
            <vxe-grid
              ref="table"
              :data="tableData"
              auto-resize
              height="100%"
              highlight-current-row
              highlight-hover-row
              resizable
              show-overflow="title"
              size="small"
            >
              <vxe-table-column
                title="序号"
                type="seq"
                width="60"
              ></vxe-table-column>
              <vxe-table-column
                align="left"
                field="nodeName"
                header-align="left"
                title="节点名称"
                width="140"
              ></vxe-table-column>
              <vxe-table-column
                align="left"
                field="pass"
                header-align="left"
                title="审批状态"
                width="100"
              >
                <template v-slot="{row}">
                  <span
                    :style="{color: statusColor[`${row.status}`]}"
                  >{{ statusMap[`${row.status}`] }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                align="left"
                field="auditor"
                header-align="left"
                title="参与者"
              >
                <template v-slot="{row}">{{ row.auditor ? row.auditor : '-' }}
                </template>
              </vxe-table-column>
              <vxe-table-column
                align="left"
                field="suggestion"
                header-align="left"
                title="审批意见"
              ></vxe-table-column>
              <vxe-table-column
                align="center"
                field="createTime"
                header-align="left"
                title="审批时间"
                width="160"
              ></vxe-table-column>
            </vxe-grid>
          </div>
        </a-spin>
      </div>
    </a-tab-pane>
    <a-tab-pane
      v-if="showScoreList"
      key="3"
      tab="评分记录"
    >
      <scores-log
        ref="scoresLog"
        :params="{
          companyId: this.currentObj.companyId,
          rsProjectId: this.currentObj.rsProjectId || this.currentObj.projectId,
          type: this.active,
          year: this.params.year
        }"
        height="100%"
      />
    </a-tab-pane>
  </a-tabs>
</template>

<script>
import ActiveFlowModal from './ActiveFlowModal'
import { statusColor, statusMap } from '@/utils/processDoc/auditStatus'
import { popupContainer } from './js/screenFullMountDom'
import moment from 'moment'
import ScoresLog from '@/views/customer/modules/AuditProgress/modules/ScoresLog'

export default {
  name: 'AuditLog',
  components: {
    ScoresLog,
    ActiveFlowModal
  },
  props: {
    params: {
      type: Object,
      default: () => {
        return {}
      }
    },
    configParams: {
      type: Object,
      default: () => {
        return {}
      }
    },
    title: {
      type: String,
      default: ''
    },
    patentProcess: {
      type: Number,
      default: 0
    },
    currentObj: {
      type: Object,
      default: () => {
        return {}
      }
    },
    active: {
      type: Number,
      default: 1
    },
    full: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    params: {
      deep: true,
      handler: function (n) {
        this.form.resetFields()
        this.handleGetAuditLog()
      }
    }
  },
  computed: {
    totalScore () {
      if (this.active === 8) return this.score
      let total = 0
      Object.keys(this.scores).forEach(item => {
        total += this.scores[item]
      })
      return total
    },
    isScore () {
      return this.control.score && (this.active === 5 || this.active === 8)
    },
    showScoreList () {
      return this.control.log && (this.active === 5 || this.active === 8)
    }
  },
  data () {
    return {
      statusMap,
      statusColor,
      first: true,
      self: this,
      btnLoading: false,
      tableData: [],
      isAuth: false,
      activeKey: '1',
      spinningLog: false,
      form: this.$form.createForm(this, { onValuesChange: this.setScores }),
      ActiveForm: this.$form.createForm(this),
      auditStatus: undefined,
      control: {
        score: this.$auth('customer:projectProgress:score'),
        log: this.$auth('customer:projectProgress:scoreLog')
      },
      auditDisabled: false, // 评分是否禁用
      notChecked: false,
      qualityId: '',
      month: '',
      scoreCount: 1, // 当前评分次数
      engineerList: [], // 工程师列表
      scores: {
        1: 0,
        2: 0,
        3: 0,
        4: 0,
        5: 0,
        6: 0
      },
      score: 0
    }
  },
  methods: {
    moment,
    popupContainer,
    refreshAuditLog () {
      this.handleGetAuditLog()
      this.$emit('updataStatus')
    },
    getPopupContainer (triggerNode) {
      return triggerNode.parentNode
    },
    reset () {
      this.activeKey = '2'
      this.isAuth = false
      this.tableData = []
    },
    // 获取审批日志
    handleGetAuditLog () {
      this.tableData = []
      this.auditStatus = {}
      this.spinningLog = true
      this.$http.get('/projectProgress/getAuditLog', { params: this.params }).then((res) => {
        if (res.data && res.success) {
          this.tableData = res.data.logs
          this.isAuth = res.data.nodePerms
          this.auditStatus = res.data.auditStatus
          this.tableData = this.pushCurrendActiveNode(this.tableData, res.data.auditStatus)
          if (!this.isAuth) {
            this.activeKey = '2'
          } else {
            if (this.activeKey !== '1') this.activeKey = '1'
            this.handleGetConfig()
          }
          this.spinningLog = false
        } else {
          this.$message.error(res.errorMessage)
          this.spinningLog = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
        this.spinningLog = false
      })
    },
    handleGetConfig () {
      if (this.isScore) {
        this.$http.get('/qualityScore/getScoreConfig', {
          params: {
            type: this.active,
            ...this.configParams
          }
        }).then(({
          success,
          data: {
            engineerList,
            scoreCount,
            weight,
            isFinal,
            qualityId,
            month,
            engineerId,
            scores,
            totalScore
          },
          errorMessage
        }) => {
          let scoring = true
          if (success) {
            engineerList.forEach(item => {
              item.key = item.id
              item.title = item.realName
            })
            this.engineerList = engineerList
            this.scoreCount = scoreCount
            this.qualityId = qualityId
            this.notChecked = false
            this.month = month
            this.score = 0
            this.scores = scores || {
              1: 0,
              2: 0,
              3: 0,
              4: 0,
              5: 0,
              6: 0
            }
            if (isFinal || scoreCount > 2) {
              this.auditDisabled = true
              this.notChecked = true
              scoring = false
            }
            this.$nextTick(() => {
              this.form.setFieldsValue({
                scoring,
                engineerId: engineerId || engineerList[engineerList.length - 1].key,
                weight: weight,
                month: moment()
              })
              if (scores) {
                const temp = { ...Array.from({ length: 7 }, (item, index) => scores[index]) }
                delete temp[0]
                this.form.setFieldsValue({
                  totalScore: totalScore,
                  ...temp
                })
              }
            })
          } else {
            this.$message.error(errorMessage)
          }
        })
      }
    },
    setScores (props, values) {
      if (values.scoring === false) {
        this.auditDisabled = true
      } else if (values.scoring === true) {
        this.auditDisabled = false
      }

      if (this.active === 8) {
        if (values.score) this.score = isNaN(+values.score) ? 0 : +values.score
      } else {
        Object.keys(values).forEach(item => {
          if (this.scores[item] !== undefined) {
            this.scores[item] = isNaN(+values[item]) ? 0 : +values[item]
          }
        })
      }
    },
    /**
     * @description: 日志添加当前处理的节点的内容
     * @param {*} tableData
     * @return {*} tableData
     */
    pushCurrendActiveNode (tableData, auditStatus) {
      if (auditStatus && auditStatus.nodeStatus === 0) {
        const currendActiveNode = {
          auditor: auditStatus.auditor,
          nodeName: auditStatus.nodeName,
          suggestion: '-',
          createTime: '-',
          status: 9999
        }
        tableData.push(currendActiveNode)
      }
      return tableData
    },
    handleSubmit (status) {
      this.form.validateFields((error, values) => {
        if (!error) {
          const params = {
            moduleId: this.auditStatus.moduleId,
            pass: status,
            nodeId: this.auditStatus.curNodeId,
            suggestion: values.suggestion,
            instanceId: this.auditStatus.instanceId
          }
          if (this.isScore && values.scoring) {
            params.model = {
              type: this.active,
              engineerId: values.engineerId,
              weight: values.weight,
              month: values.month.format('YYYY-MM'),
              scores: this.active === 8 ? {} : this.scores,
              passRate: this.scoreCount === 1 ? 1 : (this.scoreCount === 2 ? 0.8 : 0),
              scoreCount: this.scoreCount,
              totalScore: this.totalScore,
              qualityId: this.scoreCount > 2 ? this.qualityId : undefined,
              ...this.configParams
            }
            params.scoring = values.scoring
          } else if (this.scoreCount > 2) {
            params.model = {
              qualityId: this.scoreCount > 2 ? this.qualityId : undefined
            }
          }
          this.btnLoading = true
          this.$http.post('/projectProgress/projectAudit', params).then((res) => {
            if (res.data && res.success) {
              this.$message.success('操作成功')
              this.$emit('updataStatus')
              this.handleGetAuditLog()
            } else {
              this.$message.error(res.errorMessage)
            }
          }).catch((error) => {
            this.$message.error(error.message)
          }).finally(() => {
            this.btnLoading = false
          })
        }
      })
    },
    // 激活流程
    activeFlow () {
      this.$refs.activeFlowModal.show(`激活【${this.auditStatus.modeName}】流程`)
    },
    tabClick (tabKey) {
      this.form.resetFields()
      if (tabKey === '1') {
        this.handleGetConfig()
      } else if (tabKey === '3') {
        this.$refs.scoresLog && this.$refs.scoresLog.getList()
      }
      this.activeKey = tabKey
    },
    // 计算文本域的个数
    onComputeTextarea (fieldName, limitNumber = 200) {
      const content = this.form.getFieldValue(fieldName)
      const contentLenght = content ? content.length : 0
      if (contentLenght > limitNumber) {
        const obj = {}
        obj[fieldName] = { value: content.substring(0, limitNumber) }
        this.form.setFields(obj)
      }
      return `(${contentLenght}/${limitNumber})`
    },
    resultRemark (n) {
      switch (n) {
        case 1:
          return '名称整体通顺、高端；主题鲜明；和核心产品技术、产品关联；研发主体特征描述正确'
        case 2:
          return '基本信息正确性及完整性：企业名称、logo、研发人员、研发设备、组织架构、提案管理等'
        case 3:
          return '内容完整性及逻辑合理，包括但不限于：各模块信息完整，且符合大纲内容要求及逻辑合理'
        case 4:
          return '创新性、新颖性，包括但不限于：研究目的充分体现企业着眼点、行业难点、技术难点、技术瓶颈等；主要内容扣题，准确表达研究主题的特性、关键点、亮点；研究内容完整；以通过各地查新为指导方向；技术参数体现其独有性、高标准；经济效益合理且有长足发展意义'
        case 5:
          return '技术路线：符合其研究开发的基本路线及研究方法，严禁套模板；项目计划进度与财务归费轨迹一致：与财务归集费用轨迹一致，即方案开发阶段、项目总结阶段无原材料、设备折旧费用'
        case 6:
          return '其他：错别字、乱码、排版、时态（将来时、完成时的区分）等问题'
      }
    }
  }
}
</script>

<style
  lang="less"
  scoped
>
.log {
  height: 100%;

  & /deep/ .vxe-table .c--title {
    width: 100% !important;
  }

  & /deep/ .ant-spin-nested-loading {
    height: 100%;
  }

  & /deep/ .ant-spin-container {
    height: 100%;
  }
}

.ant-tabs {
  height: 100%;

  /deep/ .ant-tabs-tabpane-active {
    height: 100%;
  }

  /deep/ .ant-tabs-content {
    height: calc(100% - 60px);
  }
}

.ant-form-item {
  margin-bottom: 0px;
}

.audit_wrap {
  position: relative;

  .audit_btn {
    margin-top: 20px;

    .reject {
      &:hover {
        .hover(red);
      }
    }

    .success {
      &:hover {
        .hover(#1890ff);
      }
    }
  }
}

.hover(@color:#fff) {
  color: #fff;
  box-shadow: #fff;
  border: 1px solid #fff;
  background: @color;
}

#tabs {
  /deep/ .ant-tabs-extra-content {
    float: left !important;
  }

  /deep/ .ant-tabs-nav-container {
    display: flex;
    flex-direction: row-reverse;
  }
}

.audit_edit {
  display: flex;
  flex-direction: column;
}

.audit_edit--check {
  display: flex;
  align-content: center;
  align-items: center;
  justify-content: space-between;
  flex-wrap: nowrap;
  margin-bottom: 20px;
}

.audit_edit--inputbox {
  display: flex;
  justify-content: space-between;
}

.audit_edit--input {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 10px;
}

& /deep/ .ant-calendar-picker-container {
  overflow: auto;
  height: 180px;
}
</style>>
