<template>
  <a-modal
    title="导出归集数据"
    v-model="visible"
    :width="500"
    @ok="exportData"
    @change="closeModal"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-spin :spinning="confirmLoading" tip="正在导出请稍等....">
      <a-form :form="form">
        <a-row v-show="type === 'rd'">
          <a-form-item label="项目" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-tree-select
              v-decorator="['projectIds', {rules:[{required: type === 'rd', message: '请选择项目'}]}]"
              style="width: 100%;"
              :dropdownStyle="{ maxHeight: '420px', maxWidth: '339px' }"
              tree-default-expand-all
              tree-checkable
              :maxTagCount="3"
              :show-checked-strategy="SHOW_PARENT"
              placeholder="请选择项目"
            >
              <a-tree-select-node
                title="所有项目"
                :value="-1"
                key="-1"
                v-if="projects && projects.length"
              >
                <a-tree-select-node
                  v-for="p in projects"
                  :key="p.id"
                  :value="p.id"
                  :title="`${p.rdTitle}-${p.pname}`"
                />
              </a-tree-select-node>
            </a-tree-select>
          </a-form-item>
        </a-row>
        <a-row v-show="type === 'month'">
          <a-form-item label="月份" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-tree-select
              :dropdownStyle="{ maxHeight: '420px', maxWidth: '339px' }"
              v-decorator="['months', {rules:[{required: type === 'month', message: '请选择月份'}]}]"
              placeholder="请选择月份"
              style="width: 100%"
              tree-default-expand-all
              tree-checkable
              :show-checked-strategy="SHOW_PARENT"
            >
              <a-tree-select-node title="所有月份" :value="-1" key="-1">
                <a-tree-select-node
                  v-for="m in monthOptions"
                  :key="m.value"
                  :value="m.value"
                  :title="m.label"
                />
              </a-tree-select-node>
            </a-tree-select>
          </a-form-item>
        </a-row>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { mapGetters } from 'vuex'
import yearMiXin from '@/utils/yearMixin'
import { zeroFormat } from '@/utils/util'
import { TreeSelect } from 'ant-design-vue'
import moment from 'moment'
const SHOW_PARENT = TreeSelect.SHOW_PARENT
export default {
  mixins: [yearMiXin],
  data () {
    return {
      SHOW_PARENT,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      type: 'rd',
      visible: false,
      form: this.$form.createForm(this),
      confirmLoading: false,
      rdTitle: '',
      projects: [],
      projectMap: {},
      isClose: true
    }
  },
  mounted () {
    this.search()
  },
  methods: {
    moment,
    search () {
      if (!this.currentYear) {
        return
      }
      this.$http.get('/project/getSelectList', { params: { year: this.currentYear, sign: 2 } })
        .then(res => {
          if (res.data != null && res.data.length > 0) {
            this.projects = res.data
          } else {
            this.projects = []
          }
          this.projectMap = {}
          this.projects.forEach(item => {
            this.projectMap[item.id] = item.rdTitle
          })
        })
    },
    show (type) {
      this.type = type
      this.$nextTick(() => {
        this.form.resetFields()
        this.visible = true
      })
    },
    ...mapGetters(['userInfo']),
    exportData () {
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          const params = { year: this.currentYear }
          if (this.type === 'rd') {
            var projectIds
            if (values.projectIds[0] === -1) {
              projectIds = this.projects.map(a => a.id)
            } else {
              projectIds = values.projectIds
            }
            this.export('/aggregation/exportByRD', params, projectIds, 0)
          } else {
            var months
            if (values.months[0] === -1) {
              months = this.monthOptions.map(m => moment([this.currentYear, m.value, 1, 0, 0, 0, 0]).format('YYYY-MM-DD HH:mm:ss'))
            } else {
              months = values.months.map(m => moment([this.currentYear, m, 1, 0, 0, 0, 0]).format('YYYY-MM-DD HH:mm:ss'))
            }
            this.export('/aggregation/exportByMonth', params, months, 0)
          }
        }
      })
    },

    export (url, params, arr, i) {
      var name
      if (this.type === 'rd') {
        name = this.projectMap[arr[i]]
        params.projectId = arr[i]
      } else {
        name = `${this.currentYear}年${zeroFormat(Number(arr[i].substr(5, 2)))}月`
        params.month = arr[i]
      }
      this.$exportData(url, params, `${this.userInfo().companyName}${name}.xls`, this.$message).then(res => {
        i++
        if (i >= arr.length) {
          this.confirmLoading = false
          this.isClose = false
          this.closeModal()
        } else {
          this.export(url, params, arr, i)
        }
      })
    },
    closeModal () {
      if (!this.isClose) {
        this.form.resetFields()
        this.rdTitle = ''
        this.type = ''
        this.visible = false
        this.confirmLoading = false
        this.isClose = true
      } else {
        this.confirmLoading ? this.visible = true : this.visible = false
      }
    }
  }
}
</script>

<style>
</style>
