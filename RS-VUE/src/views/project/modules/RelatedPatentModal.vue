<template>
  <a-modal
    :width="450"
    :visible="visible"
    :title="title"
    :maskClosable="false"
    @ok="handleSubmit"
    :afterClose="afterClose"
    @cancel="visible = false"
  >
    <a-row :gutter="24">
      <a-col :span="24">
        <a-form-item
          label="关联项目"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          style="margin-bottom: 10px;"
        >
          <a-select
            style="width: 100%"
            showSearch
            :allowClear="true"
            :value="projectId"
            placeholder="请输选择项目"
            @change="selectChange"
          >
            <a-select-option
              v-for="project in projectList"
              :key="project.id"
              :value="project.id"
            >{{ project.rdTitle }} - {{ project.pname }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
    </a-row>
  </a-modal>
</template>

<script>
import moment from 'moment'
export default {
  data () {
    return {
      title: '关联项目',
      data: {},
      visible: false,
      projectId: undefined,
      projectList: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 20 }
      },
      ids: [],
      patentNos: []
    }
  },
  methods: {
    moment,
    show (patentNos, year) {
      this.visible = true
      if (Array.isArray(patentNos)) {
        this.patentNos = Array.from(patentNos)
      } else {
        this.patentNos = [patentNos.patentNo]
      }
      this.loadProject(year)
    },
    handleSubmit () {
      // if (!this.projectId || this.projectId === -1) {
      //   this.projectId = undefined
      //   this.$message.warning('请选择项目在进行操作')
      //   return
      // }
      this.$http.post('/patentDetail/relatedProject', { patentNos: this.patentNos, projectId: this.projectId })
        .then(res => {
          if (res.success && res.data) {
            this.visible = false
            this.$emit('ok')
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '关联项目失败')
          }
        })
    },
    selectChange (value) {
      this.projectId = value
    },
    loadProject (year) {
      return this.$http.get('/project/getSelectList', { params: { year, sign: 2 } })
        .then(res => {
          if (res.data != null && res.data.length > 0) {
            this.projectList = res.data
            return this.projectList
          }
        })
    },
    formatDate (dateStr) {
      if (dateStr) {
        return moment(dateStr).format('YYYY-MM-DD')
      }
      return ''
    },
    afterClose () {
      this.projectId = undefined
      this.data = {}
      this.ids = []
      this.patentNos = []
    }
  }
}
</script>

<style>
</style>
