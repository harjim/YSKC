<template>
  <a-modal
    title="导出归集数据"
    v-model="visible"
    :width="500"
    @cancel="closeModal"
    @ok="exportData"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
  >
    <a-form :form="form">
      <a-row>
        <a-form-item
          label="项目"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <!-- <projectSelect
            v-decorator="['projectIds', {rules:[{required: false, message: '请选择项目'}]}]"
            :isMul="true"
            :year="currentYear"
            :sign="2"
            width="340px"
            @getAllId="ids=>allProjectIds = ids"
          />-->
          <a-tree-select
            v-decorator="['projectIds', { rules: [{ required: true, message: '请选择项目' }] }]"
            style="width: 100%"
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
      <a-row>
        <a-form-item
          label="月份"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-tree-select
            :dropdownStyle="{ maxHeight: '420px', maxWidth: '339px' }"
            v-decorator="['months', { rules: [{ required: true, message: '请选择月份' }] }]"
            placeholder="请选择月份"
            style="width: 100%"
            tree-default-expand-all
            tree-checkable
            :show-checked-strategy="SHOW_PARENT"
          >
            <a-tree-select-node
              title="所有月份"
              :value="-1"
              key="-1"
            >
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
      <a-row>
        <a-form-item
          label="类型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-select
            placeholder="请选择类型"
            :allowClear="true"
            v-decorator="['dataIndex', { rules: [{ required: true, message: '请选择类型' }] }]"
          >
            <a-select-option
              v-for="(item, index) in dataTypes"
              :key="index"
              :value="index"
            >{{
              item.sname
            }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-row>
      <a-row>
        <a-form-item
          label="导出样式"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
        >
          <a-radio-group v-decorator="['exportType', { rules: [{ required: true, message: '请选择导出样式' }] }]">
            <a-radio
              :value="0"
              title="所有数据导出进一个sheet"
            >单sheet</a-radio>
            <a-radio
              :value="1"
              title="每个RD数据导出进一个sheet"
            >RD</a-radio>
            <a-radio
              :value="2"
              title="每个月份数据导出进一个sheet"
            >月份</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import { mapGetters } from 'vuex'
import yearMiXin from '@/utils/yearMixin'
import { TreeSelect } from 'ant-design-vue'
import moment from 'moment'
const dataTypes = [
  { sname: '人员费用', name: '人员费用', types: [10000, 10100] },
  { sname: '设备折旧', name: '设备折旧', types: [30000, 30100] },
  { sname: '研发材料-研发材料', name: '研发材料', types: [20000] },
  { sname: '研发材料-造纸材料', name: '研发材料', types: [20001] },
  { sname: '研发材料-流程型', name: '研发材料', types: [20002] },
  { sname: '中间试制-试制材料', name: '中间试制', types: [20301] },
  { sname: '中间试制-造纸试制', name: '中间试制', types: [20303] },
  { sname: '中间试制-流程型', name: '中间试制', types: [20304] },
  { sname: '中间试制-试制动力', name: '中间试制', types: [20302], 'type': 20100 },
  { sname: '中间试制-其他试制', name: '中间试制', types: [20300] },
  { sname: '修理费用-修理材料', name: '修理费用', types: [20601] },
  { sname: '修理费用-凭证费用', name: '修理费用', types: [20600] },
  { sname: '样机费用', name: '样机费用', types: [20700] },
  { sname: '动力损耗-研发动力', name: '动力损耗', types: [20100], 'type': 20100 },
  { sname: '动力损耗-设备动力', name: '动力损耗', types: [20101] },
  { sname: '动力损耗-流程型', name: '动力损耗', types: [20102], 'type': 20100 },
  { sname: '燃料损耗-研发燃料', name: '燃料损耗', types: [20200], 'type': 20200 },
  { sname: '燃料损耗-流程型', name: '燃料损耗', types: [20201], 'type': 20200 },
  { sname: '设计费用', name: '设计费用', types: [50000] },
  { sname: '检测费用', name: '检测费用', types: [20500] },
  { sname: '差旅费用', name: '差旅费用', types: [60400] },
  { sname: '摊销费用-凭证费用', name: '摊销费用', types: [40000, 40100, 40200] },
  { sname: '摊销费用-资产摊销', name: '摊销费用', types: [40001] },
  { sname: '其他费用', name: '其他费用', types: [69900, 60000, 60100, 60200, 60300] }
]
const SHOW_PARENT = TreeSelect.SHOW_PARENT
export default {
  mixins: [yearMiXin],
  data () {
    return {
      dataTypes,
      confirmLoading: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      visible: false,
      form: this.$form.createForm(this),
      projects: [],
      SHOW_PARENT
    }
  },
  mounted () {
    this.search()
  },
  methods: {
    moment,
    ...mapGetters(['userInfo']),
    exportData () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (errors) {
          this.confirmLoading = false
          return
        }
        var projectIds = values.projectIds
        if (values.projectIds[0] === -1) {
          projectIds = this.projects.map(a => a.id)
        }
        var months = values.months
        if (values.months[0] === -1) {
          months = this.monthOptions.map(m => moment([this.currentYear, m.value, 1, 0, 0, 0, 0]).format('YYYY-MM-DD HH:mm:ss'))
        } else {
          months = values.months.map(m => moment([this.currentYear, m, 1, 0, 0, 0, 0]).format('YYYY-MM-DD HH:mm:ss'))
        }
        const params = Object.assign(this.dataTypes[values.dataIndex],
          {
            exportType: values.exportType,
            projectIds: projectIds,
            months: months
          })
        this.$exportData('/aggregation/exportFunds', params, `${this.userInfo().companyName}${params.name}.xls`, this.$message).then(res => {
          this.confirmLoading = false
        })
      })
    },
    show () {
      this.visible = true
      this.confirmLoading = false
    },
    closeModal () {
      this.visible = false
      this.form.setFieldsValue({ projectIds: undefined })
      this.form.resetFields()
    },
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
        })
    }
  }
}
</script>

<style lang="less" scoped>
.c {
  max-height: 55px;
}
</style>
