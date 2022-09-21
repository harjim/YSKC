<template>
  <a-drawer :visible="visible" destroyOnClose title="添加服务记录" :width="1184" @close="close">
    <a-form-model ref="form" :model="form" :label-col="{ span: 5 }">
      <a-row>
        <a-col :span="12">
          <a-form-model-item label="客户名称" prop="customerId" required>
            <select-company
              style="width: 420px;"
              prop="companyName"
              url="/serviceRecord/getCustomerList"
              @changeCompany="getCustomerList"
            />
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item label="关联单号" prop="serviceNo">
            <vxe-pulldown ref="pulldown" style="width: 420px;">
              <a-input
                :disabled="!form.customerId"
                placeholder="请选择关联单号（可输入单号搜索）"
                v-model="form.serviceNo"
                @focus="onPulldownFocus"
              >
                <a-icon slot="suffix" type="search"/>
              </a-input>
              <template #dropdown>
                <div class="my-dropdown">
                  <ystable
                    highlight-current-row
                    ref="table"
                    query-url="/serviceRecord/getServiceNo"
                    :params="queryParams"
                    size="mini"
                    header-align="center"
                    highlight-hover-row
                    show-overflow
                    @cell-click="clickRow"
                    resizable
                    auto-resize
                    row-id="serviceNo"
                  >
                    <vxe-table-column field="serviceNo" title="服务单号" width="60"/>
                    <vxe-table-column field="ownerName" title="申请人" width="100"/>
                    <vxe-table-column field="date" title="预计起止日期" width="140"/>
                  </ystable>
                </div>
              </template>
            </vxe-pulldown>
          </a-form-model-item>
        </a-col>
      </a-row>

      <a-row>
        <a-col :span="12">
          <a-form-model-item label="业务员" required>
            <search-select
              :disabled="!form.customerId"
              style="width: 420px;"
              url="/user/userForSelect"
              searchField="realName"
              sTitle="realName"
              placeholder="请输入业务员"
              v-model="owner"
            />
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item label="所属部门" prop="deptName" required>
            <a-input v-model="form.deptName" style="width: 420px;" disabled placeholder="请选择业务员"/>
          </a-form-model-item>
        </a-col>
      </a-row>

      <a-divider/>

      <ServiceEditTable ref="ServiceEditTable" :valid-rules="validRules" :footerMethod="footerMethod">
        <vxe-table-column field="itemType" title="事项" width="160" :edit-render="{ immediate: true }">
          <template v-slot="{ row }">
            <a-select
              allowClear
              v-model="row.itemType"
              style="width: 100%;"
              placeholder="请选择事项"
            >
              <a-select-option v-for="item in dictionary" :key="item.key">
                {{ item.value }}
              </a-select-option>
            </a-select>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="date"
          title="起止时间"
          align="center"
          width="440"
          :edit-render="{ immediate: true }"
        >
          <template v-slot="{ row }">
            <DateRange show-time @onChange="(d, s) => changeRangeDate(row, d, s)"/>
          </template>
        </vxe-table-column>
        <vxe-table-column field="amount" title="费用" align="right" width="150" :edit-render="{ immediate: true }">
          <template v-slot="{ row }">
            <a-input-number
              :min="0"
              :step="0.01"
              :max="$store.state.totalMax"
              v-model="row.amount"
              placeholder="请输入费用金额"
              style="width: 100%;"
              @change="$refs.ServiceEditTable.$refs.xTable.updateFooter()"/>
          </template>
        </vxe-table-column>
        <vxe-table-column field="remark" title="备注" width="200" :edit-render="{ immediate: true }">
          <template v-slot="{ row }">
            <a-textarea
              placeholder="请输入"
              style="width: 100%;"
              v-model="row.remark"
              :auto-size="{ minRows: 1, maxRows: 3 }"
            />
          </template>
        </vxe-table-column>
      </ServiceEditTable>
    </a-form-model>

    <div
      :style="{
        position: 'absolute',
        right: 0,
        bottom: 0,
        width: '100%',
        height: '56px',
        borderTop: '1px solid #e9e9e9',
        padding: '20px 20px',
        background: '#fff',
        textAlign: 'right',
        zIndex: 1,
      }"
    >
      <a-button
        :style="{ marginRight: '8px' }"
        @click="handleSaveForm('/serviceRecord/addServiceRecord')"
      >
        暂存
      </a-button>
      <a-popconfirm
        :autoAdjustOverflow="false"
        title="是否确认提交?"
        placement="topRight"
        @confirm="handleSaveForm('/serviceRecord/submit')"

      >
        <a-button type="primary">
          提交
        </a-button>
      </a-popconfirm>
    </div>
  </a-drawer>
</template>

<script>
import ystable from '@/components/Table/ystable'
import SelectCompany from '@/components/Selects/SelectCompany'
import DeptSelect from '@/components/Selects/DeptSelect'
import SearchSelect from '@/components/Selects/SearchSelect'
import ServiceEditTable from '@/views/customer/modules/ServiceEditTable'
import DateRange from '@/components/DateRange/DateRange'

export default {
  name: 'WorkRecordAdd',
  components: { DateRange, ServiceEditTable, SearchSelect, DeptSelect, SelectCompany, ystable },
  props: {
    dictionary: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      queryParams: {},
      visible: false,
      editCus: false,
      form: {},
      serviceNoList: [],
      owner: {},
      validRules: {
        itemType: [{ required: true, message: '事由必须填写', trigger: 'blur' }],
        date: [{ required: true, message: '起始日期必须选择', trigger: 'blur' }],
        amount: [{ required: true, message: '金额必须填写', trigger: 'blur' }]
      }
    }
  },
  watch: {
    owner (v) {
      if (!v) {
        this.form.deptId = undefined
        this.form.deptName = undefined
        this.form.ownerId = undefined
        this.form.ownerName = undefined
      } else {
        this.form.deptId = v.deptId
        this.form.deptName = v.deptName
        this.form.ownerId = v.id
        this.form.ownerName = v.realName
      }
    }
  },
  methods: {
    open () {
      this.visible = true
    },
    close () {
      Object.assign(this.$data, this.$options.data())
    },
    changeRangeDate (row, dates, dateStr) {
      row.begin = dateStr[0]
      row.end = dateStr[1]
      row.date = `${dateStr[0]}-${dateStr[1]}`
    },
    footerMethod ({ columns, data }) {
      return [columns.map((col, _colI) => {
        if (_colI === 0) {
          return '合计'
        }
        if (_colI === 3) {
          return data.reduce((total, row) => {
            if (isNaN(row.amount)) return total
            return total + row.amount
          }, 0).toFixed(2)
        }
        return ''
      })]
    },
    getCustomerList (data, option) {
      if (!data) {
        this.form = {
          ...this.form,
          customerId: undefined,
          serviceNo: undefined
        }
        this.serviceNoList = []
        return
      }
      const { datasource } = option.data.attrs
      this.form = {
        ...this.form,
        customerId: datasource.customerId
      }
      if (datasource.ownerId) {
        this.owner = {
          id: datasource.ownerId,
          realName: datasource.ownerName,
          deptId: datasource.deptId,
          deptName: datasource.deptName
        }
      }
    },
    type2value (key) {
      let result = '-'
      if (key) {
        this.dictionary.forEach(item => {
          if (item.key === key) {
            result = item.value
            return false
          }
        })
      }
      return result
    },
    handleSaveForm (url) {
      if (!this.form.customerId) {
        this.$message.error('请选择客户名称')
        return
      }
      if (!this.form.ownerId) {
        this.$message.error('请选择业务员')
        return
      }
      const [flag, list] = this.$refs.ServiceEditTable.validAllEvent()
      if (!flag) return
      const params = {
        ...this.form,
        list
      }
      this.$http.post(url, params).then(({ success, errorMessage }) => {
        if (success) {
          this.$message.success('操作成功')
          this.close()
          this.$emit('refresh')
        } else {
          this.$message.error(errorMessage)
        }
      })
    },
    clickRow ({ row }) {
      this.form = {
        ...this.form,
        serviceNo: row.serviceNo
      }
      if (row.ownerId) {
        this.owner = {
          id: row.ownerId,
          realName: row.ownerName,
          deptId: row.deptId,
          deptName: row.deptName
        }
      }
    },
    onPulldownFocus () {
      this.$refs.pulldown.showPanel()
      if (this.queryParams.customerId !== this.form.customerId) {
        this.queryParams.customerId = this.form.customerId
        this.$refs.table.refresh(true)
      }
    }
  }
}
</script>

<style lang="less" scoped>
& /deep/ .ant-form-item {
  margin-bottom: 0;
}

.my-dropdown {
  background-color: #fff;
  box-shadow: 0 0 6px 2px rgba(0, 0, 0, 0.1);
}
</style>
