<template>
  <a-card :bordered="false" :bodyStyle="{ height: '100%', overflow: 'auto'}" style="height: 100%;">
    <a-form layout="inline">
      <a-row>
        <a-col>
          <a-form-item label="项目">
            <a-select style="width:600px;" :value="projectId" @change="OnProjectChange">
              <a-select-option v-for="item in projectList" :key="item.id">
                <span
                  v-if="item!=null"
                >{{ item.rdTitle }} - {{ item.pname }}</span>
              </a-select-option>
            </a-select>
          </a-form-item>
          <a-alert
            v-if="errorMsg!==''"
            :message="errorMsg"
            type="warning"
            style="width:600px;margin-left:40px"
          />
        </a-col>
      </a-row>
      <a-row v-if="selectProject!=null">
        <a-col>
          <a-form-item label="物料编码">
            <a-input v-model="queryParam.mcode" placeholder="请输入物料编码" />
          </a-form-item>
          <a-form-item label="物料名称">
            <a-input v-model="queryParam.mname" placeholder="请输入物料名称" />
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="$refs.table.refresh(true)" v-if="$auth('project:materialTrack:search')">查询</a-button>
          </a-form-item>
        </a-col>
      </a-row>
      <!-- <span style="padding-left:20px;padding-top:5px;">
        <a-button type="primary" :disabled="selectedRowKeys.length <= 0" @click="delSelect">删除</a-button>
      </span>-->
    </a-form>
    <div id="scrollContent" v-if="projectId">
      <ystable
        ref="table"
        :params="getParam()"
        queryUrl="/projectMaterial/queryMaterialTrack"
        @completed="({data})=>completed(data)"
        :toolbar="{
          custom: true,
          zoom:true,
          refresh:true
        }"
      >
        <template v-slot:buttons>
          <span style="padding-left: 10px;">
            <span v-if="$auth('project:materialTrack:edit')">
              <a-switch
                checkedChildren="编辑"
                unCheckedChildren="编辑"
                @change="onOpenEditMode"
                v-model="openEditMode"/>
              <a-button
                style="margin-left: 10px;"
                type="primary"
                v-if="openEditMode"
                @click="onSaveList">
                保存
              </a-button>
            </span>
          </span>
        </template>
        <vxe-table-column
          title="物料名称"
          field="mname"
          key="mname"
          :width="100"
          show-header-overflow
          show-overflow="tooltip"
          align="left"
          remoteSort
          fixed="left"
        ></vxe-table-column>
        <vxe-table-column
          title="物料编码"
          field="mcode"
          key="mcode"
          :width="100"
          show-header-overflow
          show-overflow="tooltip"
          align="left"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="领用日期"
          field="acqDate"
          key="acqDate"
          :width="100"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="总价"
          field="totalPrice"
          key="totalPrice"
          :width="100"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
        >
          <template v-slot="{ row }">
            <span v-if="row.used === row.quantity">{{ (row.totalAmount).toFixed(2) }}</span>
            <span v-else>{{ (row.used * row.unitPrice).toFixed(2) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="单价"
          field="unitPrice"
          key="unitPrice"
          :width="100"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="数量"
          field="used"
          key="used"
          :width="100"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="单位"
          field="unit"
          key="unit"
          :width="80"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="出库单号"
          field="billNo"
          key="billNo"
          :width="100"
          show-header-overflow
          show-overflow="tooltip"
          align="left"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          title="正常产出率"
          field="normalOutputRate"
          key="normalOutputRate"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            <a-input-number
              v-if="openEditMode"
              :value="row.normalOutputRate"
              :step="0.01"
              :min="0"
              :max="1"
              :key="`normalOutputRate${row.key}`"
              @change="(val)=>onFieldChange(Number(val),row,'normalOutputRate')"
            />
            <span v-else>{{ row.normalOutputRate }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="研发产出率"
          field="rdOutputRate"
          key="rdOutputRate"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            <a-input-number
              v-if="openEditMode"
              :value="row.rdOutputRate"
              :step="0.01"
              :min="0"
              :max="1"
              :key="`rdOutputRate${row.key}`"
              @change="(val)=>onFieldChange(Number(val),row,'rdOutputRate')"
            />
            <span v-else>{{ row.rdOutputRate }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="研发产出金额"
          field="rdOutputAmount"
          key="rdOutputAmount"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <span
              v-if="row.used === row.quantity"
            >{{ (row.totalAmount * row.rdOutputRate).toFixed(2) }}</span>
            <span v-else>{{ (row.used * row.unitPrice * row.rdOutputRate).toFixed(2) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="研发损耗率"
          field="rdLossRate"
          key="rdLossRate"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            <a-input-number
              v-if="openEditMode"
              :value="row.rdLossRate"
              :step="0.01"
              :min="0"
              :max="1"
              :key="`rdLossRate${row.key}`"
              @change="(val)=>onFieldChange(Number(val),row,'rdLossRate')"
            />
            <span v-else>{{ row.rdLossRate }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="研发损耗量"
          field="rdLoss"
          key="rdLoss"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <span>{{ (row.used * row.rdLossRate).toFixed(2) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="研发损耗金额"
          field="rdLossAmount"
          key="rdLossAmount"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <span
              v-if="row.used === row.quantity"
            >{{ (row.totalAmount * row.rdLossRate).toFixed(2) }}</span>
            <span v-else>{{ (row.used * row.unitPrice * row.rdLossRate).toFixed(2) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="报废率"
          field="scrapRate"
          key="scrapRate"
          :width="120"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            <a-input-number
              v-if="openEditMode"
              :value="row.scrapRate"
              :step="0.01"
              :min="0"
              :max="1"
              :key="`scrapRate${row.key}`"
              @change="(val)=>onFieldChange(Number(val),row,'scrapRate')"
            />
            <span v-else>{{ row.scrapRate }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="报废量"
          field="scrap"
          key="scrap"
          :width="100"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <span>{{ (row.used * row.scrapRate).toFixed(2) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="报废金额"
          field="scrapAmount"
          key="scrapAmount"
          :width="100"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <span
              v-if="row.used === row.quantity"
            >{{ (row.totalAmount * row.scrapRate).toFixed(2) }}</span>
            <span v-else>{{ (row.used * row.unitPrice * row.scrapRate).toFixed(2) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="报废单号"
          field="scrapNo"
          key="scrapNo"
          :width="100"
          show-header-overflow
          show-overflow="tooltip"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            <a-input
              v-if="openEditMode"
              :value="row.scrapNo"
              :key="`scrapNo${row.key}`"
              @change="(e)=>onFieldChange(e.target.value,row,'scrapNo')"
            />
            <span v-else>{{ row.scrapNo }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="样品收入"
          field="sampleRevenue"
          key="sampleRevenue"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <a-input-number
              v-if="openEditMode"
              :value="row.sampleRevenue"
              :step="0.01"
              :min="0"
              :max="1"
              :key="`sampleRevenue${row.key}`"
              @change="(val)=>onFieldChange(Number(val),row,'sampleRevenue')"
            />
            <span v-else>{{ row.sampleRevenue }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="废料售价"
          field="scrapPrice"
          key="scrapPrice"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <a-input-number
              v-if="openEditMode"
              :value="row.scrapPrice"
              :step="0.01"
              :min="0"
              :max="1"
              :key="`scrapPrice${row.key}`"
              @change="(val)=>onFieldChange(Number(val),row,'scrapPrice')"
            />
            <span v-else>{{ row.scrapPrice }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="特殊收入"
          field="specialIncome"
          key="specialIncome"
          :width="130"
          show-header-overflow
          show-overflow="tooltip"
          align="right"
          remoteSort
        >
          <template v-slot="{ row }">
            <a-input-number
              v-if="openEditMode"
              :value="row.specialIncome"
              :step="0.01"
              :min="0"
              :max="1"
              :key="`specialIncome${row.key}`"
              @change="(val)=>onFieldChange(Number(val),row,'specialIncome')"
            />
            <span v-else>{{ row.specialIncome }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="特殊收入单号"
          field="specialIncomeNo"
          key="specialIncomeNo"
          :width="180"
          show-header-overflow
          show-overflow="tooltip"
          align="left"
          remoteSort
        >
          <template v-slot="{ row }">
            <a-input
              v-if="openEditMode"
              :value="row.specialIncomeNo"
              :key="`specialIncomeNo${row.key}`"
              @change="(e)=>onFieldChange(e.target.value,row,'specialIncomeNo')"
            />
            <span v-else>{{ row.specialIncomeNo }}</span>
          </template>
        </vxe-table-column>
      </ystable>
    </div>
  </a-card>
</template>

<script>
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import ystable from '@/components/Table/ystable'

export default {
  mixins: [yearMiXin],
  name: 'NewMaterialAgg',
  components: {
    ystable
  },
  data () {
    return {
      scroll: {},
      openEditMode: false,
      mname: '',
      mcode: '',
      projectId: 0,
      projectList: [],
      selectProject: {},
      queryParam: {},
      project: {},
      // getData: parameter => {
      //   return this.$http.get('/projectMaterial/queryMaterialTrack', { params: Object.assign(parameter, { mname: this.mname, mcode: this.mcode, projectId: this.projectId }) })
      //     .then(res => {
      //       this.tableData = res.data.data
      //       return res.data
      //     })
      // },
      tableData: [],
      title: '',
      loading: false,
      form: this.$form.createForm(this, { scroll: {} }),
      errorMsg: ''
    }
  },
  watch: {
    projectId (newId) {
      this.queryParam.projectId = newId
      if (this.$refs.table) {
        this.$refs.table.refresh(true)
      }
    }
  },
  computed: {
    rdTotalProjectList: function () {
      return [...this.projectList, { 'rdIndex': -1 }]
    }
  },
  created () {
    if (document.body.clientWidth < 1640) {
      this.scroll = { x: 1300 }
    }
    this.onYearChange(this.currentYear)
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1640) {
          this.scroll = { x: 1300 }
        }
      })()
    }
  },
  methods: {
    getParam () {
      this.queryParam.projectId = this.projectId
      return this.queryParam
    },
    search () {
      this.onYearChange(this.currentYear)
    },
    moment,
    completed (data) {
      this.tableData = data.data
    },
    onSaveList () {
      const values = []
      for (var i = 0; i < this.tableData.length; i++) {
        // var record = this.tableData[i]
        // if (record.wastage !== record.cacheData.wastage || record.finished !== record.cacheData.finished || record.normalOutputRate !== record.cacheData.normalOutputRate) {
        //   values.push(record)
        // }
        if (this.tableData[i].editState) {
          values.push(this.tableData[i])
        }
      }
      if (values.length <= 0) {
        this.$message.info('未进行任何编辑')
        return
      }
      this.$http.post('/projectMaterial/editMaterialTrack', values)
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('保存成功')
          } else {
            this.$refs.table.refresh(true)
            this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          }
        }).finally(res => {
          this.confirmLoading = false
          this.$refs.table.refresh(true)
          this.openEditMode = false
        })
    },
    onOpenEditMode (checked) {
      this.openEditMode = checked
      for (var i = 0; i < this.tableData.length; i++) {
        var record = this.tableData[i]
        if (checked) {
          record.cacheData = { ...record }
        } else {
          const cacheRecord = record.cacheData
          Object.assign(record, cacheRecord)
          delete record.cacheRecord
        }
      }
    },
    onFieldChange (value, record, type) {
      record[type] = value
      record.editState = true
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.$refs.table.refresh(true)
    },
    onYearChange (value, option) {
      if (!value) {
        return
      }
      this.rdYear = value
      this.$http.get('/project/getSelectList', { params: { year: this.currentYear, sign: 2 } })
        .then(res => {
          if (res.success && res.data !== null && res.data.length > 0) {
            this.projectList = res.data
            if (this.projectList.length > 0) {
              this.projectList.map(item => {
                this.project[item.id] = item
              })
              this.projectId = this.projectList[0].id
              this.errorMsg = ''
            } else {
              this.projectId = undefined
              this.project = {}
              this.errorMsg = '当前年份：' + value + '没有项目，请重新选择年份'
            }
          } else {
            this.projectList = []
            this.projectId = undefined
            this.project = {}
            // this.$message.warning('当前年份：' + value + '没有项目，请重新选择年份')
            this.errorMsg = '当前年份：' + value + '没有项目，请重新选择年份'
          }
          this.OnProjectChange(this.projectId)
          return res.data
        })
    },
    OnProjectChange (value) {
      this.projectId = value
      this.selectProject = this.project[value]
    }
  }
}
</script>

<style lang="less" scoped>
.word-wrap {
  word-break: break-all;
}
#scrollContent {
  height: calc(100% - 78px);
  overflow: auto;
  padding: 0 8px;
}
</style>
