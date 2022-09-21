<!--
 * @Author: ldx
 * @Date: 2021-02-25 12:03:18
 * @LastEditTime: 2021-03-01 16:30:27
 * @LastEditors: ldx
 * @Description: 项目情况汇总表
 * @FilePath: \RS-VUE\src\views\project\SituationGather.vue
-->
<template>
  <a-card class="contentPage" :bodyStyle="{height: '100%', overflow: 'auto'}">
    <div class="btn_wrap">
      <a-button size="small" type="primary" class="btn_spcoe" v-show="!isEdit" @click="changeMode(true)">编辑</a-button>
      <a-button size="small" type="primary" class="btn_spcoe" v-show="isEdit" @click="onReturn">返回</a-button>
      <a-button size="small" type="primary" class="btn_spcoe" v-show="isEdit" @click="changeMode(false)">保存</a-button>
    </div>
    <a-row type="flex" justify="center" align="middle">
      <a-col> <span class="title">{{ currentYear }}项目情况汇总表</span> </a-col>
    </a-row>
    <a-divider/>
    <div class="form_wrap">
      <a-spin :spinning="spinning" tip="加载中...">
        <a-form :form="form">
          <a-divider orientation="left">{{ currentYear }}年度项目实施情况</a-divider>
          <a-form-item label="年度回顾总述" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea v-if="isEdit" placeholder="年度回顾总述" :rows="5" v-decorator="['annualReview']"></a-textarea>
            <span v-else>{{ situationData.annualReview ? situationData.annualReview : '-' }}</span>
          </a-form-item>
          <a-form-item label="团队努力情况(企业+YS)" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea v-if="isEdit" placeholder="团队努力情况(企业+YS)" :rows="5" v-decorator="['teamEffort']"></a-textarea>
            <span v-else>{{ situationData.teamEffort ? situationData.teamEffort : '-' }}</span>
          </a-form-item>
          <a-form-item label="进度计划回顾" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea v-if="isEdit" placeholder="进度计划回顾" :rows="5" v-decorator="['scheduleReview']"></a-textarea>
            <span v-else>{{ situationData.scheduleReview ? situationData.scheduleReview : '-' }}</span>
          </a-form-item>
          <a-form-item label="价值实现情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea v-if="isEdit" placeholder="价值实现情况" :rows="5" v-decorator="['valueRealization']"></a-textarea>
            <span v-else>{{ situationData.valueRealization ? situationData.valueRealization : '-' }}</span>
          </a-form-item>
          <a-form-item label="问题总结" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <vxe-grid
              id="SituationGather"
              :data="situationData.problemSummarys"
              ref="table"
              highlight-current-row
              highlight-hover-row
              show-overflow="title"
              resizable
              auto-resize
              :show-footer="true"
            >

              <vxe-table-column
                title="遇到的问题"
                header-align="center"
                field="problem"
                align="left">
                <template #default="{ row }">
                  <a-textarea v-if="isEdit" placeholder="遇到的问题" :rows="1" v-model="row.problem"></a-textarea>
                  <span v-else>{{ row.problem }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="解决方案"
                header-align="center"
                field="solution"
                align="left">
                <template #default="{ row }">
                  <a-textarea v-if="isEdit" placeholder="解决方案" :rows="1" v-model="row.solution"></a-textarea>
                  <span v-else>{{ row.solution }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="操作"
                :width="80"
                header-align="center"
                align="center">
                <template #default="{ row, rowIndex }">
                  <a :disabled="!isEdit" @click="delRow(row,rowIndex)">移除</a>
                </template>
              </vxe-table-column>
              <template v-slot:bottom>
                <a-button v-if="isEdit" title="点击添加问题" type="dashed" style="width: 100%;font-weight: bolder" @click="addRow">+</a-button>
              </template>
            </vxe-grid>
          </a-form-item>
          <a-form-item label="后续需加强点" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea v-if="isEdit" placeholder="后续需加强点" :rows="5" v-decorator="['improvementPoints']"></a-textarea>
            <span v-else>{{ situationData.improvementPoints ? situationData.improvementPoints : '-' }}</span>
          </a-form-item>
          <a-divider orientation="left">研发/研发项目来源</a-divider>
          <a-form-item label="研发存在于那些部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea v-if="isEdit" placeholder="研发存在于那些部门" :rows="5" v-decorator="['rdDepts']"></a-textarea>
            <span v-else>{{ situationData.rdDepts ? situationData.rdDepts : '-' }}</span>
          </a-form-item>
          <a-form-item label="研发内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea v-if="isEdit" placeholder="研发内容" :rows="5" v-decorator="['rdContent']"></a-textarea>
            <span v-else>{{ situationData.rdContent ? situationData.rdContent : '-' }}</span>
          </a-form-item>
          <a-form-item label="取得的效果/成效" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea v-if="isEdit" placeholder="取得的效果/成效" :rows="5" v-decorator="['achievements']"></a-textarea>
            <span v-else>{{ situationData.achievements ? situationData.achievements : '-' }}</span>
          </a-form-item>
          <a-divider orientation="left">研发项目汇总</a-divider>
          <a-form-item label="研发项目管理" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <vxe-grid
              id="SituationGather"
              :data="situationData.projectSituations"
              size="small"
              ref="table"
              highlight-current-row
              highlight-hover-row
              show-overflow="title"
              resizable
              auto-resize
            >
              <vxe-table-column
                title="项目编号"
                width="120"
                header-align="center"
                field="rdTitle"
                align="left">
              </vxe-table-column>
              <vxe-table-column
                title="项目名称"
                header-align="center"
                field="pname"
                align="left">
              </vxe-table-column>
              <vxe-table-column
                title="项目负责人"
                width="120"
                header-align="center"
                field="manager"
                align="left">
              </vxe-table-column>
              <vxe-table-column
                title="项目起止日期"
                width="180"
                header-align="center"
                field="solution"
                align="center">
                <template #default="{ row }">
                  {{ row.beginDate ? moment(row.beginDate).format('YYYY-MM-DD') : '' }} - {{ row.endDate ? moment(row.endDate).format('YYYY-MM-DD') : '' }}
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="预算"
                width="100"
                header-align="center"
                field="budget"
                align="right">
                <template #default="{ row }">
                  {{ row.budget ? row.budget : '-' }}
                </template>
              </vxe-table-column>
              <vxe-table-column
                title="核算"
                width="100"
                header-align="center"
                field="account"
                align="right">
                <template #default="{ row }">
                  {{ row.account ? row.account : '-' }}
                </template>
              </vxe-table-column>
            </vxe-grid>
          </a-form-item>
        </a-form>
      </a-spin>
    </div>
  </a-card>
</template>

<script>
import { querySituation, saveSituation } from '@/api/situationGather/index'
import { mapGetters } from 'vuex'
import yetable from '@/components/Table/ystable'
import { cloneDeep } from 'lodash'
import moment from 'moment'
const situationData = {
  id: undefined,
  achievements: undefined,
  annualReview: undefined,
  improvementPoints: undefined,
  problemSummarys: [],
  projectSituations: [],
  rdContent: undefined,
  rdDepts: undefined,
  scheduleReview: undefined,
  teamEffort: undefined,
  valueRealization: undefined,
  year: undefined
}
export default {
  name: 'SituationGather',
  components: {
    yetable
  },
  mounted () {
    this.handleQuerySituation()
  },
  computed: {
    ...mapGetters(['currentYear'])
  },
  watch: {
    currentYear () {
      this.handleQuerySituation()
      this.isEdit = false
    }
  },
  data () {
    return {
      form: this.$form.createForm(this),
      labelCol: { xs: { span: 24 }, sm: { span: 6 }, md: { span: 4 } },
      wrapperCol: { xs: { span: 24 }, sm: { span: 18 }, md: { span: 20 } },
      isEdit: false,
      spinning: false,
      situationData,
      emptyData: situationData,
      backupSituationData: situationData
    }
  },
  methods: {
    moment,
    /**
     * @description: 返回按钮
     * @param {*} null
     * @return {*} null
     */
    onReturn () {
      this.isEdit = false
      this.form.resetFields()
      if (this.backupSituationData) {
        this.$nextTick(() => {
          this.$initForm(this.form, this.backupSituationData)
        })
      }
    },
    /**
     * @description: 切换模式按钮
     * @param {*} model 编辑true  保存false
     * @return {*}null
     */
    changeMode (model) {
      // this.isEdit = model
      if (model) {
        this.isEdit = model
        this.spinning = !this.spinning
        this.transformFormData()
        this.spinning = !this.spinning
      } else {
        this.handleSaveSituation()
      }
    },
    /**
     * @description: 获取项目情况汇总表
     * @param {*} null
     * @return {*} null
     */
    handleQuerySituation () {
      this.spinning = !this.spinning
      querySituation({ year: this.currentYear }).then((resolve, reject) => {
        if (resolve.success) {
          if (resolve.data) {
            this.situationData = resolve.data
            this.backupSituationData = cloneDeep(this.situationData)
            if (!this.situationData.problemSummarys) {
              this.$set(this.situationData, 'problemSummarys', [])
            }
          } else {
            this.situationData = cloneDeep(this.emptyData)
          }
        } else {
          this.situationData = cloneDeep(this.emptyData)
          this.$message.info('数据查询出错!', resolve.errorCode, resolve.errorMessage)
          console.log('handleSaveSituation function error' + resolve.errorCode, resolve.errorMessage)
        }
        this.backupSituationData = cloneDeep(this.situationData)
        this.transformFormData()
      }).catch((error) => {
        this.$message.info('数据查询出错', error)
        console.log('handleQuerySituation function error: ', +error)
      }).finally(() => {
        this.spinning = !this.spinning
      })
    },
    /**
     * @description: 保存项目情况总结数据
     * @param {*} null
     * @return {*} null
     */
    handleSaveSituation () {
      this.form.validateFields((error, values) => {
        if (!error) {
          this.spinning = !this.spinning
          values.year = this.currentYear
          values.id = this.situationData.id
          if (this.situationData.problemSummarys && this.situationData.problemSummarys.length) {
            values.problemSummarys = this.situationData.problemSummarys
          }
          saveSituation(values).then((resolve, reject) => {
            if (resolve.success && resolve.data) {
              Object.assign(this.situationData, values)
              this.backupSituationData = cloneDeep(this.situationData)
              this.isEdit = false
            } else {
              this.$message.info('操作失败！', resolve.errorCode, resolve.errorMessage)
              this.situationData = cloneDeep(this.backupSituationData)
              console.log('handleSaveSituation function error' + resolve.errorCode, resolve.errorMessage)
            }
          }).catch((error) => {
            console.log('handleSaveSituation function error' + error)
            this.situationData = cloneDeep(this.backupSituationData)
          }).finally(() => {
            this.spinning = !this.spinning
          })
        }
      })
    },
    /**
     * @description: 初始化表单数据
     * @param {*}
     * @return {*}
     */
    transformFormData () {
      this.form.resetFields()
      if (this.situationData) {
        this.$nextTick(() => {
          this.$initForm(this.form, this.situationData)
        })
      }
    },
    addRow () {
      const row = { problem: undefined, solution: undefined }
      this.situationData.problemSummarys.push(row)
    },
    delRow (record, rowIndex) {
      const slef = this
      this.$confirm({
        title: '您确定要删除吗？',
        onOk () {
          slef.handleDelRow(record, rowIndex)
        }
      })
    },
    handleDelRow (record, rowIndex) {
      this.situationData.problemSummarys.splice(rowIndex, 1)
    }
  }
}
</script>

<style lang="less" scoped>
.contentPage {
  height: 100%;
  overflow: auto;
  position: relative;
   .title {
    font-size: 18px;
    font-weight: bold;
  }
  .ant-divider-horizontal {
    margin: 10px 0;
  }
  .btn_wrap {
    position: absolute;
    top: 28px;
    right: 26px;
    .btn_spcoe {
      margin: 0 3px;
    }
  }
  .form_wrap {
    height: calc(100% - 54px);
    overflow: auto;
    & /deep/ .ant-spin-nested-loading {
      height: 100%;
    }
    & /deep/ .ant-spin-container {
      height: 100%;;
    }
  }
}

</style>
