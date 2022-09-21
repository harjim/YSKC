<template>
  <a-card :bordered="false">
    <!-- 表格信息内容（主） -->
    <!-- <div v-if="!isEdit"> -->
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input
          v-model="queryParam.companyName"
          placeholder="请输入客户名称"
        />
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
      <a-form-item label="是否开通平台">
        <a-select
          v-model="queryParam.hasOpen"
          allowClear
          placeholder="是否开通平台"
          style="width:150px;"
        >
          <a-select-option value="1">是</a-select-option>
          <a-select-option value="0">否</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="公司类型">
        <a-select
          v-model="queryParam.companyType"
          allowClear
          placeholder="公司类型"
          style="width:150px;"
        >
          <a-select-option value="0">普通公司</a-select-option>
          <a-select-option value="1">集团公司</a-select-option>
          <a-select-option value="3">子集团</a-select-option>
          <a-select-option value="2">子公司</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item>
        <a-button
          v-if="$auth('customer:unsigned:search')"
          type="primary"
          @click="search(true)"
        >查询
        </a-button>
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
        <span
          v-if="$auth('customer:unsigned:follow')"
          style="padding-right:10px;"
        >
          <a-button
            type="primary"
            :disabled="selectedRowKeys.length <= 0"
            @click="$refs.followCustomer.open('', selectedRowKeys)"
          >跟进</a-button
          >
        </span>
      </template>
      <vxe-table-column
        fixed="left"
        type="checkbox"
        width="40"
      />
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
          <span v-if="row.industryCode !== ''">{{ industryCodeDataArr(row.industryCode)
          }}</span>
        </template>
      </vxe-table-column>
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
        :width="122"
        header-align="center"
        fixed="right"
      >
        <template v-slot="{ row }">
          <span v-if="$auth('customer:unsigned:followLog')">
            <a @click="$refs.followLog.open(row.companyName, row.id)">跟进记录</a>
          </span>
        </template>
      </vxe-table-column>
    </ystable>
    <follow-customers-modal
      ref="followCustomer"
      @refresh="search"
    ></follow-customers-modal>
    <follow-log-modal ref="followLog"></follow-log-modal>
    <!-- <owner-user-modal ref="ownerUser" :unsigned="true" @ok="f => search(f)" /> -->
    <customer-information ref="customerInformation"></customer-information>
  </a-card>
</template>

<script>
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import MiniSelect from '@/components/MiniSelect'
import customerInformation from './customerInformation/Index'
import Template from '../doc/Template'
import FollowLogModal from './modules/FollowLogModal'
import FollowCustomersModal from './modules/FollowCustomersModal'

export default {
  name: 'Customer',
  components: {
    ystable,
    MiniSelect,
    customerInformation,
    Template,
    FollowCustomersModal,
    FollowLogModal
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
      this.queryParam.unsigned = true
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
