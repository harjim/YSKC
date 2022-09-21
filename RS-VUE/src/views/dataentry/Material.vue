<template>
  <a-card :bordered="false">
    <span v-if="control.search">
      <a-spin tip="请稍后..." :spinning="spinning">
        <a-form layout="inline">
          <a-form-item
            label="物料编码"
          >
            <a-input v-model="queryParam.mcode" placeholder="请输入物料编码" />
          </a-form-item>
          <a-form-item
            label="物料名称"
          >
            <a-input v-model="queryParam.mname" placeholder="请输入物料名称" />
          </a-form-item>
          <a-form-item
            label="制单人"
          >
            <a-input v-model="queryParam.biller" placeholder="请输入制单人" />
          </a-form-item>
          <a-form-item
            label="月份"
          >
            <a-select
              style="width: 174px;"
              v-model="queryParam.monthValue"
              placeholder="请选择月份"
              :allowClear="true"
              :options="monthOptions"
            ></a-select>
          </a-form-item>
          <a-form-item
            label="部门"
          >
            <a-input v-model="queryParam.deptName" placeholder="请输入部门" />
          </a-form-item>
          <a-form-item
            label="出库单号"
          >
            <a-input v-model="queryParam.billNo" placeholder="请输入出库单号" />
          </a-form-item>
          <a-form-item
            label="记帐人"
          >
            <a-input v-model="queryParam.booker" placeholder="请输入记帐人" />
          </a-form-item>
          <a-form-item
            label="仓库"
          >
            <a-input v-model="queryParam.warehouse" placeholder="请输入仓库" />
          </a-form-item>
          <a-form-item
            label="规格型号"
          >
            <a-input v-model="queryParam.specification" placeholder="请输入规格型号" />
          </a-form-item>
          <a-form-item
            label="审核人"
          >
            <a-input v-model="queryParam.auditor" placeholder="请输入审核人" />
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              v-if="control.search"
              @click="search(true)"
            >查询</a-button>
          </a-form-item>
        </a-form>
        <ystable
          ref="table"
          queryUrl="/material/queryMaterial"
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
              v-if="control.add"
              @click="$refs.modal.add(type,currentYear)"
              style="margin-right:10px"
            >添加</a-button>
            <a-button
              type="primary"
              v-if="control.import"
              @click="openUploadModal(false)"
              style="margin-right:10px"
            >导入</a-button>
            <a-button
              type="primary"
              v-if="control.export"
              @click="exportQuipment"
              style="margin-right:10px"
            >导出</a-button>
            <a-button
              type="primary"
              v-if="control.delete"
              :disabled="selectedRowKeys.length <= 0"
              @click="delSelect"
              style="margin-right:10px"
            >删除</a-button>
            <a-button
              type="primary"
              v-if="control.rdImport"
              @click="openUploadModal(true)"
              style="margin-right:10px"
            >导入归集数据</a-button>
            <a-button
              type="primary"
              v-if="control.setPicker"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.setPicker.open(selectedRowKeys)"
              style="margin-right:10px"
            >设置领料信息</a-button>
          </template>
          <vxe-table-column type="checkbox" width="40" fixed="left" />
          <vxe-table-column title="物料名称" field="mname" width="120" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="物料编码" field="mcode" width="100" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="领用日期" field="acqDate" width="140" remoteSort align="center"></vxe-table-column>
          <vxe-table-column title="单价" field="unitPrice" width="80" remoteSort align="right"></vxe-table-column>
          <vxe-table-column title="数量" field="quantity" width="80" remoteSort align="right"></vxe-table-column>
          <vxe-table-column title="金额" field="totalAmount" width="80" remoteSort align="right"></vxe-table-column>
          <vxe-table-column title="单位" field="unit" width="60" align="center"></vxe-table-column>
          <vxe-table-column title="出库单号" field="billNo" width="100" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="用途" field="purpose" width="100" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="凭证号" field="accNumber" width="120" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="规格型号" field="specification" width="100" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="部门" field="deptName" width="150" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="仓库" field="warehouse" width="100" remoteSort align="center"></vxe-table-column>
          <vxe-table-column title="科目" field="fullAccountName" width="160" remoteSort align="left"></vxe-table-column>
          <vxe-table-column title="领料人" field="picker" width="80" align="left"></vxe-table-column>
          <vxe-table-column title="制单人" field="biller" width="80" align="left"></vxe-table-column>
          <vxe-table-column title="审核人" field="auditor" width="80" align="left"></vxe-table-column>
          <vxe-table-column title="记帐人" field="booker" width="80" align="left"></vxe-table-column>
          <vxe-table-column title="备注" field="remark" min-width="150" align="left"></vxe-table-column>
          <vxe-table-column title="操作" field="action" width="100" fixed="right" align="center">
            <template v-slot="{ row }">
              <a href="javascript:;" @click="handleEdit(row)" v-if="control.edit">编·辑</a>
              <span v-if="control.delete">
                <a-divider type="vertical" v-if="control.edit" />
                <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)">
                  <a>删除</a>
                </a-popconfirm>
              </span>
            </template>
          </vxe-table-column>
        </ystable>
        <material-modal ref="modal" @ok="handleOk"></material-modal>
        <upload-modal
          :sampleData="sampleData"
          :showProgress="true"
          :paramData="paramData"
          paramKey="tableField"
          :title="uploadTitle"
          :templateName="templateName"
          :confirmAction="confirmActionUrl"
          ref="uploadModal"
          :action="actionUrl"
          @onSuccess="success"
          @error="error"
        />
        <SetPickerModal ref="setPicker" @ok="search(false)" />
      </a-spin>
    </span>
  </a-card>
</template>

<script>
// SetRdDept,
import { UploadModal } from '@/components'
import MaterialModal from './modules/MaterialModal'
import yearMiXin from '@/utils/yearMixin'
import moment from 'moment'
import ystable from '@/components/Table/ystable'
import SetPickerModal from './modules/SetPickerModal.vue'

export default {
  mixins: [yearMiXin],
  name: 'MaterialManage',
  components: {
    UploadModal,
    MaterialModal,
    ystable,
    SetPickerModal
  },
  props: {
    type: {
      type: Number,
      required: true
    },
    typeName: {
      type: String,
      required: true
    },
    control: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      confirmActionUrl: '',
      actionUrl: '',
      tableToolbar: {
        refresh: true,
        zoom: true,
        custom: true
      },
      labelCol: {
        md: { span: 8 }
      },
      wrapperCol: {
        md: { span: 16 }
      },
      rdFieldTitleObject: {
        rdTitle: { title: 'RD', required: true, defaultTitle: 'RD', importField: true },
        rdTypeName: { title: '研发类型', required: true, defaultTitle: '研发类型', importField: true },
        used: { title: '研发数量', required: true, defaultTitle: '研发数量', importField: true },
        rdAmount: { title: '研发金额', defaultTitle: '研发金额', importField: true }
      },
      tableField: {
        tableId: 'materialTable',
        fieldTitleObject: {
          mname: { title: '物料名称', required: true, defaultTitle: '物料名称', importField: true },
          mcode: { title: '物料编码', defaultTitle: '物料编码', importField: true },
          acqDate: { title: '领用日期', required: true, defaultTitle: '领用日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          unitPrice: { title: '单价', required: true, defaultTitle: '单价', importField: true, sampleValue: '请输入纯数字，例如：100' },
          quantity: { title: '数量', required: true, defaultTitle: '数量', importField: true, sampleValue: '请输入纯数字，例如：100' },
          totalAmount: { title: '金额', required: true, defaultTitle: '金额', importField: true, sampleValue: '请输入纯数字，例如：100' },
          unit: { title: '单位', required: true, defaultTitle: '单位', importField: true },
          billNo: { title: '出库单号', defaultTitle: '出库单号', importField: true },
          purpose: { title: '用途', defaultTitle: '用途', importField: true },
          accNumber: { title: '凭证号', defaultTitle: '凭证号', importField: true },
          specification: { title: '规格型号', defaultTitle: '规格型号', importField: true },
          deptName: { title: '部门', defaultTitle: '部门', importField: true },
          warehouse: { title: '仓库', defaultTitle: '仓库', importField: true },
          fullAccountName: { title: '科目', defaultTitle: '科目', importField: true },
          picker: { title: '领料人', defaultTitle: '领料人', importField: true },
          biller: { title: '制单人', defaultTitle: '制单人', importField: true },
          auditor: { title: '审核人', defaultTitle: '审核人', importField: true },
          booker: { title: '记帐人', defaultTitle: '记帐人', importField: true },
          remark: { title: '备注', defaultTitle: '备注', importField: true }
        }
      },
      sampleData: [
        {
          mname: '316不锈钢管',
          mcode: '唯一编码，不能重复，例如：ZC-DZ-00441',
          acqDate: '格式："年-月-日"，例如：2019-10-11',
          totalAmount: '数字格式，例如：300000',
          unitPrice: '数字格式，例如：300000',
          quantity: '数字格式，例如：300000',
          unit: '个',
          billNo: 'A-11',
          purpose: '用途',
          accNumber: 'C-1',
          specification: '304*P1.0*DN125',
          deptName: 'xxx部门',
          warehouse: '字符类型，例如：东莞仓库',
          picker: '张三',
          biller: '张三',
          auditor: '张三',
          booker: '张三',
          remark: '备注',
          rdTitle: '2020RD01',
          rdTypeName: '请从中选择填写：研发材料、中间试制、修理材料，如：研发材料',
          used: '数字格式，例如：300000',
          rdAmount: '数字格式，例如：300000'
        }
      ],
      spinning: false,
      templateName: this.typeName + '模板',
      uploadTitle: '导入' + this.typeName,
      materialList: [],
      selectedRowKeys: [],
      paramData: { type: this.type, year: this.currentYear },
      // 查询参数
      queryParam: {},
      getData: parameter => {
        this.initParam()
        return this.$http.get('/material/queryMaterial', { params: Object.assign(parameter, this.queryParam) })
          .then(res => {
            return res.data
          })
      },
      options: {},
      title: '',
      form: this.$form.createForm(this, { scroll: {} }),
      values: {}
    }
  },
  created () {
    this.paramData.year = this.currentYear
  },
  methods: {
    getParams () {
      this.initParam()
      return this.queryParam
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.initParam()
      this.$refs.table.refresh(refresh)
      this.paramData.year = this.currentYear
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
      }).finally(res => {
        this.$refs.table.refresh(false)
      })
    },
    delSelect () {
      if (this.selectedRowKeys.length < 1) {
        this.$message.info('请选择要删除的物料')
        return
      }
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的物料吗?',
        content: <b>已被数据归集使用的物料不会删除。</b>,
        onOk () {
          return self.$http.post('/material/delSelect', { ids: self.selectedRowKeys })
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.selectedRowKeys = []
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
              }
              self.spinning = false
            }).finally(res => {
              self.spinning = false
              self.$refs.table.refresh(false)
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    moment,
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    openUploadModal (isRD) {
      const tableField = { tableId: 'materialTable' }
      if (isRD) {
        this.actionUrl = '/doc/material/rdImportMaterial'
        this.confirmActionUrl = '/doc/material/rdConfirmImport'
        tableField.fieldTitleObject = Object.assign(this.$deepClone(this.tableField.fieldTitleObject), this.rdFieldTitleObject)
        this.uploadTitle = '导入研发归集数据'
        this.templateName = this.typeName + '研发归集模板'
      } else {
        this.actionUrl = '/doc/material/importMaterial'
        this.confirmActionUrl = '/doc/material/confirmImport'
        tableField.fieldTitleObject = { ...this.tableField.fieldTitleObject }
        this.uploadTitle = '导入' + this.typeName
        this.templateName = this.typeName + '模板'
      }
      this.$refs.uploadModal.show(tableField)
    },
    initParam () {
      this.queryParam.type = this.type
      if (this.queryParam.monthValue === Number.undefined || this.queryParam.monthValue === '-1') {
        var startMonth = moment([this.currentYear, 0, 1, 0, 0, 0, 0])
        var endMonth = moment([this.currentYear, 11, 31, 0, 0, 0, 0])
        this.queryParam.startMonth = startMonth
        this.queryParam.endMonth = endMonth
        this.queryParam.selectDate = null
      } else {
        this.queryParam.selectDate = moment([this.currentYear, this.queryParam.monthValue, 1, 0, 0, 0, 0])
        this.queryParam.startMonth = null
        this.queryParam.endMonth = null
      }
    },
    exportQuipment () {
      this.initParam()
      this.$http.get('/material/exportMaterial', { params: this.queryParam })
        .then(res => {
          if (res.success) {
            var keys = Object.keys(this.tableField.fieldTitleObject).map(a => this.tableField.fieldTitleObject[a].title)
            var sheetData = res.data
            var sheetFilter = Object.keys(this.tableField.fieldTitleObject)
            this.$exportJsonData(this.typeName + '列表数据', keys, sheetFilter, sheetData)
          } else {
            this.$message.error(`导出失败${res.errorMessage ? ':' + res.errorMessage : ''}`)
          }
        })
    },
    success (fileName, resData) {
      this.$message.success(fileName + '导入成功')
      this.$refs.table.refresh(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    handleDel (record) {
      this.$http.post('/material/del', { id: record.id }).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
          this.$refs.table.refresh(false)
        } else {
          this.$message.error(`${res.errorMessage ? res.errorMessage : '删除失败'}`)
        }
      })
    },
    handleEdit (record) {
      this.$refs.modal.edit(record, this.type, this.currentYear)
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
        this.$refs.table.refresh(true)
      } else {
        this.$message.success('更新成功')
        this.$refs.table.refresh(false)
      }
    },
    // selRdDeptId (rdDeptId) {
    //   this.values.rdDeptId = rdDeptId
    //   this.values.materialList = this.materialList
    //   this.$http.post('/material/editDept', this.values)
    //     .then(res => {
    //       if (res.success && res.data) {
    //         this.$message.success('设置成功')
    //         this.$refs.table.refresh(false)
    //       } else {
    //         this.$message.error(res.errorMessage ? res.errorMessage : '设置失败')
    //       }
    //     })
    // },
    selectChange ({ records }) {
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
