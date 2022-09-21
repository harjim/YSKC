<template>
  <a-modal
    :class="companyId ? 'notFooter' : ''"
    :width="companyId ? '850px' : 600"
    :dialog-style="{ top: '250px' }"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @cancel="closeModal"
    :confirmLoading="confirmLoading"
  >
    <template #footer>
      <span v-if="!companyId">
        <a-button type="default" @click="closeModal">取消</a-button>
        <a-button type="primary" @click="handleSubmit">确定</a-button>
      </span>
    </template>
    <a-spin :spinning="confirmLoading">

      <template v-if="!companyId">
        <a-form @submit="handleSubmit" :form="form">
          <a-form-item label="公司名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              v-decorator="['companyName', {rules: [{ required: true, message: '内容不可为空' }], validateTrigger: ['change', 'blur'], initialValue: companyName}]"
            />
          </a-form-item>
        </a-form>
      </template>
      <template v-else>
        <vxe-grid
          :data="tableData"
          highlight-hover-row
          show-overflow="tooltip"
          show-footer
          resizable
          auto-resize
          max-height="100%"
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
                type="text"
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
            <!-- showOverflow="tooltip" -->
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
          <!-- <vxe-table-column
            title="创建时间"
            field="createTime"
            align="center"
            width="100px"
            showOverflow="tooltip"
          ><template #default="{ row }">
              <span>{{ row.createTime ? row.createTime.format('l') : '' }}</span>
            </template>
          </vxe-table-column> -->
          <vxe-table-column
            title="操作"
            width="150"
            align="left"
            showOverflow="tooltip"
          >
            <template #default="{ row, rowIndex }" v-if="$auth('customer:all:changeName')">
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
      </template>
    </a-spin>
  </a-modal>
</template>

<script>
import moment from 'moment'
import Template from '@/views/doc/Template.vue'

export default {
  components: { Template },
  data () {
    return {
      title: '',
      companyName: '',
      companyId: null,
      customerId: null,
      visible: false,
      confirmLoading: false,
      tableData: [],
      // copyData: [],
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 14 }
      },
      isChange: false

    }
  },
  methods: {
    moment,
    open (companyName, companyId, customerId) {
      this.title = '变更名称 [' + companyName + ']'
      this.companyName = companyName
      this.confirmLoading = false
      this.visible = true
      this.companyId = companyId
      this.customerId = customerId
      if (companyId) {
        this.confirmLoading = true
        this.$http.get('/customer/getNameHistory', { params: { companyId } }).then(res => {
          if (res.success && res.data) {
            res.data.forEach(element => {
              element.isEdit = false
              element.customerId = customerId
              element.startDate = this.moment(element.startDate)
            })
            this.tableData = res.data
          } else {
            this.$message.error(res.errorMessage || '获取记录失败，请重新操作!')
          }
        }).catch(res => {
          this.$message.error(res.errorMessage || '系统异常，请联系管理员!')
        }).finally(() => {
          this.confirmLoading = false
        })
      }
    },
    editRow (row) {
      row.old = { ...row, startDate: row.startDate.clone() }
      row.isEdit = true
    },
    delName (row, rowIndex) {
      if (row.id) {
        // 删除数据库存在数据
        this.confirmLoading = true
        this.$http.post('/customer/delNameHistory', { 'id': row.id, 'companyId': this.companyId, 'customerId': this.customerId }).then(res => {
          if (res.success) {
            this.$message.success('删除成功!')
            this.tableData.splice(rowIndex, 1)
            this.isChange = true
          } else {
            this.$message.error(res.errorMessage || '删除失败!')
          }
        }).catch(res => {
          this.$message.error(res.errorMessage || '系统异常，请联系管理员!')
        }).finally(() => {
          this.confirmLoading = false
        })
      } else {
        this.tableData.splice(rowIndex, 1)
      }
    },
    saveName (row) {
      if (row.companyName === null || row.companyName === '' || row.startDate === undefined) {
        this.$message.error('公司【变更名称】与【生效时间】为必填项，请填写后重新操作!')
        return
      }
      row.startDate = row.startDate.millisecond(0).second(0).minute(0).hour(0)
      row.companyName = row.companyName.trim()
      for (let i = 0; i < this.tableData.length; i++) {
        let item = this.tableData[i]
        if (!item.id || item.id === row.id) {
          continue
        }
        if (item.old) {
          item = item.old
        }
        if (item.startDate.diff(row.startDate) === 0) {
          this.$message.error(`当前存在相同的生效时间，请重新输入!`)
          return
        }
        if (row.companyName === item.companyName) {
          this.$message.error(`当前存在相同公司名【${row.companyName}】，请重新输入!`)
          return
        }
      }
      this.confirmLoading = true
      this.$http.post('/customer/saveNameHistory', { ...row }).then(res => {
        if (res.success) {
          this.$message.success('保存成功!')
          this.isChange = true
          row.id = res.data
          row.isEdit = false
          delete row.old
        } else {
          this.$message.error(res.errorMessage || '系统异常，请联系管理员!')
        }
      }).catch(res => {
        this.$message.error(res.errorMessage || '系统异常，请联系管理员!')
      }).finally(() => {
        this.confirmLoading = false
      })
    },
    addName () {
      this.tableData.push({
        companyName: null,
        companyId: this.companyId,
        customerId: this.customerId,
        remark: null,
        isEdit: true,
        id: null
      })
    },
    cancel (row, rowIndex) {
      if (!row.id) {
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
      if (this.isChange) {
        this.$emit('refresh', false)
      }
    },
    disabledDate (current) {
      return current > moment()
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.visible = true
      validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          values.id = this.customerId
          this.$http.post('/customer/changeName', values)
            .then(res => {
              if (res.success && res.data) {
                this.confirmLoading = false
                this.form.resetFields()
                this.$message.success('提交成功')
                this.isChange = true
                this.closeModal()
              } else {
                this.$message.error(res.errorMessage)
              }
            }).finally(res => {
              this.confirmLoading = false
            })
        }
      })
    },
    requireShow () {
      return 'isRequired'
    }
  }
}
</script>

<style>
.notFooter .ant-modal-footer {
  padding: 0;
}
</style>

<style lang="less" scoped>
  /deep/ .isRequired .vxe-cell--title::before {
    content: '*';
    color: red;
  }
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
