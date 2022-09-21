<!--
 * @Author: your name
 * @Date: 2022-04-19 13:40:25
 * @LastEditors: lzh
 * @LastEditTime: 2022-04-19 17:21:31
 * @Description: 关联提案弹出框
 * @FilePath: \RS-VUE\src\views\project\modules\ProposalModal.vue
-->
<template>
  <a-modal
    :title="title"
    :visible="visible"
    @ok="submit"
    @cancel="visible = false"
    :confirmLoading="confirmLoading"
    destroyOnClose>
    <a-spin :spinning="spinning">
      <a-form :form="form" :labelCol="{ span: 5 }" :wrapperCol="{ span: 16 }">
        <a-form-item label="关联提案">
          <a-select
            mode="multiple"
            style="width: 100%"
            placeholder="请选择关联提案"
            v-decorator="['ids', {rules: [{required: false}]}]"
          >
            <a-select-option v-for="item in list" :key="item.id">
              {{ item.pname }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      form: this.$form.createForm(this),
      confirmLoading: false,
      spinning: false,
      list: [],
      title: ''
    }
  },
  methods: {
    show (row) {
      this.visible = true
      this.getDataById(row.id)
      this.projectId = row.id
      this.title = `关联提案【${row.pname}】`
    },
    getDataById (id) {
      this.spinning = true
      this.$http.get('/proposalList/selectByProjectId', { params: { id } }).then(res => {
        if (res.success && res.data) {
          this.list = res.data
          const selectedItems = []
          this.list.forEach((item) => {
            if (item.relevance) {
              selectedItems.push(item.id)
            }
          })
          this.form.setFieldsValue({ ids: selectedItems })
        } else {
          this.$message.error('获取数据失败')
        }
      }).finally(() => {
        this.spinning = false
      })
    },
    submit () {
      if (this.confirmLoading) {
        return
      }
      this.form.validateFields((errors, values) => {
        if (!errors) {
          this.confirmLoading = true
          this.$http.post('/proposalList/relevance', {
            projectId: this.projectId,
            ...values
          }).then(res => {
            if (res.success && res.data) {
              this.$message.success('操作成功')
              this.visible = false
              this.$emit('ok')
            } else {
              this.$message.error(res.errorMessage || '操作失败')
            }
          }).finally(() => {
            this.confirmLoading = false
          })
        }
      })
    }
  }
}
</script>
