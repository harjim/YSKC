<!--
 * @Author: ldx
 * @Date: 2020-12-07 09:00:33
 * @LastEditTime: 2020-12-12 11:52:31
 * @LastEditors: ldx
 * @Description: 单位财务情况
 * @FilePath: \MS-VUE\src\views\customer\customerInformation\modules\FinaceCase.vue
-->
<template>
  <div>
    <!-- <a-divider orientation="left">
      <span class="mode-title">单位财务情况</span>
    </a-divider> -->
    <custom-title
      title="单位财务情况"
      @switchEdit="switchEdit"
      ref="title"
    ></custom-title>
    <a-spin
      :spinning="spinning"
      tip="请稍后..."
    >
      <div>
        <vxe-table
          border
          :data="tableData"
          header-align="center"
          highlight-current-row
          highlight-hover-row
          show-overflow="title"
          resizable
          auto-resize
        >
          <vxe-table-column
            title="序号"
            type="seq"
            width="50"
            align="right"
          ></vxe-table-column>
          <vxe-table-column
            title="项目类别"
            width="260"
            field="projectType"
          ></vxe-table-column>
          <vxe-table-column
            title="上年"
            width="100"
            align="center"
          >
            <vxe-table-column
              :title="`${yearObj.prevYear}年末`"
              align="right"
              header-align="center"
            >
              <template v-slot="{row}">
                <template v-if="isEdit">
                  <a-input-number
                    width="80"
                    :precision="2"
                    :max="$store.state.totalMax"
                    :value="row[yearObj.prevYear]"
                    @change="v=>handleChange(v,row,yearObj.prevYear)"
                  />
                </template>
                <template v-else>
                  {{ row[yearObj.prevYear] || row[yearObj.prevYear] === 0 ? row[yearObj.prevYear].toFixed(2) : '-' }}
                </template>
              </template>
            </vxe-table-column>
          </vxe-table-column>
          <vxe-table-column
            title="前年"
            width="100"
            align="center"
          >
            <vxe-table-column
              :title="`${yearObj.nextPrevYear}年末`"
              align="right"
              header-align="center"
            >
              <template v-slot="{row}">
                <template v-if="isEdit">
                  <a-input-number
                    width="80"
                    :precision="2"
                    :max="$store.state.totalMax"
                    :value="row[yearObj.nextPrevYear]"
                    @change="v=>handleChange(v,row,yearObj.nextPrevYear)"
                  />
                </template>
                <template v-else>
                  {{ row[yearObj.nextPrevYear] || row[yearObj.nextPrevYear]===0 ? row[yearObj.nextPrevYear].toFixed(2) : '-' }}
                </template>
              </template>
            </vxe-table-column>
          </vxe-table-column>
          <vxe-table-column
            title="大前年"
            width="100"
            align="center"
          >
            <vxe-table-column
              :title="`${yearObj.lastPrevYear}年末`"
              align="right"
              header-align="center"
            >
              <template v-slot="{row}">
                <template v-if="isEdit">
                  <a-input-number
                    width="80"
                    :precision="2"
                    :max="$store.state.totalMax"
                    :value="row[yearObj.lastPrevYear]"
                    @change="v=>handleChange(v,row,yearObj.lastPrevYear)"
                  />
                </template>
                <template v-else>
                  {{ row[yearObj.lastPrevYear] || row[yearObj.lastPrevYear] === 0 ? row[yearObj.lastPrevYear].toFixed(2) : '-' }}
                </template>
              </template>
            </vxe-table-column>
          </vxe-table-column>
        </vxe-table>
        <div
          class="action-buttons"
          v-if="isEdit"
        >
          <a-button
            class="save-btn"
            type="primary"
            @click="saveBtn"
          >保存</a-button>
          <a-button @click="cancelBtn()">取消</a-button>
        </div>
      </div>
    </a-spin>
  </div>

</template>

<script>
import CustomTitle from './CustomTitle'
const currentYear = new Date().getFullYear()
const yearObj = { prevYear: currentYear - 1, nextPrevYear: currentYear - 2, lastPrevYear: currentYear - 3 }
export default {
  name: 'FinaceCase',
  components: {
    CustomTitle
  },
  props: {
    companyId: {
      type: Number,
      required: true
    }
  },
  data () {
    return {
      isEdit: false,
      yearObj,
      currentYear,
      spinning: false,
      tableData: [],
      rowMap: {},
      bakData: undefined,
      editRow: {}
    }
  },
  created () {
    this.loadDictionary()
  },

  watch: {
    companyId (cId) {
      this.cancelBtn()
      this.loadData()
    }
  },
  methods: {
    loadDictionary () {
      this.$getFinanceDictionary().then(res => {
        const arr = []
        res.forEach((e, index) => {
          const row = { projectType: e.value, key: e.key }
          row[yearObj.prevYear] = undefined
          row[yearObj.nextPrevYear] = undefined
          row[yearObj.lastPrevYear] = undefined
          arr.push(row)
          this.rowMap[e.key] = index
        })
        this.tableData = arr
        this.loadData()
      })
    },
    loadData () {
      this.cleanData()
      this.$http.get('/companyInfo/getFinanceInfo', { params: { companyId: this.companyId, currentYear: this.currentYear } }).then(res => {
        if (res.success && res.data) {
          res.data.forEach(e => {
            const row = this.tableData[this.rowMap[e.key]]
            row[e.year] = e.value
          })
        } else {
          this.$message.error('获取单位财务情况失败')
        }
      })
    },
    cleanData () {
      this.tableData.forEach(row => {
        row[yearObj.prevYear] = undefined
        row[yearObj.nextPrevYear] = undefined
        row[yearObj.lastPrevYear] = undefined
      })
    },
    handleChange (v, row, year) {
      if (!v && v !== 0) {
        return
      }
      row[year] = v
      this.editRow[`${row.key}${year}`] = { year: year, key: row.key, value: v, companyId: this.companyId }
    },
    saveBtn () {
      this.spinning = true
      const arr = []
      for (const k in this.editRow) {
        arr.push(this.editRow[k])
      }
      if (!arr.length) {
        this.spinning = false
        return
      }
      this.$http.post('/companyInfo/editFinancialInfo', arr).then(res => {
        if (res.success && res.data) {
          this.$message.success('保存成功')
          this.editRow = {}
          this.bakData = this.$deepClone(this.tableData)
          this.cancelBtn()
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '保存失败。')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    cancelBtn () {
      this.isEdit = false
      this.$refs.title.changeEdit(false)
      if (this.bakData) {
        this.tableData = this.$deepClone(this.bakData)
        this.bakData = undefined
      }
      this.editRow = {}
    },
    switchEdit (isEdit) {
      this.isEdit = isEdit
      if (isEdit) {
        this.bakData = this.$deepClone(this.tableData)
      }
    }
  }
}
</script>
<style lang="less" scoped>
.mode-title {
  font-size: 18px;
  font-weight: 600;
  color: #151515;
}
.action-buttons {
  padding: 12px 0;
  text-align: center;
}
.save-btn {
  margin-right: 40px;
}
</style>
