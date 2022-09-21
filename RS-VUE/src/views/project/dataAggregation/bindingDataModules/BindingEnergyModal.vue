<template>
  <a-modal
    :width="1300"
    :title="title"
    :visible="visible"
    :footer="null"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-spin
      tip="请稍后..."
      :spinning="spinning"
    >
      <a-card :bordered="false">
        <div>
          <a-form layout="inline">
            <a-form-item label="能源名称">
              <a-input
                v-model="queryParam.ename"
                placeholder="请输入能源名称"
              />
            </a-form-item>
            <a-form-item label="部门">
              <a-input
                v-model="queryParam.deptName"
                placeholder="请输入部门"
              />
            </a-form-item>
            <a-form-item label="备注">
              <a-input
                v-model="queryParam.remark"
                placeholder="请输入备注"
              />
            </a-form-item>
            <a-form-item>
              <a-button
                type="primary"
                @click="search"
              >查询</a-button>
            </a-form-item>
          </a-form>
        </div>
        <ystable
          rowId="id"
          ref="table"
          queryUrl="/projectEnergy/getEnergyList"
          :params="queryParam"
          @checkbox-change="onSelectChange"
          @checkbox-all="onSelectChange"
          :toolbar="{zoom: true,custom:true,refresh:true}"
          @completed="({data})=>completed(data)"
          show-overflow="title"
        >
          <template v-slot:buttons>
            <span>
              总计:&nbsp;&nbsp;
              <a>{{ checkAmount.toFixed(2) }}/{{ totalAmount.toFixed(2) }}</a>
            </span>
          </template>
          <vxe-table-column
            type="checkbox"
            :width="40"
          />
          <vxe-table-column
            title="能源名称"
            field="ename"
            align="left"
            :min-width="200"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="凭证号"
            field="accNumber"
            align="left"
            :width="160"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="部门"
            field="deptName"
            align="left"
            :width="160"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="发生日期"
            field="occDate"
            align="center"
            :width="120"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="单价"
            field="unitPrice"
            align="right"
            :width="110"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="数量"
            field="quantity"
            align="right"
            :width="110"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="单位"
            field="unit"
            align="right"
            :width="90"
          />
          <vxe-table-column
            title="金额"
            field="totalAmount"
            align="right"
            :width="110"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="分配金额"
            field="amount"
            align="right"
            :width="110"
            remoteSort
            show-header-overflow
          />
          <vxe-table-column
            title="剩余金额"
            field="remainAmount"
            align="right"
            :width="110"
            remoteSort
            show-header-overflow
          >
            <template slot-scope="{row}">{{ row.remainAmount ? row.remainAmount.toFixed(2) : '' }}</template>
          </vxe-table-column>
          <vxe-table-column
            title="备注"
            field="remark"
            align="left"
            :width="110"
            remoteSort
            show-header-overflow
          />
        </ystable>
      </a-card>
    </a-spin>
    <div
      style="text-align: right;margin-top:10px"
      v-if="$auth('project:data:agg')"
    >
      <a-tooltip placement="top">
        <template slot="title">
          <span>添加当前条件查询的结果</span>
        </template>
        <a-button
          :disabled="conditionAdd"
          type="primary"
          :loading="confirmLoading"
          @click="querySubmit"
        >条件添加</a-button>
      </a-tooltip>
      <a-tooltip placement="top">
        <template slot="title">
          <span>添加当前选中的数据</span>
        </template>
        <a-button
          type="primary"
          :loading="confirmLoading"
          :disabled="selectedRowKeys.length <= 0"
          @click="handleSubmit"
          style="margin-left:20px"
        >选中添加</a-button>
      </a-tooltip>
    </div>
  </a-modal>
</template>
<script>
import moment from 'moment'
import ystable from '@/components/Table/ystable'
export default {
  name: 'BindingEnergy',
  components: {
    ystable
  },
  data () {
    return {
      conditionAdd: false,
      checkAmount: 0,
      totalAmount: 0,
      projectId: '',
      energyList: [],
      selectedRowKeys: [],
      spinning: false,
      queryParam: {},
      confirmLoading: false,
      visible: false,
      title: '',
      form: this.$form.createForm(this, { scroll: {} }),
      firstLoad: false
    }
  },
  methods: {
    completed (data) {
      this.totalAmount = 0
      this.checkAmount = 0
      this.selectedRowKeys = []
      this.energyList = []
      this.conditionAdd = true
      if (data) {
        this.conditionAdd = data.data.length <= 0
      }
      this.getTotal(data ? data.header : null)
    },
    moment,
    getTotal (header) {
      if (header != null) {
        this.totalAmount = header
      } else {
        this.totalAmount = 0
      }
    },
    search () {
      this.selectedRowKeys = []
      this.totalAmount = 0
      this.checkAmount = 0
      this.$refs.table.refresh(true)
    },
    add (projectId, month, type, etype) {
      this.queryParam = { projectId: projectId, month: moment(month), type: type, etype: etype }
      this.projectId = projectId
      if (this.$refs.deptSelet) {
        this.$refs.deptSelet.setValue(0)
      }
      if (this.$refs.rdDeptSelet) {
        this.$refs.rdDeptSelet.setValue(0)
      }
      this.title = `${type === 20100 ? '添加动力' : '添加燃料'}[${moment(month).format('YYYY-MM')}]`
      this.confirmLoading = false
      this.visible = true
      this.form.resetFields()
      this.id = 0
      this.energyList = []
      this.$nextTick(() => {
        if (this.firstLoad) {
          this.search()
        }
        this.firstLoad = true
      })
    },
    onSelectChange ({ records }) {
      this.selectedRowKeys = records.map(item => item.id)
      this.energyList = records
      this.getCheckedTotal()
    },
    getCheckedTotal () {
      var price = 0
      this.energyList.forEach(item => {
        price += Number(item.amount)
      })
      this.checkAmount = price
    },
    getPostItems () {
      var itemRows = []
      for (var i = 0; i < this.energyList.length; i++) {
        var item = this.energyList[i]
        if (this.selectedRowKeys.indexOf(item.id) !== -1) {
          itemRows.push(item)
        }
      }
      return itemRows
    },
    handleSubmit () {
      this.confirmLoading = true
      var value = {}
      value.energyModels = this.getPostItems()
      if (value.energyList === null || value.energyModels.length <= 0) {
        this.$emit('error', '未选择任何能源')
        this.confirmLoading = false
        return
      }
      value.projectId = this.queryParam.projectId
      value.month = this.queryParam.month
      value.etype = this.queryParam.etype
      value.type = this.queryParam.type
      this.$http.post('/projectEnergy/addList', value).then(res => {
        if (res.success && res.data) {
          this.visible = false
          this.$emit('ok')
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
        }
        this.confirmLoading = false
      })
    },
    querySubmit () {
      this.confirmLoading = true
      this.$http.post('/projectEnergy/addByCondition', this.queryParam)
        .then(res => {
          if (res.success && res.data) {
            this.visible = false
            this.$message.success('添加成功')
            this.$emit('ok')
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
          }
          this.confirmLoading = false
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
