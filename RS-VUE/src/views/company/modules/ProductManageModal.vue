<!-- 企业产品管理--产值管理 -->
<template>
  <a-modal
    :confirmLoading="confirmLoading"
    :title="title"
    :visible="visible"
    :width="800"
    @cancel="visible = false"
    @ok="handleSubmit"
  >
    <a-spin :spinning="confirmLoading">
      <vxe-grid
        ref="xTable"
        :column-config="{resizable: true}"
        :data="tableData"
        :loading="confirmLoading"
        :showUpdateStatus="true"
        resizable
        show-overflow
      >
        <template #empty>
          <a-empty />
        </template>
        <vxe-table-column
          align="center"
          field="year"
          headerAlign="center"
          minWidth="150"
          title="年份">
          <template #default="{ row }">
            <a-select
              v-model="row.year"
              style="width: 80%"
              @change="roleChangeEvent">
              <a-select-option
                v-for="(item, index) in yearList"
                :key="index"
                :disabled="item.disabled"
                :label="item.value"
                :value="item.value"> {{ item.value }}
              </a-select-option>
            </a-select>
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="center"
          field="output"
          headerAlign="center"
          minWidth="150"
          :title="`产量(${unit})`">
          <template #default="{ row }">
            <a-input v-model="row.output" type="number"></a-input>
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="center"
          field="outputValue"
          headerAlign="center"
          minWidth="150"
          title="产值(万元)">
          <template #default="{ row }">
            <a-input v-model="row.outputValue" type="number"></a-input>
          </template>
        </vxe-table-column>
        <vxe-table-column
          align="center"
          headerAlign="center"
          minWidth="150"
          title="操作">
          <template #default="{ row }">
            <span v-if="$auth('company:product:delete')">
              <a-popconfirm
                cancel-text="取消"
                ok-text="确定"
                placement="topRight"
                @confirm="deleteOne(row.id, row._XID)">
                <template slot="title">
                  <p>是否确定移除</p>
                </template>
                <a>移除</a>
              </a-popconfirm>
            </span>
          </template>
        </vxe-table-column>
      </vxe-grid>
      <a-button style="width: 100%;" type="dashed" @click="insertEvent" :disabled="yearHas">
        新增
      </a-button>
    </a-spin>
  </a-modal>
</template>

<script>

export default ({
  data () {
    return {
      title: '',
      productId: null,
      tableData: [],
      visible: false,
      confirmLoading: false,
      creationDate: null,
      yearList: [],
      form: this.$form.createForm(this, { name: 'coordinated' }),
      updateData: false,
      unit: null,
      yearHas: false
    }
  },
  watch: {
    tableData (newValue, oldValue) {
      if (oldValue !== []) {
        this.updateData = true
      }
    }
  },
  methods: {
    showModal (pname, productId, creationDate, unit) {
      this.title = `【${pname}】产值管理`
      this.visible = true
      this.confirmLoading = true
      this.contentInit(productId, creationDate)
      this.getTableData(productId)
      this.unit = unit
    },
    getTableData (productId) {
      this.$http.get('/product/getYearList', { params: { productId } })
        .then(res => {
          if (res.success && res.data) {
            this.confirmLoading = false
            this.tableData = res.data
          } else {
            this.$message.error(res.errorMessage)
          }
        }).finally(res => {
          this.confirmLoading = false
          this.roleChangeEvent()
        })
    },
    handleSubmit () {
      const $table = this.$refs.xTable
      const updateRecords = $table.getUpdateRecords()
      if (!this.updateData) {
        this.visible = false
        return
      } else if (this.contentEmpty()) {
        this.$message.info('当前存在未填内容，请全部填写后进行提交')
        return
      }
      console.log(updateRecords)
      this.$http.post('/product/editProductYear', { models: this.tableData, productId: this.productId }).then(res => {
        if (res.success) {
          this.$message.success('修改成功')
          this.$emit('tableRefresh', false, this.productId)
          this.visible = false
        } else {
          this.$message.error(`修改失败：${res.data.message}`)
        }
      })
    },
    contentEmpty () {
      const { fullData } = this.$refs.xTable.getTableData()
      if (fullData.some(row => row.year === null || row.output === null || row.outputValue === null || row.year === '' || row.output === '' || row.outputValue === '')) {
        return true
      }
      return false
    },
    contentInit (productId, creationDate) {
      this.yearList = []
      this.productId = productId
      this.creationDate = creationDate.split('-')[0]
      Array.from({ length: (new Date()).getFullYear() - this.creationDate + 1 },
        (_, index) => Number(this.creationDate) + index).forEach((item, index) => {
        this.yearList[index] = { value: item, disabled: false }
      })
    },
    insertEvent () {
      const record = {
        'id': null,
        'year': null,
        'output': null,
        'outputValue': null,
        'productId': this.productId
      }
      this.tableData.push(record)
    },
    deleteOne (outputId, xid) {
      this.tableData = this.tableData.filter(item => item._XID !== xid)
    },
    roleChangeEvent () {
      const $table = this.$refs.xTable
      // 获取表格中的全量数据
      const { fullData } = $table.getTableData()
      let isTrue = true
      this.yearList.forEach(item => {
        if (item.value) {
          // 如果当前选项已经被选过，则禁用
          item.disabled = fullData.some(row => row.year === item.value)
          if (!item.disabled) isTrue = false
        }
      })
      this.yearHas = isTrue
    }
  }
})
</script>
