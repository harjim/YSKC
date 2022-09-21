<template>
  <a-modal
    :width="960"
    :visible="visible"
    :title="title"
    @ok="saveRow"
    @cancel="visible = false"
    :maskClosable="false"
    :footer="null"
  >
    <a-table :dataSource="data" :pagination="false" rowKey="id" size="small">
      <a-table-column
        align="left"
        title="开始月份"
        data-index="startMonth"
        key="startMonth"
        :width="120"
      >
        <template slot-scope="text,record">
          <a-month-picker
            :allowClear="false"
            v-if="record.editable"
            :defaultValue="moment(text)"
            format="YYYY-MM"
            placeholder="开始月份"
            @change="(date,dateStrings)=>onStartMonthChange(dateStrings,record)"
          />
          <div v-else>{{ text }}</div>
        </template>
      </a-table-column>
      <a-table-column align="left" data-index="endowment" key="endowment">
        <a-row slot="title">
          <a-col :span="4">养老</a-col>
          <a-col :span="4">医疗</a-col>
          <a-col :span="4">失业</a-col>
          <a-col :span="4">工伤</a-col>
          <a-col :span="4">生育</a-col>
          <a-col :span="4">公积金</a-col>
        </a-row>
        <template slot-scope="text,record">
          <p>个人</p>
          <a-row v-if="record.editable">
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:48px;"
                size="small"
                :value="record.endowment"
                placeholder="养老"
                @change="e => handleChange(e.target.value, record, 'endowment')"
              />
            </a-col>
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:48px;"
                size="small"
                :value="record.medical"
                placeholder="医疗"
                @change="e => handleChange(e.target.value, record, 'medical')"
              />
            </a-col>
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:48px;"
                size="small"
                :value="record.unemployment"
                placeholder="失业"
                @change="e => handleChange(e.target.value, record, 'unemployment')"
              />
            </a-col>
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:48px;"
                size="small"
                :value="record.injury"
                placeholder="工伤"
                @change="e => handleChange(e.target.value, record, 'injury')"
              />
            </a-col>
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:48px;"
                size="small"
                :value="record.maternity"
                placeholder="生育"
                @change="e => handleChange(e.target.value, record, 'maternity')"
              />
            </a-col>
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:60px;"
                size="small"
                :value="record.house"
                placeholder="公积金"
                @change="e => handleChange(e.target.value, record, 'house')"
              />
            </a-col>
          </a-row>
          <a-row v-else>
            <a-col :span="4">{{ record.endowment }}</a-col>
            <a-col :span="4">{{ record.medical }}</a-col>
            <a-col :span="4">{{ record.unemployment }}</a-col>
            <a-col :span="4">{{ record.injury }}</a-col>
            <a-col :span="4">{{ record.maternity }}</a-col>
            <a-col :span="4">{{ record.house }}</a-col>
          </a-row>
          <br />
          <p>公司</p>
          <a-row v-if="record.editable">
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:48px;"
                size="small"
                :value="record.endowmentOfCom"
                placeholder="养老"
                @change="e => handleChange(e.target.value, record, 'endowmentOfCom')"
              />
            </a-col>
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:48px;"
                size="small"
                :value="record.medicalOfCom"
                placeholder="医疗"
                @change="e => handleChange(e.target.value, record, 'medicalOfCom')"
              />
            </a-col>
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:48px;"
                size="small"
                :value="record.unemploymentOfCom"
                placeholder="失业"
                @change="e => handleChange(e.target.value, record, 'unemploymentOfCom')"
              />
            </a-col>
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:48px;"
                size="small"
                :value="record.injuryOfCom"
                placeholder="工伤"
                @change="e => handleChange(e.target.value, record, 'injuryOfCom')"
              />
            </a-col>
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:48px;"
                size="small"
                :value="record.maternityOfCom"
                placeholder="生育"
                @change="e => handleChange(e.target.value, record, 'maternityOfCom')"
              />
            </a-col>
            <a-col :span="4">
              <a-input
                key="endowment"
                style="margin: -5px 0;width:60px;"
                size="small"
                :value="record.houseOfCom"
                placeholder="公积金"
                @change="e => handleChange(e.target.value, record, 'houseOfCom')"
              />
            </a-col>
          </a-row>
          <a-row v-else>
            <a-col :span="4">{{ record.endowmentOfCom }}</a-col>
            <a-col :span="4">{{ record.medicalOfCom }}</a-col>
            <a-col :span="4">{{ record.unemploymentOfCom }}</a-col>
            <a-col :span="4">{{ record.injuryOfCom }}</a-col>
            <a-col :span="4">{{ record.maternityOfCom }}</a-col>
            <a-col :span="4">{{ record.houseOfCom }}</a-col>
          </a-row>
        </template>
      </a-table-column>
      <a-table-column align="center" title="操作" data-index="id" key="id" :width="120">
        <template slot-scope="text,record">
          <template v-if="record.editable">
            <span v-if="record.isNew">
              <a @click="saveRow(record)">添加</a>
              <a-divider type="vertical" />
              <a-popconfirm title="是否要删除此行？" @confirm="remove(record)">
                <a>删除</a>
              </a-popconfirm>
            </span>
            <span v-else>
              <a @click="saveRow(record)">保存</a>
              <a-divider type="vertical" />
              <a @click="cancel(record)">取消</a>
            </span>
          </template>
          <span v-else>
            <a @click="toggle(record)">编辑</a>
            <a-divider type="vertical" />
            <a-popconfirm title="是否要删除此行？" @confirm="remove(record)">
              <a>删除</a>
            </a-popconfirm>
          </span>
        </template>
      </a-table-column>
    </a-table>
    <a-button
      style="width: 100%; margin-top: 16px; margin-bottom: 8px"
      type="dashed"
      icon="plus"
      @click="newConfig"
    >新增</a-button>
  </a-modal>
</template>

<script>
import moment from 'moment'
export default {
  name: 'InsuranceModal',
  deptTree: [],
  data () {
    return {
      title: '',
      visible: false,
      companyId: 0,
      deptId: 0,
      enumber: '',
      data: []
    }
  },
  created () {
  },
  methods: {
    moment,
    onStartMonthChange (dateString, record) {
      record.startMonth = dateString
    },
    open (data, configType) {
      if (configType === 0) {
        this.title = data.ename + `--五险一金比例(百分比)`
        this.companyId = data.companyId
        this.deptId = data.deptId
        this.enumber = data.enumber
      } else if (configType === 1) {
        this.title = data.deptName + `--五险一金比例(百分比)`
        this.companyId = data.companyId
        this.deptId = data.deptId
        this.enumber = ''
      } else if (configType === 2) {
        this.title = data.companyName + `--五险一金比例(百分比)`
        this.companyId = data.companyId
        this.deptId = 0
        this.enumber = ''
      }

      this.$http.get('/insuranceConfig/queryInsuranceConfig', { params: { companyId: this.companyId, deptId: this.deptId, enumber: this.enumber } })
        .then(res => {
          for (let i = 0; i < res.data.length; i++) {
            const e = res.data[i]
            e.editable = false
            e.isNew = false
          }
          this.$set(this, 'data', res.data)
          return res.data
        })
      this.visible = true
    },
    newConfig () {
      this.data.push({
        startMonth: moment(),
        endowment: 0,
        medical: 0,
        unemployment: 0,
        injury: 0,
        maternity: 0,
        house: 0,
        endowmentOfCom: 0,
        medicalOfCom: 0,
        unemploymentOfCom: 0,
        injuryOfCom: 0,
        maternityOfCom: 0,
        houseOfCom: 0,
        editable: true,
        isNew: true
      })
    },
    handleChange (value, record, column) {
      record[column] = value
    },
    toggle (record) {
      record.cacheData = { ...record }
      this.$set(record, 'editable', true)
      // record.editable = !record.editable
    },
    saveRow (record) {
      record.companyId = this.companyId
      record.deptId = this.deptId
      record.enumber = this.enumber
      this.$http.post('/insuranceConfig/save', record)
        .then(res => {
          if (res.success && res.data) {
            this.$emit('ok', record.isNew)
            this.$set(record, 'editable', false)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          }
        })
    },
    remove (record) {
      if (!record.isNew) {
        this.$http.post('/insuranceConfig/del', record)
          .then(res => {
            this.$emit('ok', false)
          })
      }
      for (let index = 0; index < this.data.length; index++) {
        const element = this.data[index]
        if (element === record) {
          this.data.splice(index, 1)
          break
        }
      }
    },
    cancel (record) {
      Object.assign(record, record.cacheData)
      delete record.cacheData
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
