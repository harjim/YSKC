<!--
 * @Author: ldx
 * @Date: 2020-10-13 09:39:36
 * @LastEditTime: 2020-11-04 11:34:36
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\project\modules\CopyDeclareItemModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="450"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    :confirmLoading="spinning"
    @cancel="isVisible = false"
    @ok="handleSubmit"
  >
    <a-spin :spinning="spinning">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="地区"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-cascader
                :changeOnSelect="true"
                placeholder="请选择地区"
                :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
                :options="addressCode"
                v-decorator="['addressCode', {rules:[{required: true, message: '请选择地区'}]}]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :span="24">
            <a-form-item
              label="年份"
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
            >
              <a-select
                placeholder="请选择年份"
                :allowClear="true"
                v-decorator="['year', {rules:[{required: true, message: '请选择年份'}]}]"
              >
                <a-select-option v-for="y in years" :key="y">{{ y }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
// import { cloneDeep } from 'lodash'
import moment from 'moment'
const startYear = (new Date()).getFullYear() - 4
const endYear = (new Date()).getFullYear() + 6
const years = []
for (let y = startYear; y < endYear; y++) {
  years.unshift(y)
}
export default {
  name: 'HandleDeclareItemModal',
  components: {
  },
  created () {
    this.$getDictionary(1).then(d => {
      this.addressCode = d
    })
  },
  data () {
    return {
      addressCode: [], // 地址编号
      isVisible: false,
      form: this.$form.createForm(this),
      labelCol: {
        xs: { span: 3 },
        sm: { span: 3 }
      },
      wrapperCol: {
        xs: { span: 21 },
        sm: { span: 21 }
      },
      isEdit: false,
      row: undefined,
      title: '',
      spinning: true,
      years
    }
  },
  methods: {
    moment,
    show (isEdit, row) {
      this.afterClose()
      this.spinning = false
      this.isEdit = isEdit
      this.row = row
      this.title = `复制申报项目[${row.productName}-${this.getAddres(row.addressCode)}-${row.year}]`
      this.isVisible = true
      this.form.resetFields()
    },
    afterClose () {
      this.isVisible = true
      this.isEdit = false
      this.tableData = []
      this.title = ''
      this.row = undefined
      this.spinning = true
    },
    handleSubmit () {
      this.form.validateFields((error, values) => {
        if (!error) {
          this.spinning = true
          const addressCode = values.addressCode.join('/')
          const year = values.year
          const id = this.row.id
          this.$http.get('/rsProduct/copyProject', { params: { addressCode, year, id } }).then((res) => {
            if (res.data && res.success) {
              this.$emit('ok', this.isEdit)
              this.$message.success('操作成功')
              this.spinning = false
              this.isVisible = false
            } else {
              this.spinning = false
              this.$message.error(res.errorMessage)
            }
          }).catch((error) => {
            this.spinning = false
            this.$message.error(error.message)
          })
        }
      })
    },
    getAddres (code) {
      if (!code) {
        return ''
      }
      const arr = code.split('/')
      var currentIndex = 0
      var result = ''
      var tempChildren = this.addressCode
      while (currentIndex < arr.length) {
        for (let i = 0; i < tempChildren.length; i++) {
          if (arr[currentIndex] === tempChildren[i].key) {
            result += tempChildren[i].value + '/'
            if (tempChildren[i].children && tempChildren[i].children.length > 0) {
              tempChildren = tempChildren[i].children
            } else {
              tempChildren = []
            }
            break
          }
        }
        currentIndex++
      }
      return result ? result.substr(0, result.length - 1) : result
    }
  }
}
</script>
<style lang='less' scoped>
</style>
