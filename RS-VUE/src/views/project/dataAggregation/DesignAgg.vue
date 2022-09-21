<template>
  <div class="customCard">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="设计名称">
          <a-input v-model="queryParam.name" placeholder="请输入设计名称" style="width:165px" />
        </a-form-item>
        <a-form-item label="部门">
          <a-input v-model="queryParam.deptName" placeholder="请输入部门" style="width:165px" />
        </a-form-item>
        <span>
          <a-button type="primary" @click="search(true)">查询</a-button>
        </span>
      </a-form>
      <div>
        <ystable
          ref="table"
          rowId="id"
          :params="queryParam"
          highlight-hover-row
          highlight-current-row
          show-overflow
          resizable
          auto-resize
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          :toolbar="{zoom:true,custom:true,refresh:true}"
          queryUrl="/designProject/queryProjectDesignList"
          @completed="({data})=>completed(data)"
          :pagerConfig="{ pageSize: 50, pageSizes: [50, 100, 200, 500] }"
        >
          <template v-slot:buttons>
            <template v-if="canModify && $auth('project:data:agg')">
              <span style="margin-right: 10px;">
                <a-button type="primary" @click="$refs.projectDesign.add(projectId,projectMonth)">添加</a-button>
              </span>
              <span style="margin-right: 10px;">
                <a-button
                  type="primary"
                  :disabled="designEntityList.length <= 0"
                  @click="delDesign"
                >
                  删除
                </a-button>
              </span>
              <span style="margin-right: 10px;">
                <a-button
                  type="primary"
                  :disabled="designEntityList.length <= 0"
                  @click="showSetRdAmount"
                >
                  设置研发费用
                </a-button>
              </span>
              <rd-fee-submit :projectId="projectId" :month="projectMonth" :rdType="rdFeeType" style="margin-right: 10px;" @getSummary="getSummary"/>
            </template>
            <span :style="{paddingLeft: canModify ? '10px' : 0}">
              &nbsp;&nbsp;总计：
              <a
                style="font-weight: 600"
              >{{ designCount ? designCount.toFixed(2):'-' }}</a>
            </span>
            <span v-if="canModify && $auth('project:data:agg')">
              <a-switch
                checkedChildren="编辑"
                unCheckedChildren="编辑"
                style="margin-left:10px;"
                type="primary"
                :checked="editChecked"
                @change="editCheckedChange"
              />
              <a-button
                style="margin-left: 10px;"
                type="primary"
                size="small"
                v-if="editChecked"
                @click="saveData"
              >保存</a-button>
            </span>
          </template>
          <vxe-table-column type="checkbox" :width="40" fixed="left" />
          <vxe-table-column
            title="设计名称"
            field="dname"
            :min-width="260"
            remoteSort
            show-header-overflow
            show-overflow="title"
            fixed="left"
          >
            <template v-slot="{ row }" >
              <a-badge v-if="row.usedModels" :dot="row.usedModels.length > 0 ">
                <a-tooltip placement="top">
                  <template slot="title">
                    <div v-for="(item, index) in row.usedModels" :key="index" >
                      {{ item.rdTitle }} 归集金额：{{ item.rdAmount }}
                    </div>
                  </template>
                  <span>{{ row.dname }}</span>
                </a-tooltip>
              </a-badge>
              <span v-else>{{ row.dname }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="设计日期"
            align="center"
            field="designDate"
            width="160"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
          <vxe-table-column
            title="部门"
            field="deptName"
            :width="160"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
          <vxe-table-column
            title="设计费用"
            field="dFee"
            align="right"
            :width="160"
            remoteSort
            show-header-overflow
            show-overflow="title"
          />
          <vxe-table-column
            title="研发费用"
            field="rdAmount"
            align="right"
            :width="110"
            remoteSort
            show-header-overflow
          >
            <template slot-scope="{row}">
              <span
                v-if="!editChecked"
              >{{ row.rdAmount ? Number(row.rdAmount) : '-' }}</span>
              <span v-else>
                <a-input-number
                  v-model="row.rdAmount"
                  style="width:100%"
                  :min="(row.Amount + row.remainDFee) > 0 ? 0 : (row.Amount + row.remainDFee)"
                  :max="(row.Amount + row.remainDFee) > 0 ? (row.Amount + row.remainDFee) : 0"
                  :precision="2"
                  size="small"
                  @change="v=>handleChange(v,row)"
                />
              </span>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <projectDesign-modal ref="projectDesign" @ok="handleOk"></projectDesign-modal>
      <set-rdAmount-modal url="/designProject/setDesignRdAmounts" ref="setRdAmountModal" @ok="handleOk" />
    </a-spin>
  </div>
</template>

<script>
import RdFeeSubmit from './modules/RdFeeSubmit.vue'
import ystable from '@/components/Table/ystable'
import ProjectDesignModal from './bindingDataModules/ProjectDesignModal'
import SetRdAmountModal from './modules/SetRdAmountModal'
import moment from 'moment'
export default {
  name: 'NewDesignAgg',
  components: {
    ProjectDesignModal,
    ystable,
    SetRdAmountModal,
    RdFeeSubmit
  },
  props: {
    projectId: {
      type: Number,
      required: true
    },
    projectMonth: {
      type: String,
      default: ''
    },
    rdFeeType: {
      type: String,
      required: true
    },
    canModify: {
      type: Boolean,
      default: true
    }
  },
  data () {
    return {
      openEditMode: false,
      form: this.$form.createForm(this),
      spinning: false,
      designCount: 0,
      designEntityList: [],
      tableData: [],
      editRow: {},
      editChecked: false,
      queryParam: {
        projectId: this.projectId,
        projectMonth: this.projectMonth === '' ? undefined : moment(this.projectMonth + '-' + '01' + ' 00:00:00'),
        name: undefined,
        deptName: undefined
      }
    }
  },
  watch: {
    projectId (newId) {
      this.queryParam.projectId = newId
      this.queryParam.projectMonth = this.projectMonth === '' ? undefined : moment(this.projectMonth + '-' + '01' + ' 00:00:00')
      this.queryParam.name = undefined
      this.queryParam.deptName = undefined
      this.search(true)
    }
  },
  methods: {
    completed (data) {
      this.designCount = 0
      this.designEntityList = []
      data.data.forEach(item => {
        item.Amount = item.rdAmount
      })
      this.tableData = data.data
      if (data) {
        this.designCount = data.header
      }
      this.editCheckedChange(this.editChecked)
    },
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    moment,
    handleOk (flag) {
      if (flag) {
        this.$message.success('操作成功')
      } else {
        this.$message.success('操作成功')
      }
      this.getSummary()
      this.search(true)
    },
    getSummary () {
      this.$emit('getSummary')
    },
    selectChange ({ records }) {
      this.designEntityList = records
    },
    delDesign () {
      this.spinning = true
      if (this.designEntityList.length === undefined || this.designEntityList.length <= 0) {
        this.$message.info('请选择要删除的设计费用')
        return
      }
      this.$http.post('/designProject/delProjectDesignBatch', this.designEntityList).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
        } else {
          this.$message.error(res.errorMessage || '删除失败')
        }
        this.designEntityList = []
      }).finally(res => {
        this.spinning = false
        this.$emit('getSummary')
        this.search(false)
      })
    },
    editCheckedChange (ck) {
      this.editChecked = ck
      if (this.tableData) {
        if (ck) {
          this.tableData.forEach(record => {
            record.cacheData = { ...record }
          })
        } else {
          this.tableData.forEach(record => {
            Object.assign(record, record.cacheData)
            delete record.cacheData
          })
        }
      }
    },
    saveData () {
      if (!this.editRow) {
        return
      }
      this.spinning = true
      const arr = []
      for (const k in this.editRow) {
        this.editRow[k]['projectId'] = this.projectId
        this.editRow[k]['projectMonth'] = this.projectMonth === '' ? undefined : moment(this.projectMonth + '-' + '01' + ' 00:00:00')
        arr.push(this.editRow[k])
      }
      if (arr.length === 0) {
        this.spinning = false
        return
      }
      this.$http.post('/designProject/setRdAmounts', arr).then((res) => {
        if (res.data && res.success) {
          this.spinning = false
          this.$message.success('保存成功')
          this.editRow = {}
          this.search(false)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
          this.spinning = false
        }
      }).catch((error) => {
        this.$message.error(error.message)
      })
    },
    handleChange (v, record) {
      if (v !== '-' && record.rdAmount && Number(record.rdAmount) !== 0) {
        record.rdAmount = v
        this.editRow[record.id] = record
      }
    },
    showSetRdAmount () {
      const params = {
        projectId: this.projectId,
        projectMonth: moment(this.projectMonth + '-' + '01' + ' 00:00:00'),
        ids: this.designEntityList.map(item => {
          return item.id
        })
      }
      this.$refs.setRdAmountModal.show(params)
    }
  }
}
</script>

<style  lang="less" scoped >
.customCard {
  padding: 0 12px;
}
</style>
