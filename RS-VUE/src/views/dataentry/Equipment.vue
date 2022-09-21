<template>
  <a-card :bordered="false">
    <a-spin
      tip="请稍后..."
      :spinning="spinning"
    >
      <a-form layout="inline">
        <a-form-item label="月份">
          <a-select
            style="width: 174px;"
            v-model="queryParam.monthValue"
            placeholder="请选择月份"
            :allowClear="true"
            :options="monthOptions"
          ></a-select>
        </a-form-item>
        <a-form-item label="资产代码">
          <a-input
            v-model="queryParam.ecode"
            placeholder="请输入资产代码"
          />
        </a-form-item>
        <a-form-item label="设备名称">
          <a-input
            v-model="queryParam.ename"
            placeholder="请输入设备名称"
          />
        </a-form-item>
        <a-form-item label="部门">
          <a-input
            v-model="queryParam.deptName"
            placeholder="请输入部门"
          />
        </a-form-item>
        <a-form-item label="设备类型">
          <a-select
            style="width: 174px;"
            v-model="queryParam.etype"
            placeholder="请选择设备类型"
          >
            <a-select-option value="-1">请选择设备类型</a-select-option>
            <a-select-option v-for="item in $getEnums('equipmentEnum')" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item>
          <a-button
            style="margin-right: 10px;"
            v-if="$auth('dataentry:equipment:search')"
            type="primary"
            @click="search(true)"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          queryUrl="/dEquipment/getList"
          :params="getParams()"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          :toolbar="tableToolbar"
        >
          <template v-slot:buttons>
            <a-button
              type="primary"
              v-if="$auth('dataentry:equipment:add')"
              @click="$refs.equipmentModal.add()"
              style="margin-right:10px"
            >添加</a-button>
            <a-button
              type="primary"
              v-if="$auth('dataentry:equipment:import')"
              @click="$refs.uploadModal.show(tableField)"
              style="margin-right:10px"
            >导入</a-button>
            <a-button
              type="primary"
              v-if="$auth('dataentry:equipment:export')"
              @click="exportEqipment"
              style="margin-right:10px"
            >导出</a-button>
            <a-button
              type="primary"
              v-if="$auth('dataentry:equipment:del')"
              :disabled="selectedRowKeys.length <= 0"
              @click="delList"
              style="margin-right:10px"
            >删除</a-button>
            <a-button
              type="primary"
              v-if="$auth('dataentry:equipment:setHour')"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.setWorkHourModel.showModal(selectedRows)"
              style="margin-right:10px"
            >设置运行工时</a-button>
            <!-- <a-button
              type="primary"
              v-if="$auth('company:equipment:setType')"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.setType.add(selectedRowKeys)"
              style="margin-right:10px"
            >设置类型</a-button> -->
          </template>
          <vxe-table-column
            type="checkbox"
            width="40"
            fixed="left"
          />
          <vxe-table-column
            title="月份"
            field="month"
            width="100"
            remoteSort
            align="center"
          ></vxe-table-column>
          <vxe-table-column
            title="资产代码"
            field="ecode"
            width="120"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="设备名称"
            field="ename"
            width="200"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="设备型号"
            field="emodal"
            width="120"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="设备类型"
            field="etype"
            width="120"
            remoteSort
            align="center"
          >
            <template v-slot="{ row }">
              {{ row.etype ? $getEnums('equipmentEnum').find(elem => row.etype === elem.value).label : '' }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="月折旧额"
            field="depreciation"
            width="120"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="月折旧率"
            field="depreciationRate"
            width="120"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="运行工时"
            field="workHours"
            width="120"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="部门"
            field="deptName"
            width="120"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="科目"
            field="fullAccountName"
            min-width="150"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="操作"
            field="操作"
            width="120"
            align="center"
            fixed="right"
          >
            <template v-slot="{ row }">
              <span v-if="$auth('dataentry:equipment:edit')">
                <a
                  href="javascript:;"
                  @click="$refs.equipmentModal.edit(row)"
                >编辑</a>
                <a-divider
                  type="vertical"
                  v-if="$auth('dataentry:equipment:del')"
                />
              </span>
              <a-popconfirm
                title="是否确定删除?"
                @confirm="del(row)"
              >
                <a v-if="$auth('dataentry:equipment:del')">删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <equipment-Modal
        ref="equipmentModal"
        @ok="(ok)=>search(ok)"
      ></equipment-Modal>
      <set-work-hour-modal
        ref="setWorkHourModel"
        @ok="search(false)"
      ></set-work-hour-modal>
      <setting-equipment-type-modal
        ref="setType"
        @ok="search(false)"
      />
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        paramKey="name"
        title="导入设备使用"
        ref="uploadModal"
        action="/doc/dEquipment/importDEquipment"
        templateName="设备使用模板"
        @onSuccess="success"
        @error="error"
      />
    </a-spin>
  </a-card>
</template>

<script>
import { UploadModal, TimeDay, Ellipsis, EditTableTitle } from '@/components'
import equipmentModal from './modules/EquipmentModal'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import SetWorkHourModal from './modules/SetWorkHourModal'
import SettingEquipmentTypeModal from './modules/SettingEquipmentTypeModal'
import ystable from '@/components/Table/ystable'
import { mapState } from 'vuex'
// const etypeArr = { '0': '普通', '30000': '设备', '30100': '仪器', '40001': '软件摊销' }
export default {
  mixins: [yearMiXin],
  name: 'Equipment',
  components: {
    UploadModal,
    equipmentModal,
    TimeDay,
    SetWorkHourModal,
    EditTableTitle,
    SettingEquipmentTypeModal,
    Ellipsis,
    ystable
  },
  data () {
    return {
      tableToolbar: {
        refresh: true,
        zoom: true,
        custom: true
      },
      scroll: {},
      spinning: false,
      queryParam: {},
      selectedRowKeys: [],
      inputMode: 'checkbox',
      selectedRows: [],
      tableField: {
        tableId: 'dataEquipmentTable',
        fieldTitleObject: {
          month: { title: '月份', required: true, defaultTitle: '月份', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-01' },
          ecode: { title: '资产代码', required: true, defaultTitle: '资产代码', importField: true },
          ename: { title: '设备名称', required: true, defaultTitle: '设备名称', importField: true },
          depreciation: { title: '月折旧额', required: true, defaultTitle: '月折旧额', importField: true },
          depreciationRate: { title: '月折旧率', defaultTitle: '月折旧率', importField: true },
          workHours: { title: '运行工时', defaultTitle: '运行工时', importField: true },
          deptName: { title: '部门', defaultTitle: '部门', importField: true },
          fullAccountName: { title: '科目', defaultTitle: '科目', importField: true },
          emodal: { title: '设备型号', defaultTitle: '设备型号', importField: true },
          typeName: { title: '设备类型', defaultTitle: '设备类型', importField: true }
        },
        hasMonth: true
      },
      sampleData: [
        {
          ecode: 'ZC-JQ-00081',
          ename: '上煤皮带机',
          month: '格式："年-月-日"，一般为当月第一天，例如：2019-10-01',
          workHours: '数字格式,工作小时，范围：0-24，例如：23',
          depreciation: '数字格式，例如：300000',
          emodal: 'L001',
          deptName: 'xxx部门',
          typeName: '请从中选择填写：仪器/设备/软件摊销/房屋建筑，例如：设备',
          depreciationRate: '数字格式，例如：300000',
          fullAccountName: '科目'
        }
      ],
      title: '',
      labelCol: {
        md: { span: 8 }
      },
      wrapperCol: {
        md: { span: 16 }
      },
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this)
    }
  },
  created () {
    this.getTableField()
    if (document.body.clientWidth < 1780) {
      this.scroll = { x: 1800 }
    }
  },
  mounted () {
    window.onresize = () => {
      return (() => {
        this.scroll = {}
        if (document.body.clientWidth < 1780) {
          this.scroll = { x: 1800 }
        }
      })()
    }
  },
  computed: {
    ...mapState({
    })
  },
  methods: {
    getParams () {
      this.initParam()
      return this.queryParam
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
    existDate (n, month) {
      var now = new Date(month)
      return n <= new Date(now.getFullYear(), now.getMonth() + 1, 0).getDate()
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
    moment,
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.selectedRows = []
      this.initParam()
      this.$refs.table.refresh(refresh)
    },

    del (record) {
      this.spinning = true
      var value = {}
      value.id = record.id
      this.$http.post('/dEquipment/del', value).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
          this.search(false)
        } else {
          this.$message.error(`${res.errorMessage ? res.errorMessage : '删除失败'}`)
        }
        this.spinning = false
      })
    },
    delList () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的设备使用吗?',
        content: <b>已被加计扣除使用的设备不会删除。</b>,
        onOk () {
          var values = []
          self.selectedRowKeys.forEach(e => {
            var node = {}
            node.id = e
            values.push(node)
          })
          return self.$http.post('/dEquipment/dels', values).then(res => {
            if (res.success && res.data) {
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
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    initParam () {
      if (this.queryParam.monthValue === Number.undefined || this.queryParam.monthValue === '-1') {
        var startMonth = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
        var endMonth = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
        this.queryParam.startMonth = startMonth
        this.queryParam.endMonth = endMonth
        this.queryParam.month = null
      } else {
        this.queryParam.month = moment([this.currentYear, this.queryParam.monthValue, 1, 0, 0, 0, 0])
        this.queryParam.startMonth = null
        this.queryParam.endMonth = null
      }
      this.queryParam.year = this.currentYear
    },
    exportEqipment () {
      this.spinning = true
      this.initParam()
      this.$http.get('/dEquipment/exportDataEquipment', { params: this.queryParam })
        .then(res => {
          if (res.success) {
            const table = this.$deepClone(this.tableField)
            delete table.fieldTitleObject.unitPrice
            delete table.fieldTitleObject.unit
            delete table.fieldTitleObject.quantity
            delete table.fieldTitleObject.usefullife
            delete table.fieldTitleObject.purchaseDate
            delete table.fieldTitleObject.depreciationDate
            delete table.fieldTitleObject.usagePower
            delete table.fieldTitleObject.kinds
            var keys = Object.keys(table.fieldTitleObject).map(a => table.fieldTitleObject[a].title)
            var sheetData = res.data
            var sheetFilter = Object.keys(table.fieldTitleObject)
            this.$exportJsonData('设备使用列表数据', keys, sheetFilter, sheetData)
          } else {
            this.$message.error(`导出失败${res.errorMessage ? ':' + res.errorMessage : ''}`)
          }
        }).finally(() => {
          this.spinning = false
        })
    },
    selectChange ({ records }) {
      this.selectedRows = records
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    }
  }
}
</script>

<style>
.table-page-search-wrapper .ant-form-inline .ant-form-item > .ant-form-item-label {
  width: 33%;
}
</style>
