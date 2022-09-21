<!--
 * @Author: your name
 * @Date: 2022-02-15 13:58:17
 * @LastEditors: hm
 * @LastEditTime: 2022-02-22 15:48:53
 * @Description: 公司名变更弹出框
 * @FilePath: \RS-VUE\src\views\company\modules\updateCompanyNameModal.vue
-->
<template>
  <a-modal
    :title="title"
    :visible="visible"
    @cancel="closeModal"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    :footer="false"
    destroyOnClose
    width="800px">
    <vxe-grid
      :data="tableData"
      highlight-hover-row
      show-overflow="tooltip"
      show-footer
      resizable
      auto-resize
      max-height="100%"
      :loading="tableLoading"
      header-align="center"
      ref="xTable"
    >
      <vxe-table-column
        title="序号"
        type="seq"
        width="50"
        align="center"
        showOverflow="tooltip"
      ></vxe-table-column>
      <vxe-table-column
        title="变更名称"
        field="companyName"
        align="left"
        width="230px"
        showOverflow="tooltip"
        :header-class-name="requireShow"
      >
        <template #default="{ row }">
          <a-input
            v-if="row.isEdit"
            type="text"
            placeholder="请输入变更名称"
            :maxLength="200"
            v-model="row.companyName" />
          <span v-else>{{ row.companyName }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="生效时间"
        field="startDate"
        align="center"
        width="160px"
        showOverflow="tooltip"
        :header-class-name="requireShow"
      >
        <template #default="{ row }">
          <a-date-picker
            v-if="row.isEdit"
            :allowClear="false"
            placeholder="请选择生效时间"
            v-model="row.startDate"
            :disabledDate="disabledDate"
          />
          <span v-else>{{ row.startDate.format('YYYY/MM/DD') }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="备注"
        field="remark"
        align="left"
        width="160px"
        :showOverflow="false"
      >
        <template #default="{ row }">
          <a-textarea
            v-if="row.isEdit"
            placeholder="请输入备注"
            allow-clear
            :max-length="200"
            :rows="1"
            v-model="row.remark" />
          <span v-else>{{ row.remark }}</span>
        </template>
      </vxe-table-column>
      <vxe-table-column
        title="操作"
        width="150"
        align="left"
      >
        <template v-if="$auth('company:info:base:updateName')" #default="{ row, rowIndex }">
          <template v-if="row.id && !row.isEdit">
            <a v-if="row.id" @click="editRow(row)">编辑</a>
            <a-divider type="vertical" />
            <a @click="delName(row, rowIndex)">删除</a>
          </template>
          <template v-else>
            <a @click="saveName(row)">保存</a>
            <a-divider type="vertical" />
            <a @click="cancel(row, rowIndex)">取消</a>
          </template>
        </template>
      </vxe-table-column>
    </vxe-grid>
    <a-button style="width: 100%;" type="dashed" @click="addName">新  增</a-button>
  </a-modal>
</template>

<script>
import VXETable from 'vxe-table'
import moment from 'moment'
VXETable.setup({
  zIndex: 9999 // 层级
})
export default {
  data () {
    return {
      title: '变更公司名称',
      visible: false,
      confirmLoading: false,
      tableLoading: false,
      tableData: [],
      isChange: false
    }
  },
  methods: {
    moment,
    show () {
      this.visible = true
      this.tableLoading = true
      this.isChange = false
      this.$http.get('/company/getCompanyHistoryName').then(res => {
        if (res.data && res.success) {
          res.data.forEach(element => {
            element.startDate = this.moment(element.startDate)
            element.isEdit = false
          })
          this.tableData = res.data
        } else {
          this.$message.error(res.errorMessage || '获取信息失败！')
        }
      }).catch(res => {
        this.$message.error(res.errorMessage || '系统异常，请联系管理员！')
      }).finally(() => {
        this.tableLoading = false
      })
    },
    isNullObj (obj) {
      return obj === null || obj === undefined || obj === '' || JSON.stringify(obj) === '{}' || JSON.stringify(obj) === '[]'
    },
    resetCompanyName (companyName) {
      if (!this.isNullObj(companyName)) {
        this.$emit('changeCompanyName', companyName)
        this.$store.state.user.info.companyName = companyName
      }
    },
    editRow (row) {
      const copyRow = { ...row, startDate: row.startDate.clone() }
      row.old = copyRow
      row.isEdit = true
    },
    delName (row, rowIndex) {
      if (row.id) {
        // 删除数据库存在数据
        const self = this

        this.$confirm({
          title: '提示',
          content: '真的要删除当前记录吗 ?',
          onOk () {
            self.tableLoading = true
            self.$http.post('/company/delCompanyHistoryName', { 'id': row.id }).then(res => {
              if (res.success) {
                self.$message.success('删除成功!')
                self.tableData.splice(rowIndex, 1)
                self.resetCompanyName(res.data.companyName)
              } else {
                self.$message.error(res.errorMessage || '删除失败!')
              }
            }).catch(res => {
              self.$message.error(res.errorMessage || '系统异常，请联系管理员!')
            }).finally(() => {
              self.tableLoading = false
            })
          },
          onCancel () {
          }
        })
      } else {
        this.tableData.splice(rowIndex, 1)
      }
    },
    saveName (row) {
      if (this.isNullObj(row.companyName) || this.isNullObj(row.startDate)) {
        this.$message.error('公司【变更名称】与【生效时间】为必填项，请填写后重新操作!')
        return
      }
      row.startDate = row.startDate.millisecond(0).second(0).minute(0).hour(0)
      row.companyName = row.companyName.trim()

      for (let index = 0; index < this.tableData.length; index++) {
        const item = this.tableData[index]
        if (this.isNullObj(item.id) || item.id === row.id) {
          continue
        }
        let rowData = item
        if (item.old) {
          rowData = item.old
        }
        if (rowData.companyName === row.companyName) {
          this.$message.error(`当前存在相同公司名【${row.companyName}】，请重新输入!`)
          return
        }
        if (rowData.startDate.diff(row.startDate, 'days') === 0) {
          this.$message.error(`当前存在相同的生效时间，请重新输入!`)
          return
        }
      }

      this.tableLoading = true
      const saveRow = { ...row }
      delete saveRow.old
      this.$http.post('/company/saveCompanyHistoryName', saveRow).then(res => {
        if (res.success) {
          this.$message.success('保存成功!')
          this.resetCompanyName(res.data.companyName)
          row.isEdit = false
          row.id = res.data.id
          delete row.old
        } else {
          this.$message.error(res.errorMessage || '保存失败!')
        }
      }).catch(res => {
        this.$message.error(res.errorMessage || '系统异常，请联系管理员!')
      }).finally(() => {
        this.tableLoading = false
      })
    },
    addName () {
      this.tableData.push({
        companyName: null,
        remark: null,
        isEdit: true
      })
    },
    cancel (row, rowIndex) {
      if (this.isNullObj(row.id)) {
        this.tableData.splice(rowIndex, 1)
      } else {
        const old = row.old
        row.isEdit = false
        row.startDate = old.startDate
        row.companyName = old.companyName
        row.remark = old.remark
        delete row.old
      }
    },
    closeModal () {
      this.visible = false
    },
    disabledDate (current) {
      return current > moment()
    },
    requireShow () {
      return 'isRequired'
    }
  }
}
</script>

<style lang="less" scoped>
  /deep/ .isRequired .vxe-cell--title::before {
    content: '*';
    color: red;
  }
</style>
