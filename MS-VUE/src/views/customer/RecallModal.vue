<template>
  <a-modal
    title="撤回"
    v-model="visible"
    @ok="handleSubmit"
    :okButtonProps="{props: { loading: loading }}"
    destroyOnClose
  >
    <a-form-model ref="form" :model="form" v-bind="{ labelCol, wrapperCol, rules }">
      <a-form-model-item label="撤回原因" required prop="suggestion">
        <a-textarea
          v-model="form.suggestion"
          :maxLength="200"
          :rows="3"
          placeholder="请输入撤回说明"
        />
      </a-form-model-item>
    </a-form-model>
  </a-modal>
</template>

<script>
export default {
  name: 'RecallModal',
  components: {},
  props: {},
  data () {
    return {
      labelCol: {
        span: 5
      },
      wrapperCol: {
        span: 19
      },
      visible: false,
      loading: false,
      form: {},
      rules: {
        suggestion: [
          { required: true, whitespace: true, min: 5, message: '请输入5个字以上撤回原因' }
        ]
      },
      instanceIds: []
    }
  },
  computed: {},
  watch: {},
  methods: {
    open (ids) {
      this.instanceIds = [...ids]
      this.visible = true
    },
    close () {
      this.visible = false
      Object.assign(this.$data, this.$options.data())
    },
    handleSubmit () {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true
          const params = {
            instanceIds: this.instanceIds,
            ...this.form
          }
          this.$http.post('/myAudit/recall', params).then(({ success, errorMessage }) => {
            if (success) {
              this.$message.success('操作成功')
              this.close()
              this.$emit('ok', false)
            } else {
              this.$message.error(errorMessage)
            }
          }).finally(() => {
            this.loading = false
          })
        }
      })
    }
  },
  beforeCreate () {
  },
  created () {
  },
  beforeMount () {
  },
  mounted () {
  },
  beforeUpdate () {
  },
  updated () {
  },
  beforeDestroy () {
  },
  destroyed () {
  }
}
</script>

<style scoped>

</style>
