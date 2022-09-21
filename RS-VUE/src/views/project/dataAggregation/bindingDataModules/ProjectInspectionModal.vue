<template>
  <a-modal
    :width="1300"
    :visible="visible"
    :title="title"
    :footer="null"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <div>
      <a-form layout="inline">
        <a-form-item label="摘要">
          <a-input v-model="summary" placeholder="请输入摘要" />
        </a-form-item>
        <a-form-item label="部门">
          <a-input v-model="deptName" placeholder="请输入部门" />
        </a-form-item>
        <a-form-item label="凭证号">
          <a-input v-model="accNumber" placeholder="请输入凭证号" />
        </a-form-item>
        <template v-if="this.selectType.toString() == '60400'">
          <a-form-item label="工号">
            <a-input v-model="enumber" placeholder="请输入工号" />
          </a-form-item>
          <a-form-item label="姓名">
            <a-input v-model="ename" placeholder="请输入姓名" />
          </a-form-item>
          <a-form-item label="备注">
            <a-input v-model="remark" placeholder="请输入备注" />
          </a-form-item>
        </template>
        <a-form-item label="备注">
          <a-input v-model="remark" placeholder="请输入备注" />
        </a-form-item>
        <a-form-item>
          <a-button v-if="$auth('company:equipment:search')" type="primary" @click="refreshData">查询</a-button>
        </a-form-item>
      </a-form>
    </div>
    <div>
      <ystable
        ref="table"
        rowId="id"
        :toolbar="{ zoom: true, custom: true, refresh: true }"
        @completed="({ data }) => completed(data)"
        :queryUrl="
          this.selectType.toString() === '60400' ? '/inspection/queryTravelFee' : '/inspection/queryInspectionByTerm'
        "
        :params="
          this.selectType.toString() === '60400'
            ? {
              projectId: this.id,
              types: this.selectType,
              projectMonth:
                this.projectMonth === '' ? undefined : moment(this.projectMonth + '-' + '01' + ' 00:00:00'),
              summary: this.summary,
              deptName: this.deptName,
              enumber: this.enumber,
              ename: this.ename,
              accNumber: this.accNumber,
              remark: this.remark
            }
            : {
              projectId: this.id,
              types: this.selectType,
              projectMonth:
                this.projectMonth === '' ? undefined : moment(this.projectMonth + '-' + '01' + ' 00:00:00'),
              summary: this.summary,
              deptName: this.deptName,
              accNumber: this.accNumber,
              remark: this.remark
            }
        "
        @checkbox-change="onSelectChange"
        @checkbox-all="onSelectChange"
        show-overflow="title"
      >
        <template v-slot:buttons>
          <span>
            &nbsp;&nbsp;总费用:
            <a
              style="font-weight: 600"
            >{{ totals.total.select ? totals.total.select.toFixed(2) : '-' }}/{{
              totals.total.sum ? totals.total.sum.toFixed(2) : '-'
            }}</a
            >
          </span>
          <span v-if="selectType.length == 3">
            &nbsp;&nbsp;软件摊销:
            <a style="font-weight: 600">
              {{ totals.software.select ? totals.software.select.toFixed(2) : '-' }}/{{
                totals.software.sum ? totals.software.sum.toFixed(2) : '-'
              }}
            </a>
            &nbsp;&nbsp;专利摊销:
            <a style="font-weight: 600">
              {{ totals.patent.select ? totals.patent.select.toFixed(2) : '-' }}/{{
                totals.patent.sum ? totals.patent.sum.toFixed(2) : '-'
              }}
            </a>
            &nbsp;&nbsp;其他摊销:
            <a style="font-weight: 600">
              {{ totals.otherAmortization.select ? totals.otherAmortization.select.toFixed(2) : '-' }}/{{
                totals.otherAmortization.sum ? totals.otherAmortization.sum.toFixed(2) : '-'
              }}
            </a>
          </span>
          <span v-if="selectType.length === 5">
            &nbsp;&nbsp;资料:
            <a style="font-weight: 600">
              {{ totals.book.select ? totals.book.select.toFixed(2) : '-' }}/{{
                totals.book.sum ? totals.book.sum.toFixed(2) : '-'
              }}
            </a>
            &nbsp;&nbsp;研发成果:
            <a style="font-weight: 600">
              {{ totals.rdProduction.select ? totals.rdProduction.select.toFixed(2) : '-' }}/{{
                totals.rdProduction.sum ? totals.rdProduction.sum.toFixed(2) : '-'
              }}
            </a>
            &nbsp;&nbsp;知识产权:
            <a style="font-weight: 600">
              {{ totals.copyright.select ? totals.copyright.select.toFixed(2) : '-' }}/{{
                totals.copyright.sum ? totals.copyright.sum.toFixed(2) : '-'
              }}
            </a>
            &nbsp;&nbsp;福利：
            <a style="font-weight: 600">
              {{ totals.benefits.select ? totals.benefits.select.toFixed(2) : '-' }}/{{
                totals.benefits.sum ? totals.benefits.sum.toFixed(2) : '-'
              }}
            </a>
            &nbsp;&nbsp;其他:
            <a style="font-weight: 600">
              {{ totals.other.select ? totals.other.select.toFixed(2) : '-' }}/{{
                totals.other.sum ? totals.other.sum.toFixed(2) : '-'
              }}
            </a>
          </span>
        </template>
        <vxe-table-column type="checkbox" :width="40" />
        <vxe-table-column title="摘要" field="summary" :min-width="200" show-header-overflow />
        <vxe-table-column
          align="center"
          title="记账日期"
          field="accDate"
          key="accDate"
          :width="110"
          remoteSort
          show-header-overflow
        />
        <vxe-table-column
          align="center"
          title="凭证号"
          field="accNumber"
          :width="160"
          remoteSort
          show-header-overflow
        />
        <vxe-table-column
          align="center"
          title="费用类型"
          field="type"
          :width="130"
          remoteSort
          show-header-overflow>
          <span slot-scope="{ row }">{{ typeObj[row.type] }}</span>
        </vxe-table-column>
        <vxe-table-column
          align="center"
          title="部门"
          field="deptName"
          :width="160"
          remoteSort
          show-header-overflow />
        <vxe-table-column
          align="center"
          title="费用"
          field="expense"
          :width="120"
          remoteSort
          show-header-overflow />
        <template v-if="this.selectType.toString() == '60400'">
          <vxe-table-column
            align="center"
            title="工号"
            field="enumber"
            :width="120"
            remoteSort
            show-header-overflow />
          <vxe-table-column
            align="center"
            title="姓名"
            field="ename"
            :width="120"
            remoteSort
            show-header-overflow>
          </vxe-table-column>
        </template>
        <vxe-table-column
          align="left"
          title="备注"
          field="remark"
          :width="120"
          remoteSort
          show-header-overflow>
        </vxe-table-column>
      </ystable>
    </div>
    <div style="text-align: right; margin-top: 10px" v-if="$auth('project:data:agg')">
      <a-tooltip placement="top">
        <template slot="title">
          <span>添加当前条件查询的结果</span>
        </template>
        <a-button type="primary" @click="queryTermSubmit" :loading="confirmLoading">条件添加</a-button>
      </a-tooltip>
      <a-tooltip placement="top">
        <template slot="title">
          <span>添加当前选中的数据</span>
        </template>
        <a-button
          type="primary"
          @click="handleSubmit"
          :loading="confirmLoading"
          style="margin-left: 20px"
          :disabled="inspectionList.length <= 0"
        >
          选中添加
        </a-button>
      </a-tooltip>
    </div>
  </a-modal>
</template>
<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'
const typeObj = {}
typeObj['20500'] = '检测'
typeObj['20600'] = '修理'
typeObj['20700'] = '样机'
typeObj['40000'] = '软件摊销'
typeObj['40100'] = '专利摊销'
typeObj['40200'] = '其他摊销'
typeObj['20300'] = '其他试制'
typeObj['69900'] = '其他'
typeObj['60400'] = '差旅费'
typeObj['60000'] = '资料'
typeObj['60100'] = '研发成果'
typeObj['60200'] = '知识产权'
typeObj['60300'] = '福利'
export default {
  name: 'ProjectInspectionModal',
  components: {
    ystable
  },
  data () {
    return {
      form: this.$form.createForm(this),
      selectType: [],
      title: '',
      typeObj,
      visible: false,
      inspectionList: [],
      confirmLoading: false,
      accDate: undefined,
      deptName: undefined,
      accNumber: undefined,
      summary: '',
      enumber: undefined,
      ename: undefined,
      remark: undefined,
      projectMonth: undefined,
      loadFirst: false,
      keyMap: {
        '40000': 'software',
        '40100': 'patent',
        '40200': 'otherAmortization',
        '60000': 'book',
        '60100': 'rdProduction',
        '60200': 'copyright',
        '60300': 'benefits',
        '69900': 'other'
      },
      totals: {
        total: { sum: 0, select: 0 },
        software: { sum: 0, select: 0, type: 40000 },
        patent: { sum: 0, select: 0, type: 40100 },
        otherAmortization: { sum: 0, select: 0, type: 40200 },
        book: { sum: 0, select: 0, type: 60000 },
        rdProduction: { sum: 0, select: 0, type: 60100 },
        copyright: { sum: 0, select: 0, type: 60200 },
        benefits: { sum: 0, select: 0, type: 60300 },
        other: { sum: 0, select: 0, type: 69900 }
      },
      id: undefined
    }
  },
  created () {
  },
  methods: {
    completed (data) {
      this.inspectionList = []
      for (const key in this.totals) {
        this.totals[key].sum = 0
        this.totals[key].select = 0
      }
      if (this.selectType.length === 1) {
        this.totals.total.sum = data.header
      } else {
        if (data.header) {
          if (this.selectType.length === 3) {
            this.totals.software.sum = data.header.softwareSum
            this.totals.patent.sum = data.header.patentSum
            this.totals.otherAmortization.sum = data.header.otherSum
            this.totals.total.sum = data.header.softwareSum + data.header.patentSum + data.header.otherSum
          } else {
            this.totals.book.sum = data.header.book
            this.totals.rdProduction.sum = data.header.rdProduction
            this.totals.copyright.sum = data.header.copyright
            this.totals.benefits.sum = data.header.benefits
            this.totals.other.sum = data.header.other
            this.totals.total.sum = data.header.book + data.header.rdProduction + data.header.copyright + data.header.benefits + data.header.other
          }
        }
      }
    },
    add (projectId, projectMonth, types) {
      this.title = '添加费用数据'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.inspectionList = []
      this.id = projectId
      this.projectMonth = projectMonth
      this.selectType = types
      this.selectTotal = 0
      this.selectSoftware = 0
      this.selectPatent = 0
      this.selectOther = 0
      this.deptName = null
      this.summary = ''
      this.accNumber = ''
      this.remark = ''
      this.$nextTick(() => {
        if (this.loadFirst) {
          this.$refs.table.refresh(true)
        }
        this.loadFirst = true
      })
    },
    onSelectChange ({ records }) {
      this.inspectionList = records
      for (const key in this.totals) {
        this.totals[key].select = 0
      }
      for (let i = 0; i < records.length; i++) {
        if (this.keyMap[records[i].type]) {
          this.totals[this.keyMap[records[i].type]].select += parseFloat(records[i].expense)
        }
        this.totals.total.select += parseFloat(records[i].expense)
      }
    },
    moment,
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          if (this.inspectionList.length > 0) {
            values.projectId = this.id
            if (this.inspectionList.length === undefined || this.inspectionList.length < 1) {
              this.$message.info('请选择数据')
              return
            }
            values.inspectionEntities = this.inspectionList
            values.projectMonth = moment(this.projectMonth + '-' + '01' + ' 00:00:00')
            this.$http.post('/projectInspection/addProjectInspection', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.confirmLoading = false
                  this.$emit('ok', true)
                  this.$refs.table.refresh(true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            this.$message.info('请选择要添加的费用')
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    queryTermSubmit () {
      this.confirmLoading = true
      const value = this.selectType.toString() === '60400'
        ? { projectId: this.id, types: this.selectType, projectMonth: this.projectMonth === '' ? undefined : moment(this.projectMonth + '-' + '01' + ' 00:00:00'), summary: this.summary, deptName: this.deptName, enumber: this.enumber, ename: this.ename, accNumber: this.accNumber }
        : { projectId: this.id, types: this.selectType, projectMonth: this.projectMonth === '' ? undefined : moment(this.projectMonth + '-' + '01' + ' 00:00:00'), summary: this.summary, deptName: this.deptName, accNumber: this.accNumber }
      this.$http.post('/projectInspection/addProjectInspectionByTerm', value).then(res => {
        if (res.success && res.data) {
          this.visible = false
          this.confirmLoading = false
          this.$emit('ok', true)
          this.$refs.table.refresh(true)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
        }
      }).finally(res => {
        this.$refs.table.refresh(true)
        this.confirmLoading = false
      })
    },
    setData (res) {
      this.inspectionSum = res.data.inspectionCount
      this.softwareSum = res.data.softwareSum
      this.patentSum = res.data.patentSum
      this.otherSum = res.data.otherSum
    },
    refreshData () {
      this.$refs.table.refresh(true)
    }

  }
}
</script>
<style scoped>
.word-wrap {
  word-break: break-all;
}
</style>
