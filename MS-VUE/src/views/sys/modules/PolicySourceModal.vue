<template>
  <a-modal
    :title="title"
    style="top:20px;"
    :width="1000"
    v-model="visible"
    :footer="null"
    :confirmLoading="loading"
    :maskClosable="false"
  >
    <div style="width:96%;padding-left:3%">
      <a-steps :current="current">
        <a-step
          v-for="item in steps"
          :key="item.title"
          :title="item.title"
          :description="item.description"
        />
      </a-steps>
    </div>
    <div style="padding-top:30px;width:88%;padding-left:3%;">
      <a-form v-show="current === 0" :form="formSept1">
        <a-row :gutter="48">
          <a-col :span="24">
            <a-form-item label="来源" :labelCol="rowLabel" :wrapperCol="rowWrapper">
              <a-input
                v-decorator="['sname', {rules:[{required: true, message: '请输入来源'}]}]"
                placeholder="请输入来源"
              />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="域名" :labelCol="rowLabel" :wrapperCol="rowWrapper">
              <a-input
                v-decorator="['domain', {rules:[{required: true, message: '请输入域名'}]}]"
                placeholder="请输入域名"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="48">
          <a-col :span="24">
            <a-form-item label="栏目" :labelCol="rowLabel" :wrapperCol="rowWrapper">
              <a-input
                v-decorator="['stype', {rules:[{required: true, message: '请输入栏目'}]}]"
                placeholder="请输入栏目"
              />
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="栏目地址" :labelCol="rowLabel" :wrapperCol="rowWrapper">
              <a-input
                placeholder="请输入栏目地址"
                v-decorator="['typeUrl', {rules:[{required: true, message: '请输入栏目地址'}]}]"
                @blur="checkTypeUrl"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <a-form v-show="current === 1" :form="formSept2">
        <a-row :gutter="48">
          <a-col :span="24">
            <a-form-item label="请求地址" :labelCol="rowLabel" :wrapperCol="rowWrapper">
              <a-input
                v-decorator="['url', {rules:[{required: true, message: '请输入请求地址'}]}]"
                placeholder="请输入请求地址"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="请求方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="['method', {rules:[{required: false, message: '请选择请求方式'}],initialValue:'GET'}]"
                @change="methodChange"
              >
                <a-select-option value="GET">GET</a-select-option>
                <a-select-option value="POST">POST</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="表单数据" :labelCol="rowLabel" :wrapperCol="rowWrapper">
              <a-textarea
                v-decorator="['formData', {rules:[{required: reqMethod === 'POST', message: '请输入表单数据(json格式)'}]}]"
                placeholder="请输入表单数据(json格式)"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="48">
          <a-col :span="12">
            <a-form-item label="分页类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="['pageType', {rules:[{required: false, message: '请选择分页类型'}],initialValue:'1'}]"
              >
                <a-select-option value="1">1</a-select-option>
                <a-select-option value="2">2</a-select-option>
                <a-select-option value="3">3</a-select-option>
                <a-select-option value="4">4</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="分页数" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number
                v-decorator="['pageMax', {rules:[{required: true, message: '请输入分页数'}]}]"
                placeholder="请输入分页数"
                :min="1"
                :precision="0"
                :max="99"
                style="width:100%;"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="48">
          <a-col :span="12">
            <a-form-item label="分页解析" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="['dataParse', {rules:[{required: false, message: '请选择分页解析'}],initialValue:'RE'}]"
                @change="dataParseChange"
              >
                <a-select-option value="RE">RE</a-select-option>
                <a-select-option value="XPATH">XPATH</a-select-option>
                <a-select-option value="JSON">JSON</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="分页表达式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['pageParse', {rules:[{required: dataParse !== 'JSON', message: '请输入分页表达式'}]}]"
                placeholder="请输入分页表达式"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="48">
          <a-col :span="24">
            <a-form-item label="JSON结构" :labelCol="rowLabel" :wrapperCol="rowWrapper">
              <a-textarea
                v-decorator="['jsonFormat', {rules:[{required: dataParse === 'JSON', message: jsonFormatMsg}]}]"
                :placeholder="jsonFormatMsg"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <a-form v-show="current === 2" :form="formSept3">
        <a-row :gutter="48">
          <a-col :span="12">
            <a-form-item label="时间格式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['dateFormat', {rules:[{required: true, message: '请输入时间格式（如：%Y-%m-%d)'}]}]"
                placeholder="请输入时间格式（如：%Y-%m-%d)"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="内容解析" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                v-decorator="['dataContent', {rules:[{required: dataParse !== 'JSON', message: '请选择内容解析'}]}]"
              >
                <a-select-option value="RE">RE</a-select-option>
                <a-select-option value="XPATH">XPATH</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="标题表达式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['titleParse', {rules:[{required: dataParse !== 'JSON', message: '请输入标题表达式'}]}]"
                placeholder="请输入标题表达式"
              />
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="日期表达式" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-decorator="['dateParse', {rules:[{required: dataParse !== 'JSON', message: '请输入日期表达式'}]}]"
                placeholder="请输入日期表达式"
              />
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <div style="padding-top:20px;padding-left:40%;">
        <span v-show="current < 2" style="padding-left:18px;">
          <a-button type="primary" @click="submitSept(current)">下一步</a-button>
        </span>
        <span v-show="current === 2" style="padding-left:18px;">
          <a-button type="primary" @click="submitSept(current)">完成</a-button>
        </span>
        <span v-show="current > 0" style="padding-left:18px;">
          <a-button type="primary" @click="current --">上一步</a-button>
        </span>
      </div>
    </div>
  </a-modal>
</template>

<script>
export default {
  name: 'PolicySourceModal',
  data () {
    return {
      steps: [
        { title: '基本信息', description: '输入来源基础信息' },
        { title: '内容解析', description: '配置分页解析及请求方式' },
        { title: '完成', description: '配置内容解析及表达式' }
      ],
      current: 0,
      visible: false,
      loading: false,
      title: '',
      formSept1: this.$form.createForm(this),
      formSept2: this.$form.createForm(this),
      formSept3: this.$form.createForm(this),
      id: undefined,
      dataParse: '',
      reqMethod: '',
      jsonFormatMsg: '请输入JSON结构(json) 格式： {"keys":["parent","parent2","parent3" ...],"url": "path","title": "name","issueDate":"lastTime","isTimeStamp": "False | True"}',
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      },
      rowLabel: {
        xs: 24,
        sm: 3
      },
      rowWrapper: {
        xs: 24,
        sm: 21
      },
      unchecked: false,
      valueArr: []
    }
  },
  watch: {
    dataParse (dParse) {
      if (dParse !== 'JSON') {
        this.formSept2.setFields({ 'jsonFormat': { value: '' } })
      }
    },
    reqMethod (newMethod) {
      if (newMethod !== 'POST') {
        this.formSept2.setFields({ 'formData': { value: '' } })
      }
    }
  },
  methods: {
    add () {
      this.id = undefined
      this.title = '添加来源'
      this.current = 0
      this.formSept1.resetFields()
      this.formSept2.resetFields()
      this.formSept3.resetFields()
      this.visible = true
    },
    edit (record) {
      this.current = 0
      this.title = `修改政策[${record.sname}-${record.stype}]`
      this.visible = true
      this.$http.get('/policySource/getSource', { params: { sourceId: record.id } }).then(res => {
        var row = {}
        this.id = undefined
        if (res.success && res.data) {
          row = res.data
          this.id = row.id
        }
        this.$nextTick(() => {
          this.$initForm(this.formSept1, { sname: row.sname, domain: row.domain, stype: row.stype, typeUrl: row.typeUrl })
          this.$initForm(this.formSept2, {
            url: row.url, method: row.method, formData: row.formData, pageType: row.pageType, pageMax: row.pageMax, dataParse: row.dataParse, pageParse: row.pageParse, jsonFormat: row.jsonFormat
          })
          this.$initForm(this.formSept3, { dateFormat: row.dateFormat, dataContent: row.dataContent, titleParse: row.titleParse, dateParse: row.dateParse })
        })
      })
    },
    checkTypeUrl () {
      const typeUrl = this.formSept1.getFieldValue('typeUrl')
      if (!typeUrl || typeUrl.trim() === '') {
        return
      }
      this.$http.get('/policySource/checkTypeUrl', { params: { typeUrl: typeUrl, sourceId: this.id } }).then(res => {
        if (!res.success || !res.data) {
          this.unchecked = true
          this.formSept1.setFields({ 'typeUrl': { value: typeUrl, errors: [new Error('已存在相同类型地址，请输入其他类型地址。')] } })
        } else {
          this.unchecked = false
          this.formSept1.setFields({ 'typeUrl': { value: typeUrl } })
        }
      })
    },
    methodChange (v) {
      this.reqMethod = v
    },
    dataParseChange (v) {
      this.dataParse = v
    },
    submitSept (current) {
      if (this.unchecked) {
        return
      }
      const form = current === 0 ? this.formSept1 : current === 1 ? this.formSept2 : this.formSept3
      form.validateFields((error, values) => {
        if (!error) {
          this.valueArr[current] = values
          if (current === 2) {
            this.loading = true
            const submitData = Object.assign(this.valueArr[0], this.valueArr[1], this.valueArr[2])
            if (this.id) {
              submitData.id = this.id
              this.$http.post('/policySource/update', submitData).then(res => {
                if (res.success && res.data) {
                  this.$emit('ok', false)
                  this.$message.success('编辑成功')
                  this.loading = false
                  this.visible = false
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '编辑失败')
                }
              })
            } else {
              this.$http.post('/policySource/add', submitData).then(res => {
                if (res.success && res.data) {
                  this.$emit('ok', true)
                  this.$message.success('添加成功')
                  this.loading = false
                  this.visible = false
                } else {
                  this.$message.error(res.errorMessage ? res.errorMessage : '添加失败')
                }
              })
            }
            this.loading = false
          } else {
            this.current++
          }
        }
      })
    }
  }
}
</script>

<style>
.divContent {
  height: 350px;
}
</style>
