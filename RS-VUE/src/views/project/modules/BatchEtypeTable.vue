<template>
  <div v-if="tableData.length > 1">
    <vxe-table
      border
      size="small"
      :show-header="false"
      resizable
      :data="tableData">
      <vxe-table-column
        field="etype"
        title="人员类型"
        width="100"
        headerAlign="center"
        align="center"
      >
        <template v-slot="{row,rowIndex}">
          <span v-if="rowIndex===0">{{ row.etype }}</span>
          <span v-else>{{ row.rd }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        field="ratio"
        title="研发工时占比"
        headerAlign="center"
        :min-width="maxWidth"
        align="left"
      >
        <template v-slot="{row,rowIndex}">
          <template v-if="rowIndex === 0">
            <a-input-number
              :value="ratioConfig.etypeRatio"
              :min="0"
              :disabled="disabled"
              @change="v=>ratioChange(v,ratioConfig)"
              :max="100"
              :precision="2"
              size="small"
              title="总研发工时与总工时占比"
              style="width:100px;"/>
          </template>

          <template v-else>
            <a-input-number
              v-for="(v,index) in row.rdRatios"
              :key="index"
              :value="row.rdRatios[index]"
              :min="0"
              :disabled="disabled"
              title="与总研发工时占比"
              :max="getMaxV(row,index)"
              :precision="2"
              @change="v=>handleChange(v,row,index)"
              size="small"
              style="width:100px;margin-right:5px;"/>
          </template>
        </template>
      </vxe-table-column>
    </vxe-table>
  </div>
</template>
<script>
export default {
  props: {
    // 比例配置
    ratioConfig: {
      type: Object,
      default: () => {}
    },
    etypeName: {
      type: String,
      required: true
    },
    configLenth: {
      type: Number,
      required: true
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      tableData: [],
      maxWidth: 840
    }
  },
  watch: {
    ratioConfig: {
      immediate: true,
      handler () {
        this.loadData()
      }
    }
  },
  methods: {
    handleChange (v, row, index) {
      this.vChange(v, row.rdRatios, index, this.getMaxV(row, index))
    },
    ratioChange (v, row) {
      this.vChange(v, row, 'etypeRatio', 100)
    },
    vChange (v, row, field, total) {
      if (!isNaN(v)) {
        if (v > total) {
          v = total
        }
      } else {
        v = 0
      }
      row[field] = v
      this.$set(row, field, v)
    },
    getMaxV (row, index) {
      const currentV = row.rdRatios[index]
      let total = 0
      row.rdRatios.forEach(ratio => {
        if (ratio) {
          total += Number(ratio)
        }
      })
      total = 100 - total
      if (currentV) {
        total += Number(currentV)
      }
      return total
    },
    loadData () {
      const dataArr = []
      if (this.configLenth > 7) {
        this.maxWidth = 840 + (this.configLenth - 7) * 106
      }
      let configs = this.ratioConfig.configs
      if (!configs) {
        configs = []
      }
      dataArr.push({ etype: this.etypeName, ratio: this.ratioConfig.etypeRatio })
      for (let i = 0; i < this.configLenth; i++) {
        let item = configs[i]
        if (!item) {
          item = []
          for (let j = 0; j <= i; j++) {
            item[j] = undefined
          }
          configs[i] = item
        }
        dataArr.push({ rdRatios: item, rd: (i + 1) + '个RD' })
      }
      this.tableData = dataArr
    }
  }

}
</script>
