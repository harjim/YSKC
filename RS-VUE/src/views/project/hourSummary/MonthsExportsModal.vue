/**
 * @Author        : hzp
 * @Date          : 2022-09-24 11:40:04
 * @FilePath      : \YSIS\RS-VUE\src\views\project\hourSummary\MonthsExportsModal.vue
 * @Description   : 人员/设备按月份进行导出
 * @LastEditors   : hzp
 * @LastEditTime  : 2022-09-26 11:37:31
 */

<template>
  <a-modal
    destroyOnClose
    v-model="visible"
    title="按月份导出"
    :confirmLoading="spinning"
    @cancel="close"
    @ok="exportData"
  >
    <a-form :form="form" :label-col="{ span: 3 }" :wrapper-col="{ span: 20 }">
      <a-form-item label="月份">
        <a-tree-select
          show-search
          placeholder="请选择导出月份"
          multiple
          tree-checkable
          treeDefaultExpandAll
          dropdownMatchSelectWidth
          :show-checked-strategy="SHOW_PARENT"
          :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
          :treeData="months"
          v-decorator="[
            'months',
            { rules: [{ required: true, message: '请选择导出月份' }] }
          ]"
        ></a-tree-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script>
import { TreeSelect } from 'ant-design-vue'
import { get } from 'lodash'
import { mapGetters } from 'vuex'

const SHOW_PARENT = TreeSelect.SHOW_PARENT

export default {
  data () {
    return {
      SHOW_PARENT,
      visible: false,
      spinning: false,
      months: [],
      type: undefined,
      queryParams: {}
    }
  },
  created () {
    this.form = this.$form.createForm(this)
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    show ({ months, ...args }, type) {
      this.months = [{
        value: 0,
        label: `所有月份${months.length}`,
        children: months
      }]
      this.queryParams = args
      this.type = type
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue({
          months: [get(months, '[0].value')]
        })
      })
    },
    close () {
      Object.assign(this.$data, this.$options.data.call(this))
    },
    /******
     * @description: 根据人员/设备进行按月份导出
     * @return {*}
     */
    exportData () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.spinning = true
          if (values.months.length === 1 && values.months[0] === 0) {
            values.months = this.months[0].children.map(item => item.value)
          }
          const params = {
            ...this.queryParams,
            ...values
          }
          const url = this.type === 1 ? '/projectAttendance/exportRdHourData' : '/projectEquipment/exportRdHourData'
          this.$exportData(url, params, `${this.userInfo.companyName}${this.type === 1 ? '人员' : '设备'}工时记录按月份导出.xlsx`, this.$message).then(() => {
            this.spinning = false
            this.close()
          })
        }
      })
    }
  }
}
</script>
