<template>
  <a-card :bordered="false">
    <!-- 表格信息内容（主） -->
    <!-- <div v-if="!isEdit"> -->
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input placeholder="请输入客户名称" v-model="queryParam.companyName" />
      </a-form-item>
      <a-form-item label="所在地">
        <a-cascader
          :changeOnSelect="true"
          @change="v => (queryParam.addressCode = v)"
          placeholder="请输入所在地"
          style="width:230px;"
          :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
          :options="addressCode"
        />
      </a-form-item>
      <a-form-item label="所属行业">
        <a-tree-select
          allowClear
          :dropdown-style="{ maxHeight: '250px', overflow: 'auto' }"
          v-model="queryParam.industryCode"
          placeholder="请选择所属行业"
          style="width:230px;"
          treeDefaultExpandAll
          :maxTagCount="1"
          :treeData="industryCodeArr"
        >
        </a-tree-select>
      </a-form-item>
      <a-form-item label="业务员">
        <mini-select
          style="width:180px;"
          placeholder="请输入业务员查询"
          url="/user/userForSelect"
          sKey="id"
          :sTitle="['realName', 'userName']"
          queryKey="realName"
          ref="miniUser"
          @change="(v, row) => (queryParam.userId = v)"
        />
      </a-form-item>
      <a-form-item label="所属部门">
        <dept-select-only ref="deptSelect" @deptFullPath="path => (queryParam.fullPath = path)" style="width:180px;" />
      </a-form-item>
      <a-form-item label="是否开通平台">
        <a-select v-model="queryParam.hasOpen" style="width:150px;" placeholder="是否开通平台" allowClear>
          <a-select-option value="1">是</a-select-option>
          <a-select-option value="0">否</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="公司类型">
        <a-select v-model="queryParam.companyType" style="width:150px;" placeholder="公司类型" allowClear>
          <a-select-option value="0">普通公司</a-select-option>
          <a-select-option value="1">集团公司</a-select-option>
          <a-select-option value="3">子集团</a-select-option>
          <a-select-option value="2">子公司</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button type="primary" @click="search(true)" v-if="$auth('customer:all:search')">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      ref="table"
      queryUrl="/customer/getList"
      :params="getParams()"
      highlight-current-row
      highlight-hover-row
      show-overflow
      resizable
      auto-resize
      @completed="() => (selectedRowKeys = [])"
      @checkbox-change="selectChange"
      @checkbox-all="selectChange"
      :toolbar="{ zoom: true, custom: true, refresh: true }"
    >
      <template v-slot:buttons>
        <span v-if="$auth('customer:all:add')" style="padding-right:10px;">
          <a-button type="primary" @click="$refs.addCustomer.add()">添加</a-button>
        </span>
        <span v-if="$auth('customer:all:setOwner')" style="padding-right:10px;">
          <a-button
            type="primary"
            :disabled="selectedRowKeys.length <= 0"
            @click="$refs.ownerUser.showModal(selectedRowKeys)"

          >指定业务员</a-button
          >
        </span>
        <span v-if="$auth('customer:all:setGroup')" style="padding-right:10px;">
          <a-button
            type="primary"
            :disabled="selectedRowKeys.length != 1 || ![0, 2].includes(currentType)"
            @click="setGroup"
          >集团设置</a-button
          >
        </span>

      </template>
      <vxe-table-column type="checkbox" width="40" fixed="left" />
      <vxe-table-column
        title="客户名称"
        field="companyName"
        key="companyName"
        show-header-overflow
        :min-width="200"
        fixed="left"
        remoteSort
        show-overflow="tooltip"
      >
        <template v-slot="{ row }">
          <template v-if="row.companyId">
            <a @click="switchEdit(row)">{{ row.companyName }}</a>
          </template>
          <template v-else>
            {{ row.companyName }}
          </template>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="级别"
        field="companyLevel"
        remoteSort
        key="companyLevel"
        :width="80"
        show-header-overflow
        align="center"
      >
        <template v-slot="{ row }">
          <span v-if="row.companyLevel==null">{{ '--' }}</span>
          <span v-else>{{ row.companyLevel }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="所在地"
        field="addressCode"
        key="addressCode"
        :width="220"
        show-header-overflow
        show-overflow="tooltip"
      >
        <template v-slot="{ row }">{{ row.addressCode ? getAddres(row.addressCode): '--' }}</template>
      </vxe-table-column>
      <vxe-table-column
        title="所属行业"
        field="industryCode"
        key="industryCode"
        show-header-overflow
        :width="220"
        show-overflow="tooltip"
      >
        <template v-slot="{ row }">
          <span v-if="row.industryCode !== ''">{{ industryCodeDataArr(row.industryCode) }}</span>
          <span v-else>--</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="状态"
        field="status"
        key="status"
        show-header-overflow
        :width="220"
        show-overflow="tooltip"
      >
        <template v-slot="{ row }">
          <span>{{ statusData[row.status] }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column title="业务员" field="owerName" key="owerName" :width="80" />
      <vxe-table-column
        title="所属部门"
        field="deptName"
        key="deptName"
        :width="100"
        show-header-overflow
        show-overflow="tooltip"
      />
      <vxe-table-column
        title="是否开通平台"
        align="center"
        field="hasAccount"
        key="hasAccount"
        remoteSort
        show-overflow="tooltip"
        show-header-overflow
        :width="130"
      >
        <template v-slot="{ row }">
          <span v-if="row.companyId > 0">是</span>
          <span v-else>否</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="集团"
        align="center"
        field="companyType"
        key="companyType"
        remoteSort
        show-overflow="tooltip"
        show-header-overflow
        :width="100"
      >
        <template v-slot="{ row }">
          <span v-if="[1, 3].includes(row.companyType)"> 是</span>
          <span v-else>否</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="创建时间"
        align="center"
        show-overflow="tooltip"
        show-header-overflow
        field="creatorTime"
        key="creatorTime"
        remoteSort
        :width="180"
      />
      <vxe-table-column
        title="最后更新时间"
        remoteSort
        align="center"
        field="lastUpdateTime"
        key="lastUpdateTime"
        show-header-overflow
        show-overflow="tooltip"
        :width="180"
      />
      <vxe-table-column
        title="操作"
        field="key"
        :width="272"
        header-align="center"
        fixed="right">
        <template v-slot="{ row }">
          <span v-if="row.companyId <= 0 && $auth('customer:all:addAccount')">
            <a @click="$refs.account.open(row)">开通帐号</a>
            <a-divider type="vertical" v-if="$auth('customer:all:sonCustomer')" />
          </span>
          <!-- will fix, add Permissions -->
          <span v-if="row.status !== 30 && $auth('customer:all:follow')">
            <a @click="$refs.followCustomer.open(row.companyName, row.id)">跟进</a>
            <a-divider type="vertical" v-if="$auth('customer:all:sonCustomer') || $auth('customer:all:changeName') || $auth('customer:all:platformAccount')" />
          </span>
          <span v-if="$auth('customer:all:followLog')">
            <a @click="$refs.followLog.open(row.companyName, row.id)">跟进记录</a>
            <a-divider type="vertical" v-if="$auth('customer:all:changeName')" />
          </span>
          <span v-if="$auth('customer:all:changeName')">
            <a @click="$refs.changeName.open(row.companyName, row.companyId, row.id)">变更名称</a>
            <a-divider type="vertical" v-if="row.companyId != 0 && $auth('customer:all:sonCustomer')" />
          </span>
          <span v-if="row.companyId != 0 && $auth('customer:all:platformAccount')">
            <a @click="$refs.number.open(row)">平台帐号</a>
            <a-divider type="vertical" v-if="[1,3].includes(row.companyType) && $auth('customer:all:sonCustomer')" />
          </span>
          <span v-if="[1,3].includes(row.companyType) && $auth('customer:all:sonCustomer')">
            <a @click="$refs.addSonCustomer.findSonCustomer(row)">集团管理</a>
          </span>
        </template>
      </vxe-table-column>
    </ystable>
    <openingAccount-modal ref="account" @ok="openingOk"></openingAccount-modal>
    <follow-customers-modal ref="followCustomer" @refresh="search" :unsigned="false"></follow-customers-modal>
    <follow-log-modal ref="followLog"></follow-log-modal>
    <change-name-customer-modal ref="changeName" @refresh="search"></change-name-customer-modal>
    <platformAccountNumber-modal ref="number"></platformAccountNumber-modal>
    <owner-user-modal ref="ownerUser" @ok="f => search(f)" />
    <add-customer-modal ref="addCustomer" @ok="handleOk"></add-customer-modal>
    <!-- </div> -->
    <!-- <div v-else :bordered="false"> -->
    <customer-information ref="customerInformation" @refresh="search"></customer-information>
    <!-- </div> -->
    <add-son-customer-modal ref="addSonCustomer" @search="search"></add-son-customer-modal>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import DeptSelectOnly from '@/components/DeptSelectOnly'
import MiniSelect from '@/components/MiniSelect'
import PlatformAccountNumberModal from './modules/PlatformAccountNumberModal'
import OwnerUserModal from './modules/OwnerUserModal'
import OpeningAccountModal from './modules/OpeningAccountModal'
import AddCustomerModal from './modules/AddCustomerModal'
import customerInformation from './customerInformation/Index'
import Template from '../doc/Template'
import AddSonCustomerModal from './modules/AddSonCustomerModal'
import FollowLogModal from './modules/FollowLogModal'
import FollowCustomersModal from './modules/FollowCustomersModal'
import ChangeNameCustomerModal from './modules/ChangeNameCustomerModal'

export default {
  name: 'Customer',
  components: {
    ystable,
    DeptSelectOnly,
    MiniSelect,
    PlatformAccountNumberModal,
    OwnerUserModal,
    OpeningAccountModal,
    AddCustomerModal,
    customerInformation,
    Template,
    AddSonCustomerModal,
    FollowCustomersModal,
    FollowLogModal,
    ChangeNameCustomerModal
  },
  data () {
    return {
      currentCompanyName: '',
      currentType: 0,
      selectedRowKeys: [],
      addressCode: [],
      // 查询参数
      queryParam: {},
      visible: false,
      title: '',
      isEdit: true,
      form: this.$form.createForm(this, { scroll: {} }),
      industryCodeArr: [],
      industryCodeData: {},
      // statusData: {
      //   /// 状态，0:未审核，1：审核通过 2： 审核未通过
      //   '0': '未审核',
      //   '1': '审核通过',
      //   '2': '审核未通过'
      // },
      statusData: {
        // '0': '未签',
        '10': '邀约',
        '20': '拜访',
        '30': '已签'
      },
      from: {
        /// 0:用户注册，1：氚云，2：新增
        '0': '用户注册',
        '1': '氚云',
        '2': '新增'
      }
    }
  },
  created () {
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
    this.$getDictionary(15).then(d => {
      this.industryCodeArr = this.$deepClone(d)
      this.transfromTreeData(this.industryCodeArr)
    })
  },
  methods: {
    transfromTreeData (data) {
      if (!data || !data.length) {
        return
      }
      for (const item of data) {
        item['title'] = item['value']
        item['value'] = item['key']
        this.industryCodeData[item['key']] = item['title']
        if (item['children'] && item['children'].length) {
          this.transfromTreeData(item['children'])
        } else {
          delete item['children']
        }
      }
    },
    getParams () {
      if (this.queryParam.addressCode && Array.isArray(this.queryParam.addressCode)) {
        this.queryParam.addressCode = this.queryParam.addressCode.join(',')
      }
      this.queryParam.unsigned = false
      return this.queryParam
    },
    getAddres (code) {
      if (!code) {
        return ''
      }
      const arr = code.split(',')
      var currentIndex = 0
      var result = ''
      var tempChildren = this.addressCode
      while (currentIndex < arr.length) {
        for (let i = 0; i < tempChildren.length; i++) {
          if (arr[currentIndex] === tempChildren[i].key) {
            result += tempChildren[i].value + '/'
            if (tempChildren[i].children && tempChildren[i].children.length > 0) {
              tempChildren = tempChildren[i].children
            } else {
              tempChildren = []
            }
            break
          }
        }
        currentIndex++
      }
      return result ? result.substr(0, result.length - 1) : result
    },
    industryCodeDataArr (text) {
      if (text) {
        const result = []
        const dataAdd = text.split(',')
        for (let i = 0; i < dataAdd.length; i++) {
          if (this.industryCodeData[dataAdd[i]]) {
            result.push(this.industryCodeData[dataAdd[i]])
          }
        }
        return result.join(' | ')
      } else {
        return '-'
      }
    },
    moment,
    handleOk (flag) {
      this.selectedRowKeys = []
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.search(flag)
    },
    openingOk (flag) {
      if (flag) {
        this.$message.success('开通账户成功')
      }
      this.$refs.table.refresh(true)
    },
    getFullPath (fullPath) {
      this.queryParam.fullPath = fullPath
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.$refs.table.refresh(refresh)
    },
    selectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
      if (records && records.length) {
        this.currentCompanyName = records[0].companyName
        this.currentType = records[0].companyType
      } else {
        this.currentType = 0
        this.currentCompanyName = ''
      }
    },
    switchEdit (row) {
      this.$refs.customerInformation.show(row)
      // this.isEdit = !this.isEdit
    },
    setGroup () {
      const self = this
      this.$confirm({
        title: `您确定要设置[${self.currentCompanyName}]为集团吗？`,
        onOk () {
          self.confirmSetGroup()
        },
        onCancel () {
        }
      })
    },
    confirmSetGroup () {
      this.$http
        .post('/customer/setGroup', { id: this.selectedRowKeys[0] })
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('设置成功')
            this.selectedRowKeys = []
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '修改失败')
          }
        })
        .finally(res => {
          this.$refs.table.refresh(false)
        })
    }
  }
}
</script>
<style lang="less">
.toLeft {
  padding-left: 10px;
}

.vxe-table--body tr td:last-child span {
  display: inline-block;
  // margin: 0 10px;
}
</style>
