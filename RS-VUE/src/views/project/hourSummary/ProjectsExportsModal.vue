/**
 * @Author        : hzp
 * @Date          : 2022-09-24 11:40:21
 * @FilePath      : \YSIS\RS-VUE\src\views\project\hourSummary\ProjectsExportsModal.vue
 * @Description   : 人员/设备按项目进行导出
 * @LastEditors   : hzp
 * @LastEditTime  : 2022-09-26 15:29:48
 */

<template>
  <a-modal
    destroyOnClose
    v-model="visible"
    title="按RD导出"
    :confirmLoading="spinning"
    @cancel="close"
    @ok="exportData">
    <a-form :form="form" :label-col="{ span: 3 }" :wrapper-col="{ span: 20 }">
      <a-form-item label="项目">
        <a-tree-select
          show-search
          placeholder="请选择导出项目"
          multiple
          tree-checkable
          treeDefaultExpandAll
          dropdownMatchSelectWidth
          :show-checked-strategy="SHOW_PARENT"
          :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
          :treeData="projects"
          v-decorator="[
            'projectIds',
            { rules: [{ required: true, message: '请选择导出项目' }] }
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
      projects: [],
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
    show ({ projects, ...args }, type) {
      this.projects = projects
      this.queryParams = args
      this.type = type
      this.visible = true
      this.$nextTick(() => {
        this.form.setFieldsValue({
          projectIds: [get(projects, '[0].children[0].value')]
        })
      })
    },
    close () {
      Object.assign(this.$data, this.$options.data.call(this))
    },
    exportData () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.spinning = true
          if (values.projectIds.length === 1 && values.projectIds[0] === 0) {
            values.projectIds = this.projects[0].children.map(item => item.value)
          }
          const params = {
            ...this.queryParams,
            ...values
          }
          const url = this.type === 1 ? '/projectAttendance/exportRdHourProject' : '/projectEquipment/exportRdHourProject'
          this.$exportData(url, params, `${this.userInfo.companyName}${this.type === 1 ? '人员' : '设备'}工时记录按项目导出.xlsx`, this.$message).then(() => {
            this.spinning = false
            this.close()
          })
        }
      })
    }
  }
}
</script>
