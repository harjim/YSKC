<!--
 * @Author: ldx
 * @Date: 2021-05-27 17:19:25
 * @LastEditTime: 2021-07-14 08:50:28
 * @LastEditors: ldx
 * @Description:高新技术明细
 * @FilePath: \RS-VUE\src\views\highTech\modules\HighTechDetail.vue
-->
<template>
  <section class="container">
    <a-spin :spinning="spinning" tip="加载中...">
      <div class="exit_wrap">
        <a-icon class="exit" type="left" @click="onExit" />
      </div>
      <a-row type="flex" justify="center" align="middle">
        <a-col> <span class="title">高新技术产品收入归集明细表</span> </a-col>
      </a-row>
      <div class="btn_wrap">
        <a-button
          v-if="$auth('highTech:highTechIndex:export') && isEdit"
          size="small"
          :disabled="spinning"
          :loading="spinning"
          type="primary"
          class="btn_spcoe"
          @click="onExport">导出</a-button>
        <a-button
          v-if="$auth('highTech:highTechIndex:saveDetail') && isEdit"
          size="small"
          :disabled="spinning"
          :loading="spinning"
          type="primary"
          class="btn_spcoe"
          @click="onSave">保存</a-button>
      </div>
      <a-divider/>
      <div class="form_wrap" style="width: 1000px; margin:0 auto;">
        <!-- <a-spin :spinning="spinning" tip="加载中..."> -->
        <a-form :form="form">
          <table border="1" width="100%">
            <tbody>
              <tr>
                <td style="width:25%">高新技术产品名称</td>
                <td style="width:25%"> {{ record.hcode }}</td>
                <td colspan="2" style="width:50%">{{ record.hname }}</td>
              </tr>
              <tr>
                <td>高新技术领域</td>
                <td colspan="3">{{ renderTecIndustry(record.tecIndustry) }}</td>
              </tr>
              <tr>
                <td colspan="2" style="text-align: center; vertical-align: middle;">月份</td>
                <td colspan="2" style="text-align: center; vertical-align: middle;">收入金额/万元</td>
              </tr>
              <tr v-for="(month,index) in monthData" :key="index + '_month'">
                <td colspan="2" style="text-align: center; vertical-align: middle;">{{ month.month }}</td>
                <td colspan="2" style="text-align: right; vertical-align: middle;">{{ month.value }}</td>
              </tr>
              <tr>
                <td colspan="2" style="text-align: right; vertical-align: middle;">合计</td>
                <td colspan="2" style="text-align: right; vertical-align: middle;">{{ incomeMap && incomeMap.total ? incomeMap.total : '-' }}</td>
              </tr>
              <tr>
                <td colspan="2" style="text-align: right; vertical-align: middle;">当期累计总收入</td>
                <td colspan="2" style="text-align: right; vertical-align: middle;">{{ totalAmount ? totalAmount : '-' }}</td>
              </tr>
              <tr>
                <td colspan="2" style="text-align: right; vertical-align: middle;">高新技术产品占比</td>
                <td colspan="2" style="text-align: right; vertical-align: middle;">{{ incomeMap && incomeMap.proportion ? incomeMap.proportion + '%' : '-' }}</td>
              </tr>
              <tr>
                <td rowspan="2" colspan="2">同类产品是否已有技术标准</td>
                <td :colspan="2">
                  <a-form-item >
                    <!-- <a-radio-group v-if="isEdit" v-decorator="['hasSameTechStandard',{rules:[{required: true, message: '请选择同类产品是否已有技术标准'}]} ]" @change="hasSameTechStandardChange" :options="config.hasSameTechStandardOpt" /> -->
                    <a-radio-group v-decorator="['hasSameTechStandard',{rules:[{required: true, message: '请选择同类产品是否已有技术标准'}]} ]" @change="hasSameTechStandardChange" :options="config.hasSameTechStandardOpt" />
                  <!-- <span v-else>{{ detailModel && detailModel.hasSameTechStandard ? '有' : '无' }}</span> -->
                  </a-form-item>
                </td>
              </tr>
              <tr >
                <td colspan="2">
                  <a-form-item >
                    <a-checkbox-group v-decorator="['techStandard',{rules:[{required: hasSameTechStandardShow, message: '请选择技术标准'}]}]" @change="techStandardChange" :options="config.techStandardOpt" />
                  </a-form-item>
                  <a-form-item >
                    <a-input :disabled="!hasOtherTechStandardShow" v-decorator="['otherTechStandard',{rules:[{required: hasOtherTechStandardShow, message: '请输入其他'}]} ]" class="custom_input" placeholder="其他"></a-input>
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">新产品是否制定质量标准</td>
                <td colspan="2">
                  <a-form-item style="display: inline-block; width: 100%;">
                    <a-form-item style="width: 25%">
                      <a-radio-group v-decorator="['hasQualityStandard',{rules:[{required: true, message: '请选择新产品是否制定质量标准'}]} ]" @change="hasQualityStandardChange" :options="config.hasQualityStandardOpt" />
                    </a-form-item>
                    <a-form-item
                      label="名称"
                      style="width: 75%"
                      :labelCol="{md: {span: 4}}"
                      :wrapperCol="{md: {span: 20}}">
                      <a-input :disabled="!hasQualityStandardShow" v-decorator="['qualityStandard',{rules:[{required: hasQualityStandardShow, message: '请输入名称'}]} ]" style="margin-left: 0px;" class="custom_input" placeholder="名称"></a-input>
                    </a-form-item>
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">新产品是否通过鉴定或第三方检测</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['passTest',{rules:[{required: true, message: '请选择新产品是否通过鉴定或第三方检测'}]}]" :options="config.passTestOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">新产品技术标准指标或鉴定指标是否达到国内外先进技术标准的水平</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['techLevel',{rules:[{required: true, message: '请选择新产品技术标准指标或鉴定指标是否达到国内外先进技术标准的水平'}]}]" :options="config.techLevelOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">新产品是否国内首创新产品</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['domesticFirst',{rules:[{required: true, message: '请选择新产品是否国内首创新产品'}]}]" :options="config.domesticFirstOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">技术指标是否达到国际标准水平</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['internationalLevel',{rules:[{required: true, message: '请选择技术指标是否达到国际标准水平'}]}]" :options="config.internationalLevelOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">项目来源</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['projectSource',{rules:[{required: true, message: '请选择项目来源'}]} ]" :options="config.projectSourceOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">技术来源</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['techSource',{rules:[{required: true, message: '请选择技术来源'}]} ]" :options="config.techSourceOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">产品开发形式</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['devType',{rules:[{required: true, message: '请选择产品开发形式'}]} ]" :options="config.devTypeOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">应用新研发技术情况</td>
                <td colspan="2">
                  <ul>
                    <li v-for="(project,index) in projects" :key="project.id">
                      {{ project.rdTitle }} - {{ project.pname }}
                      <a-icon @click="onDelProject(project,index)" type="close" class="close_icon" />
                    </li>
                  </ul>
                  <a-button v-if="$auth('highTech:highTechIndex:saveDetail')" type="primary" size="small" @click="onRelProject">关联项目</a-button>
                </td>
              </tr>
              <tr>
                <td colspan="2">本高新技术产品先进性说明</td>
                <td colspan="2">
                  <a-form-item >
                    <a-textarea v-decorator="['advancedExplain']" placeholder="本高新技术产品先进性说明" :rows="5" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">相关的专利</td>
                <td colspan="2">
                  <a-form-item >
                    <a-textarea v-decorator="['patents']" placeholder="相关的专利" :rows="5" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">应用专利关键技术</td>
                <td colspan="2">
                  <a-form-item >
                    <a-textarea v-decorator="['patentsTech']" placeholder="应用专利关键技术" :rows="5" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">新产品与国际标准、国内标准（国标、行标、先进企业标准）具体技术指标数据比较情况。
                  主要技术指标或与之前技术对比</td>
                <td colspan="2">
                  <a-form-item >
                    <a-textarea
                      v-decorator="['techCompare']"
                      placeholder="新产品与国际标准、国内标准（国标、行标、先进企业标准）具体技术指标数据比较情况。主要技术指标或与之前技术对比"
                      :rows="5" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">与同类产品（服务）或研究之前产品的优势</td>
                <td colspan="2">
                  <a-form-item >
                    <a-textarea
                      v-decorator="['advantage']"
                      placeholder="与同类产品（服务）或研究之前产品的优势"
                      :rows="5" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">高新技术产品认定或第三方评价</td>
                <td colspan="2">
                  <a-form-item >
                    <a-textarea
                      v-decorator="['evaluate']"
                      placeholder="高新技术产品认定或第三方评价"
                      :rows="5" />
                  </a-form-item>
                </td>
              </tr>
              <tr>
                <td colspan="2">产品对产业发展的影响和贡献情况</td>
                <td colspan="2">
                  <a-form-item >
                    <a-textarea
                      v-decorator="['contribution']"
                      placeholder="产品对产业发展的影响和贡献情况"
                      :rows="5" />
                  </a-form-item>
                </td>
              </tr>
              <tr >
                <td rowspan="5" style="text-align: center;">技术水平</td>
                <td>自主知识产权含量</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['ownerProperty',{rules:[{required: true, message: '请选择自主知识产权含量'}]} ]" :options="config.ownerPropertyOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr >
                <td>创新性</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['innovation',{rules:[{required: true, message: '请选择创新性'}]} ]" :options="config.innovationOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr >
                <td>先进性</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['advanced',{rules:[{required: true, message: '请选择先进性'}]} ]" :options="config.advancedOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr >
                <td>成熟度</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['maturity',{rules:[{required: true, message: '请选择成熟度'}]} ]" :options="config.maturityOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr >
                <td>产品获奖情况</td>
                <td colspan="2">
                  <a-form-item >
                    <a-radio-group v-decorator="['award',{rules:[{required: true, message: '请选择产品获奖情况'}]} ]" :options="config.awardOpt" />
                  </a-form-item>
                </td>
              </tr>
              <tr v-for="(item,index) in uploadItem" :key="index">
                <td v-if="index ===0" :rowspan="uploadItem.length" style="text-align: center;">证明材料</td>
                <td>{{ item.type }}.{{ item.typeName }}</td>
                <td colspan="2">
                  <ul>
                    <li v-for="(file,i) in item.fileLists" :key="i">
                      <span>
                        {{ i+1 }}.  <a v-if="$auth('highTech:highTechIndex:download')" @click="onDownload(file)">{{ file.fileName }}</a><span v-else>{{ file.fileName }}</span>
                      </span>
                      <span>
                        <a-icon v-if="$auth('highTech:highTechIndex:preview')" type="eye" class="icon" @click="onEyeFile(file)"/>
                        <a-icon v-if="$auth('highTech:highTechIndex:delFile')" type="close" class="close_icon" @click="onDelFile(item.type,file,i)" />
                      </span>
                    </li>
                  </ul>
                  <a-button v-if="$auth('highTech:highTechIndex:upload')" @click="onUploadFile(item.type,item.typeName)" type="primary" size="small">上传</a-button>
                </td>
              </tr>
            </tbody>
          </table>
        </a-form>
      <!-- </a-spin> -->
      </div>
      <RelevanceProject ref="relevanceProject" @selected="selected" />
      <PreviewModal url="/techAttachments/preview" ref="previewModal" />
      <UploadFileModal
        action="/highTech/upload"
        ref="uploadFileModal"
        :paramData="{highTechId: record.id}"
        @error="uploadError"
        @success="uploadSuccess"
      />
    </a-spin>
  </section>
</template>

<script>
import config from './highTechDetailConfig'
import { getTechInfo, saveDetail, delTechFile } from '@/api/highTech/highTech'
import RelevanceProject from './RelevanceProject'
import UploadFileModal from './UploadFileModal'
import { PreviewModal } from '@/components'
import { mapGetters } from 'vuex'
export default {
  name: 'HighTechDetail',
  props: {
    record: {
      type: Object,
      default: () => { return undefined }
    }
  },
  components: {
    RelevanceProject,
    UploadFileModal,
    PreviewModal
  },
  watch: {
    record: {
      deep: true,
      immediate: true,
      handler (val) {
        if (val.id > 0) {
          this.resetData()
          this.initData(val.id, this.currentYear)
        }
      }
    },
    currentYear: {
      handler (val) {
        this.resetData()
        this.initData(this.record.id, val)
      }
    }
  },
  data () {
    return {
      config,
      spinning: false,
      isEdit: true,
      form: this.$form.createForm(this),
      labelCol: { xs: { span: 24 }, sm: { span: 4 }, md: { span: 4 } },
      wrapperCol: { xs: { span: 24 }, sm: { span: 20 }, md: { span: 20 } },
      projects: [],
      detailModel: undefined,
      totalAmount: undefined,
      hasProjectChange: false,
      incomeMap: undefined,
      hasDetailChange: true,
      hasSameTechStandardShow: false,
      hasQualityStandardShow: false,
      hasOtherTechStandardShow: false,
      monthData: [],
      tecIndustryMap: {},
      uploadItem: [
        { type: 1, typeName: '生产许可证', fileLists: [] },
        { type: 2, typeName: '国内知识产权证书', fileLists: [] },
        { type: 3, typeName: '检测报告', fileLists: [] },
        { type: 4, typeName: '能源体系证书', fileLists: [] },
        { type: 5, typeName: '销售发票', fileLists: [] },
        { type: 6, typeName: '销售合同', fileLists: [] },
        { type: 7, typeName: '用户反馈', fileLists: [] },
        { type: 8, typeName: '产品图片', fileLists: [] },
        { type: 9, typeName: '其他', fileLists: [] }
      ]
    }
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  mounted () {
    this.$highTecIndustryTreeMap(this).then((res) => {
      this.tecIndustryMap = res
    })
  },
  methods: {
    resetData () {
      this.isEdit = true
      this.spinning = false
      this.projects = []
      this.hasProjectChange = false
      this.detailModel = undefined
      this.totalAmount = undefined
      this.hasDetailChange = true
      this.hasSameTechStandardShow = false
      this.hasQualityStandardShow = false
      this.hasOtherTechStandardShow = false
      this.incomeMap = undefined
      this.monthData = []
      this.uploadItem.forEach(item => { item.fileLists = [] })
      this.form.resetFields()
      // this.postProjects = []
    },
    onSearch () {
      this.test.age = 23
    },
    changeMode (isEdit) {
      this.isEdit = isEdit
    },
    onReturn () {
      this.isEdit = false
    },
    onExit () {
      this.resetData()
      this.$emit('showList', true)
    },
    onRelProject () {
      this.$refs.relevanceProject.show(this.record, this.projects)
    },
    selected (chooseProject) {
      this.hasProjectChange = true
      this.projects.push(chooseProject)
      // this.postProjects.push(chooseProject)
    },
    onDelProject (project, index) {
      this.$confirm({
        title: '您确定要删除吗?',
        okText: '确定',
        okType: 'danger',
        cancelText: '取消',
        onOk: () => {
          this.handlerDelProject(project, index)
        },
        onCancel () { }
      })
    },
    handlerDelProject (project, index) {
      this.spinning = true
      this.hasProjectChange = true
      this.projects.splice(index, 1)
      this.spinning = false
    },
    async initData (highTechId, currentYear) {
      this.spinning = true
      const result = await getTechInfo({ highTechId, year: currentYear }).then((response) => {
        if (response.success && response.data) {
          const data = response.data
          const { fileMap, projects, detailModel, incomeMap, totalAmount } = data
          this.projects = projects
          this.detailModel = detailModel
          this.incomeMap = incomeMap
          this.totalAmount = totalAmount
          this.monthData = []
          for (let index = 1; index <= 12; index++) {
            this.monthData.push({ month: `${index}月份`, value: this.incomeMap && this.incomeMap[index] ? this.incomeMap[index] : '-' })
          }
          this.uploadItem.forEach((item) => { item.fileLists = fileMap[item.type] ? fileMap[item.type] : [] })
          return Promise.resolve(true)
        } else {
          this.$message.error(response.errorMessage || '系统异常请联系管理员！')
          console.error(`function getTechInfo error:` + response.errorMessage || '系统异常请联系管理员！')
          return Promise.resolve(true)
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常请联系管理员！')
        console.error(`function getTechInfo error:` + error.message || '系统异常请联系管理员！')
        return Promise.resolve(true)
      })
      if (!result || !this.detailModel) {
        this.spinning = false
        return
      }
      if (!this.detailModel) {
        this.spinning = false
        return
      }
      this.hasSameTechStandardShow = !!(this.detailModel && this.detailModel.hasSameTechStandard)
      this.hasQualityStandardShow = !!(this.detailModel && this.detailModel.hasQualityStandard)
      this.hasOtherTechStandardShow = !!(this.detailModel && this.detailModel.otherTechStandard)
      if (this.detailModel && this.detailModel.techStandard) {
        this.detailModel.techStandard = this.detailModel && this.detailModel.techStandard ? this.detailModel.techStandard.split(',') : []
        const techStandardAry = []
        this.detailModel.techStandard.forEach(item => {
          techStandardAry.push(Number(item))
        })
        this.$set(this.detailModel, 'techStandard', techStandardAry)
      }
      this.$nextTick(() => {
        const fieldsVal = this.form.getFieldsValue()
        for (const key in fieldsVal) {
          if (typeof this.detailModel[key] !== 'undefined') {
            fieldsVal[key] = this.detailModel[key]
          }
        }
        this.form.setFieldsValue(fieldsVal)
        this.spinning = false
      })
    },
    onSave () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          if (values.techStandard) {
            values['techStandard'] = values.techStandard.join(',')
          }
          if (this.detailModel && this.detailModel.id) {
            values.id = this.detailModel.id
          }
          const highTechInfoModel = {
            id: this.record.id,
            detailModel: this.hasDetailChange ? values : undefined,
            projects: this.hasProjectChange ? this.projects : undefined
          }
          this.handlerSave(highTechInfoModel)
        }
      })
    },
    handlerSave (highTechInfoModel) {
      this.spinning = true
      saveDetail(highTechInfoModel).then(response => {
        if (response.success && response.data) {
          if (response.data > 0) {
            if (!this.detailModel) {
              this.$set(this, 'detailModel', { id: response.data })
            } else {
              this.detailModel['id'] = response.data
            }
          }
          this.$message.success('保存成功！')
        } else {
          this.$message.error(response.errorMessage || '系统异常请联系管理员！')
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常请联系管理员！')
      }).finally(() => {
        this.spinning = false
      })
    },
    hasSameTechStandardChange (e) {
      this.hasSameTechStandardShow = e.target.value
    },
    hasQualityStandardChange (e) {
      this.hasQualityStandardShow = e.target.value
    },
    techStandardChange (checkedValue) {
      this.hasOtherTechStandardShow = checkedValue.includes(4)
    },
    onUploadFile (type, typeName) {
      this.$refs.uploadFileModal.show(type, typeName)
      // this.$refs.uploadModal.show()
    },
    uploadError (name, message) {
      this.$message.error(`上传文件：${name},失败：${message}`)
    },
    uploadSuccess (name, message, type, file) {
      this.uploadItem.forEach(item => {
        if (item.type === type) {
          item.fileLists.push({ fileName: file.fileName, filePath: file.filepath, id: file.id })
        }
      })
      this.$message.success(`上传文件：${name},成功：${message}`)
    },
    onDownload (file) {
      this.$exportData('beian/download', { filePath: file.filePath }, file.fileName, this.$message)
    },
    onEyeFile (file) {
      this.$refs.previewModal.show(file.filePath, file.fileName)
    },
    onDelFile (type, file, index) {
      this.$confirm({
        title: '您确定要删除吗?',
        okText: '确定',
        okType: 'danger',
        cancelText: '取消',
        onOk: () => {
          this.handlerDelFile(type, file, index)
        },
        onCancel () { }
      })
    },
    handlerDelFile (type, file, index) {
      this.spinning = true
      delTechFile(file).then(resopnse => {
        if (resopnse.success && resopnse.data) {
          this.uploadItem.forEach(item => {
            if (item.type === type) {
              item.fileLists.splice(index, 1)
            }
          })
        } else {
          this.$message.error(resopnse.errorMessage || '系统异常请联系管理员！')
        }
      }).catch(error => {
        this.$message.error(error.message || '系统异常请联系管理员！')
      }).finally(() => {
        this.spinning = false
      })
    },
    renderTecIndustry (tecIndustry) {
      if (!tecIndustry) { return '' }
      const ary = tecIndustry.split(',')
      const reslut = []
      ary.forEach((item) => { reslut.push(this.tecIndustryMap[item]) })
      return reslut.join('-')
    },
    getFieldValue (field) {
      if (this.detailModel && this.detailModel[field]) {
        const value = this.detailModel[field]
        return config.detailMap[field][value]
      } else {
        return ''
      }
    },
    onExport () {
      this.spinning = true
      this.$exportData('highTech/exportHighTech', { highTechId: this.record.id, year: this.currentYear })
        .then()
        .finally(() => {
          this.spinning = false
        })
    }
  }
}
</script>

<style lang="less" scoped>
.container {
  height: 100%;
  overflow: auto;
  position: relative;
  & /deep/ .ant-spin-nested-loading {
      height: 100%;
    }
  & /deep/ .ant-spin-container {
    height: 100%;;
  }
   .title {
    font-size: 18px;
    font-weight: bold;
  }
  .ant-divider-horizontal {
    margin: 10px 0;
  }
  .exit_wrap {
    position: absolute;
    .exit {
      font-size: 27px;
      color: #1890FF;
    }
  }
  .btn_wrap {
    position: absolute;
    top: 2px;
    right: 0px;
    .btn_spcoe {
      margin: 0 3px;
    }
  }
  .form_wrap {
    height: calc(100% - 54px);
    overflow: auto;
    table {
      tbody {
        tr {
          td {
            padding: 8px;
          }
        }
      }
      .custom_input {
        width: 150px;
        margin-left: 8px;
        border: none;
        border-bottom: 1px solid;
        border-radius: unset;
        box-shadow: none;
        padding: 4px 0;
        width: 150px;
        margin-left: 8px;
      }
      .ant-checkbox-wrapper {
        margin-left: 0px;
      }
      ul {
        margin: 0 0;
        padding: 0 0;
        li {
          list-style: none;
          padding: 3px 3px;
          border-radius: 3px;
          display: flex;
          justify-content: space-between;
          border-bottom: 1px solid rgb(247, 241, 241);
          &:hover {
            background-color: rgb(247, 241, 241);
          }
          &:last-child{
            border-bottom: none;
          }
        }
      }
      .icon {
        cursor: pointer;
      }
      .close_icon {
        cursor: pointer;
        color: red;
        padding: 2px 2px;
        font-size: 14px;
      }
      .ant-form-item {
        display: inline-block;
        width: 100%;
        margin-bottom: 0px;
      }
    }
    & /deep/ .ant-spin-nested-loading {
      height: 100%;
    }
    & /deep/ .ant-spin-container {
      height: 100%;;
    }
  }
}
</style>
