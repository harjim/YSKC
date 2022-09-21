<!--
 * @Author: ldx
 * @Date: 2020-10-14 16:12:02
 * @LastEditTime: 2020-11-23 08:18:39
 * @LastEditors: ldx
 * @Description:
 * @FilePath: \MS-VUE\src\views\project\modules\StageListsModal.vue
-->
<template>
  <a-modal
    title="阶段列表"
    :width="200"
    :visible="isVisible"
    :afterClose="afterClose"
    :maskClosable="false"
    @cancel="isVisible = false"
    @ok="handleSubmit">
    <a-form >
      <a-row :gutter="24">
        <a-checkbox-group :options="stages" @change="onChange" :value="checkedValue" />
      </a-row>
    </a-form>
  </a-modal>
</template>
<script>
export default {
  name: 'StageListsModal',
  created () {
    // 获取阶段字典
    this.$getDictionary(7).then(res => {
      res.forEach((item, index) => {
        this.stages.push({ value: item.key, label: `${index + 1}、${item.value}` })
      })
    })
  },
  data () {
    return {
      isVisible: false,
      stages: [],
      checkedValue: []
    }
  },
  methods: {
    show (stages) {
      this.$nextTick(() => {
        this.isVisible = true
        this.checkedValue = stages
        for (const stage of this.stages) {
          for (const checkedKey of stages) {
            if (stage.value === checkedKey) {
              stage['disabled'] = true
              break
            } else {
              delete stage['disabled']
            }
          }
        }
      })
    },
    afterClose () {
    },
    handleSubmit () {
      this.$emit('refreshStage', this.checkedValue)
      this.isVisible = false
      this.checkedValue = []
    },
    onChange (checkedValue) {
      this.checkedValue = checkedValue
    }
  }
}
</script>
<style lang='less' scoped>
</style>
