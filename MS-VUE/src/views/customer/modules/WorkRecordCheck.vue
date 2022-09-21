<template>
  <a-drawer
    :visible="visible"
    destroyOnClose
    title="服务记录详情"
    :width="1184"
    :drawerStyle="{ height: '100vh' }"
    :bodyStyle="{ overflowY: 'hidden' }"
    @close="close"
  >
    <tab-layout :class="{ 'not-log': order.instanceId }">
      <template #up>
        <div style="width: 100%">
          <a-descriptions :column="2">
            <a-descriptions-item label="客户名称">
              {{ order.companyName }}
            </a-descriptions-item>
            <a-descriptions-item label="关联单号">
              <template v-if="!getIsEditStatus">
                <a @click="openServiceEdit">{{ order.serviceNo }}</a>
              </template>
              <template v-else>
                <a-popover
                  :autoAdjustOverflow="false"
                  placement="bottom"
                  destroyTooltipOnHide
                  arrowPointAtCenter
                  trigger="click"
                >
                  <template slot="content">
                    <ystable
                      ref="pTable"
                      query-url="/serviceRecord/getServiceNo"
                      :params="{ customerId: order.customerId }"
                      size="mini"
                      :max-height="200"
                      show-overflow="title"
                      @cell-click="clickRow"
                    >
                      <vxe-table-column field="serviceNo" title="服务单号" minWidth="140" />
                      <vxe-table-column field="ownerName" title="申请人" width="100" />
                      <vxe-table-column field="date" title="预计起止日期" width="100" />
                    </ystable>
                  </template>
                  <a-input
                    readOnly
                    v-model="order.serviceNo"
                    style="width: 300px"
                    :disabled="!order.customerId"
                    placeholder="请选择服务单号"
                  />
                </a-popover>
              </template>
            </a-descriptions-item>
            <a-descriptions-item label="业务员">
              <template v-if="!getIsEditStatus">
                {{ order.ownerName }}
              </template>
              <template v-else>
                <search-select
                  style="width: 300px"
                  url="/user/userForSelect"
                  searchField="realName"
                  sTitle="realName"
                  placeholder="请输入业务员"
                  v-model="owner"
                  :allowClear="false"
                />
              </template>
            </a-descriptions-item>
            <a-descriptions-item label="所属部门">
              <template v-if="!getIsEditStatus">
                {{ order.deptName }}
              </template>
              <template v-else>
                <a-input style="width: 300px" v-model="order.deptName" placeholder="请输入所属部门" disabled />
              </template>
            </a-descriptions-item>
            <a-descriptions-item label="创建人">
              {{ order.creatorName }}
            </a-descriptions-item>
            <a-descriptions-item label="创建时间">
              {{ order.createTime }}
            </a-descriptions-item>
          </a-descriptions>
          <div style="position: absolute; top: 0; right: 0" v-if="!getIsEditStatus">
            <div style="color: rgba(0, 0, 0, 0.4); text-align: right">流程状态</div>
            <div style="font-size: 24px" :style="{ color: getStatusColor }">{{ getStatusTitle }}</div>
          </div>
        </div>

        <ServiceEditTable
          ref="ServiceEditTable"
          :custom-list="order.list"
          :valid-rules="validRules"
          :edit="getIsEditStatus"
          :footerMethod="footerMethod"
        >
          <vxe-table-column field="itemType" title="事项" width="160" :edit-render="{ immediate: true }">
            <template v-slot="{ row }">
              <template v-if="!getIsEditStatus">
                <span>{{ type2value(row.itemType) }}</span>
              </template>
              <template v-else>
                <a-select allowClear v-model="row.itemType" style="width: 100%" placeholder="请选择事项">
                  <a-select-option v-for="item in dictionary" :key="item.key">
                    {{ item.value }}
                  </a-select-option>
                </a-select>
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column field="date" title="起止时间" align="center" width="440" :edit-render="{ immediate: true }">
            <template v-slot="{ row }">
              <template v-if="!getIsEditStatus">
                <span>{{ row.date }}</span>
              </template>
              <template v-else>
                <DateRange
                  show-time
                  :default-value="[row.begin ? moment(row.begin) : null, row.end ? moment(row.end) : null]"
                  @onChange="(d, s) => changeRangeDate(row, d, s)"
                />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column field="amount" title="费用" align="right" width="150" :edit-render="{ immediate: true }">
            <template v-slot="{ row }">
              <template v-if="!getIsEditStatus">
                <span>{{ row.amount }}</span>
              </template>
              <template v-else>
                <a-input-number
                  :min="0"
                  :step="0.01"
                  :max="$store.state.totalMax"
                  v-model="row.amount"
                  placeholder="请输入费用金额"
                  style="width: 100%"
                  @change="$refs.ServiceEditTable.$refs.xTable.updateFooter()"
                />
              </template>
            </template>
          </vxe-table-column>
          <vxe-table-column field="remark" title="备注" width="200" :edit-render="{ immediate: true }">
            <template v-slot="{ row }">
              <template v-if="!getIsEditStatus">
                <span v-if="row.remark" v-html="row.remark.replace(/\n/g, '<br/>')"></span>
                <span v-else>-</span>
              </template>
              <template v-else>
                <a-textarea
                  placeholder="请输入"
                  style="width: 100%"
                  v-model="row.remark"
                  :auto-size="{ minRows: 1, maxRows: 3 }"
                />
              </template>
            </template>
          </vxe-table-column>
        </ServiceEditTable>
      </template>
      <template #down v-if="order.instanceId">
        <template v-if="$auth('customer:serviceApply:review') || $auth('customer:serviceApply:audit')">
          <ServiceOrderDetailAudit @refresh="$emit('refresh')" storeName="workRecord" detail="recordOrder" />
        </template>
      </template>
    </tab-layout>

    <div
      v-if="getIsEditStatus"
      :style="{
        position: 'absolute',
        right: 0,
        bottom: 0,
        width: '100%',
        height: '56px',
        borderTop: '1px solid #e9e9e9',
        padding: '10px 16px',
        background: '#fff',
        textAlign: 'right',
        zIndex: 1
      }"
    >
      <a-button :style="{ marginRight: '8px' }" @click="handleSaveForm('/serviceRecord/editServiceRecord')">
        暂存
      </a-button>
      <a-popconfirm
        title="是否确认提交?"
        placement="topRight"
        :autoAdjustOverflow="false"
        @confirm="handleSaveForm('/serviceRecord/submit')"
      >
        <a-button type="primary" :disabled="!getIsEditStatus || tableEdit || !(order.list && order.list.length)"> 提交 </a-button>
      </a-popconfirm>
    </div>

    <RecordCheckServiceDetail ref="RecordCheckServiceDetail" :service-no="order.serviceNo" />
  </a-drawer>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { mapGetters, mapState } from 'vuex'
import ServiceOrderDetailAudit from '@/views/customer/modules/ServiceOrderDetailAudit'
import TabLayout from '@/views/customer/modules/AuditProgress/modules/TabLayout'
import SearchSelect from '@/components/Selects/SearchSelect'
import ServiceEditTable from '@/views/customer/modules/ServiceEditTable'
import DateRange from '@/components/DateRange/DateRange'
import moment from 'moment'
import RecordCheckServiceDetail from '@/views/customer/modules/RecordCheckServiceDetail'

export default {
  name: 'WorkRecordCheck',
  components: {
    RecordCheckServiceDetail,
    DateRange,
    ServiceEditTable,
    SearchSelect,
    TabLayout,
    ServiceOrderDetailAudit,
    ystable
  },
  props: {
    dictionary: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      visible: false,
      tableEdit: false,
      owner: {},
      validRules: {
        itemType: [{ required: true, message: '事由必须填写', trigger: 'blur' }],
        date: [{ required: true, message: '起始日期必须选择', trigger: 'blur' }],
        amount: [{ required: true, message: '金额必须填写', trigger: 'blur' }]
      }
    }
  },
  computed: {
    ...mapState({
      order: (state) => state.workRecord.recordOrder
    }),
    ...mapGetters('workRecord', ['getIsEditStatus', 'getStatusColor', 'getStatusTitle'])
  },
  watch: {
    owner(v) {
      if (v) {
        this.$store.commit('workRecord/CHANGE_OWNER', v)
      }
    }
  },
  methods: {
    moment,
    open() {
      this.owner = {
        id: this.order.ownerId,
        realName: this.order.ownerName,
        deptName: this.order.deptName
      }
      this.visible = true
    },
    close() {
      Object.assign(this.$data, this.$options.data())
      this.$store.commit('workRecord/SET_ORDER', {})
    },
    footerMethod({ columns, data }) {
      return [
        columns.map((col, _colI) => {
          if (_colI === 0) {
            return '合计'
          }
          if (_colI === 3) {
            return data
              .reduce((total, row) => {
                if (isNaN(row.amount)) return total
                return total + row.amount
              }, 0)
              .toFixed(2)
          }
          return ''
        })
      ]
    },
    type2value(key) {
      let result = '-'
      if (key) {
        this.dictionary.forEach((item) => {
          if (item.key === key) {
            result = item.value
            return false
          }
        })
      }
      return result
    },
    clickRow({ row }) {
      this.$store.commit('workRecord/CHANGE_SERVICE', row)
      if (row.ownerId) {
        this.owner = {
          id: row.ownerId,
          realName: row.ownerName,
          deptId: row.deptId,
          deptName: row.deptName
        }
      }
    },
    changeRangeDate(row, dates, dateStr) {
      row.begin = dateStr[0]
      row.end = dateStr[1]
      row.date = `${dateStr[0]}-${dateStr[1]}`
    },
    handleSaveForm(url) {
      if (!this.order.customerId) {
        this.$message.error('请选择客户名称')
        return
      }
      if (!this.order.ownerId) {
        this.$message.error('请选择业务员')
        return
      }
      const [flag, list] = this.$refs.ServiceEditTable.validAllEvent()
      if (!flag) return
      const params = {
        ...this.order,
        list
      }
      this.$nextTick(() => {
        this.$http.post(url, params).then(({ success, errorMessage }) => {
          if (success) {
            this.$message.success('操作成功')
            this.close()
            this.$emit('refresh', false)
          } else {
            this.$message.error(errorMessage)
          }
        })
      })
    },
    openServiceEdit() {
      this.$refs.RecordCheckServiceDetail.open()
    }
  }
}
</script>

<style lang="less" scoped>
& /deep/ .ant-spin-container {
  height: calc(100vh - 64px);
}

& /deep/ .ant-descriptions-item-content {
  width: auto;
}

.not-log /deep/ .center_wrap {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 110px);

  .up {
    height: 70%;
    width: 100%;

    &::-webkit-scrollbar {
      display: none;
    }
  }

  & > .midden {
    width: 100%;
    height: 6px;
    background-color: #f0f2f5;
    border-radius: 3px;

    &:hover {
      cursor: n-resize;
      background-color: #d6d6d6;
    }
  }

  .down {
    overflow: auto;
    flex: 1;
    height: 100%;

    & /deep/ .ant-tabs-tabpane {
      overflow: auto;
    }
  }
}
</style>
