<template>
  <a-modal
    :width="1000"
    :visible="visible"
    :title="title"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    @ok="handleSubmit"
    @cancel="closeModal"
  >
    <div>
      <a-form layout="inline" :form="form">
        <a-form-item label="资产代码">
          <a-input v-model="queryParam.ecode" style="width:130px;" placeholder="请输入资产代码" />
        </a-form-item>

        <a-form-item label="设备名称">
          <a-input v-model="queryParam.ename" style="width:130px;" placeholder="请输入设备名称" />
        </a-form-item>

        <a-form-item label="研发部门">
          <select-down
            ref="rdDeptSelet"
            treeType="rdDept"
            @fullPath="rdDeptSelected"
            style="width:130px;"
            placeholder="请选择研发部门"
            :year="this.currentYear"
          />
        </a-form-item>

        <a-form-item label="类别">
          <a-input style="width:130px;" v-model="queryParam.kinds" placeholder="请输入类别" />
        </a-form-item>

        <a-form-item label="设备类型">
          <a-select v-model="queryParam.etype" placeholder="请选择设备类型" style="width:130px;">
            <a-select-option value="-1">请选择设备类型</a-select-option>
            <a-select-option v-for="item in $getEnums('equipmentEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>

        <!-- <a-form-item label="车间">
          <a-input style="width:130px;" v-model="queryParam.workshop" placeholder="请输入车间" />
        </a-form-item> -->
        <a-form-item label="备注">
          <a-input style="width:130px;" v-model="queryParam.remark" placeholder="请输入备注" />
        </a-form-item>
        <a-form-item v-if="$auth('project:report:equipment:add')">
          <a-button type="primary" @click="search">查询</a-button>
        </a-form-item>
      </a-form>
    </div>
    <ystable
      ref="table"
      queryUrl="/initEquipment/getEquipmentList"
      :params="queryParam"
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      @checkbox-all="selectCheckBoxChange"
      @checkbox-change="selectCheckBoxChange"
      :toolbar="{ refresh: true, zoom: true, custom: true, slots: { buttons: 'toolbar_buttons' } }"
    >
      <template v-slot:toolbar_buttons>
        <span>
          当前选中个数：
          <a>{{ selectedRowKeys.length }}</a>
        </span>
      </template>
      <vxe-table-column type="checkbox" width="40" align="center" fixed="left"></vxe-table-column>
      <vxe-table-column field="ecode" title="资产代码" width="110" fixed="left" remoteSort />
      <vxe-table-column field="ename" title="设备名称" width="110" fixed="left" remoteSort />
      <vxe-table-column field="emodal" title="设备型号" width="110" remoteSort />
      <vxe-table-column field="etype" title="设备类型" width="110" align="center" remoteSort>
        <template v-slot="{ row }">{{ row.etype ? $getEnums('equipmentEnum').find(elem => elem.value === row.etype).label : '' }}</template>
      </vxe-table-column>
      <vxe-table-column field="rdDeptName" title="研发部门" width="110" remoteSort>
        <template v-slot="{ row }">{{ keyMap[row.rdDeptId] }}</template>
      </vxe-table-column>
      <vxe-table-column field="kinds" title="类别" width="110" remoteSort />
      <vxe-table-column field="deptName" title="部门" width="110" remoteSort >
        <template #default="{ row }">
          <span v-if="row.deptId">{{ row.fullname }}</span>
          <span v-else>{{ row.deptName ? `${row.deptName}` : '' }}{{ row.workshop && row.deptName ? '/' : '' }}{{
            row.workshop ? `${row.workshop}` : '' }}{{ row.productLine && (row.deptName || row.workshop) ? '/' : '' }}{{
            row.productLine ? `${row.productLine}` : '' }}{{ row.processSection && (row.deptName || row.workshop || row.productLine) ? '/' : '' }}{{
            row.processSection ? `${row.processSection}` : '' }}</span>
        </template>
      </vxe-table-column>
      <!-- <vxe-table-column field="workshop" title="车间" width="110" remoteSort />
      <vxe-table-column field="productLine" title="产线" width="110" remoteSort />
      <vxe-table-column field="processSection" title="工艺段" width="110" remoteSort /> -->
      <vxe-table-column field="remark" title="备注" min-width="180" align="left" remoteSort></vxe-table-column>
    </ystable>
  </a-modal>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, SelectDown } from '@/components'
import { mapState } from 'vuex'
export default {
  name: 'MemberModal',
  components: {
    ystable,
    STable,
    SelectDown
  },
  data () {
    return {
      keyMap: {},
      selectedRowKeys: [],
      rowKeyObj: {},
      confirmLoading: false,
      title: '',
      queryParam: {},
      currentYear: undefined,
      visible: false,
      firstLoad: false,
      form: this.$form.createForm(this)
    }
  },
  computed: {
    ...mapState({
    })
  },
  methods: {
    closeModal () {
      this.visible = false
      this.selectedRowKeys = []
      this.rowKeyObj = {}
      this.resetDept()
    },
    rdDeptSelected (rdDeptPath) {
      this.queryParam.rdDeptPath = rdDeptPath
    },
    resetDept () {
      this.queryParam.deptId = -1
      if (this.$refs.deptSelet) {
        this.$refs.deptSelet.setValue(0)
      }
    },
    onSelectChange (selectedRowKeys, rows) {
      this.selectedRowKeys = selectedRowKeys
      rows.forEach(r => {
        this.rowKeyObj[r.id] = r
      })
    },
    getPostItems () {
      var itemRows = []
      for (var i = 0; i < this.selectedRowKeys.length; i++) {
        const id = this.selectedRowKeys[i]
        const item = this.rowKeyObj[id]
        if (item) {
          itemRows.push(item.ecode)
        }
      }
      return itemRows
    },
    showModal (projectId, pname, rdTitle, year) {
      this.queryParam = {}
      this.currentYear = year
      this.queryParam.projectId = projectId
      this.queryParam.year = year
      this.confirmLoading = false
      this.title = `添加项目设备[${pname}-${rdTitle}]`
      this.visible = true
      this.$nextTick(() => {
        this.$getTree('rdDept', false, year).then(res => {
          this.keyMap = res.keyMap
        })
        if (this.firstLoad) {
          this.search()
        }
        this.firstLoad = true
      })
    },
    search () {
      this.$refs.table.refresh(true)
    },
    handleSubmit () {
      this.confirmLoading = true
      var value = {}
      value.ecodes = this.getPostItems()
      if (value.ecodes === null || value.ecodes.length <= 0) {
        this.$emit('error', '未选择任何设备')
        this.confirmLoading = false
        return
      }
      value.projectId = this.queryParam.projectId
      value.year = this.queryParam.year
      this.$http.post('/initEquipment/addList', value).then(res => {
        if (res.success && res.data) {
          this.closeModal()
          this.$emit('ok')
          this.confirmLoading = false
        } else {
          this.$emit('error', res.errorMessage ? res.errorMessage : '添加失败')
          this.confirmLoading = false
        }
      })
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
      records.forEach(r => {
        this.rowKeyObj[r.id] = r
      })
    }
  }
}
</script>

<style>
.word-wrap {
  word-break: break-all;
}
</style>
