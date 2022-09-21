<template>
  <div style="padding-top:20px">
    <a-spin tip="请稍后..." :spinning="spinning">
      <vxe-grid
        ref="table"
        :data="tableData"
        border
        size="small"
        resizable
        auto-resize
        highlight-hover-row
        show-overflow
        show-header-overflow
        highlight-current-row
        :toolbar="{zoom: true,custom:true,refresh:true}"
        header-align="center"
      >
        <vxe-table-column type="seq" title="序号" width="60" align="center" header-align="center"></vxe-table-column>
        <vxe-table-column
          title="建设事项"
          field="docName"
          show-header-overflow
          show-overflow="tooltip"
          width="260"
          align="left"
        />
        <vxe-table-column
          title="研发活动"
          show-header-overflow
          show-overflow="tooltip"
          field="rdActivities"
          width="160"
          align="left"
        />
        <vxe-table-column
          title="负责岗位"
          show-header-overflow
          show-overflow="tooltip"
          field="operators"
          width="160"
          align="left"
        />
        <vxe-table-column
          title="附件"
          show-header-overflow
          show-overflow="tooltip"
          field="docList"
          align="left"
        >
          <template v-slot="{row}">
            <template v-if="row.docList">
              <span v-for="(file,index) in row.docList" :key="index" style="margin-right:10px;">
                <span>
                  {{ file.fileName }}
                </span>
                <!-- <a-tooltip style="cursor:pointer" placement="top" @click="preview(file)" v-if="control.preview">
                  <template slot="title">
                    <span>预览</span>
                  </template>
                  <a-icon type="eye" style="margin-left:5px" />
                </a-tooltip> -->
              </span>
            </template>
          </template>
        </vxe-table-column>
      </vxe-grid>
    </a-spin>
  </div>
</template>

<script>
export default {
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
      tableData: [],
      spinning: false
    }
  },
  created () {
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
      this.spinning = true
      this.$http.get('/companyRdSummary/getCompanyBuildList', { params: { year: this.year, companyId: this.companyId } }).then(res => {
        if (res.success && res.data) {
          this.tableData = res.data
        } else {
          this.$message.error(res.errorMessage || '加载失败')
          this.tableData = []
        }
      }).finally(() => {
        this.spinning = false
      })
    }
  }
}
</script>

<style>

</style>
