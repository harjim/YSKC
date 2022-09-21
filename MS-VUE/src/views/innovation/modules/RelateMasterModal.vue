<!--
 * @Author: zdf
 * @Date: 2021-07-26 14:56:01
 * @LastEditTime: 2022-03-22 09:45:43
 * @LastEditors: zdf
 * @Description: 关联项目负责人
 * @FilePath: \MS-VUE\src\views\innovation\modules\RelateMasterModal.vue
-->
<template>
  <a-modal
    :width="400"
    :footer="null"
    :title="`分配${labels[mType]}`"
    @cancel="closeModal"
    :visible="visible">
    <a-spin :spinning="spinning" tip="请稍后...">
      <a-form :form="form">
        <a-row>
          <a-form-item :label="labels[mType]" :labelCol="{span: 7}" :wrapperCol="{span:17}">
            <a-select
              style="width:100%;"
              :placeholder="`请选择${labels[mType]}`"
              v-decorator="['userId', { rules: [{ required: true, message: `${labels[mType]}不能为空` }] }]">
              <a-select-option v-for="item in selectData" :key="item.memberId" :value="item.memberId">{{ item.realName }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-row>
      </a-form>
      <div style="text-align: right;">
        <a-button @click="closeModal" style="margin-right:10px;">关闭</a-button>
        <a-button @click="handleSubmit" type="primary">确定</a-button>
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
const labels = { '1': '技术负责人', '2': '财务负责人' }
export default {
  data () {
    return {
      visible: false,
      spinning: false,
      form: this.$form.createForm(this),
      row: {},
      labels,
      selectData: [],
      mType: undefined
    }
  },
  methods: {
    open (row, mType) {
      this.mType = mType
      if (this.row.companyId !== row.companyId || this.row.year !== row.year) {
        this.row = row
        this.loadMember()
      } else {
        this.row = row
      }
      this.visible = true
      this.$nextTick(() => {
        this.form.resetFields()
      })
    },
    loadMember () {
      this.$http.get('/rsProjectMaster/getMemberList', { params: { companyId: this.row.companyId, year: this.row.year, mType: this.mType } }).then(res => {
        if (res.data && res.success) {
          this.selectData = res.data
        } else {
          this.selectData = []
        }
      })
    },
    closeModal () {
      this.visible = false
      this.spinning = false
    },
    handleSubmit () {
      this.spinning = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (errors) {
          this.spinning = false
          return
        }
        values.companyId = this.row.companyId
        values.year = this.row.year
        values.rsProjectId = this.row.rsProjectId
        values.mType = this.mType
        this.$http.post('/rsProjectMaster/saveMaster', values).then(res => {
          if (res.success && res.data) {
            this.$emit('ok', false)
            this.$message.success('保存成功')
            this.closeModal()
          } else {
            this.$message.error(res.errorMessage || '保存失败')
            this.spinning = false
          }
        })
      })
    }
  }
}
</script>

<style>

</style>
