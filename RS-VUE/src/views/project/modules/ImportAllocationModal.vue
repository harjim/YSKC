<!--
 * @Author: your name
 * @Date: 2022-04-14 16:10:14
 * @LastEditors: lzh
 * @LastEditTime: 2022-04-19 16:37:19
 * @Description: 小程序计划工时-计划-导入分配
 * @FilePath: \RS-VUE\src\views\project\modules\ImportAllocationModal.vue
-->
<template>
  <UploadModal v-bind="$attrs" ref="uploadModal" :get-additional-event="getAdditionalEvent" @onSuccess="onSuccess">
    <template>
      <div class="table-wrapper">
        <a-tabs type="card" :defaultActiveKey="0">
          <a-tab-pane :tab="tabName.label" v-for="(tabName, tabIndex) in $getEnums('rdEmployeeEnum')" :key="tabIndex">
            <vxe-grid
              size="mini"
              show-overflow
              :data="tableDatas[tabIndex]"
              :edit-config="{trigger: 'click', mode: 'cell', activeMethod: activeMethod, showIcon: false}"
              @edit-actived="editActived"
              @edit-closed="editClosed"
              height="500px"
              ref="`xTable"
              :key="`xTable${tabIndex}`"
              :toolbar="{ perfect: true, slots: { buttons: 'toolbar_buttons' } }"
            >
              <template v-slot:toolbar_buttons>
                <div style="margin-left: 8px">{{ tabName.label }}：<a-input-number v-model="etypeRatios[tabIndex]" :max="100" /> %</div>
              </template>
              <vxe-table-column field="number" title="RD个数" :width="28" align="center" fixed="left">
                <template slot-scope="{rowIndex}">{{ rowIndex + 1 }}</template>
              </vxe-table-column>
              <vxe-table-column title="占比%" align="center">
                <vxe-table-column
                  align="center"
                  v-for="i in 30"
                  :key="i"
                  :field="'column' + i"
                  :min-width="50"
                  :edit-render="{autofocus: '.vxe-input--inner'}">
                  <template #header>
                    <span>{{ i }}</span>
                  </template>
                  <template v-slot="{ row }">{{ row[`column${i}`] }}</template>
                  <template v-slot:edit="{ row, columnIndex, rowIndex }">
                    <span v-if="columnIndex > rowIndex">{{ row[`column${i}`] }}</span>
                    <vxe-input
                      v-else
                      type="number"
                      v-model="row[`column${i}`]"
                      :key="`${rowIndex}-${columnIndex}`"
                      :min="0"
                      :max="row.max"
                    />
                  </template>
                </vxe-table-column>
              </vxe-table-column>
            </vxe-grid>
          </a-tab-pane>
        </a-tabs>
      </div>
    </template>
  </UploadModal>
</template>

<script>
import { UploadModal } from '@/components'
export default {
  components: {
    UploadModal
  },
  data () {
    // 初始30个RD,默认所有人员通用此配置
    const defaultConfig = [
      [100], [60, 40], [50, 30, 20], [40, 30, 20, 10], [35, 25, 15, 10, 15],
      [25, 20, 15, 12, 13, 15], [20, 15, 12, 13, 10, 15, 8], [16, 15, 12, 13, 10, 15, 8, 7],
      [14, 11, 12, 13, 10, 15, 8, 7, 4], [14, 11, 12, 13, 10, 5, 8, 7, 4, 6], [13, 11, 12, 9, 10, 5, 8, 7, 4, 6, 11],
      [13, 10, 9, 8, 10, 5, 8, 7, 4, 6, 11, 9], [10, 8, 13, 9, 6, 5, 4, 6, 8, 9, 5, 12, 5],
      [7, 8, 10, 9, 6, 5, 4, 6, 8, 9, 5, 12, 5, 6], [7, 8, 5, 9, 6, 5, 4, 6, 8, 9, 5, 7, 5, 6, 10],
      [7, 8, 5, 4, 6, 5, 4, 6, 8, 9, 5, 7, 5, 6, 7, 8], [2, 3, 4, 6, 6, 5, 4, 6, 8, 9, 5, 7, 5, 6, 7, 8, 9],
      [2, 3, 4, 6, 6, 5, 4, 6, 3, 9, 5, 7, 5, 6, 7, 6, 5, 11], [2, 3, 4, 6, 6, 5, 4, 6, 3, 4, 5, 7, 5, 6, 7, 6, 5, 7, 9],
      [4, 3, 4, 6, 6, 5, 4, 6, 3, 4, 5, 7, 5, 2, 7, 6, 5, 7, 5, 6], [4, 3, 4, 6, 6, 5, 4, 2, 3, 4, 5, 7, 5, 2, 7, 6, 5, 7, 5, 6, 4],
      [4, 3, 4, 6, 6, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 12],
      [4, 3, 4, 6, 6, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 5, 7],
      [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 5, 7, 9],
      [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 2, 3, 8, 8],
      [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 2, 3, 7, 3, 6],
      [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 2, 3, 7, 3, 2, 4],
      [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 2, 3, 3, 3, 2, 4, 4],
      [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 3, 5, 4, 2, 6, 4, 2, 3, 3, 3, 2, 4, 4, 6],
      [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 3, 5, 4, 2, 6, 4, 2, 3, 3, 3, 2, 4, 4, 2, 4]
    ]
    return {
      defaultConfig,
      tableDatas: [],
      etypeRatios: [80, 65, 35],
      defaultTableDatas: []
    }
  },
  methods: {
    initDefaultTableData () {
      if (this.defaultTableDatas.length > 0) return
      const tData = this.defaultConfig.map((list) => {
        let params = {}
        list.forEach((item, index) => {
          params = { ...params, [`column${index + 1}`]: item }
        })
        return params
      })
      this.defaultTableDatas = new Array(3).fill(tData)
    },
    show (tableField) {
      this.initDefaultTableData()
      this.tableDatas = this.$deepClone(this.defaultTableDatas)
      this.etypeRatios = [80, 65, 35]
      this.$refs['uploadModal'].show(tableField)
    },
    activeMethod ({ rowIndex, columnIndex }) {
      return columnIndex <= rowIndex
    },
    editActived ({ row, columnIndex, $columnIndex }) {
      let total = 0
      for (const key in row) {
        if (Object.hasOwnProperty.call(row, key)) {
          if (key.startsWith('column')) {
            total += Number(row[key]) || 0
          }
        }
      }
      row.max = 100 + Number(row[`column${columnIndex + 1}`]) - total
    },
    editClosed ({ row, columnIndex, $columnIndex }) {
      if (row[`column${columnIndex + 1}`] === '') {
        row[`column${columnIndex + 1}`] = 0
      }
    },
    getAdditionalEvent () {
      const tableConfig = {}
      this.tableDatas.forEach((array, index) => {
        const configs = []
        array.forEach((item, i) => {
          const tempArr = []
          Object.keys(item).forEach(key => {
            if (key.startsWith('column')) {
              tempArr.push(item[key])
            }
          })
          configs.push(tempArr)
        })
        tableConfig[index + 1] = {
          configs,
          etypeRatio: this.etypeRatios[index]
        }
      })
      return {
        config: JSON.stringify(tableConfig),
        type: 0
      }
    },
    onSuccess (name, data) {
      this.$emit('onSuccess', name, data)
    }
  }
}
</script>

<style lang="less" scoped>
.table-wrapper {
  margin-top: 10px;
  /deep/ .ant-tabs {
    .ant-tabs-bar {
      margin-bottom: 0;
    }
    .vxe-table {
      .vxe-cell {
        padding: 0;
      }
    }
  }
}
</style>
