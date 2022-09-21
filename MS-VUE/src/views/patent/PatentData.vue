<template>
  <a-spin tip="请稍后..." :spinning="spin">
    <a-card :bordered="false">
      <a-form layout="inline">
        <a-form-item label="专利号">
          <a-input v-model="queryParams.patentNo" placeholder="请输入专利号" style="width: 180px" />
        </a-form-item>
        <a-form-item label="专利名称">
          <a-input v-model="queryParams.patentName" placeholder="请输入专利名称" style="width: 180px" />
        </a-form-item>
        <a-form-item label="专利类型">
          <!-- <a-input v-model="queryParams.mainType" placeholder="请输入专利类型" style="width: 180px" /> -->
          <a-select
            style="width:150px"
            :allowClear="true"
            v-model="queryParams.mainType"
            placeholder="请选择专利类型">
            <a-select-option :value="value" v-for="(title,value) in mainTypes" :key="value">{{ title }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="申请人">
          <a-input v-model="queryParams.applyName" placeholder="申请人" style="width: 180px" />
        </a-form-item>
        <a-form-item label="发明人">
          <a-input v-model="queryParams.inventor" placeholder="发明人" style="width: 180px" />
        </a-form-item>
        <a-form-item label="法律状态">
          <a-select
            style="width:150px"
            :allowClear="true"
            v-model="queryParams.caseStatus"
            placeholder="请选择法律状态">
            <a-select-option :value="item" v-for="(item,index) in statusAry" :key="index">{{ item }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="申请日期">
          <a-date-picker
            v-model="queryParams.applyDateTime"
            placeholder="请输入申请日期"
          />
        </a-form-item>
        <a-form-item label="公告日期">
          <a-date-picker
            v-model="queryParams.publicDate"
            placeholder="请输入公告日期"
            format="YYYY-MM-DD"
          />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="search(true)" v-if="$auth('patent:patentData:search')">查询</a-button>
        </a-form-item>
      </a-form>
      <div>
        <ystable
          ref="table"
          queryUrl="/patentData/getList"
          :params="queryParams"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
          :toolbar="{ refresh: true, zoom: true, custom: true}"
        >
          <vxe-table-column
            type="seq"
            title="序号"
            width="60"
            align="center"
            header-align="center"
            fixed="left"></vxe-table-column>
          <vxe-table-column
            title="专利号"
            field="patentNo"
            width="150"
            align
            header-align="center"
            fixed="left"
            remoteSort
          >
          </vxe-table-column>
          <vxe-table-column
            title="专利名称"
            field="patentName"
            :min-width="140"
            align="left"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="	发明人"
            field="inventor"
            width="120"
            align="left"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="	申请人"
            field="applyName"
            width="100"
            align="left"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="	申请日期"
            field="applyDateTime"
            width="80"
            align="center"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="专利类型"
            field="mainType"
            width="120"
            align="center"
            header-align="center"
            remoteSort
          >
            <template v-slot="{row}">
              {{ mainTypes[row.mainType] }}
            </template>
          </vxe-table-column>
          <vxe-table-column
            title="主分类号"
            field="mainTypeNo"
            width="120"
            align="center"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="法律状态"
            field="caseStatus"
            width="100"
            align="left"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="地址"
            field="address"
            width="120"
            align="left"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="公告号"
            field="publicNo"
            width="110"
            align="left"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="公告日期"
            field="publicDate"
            width="100"
            align="center"
            header-align="center"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="更新时间"
            field="lastUpdateTime"
            width="120"
            align="center"
            header-align="center"
            remoteSort
          ></vxe-table-column>
        </ystable>
      </div>
    </a-card>
  </a-spin>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { STable, Ellipsis } from '@/components'
import moment from 'moment'
const statusAry = ['有效', '审中', '无效']
export default {
  name: 'Patent',
  components: {
    ystable,
    STable,
    Ellipsis
  },
  data () {
    return {
      statusAry,
      mainTypes: {
        '1': '发明专利',
        '2': '实用新型',
        '3': '外观设计'
      },
      syncMap: new Set(),
      addToIds: [],
      first: true,
      spin: false,
      syncObj: { syncCount: undefined, total: 0, msg: '', done: true, fail: false },
      selectedRowKeys: [],
      hasHeart: false,
      form: this.$form.createForm(this),
      endOpen: false,
      expiryDateOpen: false,
      pagination: [],
      queryParams: {},
      options: {}
    }
  },
  methods: {
    moment,
    search (refresh) {
      this.$refs.table.refresh(refresh)
    },
    handleStartChange (open) {
      if (!open) {
        this.expiryDateOpen = true
      }
    },
    handleEndChange (open) {
      this.expiryDateOpen = open
    }
  }
}
</script>
<style scoped>
</style>
