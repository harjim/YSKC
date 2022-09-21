<template>
  <a-card :bordered="false">
    <template v-if="$auth('customer:workRecord:search')">
      <a-form-model layout="inline" ref="form" :model="form">
        <a-form-model-item label="客户名称">
          <select-company style="width: 240px;" prop="companyName" @changeCompany="v => form.companyName = v"/>
        </a-form-model-item>
        <a-form-model-item label="单号">
          <a-input v-model="form.serviceNo" placeholder="请输入单号" style="width: 240px;"/>
        </a-form-model-item>
        <a-form-model-item label="创建人">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            placeholder="请输入创建人"
            style="width:240px;"
            v-model="temp.creatorName"
            @change="v => form.creatorName = v.realName"
          />
        </a-form-model-item>
        <a-form-model-item label="流程状态">
          <a-select v-model="form.status" placeholder="请输入流程状态" style="width: 240px;">
            <a-select-option v-for="(v, k) in statusMap" :key="k" :value="k">{{ v }}</a-select-option>
          </a-select>
        </a-form-model-item>
        <a-form-model-item label="当前处理人">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            placeholder="请输入当前处理人"
            style="width:240px;"
            v-model="temp.auditUsers"
            @change="v => form.auditUsers = v.realName"
          />
        </a-form-model-item>
        <a-form-model-item>
          <a-button type="primary" @click="onSearch">查询</a-button>
        </a-form-model-item>
      </a-form-model>

      <ystable
        id="workRecordTable"
        ref="xTable"
        :params="form"
        :seq-config="{ startIndex: 1 }"
        query-url="/serviceRecord/getList"
        :toolbar="{
          refresh: true,
          zoom: true,
          custom: true
        }"
        show-overflow="tooltip"
        @checkbox-all="selectAllEvent"
        @checkbox-change="selectChangeEvent"
      >
        <template v-slot:buttons>
          <template v-if="$auth('customer:workRecord:add')">
            <a-button style="margin-right: 12px;" type="primary" @click="$refs.WorkRecordAdd.open()">添加</a-button>
          </template>
          <template v-if="$auth('customer:workRecord:del')">
            <a-button
              style="margin-right: 12px;"
              type="primary"
              :disabled="selectedRowKeys.length === 0"
              @click="delServices">删除
            </a-button>
          </template>
        </template>
        <vxe-table-column type="checkbox" width="50" align="center" fixed="left"/>
        <vxe-table-column
          title="客户名称"
          field="companyName"
          fixed="left"
          width="260"
          remoteSort
        >
          <template v-slot="{ row }">
            <a v-if="$auth('customer:workRecord:check')" @click="openDetail(row)">{{ row.companyName }}</a>
            <div v-else>{{ row.companyName }}</div>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="关联单号"
          field="serviceNo"
          align="center"
          fixed="left"
          width="200"
          remoteSort
        />
        <vxe-table-column
          title="业务员"
          field="ownerName"
          width="100"
          remoteSort
        />
        <vxe-table-column title="所属部门" width="100" field="deptName" remoteSort/>
        <vxe-table-column field="itemCnt" title="事项" width="80" align="right" remoteSort>
          <template v-slot="{ row }">
            <a-popover
              :autoAdjustOverflow="false"
              placement="bottom"
              destroyTooltipOnHide
              arrowPointAtCenter
              trigger="click"
            >
              <a>{{ row.list.length }}</a>
              <template slot="content">
                <vxe-grid
                  border
                  resizable
                  auto-resize
                  :data="row.list"
                  show-overflow="title"
                  max-height="400"
                  show-footer
                  size="mini"
                  :footerMethod="footerMethod"
                >
                  <vxe-table-column type="seq" title="序号" width="60"/>
                  <vxe-table-column field="itemType" title="事项" width="100">
                    <template v-slot="{ row }">
                      <div>{{ type2value(row.itemType) }}</div>
                    </template>
                  </vxe-table-column>
                  <vxe-table-column field="date" title="起止时间" align="center" width="220"></vxe-table-column>
                  <vxe-table-column field="amount" title="费用" width="100"></vxe-table-column>
                  <vxe-table-column field="remark" title="备注" width="100"></vxe-table-column>
                </vxe-grid>
              </template>
            </a-popover>
          </template>
        </vxe-table-column>
        <vxe-table-column title="总费用" width="100" align="right">
          <template v-slot="{ row }">
            <div>{{ countArrCost(row.list) }}</div>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="创建人"
          field="creatorName"
          width="100"
          remoteSort
        />
        <vxe-table-column
          title="流程状态"
          field="status"
          width="120"
          remoteSort
        >
          <template v-slot="{ row }">
            <a-badge :color="statusColor[row.status === null ? 5 : row.status]" :text="getStatusName(row.status)"/>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="当前处理人"
          field="auditUsers"
          width="120"
          remoteSort
        />
        <vxe-table-column
          title="创建时间"
          field="createTime"
          width="160"
          align="center"
          remoteSort
        />
        <vxe-table-column
          title="最后更新时间"
          field="lastUpdateTime"
          width="160"
          align="center"
          remoteSort
        />
      </ystable>

      <WorkRecordCheck ref="WorkRecordCheck" :dictionary="dictionary" @refresh="onSearch"/>
      <WorkRecordAdd ref="WorkRecordAdd" :dictionary="dictionary" @refresh="onSearch"/>
    </template>
    <template v-else>
      <a-empty :description="false"/>
    </template>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import SelectCompany from '@/components/Selects/SelectCompany'
import { getStatusName, statusColor } from '@/utils/processDoc/auditStatus'
import SearchSelect from '@/components/Selects/SearchSelect'
import WorkRecordAdd from '@/views/customer/modules/WorkRecordAdd'
import WorkRecordCheck from '@/views/customer/modules/WorkRecordCheck'

export default {
  name: 'WorkRecord',
  components: { WorkRecordCheck, WorkRecordAdd, SearchSelect, SelectCompany, ystable },
  props: {},
  data () {
    const statusMap = {
      '0': '进行中',
      '1': '通过',
      '2': '驳回',
      '4': '提交',
      '5': '未提交'
    }
    return {
      statusMap,
      statusColor,
      temp: {},
      dictionary: [],
      form: {},
      selectedRowKeys: [],
      selectUser: []
    }
  },
  computed: {},
  watch: {},
  methods: {
    getStatusName,
    onSearch (flag = true) {
      this.$refs.xTable.refresh(flag)
    },
    countArrCost (arr) {
      return arr.reduce((total, row) => {
        return total + row.amount
      }, 0).toFixed(2)
    },
    footerMethod ({ columns, data }) {
      return [columns.map((col, _colI) => {
        if (_colI === 0) {
          return '合计'
        }
        if (_colI === 3) {
          return this.countArrCost(data)
        }
        return ''
      })]
    },
    openDetail (row) {
      this.$store.commit('workRecord/SET_ORDER', row)
      this.$refs.WorkRecordCheck.open()
    },
    type2value (key) {
      let result = '-'
      if (key) {
        this.dictionary.forEach(item => {
          if (item.key === key) {
            result = item.value
            return false
          }
        })
      }
      return result
    },
    selectChangeEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
      this.selectUser = records
    },
    selectAllEvent ({ checked, records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
      this.selectUser = records
    },
    delServices () {
      this.$http.post('/serviceRecord/delServiceRecord', { ids: this.selectedRowKeys }).then(({
        success,
        errorMessage
      }) => {
        if (success) {
          this.$message.success('删除成功')
          this.onSearch()
        } else {
          this.$message.error(errorMessage)
        }
      })
    }
  },
  beforeMount () {
    this.$http.get('/sysDictionary/getDictionary', {
      params: { type: 17 }
    }).then(({ data }) => {
      this.dictionary = data
    })
  }
}
</script>
