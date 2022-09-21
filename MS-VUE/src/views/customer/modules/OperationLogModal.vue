<!--
 * @Author: zdf
 * @Date: 2021-10-25 16:16:17
 * @LastEditTime: 2022-01-20 10:10:37
 * @LastEditors: zdf
 * @Description: 操作日志弹窗
 * @FilePath: \MS-VUE\src\views\customer\modules\OperationLogModal.vue
-->
<template>
  <a-modal
    :width="1000"
    :footer="null"
    :visible="visible"
    :title="title"
    @cancel="visible = false"
    :maskClosable="false"
  >
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input placeholder="请输入客户名称" style="width:146px;" v-model="queryParams.companyName" />
      </a-form-item>
      <a-form-item label="功能">
        <a-input placeholder="请输入功能" style="width:146px;" v-model="queryParams.description" />
      </a-form-item>
      <a-form-item label="操作时间">
        <a-date-picker
          style="width:146px;"
          v-model="queryParams.beginDate"
          placeholder="开始时间"
          :show-time="{ format: 'HH:mm' }"
          format="YYYY-MM-DD HH:mm"
          :disabledDate="disabledBeginDate"
          @openChange="handleStartOpenChange"
        />-
        <a-date-picker
          style="width:146px;"
          :show-time="{ format: 'HH:mm' }"
          v-model="queryParams.endDate"
          placeholder="开始时间"
          format="YYYY-MM-DD HH:mm"
          :disabledDate="disabledEndDate"
          :open="endOpen"
          @openChange="handleEndOpenChange"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="search">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      :loading="tableLoad"
      ref="table"
      :params="queryParams"
      @completed="completed"
      highlight-current-row
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      queryUrl="/sysLog/getUserRsLog"
      :toolbar="{custom: true,zoom:true,refresh:true}">
      <template v-slot:buttons><span>
        <a-button style="margin-right:8px;" @click="clickDay(7)">近7天</a-button>
        <a-button style="margin-right:8px;" @click="clickDay(15)">近15天</a-button>
        <a-button style="margin-right:8px;" @click="clickDay(30)">近30天</a-button>
      </span></template>
      <vxe-table-column type="seq" width="50" title="序号"/>
      <vxe-table-column
        title="客户名称"
        field="companyName"
        width="260"
        showHeaderOverflow
        showOverflow
        remoteSort/>
      <vxe-table-column
        title="功能"
        field="description"
        width="300"
        showHeaderOverflow
        showOverflow
        remoteSort/>
      <vxe-table-column
        title="操作时间"
        field="logTime"
        width="160"
        align="center"
        showHeaderOverflow
        showOverflow
        remoteSort/>
    </ystable>
    <a-row style="text-align:right;padding-top:24px;">
      <a-button @click="closeModal">关闭</a-button>
    </a-row>
  </a-modal>
</template>

<script>
import moment from 'moment'
import ystable from '@/components/Table/ystable'
import { cloneDeep } from 'lodash'
export default {
  components: {
    ystable
  },
  data () {
    return {
      queryParams: { beginDate: undefined, endDate: undefined },
      title: '',
      endOpen: false,
      visible: false,
      tableLoad: false
    }
  },
  methods: {
    clickDay (day) {
      if (day) {
        this.queryParams.beginDate = this.moment().subtract(day, 'days')
        this.queryParams.endDate = this.moment()
      }
      this.search()
    },
    moment,
    show (userId, realName) {
      this.tableLoad = true
      this.title = `查看[${realName}]操作日志`
      this.visible = true
      const sameId = !this.queryParams.userId || this.queryParams.userId === userId
      if (!sameId || !this.queryParams.userId) {
        this.queryParams = { userId, beginDate: undefined, endDate: undefined }
      }
      this.$nextTick(() => {
        if (!sameId) {
          this.search()
        }
      })
    },
    search () {
      this.$refs.table.refresh(true)
    },
    completed () {
      this.tableLoad = false
    },
    disabledBeginDate (beginDate) {
      const endValue = this.queryParams.endDate
      if (!beginDate || !endValue) {
        return false
      }
      return beginDate.valueOf() > endValue.valueOf()
    },
    disabledEndDate (endDate) {
      const beginDate = this.queryParams.beginDate
      if (!endDate || !beginDate) {
        return false
      }
      const copyBeginDate = cloneDeep(beginDate)
      return endDate.isBefore(copyBeginDate.startOf('day'), 'minute ') || beginDate.valueOf() >= endDate.valueOf()
    },
    handleStartOpenChange (open) {
      if (!open) {
        this.endOpen = true
      }
    },
    handleEndOpenChange (open) {
      this.endOpen = open
    },
    closeModal () {
      this.visible = false
    }
  }
}
</script>

<style>

</style>
