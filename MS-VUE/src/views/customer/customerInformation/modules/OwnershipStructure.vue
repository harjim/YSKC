<!--
 * @Author: ldx
 * @Date: 2020-12-07 08:52:36
 * @LastEditTime: 2020-12-07 10:37:50
 * @LastEditors: ldx
 * @Description: 公司股权结构
 * @FilePath: \MS-VUE\src\views\customer\customerInformation\modules\ownershipStructure.vue
-->
<template>
  <div>
    <!-- <a-divider orientation="left">
      <span class="mode-title">公司股权结构</span>
    </a-divider> -->
    <custom-title
      title="公司股权结构"
      @switchEdit="switchEdit"
      ref="title"
    ></custom-title>
    <a-spin
      :spinning="spinning"
      tip="请稍后..."
    >
      <vxe-grid
        ref="table"
        :data="tableData"
        highlight-current-row
        highlight-hover-row
        show-overflow="title"
        resizable
        auto-resize
      >
        <vxe-table-column
          field="shareholder"
          min-width="200"
          align="left"
          header-align="center"
        >
          <template v-slot:header>
            <font color="red">*</font>股东
          </template>
          <template v-slot="{row}">
            <template v-if="isEdit">
              <a-input v-model="row.shareholder" />
            </template>
            <template v-else>{{ row.shareholder }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="出资额（万元）"
          field="capitalContribution"
          min-width="150"
          align="right"
          header-align="center"
        >
          <template v-slot:header>
            <font color="red">*</font>出资额（万元）
          </template>
          <template v-slot="{row}">
            <template v-if="isEdit">
              <a-input-number
                :precision="2"
                :min="0"
                :max="$store.state.totalMax"
                v-model="row.capitalContribution"
              />
            </template>
            <template v-else>{{ row.capitalContribution }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="出资方式"
          field="contributionType"
          min-width="140"
          align="left"
          header-align="center"
        >
          <template v-slot:header>
            <font color="red">*</font>出资方式
          </template>
          <template v-slot="{row}">
            <template v-if="isEdit">
              <a-input v-model="row.contributionType" />
            </template>
            <template v-else>{{ row.contributionType }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title=""
          field="proportion"
          min-width="140"
          align="riht"
          header-align="center"
        >
          <template v-slot:header>
            <font color="red">*</font>所占比例（%）
          </template>
          <template v-slot="{row,rowIndex}">
            <template v-if="isEdit">
              <a-input-number
                :precision="2"
                :max="100"
                :min="0"
                :value="row.proportion"
                @change="v=>handleChange(v,row,rowIndex)"
              />
            </template>
            <template v-else>{{ row.proportion }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column
          title="操作"
          align="center"
          width="80"
          header-align="center"
          v-if="isEdit"
        >
          <template v-slot="{row,rowIndex}">
            <a-popconfirm
              title="是否确定删除?"
              @confirm="handleDel(row,rowIndex)"
            >
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
        <template
          v-slot:bottom
          v-if="this.tableData.length < 5 && isEdit"
        >
          <a-button
            title="点击添加股东"
            type="dashed"
            style="width: 100%;font-weight: bolder"
            @click="addRow()"
          >+</a-button>
        </template>
      </vxe-grid>
      <div>
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
export default {
  name: 'OwnershipStructure',
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
      bakData: undefined,
      spinning: false,
      tableData: [],
      isEdit: false
    }
  },
  created () {
    this.loadData()
  },
  watch: {
    companyId (cId) {
      this.cancelBtn()
      this.loadData()
    }
  },
  methods: {
    handleChange (v, row, index) {
      if (isNaN(v)) {
        v = 0
      }
      const max = this.getMax(index)
      if (max < v) {
        v = max
      }
      row.proportion = v
    },
    loadData (closeEdit) {
      this.$http.get('/companyInfo/getOwnership', { params: { companyId: this.companyId } }).then(res => {
        let arr = []
        if (res.success) {
          if (res.data) {
            arr = res.data
          }
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '获取公司股权结构失败')
        }
        this.tableData = arr
        this.bakData = this.$deepClone(this.tableData)
        if (closeEdit) {
          this.cancelBtn()
        }
      })
    },
    handleDel (row, index) {
      this.spinning = true
      if (row.id) {
        this.$http.post('/companyInfo/deleteOwnership', { id: row.id }).then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.tableData.splice(index, 1)
            this.bakData = this.$deepClone(this.tableData)
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '删除失败')
          }
        }).finally(() => {
          this.spinning = false
        })
      } else {
        this.tableData.splice(index, 1)
        this.spinning = false
      }
    },
    addRow () {
      if (this.tableData.length === 5) {
        return
      }
      this.tableData.push({ shareholder: undefined, capitalContribution: undefined, contributionType: undefined, proportion: undefined, companyId: this.companyId })
    },
    getMax (index) {
      let max = 0
      for (const i in this.tableData) {
        if (Number(i) === Number(index)) {
          continue
        }
        if (this.tableData[i].proportion) {
          max += Number(this.tableData[i].proportion)
        }
      }
      return 100 - max
    },
    saveBtn () {
      this.spinning = true
      if (!this.checkData()) {
        this.spinning = false
        return
      }
      this.$http.post('/companyInfo/editOwnership', this.tableData).then(res => {
        if (res.success && res.data) {
          this.$message.success('保存成功')
          this.loadData(true)
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    checkData () {
      if (!this.tableData.length) {
        this.$message.warning('请至少添加一行数据。')
        return false
      }
      let check = true
      for (const i in this.tableData) {
        const row = Number(i) + 1
        if (!this.tableData[i].shareholder) {
          this.$message.warning(`第${row}行，股东不能为空`)
          check = false
          break
        }
        if (!this.tableData[i].capitalContribution && this.tableData[i].capitalContribution !== 0) {
          this.$message.warning(`第${row}行，出资额不能为空`)
          check = false
          break
        }
        if (!this.tableData[i].contributionType) {
          this.$message.warning(`第${row}行，出资方式不能为空`)
          check = false
          break
        }
        if (!this.tableData[i].proportion && this.tableData[i].proportion !== 0) {
          this.$message.warning(`第${row}行，所占比例不能为空`)
          check = false
          break
        }
      }
      return check
    },
    cancelBtn () {
      this.isEdit = false
      this.$refs.title.changeEdit(false)
      if (this.bakData) {
        this.tableData = this.$deepClone(this.bakData)
        this.bakData = undefined
      }
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
