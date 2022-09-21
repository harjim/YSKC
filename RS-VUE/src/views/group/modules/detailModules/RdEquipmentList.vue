<template>
  <div style="padding-top:20px;">
    <a-form layout="inline" >
      <a-form-item label="设备名称">
        <a-input placeholder="请输入设备名称" v-model="queryParams.ename"></a-input>
      </a-form-item>
      <a-form-item label="资产代码">
        <a-input placeholder="请输入资产代码" v-model="queryParams.ecode"></a-input>
      </a-form-item>
      <a-form-item><a-button type="primary" @click="search()">查询</a-button></a-form-item>
    </a-form>
    <div style="height: calc(100% - 39px); overflow: auto;">
      <ystable
        border
        highlight-hover-row
        show-overflow
        show-header-overflow
        ref="table"
        size="small"
        queryUrl="/companyRdSummary/getCompanyRdEquipmentList"
        :params="queryParams"
        :toolbar="{zoom: true,custom:true,refresh:true}"
      >
        <vxe-table-column
          type="seq"
          title="序号"
          :width="50"
        />
        <vxe-table-column
          title="设备名称"
          remoteSort
          field="ename"
          :width="120"
        />
        <vxe-table-column
          title="资产代码"
          remoteSort
          field="ecode"
          :width="120"
        />
        <vxe-table-column
          title="研发部门"
          remoteSort
          field="rdDeptName"
          :width="120"
        >
          <template v-slot="{row}">{{ row.rdDeptName }}</template>
        </vxe-table-column>
        <vxe-table-column
          title="设备类型"
          field="etype"
          remoteSort
          :width="120"
        >
          <template v-slot="{row}">{{ row.etype ? $getEnums('equipmentEnum').find(elem => elem.value === row.etype ).label : '' }}</template>
        </vxe-table-column>
        <vxe-table-column
          title="RD"
          field="rds"
        />
      </ystable>
    </div>
  </div>
</template>

<script>
import ystable from '@/components/Table/ystable'
import { mapState } from 'vuex'
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
      queryParams: {},
      tableData: [],
      spinning: false
    }
  },
  created () {
    this.queryParams.year = this.year
    this.queryParams.companyId = this.companyId
  },
  swatch: {
    companyId () {
      this.search()
    },
    year () {
      this.search()
    }
  },
  computed: {
    ...mapState({
    })
  },
  methods: {
    search () {
      this.queryParams.year = this.year
      this.queryParams.companyId = this.companyId
      this.$refs.table.refresh(true)
    }
  }
}
</script>

<style>

</style>
