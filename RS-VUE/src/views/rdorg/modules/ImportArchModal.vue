<!--
 * @Author: ldx
 * @Date: 2020-11-25 16:36:09
 * @LastEditTime: 2021-05-14 08:33:01
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \RS-VUE\src\views\rdorg\modules\ImportArchModal.vue
-->
<template>
  <div>
    <a-modal
      :title="title"
      :visible="visible"
      @ok="handleOk"
      width="300px"
      :maskClosable="false"
      :confirmLoading="confirmLoading"
      @cancel="closeModal"
    >
      <a-form :form="form">
        <a-form-item :label-col="{ span: 0 }" :wrapper-col="{ span: 24 }">
          <a-select
            :allowClear="true"
            style="width: 250px"
            v-decorator="['chYear',{rules: [{ required: true, message: '请选择要导入的组织架构年份' }]}]"
            placeholder="请选择要导入的组织架构年份"
          >
            <a-select-option v-for="(item,index) in yearList" :key="index" :value="item">{{ item }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>
</template>

<script>
export default {
  data () {
    return {
      title: '',
      visible: false,
      confirmLoading: false,
      yearList: [],
      form: this.$form.createForm(this),
      currentYear: undefined
    }
  },
  methods: {
    showModal (yearList, currentYear) {
      this.currentYear = currentYear
      this.form.resetFields()
      if (this.yearList !== yearList) {
        this.yearList = yearList
      }
      this.$nextTick(() => {
        this.title = `导入[${currentYear}]组织架构`
        this.visible = true
      })
    },
    handleOk () {
      this.confirmLoading = true
      const { form: { validateFields } } = this
      validateFields((errors, values) => {
        if (!errors) {
          this.$http.post('/rdDept/importYear', { currentYear: this.currentYear, chYear: values.chYear })
            .then(res => {
              if (res.data && res.success) {
                this.$message.success('导入成功')
                this.$emit('ok', true)
                this.closeModal()
              } else {
                this.$message.error(res.errorMessage ? res.errorMessage : '导入失败')
              }
            }).finally(() => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    closeModal () {
      this.visible = false
    }
  }
}
</script>

<style>
</style>
