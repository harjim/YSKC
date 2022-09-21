<template>
  <a-modal
    :title="title"
    style="top: 0px;"
    :width="1000"
    v-model="visible"
    :confirmLoading="confirmLoading"
    :maskClosable="false"
    @cancel="closeModal"
    :bodyStyle="{ maxHeight:'84vh', overflow: 'auto' }"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="48">
        <a-col :span="12">
          <a-form-item label="需求名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              :disabled="showDetail"
              v-decorator="['techName', {rules:[{required: !showDetail, message: '请输入需求名称'}]}]"
              placeholder="请输入需求名称"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="拟投资(万元)" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              :min="0"
              :disabled="showDetail"
              :precision="2"
              style="width:100%"
              v-decorator="['investment', {rules:[{required: !showDetail, message: '请输入拟投资金额'}]}]"
              placeholder="请输入拟投资金额"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              :disabled="showDetail"
              v-decorator="['linkName', {rules:[{required: !showDetail, message: '请输入联系人'}]}]"
              placeholder="请输入联系人"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="职位" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              :disabled="showDetail"
              v-decorator="['position', {rules:[{required: !showDetail, message: '请输入职位'}]}]"
              placeholder="请输入职位"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="联系号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              :disabled="showDetail"
              v-decorator="['linkTel', {rules:[{required: !showDetail, message: '请输入联系号码'}]}]"
              placeholder="请输入联系号码"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="邮箱" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input
              :disabled="showDetail"
              v-decorator="['linkEmail', {rules:[{required: !showDetail, message: '请输入邮箱'}]}]"
              placeholder="请输入邮箱"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :span="24">
          <a-form-item
            label="客户简介"
            :help="()=>{const r = form.getFieldValue('description'); return `(${!r? 0 : r.length > 300 ? 300 : r.length}/300)`}"
          >
            <a-textarea
              :disabled="showDetail"
              v-decorator="['description', {rules:[{required: false, message: '请输入客户简介'}]}]"
              placeholder="（文字300字以内，介绍企业基本信息、主要产品及发展方向等，另附照片2-3张，便于制作宣传材料）"
              :max-length="300"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item
            label="附件"
            :labelCol="{ sm: { span: 1 } }"
            :wrapperCol="{ sm: { span: 20 } }"
          >
            <a-upload
              :fileList="fileList"
              :multiple="true"
              @change="UploadHandleChange"
              :beforeUpload="beforeUpload"
              @preview="downloadfile"
            >
              <a-button :disabled="showDetail">
                <a-icon type="upload" />上传附件
              </a-button>
            </a-upload>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :span="24">
          <a-form-item label="所属技术领域">
            <a-checkbox-group
              v-decorator="['domainArr', {rules:[{required: !showDetail, message: '请选择所属技术领域'}]}]"
              name="checkboxgroup"
              :options="domainOptions"
              @change="v=>checkValue(v,'domain','domainArr')"
            />
            <br />
            <span>
              <span style="float:left;">
                <a-checkbox
                  :disabled="showDetail"
                  v-model="ck.domain.checked"
                  @change="handleChange('domain','domainArr')"
                >其他</a-checkbox>
              </span>
              <span style="float:left;width:60%">
                <a-input
                  :disabled="showDetail || !ck.domain.checked"
                  v-model="ck.domain.value"
                  placeholder="请说明"
                />
              </span>
            </span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :span="24">
          <a-form-item
            label="需求描述"
            :help="()=>{const r = form.getFieldValue('requirement'); return `(${!r? 0 : r.length > 300 ? 300 : r.length}/300)`}"
          >
            <a-textarea
              :disabled="showDetail"
              v-decorator="['requirement', {rules:[{required: !showDetail, message: '请输入需求描述'}]}]"
              placeholder="（精准描述所要解决的技术问题、可能使用的技术手段或研发内容、预期达到的技术效果等信息，方便匹配技术专家，多个技术需求可添加附页）"
              :max-length="300"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :span="24">
          <a-form-item label="合作方式">
            <a-checkbox-group
              v-decorator="['cooperateTypeArr', {rules:[{required: !showDetail, message: '请选择合作方式'}]}]"
              :options="cooperateTypeOptions"
              @change="v=>checkValue(v,'cooperateType','cooperateTypeArr')"
            />
            <br />
            <span>
              <span style="float:left;">
                <a-checkbox
                  :disabled="showDetail"
                  v-model="ck.cooperateType.checked"
                  @change="handleChange('cooperateType','cooperateTypeArr')"
                >其他</a-checkbox>
              </span>
              <span style="float:left;width:60%">
                <a-input
                  :disabled="showDetail || !ck.cooperateType.checked"
                  v-model="ck.cooperateType.value"
                  placeholder="请说明"
                />
              </span>
            </span>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="48">
        <a-col :span="24">
          <a-form-item style="margin-left: -20px;" label="希望合作的高校院所" :labelCol="{ sm: { span: 4 }}" :wrapperCol="{ sm: { span: 16 } }">
            <a-input
              :disabled="showDetail"
              v-decorator="['cooperateSchool', {rules:[{required: false}]}]"
              placeholder="请输入希望合作的高校院所"
            />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <template v-slot:footer>
      <a-button @click="closeModal">关闭</a-button>
      <a-button v-if="!showDetail" type="primary" @click="handleSubmit">确定</a-button>
    </template>
  </a-modal>
</template>

<script>
export default {
  name: 'TechRequiirementModal',
  data () {
    return {
      showDetail: false,
      ck: { domain: { checked: false, value: '' }, cooperateType: { checked: false, value: '' } },
      fileList: [],
      domainOptions: [
        { label: '电子信息', value: '电子信息', disabled: false },
        { label: '生物医药', value: '生物医药', disabled: false },
        { label: '新材料', value: '新材料', disabled: false },
        { label: '装备制造', value: '装备制造', disabled: false },
        { label: '能源环保', value: '能源环保', disabled: false },
        { label: '现代农业', value: '现代农业', disabled: false },
        { label: '医疗器材', value: '医疗器材', disabled: false }],
      cooperateTypeOptions: [
        { label: '技术开发', value: '技术开发', disabled: false },
        { label: '技术转让', value: '技术转让', disabled: false },
        { label: '技术服务', value: '技术服务', disabled: false },
        { label: '技术入股', value: '技术入股', disabled: false },
        { label: '共建载体', value: '共建载体', disabled: false }],
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      labelRow: {
        xs: { span: 24 },
        ms: { span: 2 }
      },
      wrapperRow: {
        xs: { span: 24 },
        ms: { span: 21 }
      },
      title: '',
      visible: false,
      confirmLoading: false,
      id: 0,
      form: this.$form.createForm(this),
      loading: false,
      currentYear: undefined
    }
  },
  methods: {
    add (currentYear) {
      this.currentYear = currentYear
      this.ck = { domain: { checked: false, value: '' }, cooperateType: { checked: false, value: '' } }
      this.title = '添加技术需求'
      this.form.resetFields()
      this.fileList = []
      this.visible = true
      this.id = -1
    },
    detail (record) {
      this.domainOptions.forEach(item => { item.disabled = true })
      this.cooperateTypeOptions.forEach(item => { item.disabled = true })
      this.showDetail = true
      this.form.resetFields()
      this.title = `查看技术需求[${record.techName}]`
      this.ck = {
        domain: { checked: record.domain.indexOf('其他') >= 0, value: record.otherDomain },
        cooperateType: { checked: record.cooperateType.indexOf('其他') >= 0, value: record.otherCooperateType }
      }
      record.domainArr = record.domain.split('，')
      record.cooperateTypeArr = record.cooperateType.split('，')
      this.id = record.id
      this.visible = true
      this.$nextTick(() => {
        this.$initForm(this.form, Object.assign(record, {}))
        if (record.filePath) {
          const temp = []
          record.filePath.split(',').forEach(item => {
            const name = item.substring(item.lastIndexOf('/') + 14, item.length)
            temp.push({
              uid: name,
              name,
              status: 'done',
              url: item
            })
          })
          this.fileList = temp
        }
      })
    },
    edit (record) {
      this.currentYear = undefined
      this.ck = {
        domain: { checked: record.domain.indexOf('其他') >= 0, value: record.otherDomain },
        cooperateType: { checked: record.cooperateType.indexOf('其他') >= 0, value: record.otherCooperateType }
      }
      record.domainArr = record.domain.split('，')
      record.cooperateTypeArr = record.cooperateType.split('，')
      this.title = `修改技术需求[${record.techName}]`
      this.form.resetFields()
      this.visible = true
      this.id = record.id
      this.$nextTick(() => {
        this.$initForm(this.form, Object.assign(record, {}))
        if (record.filePath) {
          const temp = []
          record.filePath.split(',').forEach(item => {
            const name = item.substring(item.lastIndexOf('/') + 14, item.length)
            temp.push({
              uid: name,
              name,
              status: 'done',
              url: item
            })
          })
          this.fileList = temp
        }
      })
    },
    downloadfile (file) {
      this.$exportData('/techRequirement/download', { id: this.id, fileName: file.name, path: file.url }, file.name, this.$message)
    },
    beforeUpload (file, key) {
      const param = new FormData()
      param.append('file', file)
      param.append('dir', '/requirement/')
      const config = {
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http.post('/techRequirement/upload', param, config).then(res => {
        if (res.success) {
          this.fileList.push({
            uid: res.data.fileName,
            name: res.data.fileName,
            status: 'done',
            url: res.data.filePath
          })
        }
      }).catch(res => {
      })
      return false
    },
    UploadHandleChange (file) {
      if (file.file.status === 'removed') {
        this.fileList = file.fileList
      }
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          const paths = []
          this.fileList.forEach(item => {
            paths.push(item.url)
          })
          values.cooperateType = values.cooperateTypeArr.join('，')
          values.domain = values.domainArr.join('，')
          delete values.cooperateTypeArr
          delete values.domainArr
          values.filePath = paths.join(',')
          values.otherDomain = this.ck.domain.value
          values.otherCooperateType = this.ck.cooperateType.value
          this.confirmLoading = true
          if (this.id === -1) {
            values.year = this.currentYear
            this.$http.post('/techRequirement/add', values)
              .then(res => {
                if (res.success && res.data) {
                  this.confirmLoading = false
                  this.$emit('ok', true)
                  this.closeModal()
                } else {
                  this.confirmLoading = false
                }
              })
          } else {
            values.id = this.id
            this.$http.post('/techRequirement/update', values)
              .then(res => {
                if (res.success && res.data) {
                  this.confirmLoading = false
                  this.$emit('ok', false)
                  this.closeModal()
                } else {
                  this.confirmLoading = false
                }
              })
          }
        }
      })
    },
    closeModal () {
      this.showDetail = false
      this.fileList = []
      this.domainOptions.forEach(item => { item.disabled = false })
      this.cooperateTypeOptions.forEach(item => { item.disabled = false })
      this.confirmLoading = false
      this.visible = false
    },
    handleChange (key, arrkey) {
      var arr = this.form.getFieldValue(arrkey)
      arr = arr !== undefined ? arr : []
      if (this.ck[key].checked) {
        arr.push('其他')
      } else {
        this.ck[key].value = ''
        const index = arr.indexOf('其他')
        if (index >= 0) {
          arr.splice(index, 1)
        }
      }
      const value = {}
      value[arrkey] = { value: arr }
      this.form.setFields(value)
    },
    checkValue (v, key, arrkey) {
      if (this.ck[key].checked) {
        v.push('其他')
      }
      const value = {}
      value[arrkey] = { value: v }
      this.form.setFields(value)
    }
  }
}
</script>

<style scoped>
</style>
