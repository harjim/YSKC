<!--
 * @Author: ldx
 * @Date: 2021-03-29 16:19:38
 * @LastEditTime: 2021-04-02 09:59:33
 * @LastEditors: ldx
 * @Description: 铭牌列表
 * @FilePath: \RS-VUE\src\views\tech\Beian\modules\Nameplate.vue
-->
<template>
  <div>
    <vxe-grid
      ref="table"
      :data="tableDatas"
      border
      resizable
      auto-resize
      highlight-hover-row
      show-overflow="title"
      show-header-overflow
      highlight-current-row
      size="small"
      :toolbar="{ custom: true }"
    >
      <template #buttons>
        <a-button size="small" type="primary" @click="onAdd" v-if="$auth('tech:beian:investments:addNameplate')">添加</a-button>
      </template>
      <vxe-table-column field="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
      <vxe-table-column title="设备名称" field="ename" width="120" align="left" header-align="center">
        <template #default="{row}">
          <a @click="onEdit(row)" title="点击编辑铭牌">{{ row.ename }}</a>
        </template>
      </vxe-table-column>
      <vxe-table-column title="规格型号" field="emodal" width="120" align="left" header-align="center">
        <template #default="{row}">
          {{ row.emodal }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="设备制造商" field="manufacturer" min-width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="设备出厂编号" field="factoryNo" width="120" align="left" header-align="center"></vxe-table-column>
      <vxe-table-column title="设备出厂日期" field="factoryDate" width="120" align="center" header-align="center">
        <template #default="{row}">
          {{ moment(row.factoryDate).format("YYYY-MM-DD") }}
        </template>
      </vxe-table-column>
      <vxe-table-column title="操作" width="100" align="center" header-align="center" fixed="right">
        <template #default="{row,rowIndex}">
          <a-popconfirm title="您确定要删除吗？" @confirm="onConfirm(row,rowIndex)">
            <a>删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </vxe-grid>
    <add-nameplate-modal ref="addNameplateModal" @updateTableDatas="updateTableDatas" @changeSaveBtnStatus="changeSaveBtnStatus"></add-nameplate-modal>
  </div>
</template>

<script>
import moment from 'moment'
import AddNameplateModal from './nameplateModules/AddNameplateModal.vue'
import { cloneDeep } from 'lodash'

export default {
  name: 'Nameplate',
  components: {
    AddNameplateModal
  },
  props: {
    investRecord: {
      type: Object,
      default: () => { return {} }
    }
  },
  data () {
    return {
      tableDatas: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      }
    }
  },
  methods: {
    moment,
    onAdd () {
      this.$refs.addNameplateModal.show('添加铭牌', this.tableDatas, this.investRecord)
    },
    onEdit (row) {
      this.$refs.addNameplateModal.edit(`编辑铭牌【${row.ename}】`, cloneDeep(row), cloneDeep(this.investRecord))
    },
    onConfirm (row, index) {
      this.tableDatas.splice(index, 1)
      this.$emit('changeSaveBtnStatus')
    },
    updateTableDatas (tableDatas) {
      this.tableDatas = tableDatas
      this.tableDatas.forEach((item) => {
        delete item.id
      })
      this.$emit('changeSaveBtnStatus')
    },
    resetTableData () {
      this.tableDatas = []
    },
    onloadTableData (tableDatas) {
      this.tableDatas = tableDatas
    },
    changeSaveBtnStatus () {
      this.$emit('changeSaveBtnStatus')
    }
  }
}
</script>

<style>

</style>
