<template>
  <a-modal
    :title="title"
    style="top: 20px;"
    :width="400"
    v-model="visible"
    @ok="handleSubmit"
    :afterClose="afterClose"
    :maskClosable="false"
  >
    <a-form @submit="handleSubmit" :form="form">
      <a-row :gutter="24">
        <a-col :span="24">
          <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-select
              showSearch
              :allowClear="true"
              placeholder="请选择负责人"
              :value="masterId"
              @change="selectChange">
              <a-select-option v-for="master in masterList" :key="master.id" :value="master.id">
                {{ master.masterName }}
              </a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
export default {
  components: {
  },
  data () {
    return {
      title: '',
      labelCol: {
        xs: { span: 4 },
        sm: { span: 6 }
      },
      wrapperCol: {
        xs: { span: 20 },
        sm: { span: 14 }
      },
      visible: false,
      ids: [],
      form: this.$form.createForm(this),
      masterId: undefined,
      masterList: []
    }
  },
  created () {
    this.queryMasters()
  },
  methods: {
    show (ids) {
      this.title = '分配负责人'
      this.visible = true
      this.form.resetFields()
      this.ids = ids
    },
    selectChange (value) {
      this.masterId = value
    },
    handleSubmit () {
      if (!this.masterId) {
        return
      }
      this.$http.get('/rsPatentPlan/allocationMaster', { params: { ids: this.ids, masterId: this.masterId } }).then(res => {
        if (res.success && res.data) {
          this.$message.success('分配成功')
          this.$emit('ok')
          this.visible = false
        } else {
          this.$message.error('分配失败')
        }
      })
    },
    queryMasters () {
      this.$http.get('/patentMaster/getMasterList').then(res => {
        if (res.success) {
          this.masterList = res.data
        }
      })
    },
    afterClose () {
      this.masterId = undefined
    }
  }
}
</script>

<style scoped>
.ant-form-item-children .ant-calendar-picker {
  width: 100%;
}
</style>
