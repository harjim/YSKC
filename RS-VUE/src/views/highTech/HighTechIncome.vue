<template>
  <a-card class="contentPage" :bodyStyle="{height: '100%', overflow: 'auto'}">
    <a-spin
      tip="请稍后..."
      :spinning="spinning"
    >
      <div>
        <a-form
          layout="inline"
        >
          <a-form-item label="凭证号">
            <a-input
              style="width:165px;"
              v-model="queryParams.voucherNo"
              placeholder="请输入凭证号"
            />
          </a-form-item>
          <a-form-item label="产品名称">
            <a-input
              style="width:165px;"
              v-model="queryParams.productName"
              placeholder="请输入产品名称"
            />
          </a-form-item>
          <a-form-item label="客户名称">
            <a-input
              style="width:165px;"
              v-model="queryParams.client"
              placeholder="请输入客户名称"
            />
          </a-form-item>
          <a-form-item label="记账月份">
            <a-select
              style="width:165px;"
              v-model="queryParams.monthValue"
              placeholder="请选择月份"
              :allowClear="true"
              :options="monthOptions"
            ></a-select>
          </a-form-item>
          <a-form-item>
            <a-button
              type="primary"
              v-if="control.search"
              @click="search(true)"
            >查询</a-button>
          </a-form-item>
        </a-form>
      </div>
      <div>
        <ystable
          ref="table"
          queryUrl="/highTechIncome/getList"
          :params="getParams()"
          @completed="completed()"
          @checkbox-change="selectChange"
          @checkbox-all="selectChange"
          :toolbar="{ refresh: true, zoom: true, custom: true }"
          highlight-hover-row
          show-overflow
          resizable
          auto-resize
        >
          <template v-slot:buttons>
            <a-button
              type="primary"
              v-if="control.add"
              @click="$refs.incomeModal.add()"
              style="margin-right:10px"
            >添加</a-button>
            <a-button
              type="primary"
              v-if="control.import"
              @click="$refs.uploadModal.show(tableField)"
              style="margin-right:10px"
            >导入</a-button>
            <a-button
              type="primary"
              v-if="control.export"
              @click="exportData"
              style="margin-right:10px"
            >导出</a-button>
            <a-button
              type="primary"
              v-if="control.bind"
              style="margin-right:10px"
              :disabled="selectedRowKeys.length <= 0"
              @click="$refs.bindHighTech.bind(selectedRowKeys,currentYear)"
            >关联高品</a-button>
            <a-button
              type="primary"
              v-if="control.unbind"
              :disabled="selectedBindKeys.length <= 0"
              style="margin-right:10px"
              @click="unbind()"
            >解除关联高品</a-button>
            <a-button
              type="primary"
              v-if="control.del"
              :disabled="selectedRowKeys.length <= 0"
              @click="delList"
              style="margin-right:10px"
            >删除</a-button>
          </template>
          <vxe-table-column
            type="checkbox"
            width="40"
          />
          <vxe-table-column
            title="记帐日期"
            field="bookDate"
            width="110"
            remoteSort
            align="center"
          ></vxe-table-column>
          <vxe-table-column
            title="凭证号"
            field="voucherNo"
            width="120"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="品名"
            field="productName"
            min-width="140"
            remoteSort
            align="left"
          ></vxe-table-column>
          <vxe-table-column
            title="代码"
            field="hcode"
            width="120"
            remoteSort
            align="left"
          />
          <vxe-table-column
            title="高新技术产品名称"
            field="hname"
            min-width="140"
            remoteSort
            align="left"
          />
          <vxe-table-column
            title="数量"
            field="quantity"
            width="120"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="单价(元)"
            field="unitPrice"
            width="120"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="高新收入(元)"
            field="income"
            width="140"
            remoteSort
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="对应客户"
            field="client"
            min-width="140"
            remoteSort
          ></vxe-table-column>
          <vxe-table-column
            title="操作"
            field="action"
            width="120"
            align="center"
            fixed="right"
          >
            <template v-slot="{ row }">
              <span v-if="control.edit">
                <a
                  href="javascript:;"
                  @click="$refs.incomeModal.edit(row)"
                >编辑</a>
              </span>
              <span v-if="control.del">
                <a-divider
                  type="vertical"
                  v-if="control.edit"
                />
                <a-popconfirm
                  title="是否确定删除?"
                  @confirm="handleDel(row)"
                >
                  <a>删除</a>
                </a-popconfirm>
              </span>
            </template>
          </vxe-table-column>
        </ystable>
      </div>
      <high-tech-income-modal ref="incomeModal" @ok="search"/>
      <upload-modal
        :sampleData="sampleData"
        :showProgress="true"
        paramKey="name"
        title="导入高品收入"
        ref="uploadModal"
        action="/doc/highTechIncome/importIncome"
        templateName="高品收入模板"
        @onSuccess="success"
      />
      <bind-high-tech-modal ref="bindHighTech" @ok="search(false)" />
    </a-spin>
  </a-card>
</template>
<script>
import { UploadModal } from '@/components'
import HighTechIncomeModal from './modules/HighTechIncomeModal'
import BindHighTechModal from './modules/BindHighTechModal'
import ystable from '@/components/Table/ystable'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
export default {
  name: 'HighTechIncome',
  mixins: [yearMiXin],
  components: {
    HighTechIncomeModal,
    ystable,
    UploadModal,
    BindHighTechModal
  },
  data () {
    return {
      selectedRowKeys: [],
      selectedBindKeys: [],
      control: {
        search: this.$auth('highTech:highTechIncome:search'),
        add: this.$auth('highTech:highTechIncome:add'),
        edit: this.$auth('highTech:highTechIncome:edit'),
        del: this.$auth('highTech:highTechIncome:del'),
        import: this.$auth('highTech:highTechIncome:import'),
        export: this.$auth('highTech:highTechIncome:export'),
        bind: this.$auth('highTech:highTechIncome:bind'),
        unbind: this.$auth('highTech:highTechIncome:unbind')
      },
      tableField: {
        tableId: 'highTechIncomeTable',
        fieldTitleObject: {
          bookDate: { title: '记帐日期', defaultTitle: '记帐日期', importField: true, sampleValue: '格式："年-月-日"，例如：2019-10-11' },
          voucherNo: { title: '凭证号', required: true, defaultTitle: '凭证号', importField: true },
          productName: { title: '品名', required: true, defaultTitle: '品名', importField: true },
          quantity: { title: '数量', required: true, defaultTitle: '数量', importField: true },
          unitPrice: { title: '单价(元)', required: true, defaultTitle: '单价(元)', importField: true },
          income: { title: '高新收入(元)', required: true, defaultTitle: '高新收入(元)', importField: true },
          client: { title: '对应客户', required: true, defaultTitle: '对应客户', importField: true }
        }
      },
      sampleData: [
        {
          bookDate: '格式："年-月-日"，例如：2019-10-11',
          voucherNo: 'XX-2020XX-XX',
          productName: '产品名称',
          unitPrice: '数字格式，例如：300000',
          quantity: '数字格式，例如：300000',
          income: '数字格式，例如：300000',
          client: 'xxxxxx公司'
        }
      ],
      queryParams: {},
      spinning: false
    }
  },
  methods: {
    completed () {
      this.selectedRowKeys = []
      this.selectedBindKeys = []
    },
    search (refresh) {
      this.selectedRowKeys = []
      this.selectedBindKeys = []
      this.initParam()
      this.$refs.table.refresh(refresh)
    },
    success (fileName, resData) {
      if (resData) {
        this.$confirm({
          title: '导入信息',
          content: resData,
          onOk () {

          },
          onCancel () { }
        })
      } else {
        this.$message.success(fileName + '导入成功')
      }
      this.search(true)
    },
    getParams () {
      this.initParam()
      return this.queryParams
    },
    moment,
    initParam () {
      this.queryParams.year = this.currentYear
      if (!this.queryParams.monthValue || this.queryParams.monthValue === '-1') {
        this.queryParams.month = undefined
      } else {
        this.queryParams.month = moment([this.currentYear, this.queryParams.monthValue, 1, 0, 0, 0, 0])
      }
    },
    handleDel (row) {
      this.executorDelete([row.id])
    },
    delList () {
      if (!this.selectedRowKeys.length) {
        return
      }
      const self = this
      this.$confirm({
        title: '您确定要删除选中的高品收入吗?',
        onOk () {
          self.executorDelete(self.selectedRowKeys)
        },
        onCancel () {
        }
      })
    },
    executorDelete (ids) {
      this.spinning = true
      this.$http.post('/highTechIncome/deleteList', { ids })
        .then(res => {
          if (res.success) {
            this.$message.success('删除成功')
            this.search(false)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
          }
          this.spinning = false
        })
    },
    selectChange ({ records }) {
      const rowKeys = []
      const bindKeys = []
      records.forEach(a => {
        if (a.highTechId) {
          bindKeys.push(a.id)
        }
        rowKeys.push(a.id)
      })
      this.selectedRowKeys = rowKeys
      this.selectedBindKeys = bindKeys
    },
    exportData () {
      this.spinning = true
      this.$exportData('/highTechIncome/exportIncome', this.initParam(), '高品收入列表数据.xlsx', this.$message).then(res => {
        this.spinning = false
      })
    },
    unbind () {
      this.spinning = true
      this.$http.post('highTechIncome/unbindHighTech', { ids: this.selectedBindKeys }).then(res => {
        if (res.success && res.data) {
          this.$message.success('解除关联成功')
          this.search(false)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '解除关联失败')
        }
        this.spinning = false
      })
    }
  }
}
</script>
<style lang="less" scoped>
.contentPage {
  height: 100%;
  overflow: auto;
  position: relative;
}
</style>
