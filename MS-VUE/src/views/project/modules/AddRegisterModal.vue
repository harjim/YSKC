<!--
 * @Author: ldx
 * @Date: 2020-08-06 11:35:22
 * @LastEditTime: 2021-06-11 14:29:18
 * @LastEditors: ldx
 * @Description: 添加技改备案信息
 * @FilePath: \MS-VUE\src\views\project\modules\AddRegisterModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="850"
    :visible="isVisible"
    :afterClose="resetData"
    :maskClosable="false"
    @cancel="isVisible = false"
    @ok="onHandleSubmit">
    <a-form :form="form">
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="备案项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入项目名称" v-decorator="['registerName',{rules: [{ required: true, whitespace: true, message: '不能只输入空格' }] }]"></a-input>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="备案区间" :required="true" :labelCol="labelCol" :wrapperCol="wrapperCol" style="margin-bottom: 0;">
            <a-form-item :style="{ display: 'inline-block', width: 'calc( 50% - 12px )' }">
              <a-date-picker :disabledDate="startDateDisabled" v-decorator="['beginDate',{ rules: [{ required: true, message: '请输入起始日期'}] }]" />
            </a-form-item>
            <span :style="{ display: 'inline-block', width: '12px', textAlign: 'center' }" >
              -
            </span>
            <a-form-item :style="{ display: 'inline-block', width: 'calc( 50% - 12px )' }">
              <a-date-picker :disabledDate="endDateDisabled" v-decorator="['endDate',{ rules: [{ required: true, message: '请输入结束日期'}] }]" />
            </a-form-item>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="备案证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入备案证号" v-decorator="['registerNo', { rules:[{ required: true, whitespace: true, message: '请输入备案证号'}] }]" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="备案证原件所属地" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入备案证原件所属地" v-decorator="['originalPlace', { rules:[{ required: true, whitespace: true, message: '请输入备案证原件所属地' }] }]" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12">
          <a-form-item label="备案金额（万）" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              @change="onAmount"
              :min="0"
              :precision="2"
              :max="999999999999999999"
              :style="{ width: '100%' }"
              placeholder="请输入备案金额（万）"
              v-decorator="['amount', { rules:[{ required: true, message: '请输入备案金额'}] }]" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="已投资金额（万）" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input-number
              @change="onInvestAmount"
              :min="0"
              :precision="2"
              :max="999999999999999999"
              :style="{ width: '100%' }"
              placeholder="请输入已投资金额（万）"
              v-decorator="['investAmount', { rules:[{ required: true, message: '请输入已投资金额'}] }]" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="12" >
          <a-form-item label="预计完工时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker v-decorator="['finishDate']" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="完工情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
            {{ completion }}%
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24" v-if="$auth('project:register:upload')">
        <a-col :span="12">
          <a-form-item label="备案证扫描件" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-upload
              :disabled="isUploadDisabled"
              :fileList="uploadFile.registerNos"
              :multiple="true"
              :remove="file => handleRemove(file,'main')"
              :beforeUpload="(file,files) => beforeUpload(file,files,'main')"
              :showUploadList="{ showRemoveIcon: status > 0 && status !==2 ? false : true}"
              @preview="downloadfile"
            >
              <a-button>
                <a-icon type="upload" />选择附件
              </a-button>
            </a-upload>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label="备案文件" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-upload
              :disabled="isUploadDisabled"
              :fileList="uploadFile.registerFiles"
              :multiple="true"
              :remove="file => handleRemove(file,'vice')"
              :beforeUpload="(file,files) => beforeUpload(file,files,'vice')"
              @preview="downloadfile"
            >
              <a-button>
                <a-icon type="upload" />选择附件
              </a-button>
            </a-upload>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="变更情况" :labelCol="{ sm: 4, xs: 4 }" :wrapperCol="{ sm: 20, xs: 20 }" :extra="onComputeTextarea('changeContent',200)">
            <a-textarea :rows="2" placeholder="请输入变更情况" v-decorator="['changeContent',{rules:[{ whitespace: true, message: '请输入变更情况' }] }]" />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="备注" :labelCol="{ sm: 4, xs: 4 }" :wrapperCol="{ sm: 20, sx: 20 }" :extra="onComputeTextarea('remark',200)">
            <a-textarea :rows="2" placeholder="请输入备注" v-decorator="['remark']" />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
import moment from 'moment'
import { replaceChar } from '@/utils/util'
export default {
  name: '',
  data () {
    return {
      title: '添加备案信息',
      isAdd: true, // 重要通过此标识判断添加或修改
      isVisible: false,
      form: this.$form.createForm(this),
      isDocking: false,
      isUploadDisabled: false,
      status,
      watchInvestAmount: 0,
      watchAmount: 0,
      projectId: undefined,
      id: undefined, // 备案ID
      record: {},
      uploadFile: {
        registerNos: [],
        registerFiles: [],
        registerMainPath: '',
        registerVicePath: ''
      },
      changeOptions: [
        { label: '否', value: 0 },
        { label: '是', value: 1 }
      ],
      dockOptions: [
        { label: '未对接', value: 0 },
        { label: '待确定', value: 2 },
        { label: '已对接', value: 1 }
      ],
      reportOptions: [
        { label: '否', value: 0 },
        { label: '待确定', value: 2 },
        { label: '是', value: 1 }
      ],
      schemeOptions: [
        { label: '待确定', value: 1 },
        { label: '已确定', value: 0 }
      ],
      labelCol: {
        sm: 9,
        xs: 9
      },
      wrapperCol: {
        sm: 15,
        xm: 15
      },
      d: 0
    }
  },
  computed: {
    /**
     * @description: 计算完工进度
     * @param {nul}
     * @return {number}
     */
    completion () {
      if (this.watchInvestAmount && this.watchAmount) {
        return ((this.watchInvestAmount / this.watchAmount).toFixed(2)) * 100
      }
      return 0
    }
  },
  methods: {
    moment,
    /**
     * @description: 添加
     * @param record { type: Object}
     * @param isAdd { type: Boolean}
     * @return null
     */
    add (record, isAdd) {
      this.resetData()
      this.title = '添加备案信息'
      this.isAdd = isAdd
      this.isVisible = true
      this.projectId = record.id
    },
    /**
     * @description: 编辑
     * @param record { type: Object }
     * @param isAdd { type: Boolean }
     * @return null
     */
    edit (record, isAdd) {
      this.resetData()
      this.title = '编辑备案信息'
      this.record = record
      this.projectId = record.projectId
      this.id = record.id
      this.isAdd = isAdd
      this.isVisible = true
      this.watchInvestAmount = record.investAmount
      this.watchAmount = record.amount
      this.uploadFile.registerNos = this.getFiles(record.filePath, 1)
      this.uploadFile.registerFiles = this.getFiles(record.registerFile, 2)
      this.uploadFile.registerMainPath = record.filePath ? record.filePath : ''
      this.uploadFile.registerVicePath = record.registerFile ? record.registerFile : ''
      this.$nextTick(() => {
        this.$initForm(this.form, record, ['beginDate', 'endDate'])
        if (record.finishDate) {
          this.form.setFieldsValue({ finishDate: this.moment(record.finishDate) })
        }
      })
    },
    /**
     * @description: 重置变量值
     * @param null
     * @return null
     */
    resetData () {
      this.form.resetFields()
      this.isVisible = false
      // this.isChange = false
      this.isDocking = false
      this.watchInvestAmount = 0
      this.watchAmount = 0
      this.projectId = undefined
      this.id = undefined
      this.isAdd = false
      this.record = {}
      this.uploadFile = {
        registerNos: [],
        registerFiles: [],
        registerMainPath: '',
        registerVicePath: ''
      }
    },
    /**
     * @description: 表单提交事件
     * @param null
     * @return null
     */
    onHandleSubmit () {
      this.form.validateFields((error, values) => {
        if (!error) {
          values.projectId = this.projectId
          if (this.id) values.id = this.id
          if (!this.$auth('project:register:upload')) { // 没有上传权限，不去请求上传接口
            this.handleFormSubmit(values)
          } else {
            this.handleUpload().then((res) => {
              if (res) {
                this.handleFormSubmit(values)
              } else {
                this.$message.error('文件上传失败,请联系管理员')
              }
            }).catch(error => {
              this.$message.error('操作失败,请联系管理员. 错误信息：' + error.message)
            })
          }
        }
      })
    },
    /**
     * @description: 备案起始日期限制
     * @param v { type: Date }
     * @return Boolean { type: Boolean }
     */
    startDateDisabled (v) {
      const end = this.form.getFieldValue('endDate')
      return end ? v > end : false
    },
    /**
     * @description:  备案结束日期限制
     * @param v { type: Data }
     * @return Boolean { type: Boolean }
     */
    endDateDisabled (v) {
      const start = this.form.getFieldValue('beginDate')
      return start ? v < start : false
    },
    /**
     * @description: 计算与控制文本域的个数
     * @param fieldName { type: String }
     * @param limitNumber {type:String }
     * @return String { type: String }
     */
    onComputeTextarea (fieldName, limitNumber = 200) {
      const content = this.form.getFieldValue(fieldName)
      const contentLenght = content ? content.length : 0
      if (contentLenght > limitNumber) {
        const obj = {}
        obj[fieldName] = { value: content.substring(0, limitNumber) }
        this.form.setFields(obj)
      }
      return `(${contentLenght}/${limitNumber})`
    },
    /**
     * @description: 监视 已投金额变化
     * @param v { type: number|string }
     * @return null
     */
    onInvestAmount (v) {
      this.watchInvestAmount = v
    },
    /**
     * @description: 监视 备案金额变化
     * @param v { type: number|string }
     * @return null
     */
    onAmount (v) {
      this.watchAmount = v
    },
    /**
     * @description: 上传前的钩子函数
     * @param file { type:File }
     * @param files { type: File }
     * @param type { type:String }
     * @return Boolean { type: Boolean }
     */
    beforeUpload (file, files, type = 'main') {
      if (type === 'main') {
        this.uploadFile.registerNos.push(file)
      } else if (type === 'vice') {
        this.uploadFile.registerFiles.push(file)
      }
      return false
    },
    /**
     * @description:  下载文件
     * @param file { type:File|Object }
     * @return null
     */
    downloadfile (file) {
      if (file.url && file.sign) {
        this.$exportData('/projectRegister/download', { filePath: file.url, sign: file.sign, registerId: this.record.id, fileName: file.name }, file.name, this.$message)
      }
    },
    /**
     * @description:  执行移除上传列表文件事件
     * @param file { type:File|Object }
     * @return null
     */
    handleRemove (file, type) {
      if (type === 'main') {
        this.uploadFile.registerNos = this.getFileList(file, 'registerMainPath', 'registerNos')
      } else if (type === 'vice') {
        this.uploadFile.registerFiles = this.getFileList(file, 'registerVicePath', 'registerFiles')
      }
    },
    /**
     * @description:  执行文件上传服务器
     * @param null
     * @return null
     */
    handleUpload () {
      let param = new FormData()
      param.append('dir', '/registerFile/')
      // 给Param添加需要上传的文件
      param = this.handleUploadParam(param, 'registerNos', 'file')
      param = this.handleUploadParam(param, 'registerFiles', 'registerFile')
      // 设置请求头
      const config = { headers: { 'Content-Type': 'multipart/form-data' } }
      return this.$http.post('/projectRegister/upload', param, config).then((res) => {
        if (res.data && res.success) {
          const promise = Promise.resolve(true)
          if (!res.data) { return promise }
          this.uploadFile.registerMainPath = this.updateFilePath(res.data, 'filePath', 'registerMainPath') // 备案证扫描件
          this.uploadFile.registerVicePath = this.updateFilePath(res.data, 'registerFile', 'registerVicePath') // 备案文件
          return promise
        }
      })
    },
    /**
     * @description: 获取文件名称
     * @param  filePathStrs { type:String }
     * @param  sign { type:number, value: 1=>下载备案扫描证; 2=>下载备案文件 }
     * @return Array { type:Array }
     */
    getFiles (filePathStrs, sign) {
      if (!filePathStrs) return []
      const filePaths = filePathStrs.split(',')
      const files = []
      filePaths.forEach(item => {
        const temps = item.split('/')
        const fileName = temps[temps.length - 1]
        files.push({
          uid: fileName,
          name: fileName,
          status: 'status', // 状态有：uploading done error removed
          url: item,
          sign
        })
      })
      return files
    },
    /**
     * @description: 删除字符串里面的指定路径
     * @param paths { type: String }
     * @return delPath { type:String }
     */
    delPath (paths, delPath) {
      let newPaths = ''
      paths.split(',').forEach(item => {
        if (!(item === delPath)) {
          newPaths += item + ','
        }
      })
      return newPaths ? replaceChar(',', '', newPaths, 'firstOrLast') : ''
    },
    /**
     * @description: 给Param添加需要上传的文件
     * @param FromData { type: FromData }
     * @param field  { type: String }
     * @param  paramName { type: String }
     * @return FromData  { type:FromData }
     */
    handleUploadParam (FromData, field, paramName) {
      this.uploadFile[field].forEach(item => {
        if (!(item.sign || item.url)) {
          FromData.append(paramName, item)
        }
      })
      return FromData
    },
    /**
     * @description: 执行表单提交接口
     * @param values { type: Object }
     * @return null
     */
    handleFormSubmit (values) {
      values.filePath = this.uploadFile.registerMainPath
      values.registerFile = this.uploadFile.registerVicePath
      this.$http.post(this.isAdd ? '/projectRegister/add' : '/projectRegister/edit', values).then((res) => {
        if (res.data && res.success) {
          // let postData
          this.$message.success('操作成功')
          this.isVisible = false
          this.$emit('ok', true, values)
        } else {
          this.$message.error(res.errorMessage)
        }
      }).catch((error) => {
        this.$message.error(error.message)
      })
    },
    /**
     * @description: 获取删除后的列表
     * @param file { type: Object }
     * @param pathFieldName { type: String 路径属性名}
     * @param fieldListName { type: String 列表属性名}
     * @return Array { type: Array }
     */
    getFileList (file, pathFieldName, fieldListName) {
      if (file.sign || file.url) {
        if (this.uploadFile[pathFieldName]) {
          this.uploadFile[pathFieldName] = this.delPath(this.uploadFile[pathFieldName], file.url)
        }
      }
      const index = this.uploadFile[fieldListName].indexOf(file)
      const newFileList = this.uploadFile[fieldListName].slice()
      newFileList.splice(index, 1)
      return newFileList
    },
    /**
     * @description: 更新文件路径
     * @param data { type:Object }
     * @param dataFieldName { type: String }
     * @param updatePathFieldName { type: String}
     * @return String { type: String }
     */
    updateFilePath (data, dataFieldName, updatePathFieldName) {
      let tempPath = ''
      if (data[dataFieldName] && data[dataFieldName].length) {
        data[dataFieldName].forEach(item => {
          tempPath += ',' + item
        })
      }
      this.uploadFile[updatePathFieldName] += tempPath
      return replaceChar(',', '', this.uploadFile[updatePathFieldName], 'firstOrLast')
    }
  }
}
</script>
<style lang='less' scoped>
</style>
