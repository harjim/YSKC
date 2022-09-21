<template>
  <a-modal
    style="top: 0px;"
    :width="950"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    :footer="null"
    @cancel="closeModal"
    :bodyStyle="{ maxHeight:'84vh',height: '84vh'}"
  >
    <vxe-grid
      id="/projectProgress/queryProject"
      ref="table"
      :data="projectTableData"
      highlight-hover-row
      show-overflow="title"
      show-header-overflow
      resizable
      auto-resize
      max-height="94%"
      :customConfig="{storage: { visible: true, resizable: true } }"
    >
      <template v-slot:top>
        <span style="padding-left: 10px;">
          项目数:
          <a style="font-weight: 600">{{ existProjectNum }}</a>
        </span>
        <span style="padding-left: 10px;">
          总预算:
          <a style="font-weight: 600">{{ budgetNum.toFixed(2) }}万元</a>
        </span>
      </template>
      <vxe-table-column title="RD" field="rdTitle" align="left" :width="100" />
      <vxe-table-column title="项目名称" field="pname" align="left" :min-width="200" />
      <vxe-table-column title="工艺段" field="processSection" align="left" :width="140" />
      <vxe-table-column title="项目负责人" field="ename" align="left" :width="120" />
      <vxe-table-column title="预算(万元)" field="budget" align="right" :width="120">
        <template v-slot="{ row }">
          <span v-if="row.budget!==null">{{ row.budget.toFixed(2) }}</span>
          <span v-else>--</span>
        </template>
      </vxe-table-column>
      <vxe-table-column title="项目周期" field="beginAndEndDate" align="center" :width="200">
        <template v-slot="{ row }">
          <span>{{ `${row.beginDate} ~ ${row.endDate}` }}</span>
        </template>
      </vxe-table-column>
    </vxe-grid>
  </a-modal>
</template>

<script>

export default {
  components: {
  },
  data () {
    return {
      title: '',
      visible: false,
      existProjectNum: 0,
      budgetNum: 0,
      projectTableData: []
    }
  },
  created () {
  },
  methods: {
    show (record) {
      if (record.productName) {
        this.title = '[' + record.companyName + '][' + record.productName + '][' + record.year + '年]项目列表'
      } else {
        this.title = '[' + record.companyName + '][' + record.year + '年]项目列表'
      }
      this.visible = true
      this.queryProject(record)
    },
    queryProject (record) {
      this.$http.get('/projectProgress/queryProject', { params: { companyId: record.companyId, year: record.year } })
        .then(res => {
          if (res.data) {
            this.existProjectNum = res.data.length
            this.budgetNum = 0
            for (let index = 0; index < res.data.length; index++) {
              if (res.data[index].budget !== null) {
                const element = res.data[index]
                this.budgetNum += element.budget
              }
            }
            this.projectTableData = res.data
            return res.data
          }
        })
    },
    closeModal () {
      this.visible = false
    }
  }
}
</script>

<style lang="less" scoped>
.ant-form-item-children .ant-input-number {
  width: 100%;
}
</style>
