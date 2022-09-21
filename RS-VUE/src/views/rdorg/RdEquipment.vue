<template>
  <a-card
    :bordered="false"
    style="height: 100%; overflow: auto"
    :bodyStyle="{ height: '100%', overflow: 'auto'}"
  >
    <a-spin :spinning="spinning">
      <a-form
        layout="inline"
        ref="form"
      >
        <a-form-item label="资产代码">
          <a-input
            v-model="queryParams.ecode"
            placeholder="请输入资产代码"
          />
        </a-form-item>
        <a-form-item label="设备名称">
          <a-input
            v-model="queryParams.ename"
            placeholder="请输入设备名称"
          />
        </a-form-item>
        <a-form-item label="研发部门">
          <select-down
            ref="rdDept"
            treeType="rdDept"
            @fullPath="rdDeptSelected"
            placeholder="请选择研发部门"
            style="width: 174px"
          />
        </a-form-item>
        <a-form-item label="折旧月份">
          <a-month-picker
            v-model="queryParams.depreciationDate"
            placeholder="请选择折旧月份"
            style="width: 100%"
          />
        </a-form-item>
        <a-form-item label="设备类别">
          <a-input
            v-model="queryParams.kinds"
            placeholder="请输入类别"
          />
        </a-form-item>
        <a-form-item label="设备型号">
          <a-input
            v-model="queryParams.emodal"
            placeholder="请输入设备型号"
          />
        </a-form-item>
        <a-form-item label="设备类型">
          <a-select
            v-model="queryParams.etype"
            placeholder="请选择设备类型"
            style="width: 174px"
          >
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
          <a-button
            v-if="$auth('rdorg:rdEquipment:search')"
            type="primary"
            @click="search(true)"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div class="table">
        <ystable
          ref="table"
          queryUrl="/rdEquipment/getList"
          :params="queryParams"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          @checkbox-all="selectCheckBoxChange"
          @checkbox-change="selectCheckBoxChange"
          :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
          max-height="100%"
        >
          <template v-slot:toolbar_buttons>
            <a-button
              type="primary"
              v-if="$auth('rdorg:rdEquipment:add') && modify"
              @click="$refs.selectEquipment.showModal(currentYear)"
              class="btnInterval"
            >添加</a-button>
            <a-button
              v-if="$auth('rdorg:rdEquipment:import') && modify"
              type="primary"
              @click="openUploadModal"
              class="btnInterval"
            >导入</a-button>
            <a-button
              v-if="$auth('rdorg:rdEquipment:export')"
              type="primary"
              @click="exportRdEquipment"
              class="btnInterval"
            >导出</a-button>
            <a-button
              type="primary"
              :disabled="selectedRowKeys.length <= 0"
              v-if="$auth('rdorg:rdEquipment:del') && modify"
              @click="delLists"
              class="btnInterval"
            >删除</a-button>
            <a-button
              type="primary"
              v-if="$auth('rdorg:rdEquipment:edit') && modify"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.rdDept.add(currentYear)"
              class="btnInterval"
            >设置研发部门</a-button>
            <a-button
              type="primary"
              v-if="$auth('company:equipment:setType') && modify"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.settingEtype.add(selectedRowKeys,currentYear)"
              class="btnInterval"
            >设置类型</a-button>
            <a-button
              type="primary"
              class="btnInterval"
              v-if="$auth('rdorg:rdEquipment:setProject') && modify"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.setProject.open(equipmentList.map(a=>a.ecode),currentYear)"
            >分配项目</a-button>
          </template>
          <vxe-table-column
            type="checkbox"
            width="40"
            align="center"
            fixed="left"
          ></vxe-table-column>
          <vxe-table-column
            field="ecode"
            title="资产代码"
            min-width="120"
            align="left"
            fixed="left"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            field="ename"
            title="设备名称"
            min-width="150"
            align="left"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            field="emodal"
            title="设备型号"
            width="110"
            align="left"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            field="etype"
            title="设备类型"
            width="100"
            align="center"
            remoteSort
          >
            <template v-slot="{ row }">{{ row.etype ? $getEnums('equipmentEnum').find(elem => row.etype === elem.value).label : '' }}</template>
          </vxe-table-column>
          <vxe-table-column
            field="rdDeptName"
            title="研发部门"
            width="170"
            align="left"
          >
            <template v-slot="{ row }">
              <span v-if="$auth('rdorg:rdEquipment:edit')&& modify">
                <select-down
                  ref="rdDeptSelet"
                  treeType="rdDept"
                  :allowClear="false"
                  :value="row.rdDeptId"
                  @select="v=>setOneRdDept(v,row)"
                  placeholder="请选择研发部门"
                  style="width:140px;"
                />
              </span>
              <span v-else>
                {{ row.fullName }}
              </span>
            </template>
          </vxe-table-column>
          <!-- <vxe-table-column
        field="workshop"
        title="车间"
        width="130"
        align="left"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column
        field="productLine"
        title="产线"
        width="100"
        align="left"
        remoteSort
      ></vxe-table-column>
      <vxe-table-column
        field="processSection"
        title="工艺段"
        width="100"
        align="left"
        remoteSort
      ></vxe-table-column> -->
          <vxe-table-column
            field="deptName"
            title="部门"
            width="130"
            align="left"
            remoteSort
          >
            <template #default="{ row }">
              <span v-if="row.deptId">{{ row.deptFullName }}</span>
              <span v-else>{{ row.deptName ? `${row.deptName}` : '' }}{{ row.workshop && row.deptName ? '/' : '' }}{{
                row.workshop ? `${row.workshop}` : '' }}{{ row.productLine && (row.deptName || row.workshop) ? '/' : '' }}{{
                row.productLine ? `${row.productLine}` : '' }}{{ row.processSection && (row.deptName || row.workshop || row.productLine) ? '/' : '' }}{{
                row.processSection ? `${row.processSection}` : '' }}</span>
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="项目编号"
            field="rds"
            width="140"
            align="left"
          /> <vxe-table-column
            title="缺失月份"
            field="lackMonth"
            width="130"
            align="center"
          />
          <vxe-table-column
            field="unitPrice"
            title="原值"
            width="100"
            align="right"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            field="quantity"
            title="数量"
            width="100"
            align="right"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            field="unit"
            title="单位"
            width="100"
            align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            field="usefullife"
            title="使用年限"
            width="100"
            align="right"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            field="purchaseDate"
            title="开始使用日期"
            width="130"
            align="center"
            remoteSort
          >
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
          </vxe-table-column> <vxe-table-column
            field="invalidated"
            title="停用报废日期"
            width="130"
            align="center"
            remoteSort
          >
            <template v-slot="{ row }">{{ row.invalidated | formatDate }}</template>
          </vxe-table-column>
          <vxe-table-column
            field="depreciationRate"
            title="月折旧率"
            width="100"
            align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            field="usagePower"
            title="功率(kWh)"
            width="110"
            align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            field="kinds"
            title="类别"
            width="100"
            align="left"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="操作"
            width="80"
            align="center"
            fixed="right"
          >
            <template v-slot="{ row }">
              <a-popconfirm
                v-if="$auth('rdorg:rdEquipment:del')&& modify"
                title="是否确定删除?"
                @confirm="handleDel(row)"
              >
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
        </ystable>
      </div></a-spin>
    <setting-Equipment-type-modal
      ref="settingEtype"
      @ok="search(false)"
    />
    <select-equipment-modal
      ref="selectEquipment"
      @ok="search(true)"
    />
    <set-rd-dept
      ref="rdDept"
      @ok="setRdDept"
    />
    <upload-modal
      :sampleData="sampleData"
      :showProgress="true"
      :paramData="paramData"
      paramKey="tableFieEq"
      title="导入研发设备"
      :templateName="templateName"
      ref="uploadModal"
      action="/doc/rdEquipment/importRdEquipment"
      @onSuccess="success"
      @error="error"
    />
    <setting-project-modal
      title="分配项目-研发设备"
      url="/rdEquipment/setProjectEquipment"
      ref="setProject"
      @ok="search"
    />
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { isEditStatus, isExportStatus } from '@/utils/processDoc/auditStatus'
import { SetRdDept, SelectDown, UploadModal } from '@/components'
import moment from 'moment'
import { mapGetters, mapState } from 'vuex'
import SelectEquipmentModal from './modules/SelectEquipmentModal'
import SettingEquipmentTypeModal from './modules/SettingEquipmentTypeModal'
import yearMixin from '@/utils/yearMixin'
import SettingProjectModal from './modules/settingProjectModal'

export default {
  name: 'RdEquipmenrt',
  mixins: [yearMixin],
  components: {
    SetRdDept,
    SelectEquipmentModal,
    SettingEquipmentTypeModal,
    SelectDown,
    UploadModal,
    ystable,
    SettingProjectModal
  },
  data () {
    return {
      spinning: false,
      queryParams: {},
      equipmentList: [],
      selectedRowKeys: [],
      paramData: { year: this.currentYear },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 12 }
      },
      templateName: '研发设备模板',
      tableField: {
        tableId: 'equipmentTable',
        fieldTitleObject: {
          ecode: { title: '资产代码', defaultTitle: '资产代码', importField: true },
          ename: { title: '设备名称', defaultTitle: '设备名称', importField: true },
          rdDeptName: { title: '研发部门', defaultTitle: '研发部门', importField: true },
          deptName: { title: '部门', defaultTitle: '部门', importField: true },
          rds: { title: '项目编号' },
          lackMonth: { title: '缺失月份' },
          workshop: { title: '车间', defaultTitle: '车间', importField: true },
          productLine: { title: '产线', defaultTitle: '产线', importField: true },
          processSection: { title: '工艺段', defaultTitle: '工艺段', importField: true },
          emodal: { title: '设备型号', defaultTitle: '设备型号', importField: true },
          typeName: { title: '设备类型', defaultTitle: '设备类型', importField: true },
          unitPrice: { title: '原值', defaultTitle: '原值', importField: true },
          quantity: { title: '数量', defaultTitle: '数量', importField: true },
          unit: { title: '单位', defaultTitle: '单位', importField: true },
          usefullife: { title: '使用年限', defaultTitle: '使用年限', importField: true },
          purchaseDate: { title: '开始使用日期', defaultTitle: '开始使用日期', importField: true },
          depreciationDate: { title: '计提折旧日期', defaultTitle: '计提折旧日期', importField: true },
          depreciationRate: { title: '月折旧率', defaultTitle: '月折旧率', importField: true },
          usagePower: { title: '功率(kWh)', defaultTitle: '功率(kWh)', importField: true },
          kinds: { title: '类别', defaultTitle: '类别', importField: true },
          remark: { title: '备注', defaultTitle: '备注', importField: true }
        }
      },
      tableFieEq: {
        tableId: 'importRdEquipmentTable',
        fieldTitleObject: {
          ecode: { title: '资产代码', required: true, defaultTitle: '资产代码', importField: true },
          ename: { title: '设备名称', required: true, defaultTitle: '设备名称', importField: true },
          rdDeptName: { title: '研发部门', required: true, defaultTitle: '研发部门', importField: true },
          typeName: { title: '设备类型', required: true, defaultTitle: '设备类型', importField: true }
        }
      },
      sampleData: [{
        ecode: '格式：ztest01',
        ename: '设备名',
        rdDeptName: '/xxxx研发中心/xxxx组/',
        typeName: '请从中选择填写：仪器/设备/软件摊销/房屋建筑，例如：设备'
      }],
      modify: false
    }
  },
  computed: {
    ...mapGetters(['auditStatus', 'userInfo']),
    ...mapState({
    })
  },
  watch: {
    auditStatus: {
      immediate: true,
      handler (val) {
        this.modify = this.isEditStatus(val) || !this.userInfo.userSource
        this.search(true)
      }
    }
  },
  created () {
    this.paramData.year = this.currentYear
    this.queryParams.year = this.currentYear
  },
  mounted () {
    this.modify = this.isEditStatus(this.auditStatus) || !this.userInfo.userSource
  },
  filters: {
    formatDate (value) {
      if (!value) return ''
      return moment(value).format('YYYY-MM-DD')
    }
  },
  methods: {
    isEditStatus,
    isExportStatus,
    moment,
    setRdDept (rdDeptId) {
      const values = { ids: this.selectedRowKeys, rdDeptId: rdDeptId }
      this.saveRdDept(values, true)
    },
    saveRdDept (values, showMsg) {
      values.year = this.currentYear
      this.$http.post('/rdEquipment/setRdDept', values)
        .then(res => {
          if (res.data && res.success) {
            if (showMsg) {
              this.$message.success('设置成功')
              this.search(false)
            }
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '设置失败')
          }
        })
    },
    setOneRdDept (v, row) {
      const values = { ids: [row.id], rdDeptId: v }
      row.rdDeptId = v
      this.saveRdDept(values)
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.equipmentList = []
      this.queryParams.year = this.currentYear
      this.paramData.year = this.currentYear
      if (this.$refs.table && this.$refs.table.refresh) {
        this.$refs.table.refresh(refresh)
      }
    },
    handleDel (record) {
      this.spinning = true
      this.$http.post('/rdEquipment/delete', { id: record.id, ecode: record.ecode, year: this.currentYear })
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
          var params = { ids: self.selectedRowKeys, year: self.currentYear }
          return self.$http.post('/rdEquipment/delRdEquipments', params)
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
    disabledDate (current) {
      return !current || current > moment().endOf('day')
    },
    rdDeptSelected (rdDeptPath) {
      this.queryParams.rdDeptPath = rdDeptPath
    },
    openUploadModal () {
      this.paramData.year = this.currentYear
      this.$refs.uploadModal.show(this.tableFieEq)
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
    exportRdEquipment () {
      this.spinning = true
      this.$exportData('/rdEquipment/deriveRdEquipment', this.queryParams,
        `研发设备列表数据.xls`, this.$message).then(res => {
        this.spinning = false
      })
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
      this.equipmentList = records
    }
  }
}
</script>

<style lang="less" scoped>
#tabel /deep/ .ant-table-small {
  /* border: 1px solid #e8e8e8; */
  /* border-radius: 4px; */
  border: none;
  border-radius: none;
}
.btnInterval {
  margin-right: 10px;
}

/deep/ .ant-spin-container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 220px);

}
/deep/ .ant-form-inline {
  height: auto;
  flex-shrink: 0;
}

/deep/ .table {
  flex: 1;
  overflow-y: hidden;
}
</style>
