<!--
 * @Author: ldx
 * @Date: 2021-05-29 08:51:25
 * @LastEditTime: 2021-07-13 18:34:28
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\highTech\modules\AddHighTechModal.vue
-->
<template>
  <a-modal
    :title="title"
    :width="550"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @cancel="isVisible = false"
    @ok="handleSubmit"
  >
    <a-form :form="form">
      <!-- <a-form-item
        label="年份"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol">
        <year-select style="width: 100%;" @change="onYearChange" v-decorator="['startYear', {rules:[{required: true, message: '请选择年份'}]}]"></year-select>
      </a-form-item> -->
      <a-form-item label="高新技术代码" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input-group compact>
          <span style="margin-top:5px;">{{ year }}PS&nbsp;</span>
          <a-input-number
            style="width: calc(100% - 62px);"
            :min="1"
            :max="999999999"
            :precision="0"
            step="1"
            placeholder="编号"
            v-decorator="[
              'codeNum',
              {
                rules: [{ required: true, message: '请输入RD编号' }, { validator: this.checkHcode }],
                validateTrigger: ['blur', 'submit']
              }
            ]"
          ></a-input-number>
        </a-input-group>
      </a-form-item>
      <a-form-item label="高新技术产品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input
          placeholder="高新技术产品名称"
          v-decorator="[
            'hname',
            {
              rules: [
                { required: true, whitespace: true, message: '请输入高新技术产品名称' },
                { validator: this.checkHname }
              ],
              validateTrigger: ['blur', 'submit']
            }
          ]"
        ></a-input>
      </a-form-item>
      <a-form-item label="高新技术领域" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-cascader
          :fieldNames="{ label: 'value', value: 'key', children: 'children' }"
          :options="tecIndustryLevel"
          placeholder="请选择高新领域"
          v-decorator="[
            'tecIndustry',
            {
              rules: [{ required: true, message: '请选择高新领域' }],
              normalize: val => (typeof val === 'string' ? val.split(',') : val)
            }
          ]"
        />
      </a-form-item>
      <a-form-item label="高品说明" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-textarea
          :auto-size="{ minRows: 5 }"
          :maxLength="1000"
          placeholder="请输入高品说明"
          v-decorator="['description']"
        />
      </a-form-item>
      <!-- <a-form-item
        label="当期累计总收入"
        :labelCol="labelCol"
        :wrapperCol="wrapperCol">
        <a-input-number
          style="width:100%;"
          :min="0"
          :max="9999999999999999"
          :precision="2"
          placeholder="当期累计总收入"
          v-decorator="['totalAmount',{ rules: [{required: true,message:'请输入当期总收入' }] }]"></a-input-number>
      </a-form-item> -->
    </a-form>
  </a-modal>
</template>
<script>
import YearSelect from '@/components/YearSelect/index'
import { save, verifyCodeAndName, getMaxCodeNum } from '@/api/highTech/highTech'
import { mapGetters } from 'vuex'
export default {
  name: 'AddHighTechModal',
  components: {
    YearSelect
  },
  data () {
    return {
      isVisible: false,
      title: '',
      form: this.$form.createForm(this),
      tecIndustryLevel: [], // 高新领域
      confirmLoading: false,
      record: undefined,
      year: undefined,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 }
      }
    }
  },
  mounted () {},
  computed: {
    ...mapGetters(['currentYear'])
  },
  methods: {
    show (title = '添加高品信息') {
      this.isVisible = true
      this.title = title
      this.tecIndustryLevel = this.$store.state.sysDictionary.highTecIndustryTree // 高新领域
      this.year = this.currentYear
      this.handlerGetMaxCodeNum(this.currentYear)
    },
    edit (record, title = '编辑高品信息') {
      this.title = title
      this.isVisible = true
      this.tecIndustryLevel = this.$store.state.sysDictionary.highTecIndustryTree // 高新领域
      this.record = record
      this.year = this.record.startYear
      this.$http.get('/highTech/getDescription', { params: { hcode: this.record.hcode } }).then(res => {
        if (res.success && res.data) {
          this.form.setFieldsValue({ description: res.data })
        }
      })
      this.$nextTick(() => {
        const { tecIndustry, codeNum, hname } = record
        this.form.setFieldsValue({ tecIndustry, codeNum, hname })
      })
    },
    afterClose () {
      this.form.resetFields()
      this.confirmLoading = false
      this.record = undefined
      this.year = undefined
    },
    handleSubmit () {
      this.form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          values.tecIndustry = values.tecIndustry ? values.tecIndustry.join(',') : values.tecIndustry
          if (this.record && this.record.startYear) {
            values.startYear = this.record.startYear
          } else {
            values.startYear = this.currentYear
          }
          if (this.record) {
            values.id = this.record.id
          }
          save(values)
            .then(response => {
              if (response.data && response.success) {
                this.$message.success(`操作成功！`)
                this.$emit('refresh')
                this.isVisible = false
              } else {
                this.$message.error(response.errorMessage || `操作失败`)
              }
            })
            .catch(error => {
              this.$message.error(error.message || `保存失败请联系管理员`)
            })
            .finally(res => {
              this.confirmLoading = false
            })
        }
      })
    },
    checkHcode (rule, value, callback) {
      if (value) {
        const param = {
          hcode: this.year + 'PS' + (value <= 9 ? '0' + value : value),
          highTechId: this.record && this.record.id ? this.record.id : undefined
        }
        verifyCodeAndName(param)
          .then(response => {
            if (response.data) {
              callback()
            } else {
              callback(new Error('编号已存在,请重新输入'))
            }
            return response.data
          })
          .catch(error => {
            this.$message.error(error.message || '系统异常请联系管理员！')
          })
      } else {
        callback()
      }
    },
    checkHname (rule, value, callback) {
      if (value) {
        const param = {
          hname: value,
          highTechId: this.record && this.record.id ? this.record.id : undefined
        }
        verifyCodeAndName(param)
          .then(response => {
            if (response.data) {
              callback()
            } else {
              callback(new Error('高新技术名称已存在,请重新输入'))
            }
            return response.data
          })
          .catch(error => {
            this.$message.error(error.message || '系统异常请联系管理员！')
          })
      } else {
        callback()
      }
    },
    handlerGetMaxCodeNum (year) {
      getMaxCodeNum({ year })
        .then(response => {
          if (response.success) {
            this.form.setFieldsValue({ codeNum: response.data + 1 })
          } else {
            this.$message.error(response.errorMessage || '系统异常请联系管理员！')
          }
        })
        .catch(error => {
          this.$message.error(error.message || '系统异常请联系管理员！')
        })
    }
  }
}
</script>
<style lang="less" scoped></style>
