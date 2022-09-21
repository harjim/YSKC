<!--
 * @Author: ldx
 * @Date: 2020-12-07 08:59:41
 * @LastEditTime: 2020-12-08 18:11:39
 * @LastEditors: ldx
 * @Description: 国家省市财政全部支持情况
 * @FilePath: \MS-VUE\src\views\customer\customerInformation\modules\SupportCase.vue
-->
<template>
  <div>
    <custom-title title="国家省市财务全部支持情况" @switchEdit="switchEdit" ref="title"></custom-title>
    <!-- <a-row :gutter="8">
      <a-col :span="12">
        <a-form-item label="年份" :labelCol="labelColT" :wrapperCol="wrapperColT" :style="{ marginBottom: '0' }">-->
    <!-- <div v-if="isEdit"> -->
    <!-- <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)', marginBottom: '0' }">
            <a-date-picker mode="year" style="width: 100%" />
          </a-form-item>
          <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
            -
          </span>
          <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)', marginBottom: '0' }">
            <a-date-picker mode="year" style="width: 100%" />
          </a-form-item>
        </a-form-item>
      </a-col>
    </a-row> -->
    <a-spin :spinning="spinning" tip="请稍后...">
      <vxe-grid
        ref="table"
        :data="tableData"
        highlight-current-row
        highlight-hover-row
        show-overflow
        resizable
        auto-resize
      >
        <vxe-table-column title="项目名称" field="projectName" width="160" align="center" header-align="center">
          <template v-slot:header>
            <font color="red">*</font>项目名称
          </template>
          <template v-slot="{ row }">
            <template v-if="isEdit">
              <a-input v-model="row.projectName" @change="valueChange(row)"/>
            </template>
            <template v-else>{{ row.projectName }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column title="资助单位" field="supportDeptName" width="140" align="center" header-align="center">
          <template v-slot:header>
            <font color="red">*</font>资助单位
          </template>
          <template v-slot="{ row }">
            <template v-if="isEdit">
              <a-input v-model="row.supportDeptName" @change="valueChange(row)"/>
            </template>
            <template v-else>{{ row.supportDeptName }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column title="受资助年份" field="syear" width="100" align="center" header-align="center">
          <template v-slot:header>
            <font color="red">*</font>受资助年份
          </template>
          <template v-slot="{ row }">
            <template v-if="isEdit">
              <a-select style="width:78px;" v-model="row.syear" @change="valueChange(row)">
                <a-select-option v-for="y in years" :key="y" :value="`${y}`">{{ y }}</a-select-option>
              </a-select>
            </template>
            <template v-else>{{ row.syear }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column title="资助金额(万元)" field="supportAmount" width="120" align="center" header-align="center">
          <template v-slot:header>
            <font color="red">*</font>资助金额(万元)
          </template>
          <template v-slot="{ row }">
            <template v-if="isEdit">
              <a-input-number
                @change="valueChange(row)"
                v-model="row.supportAmount"
                :max="$store.state.totalMax"
                :min="0"
                width="80"
                :precision="2"
                placeholder=""/>
            </template>
            <template v-else>{{ row.supportAmount || row.supportAmount === 0 ? row.supportAmount.toFixed(2): '-' }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column title="下达文号" field="issuceNum" width="100" align="center" header-align="center">
          <template v-slot:header>
            <font color="red">*</font>下达文号
          </template>
          <template v-slot="{ row }">
            <template v-if="isEdit">
              <a-input v-model="row.issuceNum" @change="valueChange(row)"/>
            </template>
            <template v-else>{{ row.issuceNum }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column title="项目负责人" field="master" width="100" align="center" header-align="center">
          <template v-slot:header>
            <font color="red">*</font>项目负责人
          </template>
          <template v-slot="{ row }">
            <template v-if="isEdit">
              <a-input v-model="row.master" @change="valueChange(row)" />
            </template>
            <template v-else>{{ row.master }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column title="项目实施年限" field="supportTime" width="120" align="center" header-align="center">
          <template v-slot="{ row }">
            <template v-if="isEdit">
              <a-input v-model="row.supportTime" @change="valueChange(row)" />
            </template>
            <template v-else>{{ row.supportTime }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column title="验收时间" field="checkTime" width="140" align="center" header-align="center">
          <template v-slot:header>
            <font color="red">*</font>验收时间
          </template>
          <template v-slot="{ row }">
            <template v-if="isEdit">
              <a-date-picker :value="row.checkTime?moment(row.checkTime) : undefined" @change="(m,s)=>dateChange(m,s,row)"/>
            </template>
            <template v-else>{{ row.checkTime }}</template>
          </template>
        </vxe-table-column>
        <vxe-table-column title="操作" width="70" align="center" header-align="center" v-if="isEdit">
          <template v-slot="{ row, rowIndex }">
            <a-popconfirm title="是否确定删除?" @confirm="handleDel(row, rowIndex)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </vxe-table-column>
        <template v-if="isEdit" v-slot:bottom>
          <a-button
            title="添加国家省市财政支持情况"
            type="dashed"
            style="width: 100%;font-weight: bolder"
            @click="addRow"
          >+</a-button
          >
        </template>
      </vxe-grid>
      <div>
        <div v-if="isEdit" class="action-buttons">
          <a-button class="save-btn" type="primary" @click="saveBtn">保存</a-button>
          <a-button @click="cancelBtn">取消</a-button>
        </div>
      </div>
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment'
import CustomTitle from './CustomTitle'
const currentYear = new Date().getFullYear()
const years = [currentYear, currentYear - 1, currentYear - 2, currentYear - 3]
export default {
  name: 'SupportCase',
  components: {
    CustomTitle
  },
  props: {
    companyId: {
      type: Number,
      required: true
    }
  },
  created () {
    this.loadData()
  },
  data () {
    return {
      years,
      spinning: false,
      currentYear,
      bakData: undefined,
      tableData: [],
      changeObj: {},
      isEdit: false,
      labelColT: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperColT: {
        xs: { span: 24 },
        sm: { span: 20 }
      }
    }
  },
  watch: {
    companyId (cId) {
      this.cancelBtn()
      this.loadData()
    }
  },
  methods: {
    valueChange (row) {
      if (row.id) {
        this.changeObj[row.id] = true
      }
    },
    dateChange (m, s, row) {
      row.checkTime = s
      this.valueChange(row)
    },
    moment,
    handleDel (row, index) {
      this.spinning = true
      if (row.id) {
        this.$http.post('/companyInfo/deleteSupport', { id: row.id }).then(res => {
          if (res.success && res.data) {
            this.$message.success('删除成功')
            this.tableData.splice(index, 1)
            this.bakData = this.$deepClone(this.tableData)
            this.changeObj[row.id] = true
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
    saveBtn () {
      this.spinning = true
      const params = this.checkData()
      if (!params) {
        this.spinning = false
        return
      }
      this.$http.post('/companyInfo/editSupport', params).then(res => {
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
    loadData (closeEdit) {
      this.$http.get('/companyInfo/getSupport', { params: { companyId: this.companyId, year: currentYear } }).then(res => {
        let arr = []
        if (res.success) {
          if (res.data) {
            arr = res.data
          }
          this.tableData = arr
          this.bakData = this.$deepClone(arr)
          if (closeEdit) {
            this.cancelBtn()
          }
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '获取国家省市财政全部支持情况失败')
        }
      })
    },
    checkData () {
      if (!this.tableData.length) {
        this.$message.warning('请至少添加一行数据。')
        return false
      }
      const arr = []
      let check = true
      for (const i in this.tableData) {
        const item = this.tableData[i]
        if (item.id && !this.changeObj[item.id]) {
          continue
        }
        const row = Number(i) + 1
        if (!item.projectName) {
          this.$message.warning(`第${row}行，项目名称不能为空`)
          check = false
          break
        }
        if (!item.supportDeptName) {
          this.$message.warning(`第${row}行，资助单位不能为空`)
          check = false
          break
        }
        if (!item.syear) {
          this.$message.warning(`第${row}行，受资助年份不能为空`)
          check = false
          break
        }
        if (!item.supportAmount && item.supportAmount !== 0) {
          this.$message.warning(`第${row}行，资助金额不能为空`)
          check = false
          break
        }
        if (!item.issuceNum) {
          this.$message.warning(`第${row}行，下达文号不能为空`)
          check = false
          break
        }
        if (!item.master) {
          this.$message.warning(`第${row}行，项目负责人不能为空`)
          check = false
          break
        }
        if (!item.checkTime) {
          this.$message.warning(`第${row}行，验收时间不能为空`)
          check = false
          break
        }
        arr.push(item)
      }
      return check ? arr : false
    },
    cancelBtn () {
      this.isEdit = false
      this.changeObj = {}
      this.$refs.title.changeEdit(false)
      if (this.bakData) {
        this.tableData = this.$deepClone(this.bakData)
        this.bakData = undefined
      }
    },
    switchEdit (isEdit) {
      this.isEdit = isEdit
    },
    addRow () {
      this.tableData.push({
        projectName: undefined,
        supportDeptName: undefined,
        syear: undefined,
        supportAmount: undefined,
        supportTime: undefined,
        issuceNum: undefined,
        master: undefined,
        checkTime: undefined,
        companyId: this.companyId
      })
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
