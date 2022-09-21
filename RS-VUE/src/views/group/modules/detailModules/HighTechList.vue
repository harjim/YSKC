<template>
  <a-spin :spinning="spinning" tip="请稍后...">
    <div style="padding-top:20px;">
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
            @click="loadData"
          >查询</a-button>
        </a-form-item>
      </a-form>
      <vxe-grid
        ref="table"
        show-footer
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
        :data="tableData"
        :toolbar="{ refresh: {query:loadData}, zoom: true, custom: true }"
      >
        <vxe-table-column
          type="seq"
          title="序号"
          width="50"
        />
        <vxe-table-column
          field="startYear"
          title="年份"
          width="80"
          align="center"
          header-align="center"
        >
        </vxe-table-column>
        <vxe-table-column
          field="hcode"
          title="高新技术代码"
          width="120"
          align="center"
          header-align="center"
        >
          <template #default="{row}">
            {{ row.hcode }}
          </template>
        </vxe-table-column>
        <vxe-table-column
          field="hname"
          title="高新技术名称"
          min-width="230"
          align="left"
          header-align="center"
        >
          <template #default="{row}">
            <span>{{ row. hname }}</span>
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
      </vxe-grid>
    </div>
  </a-spin>
</template>

<script>
import ystable from '@/components/Table/ystable'
export default {
  components: {
    ystable
  },
  props: {
    companyId: {
      type: Number,
      required: true
    },
    year: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      etypeArr: { '0': '普通', '30000': '设备', '30100': '仪器', '40001': '软件摊销' },
      queryParams: {},
      tableData: [],
      tecIndustryMap: {},
      spinning: false
    }
  },
  created () {
    this.$highTecIndustryTreeMap(this).then((res) => {
      this.tecIndustryMap = res
    })
    this.loadData()
  },
  swatch: {
    companyId () {
      this.loadData()
    },
    year () {
      this.loadData()
    }
  },
  methods: {
    loadData () {
      this.queryParams.year = this.year
      this.queryParams.companyId = this.companyId
      this.spinning = true
      this.$http.get('/companyRdSummary/getCompanyHighTechList', { params: this.queryParams }).then(res => {
        if (res.success && res.data) {
          this.tableData = res.data
        } else {
          this.$messag.error(res.errorMessage || '加载失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    renderTecIndustry (tecIndustry) {
      if (!tecIndustry) { return '' }
      const ary = tecIndustry.split(',')
      const reslut = []
      ary.forEach((item) => { reslut.push(this.tecIndustryMap[item]) })
      return reslut.join('-')
    }
  }
}
</script>

<style>

</style>
