<template>
  <a-modal
    :title="title"
    :visible="visible"
    :width="600"
    :bodyStyle="{minHeight:'300px'}"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    @ok="handleOk"
    @cancel="closeModal"
  >
    <div style="text-align: center;padding-bottom: 20px;">
      <a-radio-group v-model="mode" @change="radioChange">
        <a-radio :value="0">
          连续
        </a-radio>
        <a-radio :value="1">
          间隔
        </a-radio>
      </a-radio-group>
    </div>
    <template>
      <a-form :form="form" v-show="mode === 0">
        <a-row :gutter="24" v-if="!hasAttendance">
          <a-col :span="24">
            <a-form-item label="日工时" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
              <a-input-number
                placeholder="请输入日工时"
                v-decorator="['dayHour', { rules: [{ required: true, message: '请输入日工时' }] }]"
                :precision="2"
                :max="freshType === 'equipment' ? 24 : 14"
                :min="1"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="开始日期" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
              <a-select
                showSearch
                v-decorator="['startDay']"
                placeholder="请选择开始日期"
              >
                <a-select-option v-for="day in maxDay" :key="day">{{ day }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item label="不跳过节假日" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
              <a-checkbox :checked="skipHoliday" @change="handleChange" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-if="freshType === 'equipment'">
          <a-col :span="24">
            <a-form-item label="日期不重复" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
              <a-checkbox :checked="noRepeat" @change="handleDayChange" />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </template>
    <template v-if="mode === 1">
      <a-spin tip="请稍后..." :spinning="spin">
        <a-checkbox :checked="skipHoliday" @change="handleChange" style="margin-right:20px">不跳过节假日</a-checkbox>
        <template v-if="freshType === 'equipment'">
          <a-checkbox :checked="noRepeat" @change="handleDayChange">日期不重复</a-checkbox>
        </template>
        <br/><br/>
        <vxe-grid
          ref="table"
          :data="tableDatas"
          border
          size="small"
          resizable
          auto-resize
          highlight-hover-row
          show-overflow
          show-header-overflow
          highlight-current-row
          header-align="center"
        >
          <vxe-table-column type="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
          <vxe-table-column title="工时" field="totalHour" min-width="120" align="left" header-align="center">
            <template #header>
              <span class="required">*</span>工时
            </template>
            <template #default="{row}">
              <a-input-number
                placeholder="工时"
                v-model="row.totalHour"
                :precision="2"
                :max="maxHour"
                :min="1"
              />
            </template>
          </vxe-table-column>
          <vxe-table-column title="开始日期" field="ename" min-width="120" align="left" header-align="center">
            <template #header>
              <span class="required">*</span>开始日期
            </template>
            <template #default="{row,rowIndex}">
              <a-select
                showSearch
                style="width:100%"
                v-model="row.startDay"
                @change="selectChange(rowIndex)"
                placeholder="开始日期"
              >
                <a-select-option :disabled="filterMap[rowIndex] && (day >=filterMap[rowIndex].max || day <= filterMap[rowIndex].min)" v-for="day in maxDay" :key="day">{{ day }}</a-select-option>
              </a-select>
            </template>
          </vxe-table-column>
          <vxe-table-column title="日工时" field="emodal" min-width="120" align="left" header-align="center">
            <template #header>
              <span class="required">*</span>日工时
            </template>
            <template #default="{row}">
              <a-input-number
                placeholder="日工时"
                v-model="row.dayHour"
                :precision="2"
                :max="freshType === 'equipment' ? 24 : 14"
                :min="1"
              />
            </template>
          </vxe-table-column>
          <vxe-table-column title="操作" width="100" align="center" header-align="center">
            <template #default="{row,rowIndex}">
              <a-popconfirm title="您确定要删除？" placement="topLeft" @confirm="onConfirm(row,rowIndex)">
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
          <template #bottom>
            <a-button
              :disabled="maxDay === tableDatas.length"
              title="添加"
              type="dashed"
              style="width: 100%;font-weight: bolder"
              @click="onAdd">+</a-button>
          </template>
        </vxe-grid>
      </a-spin>
    </template>
  </a-modal>
</template>
<script>
import moment from 'moment'
const configType = {
  'attendance': 0,
  'equipment': 1
}
export default {
  props: {
    url: {
      type: String,
      required: true
    },
    freshType: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      filterMap: {},
      mode: 0,
      skipHoliday: false,
      noRepeat: true,
      title: '',
      visible: false,
      confirmLoading: false,
      params: {},
      hasAttendance: false,
      form: this.$form.createForm(this),
      tableDatas: [],
      maxDay: 0,
      maxHour: 672,
      spin: false
    }
  },
  methods: {
    moment,
    selectChange () {
      const tempFilter = {}
      this.tableDatas.forEach((item, index) => {
        tempFilter[index] = {}
        if (index === 0) {
          tempFilter[index].min = 0
        } else {
          tempFilter[index].min = this.tableDatas[index - 1].startDay
        }
        if (index === this.tableDatas.length - 1) {
          tempFilter[index].max = this.maxDay + 1
        } else {
          tempFilter[index].max = this.tableDatas[index + 1].startDay
        }
      })
      this.filterMap = tempFilter
    },
    radioChange (e) {
      this.skipHoliday = false
      this.noRepeat = true
      if (e && e.target.value === 1) {
        this.getRdHourConfig()
      }
    },
    getRdHourConfig () {
      if (this.tableDatas && this.tableDatas.length) {
        return
      }
      this.spin = true
      this.$http.get('/projectEquipment/getRdHourConfig', { params: Object.assign(this.params, { type: configType[this.freshType] }) }).then(res => {
        if (res.success) {
          if (res.data) {
            this.tableDatas = JSON.parse(res.data)
          } else {
            this.tableDatas = []
          }
        } else {
          this.$message.error(res.errorMessage && res.errorMessage !== '系统异常' ? res.errorMessage : '获取研发配置失败，请联系管理员。')
        }
      }).finally(() => {
        this.spin = false
      })
    },
    onAdd () {
      this.tableDatas.push({ totalHour: undefined, startDay: undefined, dayHour: undefined })
      this.selectChange()
    },
    onConfirm (row, index) {
      this.tableDatas.splice(index, 1)
      this.selectChange()
    },
    set (data, hasAttendance) {
      this.mode = 0
      this.tableDatas = []
      this.hasAttendance = hasAttendance
      this.params = { ...data }
      this.maxDay = moment(data.month).daysInMonth()
      this.maxHour = this.maxDay * 24
      this.params.startDay = 1
      this.params.dayHour = 8
      this.skipHoliday = false
      this.noRepeat = true
      this.title = `设置[${data.month.format('YYYY年MM月')}]研发工时`
      this.visible = true
      this.$nextTick(() => {
        this.$initForm(this.form, this.params)
      })
    },
    handleOk () {
      if (this.mode === 0) {
        this.normalFresh()
      } else if (this.mode === 1) {
        this.termFresh()
      }
    },
    fresh () {
      this.confirmLoading = true
      this.$http.post(this.url, this.params)
        .then(res => {
          if (res.data) {
            this.$message.success('刷新成功')
            this.$emit('ok')
            this.closeModal()
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '刷新失败')
          }
        }).finally(() => {
          this.confirmLoading = false
        })
    },
    normalFresh () {
      this.confirmLoading = true
      this.form.validateFields((error, values) => {
        if (error) {
          this.confirmLoading = false
          return
        }
        Object.assign(this.params, values)
        delete this.params.type
        delete this.params.configs
        this.params.skipHoliday = this.skipHoliday
        this.params.noRepeat = this.noRepeat
        this.params.startDay = moment(`${this.params.month.format('YYYY-MM')}-${this.params.startDay < 10 ? '0' + this.params.startDay : this.params.startDay} 00:00:00`)
        this.fresh()
      })
    },
    termFresh () {
      this.confirmLoading = true
      if (!this.tableDatas || !this.tableDatas.length) {
        this.confirmLoading = false
        this.$message.warning('请添加分配研发工时配置。')
        return
      }
      if (!this.checkEmpty()) {
        this.confirmLoading = false
        return
      }
      const datas = [...this.tableDatas]
      if (datas.length > 1) {
        for (let i = 0; i < datas.length; i++) {
          const next = datas[i + 1]
          if (!next) {
            break
          }
          const current = datas[i]
          const currentMaxDay = Math.min(Number(current.startDay) - 1 + Math.ceil(current.totalHour / current.dayHour), this.maxDay)
          if (next.startDay <= currentMaxDay) {
            this.$message.warning(`第[${i + 1}]行预计分配到第[${currentMaxDay}]天，超过第[${i + 2}]行的开始日期[${next.startDay}]`)
            this.confirmLoading = false
            return
          }
        }
      }
      this.params.type = configType[this.freshType]
      this.params.configs = datas
      this.params.skipHoliday = this.skipHoliday
      this.params.noRepeat = this.noRepeat
      this.fresh()
    },
    checkEmpty () {
      var total = 0
      for (let i = 0; i < this.tableDatas.length; i++) {
        if (!this.tableDatas[i].totalHour) {
          this.$message.warning(`第${i + 1}行，工时不能为空`)
          return false
        }
        if (!this.tableDatas[i].startDay) {
          this.$message.warning(`第${i + 1}行，请选择开始日期`)
          return false
        }
        if (!this.tableDatas[i].dayHour) {
          this.$message.warning(`第${i + 1}行，日工时不能为空`)
          return false
        }
        total += Number(this.tableDatas[i].totalHour)
      }
      if (total > this.maxHour) {
        this.$message.warning(`工时合计不能超过当月总小时[${this.maxHour}]`)
        return false
      }
      return true
    },
    handleChange (e) {
      this.skipHoliday = e.target.checked
    },
    handleDayChange (e) {
      this.noRepeat = e.target.checked
    },
    closeModal () {
      this.visible = false
    }
  }
}
</script>
<style scoped>
.required {
  color: red;
}
</style>
