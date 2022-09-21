<template>
  <a-card :bordered="false" :bodyStyle="{ height: '100%', overflow: 'auto' }">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="资产代码">
          <a-input v-model="queryParams.ecode" placeholder="请输入资产代码" />
        </a-form-item>
        <a-form-item label="设备名称">
          <a-input v-model="queryParams.ename" placeholder="请输入设备名称" />
        </a-form-item>
        <a-form-item label="折旧月份">
          <a-month-picker v-model="queryParams.depreciationDate" placeholder="请选择折旧月份" />
        </a-form-item>
        <a-form-item label="类别">
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
        <a-form-item label="使用月份">
          <a-month-picker
            :disabledDate="disabledDate"
            v-model="queryParams.purchaseDate"
            placeholder="请选择使用月份"
            style="width: 100%"
          />
        </a-form-item>
        <a-form-item label="备注">
          <a-input v-model="queryParams.remark" placeholder="请输入备注" />
        </a-form-item>
        <a-form-item>
          <a-button
            class="btnInterval"
            v-if="$auth('company:equipment:search')"
            type="primary"
            @click="search(true)"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <ystable
        ref="table"
        queryUrl="/equipment/queryAll"
        :params="queryParams"
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        @checkbox-all="selectAllEvent"
        @checkbox-change="selectChangeEvent"
        :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
      >
        <template v-slot:toolbar_buttons>
          <a-button
            class="btnInterval"
            type="primary"
            v-if="$auth('company:equipment:add')"
            @click="$refs.createModal.add()"
          >添加</a-button>
          <a-button
            class="btnInterval"
            type="primary"
            v-if="$auth('company:equipment:import')"
            @click="openUploadModal"
          >导入</a-button>
          <a-button
            class="btnInterval"
            type="primary"
            v-if="$auth('company:equipment:export')"
            @click="exportEquipment"
          >导出</a-button>
          <a-button
            class="btnInterval"
            type="primary"
            :disabled="selectedRowKeys.length <= 0"
            v-if="$auth('company:equipment:del')"
            @click="delLists"
          >删除</a-button>
          <a-button
            class="btnInterval"
            type="primary"
            v-if="$auth('company:equipment:setType')"
            :disabled="selectedRowKeys.length <= 0"
            @click="$refs.settingEtype.add(selectedRowKeys)"
          >设置类型</a-button>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            @click="$refs.configColumn.showModal(3)"
            v-if="$auth('company:equipment:config')"
          >自定义列</a-button>
        </template>
        <vxe-table-column type="checkbox" width="40" align="center" fixed="left"></vxe-table-column>
        <vxe-table-column
          field="ecode"
          title="资产代码"
          width="120"
          align="left"
          fixed="left"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column field="ename" title="设备名称" min-width="150" align="left" remoteSort></vxe-table-column>
        <vxe-table-column field="emodal" title="设备型号" width="110" align="left" remoteSort></vxe-table-column>
        <vxe-table-column field="kinds" title="类别" width="100" align="left" remoteSort></vxe-table-column>
        <vxe-table-column field="etype" title="设备类型" width="100" align="center" remoteSort>
          <template v-slot="{ row }">{{ row.etype ? $getEnums('equipmentEnum').find(elem => elem.value === row.etype).label : '' }}</template>
        </vxe-table-column>
        <vxe-table-column field="fullname" title="部门" width="130" align="left" remoteSort>
          <template v-slot="{ row }">
            <span v-if="row.deptId">{{ row.fullname }}</span>
            <span v-else>
              {{ row.deptName ? `${row.deptName}` : '' }}{{ row.workshop && row.deptName ? '/' : '' }}{{
                row.workshop ? `${row.workshop}` : '' }}{{ row.productLine && (row.deptName || row.workshop) ? '/' : '' }}{{
                row.productLine ? `${row.productLine}` : '' }}{{ row.processSection && (row.deptName || row.workshop || row.productLine) ? '/' : '' }}{{
                row.processSection ? `${row.processSection}` : '' }}
            </span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="unitPrice" title="原值" width="100" align="right" remoteSort></vxe-table-column>
        <vxe-table-column field="quantity" title="数量" width="100" align="right" remoteSort></vxe-table-column>
        <vxe-table-column field="unit" title="单位" width="100" align="center" remoteSort></vxe-table-column>
        <vxe-table-column field="usefullife" title="使用年限" width="100" align="right" remoteSort></vxe-table-column>
        <vxe-table-column field="purchaseDate" title="开始使用日期" width="140" align="center" remoteSort>
          <template v-slot="{ row }">
            {{ row.purchaseDate | formatDate }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="depreciationDate"
          title="计提折旧日期"
          width="140"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            {{ row.depreciationDate | formatDate }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="invalidated"
          title="停用报废日期"
          width="140"
          align="center"
          remoteSort
        >
          <template v-slot="{ row }">
            {{ row.invalidated | formatDate }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="depreciationRate"
          title="月折旧率"
          width="100"
          align="right"
          remoteSort
        ></vxe-table-column>
        <vxe-table-column field="usagePower" title="功率(kWh)" width="110" align="right" remoteSort></vxe-table-column>
        <vxe-table-column
          v-for="config in equipmentArr"
          :key="config.name"
          align="center"
          :title="config.alias"
          width="80"
        >
          <template v-slot="{ row }">{{ row && row.data ? JSON.parse(row.data)[config.name] : '' }}</template>
        </vxe-table-column>
        <vxe-table-column field="remark" title="备注" width="140" align="left" remoteSort></vxe-table-column>
        <vxe-table-column title="操作" width="120" align="center" fixed="right">
          <template v-slot="{ row }">
            <a
              v-if="$auth('company:equipment:edit')"
              href="javascript:;"
              @click="$refs.createModal.edit(row)"
            >编辑</a>
            <a-divider type="vertical" v-if="$auth('company:equipment:edit')" />
            <a-popconfirm
              title="是否确定删除?"
              @confirm="handleDel(row)"
            >
              <a v-if="$auth('company:equipment:del')">删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ystable>
      <equipment-modal ref="createModal" @ok="(ok)=>search(ok)" :equipmentArr="equipmentArr"></equipment-modal>
      <setting-EquipmentEtype-modal ref="settingEtype" @ok="search(false)"></setting-EquipmentEtype-modal>
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        paramKey="tableField"
        title=" "
        ref="uploadModal"
        action="/doc/equipment/importEquipment"
        templateName="设备列表模板"
        @onSuccess="success"
        @error="error"
      />
      <config-column-modal ref="configColumn" @ok="reloadTable" />
    </a-spin>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, UploadModal, Ellipsis, EditTableTitle } from '@/components'
import equipmentModal from './modules/EquipmentModal'
import SettingEquipmentEtypeModal from './modules/SettingEquipmentEtypeModal'
import ConfigColumnModal from './modules/ConfigColumnModal'
import moment from 'moment'
import { mixinLoadTitleObject } from '@/utils/mixin.js'
import { mapState } from 'vuex'
const defaultTableField = {
  tableId: 'equipmentTable',
  fieldTitleObject: {
    ecode: { title: '资产代码', required: true, defaultTitle: '资产代码', importField: true },
    ename: { title: '设备名称', required: true, defaultTitle: '设备名称', importField: true },
    unitPrice: { title: '原值', required: true, defaultTitle: '原值', importField: true },
    fullname: { title: '部门', required: true, defaultTitle: '部门', importField: true },
    emodal: { title: '设备型号', defaultTitle: '设备型号', importField: true },
    kinds: { title: '类别', defaultTitle: '类别', importField: true },
    typeName: { title: '设备类型', defaultTitle: '设备类型', importField: true },
    quantity: { title: '数量', defaultTitle: '数量', importField: true },
    unit: { title: '单位', defaultTitle: '单位', importField: true },
    usefullife: { title: '使用年限', defaultTitle: '使用年限', importField: true },
    purchaseDate: { title: '开始使用日期', defaultTitle: '开始使用日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
    depreciationDate: { title: '计提折旧日期', defaultTitle: '计提折旧日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
    invalidated: { title: '停用报废日期', defaultTitle: '停用报废日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
    depreciationRate: { title: '月折旧率', defaultTitle: '月折旧率', importField: true },
    usagePower: { title: '功率(kWh)', defaultTitle: '功率(kWh)', importField: true },
    remark: { title: '备注', defaultTitle: '备注', importField: true }
  }
}
const defaultTitleObj = [{
  ecode: 'ZC-JQ-00081',
  ename: '上煤皮带机',
  emodal: '	J001',
  fullname: 'xxx部门',
  typeName: '请从中选择填写：仪器/设备/软件摊销/房屋建筑，例如：设备',
  unitPrice: '数字格式，例如：300000',
  unit: '台',
  quantity: '数字格式，例如：300000',
  usefullife: '数字格式，例如：4',
  purchaseDate: '格式："年-月-日"，例如：2019-10-11',
  depreciationDate: '格式："年-月-日"，例如：2019-10-11',
  invalidated: '格式："年-月-日"，例如：2019-10-11',
  depreciationRate: '小数字格式，范围：0-1，例如：0.0792',
  usagePower: '数字格式，例如：300000',
  kinds: '类别',
  remark: '备注'
}]
export default {
  name: 'Equipmenrt',
  mixins: [mixinLoadTitleObject],
  components: {
    STable,
    UploadModal,
    equipmentModal,
    Ellipsis,
    SettingEquipmentEtypeModal,
    ConfigColumnModal,
    EditTableTitle,
    ystable
  },
  data () {
    return {
      spinning: false,
      queryParams: {},
      selectedRowKeys: [],
      tableField: {},
      sampleData: [],
      equipmentArr: null
    }
  },
  created () {
    this.getTableField()
    this.getEquipmentConfig()
  },
  filters: {
    formatDate (value) {
      if (!value) { return '' }
      return moment(value).format('YYYY-MM-DD')
    }
  },
  computed: {
    ...mapState({
    })
  },
  methods: {
    moment,
    getEquipmentConfig () {
      this.$http.get('/fieldConfig/getTypeConfig', { params: { type: 3 } }).then(res => {
        if (res.success && res.data) {
          this.equipmentArr = JSON.parse(res.data.config)
        }
      })
    },
    reloadTable (edit) {
      if (edit) {
        this.getEquipmentConfig()
      }
      this.$refs.table.refresh(true)
    },
    getTableField () {
      this.$http.get('/sysDictionary/getTableField', { params: { tableId: this.tableField.tableId } })
        .then(res => {
          if (res.success && res.data != null) {
            this.tableField = res.data
          }
        })
    },
    onSaveTitle (value, name) {
      this.tableField.fieldTitleObject[name].title = value
      this.tableField.fieldTitle = JSON.stringify(this.tableField.fieldTitleObject)
      this.$http.post('/sysDictionary/saveTableField', this.tableField).then(res => {
        this.$message.success('保存成功')
        this.getTableField()
      })
    },
    openUploadModal () {
      this.loadTitle()
      this.$refs.uploadModal.show(this.tableField)
    },
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          content: resData,
          onOk () {

          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
      }
      this.search(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.$refs.table.refresh(refresh)
    },
    handleDel (record) {
      this.spinning = true
      this.$http.get('/equipment/delEquipment', { params: { id: record.id } })
        .then(res => {
          if (res.success) {
            this.$message.success('删除成功')
            this.search(false)
          } else {
            this.$message.error(`[${record.ename}]${res.errorMessage ? res.errorMessage : '删除失败'}`)
          }
          this.spinning = false
        })
    },
    delLists () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的设备吗?',
        content: <b>已被加计扣除使用的设备不会删除。</b>,
        onOk () {
          var params = {}
          params.ids = self.selectedRowKeys
          return self.$http.post('/equipment/delEquipments', params)
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.search(false)
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    loadTitle () {
      ({ sampleData: this.sampleData, tableField: this.tableField } = this.loadTitleObject('remark', this.equipmentArr, defaultTableField, defaultTitleObj))
    },
    exportEquipment () {
      this.spinning = true
      this.getTableField()
      this.loadTitle()
      this.$http.get('/equipment/exportEquipment', { params: this.queryParams })
        .then(res => {
          if (res.success) {
            var keys = Object.keys(this.tableField.fieldTitleObject).map(a => this.tableField.fieldTitleObject[a].title)
            var sheetData = res.data
            sheetData.forEach(r => {
              if (r.data && typeof (r.data) === 'string') {
                r = Object.assign(r, JSON.parse(r.data))
              }
            })
            var sheetFilter = Object.keys(this.tableField.fieldTitleObject)
            this.$exportJsonData('设备列表数据', keys, sheetFilter, sheetData)
          } else {
            this.$message.error('导出失败' + res.errorMessage ? res.errorMessage : '')
          }
        }).finally(res => {
          this.spinning = false
        })
    },
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    selectChangeEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
    },
    selectAllEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => { return item.id })
    }
  }
}
</script>
<style lang="less" scoped>
.btnInterval {
  margin-right: 10px;
}
</style>
