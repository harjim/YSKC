<!--
 * @Author: ldx
 * @Date: 2021-03-18 09:02:10
 * @LastEditTime: 2021-10-20 09:21:07
 * @LastEditors: zdf
 * @Description: 基本信息
 * @FilePath: \RS-VUE\src\views\tech\Beian\BaseInfo.vue
-->
<template>
  <a-card class="container" :bodyStyle="{ height: '100%', padding: '12px', overflow: 'auto' }">
    <a-spin id="spin" :spinning="spinning" tip="加载中...">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-if="saveAuth"
                placeholder="项目名称"
                v-decorator="['pname', { rules: [{ required: true, message: '项目名称', whitespace: true }] }]"
              ></a-input>
              <template v-else>{{ beianInfo.pname ? beianInfo.pname : ' -' }}</template>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item
              :labelCol="labelCol"
              :wrapperCol="wrapperCol"
              :required="true"
              style="margin-bottom:0;"
              label="项目区间"
            >
              <template v-if="saveAuth">
                <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-date-picker
                    style="width: 100%;"
                    :disabledDate="disabledBeginDate"
                    format="YYYY-MM-DD"
                    placeholder="开始日期"
                    v-decorator="['beginDate', { rules: [{ required: true, message: '请选择开始日期' }] }]"
                  />
                </a-form-item>
                <span :style="{ display: 'inline-block', width: '24px', textAlign: 'center' }">
                  -
                </span>
                <a-form-item :style="{ display: 'inline-block', width: 'calc(50% - 12px)' }">
                  <a-date-picker
                    style="width: 100%;"
                    :disabledDate="disabledEndDate"
                    format="YYYY-MM-DD"
                    placeholder="结束日期"
                    v-decorator="['endDate', { rules: [{ required: true, message: '请选择结束日期' }] }]"
                  />
                </a-form-item>
              </template>
              <template v-else>
                {{ beianInfo.beginDate ? moment(beianInfo.beginDate).format('YYYY-MM-DD') : ' -' }} -
                {{ beianInfo.endDate ? moment(beianInfo.endDate).format('YYYY-MM-DD') : ' -' }}
              </template>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="备案单位名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-if="saveAuth"
                placeholder="请输入备案单位名称"
                v-decorator="[
                  'applicant',
                  { rules: [{ required: true, message: '请输入备案单位名称', whitespace: true }] }
                ]"
              ></a-input>
              <template v-else>{{ beianInfo.applicant ? beianInfo.applicant : ' -' }}</template>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="签发日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-date-picker
                v-if="saveAuth"
                style="width: 100%"
                v-decorator="['beianDate', { rules: [{ required: true, message: '请选择签发日期' }] }]"
              />
              <template v-else>{{
                beianInfo.beianDate ? moment(beianInfo.beianDate).format('YYYY-MM-DD') : ' -'
              }}</template>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="备案证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-if="saveAuth"
                placeholder="备案证号"
                v-decorator="['beianNo', { rules: [{ required: true, message: '请输入备案证号', whitespace: true }] }]"
              ></a-input>
              <template v-else>{{ beianInfo.beianNo ? beianInfo.beianNo : ' -' }}</template>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="项目编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-if="saveAuth"
                placeholder="请输入项目编号"
                v-decorator="[
                  'projectNo',
                  { rules: [{ required: true, message: '请输入项目编号', whitespace: true }] }
                ]"
              ></a-input>
              <template v-else>{{ beianInfo.beianNo ? beianInfo.beianNo : ' -' }}</template>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="备案账号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-if="saveAuth"
                placeholder="请输入备案账号"
                v-decorator="[
                  'accountName',
                  { rules: [{ required: true, message: '请输入备案账号', whitespace: true }] }
                ]"
              ></a-input>
              <template v-else>{{ beianInfo.beianNo ? beianInfo.beianNo : ' -' }}</template>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="备案密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input
                v-if="saveAuth"
                placeholder="请输入备案密码"
                v-decorator="[
                  'accountPassword',
                  { rules: [{ required: true, message: '请输入备案密码', whitespace: true }] }
                ]"
              ></a-input>
              <template v-else>{{ beianInfo.beianNo ? beianInfo.beianNo : ' -' }}</template>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="24">
            <a-form-item label="建设地点" :labelCol="labelCol24" :wrapperCol="wrapperCol24">
              <MultipleAddress
                v-if="saveAuth"
                style="width: 100%;"
                v-decorator="[
                  'constructionPlace',
                  { rules: [{ required: true, message: '请输入项目建设地点', whitespace: true }] }
                ]"
              />
              <template v-else>{{ beianInfo.constructionPlace ? beianInfo.constructionPlace : ' -' }}</template>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="24">
            <a-form-item label="备案主要内容" :labelCol="labelCol24" :wrapperCol="wrapperCol24">
              <a-textarea
                v-if="saveAuth"
                placeholder="备案主要内容"
                v-decorator="['content', { rules: [{ required: true, message: '备案项目名称', whitespace: true }] }]"
                rows="3"
              ></a-textarea>
              <template v-else>{{ beianInfo.content ? beianInfo.content : ' -' }}</template>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="24">
            <a-form-item label="备案经费" required :labelCol="labelCol24" :wrapperCol="wrapperCol24">
              <BaseInfoTable :record="beianInfo" :saveAuth="saveAuth" />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="12">
            <a-form-item label="备案证扫描件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-upload
                :disabled="!saveAuth"
                :fileList="files['scanFilePath']"
                :multiple="false"
                @preview="download('scanFilePath')"
                :beforeUpload="file => beforeUpload(file, 'scanFilePath')"
                @change="file => handleChange(file, 'scanFilePath')"
                v-decorator="[
                  'scanFilePathUpload',
                  { rules: [{ required: true, type: 'array', transform: transformUpload, message: '请上传附件' }] }
                ]"
              >
                <a-button> <a-icon type="upload" />上传</a-button>
              </a-upload>
            </a-form-item>
          </a-col>
          <a-col span="12">
            <a-form-item label="备案文件" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-upload
                id="122asdfa-asf1"
                :disabled="!saveAuth"
                :fileList="files['filePath']"
                :multiple="true"
                @preview="multipleDownload"
                :beforeUpload="file => multipleBeforeUpload(file, 'filePath')"
                @change="file => handleChange(file, 'filePath')"
                v-decorator="[
                  'filePathUpload',
                  { rules: [{ required: true, type: 'array', transform: transformUpload, message: '请上传附件' }] }
                ]"
              >
                <a-button> <a-icon type="upload" />上传</a-button>
              </a-upload>
            </a-form-item>
          </a-col>
        </a-row>
        <a-row :gutter="24">
          <a-col span="24">
            <a-form-item required label="变更签发" :labelCol="labelCol24" :wrapperCol="wrapperCol24">
              <vxe-grid
                show-overflow
                highlight-hover-row
                auto-resize
                resizable
                header-align="center"
                :data="beianInfo.changedList"
              >
                <vxe-table-column field="changeLetterDate" title="变更函签发时间" width="140">
                  <template #default="{ row, $rowIndex }">
                    <a-form-item>
                      <a-date-picker
                        v-if="saveAuth"
                        style="width: 100%"
                        v-decorator="[`changedList[${$rowIndex}]changeLetterDate`, { rules: [{ required: true, message: '请选择日期' }] }]"
                      />
                      <span v-else>{{ row.changeLetterDate }}</span>
                    </a-form-item>
                  </template>
                </vxe-table-column>
                <vxe-table-column field="changeFilePath" title="变更函" width="160">
                  <template #default="{ row, $rowIndex }">
                    <a-form-item>
                      <a-upload
                        v-if="saveAuth"
                        :disabled="!saveAuth"
                        :fileList="files['changeFilePath']"
                        :multiple="false"
                        @preview="download('changeFilePath')"
                        :beforeUpload="file => beforeUpload(file, 'changeFilePath')"
                        @change="file => handleChange(file, 'changeFilePath')"
                        v-decorator="[
                          `changedList[${$rowIndex}]changeFilePathUpload`,
                          { rules: [{ required: true, type: 'array', transform: transformUpload, message: '请上传附件' }] }
                        ]"
                      >
                        <a-button> <a-icon type="upload" />上传</a-button>
                      </a-upload>
                      <span v-else>{{ row.changeFilePath }}</span>
                    </a-form-item>
                  </template>
                </vxe-table-column>
                <vxe-table-column field="changeContent" title="变更内容" minWidth="120">
                  <template #default="{ row, $rowIndex }">
                    <a-form-item>
                      <a-textarea
                        v-if="saveAuth"
                        auto-size
                        style="width: 100%"
                        v-decorator="[`changedList[${$rowIndex}]changeContent`, { rules: [{ required: true, message: '请输入变更内容' }] }]"
                      />
                      <span v-else>{{ row.changeContent }}</span>
                    </a-form-item>
                  </template>
                </vxe-table-column>
              </vxe-grid>
              <a-button block icon="plus" type="dashed" @click="addChanged">添加</a-button>
            </a-form-item>
          </a-col>
        </a-row>
        <template v-if="isChange">
          <a-row :gutter="24">
            <a-col span="12">
              <a-form-item label="变更函签发时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-date-picker
                  v-if="saveAuth"
                  v-decorator="['changeLetterDate', { rules: [{ required: true, message: '变更函签发时间' }] }]"
                ></a-date-picker>
                <template v-else>{{
                  beianInfo.changeLetterDate ? moment(beianInfo.changeLetterDate).format('YYYY-MM-DD') : ' -'
                }}</template>
              </a-form-item>
            </a-col>
            <a-col span="12">
              <a-form-item label="变更函" :labelCol="labelCol" :wrapperCol="wrapperCol">
                <a-upload
                  :disabled="!saveAuth"
                  :fileList="files['changeFilePath']"
                  :multiple="false"
                  @preview="download('changeFilePath')"
                  :beforeUpload="file => beforeUpload(file, 'changeFilePath')"
                  @change="file => handleChange(file, 'changeFilePath')"
                  v-decorator="[
                    'changeFilePathUpload',
                    { rules: [{ required: true, type: 'array', transform: transformUpload, message: '请上传附件' }] }
                  ]"
                >
                  <a-button> <a-icon type="upload" />上传</a-button>
                </a-upload>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row :gutter="24">
            <a-col span="24">
              <a-form-item label="变更内容" :labelCol="labelCol24" :wrapperCol="wrapperCol24">
                <a-textarea
                  v-if="saveAuth"
                  placeholder="变更内容"
                  rows="3"
                  v-decorator="[
                    'changeContent',
                    { rules: [{ required: true, message: '变更内容', whitespace: true }] }
                  ]"
                ></a-textarea>
                <template v-else>{{ beianInfo.changeContent ? beianInfo.changeContent : ' -' }}</template>
              </a-form-item>
            </a-col>
          </a-row>
        </template>
        <div style="text-align:center;padding-bottom:20px;">
          <a-button type="primary" @click="save" v-if="saveAuth" :loading="btnLoading">保存</a-button>
        </div>
      </a-form>
    </a-spin>
  </a-card>
</template>

<script>
import { mapGetters } from 'vuex'
import { getBeianInfo } from '@/api/tech/BeiAnGuanLi/index'
import moment from 'moment'
import MultipleAddress from './modules/MultipleAddress.vue'
import BaseInfoTable from './modules/BaseInfoTable.vue'
import { set } from 'lodash'

export default {
  name: 'BaseInfo',
  props: {
    record: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data () {
    return {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      labelCol24: {
        xs: { span: 24 },
        sm: { span: 4 }
      },
      wrapperCol24: {
        xs: { span: 24 },
        sm: { span: 20 }
      },
      isChange: false,
      spinning: false,
      files: { filePath: [], scanFilePath: [], changeFilePath: [] },
      totalAmount: undefined,
      totalBeianInvest: undefined,
      beianInfo: {},
      model: {},
      saveAuth: false,
      btnLoading: false
    }
  },
  created () {
    this.form = this.$form.createForm(this, { onValuesChange: (props, values) => {
      console.log(values)
      console.log(props.form.getFieldsValue())
    } })
  },
  mounted () {
    this.handleGetBeianInfo()
    this.saveAuth = this.$auth('tech:beian:basicInfo:save')
  },
  methods: {
    ...mapGetters(['userInfo']),
    moment,
    inputNumberChange (value, field) {
      const obj = this.form.getFieldsValue(['equipment', 'construction', 'initWorkCapital'])
      obj[field] = value
      const { equipment, construction, initWorkCapital } = obj
      this.computedTotalAmount(equipment, construction)
      this.computedTotalBeianInvest(equipment, construction, initWorkCapital)
    },
    onChange (e) {
      this.isChange = e.target.value
      if (this.isChange) {
        this.files.changeFilePath = []
      }
    },
    computedTotalAmount (equipment, construction) {
      this.totalAmount = (equipment * 1000 + construction * 1000) / 1000
    },
    computedTotalBeianInvest (equipment, construction, initWorkCapital) {
      this.totalBeianInvest = (equipment * 1000 + construction * 1000 + initWorkCapital * 1000) / 1000
    },
    disabledBeginDate (v) {
      const end = this.form.getFieldValue('endDate')
      if (end) {
        return v > end
      }
    },
    disabledEndDate (v) {
      const beginDate = this.form.getFieldValue('beginDate')
      if (beginDate) {
        return v < beginDate
      }
    },
    save () {
      const {
        form: { validateFields }
      } = this
      validateFields((errors, values) => {
        if (!errors) {
          this.btnLoading = true
          const data = { ...values }
          data.id = this.record.id
          for (const key in this.files) {
            if (this.files[key].length) {
              // data[key] = this.files[key][0].url
              const tempAry = this.files[key]
              let url = ''
              tempAry.forEach((item, index) => {
                url += item.url + ','
              })
              url = url.substring(0, url.length - 1)
              data[key] = url
            }
          }
          if (!data.change) {
            data['changeLetterDate'] = ''
            data['changeFilePath'] = ''
            data['changeContent'] = ''
          }
          this.$http
            .post('/beian/save', data)
            .then(res => {
              if (res.success && res.data) {
                this.$emit('getBaseInfo', data)
                this.$message.success('保存成功')
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '保存失败')
                console.log(`save function error ${res.errorCode} : ${res.errorMessage} `)
              }
            })
            .catch(error => {
              console.log('save function error', error)
            })
            .finally(() => {
              this.btnLoading = false
            })
        }
      })
    },
    handleGetBeianInfo () {
      this.spinning = true
      getBeianInfo({ beianId: this.record.id })
        .then(response => {
          if (response.success && response.data) {
            this.beianInfo = response.data
            if (!this.beianInfo.applicant) {
              this.beianInfo.applicant = this.userInfo().companyName
            }
            this.$emit('getBaseInfo', response.data)
            this.isChange = this.beianInfo.change
            this.$nextTick(() => {
              for (const key in this.files) {
                if (this.beianInfo[key]) {
                  const filePath = this.beianInfo[key]
                  const filePaths = filePath.split(',')
                  if (Array.isArray(filePaths)) {
                    filePaths.forEach(item => {
                      const arr = item.split('/')
                      const fileName = arr[arr.length - 1].substr(13)
                      this.files[key].push({
                        uid: item,
                        name: fileName,
                        status: 'done',
                        url: item
                      })
                    })
                  } else {
                    const arr = filePath.split('/')
                    const fileName = arr[arr.length - 1].substr(13)
                    this.files[key] = [
                      {
                        uid: filePath,
                        name: fileName,
                        status: 'done',
                        url: filePath
                      }
                    ]
                  }
                }
              }
              this.handleInitForm(this.beianInfo)
            })
          } else {
            console.log(`handleGetBeianInfo function error ${response.errorCode}: ${response.errorMessage}`)
          }
        })
        .catch(error => {
          console.log(`handleGetBeianInfo function error : ${error}`)
        })
        .finally(() => {
          this.spinning = false
        })
    },
    refresh () {
      this.isChange = false
      this.spinning = false
      this.files = { filePath: [], scanFilePath: [], changeFilePath: [] }
      this.totalAmount = undefined
      this.totalBeianInvest = undefined
      this.beianInfo = {}
      this.handleGetBeianInfo()
    },
    beforeUpload (file, key) {
      const param = new FormData()
      param.append('file', file)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http
        .post('/beian/upload', param, config)
        .then(res => {
          if (res.success) {
            this.files[key] = [
              {
                uid: res.data.fileName,
                name: res.data.fileName,
                status: 'done',
                url: res.data.filePath
              }
            ]
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '上传失败')
          }
        })
        .catch(res => {})
      return false
    },
    multipleBeforeUpload (file, key) {
      const param = new FormData()
      param.append('file', file)
      const config = {
        // 添加请求头
        headers: { 'Content-Type': 'multipart/form-data' }
      }
      this.$http
        .post('/beian/upload', param, config)
        .then(res => {
          if (res.success) {
            this.files[key].push({
              uid: res.data.filePath,
              name: res.data.fileName,
              status: 'done',
              url: res.data.filePath
            })
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '上传失败')
          }
        })
        .catch(res => {})
      return false
    },
    handleChange (file, key) {
      if (file.file.status === 'removed') {
        this.files[key] = file.fileList
      }
    },
    download (key) {
      const file = this.files[key][0]
      if (file) {
        const filePath = file.url
        const arr = filePath.split('/')
        const fileName = arr[arr.length - 1]
        this.$exportData(
          '/beian/download',
          { filePath },
          fileName.length > 13 ? fileName.substr(13) : fileName,
          this.$message
        )
      }
    },
    multipleDownload (file) {
      if (file) {
        const filePath = file.url
        const arr = filePath.split('/')
        const fileName = arr[arr.length - 1]
        this.$exportData(
          '/beian/download',
          { filePath },
          fileName.length > 13 ? fileName.substr(13) : fileName,
          this.$message
        )
      }
    },
    /**
     * @description: 转化上传的验证值的回调函数
     * @param {*} 当前的值
     * @return {*} 验证的内容
     */
    transformUpload (value) {
      if (value && value.fileList && value.fileList.length) {
        return value.fileList
      }
      return null
    },
    handleInitForm (beianInfo) {
      const dates = ['beginDate', 'endDate', 'beianDate', 'changeLetterDate']
      const { equipment, construction, initWorkCapital } = beianInfo
      this.computedTotalAmount(equipment, construction)
      this.computedTotalBeianInvest(equipment, construction, initWorkCapital)
      this.$initForm(this.form, beianInfo, dates)
      this.form.setFieldsValue({
        filePathUpload: { fileList: this.files['filePath'] },
        changeFilePathUpload: { fileList: this.files['changeFilePath'] },
        scanFilePathUpload: { fileList: this.files['scanFilePath'] }
      })
    },
    addChanged () {
      let { changedList } = this.beianInfo
      changedList = changedList ? [...changedList, {}] : [{}]
      this.beianInfo = {
        ...this.beianInfo,
        changedList
      }
    }
  },
  components: { MultipleAddress, BaseInfoTable }
}
</script>
<style lang="less" scoped>
.container {
  height: 100%;
  #spin {
    height: 100%;
    width: 100%;
    & /deep/ .ant-spin-nested-loading {
      height: 100%;
    }
    & /deep/ .ant-spin-container {
      height: 100%;
    }
  }
}

& /deep/ .vxe-cell {
  max-height: none !important;

  .ant-form-item {
    margin-bottom: 4px;
  }
}
//测试修改上传样式
// .ant-form {
//   & /deep/ .ant-form-item-children  {
//     //  background-color: red;
//      & > span {
//        display: flex;
//        flex-direction: row;
//      }
//   }
// }
</style>
