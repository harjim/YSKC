<!--
 * @Author: ldx
 * @Date: 2021-03-18 09:03:36
 * @LastEditTime: 2022-04-09 11:28:18
 * @LastEditors: lzh
 * @Description: 备案清单
 * @FilePath: \RS-VUE\src\views\tech\Beian\EquipmentList.vue
-->
<template>
  <a-card class="container" :bodyStyle="{ height: '100%', padding: '12px', overflow: 'auto'}">
    <template v-if="$auth('tech:beian:beianList:search')">
      <a-spin :spinning="spinning" tip="请稍后...">
        <a-form layout="inline">
          <a-form-item label="设备名称" >
            <a-input v-model="queryParams.ename" placeholder="请输入设备名称" />
          </a-form-item>
          <a-form-item label="规格型号" >
            <a-input v-model="queryParams.emodal" placeholder="请输入规格型号" />
          </a-form-item>
          <a-form-item>
            <a-button style="margin-right: 10px;" type="primary" @click="search(true)">查询</a-button>
          </a-form-item>
        </a-form>
        <ystable
          ref="table"
          queryUrl="/techEquipment/getList"
          :params="queryParams"
          border
          resizable
          auto-resize
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          highlight-hover-row
          show-overflow="title"
          show-header-overflow
          highlight-current-row
          :toolbar="{ refresh: true, zoom: true, custom: true}"
        >
          <template v-slot:buttons>
            <a-button type="primary" style="margin-right: 10px;" @click="$refs.equipmentListModal.add(record.id)" v-if="$auth('tech:beian:beianList:add')">添加</a-button>
            <a-button type="primary" style="margin-right: 10px;" :disabled="!selectedRowKeys.length" @click="batchDelete" v-if="$auth('tech:beian:beianList:del')">删除</a-button>
            <a-button type="primary" style="margin-right: 10px;" @click="openUploadModal" v-if="$auth('tech:beian:beianList:import')">导入</a-button>
            <a-button type="primary" style="margin-right: 10px;" @click="onExportEquipment" v-if="$auth('tech:beian:beianList:export')">导出</a-button>
          </template>
          <vxe-table-column type="checkbox" width="50" header-align="center" align="center"/>
          <vxe-table-column
            type="seq"
            title="序号"
            field=""
            width="60"
            header-align="center"
            align="center"/>
          <vxe-table-column title="设备名称" field="ename" min-width="140" header-align="center" align="left">
            <template #default="{row}">
              <a @click="$refs.equipmentListModal.edit(row)" v-if="$auth('tech:beian:beianList:edit')">{{ row.ename }}</a>
              <template v-else>{{ row.ename }}</template>
            </template>
          </vxe-table-column>
          <vxe-table-column title="规格型号" field="emodal" min-width="140" header-align="center" align="left"/>
          <vxe-table-column title="单价(元)" field="unitPrice" width="100" header-align="center" align="right"/>
          <vxe-table-column title="数量" field="quantity" width="90" header-align="center" align="right"/>
          <vxe-table-column title="单位" field="unit" width="60" header-align="center" align="center"/>
          <vxe-table-column title="金额(元)" field="amount" width="120" header-align="center" align="right"/>
          <vxe-table-column title="额定功耗(kW)" field="usagePower" width="120" header-align="center" align="right"/>
          <vxe-table-column title="负荷系数(%)" field="loadFactor" width="120" header-align="center" align="right">
            <template #default="{ row }">{{ (row.loadFactor * 100).toFixed(2) }}%</template>
          </vxe-table-column>
          <vxe-table-column title="稼动率(%)" field="runRate" width="120" header-align="center" align="right">
            <template #default="{ row }">{{ (row.runRate * 100).toFixed(2) }}%</template>
          </vxe-table-column>
          <vxe-table-column title="运转时间(h)" field="workHour" width="120" header-align="center" align="right">
            <template #default="{ row }">{{ row.workHour.toFixed(2) }}h</template>
          </vxe-table-column>
          <vxe-table-column title="用电(kW·h)" field="powerUsed" width="120" header-align="center" align="right" />
          <vxe-table-column
            title="操作"
            width="90"
            header-align="center"
            align="center"
            v-if="$auth('tech:beian:beianList:del')">
            <template v-slot="{row}">
              <a-popconfirm title="是否确定删除?" @confirm="deleteOne(row.id)" v-if="$auth('tech:beian:beianList:del')" >
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column>
          <!-- <vxe-table-column title="操作" width="120" header-align="center" align="center" v-if="$auth('tech:beian:beianList:edit') || $auth('tech:beian:beianList:del')">
            <template v-slot="{row}">
              <a @click="$refs.equipmentListModal.edit(row)" style="padding: 0 5px;" v-if="$auth('tech:beian:beianList:edit')">编辑</a>
              <a-divider type="vertical" v-if="$auth('tech:beian:beianList:edit') && $auth('tech:beian:beianList:del')" />
              <a-popconfirm title="是否确定删除?" @confirm="deleteOne(row.id)" v-if="$auth('tech:beian:beianList:del')" >
                <a>删除</a>
              </a-popconfirm>
            </template>
          </vxe-table-column> -->
        </ystable>
        <upload-modal
          :sampleData="sampleData"
          :showProgress="true"
          :paramData="{ beianId: this.record.id }"
          paramKey="tableField"
          title="导入备案清单"
          ref="uploadModal"
          action="/doc/techEquipment/importEquipment"
          templateName="备案清单模板"
          @onSuccess="success"
        />
        <equipment-list-modal ref="equipmentListModal" @ok="search"/>
      </a-spin>
    </template>
  </a-card>
</template>

<script>
import { UploadModal, ystable } from '@/components'
import { mapGetters } from 'vuex'
import EquipmentListModal from './modules/EquipmentListModal.vue'
export default {
  components: {
    ystable,
    UploadModal,
    EquipmentListModal
  },
  name: 'EquipmentList',
  props: {
    record: {
      type: Object,
      default: () => { return {} }
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  data () {
    return {
      queryParams: { beianId: this.record.id },
      tableField: {
        tableId: 'designTable',
        fieldTitleObject: {
          ename: { title: '设备名称', required: true, defaultTitle: '设备名称', importField: true },
          emodal: { title: '规格型号', required: true, defaultTitle: '规格型号', importField: true },
          // producePlace: { title: '产地', required: true, defaultTitle: '产地', importField: true },
          unitPrice: { title: '单价(元)', required: true, defaultTitle: '单价(元)', importField: true },
          quantity: { title: '数量', required: true, defaultTitle: '数量', importField: true },
          unit: { title: '单位', required: true, defaultTitle: '单位', importField: true },
          amount: { title: '金额(元)', required: true, defaultTitle: '金额(元)', importField: true },
          usagePower: { title: '额定功耗(kW)', required: true, defaultTitle: '额定功耗(kW)', importField: true },
          loadFactor: { title: '负荷系数', required: true, defaultTitle: '负荷系数', importField: true },
          runRate: { title: '稼动率', required: true, defaultTitle: '稼动率', importField: true },
          workHour: { title: '运转时间(h)', required: true, defaultTitle: '运转时间(h)', importField: true }
        }
      },
      sampleData: [{
        ename: 'XXX设备',
        emodal: '型号',
        producePlace: '广东深圳',
        unitPrice: '数字格式，例如：300000',
        quantity: '数字格式，例如：300000',
        unit: '单位：台',
        amount: '数字格式，例如：300000',
        usagePower: '数字格式，例如：300000',
        loadFactor: '0-1之间的数字，例如：0.99',
        runRate: '0-1之间的数字，例如：0.99',
        workHour: '小时，允许小数，例如：8.88'
      }],
      selectedRowKeys: [],
      spinning: false
    }
  },
  methods: {
    success () {
      this.search(true)
    },
    search (refresh) {
      this.spinning = true
      this.selectedRowKeys = []
      this.$refs.table.refresh(refresh)
      this.spinning = false
    },
    deleteOne (id) {
      this.handleDelete([ id ])
    },
    batchDelete () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的设备吗?',
        onOk () {
          self.handleDelete(self.selectedRowKeys)
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    handleDelete (ids) {
      if (!ids || !ids.length) {
        return
      }
      this.spinning = true
      this.$http.post('/techEquipment/deletes', { ids: ids }).then(res => {
        if (res.success && res.data) {
          this.$message.success('删除成功')
          this.search(false)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    openUploadModal () {
      this.$refs.uploadModal.show(this.tableField)
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    },
    onExportEquipment () {
      if (!this.record.id) {
        return
      }
      this.spinning = true
      const fileName = `${this.userInfo.companyName}-${this.record.pname}-备案清单.xls`
      this.$exportData('/techEquipment/exportEquipment', { beianId: this.record.id }, fileName, this.$message).then(res => {
        this.spinning = false
      })
    }
  }
}
</script>
<style lang="less" scoped>
.container {
  height: 100%;
}
</style>
