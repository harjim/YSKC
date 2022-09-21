<template>
  <div>
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="资产代码">
          <a-input style="width:165px" v-model="queryParam.ecode" placeholder="请输入资产代码" />
        </a-form-item>
        <a-form-item label="设备名称">
          <a-input style="width:165px" v-model="queryParam.ename" placeholder="请输入设备名称" />
        </a-form-item>
        <a-form-item label="研发部门">
          <select-down
            ref="deptSelet"
            treeType="rdDept"
            style="width:165px"
            @fullPath="deptSelected"
            :year="currentYear"
            placeholder="请选择研发部门"
          />
        </a-form-item>
        <a-form-item label="项目作用">
          <a-input style="width:165px" v-model="queryParam.effect" placeholder="请输入项目作用" />
        </a-form-item>
        <a-form-item>
          <a-button
            type="primary"
            style="margin-right: 10px;"
            @click="search(true)"
            v-if="$auth('project:report:equipment:search')"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          queryUrl="/initEquipment/getList"
          :params="queryParam"
          highlight-hover-row
          show-overflow="title"
          resizable
          auto-resize
          @checkbox-all="selectCheckBoxChange"
          @checkbox-change="selectCheckBoxChange"
          :toolbar="{ refresh: true, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
        >
          <template v-slot:toolbar_buttons>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              v-if="$auth('project:report:equipment:add')"
              @click="$refs.equipmentListModal.showModal(projectData.id,projectData.pname,projectData.rdTitle,currentYear)"
            >添加</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              v-if="$auth('project:report:equipment:import')"
              @click="openUploadModal"
            >导入</a-button>
            <a-button
              type="primary"
              style="margin-right: 10px;"
              :disabled="selectedRowKeys.length <= 0"
              v-if="$auth('project:report:equipment:del')"
              @click="delInitEquipments"
            >删除</a-button>
            <template v-if="$auth('project:report:equipment:edit')">
              <a-button
                type="primary"
                style="margin-right: 10px;"
                :disabled="selectedRowKeys.length <= 0"
                @click="$refs.setRoleModal.add(selectedRowKeys)"
              >设置项目作用</a-button>
              <a-button
                type="primary"
                style="margin-right: 10px;"
                :disabled="selectedRowKeys.length <= 0"
                @click="$refs.setEntryDateModal.add(selectedRowKeys,projectData.beginDate,projectData.endDate,projectData.id,currentYear)"
              >设置加入日期</a-button>
              <a-button
                v-if="projectData.beginYear !== projectData.endYear"
                type="primary"
                style="margin-right: 10px"
                @click="$refs.yearImport.open(currentYear,projectData.id)"
              >引入</a-button>
            </template>
          </template>
          <vxe-table-column type="checkbox" width="40" align="center" fixed="left" />
          <vxe-table-column field="ecode" title="资产代码" width="100" fixed="left" remoteSort />
          <vxe-table-column field="ename" title="设备名称" width="100" fixed="left" remoteSort />
          <vxe-table-column field="emodal" title="设备型号" width="100" remoteSort />
          <vxe-table-column field="etype" title="设备类型" width="100" align="center" remoteSort>
            <template v-slot="{ row }">{{ row.etype !== null ? $getEnums('equipmentEnum').find(elem => elem.value === row.etype).label: '' }}</template>
          </vxe-table-column>
          <vxe-table-column field="rdDeptName" title="研发部门" width="100">
            <template v-slot="{ row }">{{ keyMap[row.rdDeptId] }}</template>
          </vxe-table-column>
          <vxe-table-column field="entryDate" title="加入日期" width="100" align="center" remoteSort></vxe-table-column>
          <vxe-table-column field="unitPrice" title="原值" width="100" align="right" remoteSort></vxe-table-column>
          <vxe-table-column field="effect" title="项目作用" width="100" remoteSort></vxe-table-column>
          <vxe-table-column field="deptName" title="部门" width="200" remoteSort>
            <template #default="{ row }">
              <span v-if="row.deptId">{{ row.fullname }}</span>
              <span v-else>
                {{ row.deptName ? `${row.deptName}` : '' }}{{ row.workshop && row.deptName ? '/' : '' }}{{
                  row.workshop ? `${row.workshop}` : '' }}{{ row.productLine && (row.deptName || row.workshop) ? '/' : '' }}{{
                  row.productLine ? `${row.productLine}` : '' }}{{ row.processSection && (row.deptName || row.workshop || row.productLine) ? '/' : '' }}{{
                  row.processSection ? `${row.processSection}` : '' }}
              </span>
            </template>
          </vxe-table-column>
          <vxe-table-column title="操作" width="100" fixed="right" align="center" >
            <template v-slot="{ row }">
              <a-popconfirm
                title="是否确定删除?"
                v-if="$auth('project:report:equipment:del')"
                @confirm="del(row)"
              >
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>

          <!-- <vxe-table-column field="workshop" title="车间" width="100" remoteSort></vxe-table-column>
          <vxe-table-column field="productLine" title="产线" width="100" remoteSort></vxe-table-column>
          <vxe-table-column field="processSection" title="工艺段" width="100" remoteSort></vxe-table-column> -->
        </ystable>
      </div>
      <equipment-list-modal ref="equipmentListModal" @ok="handleOk(true)" @error="handleError"></equipment-list-modal>
      <set-used-role-modal
        ref="setRoleModal"
        @ok="handleOk(false)"
        url="/initEquipment/updateEffect"
        :year="currentYear"
        initName="项目作用"
      />
      <set-entry-date-modal
        ref="setEntryDateModal"
        @ok="handleOk(false)"
        :checkRdUsed="checkRdUsed"
        url="/initEquipment/setEntryDate"
        :year="currentYear"
      />
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        paramKey="tableField"
        :paramData="{year: currentYear,projectId: projectData.id}"
        title="导入设备清单"
        ref="uploadModal"
        action="/doc/initEquipment/importInitEquipment"
        templateName="设备清单模板"
        @onSuccess="success"
        @error="error"
      />
      <year-import-modal
        ref="yearImport"
        initName="资产清单"
        url="initEquipment/importEquipments"
        queryUrl="initEquipment/getEquipmentsYear"
        @ok="search"
      />
    </a-spin>
  </div>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { UploadModal, Ellipsis, SelectDown } from '@/components'
import EquipmentListModal from '../../init/EquipmentListModal'
import SetUsedRoleModal from '../../init/SetUsedRoleModal'
import SetEntryDateModal from '../../init/SetEntryDateModal'
import yearMixin from '@/utils/yearMixin'
import YearImportModal from '../../init/YearImportModal'
import { mapState } from 'vuex'
export default {
  mixins: [yearMixin],
  name: 'EquipmentTab',
  components: {
    ystable,
    EquipmentListModal,
    Ellipsis,
    SetUsedRoleModal,
    UploadModal,
    SelectDown,
    SetEntryDateModal,
    YearImportModal
  },
  props: {
    projectData: {
      type: Object,
      required: true
    }
  },
  mounted () {
    this.queryParam.projectId = this.projectData.id
    this.queryParam.year = this.currentYear
    this.$getTree('rdDept', false, this.currentYear).then(res => {
      this.keyMap = res.keyMap
    })
  },
  data () {
    return {
      keyMap: {},
      confirmLoading: false,
      visible: true,
      queryParam: {},
      selectedRowKeys: [],
      spinning: false,
      tableField: {
        tableId: 'initEquipment',
        fieldTitleObject: {
          ecode: { title: '资产代码', required: true, defaultTitle: '资产代码', importField: true },
          ename: { title: '设备名称', required: true, defaultTitle: '设备名称', importField: true },
          emodal: { title: '设备型号', defaultTitle: '设备型号', importField: true },
          typeName: { title: '设备类型', defaultTitle: '设备类型', importField: true },
          entryDate: { title: '加入日期', defaultTitle: '加入日期', importField: true },
          effect: { title: '项目作用', defaultTitle: '项目作用', importField: true }
        }
      },
      sampleData: [{
        ecode: 'ZC-JQ-00081',
        ename: '上煤皮带机',
        entryDate: '格式："年-月-日"，例如：2019-10-11',
        emodal: '	J001',
        typeName: '请从中选择填写：仪器/设备/软件摊销/房屋建筑，例如：设备',
        effect: '生产'
      }]
    }
  },
  watch: {
    projectData (item) {
      this.$nextTick(() => {
        this.$refs.table.refresh(true)
      })
    }
  },
  computed: {
    ...mapState({
    })
  },
  methods: {
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
        this.$message.success(`${fileName}导入成功`)
      }
      this.$refs.table.refresh(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    openUploadModal () {
      this.$refs.uploadModal.show(this.tableField)
    },
    deptSelected (rdDeptPath) {
      this.queryParam.rdDeptPath = rdDeptPath
    },
    handleSubmit () {
      this.visible = false
    },
    handleOk (ok) {
      this.selectedRowKeys = []
      this.$refs.table.refresh(ok)
    },
    handleError (errorMassege) {
      this.$message.error(errorMassege)
    },
    del (record) {
      this.spinning = true
      this.checkRdUsed({ ids: [record.id], projectId: this.projectData.id, year: this.currentYear }).then(res => {
        if (res) {
          this.$http.post('/initEquipment/del', { id: record.id, projectId: this.projectData.id, year: this.currentYear })
            .then(res => {
              if (res.success && res.data) {
                this.spinning = false
                this.$message.success('删除成功')
                this.selectedRowKeys = []
                this.$refs.table.refresh(false)
              } else {
                this.spinning = false
                this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
              }
            })
        } else {
          this.spinning = false
        }
      })
    },
    onSelectChange (rows) {
      this.selectedRowKeys = rows
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.$refs.table.refresh(refresh)
    },
    delInitEquipments () {
      this.spinning = true
      const values = { ids: this.selectedRowKeys, projectId: this.projectData.id, year: this.currentYear }
      this.checkRdUsed(values).then(res => {
        if (res) {
          const self = this
          this.$confirm({
            title: '您确定要删除选中的设备吗?',
            onOk () {
              return self.$http.post('/initEquipment/delInitEquipments', values)
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
        } else {
          this.spinning = false
        }
      })
    },
    checkRdUsed (values) {
      return new Promise((resolve, reject) => {
        this.$http.get('/initEquipment/checkRdUsed', { params: values })
          .then(res => {
            if (res.success) {
              if (res.data) {
                this.$confirm({
                  title: '您确定要继续当前操作吗?',
                  content: () => { return res.data },
                  onOk () {
                    resolve(true)
                  },
                  onCancel () {
                    resolve(false)
                  }
                })
              } else {
                resolve(true)
              }
            } else {
              reject(res)
            }
          })
      })
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
    }
  }
}
</script>

<style>
</style>
