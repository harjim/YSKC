<template>
  <a-modal
    centered
    destroyOnClose
    :visible="visible"
    title="权值设置"
    @cancel="$emit('close')"
    @ok="submitSet"
  >
    <a-form-model
      :label-col="{ span: 8 }"
      :model="form"
      :wrapper-col="{ span: 8 }"
    >
      <a-row>
        <a-col :span="12">
          <a-form-model-item
            label="立项报告"
            required
          >
            <a-input
              v-model="form[0].weight"
              placeholder="1"
            />
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item
            label="专利管理"
            required
          >
            <a-input
              v-model="form[1].weight"
              placeholder="0.1"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-model-item
            label="查新报告"
            required
          >
            <a-input
              v-model="form[2].weight"
              placeholder="0.2"
            />
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item
            label="备查资料"
            required
          >
            <a-input
              v-model="form[3].weight"
              placeholder="0.1"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-model-item
            label="创新体系"
            required
          >
            <a-input
              v-model="form[4].weight"
              placeholder="1"
            />
          </a-form-model-item>
        </a-col>
        <a-col :span="12">
          <a-form-model-item
            label="过程文档"
            required
          >
            <a-input
              v-model="form[5].weight"
              placeholder="0.083"
            />
          </a-form-model-item>
        </a-col>
      </a-row>
    </a-form-model>
    <template slot="footer">
      <a-button
        key="submit"
        :loading="loading"
        type="primary"
        @click="submitSet"
      >
        应用
      </a-button>
    </template>
  </a-modal>
</template>

<script>
import { getWeight, saveWeight } from '@/api/qualityScore'

export default {
  name: 'WeightSet',
  props: {
    visible: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    visible (n) {
      if (n) this.getWeight()
    }
  },
  data () {
    return {
      loading: false,
      form: {
        0: { type: 5, weight: 1 }, // 立项
        1: { type: 7, weight: 0.1 }, // 专利
        2: { type: 8, weight: 0.2 }, // 查新
        3: { type: 10, weight: 0.1 }, // 备查
        4: { type: 9, weight: 1 }, // 创新体系
        5: { type: 6, weight: 0.083 }, // 过程文档
        length: 6
      }
    }
  },
  methods: {
    async getWeight () {
      const data = await getWeight()
      const form = this.form
      for (let i = 0; i < data.length; i++) {
        if (data[i].type === form[i].type) form[i].weight = data[i].weight
      }
      this.form = form
    },
    async submitSet () {
      this.loading = true
      const data = await saveWeight({ form: this.form })
      if (data) this.$message.success('应用权值成功')
      this.loading = false
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
