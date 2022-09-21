<template>
  <a-modal
    :width="1000"
    :visible="visible"
    title="添加研发设备"
    :footer="null"
    @cancel="visible = false"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-spin :spinning="confirmLoading" tip="请稍后...">
      <a-form layout="inline" :form="form">
        <a-form-item label="资产代码">
          <a-input v-model="queryParams.ecode" placeholder="请输入资产代码" />
        </a-form-item>
        <a-form-item label="设备名称">
          <a-input v-model="queryParams.ename" placeholder="请输入设备名称" />
        </a-form-item>
        <a-form-item label="折旧月份">
          <a-month-picker v-model="queryParams.depreciationDate" placeholder="请选择折旧月份" />
        </a-form-item>
        <a-form-item label="设备类别">
          <a-input v-model="queryParams.kinds" placeholder="请输入类别" />
        </a-form-item>
        <a-form-item label="设备型号">
          <a-input v-model="queryParams.emodal" placeholder="请输入设备型号" />
        </a-form-item>
        <a-form-item label="设备类型">
          <a-select v-model="queryParams.etype" placeholder="请选择设备类型" style="width:165px">
            <a-select-option value="-1">请选择设备类型</a-select-option>
            <a-select-option v-for="item in $getEnums('equipmentEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="计提月份">
          <a-month-picker
            :disabledDate="disabledDate"
            v-model="queryParams.purchaseDate"
            placeholder="请选择计提月份"
            style="width: 100%"
          />
        </a-form-item>

        <a-form-item>
          <template v-slot:label>&emsp;&emsp;备注</template>
          <a-input v-model="queryParams.remark" placeholder="请输入备注" />
        </a-form-item>
        <a-form-item>
          <a-button style="margin-right:10px;" type="primary" @click="search">查询</a-button>
        </a-form-item>
        <!-- <span style="padding-left:30px;">
        </span>-->
      </a-form>
      <!-- <div> -->
      <ystable
        queryUrl="/rdEquipment/getEquipmentList"
        :params="queryParams"
        highlight-hover-row
        show-overflow
        resizable
        ref="table"
        auto-resize
        @checkbox-all="selectCheckBoxChange"
        @checkbox-change="selectCheckBoxChange"
        :toolbar="{ refresh: true, zoom: true, custom: true, slots: { buttons: 'toolbar_buttons' } }"
      >
        <template v-slot:toolbar_buttons>
          <!-- <a-button
            type="primary"
            @click="handleSubmit"
            style="margin-right:10px"
            :disabled="selectedRowKeys.length <= 0"
          >添加</a-button>-->
        </template>
        <vxe-table-column type="checkbox" width="40" align="center" fixed="left"></vxe-table-column>
        <vxe-table-column
          field="ecode"
          title="资产代码"
          width="140"
          align="left"
          fixed="left"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column
          field="ename"
          title="设备名称"
          width="140"
          align="left"
          fixed="left"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column field="emodal" title="设备型号" width="130" align="left" remoteSort></vxe-table-column>
        <vxe-table-column field="etype" title="设备类型" width="100" align="center" remoteSort>
          <template v-slot="{ row }">{{ row.etype ? $getEnums('equipmentEnum').find(elem => row.etype === elem.value).label : '' }}</template>
        </vxe-table-column>
        <vxe-table-column field="deptName" title="部门" width="120" align="left" remoteSort>
          <template #default="{ row }">
            <span v-if="row.deptId">{{ row.fullname }}</span>
            <span v-else>{{ row.deptName ? `${row.deptName}` : '' }}{{ row.workshop && row.deptName ? '/' : '' }}{{
              row.workshop ? `${row.workshop}` : '' }}{{ row.productLine && (row.deptName || row.workshop) ? '/' : '' }}{{
              row.productLine ? `${row.productLine}` : '' }}{{ row.processSection && (row.deptName || row.workshop || row.productLine) ? '/' : '' }}{{
              row.processSection ? `${row.processSection}` : '' }}</span>
          </template>
        </vxe-table-column>
        <!-- <vxe-table-column field="workshop" title="车间" width="120" align="left" remoteSort></vxe-table-column>
        <vxe-table-column field="productLine" title="产线" width="120" align="left" remoteSort></vxe-table-column>
        <vxe-table-column field="processSection" title="工艺段" width="120" align="left" remoteSort></vxe-table-column> -->
        <vxe-table-column field="unitPrice" title="原值" width="100" align="right" remoteSort></vxe-table-column>
        <vxe-table-column field="quantity" title="数量" width="80" align="right" remoteSort></vxe-table-column>
        <vxe-table-column field="unit" title="单位" width="80" align="center" remoteSort></vxe-table-column>
        <vxe-table-column field="usefullife" title="使用年限" width="100" align="right" remoteSort></vxe-table-column>
        <vxe-table-column field="purchaseDate" title="开始使用日期" width="130" align="center" remoteSort>
          <template v-slot="{ row }">{{ row.purchaseDate | formatDate }}</template>
        </vxe-table-column>
        <vxe-table-column
          field="depreciationDate"
          title="计提折旧日期"
          width="130"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">{{ row.depreciationDate | formatDate }}</template>
        </vxe-table-column>
        <vxe-table-column
          field="invalidated"
          title="停用报废日期"
          width="130"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }"> {{ row.invalidated | formatDate }}</template>
        </vxe-table-column>
        <vxe-table-column
          field="depreciationRate"
          title="月折旧率"
          width="140"
          align="center"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column field="usagePower" title="功率(kWh)" width="110" align="center" remoteSort></vxe-table-column>
        <vxe-table-column field="kinds" title="类别" width="80" align="center" remoteSort></vxe-table-column>
        <vxe-table-column field="remark" title="备注" min-width="180" align="left" remoteSort></vxe-table-column>
      </ystable>
      <div style="margin-top: 12px ; display:flex; flexDirection:row-reverse;">
        <a-button type="primary" @click="handleSubmit" :disabled="selectedRowKeys.length <= 0">添加</a-button>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, Ellipsis } from '@/components'
import moment from 'moment'
import yearMixin from '@/utils/yearMixin'
import { mapState } from 'vuex'
export default {
  mixins: [yearMixin],
  name: 'SelectEquipmenrt',
  components: {
    STable,
    Ellipsis,
    ystable
  },
  data () {
    return {
      confirmLoading: false,
      visible: false,
      form: this.$form.createForm(this),
      queryParams: {},
      selectedRowKeys: [],
      first: true,
      year: -1
    }
  },
  filters: {
    formatDate (value) {
      if (!value) return ''
      return moment(value).format('YYYY-MM-DD')
    }
  },
  computed: {
    ...mapState({
    })
  },
  methods: {
    moment,
    showModal (year) {
      for (const key in this.queryParams) {
        this.queryParams[key] = undefined
      }
      this.queryParams.year = year
      this.year = year
      this.visible = true
      if (!this.first) {
        this.search()
      }
      this.first = false
    },
    handleSubmit () {
      this.confirmLoading = true
      var values = {}
      values.ecodes = this.selectedRowKeys
      values.year = this.year
      this.$http.post('/rdEquipment/addRdEquipments', values)
        .then(res => {
          if (res.data && res.success) {
            this.$message.success('添加成功')
            this.$emit('ok')
            this.visible = false
            this.selectedRowKeys = []
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
          }
        }).finally(r => {
          this.confirmLoading = false
        })
    },
    search () {
      this.selectedRowKeys = []
      if (this.$refs.table) {
        this.$refs.table.refresh(true)
      }
    },
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.ecode })
    }
  }
}
</script>

<style scoped>
</style>
