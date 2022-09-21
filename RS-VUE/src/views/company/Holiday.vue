<template>
  <a-card :bordered="false" :bodyStyle="{ height: '100%', overflow: 'auto' }">
    <a-spin :spinning="spinning" tip="请稍后...">
      <div style="text-align:center;padding:10px;" v-if="$auth('company:holiday:search')">
        <year-select style="width:200px;font-size:20px;height:28px;" @change="y=>currentYear = y" :value="currentYear" :allowClear="false"/>
      </div>
      <div>
        <a-row v-if="$auth('company:holiday:edit')">
          <div style="float:left;">
            <div class="header-row">
              <a-button @click="saveHoliday" :disabled="noModify" type="primary" style="left:7px;margin-right:16px;">保存</a-button>
              <a-button @click="cancelModify" :disabled="noModify" type="primary">取消</a-button>
              <span style="color:rgb(255,68,51);font-size:12px;padding-left:10px;">红色为假期</span>
            </div>
          </div>
          <div style="float:right;">
            <a-button @click="selectWeekEnd" type="primary" style="right:7px;">批量选中周末</a-button>
          </div>
        </a-row>
        <a-row :span="24">
          <template v-for="m in 12">
            <a-col :span="8" :key="m" style="padding:5px;">
              <div style="border: 1px solid #d9d9d9;borderRadius: 4px;height:330px;">
                <a-calendar :fullscreen="false" :value="months[m]">
                  <template v-slot:headerRender>
                    <div style="text-align:center;font-size:18px;">{{ currentYear }}-{{ m | ZeroFormat }}</div>
                  </template>
                  <template v-slot:dateFullCellRender="date">
                    <a style="color:#000" @click="clickDate(date,m)"><div v-if="date.month()+1 === m" style="text-align:center;width:100%;heigth:100%;" :class="holidays[m] && holidays[m].includes(date.date()) ? 'holiday': 'normal'">{{ date.date() }}<br/>{{ lunarDay(date).mark }}</div></a>
                  </template>
                </a-calendar>
              </div>
            </a-col>
          </template>
        </a-row>
      </div>
    </a-spin>
  </a-card>
</template>

<script>
import YearSelect from '@/components/YearSelect'
import moment from 'moment'
import { lunarDay } from '@/utils/calendar'
export default {
  name: 'Holiday',
  components: { YearSelect },
  data () {
    return {
      currentYear: new Date().getFullYear(),
      months: {},
      holidays: {},
      spinning: false,
      noModify: true,
      idMap: {},
      editMap: {},
      bakData: undefined
    }
  },
  created () {
    this.changeMonth()
    this.getHoliday()
  },
  mounted () {
  },
  watch: {
    currentYear (y) {
      this.changeMonth()
      this.getHoliday()
    }
  },
  methods: {
    lunarDay,
    moment,
    changeMonth () {
      if (this.currentYear) {
        const months = {}
        for (let i = 1; i <= 12; i++) {
          months[i] = moment(`${this.currentYear}-${i < 10 ? '0' + i : i}-01`)
        }
        this.months = months
      }
    },
    getHoliday () {
      this.noModify = true
      this.spinning = true
      this.editMap = {}
      this.bakData = undefined
      this.$http.get('/companyHoliday/getHolidays', { params: { year: this.currentYear } }).then(res => {
        const temp = {}
        const tempIdMap = {}
        res.data.forEach(d => {
          if (d.holidays) {
            const m = moment(d.month).month() + 1
            temp[m] = d.holidays.split(',').map(a => Number(a))
            tempIdMap[m] = d.id
          }
        })
        this.holidays = temp
        this.idMap = tempIdMap
      }).finally(() => {
        this.spinning = false
      })
    },
    clickDate (d, m) {
      if (this.noModify) {
        this.bakData = this.$deepClone(this.holidays)
      }
      this.editMap[m] = true
      this.noModify = false
      const day = d.date()
      let tempMonthHoliday = this.holidays[m]
      if (!tempMonthHoliday) {
        tempMonthHoliday = []
      }
      const index = tempMonthHoliday.indexOf(day)
      if (index >= 0) {
        tempMonthHoliday.splice(index, 1)
      } else {
        tempMonthHoliday.push(day)
      }
      this.$set(this.holidays, m, tempMonthHoliday)
    },
    saveHoliday () {
      this.spinning = true
      const params = []
      for (const m in this.editMap) {
        if (this.idMap[m] || this.holidays[m].length) {
          params.push({ month: `${this.currentYear}-${m < 10 ? '0' + m : m}-01`, holidays: this.holidays[m].join(','), id: this.idMap[m] })
        }
      }
      if (!params.length) {
        this.spinning = false
        return
      }
      this.$http.post('/companyHoliday/saveHoliday', params).then(res => {
        this.spinning = false
        if (res.success && res.data) {
          this.$message.success('保存成功')
          this.getHoliday()
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
        }
      })
    },
    cancelModify () {
      this.noModify = true
      this.editMap = {}
      this.holidays = { ...this.bakData }
      this.bakData = undefined
    },
    selectWeekEnd () {
      const begin = moment(`${this.currentYear}-01-01`)
      const sub = 7 - begin.format('E')
      const defaultWeekDays = { '1': [] }
      if (sub > 0 && sub <= 6) {
        begin.add(sub - 1, 'd')
      } else {
        const beginDate = begin.date()
        defaultWeekDays['1'].push(beginDate)
        if (sub === 1) {
          defaultWeekDays['1'].push(beginDate + 1)
        }
        begin.add(6, 'd')
      }
      while (begin.year() === this.currentYear) {
        this.addWeekEnd(defaultWeekDays, begin)
        begin.add(1, 'd')
        this.addWeekEnd(defaultWeekDays, begin)
        begin.add(6, 'd')
      }
      this.noModify = false
      if (!this.bakData) {
        this.bakData = this.$deepClone(this.holidays)
      }
      for (const m in defaultWeekDays) {
        this.editMap[m] = true
        if (this.holidays[m]) {
          defaultWeekDays[m].forEach(d => {
            if (!this.holidays[m].includes(d)) {
              this.holidays[m].push(d)
            }
          })
        } else {
          this.$set(this.holidays, m, [...defaultWeekDays[m]])
        }
      }
    },
    addWeekEnd (data, d) {
      const m = d.month() + 1
      if (!data[m]) {
        data[m] = []
      }
      data[m].push(d.date())
    }
  }
}
</script>

<style scoped>

  /* background-color:rgb(255,240,240); */
.holiday{
  color: rgb(255,68,51);
  font-weight: 600;
}
.normal{
  background-color: #fff;
  color: #000;
}
.header-row  {
    display: flex;
  justify-self:end;
  align-items: flex-end;
}
</style>
