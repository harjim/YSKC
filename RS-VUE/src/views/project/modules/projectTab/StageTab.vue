<!--
 * @Author:
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2022-06-28 16:03:24
 * @LastEditors: zdf
 * @Description: 配置阶段
 * @FilePath: \RS-VUE\src\views\project\modules\projectTab\StageTab.vue
-->
<template>
  <div ref="stageTab">
    <div v-if="isShowConfig || modifyStatus" style="margin-bottom: 10px">
      方案：
      <a-radio-group
        v-model="radioStageType"
        :options="radioOptions"
        :default-value="1"
        @change="onRadioChange"
      />
      <a-button type="primary" @click="onCreateStages" style="margin-right: 10px">生成</a-button>
      <a-button v-if="control.rename" type="primary" @click="() => this.$emit('showEdit')">自定义名称</a-button>
    </div>
    <ystable
      ref="table"
      id="stageTab_stageTable"
      queryUrl="/stage/queryStage"
      :params="queryParam"
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      keep-source
      :show-footer="true"
      @completed="({ data }) => completed(data.data)"
    >
      <vxe-table-column type="seq" width="60" align="center" title="序号"></vxe-table-column>
      <vxe-table-column field="stageName" title="规划阶段" width="150" align="center">
        <template v-slot:header>
          <span style="color: red">*</span>规划阶段
        </template>
        <template v-slot="{ row }">
          <a-select
            v-if="row.isAdd && $auth('project:report:stage:edit')"
            :options="stages"
            v-model="row['stageKey']"
            placeholder="请选择阶段"
            @change="(value, option) => selectChange(value, option, row)"
            :getPopupContainer="setMountDOM"
            style="width: 100%"
          ></a-select>
          <span v-else>{{ row.stageType }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column field="beginDate" width="150" align="center">
        <template v-slot:header>
          <span style="color: red">*</span>开始日期
        </template>
        <template v-slot="{ row, rowIndex }">
          <a-date-picker
            v-if="$auth('project:report:stage:edit')"
            :getCalendarContainer="setMountDOM"
            format="YYYY-MM-DD"
            :disabledDate="(val) => disabledTStartDate(val, row, rowIndex, true)"
            :value="row.beginDate ? moment(row.beginDate, 'YYYY-MM-DD') : null"
            :defaultPickerValue="defaultPickerValue(row, rowIndex, true, true)"
            @change="(dates, dateString) => onDateChange(dates, dateString, row, 'beginDate')"
          />
          <span v-else>{{ row.beginDate }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column field="endDate" width="150" align="center">
        <template v-slot:header>
          <span style="color: red">*</span>结束日期
        </template>
        <template v-slot="{ row, rowIndex }">
          <a-date-picker
            v-if="$auth('project:report:stage:edit')"
            :getCalendarContainer="setMountDOM"
            format="YYYY-MM-DD"
            :disabledDate="(val) => disabledTEndDate(val, row, rowIndex, false)"
            :value="row.endDate ? moment(row.endDate, 'YYYY-MM-DD') : null"
            :defaultPickerValue="defaultPickerValue(row, rowIndex, true, false)"
            @change="(dates, dateString) => onDateChange(dates, dateString, row, 'endDate')"
          />
          <span v-else>{{ row.endDate }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="workDesc"
        title="工作内容"
        min-width="150"
        align="left"
        header-align="center"
      >
        <template v-slot="{ row }">
          <a-textarea
            v-if="$auth('project:report:stage:edit')"
            :rows="2"
            :value="row.workDesc"
            @change="(e) => onCellChange(e.target.value, row, 'workDesc')"
          />
          <span v-else>{{ row.workDesc }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column field="remark" title="备注" width="150" align="left" header-align="center">
        <template v-slot="{ row }">
          <a-input
            v-if="$auth('project:report:stage:edit')"
            :value="row.remark"
            @change="(e) => onCellChange(e.target.value, row, 'remark')"
          />
          <span v-else>{{ row.remark }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column title="操作" width="130" align="center" header-align="center">
        <template v-slot="{ row, rowIndex }" v-if="modifyStatus">
          <a
            title="点击向上添加规划阶段"
            @click="addStage(true, rowIndex)"
            v-if="
              tableData.length < stages.length &&
                $auth('project:report:stage:add') &&
                $auth('project:report:stage:edit')
            "
          >向上添加</a>
          <a-divider
            type="vertical"
            v-if="
              tableData.length < stages.length &&
                $auth('project:report:stage:add') &&
                $auth('project:report:stage:edit')
            "
          ></a-divider>
          <a-popconfirm
            title="是否确定删除?"
            v-if="$auth('project:report:stage:del') && $auth('project:report:stage:edit')"
            @confirm="del(row)"
          >
            <a>删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
      <template v-slot:bottom>
        <a-button
          v-if="$auth('project:report:stage:add') && $auth('project:report:stage:edit') && modifyStatus"
          :disabled="tableData.length >= stages.length"
          title="点击添加规划阶段"
          type="dashed"
          style="width: 100%; font-weight: bolder"
          @click="addStage()"
        >+</a-button>
      </template>
    </ystable>
    <template v-if="modifyStatus">
      <a-button
        type="primary"
        style="margin-right: 10px; margin-top: 10px"
        v-if="openEditMode && $auth('project:report:stage:edit')"
        :disabled="isSaveDisabled"
        @click="onSaveList"
      >保存</a-button>
      <a-checkbox :checked="checked" @change="onCheckBoxChange">添加默认文档</a-checkbox>
      <a-tooltip placement="top">
        <template slot="title">
          <span>只添加纯委托文档</span>
        </template>
        <a-checkbox v-model="templateType">纯委托项目</a-checkbox>
      </a-tooltip>
    </template>
  </div>
</template>
<script>
import ystable from '@/components/Table/ystable'
import { Ellipsis, UploadModal } from '@/components'
// import Stage from './js/StageConfig'
import moment from 'moment'

const desc = {
  100: '通过分析公司的技术、资金和运营资源，针对研究开发项目成立研发规划小组，基于合理化建议，草拟规划方案，定期召开例会，对规划的内容进行讨论确定。研发规划草案编制结束后，报研发总监审核，审核通过后报高层审议。',
  200: '根据研究开发项目功能和性能及项目任务的要求，在符合法律法规、组织承诺的标准和行业规范的前提下，确定项目的质量和目标，准备原材料和设备仪器的投入，制订实施计划等，同时进行关键性技术难题的分析研究，对产品及工艺开发过程特殊性分析，通过理论和试验的结合，采取多方论证小组会议，完成研究阶段性评审及总结。',
  300: '根据项目任务，开展设计工作，初步确定设计方案，包括：主要工艺要求、精度及其特点，特殊要求等，试制中可能出现的各种关键问题、关键件及其解决措施；工艺路线，工艺规程详尽程度，工艺装备系数和设计原则。确定开发阶段物料清单，采购和准备开发所需的的设备、零部件、原材料、工装和模具等，根据选择的技术方案进行试验，测试性能，重点考虑新工艺技术的性能，采取多方论证小组会议，完成设计开发阶段性评审及总结。',
  400: '根据研发计划，对设计工艺进行验证，制定工艺流程、测试系统分析计划及分析潜在失效后果。验证设计能力、过程能力、时程安排、成本目标、试制率目标及老化能力及是否满足特殊特性要求。通过多方论证小组会议，完成阶段性总结。',
  500: '根据初定的设计方案作进一步的实验验证，重点关注设计系统运行的各项指标，最终确定设计方案及新工艺。通过多方论证小组会议，完成阶段性总结。',
  600: '对新工艺进行试验证，对局部和系统主要设计部分进行验证和确认，对设计试验证中出现的问题进行分析、记录及总结，并采取措施、跟踪进一步做技术改进与提高，不断优化新工艺的各项技术性能，通过多方论证小组会议，完成阶段性总结。',
  700: '对新工艺进行放大试验和试制，对整体设计进行验证和确认，对设计试验试制中出现的问题进行分析、记录及总结，并采取措施、跟踪进一步做技术改进与提高，不断优化新工艺的各项技术性能，通过多方论证小组会议，准备新工艺评审及鉴定，完成试验试制阶段性评审及总结。',
  800: '核查研发项目计划规定范围内的各项工作或活动是否已经全部完成，项目成果是否达标和令人满意，并将核查结果记录在验收文件中。如果项目没有全部完成而提前结束，则应查明有哪些工作已经完成，完成到了什么程度，哪些工作没有完成并将核查结果记录在案，形成文件，完成验收阶段性评审及总结。',
  900: '项目资料整理归档，要求研发项目资料符合公司文件整编和归档要求。项目经验总结，积累设计和开发经验。'
}

export default {
  name: 'StageTab',
  components: {
    ystable,
    // StageModal,
    Ellipsis,
    UploadModal
  },
  props: {
    projectData: {
      type: Object,
      required: true
    },
    modifyStatus: {
      type: Boolean,
      default: true
    },
    loading: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      templateType: false,
      visible: false,
      editModal: false,
      title: '',
      projectId: '',
      paramData: { projectId: this.projectId },
      queryParam: {},
      isSaveDisabled: false,
      form: this.$form.createForm(this),
      typeList: [],
      saveData: [],
      isShowConfig: false,
      openEditMode: true,
      pStartDate: undefined, // 项目的开始日期
      pEndDate: undefined, // 项目的结束日期
      stages: [],
      // selectStages: [],
      stagesKV: {},
      tableData: [],
      checked: true,
      radioOptions: [
        { label: '7阶段', value: 1 },
        { label: '9阶段', value: 2 }
      ],
      radioStageType: 1,
      control: {
        rename: this.$auth('project:report:stage:rname')
      },
      proGetStage: {}
    }
  },
  watch: {
    projectData (item) {
      this.isSaveDisabled = false
      this.projectId = item.id
      this.paramData.projectId = item.id
      this.pStartDate = moment(item.beginDate)
      this.pEndDate = moment(item.endDate)
      this.saveData = []
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
    },
    loading (newValue) {
      if (newValue === false) {
        this.updateStage()
      }
    }
  },
  created () {
    this.projectId = this.projectData.id
    this.paramData.projectId = this.projectData.id
    this.queryParam.projectId = this.projectData.id
    this.pStartDate = moment(this.projectData.beginDate)
    this.pEndDate = moment(this.projectData.endDate)
    this.$getDictionary(6).then((res) => {
      res.forEach((item, index) => {
        this.stages.push({ value: item.key, label: item.value })
        this.stagesKV[item.key] = item.value
      })
    })
    this.updateStage()
  },
  methods: {
    updateStage (e) {
      this.$http.get('stage/getStage').then(({ data }) => {
        // ({ defaultStageList: this.proGetStage } = data)
        data.defaultStageList.forEach((elem) => {
          this.proGetStage[elem.stageKey] = elem.stageName
        })
        data.companyStageList.forEach((elem) => {
          this.proGetStage[elem.stageKey] = elem.stageName
        })
      })
    },
    moment,
    onRadioChange (e) { },
    onCheckBoxChange (e) {
      this.checked = e.target.checked
    },
    completed (data) {
      this.tableData = data
      this.isShowConfig = data.length <= 0
      this.typeList = []
      data.forEach((e) => {
        this.typeList.push(e.stageKey)
      })
      this.resetDisable()
    },
    /**
     * @description:  禁选开始日期列的日期
     * @param {*} date
     * @param {*} record
     * @param {*} rowIndex
     * @param {*} isStart true开始日期 false 结束日期
     * @return {*}
     */
    disabledTStartDate (date, record, rowIndex, isStart) {
      const { pStartDate, pEndDate } = this
      let start = pStartDate
      let end = pEndDate
      const previousDate = this.findRecentlyDate(true, rowIndex, isStart)
      const nextDate = this.findRecentlyDate(false, rowIndex, isStart)
      if (previousDate) {
        start = moment(previousDate)
      }
      if (nextDate) {
        end = moment(nextDate)
      }
      return date < start || date > end
    },
    /**
     * @description: 禁选结束日期了的日期
     * @param {*} date
     * @param {*} record
     * @param {*} rowIndex
     * @param {*} isStart true开始日期 false 结束日期
     * @return {*}
     */
    disabledTEndDate (date, record, rowIndex, isStart) {
      const { pStartDate, pEndDate } = this
      let start = pStartDate
      let end = pEndDate
      const previousDate = this.findRecentlyDate(true, rowIndex, isStart)
      const nextDate = this.findRecentlyDate(false, rowIndex, isStart)
      if (previousDate) {
        start = moment(previousDate)
      }
      if (nextDate) {
        end = moment(nextDate)
      }
      return date < start || date > end
    },
    onDateChange (date, dateString, record, name) {
      record[name] = dateString
      new Promise((resolve, reject) => {
        let isRepeat = false
        this.saveData.forEach((item) => {
          if (item.id === record.id) {
            item = record
            isRepeat = true
          }
        })
        resolve(isRepeat)
      }).then((resolve, reject) => {
        if (!resolve) {
          this.saveData.push(record)
        }
      })
    },
    onCellChange (value, record, name) {
      record[name] = value
      const promise = new Promise((resolve, reject) => {
        let isRepeat = false
        this.saveData.forEach((item) => {
          if (item.id === record.id) {
            item = record
            isRepeat = true
          }
        })
        resolve(isRepeat)
      })
      promise.then((resolve, reject) => {
        if (!resolve) {
          this.saveData.push(record)
        }
      })
    },
    // 保存规划阶段
    onSaveList () {
      const values = this.saveData
      if (values.length <= 0) {
        this.$message.info('未进行任何编辑')
        return
      }
      const result = this.verifyTableData()
      if (result && result.result) {
        this.$message.warning(result.message)
        return
      }
      values.forEach((item) => {
        item['autoAdd'] = this.checked
        item['type'] = this.templateType ? 1 : 0
        item.id = item.stageId
      })
      this.isSaveDisabled = true
      this.$http
        .post('/stage/editList', values)
        .then((res) => {
          if (res.success && res.data) {
            this.$message.success('保存成功')
            this.$emit('ok')
            this.$refs.table.refresh(false)
            this.saveData = []
            this.$emit('refresh', false, true)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          }
        })
        .finally((res) => {
          this.isSaveDisabled = false
        })
    },
    /**
     * @description: 重置数据 （给予父级组件调用）
     * @param {*}
     * @return {*}
     */
    resetData () {
      this.saveData = []
    },
    // 删除规划阶段
    del (record) {
      if (!record.id) {
        const index = this.tableData.findIndex((item) => {
          return item._XID === record._XID
        })
        const saveDataIndex = this.saveData.findIndex((item) => {
          return item._XID === record._XID
        })
        if (index > -1) {
          this.tableData.splice(index, 1)
          if (saveDataIndex > -1) {
            this.saveData.splice(saveDataIndex, 1)
          }
          this.$message.success('删除成功')
          this.resetDisable()
          this.isShowConfig = this.tableData.length <= 0
        }
      } else {
        record.id = record.stageId
        this.$http
          .post('/stage/del', record)
          .then((res) => {
            if (res.success && res.data) {
              const index = this.tableData.findIndex((item) => {
                return item.id === record.id
              })
              if (index > -1) {
                this.tableData.splice(index, 1)
              }
              const saveDataIndex = this.saveData.findIndex((item) => {
                return item.id === record.id
              })
              if (saveDataIndex > -1) {
                this.saveData.splice(saveDataIndex, 1)
              }
              this.$message.success('删除成功')
              this.$emit('refresh', true, true)
              this.resetDisable()
            } else {
              this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
            }
          })
          .finally((res) => {
            this.$emit('change')
            this.isShowConfig = this.tableData.length <= 0
          })
      }
    },
    refresheTable () {
      this.$refs.table.refresh(true)
    },
    refresheProjectData (projectData) {
      this.projectData = projectData
      this.pStartDate = projectData.beginDate
      this.pEndDate = projectData.endDate
    },
    // 添加规划阶段
    addStage (isInsert, rowIndex) {
      if (this.tableData.length >= this.stages.length) {
        this.$message.info('没有可以添加的阶段')
        return
      }
      // this.filterStages(!isInsert)
      const addRow = {
        beginDate: null,
        endDate: null,
        workDesc: null,
        remark: null,
        projectId: this.projectId,
        isAdd: true
      }
      if (isInsert) {
        this.tableData.splice(rowIndex, 0, addRow)
        this.saveData.splice(rowIndex, 0, addRow)
      } else {
        this.tableData.push(addRow)
        this.saveData.push(addRow)
      }
      this.isShowConfig = this.tableData.length <= 0
    },
    selectChange (value, option, row) {
      new Promise((resolve, reject) => {
        let isRepeat = false
        this.saveData.forEach((item) => {
          if (item.stageKey === row.stageKey) {
            item = row
            item['stageType'] = this.stagesKV[value]
            isRepeat = true
          }
        })
        resolve(isRepeat)
      }).then((resolve, reject) => {
        if (!resolve) {
          row['stageType'] = this.stagesKV[value]
        }
      })
      this.resetDisable()
    },
    /**
     * @description: 重置禁用阶段选择
     */
    resetDisable () {
      for (const stage of this.stages) {
        delete stage['disabled']
      }
      for (const item of this.tableData) {
        for (const stage of this.stages) {
          if (item.stageKey === stage.value) {
            this.$set(stage, 'disabled', true)
          }
        }
      }
    },
    /**
     * @description: 验证表格必填列
     * @return  object
     */
    verifyTableData () {
      let count = 0
      for (const item of this.tableData) {
        count++
        const verifyAry = ['stageKey', 'beginDate', 'endDate']
        for (const value of verifyAry) {
          if (!item[value]) {
            return {
              result: true,
              message: `第${count}行: 标题带*的要必填！`
            }
          }
        }
      }
    },
    /**
     * @description: 查找最近一个日期
     * @param {*} direction true向上找 false 向下找
     * @param {*} rowIndex
     * @param {*} isStart true开始日期 false 结束日期
     * @return {*}
     */
    findRecentlyDate (direction, rowIndex, isStart) {
      const { tableData } = this
      if (!tableData.length) {
        return
      }
      const maxlength = tableData.length - 1
      const currentIndex = rowIndex
      if (direction) {
        // 向上找
        while (rowIndex > -1) {
          if (!isStart && currentIndex === rowIndex) {
            const { beginDate } = tableData[rowIndex]
            if (beginDate) {
              return beginDate
            }
            rowIndex--
            continue
          }
          const currentRow = isStart
            ? rowIndex - 1 < -1
              ? tableData[0]
              : tableData[rowIndex - 1]
            : tableData[rowIndex]
          if (!currentRow) return
          const { beginDate, endDate } = currentRow
          if (endDate) {
            return endDate
          }
          if (beginDate) {
            return beginDate
          }
          rowIndex--
        }
      } else {
        // 向下找
        while (rowIndex <= maxlength) {
          if (!isStart && rowIndex === tableData.length - 1) {
            return undefined
          }
          if (isStart && currentIndex === rowIndex) {
            const { endDate } = tableData[rowIndex]
            if (endDate) {
              return endDate
            }
            rowIndex++
            continue
          }
          const currentRow = isStart
            ? tableData[rowIndex]
            : rowIndex + 1 > maxlength
              ? tableData[maxlength]
              : tableData[rowIndex + 1]
          if (!currentRow) return
          const { beginDate, endDate } = currentRow
          if (beginDate) {
            return beginDate
          }
          if (endDate) {
            return endDate
          }
          rowIndex++
        }
      }
    },
    /**
     * @description: 日期面板默认选中日期
     * @param {*} record 当前行的数据
     * @param {*} rowIndex 当前的索引
     * @param {*} direction true向上找 false 向下找
     * @param {*} isStart true开始日期 false 结束日期
     * @return {*}
     */
    defaultPickerValue (record, rowIndex, direction, isStart) {
      const { pStartDate } = this
      if (direction) {
        const previousDate = this.findRecentlyDate(direction, rowIndex, isStart)
        if (previousDate) {
          return moment(previousDate).add(1, 'days')
        } else {
          return moment(pStartDate)
        }
      }
    },
    /**
     * @description: 设置挂载DOM元素
     */
    setMountDOM () {
      return this.$refs.stageTab
    },
    onCreateStages () {
      const data = []

      if (this.radioStageType === 1) {
        for (const key in this.proGetStage) {
          if ([400, 500].indexOf(+key) < 0) {
            const item = {
              stageKey: `${key}`,
              stageType: `${this.proGetStage[key]}`,
              beginDate: null,
              endDate: null,
              workDesc: desc[key],
              remark: undefined,
              projectId: this.projectId
            }
            data.push(item)
          }
        }
      } else if (this.radioStageType === 2) {
        for (const key in this.proGetStage) {
          const item = {
            stageKey: `${key}`,
            stageType: `${this.proGetStage[key]}`,
            beginDate: null,
            endDate: null,
            workDesc: desc[key],
            remark: undefined,
            projectId: this.projectId
          }
          data.push(item)
        }
      }
      if (data && data.length) {
        this.tableData.splice(0)
        this.saveData = []
        data.forEach((item) => {
          this.tableData.push(item)
          this.saveData.push(item)
        })
      }
    },
    change2Refresh () {
      this.$refs.table.refresh(true)
    }
  }
}
</script>

<style lang="less" scoped>
#stageTab_stageTable /deep/ .vxe-grid .c--tooltip {
  width: 100% !important;
}
</style>
