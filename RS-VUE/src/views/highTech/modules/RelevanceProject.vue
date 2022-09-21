<!--
 * @Author: ldx
 * @Date: 2021-05-31 08:28:54
 * @LastEditTime: 2021-06-01 10:14:23
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\highTech\modules\RelevanceProject.vue
-->
<template>
  <a-modal
    :title="title"
    :width="500"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <a-form :form="form">
      <a-form-item
        label="项目"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol">
        <a-select placeholder="请选择关联项目" v-model="selectedId">
          <a-select-option v-for=" (project) in projects" :key="project.id">
            {{ project.rdTitle }} - {{ project.pname }}
          </a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
import { getProjects } from '@/api/highTech/highTech'
export default {
  name: 'RelevanceProject',
  data () {
    return {
      isVisible: false,
      title: '',
      form: this.$form.createForm(this),
      record: undefined,
      projects: [],
      projectMap: {},
      selectedId: undefined,
      chooseProjects: [],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 3 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 21 }
      }
    }
  },
  methods: {
    async show (record, chooseProjects, title = '关联项目') {
      this.title = title
      this.record = record
      const result = await this.handleGetProjects(record.id, chooseProjects)
      this.isVisible = result
    },
    afterClose () {
      this.record = undefined
      this.selectedId = undefined
      this.projectMap = []
      this.projects = []
      this.form.resetFields()
    },
    handleGetProjects (highTechId, chooseProjects) {
      return getProjects({ highTechId }).then(response => {
        if (response.success && response.data) {
          const data = response.data
          const chooseIds = []
          chooseProjects.forEach(item => { chooseIds.push(item.id) })
          this.projects = data.filter(item => { return !chooseIds.includes(item.id) })
          this.projects.forEach(item => { this.projectMap[item.id] = item })
          return Promise.resolve(true)
        } else {
          this.$message.error(response.errorMessage || '初始化项目列表异常！')
          return Promise.resolve(false)
        }
      }).catch(error => {
        this.$message.error(error.message || '初始化项目列表异常，请联系管理员！')
        return Promise.resolve(false)
      })
    },
    handleSubmit () {
      this.$emit('selected', this.projectMap[this.selectedId])
      this.isVisible = false
    }
  }
}
</script>
<style lang='less' scoped>
</style>
