<template>
  <a-modal
    :title="title"
    :width="1300"
    v-model="visible"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    :footer="null"
  >
    <div>
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="6" :sm="24">
            <a-form-item label="物料类型">
              <a-select style="width:165px" v-model="queryParam.type" placeholder="请选择">
                <a-select-option value="-1">请选择</a-select-option>
                <a-select-option value="0">原料</a-select-option>
                <a-select-option value="1">辅料</a-select-option>
                <a-select-option value="2" v-if="parentRdType !== 200">备品备件</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="物料编码">
              <a-input v-model="queryParam.mcode" placeholder="请输入物料编码" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="物料名称">
              <a-input v-model="queryParam.mname" placeholder="请输入物料名称" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item>
              <span slot="label">&nbsp;&nbsp;&nbsp;&nbsp;审核人</span>
              <a-input v-model="queryParam.auditor" placeholder="请输入审核人" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="出库单号">
              <a-input v-model="queryParam.billNo" placeholder="请输入出库单号" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item>
              <span slot="label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;部门</span>
              <a-input v-model="queryParam.deptName" placeholder="请输入部门" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item>
              <span slot="label">&nbsp;&nbsp;&nbsp;&nbsp;制单人</span>
              <a-input v-model="queryParam.biller" placeholder="请输入制单人" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item>
              <span slot="label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;仓库</span>
              <a-input v-model="queryParam.warehouse" placeholder="请输入仓库" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item label="规格型号">
              <a-input v-model="queryParam.specification" placeholder="请输入规格型号" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item>
              <span slot="label">&nbsp;&nbsp;&nbsp;&nbsp;记帐人</span>
              <a-input v-model="queryParam.booker" placeholder="请输入记帐人" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-item>
              <span slot="label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注</span>
              <a-input v-model="queryParam.remark" placeholder="请输备注" />
            </a-form-item>
          </a-col>
          <a-form-item>
            <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
          </a-form-item>
        </a-row>
      </a-form>
    </div>
    <ystable
      rowId="id"
      ref="table"
      queryUrl="/projectMaterial/queryMaterialAndQuantity"
      :params="queryParam"
      :toolbar="{zoom: true, custom: true, refresh: true}"
      @checkbox-change="onSelectChange"
      @checkbox-all="onSelectChange"
      @completed="({data})=>completed(data)"
      show-overflow="title"
    >
      <template v-slot:buttons>
        <span v-if="countTotal">
          总费用:&nbsp;&nbsp;
          <a>{{ selectTotal ? selectTotal.toFixed(2) : '-' }}/{{ countTotal.toFixed(2) }}</a>&nbsp;&nbsp;&nbsp;&nbsp;
          <a>{{ ( selectTotal/countTotal*100).toFixed(2) }}%</a>
        </span>
        <span v-else>总费用: -/-</span>
      </template>
      <vxe-table-column type="checkbox" :width="40" fixed="left" />
      <vxe-table-column
        title="物料类型"
        field="type"
        align="left"
        :width="100"
        remoteSort
        show-header-overflow
      >
        <template slot-scope="{row}">
          <span>{{ typeArr[row.type] }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="物料编码"
        field="mcode"
        align="left"
        :width="140"
        remoteSort
        show-header-overflow
      />
      <vxe-table-column
        title="物料名称"
        field="mname"
        align="left"
        :width="200"
        remoteSort
        show-header-overflow
      />
      <vxe-table-column
        title="出库单号"
        field="billNo"
        align="left"
        :width="140"
        remoteSort
        show-header-overflow
      />
      <vxe-table-column
        title="领用日期"
        field="acqDate"
        align="center"
        :width="120"
        remoteSort
        show-header-overflow
      />
      <vxe-table-column
        title="单价"
        field="unitPrice"
        align="right"
        :width="120"
        remoteSort
        show-header-overflow
      />
      <vxe-table-column
        title="数量"
        field="remainQuantity"
        align="right"
        :width="120"
        remoteSort
        show-header-overflow
      >
        <template slot-scope="{row}">
          <a-input-number
            :value="row.remainQuantity"
            :precision="6"
            :min="Number(row.maxQuantity > 0 ? 0 : row.maxQuantity)"
            :max="Number(row.maxQuantity > 0 ? row.maxQuantity : 0)"
            :key="`remainQuantity${row.key}`"
            @change="(val)=>onCellChange(Number(val),row,'remainQuantity')"
          />
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="金额"
        field="totalAmount"
        align="right"
        :width="120"
        remoteSort
        show-header-overflow
      />
      <vxe-table-column title="单位" field="unit" align="left" :width="80" />
      <vxe-table-column
        title="规格型号"
        field="specification"
        align="left"
        :width="140"
        remoteSort
        show-header-overflow
      />
      <vxe-table-column
        title="部门"
        field="deptName"
        align="left"
        :width="140"
        remoteSort
        show-header-overflow
      ></vxe-table-column>
      <vxe-table-column
        title="仓库"
        field="warehouse"
        align="center"
        :width="100"
        show-header-overflow
      />
      <vxe-table-column
        title="制单人"
        field="biller"
        align="center"
        :width="100"
        show-header-overflow
      />
      <vxe-table-column
        title="审核人"
        field="auditor"
        align="center"
        :width="100"
        show-header-overflow
      />
      <vxe-table-column
        title="记帐人"
        field="booker"
        align="center"
        :width="100"
        show-header-overflow
      />
      <vxe-table-column
        title="备注"
        field="remark"
        align="left"
        :width="100"
        show-header-overflow
      />
    </ystable>
    <div style="text-align: right;margin-top:10px" v-if="$auth('project:data:agg')">
      <a-tooltip placement="top">
        <template slot="title">
          <span>是否添加相同出库单号物料</span>
        </template>
        <a-checkbox @change="checkNum">同单号</a-checkbox>
      </a-tooltip>
      <a-tooltip placement="top">
        <template slot="title">
          <span>添加当前条件查询的结果</span>
        </template>
        <a-button
          type="primary"
          @click="querySubmit"
          style="margin-left:20px"
          :loading="confirmLoading"
        >条件添加</a-button>
      </a-tooltip>
      <a-tooltip placement="top">
        <template slot="title">
          <span>添加当前选中的数据</span>
        </template>
        <a-button
          type="primary"
          @click="handleSubmit"
          style="margin-left:20px"
          :loading="confirmLoading"
        >选中添加</a-button>
      </a-tooltip>
    </div>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'
export default {
  name: 'BindingMaterial',
  components: {
    ystable
  },
  data () {
    return {
      typeArr: ['原料', '辅料', '备品备件'],
      projectId: '',
      monthDate: '',
      materialList: [],
      selectedRowKeys: [],
      mname: '',
      mcode: '',
      deptId: 0,
      rdDeptId: 0,
      countTotal: 0,
      selectTotal: 0,
      // 查询参数
      queryParam: { type: '-1' },
      tableData: [],
      visible: false,
      confirmLoading: false,
      title: '',
      form: this.$form.createForm(this, { scroll: {} }),
      rdType: 0,
      parentRdType: 0,
      checkState: false
    }
  },
  methods: {
    completed (data) {
      this.selectTotal = 0
      this.selectedRowKeys = []
      this.materialList = []
      if (data) {
        this.countTotal = data.footer
      }
    },
    checkNum (e) {
      this.checkState = e.target.checked
    },
    moment,
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    add (projectId, monthDate, rdTypeInfo) {
      this.rdType = rdTypeInfo.types[0]
      this.parentRdType = parseInt(this.rdType / 100)
      this.monthDate = monthDate
      this.projectId = projectId
      this.title = '添加物料'
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = 0
      this.selectedRowKeys = []
      this.materialList = []
      this.queryParam = { type: '-1' }
      this.queryParam.selectDate = this.monthDate
      this.queryParam.rdType = this.rdType
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
      this.countTotal = 0
      this.selectTotal = 0
    },
    onCellChange (value, record, name) {
      record[name] = value
      this.selectTotal = 0
      if (this.materialList.length !== 'undefined') {
        for (let i = 0; i < this.materialList.length; i++) {
          if (this.materialList[i].quantity === this.materialList[i].remainQuantity) {
            this.selectTotal = this.selectTotal + this.materialList[i].totalAmount
          } else {
            this.selectTotal = this.selectTotal + (this.materialList[i].remainQuantity * this.materialList[i].unitPrice)
          }
        }
      }
    },
    onSelectChange ({ records }) {
      const selectedRows = records
      const selectedRowKeys = records.map(item => item.id)
      this.selectedRowKeys = selectedRowKeys
      if (this.checkState) {
        let selectedRowId, selectedBillNo
        if (selectedRows.length > this.selectedRowKeys.length) {
          for (let index = 0; index < selectedRows.length; index++) {
            const rowId = selectedRows[index].id
            const billNo = selectedRows[index].billNo
            if (billNo && this.selectedRowKeys.indexOf(rowId) === -1) {
              selectedRowId = rowId
              selectedBillNo = billNo
              break
            }
          }
          if (selectedBillNo) {
            for (let index = 0; index < this.tableData.length; index++) {
              const element = this.tableData[index]
              if (selectedRowId !== element.id && element.billNo === selectedBillNo) {
                selectedRowKeys.push(element.id)
                selectedRows.push(element)
              }
            }
          }
        } else {
          // let unSelectedRowId,
          let unBillNo
          for (let index = 0; index < this.materialList.length; index++) {
            unBillNo = this.materialList[index].billNo
            if (unBillNo && selectedRowKeys.indexOf(this.materialList[index].id) === -1) {
              // unSelectedRowId = this.materialList[index].id
              break
            }
          }
          if (unBillNo) {
            for (let index = selectedRows.length - 1; index >= 0; index--) {
              const element = selectedRows[index]
              if (element.billNo === unBillNo) {
                selectedRowKeys.splice(index, 1)
                selectedRows.splice(index, 1)
              }
            }
          }
        }
      }
      this.materialList = selectedRows
      this.selectTotal = 0
      for (let i = 0; i < selectedRows.length; i++) {
        if (selectedRows[i].quantity === selectedRows[i].remainQuantity) {
          this.selectTotal = this.selectTotal + selectedRows[i].totalAmount
        } else {
          this.selectTotal = this.selectTotal + (selectedRows[i].remainQuantity * selectedRows[i].unitPrice)
        }
      }
    },
    querySubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values = this.queryParam
          values.projectId = this.projectId
          values.rdType = this.rdType
          values.acqMonth = this.monthDate
          if (this.checkState) {
            this.$http.post('/projectMaterial/conditionalAdditionBillNo', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          } else {
            this.$http.post('/projectMaterial/conditionalAddition', values)
              .then(res => {
                if (res.success && res.data) {
                  this.visible = false
                  this.$emit('ok', true)
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              }).finally(res => {
                this.confirmLoading = false
              })
          }
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          values.projectId = this.projectId
          values.rdType = this.rdType
          if (this.materialList.length > 0) {
            values.acqMonth = this.monthDate
            if (this.checkState) {
              values.modelList = this.materialList
              this.$http.post('/projectMaterial/saveBillNo', values)
                .then(res => {
                  if (res.success && res.data) {
                    this.visible = false
                    this.$emit('ok', true)
                  } else {
                    this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
                  }
                }).finally(res => {
                  this.confirmLoading = false
                })
            } else {
              values.materialList = this.materialList
              this.$http.post('/projectMaterial/save', values)
                .then(res => {
                  if (res.success && res.data) {
                    this.visible = false
                    this.$emit('ok', true)
                  } else {
                    this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
                  }
                }).finally(res => {
                  this.confirmLoading = false
                })
            }
          } else {
            this.$message.info('请选择要添加的物料')
            this.confirmLoading = false
          }
        } else {
          this.confirmLoading = false
        }
      })
    }
  }
}
</script>

<style scoped>
.word-wrap {
  word-break: break-all;
}
</style>
