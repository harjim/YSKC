<template>
  <a-card :bordered="false">
    <a-spin tip="请稍后..." :spinning="spinning">
      <a-form layout="inline">
        <a-form-item label="客户名称">
          <a-input placeholder="请输入客户名称" v-model="queryParams.companyName" />
        </a-form-item>
        <a-form-item label="所属部门">
          <dept-select style="width:165px;" v-model="queryParams.deptId" />
        </a-form-item>
        <a-form-item label="项目年份">
          <year-select style="width:120px;" v-model="queryParams.year" placeholder="项目年份" />
        </a-form-item>
        <a-form-item label="项目类型">
          <a-input placeholder="请输入项目类型" v-model="queryParams.productName" />
        </a-form-item>
        <a-form-item label="技术人员">
          <search-select
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="true"
            placeholder="技术人员"
            style="width:165px;"
            v-model="queryParams.tIds"
          />
        </a-form-item>
        <a-form-item label="财务人员">
          <search-select
            style="width:165px;"
            url="/user/userForSelect"
            searchField="realName"
            sTitle="realName"
            :multiple="true"
            placeholder="财务人员"
            v-model="queryParams.fIds"
          />
        </a-form-item>
        <a-form-item label="客户级别">
          <a-select style="width:165px;" v-model="queryParams.companyLevel" placeholder="请选择客户级别" allowClear>
            <a-select-option v-for="item in companyLevelOptions" :key="item.value" :value="item.label">{{ item.label
            }}
            </a-select-option>
          </a-select>
        </a-form-item>

        <a-form-item v-if="$auth('customer:my:search')">
          <a-button type="primary" @click="search">查询</a-button>
        </a-form-item>
      </a-form>

      <s-table rowKey="id" ref="table" :data="getData">
        <a-table-column
          title="客户名称"
          data-index="companyName"
          key="companyName"
          :width="320"
          align="left"
        />
        <a-table-column
          title="级别"
          data-index="companyLevel"
          key="companyLevel"
          :width="80"
          align="left"
        >
          <template slot-scope="t">
            <span v-if="t===null">--</span>
            <span v-else>{{ t }}</span>
          </template>
        </a-table-column>
        <a-table-column
          title="所在地"
          data-index="addressCode"
          key="addressCode"
          :width="320"
          align="left"
        >
          <template slot-scope="t">{{ getAddres(t) }}</template>
        </a-table-column>
        <a-table-column title="所属部门" data-index="deptName" key="deptName" :width="200" align="left">
          <template slot-scope="text">
            <span v-if="text===null">-</span>
            <span v-else>{{ text.replace('东莞市优赛科创企业管理咨询有限公司/', '') }}</span>
          </template>
        </a-table-column>
        <a-table-column title="操作" data-index="key" align="center" :width="240">
          <span slot-scope="text, record">
            <template v-if="record.companyId > 0">
              <template v-if="record.projectList.length === 0">无项目</template>
              <template v-else>
                <a @click="requestCustomer(record)" v-if="$auth('customer:my:jumpPage')">进入创新标准化管理系统</a>
              </template>
            </template>
            <template v-else>未开通平台</template>
          </span>
        </a-table-column>
        <a-table
          slot="expandedRowRender"
          slot-scope="record"
          size="small"
          rowKey="id"
          :dataSource="record.projectList"
          :pagination="false"
          :showHeader="false"
        >
          <a-table-column data-index="productName" align="left" :width="300" />
          <a-table-column data-index="year" align="center" :width="60" />
          <a-table-column data-index="techRealName" align="left" :width="120" />
          <a-table-column data-index="financeRealName" align="left" />
        </a-table>
      </s-table>
    </a-spin>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { DeptSelect, SearchSelect, YearSelect } from '@/components/Selects'
import { mapState } from 'vuex'

export default {
  name: 'MyCustomer',
  components: {
    STable,
    SearchSelect,
    YearSelect,
    DeptSelect
  },
  data () {
    return {
      spinning: false,
      companyName: undefined,
      queryParams: {
        companyName: undefined,
        deptId: undefined,
        tIds: [],
        fIds: [],
        productName: undefined,
        companyLevel: undefined
      },
      addressCode: {},
      getData: parameter => {
        const params = {
          companyName: this.queryParams.companyName,
          deptId: this.queryParams.deptId,
          year: this.queryParams.year,
          productName: this.queryParams.productName,
          companyLevel: this.queryParams.companyLevel
        }
        if (this.queryParams.tIds) {
          params.tIds = this.queryParams.tIds.map(a => a.id)
        }
        if (this.queryParams.fIds) {
          params.fIds = this.queryParams.fIds.map(a => a.id)
        }
        return this.$http.get('/customer/getMyCustomerList', { params: Object.assign(parameter, { ...params }) })
          .then(res => {
            if (res.success && res.data) {
              return res.data
            }
          })
      }

    }
  },
  computed: {
    ...mapState({
      companyLevelOptions: state => state.enums.companyLevelOptions
    })
  },
  created () {
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
  },
  methods: {
    search () {
      this.$refs.table.refresh(true)
    },
    requestCustomer (record) {
      this.$companyJump(this, { id: record.id, companyId: record.companyId })
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
    }
  }
}
</script>
