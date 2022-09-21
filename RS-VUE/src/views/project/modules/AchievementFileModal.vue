<!--
 * @Author: zdf
 * @Date: 2021-11-18 17:42:39
 * @LastEditTime: 2022-02-24 10:06:14
 * @LastEditors: zdf
 * @Description: 成果管理文件modal
 * @FilePath: \RS-VUE\src\views\project\modules\AchievementFileModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="600"
    v-model="visible"
    :maskClosable="false"
    @ok="handleSubmit"
    :confirmLoading="confirmLoading"
    :afterClose="close"
  >
    <a-spin :spinning="spinning">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :md="24" :sm="24">
            <a-form-item label="项目阶段" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                :allowClear="true"
                placeholder="请选择项目阶段"
                v-decorator="['stageKey', {rules:[{required: false, message: '请选择项目阶段'}]}]"
              >
                <a-select-option
                  v-for="stage in stageList"
                  :key="stage.id"
                  :value="stage.stageKey"
                >{{ stage.stageType }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="24" :sm="24">
            <a-form-item label="转化结果" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                :allowClear="true"
                placeholder="请选择转化结果"
                v-decorator="['converResult', {initialValue: 0,rules:[{required: true, message: '请选择转化结果'}]}]"
              >
                <a-select-option v-for="(v,k) in converResults" :key="Number(k)" :value="Number(k)" >{{ v }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col :md="24" :sm="24">
            <a-form-item label="证明材料" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                showSearch
                :allowClear="true"
                placeholder="请选择证明材料"
                v-decorator="['listId', {rules:[{required: true, message: '请选择证明材料'}]}]"
              >
                <a-select-option
                  v-for="fileType in typeList"
                  :key="fileType.id"
                  :value="fileType.id"
                >{{ fileType.docName }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
        <!-- <a-row :gutter="24">
          <a-col :md="24" :sm="24">
            <a-form-item
              label="文件名"
              :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 18 } }"
            >
              <a-input
                v-decorator="['documentName', {rules:[{required: true, message: '请输入文件名'}]}]"
                placeholder="请输入文件名"
                @change="documentNameChange"
              />
            </a-form-item>
          </a-col>
        </a-row> -->
        <a-row :gutter="24">
          <a-col :md="24" :sm="24">
            <a-form-item
              label="附件:"
              :labelCol=" {xs: { span: 24 },sm: { span: 4 } }"
              :wrapperCol="{xs: { span: 24 },sm: { span: 18 } }"
            >
              <a-col :span="19">
                <a-input
                  v-decorator="['doc', {rules:[{required: true, message: '请上传附件'}]}]"
                  placeholder="请选择附件"
                  :disabled="true"
                />
              </a-col>
              <a-col :span="2">
                <a-upload
                  :fileList="fileList"
                  :multiple="false"
                  :beforeUpload="beforeUpload"
                >
                  <a-button>
                    <a-icon type="upload" />选择
                  </a-button>
                </a-upload>
              </a-col>
            </a-form-item>
          </a-col>
        </a-row>
        <div>
          <font>{{ message }}</font>
        </div>
      </a-form>
    </a-spin>
  </a-modal>
</template>
<script>
export default {
  name: 'AchievementFileModal',
  props: {
    converResults: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      title: '',
      spinning: false,
      fileList: [],
      uploadFile: undefined,
      message: '',
      visible: false,
      confirmLoading: false,
      form: this.$form.createForm(this),
      projectList: [],
      stageList: [],
      typeList: [],
      typeMap: {},
      labelCol: { xs: { span: 24 }, sm: { span: 4 } },
      wrapperCol: { xs: { span: 24 }, sm: { span: 18 } },
      achievementId: undefined,
      projectId: undefined,
      row: undefined,
      record: undefined,
      isEdit: false
    }
  },
  created () {
    this.getTypes()
  },
  methods: {
    show (row) {
      this.isEdit = false
      this.row = row
      this.title = `上传[${row.achievementName}]文件`
      this.achievementId = row.id
      if (this.projectId !== row.projectId) {
        this.projectId = row.projectId
        this.loadStage(row.projectId)
      }
      this.form.resetFields()
      this.confirmLoading = false
      this.visible = true
    },
    edit (row, record) {
      this.isEdit = true
      this.row = row
      this.record = record
      this.title = `上传[${row.achievementName}]文件`
      this.achievementId = row.id
      if (this.projectId !== row.projectId) {
        this.projectId = row.projectId
        this.loadStage(row.projectId)
      }
      this.$nextTick(() => {
        this.form.setFieldsValue({
          stageKey: (record.stageKey == null || record.stageKey === '') ? undefined : record.stageKey,
          listId: record.listId,
          doc: record.fileName,
          converResult: record.converResult
        })
      })
      this.confirmLoading = false
      this.visible = true
    },
    loadStage (projectId) {
      this.$http.get('/sysDocument/getStage', { params: { projectId } })
        .then(res => {
          if (res.data && res.success) {
            this.stageList = res.data
          }
        })
    },
    getTypes () {
      return this.$http.get('/sysDocument/getFileType')
        .then(res => {
          if (res.data != null && res.data.length > 0) {
            this.typeList = res.data
            // if (this.isNewReportCommit) {
            //   this.typeList = this.typeList.filter((item) => {
            //     return item.id * 1 !== 3 // 过滤查新类型 3表示查新类型
            //   })
            // }
            this.typeList.forEach(item => { this.typeMap[item.id] = item.docName })
            return this.typeList
          }
        })
    },
    beforeUpload (file) {
      if (file && file.size > 10485760) {
        this.$message.error(`上传文件不得超过10M。`)
        this.fileList = []
        return false
      }
      this.form.setFieldsValue({ doc: file.name })
      this.uploadFile = file
      return false
    },

    handleSubmit () {
      if (!this.isEdit) {
        this.handleAddSubmit()
      } else {
        this.handleEditSubmit()
      }
    },
    handleAddSubmit () {
      if (!this.uploadFile) {
        this.$message.warning('请选择附件')
        return
      }
      this.form.validateFields((errors, values) => {
        if (!errors) {
          const model = {
            stageKey: values.stageKey == null ? '' : values.stageKey,
            achievementId: this.achievementId,
            listId: values.listId,
            converResult: values.converResult
          }
          const param = new FormData()
          param.append('file', this.uploadFile)
          for (const key in model) {
            param.append(key, model[key])
          }
          const config = {
            // 添加请求头
            headers: { 'Content-Type': 'multipart/form-data' }
          }
          this.confirmLoading = true
          this.$http.post('/achievement/addFile', param, config).then(res => {
            if (res.success) {
              if (!this.row.files) {
                this.row.files = []
              }
              res.data.docName = this.typeMap[res.data.listId]
              this.row.files.push(res.data)
              this.row.fileCnt = this.row.fileCnt ? this.row.fileCnt + 1 : 1
              this.row.lastUploadTime = res.data.lastUpdateTime
              this.$message.success('添加成功')
              this.close()
            } else {
              this.$message.error(res.errorMessage || '添加失败')
            }
            return res
          }).catch(res => {
            this.$emit('error', res.message)
          }).finally(res => {
            this.visible = false
            this.confirmLoading = false
          })
        }
      })
    },
    handleEditSubmit () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          const model = {
            stageKey: values.stageKey == null ? '' : values.stageKey,
            achievementId: this.achievementId,
            listId: values.listId,
            converResult: values.converResult
          }
          const param = new FormData()
          // if (this.uploadFile != null) {
          param.append('file', this.uploadFile == null ? null : this.uploadFile)
          // }
          param.append('id', this.record.id)
          param.append('seq', this.record.seq)
          for (const key in model) {
            param.append(key, model[key])
          }
          const config = {
            headers: { 'Content-Type': 'multipart/form-data' }
          }
          this.confirmLoading = true
          this.$http.post('/achievement/updateFile', param, config).then(res => {
            if (res.success) {
              if (!this.row.files) {
                this.row.files = []
              }
              this.$message.success('更新成功')
              this.$emit('update', this.row)
              this.close()
            } else {
              this.$message.error(res.errorMessage || '更新失败')
            }
            return res
          }).catch(res => {
            this.$emit('error', res.message)
          }).finally(res => {
            this.visible = false
            this.confirmLoading = false
          })
        }
      })
    },
    close () {
      this.visible = false
      this.confirmLoading = false
      this.achievementId = undefined
      this.uploadFile = undefined
      this.fileList = []
    }
  }
}
</script>

<style scoped>
</style>
