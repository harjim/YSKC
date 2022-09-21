<template>
  <div :class="{ required: getIsEditStatus }">
    <a-descriptions :column="2" layout="horizontal">
      <a-descriptions-item label="申请人">
        {{ currentOrder.ownerName }}
      </a-descriptions-item>
      <a-descriptions-item label="所属部门">
        {{ currentOrder.deptName }}
      </a-descriptions-item>
      <a-descriptions-item label="技术人员">
        <template v-if="!getIsEditStatus && !(canEditTech || canEditAll)">
          {{ currentOrder.techList && currentOrder.techList.map(i => i.userName).join(', ') }}
        </template>
        <template v-else>
          <select-man
            placeholder="请输入技术人员"
            style="width:300px;"
            :type="1"
            keyField="tech"
            :list="currentOrder.techList"
            :userId="canEditTech ? userInfo.id : null"
            @change="changeStateUser"
            @blur="getBoss"
          />
        </template>
      </a-descriptions-item>
      <a-descriptions-item label="财务人员">
        <template v-if="!getIsEditStatus && !(canEditFina || canEditAll)">
          {{ currentOrder.finaList && currentOrder.finaList.map(i => i.userName).join(', ') }}
        </template>
        <template v-else>
          <select-man
            placeholder="请输入财务人员"
            style="width:300px;"
            :type="2"
            keyField="fina"
            :userId="canEditFina ? userInfo.id : null"
            :list="currentOrder.finaList"
            @change="changeStateUser"
            @blur="getBoss"
          />
        </template>
      </a-descriptions-item>
      <a-descriptions-item label="技术经理">
        {{ currentOrder.techManagerName || '-' }}
      </a-descriptions-item>
      <a-descriptions-item label="财务经理">
        {{ currentOrder.finaManagerName || '-' }}
      </a-descriptions-item>
      <a-descriptions-item label="技术总监">
        {{ currentOrder.techDirectorName || '-' }}
      </a-descriptions-item>
      <a-descriptions-item label="财务总监">
        {{ currentOrder.finaDirectorName || '-' }}
      </a-descriptions-item>
      <a-descriptions-item label="其他人员">
        <template v-if="!getIsEditStatus && !canEditAll">
          {{ currentOrder.otherList && currentOrder.otherList.map(i => i.userName).join(', ') }}
        </template>
        <template v-else>
          <select-man
            placeholder="请输入其他人员"
            style="width:300px;"
            :type="5"
            keyField="other"
            :list="currentOrder.otherList"
            @change="changeStateUser"
          />
        </template>
      </a-descriptions-item>
      <a-descriptions-item label="预计起止日期" class="required">
        <template v-if="!getIsEditStatus">
          {{ currentOrder.date }}
        </template>
        <template v-else>
          <date-range
            style="width: 300px;"
            :default-value="[moment(currentOrder.begin), moment(currentOrder.end)]"
            @onChange="onChange"/>
        </template>
      </a-descriptions-item>
    </a-descriptions>

    <a-descriptions class="flex-top" style="margin-top: 16px;">
      <a-descriptions-item label="服务事项">
        <div style="width: 1034px;">
          <ServiceEditTable
            ref="ServiceEditTable"
            :custom-list="currentOrder.customerList"
            :valid-rules="validRules"
            :edit="getIsEditStatus"
          >
            <vxe-table-column
              field="companyName"
              title="客户名称"
              minWidth="200"
              :edit-render="{ immediate: true }"
            >
              <template v-slot="{ row }">
                <template v-if="!getIsEditStatus">
                  <span>{{ row.companyName }}</span>
                </template>
                <select-company
                  v-else
                  style="width: 100%"
                  prop="companyName"
                  :defaultValue="row.companyName"
                  @changeCompany="(data, option) => {
                    row.companyName = data
                    row.customerId = option.key
                  }"
                />
              </template>
            </vxe-table-column>
            <vxe-table-column
              field="causes"
              title="事由"
              minWidth="200"
              :edit-render="{ immediate: true }"
            >
              <template v-slot="{ row }">
                <template v-if="!getIsEditStatus">
                  <span>{{ row.causes }}</span>
                </template>
                <a-textarea
                  v-else
                  placeholder="请输入事由"
                  style="width: 100%;"
                  v-model="row.causes"
                  :auto-size="{ minRows: 1, maxRows: 5 }"
                />
              </template>
            </vxe-table-column>
          </ServiceEditTable>
        </div>
      </a-descriptions-item>
    </a-descriptions>
    <a-descriptions class="flex-top" style="margin-top: 16px;">
      <a-descriptions-item label="备注">
        <template v-if="!getIsEditStatus">
          <span v-if="currentOrder.remark" v-html="currentOrder.remark.replace(/\n/g, '<br/>')"></span>
          <span v-else>-</span>
        </template>
        <template v-else>
          <a-textarea
            style="width: 1034px;"
            placeholder="请输入"
            :defaultValue="currentOrder.remark"
            :auto-size="{ minRows: 3, maxRows: 5 }"
            @change="changeRemark"
          />
        </template>
      </a-descriptions-item>
    </a-descriptions>

    <div style="position: absolute;top: 0;right: 4px;" v-if="!getIsEditStatus">
      <div style="color: rgba(0, 0, 0, .4);text-align: right;">流程状态</div>
      <div style="font-size: 24px;" :style="{ color: getStatusColor }">{{ getStatusTitle }}
      </div>
    </div>
  </div>
</template>

<script>
import SelectMan from '@/components/Selects/SelectMan'
import moment from 'moment'
import { mapGetters, mapState } from 'vuex'
import SelectCompany from '@/components/Selects/SelectCompany'
import ServiceEditTable from '@/views/customer/modules/ServiceEditTable'
import DateRange from '@/components/DateRange/DateRange'

export default {
  name: 'ServiceOrderDetailCheck',
  components: { DateRange, ServiceEditTable, SelectCompany, SelectMan },
  data () {
    return {
      form: {},
      validRules: {
        companyName: [{ required: true, message: '公司必须选择', trigger: 'blur' }],
        causes: [{ required: true, message: '事由必须填写', trigger: 'blur' }]
      },
      canEditTech: false,
      canEditFina: false,
      canEditAll: false
    }
  },
  computed: {
    ...mapState({
      currentOrder: state => state.service.currentOrder
    }),
    ...mapGetters(['userInfo']),
    ...mapGetters('service', ['getStatusColor', 'getStatusTitle', 'getIsEditStatus'])
  },
  mounted () {
    this.canEditTech = this.editTech()
    this.canEditFina = this.editFina()
    this.canEditAll = this.editAll()
  },
  methods: {
    moment,
    editTech () {
      const flag = this.currentOrder.techDirectorId === this.userInfo.id || this.currentOrder.techManagerId === this.userInfo.id
      return this.currentOrder.hasPermission && flag
    },
    editFina () {
      const flag = this.currentOrder.finaDirectorId === this.userInfo.id || this.currentOrder.finaManagerId === this.userInfo.id
      return this.currentOrder.hasPermission && flag
    },
    editAll () {
      const flag1 = this.currentOrder.techDirectorId !== this.userInfo.id && this.currentOrder.techManagerId !== this.userInfo.id
      const flag2 = this.currentOrder.finaDirectorId !== this.userInfo.id && this.currentOrder.finaManagerId !== this.userInfo.id
      return this.currentOrder.hasPermission && flag1 && flag2
    },
    onChange (dates, dateStr) {
      this.$store.commit('service/CHANGE_DATE', dateStr)
    },
    changeRemark (e) {
      this.$store.commit('service/CHANGE_REMARK', e.target.value)
    },
    changeStateUser (k, v) {
      this.$store.commit({
        type: 'service/CHANGE_USER',
        key: `${k}List`,
        data: v.map(item => ({
          id: item.id,
          userId: item.key,
          userName: item.label
        }))
      })
    },
    getBoss (keyField, value, type) {
      this.$store.dispatch({
        type: 'service/changeBoss',
        key: keyField,
        userIds: value ? value.map(i => i.key) : [],
        types: type
      })
    }
  }
}
</script>

<style lang="less" scoped>
& /deep/ .ant-descriptions-item-content {
  width: 460px;
  display: inline-table;
}

& /deep/ tr:nth-child(2) {
  height: 50px;
}

& /deep/ tr:nth-child(5) {
  height: 50px;
}

& /deep/ .ant-descriptions-item-label {
  width: 100px;
  text-align: right;
}

.flex-top /deep/ .ant-descriptions-item {
  display: flex;
}

.required /deep/ tr:nth-child(2) {
  & .ant-descriptions-item {
    position: relative;

    & .ant-descriptions-item-label::before {
      content: '*';
      color: #f5222d;
      position: absolute;
      top: 6px;
      left: 20px;
    }
  }
}

.required /deep/ tr:nth-child(5) {
  & .ant-descriptions-item:last-child {
    position: relative;

    & .ant-descriptions-item-label::before {
      content: '*';
      color: #f5222d;
      position: absolute;
      top: 6px;
      left: -4px;
    }
  }
}
</style>
