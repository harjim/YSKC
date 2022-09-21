<template>
  <a-modal
    :width="1000"
    v-model="visible"
    @cancel="closeModal"
    :title="title"
    :min-hight="600"
    style="top: 20px"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit">
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form :form="form">
        <a-row :gutter="24">
          <a-col :span="12">
            <a-form-item label="月份" :labelCol="{sm:{span: 3}}" :wrapperCol="{sm:{span: 12}}">
              <a-select
                @change="onChange"
                placeholder="请选择月份"
                v-decorator="['month', { rules: [{ required: true, message: '请选择月份' }] }]"
              >
                <a-select-option v-for="item in monthOptions" :key="item.value" :value="item.value">{{ item.label }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
      <div style="height:600px;width:100%;overflow-y:auto;" v-if="etypeMax">
        <template>
          <batch-etype-table
            v-for="(v, k) in etypeMax"
            :key="k"
            :ref="`batch${k}`"
            :configLenth="v"
            :ratioConfig="currentConfig[k]"
            :disabled="!Boolean(selectMonth)"
            :etypeName="k && k !== -1 ? $getEnums('rdEmployeeEnum').find(item => item.value === +k).label : ''"/>
        </template>
      </div>
    </a-spin>
  </a-modal>
</template>
<script>
import BatchEtypeTable from '../modules/BatchEtypeTable'
import moment from 'moment'
import yearMiXin from '@/utils/yearMixin'
// 初始30个RD,默认所有人员通用此配置
const defaultConfig = [
  [100], [60, 40], [50, 30, 20], [40, 30, 20, 10], [35, 25, 15, 10, 15],
  [25, 20, 15, 12, 13, 15], [20, 15, 12, 13, 10, 15, 8], [16, 15, 12, 13, 10, 15, 8, 7],
  [14, 11, 12, 13, 10, 15, 8, 7, 4], [14, 11, 12, 13, 10, 5, 8, 7, 4, 6], [13, 11, 12, 9, 10, 5, 8, 7, 4, 6, 11],
  [13, 10, 9, 8, 10, 5, 8, 7, 4, 6, 11, 9], [10, 8, 13, 9, 6, 5, 4, 6, 8, 9, 5, 12, 5],
  [7, 8, 10, 9, 6, 5, 4, 6, 8, 9, 5, 12, 5, 6], [7, 8, 5, 9, 6, 5, 4, 6, 8, 9, 5, 7, 5, 6, 10],
  [7, 8, 5, 4, 6, 5, 4, 6, 8, 9, 5, 7, 5, 6, 7, 8], [2, 3, 4, 6, 6, 5, 4, 6, 8, 9, 5, 7, 5, 6, 7, 8, 9],
  [2, 3, 4, 6, 6, 5, 4, 6, 3, 9, 5, 7, 5, 6, 7, 6, 5, 11], [2, 3, 4, 6, 6, 5, 4, 6, 3, 4, 5, 7, 5, 6, 7, 6, 5, 7, 9],
  [4, 3, 4, 6, 6, 5, 4, 6, 3, 4, 5, 7, 5, 2, 7, 6, 5, 7, 5, 6], [4, 3, 4, 6, 6, 5, 4, 2, 3, 4, 5, 7, 5, 2, 7, 6, 5, 7, 5, 6, 4],
  [4, 3, 4, 6, 6, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 12],
  [4, 3, 4, 6, 6, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 5, 7],
  [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 5, 7, 9],
  [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 2, 3, 8, 8],
  [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 2, 3, 7, 3, 6],
  [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 2, 3, 7, 3, 2, 4],
  [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 6, 5, 4, 5, 6, 4, 2, 3, 3, 3, 2, 4, 4],
  [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 3, 5, 4, 2, 6, 4, 2, 3, 3, 3, 2, 4, 4, 6],
  [4, 3, 4, 1, 2, 5, 4, 2, 3, 4, 5, 2, 5, 2, 3, 3, 5, 4, 2, 6, 4, 2, 3, 3, 3, 2, 4, 4, 2, 4]
]
export default {
  mixins: [yearMiXin],
  name: 'BatchAggModal',
  components: {
    BatchEtypeTable
  },
  props: {
    configType: {
      type: [Number, String],
      default: 0
    }
  },
  data () {
    return {
      etypeRatios: [0, 80, 65, 35],
      defaultConfig,
      form: this.$form.createForm(this),
      keys: [],
      etypeMax: undefined,
      currentYear: 0,
      title: '',
      visible: false,
      spinning: false,
      confirmLoading: false,
      currentConfig: {},
      selectMonth: undefined
    }
  },
  methods: {
    moment,
    showModal (keys, etypeMax, year) {
      this.keys = keys
      this.etypeMax = undefined
      this.selectMonth = undefined
      this.show(etypeMax, year)
    },
    show (etypeMax, year) {
      this.etypeMax = etypeMax
      this.currentConfig = {
        1: { configs: this.$deepClone(defaultConfig), etypeRatio: this.etypeRatios[1] },
        2: { configs: this.$deepClone(defaultConfig), etypeRatio: this.etypeRatios[2] },
        3: { configs: this.$deepClone(defaultConfig), etypeRatio: this.etypeRatios[3] }
      }
      this.visible = true
      this.currentYear = year
      this.title = '研发人员归集费用'
      this.form.resetFields()
    },
    loadConfig () {
      this.spinning = true
      this.confirmLoading = true
      return this.$http.get('/projectRdEmployee/getRdAggConfig', { params: { type: this.configType, month: moment([this.currentYear, this.selectMonth, 1, 0, 0, 0, 0]) } }).then(res => {
        if (res.success) {
          if (res.data) {
            this.currentConfig = JSON.parse(res.data)
          } else {
            this.currentConfig = {
              1: { configs: this.$deepClone(defaultConfig), etypeRatio: this.etypeRatios[1] },
              2: { configs: this.$deepClone(defaultConfig), etypeRatio: this.etypeRatios[2] },
              3: { configs: this.$deepClone(defaultConfig), etypeRatio: this.etypeRatios[3] }
            }
          }
        } else {
          this.$message.error(res.errorMessage ? res.errorMessage : '获取研发归集配置失败')
        }
      }).finally(() => {
        this.spinning = false
        this.confirmLoading = false
      })
    },
    closeModal () {
      this.visible = false
      // 通过按钮，esc关闭窗口，下次打开重新加载数据
      this.currentYear = 0
    },
    handleSubmit () {
      const {
        form: { validateFields }
      } = this
      this.spinning = true
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (errors) {
          this.spinning = false
          this.confirmLoading = false
          return
        }
        for (const key in this.etypeMax) {
          this.currentConfig[key].max = this.etypeMax[key]
        }
        const month = moment([this.currentYear, values.month, 1, 0, 0, 0, 0])
        this.$http.post('/projectRdEmployee/aggFee', { month, config: this.currentConfig, type: this.configType, keys: this.keys }).then(res => {
          if (res.success && res.data) {
            this.$message.success('归集成功')
            this.$emit('ok')
            this.visible = false
          } else {
            this.$message.error(res.errorMessage ? res.errorMessage : '归集失败')
          }
        }).finally(res => {
          this.confirmLoading = false
          this.spinning = false
        })
      })
    },
    onChange (value) {
      this.selectMonth = value
      this.loadConfig()
    }

  }
}
</script>
