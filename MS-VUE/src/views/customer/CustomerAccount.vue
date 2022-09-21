<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input placeholder="请输入客户名称" v-model="companyName" />
      </a-form-item>

      <a-form-item label="系统名称">
        <a-select
          showSearch
          v-model="platform"
          placeholder="请输入系统名称搜索"
          style="width:200px"
          :defaultActiveFirstOption="false"
          :showArrow="false"
          :allowClear="true"
          :filterOption="false"
          @search="getPlatform"
          @change="platformChange"
          :notFoundContent="null"
        >
          <a-select-option v-for="d in platformList" :key="d.platform">{{ d.platform }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="网址">
        <a-select
          showSearch
          v-model="pUrl"
          placeholder="请输入网址搜索"
          style="width:200px"
          :allowClear="true"
          :defaultActiveFirstOption="false"
          :showArrow="false"
          :filterOption="false"
          @search="getpUrl"
          @change="pUrlChange"
          :notFoundContent="null"
        >
          <a-select-option v-for="d in pUrlList" :key="d.pUrl">{{ d.pUrl }}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label="所在地">
        <a-cascader
          :changeOnSelect="true"
          @change="(v)=>addrcode = v"
          placeholder="请输入所在地"
          style="width:230px;"
          :options="addressCode"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" v-if="$auth('customer:account:search')" @click="$refs.table.refresh(true)">查询</a-button>
      </a-form-item>
    </a-form>
    <ystable
      rowId="id"
      ref="table"
      @completed="()=>selectedRowKeys=[]"
      queryUrl="/customerAccount/queryCustomerAccountList"
      :params="getParams()"
      :toolbar="{
        custom: true,
        zoom:true,
        refresh:true
      }"
      @checkbox-change="onSelectChange"
      @checkbox-all="onSelectChange"
    >
      <template v-slot:buttons>
        <a-button
          v-if="$auth('customer:account:add')"
          type="primary"
          style="margin-right:10px"
          @click="$refs.creatModal.add()"
        >
          添加
        </a-button>
        <a-button
          v-if="$auth('customer:account:delBatch')"
          type="primary"
          style="margin-right:10px"
          @click="delBatch"
          :disabled="selectedRowKeys.length <= 0"
        >
          删除
        </a-button>
      </template>
      <vxe-table-column type="checkbox" width="40" fixed="left" />
      <vxe-table-column
        title="客户名称"
        field="companyName"
        align="left"
        remoteSort
        fixed="left"
        show-header-overflow
        show-overflow="tooltip"
        :width="200"
      />
      <vxe-table-column
        title="系统名称"
        field="platform"
        align="left"
        remoteSort
        show-header-overflow
        show-overflow="tooltip"
        :width="180"
      />
      <vxe-table-column
        title="网址"
        field="pUrl"
        align="left"
        remoteSort
        show-header-overflow
        show-overflow="tooltip"
        :width="180"
      >
        <template v-slot="{row}"><a
          :href="row.pUrl"
          target="_blank">{{ row.pUrl }}</a></template>
      </vxe-table-column>
      <vxe-table-column
        title="用户名"
        field="username"
        align="left"
        remoteSort
        show-header-overflow
        show-overflow="tooltip"
        :width="120"
      />
      <vxe-table-column title="密码" field="password" align="center" :width="110" />
      <vxe-table-column title="创建人" field="creatorName" align="left" :width="110" />
      <vxe-table-column
        title="创建时间"
        field="createTime"
        align="center"
        remoteSort
        show-header-overflow
        show-overflow="tooltip"
        :width="160"
      />
      <vxe-table-column
        title="客户所在地"
        field="addressCode"
        align="left"
        show-header-overflow
        show-overflow="tooltip"
        :width="180"
      >
        <template v-slot="{ row }">{{ getAddres(row.addressCode) }}</template>
      </vxe-table-column>
      <vxe-table-column
        title="备注"
        field="remark"
        align="left"
        show-header-overflow
        show-overflow="tooltip"
        :width="100"
      />
      <vxe-table-column align="center" title="操作" key="action" :width="200" fixed="right">
        <template v-slot="{ row }">
          <a
            href="javascript:;"
            @click="getCustomerMangetMent(row)"
            v-if="$auth('customer:account:seePassword')"
          >查看密码</a>
          <a-divider type="vertical" v-if="$auth('customer:account:seePassword')" />
          <a
            href="javascript:;"
            v-if="$auth('customer:account:edit')"
            @click="$refs.creatModal.edit(row)"
          >编辑</a>
          <a-divider type="vertical" v-if="$auth('customer:account:edit')" />
          <a-popconfirm
            title="是否确定删除?"
            v-if="$auth('customer:account:del')"
            @confirm="handleDel(row)"
          >
            <a>删除</a>
          </a-popconfirm>
        </template>
      </vxe-table-column>
    </ystable>
    <customerAccount-modal ref="creatModal" @ok="handleOk"></customerAccount-modal>
  </a-card>
</template>
<script>
import ystable from '@/components/Table/ystable'
import addressCode from '@/assets/addressCode'
import CustomerAccountModal from './modules/CustomerAccountModal'
export default {
  name: 'CustomerAccount',
  components: {
    ystable,
    CustomerAccountModal
  },
  data () {
    return {
      customerList: [],
      platformList: [],
      pUrlList: [],
      pagination: [],
      addrcode: undefined,
      selectedRowKeys: [],
      companyName: '',
      addressCode,
      pUrl: undefined,
      platform: undefined,
      companyAddress: undefined
    }
  },
  methods: {
    getParams () {
      if (this.addrcode && Array.isArray(this.addrcode)) {
        this.addrcode = this.addrcode.join(',')
      }
      return { companyName: this.companyName, platform: this.platform, pUrl: this.pUrl, addressCode: this.addrcode }
    },
    getAddres (code) {
      if (!code) {
        return ''
      }
      var arr = code.split(',')
      var currentIndex = 0
      var result = ''
      var tempChildren = this.addressCode
      while (currentIndex < arr.length) {
        for (let i = 0; i < tempChildren.length; i++) {
          if (arr[currentIndex] === tempChildren[i].value) {
            result += tempChildren[i].label + '/'
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
    onSelectChange ({ records }) {
      this.selectedRowKeys = records.map(item => {
        return item.id
      })
    },
    handleDel (record) {
      this.$http.post('/customerAccount/delCustomerAccount', { id: record.id })
        .then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.$refs.table.refresh()
          } else {
            this.$message.info(res.errorMessage)
          }
        })
    },

    delBatch () {
      this.spinning = true
      const self = this
      this.$confirm({
        title: '您确定要删除选中的账户吗?',
        onOk () {
          return self.$http.post('/customerAccount/delCustomerAccountList', { ids: self.selectedRowKeys })
            .then(res => {
              if (res.success) {
                self.$message.success('删除成功')
                self.selectedRowKeys = []
                self.$refs.table.refresh()
              } else {
                self.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
                self.selectedRowKeys = []
              }
              self.spinning = false
            })
        },
        onCancel () {
          self.spinning = false
        }
      })
    },
    handleOk (flag) {
      if (flag) {
        this.$message.success('添加成功')
      } else {
        this.$message.success('更新成功')
      }
      this.$refs.table.refresh(flag)
    },
    getCustomerMangetMent (row) {
      const temp = '******'
      if (row.password === temp) {
        this.$http.get('/customerAccount/getDataById', { params: { id: row.id } })
          .then(res => {
            this.$set(row, 'password', res.data.password)
          })
      } else {
        this.$set(row, 'password', temp)
      }
    },
    getCustomer (value) {
      return this.$http.get('/customer/getCustomerSelect', { params: { customerName: value } })
        .then(res => {
          this.customerList = res.data
          return this.customerList
        })
    },
    customerChange (value) {
      this.customerId = value
    },
    getPlatform (value) {
      return this.$http.get('/customerAccount/getDataByPlatform', { params: { platform: value } })
        .then(res => {
          this.platformList = res.data
          return this.platformList
        })
    },
    platformChange (value) {
      this.platform = value
    },
    getpUrl (value) {
      return this.$http.get('/customerAccount/getDataBypUrl', { params: { pUrl: value } })
        .then(res => {
          this.pUrlList = res.data
          return this.pUrlList
        })
    },
    pUrlChange (value) {
      this.pUrl = value
    }
  }
}
</script>
