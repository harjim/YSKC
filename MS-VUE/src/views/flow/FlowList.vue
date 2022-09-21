<!--
 * @Author: your name
 * @Date: 2022-03-16 11:02:51
 * @LastEditors: zdf
 * @LastEditTime: 2022-08-23 10:55:24
 * @Description: 流程列表
 * @FilePath: \MS-VUE\src\views\flow\FlowList.vue
-->
<template>
  <a-card v-if="control.search">
    <TableSearchbar :colNum="4" @search="search(true)">
      <a-form-item label="标题">
        <a-input v-model="queryParams.content" placeholder="请输入标题" allowClear/>
      </a-form-item>
      <a-form-item label="模块">
        <a-input v-model="queryParams.flowName" placeholder="请输入模块" allowClear/>
      </a-form-item>
      <a-form-item label="客户名称">
        <a-input v-model="queryParams.companyName" placeholder="请输入客户名称" allowClear/>
      </a-form-item>
      <a-form-item label="发起人">
        <search-select
          url="/user/userForSelect"
          searchField="realName"
          sTitle="realName"
          :multiple="false"
          placeholder="请输入发起人"
          v-model="queryParams.lastSubmiterId"
        />
      </a-form-item>
      <a-form-item label="当前处理人">
        <a-input v-model="queryParams.auditUsers" placeholder="请输入当前处理人" allowClear/>
      </a-form-item>
      <a-form-item label="状态">
        <a-select v-model="queryParams.status" placeholder="请选择状态" allowClear>
          <a-select-option v-for="(value, key) in statusMap" :key="key">{{ value }}</a-select-option>
        </a-select>
      </a-form-item>
    </TableSearchbar>
    <ystable
      ref="table"
      queryUrl="/flowList/getList"
      :columns="columns"
      :params="getParams()"
      headerAlign="center"
      highlight-hover-row
      show-overflow
      resizable
      :checkbox-config="{ checkMethod: checkBoxMethod,showHeader: showHead}"
      @completed="completed"
      @checkbox-all="selectEvent"
      @checkbox-change="selectEvent"
      :toolbar="{ refresh:true, zoom: true, custom: true, slots: { buttons: 'toolbar_buttons' } }"
    >
      <template v-slot:toolbar_buttons>
        <a-button
          v-if="control.deliverMaster"
          type="primary"
          :disabled="!selectedRowKeys.length"
          @click="clickHandler('transfer','selectedRowKeys','selectedActivateRows')">转交
        </a-button>
        <a-button
          style="margin-left:10px;"
          v-if="control.activates"
          type="primary"
          :disabled="!selectedActivateRows.length"
          @click="clickHandler('activate','selectedActivateRows','selectedRowKeys')">激活
        </a-button>
        <a-button
          style="margin-left:10px;"
          v-if="control.cancel"
          type="primary"
          :disabled="!selectedRowKeys.length && !selectedActivateRows.length"
          @click="$refs.cancel.open(selectedActivateRows, selectedRowKeys)">取消
        </a-button>
      </template>
      <template #status="{row}">
        <span>{{ statusMap[row.status] }}</span>
      </template>
    </ystable>
    <transfer ref="transfer" @ok="search(true)"/>
    <activate-modal ref="activate" @ok="search(true)"/>
    <cancel-modal ref="cancel" @ok="search(true)"/>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import TableSearchbar from '@/components/TableSearchbar'
import { statusMap } from '@/utils/processDoc/auditStatus'
import { SearchSelect } from '@/components/Selects'
import transfer from './modules/transfer.vue'
import ActivateModal from './modules/ActivateModal'
import CancelModal from '@/views/flow/modules/CancelModal'

export default {
  components: {
    CancelModal,
    ystable,
    TableSearchbar,
    SearchSelect,
    transfer,
    ActivateModal
  },
  data () {
    return {
      statusMap,
      columns: [
        { type: 'checkbox', field: 'content', width: 40, fixed: 'left' },
        { title: '标题', field: 'content', width: 250, fixed: 'left', remoteSort: true },
        { title: '模块', field: 'flowName', width: 150, align: 'center', remoteSort: true },
        { title: '客户名称', field: 'companyName', width: 250, remoteSort: true },
        { title: '发起人', field: 'lastSubmiter', width: 200, align: 'center', remoteSort: true },
        { title: '当前处理人', field: 'auditUsers', width: 200, align: 'center', remoteSort: true },
        { title: '状态', field: 'status', width: 150, align: 'center', slots: { default: 'status' }, remoteSort: true },
        { title: '当前节点数', field: 'nodeCnt', width: 100, align: 'right' },
        { title: '更新时间', field: 'lastUpdateTime', width: 200, align: 'center', remoteSort: true }
      ],
      showHead: false,
      queryParams: {},
      selectedRowKeys: [],
      selectedActivateRows: [],
      control: {
        search: this.$auth('flow:flowList:search'),
        deliverMaster: this.$auth('flow:flowList:deliverMaster'),
        activates: this.$auth('flow:flowList:activates'),
        cancel: this.$auth('flow:flowList:cancel')
      }
    }
  },
  methods: {
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    getParams () {
      const parmas = { ...this.queryParams }
      parmas.lastSubmiterId = parmas.lastSubmiterId && parmas.lastSubmiterId.id
      return parmas
    },
    checkBoxMethod ({ row }) {
      // 进行中的或已完成且不是专利审核的，可选
      return row.status === 0 || (row.status === 1 && row.moduleId !== 7)
    },
    completed ({ data: { data } }) {
      this.selectedActivateRows = []
      this.selectedRowKeys = []
      this.selectUser = []
      if (data && data.length) {
        // 进行中的或已完成且不是专利审核的，可选
        this.showHead = data.some(item => item.status === 0 || (item.status === 1 && item.moduleId !== 7))
      } else {
        this.showHead = false
      }
    },
    clickHandler (ref, key, filterKey) {
      if (this[filterKey] && this[filterKey].length) {
        this.$confirm({
          title: key === 'selectedRowKeys' ? '所选流程存在已结束流程,请重新选择再转交！' : '所选流程存在未结束流程,请重新选择再激活！'
        })
      } else {
        this.$refs[ref].show(this[key])
      }
    },
    selectEvent ({ records }) {
      const selectedActivateRows = []
      const selectedRowKeys = []
      records.forEach(item => {
        if (item.status === 0) {
          selectedRowKeys.push(item.id)
          return
        }
        if (item.status === 1) {
          selectedActivateRows.push(item.id)
        }
      })
      this.selectedActivateRows = selectedActivateRows
      this.selectedRowKeys = selectedRowKeys
    }
  }
}
</script>
