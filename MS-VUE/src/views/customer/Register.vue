<template>
  <a-card :bordered="false">
    <a-form layout="inline">
      <a-form-item label="客户名称">
        <a-input v-model="companyName" placeholder="请输入客户名称" />
      </a-form-item>
      <a-form-item label="联系人">
        <a-input v-model="linkMan" placeholder="请输入联系人" />
      </a-form-item>
      <a-form-item>
        <a-button
          v-if="$auth('customer:register:search')"
          type="primary"
          @click="$refs.table.refresh(true)"
        >查询</a-button>
      </a-form-item>
      <!-- <span style="padding-left:20px;padding-top:5px;">
        <a-button type="primary" icon="plus" @click="$refs.modal.add()">申报立项</a-button>
      </span>-->
    </a-form>

    <ystable
      rowId="id"
      ref="table"
      queryUrl="/company/queryCompanyList"
      :params="{ companyName: this.companyName, linkMan: this.linkMan }"
      :toolbar="{
        custom: true,
        zoom:true,
        refresh:true
      }"
    >
      <vxe-table-column type="seq" width="50" fixed="left" title="序号" />
      <vxe-table-column
        title="客户名称"
        field="companyName"
        key="companyName"
        :width="260"
        remoteSort
        show-overflow="tooltip"
        show-header-overflow
        fixed="left"
      />
      <vxe-table-column
        title="公司地址"
        field="companyAddress"
        key="companyAddress"
        :width="190"
        show-overflow="tooltip"
        show-header-overflow
      />
      <vxe-table-column title="联系人" field="linkMan" key="linkMan" :width="80" show-header-overflow />
      <vxe-table-column
        title="联系电话"
        field="linkTel"
        key="linkTel"
        :width="110"
        show-header-overflow
      />
      <vxe-table-column
        title="所属行业"
        field="industryCode"
        key="industryCode"
        show-overflow="tooltip"
        show-header-overflow
        :width="180"
      >
        <template slot-scope="{row}">{{ industryCodeDataArr(row.industryCode) }}</template>
      </vxe-table-column>
      <vxe-table-column
        title="主账户"
        field="userName"
        key="userName"
        :width="100"
        show-overflow="tooltip"
        show-header-overflow
      />
      <vxe-table-column
        title="来源"
        field="from"
        key="from"
        :width="100"
        show-overflow="tooltip"
        show-header-overflow
      >
        <template slot-scope="{row}">{{ from[row.from] }}</template>
      </vxe-table-column>
      <vxe-table-column
        title="创建时间"
        field="createTime"
        key="createTime"
        show-overflow="tooltip"
        show-header-overflow
        :width="160"
        remoteSort
      />
      <vxe-table-column
        title="最后更新时间"
        field="lastUpdateTime"
        key="lastUpdateTime"
        show-header-overflow
        show-overflow="tooltip"
        :width="160"
        remoteSort
      />
      <vxe-table-column
        title="审核状态"
        field="status"
        key="status"
        show-overflow="tooltip"
        :width="100"
        remoteSort
      >
        <template slot-scope="{row}">{{ statusData[row.status] }}</template>
      </vxe-table-column>
      <vxe-table-column title="操作" field="key" :width="100" fixed="right">
        <template slot-scope="{row}" v-if="$auth('customer:register:examine')">
          <a @click="handleEdit(row)" v-if="row.status===0">审核</a>
          <!-- <a-divider type="vertical" />
          <a-popconfirm title="是否确定删除" @confirm="handleDel(record)">
            <a>删除</a>
          </a-popconfirm>-->
        </template>
      </vxe-table-column>
    </ystable>
    <register-modal ref="modal" @ok="handleOk"></register-modal>
  </a-card>
</template>

<script>
import moment from 'moment'
import ystable from '@/components/Table/ystable'
import RegisterModal from './modules/RegisterModal'

export default {
  name: 'ReportingManage',
  components: {
    ystable,
    RegisterModal
  },
  data () {
    return {
      companyName: '',
      linkMan: '',
      paramData: { name: 'aaaaa' },
      menuTree: [],
      menuId: 0,
      pagination: [],
      getData: parameter => {
        return this.$http.get('/company/queryCompanyList', { params: Object.assign(parameter) })
          .then(res => {
            return res.data
          })
      },
      // 查询参数
      queryParam: {},
      permissionList: [],
      visible: false,
      title: '',
      form: this.$form.createForm(this, { scroll: {} }),
      industryCodeData: {
        'A': '农、林、牧、渔业',
        'B': '采矿业',
        'C': '制造业',
        'D': '电力、热力、燃气及水的生产和供应业',
        'E': '建筑业',
        'F': '批发和零售业',
        'G': '交通运输、仓储和邮政业',
        'H': '住宿和餐饮业',
        'I': '信息传输、软件和信息技术服务业',
        'J': '金融业',
        'K': '房地产业',
        'L': '租赁和商务服务业',
        'M': '科学研究和技术服务业',
        'N': '水利、环境和公共设施管理业',
        'O': '居民服务、修理和其他服务业',
        'P': '教育',
        'Q': '卫生和社会工作',
        'R': '文化、体育和娱乐业',
        'S': '公共管理、社会保障和社会组织',
        'T': '国际组织'
      },
      statusData: {
        /// 状态，0:未审核，1：审核通过, 2： 审核不通过, 3:审核失败
        '0': '未审核',
        '1': '审核通过',
        '2': '审核不通过',
        '3': '客户已存在'
      },
      from: {
        /// 0:用户注册，1：氚云，2：新增
        '0': '用户注册',
        '1': '氚云',
        '2': '新增'
      }
    }
  },
  mounted () {
    // this.loading = true
    // this.$refs.table.refresh(true)
    // this.loadTree()
  },
  methods: {
    industryCodeDataArr (text) {
      if (text) {
        let industry = '| '
        const dataAdd = text.split(',')
        for (let i = 0; i < dataAdd.length; i++) {
        // const industryCode = text[i]
          industry += this.industryCodeData[dataAdd[i]] + ' | '
        }
        return industry
      }
      return ''
    },
    moment,
    openUploadModal () {
      this.$refs.uploadModal.show()
    },
    // handleDel (record) {
    //   this.$http.post('/report/del', { id: record.id }).then(res => {
    //     this.$message.success('删除成功')
    //   }).finally(res => {
    //     this.$refs.table.refresh(true)
    //   })
    // },
    handleEdit (record) {
      this.$refs.modal.edit(record)
    },
    handleOk (value) {
      if (value === 1) {
        this.$message.success('审核通过')
      } else if (value === 2) {
        this.$message.success('审核不通过')
      } else if (value === 3) {
        this.$message.info('客户名称已存在')
      }
      this.$refs.table.refresh(false)
    }
  }
}
</script>
