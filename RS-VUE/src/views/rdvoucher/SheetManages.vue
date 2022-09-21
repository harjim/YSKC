<template>
  <a-card :bodyStyle="{ height: '100%', overflow: 'auto'}" style="height: 100%;">
    <span v-if="$auth('rdvoucher:sheetManages:search')">
      <a-form layout="inline">
        <a-form-item label="凭证号">
          <a-input v-model="queryParams.voucherNo" placeholder="请输入凭证号"></a-input>
        </a-form-item>
        <a-form-item label="月份" >
          <a-month-picker
            format="YYYY-MM"
            v-model="queryParams.voucherMonth"
            placeholder="请选择月份"/>
        </a-form-item>
        <a-form-item label="费用类型">
          <a-select v-model="queryParams.rdType" :allowClear="true" :filter-option="filterOption" placeholder="请选择费用类型" style="width: 174px;">
            <a-select-option v-for="item in costTypes" :key="item.type" :title="item.title" :text="item.title">{{ item.title }}</a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="摘要">
          <a-input v-model="queryParams.summary" placeholder="请输入摘要"></a-input>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" style="margin-right:10px;" @click="searchData" v-if="$auth('rdvoucher:sheetManages:search')">查询</a-button>
        </a-form-item>
      </a-form>
      <div id="scrollContent">
        <a-spin tip="请稍后..." :spinning="spinning" style="height: 100%">
          <div id="scrollContent">
            <ystable
              ref="table"
              border="full"
              queryUrl="/voucher/getList"
              :params="queryParams"
              max-height="100%"
              highlight-hover-row
              show-overflow
              resizable
              auto-resize
              @checkbox-all="selectCheckBoxChange"
              @checkbox-change="selectCheckBoxChange"
              :toolbar="{ refresh: {query: searchData}, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
            >
              <template v-slot:toolbar_buttons>
                <a-button
                  type="primary"
                  style="margin-right: 10px;"
                  @click="$refs.addseehtModal.add()"
                  v-if="$auth('rdvoucher:sheetManages:add')"
                >添加</a-button>
                <a-button
                  type="primary"
                  style="margin-right: 10px;"
                  :disabled="selectedRowKeys.length <= 0"
                  @click="$refs.relatedVoucherModal.show(selectedRowKeys,currentYear)"
                  v-if="$auth('rdvoucher:SheetManages:related')"
                >关联项目</a-button>
                <a-button
                  type="primary"
                  style="margin-right: 10px;"
                  @click="openUploadModal"
                  v-if="$auth('rdvoucher:sheetManages:import')"
                >导入</a-button>
              </template>
              <vxe-table-column
                type="checkbox"
                width="60"
                align="center"
                header-align="center"
                fixed="left"
              ></vxe-table-column>
              <vxe-table-column
                title="凭证号"
                field="voucherNo"
                width="150"
                align="left"
                header-align="center"
                fixed="left"
                remoteSort
              ></vxe-table-column>
              <vxe-table-column
                title="凭证日期"
                field="voucherDate"
                width="150"
                align="center"
                header-align="center"
                remoteSort
              >
                <template v-slot=" { row }">
                  {{ row.voucherDate || DayFormat }}
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="金额"
                field="amount"
                width="120"
                align="right"
                header-align="center"
                remoteSort
              >
              </vxe-table-column>
              <vxe-table-column
                title="项目"
                field="projectRds"
                min-width="250"
                align="left"
                header-align="center"
              ></vxe-table-column>
              <vxe-table-column
                title="费用类型"
                field="rdType"
                width="150"
                align="left"
                header-align="center"
              >
                <template v-slot="{ row }">
                  {{ findCostName(row.rdType) }}
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="摘要"
                field="summary"
                width="200"
                align="left"
                header-align="center"
                remoteSort
              ></vxe-table-column>
              <vxe-table-column title="操作" width="120" align="center" header-align="center" fixed="right">
                <template v-slot="{ row }">
                  <span >
                    <a @click="$refs.addseehtModal.edit(row)" v-if="$auth('rdvoucher:sheetManages:edit')">编辑</a>
                  </span>
                  <span >
                    <a-divider
                      type="vertical"
                      v-if="$auth('rdvoucher:sheetManages:delete') && $auth('rdvoucher:sheetManages:edit') "/>
                    <a-popconfirm title="是否确定删除?" @confirm="handleDel(row)" v-if="$auth('rdvoucher:sheetManages:delete')">
                      <a>删除</a>
                    </a-popconfirm>
                  </span>
                </template>
              </vxe-table-column>
            </ystable>
          </div></a-spin>
      </div>
      <add-seeht-modal ref="addseehtModal" @ok="refreshTable" :year="currentYear"></add-seeht-modal>
      <related-voucher-modal ref="relatedVoucherModal" :year="currentYear" @ok="refreshTable"></related-voucher-modal>
      <upload-modal
        :showProgress="true"
        :sampleData="sampleData"
        paramKey="tableField"
        title="导入凭证"
        templateName="凭证列表模板"
        ref="uploadModal"
        action="/doc/voucher/importVoucher"
        @onSuccess="success"
        @error="error"
      />
    </span>
  </a-card>
</template>

<script>
import { PageView } from '@/layouts'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
import ystable from '@/components/Table/ystable'
import AddSeehtModal from './modules/AddSeehtModal'
import relatedVoucherModal from './modules/relatedVoucherModal'
import { UploadModal } from '@/components'
export default {
  mixins: [yearMiXin],
  name: 'Sheets',
  components: {
    PageView,
    ystable,
    AddSeehtModal,
    relatedVoucherModal,
    UploadModal
  },
  data () {
    return {
      spinning: false,
      selectedRowKeys: [],
      errorMsg: '',
      costTypes: this.$getCostTypes(),
      queryParams: {},
      tableField: {
        tableId: 'SheetTable',
        fieldTitleObject: {
          voucherNo: { title: '凭证号', required: true, defaultTitle: '凭证号', importField: true },
          voucherDate: { title: '日期', required: true, defaultTitle: '日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          amount: { title: '金额', required: true, defaultTitle: '金额', importField: true },
          projectRds: { title: '项目', required: false, defaultTitle: '项目', importField: true },
          rdType: { title: '费用类型', required: false, defaultTitle: '费用类型', importField: true },
          summary: { title: '摘要', required: true, defaultTitle: '摘要', importField: true }
        }
      },
      sampleData: [{
        voucherDate: '格式："年-月-日"，例如：2020-7-15',
        rdType: '工资/五险一金/设备折旧/仪器折旧/材料/动力/燃料/试制/修理/检测/设计/摊销/差旅费/其他',
        projectRds: '2019RD01,2020RD02,2020RD03...'
      }]
    }
  },
  created () {
  },
  computed: {

  },
  methods: {
    moment,
    searchData () {
      if (this.queryParams.voucherMonth) {
        this.queryParams.voucherMonth = this.queryParams.voucherMonth.startOf('month')
      }
      this.$refs.table.refresh(true)
    },
    openUploadModal () {
      this.$refs.uploadModal.show(this.tableField)
    },
    refreshTable (isTrue, isRefreshTable) {
      if (isTrue) {
        this.$message.success('操作成功')
      }
      this.$refs.table.refresh(isRefreshTable)
    },
    findCostName (rdType) {
      const index = this.costTypes.findIndex((item) => {
        return item.type === rdType * 1
      })
      if (index !== -1) {
        return this.costTypes[index].title
      }
      return ''
    },
    handleDel (record) {
      if (!record.id) { return }
      this.$http.get('/voucher/delVoucher', { params: { id: record.id } }).then((res) => {
        if (res.success && res.data) {
          this.$message.success('操作成功')
          this.$refs.table.refresh(false)
        } else {
          this.$message.error(res.errorMessage)
        }
      })
    },
    success (fileName, resData) {
      this.$message.success(fileName + '导入成功')
      this.$refs.table.refresh(true)
    },
    error (fileName, msg) {
      this.$message.error(`${fileName}导入失败${msg ? ':' + msg : ''}`)
    },
    selectCheckBoxChange ({ checked, records }) {
      this.selectedRowKeys = records.map((item) => { return item.id })
    },
    filterOption (input, option) {
      return (
        option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
      )
    }
  }
}
</script>
<style lang="less" scoped>
.menu-style {
  border: '0';
  width: 'auto';
  padding-top: 12px;
}
#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
#scrollContent {
  // border: 1px solid #e3e3e3;
  // border-radius: 8px;
  height: calc(100% - 40px);
  overflow: auto;
}
#scrollContent /deep/ .ant-spin-container {
  height: 100%;
}
</style>
