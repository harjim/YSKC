<!--
 * @Author: zdf
 * @Date: 2021-08-19 16:20:18
 * @LastEditTime: 2021-12-20 16:10:34
 * @LastEditors: zdf
 * @Description: 专利申请基本信息
 * @FilePath: \MS-VUE\src\views\patent\modules\patentPlan\BaseInfo.vue
-->
<template>
  <a-spin :spinning="spinning" tip="请稍后...">
    <a-card title="基本信息" style="width: 100%;">
      <template slot="extra">
        <template v-if="canEdit">
          <a @click="openEdit(true)" v-if="!inputRequired">编辑</a>
          <a @click="openEdit(false)" v-else>取消</a>
        </template>
      </template>

      <a-form :form="form">
        <a-row style="margin-right:-16px;">
          <a-row>
            <a-col class="colClass" :span="6">
              <a-form-item label="专利需求方" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ `${row.companyName}-${row.year}` }}</div>
                <search-select
                  v-else
                  :dropdownStyle="{ maxHeight: '420px', minWidth: '350px' }"
                  :disabled="canEdit"
                  sKey="id"
                  url="/patentPlan/getCustomers"
                  searchField="companyName"
                  :sTitle="titleShow"
                  @change="getRds"
                  :multiple="false"
                  placeholder="请选择专利需求方"
                  v-decorator="['demand', {rules:[{required: true, message: '请选择专利需求方'}]}]"
                />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="RD" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.rdTitle || '其他' }}</div>
                <a-select v-else v-decorator="['projectId', {rules:[{required: inputRequired, message: '请选择RD'}]}]" placeholder="请选择RD">
                  <a-select-option v-for="rd in rds" :key="String(rd.id)">{{ `${rd.rdTitle}` }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="业务员" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.ownerName || '-' }}</div>
                <search-select
                  v-else
                  url="/user/userForSelect"
                  searchField="realName"
                  sTitle="realName"
                  :multiple="false"
                  placeholder="请输入业务员"
                  v-decorator="['owner', {rules:[{required: true, message: '请输入业务员'}]}]"
                />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="工程师" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.engineer || '-' }}</div>
                <search-select
                  :disabled="true"
                  v-else
                  url="/user/userForSelect"
                  searchField="realName"
                  sTitle="realName"
                  :multiple="false"
                  placeholder="请输入工程师"
                  v-decorator="['engineer', {rules:[{required: true, message: '请输入工程师'}]}]"
                />
              </a-form-item>
            </a-col>
          </a-row>
          <div style="width:100%;padding-top:0px;margin-bottom:14px;border-bottom:1px solid #EDF2F7;"></div>
          <a-row>
            <a-col class="colClass" :span="6">
              <a-form-item label="申请名称" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.patentName || '-' }}</div>
                <a-input v-else v-decorator="['patentName', {rules:[{required: inputRequired, message: '请输入申请名称'}]}]" placeholder="请输入申请名称" />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="申请类型" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ patentTypes[row.patentType] || '-' }}</div>
                <a-select v-else v-decorator="['patentType', {rules:[{required: inputRequired, message: '请选择申请类型'}]}]" placeholder="请选择申请类型">
                  <a-select-option v-for="(v,k) in patentTypes" :key="String(k)">{{ v }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="联系人" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.linkMan || '-' }}</div>
                <a-input v-else v-decorator="['linkMan', {rules:[{required: inputRequired, message: '请输入联系人'}]}]" placeholder="请输入联系人" />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="联系方式" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.linkTel || '-' }}</div>
                <a-input v-else v-decorator="['linkTel', {rules:[{required: inputRequired, message: '请输入联系方式'}]}]" placeholder="请输入联系方式" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col class="colClass" :span="6">
              <a-form-item label="申请（专利权）类型" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ unitTypes[row.unitType] || '-' }}</div>
                <a-select v-else v-decorator="['unitType', {rules:[{required: inputRequired, message: '请选择申请人类型'}]}]" placeholder="请选择申请人类型">
                  <a-select-option v-for="(item,index) in unitTypes" :key="String(index)">{{ item }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="申请（专利权）人" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.applicant || '-' }}</div>
                <a-input v-else v-decorator="['applicant', {rules:[{required: inputRequired, message: '请输入申请人'}]}]" placeholder="请输入申请人" />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="身份证号码或统一信息代码" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.code || '-' }}</div>
                <a-input v-else v-decorator="['code', {rules:[{required: inputRequired,pattern: new RegExp(numPattern),message: '请输入身份证号码或统一信息代码'}]}]" placeholder="请输入身份证号码或统一信息代码"/>
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="版本" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ planTypes[row.planType] }}</div>
                <a-select v-else v-decorator="['planType', {rules:[{required: inputRequired, message: '请选择版本'}]}]" placeholder="请选择版本">
                  <a-select-option v-for="(item,index) in planTypes" :key="String(index)">{{ item }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col class="colClass" :span="6">
              <a-form-item label="初稿需求日期" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.demandDate ? moment(row.demandDate).format('YYYY-MM-DD') : '-' }}</div>
                <a-date-picker v-else style="width:100%" v-decorator="['demandDate', {rules:[{required: inputRequired, message: '请选择初稿需求日期'}]}]" placeholder="请选择初稿需求日期" />
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="提交国知局日期" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.submitDate ? moment(row.submitDate).format('YYYY-MM-DD') : '-' }}</div>
                <a-date-picker v-else style="width:100%" v-decorator="['submitDate', {rules:[{required: inputRequired, message: '请选择提交国知局日期'}]}]" placeholder="请选择提交国知局日期" />
              </a-form-item>
            </a-col>
            <!-- <a-col class="colClass" :span="12">
              <a-form-item label="代理人" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ masters[row.masterId] || '-' }}</div>
                <a-select v-else v-decorator="['masterId', {rules:[{required: inputRequired, message: '请选择代理人'}]}]" placeholder="请选择代理人">
                  <a-select-option v-for="(v,k) in masters" :key="k">{{ v }}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col> -->
          </a-row>
          <a-row>
            <a-col class="colClass" :span="12">
              <a-form-item label="第一发明人" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.firstInventor || '-' }}</div>
                <a-input v-else v-decorator="['firstInventor', {rules:[{required: inputRequired, message: '请输入第一发明人姓名及身份证号'}]}]" placeholder="请输入第一发明人姓名及身份证号"/>
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="12">
              <a-form-item label="其他发明人" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.otherInventor || '-' }}</div>
                <a-input v-else v-decorator="['otherInventor', {rules:[{required: false, message: '请输入其他发明人'}]}]" placeholder="请输入其他发明人"/>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col class="colClass" :span="12">
              <a-form-item label="详细地址" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.address || '-' }}</div>
                <a-input v-else v-decorator="['address', {rules:[{required: false, message: '请输入详细地址'}]}]" placeholder="请输入详细地址"/>
              </a-form-item>
            </a-col>
            <a-col class="colClass" :span="6">
              <a-form-item label="邮政编码" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">{{ row.postalCode || '-' }}</div>
                <a-input :maxLength="6" v-else v-decorator="['postalCode', {rules:[{required: false,whitespace:true,min:6,max:6,pattern: new RegExp('\\d{6}'), message: '请输入6位邮政编码'}]}]" placeholder="请输入邮政编码" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col class="colClass" :span="18">
              <a-form-item label="其他事项" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">
                  <a-checkbox-group :value="getOtherItem">
                    <a-checkbox style="color:rgb(89,89,89)" v-for="(item,index) in otherItems" :key="String(index)" :value="String(index)">{{ item }}</a-checkbox>
                  </a-checkbox-group>
                </div>
                <a-checkbox-group v-else v-decorator="['otherItem', {rules:[{required: false, message: '请选择其他事项'}]}]">
                  <a-checkbox style="color:rgb(89,89,89)" v-for="(item,index) in otherItems" :key="String(index)" :value="String(index)">{{ item }}</a-checkbox>
                </a-checkbox-group>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col class="colClass" :span="24">
              <a-form-item label="交底书" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv">
                  <p v-for="file in baseFiles" :key="file.id">
                    <a v-if="control.download" @click="onDownloadFile(file.filePath,file.fileName)">{{ file.fileName }}</a>
                    <template v-else>{{ file.fileName }}</template>
                    <a-tooltip @click="preview(file.filePath,file.fileName)" v-if="control.preview">
                      <template slot="title">
                        <span>预览</span>
                      </template>
                      <a-icon type="eye" style="margin-left:5px" />
                    </a-tooltip>
                  </p>
                </div>
                <template v-else>
                  <input style="display:none;" v-decorator="[ 'fileList', { rules:[{ required: inputRequired, message: '请上传附件' }]} ]" />
                  <a-upload
                    :fileList="fileList"
                    :multiple="false"
                    @change="changeUpload"
                    :beforeUpload="beforeUpload"
                    :showUploadList="{showPreviewIcon: true, showDownloadIcon:true, showRemoveIcon:true}"
                    @preview="file=>preview(file.url,file.name)"
                    @download="file=>onDownloadFile(file.url,file.name)"
                  >
                    <a-button v-if="inputRequired">
                      <a-icon type="upload" />上传附件
                    </a-button>
                  </a-upload>
                </template>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col class="colClass" :span="24">
              <a-form-item label="备注" :labelCol="fullCol" :wrapperCol="fullCol">
                <div v-if="!inputRequired" class="colDiv" style="white-space: pre-wrap;">{{ row.remark || '-' }}</div>
                <a-textarea v-else v-decorator="[ 'remark', { rules: [{ required: false, message: '请输入备注' }], }, ]" placeholder="请输入备注" :rows="4" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-row>
        <a-row>
          <div style="text-align:right;margin-top:10px;">
            <a-button style="margin-right:10px;" v-if="inputRequired" type="primary" @click="save(5)">保存</a-button>
            <a-popconfirm v-if="control.submit && !inputRequired && canEdit" title="是否确定提交?" @confirm="submitAudit()">
              <a-button style="margin-right:10px;" type="primary">提交</a-button>
            </a-popconfirm>

          </div>
        </a-row>
      </a-form>
    </a-card>
  </a-spin>
</template>

<script>
import moment from 'moment'
import SearchSelect from '@/components/Selects/SearchSelect.vue'
export default {
  components: {
    SearchSelect
  },
  props: {
    row: {
      type: Object,
      default: () => {}
    },
    canEdit: {
      type: Boolean,
      default: false
    },
    onAdd: {
      type: Boolean,
      default: false
    },
    patentTypes: {
      type: Object,
      required: true
    },
    planTypes: {
      type: Array,
      required: true
    },
    onPreview: {
      type: Function,
      required: true
    },
    control: {
      type: Object,
      required: true
    },
    baseFiles: {
      type: Array,
      default: () => []
    }
  },
  computed: {
    numPattern () {
      const year = ((new Date().getFullYear() - 2000) + '').split('')
      return `^([1-9]\\d{5}(([1][9]\\d{2})|([2][0]([0-${Number(year[0]) - 1}]\\d)|([${Number(year[0])}][0${Number(year[1]) !== 0 ? '-' + Number(year[1]) : ''}])))\\d{7}[\\d|x|X]$)|(^[^_IOZSVa-z\\W]{2}\\d{6}[^_IOZSVa-z\\W]{10}$)`
    },
    getOtherItem () {
      if (!this.row.otherItem) {
        return []
      }
      return this.row.otherItem.split(',')
    }
  },
  data () {
    return {
      fileList: [],
      inputRequired: false,
      otherItems: ['发明+实用新型同时申请', '申请时办理提前公开请求', '是否办理费减'],
      unitTypes: ['工矿企业', '个人', '大专院校', '科研单位', '事业单位'],
      spinning: false,
      form: this.$form.createForm(this),
      fullCol: { span: 24 },
      rds: []
      // masters: {}
    }
  },
  created () {
    this.inputRequired = this.onAdd
    // this.getMasters()
  },
  watch: {
    row () {
      this.inputRequired = this.onAdd
      this.$nextTick(() => {
        this.form.resetFields()
      })
    }
  },
  methods: {
    moment,
    titleShow (d) {
      return `${d.companyName}(${d.year})`
    },
    openEdit (inputRequired) {
      this.inputRequired = inputRequired
      if (this.inputRequired) {
        const params = Object.assign({}, this.row)
        this.$set(params, 'demand', { id: -1, customerId: this.row.customerId, companyName: this.row.companyName, year: this.row.year, companyId: this.row.companyId })
        this.getRds(params.demand)
        params.owner = { id: this.row.ownerId, realName: this.row.ownerName }
        if (this.row.engineerId) {
          params.engineer = { id: this.row.engineerId, realName: this.row.engineer }
        }
        params.patentType = Number(params.patentType)
        params.fileList = []
        this.baseFiles.forEach(item => {
          params.fileList.push({ id: item.id, name: item.fileName, url: item.filePath, uid: item.filePath })
        })
        this.fileList = params.fileList
        if (params.otherItem) {
          params.otherItem = params.otherItem.split(',').map(a => a)
        } else {
          params.otherItem = undefined
        }
        if (this.row.demandDate) {
          params.demandDate = this.moment(this.row.demandDate, 'YYYY-MM-DD 00:00:00')
        }
        if (this.row.submitDate) {
          params.submitDate = this.moment(this.row.submitDate, 'YYYY-MM-DD 00:00:00')
        }
        this.$nextTick(() => {
          this.form.resetFields()
          this.$initForm(this.form, params)
        })
      }
    },
    save () {
      this.spinning = true
      this.form.validateFields((errors, values) => {
        if (errors) {
          this.spinning = false
          return
        }
        values.customerId = values.demand.customerId
        values.year = values.demand.year
        delete values.demand
        values.ownerId = values.owner.id
        values.engineerId = values.engineer.id
        const owner = values.owner
        const engineer = values.engineer
        delete values.owner
        delete values.engineer
        values.process = 0
        values.id = this.row.id
        if (values.otherItem) {
          values.otherItem = values.otherItem.join(',')
        }
        values.fileModels = values.fileList.map(a => { return { fileName: a.name, filePath: a.url, id: a.id, fileType: 0 } })
        delete values.fileList
        this.$http.post('/patentPlan/save', values).then(res => {
          if (res.success && res.data) {
            this.inputRequired = false
            this.$message.success('操作成功')
            for (const key in values) {
              this.row[key] = values[key]
            }
            this.row.ownerName = owner.realName
            this.row.engineer = engineer.realName
            const rd = this.rds.find(a => a.id === Number(this.row.projectId))
            this.row.rdTitle = rd ? rd.rdTitle : ''
            if (this.onAdd) {
              this.$emit('onClose', true)
            } else {
              this.$emit('getInfo')
            }
          } else {
            this.$message.error(res.errorMessage || '操作失败')
          }
          this.spinning = false
        })
      })
    },
    submitAudit () {
      this.spinning = true
      this.$http.post('/patentPlan/submitPatent', { id: this.row.id }).then(res => {
        if (res.success && res.data) {
          this.row.status = 4
          setTimeout(() => {
            this.$emit('getInfo')
            this.spinning = false
          }, 200)
        } else {
          this.$message.error(res.errorMessage || '提交失败')
          this.spinning = false
        }
      })
    },
    getRds (d) {
      if (d) {
        this.form.setFieldsValue({ owner: { realName: d.ownerName, id: d.ownerId }, engineer: { realName: d.engineer, id: d.engineerId } })
        this.spinning = true
        this.$http.get('/patentPlan/getProjectSelect', { params: { companyId: d.companyId, year: d.year } }).then(res => {
          if (res.success) {
            this.rds = res.data || []
            this.rds.push({ id: -1, rdTitle: '其他' })
          } else {
            this.rds = []
          }
          this.spinning = false
        })
      } else {
        this.rds = [{ id: -1, rdTitle: '其他' }]
        this.form.setFieldsValue({ owner: undefined, engineer: undefined })
      }
    },
    changeUpload (file) {
      if (file.file.status === 'removed') {
        const index = this.fileList.findIndex(item => item.url === file.file.url)
        this.fileList.splice(index, 1)
        this.form.setFieldsValue({ fileList: this.fileList.length ? this.fileList : undefined })
      }
    },
    // getMasters () {
    //   this.$http.get('/patentMaster/getMasterList', { params: { model: null } }).then(res => {
    //     if (res.success && res.data) {
    //       const masters = {}
    //       res.data.forEach(item => { masters[item.id] = item.masterName })
    //       this.masters = masters
    //     } else {
    //       this.$message.error(res.errorMessage || '系统异常，请联系管理员')
    //     }
    //   })
    // },
    preview (url, name) {
      this.onPreview(url, name)
    },
    onDownloadFile (url, name) {
      this.$exportData('/patentBuying/download', { filePath: url }, name, this.$message)
    },
    beforeUpload (file) {
      if (!this.$checkFileSize(file, this.$message)) {
        return
      }
      const param = new FormData()
      param.append('file', file)
      param.append('dir', '/patent/')
      this.$http.post('/serviceLog/upload', param).then(res => {
        if (res.success && res.data) {
          const data = res.data
          this.fileList.push({
            uid: data.filePath,
            name: data.fileName,
            status: 'done',
            url: data.filePath
          })
          this.form.setFieldsValue({ fileList: this.fileList })
        } else {
          this.$message.error(res.errorMessage || '系统异常，请联系管理员！')
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常，请联系管理员！')
      })
      return false
    }
  }
}
</script>

<style scoped>
.colClass{
  margin-bottom:-4px;
  padding-right:8px;
}
  .colClass.ant-form-item-label{
    text-align:left;
    padding-bottom: 8px;
    color:aliceblue;

  }
  .colClass.ant-form-item-label > label::after {
    content: '';
  }
.colClass.ant-form-item-label > label{
  color:#99AEC2;
}
.colDiv{
  line-height: 18px;
}
</style>
