<!--
 * @Author: ldx
 * @Date: 2020-12-03 09:05:03
 * @LastEditTime: 2021-04-16 09:56:58
 * @LastEditors: ldx
 * @Description: 流程列表
 * @FilePath: \MS-VUE\src\views\flow\FlowNodes.vue
-->
<template>
  <div style="height: 100%;" :bodyStyle="{height: '100%'}">
    <a-card v-show="!isSetting">
      <a-form layout="inline">
        <a-form-item label="模块名称">
          <a-input style="width:160px;" placeholder="请输入模块名称" v-model="queryParams.flowName" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="onQuery">查询</a-button>
        </a-form-item>
      </a-form>
      <ystable
        ref="table"
        queryUrl="/flow/getList"
        :params="getParams()"
        highlight-current-row
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        :toolbar="{ refresh:true, zoom: true, custom: true, slots: { buttons: 'toolbar_buttons' } }"
      >
        <template v-slot:toolbar_buttons>
          <a-button type="primary" @click="$refs.addFlow.show('添加流程')" >添加</a-button >
        </template>
        <vxe-table-column title="序号" type="seq" width="60" header-align="center" align="center"></vxe-table-column>
        <vxe-table-column title="流程名称" field="name" align="left" header-align="center">
          <template v-slot="{row}">
            <a @click="switchSetting(row)">{{ row.name }}</a>
          </template>
        </vxe-table-column>
        <vxe-table-column title="备注" field="remark" align="left" header-align="center">
          <template v-slot="{row}">
            {{ row.remark }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="创建时间" field="createTime" align="center" header-align="center">
          <template v-slot="{row}">
            {{ row.createTime | DayFormat }}
          </template>
        </vxe-table-column>
        <vxe-table-column title="操作" align="center" header-align="center">
          <template v-slot="{ row }">
            <a-popconfirm
              title="是否确定删除?"
              @confirm="delFlow(row)"
            >
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
      </ystable>
      <add-flow ref="addFlow" @refreshTable="onQuery"></add-flow>
    </a-card>
    <a-card v-if="isSetting" style="height: 100%;" :bodyStyle="{height: '100%'}">
      <!-- <setting-flow @switchPage="switchPage" :flowId="flowId" :sign="sign" :flowName="flowName" ></setting-flow> -->
      <setting-flow @switchPage="switchPage" :flowId="flowId" :flowName="flowName" ></setting-flow>
    </a-card>
  </div>

</template>

<script>
import ystable from '@/components/Table/ystable'
import AddFlow from './modules/AddFlow'
import SettingFlow from './SettingFlow'
export default {
  name: 'FlowNodes',
  components: {
    ystable,
    SettingFlow,
    AddFlow
  },
  data () {
    return {
      nowVal: 100,
      isSetting: false,
      flowId: undefined,
      sign: false,
      flowName: undefined,
      tableData: [
        { modeName: '加技扣除' }
      ],
      queryParams: {
        flowName: undefined
      }
    }
  },
  methods: {
    switchSetting (row) {
      if (row) {
        this.flowId = row.id
        this.sign = row.sign
        this.flowName = row.name
      }
      this.isSetting = !this.isSetting
    },
    switchPage () {
      this.switchSetting()
    },
    // 刷新表格数据
    onQuery (refresh = true) {
      this.$refs.table.refresh(refresh)
    },
    getParams () {
      const params = {
        flowName: this.queryParams.flowName
      }
      return params
    },
    // 删除流程
    delFlow (row) {
      this.$http.post('/flow/delFlow', row).then((res) => {
        if (res.data && res.success) {
          this.$message.success('操作成功')
          this.onQuery()
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      }).finally((res) => {
      })
    }
  }
}
</script>

<style lang="less" scoped>
// @import "./css/workflow.css";
</style>
