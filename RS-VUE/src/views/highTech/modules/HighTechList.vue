<!--
 * @Author: ldx
 * @Date: 2021-05-27 17:20:17
 * @LastEditTime: 2022-01-27 10:52:21
 * @LastEditors: lzh
 * @Description: 高新技术列表
 * @FilePath: \RS-VUE\src\views\highTech\modules\HighTechList.vue
-->
<template>
  <section class="container">
    <a-form layout="inline">
      <a-form-item label="高新技术名称">
        <a-input v-model="queryParams.hname" placeholder="请输入高新技术名称"></a-input>
      </a-form-item>
      <a-form-item label="高新技术代码">
        <a-input v-model="queryParams.hcode" placeholder="请输入高新技术代码"></a-input>
      </a-form-item>
      <!-- <a-form-item label="领域">
        <a-input v-model="queryParams.tecIndustry" placeholder="请输入领域"></a-input>
      </a-form-item> -->
      <a-form-item style="margin-button: 0px;">
        <a-button
          type="primary"
          style="margin-right: 10px;"
          @click="handleGetHighTechLists"
        >查询</a-button>
      </a-form-item>
    </a-form>
    <div class="table_wrap">
      <vxe-grid
        ref="table"
        border="full"
        height="100%"
        show-footer
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        :span-method="spanMethod"
        :data="tableData"
        :footer-method="footerMethod"
        :footer-span-method="footerColspanMethod"
        :toolbar="{ refresh: {query:handleGetHighTechLists}, zoom: true, custom: true,slots: { buttons: 'toolbar_buttons' } }"
        @checkbox-change="selectChange"
        @checkbox-all="selectChange"
      >
        <template #toolbar_buttons>
          <a-button type="primary" style="margin-right:10px" @click="onAdd" v-if="$auth('highTech:highTechIndex:save')">添加</a-button>
          <a-button
            v-if="$auth('highTech:highTechIndex:del')"
            type="primary"
            :disabled="selectedRows.length <= 0"
            @click="delList"
            style="margin-right:10px"
          >删除</a-button>
        </template>
        <vxe-table-column
          type="checkbox"
          width="40"
          align="center"
          header-align="center"
          fixed="left"
        />
        <vxe-table-column
          type="seq"
          title="序号"
          align="center"
          header-align="center"
          width="60"
          fixed="left"
        />
        <vxe-table-column
          field="startYear"
          title="年份"
          width="80"
          align="center"
          header-align="center"
          fixed="left"
        >
        </vxe-table-column>
        <vxe-table-column
          field="hcode"
          title="高新技术代码"
          width="120"
          align="center"
          header-align="center"
          fixed="left"
        >
          <template #default="{row}">
            {{ row.hcode }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="hname"
          title="高新技术产品名称"
          min-width="230"
          align="left"
          header-align="center"
        >
          <template #default="{row}">
            <a v-if="$auth('highTech:highTechIndex:view')" @click="showDetail(row)" > {{ row. hname }}</a>
            <span v-else>{{ row. hname }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="tecIndustry"
          title="高新技术领域"
          width="220"
          align="left"
          header-align="center"
        >
          <template #default="{row}">
            {{ renderTecIndustry(row.tecIndustry) }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="total"
          title="合计(万元)"
          width="100"
          align="right"
          header-align="center"
        >
          <template #default="{row}">
            {{ row.total || '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="totalAmount"
          title="当期累计总收入(万元)"
          width="180"
          align="right"
          header-align="center"
        >
          <template #default="{row}">
            {{ row.totalAmount || '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="proportion"
          title="占比(%)"
          width="90"
          align="right"
          header-align="center"
        >
          <template #default="{row}">
            {{ row.proportion || '-' }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="操作"
          width="100"
          align="center"
          header-align="center"
        >
          <template #default="{row}">
            <a @click="onEdit(row)" v-if="$auth('highTech:highTechIndex:save')">编辑</a>
            <a-divider type="vertical" v-if="$auth('highTech:highTechIndex:save') && $auth('highTech:highTechIndex:del')"></a-divider>
            <a @click="onDel(row)" v-if="$auth('highTech:highTechIndex:del')">删除</a>
          </template>
        </vxe-table-column>
      </vxe-grid>
      <AddHighTechModal ref="addHighTechModal" @refresh="refresh"></AddHighTechModal>
    </div>
  </section>
</template>

<script>
import { ystable } from '@/components/index'
import { delHighTech, getList } from '@/api/highTech/highTech'
import AddHighTechModal from './AddHighTechModal'
import { mapGetters } from 'vuex'
import XEUtils from 'xe-utils'
export default {
  name: 'HighTechList',
  components: {
    ystable,
    AddHighTechModal
  },
  data () {
    return {
      queryParams: {
        hname: undefined,
        hcode: undefined,
        tecIndustry: undefined,
        year: undefined
      },
      selectedRows: [],
      tecIndustryMap: {},
      tableData: [
        { a: 'a', b: 'b' }
      ]
    }
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  watch: {
    currentYear: {
      handler (val) {
        this.resetDataVal()
        this.queryParams.year = val
        this.handleGetHighTechLists()
        // this.onSearch()
      }
    }
  },
  mounted () {
    this.$highTecIndustryTreeMap(this).then((res) => {
      this.tecIndustryMap = res
    })
    this.queryParams.year = this.currentYear
    this.handleGetHighTechLists(this.queryParams)
  },
  methods: {
    onSearch () {
      this.selectedRows = []
      this.handleGetHighTechLists()
    },
    onAdd () {
      this.$refs.addHighTechModal.show()
    },
    onEdit (record) {
      this.$refs.addHighTechModal.edit(record)
    },
    onDel (record) {
      this.$confirm({
        title: '您确定要删除吗?',
        okText: '确定',
        okType: 'danger',
        cancelText: '取消',
        onOk: () => {
          this.handlerDel([record])
        },
        onCancel () { }
      })
    },
    handlerDel (records) {
      delHighTech(records).then(response => {
        if (response.success && response.data) {
          this.$message.success('删除成功！')
          this.onSearch()
        } else {
          this.$message.error(response.errorMessage || '系统异常请联系管理员！')
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常请联系管理员！')
      })
    },
    showDetail (record, isShow = false) {
      this.$emit('showDetail', record, isShow)
    },
    refresh () {
      this.onSearch()
    },
    renderTecIndustry (tecIndustry) {
      if (!tecIndustry) { return '' }
      const ary = tecIndustry.split(',')
      const reslut = []
      ary.forEach((item) => { reslut.push(this.tecIndustryMap[item]) })
      return reslut.join('-')
    },
    selectChange ({ records }) {
      this.selectedRows = records
    },
    delList () {
      if (!this.selectedRows.length) {
        return
      }
      this.$confirm({
        title: '您确定要删除选中的高品吗?',
        onOk: () => {
          this.handlerDel(this.selectedRows)
        },
        onCancel () { }
      })
    },
    handleGetHighTechLists () {
      getList(this.queryParams).then(response => {
        if (response.success && response.data) {
          this.tableData = response.data
        } else {
          this.$messag.error(response.errorMessage || '系统异常请联系管理员！')
        }
      }).catch(error => {
        this.$messag.error(error.message || '系统异常请联系管理员！')
      }).finally(() => {
      })
    },
    resetDataVal () {
      this.tableData = []
      this.selectedRows = []
      Object.keys(this.queryParams).forEach(key => {
        this.queryParams[key] = undefined
      })
    },
    footerMethod ({ columns, data }) {
      let totalSum = 0
      let totalAmount = 0
      const footerData = [
        columns.map((column, _columnIndex) => {
          if (_columnIndex === 0) {
            return '合计'
          }
          if (_columnIndex === 6) {
            totalSum = XEUtils.sum(data, 'total')
            return totalSum
          }
          if (_columnIndex === 7) {
            totalAmount = XEUtils.mean(data, 'totalAmount')
            return totalAmount
          }
          if (_columnIndex === 8) {
            return (XEUtils.divide(totalSum, totalAmount) * 100).toFixed(2)
          }
          return null
        })
      ]
      return footerData
    },
    spanMethod ({ column, row, columnIndex, rowIndex, data }) {
      if (rowIndex === 0 && columnIndex === 7) {
        return { rowspan: data.length, colspan: 1 }
      }
      if (rowIndex !== 0 && columnIndex === 7) {
        return { rowspan: 0, colspan: 0 }
      }
      return { rowspan: 1, colspan: 1 }
    },
    footerColspanMethod ({ $rowIndex, $columnIndex }) {
      if ($columnIndex === 0) {
        return {
          rowspan: 1,
          colspan: 6
        }
      } else if ($columnIndex >= 1 && $columnIndex <= 5) {
        return {
          rowspan: 0,
          colspan: 0
        }
      }
      return 	{ rowspan: 1, colspan: 1 }
    }
    // mergeCells() {}
  }
}
</script>

<style lang="less" scoped>
.container {
  height: 100%;
  .table_wrap {
    height: calc(100% - 40px);
  }
}
</style>
